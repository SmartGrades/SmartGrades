package kz.tech.smartgrades.sponsor.models;

import java.util.ArrayList;

import kz.tech.smartgrades.school.models.ModelSchoolData;
public class ModelChildrenListForSponsorship {

    private String UserId;
    private String Avatar;
    private String FirstName;
    private String LastName;
    private String Login;
    private String AvatarOriginal;
    private String AboutMe;
    private String StateName;

    private String ParentId;
    private String ParentAvatar;

    private ArrayList<ModelLessonsForSponsorship> Lessons;
    private ArrayList<ModelSchoolData> Schools;

    public String getUserId(){
        return UserId;
    }
    public void setUserId(String userId){
        UserId = userId;
    }
    public String getAvatar(){
        return Avatar;
    }
    public void setAvatar(String avatar){
        Avatar = avatar;
    }
    public String getFirstName(){
        return FirstName;
    }
    public void setFirstName(String firstName){
        FirstName = firstName;
    }
    public String getLastName(){
        return LastName;
    }
    public void setLastName(String lastName){
        LastName = lastName;
    }
    public String getLogin(){
        return Login;
    }
    public void setLogin(String login){
        Login = login;
    }
    public String getAvatarOriginal(){
        return AvatarOriginal;
    }
    public void setAvatarOriginal(String avatarOriginal){
        AvatarOriginal = avatarOriginal;
    }
    public String getAboutMe(){
        return AboutMe;
    }
    public void setAboutMe(String aboutMe){
        AboutMe = aboutMe;
    }
    public String getStateName(){
        return StateName;
    }
    public void setStateName(String stateName){
        StateName = stateName;
    }
    public ArrayList<ModelLessonsForSponsorship> getLessons(){
        return Lessons;
    }
    public void setLessons(ArrayList<ModelLessonsForSponsorship> lessons){
        Lessons = lessons;
    }
    public ArrayList<ModelSchoolData> getSchools(){
        return Schools;
    }
    public void setSchools(ArrayList<ModelSchoolData> schools){
        Schools = schools;
    }
    public String getParentId(){
        return ParentId;
    }
    public void setParentId(String parentId){
        ParentId = parentId;
    }
    public String getParentAvatar(){
        return ParentAvatar;
    }
    public void setParentAvatar(String parentAvatar){
        ParentAvatar = parentAvatar;
    }
}
