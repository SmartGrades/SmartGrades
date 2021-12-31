package kz.tech.smartgrades.root.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelPasswordEdit{

    private String UserId;
    private String OldPassword;
    private String NewPassword;

    public String getUserId(){
        return UserId;
    }
    public void setUserId(String userId){
        UserId = userId;
    }
    public String getOldPassword(){
        return OldPassword;
    }
    public void setOldPassword(String oldPassword){
        OldPassword = oldPassword;
    }
    public String getNewPassword(){
        return NewPassword;
    }
    public void setNewPassword(String newPassword){
        NewPassword = newPassword;
    }
}
