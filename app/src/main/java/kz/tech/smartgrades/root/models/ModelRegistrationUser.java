package kz.tech.smartgrades.root.models;

import androidx.annotation.Nullable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelRegistrationUser implements Serializable {
    @Nullable
    @SerializedName("id")
    @Expose
    private String id;
    @Nullable
    @SerializedName("mail")
    @Expose
    private String mail;
    @Nullable
    @SerializedName("password")
    @Expose
    private String password;
    @Nullable
    @SerializedName("typeAccount")
    @Expose
    private String typeAccount;
    public ModelRegistrationUser() {}
    public ModelRegistrationUser(@Nullable String id, @Nullable String mail, @Nullable String password, @Nullable String typeAccount) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.typeAccount = typeAccount;
    }

    @Nullable
    public String getId() {
        return id;
    }

    public void setId(@Nullable String id) {
        this.id = id;
    }

    @Nullable
    public String getMail() {
        return mail;
    }

    public void setMail(@Nullable String mail) {
        this.mail = mail;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }

    @Nullable
    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(@Nullable String typeAccount) {
        this.typeAccount = typeAccount;
    }
}
