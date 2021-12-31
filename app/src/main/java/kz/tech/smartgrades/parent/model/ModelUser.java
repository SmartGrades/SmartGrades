package kz.tech.smartgrades.parent.model;

public class ModelUser {
    private String UserId;
    private String Avatar;
    private String FirstName;
    private String LastName;
    private String Login;
    private String AboutMe;
    private String AvatarOriginal;
    private boolean IsInterFormActive;

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

    public boolean isInterFormActive() {
        return IsInterFormActive;
    }

    public void setInterFormActive(boolean interFormActive) {
        IsInterFormActive = interFormActive;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getAboutMe() {
        return AboutMe;
    }

    public void setAboutMe(String aboutMe) {
        AboutMe = aboutMe;
    }

    public String getAvatarOriginal() {
        return AvatarOriginal;
    }

    public void setAvatarOriginal(String avatarOriginal) {
        AvatarOriginal = avatarOriginal;
    }
}
