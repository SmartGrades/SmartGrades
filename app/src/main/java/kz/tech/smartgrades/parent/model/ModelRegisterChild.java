package kz.tech.smartgrades.parent.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ModelRegisterChild {

    private String ParentId;

    private String Login;
    private String PhoneOrMail;
    private String Phone;
    private String Mail;
    private String FirstName;
    private String LastName;
    private String Avatar;
    private String AvatarOriginal;
    private String Birthday;
    private String Address;
    private String Code;
    private String Password;
    private String Type;

    public String getParentId() {
        return ParentId;
    }
    public void setParentId(String parentId) {
        ParentId = parentId;
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
    public String getBirthday() {
        return Birthday;
    }
    public void setBirthday(String birthday) {
        Birthday = birthday;
    }
    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        Address = address;
    }
    public String getCode() {
        return Code;
    }
    public void setCode(String code) {
        Code = code;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }
    public String getType() {
        return Type;
    }
    public void setType(String type) {
        Type = type;
    }
    public String getPhoneOrMail() {
        return PhoneOrMail;
    }
    public void setPhoneOrMail(String phoneOrMail) {
        PhoneOrMail = phoneOrMail;
    }
}
