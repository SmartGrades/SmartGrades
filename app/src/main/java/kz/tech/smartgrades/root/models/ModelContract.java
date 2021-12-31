package kz.tech.smartgrades.root.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelContract implements Serializable {

    @Nullable
    @SerializedName("avatar")
    @Expose
    private String avatar;

    @Nullable
    @SerializedName("checkChild")
    @Expose
    private String checkChild;

    @Nullable
    @SerializedName("checkParent")
    @Expose
    private String checkParent;

    @Nullable
    @SerializedName("countSteps")
    @Expose
    private String countSteps;

    @Nullable
    @SerializedName("dateChild")
    @Expose
    private String dateChild;

    @Nullable
    @SerializedName("dateParent")
    @Expose
    private String dateParent;

    @Nullable
    @SerializedName("description")
    @Expose
    private String description;

    @Nullable
    @SerializedName("idChild")
    @Expose
    private String idChild;

    @Nullable
    @SerializedName("idContract")
    @Expose
    private String idContract;

    @Nullable
    @SerializedName("idParent")
    @Expose
    private String idParent;

    @Nullable
    @SerializedName("purposeChild")
    @Expose
    private String purposeChild;

    @Nullable
    @SerializedName("title")
    @Expose
    private String title;




    public ModelContract() {}

    public ModelContract(@Nullable String avatar,  @Nullable String checkChild, @Nullable String checkParent, @Nullable String countSteps, @Nullable String dateChild, @Nullable String dateParent, @Nullable String description, @Nullable String idChild, @Nullable String idContract, @Nullable String idParent, @Nullable String purposeChild, @Nullable String title) {
        this.avatar = avatar;
        this.checkChild = checkChild;
        this.checkParent = checkParent;
        this.countSteps = countSteps;
        this.dateChild = dateChild;
        this.dateParent = dateParent;
        this.description = description;
        this.idChild = idChild;
        this.idContract = idContract;
        this.idParent = idParent;
        this.purposeChild = purposeChild;
        this.title = title;
    }


    @Nullable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(@Nullable String avatar) {
        this.avatar = avatar;
    }

    @Nullable
    public String getCheckChild() {
        return checkChild;
    }

    public void setCheckChild(@Nullable String checkChild) {
        this.checkChild = checkChild;
    }

    @Nullable
    public String getCheckParent() {
        return checkParent;
    }

    public void setCheckParent(@Nullable String checkParent) {
        this.checkParent = checkParent;
    }

    @Nullable
    public String getCountSteps() {
        return countSteps;
    }

    public void setCountSteps(@Nullable String countSteps) {
        this.countSteps = countSteps;
    }

    @Nullable
    public String getDateChild() {
        return dateChild;
    }

    public void setDateChild(@Nullable String dateChild) {
        this.dateChild = dateChild;
    }

    @Nullable
    public String getDateParent() {
        return dateParent;
    }

    public void setDateParent(@Nullable String dateParent) {
        this.dateParent = dateParent;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
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

    @Nullable
    public String getIdParent() {
        return idParent;
    }

    public void setIdParent(@Nullable String idParent) {
        this.idParent = idParent;
    }

    @Nullable
    public String getPurposeChild() {
        return purposeChild;
    }

    public void setPurposeChild(@Nullable String purposeChild) {
        this.purposeChild = purposeChild;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }
}
