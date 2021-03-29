package kz.tech.smartgrades.auth.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.auth.fragments.CodeFragment;
import kz.tech.smartgrades.auth.fragments.PasswordFragment;
import kz.tech.smartgrades.auth.fragments.SchoolRegisterOneFragment;
import kz.tech.smartgrades.auth.fragments.SchoolRegisterThreeFragment;
import kz.tech.smartgrades.auth.fragments.SchoolRegisterTwoFragment;

public class SchoolRegisterFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private SchoolRegisterOneFragment schoolRegisterOneFragment;
    private SchoolRegisterTwoFragment schoolRegisterTwoFragment;
    private SchoolRegisterThreeFragment schoolRegisterThreeFragment;
    private PasswordFragment passwordFragment;
    private CodeFragment codeFragment;

    public SchoolRegisterFragmentAdapter(FragmentManager fm) {
        super(fm);
        schoolRegisterOneFragment = new SchoolRegisterOneFragment();
        schoolRegisterTwoFragment = new SchoolRegisterTwoFragment();
        schoolRegisterThreeFragment = new SchoolRegisterThreeFragment();
        passwordFragment = new PasswordFragment();
        codeFragment = new CodeFragment();

        fragmentArrayList.add(schoolRegisterOneFragment);
        fragmentArrayList.add(schoolRegisterTwoFragment);
        fragmentArrayList.add(schoolRegisterThreeFragment);
        fragmentArrayList.add(passwordFragment);
        fragmentArrayList.add(codeFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
}
