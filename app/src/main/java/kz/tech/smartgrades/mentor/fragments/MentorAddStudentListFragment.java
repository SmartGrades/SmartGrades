package kz.tech.smartgrades.mentor.fragments;

import android.os.Bundle;
import android.os.Parcelable;
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
import kz.tech.smartgrades.auth.models.ModelUserData;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorAddStudentListAdapter;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.root.login.LoginKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetChildrenList;

public class MentorAddStudentListFragment extends Fragment implements View.OnClickListener, MentorAddStudentListAdapter.OnItemClickListener{

    private MentorActivity activity;
    private String MENTOR_ID;
    private ModelMentorData mMentorData;

    private ImageView ivBack;
    private TextView tvTitleBar;
    private ImageView ivSearch;

    private RecyclerView rvStudents;
    private MentorAddStudentListAdapter studentsListAdapter;


    public static MentorAddStudentListFragment newInstance(ModelMentorData mMentorData){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", (Parcelable) mMentorData);
        MentorAddStudentListFragment fragment = new MentorAddStudentListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (MentorActivity) getActivity();
        if(getArguments() != null) mMentorData = getArguments().getParcelable("model");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fgmt_mentor_add_student_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onLoadWardOnAddition();
    }
    private void initViews(View view){
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        tvTitleBar = view.findViewById(R.id.tvTitleBar);
        ivSearch = view.findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(this);

        rvStudents = view.findViewById(R.id.rvStudents);
        studentsListAdapter = new MentorAddStudentListAdapter(activity);
        studentsListAdapter.setOnItemClickListener(this);
        rvStudents.setLayoutManager(new LinearLayoutManager(activity));
        rvStudents.setAdapter(studentsListAdapter);
    }
    private void onLoadWardOnAddition(){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, MENTOR_ID);

        String SOAP = SoapRequest(func_GetChildrenList, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLReader(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!result.equals("null")) {
                                ArrayList<ModelUserData> dataList = new Gson().fromJson(result, new TypeToken<ArrayList<ModelUserData>>(){
                                }.getType());
                                studentsListAdapter.updateData(dataList);
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
                break;
        }
    }
    private void onBack(){
        activity.onBackPressed();
    }

    @Override
    public void onItemClick(ModelUserData m){
        activity.onSetSelectStudentData(m);
        activity.onNextFragment();
    }
}
