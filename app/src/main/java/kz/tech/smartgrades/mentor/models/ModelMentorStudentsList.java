package kz.tech.smartgrades.mentor.models;

import java.util.ArrayList;
public class ModelMentorStudentsList{

    private String Id;
    private String UserId;
    private String Login;
    private String Phone;
    private String Mail;
    private String FirstName;
    private String LastName;
    private String Avatar;
    private String AvatarOriginal;
    private String AboutMe;
    private String Address;

    private String ClassId;
    private String ClassName;

    private ArrayList<ModelMentorStudentLessons> Lessons;

    public String getId(){
        return Id;
    }
    public void setId(String id){
        Id = id;
    }
    public String getUserId(){
        return UserId;
    }
    public void setUserId(String userId){
        UserId = userId;
    }
    public String getLogin(){
        return Login;
    }
    public void setLogin(String login){
        Login = login;
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
    public String getAddress(){
        return Address;
    }
    public void setAddress(String address){
        Address = address;
    }
    public String getClassId(){
        return ClassId;
    }
    public void setClassId(String classId){
        ClassId = classId;
    }
    public String getClassName(){
        return ClassName;
    }
    public void setClassName(String className){
        ClassName = className;
    }
    public ArrayList<ModelMentorStudentLessons> getLessons(){
        return Lessons;
    }
    public void setLessons(ArrayList<ModelMentorStudentLessons> lessons){
        Lessons = lessons;
    }
}
