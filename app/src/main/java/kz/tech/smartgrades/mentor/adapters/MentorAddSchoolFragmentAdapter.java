package kz.tech.smartgrades.mentor.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.mentor.fragments.MentorAddSchoolListFragment;
import kz.tech.smartgrades.mentor.fragments.MentorAddSchoolProfileFragment;
import kz.tech.smartgrades.school.models.ModelSchoolData;

public class MentorAddSchoolFragmentAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private MentorAddSchoolListFragment addSchoolListFragment;
    private MentorAddSchoolProfileFragment addSchoolProfileFragment;

    public MentorAddSchoolFragmentAdapter(FragmentManager fm){
        super(fm);
        addSchoolListFragment = new MentorAddSchoolListFragment();
        addSchoolProfileFragment = new MentorAddSchoolProfileFragment();
        fragmentList.add(addSchoolListFragment);
        fragmentList.add(addSchoolProfileFragment);
    }

    @Override
    public Fragment getItem(int position){
        return fragmentList.get(position);
    }
    @Override
    public int getCount(){
        return fragmentList.size();
    }

    public void setSelectSchool(ModelSchoolData m){
        if(addSchoolProfileFragment != null)
            addSchoolProfileFragment.setSelectSchool(m);
    }
}
