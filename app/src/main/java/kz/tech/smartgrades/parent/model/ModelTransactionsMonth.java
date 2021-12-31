package kz.tech.smartgrades.parent.model;

import java.util.ArrayList;

public class ModelTransactionsMonth {
    private String Date;
    private ArrayList<ModelTransactions> Transactions;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public ArrayList<ModelTransactions> getTransactions() {
        return Transactions;
    }

    public void setTransactions(ArrayList<ModelTransactions> transactions) {
        Transactions = transactions;
    }
}
