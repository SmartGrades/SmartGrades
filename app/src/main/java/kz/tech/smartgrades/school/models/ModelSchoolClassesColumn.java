package kz.tech.smartgrades.school.models;

import java.util.ArrayList;

public class ModelSchoolClassesColumn{

    private String Id;
    private String SchoolId;
    private String Name;

    private ArrayList<ModelSchoolClass> Classes;


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
    public ArrayList<ModelSchoolClass> getClasses() {
        return Classes;
    }
    public void setClasses(ArrayList<ModelSchoolClass> classes) {
        Classes = classes;
    }
    public String getSchoolId(){
        return SchoolId;
    }
    public void setSchoolId(String schoolId){
        SchoolId = schoolId;
    }
}