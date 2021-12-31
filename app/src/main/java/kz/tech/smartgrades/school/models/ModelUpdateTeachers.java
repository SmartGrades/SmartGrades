package kz.tech.smartgrades.school.models;

import java.util.ArrayList;

public class ModelUpdateTeachers {
    private ArrayList<ModelTeachersList> TeachersList;
    private String SchoolId;
    private String ClassId;

    public ArrayList<ModelTeachersList> getTeachersList() {
        return TeachersList;
    }

    public void setTeachersList(ArrayList<ModelTeachersList> teachersList) {
        TeachersList = teachersList;
    }

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String schoolId) {
        SchoolId = schoolId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }
}
