package kz.tech.smartgrades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import javax.inject.Inject;

import kz.tech.esparta.R;
import kz.tech.smartgrades.authentication.AuthenticationActivity;
import kz.tech.smartgrades.family_room.FamilyRoomActivity;
import kz.tech.smartgrades.root.ParentFragmentAdapter;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.date.IDateCustomPicker;
import kz.tech.smartgrades.root.db.IFamilyDao;
import kz.tech.smartgrades.root.device.IDevice;
import kz.tech.smartgrades.root.hardware_access.IHardwareAccess;
import kz.tech.smartgrades.root.language.ILanguage;
import kz.tech.smartgrades.root.login.ILogin;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.mvp.IView;

import kz.tech.smartgrades.root.prefs.IPreferences;
import kz.tech.smartgrades.root.var_resources.PermissionCode;

//            !
public class MainActivity extends AppCompatActivity implements IView {
        public MainPresenter presenter;
        public MainView view;
        public ParentFragmentAdapter pAdapter;
        private MainView.ParentClickListener parentListener;

    @Inject
    public ILogin login;
    @Inject
    public ILanguage language;
    @Inject
    public IFamilyDao familyDao;
    @Inject
    public IPreferences prefs;
    @Inject
    public IDateCustomPicker iDate;
    @Inject
    public IDevice iDevice;
    @Inject
    public IHardwareAccess hardwareAccess;
    @Inject
    public IAlert alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MainView(this);
        setContentView(view);
        presenter = new MainPresenter(this);
        presenter.onStart();
        initViews();





    //    presenter.onSelectFragment("TestFragment");
     //   long allocatedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        android.util.Log.e("TAG", "MAIN LOAD");
    }

    public void initViews() {
        pAdapter = new ParentFragmentAdapter(getSupportFragmentManager());
        view.getViewPager().setAdapter(pAdapter);


        parentListener = new MainView.ParentClickListener() {
            @Override
            public void onBackButtonClick(int position) {
                switch (position) {
                    case 1: presenter.onOpenNavMenu(); break;
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
                                    presenter.onSelectFragment("SettingsParentFragment");
                                    view.onToolbarSelect(2, language.getLanguage().getString(R.string.settings));
                                } else if (s.equals("AddUserFragment")) {
                                    presenter.onSelectFragment("FamilyGroupFragment");
                                    view.onToolbarSelect(2, language.getLanguage().getString(R.string.family_group));
                                }
                            }
                        }
                        break;
                    case 3:
                        view.onToolbarSelect(1, null);
                        pAdapter.onFragmentListenerID(21, null);
                        break;
                    case 4:
                        view.onToolbarSelect(1, null);
                        pAdapter.onFragmentListenerID(31, null);
                        break;
                }
            }

            @Override
            public void onMenuClick(View view) {
                onMenu(view, 1);
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
                        pAdapter.onFragmentListenerID(2, id);
                        prefs.onSaveCurrentPage("ParentMainCoins");
                        switch (prefs.onLoadCurrentPageMainCoins()) {
                            case 0: view.onToolbarSelect(1, null); break;
                            case 1: view.onToolbarSelect(3, language.getLanguage().getString(R.string.reports)); break;//  Reports
                            case 2: view.onToolbarSelect(3, language.getLanguage().getString(R.string.bank_text)); break;//  Bank
                        }
                        break;
                    case 2://  Contracts
                        view.onToolbarSelect(1, null);
                        pAdapter.onFragmentListenerID(3, id);
                        prefs.onSaveCurrentPage("ParentMainContracts");
                        break;
                    case 3://  Devices
                        view.onToolbarSelect(1, null);
                        prefs.onSaveCurrentPage("ParentMainDevices");
                        break;
                    case 4://  Auto Charge
                        view.onToolbarSelect(1, null);
                        pAdapter.onFragmentListenerID(5, id);
                        prefs.onSaveCurrentPage("ParentMainAutoCharge");
                        break;
                    case 5://  Children Time
                        view.onToolbarSelect(1, null);
                        pAdapter.onFragmentListenerID(6, id);
                        prefs.onSaveCurrentPage("ParentMainChildrenTime");
                        break;
                }
                presenter.model.setCurrentChildID(id);
            }
            @Override
            public void onChildViewPagerClick(int position, boolean isSelect) {
                String id = presenter.model.getIdChildFromChildList(position);
                if (id == null) return;
                presenter.model.setCurrentChildID(id);
                switch (view.getViewPager().getCurrentItem()) {
                    case 1: pAdapter.onFragmentListenerID(2, id); break;
                    case 2: pAdapter.onFragmentListenerID(3, id); break;
                    //  case 3: adapter.onFragmentListenerID(4, id); break;
                    case 4: pAdapter.onFragmentListenerID(5, id); break;
                    case 5: pAdapter.onFragmentListenerID(6, id); break;
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
            }
            @Override
            public void onRightArrowClick() {
                int countChild = presenter.model.getChildListSize();
                int childPosition = view.getChildViewPager().getCurrentItem();
                if (countChild > childPosition) {
                    view.getChildViewPager().setCurrentItem(childPosition+1);
                }
            }
        };
        view.setParentClickListener(parentListener);



        String image = login.loadUserDate(LoginKey.AVATAR);
        if (image != null) {
            view.setAvatar(image);
        }

        view.onDefaultPageSelect(1);
    }
    public void onParentDataUpdate() {
        int lol = view.getViewPager().getCurrentItem();
        view.onDefaultPageSelect(0);
        view.onDefaultPageSelect(lol);
    }

    @Override
    public void onReplaceFragment(Fragment fragment) {
        androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(view.flMain.getId(), fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onRemoveFragment(String s) {
        /*for (Fragment fragment : getSupportFragmentManager().getFragments()) {
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
        }*/
    }

    @Override
    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager)this.getSystemService( Context.INPUT_METHOD_SERVICE );
        View f = this.getCurrentFocus();
        if( null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom( f.getClass() ) )
            imm.hideSoftInputFromWindow( f.getWindowToken(), 0 );
        else
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onLoadAnimation(boolean isLoad) {
        view.onLoadAnimation(isLoad);
    }
    @Override
    public void onToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onMenu(View v, int n) {
        PopupMenu menu = new PopupMenu(this, v);
        menu.getMenu().add(language.getLanguage().getString(R.string.family_room)).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, FamilyRoomActivity.class));
                return false;
            }
        });
        menu.getMenu().add(language.getLanguage().getString(R.string.exit)).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                login.deleteUserDate();
                startActivity(new Intent(MainActivity.this, AuthenticationActivity.class));
                finish();
                return false;
            }
        });
        menu.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //  false
        switch (requestCode) {
            case PermissionCode.LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            //  false
                            return;
                        }
                    }
                    //  true
                }
            }
        }
    }
    @Override
    public void onBackPressed() {
        if (presenter != null) {
            int page = presenter.onCurrentPage();
            switch (page) {
                case 777: super.onBackPressed(); break;  //  if start page, then use onBackPressed
                case 2:  view.onToolbarSelect(1, null); pAdapter.onFragmentListenerID(21, null); break;
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroyView();
        }
        prefs.onRemoveCurrentPageMainCoins();
    }

}





