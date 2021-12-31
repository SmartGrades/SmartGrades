package kz.tech.smartgrades.child.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.child.fragments.ChildAddParentListFragment;
import kz.tech.smartgrades.child.fragments.ChildParentProfileFragment;

public class ChildAddParentFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private ChildAddParentListFragment childAddParentFragment;
    private ChildParentProfileFragment childParentProfileFragment;

    public ChildAddParentFragmentAdapter(FragmentManager fm) {
        super(fm);
        childAddParentFragment = new ChildAddParentListFragment();
        childParentProfileFragment = new ChildParentProfileFragment();

        fragmentArrayList.add(childAddParentFragment);
        fragmentArrayList.add(childParentProfileFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void setModel(ModelUser m) {
        if(childParentProfileFragment != null)
            childParentProfileFragment.onSetParentInfo(m);
    }
}
