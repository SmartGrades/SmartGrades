package kz.tech.smartgrades.sponsor.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.sponsor.models.ModelChildrenListForSponsorship;
import kz.tech.smartgrades.sponsor.fragments.SponsorChildListFragment;
import kz.tech.smartgrades.sponsor.fragments.SponsorChildProfileFragment;

public class SponsorAddChildAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private SponsorChildListFragment sponsorChildListFragment;
    private SponsorChildProfileFragment sponsorChildProfileFragment;

    public SponsorAddChildAdapter(FragmentManager fm) {
        super(fm);
        sponsorChildListFragment = new SponsorChildListFragment();
        sponsorChildProfileFragment = new SponsorChildProfileFragment();

        fragmentArrayList.add(sponsorChildListFragment);
        fragmentArrayList.add(sponsorChildProfileFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void setChildModel(ModelChildrenListForSponsorship mChild) {
        sponsorChildProfileFragment.setChildModel(mChild);
    }
}
