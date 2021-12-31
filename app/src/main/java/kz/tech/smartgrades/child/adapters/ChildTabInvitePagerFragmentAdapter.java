package kz.tech.smartgrades.child.adapters;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.child.fragments.ChildInviteIncomingFragment;
import kz.tech.smartgrades.child.fragments.ChildInviteOutgoingFragment;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.fragments.ParentInviteIncomingFragment;
import kz.tech.smartgrades.parent.fragments.ParentInviteOutgoingFragment;

public class ChildTabInvitePagerFragmentAdapter extends FragmentPagerAdapter{
    private int PAGE_COUNT = 2;
    private String tabTitles[];
    private ChildActivity activity;
    private ChildInviteIncomingFragment incomingFragment;
    private ChildInviteOutgoingFragment outgoingFragment;

    public ChildTabInvitePagerFragmentAdapter(FragmentManager fm, ChildActivity activity){
        super(fm);
        this.activity = activity;
        PAGE_COUNT = 2;
        incomingFragment = new ChildInviteIncomingFragment(activity, 1);
        outgoingFragment = new ChildInviteOutgoingFragment(activity, 2);
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