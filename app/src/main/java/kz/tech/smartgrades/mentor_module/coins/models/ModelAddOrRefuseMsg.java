package kz.tech.smartgrades.mentor_module.coins.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import kz.tech.smartgrades.parents_module.family_group.dialogs_page.interaction_form.ModelChildArray;

public class ModelAddOrRefuseMsg {
    @Nullable
    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("childList")
    @Expose
    private List<ModelChildArray> childList;

    @Nullable
    @SerializedName("date")
    @Expose
    private String date;

    @Nullable
    @SerializedName("msg")
    @Expose
    private String msg;

    @Nullable
    @SerializedName("name")
    @Expose
    private String name;

    public ModelAddOrRefuseMsg() {}

    public ModelAddOrRefuseMsg(@Nullable String avatar, List<ModelChildArray> childList,
                               @Nullable String date, @Nullable String msg, @Nullable String name) {
        this.avatar = avatar;
        this.childList = childList;
        this.date = date;
        this.msg = msg;
        this.name = name;
    }

    @Nullable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(@Nullable String avatar) {
        this.avatar = avatar;
    }

    public List<ModelChildArray> getChildList() {
        return childList;
    }

    public void setChildList(List<ModelChildArray> childList) {
        this.childList = childList;
    }

    @Nullable
    public String getDate() {
        return date;
    }

    public void setDate(@Nullable String date) {
        this.date = date;
    }

    @Nullable
    public String getMsg() {
        return msg;
    }

    public void setMsg(@Nullable String msg) {
        this.msg = msg;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }
}
