package kz.tech.smartgrades.root.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelFamilyRoom implements Serializable {
    @Nullable
    @SerializedName("login")
    @Expose
    private String login;
    @Nullable
    @SerializedName("id")
    @Expose
    private String id;
    public ModelFamilyRoom() {}
    public ModelFamilyRoom(@Nullable String login, @Nullable String id) {
        this.login = login;
        this.id = id;
    }

    @Nullable
    public String getLogin() {
        return login;
    }
    public void setLogin(@Nullable String login) {
        this.login = login;
    }

    @Nullable
    public String getId() {
        return id;
    }
    public void setId(@Nullable String id) {
        this.id = id;
    }

}
