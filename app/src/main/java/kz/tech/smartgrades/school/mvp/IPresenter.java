package kz.tech.smartgrades.school.mvp;

import kz.tech.smartgrades.school.models.ModelSchoolCreateContact;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;
public interface IPresenter {

    void onStartPresenter();
    void onDestroyView();

    void onAddStudent(ModelSchoolCreateContact model);
    void onAddTeacher(ModelSchoolTeacher model);
    void onAddLesson(ModelSchoolLesson model);
}
