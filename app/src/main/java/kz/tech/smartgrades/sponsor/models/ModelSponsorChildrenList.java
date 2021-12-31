package kz.tech.smartgrades.sponsor.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.parent.model.ModelSponsorship;
import kz.tech.smartgrades.parent.model.ModelGrades;

public class ModelSponsorChildrenList implements Parcelable {

    private String UserId;
    private String Avatar;
    private String FirstName;
    private String LastName;

    private String LessonId1;
    private String LessonId2;
    private String LessonId3;
    private String LessonId4;

    private String ChatId;
    private String ThresholdGrade;
    private String GradesLeft;
    private String DaysLeft;
    private String AverageGrade;

    private ArrayList<ModelGrades> Grades;

    private ModelSponsorship Sponsorship;


    protected ModelSponsorChildrenList(Parcel in) {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelSponsorChildrenList> CREATOR = new Creator<ModelSponsorChildrenList>() {
        @Override
        public ModelSponsorChildrenList createFromParcel(Parcel in) {
            return new ModelSponsorChildrenList(in);
        }

        @Override
        public ModelSponsorChildrenList[] newArray(int size) {
            return new ModelSponsorChildrenList[size];
        }
    };


    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public ArrayList<ModelGrades> getGrades() {
        return Grades;
    }

    public void setGrades(ArrayList<ModelGrades> grades) {
        Grades = grades;
    }

    public String getLessonId1() {
        return LessonId1;
    }

    public void setLessonId1(String lessonId1) {
        LessonId1 = lessonId1;
    }

    public String getLessonId2() {
        return LessonId2;
    }

    public void setLessonId2(String lessonId2) {
        LessonId2 = lessonId2;
    }

    public String getLessonId3() {
        return LessonId3;
    }

    public void setLessonId3(String lessonId3) {
        LessonId3 = lessonId3;
    }

    public String getLessonId4() {
        return LessonId4;
    }

    public void setLessonId4(String lessonId4) {
        LessonId4 = lessonId4;
    }

    public String getThresholdGrade() {
        return ThresholdGrade;
    }

    public void setThresholdGrade(String thresholdGrade) {
        ThresholdGrade = thresholdGrade;
    }

    public String getGradesLeft() {
        return GradesLeft;
    }

    public void setGradesLeft(String gradesLeft) {
        GradesLeft = gradesLeft;
    }

    public String getDaysLeft() {
        return DaysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        DaysLeft = daysLeft;
    }

    public String getAverageGrade() {
        return AverageGrade;
    }

    public void setAverageGrade(String averageGrade) {
        AverageGrade = averageGrade;
    }

    public String getChatId() {
        return ChatId;
    }

    public void setChatId(String chatId) {
        ChatId = chatId;
    }

    public ModelSponsorship getSponsorship() {
        return Sponsorship;
    }

    public void setSponsorship(ModelSponsorship sponsorship) {
        Sponsorship = sponsorship;
    }
}