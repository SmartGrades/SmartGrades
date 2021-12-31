package kz.tech.smartgrades.auth.adapters;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.fragments.AvatarFragment;
import kz.tech.smartgrades.auth.fragments.BirthdayFragment;
import kz.tech.smartgrades.auth.fragments.CodeFragment;
import kz.tech.smartgrades.auth.fragments.FullNameFragment;
import kz.tech.smartgrades.auth.fragments.LoginFragment;
import kz.tech.smartgrades.auth.fragments.MailFragment;
import kz.tech.smartgrades.auth.fragments.PasswordFragment;
import kz.tech.smartgrades.auth.fragments.PhoneFragment;
import kz.tech.smartgrades.auth.fragments.ProfileFragment;
import kz.tech.smartgrades.auth.fragments.SuccessFragment;
import kz.tech.smartgrades.auth.fragments.TypeFragment;

public class UserRegisterFragmentAdapterV2 extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ProfileFragment profileFragment;
    private final SuccessFragment successFragment;


    public UserRegisterFragmentAdapterV2(ViewPager viewPager, FragmentManager fm) {
        super(fm);
        TypeFragment typeFragment = new TypeFragment();
        profileFragment = new ProfileFragment();
        successFragment = new SuccessFragment();

        fragmentArrayList.add(typeFragment);
        fragmentArrayList.add(profileFragment);
        fragmentArrayList.add(successFragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }
    public ArrayList<Fragment> getItems()  {
        return fragmentArrayList;
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void onSetType(String Type){
        profileFragment.onSetType(Type);
        successFragment.setType(Type);
    }


}
