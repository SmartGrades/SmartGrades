package kz.tech.smartgrades.childs_module.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelPiggy {
    @Nullable
    @SerializedName("coinsPiggy")
    @Expose
    private String coinsPiggy;

    public ModelPiggy(@Nullable String coinsPiggy) {
        this.coinsPiggy = coinsPiggy;
    }

    @Nullable
    public String getCoinsPiggy() {
        return coinsPiggy;
    }

    public void setCoinsPiggy(@Nullable String coinsPiggy) {
        this.coinsPiggy = coinsPiggy;
    }
}
