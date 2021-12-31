package kz.tech.smartgrades.child.models;


public class ModelChildWardCoin {
    private String type;
    private String value;

    private String coinsStart;
    private String coinsEnd;
    private String dateStart;
    private String dateEnd;

    public ModelChildWardCoin() { }

    public ModelChildWardCoin(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    public String getCoinsStart() {
        return coinsStart;
    }

    public void setCoinsStart(String coinsStart) {
        this.coinsStart = coinsStart;
    }

    public String getCoinsEnd() {
        return coinsEnd;
    }

    public void setCoinsEnd(String coinsEnd) {
        this.coinsEnd = coinsEnd;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}