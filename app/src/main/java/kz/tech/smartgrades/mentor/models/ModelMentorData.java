package kz.tech.smartgrades.mentor.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.parent.model.ModelInterFormOfMentoring;
import kz.tech.smartgrades.parent.model.ModelPrivateChat;
import kz.tech.smartgrades.school.models.ModelSchoolClassesColumn;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelSchoolRequest;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;

public class ModelMentorData implements Parcelable{

    private ArrayList<ModelMentorGroups> MentorGroups;

    private ArrayList<ModelSchoolClassesColumn> ClassessColumns;
    private ArrayList<ModelSchoolStudent> Students;
    private ArrayList<ModelSchoolData> Schools;
    private ArrayList<ModelSchoolLesson> Lessons;

    private ArrayList<ModelPrivateChat> PrivateChats;
    private ArrayList<ModelSchoolRequest> Requests;
    private ArrayList<ModelInterFormOfMentoring> InterForms;

    protected ModelMentorData(Parcel in){
        MentorGroups = in.createTypedArrayList(ModelMentorGroups.CREATOR);
        Schools = in.createTypedArrayList(ModelSchoolData.CREATOR);
        Requests = in.createTypedArrayList(ModelSchoolRequest.CREATOR);
    }
    public static final Creator<ModelMentorData> CREATOR = new Creator<ModelMentorData>(){
        @Override
        public ModelMentorData createFromParcel(Parcel in){
            return new ModelMentorData(in);
        }

        @Override
        public ModelMentorData[] newArray(int size){
            return new ModelMentorData[size];
        }
    };
    public ArrayList<ModelSchoolClassesColumn> getClassessColumns(){
        return ClassessColumns;
    }
    public void setClassessColumns(ArrayList<ModelSchoolClassesColumn> classessColumns){
        ClassessColumns = classessColumns;
    }
    public ArrayList<ModelSchoolStudent> getStudents(){
        return Students;
    }
    public void setStudents(ArrayList<ModelSchoolStudent> students){
        Students = students;
    }
    public ArrayList<ModelSchoolData> getSchools(){
        return Schools;
    }
    public void setSchools(ArrayList<ModelSchoolData> schools){
        Schools = schools;
    }
    public ArrayList<ModelSchoolLesson> getLessons(){
        return Lessons;
    }
    public void setLessons(ArrayList<ModelSchoolLesson> lessons){
        Lessons = lessons;
    }
    public ArrayList<ModelSchoolRequest> getRequests(){
        return Requests;
    }
    public void setRequests(ArrayList<ModelSchoolRequest> requests){
        Requests = requests;
    }
    public ModelMentorData(){
    }
    public ArrayList<ModelMentorGroups> getMentorGroups(){
        return MentorGroups;
    }
    public void setMentorGroups(ArrayList<ModelMentorGroups> mentorGroups){
        MentorGroups = mentorGroups;
    }
    public ArrayList<ModelPrivateChat> getPrivateChats(){
        return PrivateChats;
    }
    public void setPrivateChats(ArrayList<ModelPrivateChat> privateChats){
        PrivateChats = privateChats;
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeTypedList(MentorGroups);
        parcel.writeTypedList(Schools);
        parcel.writeTypedList(Requests);
    }
    public ArrayList<ModelInterFormOfMentoring> getInterForms(){
        return InterForms;
    }
    public void setInterForms(ArrayList<ModelInterFormOfMentoring> interForms){
        InterForms = interForms;
    }
}