package kz.tech.smartgrades.parent.model;

import java.util.ArrayList;

import kz.tech.smartgrades.auth.models.ModelUser;

public class ModelLessonWithOutSettings {

    private String ChildLessonId;
    private String LessonId;
    private String LessonName;

    private ArrayList<ModelUser> Mentors;
    private boolean SchoolLesson;

    private String LastMessage;
    private String LastMessageDate;
    private boolean LastMessageCheck;

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
    public ArrayList<ModelUser> getMentors(){
        return Mentors;
    }
    public void setMentors(ArrayList<ModelUser> mentors){
        Mentors = mentors;
    }
    public boolean isSchoolLesson(){
        return SchoolLesson;
    }
    public void setSchoolLesson(boolean schoolLesson){
        SchoolLesson = schoolLesson;
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
}
