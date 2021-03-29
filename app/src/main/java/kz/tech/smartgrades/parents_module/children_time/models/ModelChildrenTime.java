package kz.tech.smartgrades.parents_module.children_time.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelChildrenTime {
    @Nullable
    @SerializedName("aMonday")
    @Expose
    private String aMonday;
    @Nullable
    @SerializedName("bTuesday")
    @Expose
    private String bTuesday;
    @Nullable
    @SerializedName("cWednesday")
    @Expose
    private String cWednesday;
    @Nullable
    @SerializedName("dThursday")
    @Expose
    private String dThursday;
    @Nullable
    @SerializedName("eFriday")
    @Expose
    private String eFriday;
    @Nullable
    @SerializedName("fSaturday")
    @Expose
    private String fSaturday;
    @Nullable
    @SerializedName("gSunday")
    @Expose
    private String gSunday;
    @Nullable
    @SerializedName("zSwitch")
    @Expose
    private String zSwitch;

    public ModelChildrenTime() {}
    public ModelChildrenTime(@Nullable String aMonday, @Nullable String bTuesday, @Nullable String cWednesday,
                             @Nullable String dThursday, @Nullable String eFriday, @Nullable String fSaturday,
                             @Nullable String gSunday, @Nullable String zSwitch) {
        this.aMonday = aMonday;
        this.bTuesday = bTuesday;
        this.cWednesday = cWednesday;
        this.dThursday = dThursday;
        this.eFriday = eFriday;
        this.fSaturday = fSaturday;
        this.gSunday = gSunday;
        this.zSwitch = zSwitch;
    }

    @Nullable
    public String getaMonday() {
        return aMonday;
    }

    public void setaMonday(@Nullable String aMonday) {
        this.aMonday = aMonday;
    }

    @Nullable
    public String getbTuesday() {
        return bTuesday;
    }

    public void setbTuesday(@Nullable String bTuesday) {
        this.bTuesday = bTuesday;
    }

    @Nullable
    public String getcWednesday() {
        return cWednesday;
    }

    public void setcWednesday(@Nullable String cWednesday) {
        this.cWednesday = cWednesday;
    }

    @Nullable
    public String getdThursday() {
        return dThursday;
    }

    public void setdThursday(@Nullable String dThursday) {
        this.dThursday = dThursday;
    }

    @Nullable
    public String geteFriday() {
        return eFriday;
    }

    public void seteFriday(@Nullable String eFriday) {
        this.eFriday = eFriday;
    }

    @Nullable
    public String getfSaturday() {
        return fSaturday;
    }

    public void setfSaturday(@Nullable String fSaturday) {
        this.fSaturday = fSaturday;
    }

    @Nullable
    public String getgSunday() {
        return gSunday;
    }

    public void setgSunday(@Nullable String gSunday) {
        this.gSunday = gSunday;
    }

    @Nullable
    public String getzSwitch() {
        return zSwitch;
    }

    public void setzSwitch(@Nullable String zSwitch) {
        this.zSwitch = zSwitch;
    }
}
