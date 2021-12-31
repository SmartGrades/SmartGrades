package kz.tech.smartgrades.sponsor.models;

public class ModelDiscontCard {

    private String Id;
    private String Index;
    private String UserId;
    private String CardNumber;
    private String UserName;
    private String CardName;
    private String Bank;
    private String Date;
    private String Cryptogram;
    private boolean SaveInCP;


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getIndex() {
        return Index;
    }

    public void setIndex(String index) {
        Index = index;
    }
    public String getCardName(){
        return CardName;
    }
    public void setCardName(String cardName){
        CardName = cardName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCryptogram() {
        return Cryptogram;
    }

    public void setCryptogram(String cryptogram) {
        Cryptogram = cryptogram;
    }

    public boolean isSaveInCP() {
        return SaveInCP;
    }

    public void setSaveInCP(boolean saveInCP) {
        SaveInCP = saveInCP;
    }

    public String getBank() {
        return Bank;
    }

    public void setBank(String bank) {
        Bank = bank;
    }
}
