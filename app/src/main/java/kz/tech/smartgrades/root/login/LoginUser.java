package kz.tech.smartgrades.root.login;

import android.content.Context;
import android.content.SharedPreferences;

import kz.tech.smartgrades.auth.models.ModelRegisterData;
import kz.tech.smartgrades.auth.models.ModelUser;

public class LoginUser implements ILogin {

    private Context context;
    private static final String SP_USER_DATA = "SP_USER_DATA";


    public LoginUser(Context context) {
        this.context = context;
    }
    @Override
    public void saveUserData(ModelUser m) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_USER_DATA, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Code", m.getCode());
        editor.putString("Avatar", m.getAvatar());
        editor.putString("Birthday", m.getBirthday());
        editor.putString("FirstName", m.getFirstName());
        editor.putString("LastName", m.getLastName());
        editor.putString("UserId", m.getUserId());
        editor.putString("Login", m.getLogin());
        editor.putString("Password", m.getPassword());
        editor.putString("Phone", m.getPhone());
        editor.putString("Mail", m.getMail());
        editor.putString("PhoneOrMail", m.getPhoneOrMail());
        editor.putString("AboutMe", m.getAboutMe());
        editor.putString("Description", m.getDescription());
        editor.putString("Type", m.getType());
        editor.putString("Name", m.getName());
        editor.putString("Address", m.getAddress());
        editor.putString("Site", m.getSite());
        editor.putString("Token", m.getToken());
        editor.apply();
    }
    @Override
    public void saveUserData(ModelRegisterData m) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_USER_DATA, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PhoneOrMail", m.getPhoneOrMail());
        editor.putString("Password", m.getPassword());
        editor.apply();
    }
    @Override
    public void updateUserData(LoginKey key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_USER_DATA, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (key) {
            case AVATAR: editor.putString("Avatar", value); break;
            case BIRTHDAY: editor.putString("Birthday", value); break;
            case FIRST_NAME: editor.putString("FirstName", value); break;
            case LAST_NAME: editor.putString("LastName", value); break;
            case ID: editor.putString("UserId", value); break;
            case LOGIN: editor.putString("Login", value); break;
            case PHONE: editor.putString("Phone", value); break;
            case MAIL: editor.putString("Mail", value); break;
            case ABOUT_ME: editor.putString("AboutMe", value); break;
            case DESCRIPTION: editor.putString("Description", value); break;
            case NAME: editor.putString("Name", value); break;
            case ADDRESS: editor.putString("Address", value); break;
            case SITE: editor.putString("Site", value); break;
            case TYPE: editor.putString("Type", value); break;
            case PASSWORD: editor.putString("Password", value); break;
            case TOKEN: editor.putString("Token", value); break;
        }
        editor.apply();
    }
    @Override
    public String loadUserDate(LoginKey key) {
        SharedPreferences settings = context.getSharedPreferences(SP_USER_DATA, 0);
        String user = null;
        switch (key) {
            case AVATAR: user = settings.getString("Avatar", null); break;
            case BIRTHDAY: user = settings.getString("Birthday", null); break;
            case FIRST_NAME: user = settings.getString("FirstName", null); break;
            case LAST_NAME: user = settings.getString("LastName", null); break;
            case ID: user = settings.getString("UserId", null); break;
            case LOGIN: user = settings.getString("Login", null); break;
            case PHONE: user = settings.getString("Phone", null); break;
            case MAIL: user = settings.getString("Mail", null); break;
            case PHONE_OR_MAIL: user = settings.getString("PhoneOrMail", null); break;
            case ABOUT_ME: user = settings.getString("AboutMe", null); break;
            case DESCRIPTION: user = settings.getString("Description", null); break;
            case NAME: user = settings.getString("Name", null); break;
            case ADDRESS: user = settings.getString("Address", null); break;
            case SITE: user = settings.getString("Site", null); break;
            case TYPE: user = settings.getString("Type", null); break;
            case PASSWORD: user = settings.getString("Password", null); break;
            case TOKEN: user = settings.getString("Token", null); break;
            default: user = null; break;
        }
        return user;
    }
    @Override
    public void deleteUserDate() {
        context.getSharedPreferences(SP_USER_DATA, 0).edit().clear().apply();
    }
}