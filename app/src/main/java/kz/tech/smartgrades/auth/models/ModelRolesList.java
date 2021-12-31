package kz.tech.smartgrades.auth.models;

public class ModelRolesList {

    private String Id;
    private String Name;
    private boolean Valid;

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
    public boolean isValid() {
        return Valid;
    }
    public void setValid(boolean valid) {
        Valid = valid;
    }
}
