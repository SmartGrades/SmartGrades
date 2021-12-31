package kz.tech.smartgrades.mentor.models;

import java.util.ArrayList;

public class ModelAddMentorGroup {


    private ArrayList<ModelMentorChat> childList;

    private String idGroup;

    private String name;



    public ModelAddMentorGroup() {

    }

    public ModelAddMentorGroup(ArrayList<ModelMentorChat> childList, String idGroup, String name) {
        this.childList = childList;
        this.idGroup = idGroup;
        this.name = name;
    }


    public ArrayList<ModelMentorChat> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<ModelMentorChat> childList) {
        this.childList = childList;
    }

    public String getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(String idGroup) {
        this.idGroup = idGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
