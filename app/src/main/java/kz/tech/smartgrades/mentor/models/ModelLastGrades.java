package kz.tech.smartgrades.mentor.models;

public class ModelLastGrades {
    private String ChildId;
    private String Grade;
    private String GradeType;

    public ModelLastGrades() { }

    public ModelLastGrades(String ChildId, String Grade, String GradeType) {
        this.ChildId = ChildId;
        this.Grade = Grade;
        this.GradeType = GradeType;
    }

    public String getChildId() {
        return ChildId;
    }

    public void setChildId(String childId) {
        ChildId = childId;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getGradeType() {
        return GradeType;
    }

    public void setGradeType(String gradeType) {
        GradeType = gradeType;
    }
}