package kz.tech.smartgrades.school.models;

import java.util.ArrayList;

import kz.tech.smartgrades.root.models.ModelLesson;

public class ModelTeacherProfileClasses {
    private String Id;
    private String Name;
    private ArrayList<ModelLesson> Lessons;

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

    public ArrayList<ModelLesson> getLessons() {
        return Lessons;
    }

    public void setLessons(ArrayList<ModelLesson> lessons) {
        Lessons = lessons;
    }
}
