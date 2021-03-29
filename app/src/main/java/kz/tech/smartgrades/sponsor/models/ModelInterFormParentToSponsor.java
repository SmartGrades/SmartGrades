package kz.tech.smartgrades.sponsor.models;

import java.util.ArrayList;

import kz.tech.smartgrades.mentor.models.ModelMentorChat;

public class ModelInterFormParentToSponsor {

    private String Index;
    private String Date;
    private String SponsorId;
    private String ParentId;
    private String ChildId;
    private String LessonId1;
    private String LessonId2;
    private String LessonId3;
    private String LessonId4;
    private String AverageGrade;
    private String CountGrade;
    private String TimeStart;
    private String TimeStop;
    private String Answer;

    private String SponsorFirstName;
    private String SponsorLastName;
    private String SponsorAvatar;

    private String ParentFirstName;
    private String ParentLastName;
    private String ParentAvatar;

    private String ChildLogin;
    private String ChildFirstName;
    private String ChildLastName;
    private String ChildAvatar;

    private String ChatId;
    private String ChatLastMessage;
    private String ChatLastMessageDate;
    private int NoCheckCount;


    public ModelInterFormParentToSponsor() { }

    public ModelInterFormParentToSponsor(ArrayList<ModelMentorChat> childList, String idGroup, String name) {
    }

    public String getIndex() {
        return Index;
    }

    public void setIndex(String index) {
        Index = index;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSponsorId() {
        return SponsorId;
    }

    public void setSponsorId(String sponsorId) {
        SponsorId = sponsorId;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getChildId() {
        return ChildId;
    }

    public void setChildId(String childId) {
        ChildId = childId;
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

    public String getTimeStart() {
        return TimeStart;
    }

    public void setTimeStart(String timeStart) {
        TimeStart = timeStart;
    }

    public String getTimeStop() {
        return TimeStop;
    }

    public void setTimeStop(String timeStop) {
        TimeStop = timeStop;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getSponsorFirstName() {
        return SponsorFirstName;
    }

    public void setSponsorFirstName(String sponsorFirstName) {
        SponsorFirstName = sponsorFirstName;
    }

    public String getSponsorLastName() {
        return SponsorLastName;
    }

    public void setSponsorLastName(String sponsorLastName) {
        SponsorLastName = sponsorLastName;
    }

    public String getSponsorAvatar() {
        return SponsorAvatar;
    }

    public void setSponsorAvatar(String sponsorAvatar) {
        SponsorAvatar = sponsorAvatar;
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

    public String getParentAvatar() {
        return ParentAvatar;
    }

    public void setParentAvatar(String parentAvatar) {
        ParentAvatar = parentAvatar;
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

    public String getChildAvatar() {
        return ChildAvatar;
    }

    public void setChildAvatar(String childAvatar) {
        ChildAvatar = childAvatar;
    }

    public String getChatId() {
        return ChatId;
    }

    public void setChatId(String chatId) {
        ChatId = chatId;
    }

    public String getChatLastMessage() {
        return ChatLastMessage;
    }

    public void setChatLastMessage(String chatLastMessage) {
        ChatLastMessage = chatLastMessage;
    }

    public String getChatLastMessageDate() {
        return ChatLastMessageDate;
    }

    public void setChatLastMessageDate(String chatLastMessageDate) {
        ChatLastMessageDate = chatLastMessageDate;
    }

    public int getNoCheckCount() {
        return NoCheckCount;
    }

    public void setNoCheckCount(int noCheckCount) {
        NoCheckCount = noCheckCount;
    }

    public String getChildLogin() {
        return ChildLogin;
    }

    public void setChildLogin(String childLogin) {
        ChildLogin = childLogin;
    }
}
