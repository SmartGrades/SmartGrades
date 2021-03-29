package kz.tech.smartgrades.school.fragments;

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

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolStudentsListAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolStudentsListAdapterV2;
import kz.tech.smartgrades.school.dialogs.ADSchoolAddContact;
import kz.tech.smartgrades.school.models.ModelSchoolCreateContact;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_SchoolAddStudent;

public class SchoolStudentsFragment extends Fragment implements View.OnClickListener, SchoolStudentsListAdapter.OnItemClickListener, SchoolStudentsListAdapterV2.OnItemClickListener{

    private SchoolActivity activity;
    private String SCHOOL_ID;

    private ImageView ivBack;
    private TextView tvTitleBar;
    private ImageView ivSearch;

    private RecyclerView rvStudents;
    private SchoolStudentsListAdapterV2 studentsListAdapter;

    private CircleImageView civAddStudent;

    private ModelSchoolData mSchoolData;

    public static SchoolStudentsFragment newInstance(ModelSchoolData modelSchoolData){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", modelSchoolData);
        SchoolStudentsFragment fragment = new SchoolStudentsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (SchoolActivity) getActivity();
        if(getArguments() != null) mSchoolData = getArguments().getParcelable("model");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fgmt_school_students, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        if(mSchoolData != null) onLoadWardOnAddition();
    }
    private void initViews(View view){
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        civAddStudent = view.findViewById(R.id.civAddStudent);
        civAddStudent.setOnClickListener(this);

        tvTitleBar = view.findViewById(R.id.tvTitleBar);
        ivSearch = view.findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(this);

        rvStudents = view.findViewById(R.id.rvStudents);
        studentsListAdapter = new SchoolStudentsListAdapterV2(activity);
        studentsListAdapter.setOnItemClickListener(this);
        rvStudents.setLayoutManager(new LinearLayoutManager(activity));
        rvStudents.setAdapter(studentsListAdapter);
        studentsListAdapter.updateData(mSchoolData.getStudents());
    }

    public void updateData(ModelSchoolData mSchoolData){
        this.mSchoolData = mSchoolData;
        if(studentsListAdapter == null) return;
        studentsListAdapter.updateData(mSchoolData.getStudents());
    }
    private void onLoadWardOnAddition(){
        /*Picasso.get().load(modelSchoolData.getAvatar()).fit().centerInside().into(civAvatar);
        tvTitle.setText(modelSchoolData.getFullName());
        String chatId = modelSchoolData.getId();*/
    }

    private void onBack(){
        activity.onBackPressed();
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
                break;
            case R.id.civAddStudent:
                onAddStudent();
                break;
        }
    }
    private void onAddStudent(){
        ADSchoolAddContact adSchoolAddContact = new ADSchoolAddContact(activity, "Добавить ученика");
        adSchoolAddContact.setOnItemClickListener(new ADSchoolAddContact.OnItemClickListener(){
            @Override
            public void onAddClick(ModelSchoolCreateContact mContact){
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.SchoolId, SCHOOL_ID);
                /*jsonData.addProperty(F.FirstName, etFirstName);
                jsonData.addProperty(F.LastName, etLastName);*/

                String SOAP = SoapRequest(func_SchoolAddStudent, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = _Web.XMLReader(response.body().string());
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.alert.onToast(answer.getMessage());
                            if (answer.isSuccess())
                                activity.presenter.onStartPresenter();
                        }
                    }
                });
            }
            @Override
            public void ToMakeAPhotoClick(int requestLoadPhoto){

            }
        });
    }
    @Override
    public void onItemClick(ModelSchoolStudent m){

    }
    public void onUpdateData(ModelSchoolData mSchoolData){
        this.mSchoolData = mSchoolData;
        if(studentsListAdapter != null) studentsListAdapter.updateData(mSchoolData.getStudents());
    }
}
