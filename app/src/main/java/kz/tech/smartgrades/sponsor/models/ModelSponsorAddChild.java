package kz.tech.smartgrades.sponsor.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelSponsorAddChild implements Parcelable {
    private String Avatar;
    private String Birthday;
    private String FirstName;
    private String LastName;
    private String UserId;
    private String Login;
    private String PhoneOrMail;
    private String Type;
    private String AboutMe;
    private String Description;

    private String ParentId;
    private String ParentFirstName;
    private String ParentLastName;
    private String ParentAvatar;

    public ModelSponsorAddChild() { }

    public ModelSponsorAddChild(String Avatar, String Birthday, String FirstName, String UserId, String Login, String PhoneOrMail, String Type) {
        this.Avatar = Avatar;
        this.Birthday = Birthday;
        this.FirstName = FirstName;
        this.UserId = UserId;
        this.Login = Login;
        this.PhoneOrMail = PhoneOrMail;
        this.Type = Type;
    }


    protected ModelSponsorAddChild(Parcel in) {
        Avatar = in.readString();
        Birthday = in.readString();
        FirstName = in.readString();
        UserId = in.readString();
        Login = in.readString();
        PhoneOrMail = in.readString();
        ParentId = in.readString();
        ParentFirstName = in.readString();
        ParentLastName = in.readString();
        ParentAvatar = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Avatar);
        dest.writeString(Birthday);
        dest.writeString(FirstName);
        dest.writeString(UserId);
        dest.writeString(Login);
        dest.writeString(PhoneOrMail);
        dest.writeString(ParentId);
        dest.writeString(ParentFirstName);
        dest.writeString(ParentLastName);
        dest.writeString(ParentAvatar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelSponsorAddChild> CREATOR = new Creator<ModelSponsorAddChild>() {
        @Override
        public ModelSponsorAddChild createFromParcel(Parcel in) {
            return new ModelSponsorAddChild(in);
        }

        @Override
        public ModelSponsorAddChild[] newArray(int size) {
            return new ModelSponsorAddChild[size];
        }
    };

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }
    public String getAvatar() {
        return Avatar;
    }

    public void setBirthday(String DateBirthday) {
        this.Birthday = DateBirthday;
    }
    public String getBirthday() {
        return Birthday;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }
    public String getFirstName() {
        return FirstName;
    }

    public void setUserId(String Id) {
        this.UserId = Id;
    }
    public String getUserId() {
        return UserId;
    }

    public void setLogin(String Login) {
        this.Login = Login;
    }
    public String getLogin() {
        return Login;
    }

    public void setPhoneOrMail(String PhoneOrMail) {
        this.PhoneOrMail = PhoneOrMail;
    }
    public String getPhoneOrMail() {
        return PhoneOrMail;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAboutMe() {
        return AboutMe;
    }

    public void setAboutMe(String aboutMe) {
        AboutMe = aboutMe;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getParentFirstName() {
        return ParentFirstName;
    }

    public void setParentFirstName(String parentFirstName) {
        ParentFirstName = parentFirstName;
    }

    public String getParentLastName() {
        return ParentLastName;
    }

    public void setParentLastName(String parentLastName) {
        ParentLastName = parentLastName;
    }

    public String getParentAvatar() {
        return ParentAvatar;
    }

    public void setParentAvatar(String parentAvatar) {
        ParentAvatar = parentAvatar;
    }
}