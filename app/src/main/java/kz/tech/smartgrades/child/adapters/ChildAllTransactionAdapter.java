package kz.tech.smartgrades.child.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.child.fragments.ChildAllTransactionFragment;
import kz.tech.smartgrades.child.models.ModelChildData;

public class ChildAllTransactionAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private ChildAllTransactionFragment childAllTransactionFragment;

    public ChildAllTransactionAdapter(FragmentManager fm) {
        super(fm);
        childAllTransactionFragment = new ChildAllTransactionFragment();

        fragmentArrayList.add(childAllTransactionFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void setChildModel(ModelChildData mChild, String childId) {
        if (childAllTransactionFragment != null) {
            childAllTransactionFragment.setChildModel(mChild, childId);
        }
    }
}
