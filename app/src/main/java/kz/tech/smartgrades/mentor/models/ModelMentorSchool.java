package kz.tech.smartgrades.mentor.models;

import java.util.ArrayList;

import kz.tech.smartgrades.root.models.ModelLesson;

public class ModelMentorSchool{

    private String MentorId;
    private String Id;
    private String Name;
    private String Address;
    private String Site;
    private String Phone;
    private String Mail;

    private int ClassesCount;
    private int StudentsCount;

    private ArrayList<ModelLesson> Lessons;

    public String getMentorId(){
        return MentorId;
    }
    public void setMentorId(String mentorId){
        MentorId = mentorId;
    }
    public String getId(){
        return Id;
    }
    public void setId(String id){
        Id = id;
    }
    public String getName(){
        return Name;
    }
    public void setName(String name){
        Name = name;
    }
    public String getAddress(){
        return Address;
    }
    public void setAddress(String address){
        Address = address;
    }
    public String getSite(){
        return Site;
    }
    public void setSite(String site){
        Site = site;
    }
    public String getPhone(){
        return Phone;
    }
    public void setPhone(String phone){
        Phone = phone;
    }
    public String getMail(){
        return Mail;
    }
    public void setMail(String mail){
        Mail = mail;
    }
    public int getClassesCount(){
        return ClassesCount;
    }
    public void setClassesCount(int classesCount){
        ClassesCount = classesCount;
    }
    public int getStudentsCount(){
        return StudentsCount;
    }
    public void setStudentsCount(int studentsCount){
        StudentsCount = studentsCount;
    }
    public ArrayList<ModelLesson> getLessons(){
        return Lessons;
    }
    public void setLessons(ArrayList<ModelLesson> lessons){
        Lessons = lessons;
    }
}