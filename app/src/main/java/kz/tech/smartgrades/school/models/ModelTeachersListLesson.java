package kz.tech.smartgrades.school.models;

public class ModelTeachersListLesson {
    private String LessonId;
    private String LessonName;
    private boolean IsAddedToClass;
    private boolean IsSelected;

    public String getLessonId() {
        return LessonId;
    }

    public void setLessonId(String lessonId) {
        LessonId = lessonId;
    }

    public String getLessonName() {
        return LessonName;
    }

    public void setLessonName(String lessonName) {
        LessonName = lessonName;
    }

    public boolean isAddedToClass() {
        return IsAddedToClass;
    }

    public void setAddedToClass(boolean addedToClass) {
        IsAddedToClass = addedToClass;
    }

    public boolean isSelected() {
        return IsSelected;
    }

    public void setSelected(boolean selected) {
        IsSelected = selected;
    }
}
