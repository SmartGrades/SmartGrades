package kz.tech.smartgrades.school.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.root.dialogs.DefaultDialog;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolTeachersListAdapterV3;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;
import kz.tech.smartgrades.school.models.ModelTeachersList;
import kz.tech.smartgrades.school.models.ModelUpdateTeachers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetTeachersListForAddedToClass;
import static kz.tech.smartgrades._Web.func_UpdateTeachersInClass;

public class SchoolMoveTeachersListFragment extends Fragment implements View.OnClickListener, DefaultDialog.OnClickListener, SchoolTeachersListAdapterV3.OnItemClickListener{

    private SchoolActivity activity;
    private String SCHOOL_ID;
    private ImageView ivBack, ivOk;

    private RecyclerView rvStudentsList;
    private ConstraintLayout clLoading;
    private SchoolTeachersListAdapterV3 ListAdapter;

    private ModelSchoolData mSchoolData;
    private ModelSchoolClass mSchoolClass;


    public static SchoolMoveTeachersListFragment newInstance(ModelMentorData mMentorData){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", mMentorData);
        SchoolMoveTeachersListFragment fragment = new SchoolMoveTeachersListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (SchoolActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_school_move_teachers_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initViews(View view){
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);
        clLoading = view.findViewById(R.id.clLoading);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivOk = view.findViewById(R.id.ivOk);
        ivOk.setOnClickListener(this);

        rvStudentsList = view.findViewById(R.id.rvStudentsList);
        ListAdapter = new SchoolTeachersListAdapterV3(activity);
        ListAdapter.setOnItemClickListener(this);
        rvStudentsList.setLayoutManager(new LinearLayoutManager(activity));
        rvStudentsList.setAdapter(ListAdapter);
        loadData();
    }

    private void loadData() {
        clLoading.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(F.SchoolId, activity.login.loadUserDate(LoginKey.ID));
        jsonObject.addProperty(F.ClassId, mSchoolClass.getId());
        String SOAP = SoapRequest(func_GetTeachersListForAddedToClass, new Gson().toJson(jsonObject));
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
                System.out.println(e.toString());
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = _Web.XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            ArrayList<ModelTeachersList> modelTeachersLists = new Gson().fromJson(result, new TypeToken<ArrayList<ModelTeachersList>>(){
                            }.getType());
                            ListAdapter.updateData(modelTeachersLists);
                            ListAdapter.setClassId(mSchoolClass.getId());
                            clLoading.setVisibility(View.GONE);
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
                activity.onBackPressed();
                break;
            case R.id.ivOk:
                onCancelClick();
                break;
        }
    }
    private void onCancelClick(){
        DefaultDialog dialog = new DefaultDialog(activity);
        dialog.setCancel(activity.getResources().getColor(R.color.gray_average_dark), getString(R.string.Do_not_save));
        dialog.setExit(activity.getResources().getColor(R.color.blue_sea), getString(R.string.save));
        dialog.setMessage(getString(R.string.save_changes));
        dialog.setOnClickListener(this);
        dialog.show();
    }

    public void setClassData(ModelSchoolData mSchoolData, ModelSchoolClass mSchoolClass){
        this.mSchoolData = mSchoolData;
        this.mSchoolClass = mSchoolClass;
    }
    public void updateClassData(ModelSchoolData mSchoolData){
        this.mSchoolData = mSchoolData;
        if (ListAdapter != null) {
            loadData();
        }
    }

    @Override
    public void onCancelDialog() {
        activity.onBackPressed();
    }

    @Override
    public void onExitDialog() {
        onSaveChanges();
    }

    private void onSaveChanges() {
        ModelUpdateTeachers model = new ModelUpdateTeachers();
        model.setTeachersList(ListAdapter.getMTeachers());
        model.setClassId(mSchoolClass.getId());
        model.setSchoolId(activity.login.loadUserDate(LoginKey.ID));
        String SOAP = SoapRequest(func_UpdateTeachersInClass, new Gson().toJson(model));
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
                System.out.println(e.toString());
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = _Web.XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.alert.onToast(answer.getMessage());
                            if(answer.isSuccess()) {
                                activity.updatePresenter();
                                activity.onBackPressed();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onItemClick(ModelTeachersList m) {
        ModelSchoolTeacher teacher = new ModelSchoolTeacher();
        teacher.setId(m.getId());
        activity.setTeacherData(teacher);
    }
}