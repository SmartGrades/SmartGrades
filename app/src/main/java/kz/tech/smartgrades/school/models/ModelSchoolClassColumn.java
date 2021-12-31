package kz.tech.smartgrades.school.models;

import java.util.ArrayList;

public class ModelSchoolClassColumn {

    private String Id;
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
}