package kz.tech.smartgrades.sponsor.models;

import java.util.ArrayList;

import kz.tech.smartgrades.mentor.models.ModelMentorChat;

public class ModelSponsorGroups {

    private String GroupId;
    private String GroupName;
    private String LessonId;
    private ArrayList<ModelMentorChat> ChildrenList;



    public ModelSponsorGroups() {

    }

    public ModelSponsorGroups(ArrayList<ModelMentorChat> childList, String idGroup, String name) {
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getLessonId() {
        return LessonId;
    }

    public void setLessonId(String lessonId) {
        LessonId = lessonId;
    }

    public ArrayList<ModelMentorChat> getChildrenList() {
        return ChildrenList;
    }

    public void setChildrenList(ArrayList<ModelMentorChat> childrenList) {
        ChildrenList = childrenList;
    }
}
