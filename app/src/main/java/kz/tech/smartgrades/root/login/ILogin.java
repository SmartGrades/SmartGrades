package kz.tech.smartgrades.root.login;

import kz.tech.smartgrades.auth.models.ModelRegisterData;
import kz.tech.smartgrades.auth.models.ModelUser;

public interface ILogin {
    void saveUserData(ModelUser m);
    void saveUserData(ModelRegisterData m);
    void updateUserData(LoginKey key, String value);
    String loadUserDate(LoginKey key);
    void deleteUserDate();
}
