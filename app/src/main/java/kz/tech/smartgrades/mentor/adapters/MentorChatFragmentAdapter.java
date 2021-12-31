package kz.tech.smartgrades.mentor.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.mentor.fragments.MentorChatFragment;
import kz.tech.smartgrades.mentor.fragments.MentorChatListFragment;
import kz.tech.smartgrades.mentor.models.ModelMentorChat;
import kz.tech.smartgrades.mentor.models.ModelMentorData;

public class MentorChatFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private MentorChatListFragment chatListFragment;
    private MentorChatFragment chatFragment;

    public MentorChatFragmentAdapter(FragmentManager fm) {
        super(fm);
        chatListFragment = new MentorChatListFragment();
        chatFragment = new MentorChatFragment();

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

    public void setData(ModelMentorData mMentorData){
        if(chatListFragment !=null)
            chatListFragment.setData(mMentorData);
    }

    public void setChatModel(ModelMentorChat m) {
//        if(chatFragment != null)
//            chatFragment.onSetChatData(m);
    }
}
