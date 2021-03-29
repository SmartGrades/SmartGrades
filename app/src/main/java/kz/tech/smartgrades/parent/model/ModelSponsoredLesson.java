package kz.tech.smartgrades.parent.model;

import java.util.ArrayList;

public class ModelSponsoredLesson {
    private String LessonId;
    private String LessonName;
    private ArrayList<ModelGrades> Grades;

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

    public ArrayList<ModelGrades> getGrades() {
        return Grades;
    }

    public void setGrades(ArrayList<ModelGrades> grades) {
        Grades = grades;
    }
}
