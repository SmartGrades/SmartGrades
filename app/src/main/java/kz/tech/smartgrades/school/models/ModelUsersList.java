package kz.tech.smartgrades.school.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelUsersList implements Parcelable {
    private String Avatar;
    private String DateBirthday;
    private String FullName;
    private String Id;
    private String Login;
    private String PhoneOrMail;
    private String Type;

    private String ChatId;
    private String Date;
    private String ParentId;
    private String ToolInterval;
    private String ToolType;
    private String Watched;

    private String GroupId;
    private String SponsorId;

    private String lastGrade;
    private String centerGrade;
    private String minCenterGrade;

    public ModelUsersList() { }

    public ModelUsersList(String Avatar, String DateBirthday, String FullName, String Id, String Login, String PhoneOrMail,
                          String ChatId, String Date, String ParentId, String ToolInterval, String ToolType, String GroupId,
                          String lastGrade, String centerGrade, String sponsorId, String minCenterGrade, String Type) {
        this.Avatar = Avatar;
        this.DateBirthday = DateBirthday;
        this.FullName = FullName;
        this.Id = Id;
        this.Login = Login;
        this.PhoneOrMail = PhoneOrMail;

        this.ChatId = ChatId;
        this.Date = Date;
        this.ParentId = ParentId;
        this.ToolInterval = ToolInterval;
        this.ToolType = ToolType;

        this.GroupId = GroupId;

        this.lastGrade = lastGrade;
        this.centerGrade = centerGrade;
        this.minCenterGrade = minCenterGrade;

        this.SponsorId = sponsorId;
        this.Type = Type;
    }

    /*protected ModelMentorChat(Parcel in) {
        avatar = in.readString();
        chatId = in.readString();
        date = in.readString();
        fullName = in.readString();
        idChild = in.readString();
        //message = in.readString();
        //parentAvatar = in.readString();
        //parentFullName = in.readString();
        //parentMessage = in.readString();
        type = in.readInt();
        zTools = in.readString();
        zInterval = in.readString();
    }*/

    /*public static final Creator<ModelMentorChat> CREATOR = new Creator<ModelMentorChat>() {
        @Override
        public ModelMentorChat createFromParcel(Parcel in) {
            return new ModelMentorChat(in);
        }

        @Override
        public ModelMentorChat[] newArray(int size) {
            return new ModelMentorChat[size];
        }
    };*/

    protected ModelUsersList(Parcel in) {
        Avatar = in.readString();
        DateBirthday = in.readString();
        FullName = in.readString();
        Id = in.readString();
        Login = in.readString();
        PhoneOrMail = in.readString();
        ChatId = in.readString();
        Date = in.readString();
        ParentId = in.readString();
        ToolInterval = in.readString();
        ToolType = in.readString();
        Watched = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Avatar);
        dest.writeString(DateBirthday);
        dest.writeString(FullName);
        dest.writeString(Id);
        dest.writeString(Login);
        dest.writeString(PhoneOrMail);
        dest.writeString(ChatId);
        dest.writeString(Date);
        dest.writeString(ParentId);
        dest.writeString(ToolInterval);
        dest.writeString(ToolType);
        dest.writeString(Watched);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelUsersList> CREATOR = new Creator<ModelUsersList>() {
        @Override
        public ModelUsersList createFromParcel(Parcel in) {
            return new ModelUsersList(in);
        }

        @Override
        public ModelUsersList[] newArray(int size) {
            return new ModelUsersList[size];
        }
    };

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }
    public String getAvatar() {
        return Avatar;
    }

    public void setDateBirthday(String DateBirthday) {
        this.DateBirthday = DateBirthday;
    }
    public String getDateBirthday() {
        return DateBirthday;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }
    public String getFullName() {
        return FullName;
    }

    public void setChildId(String Id) {
        this.Id = Id;
    }
    public String getChildId() {
        return Id;
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

    public void setChatId(String ChatId) {
        this.ChatId = ChatId;
    }
    public String getChatId() {
        return ChatId;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
    public String getDate() {
        return Date;
    }

    public void setParentId(String ParentId) {
        this.ParentId = ParentId;
    }
    public String getParentId() {
        return ParentId;
    }

    public void setToolInterval(String ToolInterval) {
        this.ToolInterval = ToolInterval;
    }
    public String getToolInterval() {
        return ToolInterval;
    }

    public void setToolType(String ToolType) {
        this.ToolType = ToolType;
    }
    public String getToolType() {
        return ToolType;
    }

    public void setWatched(String Watched) {
        this.Watched = Watched;
    }
    public String getWatched() {
        return Watched;
    }

    public void setGroupId(String GroupId) {
        this.GroupId = GroupId;
    }
    public String getGroupId() {
        return GroupId;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
    public String getId() {
        return Id;
    }

    public String getLastGrade() {
        return lastGrade;
    }

    public void setLastGrade(String lastGrade) {
        this.lastGrade = lastGrade;
    }

    public String getCenterGrade() {
        return centerGrade;
    }

    public void setCenterGrade(String centerGrade) {
        this.centerGrade = centerGrade;
    }

    public String getSponsorId() {
        return SponsorId;
    }

    public void setSponsorId(String sponsorId) {
        SponsorId = sponsorId;
    }

    public String getMinCenterGrade() {
        return minCenterGrade;
    }

    public void setMinCenterGrade(String minCenterGrade) {
        this.minCenterGrade = minCenterGrade;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}