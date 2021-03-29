package kz.tech.smartgrades.school.adaptes;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.fragments.SchoolClassFragment;
import kz.tech.smartgrades.school.fragments.SchoolClassessFragment;
import kz.tech.smartgrades.school.models.ModelSchoolClass;

public class SchoolClassesFragmentAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    private SchoolClassessFragment ClassessFragment;
    private SchoolClassFragment ClassFragment;


    public SchoolClassesFragmentAdapter(FragmentManager fm, ModelSchoolData mSchoolData){
        super(fm);
        ClassessFragment = new SchoolClassessFragment();
        ClassFragment = new SchoolClassFragment();

        fragmentList.add(ClassessFragment);
        fragmentList.add(ClassFragment);

        if(ClassessFragment != null) ClassessFragment.setSchoolData(mSchoolData);
        if(ClassFragment != null) ClassFragment.onUpdateSchoolData(mSchoolData);
    }

    @Override
    public Fragment getItem(int position){
        return fragmentList.get(position);
    }
    @Override
    public int getCount(){
        return fragmentList.size();
    }

    public void setSelectClassData(ModelSchoolClass m){
        ClassFragment.onSetData(m);
    }
    public void setSchoolData(ModelSchoolData m){
        if(ClassessFragment != null) ClassessFragment.setSchoolData(m);
        if(ClassFragment != null) ClassFragment.onUpdateSchoolData(m);
    }
}
