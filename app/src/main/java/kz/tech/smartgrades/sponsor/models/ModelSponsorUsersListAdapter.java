package kz.tech.smartgrades.sponsor.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.parent.model.ModelGrades;

public class ModelSponsorUsersListAdapter implements Parcelable {
    private String Avatar;
    private String Birthday;
    private String FirstName;
    private String LastName;
    private String UserId;
    private String Login;
    private String PhoneOrMail;
    private String Type;
    private String AboutMe;
    private String Description;

    private String ChatId;
    private String LastMessageDate;
    private String LastMessage;
    private int NoCheckCount;

    private String LastGrade;
    private String CenterGrade;
    private String MinCenterGrade;

    private String InterFormId;
    private String Answer;

    private String LessonId1;
    private String LessonId2;
    private String LessonId3;
    private String LessonId4;

    private String ThresholdGrade;
    private String GradesLeft;
    private String DaysLeft;
    private String AverageGrade;

    private ArrayList<ModelGrades> Grades;


    public ModelSponsorUsersListAdapter() { }

    public ModelSponsorUsersListAdapter(String Avatar, String DateBirthday, String FullName, String Id, String Login, String PhoneOrMail,
                                        String ChatId, String LastMessageDate, String ParentId, String ToolInterval, String ToolType, String GroupId,
                                        String lastGrade, String centerGrade, String sponsorId, String minCenterGrade) {
    }

    /*protected ModelMentorChat(Parcel in) {
        avatar = in.readString();
        chatId = in.readString();
        date = in.readString();
        fullName = in.readString();
        idChild = in.readString();
        //message = in.readString();
        //parentAvatar = in.readString();
        //parentFullName = in.readString();
        //parentMessage = in.readString();
        type = in.readInt();
        zTools = in.readString();
        zInterval = in.readString();
    }*/

    /*public static final Creator<ModelMentorChat> CREATOR = new Creator<ModelMentorChat>() {
        @Override
        public ModelMentorChat createFromParcel(Parcel in) {
            return new ModelMentorChat(in);
        }

        @Override
        public ModelMentorChat[] newArray(int size) {
            return new ModelMentorChat[size];
        }
    };*/

    protected ModelSponsorUsersListAdapter(Parcel in) {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelSponsorUsersListAdapter> CREATOR = new Creator<ModelSponsorUsersListAdapter>() {
        @Override
        public ModelSponsorUsersListAdapter createFromParcel(Parcel in) {
            return new ModelSponsorUsersListAdapter(in);
        }

        @Override
        public ModelSponsorUsersListAdapter[] newArray(int size) {
            return new ModelSponsorUsersListAdapter[size];
        }
    };


    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
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

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPhoneOrMail() {
        return PhoneOrMail;
    }

    public void setPhoneOrMail(String phoneOrMail) {
        PhoneOrMail = phoneOrMail;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
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

    public String getLastMessageDate() {
        return LastMessageDate;
    }

    public void setLastMessageDate(String lastMessageDate) {
        LastMessageDate = lastMessageDate;
    }

    public String getLastMessage() {
        return LastMessage;
    }

    public void setLastMessage(String lastMessage) {
        LastMessage = lastMessage;
    }

    public String getLastGrade() {
        return LastGrade;
    }

    public void setLastGrade(String lastGrade) {
        LastGrade = lastGrade;
    }

    public String getCenterGrade() {
        return CenterGrade;
    }

    public void setCenterGrade(String centerGrade) {
        CenterGrade = centerGrade;
    }

    public String getMinCenterGrade() {
        return MinCenterGrade;
    }

    public void setMinCenterGrade(String minCenterGrade) {
        MinCenterGrade = minCenterGrade;
    }

    public ArrayList<ModelGrades> getGrades() {
        return Grades;
    }

    public void setGrades(ArrayList<ModelGrades> grades) {
        Grades = grades;
    }

    public String getInterFormId() {
        return InterFormId;
    }

    public void setInterFormId(String interFormId) {
        InterFormId = interFormId;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public int getNoCheckCount() {
        return NoCheckCount;
    }

    public void setNoCheckCount(int noCheckCount) {
        NoCheckCount = noCheckCount;
    }

    public String getLessonId1() {
        return LessonId1;
    }

    public void setLessonId1(String lessonId1) {
        LessonId1 = lessonId1;
    }

    public String getLessonId2() {
        return LessonId2;
    }

    public void setLessonId2(String lessonId2) {
        LessonId2 = lessonId2;
    }

    public String getLessonId3() {
        return LessonId3;
    }

    public void setLessonId3(String lessonId3) {
        LessonId3 = lessonId3;
    }

    public String getLessonId4() {
        return LessonId4;
    }

    public void setLessonId4(String lessonId4) {
        LessonId4 = lessonId4;
    }

    public String getThresholdGrade() {
        return ThresholdGrade;
    }

    public void setThresholdGrade(String thresholdGrade) {
        ThresholdGrade = thresholdGrade;
    }

    public String getGradesLeft() {
        return GradesLeft;
    }

    public void setGradesLeft(String gradesLeft) {
        GradesLeft = gradesLeft;
    }

    public String getDaysLeft() {
        return DaysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        DaysLeft = daysLeft;
    }

    public String getAverageGrade() {
        return AverageGrade;
    }

    public void setAverageGrade(String averageGrade) {
        AverageGrade = averageGrade;
    }

    public String getChatId() {
        return ChatId;
    }

    public void setChatId(String chatId) {
        ChatId = chatId;
    }
}