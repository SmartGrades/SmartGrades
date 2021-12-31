package kz.tech.smartgrades.parents_module.family_group.dialogs_page.interaction_form;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelChildArray {
    @Nullable
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @Nullable
    @SerializedName("id")
    @Expose
    private String id;
    @Nullable
    @SerializedName("name")
    @Expose
    private String name;

    public ModelChildArray() {}

    public ModelChildArray(@Nullable String avatar, @Nullable String id, @Nullable String name) {
        this.avatar = avatar;
        this.id = id;
        this.name = name;
    }

    @Nullable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(@Nullable String avatar) {
        this.avatar = avatar;
    }

    @Nullable
    public String getId() {
        return id;
    }

    public void setId(@Nullable String id) {
        this.id = id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }
}
