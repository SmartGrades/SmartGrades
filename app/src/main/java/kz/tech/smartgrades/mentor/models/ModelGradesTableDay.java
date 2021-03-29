package kz.tech.smartgrades.mentor.models;

import java.util.ArrayList;

import kz.tech.smartgrades.parent.model.ModelGrades;

public class ModelGradesTableDay {

    private int Day;
    private int Reward;
    private ArrayList<ModelGrades> Grades;

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public int getReward() {
        return Reward;
    }

    public void setReward(int reward) {
        Reward = reward;
    }

    public ArrayList<ModelGrades> getGrades() {
        return Grades;
    }

    public void setGrades(ArrayList<ModelGrades> grades) {
        Grades = grades;
    }
}