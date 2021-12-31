package kz.tech.smartgrades.child.models;

import java.util.ArrayList;

public class ModelComplaint {
    private String Title;
    private String Message;
    private String SourceId;
    private String TargettId;
    private String ParentTargetId;
    private boolean IsAnonym;
    private String SchoolId;
    private ArrayList<ModelComplaintStages> Stages;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSourceId() {
        return SourceId;
    }

    public void setSourceId(String sourceId) {
        SourceId = sourceId;
    }

    public String getTargettId() {
        return TargettId;
    }

    public void setTargettId(String targettId) {
        TargettId = targettId;
    }

    public String getParentTargetId() {
        return ParentTargetId;
    }

    public void setParentTargetId(String parentTargetId) {
        ParentTargetId = parentTargetId;
    }

    public boolean isAnonym() {
        return IsAnonym;
    }

    public void setAnonym(boolean anonym) {
        IsAnonym = anonym;
    }

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String schoolId) {
        SchoolId = schoolId;
    }

    public ArrayList<ModelComplaintStages> getStages() {
        return Stages;
    }

    public void setStages(ArrayList<ModelComplaintStages> stages) {
        Stages = stages;
    }
}
