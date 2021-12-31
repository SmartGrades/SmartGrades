package kz.tech.smartgrades.parent.model;

import java.util.ArrayList;

public class ModelUserProfile {
    private String Id;
    private String Avatar;
    private String AvatarOriginal;
    private String FirstName;
    private String LastName;
    private String Login;
    private String Address;
    private String Phone;
    private String Mail;
    private String AboutMe;

    private ArrayList<ModelChildProfileSchools> Schools;

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

    public ArrayList<ModelChildProfileSchools> getSchools() {
        return Schools;
    }

    public void setSchools(ArrayList<ModelChildProfileSchools> schools) {
        Schools = schools;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
