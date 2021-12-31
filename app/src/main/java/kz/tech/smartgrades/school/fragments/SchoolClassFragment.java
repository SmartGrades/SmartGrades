package kz.tech.smartgrades.school.fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.root.dialogs.ADAddLesson;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelLesson;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolLessonsListAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolStudentsListAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolTeachersListAdapter;
import kz.tech.smartgrades.school.dialogs.ADSchoolAddContact;
import kz.tech.smartgrades.school.dialogs.ADSchoolMoveStudentWarning;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolClassesColumn;
import kz.tech.smartgrades.school.models.ModelSchoolCreateContact;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades._Web.SoapRequest;

public class SchoolClassFragment extends Fragment implements View.OnClickListener,
        View.OnLongClickListener{

    private SchoolActivity activity;
    private ImageView ivBack;
    private ModelSchoolClass mSchoolClass;

    private TextView tvTitleBar, tvAddChild, tvAddTeacher, tvAddItem, tvStudentsCount, tvTeachersCount, tvLessonsCount;
    private RecyclerView rvStudents, rvTeachers, rvLessons;
    private SchoolStudentsListAdapter StudentsAdapter;
    private SchoolTeachersListAdapter TeachersAdapter;
    private SchoolLessonsListAdapter LessonsAdapter;
    private ADSchoolAddContact adSchoolAddContact;

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
        tvAddItem.setOnLongClickListener(this);

        tvStudentsCount = view.findViewById(R.id.tvStudentsCount);
        rvStudents = view.findViewById(R.id.rvStudents);
        StudentsAdapter = new SchoolStudentsListAdapter(activity);
        rvStudents.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvStudents.setAdapter(StudentsAdapter);

        tvTeachersCount = view.findViewById(R.id.tvTeachersCount);
        rvTeachers = view.findViewById(R.id.rvTeachers);
        TeachersAdapter = new SchoolTeachersListAdapter(activity);
        rvTeachers.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvTeachers.setAdapter(TeachersAdapter);

        tvLessonsCount = view.findViewById(R.id.tvLessonsCount);
        rvLessons = view.findViewById(R.id.rvLessons);
        LessonsAdapter = new SchoolLessonsListAdapter(activity);
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
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        View popUpView = getLayoutInflater().inflate(R.layout.pw_school_add_student_to_class, null);
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) popupWindow.setElevation(20);
        popupWindow.showAsDropDown(tvAddChild, 0, (int)Convert.DpToPx(-90));

        TextView tvSelectFromList = popUpView.findViewById(R.id.tvSelectFromList);
        TextView tvAddNew = popUpView.findViewById(R.id.tvAddNew);
        tvSelectFromList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                ADSchoolMoveStudentWarning adSchoolAddContact = new ADSchoolMoveStudentWarning(activity, "Добавить ученика");
                adSchoolAddContact.setOnItemClickListener(new ADSchoolMoveStudentWarning.OnItemClickListener(){
                    @Override
                    public void onOkClick(){
                        activity.onMoveStudentClick(mSchoolClass);
                    }
                });
            }
        });
        tvAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                adSchoolAddContact = new ADSchoolAddContact(activity, "Добавить ученика");
                adSchoolAddContact.setOnItemClickListener(new ADSchoolAddContact.OnItemClickListener(){
                    @Override
                    public void onAddClick(ModelSchoolCreateContact mContact){
                        mContact.setSchoolId(SCHOOL_ID);
                        if(mContact.getClasses() == null) mContact.setClasses(new ArrayList<>());
                        mContact.getClasses().add(mSchoolClass);
                        activity.presenter.onAddStudent(mContact);
                    }
                });
            }
        });
    }
    private void onAddTeacher(){
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        View popUpView = getLayoutInflater().inflate(R.layout.pw_school_add_student_to_class, null);
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) popupWindow.setElevation(20);
        popupWindow.showAsDropDown(tvAddTeacher, 0, (int)Convert.DpToPx(-90));

        TextView tvSelectFromList = popUpView.findViewById(R.id.tvSelectFromList);
        TextView tvAddNew = popUpView.findViewById(R.id.tvAddNew);
        tvSelectFromList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                activity.onMoveTeacherClick(mSchoolClass);
            }
        });
        tvAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                activity.onAddTeachersClick();
            }
        });
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
                model.setClassName(mSchoolClass.getName());
                model.setSchoolId(SCHOOL_ID);
                activity.presenter.onAddLesson(model);
            }
        });
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
        if(!listIsNullOrEmpty(m.getClassessColumns()))
            for(ModelSchoolClassesColumn _column : m.getClassessColumns())
                if(!listIsNullOrEmpty(_column.getClasses()))
                    for(ModelSchoolClass _class : _column.getClasses())
                        if(mSchoolClass.getId().equals(_class.getId())){
                            mSchoolClass = _class;
                            break;
                        }
        onSetData(mSchoolClass);
    }
    @Override
    public boolean onLongClick(View view){
        return false;
    }

    public void ToMakeAPhoto(int requestTakePhoto) {
        adSchoolAddContact.ToMakeAPhoto(requestTakePhoto);
    }

    public File getMPhotoFile() {
        if (adSchoolAddContact != null) {
            return adSchoolAddContact.mPhotoFile;
        } else return null;
    }

    public void startCrop(Uri imageUri) {
        if (adSchoolAddContact != null) {
            adSchoolAddContact.startCrop(imageUri);
        }
    }

    public void prepareAvatar(Uri resultUri) {
        if (adSchoolAddContact != null) {
            adSchoolAddContact.prepareAvatar(resultUri);
        }
    }
}
