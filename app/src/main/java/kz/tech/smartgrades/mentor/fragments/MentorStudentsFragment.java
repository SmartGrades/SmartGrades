package kz.tech.smartgrades.mentor.fragments;

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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorStudentsListAdapterV2;
import kz.tech.smartgrades.mentor.dialog.BSDMentorSetGrade;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class MentorStudentsFragment extends Fragment implements View.OnClickListener, MentorStudentsListAdapterV2.OnItemClickListener{

    private MentorActivity activity;
    private String MENTOR_ID;

    private ImageView ivBack;
    private TextView tvTitleBar;
    private ImageView ivSearch;

    private RecyclerView rvStudents;
    private MentorStudentsListAdapterV2 studentsListAdapter;

    private CircleImageView civAddStudent;

    private ModelMentorData mMentorData;
    private ModelSchoolClass mClass;

    public static MentorStudentsFragment newInstance(ModelMentorData mMentorData){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", mMentorData);
        MentorStudentsFragment fragment = new MentorStudentsFragment();
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
        return inflater.inflate(R.layout.fgmt_mentor_students, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        if(mMentorData != null) onLoadWardOnAddition();
    }
    private void initViews(View view){
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        civAddStudent = view.findViewById(R.id.civAddStudent);
        civAddStudent.setOnClickListener(this);

        tvTitleBar = view.findViewById(R.id.tvTitleBar);
        tvTitleBar.setText("Ученики");
        ivSearch = view.findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(this);

        rvStudents = view.findViewById(R.id.rvStudents);
        studentsListAdapter = new MentorStudentsListAdapterV2(activity);
        studentsListAdapter.setOnItemClickListener(this);
        rvStudents.setLayoutManager(new LinearLayoutManager(activity));
        rvStudents.setAdapter(studentsListAdapter);
        if(!listIsNullOrEmpty(mMentorData.getStudents()))
            if (studentsListAdapter != null) studentsListAdapter.updateData(mMentorData.getStudents());
    }

    public void updateData(ModelMentorData mMentorData){
        this.mMentorData = mMentorData;
        if(studentsListAdapter == null) return;
        studentsListAdapter.updateData(mMentorData.getStudents());
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
        activity.onAddStudentClick();
    }

    @Override
    public void onItemLongClick(ModelSchoolStudent m){
        BSDMentorSetGrade bsdMentorSetGrade = new BSDMentorSetGrade(activity, m, mClass);
        bsdMentorSetGrade.setOnItemClickListener(new BSDMentorSetGrade.OnItemClickListener(){
            @Override
            public void onGradeClick(int type){
                bsdMentorSetGrade.dismiss();
                activity.alert.onToast("Grade is sent succesfully");
//                JsonObject jsonData = new JsonObject();
//                jsonData.addProperty(F.SourceId, MENTOR_ID);
//                jsonData.addProperty(F.ChildId, m.getUserId());
//                //jsonData.addProperty(F.LessonId, mClass.getLessonId());
//                jsonData.addProperty("SchoolId", mClass.getSchoolId());
//                jsonData.addProperty("Grade", type);
//
//                String SOAP = SoapRequest(func_MentorSetGrade, jsonData.toString());
//                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
//                Request request = new Request.Builder().url(URL).post(body).build();
//                HttpClient.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(final Call call, IOException e) { }
//                    @Override
//                    public void onResponse(Call call, final Response response) throws IOException {
//                        if (response.isSuccessful()) {
//                            String result = _Web.XMLReader(response.body().string());
//                            activity.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
//                                    activity.alert.onToast(answer.getMessage());
//                                }
//                            });
//
//                        }
//                    }
//                });
            }
        });
    }
    public void setData(ModelMentorData mMentorData){
        this.mMentorData = mMentorData;
        if(!listIsNullOrEmpty(mMentorData.getStudents()))
            if (studentsListAdapter != null)
                studentsListAdapter.updateData(mMentorData.getStudents());
    }
    public void setSelectClass(ModelSchoolClass m){
        mClass = m;
        if(mClass ==null) return;
        tvTitleBar.setText(mClass.getName());
        if(studentsListAdapter!=null){
            ArrayList<ModelSchoolStudent> students = new ArrayList<>();
            if(!listIsNullOrEmpty(mMentorData.getStudents()))
                for(ModelSchoolStudent _student : mMentorData.getStudents())
                    if(_student.getClassId().equals(m.getId()))
                        students.add(_student);
            studentsListAdapter.updateData(students);
        }
    }
    public void onShowDialogSelectLesson(){
        activity.alert.onToast("Открытие диалога");
    }
}