package kz.tech.smartgrades.mentor.models;

public class ModelChildItem {

    private String avatar;
    private String date;
    private String fullName;
    private String toolsInterval;

    public ModelChildItem() {}

    public ModelChildItem(String avatar, String date, String fullName, String toolsInterval) {
        this.avatar = avatar;
        this.date = date;
        this.fullName = fullName;
        this.toolsInterval = toolsInterval;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getToolsInterval() {
        return toolsInterval;
    }

    public void setToolsInterval(String toolsInterval) {
        this.toolsInterval = toolsInterval;
    }
}
