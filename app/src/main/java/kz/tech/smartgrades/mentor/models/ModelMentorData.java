package kz.tech.smartgrades.mentor.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.parent.model.ModelPrivateChat;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelSchoolRequest;

public class ModelMentorData implements Parcelable{

    private ArrayList<ModelMentorClassesColumn> Columns;
    private ArrayList<ModelMentorStudents> Students;
    private ArrayList<ModelMentorSchool> Schools;
    private ArrayList<ModelSchoolLesson> Lessons;

    private ArrayList<ModelInterForm> InterForms;
    private ArrayList<ModelMentorChat> Chats;

    private ArrayList<ModelMentorGroups> MentorGroups;
    private ArrayList<ModelSchoolRequest> Requests;


    protected ModelMentorData(Parcel in){
        MentorGroups = in.createTypedArrayList(ModelMentorGroups.CREATOR);
        Requests = in.createTypedArrayList(ModelSchoolRequest.CREATOR);
        InterForms = in.createTypedArrayList(ModelInterForm.CREATOR);
    }
    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeTypedList(MentorGroups);
        dest.writeTypedList(Requests);
        dest.writeTypedList(InterForms);
    }
    @Override
    public int describeContents(){
        return 0;
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
    public ArrayList<ModelMentorGroups> getMentorGroups(){
        return MentorGroups;
    }
    public void setMentorGroups(ArrayList<ModelMentorGroups> mentorGroups){
        MentorGroups = mentorGroups;
    }
    public ArrayList<ModelMentorClassesColumn> getColumns(){
        return Columns;
    }
    public void setColumns(ArrayList<ModelMentorClassesColumn> columns){
        Columns = columns;
    }
    public ArrayList<ModelMentorStudents> getStudents(){
        return Students;
    }
    public void setStudents(ArrayList<ModelMentorStudents> students){
        Students = students;
    }
    public ArrayList<ModelMentorSchool> getSchools(){
        return Schools;
    }
    public void setSchools(ArrayList<ModelMentorSchool> schools){
        Schools = schools;
    }
    public ArrayList<ModelSchoolLesson> getLessons(){
        return Lessons;
    }
    public void setLessons(ArrayList<ModelSchoolLesson> lessons){
        Lessons = lessons;
    }
    public ArrayList<ModelMentorChat> getChats(){
        return Chats;
    }
    public void setChats(ArrayList<ModelMentorChat> chats){
        Chats = chats;
    }
    public ArrayList<ModelSchoolRequest> getRequests(){
        return Requests;
    }
    public void setRequests(ArrayList<ModelSchoolRequest> requests){
        Requests = requests;
    }
    public ArrayList<ModelInterForm> getInterForms(){
        return InterForms;
    }
    public void setInterForms(ArrayList<ModelInterForm> interForms){
        InterForms = interForms;
    }
}