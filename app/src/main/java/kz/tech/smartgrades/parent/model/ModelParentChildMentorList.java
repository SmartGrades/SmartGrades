package kz.tech.smartgrades.parent.model;

import java.util.ArrayList;

public class ModelParentChildMentorList {

    private String MentorId;
    private String MentorAvatar;
    private String MentorFirstName;
    private String MentorLastName;
    private String MentorLogin;

    private String LessonId;
    private String LessonName;


    public String getMentorId() {
        return MentorId;
    }

    public void setMentorId(String mentorId) {
        MentorId = mentorId;
    }

    public String getMentorAvatar() {
        return MentorAvatar;
    }

    public void setMentorAvatar(String mentorAvatar) {
        MentorAvatar = mentorAvatar;
    }

    public String getMentorFirstName() {
        return MentorFirstName;
    }

    public void setMentorFirstName(String mentorFirstName) {
        MentorFirstName = mentorFirstName;
    }

    public String getMentorLastName() {
        return MentorLastName;
    }

    public void setMentorLastName(String mentorLastName) {
        MentorLastName = mentorLastName;
    }

    public String getMentorLogin() {
        return MentorLogin;
    }

    public void setMentorLogin(String mentorLogin) {
        MentorLogin = mentorLogin;
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
}
