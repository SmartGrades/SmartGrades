package kz.tech.smartgrades.parent.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.parent.fragments.ParentAddParentListFragment;
import kz.tech.smartgrades.parent.fragments.ParentParentProfileFragment;
import kz.tech.smartgrades.parent.model.ModelUser;

public class ParentAddParentFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private ParentAddParentListFragment parentAddParentListFragment;
    private ParentParentProfileFragment parentParentProfileFragment;

    public ParentAddParentFragmentAdapter(FragmentManager fm) {
        super(fm);
        parentAddParentListFragment = new ParentAddParentListFragment();
        parentParentProfileFragment = new ParentParentProfileFragment();

        fragmentArrayList.add(parentAddParentListFragment);
        fragmentArrayList.add(parentParentProfileFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void setParentModel(ModelUser mChild) {
        parentParentProfileFragment.setParentModel(mChild);
    }
}
