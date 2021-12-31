package kz.tech.smartgrades.child.models;

import java.util.ArrayList;

public class ModelComplaintStages {
    private String Date;
    private ArrayList<ModelComplaintMembers> Members;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public ArrayList<ModelComplaintMembers> getMembers() {
        return Members;
    }

    public void setMembers(ArrayList<ModelComplaintMembers> members) {
        Members = members;
    }
}
