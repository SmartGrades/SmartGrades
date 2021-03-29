package kz.tech.smartgrades.mentor_module.coins.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelGroups {
    @SerializedName("childList")
    @Expose
    private List<ModelChildList> childList;
    @SerializedName("idGroup")
    @Expose
    private String idGroup;
    @SerializedName("name")
    @Expose
    private String name;

    public ModelGroups() {}

    public ModelGroups(List<ModelChildList> childList, String idGroup, String name) {
        this.childList = childList;
        this.idGroup = idGroup;
        this.name = name;
    }

    public List<ModelChildList> getChildList() {
        return childList;
    }

    public void setChildList(List<ModelChildList> childList) {
        this.childList = childList;
    }

    public String getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(String idGroup) {
        this.idGroup = idGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
