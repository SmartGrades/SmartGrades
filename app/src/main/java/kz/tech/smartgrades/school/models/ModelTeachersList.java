package kz.tech.smartgrades.school.models;

import java.util.ArrayList;

public class ModelTeachersList {
    private String SchoolId;
    private String ClassId;
    private boolean IsAddedToClass;
    private boolean IsSelected;

    private String Id;
    private String UserId;
    private String FirstName;
    private String LastName;
    private String Patronymic;
    private String Avatar;
    private String AvatarOriginal;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPatronymic() {
        return Patronymic;
    }

    public void setPatronymic(String patronymic) {
        Patronymic = patronymic;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getAvatarOriginal() {
        return AvatarOriginal;
    }

    public void setAvatarOriginal(String avatarOriginal) {
        AvatarOriginal = avatarOriginal;
    }

    public ArrayList<ModelTeachersListLesson> getLessons() {
        return Lessons;
    }

    public void setLessons(ArrayList<ModelTeachersListLesson> lessons) {
        Lessons = lessons;
    }

    private ArrayList<ModelTeachersListLesson> Lessons;

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String schoolId) {
        SchoolId = schoolId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
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
