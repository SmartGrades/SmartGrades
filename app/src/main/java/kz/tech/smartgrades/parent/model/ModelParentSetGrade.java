package kz.tech.smartgrades.parent.model;

public class ModelParentSetGrade {
    private String SourceId;
    private String ChildLessonId;
    private String Grade;
    private String ChildId;

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

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getChildId() {
        return ChildId;
    }

    public void setChildId(String childId) {
        ChildId = childId;
    }
}
