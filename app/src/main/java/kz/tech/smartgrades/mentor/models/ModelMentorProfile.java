package kz.tech.smartgrades.mentor.models;

import java.util.ArrayList;

import kz.tech.smartgrades.root.models.ModelLesson;

public class ModelMentorProfile{

    private String Id;
    private String Login;
    private String FirstName;
    private String LastName;
    private String Avatar;
    private String AvatarOriginal;
    private String AboutMe;
    private String Phone;
    private String Mail;
    private String Address;

    private ArrayList<ModelLesson> LessonsList;
    private ArrayList<ModelSchoolList> SchoolsList;

    public String getId(){
        return Id;
    }
    public void setId(String id){
        Id = id;
    }
    public String getLogin(){
        return Login;
    }
    public void setLogin(String login){
        Login = login;
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
    public String getAvatar(){
        return Avatar;
    }
    public void setAvatar(String avatar){
        Avatar = avatar;
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
    public String getPhone(){
        return Phone;
    }
    public void setPhone(String phone){
        Phone = phone;
    }
    public String getMail(){
        return Mail;
    }
    public void setMail(String mail){
        Mail = mail;
    }
    public String getAddress(){
        return Address;
    }
    public void setAddress(String address){
        Address = address;
    }
    public ArrayList<ModelLesson> getLessonsList(){
        return LessonsList;
    }
    public void setLessonsList(ArrayList<ModelLesson> lessonsList){
        LessonsList = lessonsList;
    }
    public ArrayList<ModelSchoolList> getSchoolsList(){
        return SchoolsList;
    }
    public void setSchoolsList(ArrayList<ModelSchoolList> schoolsList){
        SchoolsList = schoolsList;
    }
}