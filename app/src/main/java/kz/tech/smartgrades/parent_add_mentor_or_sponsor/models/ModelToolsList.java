package kz.tech.smartgrades.parent_add_mentor_or_sponsor.models;

public class ModelToolsList {




    private String name;
    private boolean isChecked;

    public ModelToolsList() {

    }

    public ModelToolsList(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
