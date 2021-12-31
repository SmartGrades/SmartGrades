package kz.tech.smartgrades.child.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.sponsor.models.ModelLessonsForSponsorship;

public class ModelInterForm implements Parcelable {
    private String TimeStamp;
    private String Id;
    private String Type;
    private String GroupId;
    private String UserId;
    private String InterFormId;

    private String SourceId;
    private String SourceLogin;
    private String SourceAvatar;
    private String SourceAvatarOriginal;
    private String SourceFirstName;
    private String SourceLastName;
    private String SourceType;

    private String TargetId;
    private String TargetAvatar;
    private String TargetLogin;
    private String TargetFirstName;
    private String TargetLastName;
    private String TargetType;

    private String StudentId;
    private String ChildId;
    private String ChildLogin;
    private String ChildAvatar;
    private String ChildFirstName;
    private String ChildLastName;

    private String VerificationСode;

    private String AverageGrade;
    private String CountGrade;
    private String Reward;
    private String TotalReward;
    private String ChatId;

    private String ClassId;
    private String ClassName;

    private String Avatar;
    private String AvatarOriginal;
    private String FirstName;
    private String LastName;

    private String ChildLessonId;
    private String LessonId;
    private String LessonName;
    private ArrayList<ModelLessonsForSponsorship> Lessons;

    public ModelInterForm(){}

    protected ModelInterForm(Parcel in) {
        TimeStamp = in.readString();
        Id = in.readString();
        Type = in.readString();
        GroupId = in.readString();
        SourceId = in.readString();
        SourceAvatar = in.readString();
        SourceFirstName = in.readString();
        SourceLastName = in.readString();
        SourceType = in.readString();
        TargetId = in.readString();
        TargetAvatar = in.readString();
        TargetFirstName = in.readString();
        TargetLastName = in.readString();
        TargetType = in.readString();
        StudentId = in.readString();
        ChildId = in.readString();
        ChildAvatar = in.readString();
        ChildFirstName = in.readString();
        ChildLastName = in.readString();
        VerificationСode = in.readString();
        AverageGrade = in.readString();
        CountGrade = in.readString();
        Reward = in.readString();
        ChatId = in.readString();
        ClassId = in.readString();
        ClassName = in.readString();
        ChildLessonId = in.readString();
        LessonId = in.readString();
        LessonName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TimeStamp);
        dest.writeString(Id);
        dest.writeString(Type);
        dest.writeString(GroupId);
        dest.writeString(SourceId);
        dest.writeString(SourceAvatar);
        dest.writeString(SourceFirstName);
        dest.writeString(SourceLastName);
        dest.writeString(SourceType);
        dest.writeString(TargetId);
        dest.writeString(TargetAvatar);
        dest.writeString(TargetFirstName);
        dest.writeString(TargetLastName);
        dest.writeString(TargetType);
        dest.writeString(StudentId);
        dest.writeString(ChildId);
        dest.writeString(ChildAvatar);
        dest.writeString(ChildFirstName);
        dest.writeString(ChildLastName);
        dest.writeString(VerificationСode);
        dest.writeString(AverageGrade);
        dest.writeString(CountGrade);
        dest.writeString(Reward);
        dest.writeString(ChatId);
        dest.writeString(ClassId);
        dest.writeString(ClassName);
        dest.writeString(ChildLessonId);
        dest.writeString(LessonId);
        dest.writeString(LessonName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelInterForm> CREATOR = new Creator<ModelInterForm>() {
        @Override
        public ModelInterForm createFromParcel(Parcel in) {
            return new ModelInterForm(in);
        }

        @Override
        public ModelInterForm[] newArray(int size) {
            return new ModelInterForm[size];
        }
    };

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getSourceId() {
        return SourceId;
    }

    public void setSourceId(String sourceId) {
        SourceId = sourceId;
    }

    public String getSourceAvatar() {
        return SourceAvatar;
    }

    public void setSourceAvatar(String sourceAvatar) {
        SourceAvatar = sourceAvatar;
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

    public String getSourceType() {
        return SourceType;
    }

    public void setSourceType(String sourceType) {
        SourceType = sourceType;
    }

    public String getTargetId() {
        return TargetId;
    }

    public void setTargetId(String targetId) {
        TargetId = targetId;
    }

    public String getTargetAvatar() {
        return TargetAvatar;
    }

    public void setTargetAvatar(String targetAvatar) {
        TargetAvatar = targetAvatar;
    }

    public String getTargetFirstName() {
        return TargetFirstName;
    }

    public void setTargetFirstName(String targetFirstName) {
        TargetFirstName = targetFirstName;
    }

    public String getTargetLastName() {
        return TargetLastName;
    }

    public void setTargetLastName(String targetLastName) {
        TargetLastName = targetLastName;
    }

    public String getTargetType() {
        return TargetType;
    }

    public void setTargetType(String targetType) {
        TargetType = targetType;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
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

    public String getVerificationСode() {
        return VerificationСode;
    }

    public void setVerificationСode(String verificationСode) {
        VerificationСode = verificationСode;
    }

    public String getAverageGrade() {
        return AverageGrade;
    }

    public void setAverageGrade(String averageGrade) {
        AverageGrade = averageGrade;
    }

    public String getCountGrade() {
        return CountGrade;
    }

    public void setCountGrade(String countGrade) {
        CountGrade = countGrade;
    }

    public String getReward() {
        return Reward;
    }

    public void setReward(String reward) {
        Reward = reward;
    }

    public String getChatId() {
        return ChatId;
    }

    public void setChatId(String chatId) {
        ChatId = chatId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getChildLessonId() {
        return ChildLessonId;
    }

    public void setChildLessonId(String childLessonId) {
        ChildLessonId = childLessonId;
    }

    public String getLessonId() {
        return LessonId;
    }

    public void setLessonId(String lessonId) {
        LessonId = lessonId;
    }

    public String getLessonName() {
        return LessonName;
    }

    public void setLessonName(String lessonName) {
        LessonName = lessonName;
    }

    public ArrayList<ModelLessonsForSponsorship> getLessons() {
        return Lessons;
    }

    public void setLessons(ArrayList<ModelLessonsForSponsorship> lessons) {
        Lessons = lessons;
    }

    public String getTotalReward() {
        return TotalReward;
    }

    public void setTotalReward(String totalReward) {
        TotalReward = totalReward;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getInterFormId() {
        return InterFormId;
    }

    public void setInterFormId(String interFormId) {
        InterFormId = interFormId;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getAvatarOriginal() {
        return AvatarOriginal;
    }

    public void setAvatarOriginal(String avatarOriginal) {
        AvatarOriginal = avatarOriginal;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getSourceAvatarOriginal() {
        return SourceAvatarOriginal;
    }

    public void setSourceAvatarOriginal(String sourceAvatarOriginal) {
        SourceAvatarOriginal = sourceAvatarOriginal;
    }
    public String getSourceLogin() {
        return SourceLogin;
    }
    public void setSourceLogin(String sourceLogin) {
        SourceLogin = sourceLogin;
    }
    public String getChildLogin() {
        return ChildLogin;
    }
    public void setChildLogin(String childLogin) {
        ChildLogin = childLogin;
    }
    public String getTargetLogin() {
        return TargetLogin;
    }
    public void setTargetLogin(String targetLogin) {
        TargetLogin = targetLogin;
    }
}