package kz.tech.smartgrades.parent.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.smartgrades.parent.fragments.ParentChatFragment;
import kz.tech.smartgrades.parent.fragments.ParentChatListFragment;
import kz.tech.smartgrades.parent.model.ModelChat;
import kz.tech.smartgrades.parent.model.ModelParentData;

public class ParentChatFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    private ParentChatListFragment chatListFragment;
    private ParentChatFragment chatFragment;

    public ParentChatFragmentAdapter(FragmentManager fm) {
        super(fm);
        chatListFragment = new ParentChatListFragment();
        chatFragment = new ParentChatFragment();

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

    public void setData(ModelParentData mParentData){
        if(chatListFragment !=null)
            chatListFragment.setData(mParentData);
    }

    public void setChatModel(ModelChat m) {
        //if(chatFragment != null)
            //chatFragment.setData(m);
    }
}
