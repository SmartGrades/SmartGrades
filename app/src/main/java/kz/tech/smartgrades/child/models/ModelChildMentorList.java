package kz.tech.smartgrades.child.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.parent.model.ModelGrades;

public class ModelChildMentorList implements Parcelable {

    private String MentorId;
    private String MentorFirstName;
    private String MentorLastName;
    private String MentorAvatar;

    private String GroupId;
    private String GroupName;

    private String LessonId;
    private String LessonName;

    private String AverageGrade;

    private String ChatId;
    private String LastMessage;
    private String LastMessageDate;
    private String LastMessageType;
    private int NoCheckCount;

    private String Model;

    private ArrayList<ModelGrades> Grades;


    public ModelChildMentorList(){}

    protected ModelChildMentorList(Parcel in) {
        MentorId = in.readString();
        MentorFirstName = in.readString();
        MentorLastName = in.readString();
        MentorAvatar = in.readString();
        GroupId = in.readString();
        GroupName = in.readString();
        LessonId = in.readString();
        LessonName = in.readString();
        AverageGrade = in.readString();
    }

    public static final Creator<ModelChildMentorList> CREATOR = new Creator<ModelChildMentorList>() {
        @Override
        public ModelChildMentorList createFromParcel(Parcel in) {
            return new ModelChildMentorList(in);
        }

        @Override
        public ModelChildMentorList[] newArray(int size) {
            return new ModelChildMentorList[size];
        }
    };

    public String getMentorId() {
        return MentorId;
    }

    public void setMentorId(String mentorId) {
        MentorId = mentorId;
    }

    public String getMentorFirstName() {
        return MentorFirstName;
    }

    public void setMentorFirstName(String mentorFirstName) {
        MentorFirstName = mentorFirstName;
    }

    public String getMentorLastName() {
        return MentorLastName;
    }

    public void setMentorLastName(String mentorLastName) {
        MentorLastName = mentorLastName;
    }

    public String getMentorAvatar() {
        return MentorAvatar;
    }

    public void setMentorAvatar(String mentorAvatar) {
        MentorAvatar = mentorAvatar;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getLessonId() {
        return LessonId;
    }

    public void setLessonId(String lessonId) {
        LessonId = lessonId;
    }

    public String getLessonName() {
        return LessonName;
    }

    public void setLessonName(String lessonName) {
        LessonName = lessonName;
    }

    public ArrayList<ModelGrades> getGrades() {
        return Grades;
    }

    public void setGrades(ArrayList<ModelGrades> grades) {
        Grades = grades;
    }

    public String getAverageGrade() {
        return AverageGrade;
    }

    public void setAverageGrade(String averageGrade) {
        AverageGrade = averageGrade;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(MentorId);
        parcel.writeString(MentorFirstName);
        parcel.writeString(MentorLastName);
        parcel.writeString(MentorAvatar);
        parcel.writeString(GroupId);
        parcel.writeString(GroupName);
        parcel.writeString(LessonId);
        parcel.writeString(LessonName);
        parcel.writeString(AverageGrade);
    }

    public String getLastMessage() {
        return LastMessage;
    }

    public void setLastMessage(String lastMessage) {
        LastMessage = lastMessage;
    }

    public String getLastMessageDate() {
        return LastMessageDate;
    }

    public void setLastMessageDate(String lastMessageDate) {
        LastMessageDate = lastMessageDate;
    }

    public String getLastMessageType() {
        return LastMessageType;
    }

    public void setLastMessageType(String lastMessageType) {
        LastMessageType = lastMessageType;
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
}