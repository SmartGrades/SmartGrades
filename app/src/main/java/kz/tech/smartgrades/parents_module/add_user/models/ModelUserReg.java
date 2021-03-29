package kz.tech.smartgrades.parents_module.add_user.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelUserReg implements Serializable {
    @Nullable
    @SerializedName("name")
    @Expose
    private String name;
    @Nullable
    @SerializedName("pin")
    @Expose
    private String pin;
    @Nullable
    @SerializedName("status")
    @Expose
    private String status;
    @Nullable
    @SerializedName("data")
    @Expose
    private byte[] data;
    public ModelUserReg(@Nullable String name, @Nullable String pin, @Nullable String status, @Nullable byte[] data) {
        this.name = name;
        this.pin = pin;
        this.status = status;
        this.data = data;
    }

    public ModelUserReg() {}

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getPin() {
        return pin;
    }

    public void setPin(@Nullable String pin) {
        this.pin = pin;
    }

    @Nullable
    public String getStatus() {
        return status;
    }

    public void setStatus(@Nullable String status) {
        this.status = status;
    }

    @Nullable
    public byte[] getData() {
        return data;
    }

    public void setData(@Nullable byte[] data) {
        this.data = data;
    }


}
