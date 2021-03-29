package kz.tech.smartgrades.parents_module.cabinet.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelChatListChild {


    @SerializedName("type")
    @Expose
    private int type;

    @Nullable
    @SerializedName("name")
    @Expose
    private String name;

    public ModelChatListChild() {}


    public ModelChatListChild(int type, @Nullable String name) {
        this.type = type;
        this.name = name;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }
}
