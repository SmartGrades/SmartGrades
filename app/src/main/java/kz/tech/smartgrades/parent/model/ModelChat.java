package kz.tech.smartgrades.parent.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ModelChat implements Parcelable {

    private String ChatId;
    private String Index;
    private String Answer;

    private String UserId;
    private String Avatar;
    private String FirstName;
    private String LastName;
    private String Login;
    private String AboutMe;
    private String Description;
    private String Type;
    private String Model;
    private String SourceId;


    //  FOR CHILDREN
    private String ChildId;
    private String SponsorId;
    private String LastCenterGrade;
    private String MinCenterGrade;
    private String MaxCenterGrade;
    private String LastGrade;
    private String LessonName;
    private String цвцвцв;
    private List<ModelGrades> Grades;
    //

    private String GroupId;
    private String GroupName;

    private String LessonId;

    //  FOR MENTORS
    private List<ModelMentorChildren> MentorChildren;
    //


    public ModelChat() {
    }

    protected ModelChat(Parcel in) {
        UserId = in.readString();
        Avatar = in.readString();
        FirstName = in.readString();
        LastName = in.readString();
        Login = in.readString();
        AboutMe = in.readString();
        Description = in.readString();
        Type = in.readString();
        SponsorId = in.readString();
        LastCenterGrade = in.readString();
        MinCenterGrade = in.readString();
        MaxCenterGrade = in.readString();
        LastGrade = in.readString();
    }

    public static final Creator<ModelChat> CREATOR = new Creator<ModelChat>() {
        @Override
        public ModelChat createFromParcel(Parcel in) {
            return new ModelChat(in);
        }

        @Override
        public ModelChat[] newArray(int size) {
            return new ModelChat[size];
        }
    };

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        this.Avatar = avatar;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        this.Login = login;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        this.UserId = userId;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAboutMe() {
        return AboutMe;
    }

    public void setAboutMe(String aboutMe) {
        AboutMe = aboutMe;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public List<ModelGrades> getGrades() {
        return Grades;
    }

    public void setGrades(List<ModelGrades> grades) {
        Grades = grades;
    }

    public List<ModelMentorChildren> getMentorChildren() {
        return MentorChildren;
    }

    public void setMentorChildren(List<ModelMentorChildren> mentorChildren) {
        MentorChildren = mentorChildren;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getSponsorId() {
        return SponsorId;
    }

    public void setSponsorId(String sponsorId) {
        SponsorId = sponsorId;
    }

    public String getLastCenterGrade() {
        return LastCenterGrade;
    }

    public void setLastCenterGrade(String lastCenterGrade) {
        LastCenterGrade = lastCenterGrade;
    }

    public String getMinCenterGrade() {
        return MinCenterGrade;
    }

    public void setMinCenterGrade(String minCenterGrade) {
        MinCenterGrade = minCenterGrade;
    }

    public String getMaxCenterGrade() {
        return MaxCenterGrade;
    }

    public void setMaxCenterGrade(String maxCenterGrade) {
        MaxCenterGrade = maxCenterGrade;
    }

    public String getLastGrade() {
        return LastGrade;
    }

    public void setLastGrade(String lastGrade) {
        LastGrade = lastGrade;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(UserId);
        parcel.writeString(Avatar);
        parcel.writeString(FirstName);
        parcel.writeString(LastName);
        parcel.writeString(Login);
        parcel.writeString(AboutMe);
        parcel.writeString(Description);
        parcel.writeString(Type);
        parcel.writeString(SponsorId);
        parcel.writeString(LastCenterGrade);
        parcel.writeString(MinCenterGrade);
        parcel.writeString(MaxCenterGrade);
        parcel.writeString(LastGrade);
    }

    public String getIndex() {
        return Index;
    }

    public void setIndex(String index) {
        Index = index;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getChildId() {
        return ChildId;
    }

    public void setChildId(String childId) {
        ChildId = childId;
    }

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

    public String getChatId() {
        return ChatId;
    }

    public void setChatId(String chatId) {
        ChatId = chatId;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getSourceId() {
        return SourceId;
    }

    public void setSourceId(String sourceId) {
        SourceId = sourceId;
    }

    public String getLessonName() {
        return LessonName;
    }

    public void setLessonName(String lessonName) {
        LessonName = lessonName;
    }

    public String getLessonId() {
        return LessonId;
    }

    public void setLessonId(String lessonId) {
        LessonId = lessonId;
    }
}
