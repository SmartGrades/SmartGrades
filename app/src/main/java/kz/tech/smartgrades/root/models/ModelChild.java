package kz.tech.smartgrades.root.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelChild {
    @Nullable
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @Nullable
    @SerializedName("name")
    @Expose
    private String name;
    @Nullable
    @SerializedName("idChild")
    @Expose
    private String idChild;
    public ModelChild() {}
    public ModelChild(@Nullable String avatar, @Nullable String name, @Nullable String idChild) {
        this.avatar = avatar;
        this.name = name;
        this.idChild = idChild;
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
    public String getIdChild() {
        return idChild;
    }
    public void setIdChild(@Nullable String idChild) {
        this.idChild = idChild;
    }
}
