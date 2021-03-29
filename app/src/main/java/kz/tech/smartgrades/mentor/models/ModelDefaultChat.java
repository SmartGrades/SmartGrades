package kz.tech.smartgrades.mentor.models;

public class ModelDefaultChat {

    private String SourceId;
    private String SourceAvatar;
    private String SourceFirstName;
    private String SourceLastName;

    private String TargetId;
    private String TargetAvatar;
    private String TargetFirstName;
    private String TargetLastName;

    private String ChildId;

    private String ChatId;
    private String MessageId;
    private String Message;
    private String Date;
    private int Type;
    private int NoCheckCount;

    private String GradeId;
    private String InterFormId;


    public ModelDefaultChat() {}

    public String getChatId() {
        return ChatId;
    }

    public void setChatId(String chatId) {
        ChatId = chatId;
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

    public String getChildId() {
        return ChildId;
    }

    public void setChildId(String childId) {
        ChildId = childId;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getNoCheckCount() {
        return NoCheckCount;
    }

    public void setNoCheckCount(int noCheckCount) {
        NoCheckCount = noCheckCount;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSourceLastName() {
        return SourceLastName;
    }

    public void setSourceLastName(String sourceLastName) {
        SourceLastName = sourceLastName;
    }

    public String getTargetLastName() {
        return TargetLastName;
    }

    public void setTargetLastName(String targetLastName) {
        TargetLastName = targetLastName;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getGradeId() {
        return GradeId;
    }

    public void setGradeId(String gradeId) {
        GradeId = gradeId;
    }

    public String getInterFormId() {
        return InterFormId;
    }

    public void setInterFormId(String interFormId) {
        InterFormId = interFormId;
    }
}