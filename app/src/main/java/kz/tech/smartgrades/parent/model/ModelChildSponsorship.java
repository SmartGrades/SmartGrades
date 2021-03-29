package kz.tech.smartgrades.parent.model;

import java.util.ArrayList;

public class ModelChildSponsorship {
    private String Id;
    private String SponsorAvatar;
    private String SponsorFirstName;
    private String SponsorLastName;
    private int CurrentWeek;
    private int TotalWeek;
    private String DateStart;
    private String DataEnd;
    private int WeekReward;
    private int TotalReward;
    private double AverageGrade;
    private double CurrentAverageGrade;
    private int CountGrade;
    private int CurrentCountGrade;
    private int Progress;
    private String CurrentTotalReward;
    private String TotalDays;
    private String CurrentDay;

    private ArrayList<ModelChildPeriods> Periods;
    private ArrayList<ModelSponsoredLesson> Lessons;

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

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public int getProgress() {
        return Progress;
    }

    public void setProgress(int progress) {
        Progress = progress;
    }

    public String getCurrentTotalReward() {
        return CurrentTotalReward;
    }

    public void setCurrentTotalReward(String currentTotalReward) {
        CurrentTotalReward = currentTotalReward;
    }

    public String getTotalDays() {
        return TotalDays;
    }

    public void setTotalDays(String totalDays) {
        TotalDays = totalDays;
    }

    public String getCurrentDay() {
        return CurrentDay;
    }

    public void setCurrentDay(String currentDay) {
        CurrentDay = currentDay;
    }
}
