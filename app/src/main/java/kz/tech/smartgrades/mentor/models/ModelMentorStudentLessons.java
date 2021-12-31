package kz.tech.smartgrades.mentor.models;

public class ModelMentorStudentLessons{

    private String SchoolLessonId;
    private String ChildLessonId;
    private String LessonId;
    private String LessonName;

    public String getSchoolLessonId(){
        return SchoolLessonId;
    }
    public void setSchoolLessonId(String schoolLessonId){
        SchoolLessonId = schoolLessonId;
    }
    public String getChildLessonId(){
        return ChildLessonId;
    }
    public void setChildLessonId(String childLessonId){
        ChildLessonId = childLessonId;
    }
    public String getLessonId(){
        return LessonId;
    }
    public void setLessonId(String lessonId){
        LessonId = lessonId;
    }
    public String getLessonName(){
        return LessonName;
    }
    public void setLessonName(String lessonName){
        LessonName = lessonName;
    }
}