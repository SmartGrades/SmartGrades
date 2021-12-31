package kz.tech.smartgrades.parent.model;

public class ModelParentSetGrade {
    private String SourceId;
    private String ChildId;
    private String ChildLessonId;
    private int Grade;
    private int Type;


    public String getSourceId() {
        return SourceId;
    }

    public void setSourceId(String sourceId) {
        SourceId = sourceId;
    }

    public String getChildLessonId() {
        return ChildLessonId;
    }

    public void setChildLessonId(String childLessonId) {
        ChildLessonId = childLessonId;
    }

    public int getGrade() {
        return Grade;
    }

    public void setGrade(int grade) {
        Grade = grade;
    }

    public String getChildId() {
        return ChildId;
    }

    public void setChildId(String childId) {
        ChildId = childId;
    }
    public int getType() {
        return Type;
    }
    public void setType(int type) {
        Type = type;
    }
}
