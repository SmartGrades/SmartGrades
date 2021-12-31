package kz.tech.smartgrades.childs_module;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelChildContracts {
    @Nullable
    @SerializedName("avatar")
    @Expose
    private String avatar;

    @Nullable
    @SerializedName("countSteps")
    @Expose
    private String countSteps;

    @Nullable
    @SerializedName("description")
    @Expose
    private String description;

    public ModelChildContracts() {}

    public ModelChildContracts(@Nullable String avatar, @Nullable String countSteps, @Nullable String description) {
        this.avatar = avatar;
        this.countSteps = countSteps;
        this.description = description;
    }

    @Nullable
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(@Nullable String avatar) {
        this.avatar = avatar;
    }

    @Nullable
    public String getCountSteps() {
        return countSteps;
    }
    public void setCountSteps(@Nullable String countSteps) {
        this.countSteps = countSteps;
    }

    @Nullable
    public String getDescription() {
        return description;
    }
    public void setDescription(@Nullable String description) {
        this.description = description;
    }
}
