package kz.tech.smartgrades.parent.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.parent.fragments.ParentAllTransactionFragment;

public class ParentAllTransactionAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private ParentAllTransactionFragment parentAllTransactionFragment;

    public ParentAllTransactionAdapter(FragmentManager fm) {
        super(fm);
        parentAllTransactionFragment = new ParentAllTransactionFragment();

        fragmentArrayList.add(parentAllTransactionFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void setChildModel(ModelChildData mChild) {
        if (parentAllTransactionFragment != null) {
            parentAllTransactionFragment.setChildModel(mChild);
        }
    }
}
