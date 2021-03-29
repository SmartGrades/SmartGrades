package kz.tech.smartgrades.root.models;

public class ModelLesson{

    private String LessonId;
    private String LessonName;
    private String LessonType;
    private String LessonTypeName;

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
    public String getLessonType(){
        return LessonType;
    }
    public void setLessonType(String lessonType){
        LessonType = lessonType;
    }
    public String getLessonTypeName(){
        return LessonTypeName;
    }
    public void setLessonTypeName(String lessonTypeName){
        LessonTypeName = lessonTypeName;
    }
}
