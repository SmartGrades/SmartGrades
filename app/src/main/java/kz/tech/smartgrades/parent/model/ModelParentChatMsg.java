package kz.tech.smartgrades.parent.model;

public class ModelParentChatMsg {
    private String date;
    private String message;
    private String type;
    public ModelParentChatMsg() {}

    public ModelParentChatMsg(String date, String message, String type) {
        this.date = date;
        this.message = message;
        this.type = type;
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
}
