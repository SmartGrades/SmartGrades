package kz.tech.smartgrades.parent.adapters;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.fragments.ParentInviteIncomingFragment;
import kz.tech.smartgrades.parent.fragments.ParentInviteOutgoingFragment;

public class ParentTabInvitePagerFragmentAdapter extends FragmentPagerAdapter{
    private int PAGE_COUNT = 2;
    private String tabTitles[];
    private ParentActivity activity;
    private ParentInviteIncomingFragment incomingFragment;
    private ParentInviteOutgoingFragment outgoingFragment;

    public ParentTabInvitePagerFragmentAdapter(FragmentManager fm, ParentActivity activity){
        super(fm);
        this.activity = activity;
        PAGE_COUNT = 2;
        incomingFragment = new ParentInviteIncomingFragment(activity, 1);
        outgoingFragment = new ParentInviteOutgoingFragment(activity, 2);
        tabTitles = new String[]{activity.getResources().getString(R.string.incoming),
                activity.getResources().getString(R.string.outgoing)};
        setInvites();
    }

    @Override
    public int getCount(){
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position){
        if(position == 0) return incomingFragment;
        return outgoingFragment;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }

    public void setInvites() {
        incomingFragment.setInvites(activity.getModelInterFormList());
        outgoingFragment.setInvites(activity.getModelInterFormList());
    }
}