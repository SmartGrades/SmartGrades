package kz.tech.smartgrades.parent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;


import kz.tech.smartgrades.parent.fragments.ContractsParentFragment;

import kz.tech.smartgrades.parent.fragments.SettingsParentFragment;
import kz.tech.smartgrades.parent.model.ModelParentData;

public class ParentFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private SettingsParentFragment settings;
    private ContractsParentFragment profile;


    public ParentFragmentAdapter(FragmentManager fm) {
        super(fm);
        settings = new SettingsParentFragment();
        profile = new ContractsParentFragment();

        fragmentArrayList.add(settings);
        fragmentArrayList.add(profile);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void onParentUsersList(ModelParentData modelParentData) {
    }

    public void setChatVisible(boolean b) {

    }
}
