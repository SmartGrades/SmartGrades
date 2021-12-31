package kz.tech.smartgrades.parents_module.family_group.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelFamilyMembers {
    @Nullable
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @Nullable
    @SerializedName("name")
    @Expose
    private String name;
    @Nullable
    @SerializedName("status")
    @Expose
    private String status;
    public ModelFamilyMembers() {}
    public ModelFamilyMembers(@Nullable String avatar, @Nullable String name, @Nullable String status) {
        this.avatar = avatar;
        this.name = name;
        this.status = status;
    }
    @Nullable
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(@Nullable String avatar) {
        this.avatar = avatar;
    }
    @Nullable
    public String getName() {
        return name;
    }
    public void setName(@Nullable String name) {
        this.name = name;
    }
    @Nullable
    public String getStatus() {
        return status;
    }
    public void setStatus(@Nullable String status) {
        this.status = status;
    }
}
