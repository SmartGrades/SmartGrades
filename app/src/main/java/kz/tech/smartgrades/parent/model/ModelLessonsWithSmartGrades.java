package kz.tech.smartgrades.parent.model;

import java.util.ArrayList;

public class ModelLessonsWithSmartGrades {

    private String ChildLessonId;
    private String LessonId;
    private String LessonName;

    private ArrayList<ModelMentorList> Mentors;
    private boolean IsSchoolLesson;

    private String LastMessage;
    private String LastMessageDate;
    private boolean LastMessageCheck;

    private String Reward;
    private String AverageGrade;
    private ModelGradesSettings GradesSettings;
    private String SchoolId;

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
    public ArrayList<ModelMentorList> getMentors(){
        return Mentors;
    }
    public void setMentors(ArrayList<ModelMentorList> mentors){
        Mentors = mentors;
    }
    public boolean isSchoolLesson(){
        return IsSchoolLesson;
    }
    public void setSchoolLesson(boolean schoolLesson){
        IsSchoolLesson = schoolLesson;
    }
    public String getLastMessage(){
        return LastMessage;
    }
    public void setLastMessage(String lastMessage){
        LastMessage = lastMessage;
    }
    public String getLastMessageDate(){
        return LastMessageDate;
    }
    public void setLastMessageDate(String lastMessageDate){
        LastMessageDate = lastMessageDate;
    }
    public boolean isLastMessageCheck(){
        return LastMessageCheck;
    }
    public void setLastMessageCheck(boolean lastMessageCheck){
        LastMessageCheck = lastMessageCheck;
    }
    public String getReward(){
        return Reward;
    }
    public void setReward(String reward){
        Reward = reward;
    }
    public String getAverageGrade(){
        return AverageGrade;
    }
    public void setAverageGrade(String averageGrade){
        AverageGrade = averageGrade;
    }

    public ModelGradesSettings getGradesSettings() {
        return GradesSettings;
    }

    public void setGradesSettings(ModelGradesSettings gradesSettings) {
        this.GradesSettings = gradesSettings;
    }

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String schoolId) {
        SchoolId = schoolId;
    }
}
