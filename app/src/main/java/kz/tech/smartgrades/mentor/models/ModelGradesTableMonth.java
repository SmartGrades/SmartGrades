package kz.tech.smartgrades.mentor.models;

import java.util.ArrayList;

public class ModelGradesTableMonth {

    private int Month;
    private double AverageGrade;
    private double Reward;
    private ArrayList<ModelGradesTableDay> GradesTableDay;

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public ArrayList<ModelGradesTableDay> getGradesTableDay() {
        return GradesTableDay;
    }

    public void setGradesTableDay(ArrayList<ModelGradesTableDay> gradesTableDay) {
        GradesTableDay = gradesTableDay;
    }

    public double getAverageGrade() {
        return AverageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        AverageGrade = averageGrade;
    }

    public double getReward() {
        return Reward;
    }

    public void setReward(double reward) {
        Reward = reward;
    }
}