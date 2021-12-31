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

import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.adaptes.SchoolLessonsListAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolStudentsListAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolTeachersListAdapter;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class MentorClassFragment extends Fragment implements View.OnClickListener, SchoolStudentsListAdapter.OnItemClickListener, SchoolTeachersListAdapter.OnItemClickListener, SchoolLessonsListAdapter.OnItemClickListener{

    private MentorActivity activity;
    private ImageView ivBack;
    private ModelSchoolClass mMentorClass;

    private TextView tvTitleBar, tvAddChild, tvAddTeacher, tvAddItem, tvStudentsCount, tvTeachersCount, tvLessonsCount;
    private RecyclerView rvStudents, rvTeachers, rvLessons;
    private SchoolStudentsListAdapter StudentsAdapter;
    private SchoolTeachersListAdapter TeachersAdapter;
    private SchoolLessonsListAdapter LessonsAdapter;

    private String MENTOR_ID;

    public static MentorClassFragment newInstance(){
        return new MentorClassFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (MentorActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fgmt_school_class, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view){
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        tvTitleBar = view.findViewById(R.id.tvTitleBar);
        tvAddChild = view.findViewById(R.id.tvAddChild);
        tvAddChild.setOnClickListener(this);
        tvAddTeacher = view.findViewById(R.id.tvAddTeacher);
        tvAddTeacher.setOnClickListener(this);
        tvAddItem = view.findViewById(R.id.tvAddItem);
        tvAddItem.setOnClickListener(this);

        tvStudentsCount = view.findViewById(R.id.tvStudentsCount);
        rvStudents = view.findViewById(R.id.rvStudents);
        StudentsAdapter = new SchoolStudentsListAdapter(activity);
        StudentsAdapter.setOnItemClickListener(this);
        rvStudents.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvStudents.setAdapter(StudentsAdapter);

        tvTeachersCount = view.findViewById(R.id.tvTeachersCount);
        rvTeachers = view.findViewById(R.id.rvTeachers);
        TeachersAdapter = new SchoolTeachersListAdapter(activity);
        TeachersAdapter.setOnItemClickListener(this);
        rvTeachers.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvTeachers.setAdapter(TeachersAdapter);

        tvLessonsCount = view.findViewById(R.id.tvLessonsCount);
        rvLessons = view.findViewById(R.id.rvLessons);
        LessonsAdapter = new SchoolLessonsListAdapter(activity);
        LessonsAdapter.setOnItemClickListener(this);
        rvLessons.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvLessons.setAdapter(LessonsAdapter);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
                break;
            case R.id.tvAddChild:
                onAddStudent();
                break;
            case R.id.tvAddTeacher:
                onAddTeacher();
                break;
            case R.id.tvAddItem:
                onAddLesson();
                break;
        }
    }
    private void onBack(){
        activity.onBackPressed();
    }
    private void onAddStudent(){
        /*ADSchoolAddContact adSchoolAddContact = new ADSchoolAddContact(activity, "Добавить ученика");
        adSchoolAddContact.setOnItemClickListener(new ADSchoolAddContact.OnItemClickListener(){
            @Override
            public void onAddClick(String etLastName, String etFirstName, String etPatronymic, String civAvatar){
                ModelSchoolStudent model = new ModelSchoolStudent();
                model.setLastName(etLastName);
                model.setFirstName(etFirstName);
                model.setPatronymic(etPatronymic);
                model.setAvatar(civAvatar);
                model.setClassId(mMentorClass.getId());
                model.setSchoolId(SCHOOL_ID);
                activity.presenter.onAddStudent(model);
            }
        });*/
    }
    private void onAddTeacher(){
        /*ADSchoolAddContact adSchoolAddContact = new ADSchoolAddContact(activity, "Добавить учителя");
        adSchoolAddContact.setOnItemClickListener(new ADSchoolAddContact.OnItemClickListener(){
            @Override
            public void onAddClick(String etLastName, String etFirstName, String etPatronymic, String civAvatar){
                ModelSchoolTeacher model = new ModelSchoolTeacher();
                model.setLastName(etLastName);
                model.setFirstName(etFirstName);
                model.setPatronymic(etPatronymic);
                model.setAvatar(civAvatar);
                model.setClassId(mMentorClass.getId());
                model.setSchoolId(SCHOOL_ID);
                activity.presenter.onAddTeacher(model);
            }
        });*/
    }
    private void onAddLesson(){
        /*AlertDialogDefaultET dialogDefaultET = new AlertDialogDefaultET(activity, "Добавить предмет", "Название предмета", "Добавить");
        dialogDefaultET.setOnItemClickListener(new AlertDialogDefaultET.OnItemClickListener(){
            @Override
            public void onOkClick(String etValue){
                ModelSchoolLesson model = new ModelSchoolLesson();
                model.setLessonName(etValue);
                model.setClassId(mMentorClass.getId());
                model.setSchoolId(SCHOOL_ID);
                activity.presenter.onAddLesson(model);
            }
        });*/
    }

    @Override
    public void onItemClick(ModelSchoolStudent m){ }
    @Override
    public void onItemClick(ModelSchoolTeacher m){
    }
    @Override
    public void onItemClick(ModelSchoolLesson m){

    }

    public void onSetData(ModelSchoolClass mSchoolClass){
        this.mMentorClass = mSchoolClass;
        tvTitleBar.setText(this.mMentorClass.getName());

        StudentsAdapter.updateData(mSchoolClass.getStudents());
        if(!listIsNullOrEmpty(mSchoolClass.getStudents()))
            tvStudentsCount.setText(String.valueOf(mSchoolClass.getStudents().size()));

        TeachersAdapter.updateData(mSchoolClass.getTeachers());
        if(!listIsNullOrEmpty(mSchoolClass.getTeachers()))
            tvTeachersCount.setText(String.valueOf(mSchoolClass.getTeachers().size()));

        LessonsAdapter.updateData(mSchoolClass.getLessons());
        if(!listIsNullOrEmpty(mSchoolClass.getLessons()))
            tvLessonsCount.setText(String.valueOf(mSchoolClass.getLessons().size()));
    }
    public void onSetMentorData(ModelMentorData m){
        if(mMentorClass == null) return;
        mMentorClass.setStudents(new ArrayList<>());
        if(!listIsNullOrEmpty(m.getStudents())){
            //for(ModelSchoolStudent _student : m.getStudents())
//                if(_student.getClassId().equals(mMentorClass.getId()))
//                    mMentorClass.getStudents().add(_student);
            tvStudentsCount.setText(String.valueOf(mMentorClass.getStudents().size()));
            if(StudentsAdapter != null) StudentsAdapter.updateData(mMentorClass.getStudents());
        }
        if(!listIsNullOrEmpty(m.getLessons())){
            mMentorClass.setLessons(new ArrayList<>());
            for(ModelSchoolLesson _lesson : m.getLessons())
                if(_lesson.getClassId().equals(mMentorClass.getId()))
                    mMentorClass.getLessons().add(_lesson);
            tvLessonsCount.setText(String.valueOf(mMentorClass.getLessons().size()));
            if(LessonsAdapter != null) LessonsAdapter.updateData(mMentorClass.getLessons());
        }
    }
}
