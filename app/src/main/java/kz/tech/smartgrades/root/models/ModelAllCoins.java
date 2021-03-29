package kz.tech.smartgrades.root.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelAllCoins {
    @Nullable
    @SerializedName("coins")
    @Expose
    private String coins;
    @Nullable
    @SerializedName("coinsBank")
    @Expose
    private String coinsBank;
    @Nullable
    @SerializedName("coinsPiggy")
    @Expose
    private String coinsPiggy;
    @Nullable
    @SerializedName("coinsSpentToday")
    @Expose
    private String coinsSpentToday;
    public ModelAllCoins() {}
    public ModelAllCoins(@Nullable String coins, @Nullable String coinsBank, @Nullable String coinsPiggy, @Nullable String coinsSpentToday) {
        this.coins = coins;
        this.coinsBank = coinsBank;
        this.coinsPiggy = coinsPiggy;
        this.coinsSpentToday = coinsSpentToday;
    }
    @Nullable
    public String getCoins() {
        return coins;
    }
    public void setCoins(@Nullable String coins) {
        this.coins = coins;
    }
    @Nullable
    public String getCoinsBank() {
        return coinsBank;
    }
    public void setCoinsBank(@Nullable String coinsBank) {
        this.coinsBank = coinsBank;
    }
    @Nullable
    public String getCoinsPiggy() {
        return coinsPiggy;
    }
    public void setCoinsPiggy(@Nullable String coinsPiggy) {
        this.coinsPiggy = coinsPiggy;
    }
    @Nullable
    public String getCoinsSpentToday() {
        return coinsSpentToday;
    }
    public void setCoinsSpentToday(@Nullable String coinsSpentToday) {
        this.coinsSpentToday = coinsSpentToday;
    }
}
