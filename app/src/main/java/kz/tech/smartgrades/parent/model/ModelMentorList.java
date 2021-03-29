package kz.tech.smartgrades.parent.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelMentorList implements Parcelable {

    @Nullable
    @SerializedName("AboutMe")
    @Expose
    private String AboutMe;

    @Nullable
    @SerializedName("Avatar")
    @Expose
    private String Avatar;

    @Nullable
    @SerializedName("Description")
    @Expose
    private String Description;

    @Nullable
    @SerializedName("FullName")
    @Expose
    private String FullName;

    @Nullable
    @SerializedName("Id")
    @Expose
    private String Id;

    @Nullable
    @SerializedName("Login")
    @Expose
    private String Login;

    @Nullable
    @SerializedName("FirstName")
    @Expose
    private String FirstName;

    @Nullable
    @SerializedName("LastName")
    @Expose
    private String LastName;

    @Nullable
    @SerializedName("Answer")
    @Expose
    private String Answer;

    public ModelMentorList() {}

    public ModelMentorList(@Nullable String aboutMe, @Nullable String avatar,
                           @Nullable String description, @Nullable String fullName,
                           @Nullable String id, @Nullable String login) {
        this.AboutMe = aboutMe;
        this.Avatar = avatar;
        this.Description = description;
        this.FullName = fullName;
        this.Id = id;
        this.Login = login;
    }

    protected ModelMentorList(Parcel in) {
        AboutMe = in.readString();
        Avatar = in.readString();
        Description = in.readString();
        FullName = in.readString();
        Id = in.readString();
        Login = in.readString();
    }

    public static final Creator<ModelMentorList> CREATOR = new Creator<ModelMentorList>() {
        @Override
        public ModelMentorList createFromParcel(Parcel in) {
            return new ModelMentorList(in);
        }

        @Override
        public ModelMentorList[] newArray(int size) {
            return new ModelMentorList[size];
        }
    };

    @Nullable
    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(@Nullable String avatar) {
        this.Avatar = avatar;
    }

    @Nullable
    public String getDescription() {
        return Description;
    }

    public void setDescription(@Nullable String description) {
        this.Description = description;
    }

    @Nullable
    public String getFullName() {
        return FullName;
    }

    public void setFullName(@Nullable String fullName) {
        this.FullName = fullName;
    }

    @Nullable
    public String getAboutMe() {
        return AboutMe;
    }

    public void setAboutMe(@Nullable String aboutMe) {
        this.AboutMe = aboutMe;
    }

    @Nullable
    public String getId() {
        return Id;
    }

    public void setId(@Nullable String id) {
        this.Id = id;
    }

    @Nullable
    public String getLogin() {
        return Login;
    }

    public void setLogin(@Nullable String login) {
        this.Login = login;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(AboutMe);
        dest.writeString(Avatar);
        dest.writeString(Description);
        dest.writeString(FullName);
        dest.writeString(Id);
        dest.writeString(Login);
    }

    @Nullable
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(@Nullable String firstName) {
        FirstName = firstName;
    }

    @Nullable
    public String getLastName() {
        return LastName;
    }

    public void setLastName(@Nullable String lastName) {
        LastName = lastName;
    }

    @Nullable
    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(@Nullable String answer) {
        Answer = answer;
    }
}
