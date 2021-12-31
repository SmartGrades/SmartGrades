package kz.tech.smartgrades.mentor.models;

public class ModelChatParentMsg {

    private String date;
    private String data;
    private String sourceId;
    private String message;
    private String type;

    private String avatar;
    private String fullName;

    public ModelChatParentMsg() {}

    public ModelChatParentMsg(String date, String data, String sourceId, String message, String type, String avatar, String fullName) {
        this.date = date;
        this.data = data;
        this.sourceId = sourceId;
        this.message = message;
        this.type = type;
        this.avatar = avatar;
        this.fullName = fullName;
    }

    public String getSourceId() {
        return sourceId;
    }
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
