package kz.tech.smartgrades.school.models;

public class ModelMessageToGroup{

    private String MentorId;
    private String GroupId;
    private String Message;
    public String getMentorId(){
        return MentorId;
    }
    public void setMentorId(String mentorId){
        MentorId = mentorId;
    }
    public String getGroupId(){
        return GroupId;
    }
    public void setGroupId(String groupId){
        GroupId = groupId;
    }
    public String getMessage(){
        return Message;
    }
    public void setMessage(String message){
        Message = message;
    }
}
