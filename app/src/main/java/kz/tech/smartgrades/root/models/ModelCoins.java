package kz.tech.smartgrades.root.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelCoins {
    @Nullable
    @SerializedName("coins")
    @Expose
    private String coins;

    @Nullable
    @SerializedName("idChild")
    @Expose
    private String idChild;

    public ModelCoins() {}

    public ModelCoins(@Nullable String coins, @Nullable String idChild) {
        this.coins = coins;
        this.idChild = idChild;
    }

    @Nullable
    public String getCoins() {
        return coins;
    }

    public void setCoins(@Nullable String coins) {
        this.coins = coins;
    }

    @Nullable
    public String getIdChild() {
        return idChild;
    }

    public void setIdChild(@Nullable String idChild) {
        this.idChild = idChild;
    }
}
