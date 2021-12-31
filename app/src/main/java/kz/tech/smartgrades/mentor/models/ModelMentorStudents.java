package kz.tech.smartgrades.mentor.models;

import java.util.ArrayList;

public class ModelMentorStudents{

    private String SchoolId;
    private String SchoolName;

    private ArrayList<ModelMentorStudentsList> Students;

    public String getSchoolId(){
        return SchoolId;
    }
    public void setSchoolId(String schoolId){
        SchoolId = schoolId;
    }
    public String getSchoolName(){
        return SchoolName;
    }
    public void setSchoolName(String schoolName){
        SchoolName = schoolName;
    }

    public ArrayList<ModelMentorStudentsList> getStudents(){
        return Students;
    }
    public void setStudents(ArrayList<ModelMentorStudentsList> students){
        Students = students;
    }
}
