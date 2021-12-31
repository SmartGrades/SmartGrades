package kz.tech.smartgrades.parents_module.coins.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelChangeStep {
    @Nullable
    @SerializedName("countSteps")
    @Expose
    private String countSteps;

    @Nullable
    @SerializedName("idChild")
    @Expose
    private String idChild;

    @Nullable
    @SerializedName("idContract")
    @Expose
    private String idContract;


    @SerializedName("step")
    @Expose
    private int step;

    public ModelChangeStep() {}

    public ModelChangeStep(@Nullable String countSteps, @Nullable String idChild, @Nullable String idContract, int step) {
        this.countSteps = countSteps;
        this.idChild = idChild;
        this.idContract = idContract;
        this.step = step;
    }


    @Nullable
    public String getCountSteps() {
        return countSteps;
    }

    public void setCountSteps(@Nullable String countSteps) {
        this.countSteps = countSteps;
    }

    @Nullable
    public String getIdChild() {
        return idChild;
    }

    public void setIdChild(@Nullable String idChild) {
        this.idChild = idChild;
    }

    @Nullable
    public String getIdContract() {
        return idContract;
    }

    public void setIdContract(@Nullable String idContract) {
        this.idContract = idContract;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
