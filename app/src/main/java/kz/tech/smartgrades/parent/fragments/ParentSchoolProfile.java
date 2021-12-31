package kz.tech.smartgrades.parent.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentSchoolChildListAdapter;
import kz.tech.smartgrades.parent.model.ModelParentDeleteChildFromSchool;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_ParentDeleteChildFromSchool;

public class ParentSchoolProfile extends Fragment implements View.OnClickListener, ParentSchoolChildListAdapter.OnItemClickListener{

    private ParentActivity activity;
    private ArrayList<ModelUser> childList;
    private ModelSchoolInfo mSchool;
    private String PARENT_ID;

    private ImageView ivNav;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvContacts;
    private RecyclerView rvChildList;

    private ParentSchoolChildListAdapter parentSchoolChildListAdapter;

    public ParentSchoolProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_school_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    private void initUI(View view) {
        ivNav = view.findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        tvName = view.findViewById(R.id.tvName);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvContacts = view.findViewById(R.id.tvContacts);
        rvChildList = view.findViewById(R.id.rvChildList);
    }

    private void setChildList() {
        rvChildList.setLayoutManager(new LinearLayoutManager(activity));
        parentSchoolChildListAdapter = new ParentSchoolChildListAdapter(activity);
        parentSchoolChildListAdapter.setOnItemClickListener(this);
        rvChildList.setAdapter(parentSchoolChildListAdapter);
        parentSchoolChildListAdapter.updateData(childList);
    }

    private void setSchoolInfo() {
        if (!stringIsNullOrEmpty(mSchool.getSchoolName())) tvName.setText(mSchool.getSchoolName());
        if (!stringIsNullOrEmpty(mSchool.getAddress())) tvAddress.setText(mSchool.getAddress());
        String contact = "";
        if (!stringIsNullOrEmpty(mSchool.getPhones().get(0).getPhone())) contact += mSchool.getPhones().get(0).getPhone() + "\n";
        if (!stringIsNullOrEmpty(mSchool.getMails().get(0).getMail())) contact += mSchool.getMails().get(0).getMail() + "\n";
        if (!stringIsNullOrEmpty(contact)) tvContacts.setText(contact);
    }

    private void onBack() {
        activity.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNav:
                onBack();
                break;
        }
    }

    public void setSchoolModel(ModelSchoolInfo mSchool) {
        this.mSchool = mSchool;
        this.childList = mSchool.getChildren();
        setSchoolInfo();
        setChildList();
    }

    @Override
    public void onClick(ModelUser m) {
        ModelParentDeleteChildFromSchool model = new ModelParentDeleteChildFromSchool();
        model.setChildId(m.getUserId());
        model.setParentId(PARENT_ID);
        model.setSchoolId(mSchool.getSchoolId());
        String SOAP = SoapRequest(func_ParentDeleteChildFromSchool, new Gson().toJson(model));
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.alert.onToast(answer.getMessage());
                            if (answer.isSuccess()) {
                                activity.updatePresenter();
                                parentSchoolChildListAdapter.removeChild(m);
                                if (parentSchoolChildListAdapter.getItemCount() == 0) {
                                    activity.onBackPressed();
                                    activity.onBackPressed();
                                }
                            }
                        }
                    });
                }
            }
        });
    }
}