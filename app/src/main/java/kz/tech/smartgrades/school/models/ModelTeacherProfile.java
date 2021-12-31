package kz.tech.smartgrades.school.models;

import java.util.ArrayList;

public class ModelTeacherProfile {
    private String SchoolId;
    private String Id;
    private String UserId;
    private String Avatar;
    private String AvatarOriginal;
    private String FirstName;
    private String LastName;
    private String Login;
    private String Address;
    private String Phone;
    private String Mail;
    private String AboutMe;

    private ArrayList<ModelTeacherProfileLessons> Lessons;
    private ArrayList<ModelTeacherProfileClasses> Classes;
    private ArrayList<ModelTeacherProfileSchools> Schools;

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String schoolId) {
        SchoolId = schoolId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getAvatarOriginal() {
        return AvatarOriginal;
    }

    public void setAvatarOriginal(String avatarOriginal) {
        AvatarOriginal = avatarOriginal;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getAboutMe() {
        return AboutMe;
    }

    public void setAboutMe(String aboutMe) {
        AboutMe = aboutMe;
    }

    public ArrayList<ModelTeacherProfileLessons> getLessons() {
        return Lessons;
    }

    public void setLessons(ArrayList<ModelTeacherProfileLessons> lessons) {
        Lessons = lessons;
    }

    public ArrayList<ModelTeacherProfileClasses> getClasses() {
        return Classes;
    }

    public void setClasses(ArrayList<ModelTeacherProfileClasses> classes) {
        Classes = classes;
    }

    public ArrayList<ModelTeacherProfileSchools> getSchools() {
        return Schools;
    }

    public void setSchools(ArrayList<ModelTeacherProfileSchools> schools) {
        Schools = schools;
    }
}
