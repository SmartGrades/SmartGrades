package kz.tech.smartgrades.mentor.models;

public class ModelChatMentorMsg {

    private String date;
    private String sourceId;
    private String mssage;
    private String type;

    public ModelChatMentorMsg() {}

    public ModelChatMentorMsg(String date, String sourceId, String mssage, String type) {
        this.date = date;
        this.sourceId = sourceId;
        this.mssage = mssage;
        this.type = type;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getSourceId() {
        return sourceId;
    }
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getMessage() {
        return mssage;
    }
    public void setMessage(String mssage) {
        this.mssage = mssage;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
