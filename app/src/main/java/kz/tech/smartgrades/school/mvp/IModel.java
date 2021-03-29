package kz.tech.smartgrades.school.mvp;

import kz.tech.smartgrades.school.models.ModelSchoolCreateContact;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;
public interface IModel {

    void onAddStudent(ICallBack csallBack, ModelSchoolCreateContact model);
    void onGetStudentsList(ICallBack callBack);

    void onAddTeacher(ICallBack callBack, ModelSchoolTeacher model);
    void onGetTeachersList(ICallBack callBack);

    void onAddLesson(ICallBack callBack, ModelSchoolLesson model);
    void onGetLessonsList(ICallBack callBack);
}
