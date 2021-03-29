package kz.tech.smartgrades.school.models;

import java.util.ArrayList;
public class ModelRequestMessages{

    private String Date;
    private String MessageId;
    private String Content;
    private int Type;
    private String ChatId;
    private String InterFormId;
    private String ClassId;
    private String ClassName;

    private String SourceId;
    private String SourceAvatar;
    private String SourceFirstName;
    private String SourceLastName;

    private String ChildId;
    private String ChildAvatar;
    private String ChildFirstName;
    private String ChildLastName;

    private ArrayList<ModelSimilar> Similares;

    public String getDate(){
        return Date;
    }
    public void setDate(String date){
        Date = date;
    }
    public String getMessageId(){
        return MessageId;
    }
    public void setMessageId(String messageId){
        MessageId = messageId;
    }
    public String getContent(){
        return Content;
    }
    public void setContent(String content){
        Content = content;
    }
    public int getType(){
        return Type;
    }
    public void setType(int type){
        Type = type;
    }
    public String getChatId(){
        return ChatId;
    }
    public void setChatId(String chatId){
        ChatId = chatId;
    }
    public String getInterFormId(){
        return InterFormId;
    }
    public void setInterFormId(String interFormId){
        InterFormId = interFormId;
    }
    public String getSourceId(){
        return SourceId;
    }
    public void setSourceId(String sourceId){
        SourceId = sourceId;
    }
    public String getSourceAvatar(){
        return SourceAvatar;
    }
    public void setSourceAvatar(String sourceAvatar){
        SourceAvatar = sourceAvatar;
    }
    public String getSourceFirstName(){
        return SourceFirstName;
    }
    public void setSourceFirstName(String sourceFirstName){
        SourceFirstName = sourceFirstName;
    }
    public String getSourceLastName(){
        return SourceLastName;
    }
    public void setSourceLastName(String sourceLastName){
        SourceLastName = sourceLastName;
    }
    public String getChildId(){
        return ChildId;
    }
    public void setChildId(String childId){
        ChildId = childId;
    }
    public String getChildAvatar(){
        return ChildAvatar;
    }
    public void setChildAvatar(String childAvatar){
        ChildAvatar = childAvatar;
    }
    public String getChildFirstName(){
        return ChildFirstName;
    }
    public void setChildFirstName(String childFirstName){
        ChildFirstName = childFirstName;
    }
    public String getChildLastName(){
        return ChildLastName;
    }
    public void setChildLastName(String childLastName){
        ChildLastName = childLastName;
    }
    public ArrayList<ModelSimilar> getSimilares(){
        return Similares;
    }
    public void setSimilares(ArrayList<ModelSimilar> similares){
        Similares = similares;
    }
    public String getClassId(){
        return ClassId;
    }
    public void setClassId(String classId){
        ClassId = classId;
    }
    public String getClassName(){
        return ClassName;
    }
    public void setClassName(String className){
        ClassName = className;
    }
}
