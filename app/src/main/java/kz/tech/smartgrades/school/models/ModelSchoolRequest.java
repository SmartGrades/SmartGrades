package kz.tech.smartgrades.school.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelSchoolRequest implements Parcelable {

    private String Date;

    private String ParentId;
    private String ParentAvatar;
    private String ParentFirstName;
    private String ParentLastName;

    private String ChildId;
    private String ChildAvatar;
    private String ChildFirstName;
    private String ChildLastName;

    private String StudentId;
    private String StudentAvatar;
    private String StudentFirstName;
    private String StudentLastName;

    private String ClassId;
    private String Name;

    private String SourceId;
    private String SourceFirstName;
    private String SourceLastName;
    private String SourceAvatar;
    private String SourceType;

    private String SchoolId;
    private String Id;
    private String Title;
    private String ChatId;
    private String LastMessage;
    private String LastMessageDate;
    private String NoCheckCount;
    public boolean IsCheck;

    protected ModelSchoolRequest(Parcel in) {
        Date = in.readString();
        ParentId = in.readString();
        ParentAvatar = in.readString();
        ParentFirstName = in.readString();
        ParentLastName = in.readString();
        ChildId = in.readString();
        ChildAvatar = in.readString();
        ChildFirstName = in.readString();
        ChildLastName = in.readString();
        ClassId = in.readString();
        Name = in.readString();
        SourceId = in.readString();
        SourceFirstName = in.readString();
        SourceLastName = in.readString();
        SourceAvatar = in.readString();
        SourceType = in.readString();
        Id = in.readString();
        Title = in.readString();
        ChatId = in.readString();
        LastMessage = in.readString();
        LastMessageDate = in.readString();
        NoCheckCount = in.readString();
        IsCheck = in.readByte() != 0;
    }
    public static final Creator<ModelSchoolRequest> CREATOR = new Creator<ModelSchoolRequest>() {
        @Override
        public ModelSchoolRequest createFromParcel(Parcel in) {
            return new ModelSchoolRequest(in);
        }

        @Override
        public ModelSchoolRequest[] newArray(int size) {
            return new ModelSchoolRequest[size];
        }
    };
    public String getDate() {
        return Date;
    }
    public void setDate(String date) {
        Date = date;
    }
    public String getParentId() {
        return ParentId;
    }
    public void setParentId(String parentId) {
        ParentId = parentId;
    }
    public String getParentAvatar() {
        return ParentAvatar;
    }
    public void setParentAvatar(String parentAvatar) {
        ParentAvatar = parentAvatar;
    }
    public String getParentFirstName() {
        return ParentFirstName;
    }
    public void setParentFirstName(String parentFirstName) {
        ParentFirstName = parentFirstName;
    }
    public String getParentLastName() {
        return ParentLastName;
    }
    public void setParentLastName(String parentLastName) {
        ParentLastName = parentLastName;
    }
    public String getChildId() {
        return ChildId;
    }
    public void setChildId(String childId) {
        ChildId = childId;
    }
    public String getChildAvatar() {
        return ChildAvatar;
    }
    public void setChildAvatar(String childAvatar) {
        ChildAvatar = childAvatar;
    }
    public String getChildFirstName() {
        return ChildFirstName;
    }
    public void setChildFirstName(String childFirstName) {
        ChildFirstName = childFirstName;
    }
    public String getChildLastName() {
        return ChildLastName;
    }
    public void setChildLastName(String childLastName) {
        ChildLastName = childLastName;
    }
    public String getClassId() {
        return ClassId;
    }
    public void setClassId(String classId) {
        ClassId = classId;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getSourceId() {
        return SourceId;
    }
    public void setSourceId(String sourceId) {
        SourceId = sourceId;
    }
    public String getSourceFirstName() {
        return SourceFirstName;
    }
    public void setSourceFirstName(String sourceFirstName) {
        SourceFirstName = sourceFirstName;
    }
    public String getSourceLastName() {
        return SourceLastName;
    }
    public void setSourceLastName(String sourceLastName) {
        SourceLastName = sourceLastName;
    }
    public String getSourceAvatar() {
        return SourceAvatar;
    }
    public void setSourceAvatar(String sourceAvatar) {
        SourceAvatar = sourceAvatar;
    }
    public String getSourceType() {
        return SourceType;
    }
    public void setSourceType(String sourceType) {
        SourceType = sourceType;
    }
    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public String getChatId() {
        return ChatId;
    }
    public void setChatId(String chatId) {
        ChatId = chatId;
    }
    public String getLastMessage() {
        return LastMessage;
    }
    public void setLastMessage(String lastMessage) {
        LastMessage = lastMessage;
    }
    public String getLastMessageDate() {
        return LastMessageDate;
    }
    public void setLastMessageDate(String lastMessageDate) {
        LastMessageDate = lastMessageDate;
    }
    public String getNoCheckCount() {
        return NoCheckCount;
    }
    public void setNoCheckCount(String noCheckCount) {
        NoCheckCount = noCheckCount;
    }
    public boolean isCheck() {
        return IsCheck;
    }
    public void setCheck(boolean check) {
        IsCheck = check;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Date);
        parcel.writeString(ParentId);
        parcel.writeString(ParentAvatar);
        parcel.writeString(ParentFirstName);
        parcel.writeString(ParentLastName);
        parcel.writeString(ChildId);
        parcel.writeString(ChildAvatar);
        parcel.writeString(ChildFirstName);
        parcel.writeString(ChildLastName);
        parcel.writeString(ClassId);
        parcel.writeString(Name);
        parcel.writeString(SourceId);
        parcel.writeString(SourceFirstName);
        parcel.writeString(SourceLastName);
        parcel.writeString(SourceAvatar);
        parcel.writeString(SourceType);
        parcel.writeString(Id);
        parcel.writeString(Title);
        parcel.writeString(ChatId);
        parcel.writeString(LastMessage);
        parcel.writeString(LastMessageDate);
        parcel.writeString(NoCheckCount);
        parcel.writeByte((byte) (IsCheck ? 1 : 0));
    }
    public String getSchoolId() {
        return SchoolId;
    }
    public void setSchoolId(String schoolId) {
        SchoolId = schoolId;
    }
    public String getStudentId() {
        return StudentId;
    }
    public void setStudentId(String studentId) {
        StudentId = studentId;
    }
    public String getStudentAvatar() {
        return StudentAvatar;
    }
    public void setStudentAvatar(String studentAvatar) {
        StudentAvatar = studentAvatar;
    }
    public String getStudentFirstName() {
        return StudentFirstName;
    }
    public void setStudentFirstName(String studentFirstName) {
        StudentFirstName = studentFirstName;
    }
    public String getStudentLastName() {
        return StudentLastName;
    }
    public void setStudentLastName(String studentLastName) {
        StudentLastName = studentLastName;
    }
}