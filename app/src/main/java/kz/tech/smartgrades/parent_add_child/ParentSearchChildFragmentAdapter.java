package kz.tech.smartgrades.parent_add_child;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.parent_add_child.fragments.pacProfileChildFragment;
import kz.tech.smartgrades.parent_add_child.fragments.pacSearchChildFragment;

public class ParentSearchChildFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> FragmentsList = new ArrayList<>();
    private pacSearchChildFragment search;
    private pacProfileChildFragment profile;


    public ParentSearchChildFragmentAdapter(FragmentManager fm) {
        super(fm);
        search = new pacSearchChildFragment();
        profile = new pacProfileChildFragment();

        FragmentsList.add(search);
        FragmentsList.add(profile);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return FragmentsList.size();
    }

    public void setProfileData(ModelUser m) {
        profile.setProfileData(m);
    }
}
