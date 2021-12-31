package kz.tech.smartgrades.parent_add_child;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.parent_add_child.fragments.pacCodeFragment;
import kz.tech.smartgrades.parent_add_child.fragments.pacAvatarFragment;
import kz.tech.smartgrades.parent_add_child.fragments.pacBirthdayFragment;
import kz.tech.smartgrades.parent_add_child.fragments.pacFullNameFragment;
import kz.tech.smartgrades.parent_add_child.fragments.pacLoginFragment;
import kz.tech.smartgrades.parent_add_child.fragments.pacMailFragment;
import kz.tech.smartgrades.parent_add_child.fragments.pacPasswordFragment;
import kz.tech.smartgrades.parent_add_child.fragments.pacPhoneFragment;

public class ParentAddChildFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    //private ParentAddChildStartFragment addChild;
    private pacFullNameFragment fullName;
    private pacBirthdayFragment birthday;
    private pacPhoneFragment phone;
    private pacMailFragment mail;
    private pacLoginFragment login;
    //private ParentAddChildFamilyStatus familyStatus;
    private pacAvatarFragment avatarSelect;
    //private ParentHardwareAccessFragment hardwareAccess;
    private pacPasswordFragment password;
    private pacCodeFragment accessCode;


    public ParentAddChildFragmentAdapter(FragmentManager fm) {
        super(fm);
        //addChild = new ParentAddChildStartFragment();
        fullName = new pacFullNameFragment();
        birthday = new pacBirthdayFragment();
        login = new pacLoginFragment();
        //familyStatus = new ParentAddChildFamilyStatus();
        avatarSelect = new pacAvatarFragment();
        //hardwareAccess = new ParentHardwareAccessFragment();
        accessCode = new pacCodeFragment();
        password = new pacPasswordFragment();
        phone = new pacPhoneFragment();
        mail = new pacMailFragment();

        //fragmentArrayList.add(addChild);
        fragmentArrayList.add(fullName);
        fragmentArrayList.add(birthday);
        fragmentArrayList.add(phone);
        fragmentArrayList.add(mail);
        fragmentArrayList.add(login);
        fragmentArrayList.add(avatarSelect);
        //fragmentArrayList.add(hardwareAccess);
        fragmentArrayList.add(password);
        fragmentArrayList.add(accessCode);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void setPhoto(Bitmap decodeStream) {
        avatarSelect.setPhoto(decodeStream, true);
    }
}
