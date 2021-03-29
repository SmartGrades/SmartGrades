package kz.tech.smartgrades.authentication.fragments.registration_mentor.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelMentor {
    @Nullable
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @Nullable
    @SerializedName("id")
    @Expose
    private String id;
    @Nullable
    @SerializedName("login")
    @Expose
    private String login;
    @Nullable
    @SerializedName("name")
    @Expose
    private String name;
    public ModelMentor() {}
    public ModelMentor(@Nullable String avatar, @Nullable String id, @Nullable String login, @Nullable String name) {
        this.avatar = avatar;
        this.id = id;
        this.login = login;
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
    public String getLogin() {
        return login;
    }
    public void setLogin(@Nullable String login) {
        this.login = login;
    }
    @Nullable
    public String getName() {
        return name;
    }
    public void setName(@Nullable String name) {
        this.name = name;
    }
}
