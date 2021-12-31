package kz.tech.smartgrades.parent.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.child.models.ModelChildData;

public class ModelFamilyGroup implements Parcelable {

    private String FamilyGroupId;
    private ArrayList<ModelParentList> Parents;
    private ArrayList<ModelChildData> Childrens;

    public ModelFamilyGroup() {
    }

    protected ModelFamilyGroup(Parcel in) {
        FamilyGroupId = in.readString();
        Childrens = in.createTypedArrayList(ModelChildData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(FamilyGroupId);
        dest.writeTypedList(Childrens);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelFamilyGroup> CREATOR = new Creator<ModelFamilyGroup>() {
        @Override
        public ModelFamilyGroup createFromParcel(Parcel in) {
            return new ModelFamilyGroup(in);
        }

        @Override
        public ModelFamilyGroup[] newArray(int size) {
            return new ModelFamilyGroup[size];
        }
    };

    public ArrayList<ModelChildData> getChildrens() {
        return Childrens;
    }

    public void setChildrens(ArrayList<ModelChildData> childrens) {
        Childrens = childrens;
    }

    public ArrayList<ModelParentList> getParents() {
        return Parents;
    }

    public void setParents(ArrayList<ModelParentList> parents) {
        Parents = parents;
    }

    public String getFamilyGroupId() {
        return FamilyGroupId;
    }

    public void setFamilyGroupId(String familyGroupId) {
        FamilyGroupId = familyGroupId;
    }
}
