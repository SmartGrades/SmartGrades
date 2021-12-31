package kz.tech.smartgrades.auth.models;

public class ModelRegisterData {

    private String Type;
    private String Login;
    private String PhoneOrMail;
    private String Birthday;
    private String FirstName;
    private String CityId;
    private String Password;

    public String getLogin() {
        return Login;
    }
    public void setLogin(String login) {
        Login = login;
    }
    public String getPhoneOrMail() {
        return PhoneOrMail;
    }
    public void setPhoneOrMail(String phoneOrMail) {
        PhoneOrMail = phoneOrMail;
    }
    public String getBirthday() {
        return Birthday;
    }
    public void setBirthday(String birthday) {
        Birthday = birthday;
    }
    public String getSchoolName() {
        return FirstName;
    }
    public void setSchoolName(String schoolName) {
        FirstName = schoolName;
    }
    public String getCityId() {
        return CityId;
    }
    public void setCityId(String cityId) {
        CityId = cityId;
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
}