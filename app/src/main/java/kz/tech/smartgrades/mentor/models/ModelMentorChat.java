package kz.tech.smartgrades.mentor.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.parent.model.ModelGrades;

public class ModelMentorChat implements Parcelable {

    private String InterFormId;
    private String Answer;
    private String Model;

    private String Avatar;
    private String FirstName;
    private String LastName;
    private String UserId;
    private String Login;
    private String Type;

    private String ChildId;
    private String SourceId;

    private String ChatId;
    private String LastMessage;
    private String LastMessageDate;
    private int NoCheckCount;

    private String AverageGrade;

    private ArrayList<ModelGrades> Grades;


    public ModelMentorChat() { }

    public ModelMentorChat(String Avatar, String DateBirthday, String FullName, String Id, String Login, String PhoneOrMail,
                           String ChatId, String LastMessageDate, String ParentId, String ToolInterval, String ToolType, String GroupId,
                           String lastGrade, String averageGrade, String sponsorId, String minCenterGrade) {
    }

    protected ModelMentorChat(Parcel in) {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelMentorChat> CREATOR = new Creator<ModelMentorChat>() {
        @Override
        public ModelMentorChat createFromParcel(Parcel in) {
            return new ModelMentorChat(in);
        }

        @Override
        public ModelMentorChat[] newArray(int size) {
            return new ModelMentorChat[size];
        }
    };


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

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getLastMessageDate() {
        return LastMessageDate;
    }

    public void setLastMessageDate(String lastMessageDate) {
        LastMessageDate = lastMessageDate;
    }

    public String getLastMessage() {
        return LastMessage;
    }

    public void setLastMessage(String lastMessage) {
        LastMessage = lastMessage;
    }

    public String getAverageGrade() {
        return AverageGrade;
    }

    public void setAverageGrade(String averageGrade) {
        AverageGrade = averageGrade;
    }

    public ArrayList<ModelGrades> getGrades() {
        return Grades;
    }

    public void setGrades(ArrayList<ModelGrades> grades) {
        Grades = grades;
    }

    public String getInterFormId() {
        return InterFormId;
    }

    public void setInterFormId(String interFormId) {
        InterFormId = interFormId;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public int getNoCheckCount() {
        return NoCheckCount;
    }

    public void setNoCheckCount(int noCheckCount) {
        NoCheckCount = noCheckCount;
    }

    public String getChatId() {
        return ChatId;
    }

    public void setChatId(String chatId) {
        ChatId = chatId;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getChildId() {
        return ChildId;
    }

    public void setChildId(String childId) {
        ChildId = childId;
    }

    public String getSourceId() {
        return SourceId;
    }

    public void setSourceId(String sourceId) {
        SourceId = sourceId;
    }
}