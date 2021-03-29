package kz.tech.smartgrades.parents_module;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import kz.tech.smartgrades.parents_module.auto_charge.AutoChargeFragment;
import kz.tech.smartgrades.parents_module.cabinet.CabinetFragment;
import kz.tech.smartgrades.parents_module.children_time.ChildrenTimeFragment;
import kz.tech.smartgrades.parents_module.coins.CoinsFragment;
import kz.tech.smartgrades.parents_module.contracts.ContractsFragment;
import kz.tech.smartgrades.parents_module.devices.DevicesFragment;

public class ParentFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int FRAGMENTS_COUNT = 6;

    private CabinetFragment cabinetFragment;
    private CoinsFragment coinsFragment;
    private ContractsFragment contractsFragment;
    private DevicesFragment devicesFragment;
    private AutoChargeFragment autoChargeFragment;
    private ChildrenTimeFragment childrenTimeFragment;




    public ParentFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        cabinetFragment = new CabinetFragment();
        coinsFragment = new CoinsFragment();
        contractsFragment = new ContractsFragment();
        devicesFragment = new DevicesFragment();
        autoChargeFragment = new AutoChargeFragment();
        childrenTimeFragment = new ChildrenTimeFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return cabinetFragment;
            case 1:
                return coinsFragment;
            case 2:
                return contractsFragment;
            case 3:
                return devicesFragment;
            case 4:
                return autoChargeFragment;
            case 5:
                return childrenTimeFragment;
            default:
                return coinsFragment;
        }
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }

    public void onFragmentListenerID(int numb, String id) {
        switch (numb) {
            case 2: coinsFragment.onChildIDClick(id); break;
            case 3: contractsFragment.onChildIDClick(id); break;
            //   case 4: devicesFragment.onChildIDClick(id); break;
            case 5: autoChargeFragment.onChildIDClick(id); break;
            case 6: childrenTimeFragment.onChildIDClick(id); break;


            case 21: coinsFragment.onBackClick(); break;
            case 31: contractsFragment.onBackClick(); break;
        }

    }

    public void onRemoveAllFragments() {
        if (cabinetFragment != null) {
            cabinetFragment = null;
        }
        if (coinsFragment != null) {
            coinsFragment.onDestroy();
            coinsFragment = null;
        }
        if (contractsFragment != null) {
            contractsFragment = null;
        }
        if (devicesFragment != null) {
            devicesFragment = null;
        }
        if (autoChargeFragment != null) {
            autoChargeFragment = null;
        }
        if (childrenTimeFragment != null) {
            childrenTimeFragment = null;
        }
        notifyDataSetChanged();

    }
}
