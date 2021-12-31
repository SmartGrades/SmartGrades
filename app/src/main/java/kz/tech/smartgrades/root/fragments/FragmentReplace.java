package kz.tech.smartgrades.root.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import kz.tech.smartgrades.authentication.AuthenticationActivity;
import kz.tech.smartgrades.family_room.FamilyRoomActivity;


public class FragmentReplace extends FragmentSelect implements IFragments{
    public FragmentReplace() { }
    AppCompatActivity activity;

    @Override
    public void initAuthenticationActivity(AuthenticationActivity activity) {
        clearActivity();
        this.activity = activity;
    }

    @Override
    public void initFamilyRoomActivity(FamilyRoomActivity activity) {
        clearActivity();
        this.activity = activity;
    }


    @Override
    public void onReplaceFragment(String nameFragment, int layout) {
        Fragment fragment = null;
        if (nameFragment == null) { return; }

        fragment = getFragment(nameFragment);
        if (fragment == null) { return; }

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(layout, fragment);
        fragmentTransaction.commit();
    }


    private void clearActivity() {
        if (activity != null) { activity = null; }
    }
}
