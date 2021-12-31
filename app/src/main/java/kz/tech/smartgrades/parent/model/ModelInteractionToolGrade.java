package kz.tech.smartgrades.parent.model;

import kz.tech.smartgrades.Convert;

public class ModelInteractionToolGrade {
    private String Type;
    private String SmartTwo;
    private String SmartThree;
    private String SmartFour;
    private String SmartFive;
    private String SmartFivePlus;
    private String avatar;
    private String fullName;
    private String stepTarget;
    private String StepId;

    public ModelInteractionToolGrade() {}
    public ModelInteractionToolGrade(String Type, String SmartTwo, String SmartThree, String SmartFour, String SmartFive, String SmartFivePlus, String avatar, String fullName, String stepTarget, String StepId) {
        this.Type = Type;
        this.SmartTwo = SmartTwo;
        this.SmartThree = SmartThree;
        this.SmartFour = SmartFour;
        this.SmartFive = SmartFive;
        this.SmartFivePlus = SmartFivePlus;
        this.avatar = avatar;
        this.fullName = fullName;
        this.stepTarget = stepTarget;
        this.StepId = StepId;
    }

    public String getDiscription(){
        String result = "2=" + SmartTwo + ";   3=" + SmartThree + ";   4=" + SmartFour + ";   5=" + SmartFive + ";   5+=" + SmartFivePlus;
        if (Type.equals("TemporaryСoins") || Type.equals("GadgetTime")){
            String Data = "";
            result = "";
            int[] value = new int[5];
            value[0] = Convert.Str2Int(SmartTwo);
            value[1] = Convert.Str2Int(SmartThree);
            value[2] = Convert.Str2Int(SmartFour);
            value[3] = Convert.Str2Int(SmartFive);
            value[4] = Convert.Str2Int(SmartFivePlus);
            if (value[0] >= 60 || value[0] <= -60) {
                Data = String.format("%02d:%02dч", value[0] / 60 % 60, value[0] % 60).replace("-", "");
                if (value[0] < 0) Data = "2=-" + Data;
                else Data = "2=" + Data;
            }
            else Data = "2=" + value[0] + "мин";
            result +=Data;

            if (value[1] >= 60 || value[1] <= -60) {
                Data = String.format("%02d:%02dч", value[1] / 60 % 60, value[1] % 60).replace("-", "");
                if (value[1] < 0) Data = ";   3=-" + Data;
                else Data = ";   3=" + Data;
            }
            else Data = ";   3=" + value[1] + "мин";
            result +=Data;

            if (value[2] >= 60 || value[2] <= -60) {
                Data = String.format("%02d:%02dч", value[2] / 60 % 60, value[2] % 60).replace("-", "");
                if (value[2] < 0) Data = ";   4=-" + Data;
                else Data = ";   4=" + Data;
            }
            else Data = ";   4=" + value[2] + "мин";
            result +=Data;

            if (value[3] >= 60 || value[3] <= -60) {
                Data = String.format("%02d:%02dч", value[3] / 60 % 60, value[3] % 60).replace("-", "");
                if (value[3] < 0) Data = ";   5=-" + Data;
                else Data = ";   5=" + Data;
            }
            else Data = ";   5=" + value[3] + "мин";
            result +=Data;

            if (value[4] >= 60 || value[4] <= -60) {
                Data = String.format("%02d:%02dч", value[4] / 60 % 60, value[4] % 60).replace("-", "");
                if (value[4] < 0) Data = ";   5+=-" + Data;
                else Data = ";   5+=" + Data;
            }
            else Data = ";   5+=" + value[4] + "мин";
            result +=Data;
        }
        return result;
    }
    public String getSmartTwo() {
        return SmartTwo;
    }
    public void setSmartTwo(String SmartTwo) {
        this.SmartTwo = SmartTwo;
    }
    public String getSmartThree() {
        return SmartThree;
    }
    public void setSmartThree(String done) {
        this.SmartThree = SmartThree;
    }
    public String getSmartFour() {
        return SmartFour;
    }
    public void setSmartFour(String SmartFour) {
        this.SmartFour = SmartFour;
    }
    public String getSmartFive() {
        return SmartFive;
    }
    public void setSmartFive(String SmartFive) {
        this.SmartFive = SmartFive;
    }
    public String getSmartFivePlus() {
        return SmartFivePlus;
    }
    public void setSmartFivePlus(String SmartFivePlus) {
        this.SmartFivePlus = SmartFivePlus;
    }
    public String getType() {
        return Type;
    }
    public void setType(String Type) {
        this.Type = Type;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getStepTarget() {
        return stepTarget;
    }
    public void setStepTarget(String stepTarget) {
        this.stepTarget = stepTarget;
    }
    public String getStepId() {
        return StepId;
    }
    public void setStepId(String StepId) {
        this.StepId = StepId;
    }
}

