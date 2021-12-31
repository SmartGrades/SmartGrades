package kz.tech.smartgrades.school.models;

import java.util.ArrayList;
public class ModelSchoolClass{

    private String Id;
    private String Name;
    private String SchoolId;
    private String SchoolName;
    private String ColumnId;
    private String ChildLessonId;

    private ArrayList<ModelSchoolTeacher> Teachers;
    private ArrayList<ModelSchoolStudent> Students;
    private ArrayList<ModelSchoolLesson> Lessons;

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
    public ArrayList<ModelSchoolStudent> getStudents(){
        return Students;
    }
    public void setStudents(ArrayList<ModelSchoolStudent> students){
        Students = students;
    }
    public ArrayList<ModelSchoolTeacher> getTeachers(){
        return Teachers;
    }
    public void setTeachers(ArrayList<ModelSchoolTeacher> teachers){
        Teachers = teachers;
    }
    public ArrayList<ModelSchoolLesson> getLessons(){
        return Lessons;
    }
    public void setLessons(ArrayList<ModelSchoolLesson> lessons){
        Lessons = lessons;
    }
    public String getChildLessonId(){
        return ChildLessonId;
    }
    public void setChildLessonId(String childLessonId){
        ChildLessonId = childLessonId;
    }
    public String getSchoolName(){
        return SchoolName;
    }
    public void setSchoolName(String schoolName){
        SchoolName = schoolName;
    }
}