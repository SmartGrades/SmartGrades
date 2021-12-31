package kz.tech.smartgrades.mentor.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelUserList implements Parcelable {
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

    public ModelUserList() { }

    public ModelUserList(String Avatar, String Birthday, String FirstName, String UserId, String Login, String PhoneOrMail, String Type) {
        this.Avatar = Avatar;
        this.Birthday = Birthday;
        this.FirstName = FirstName;
        this.UserId = UserId;
        this.Login = Login;
        this.PhoneOrMail = PhoneOrMail;
        this.Type = Type;
    }


    protected ModelUserList(Parcel in) {
        Avatar = in.readString();
        Birthday = in.readString();
        FirstName = in.readString();
        UserId = in.readString();
        Login = in.readString();
        PhoneOrMail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Avatar);
        dest.writeString(Birthday);
        dest.writeString(FirstName);
        dest.writeString(UserId);
        dest.writeString(Login);
        dest.writeString(PhoneOrMail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelUserList> CREATOR = new Creator<ModelUserList>() {
        @Override
        public ModelUserList createFromParcel(Parcel in) {
            return new ModelUserList(in);
        }

        @Override
        public ModelUserList[] newArray(int size) {
            return new ModelUserList[size];
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
}