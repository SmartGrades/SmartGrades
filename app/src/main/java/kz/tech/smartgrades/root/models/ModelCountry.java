package kz.tech.smartgrades.root.models;


public class ModelCountry {

    private String Id;
    private String Name;
    private int Type;//0 - Country 1 - Region 2 - City

    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public int getType() {
        return Type;
    }
    public void setType(int type) {
        Type = type;
    }
}
