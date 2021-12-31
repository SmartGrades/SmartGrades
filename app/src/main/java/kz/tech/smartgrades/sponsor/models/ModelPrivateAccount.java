package kz.tech.smartgrades.sponsor.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.parent.model.ModelTransactions;

//Parcelable был добавлен из за ParentCashFragment

public class ModelPrivateAccount implements Parcelable {

    private String UserId;
    private String AccountId;
    private String AccountType;
    private String CurrentSum;
    private String TodayProfit;
    private ArrayList<ModelTransactions> Transactions;
    private ArrayList<ModelDiscontCard> Cards;


    protected ModelPrivateAccount(Parcel in) {
        UserId = in.readString();
        AccountId = in.readString();
        AccountType = in.readString();
        CurrentSum = in.readString();
        TodayProfit = in.readString();
    }

    public static final Creator<ModelPrivateAccount> CREATOR = new Creator<ModelPrivateAccount>() {
        @Override
        public ModelPrivateAccount createFromParcel(Parcel in) {
            return new ModelPrivateAccount(in);
        }

        @Override
        public ModelPrivateAccount[] newArray(int size) {
            return new ModelPrivateAccount[size];
        }
    };

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public String getCurrentSum() {
        return CurrentSum;
    }

    public void setCurrentSum(String currentSum) {
        CurrentSum = currentSum;
    }

    public ArrayList<ModelDiscontCard> getCards() {
        return Cards;
    }

    public void setCards(ArrayList<ModelDiscontCard> cards) {
        Cards = cards;
    }

    public String getTodayProfit() {
        return TodayProfit;
    }

    public void setTodayProfit(String todayProfit) {
        TodayProfit = todayProfit;
    }

    public ArrayList<ModelTransactions> getTransactions() {
        return Transactions;
    }

    public void setTransactions(ArrayList<ModelTransactions> transactions) {
        Transactions = transactions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(UserId);
        parcel.writeString(AccountId);
        parcel.writeString(AccountType);
        parcel.writeString(CurrentSum);
        parcel.writeString(TodayProfit);
    }
}
