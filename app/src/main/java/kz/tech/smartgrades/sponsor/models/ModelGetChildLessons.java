package kz.tech.smartgrades.sponsor.models;

import java.util.ArrayList;

import kz.tech.smartgrades.parent.model.ModelGrades;

public class ModelGetChildLessons {

    private String LessonId;
    private String LessonName;
    private String AverageGrade;

    private ArrayList<ModelGrades> Grades;

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

    public String getLessonId() {
        return LessonId;
    }

    public void setLessonId(String lessonId) {
        LessonId = lessonId;
    }

    public String getAverageGrade() {
        return AverageGrade;
    }

    public void setAverageGrade(String averageGrade) {
        AverageGrade = averageGrade;
    }
}
