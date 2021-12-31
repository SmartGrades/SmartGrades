package kz.tech.smartgrades.parent.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelChildData2 implements Parcelable {

    private String UserId;
    private String avatar;
    private String fullName;
    private String login;
    private String type;

    private String chatId;
    private String date;
    private String lastMessage;

    private String centerGrade;
    private String minCenterGrade;
    private String SponsorId;


    public ModelChildData2() {}

    public ModelChildData2(String avatar, String chatId, String id, String sponsorId,
                           String date, String fullName, String login, String type, String centerGrade, String minCenterGrade) {
        this.avatar = avatar;
        this.chatId = chatId;
        this.UserId = id;
        this.date = date;
        this.fullName = fullName;
        this.login = login;
        this.type = type;
        this.centerGrade = centerGrade;
        this.minCenterGrade = minCenterGrade;
    }

    protected ModelChildData2(Parcel in) {
        avatar = in.readString();
        chatId = in.readString();
        UserId = in.readString();
        date = in.readString();
        fullName = in.readString();
        login = in.readString();
        type = in.readString();
    }

    public String getChatId() {
        return chatId;
    }
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUserId() {
        return UserId;
    }
    public void setUserId(String userId) {
        this.UserId = userId;
    }

    public static final Creator<ModelChildData2> CREATOR = new Creator<ModelChildData2>() {
        @Override
        public ModelChildData2 createFromParcel(Parcel in) {
            return new ModelChildData2(in);
        }

        @Override
        public ModelChildData2[] newArray(int size) {
            return new ModelChildData2[size];
        }
    };

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatar);
        dest.writeString(chatId);
        dest.writeString(UserId);
        dest.writeString(date);
        dest.writeString(fullName);
        dest.writeString(login);
        dest.writeString(type);
    }

    public String getCenterGrade() {
        return centerGrade;
    }

    public void setCenterGrade(String centerGrade) {
        this.centerGrade = centerGrade;
    }

    public String getMinCenterGrade() {
        return minCenterGrade;
    }

    public void setMinCenterGrade(String minCenterGrade) {
        this.minCenterGrade = minCenterGrade;
    }

    public String getSponsorId() {
        return SponsorId;
    }

    public void setSponsorId(String sponsorId) {
        SponsorId = sponsorId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}