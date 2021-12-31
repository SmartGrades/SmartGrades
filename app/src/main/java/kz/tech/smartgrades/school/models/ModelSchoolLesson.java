package kz.tech.smartgrades.school.models;

import java.util.ArrayList;
public class ModelSchoolLesson{

    private String Index;
    private String SchoolId;
    private String ClassId;
    private String ClassName;
    private String LessonId;
    private String LessonName;
    private int StudentsCount;

    private ArrayList<ModelSchoolStudent> Students;
    private ArrayList<ModelSchoolTeacher> Teachers;

    public String getLessonId(){
        return LessonId;
    }
    public void setLessonId(String lessonId){
        LessonId = lessonId;
    }
    public String getLessonName(){
        return LessonName;
    }
    public void setLessonName(String lessonName){
        LessonName = lessonName;
    }
    public String getSchoolId(){
        return SchoolId;
    }
    public void setSchoolId(String schoolId){
        SchoolId = schoolId;
    }
    public String getClassId(){
        return ClassId;
    }
    public void setClassId(String classId){
        ClassId = classId;
    }
    public String getIndex(){
        return Index;
    }
    public void setIndex(String index){
        Index = index;
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
    public int getStudentsCount(){
        return StudentsCount;
    }
    public void setStudentsCount(int studentsCount){
        StudentsCount = studentsCount;
    }
    public String getClassName(){
        return ClassName;
    }
    public void setClassName(String className){
        ClassName = className;
    }
}