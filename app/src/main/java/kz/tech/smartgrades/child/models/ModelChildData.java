package kz.tech.smartgrades.child.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.parent.model.ModelSponsorship;
import kz.tech.smartgrades.parent.model.ModelLessonsWithOutSmartGrades;
import kz.tech.smartgrades.parent.model.ModelLessonsWithSmartGrades;
import kz.tech.smartgrades.parent.model.ModelSponsoredNotification;
import kz.tech.smartgrades.sponsor.models.ModelPrivateAccount;

public class ModelChildData implements Parcelable{

    private String FamilyGroupId;
    private ModelPrivateAccount PrivateAccount;
    public ArrayList<ModelInterForm> InterForms;
    private ModelSponsorship Sponsorship;
    private ArrayList<ModelLessonsWithSmartGrades> LessonsWithSmartGrades;
    private ArrayList<ModelLessonsWithOutSmartGrades> LessonsWithoutSmartGrades;
    private ArrayList<ModelSponsoredNotification> SponsoredNotifications;

    private String Id;
    private String Avatar;
    private String FirstName;
    private String LastName;
    private String Login;

    public ModelChildData(){}

    protected ModelChildData(Parcel in){
        FamilyGroupId = in.readString();
        Sponsorship = in.readParcelable(ModelSponsorship.class.getClassLoader());
        Id = in.readString();
        Avatar = in.readString();
        FirstName = in.readString();
        LastName = in.readString();
        Login = in.readString();
    }
    public static final Creator<ModelChildData> CREATOR = new Creator<ModelChildData>(){
        @Override
        public ModelChildData createFromParcel(Parcel in){
            return new ModelChildData(in);
        }

        @Override
        public ModelChildData[] newArray(int size){
            return new ModelChildData[size];
        }
    };
    public String getFamilyGroupId(){
        return FamilyGroupId;
    }
    public void setFamilyGroupId(String familyGroupId){
        FamilyGroupId = familyGroupId;
    }
    public ModelPrivateAccount getPrivateAccount(){
        return PrivateAccount;
    }
    public void setPrivateAccount(ModelPrivateAccount privateAccount){
        PrivateAccount = privateAccount;
    }
    public ArrayList<ModelInterForm> getInterForms(){
        return InterForms;
    }
    public void setInterForms(ArrayList<ModelInterForm> interForms){
        InterForms = interForms;
    }
    public ModelSponsorship getSponsorship(){
        return Sponsorship;
    }
    public void setSponsorship(ModelSponsorship sponsorship){
        Sponsorship = sponsorship;
    }
    public ArrayList<ModelLessonsWithSmartGrades> getLessonsWithSmartGrades(){
        return LessonsWithSmartGrades;
    }
    public void setLessonsWithSmartGrades(ArrayList<ModelLessonsWithSmartGrades> lessonsWithSmartGrades){
        LessonsWithSmartGrades = lessonsWithSmartGrades;
    }
    public ArrayList<ModelLessonsWithOutSmartGrades> getLessonsWithoutSmartGrades(){
        return LessonsWithoutSmartGrades;
    }
    public void setLessonsWithoutSmartGrades(ArrayList<ModelLessonsWithOutSmartGrades> lessonsWithoutSmartGrades){
        LessonsWithoutSmartGrades = lessonsWithoutSmartGrades;
    }
    public ArrayList<ModelSponsoredNotification> getSponsoredNotifications(){
        return SponsoredNotifications;
    }
    public void setSponsoredNotifications(ArrayList<ModelSponsoredNotification> sponsoredNotifications){
        SponsoredNotifications = sponsoredNotifications;
    }
    public String getId(){
        return Id;
    }
    public void setId(String id){
        Id = id;
    }
    public String getAvatar(){
        return Avatar;
    }
    public void setAvatar(String avatar){
        Avatar = avatar;
    }
    public String getFirstName(){
        return FirstName;
    }
    public void setFirstName(String firstName){
        FirstName = firstName;
    }
    public String getLastName(){
        return LastName;
    }
    public void setLastName(String lastName){
        LastName = lastName;
    }
    public String getLogin(){
        return Login;
    }
    public void setLogin(String login){
        Login = login;
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(FamilyGroupId);
        parcel.writeParcelable((Parcelable) Sponsorship, i);
        parcel.writeString(Id);
        parcel.writeString(Avatar);
        parcel.writeString(FirstName);
        parcel.writeString(LastName);
        parcel.writeString(Login);
    }
}