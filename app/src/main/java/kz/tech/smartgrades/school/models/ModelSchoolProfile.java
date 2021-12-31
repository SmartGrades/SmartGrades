package kz.tech.smartgrades.school.models;

import java.util.ArrayList;

import kz.tech.smartgrades.root.models.ModelLesson;

public class ModelSchoolProfile {
    private String Id;
    private String Avatar;
    private String AvatarOriginal;
    private String Name;
    private String Address;
    private String Phone;
    private String Mail;
    private String Site;
    private String AboutMe;
    private String StateName;
    private String State;
    private ArrayList<ModelSchoolClassV2> Classes;
    private ArrayList<ModelLesson> Lessons;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public String getAboutMe() {
        return AboutMe;
    }

    public void setAboutMe(String aboutMe) {
        AboutMe = aboutMe;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public ArrayList<ModelSchoolClassV2> getClasses() {
        return Classes;
    }

    public void setClasses(ArrayList<ModelSchoolClassV2> classes) {
        Classes = classes;
    }

    public ArrayList<ModelLesson> getLessons() {
        return Lessons;
    }

    public void setLessons(ArrayList<ModelLesson> lessons) {
        Lessons = lessons;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
