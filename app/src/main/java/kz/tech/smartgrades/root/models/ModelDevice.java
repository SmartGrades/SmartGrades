package kz.tech.smartgrades.root.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDevice {
    @Nullable
    @SerializedName("idDevice")
    @Expose
    private String idDevice;

    @Nullable
    @SerializedName("idUser")
    @Expose
    private String idUser;

    @Nullable
    @SerializedName("lockStatus")
    @Expose
    private String lockStatus;

    @Nullable
    @SerializedName("nameDevice")
    @Expose
    private String nameDevice;

    @Nullable
    @SerializedName("onlineStatus")
    @Expose
    private String onlineStatus;

    @Nullable
    @SerializedName("typeDevice")
    @Expose
    private String typeDevice;

    public ModelDevice() {}

    public ModelDevice(@Nullable String idDevice, @Nullable String idUser, @Nullable String lockStatus,
                       @Nullable String nameDevice, @Nullable String onlineStatus, @Nullable String typeDevice) {
        this.idDevice = idDevice;
        this.idUser = idUser;
        this.lockStatus = lockStatus;
        this.nameDevice = nameDevice;
        this.onlineStatus = onlineStatus;
        this.typeDevice = typeDevice;
    }

    @Nullable
    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(@Nullable String idDevice) {
        this.idDevice = idDevice;
    }

    @Nullable
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(@Nullable String idUser) {
        this.idUser = idUser;
    }

    @Nullable
    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(@Nullable String lockStatus) {
        this.lockStatus = lockStatus;
    }

    @Nullable
    public String getNameDevice() {
        return nameDevice;
    }

    public void setNameDevice(@Nullable String nameDevice) {
        this.nameDevice = nameDevice;
    }

    @Nullable
    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(@Nullable String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    @Nullable
    public String getTypeDevice() {
        return typeDevice;
    }

    public void setTypeDevice(@Nullable String typeDevice) {
        this.typeDevice = typeDevice;
    }
}
