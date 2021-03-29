package kz.tech.smartgrades.parent.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.child.models.ModelInterFormList;
import kz.tech.smartgrades.sponsor.models.ModelPrivateAccount;

public class ModelParentData implements Parcelable{

    private String FamilyGroupId;
    private ModelPrivateAccount PrivateAccount;
    public ArrayList<ModelInterFormList> InterFormList;
    public ArrayList<ModelChildData> ChildrenList;
    private ArrayList<ModelParentList> ParentList;
    private ArrayList<ModelSponsoredNotification> SponsoredNotifications;

    protected ModelParentData(Parcel in){
        FamilyGroupId = in.readString();
    }
    public static final Creator<ModelParentData> CREATOR = new Creator<ModelParentData>(){
        @Override
        public ModelParentData createFromParcel(Parcel in){
            return new ModelParentData(in);
        }

        @Override
        public ModelParentData[] newArray(int size){
            return new ModelParentData[size];
        }
    };
    public String getFamilyGroupId(){
        return FamilyGroupId;
    }
    public void setFamilyGroupId(String familyGroupId){
        FamilyGroupId = familyGroupId;
    }
    public ModelPrivateAccount getPrivateAccount(){
        return PrivateAccount;
    }
    public void setPrivateAccount(ModelPrivateAccount privateAccount){
        PrivateAccount = privateAccount;
    }
    public ArrayList<ModelInterFormList> getInterFormList(){
        return InterFormList;
    }
    public void setInterFormList(ArrayList<ModelInterFormList> interFormList){
        InterFormList = interFormList;
    }
    public ArrayList<ModelChildData> getChildrenList(){
        return ChildrenList;
    }
    public void setChildrenList(ArrayList<ModelChildData> childrenList){
        ChildrenList = childrenList;
    }
    public ArrayList<ModelParentList> getParentList(){
        return ParentList;
    }
    public void setParentList(ArrayList<ModelParentList> parentList){
        ParentList = parentList;
    }
    public ArrayList<ModelSponsoredNotification> getSponsoredNotifications(){
        return SponsoredNotifications;
    }
    public void setSponsoredNotifications(ArrayList<ModelSponsoredNotification> sponsoredNotifications){
        SponsoredNotifications = sponsoredNotifications;
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(FamilyGroupId);
    }
}
