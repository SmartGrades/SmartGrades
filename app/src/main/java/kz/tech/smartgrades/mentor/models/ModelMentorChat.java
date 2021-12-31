package kz.tech.smartgrades.mentor.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.parent.model.ModelGrades;
import kz.tech.smartgrades.root.models.ModelLesson;

public class ModelMentorChat {

    private String ChatId;
    private String ChatAvatar;
    private String ChatName;
    private String ChatTag;
    private String ChatType;

    private String LastMessageSourceId;
    private String LastMessageSourceFirstName;
    private String LastMessageSourceLastName;
    private String LastMessageDate;
    private String LastMessage;
    private int NoCheckCount;

    public String getChatId() {
        return ChatId;
    }

    public void setChatId(String chatId) {
        ChatId = chatId;
    }

    public String getChatAvatar() {
        return ChatAvatar;
    }

    public void setChatAvatar(String chatAvatar) {
        ChatAvatar = chatAvatar;
    }

    public String getChatName() {
        return ChatName;
    }

    public void setChatName(String chatName) {
        ChatName = chatName;
    }

    public String getChatTag() {
        return ChatTag;
    }

    public void setChatTag(String chatTag) {
        ChatTag = chatTag;
    }

    public String getChatType() {
        return ChatType;
    }

    public void setChatType(String chatType) {
        ChatType = chatType;
    }

    public String getLastMessageSourceId() {
        return LastMessageSourceId;
    }

    public void setLastMessageSourceId(String lastMessageSourceId) {
        LastMessageSourceId = lastMessageSourceId;
    }

    public String getLastMessageSourceFirstName() {
        return LastMessageSourceFirstName;
    }

    public void setLastMessageSourceFirstName(String lastMessageSourceFirstName) {
        LastMessageSourceFirstName = lastMessageSourceFirstName;
    }

    public String getLastMessageSourceLastName() {
        return LastMessageSourceLastName;
    }

    public void setLastMessageSourceLastName(String lastMessageSourceLastName) {
        LastMessageSourceLastName = lastMessageSourceLastName;
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

    public int getNoCheckCount() {
        return NoCheckCount;
    }

    public void setNoCheckCount(int noCheckCount) {
        NoCheckCount = noCheckCount;
    }
}