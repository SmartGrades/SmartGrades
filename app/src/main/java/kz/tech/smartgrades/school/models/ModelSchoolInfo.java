package kz.tech.smartgrades.school.models;

import java.util.ArrayList;

import kz.tech.smartgrades.auth.models.ModelUser;

public class ModelSchoolInfo {
    private String ParentId;
    private String SchoolId;

    private String Login;
    private String SchoolName;
    private String Address;

    private ArrayList<ModelSchoolPhones> Phones;
    private ArrayList<ModelSchoolMail> Mails;

    private ArrayList<ModelUser> Children;

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String schoolId) {
        SchoolId = schoolId;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String schoolName) {
        SchoolName = schoolName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public ArrayList<ModelSchoolPhones> getPhones() {
        return Phones;
    }

    public void setPhones(ArrayList<ModelSchoolPhones> phones) {
        Phones = phones;
    }

    public ArrayList<ModelSchoolMail> getMails() {
        return Mails;
    }

    public void setMails(ArrayList<ModelSchoolMail> mails) {
        Mails = mails;
    }

    public ArrayList<ModelUser> getChildren() {
        return Children;
    }

    public void setChildren(ArrayList<ModelUser> children) {
        Children = children;
    }
}
