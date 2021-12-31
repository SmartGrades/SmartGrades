package kz.tech.smartgrades.parent.model;

import android.os.Parcel;
import android.os.Parcelable;
public class ModelPrivateChat implements Parcelable{

    private String TargetId;
    private String TargetFirstName;
    private String TargetLastName;
    private String TargetAvatar;
    private String TargetType;

    private String ChatId;
    private String ChatName;
    private String ChatType;

    private String LastMessageDate;
    private String LastMessage;
    private int NoCheckCount;

    protected ModelPrivateChat(Parcel in){
        TargetId = in.readString();
        TargetFirstName = in.readString();
        TargetLastName = in.readString();
        TargetAvatar = in.readString();
        TargetType = in.readString();
        ChatId = in.readString();
        ChatName = in.readString();
        ChatType = in.readString();
        LastMessageDate = in.readString();
        LastMessage = in.readString();
        NoCheckCount = in.readInt();
    }
    public static final Creator<ModelPrivateChat> CREATOR = new Creator<ModelPrivateChat>(){
        @Override
        public ModelPrivateChat createFromParcel(Parcel in){
            return new ModelPrivateChat(in);
        }

        @Override
        public ModelPrivateChat[] newArray(int size){
            return new ModelPrivateChat[size];
        }
    };
    public String getTargetId() {
        return TargetId;
    }

    public void setTargetId(String targetId) {
        TargetId = targetId;
    }

    public String getTargetFirstName() {
        return TargetFirstName;
    }

    public void setTargetFirstName(String targetFirstName) {
        TargetFirstName = targetFirstName;
    }

    public String getTargetLastName() {
        return TargetLastName;
    }

    public void setTargetLastName(String targetLastName) {
        TargetLastName = targetLastName;
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

    public int getNoCheckCount() {
        return NoCheckCount;
    }

    public void setNoCheckCount(int noCheckCount) {
        NoCheckCount = noCheckCount;
    }

    public String getTargetType() {
        return TargetType;
    }

    public void setTargetType(String targetType) {
        TargetType = targetType;
    }
    public String getChatName(){
        return ChatName;
    }
    public void setChatName(String chatName){
        ChatName = chatName;
    }
    public String getChatType(){
        return ChatType;
    }
    public void setChatType(String chatType){
        ChatType = chatType;
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(TargetId);
        parcel.writeString(TargetFirstName);
        parcel.writeString(TargetLastName);
        parcel.writeString(TargetAvatar);
        parcel.writeString(TargetType);
        parcel.writeString(ChatId);
        parcel.writeString(ChatName);
        parcel.writeString(ChatType);
        parcel.writeString(LastMessageDate);
        parcel.writeString(LastMessage);
        parcel.writeInt(NoCheckCount);
    }
}
