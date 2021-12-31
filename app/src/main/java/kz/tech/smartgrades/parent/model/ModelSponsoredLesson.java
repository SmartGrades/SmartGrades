package kz.tech.smartgrades.parent.model;

import java.util.ArrayList;

public class ModelSponsoredLesson {
    private String Id;

    private String SchoolId;
    private String ClassId;

    private String SchoolLessonId;
    private String LessonId;
    private String LessonName;
    private String LessonType;
    private ArrayList<ModelGrades> Grades;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getSchoolLessonId() {
        return SchoolLessonId;
    }

    public void setSchoolLessonId(String schoolLessonId) {
        SchoolLessonId = schoolLessonId;
    }

    public String getLessonId() {
        return LessonId;
    }

    public void setLessonId(String lessonId) {
        LessonId = lessonId;
    }

    public String getLessonName() {
        return LessonName;
    }

    public void setLessonName(String lessonName) {
        LessonName = lessonName;
    }

    public String getLessonType() {
        return LessonType;
    }

    public void setLessonType(String lessonType) {
        LessonType = lessonType;
    }

    public ArrayList<ModelGrades> getGrades() {
        return Grades;
    }

    public void setGrades(ArrayList<ModelGrades> grades) {
        Grades = grades;
    }
}
