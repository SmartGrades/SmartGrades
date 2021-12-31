package kz.tech.smartgrades.sponsor.models;

import java.util.ArrayList;

import kz.tech.smartgrades.parent.model.ModelPrivateChat;
import kz.tech.smartgrades.parent.model.ModelSponsoredNotification;

public class ModelSponsorData {

    private ArrayList<ModelSponsorChildrenList> ChildrenList;
    private ArrayList<ModelSponsoredNotification> SponsoredNotifications;
    private ArrayList<ModelPrivateChat> PrivateChats;
    private ModelPrivateAccount PrivateAccount;

    public ArrayList<ModelSponsorChildrenList> getChildrenList() {
        return ChildrenList;
    }

    public void setChildrenList(ArrayList<ModelSponsorChildrenList> ChildrenList) {
        this.ChildrenList = ChildrenList;
    }

    public ArrayList<ModelPrivateChat> getPrivateChats() {
        return PrivateChats;
    }

    public void setPrivateChats(ArrayList<ModelPrivateChat> privateChats) {
        PrivateChats = privateChats;
    }

    public ModelPrivateAccount getPrivateAccount() {
        return PrivateAccount;
    }

    public void setPrivateAccount(ModelPrivateAccount privateAccount) {
        PrivateAccount = privateAccount;
    }

    public ArrayList<ModelSponsoredNotification> getSponsoredNotifications() {
        return SponsoredNotifications;
    }

    public void setSponsoredNotifications(ArrayList<ModelSponsoredNotification> sponsoredNotifications) {
        SponsoredNotifications = sponsoredNotifications;
    }
}