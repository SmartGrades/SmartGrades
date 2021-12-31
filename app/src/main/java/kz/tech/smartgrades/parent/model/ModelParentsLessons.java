package kz.tech.smartgrades.parent.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ModelParentsLessons {

    private String LessonId;
    private String Date;
    private String ParentId;
    private String ChildId;
    private String ChildAvatar;
    private String ChildFirstName;
    private String ChildLastName;
    private String LessonName;
    private String RewardFor2;
    private String RewardFor3;
    private String RewardFor4;
    private String RewardFor5;
    private String ChatId;
    private double AverageGrade;

    private String LastMessage;
    private String LastMessageDate;
    private String LastMessageType;


    public String getLessonId() {
        return LessonId;
    }

    public void setLessonId(String lessonId) {
        LessonId = lessonId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getChildId() {
        return ChildId;
    }

    public void setChildId(String childId) {
        ChildId = childId;
    }

    public String getLessonName() {
        return LessonName;
    }

    public void setLessonName(String lessonName) {
        LessonName = lessonName;
    }

    public String getRewardFor2() {
        return RewardFor2;
    }

    public void setRewardFor2(String rewardFor2) {
        RewardFor2 = rewardFor2;
    }

    public String getRewardFor3() {
        return RewardFor3;
    }

    public void setRewardFor3(String rewardFor3) {
        RewardFor3 = rewardFor3;
    }

    public String getRewardFor4() {
        return RewardFor4;
    }

    public void setRewardFor4(String rewardFor4) {
        RewardFor4 = rewardFor4;
    }

    public String getRewardFor5() {
        return RewardFor5;
    }

    public void setRewardFor5(String rewardFor5) {
        RewardFor5 = rewardFor5;
    }

    public String getChatId() {
        return ChatId;
    }

    public void setChatId(String chatId) {
        ChatId = chatId;
    }

    public String getChildAvatar() {
        return ChildAvatar;
    }

    public void setChildAvatar(String childAvatar) {
        ChildAvatar = childAvatar;
    }

    public String getChildFirstName() {
        return ChildFirstName;
    }

    public void setChildFirstName(String childFirstName) {
        ChildFirstName = childFirstName;
    }

    public String getChildLastName() {
        return ChildLastName;
    }

    public void setChildLastName(String childLastName) {
        ChildLastName = childLastName;
    }

    public double getAverageGrade() {
        return AverageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        AverageGrade = averageGrade;
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
}
