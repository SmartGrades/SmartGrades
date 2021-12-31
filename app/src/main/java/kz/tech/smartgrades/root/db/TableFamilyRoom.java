package kz.tech.smartgrades.root.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "familyRoom")
public class TableFamilyRoom {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String avatar;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] data;

    public String idFamilyRoom;

    public String login;

    public String mail;

    public String name;

    public String password;

    public String pin;

    public String timestamp;

    public String title;

    public String typeAccount;

    public String idLogin;

    public TableFamilyRoom() {}

    public TableFamilyRoom(String avatar, byte[] data, String idFamilyRoom, String login, String mail,
                           String name, String password, String pin, String timestamp, String title,
                           String typeAccount, String idLogin) {
        this.avatar = avatar;
        this.data = data;
        this.idFamilyRoom = idFamilyRoom;
        this.login = login;
        this.mail = mail;
        this.name = name;
        this.password = password;
        this.pin = pin;
        this.timestamp = timestamp;
        this.title = title;
        this.typeAccount = typeAccount;
        this.idLogin = idLogin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getIdFamilyRoom() {
        return idFamilyRoom;
    }

    public void setIdFamilyRoom(String idFamilyRoom) {
        this.idFamilyRoom = idFamilyRoom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public String getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(String idLogin) {
        this.idLogin = idLogin;
    }
}

