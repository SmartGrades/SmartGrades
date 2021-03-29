package kz.tech.smartgrades.auth.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.auth.fragments.CodeFragment;
import kz.tech.smartgrades.auth.fragments.AvatarFragment;
import kz.tech.smartgrades.auth.fragments.BirthdayFragment;
import kz.tech.smartgrades.auth.fragments.MailFragment;
import kz.tech.smartgrades.auth.fragments.FullNameFragment;
import kz.tech.smartgrades.auth.fragments.LoginFragment;
import kz.tech.smartgrades.auth.fragments.PhoneFragment;
import kz.tech.smartgrades.auth.fragments.PasswordFragment;

public class UserRegisterFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private PhoneFragment phone;
    private MailFragment mail;
    private LoginFragment login;
    private FullNameFragment fullName;
    private AvatarFragment avatar;
    private BirthdayFragment birthday;
    private PasswordFragment password;
    private CodeFragment accessCode;


    public UserRegisterFragmentAdapter(FragmentManager fm) {
        super(fm);
        phone = new PhoneFragment();
        mail = new MailFragment();
        fullName = new FullNameFragment();
        avatar = new AvatarFragment();
        birthday = new BirthdayFragment();
        login = new LoginFragment();
        password = new PasswordFragment();
        accessCode = new CodeFragment();

        fragmentArrayList.add(phone);
        fragmentArrayList.add(mail);
        fragmentArrayList.add(login);
        fragmentArrayList.add(fullName);
        fragmentArrayList.add(avatar);
        fragmentArrayList.add(birthday);
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

    public void setType(String Type){
        avatar.setType(Type);
    }
}
