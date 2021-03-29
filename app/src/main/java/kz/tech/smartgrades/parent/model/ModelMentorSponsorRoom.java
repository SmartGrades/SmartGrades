package kz.tech.smartgrades.parent.model;

public class ModelMentorSponsorRoom {

    private String ChatId;

    private String UserId;
    private String Avatar;
    private String FirstName;
    private String LastName;
    private String PhoneOrMail;
    private String Login;
    private String AboutMe;
    private String Description;
    private String Type;

    private String SponsoredDate;
    private String SponsoredChild;

    public ModelMentorSponsorRoom() {}

    public ModelMentorSponsorRoom(String avatar, String login, String chatId, String FirstName, String UserId, String aboutMe, String description, String type) {
        this.UserId = UserId;
        this.ChatId = chatId;

        this.Avatar = avatar;
        this.FirstName = FirstName;
        this.Login = login;

        this.AboutMe = aboutMe;
        this.Description = description;

        this.Type = type;
    }

    public String getAvatar() {
        return Avatar;
    }
    public void setAvatar(String avatar) {
        this.Avatar = avatar;
    }

    public String getLogin() {
        return Login;
    }
    public void setLogin(String login) {
        this.Login = login;
    }

    public String getChatId() {
        return ChatId;
    }
    public void setChatId(String chatId) {
        this.ChatId = chatId;
    }

    public String getFirstName() {
        return FirstName;
    }
    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getUserId() {
        return UserId;
    }
    public void setUserId(String userId) {
        this.UserId = userId;
    }

    public String getAboutMe() {
        return AboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.AboutMe = aboutMe;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getSponsoredDate() {
        return SponsoredDate;
    }

    public void setSponsoredDate(String sponsoredDate) {
        SponsoredDate = sponsoredDate;
    }

    public String getSponsoredChild() {
        return SponsoredChild;
    }

    public void setSponsoredChild(String sponsoredChild) {
        SponsoredChild = sponsoredChild;
    }

    public String getPhoneOrMail() {
        return PhoneOrMail;
    }

    public void setPhoneOrMail(String phoneOrMail) {
        PhoneOrMail = phoneOrMail;
    }
}
