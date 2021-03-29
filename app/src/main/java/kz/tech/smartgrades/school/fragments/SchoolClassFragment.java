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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.dialogs.ADAddLesson;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelLesson;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolLessonsListAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolStudentsListAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolTeachersListAdapter;
import kz.tech.smartgrades.school.dialogs.ADSchoolAddContact;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolCreateContact;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class SchoolClassFragment extends Fragment implements View.OnClickListener, SchoolStudentsListAdapter.OnItemClickListener, SchoolTeachersListAdapter.OnItemClickListener, SchoolLessonsListAdapter.OnItemClickListener{

    private SchoolActivity activity;
    private ImageView ivBack;
    private ModelSchoolClass mSchoolClass;

    private TextView tvTitleBar, tvAddChild, tvAddTeacher, tvAddItem, tvStudentsCount, tvTeachersCount, tvLessonsCount;
    private RecyclerView rvStudents, rvTeachers, rvLessons;
    private SchoolStudentsListAdapter StudentsAdapter;
    private SchoolTeachersListAdapter TeachersAdapter;
    private SchoolLessonsListAdapter LessonsAdapter;

    private String SCHOOL_ID;

    public static SchoolClassFragment newInstance(){
        return new SchoolClassFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (SchoolActivity) getActivity();
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
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);

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
        ADSchoolAddContact adSchoolAddContact = new ADSchoolAddContact(activity, "Добавить ученика");
        adSchoolAddContact.setOnItemClickListener(new ADSchoolAddContact.OnItemClickListener(){
            @Override
            public void onAddClick(ModelSchoolCreateContact mContact){
                mContact.setSchoolId(SCHOOL_ID);
                mContact.setClassId(mSchoolClass.getId());
                activity.presenter.onAddStudent(mContact);
            }
            @Override
            public void ToMakeAPhotoClick(int requestLoadPhoto){ }
        });
    }
    private void onAddTeacher(){
        SchoolAddContactFragment fragment = new SchoolAddContactFragment();
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, fragment).commit();
    }
    private void onAddLesson(){
        ADAddLesson adAddLesson = new ADAddLesson(activity);
        adAddLesson.setOnItemClickListener(new ADAddLesson.OnItemClickListener(){
            @Override
            public void onOkClick(ModelLesson mLesson){
                ModelSchoolLesson model = new ModelSchoolLesson();
                model.setLessonName(mLesson.getLessonName());
                model.setLessonId(mLesson.getLessonId());
                model.setClassId(mSchoolClass.getId());
                model.setSchoolId(SCHOOL_ID);
                activity.presenter.onAddLesson(model);
            }
        });
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
        this.mSchoolClass = mSchoolClass;
        tvTitleBar.setText(this.mSchoolClass.getName());

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
    public void onUpdateSchoolData(ModelSchoolData m){
        if(mSchoolClass == null) return;
        mSchoolClass.setStudents(new ArrayList<>());
        if(!listIsNullOrEmpty(m.getStudents())){
            for(ModelSchoolStudent _student : m.getStudents())
                if(!stringIsNullOrEmpty(_student.getClassId()))
                    if(_student.getClassId().equals(mSchoolClass.getId()))
                        mSchoolClass.getStudents().add(_student);
            tvStudentsCount.setText(String.valueOf(mSchoolClass.getStudents().size()));
            if(StudentsAdapter != null) StudentsAdapter.updateData(mSchoolClass.getStudents());
        }
        if(!listIsNullOrEmpty(m.getTeachers())){
            mSchoolClass.setTeachers(new ArrayList<>());
            for(ModelSchoolTeacher _teacher : m.getTeachers())
                if(!stringIsNullOrEmpty(_teacher.getClassId()))
                    if(_teacher.getClassId().equals(mSchoolClass.getId()))
                        mSchoolClass.getTeachers().add(_teacher);
            tvTeachersCount.setText(String.valueOf(mSchoolClass.getTeachers().size()));
            if(TeachersAdapter != null) TeachersAdapter.updateData(mSchoolClass.getTeachers());
        }
        if(!listIsNullOrEmpty(m.getLessons())){
            mSchoolClass.setLessons(new ArrayList<>());
            for(ModelSchoolLesson _lesson : m.getLessons())
                if(!stringIsNullOrEmpty(_lesson.getClassId()))
                    if(_lesson.getClassId().equals(mSchoolClass.getId()))
                        mSchoolClass.getLessons().add(_lesson);
            tvLessonsCount.setText(String.valueOf(mSchoolClass.getLessons().size()));
            if(LessonsAdapter != null) LessonsAdapter.updateData(mSchoolClass.getLessons());
        }
    }
}
