package kz.tech.smartgrades.mentor.models;

public class ModelMentorLesson{

    private String Id;
    private String SchoolId;
    private String MentorId;
    private String ClassId;
    private String LessonId;
    private String LessonName;

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
    public String getSchoolId(){
        return SchoolId;
    }
    public void setSchoolId(String schoolId){
        SchoolId = schoolId;
    }
    public String getClassId(){
        return ClassId;
    }
    public void setClassId(String classId){
        ClassId = classId;
    }
    public String getId(){
        return Id;
    }
    public void setId(String id){
        Id = id;
    }
    public String getMentorId(){
        return MentorId;
    }
    public void setMentorId(String mentorId){
        MentorId = mentorId;
    }
}