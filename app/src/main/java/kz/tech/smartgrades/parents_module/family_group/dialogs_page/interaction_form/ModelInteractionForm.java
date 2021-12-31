package kz.tech.smartgrades.parents_module.family_group.dialogs_page.interaction_form;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelInteractionForm implements Parcelable {

    private String Index;
    private String Answer;
    private String Type;

    private String TargetId;
    private String TargetFirstName;
    private String TargetLastName;
    private String TargetAvatar;

    private String SourceId;
    private String SourceFirstName;
    private String SourceLastName;
    private String SourceAvatar;

    private String ChildId;
    private String ChildFirstName;
    private String ChildLastName;
    private String ChildAvatar;

    private String ChatId;
    private String ChatLastMessage;
    private String ChatLastMessageDate;
    private int NoCheckCount;


    protected ModelInteractionForm(Parcel in) {
        Index = in.readString();
        TargetId = in.readString();
        TargetFirstName = in.readString();
        TargetLastName = in.readString();
        TargetAvatar = in.readString();
        SourceId = in.readString();
        SourceFirstName = in.readString();
        SourceLastName = in.readString();
        SourceAvatar = in.readString();
        ChildId = in.readString();
        ChildFirstName = in.readString();
        ChildLastName = in.readString();
        ChildAvatar = in.readString();
        ChatId = in.readString();
        ChatLastMessage = in.readString();
        ChatLastMessageDate = in.readString();
    }

    public static final Creator<ModelInteractionForm> CREATOR = new Creator<ModelInteractionForm>() {
        @Override
        public ModelInteractionForm createFromParcel(Parcel in) {
            return new ModelInteractionForm(in);
        }

        @Override
        public ModelInteractionForm[] newArray(int size) {
            return new ModelInteractionForm[size];
        }
    };

    public String getTargetId() {
        return TargetId;
    }

    public void setTargetId(String targetId) {
        TargetId = targetId;
    }

    public String getSourceId() {
        return SourceId;
    }

    public void setSourceId(String sourceId) {
        SourceId = sourceId;
    }

    public String getChildId() {
        return ChildId;
    }

    public void setChildId(String childId) {
        ChildId = childId;
    }

    public String getChatId() {
        return ChatId;
    }

    public void setChatId(String chatId) {
        ChatId = chatId;
    }

    public String getTargetFirstName() {
        return TargetFirstName;
    }

    public void setTargetFirstName(String targetFirstName) {
        TargetFirstName = targetFirstName;
    }

    public String getTargetAvatar() {
        return TargetAvatar;
    }

    public void setTargetAvatar(String targetAvatar) {
        TargetAvatar = targetAvatar;
    }

    public String getSourceFirstName() {
        return SourceFirstName;
    }

    public void setSourceFirstName(String sourceFirstName) {
        SourceFirstName = sourceFirstName;
    }

    public String getSourceAvatar() {
        return SourceAvatar;
    }

    public void setSourceAvatar(String sourceAvatar) {
        SourceAvatar = sourceAvatar;
    }

    public String getChildFirstName() {
        return ChildFirstName;
    }

    public void setChildFirstName(String childFirstName) {
        ChildFirstName = childFirstName;
    }

    public String getChildAvatar() {
        return ChildAvatar;
    }

    public void setChildAvatar(String childAvatar) {
        ChildAvatar = childAvatar;
    }

    public String getChatLastMessage() {
        return ChatLastMessage;
    }

    public void setChatLastMessage(String chatLastMessage) {
        ChatLastMessage = chatLastMessage;
    }

    public String getChatLastMessageDate() {
        return ChatLastMessageDate;
    }

    public void setChatLastMessageDate(String chatLastMessageDate) {
        ChatLastMessageDate = chatLastMessageDate;
    }

    public String getTargetLastName() {
        return TargetLastName;
    }

    public void setTargetLastName(String targetLastName) {
        TargetLastName = targetLastName;
    }

    public String getIndex() {
        return Index;
    }

    public void setIndex(String index) {
        Index = index;
    }

    public String getSourceLastName() {
        return SourceLastName;
    }

    public void setSourceLastName(String sourceLastName) {
        SourceLastName = sourceLastName;
    }

    public String getChildLastName() {
        return ChildLastName;
    }

    public void setChildLastName(String childLastName) {
        ChildLastName = childLastName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Index);
        parcel.writeString(TargetId);
        parcel.writeString(TargetFirstName);
        parcel.writeString(TargetLastName);
        parcel.writeString(TargetAvatar);
        parcel.writeString(SourceId);
        parcel.writeString(SourceFirstName);
        parcel.writeString(SourceLastName);
        parcel.writeString(SourceAvatar);
        parcel.writeString(ChildId);
        parcel.writeString(ChildFirstName);
        parcel.writeString(ChildLastName);
        parcel.writeString(ChildAvatar);
        parcel.writeString(ChatId);
        parcel.writeString(ChatLastMessage);
        parcel.writeString(ChatLastMessageDate);
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getNoCheckCount() {
        return NoCheckCount;
    }

    public void setNoCheckCount(int noCheckCount) {
        NoCheckCount = noCheckCount;
    }
}
