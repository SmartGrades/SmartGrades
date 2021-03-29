package kz.tech.smartgrades.parent.model;

public class ModelParentChatSteps {
    private String data;
    private String date;
    private String message;
    private String type;
    private String sourceId;

    public ModelParentChatSteps() {}

    public ModelParentChatSteps(String data, String date, String message, String type, String sourceId) {
        this.data = data;
        this.date = date;
        this.message = message;
        this.type = type;
        this.sourceId = sourceId;
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
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

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}
