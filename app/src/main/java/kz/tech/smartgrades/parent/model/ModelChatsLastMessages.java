package kz.tech.smartgrades.parent.model;

public class ModelChatsLastMessages {
    private String Avatar;
    private String FirstName;
    private String LastName;
    private String LastMessage;
    private String LastMessageDate;
    private int Answer;
    private boolean LastMessageCheck;

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
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

    public boolean isLastMessageCheck() {
        return LastMessageCheck;
    }

    public void setLastMessageCheck(boolean lastMessageCheck) {
        LastMessageCheck = lastMessageCheck;
    }

    public int getAnswer() {
        return Answer;
    }

    public void setAnswer(int answer) {
        Answer = answer;
    }
}
