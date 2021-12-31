package kz.tech.smartgrades.parents_module;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import kz.tech.smartgrades.App;
import kz.tech.smartgrades.L;
import kz.tech.smartgrades.MainView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.authentication.AuthenticationActivity;
import kz.tech.smartgrades.authentication.fragments.registration_parent.RegistrationParentFragment;
import kz.tech.smartgrades.authentication.fragments.sign_in.SignInFragment;
import kz.tech.smartgrades.family_room.FamilyRoomActivity;
import kz.tech.smartgrades.family_room.fragments.quick_access_sign_in.QuickAccessSignInFragment;
import kz.tech.smartgrades.parents_module.about_app.AboutAppFragment;
import kz.tech.smartgrades.parents_module.add_user.AddUserFragment;
import kz.tech.smartgrades.parents_module.family_group.FamilyGroupFragment;
import kz.tech.smartgrades.parents_module.family_member.FamilyMemberFragment;
import kz.tech.smartgrades.parents_module.mvp.IView;
import kz.tech.smartgrades.parents_module.settings.SettingsFragment;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.date.IDateCustomPicker;
import kz.tech.smartgrades.root.db.IFamilyDao;
import kz.tech.smartgrades.root.device.IDevice;
import kz.tech.smartgrades.root.firebase.IFireBase;
import kz.tech.smartgrades.root.fragments.IFragments;
import kz.tech.smartgrades.root.hardware_access.IHardwareAccess;
import kz.tech.smartgrades.root.language.ILanguage;
import kz.tech.smartgrades.root.login.ILogin;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.prefs.IPreferences;

public class ParentActivity extends AppCompatActivity implements IView, MainView.ParentClickListener {

    public ParentView view;
    public ParentPresenter presenter;
    public ParentFragmentPagerAdapter adapter;
    public MainView.ParentClickListener parentListener;

    @Inject
    public ILogin login;
    @Inject
    public IFragments fragments;
    @Inject
    public ILanguage language;
    @Inject
    public IFamilyDao familyDao;
    @Inject
    public IPreferences prefs;
    @Inject
    public IDateCustomPicker date;
    @Inject
    public IDevice device;
    @Inject
    public IHardwareAccess hardwareAccess;
    @Inject
    public IAlert alert;
    @Inject
    public IFireBase fireBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view = new ParentView(this));
        //Init Dagger 2
        //App.app().getComponent().injectsParentActivity(this);
        //Init Views
        initViews();
        //Init Presenter
        presenter = new ParentPresenter(this, fireBase, familyDao);
        presenter.onStart();



    }





    private void initViews() {
        adapter = new ParentFragmentPagerAdapter(getSupportFragmentManager());
        view.getViewPager().setAdapter(adapter);

        String image = login.loadUserDate(LoginKey.AVATAR);
        if (image != null) {
            view.setAvatar(image);
        }

        view.onDefaultPageSelect(0);
        view.onNavInit();

        if (view.getRlViewPager() != null) {
            view.getRlViewPager().setVisibility(View.GONE);
        }
    }












    @Override
    public void onShowToast(String msg) {
        alert.onToast(msg);
    }

    @Override
    public void onNextFragment(String fragment) {
        fragments.onReplaceFragment(fragment, L.layout_family_room);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroyView();
        }
    }

    @Override
    public void onBackButtonClick(int position) {
        switch (position) {
            case 1:
                view.dlMain.openDrawer(GravityCompat.START);
                break;
            case 2:
                if (prefs.onLoadCurrentPage() != null) {
                    if (!prefs.onLoadCurrentPage().equals("")) {
                        String s = prefs.onLoadCurrentPage();
                        if (s.equals("FamilyMemberFragment") || s.equals("DeviceGroupFragment")
                                || s.equals("ReportsFragment") || s.equals("FamilyGroupFragment")
                                || s.equals("SettingsParentFragment") || s.equals("AboutAppFragment") || s.equals("ConcludeContractsFragments")) {
                            onRemoveFragment(s);
                            if (view.getViewPager() != null) {
                                view.getViewPager().setVisibility(View.VISIBLE);
                            }
                            if (view.getRlViewPager() != null) {
                                view.getRlViewPager().setVisibility(View.VISIBLE);
                            }
                            view.onToolbarSelect(1, null);
                        } else if (s.equals("PersonalDataFragment") || s.equals("TimeChildrenFragment") || s.equals("AutoChargeFragment")
                                || s.equals("NotificationFragment") || s.equals("LocalityFragment") || s.equals("ChangePinCodeFragment")
                                || s.equals("ChangePasswordFragment")) {
                          //  presenter.onSelectFragment("SettingsParentFragment");
                            view.onToolbarSelect(2, language.getLanguage().getString(R.string.settings));
                        } else if (s.equals("AddUserFragment")) {
                          //  presenter.onSelectFragment("FamilyGroupFragment");
                            view.onToolbarSelect(2, language.getLanguage().getString(R.string.family_group));
                        }
                    }
                }
                break;
            case 3:
                view.onToolbarSelect(1, null);
                adapter.onFragmentListenerID(21, null);
                break;
            case 4:
                view.onToolbarSelect(1, null);
                adapter.onFragmentListenerID(31, null);
                break;
        }
        Log.e("Tag", "Good");
    }

    @Override
    public void onMenuClick(View view) {
        onMenu(view, 1);
        Log.e("Tag", "Good");
    }

    @Override
    public void onViewPagerClick(int num1, int numb2) {
        String id = presenter.model.getIdChildFromChildList(numb2);
        if (id == null) return;
        boolean b = prefs.onEqualCurrentPage();
        if (b) {
            String s = prefs.onLoadCurrentPage();
            onRemoveFragment(s);
            if (view.getViewPager() != null) {
                view.getViewPager().setVisibility(View.VISIBLE);
            }
        }

        switch (num1) {//  VIEW PAGER POSITION
            case 0: view.onToolbarSelect(1, null); break;//  Toolbar default Nav and Logo
            case 1://  Coins
                adapter.onFragmentListenerID(2, id);
                prefs.onSaveCurrentPage("ParentMainCoins");
                switch (prefs.onLoadCurrentPageMainCoins()) {
                    case 0: view.onToolbarSelect(1, null); break;
                    case 1: view.onToolbarSelect(3, language.getLanguage().getString(R.string.reports)); break;//  Reports
                    case 2: view.onToolbarSelect(3, language.getLanguage().getString(R.string.bank_text)); break;//  Bank
                }
                break;
            case 2://  Contracts
                view.onToolbarSelect(1, null);
                adapter.onFragmentListenerID(3, id);
                prefs.onSaveCurrentPage("ParentMainContracts");
                break;
            case 3://  Devices
                view.onToolbarSelect(1, null);
                prefs.onSaveCurrentPage("ParentMainDevices");
                break;
            case 4://  Auto Charge
                view.onToolbarSelect(1, null);
                adapter.onFragmentListenerID(5, id);
                prefs.onSaveCurrentPage("ParentMainAutoCharge");
                break;
            case 5://  Children Time
                view.onToolbarSelect(1, null);
                adapter.onFragmentListenerID(6, id);
                prefs.onSaveCurrentPage("ParentMainChildrenTime");
                break;
        }
        presenter.model.setCurrentChildID(id);
        Log.e("Tag", "Good");
    }

    @Override
    public void onChildViewPagerClick(int position, boolean isSelect) {
        Log.e("Tag", "Good");
        String id = presenter.model.getIdChildFromChildList(position);
        if (id == null) return;
        presenter.model.setCurrentChildID(id);
        switch (view.getViewPager().getCurrentItem()) {
            case 1: adapter.onFragmentListenerID(2, id); break;
            case 2: adapter.onFragmentListenerID(3, id); break;
            //  case 3: adapter.onFragmentListenerID(4, id); break;
            case 4: adapter.onFragmentListenerID(5, id); break;
            case 5: adapter.onFragmentListenerID(6, id); break;
        }
        presenter.model.setCurrentChildID(id);

        //  int lol = main.presenter.model.getChildListSize()-1;
        int lol = presenter.model.getChildListSize()-1;

        if (position == 0 && lol > 0) {
            view.onRightArrow("VISIBLE");
        } else if (position < lol) {
            view.onRightArrow("VISIBLE");
        } else if (position == lol) {
            view.onRightArrow("GONE");
        }

        if (position == 0) {
            view.onLeftArrow("GONE");
        } else if (position > 0) {
            view.onLeftArrow("VISIBLE");
        } else if (position == lol) {
            view.onLeftArrow("GONE");
        }
    }

    @Override
    public void onLeftArrowClick() {
        int childPosition = view.getChildViewPager().getCurrentItem();
        if (childPosition > 0) {
            view.getChildViewPager().setCurrentItem(childPosition-1);
        }
        Log.e("Tag", "Good");
    }

    @Override
    public void onRightArrowClick() {
        int countChild = presenter.model.getChildListSize();
        int childPosition = view.getChildViewPager().getCurrentItem();
        if (countChild > childPosition) {
            view.getChildViewPager().setCurrentItem(childPosition+1);
        }
        Log.e("Tag", "Good");
    }


    public void onRemoveFragment(String s) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof SettingsFragment) {
                if (s.equals("SettingsParentFragment")) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
            if (fragment instanceof AboutAppFragment) {
                if (s.equals("AboutAppFragment")) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
            if (fragment instanceof AddUserFragment) {
                if (s.equals("AddUserFragment")) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
            if (fragment instanceof FamilyMemberFragment) {
                if (s.equals("FamilyMemberFragment")) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
            if (fragment instanceof QuickAccessSignInFragment) {
                if (s.equals("QuickAccessSignInFragment")) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
            if (fragment instanceof RegistrationParentFragment) {
                if (s.equals("RegistrationParentFragment")) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
            if (fragment instanceof SignInFragment) {
                if (s.equals("SignInFragment")) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
            if (fragment instanceof FamilyGroupFragment) {
                if (s.equals("FamilyGroupFragment")) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
        }
    }


    public void onMenu(View v, int n) {
        PopupMenu menu = new PopupMenu(this, v);
        menu.getMenu().add(language.getLanguage().getString(R.string.family_room)).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(ParentActivity.this, FamilyRoomActivity.class));
                return false;
            }
        });
        menu.getMenu().add(language.getLanguage().getString(R.string.exit)).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                login.deleteUserDate();
                startActivity(new Intent(ParentActivity.this, AuthenticationActivity.class));
                finish();
                return false;
            }
        });
        menu.show();
    }
}
