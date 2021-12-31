package kz.tech.smartgrades.mentor.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ModelMentorGroups implements Parcelable {

    private String GroupId;
    private String GroupName;
    private String LessonId;
    private String LessonName;
    private ArrayList<ModelMentorChat> ChildrenList;


    protected ModelMentorGroups(Parcel in) {
        GroupId = in.readString();
        GroupName = in.readString();
        LessonId = in.readString();
//        ChildrenList = in.createTypedArrayList(ModelMentorChat.CREATOR);
    }

    public static final Creator<ModelMentorGroups> CREATOR = new Creator<ModelMentorGroups>() {
        @Override
        public ModelMentorGroups createFromParcel(Parcel in) {
            return new ModelMentorGroups(in);
        }

        @Override
        public ModelMentorGroups[] newArray(int size) {
            return new ModelMentorGroups[size];
        }
    };

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getLessonId() {
        return LessonId;
    }

    public void setLessonId(String lessonId) {
        LessonId = lessonId;
    }

    public ArrayList<ModelMentorChat> getChildrenList() {
        return ChildrenList;
    }

    public void setChildrenList(ArrayList<ModelMentorChat> childrenList) {
        ChildrenList = childrenList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(GroupId);
        parcel.writeString(GroupName);
        parcel.writeString(LessonId);
//        parcel.writeTypedList(ChildrenList);
    }

    public String getLessonName() {
        return LessonName;
    }

    public void setLessonName(String lessonName) {
        LessonName = lessonName;
    }
}
