package kz.tech.smartgrades.mentor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_InterFormMentorToSchool;

public class MentorAddSchoolProfileFragment extends Fragment implements View.OnClickListener {

    private MentorActivity activity;
    private String MENTOR_ID;
    private ModelSchoolData mSelectSchool;

    private ImageView ivBack;
    private Button btnUnite, btnAdd;
    private TextView tvSchoolName, tvSchoolState, tvAddress, tvAbout, tvLogin, tvContacts;

    public static MentorAddSchoolProfileFragment newInstance(ModelMentorData mMentorData) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", mMentorData);
        MentorAddSchoolProfileFragment fragment = new MentorAddSchoolProfileFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MentorActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fgmt_mentor_add_school_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view) {
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        btnUnite = view.findViewById(R.id.btnUnite);
        btnUnite.setOnClickListener(this);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        tvSchoolName = view.findViewById(R.id.tvSchoolName);
        tvSchoolState = view.findViewById(R.id.tvSchoolState);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvAbout = view.findViewById(R.id.tvAbout);
        tvLogin = view.findViewById(R.id.tvLogin);
        tvContacts = view.findViewById(R.id.tvContacts);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.btnUnite:
                onUniteClick();
                break;
            case R.id.btnAdd:
                onAddClick();
                break;
        }
    }
    private void onUniteClick() {

    }
    private void onAddClick() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.MentorId, MENTOR_ID);
        jsonData.addProperty(F.SchoolId, mSelectSchool.getId());

        String SOAP = SoapRequest(func_InterFormMentorToSchool, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.alert.onToast(answer.getMessage());
                        }
                    });
                }
            }
        });
    }
    private void onBack() {
        activity.onBackPressed();
    }

    public void setSelectSchool(ModelSchoolData m) {
        mSelectSchool = m;
        tvSchoolName.setText(mSelectSchool.getName());
        tvAddress.setText(mSelectSchool.getAddress());
    }
}