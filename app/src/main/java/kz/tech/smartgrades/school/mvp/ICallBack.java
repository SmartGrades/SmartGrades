package kz.tech.smartgrades.school.mvp;

import java.util.ArrayList;

import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;

public interface ICallBack {
    void onResultLoadSchoolData(ModelSchoolData modelSchoolData);
    void onResultAddStudent(ModelAnswer answer);
    void onResultAddTeacher(ModelAnswer answer);
    void onResultAddLesson(ModelAnswer answer);
    void onResultGetStudentsList(ArrayList<ModelSchoolStudent> childrenLists);
    void onResultGetTeachersList(ArrayList<ModelSchoolTeacher> teachersLists);
    void onResultGetLessonsList(ArrayList<ModelSchoolLesson> lessonsLists);
}
