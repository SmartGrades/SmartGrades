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
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentSchoolListAdapter;
import kz.tech.smartgrades.parent.model.ModelParentData;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetSchools;

public class ParentAddSchoolFragment extends Fragment implements View.OnClickListener, ParentSchoolListAdapter.OnItemCLickListener{

    private ParentActivity activity;
    private String PARENT_ID;

    private ImageView ivBack;

    private ParentSchoolListAdapter schoolListAdapter;
    private RecyclerView rvSchoolList;

    private ModelParentData mParentData;
    private int CHILD_SELECT_INDEX;

    private TextView tvListEmpty;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_school_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view){
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);

        tvListEmpty = view.findViewById(R.id.tvListEmpty);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        schoolListAdapter = new ParentSchoolListAdapter(activity);
        schoolListAdapter.setOnItemCLickListener(this);
        rvSchoolList = view.findViewById(R.id.rvSchoolList);
        rvSchoolList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvSchoolList.setAdapter(schoolListAdapter);
    }
    private void onLoadChatsList() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, PARENT_ID);

        String SOAP = SoapRequest(func_GetSchools, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){ }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = _Web.XMLToJson(response.body().string());
                    activity.runOnUiThread(() -> {
                        if(!result.equals("null")){
                            ArrayList<ModelSchoolData> schoolList = new Gson().fromJson(result, new TypeToken<ArrayList<ModelSchoolData>>(){
                            }.getType());
                            if (!listIsNullOrEmpty(schoolList)){
                                schoolListAdapter.updateData(schoolList);
                                tvListEmpty.setVisibility(View.GONE);
                            }
                            else tvListEmpty.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onSchoolClick(ModelSchoolData m){
        activity.setSchoolProfile(m);
        activity.onNextFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
        }
    }

    private void onBack() {
        activity.onBackPressed();
    }

    public void setData(ModelParentData mParentData, int select_child_index){
        this.mParentData = mParentData;
        CHILD_SELECT_INDEX = select_child_index;
        onLoadChatsList();
    }
}