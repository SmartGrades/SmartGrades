package kz.tech.smartgrades.parent.model;

import java.util.ArrayList;

public class ModelSponsorship {
    private String Id;
    private String InterFormId;

    private String SponsorId;
    private String SponsorAvatar;
    private String SponsorFirstName;
    private String SponsorLastName;

    private String ChildId;
    private String ChildAvatar;
    private String ChildFirstName;
    private String ChildLastName;

    private int CurrentWeek;
    private int TotalWeek;

    private int CurrentDay;
    private int TotalDays;
    private String DateStart;
    private String DataEnd;

    private int WeekReward;
    private int TotalReward;
    private String CurrentTotalReward;

    private double AverageGrade;
    private double CurrentAverageGrade;
    private int CountGrade;
    private int CurrentCountGrade;

    private int Progress;

    private ArrayList<ModelChildPeriods> Periods;
    private ArrayList<ModelSponsoredLesson> Lessons;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getInterFormId() {
        return InterFormId;
    }

    public void setInterFormId(String interFormId) {
        InterFormId = interFormId;
    }

    public String getSponsorId() {
        return SponsorId;
    }

    public void setSponsorId(String sponsorId) {
        SponsorId = sponsorId;
    }

    public String getSponsorAvatar() {
        return SponsorAvatar;
    }

    public void setSponsorAvatar(String sponsorAvatar) {
        SponsorAvatar = sponsorAvatar;
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

    public int getCurrentWeek() {
        return CurrentWeek;
    }

    public void setCurrentWeek(int currentWeek) {
        CurrentWeek = currentWeek;
    }

    public int getTotalWeek() {
        return TotalWeek;
    }

    public void setTotalWeek(int totalWeek) {
        TotalWeek = totalWeek;
    }

    public int getCurrentDay() {
        return CurrentDay;
    }

    public void setCurrentDay(int currentDay) {
        CurrentDay = currentDay;
    }

    public int getTotalDays() {
        return TotalDays;
    }

    public void setTotalDays(int totalDays) {
        TotalDays = totalDays;
    }

    public String getDateStart() {
        return DateStart;
    }

    public void setDateStart(String dateStart) {
        DateStart = dateStart;
    }

    public String getDataEnd() {
        return DataEnd;
    }

    public void setDataEnd(String dataEnd) {
        DataEnd = dataEnd;
    }

    public int getWeekReward() {
        return WeekReward;
    }

    public void setWeekReward(int weekReward) {
        WeekReward = weekReward;
    }

    public int getTotalReward() {
        return TotalReward;
    }

    public void setTotalReward(int totalReward) {
        TotalReward = totalReward;
    }

    public String getCurrentTotalReward() {
        return CurrentTotalReward;
    }

    public void setCurrentTotalReward(String currentTotalReward) {
        CurrentTotalReward = currentTotalReward;
    }

    public double getAverageGrade() {
        return AverageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        AverageGrade = averageGrade;
    }

    public double getCurrentAverageGrade() {
        return CurrentAverageGrade;
    }

    public void setCurrentAverageGrade(double currentAverageGrade) {
        CurrentAverageGrade = currentAverageGrade;
    }

    public int getCountGrade() {
        return CountGrade;
    }

    public void setCountGrade(int countGrade) {
        CountGrade = countGrade;
    }

    public int getCurrentCountGrade() {
        return CurrentCountGrade;
    }

    public void setCurrentCountGrade(int currentCountGrade) {
        CurrentCountGrade = currentCountGrade;
    }

    public int getProgress() {
        return Progress;
    }

    public void setProgress(int progress) {
        Progress = progress;
    }

    public ArrayList<ModelChildPeriods> getPeriods() {
        return Periods;
    }

    public void setPeriods(ArrayList<ModelChildPeriods> periods) {
        Periods = periods;
    }

    public ArrayList<ModelSponsoredLesson> getLessons() {
        return Lessons;
    }

    public void setLessons(ArrayList<ModelSponsoredLesson> lessons) {
        Lessons = lessons;
    }
}
