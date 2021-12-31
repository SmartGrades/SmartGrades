package kz.tech.smartgrades.school.models;

public class ModelSimilar{

    private String Index;
    private String Avatar;
    private String FirstName;
    private String LastName;

    private String ClassId;
    private String ClassName;

    public String getIndex(){
        return Index;
    }
    public void setIndex(String index){
        Index = index;
    }
    public String getAvatar(){
        return Avatar;
    }
    public void setAvatar(String avatar){
        Avatar = avatar;
    }
    public String getFirstName(){
        return FirstName;
    }
    public void setFirstName(String firstName){
        FirstName = firstName;
    }
    public String getLastName(){
        return LastName;
    }
    public void setLastName(String lastName){
        LastName = lastName;
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
