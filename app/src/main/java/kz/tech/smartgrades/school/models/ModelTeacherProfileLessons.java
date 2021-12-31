package kz.tech.smartgrades.school.models;

public class ModelTeacherProfileLessons {
    private String TeacherLessonId;
    private String LessonId;
    private String LessonName;

    public String getTeacherLessonId() {
        return TeacherLessonId;
    }

    public void setTeacherLessonId(String teacherLessonId) {
        TeacherLessonId = teacherLessonId;
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
