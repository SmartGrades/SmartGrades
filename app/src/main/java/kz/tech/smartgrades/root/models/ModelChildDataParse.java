package kz.tech.smartgrades.root.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelChildDataParse {
    @Nullable
    @SerializedName("coins")
    @Expose
    private String coins;
    @Nullable
    @SerializedName("contracts")
    @Expose
    private Object contracts;
    @Nullable
    @SerializedName("idChild")
    @Expose
    private String idChild;
    public ModelChildDataParse() {}
    public ModelChildDataParse(@Nullable String coins, @Nullable Object contracts, @Nullable String idChild) {
        this.coins = coins;
        this.contracts = contracts;
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
    public Object getContracts() {
        return contracts;
    }

    public void setContracts(@Nullable Object contracts) {
        this.contracts = contracts;
    }

    @Nullable
    public String getIdChild() {
        return idChild;
    }

    public void setIdChild(@Nullable String idChild) {
        this.idChild = idChild;
    }
}
