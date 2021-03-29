package kz.tech.smartgrades.parent.model;

public class ModelGrades {

    private String Date;
    private String Id;
    private String MentorId;
    private String ChildLessonId;
    private String Grade;
    private int Type;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMentorId() {
        return MentorId;
    }

    public void setMentorId(String mentorId) {
        MentorId = mentorId;
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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
