package kz.tech.smartgrades.root.login;

import android.content.Context;
import android.content.SharedPreferences;

import kz.tech.smartgrades.auth.models.ModelUserData;

public class LoginUser implements ILogin {

    private Context context;
    private static final String SP_USER_DATA = "SP_USER_DATA";

    public LoginUser(Context context) {
        this.context = context;
    }

    @Override
    public void saveUserData(ModelUserData m) {
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
        editor.putString("AboutMe", m.getAboutMe());
        editor.putString("Description", m.getDescription());
        editor.putString("Type", m.getType());
        editor.putString("Name", m.getName());
        editor.putString("Address", m.getAddress());
        editor.putString("Site", m.getSite());
        editor.apply();
    }
    @Override
    public void updateUserData(LoginKey key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_USER_DATA, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (key) {
            case AVATAR:
                editor.putString("Avatar", value);
                break;
            case ABOUT_ME:
                editor.putString("AboutMe", value);
                break;
            case DESCRIPTION:
                editor.putString("Description", value);
                break;
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
            case ABOUT_ME: user = settings.getString("AboutMe", null); break;
            case DESCRIPTION: user = settings.getString("Description", null); break;
            case NAME: user = settings.getString("Name", null); break;
            case ADDRESS: user = settings.getString("Address", null); break;
            case SITE: user = settings.getString("Site", null); break;
            case TYPE: user = settings.getString("Type", null); break;
            default: user = null; break;
        }
        return user;
    }
    @Override
    public void deleteUserDate() {
        context.getSharedPreferences(SP_USER_DATA, 0).edit().clear().apply();
    }
}