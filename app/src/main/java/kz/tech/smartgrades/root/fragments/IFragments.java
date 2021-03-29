package kz.tech.smartgrades.root.fragments;

import kz.tech.smartgrades.authentication.AuthenticationActivity;
import kz.tech.smartgrades.family_room.FamilyRoomActivity;

public interface IFragments {
    void initAuthenticationActivity(AuthenticationActivity activity);
    void initFamilyRoomActivity(FamilyRoomActivity activity);
    void onReplaceFragment(String nameFragment, int layout);
}
