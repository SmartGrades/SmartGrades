package kz.tech.smartgrades.parent.model;

public class ModelParentDeleteChildFromSchool {
    private String ParentId;
    private String ChildId;
    private String SchoolId;

    public String isParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getChildId() {
        return ChildId;
    }

    public void setChildId(String childId) {
        ChildId = childId;
    }

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String schoolId) {
        SchoolId = schoolId;
    }
}
