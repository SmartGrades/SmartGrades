package kz.tech.smartgrades.mentor.models;

import java.util.ArrayList;

import kz.tech.smartgrades.root.models.ModelLesson;

public class ModelMentorClass{

    private String Id;
    private String MentorId;
    private String Name;
    private String SchoolId;
    private String ColumnId;

    private String SchoolName;

    private ArrayList<ModelLesson> Lessons;
    private ArrayList<ModelMentorStudentsList> Students;

    public String getId(){
        return Id;
    }
    public void setId(String id){
        Id = id;
    }
    public String getSchoolId(){
        return SchoolId;
    }
    public void setSchoolId(String schoolId){
        SchoolId = schoolId;
    }
    public String getColumnId(){
        return ColumnId;
    }
    public void setColumnId(String columnId){
        ColumnId = columnId;
    }
    public String getName(){
        return Name;
    }
    public void setName(String name){
        Name = name;
    }
    public ArrayList<ModelLesson> getLessons(){
        return Lessons;
    }
    public void setLessons(ArrayList<ModelLesson> lessons){
        Lessons = lessons;
    }
    public String getMentorId(){
        return MentorId;
    }
    public void setMentorId(String mentorId){
        MentorId = mentorId;
    }
    public ArrayList<ModelMentorStudentsList> getStudents(){
        return Students;
    }
    public void setStudents(ArrayList<ModelMentorStudentsList> students){
        Students = students;
    }
    public String getSchoolName(){
        return SchoolName;
    }
    public void setSchoolName(String schoolName){
        SchoolName = schoolName;
    }
}