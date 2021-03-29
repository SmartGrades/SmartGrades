package kz.tech.smartgrades.mentor.models;

public class ModelMentorWorksheet {

    private String aboutMe;
    private String avatar;
    private String description;
    private String fullName;
    private String login;

    public ModelMentorWorksheet() {}

    public ModelMentorWorksheet(String aboutMe, String avatar, String description, String fullName, String login) {
        this.aboutMe = aboutMe;
        this.avatar = avatar;
        this.description = description;
        this.fullName = fullName;
        this.login = login;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
