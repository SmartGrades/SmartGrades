package kz.tech.smartgrades.parents_module.coins.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelGetCoins {
    @Nullable
    @SerializedName("coins")
    @Expose
    private String coins;
    @Nullable
    @SerializedName("zid")
    @Expose
    private String zid;
    public ModelGetCoins() {}

    public ModelGetCoins(@Nullable String coins, @Nullable String zid) {
        this.coins = coins;
        this.zid = zid;
    }
    @Nullable
    public String getCoins() {
        return coins;
    }

    public void setCoins(@Nullable String coins) {
        this.coins = coins;
    }

    @Nullable
    public String getZid() {
        return zid;
    }

    public void setZid(@Nullable String zid) {
        this.zid = zid;
    }

}
