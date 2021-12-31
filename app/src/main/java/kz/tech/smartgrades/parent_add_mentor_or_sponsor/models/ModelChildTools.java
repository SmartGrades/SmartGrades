package kz.tech.smartgrades.parent_add_mentor_or_sponsor.models;

public class ModelChildTools {
    private String coinsCount;
    private String coinsTime;

    private String gadgetTime;

    private String moneyCount;

    private String stepsCount;
    private String stepsDone;

    private String childId;
    private String type;

    public ModelChildTools() {

    }

    public ModelChildTools(String coinsCount, String coinsTime, String gadgetTime, String moneyCount, String stepsCount, String stepsDone, String childId, String type) {
        this.coinsCount = coinsCount;
        this.coinsTime = coinsTime;
        this.gadgetTime = gadgetTime;
        this.moneyCount = moneyCount;
        this.stepsCount = stepsCount;
        this.stepsDone = stepsDone;
        this.childId = childId;
        this.type = type;
    }

    public String getCoinsCount() {
        return coinsCount;
    }

    public void setCoinsCount(String coinsCount) {
        this.coinsCount = coinsCount;
    }

    public String getCoinsTime() {
        return coinsTime;
    }

    public void setCoinsTime(String coinsTime) {
        this.coinsTime = coinsTime;
    }

    public String getGadgetTime() {
        return gadgetTime;
    }

    public void setGadgetTime(String gadgetTime) {
        this.gadgetTime = gadgetTime;
    }

    public String getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(String moneyCount) {
        this.moneyCount = moneyCount;
    }

    public String getStepsCount() {
        return stepsCount;
    }

    public void setStepsCount(String stepsCount) {
        this.stepsCount = stepsCount;
    }

    public String getStepsDone() {
        return stepsDone;
    }

    public void setStepsDone(String stepsDone) {
        this.stepsDone = stepsDone;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
