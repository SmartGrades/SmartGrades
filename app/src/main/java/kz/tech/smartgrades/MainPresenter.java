package kz.tech.smartgrades;



import android.view.View;

import androidx.core.view.GravityCompat;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.mvp.ICallBack;
import kz.tech.smartgrades.root.mvp.IPresenter;

public class MainPresenter implements IPresenter {
    private MainActivity main;
    public MainModel model;
    private ICallBack callBack;
    public MainPresenter(MainActivity main) {
        this.main = main;
        this.model = new MainModel();
        this.callBack = new ICallBack() {
            @Override
            public void onMessageCallBack(String msg) {
                main.onToastMsg(msg);
            }
            @Override
            public void onResponseAddUser() {//  Response after Added User
              //  model.onClearFamilyRoomList();//  Clear FamilyList
             //   model.onClearChildList();//  Clear Child
             //   model.onClearChildDataList();//  Clear ChildData
              //  model.onRequestFamilyRoom(callBack, main.prefs.getLoginData("id"));//  Again Load FamilyList
              //  navigationView.setClearContainerLL();//  Clear Family Group in NavigationView
            }
            @Override
            public void onResponseFamilyRoom() {//  Response after Request FamilyList
                onLoadFamilyRoomToNavigationView();//  Add FamilyList To NavigationView
                main.hideSoftKeyboard();//  Hide KeyBoard
                main.onLoadAnimation(false);//  Hide Load Animation
                main.view.onToolbarSelect(1, null);//  Toolbar parent main

                model.onRequestChildData(callBack, main.login.loadUserDate(LoginKey.ID), 1);//
            }
            @Override
            public void onResponseChildData() {//  AFTER GET CHILD DATA (COINS and CONTRACTS)
                main.onParentDataUpdate();
            }

            @Override
            public void onResponseSignIn(int n) {
                if (n == 2) {
                    main.initViews();
                }
            }

            @Override
            public void onResponseMentors(String a, String n) {
                main.prefs.onSaveMentorAccount(a, n);
                if (a != null) {
                 //   main.view.setMentorAvatar(a);
                 //   main.initMentorState();
                }
            }
        };
    }


    /////////////            FAMILY ROOM              ///////////////////////////
    @Override
    public void onSignInFromFamilyRoom(int position) {
        switch (position) {
            case 1:
                // getPhonePermission();
                model.onFamilyRoomListToChildList();
                model.onRequestChildData(callBack, main.prefs.getLoginData("id"),2);//  GET CHILD COINS AND CONTRACTS
                break;
            case 2:

                break;
        }

    }

    /////////////            FAMILY ROOM              ///////////////////////////




    protected int onCurrentPage() {
        int countPage = 0;
        if (main.prefs.onLoadCurrentPage() != null) {
            if (!main.prefs.onLoadCurrentPage().equals("")) {
                String s = main.prefs.onLoadCurrentPage();
                switch (main.prefs.onLoadCurrentPage()) {
                    case "ParentMainCabinet": countPage = 777; break;
                    case "ParentMainCoins":
                        if (main.prefs.onLoadCurrentPageMainCoins() == 0) {
                            countPage = 777;
                        } else if (main.prefs.onLoadCurrentPageMainCoins() > 0) {
                            main.view.onToolbarSelect(1, null);
                            main.pAdapter.onFragmentListenerID(21, null);
                        }
                        break;
                    case "ParentMainContracts": countPage = 777; break;
                    case "ParentMainDevices": countPage = 777; break;
                    case "ParentMainAutoCharge": countPage = 777; break;
                    case "ParentMainChildrenTime": countPage = 777; break;

                    case "ChildMainFragment": countPage = 777; break;



                    case "FamilyGroupFragment":
                        main.onRemoveFragment(s);
                        if (main.view.getViewPager() != null) { main.view.getViewPager().setVisibility(View.VISIBLE); }
                        if (main.view.getRlViewPager() != null) { main.view.getRlViewPager().setVisibility(View.VISIBLE); }
                        main.view.onToolbarSelect(1, null);
                        break;
                    case "AddUserFragment":
                        main.presenter.onSelectFragment("FamilyGroupFragment");
                        main.view.onToolbarSelect(2, main.language.getLanguage().getString(R.string.family_group));
                        break;

                //    case "FamilyMemberFragment": main.onReplaceFragment(model.getFragment("ParentMainFragment")); break;

                 //   case "SettingsParentFragment": main.onReplaceFragment(model.getFragment("ParentMainFragment")); break;
                 //   case "AboutAppFragment": main.onReplaceFragment(model.getFragment("ParentMainFragment")); break;
                 //   case "ConcludeContractsFragments": main.onReplaceFragment(model.getFragment("ParentMainFragment")); break;
                 //   case "SigningContractsFragment": main.onReplaceFragment(model.getFragment("ConcludeContractsFragments")); break;



                    case "PersonalDataFragment": main.onReplaceFragment(model.getFragment("SettingsParentFragment")); break;
                    case "TimeChildrenFragment": main.onReplaceFragment(model.getFragment("SettingsParentFragment")); break;
                    case "AutoChargeFragment": main.onReplaceFragment(model.getFragment("SettingsParentFragment")); break;
                    case "NotificationFragment": main.onReplaceFragment(model.getFragment("SettingsParentFragment")); break;
                    case "LocalityFragment": main.onReplaceFragment(model.getFragment("SettingsParentFragment")); break;
                    case "ChangePinCodeFragment": main.onReplaceFragment(model.getFragment("SettingsParentFragment")); break;
                    case "ChangePasswordFragment": main.onReplaceFragment(model.getFragment("SettingsParentFragment")); break;
                }
            }
        }
        return countPage;
    }


    ///////////      NAVIGATION VIEW       /////////////////
    public void onParentsNav() {
        /*navigationView = new ParentsNavigationView(main, main.language.getLanguage());
        this.main.view.dlMain.addView(navigationView);
        onLoadFamilyRoomToNavigationView();
        navigationView.setOnItemClickListener(new ParentsNavigationView.OnItemClickListener() {
            @Override
            public void onFamilyMemberClick(String zID) {
                if (main.view.getViewPager() != null) {
                    main.view.getViewPager().setVisibility(View.GONE);
                }
                onSelectFragment("FamilyMemberFragment");
                main.view.onToolbarSelect(2, main.language.getLanguage().getString(R.string.family_member));
                model.setIdSelectFamilyMember(zID);
                onCloseNavMenu();
            }
            @Override
            public void onNextPageClick(String fragment) {
                if (main.view.getViewPager() != null) {
                    main.view.getViewPager().setVisibility(View.GONE);
                }
                if (main.view.getRlViewPager() != null) {
                    main.view.getRlViewPager().setVisibility(View.GONE);
                }
                onSelectFragment(fragment);
                switch (fragment) {
                    case "AddUserFragment":
                        main.view.onToolbarSelect(2, main.language.getLanguage().getString(R.string.add_user));
                        break;
                    case "DeviceGroupFragment":
                        main.view.onToolbarSelect(2, main.language.getLanguage().getString(R.string.device_group));
                        break;
                    case "SettingsParentFragment":
                        main.view.onToolbarSelect(2, main.language.getLanguage().getString(R.string.settings));
                        break;
                    case "AboutAppFragment":
                        main.view.onToolbarSelect(2, main.language.getLanguage().getString(R.string.about_application));
                        break;
                    case "FamilyGroupFragment":
                        main.view.onToolbarSelect(2, main.language.getLanguage().getString(R.string.family_group));
                        break;
                }
                onCloseNavMenu();
            }
        });*/
    }
    public void onParentsNavRemove() {
        //this.main.view.dlMain.removeView(navigationView);
    }
    public void onOpenNavMenu() {
        this.main.view.dlMain.openDrawer(GravityCompat.START);
    }
    public void onCloseNavMenu() {
        if (this.main.view.dlMain.isDrawerOpen(GravityCompat.START)) {
            this.main.view.dlMain.closeDrawer(GravityCompat.START);
        }
    }
    private void onLoadFamilyRoomToNavigationView() {
        /*if (navigationView != null) {
            for (int i = 0; i < model.familyRoomList.size(); i++) {
                String child = model.familyRoomList.get(i).getLogin();
                String name = "";
                if (child != null) {
                    switch (child) {
                        case "Father":
                            name = model.familyRoomList.get(i).getLogin();
                            break;
                        case "Mother":
                            name = model.familyRoomList.get(i).getLogin();
                            break;
                        /*case "Son":
                            name = model.familyRoomList.get(i).getName();
                            break;
                        case "Daughter":
                            name = model.familyRoomList.get(i).getName();
                            break;
                    }
                }
                //navigationView.onLoadFamilyMember(model.familyRoomList.get(i).getAvatar(), name, model.familyRoomList.get(i).getZId());
            }
        }*/
    }
    public void onNavChangeLanguage(android.content.res.Resources res) {
        /*if (navigationView != null) {
            navigationView.changeLanguage(res);
        }*/
    }
    ///////////      NAVIGATION VIEW       /////////////////
    public void initParentSignIn() {
        onParentsNav();
     //   main.view.onParentOn();
        main.initViews();
        if (main.prefs.getFamilyData("avatar") != null) {
            String image = main.prefs.getFamilyData("avatar");
            main.view.setAvatar(image);
        }
        model.onRequestFamilyRoom(callBack, main.login.loadUserDate(LoginKey.ID));
    }
    @Override
    public void onStart() {
        initParentSignIn();
      /*  if (main.prefs.isLoginStatus()) {
            switch (main.prefs.getLoginData("typeAccount")) {
                case "parent":
                    String status = main.prefs.getFamilyData("status");
                    if (status != null) {
                        if (status.equals("Father") || status.equals("Mother")) {
                            initParentSignIn();
                        } else if (status.equals("Son") || status.equals("Daughter")) {
                            onSelectFragment("ChildMainFragment");
                        } else {
                            onSelectFragment("FamilyMemberListFragment");
                        }
                        model.onDeviceCheckIfExist(main.iDevice.onDeviceRegistration(main, main.prefs.getFamilyData("id")),
                                main.iDevice.getDeviceGenerationNumber(main),
                                main.prefs.getLoginData("id"),
                                main.prefs.getFamilyData("id"));
                    }
                    break;
            /*    case "mentor":
                    main.view.onMentorOn();
                    String image = main.prefs.getMentorData("avatar");
                    if (image != null) {
                        main.view.setMentorAvatar(image);
                        main.initMentorState();
                    } else {
                        onLoadMentorData();
                    }
                    break;
            }
        } else {
            Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                    .take(2)
                    .map(v -> 2 - v)
                    .subscribe(onNext -> {
                            },//on every second pass trigger
                            onError -> {
                            },//do on error
                            () -> {
                                onSelectFragment("SignInFragment");
                            },
                            onSubscribe -> {
                                onSelectFragment("Splash");
                            });
        } */
        main.onLoadAnimation(false);
    }
    public void onLoadMentorData() {
        model.onRequestMentor(callBack, main.prefs.getLoginData("id"));
    }
    /////////          FRAGMENTS         //////////
    @Override
    public void onSelectFragment(String fragment) {
        main.onReplaceFragment(model.getFragment(fragment));
        main.prefs.onSaveCurrentPage(fragment);
        main.onLoadAnimation(false);
    }
    @Override
    public void onDestroyView() {
        if (model != null) {
            model = null;
        }
        if (callBack != null) {
            callBack = null;
        }
        if (main != null) {
            main = null;
        }
    }
}
