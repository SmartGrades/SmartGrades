package kz.tech.smartgrades.root.prefs;

public interface IPreferences {
    boolean isLoginStatus();
    //  PREFS STORAGE NAME
    void onSaveLogin(String id, String mail, String name, String password, String typeAccount);
    void onRemoveLogin();
    String getLoginData(String s);

    //  PREFS STORAGE FAMILY ACCOUNT
    void onSaveFamilyAccount(String avatar, String name, String pin, String status, String id);
    String[] onLoadFamilyAccount();
    void onRemoveFamilyAccount();
    void initLogin();
    String getFamilyData(String s);
    void setFamilyData(String s, String t);
    void initFamilyAccount();

    //  PREFS STORAGE MENTOR ACCOUNT
    void onSaveMentorAccount(String avatar, String name);
    String[] onLoadMentorAccount();
    void onRemoveMentorAccount();
    String getMentorData(String s);

    //  PREFS CURRENT PAGE
    void onSaveCurrentPage(String page);
    String onLoadCurrentPage();
    void onRemoveCurrentPage();
    boolean onEqualCurrentPage();

    //  PREFS LOCK APP LIST SELECT
    void onSaveAppLockList(String name);
    String onLoadAppLockList();
    void onRemoveAppLockList();

    //  PREFS CURRENT PAGE MAIN COINS
    void onSaveCurrentPageMainCoins(int pageCoins);
    int onLoadCurrentPageMainCoins();
    void onRemoveCurrentPageMainCoins();


}
