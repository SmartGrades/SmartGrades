package kz.tech.smartgrades.root.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences implements IPreferences {
    private static final String PREFS_STORAGE_LOGIN = "PREFS_STORAGE_LOGIN";
    private static final String PREFS_STORAGE_FAMILY_ACCOUNT = "PREFS_STORAGE_FAMILY_ACCOUNT";
    private static final String PREFS_STORAGE_MENTOR_ACCOUNT = "PREFS_STORAGE_MENTOR_ACCOUNT";
    private static final String PREFS_CURRENT_PAGE = "PREFS_CURRENT_PAGE";
    private static final String PREFS_APP_LOCK_LIST_SELECT = "PREFS_APP_LOCK_LIST_SELECT";
    private static final String PREFS_CURRENT_PAGE_MAIN_COINS = "PREFS_CURRENT_PAGE_MAIN_COINS";
    private Context context;
    private String id, mail, name, password, typeAccount;
    private String fAvatar, fName, fQuickAccessCode, fStatus, fId;
    private String mAvatar, mName;
    public Preferences(Context context) {
        this.context = context;
        initLogin();
        onSelectAccountType(typeAccount);
    }
    @Override
    public boolean isLoginStatus() {
        if (id != null && !id.equals("")) { return true;
        } else if (id == null) { return false; }
        return false;
    }

    ///////////////////     PREFS STORAGE LOGIN NAME          ////////////////////
    @Override
    public void onSaveLogin(String s1, String s2, String s3, String s4, String s5) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_STORAGE_LOGIN, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", s1);
        editor.putString("mail", s2);
        editor.putString("name", s3);
        editor.putString("password", s4);
        editor.putString("typeAccount", s5);
        editor.apply();
    }
    private String[] onLoadLogin() {
        String[] strings = new String[5];
        SharedPreferences settings = context.getSharedPreferences(PREFS_STORAGE_LOGIN, 0);
        strings[0] = settings.getString("id", null);
        strings[1] = settings.getString("mail", null);
        strings[2] = settings.getString("name", null);
        strings[3] = settings.getString("password", null);
        strings[4] = settings.getString("typeAccount", null);
        return strings;
    }
    @Override
    public void onRemoveLogin() {
        context.getSharedPreferences(PREFS_STORAGE_LOGIN, 0).edit().clear().apply();
    }
    @Override
    public void initLogin() {
        String[] arrLogin = onLoadLogin();
        id = arrLogin[0];
        mail = arrLogin[1];
        name = arrLogin[2];
        password = arrLogin[3];
        typeAccount = arrLogin[4];
    }
    @Override
    public String getLoginData(String s) {
        switch (s) {
            case "id": return id;
            case "mail": return mail;
            case "name": return name;
            case "password": return password;
            case "typeAccount": return typeAccount; }
        return null;
    }
    ////////////////////    PREFS STORAGE FAMILY ACCOUNT       ////////////////////
    @Override
    public void onSaveFamilyAccount(String s1, String s2, String s3, String s4, String s5) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_STORAGE_FAMILY_ACCOUNT, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("avatar", s1);
        editor.putString("name", s2);
        editor.putString("quickAccessCode", s3);
        editor.putString("status", s4);
        editor.putString("id", s5);
        editor.apply();
    }
    @Override
    public String[] onLoadFamilyAccount() {
        String[] strings = new String[5];
        SharedPreferences settings = context.getSharedPreferences(PREFS_STORAGE_FAMILY_ACCOUNT, 0);
        strings[0] = settings.getString("avatar", "");
        strings[1] = settings.getString("name", "");
        strings[2] = settings.getString("quickAccessCode", "");
        strings[3] = settings.getString("status", "");
        strings[4] = settings.getString("id", "");
        return strings;
    }
    @Override
    public void onRemoveFamilyAccount() {
        context.getSharedPreferences(PREFS_STORAGE_FAMILY_ACCOUNT, 0).edit().clear().apply();
    }
    @Override
    public String getFamilyData(String s) {
        switch (s) {
            case "avatar": return fAvatar;
            case "name": return fName;
            case "quickAccessCode": return fQuickAccessCode;
            case "status": return fStatus;
            case "id": return fId; }
        return null;
    }
    @Override
    public void setFamilyData(String s, String t) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_STORAGE_FAMILY_ACCOUNT, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (s) {
            case "avatar": editor.putString("avatar", t); fAvatar = t; break;
            case "name": editor.putString("name", t); fName = t; break;
            case "quickAccessCode": editor.putString("quickAccessCode", t); fQuickAccessCode = t; break;
            case "status": editor.putString("status", t); fStatus = t; break;
            case "id": editor.putString("id", t); fId = t; break; }
        editor.apply();
    }
    @Override
    public void initFamilyAccount() {
        onSelectAccountType("parent");
    }
    ////////////////////  PREFS STORAGE MENTOR ACCOUNT  ////////////////////
    @Override
    public void onSaveMentorAccount(String avatar, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_STORAGE_MENTOR_ACCOUNT, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("avatar", avatar);
        editor.putString("name", name);
        editor.apply();
    }
    @Override
    public String[] onLoadMentorAccount() {
        String[] strings = new String[5];
        SharedPreferences settings = context.getSharedPreferences(PREFS_STORAGE_MENTOR_ACCOUNT, 0);
        strings[0] = settings.getString("avatar", null);
        strings[1] = settings.getString("name", null);
        return strings;
    }
    @Override
    public void onRemoveMentorAccount() {
        context.getSharedPreferences(PREFS_STORAGE_MENTOR_ACCOUNT, 0).edit().clear().apply();
    }
    @Override
    public String getMentorData(String s) {
        switch (s) {
            case "avatar": return mAvatar;
            case "name": return mName; }
        return null;
    }
    ////////////////////    PREFS CURRENT PAGE       ////////////////////
    @Override
    public void onSaveCurrentPage(String page) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_CURRENT_PAGE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("page", page);
        editor.apply();
    }
    @Override
    public String onLoadCurrentPage() {
        SharedPreferences settings = context.getSharedPreferences(PREFS_CURRENT_PAGE, 0);
        return settings.getString("page", "");
    }
    @Override
    public void onRemoveCurrentPage() {
        context.getSharedPreferences(PREFS_CURRENT_PAGE, 0).edit().clear().apply();
    }
    @Override
    public boolean onEqualCurrentPage() {
        if (onLoadCurrentPage() != null) {
            if (!onLoadCurrentPage().equals("")) {
                String s = onLoadCurrentPage();
                if (s.equals("AddUserFragment") || s.equals("FamilyMemberFragment") || s.equals("DeviceGroupFragment")
                        || s.equals("ReportsFragment") || s.equals("SettingsParentFragment") || s.equals("AboutAppFragment")
                        || s.equals("ConcludeContractsFragments") || s.equals("PersonalDataFragment") || s.equals("TimeChildrenFragment")
                        || s.equals("AutoChargeFragment") || s.equals("NotificationFragment") || s.equals("LocalityFragment")
                        || s.equals("ChangePinCodeFragment") || s.equals("ChangePasswordFragment") || s.equals("FamilyGroupFragment")) {
                    return true;
                }
            }
        }
        return false;
    }
    ///////////////////      PREFS LOCK APP LIST SELECT      ////////////////////
    @Override
    public void onSaveAppLockList(String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_APP_LOCK_LIST_SELECT, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.apply();
    }
    @Override
    public String onLoadAppLockList() {
        SharedPreferences settings = context.getSharedPreferences(PREFS_APP_LOCK_LIST_SELECT, 0);
        return settings.getString("name", "standardList");
    }
    @Override
    public void onRemoveAppLockList() {
        context.getSharedPreferences(PREFS_APP_LOCK_LIST_SELECT, 0).edit().clear().apply();
    }
    //////////////////        PREFS CURRENT PAGE MAIN COINS       ////////////////////////
    @Override
    public void onSaveCurrentPageMainCoins(int pageCoins) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_CURRENT_PAGE_MAIN_COINS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("pageCoins", pageCoins);
        editor.apply();
    }
    @Override
    public int onLoadCurrentPageMainCoins() {
        SharedPreferences settings = context.getSharedPreferences(PREFS_CURRENT_PAGE_MAIN_COINS, 0);
        return settings.getInt("pageCoins", 0);
    }
    @Override
    public void onRemoveCurrentPageMainCoins() {
        context.getSharedPreferences(PREFS_CURRENT_PAGE_MAIN_COINS, 0).edit().clear().apply();
    }






    ////////           SELECT ACCOUNT TYPE         //////////////
    private void onSelectAccountType(String typeAccount) {
        if (typeAccount != null) {
            switch (typeAccount) {
                case "parent":
                    String[] array = onLoadFamilyAccount();
                    fAvatar = array[0];
                    fName = array[1];
                    fQuickAccessCode = array[2];
                    fStatus = array[3];
                    fId = array[4];
                    break;
                case "mentor":
                    String[] arr = onLoadMentorAccount();
                    mAvatar = arr[0];
                    mName = arr[1];
                    break;
            }
        }
    }
}
