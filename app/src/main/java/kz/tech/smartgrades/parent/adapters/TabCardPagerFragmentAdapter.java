package kz.tech.smartgrades.parent.adapters;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.fragments.ParentCashExtractFragment;
import kz.tech.smartgrades.parent.fragments.ParentCashReplenishFragment;
import kz.tech.smartgrades.sponsor.models.ModelDiscontCard;

public class TabCardPagerFragmentAdapter extends FragmentPagerAdapter{
    private int PAGE_COUNT = 2;
    private String tabTitles[];
    private ParentActivity activity;
    private ParentCashReplenishFragment replenishFragment;
    private ParentCashExtractFragment extractFragment;
    private ArrayList<ModelDiscontCard> cards;

    public TabCardPagerFragmentAdapter(FragmentManager fm, ParentActivity activity){
        super(fm);
        this.activity = activity;
        PAGE_COUNT = 2;
        replenishFragment = new ParentCashReplenishFragment(activity, 1);
        extractFragment = new ParentCashExtractFragment(activity, 2);
        tabTitles = new String[]{activity.getResources().getString(R.string.top_up),
                activity.getResources().getString(R.string.withdraw)};
        cards = activity.getMParentData().getPrivateAccount().getCards();
        setCards();
    }

    @Override
    public int getCount(){
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position){
        if(position == 0) return replenishFragment;
        return extractFragment;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }

    private void setCards() {
        replenishFragment.setCards(cards);
        extractFragment.setCards(cards);
    }

    public void updateCards() {
        cards = activity.getMParentData().getPrivateAccount().getCards();
        setCards();
    }
}