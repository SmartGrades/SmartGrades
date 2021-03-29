package kz.tech.smartgrades.school.models;


public class ModelSchoolLessons {
    private String LessonId;
    private String LessonName;

    public ModelSchoolLessons() { }

    public ModelSchoolLessons(String LessonId, String LessonName) {
        this.LessonId = LessonId;
        this.LessonName = LessonName;
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
}