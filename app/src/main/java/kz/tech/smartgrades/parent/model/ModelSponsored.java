package kz.tech.smartgrades.parent.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ModelSponsored {

    private String Index;
    private String InterFormId;
    private String Week;
    private boolean Done;
    private boolean CondAreMet;
    private boolean Cheked;
    private String UserType;

    public String getIndex() {
        return Index;
    }

    public void setIndex(String index) {
        Index = index;
    }

    public String getInterFormId() {
        return InterFormId;
    }

    public void setInterFormId(String interFormId) {
        InterFormId = interFormId;
    }

    public String getWeek() {
        return Week;
    }

    public void setWeek(String week) {
        Week = week;
    }


    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public boolean isCheked() {
        return Cheked;
    }

    public void setCheked(boolean cheked) {
        Cheked = cheked;
    }

    public boolean isCondAreMet() {
        return CondAreMet;
    }

    public void setCondAreMet(boolean condAreMet) {
        CondAreMet = condAreMet;
    }

    public boolean isDone() {
        return Done;
    }

    public void setDone(boolean done) {
        Done = done;
    }
}
