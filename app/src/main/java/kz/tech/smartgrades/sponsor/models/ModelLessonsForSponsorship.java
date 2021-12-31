package kz.tech.smartgrades.sponsor.models;

public class ModelLessonsForSponsorship{

    private String ChildLessonId;
    private String LessonName;
    private double AverageGrade ;

    public String getChildLessonId(){
        return ChildLessonId;
    }
    public void setChildLessonId(String childLessonId){
        ChildLessonId = childLessonId;
    }
    public String getLessonName(){
        return LessonName;
    }
    public void setLessonName(String lessonName){
        LessonName = lessonName;
    }
    public double getAverageGrade(){
        return AverageGrade;
    }
    public void setAverageGrade(double averageGrade){
        AverageGrade = averageGrade;
    }
}
