package kz.tech.smartgrades.parent.model;

import java.util.ArrayList;
import java.util.List;

import kz.tech.smartgrades.mentor.models.ModelGradesTableDay;

public class ModelGradesTable {
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

    public List<ModelGradesTableDay> getGradesTableDay() {
        return GradesTableDay;
    }

    public void setGradesTableDay(ArrayList<ModelGradesTableDay> gradesTableDay) {
        GradesTableDay = gradesTableDay;
    }
}
