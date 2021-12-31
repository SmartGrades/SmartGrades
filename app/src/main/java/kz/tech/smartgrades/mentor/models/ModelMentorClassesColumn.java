package kz.tech.smartgrades.mentor.models;

import java.util.ArrayList;

public class ModelMentorClassesColumn{

    private String Id;
    private String SchoolId;
    private String Name;

    private ArrayList<ModelMentorClass> Classes;


    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public ArrayList<ModelMentorClass> getClasses() {
        return Classes;
    }
    public void setClasses(ArrayList<ModelMentorClass> classes) {
        Classes = classes;
    }
    public String getSchoolId(){
        return SchoolId;
    }
    public void setSchoolId(String schoolId){
        SchoolId = schoolId;
    }
}