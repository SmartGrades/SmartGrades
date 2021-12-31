package kz.tech.smartgrades.mentor_module.coins.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import kz.tech.smartgrades.parents_module.family_group.dialogs_page.interaction_form.ModelChildArray;

public class ModelMentorPushList {

    @Nullable
    @SerializedName("accrualOfCoins")
    @Expose
    private String accrualOfCoins;

    @Nullable
    @SerializedName("accrualOfCoinsSuper")
    @Expose
    private String accrualOfCoinsSuper;

    @Nullable
    @SerializedName("accrualOfOffset")
    @Expose
    private String accrualOfOffset;

    @Nullable
    @SerializedName("accrualOfOffsetSuper")
    @Expose
    private String accrualOfOffsetSuper;

    @Nullable
    @SerializedName("avatar")
    @Expose
    private String avatar;

    @Nullable
    @SerializedName("childList")
    @Expose
    private List<ModelChildArray> childList;

    @Nullable
    @SerializedName("date")
    @Expose
    private String date;

    @Nullable
    @SerializedName("idLogin")
    @Expose
    private String idLogin;

    @Nullable
    @SerializedName("msg")
    @Expose
    private String msg;

    @Nullable
    @SerializedName("name")
    @Expose
    private String name;

    @Nullable
    @SerializedName("type")
    @Expose
    private String type;


    public ModelMentorPushList() {}

    public ModelMentorPushList(@Nullable String accrualOfCoins, @Nullable String accrualOfCoinsSuper,
                               @Nullable String accrualOfOffset, @Nullable String accrualOfOffsetSuper,
                               @Nullable String avatar, @Nullable List<ModelChildArray> childList,
                               @Nullable String date, @Nullable String idLogin, @Nullable String msg,
                               @Nullable String name, @Nullable String type) {
        this.accrualOfCoins = accrualOfCoins;
        this.accrualOfCoinsSuper = accrualOfCoinsSuper;
        this.accrualOfOffset = accrualOfOffset;
        this.accrualOfOffsetSuper = accrualOfOffsetSuper;
        this.avatar = avatar;
        this.childList = childList;
        this.date = date;
        this.idLogin = idLogin;
        this.msg = msg;
        this.name = name;
        this.type = type;
    }

    @Nullable
    public String getAccrualOfCoins() {
        return accrualOfCoins;
    }

    public void setAccrualOfCoins(@Nullable String accrualOfCoins) {
        this.accrualOfCoins = accrualOfCoins;
    }

    @Nullable
    public String getAccrualOfCoinsSuper() {
        return accrualOfCoinsSuper;
    }

    public void setAccrualOfCoinsSuper(@Nullable String accrualOfCoinsSuper) {
        this.accrualOfCoinsSuper = accrualOfCoinsSuper;
    }

    @Nullable
    public String getAccrualOfOffset() {
        return accrualOfOffset;
    }

    public void setAccrualOfOffset(@Nullable String accrualOfOffset) {
        this.accrualOfOffset = accrualOfOffset;
    }

    @Nullable
    public String getAccrualOfOffsetSuper() {
        return accrualOfOffsetSuper;
    }

    public void setAccrualOfOffsetSuper(@Nullable String accrualOfOffsetSuper) {
        this.accrualOfOffsetSuper = accrualOfOffsetSuper;
    }

    @Nullable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(@Nullable String avatar) {
        this.avatar = avatar;
    }

    @Nullable
    public List<ModelChildArray> getChildList() {
        return childList;
    }

    public void setChildList(@Nullable List<ModelChildArray> childList) {
        this.childList = childList;
    }

    @Nullable
    public String getDate() {
        return date;
    }

    public void setDate(@Nullable String date) {
        this.date = date;
    }

    @Nullable
    public String getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(@Nullable String idLogin) {
        this.idLogin = idLogin;
    }

    @Nullable
    public String getMsg() {
        return msg;
    }

    public void setMsg(@Nullable String msg) {
        this.msg = msg;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getType() {
        return type;
    }

    public void setType(@Nullable String type) {
        this.type = type;
    }
}
