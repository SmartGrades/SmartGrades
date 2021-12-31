package kz.tech.smartgrades.school;

import java.util.ArrayList;

import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.school.models.ModelSchoolCreateContact;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;
import kz.tech.smartgrades.school.mvp.ICallBack;
import kz.tech.smartgrades.school.mvp.IPresenter;

public class SchoolPresenter implements IPresenter, ICallBack {

    private SchoolActivity activity;
    private SchoolModel schoolModel;
    private ICallBack callBack;

    public SchoolPresenter(SchoolActivity activity) {
        this.activity = activity;
        this.schoolModel = new SchoolModel(activity);
        this.callBack = this;
    }

    @Override
    public void onStartPresenter() {
        schoolModel.onLoadSchoolData(callBack);
    }
    @Override
    public void onDestroyView() {
        if (schoolModel != null) schoolModel = null;
        if (activity != null) activity = null;
    }

    @Override
    public void onAddStudent(ModelSchoolCreateContact model){
        schoolModel.onAddStudent(callBack, model);
    }
    @Override
    public void onAddTeacher(ModelSchoolTeacher model){
        schoolModel.onAddTeacher(callBack, model);
    }
    @Override
    public void onAddLesson(ModelSchoolLesson model){
        schoolModel.onAddLesson(callBack, model);
    }

    ////////////////////////////// CALLBACK //////////////////////////////
    @Override
    public void onResultLoadSchoolData(ModelSchoolData modelSchoolData) {
        if(modelSchoolData != null) activity.setSchoolData(modelSchoolData);
    }
    @Override
    public void onResultAddStudent(ModelAnswer answer){
        activity.runOnUiThread(new Runnable(){
            @Override
            public void run(){
                activity.alert.onToast(answer.getMessage());
                if(answer.isSuccess()) schoolModel.onGetStudentsList(callBack);
            }
        });
    }
    @Override
    public void onResultAddTeacher(ModelAnswer answer){
        activity.runOnUiThread(new Runnable(){
            @Override
            public void run(){
                activity.alert.onToast(answer.getMessage());
                if(answer.isSuccess()) schoolModel.onGetTeachersList(callBack);
            }
        });
    }
    @Override
    public void onResultAddLesson(ModelAnswer answer){
        activity.runOnUiThread(new Runnable(){
            @Override
            public void run(){
                activity.alert.onToast(answer.getMessage());
                if(answer.isSuccess()) schoolModel.onGetLessonsList(callBack);
            }
        });
    }
    @Override
    public void onResultGetStudentsList(ArrayList<ModelSchoolStudent> studentsLists){
        activity.runOnUiThread(new Runnable(){
            @Override
            public void run(){
                activity.onUpdateStudentsList(studentsLists);
            }
        });
    }
    @Override
    public void onResultGetTeachersList(ArrayList<ModelSchoolTeacher> teachersLists){
        activity.runOnUiThread(new Runnable(){
            @Override
            public void run(){
                activity.onUpdateTeachersList(teachersLists);
            }
        });
    }
    @Override
    public void onResultGetLessonsList(ArrayList<ModelSchoolLesson> lessonsLists){
        activity.runOnUiThread(new Runnable(){
            @Override
            public void run(){
                activity.onUpdateLessonsList(lessonsLists);
            }
        });
    }
}