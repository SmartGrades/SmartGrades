package kz.tech.smartgrades.mentor.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.mentor.fragments.MentorClassessFragment;
import kz.tech.smartgrades.mentor.fragments.MentorStudentsForGradesFragment;
import kz.tech.smartgrades.mentor.models.ModelMentorClass;
import kz.tech.smartgrades.mentor.models.ModelMentorData;

public class MentorClassesFragmentAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    private MentorClassessFragment ClassessFragment;
    private MentorStudentsForGradesFragment StudentsFragment;


    public MentorClassesFragmentAdapter(FragmentManager fm){
        super(fm);
        ClassessFragment = new MentorClassessFragment();
        StudentsFragment = new MentorStudentsForGradesFragment();
        fragmentList.add(ClassessFragment);
        fragmentList.add(StudentsFragment);
    }

    @Override
    public Fragment getItem(int position){
        return fragmentList.get(position);
    }
    @Override
    public int getCount(){
        return fragmentList.size();
    }

    public void setMentorData(ModelMentorData m){
        if(ClassessFragment != null) ClassessFragment.setMentorData(m);
        if(StudentsFragment != null) StudentsFragment.setData(m);
    }
    public void setSelectClass(ModelMentorClass m){
        if (StudentsFragment!=null) StudentsFragment.setSelectClass(m);
    }
    public void onShowDialogSelectLesson(){
        StudentsFragment.onShowDialogSelectLesson();
    }
}
