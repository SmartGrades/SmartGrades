package kz.tech.smartgrades.parent.model;

import java.util.List;

import kz.tech.smartgrades.mentor.models.ModelMentorList;

public class ModelParentAddLessonForChild {
    private String SourceId;
    private String ChildId;
    private String LessonId;
    private String LessonName;
    private int LessonType;
    private String InterFormId;
    private String InterFormType;

    private List<ModelMentorList> Mentors;

    private ModelGradesSettings GradesSettings;

    public String getSourceId() {
        return SourceId;
    }

    public void setSourceId(String sourceId) {
        SourceId = sourceId;
    }

    public String getChildId() {
        return ChildId;
    }

    public void setChildId(String childId) {
        ChildId = childId;
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

    public int getLessonType() {
        return LessonType;
    }

    public void setLessonType(int lessonType) {
        LessonType = lessonType;
    }

    public List<ModelMentorList> getMentors() {
        return Mentors;
    }

    public void setMentors(List<ModelMentorList> mentors) {
        Mentors = mentors;
    }

    public ModelGradesSettings getGradesSettings() {
        return GradesSettings;
    }

    public void setGradesSettings(ModelGradesSettings gradesSettings) {
        GradesSettings = gradesSettings;
    }

    public String getInterFormId() {
        return InterFormId;
    }

    public void setInterFormId(String interFormId) {
        InterFormId = interFormId;
    }

    public String getInterFormType() {
        return InterFormType;
    }

    public void setInterFormType(String interFormType) {
        InterFormType = interFormType;
    }
}
