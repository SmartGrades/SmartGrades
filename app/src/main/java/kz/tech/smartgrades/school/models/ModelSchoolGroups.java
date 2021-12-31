package kz.tech.smartgrades.school.models;


public class ModelSchoolGroups {
    private String GroupId;
    private String GroupName;
    private String Type;

    public ModelSchoolGroups() { }

    public ModelSchoolGroups(String GroupId, String GroupName, String Type) {
        this.GroupId = GroupId;
        this.GroupName = GroupName;
        this.Type = Type;
    }

    public void setGroupId(String GroupId) {
        this.GroupId = GroupId;
    }
    public String getGroupId() {
        return GroupId;
    }

    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
    }
    public String getGroupName() {
        return GroupName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}