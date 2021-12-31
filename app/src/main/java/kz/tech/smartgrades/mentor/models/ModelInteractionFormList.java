package kz.tech.smartgrades.mentor.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ModelInteractionFormList implements Parcelable {
    private ArrayList<String> arrayList;
    private String chatId;
    private String childId;
    private String date;
    private String parentId;
    private String toolInterval;
    private String toolType;
    private String watched;

    public ModelInteractionFormList() { }

    public ModelInteractionFormList(String chatId, String childId, String date, String parentId, String toolInterval, String toolType, String watched) {
        this.chatId = chatId;
        this.childId = childId;
        this.date = date;
        this.parentId = parentId;
        this.toolInterval = toolInterval;
        this.toolType = toolType;
        this.watched = watched;
    }

    protected ModelInteractionFormList(Parcel in) {
        arrayList = in.createStringArrayList();
        chatId = in.readString();
        childId = in.readString();
        date = in.readString();
        parentId = in.readString();
        toolInterval = in.readString();
        toolType = in.readString();
        watched = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(arrayList);
        dest.writeString(chatId);
        dest.writeString(childId);
        dest.writeString(date);
        dest.writeString(parentId);
        dest.writeString(toolInterval);
        dest.writeString(toolType);
        dest.writeString(watched);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelInteractionFormList> CREATOR = new Creator<ModelInteractionFormList>() {
        @Override
        public ModelInteractionFormList createFromParcel(Parcel in) {
            return new ModelInteractionFormList(in);
        }

        @Override
        public ModelInteractionFormList[] newArray(int size) {
            return new ModelInteractionFormList[size];
        }
    };

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
    public String getChatId() {
        return chatId;
    }

    public String getChildId() {
        return childId;
    }
    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getToolInterval() {
        return toolInterval;
    }
    public void setToolInterval(String toolInterval) {
        this.toolInterval = toolInterval;
    }

    public String getToolType() {
        return toolType;
    }
    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getWatched() {
        return watched;
    }
    public void setWatched(String watched) {
        this.watched = watched;
    }
}