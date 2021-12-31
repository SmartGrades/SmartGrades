package kz.tech.smartgrades.mentor_module.coins.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelChildList {
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
    @SerializedName("idChild")
    @Expose
    private String idChild;

    @Nullable
    @SerializedName("idLogin")
    @Expose
    private String idLogin;

    @Nullable
    @SerializedName("name")
    @Expose
    private String name;

    public ModelChildList() {}

    public ModelChildList(@Nullable String accrualOfCoins, @Nullable String accrualOfCoinsSuper,
                          @Nullable String accrualOfOffset, @Nullable String accrualOfOffsetSuper,
                          @Nullable String avatar, @Nullable String idChild, @Nullable String idLogin,
                          @Nullable String name) {
        this.accrualOfCoins = accrualOfCoins;
        this.accrualOfCoinsSuper = accrualOfCoinsSuper;
        this.accrualOfOffset = accrualOfOffset;
        this.accrualOfOffsetSuper = accrualOfOffsetSuper;
        this.avatar = avatar;
        this.idChild = idChild;
        this.idLogin = idLogin;
        this.name = name;
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
    public String getIdChild() {
        return idChild;
    }

    public void setIdChild(@Nullable String idChild) {
        this.idChild = idChild;
    }

    @Nullable
    public String getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(@Nullable String idLogin) {
        this.idLogin = idLogin;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }
}
