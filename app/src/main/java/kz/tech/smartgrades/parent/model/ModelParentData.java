package kz.tech.smartgrades.parent.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.sponsor.models.ModelPrivateAccount;

public class ModelParentData implements Parcelable{

    private ModelFamilyGroup FamilyGroup;
    private ModelPrivateAccount PrivateAccount;
    private ArrayList<ModelInterForm> InterForms;
    private ArrayList<ModelSponsoredNotification> SponsoredNotifications;

    protected ModelParentData(Parcel in) {
        FamilyGroup = in.readParcelable(ModelFamilyGroup.class.getClassLoader());
        PrivateAccount = in.readParcelable(ModelPrivateAccount.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(FamilyGroup, flags);
        dest.writeParcelable(PrivateAccount, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelParentData> CREATOR = new Creator<ModelParentData>() {
        @Override
        public ModelParentData createFromParcel(Parcel in) {
            return new ModelParentData(in);
        }

        @Override
        public ModelParentData[] newArray(int size) {
            return new ModelParentData[size];
        }
    };

    public ModelFamilyGroup getFamilyGroup() {
        return FamilyGroup;
    }

    public void setFamilyGroup(ModelFamilyGroup familyGroup) {
        FamilyGroup = familyGroup;
    }

    public ModelPrivateAccount getPrivateAccount() {
        return PrivateAccount;
    }

    public void setPrivateAccount(ModelPrivateAccount privateAccount) {
        PrivateAccount = privateAccount;
    }

    public ArrayList<ModelInterForm> getInterForms() {
        return InterForms;
    }

    public void setInterForms(ArrayList<ModelInterForm> interForms) {
        InterForms = interForms;
    }

    public ArrayList<ModelSponsoredNotification> getSponsoredNotifications() {
        return SponsoredNotifications;
    }

    public void setSponsoredNotifications(ArrayList<ModelSponsoredNotification> sponsoredNotifications) {
        SponsoredNotifications = sponsoredNotifications;
    }
}
