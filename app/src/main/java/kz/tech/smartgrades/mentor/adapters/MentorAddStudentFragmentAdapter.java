package kz.tech.smartgrades.mentor.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.mentor.fragments.MentorAddStudentListFragment;
import kz.tech.smartgrades.mentor.fragments.MentorAddStudentProfileFragment;
import kz.tech.smartgrades.mentor.models.ModelMentorData;

public class MentorAddStudentFragmentAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    private MentorAddStudentListFragment AddStudentListFragment;
    private MentorAddStudentProfileFragment AddStudentProfileFragment;


    public MentorAddStudentFragmentAdapter(FragmentManager fm){
        super(fm);
        AddStudentListFragment = new MentorAddStudentListFragment();
        AddStudentProfileFragment = new MentorAddStudentProfileFragment();
        fragmentList.add(AddStudentListFragment);
        fragmentList.add(AddStudentProfileFragment);
    }

    @Override
    public Fragment getItem(int position){
        return fragmentList.get(position);
    }
    @Override
    public int getCount(){
        return fragmentList.size();
    }

    public void onSetSelectStudentData(ModelUser m){
        if(AddStudentProfileFragment != null)
            AddStudentProfileFragment.onSetSelectStudentData(m);
    }
    public void setMentorData(ModelMentorData mMentorData){
        if(AddStudentProfileFragment != null)
            AddStudentProfileFragment.onSetMentorData(mMentorData);
    }
}
