package kz.tech.smartgrades.school.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.auth.models.ModelRepresentative;
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.parents_module.family_group.dialogs_page.interaction_form.ModelInteractionForm;

public class ModelSchoolData implements Parcelable{
    private String Id;
    private String Name;
    private String Address;
    private String Site;
    private String Phone;
    private String Login;
    private String Mail;
    private String StateRegistration;
    private String Password;
    private String Code;
    private String SchoolClass;
    private boolean Complaint;

    private ModelLeader modelLeader;
    private ModelRepresentative modelRepresentative;

    private ArrayList<ModelSchoolClassesColumn> ClassessColumns;
    private ArrayList<ModelSchoolStudent> Students;
    private ArrayList<ModelSchoolTeacher> Teachers;
    private ArrayList<ModelSchoolLesson> Lessons;

    private ArrayList<ModelSchoolRequest> Requests;
    private ArrayList<ModelInterForm> InterForms;

    public ModelSchoolData(){
    }

    protected ModelSchoolData(Parcel in){
        Id = in.readString();
        Name = in.readString();
        Address = in.readString();
        Site = in.readString();
        Phone = in.readString();
        Mail = in.readString();
        StateRegistration = in.readString();
        Password = in.readString();
        Code = in.readString();
    }

    public static final Creator<ModelSchoolData> CREATOR = new Creator<ModelSchoolData>(){
        @Override
        public ModelSchoolData createFromParcel(Parcel in){
            return new ModelSchoolData(in);
        }

        @Override
        public ModelSchoolData[] newArray(int size){
            return new ModelSchoolData[size];
        }
    };

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

    public String getStateRegistration(){
        return StateRegistration;
    }

    public void setStateRegistration(String stateRegistration){
        StateRegistration = stateRegistration;
    }

    public ModelLeader getModelLeader(){
        return modelLeader;
    }

    public void setModelLeader(ModelLeader modelLeader){
        this.modelLeader = modelLeader;
    }
    public void setModelRepresentative(ModelRepresentative modelRepresentative){
        this.modelRepresentative = modelRepresentative;
    }

    public String getPassword(){
        return Password;
    }

    public void setPassword(String password){
        Password = password;
    }

    public String getCode(){
        return Code;
    }

    public void setCode(String code){
        Code = code;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(Id);
        parcel.writeString(Name);
        parcel.writeString(Address);
        parcel.writeString(Site);
        parcel.writeString(Phone);
        parcel.writeString(Mail);
        parcel.writeString(StateRegistration);
        parcel.writeString(Password);
        parcel.writeString(Code);
    }

    /*public ModelSchoolData getClassessColumns() {
        return ClassessColumns;
    }*/
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
    public ArrayList<ModelSchoolLesson> getLessons(){
        return Lessons;
    }
    public void setLessons(ArrayList<ModelSchoolLesson> lessons){
        Lessons = lessons;
    }
    public ArrayList<ModelSchoolTeacher> getTeachers(){
        return Teachers;
    }
    public void setTeachers(ArrayList<ModelSchoolTeacher> teachers){
        Teachers = teachers;
    }
    public ArrayList<ModelSchoolRequest> getRequests(){
        return Requests;
    }
    public void setRequests(ArrayList<ModelSchoolRequest> requests){
        Requests = requests;
    }
    public boolean isComplaint(){
        return Complaint;
    }
    public void setComplaint(boolean complaint){
        Complaint = complaint;
    }
    public ArrayList<ModelInterForm> getInterForms(){
        return InterForms;
    }
    public void setInterForms(ArrayList<ModelInterForm> interForms){
        InterForms = interForms;
    }
    public String getSchoolClass(){
        return SchoolClass;
    }
    public void setSchoolClass(String schoolClass){
        SchoolClass = schoolClass;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }
}