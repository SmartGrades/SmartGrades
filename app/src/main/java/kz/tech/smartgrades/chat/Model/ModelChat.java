package kz.tech.smartgrades.chat.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelChat implements Parcelable {

    private String SourceId;
    private String SourceName;
    private String SourceAvatar;

    private String TargetId;
    private String TargetName;
    private String TargetAvatar;

    private String MessageId;
    private String ChatId;
    private String Data;
    private String Message;
    private String Type;


    public ModelChat() { }

    protected ModelChat(Parcel in) {
        SourceId = in.readString();
        SourceName = in.readString();
        SourceAvatar = in.readString();
        TargetId = in.readString();
        TargetName = in.readString();
        TargetAvatar = in.readString();
        ChatId = in.readString();
        Data = in.readString();
        Message = in.readString();
        Type = in.readString();
    }

    public static final Creator<ModelChat> CREATOR = new Creator<ModelChat>() {
        @Override
        public ModelChat createFromParcel(Parcel in) {
            return new ModelChat(in);
        }

        @Override
        public ModelChat[] newArray(int size) {
            return new ModelChat[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(SourceId);
        parcel.writeString(SourceName);
        parcel.writeString(SourceAvatar);
        parcel.writeString(TargetId);
        parcel.writeString(TargetName);
        parcel.writeString(TargetAvatar);
        parcel.writeString(ChatId);
        parcel.writeString(Data);
        parcel.writeString(Message);
        parcel.writeString(Type);
    }


    public String getSourceId() {
        return SourceId;
    }

    public void setSourceId(String sourceId) {
        SourceId = sourceId;
    }

    public String getSourceName() {
        return SourceName;
    }

    public void setSourceName(String sourceName) {
        SourceName = sourceName;
    }

    public String getSourceAvatar() {
        return SourceAvatar;
    }

    public void setSourceAvatar(String sourceAvatar) {
        SourceAvatar = sourceAvatar;
    }

    public String getTargetId() {
        return TargetId;
    }

    public void setTargetId(String targetId) {
        TargetId = targetId;
    }

    public String getTargetName() {
        return TargetName;
    }

    public void setTargetName(String targetName) {
        TargetName = targetName;
    }

    public String getTargetAvatar() {
        return TargetAvatar;
    }

    public void setTargetAvatar(String targetAvatar) {
        TargetAvatar = targetAvatar;
    }

    public String getChatId() {
        return ChatId;
    }

    public void setChatId(String chatId) {
        ChatId = chatId;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }
}