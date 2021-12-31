package kz.tech.smartgrades.auth.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kz.tech.smartgrades.mentor.models.ModelSchoolList;

public class ModelUser implements Parcelable {

    private String Id;
    private String Avatar;
    private String AvatarOriginal;
    private String Birthday;
    private String FirstName;
    private String LastName;
    private String UserId;
    private String SchoolId;
    private String Login;
    private String Phone;
    private String Mail;
    private String PhoneOrMail;
    private String Age;
    private String AboutMe;
    private String Description;
    private String Type;
    private String Token;

    private String Name;
    private String Address;
    private String Site;

    private String Code;
    private String Password;

    private String FamilyGroupId;
    private String ParentId;

    private String State;
    private String PublicKey;

    private ArrayList<ModelSchoolList> SchoolList;

    public ModelUser() { }

    public ModelUser(String Avatar, String Birthday, String FirstName, String LastName, String UserId, String Login, String Phone, String Type, String Code, String Password) {
        this.Avatar = Avatar;
        this.Birthday = Birthday;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.UserId = UserId;
        this.Login = Login;
        this.Phone = Phone;
        this.Code = Code;
        this.Password = Password;
        this.Type = Type;
    }


    protected ModelUser(Parcel in) {
        Avatar = in.readString();
        Birthday = in.readString();
        FirstName = in.readString();
        LastName = in.readString();
        UserId = in.readString();
        Login = in.readString();
        Phone = in.readString();
        Type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Avatar);
        dest.writeString(Birthday);
        dest.writeString(FirstName);
        dest.writeString(LastName);
        dest.writeString(UserId);
        dest.writeString(Login);
        dest.writeString(Phone);
        dest.writeString(Type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelUser> CREATOR = new Creator<ModelUser>() {
        @Override
        public ModelUser createFromParcel(Parcel in) {
            return new ModelUser(in);
        }

        @Override
        public ModelUser[] newArray(int size) {
            return new ModelUser[size];
        }
    };

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }
    public String getAvatar() {
        return Avatar;
    }

    public void setBirthday(String DateBirthday) {
        this.Birthday = DateBirthday;
    }
    public String getBirthday() {
        return Birthday;
    }

    public void setFirstName(String FullName) {
        this.FirstName = FullName;
    }
    public String getFirstName() {
        return FirstName;
    }

    public void setUserId(String Id) {
        this.UserId = Id;
    }
    public String getUserId() {
        return UserId;
    }

    public void setLogin(String Login) {
        this.Login = Login;
    }
    public String getLogin() {
        return Login;
    }

    public void setPhone(String PhoneOrMail) {
        this.Phone = PhoneOrMail;
    }
    public String getPhone() {
        return Phone;
    }

    public void setCode(String code) {
        this.Code = code;
    }
    public String getCode() {
        return Code;
    }

    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        this.Password = password;
    }

    public String getType() {
        return Type;
    }
    public void setType(String type) {
        Type = type;
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

    public String getFamilyGroupId() {
        return FamilyGroupId;
    }

    public void setFamilyGroupId(String familyGroupId) {
        FamilyGroupId = familyGroupId;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getAvatarOriginal() {
        return AvatarOriginal;
    }

    public void setAvatarOriginal(String avatarOriginal) {
        AvatarOriginal = avatarOriginal;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
    public ArrayList<ModelSchoolList> getSchoolList(){
        return SchoolList;
    }
    public void setSchoolList(ArrayList<ModelSchoolList> schoolList){
        SchoolList = schoolList;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPublicKey() {
        return PublicKey;
    }

    public void setPublicKey(String publicKey) {
        PublicKey = publicKey;
    }

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String schoolId) {
        SchoolId = schoolId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
    public String getPhoneOrMail() {
        return PhoneOrMail;
    }
    public void setPhoneOrMail(String phoneOrMail) {
        PhoneOrMail = phoneOrMail;
    }
    public String getAge() {
        return Age;
    }
    public void setAge(String age) {
        Age = age;
    }
}