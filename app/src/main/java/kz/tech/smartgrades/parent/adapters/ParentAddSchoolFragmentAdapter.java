package kz.tech.smartgrades.parent.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.parent.fragments.ParentAddSchoolFragment;
import kz.tech.smartgrades.parent.fragments.ParentAddSchoolProfileFragment;
import kz.tech.smartgrades.parent.model.ModelParentData;
import kz.tech.smartgrades.school.models.ModelSchoolData;

public class ParentAddSchoolFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private ParentAddSchoolFragment addSchoolFragment;
    private ParentAddSchoolProfileFragment addSchoolProfileFragment;

    public ParentAddSchoolFragmentAdapter(FragmentManager fm) {
        super(fm);
        addSchoolFragment = new ParentAddSchoolFragment();
        addSchoolProfileFragment = new ParentAddSchoolProfileFragment();

        fragmentArrayList.add(addSchoolFragment);
        fragmentArrayList.add(addSchoolProfileFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
    public void setData(ModelParentData mParentData, int select_child_index){
        if(addSchoolFragment!=null)
            addSchoolFragment.setData(mParentData, select_child_index);
        if(addSchoolFragment!=null)
            addSchoolProfileFragment.setData(mParentData, select_child_index);
    }
    public void setSchoolProfile(ModelSchoolData m){
        if(addSchoolProfileFragment!=null){
            addSchoolProfileFragment.setData(m);
        }
    }
}
