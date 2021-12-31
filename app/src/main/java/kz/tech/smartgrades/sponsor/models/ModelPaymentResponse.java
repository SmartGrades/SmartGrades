package kz.tech.smartgrades.sponsor.models;

public class ModelPaymentResponse {

    private boolean Success;
    private String Message;
    private ModelPaymentResponseModel Model;


    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public ModelPaymentResponseModel getModel() {
        return Model;
    }

    public void setModel(ModelPaymentResponseModel model) {
        this.Model = model;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }
}

