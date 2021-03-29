package kz.tech.smartgrades.root.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelChildData implements Serializable, Parcelable{
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
    @Nullable
    @SerializedName("contracts")
    @Expose
    private ArrayList<ModelContract> contracts = new ArrayList<>();
    @Nullable
    @SerializedName("idChild")
    @Expose
    private String idChild;
    public ModelChildData() {}
    public ModelChildData(@Nullable String coins, @Nullable String coinsBank, @Nullable String coinsPiggy,
        @Nullable String coinsSpentToday, @Nullable ArrayList<ModelContract> contracts, @Nullable String idChild) {
        this.coins = coins;
        this.coinsBank = coinsBank;
        this.coinsPiggy = coinsPiggy;
        this.coinsSpentToday = coinsSpentToday;
        this.contracts = contracts;
        this.idChild = idChild;
    }
    protected ModelChildData(Parcel in){
        coins = in.readString();
        coinsBank = in.readString();
        coinsPiggy = in.readString();
        coinsSpentToday = in.readString();
        idChild = in.readString();
    }
    public static final Creator<ModelChildData> CREATOR = new Creator<ModelChildData>(){
        @Override
        public ModelChildData createFromParcel(Parcel in){
            return new ModelChildData(in);
        }

        @Override
        public ModelChildData[] newArray(int size){
            return new ModelChildData[size];
        }
    };
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
    @Nullable
    public ArrayList<ModelContract> getContracts() {
        return contracts;
    }
    public void setContracts(@Nullable ArrayList<ModelContract> contracts) {
        this.contracts = contracts;
    }
    @Nullable
    public String getIdChild() {
        return idChild;
    }
    public void setIdChild(@Nullable String idChild) {
        this.idChild = idChild;
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(coins);
        parcel.writeString(coinsBank);
        parcel.writeString(coinsPiggy);
        parcel.writeString(coinsSpentToday);
        parcel.writeString(idChild);
    }
}
