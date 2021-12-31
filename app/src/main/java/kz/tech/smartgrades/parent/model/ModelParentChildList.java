package kz.tech.smartgrades.parent.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.child.models.ModelChildSponsorData;

public class ModelParentChildList implements Parcelable{

    private String ChildId;
    private String ChildAvatar;
    private String ChildFirstName;
    private String ChildLastName;
    private String ChildLogin;

    private String AverageGrade;
    private String CurentCash;
    private String ChatId;

    private ModelChildSponsorData SponsorData;
    private ArrayList<ModelSponsoredNotification> SponsoredNotifications;
    private ArrayList<ModelParentsLessons> ParentsLessons;
    private ArrayList<kz.tech.smartgrades.child.models.ModelChildMentorList> MentorList;
    private ArrayList<ModelLessonWithSettings> ModelLessonWithSettings;
    private ArrayList<ModelLessonWithOutSettings> ModelLessonWithOutSettings;


    protected ModelParentChildList(Parcel in) {
        ChildId = in.readString();
        ChildAvatar = in.readString();
        ChildFirstName = in.readString();
        ChildLastName = in.readString();
        ChildLogin = in.readString();
        AverageGrade = in.readString();
        CurentCash = in.readString();
        ChatId = in.readString();
        SponsorData = in.readParcelable(ModelChildSponsorData.class.getClassLoader());
        MentorList = in.createTypedArrayList(kz.tech.smartgrades.child.models.ModelChildMentorList.CREATOR);
    }

    public static final Creator<ModelParentChildList> CREATOR = new Creator<ModelParentChildList>() {
        @Override
        public ModelParentChildList createFromParcel(Parcel in) {
            return new ModelParentChildList(in);
        }

        @Override
        public ModelParentChildList[] newArray(int size) {
            return new ModelParentChildList[size];
        }
    };

    public String getChildId() {
        return ChildId;
    }

    public void setChildId(String childId) {
        ChildId = childId;
    }

    public String getChildAvatar() {
        return ChildAvatar;
    }

    public void setChildAvatar(String childAvatar) {
        ChildAvatar = childAvatar;
    }

    public String getChildFirstName() {
        return ChildFirstName;
    }

    public void setChildFirstName(String childFirstName) {
        ChildFirstName = childFirstName;
    }

    public String getChildLastName() {
        return ChildLastName;
    }

    public void setChildLastName(String childLastName) {
        ChildLastName = childLastName;
    }

    public String getChildLogin() {
        return ChildLogin;
    }

    public void setChildLogin(String childLogin) {
        ChildLogin = childLogin;
    }

    public String getAverageGrade() {
        return AverageGrade;
    }

    public void setAverageGrade(String averageGrade) {
        AverageGrade = averageGrade;
    }

    public ModelChildSponsorData getSponsorData() {
        return SponsorData;
    }

    public void setSponsorData(ModelChildSponsorData sponsorData) {
        SponsorData = sponsorData;
    }

    public ArrayList<kz.tech.smartgrades.child.models.ModelChildMentorList> getMentorList() {
        return MentorList;
    }

    public void setMentorList(ArrayList<kz.tech.smartgrades.child.models.ModelChildMentorList> mentorList) {
        MentorList = mentorList;
    }

    public String getCurentCash() {
        return CurentCash;
    }

    public void setCurentCash(String curentCash) {
        CurentCash = curentCash;
    }

    public String getChatId() {
        return ChatId;
    }

    public void setChatId(String chatId) {
        ChatId = chatId;
    }

    public ArrayList<ModelSponsoredNotification> getSponsoredNotifications() {
        return SponsoredNotifications;
    }

    public void setSponsoredNotifications(ArrayList<ModelSponsoredNotification> sponsoredNotifications) {
        SponsoredNotifications = sponsoredNotifications;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ChildId);
        parcel.writeString(ChildAvatar);
        parcel.writeString(ChildFirstName);
        parcel.writeString(ChildLastName);
        parcel.writeString(ChildLogin);
        parcel.writeString(AverageGrade);
        parcel.writeString(CurentCash);
        parcel.writeString(ChatId);
        parcel.writeParcelable(SponsorData, i);
        parcel.writeTypedList(MentorList);
    }

    public ArrayList<ModelParentsLessons> getParentsLessons() {
        return ParentsLessons;
    }

    public void setParentsLessons(ArrayList<ModelParentsLessons> parentsLessons) {
        ParentsLessons = parentsLessons;
    }

    public ArrayList<kz.tech.smartgrades.parent.model.ModelLessonWithSettings> getModelLessonWithSettings() {
        return ModelLessonWithSettings;
    }

    public void setModelLessonWithSettings(ArrayList<kz.tech.smartgrades.parent.model.ModelLessonWithSettings> modelLessonWithSettings) {
        ModelLessonWithSettings = modelLessonWithSettings;
    }

    public ArrayList<kz.tech.smartgrades.parent.model.ModelLessonWithOutSettings> getModelLessonWithOutSettings() {
        return ModelLessonWithOutSettings;
    }

    public void setModelLessonWithOutSettings(ArrayList<kz.tech.smartgrades.parent.model.ModelLessonWithOutSettings> modelLessonWithOutSettings) {
        ModelLessonWithOutSettings = modelLessonWithOutSettings;
    }
}
