package kz.tech.smartgrades.child.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.child.fragments.ChildComplaintFragment;
import kz.tech.smartgrades.child.fragments.ChildComplaintStep1Fragment;
import kz.tech.smartgrades.child.fragments.ChildComplaintStep2Fragment;

public class ChildComplaintFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private ChildComplaintFragment childComplaintFragment;
    private ChildComplaintStep1Fragment childComplaintStep1Fragment;
    private ChildComplaintStep2Fragment childComplaintStep2Fragment;

    public ChildComplaintFragmentAdapter(FragmentManager fm) {
        super(fm);
        childComplaintFragment = new ChildComplaintFragment();
        childComplaintStep1Fragment = new ChildComplaintStep1Fragment();
        childComplaintStep2Fragment = new ChildComplaintStep2Fragment();

        fragmentArrayList.add(childComplaintFragment);
        fragmentArrayList.add(childComplaintStep1Fragment);
        fragmentArrayList.add(childComplaintStep2Fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
}
