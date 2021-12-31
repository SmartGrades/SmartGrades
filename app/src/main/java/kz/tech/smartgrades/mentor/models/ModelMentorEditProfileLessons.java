package kz.tech.smartgrades.mentor.models;

import java.util.ArrayList;

import kz.tech.smartgrades.root.models.ModelLesson;

public class ModelMentorEditProfileLessons{

    private String Id;
    private ArrayList<ModelLesson> Lessons;

    public String getId(){
        return Id;
    }
    public void setId(String id){
        Id = id;
    }
    public ArrayList<ModelLesson> getLessons(){
        return Lessons;
    }
    public void setLessons(ArrayList<ModelLesson> lessons){
        Lessons = lessons;
    }
}