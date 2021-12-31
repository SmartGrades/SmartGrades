package kz.tech.smartgrades.parent.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.parent.fragments.ParentAddChildListFragment;
import kz.tech.smartgrades.parent.fragments.ParentChildProfileFragment;

public class ParentAddChildFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private ParentAddChildListFragment parentAddChildListFragment;
    private ParentChildProfileFragment parentChildProfileFragment;

    public ParentAddChildFragmentAdapter(FragmentManager fm) {
        super(fm);
        parentAddChildListFragment = new ParentAddChildListFragment();
        parentChildProfileFragment = new ParentChildProfileFragment();

        fragmentArrayList.add(parentAddChildListFragment);
        fragmentArrayList.add(parentChildProfileFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void setChildModel(ModelUser mChild) {
        parentChildProfileFragment.setChildModel(mChild);
    }
}
