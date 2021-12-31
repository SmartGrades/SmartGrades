package kz.tech.smartgrades.child.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelChildWardStepsDetailList {
    private String Avatar;
    private String FullName;
    private String Id;

    public ModelChildWardStepsDetailList() { }

    public ModelChildWardStepsDetailList(String Avatar, String DateBirthday, String FullName, String Id, String Login, String PhoneOrMail, String accessCode, String password) {
        this.Avatar = Avatar;
        this.FullName = FullName;
        this.Id = Id;
    }


    protected ModelChildWardStepsDetailList(Parcel in) {
        Avatar = in.readString();
        FullName = in.readString();
        Id = in.readString();
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }
    public String getAvatar() {
        return Avatar;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }
    public String getFullName() {
        return FullName;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
    public String getId() {
        return Id;
    }

}