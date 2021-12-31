package kz.tech.smartgrades.sponsor.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.sponsor.fragments.SponsorChatFragment;
import kz.tech.smartgrades.sponsor.fragments.SponsorChatListFragment;
import kz.tech.smartgrades.sponsor.models.ModelSponsorData;
import kz.tech.smartgrades.sponsor.models.ModelSponsorUsersListAdapter;

public class SponsorChatFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private SponsorChatListFragment chatListFragment;
    private SponsorChatFragment chatFragment;

    public SponsorChatFragmentAdapter(FragmentManager fm) {
        super(fm);
        chatListFragment = new SponsorChatListFragment();
        chatFragment = new SponsorChatFragment();

        fragmentArrayList.add(chatListFragment);
        fragmentArrayList.add(chatFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void setData(ModelSponsorData mSponsorData){
        if(chatListFragment !=null)
            chatListFragment.setData(mSponsorData);
    }

    public void setChatModel(ModelSponsorUsersListAdapter m) {
        if(chatFragment != null)
            chatFragment.setData(m);
    }
}
