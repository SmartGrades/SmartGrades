package kz.tech.smartgrades.auth.models;

public class ModelAnswer {

    private boolean Success;
    private String Message;

    private String CardId;
    private int Code;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String cardId) {
        CardId = cardId;
    }
    public int getCode() {
        return Code;
    }
    public void setCode(int code) {
        Code = code;
    }
}
