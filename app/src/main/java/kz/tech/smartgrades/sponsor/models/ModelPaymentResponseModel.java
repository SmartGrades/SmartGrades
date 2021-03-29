package kz.tech.smartgrades.sponsor.models;

public class ModelPaymentResponseModel {

    private String TransactionId;
    private String PaReq;
    private String AcsUrl;

    private String CardHolderMessage;

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public String getPaReq() {
        return PaReq;
    }

    public void setPaReq(String paReq) {
        PaReq = paReq;
    }

    public String getAcsUrl() {
        return AcsUrl;
    }

    public void setAcsUrl(String acsUrl) {
        AcsUrl = acsUrl;
    }

    public String getCardHolderMessage() {
        return CardHolderMessage;
    }

    public void setCardHolderMessage(String cardHolderMessage) {
        CardHolderMessage = cardHolderMessage;
    }
}

