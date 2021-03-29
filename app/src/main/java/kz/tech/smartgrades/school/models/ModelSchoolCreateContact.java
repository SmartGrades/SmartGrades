package kz.tech.smartgrades.school.models;

import java.util.ArrayList;

import kz.tech.smartgrades.root.models.ModelLesson;

public class ModelSchoolCreateContact{

    private String SchoolId;

    private String Avatar;
    private String FirstName;
    private String LastName;
    private String Patronymic;
    private String ClassId;

    private ArrayList<ModelLesson> Lessons;
    private ArrayList<ModelSchoolClass> Classes;

    public String getAvatar(){
        return Avatar;
    }
    public void setAvatar(String avatar){
        Avatar = avatar;
    }
    public String getFirstName(){
        return FirstName;
    }
    public void setFirstName(String firstName){
        FirstName = firstName;
    }
    public String getLastName(){
        return LastName;
    }
    public void setLastName(String lastName){
        LastName = lastName;
    }
    public String getPatronymic(){
        return Patronymic;
    }
    public void setPatronymic(String patronymic){
        Patronymic = patronymic;
    }
    public String getClassId(){
        return ClassId;
    }
    public void setClassId(String classId){
        ClassId = classId;
    }
    public ArrayList<ModelLesson> getLessons() {
        return Lessons;
    }
    public void setLessons(ArrayList<ModelLesson> lessons) {
        Lessons = lessons;
    }
    public ArrayList<ModelSchoolClass> getClasses() {
        return Classes;
    }
    public void setClasses(ArrayList<ModelSchoolClass> classes) {
        Classes = classes;
    }
    public String getSchoolId() {
        return SchoolId;
    }
    public void setSchoolId(String schoolId) {
        SchoolId = schoolId;
    }
}
