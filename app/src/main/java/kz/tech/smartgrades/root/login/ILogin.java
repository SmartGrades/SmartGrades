package kz.tech.smartgrades.root.login;

import kz.tech.smartgrades.auth.models.ModelUserData;

public interface ILogin {
    void saveUserData(ModelUserData m);
    void updateUserData(LoginKey key, String value);
    String loadUserDate(LoginKey key);
    void deleteUserDate();
}
