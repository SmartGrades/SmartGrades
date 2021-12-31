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
import kz.tech.smartgrades.auth.fragments.SuccessFragment;

public class SchoolRegisterFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private SchoolRegisterOneFragment schoolRegisterOneFragment;
    private SchoolRegisterTwoFragment schoolRegisterTwoFragment;
    private SchoolRegisterThreeFragment schoolRegisterThreeFragment;
    private PasswordFragment passwordFragment;
    private CodeFragment codeFragment;
    private SuccessFragment successFragment;

    public SchoolRegisterFragmentAdapter(FragmentManager fm) {
        super(fm);
        schoolRegisterOneFragment = new SchoolRegisterOneFragment();
        schoolRegisterTwoFragment = new SchoolRegisterTwoFragment();
        schoolRegisterThreeFragment = new SchoolRegisterThreeFragment();
        passwordFragment = new PasswordFragment();
        codeFragment = new CodeFragment();
        successFragment = new SuccessFragment();

        fragmentArrayList.add(schoolRegisterOneFragment);
        fragmentArrayList.add(schoolRegisterTwoFragment);
        fragmentArrayList.add(schoolRegisterThreeFragment);
        fragmentArrayList.add(passwordFragment);
        fragmentArrayList.add(codeFragment);
        fragmentArrayList.add(successFragment);
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
        successFragment.setType(Type);
    }
}
