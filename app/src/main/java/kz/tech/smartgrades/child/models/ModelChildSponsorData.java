package kz.tech.smartgrades.child.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.parent.model.ModelGrades;
import kz.tech.smartgrades.parent.model.ModelSponsored;

public class ModelChildSponsorData implements Parcelable {

    private String SponsorId;
    private String SponsorFirstName;
    private String SponsorLastName;
    private String SponsorAvatar;

    private int DaysLeft;
    private int GradesLeft;
    private String ThresholdGrade;
    private String AverageGrade;

    private String LessonId1;
    private String LessonId2;
    private String LessonId3;
    private String LessonId4;

    private String ChatId;

    private boolean Done;

    private String DateStartCurrent;
    private String DateEndCurrent;
    private String DateEndTotal;

    private String RewardCurrent;
    private String RewardTotal;

    private ArrayList<ModelGrades> Grades;
    private ArrayList<ModelSponsored> Sponsored;


    protected ModelChildSponsorData(Parcel in) {
        SponsorId = in.readString();
        SponsorFirstName = in.readString();
        SponsorLastName = in.readString();
        SponsorAvatar = in.readString();
        DaysLeft = in.readInt();
        GradesLeft = in.readInt();
        ThresholdGrade = in.readString();
        AverageGrade = in.readString();
        LessonId1 = in.readString();
        LessonId2 = in.readString();
        LessonId3 = in.readString();
        LessonId4 = in.readString();
        ChatId = in.readString();
        Done = in.readByte() != 0;
    }

    public static final Creator<ModelChildSponsorData> CREATOR = new Creator<ModelChildSponsorData>() {
        @Override
        public ModelChildSponsorData createFromParcel(Parcel in) {
            return new ModelChildSponsorData(in);
        }

        @Override
        public ModelChildSponsorData[] newArray(int size) {
            return new ModelChildSponsorData[size];
        }
    };

    public int getDaysLeft() {
        return DaysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        DaysLeft = daysLeft;
    }

    public int getGradesLeft() {
        return GradesLeft;
    }

    public void setGradesLeft(int gradesLeft) {
        GradesLeft = gradesLeft;
    }

    public String getThresholdGrade() {
        return ThresholdGrade;
    }

    public void setThresholdGrade(String thresholdGrade) {
        ThresholdGrade = thresholdGrade;
    }

    public String getSponsorId() {
        return SponsorId;
    }

    public void setSponsorId(String sponsorId) {
        SponsorId = sponsorId;
    }

    public String getSponsorFirstName() {
        return SponsorFirstName;
    }

    public void setSponsorFirstName(String sponsorFirstName) {
        SponsorFirstName = sponsorFirstName;
    }

    public String getSponsorLastName() {
        return SponsorLastName;
    }

    public void setSponsorLastName(String sponsorLastName) {
        SponsorLastName = sponsorLastName;
    }

    public String getSponsorAvatar() {
        return SponsorAvatar;
    }

    public void setSponsorAvatar(String sponsorAvatar) {
        SponsorAvatar = sponsorAvatar;
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

    public ArrayList<ModelGrades> getGrades() {
        return Grades;
    }

    public void setGrades(ArrayList<ModelGrades> grades) {
        Grades = grades;
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

    public boolean isDone() {
        return Done;
    }

    public void setDone(boolean done) {
        Done = done;
    }

    public ArrayList<ModelSponsored> getSponsored() {
        return Sponsored;
    }

    public void setSponsored(ArrayList<ModelSponsored> sponsored) {
        Sponsored = sponsored;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(SponsorId);
        parcel.writeString(SponsorFirstName);
        parcel.writeString(SponsorLastName);
        parcel.writeString(SponsorAvatar);
        parcel.writeInt(DaysLeft);
        parcel.writeInt(GradesLeft);
        parcel.writeString(ThresholdGrade);
        parcel.writeString(AverageGrade);
        parcel.writeString(LessonId1);
        parcel.writeString(LessonId2);
        parcel.writeString(LessonId3);
        parcel.writeString(LessonId4);
        parcel.writeString(ChatId);
        parcel.writeByte((byte) (Done ? 1 : 0));
    }

    public String getDateEndCurrent() {
        return DateEndCurrent;
    }

    public void setDateEndCurrent(String dateEndCurrent) {
        DateEndCurrent = dateEndCurrent;
    }

    public String getDateEndTotal() {
        return DateEndTotal;
    }

    public void setDateEndTotal(String dateEndTotal) {
        DateEndTotal = dateEndTotal;
    }

    public String getRewardCurrent() {
        return RewardCurrent;
    }

    public void setRewardCurrent(String rewardCurrent) {
        RewardCurrent = rewardCurrent;
    }

    public String getRewardTotal() {
        return RewardTotal;
    }

    public void setRewardTotal(String rewardTotal) {
        RewardTotal = rewardTotal;
    }

    public String getDateStartCurrent() {
        return DateStartCurrent;
    }

    public void setDateStartCurrent(String dateStartCurrent) {
        DateStartCurrent = dateStartCurrent;
    }
}