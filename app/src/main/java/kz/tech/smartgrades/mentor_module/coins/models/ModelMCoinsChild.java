package kz.tech.smartgrades.mentor_module.coins.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelMCoinsChild {
    //@Nullable
    @SerializedName("avatar")
    @Expose
    private int avatar;
    @Nullable
    @SerializedName("name")
    @Expose
    private String name;
    public ModelMCoinsChild() {}
    public ModelMCoinsChild(int avatar, @Nullable String name) {
        this.avatar = avatar;
        this.name = name;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }
}
