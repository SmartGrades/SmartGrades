package kz.tech.smartgrades.mentor_module;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import kz.tech.smartgrades.mentor_module.cabinet.CabinetFragment;
import kz.tech.smartgrades.mentor_module.calendar.CalendarFragment;
import kz.tech.smartgrades.mentor_module.coins.CoinsFragment;
import kz.tech.smartgrades.mentor_module.content.ContentFragment;

public class MentorFragmentAdapter extends FragmentStatePagerAdapter {
    private CabinetFragment cabinetFragment;
    private CoinsFragment coinsFragment;
    private ContentFragment contentFragment;
    private CalendarFragment calendarFragment;
    public MentorFragmentAdapter(FragmentManager fm) {
        super(fm);
        cabinetFragment = new CabinetFragment();
        calendarFragment = new CalendarFragment();
        coinsFragment = new CoinsFragment();
        contentFragment = new ContentFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return cabinetFragment;
            case 1:
                return coinsFragment;
            case 2:
                return contentFragment;
            case 3:
                return calendarFragment;
            default:
                return cabinetFragment;

        }
    }

    @Override
    public int getCount() {
        return 4;
    }
    public void onFragmentListenerID(int numb, String id) {
        switch (numb) {
         //   case 2: coinsFragment.onChildIDClick(id); break;
          //  case 3: contractsFragment.onChildIDClick(id); break;
            //   case 4: devicesFragment.onChildIDClick(id); break;
        //    case 5: autoChargeFragment.onChildIDClick(id); break;
        //    case 6: childrenTimeFragment.onChildIDClick(id); break;


            case 21: coinsFragment.onBackClick(); break;
         //   case 31: contractsFragment.onBackClick(); break;
        }

    }
    public void onRemoveAllFragments() {
        if (cabinetFragment != null) {
            cabinetFragment = null;
        }
        if (coinsFragment != null) {
            coinsFragment = null;
        }
        if (contentFragment != null) {
            contentFragment = null;
        }
        if (calendarFragment != null) {
            calendarFragment = null;
        }
        notifyDataSetChanged();
    }
}
