package kz.tech.smartgrades.authentication.quick_access_sign_out;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.WorkManager;

import kz.tech.smartgrades.childs_module.service.ChildIntentService;
import kz.tech.smartgrades.childs_module.service.ChildService;
import kz.tech.smartgrades.MainActivity;
import kz.tech.smartgrades.root.models.ModelFamilyRoom;
import kz.tech.smartgrades.root.service.CheckLaunchService;

public class QuickAccessSignOutFragment extends Fragment {
    private static final String PREFS_CHILD_COINS = "PREFS_CHILD_COINS";
    private static final String PREFS_CHILD_LOGIN = "PREFS_CHILD_LOGIN";
    private MainActivity main;
    private QuickAccessSignOutView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        main = (MainActivity) getActivity();
        view = new QuickAccessSignOutView(getActivity(), main.language.getLanguage());
        view.setOnItemClickListener(new QuickAccessSignOutView.OnItemClickListener() {
            @Override
            public void onBackButtonClick() {
                if (main.prefs.getFamilyData("status") != null) {
                    if (main.prefs.getFamilyData("status").equals("Father") || main.prefs.getFamilyData("status").equals("Mother")) {
                        main.presenter.onSelectFragment("ParentMainFragment");
                    } else if (main.prefs.getFamilyData("status").equals("Son") || main.prefs.getFamilyData("status").equals("Daughter")) {
                        main.presenter.onSelectFragment("ChildMainFragment");
                    }
                }
            }
            @Override
            public void onMenuClick(View view) {

            }
            @Override
            public void onMessageClick(String msg) {
                main.onToastMsg(msg);
            }
            @Override
            public void onAccessOkClick(ModelFamilyRoom modelFamilyRoom) {
                switch (modelFamilyRoom.getLogin()) {
                    case "Father": onNextParentOrChildPage(modelFamilyRoom, "Parent"); break;
                    case "Mother": onNextParentOrChildPage(modelFamilyRoom, "Parent"); break;
                    case "Son": onNextParentOrChildPage(modelFamilyRoom, "Child"); break;
                    case "Daughter": onNextParentOrChildPage(modelFamilyRoom, "Child"); break;
                }
            }
        });
        /*view.setData(new ModelFamilyGroup(
                main.prefs.getFamilyData("avatar"),
                main.prefs.getFamilyData("name"),
                main.prefs.getFamilyData("quickAccessCode"),
                main.prefs.getFamilyData("status"),
                main.prefs.getFamilyData("id")));*/
    }
    private void onNextParentOrChildPage(ModelFamilyRoom model, String status) {
        switch (status) {
            case "Parent":

                break;
            case "Child":
                main.getSharedPreferences(PREFS_CHILD_LOGIN, 0).edit().clear().apply();//  Remove LOGIN PREFFs
                main.getSharedPreferences(PREFS_CHILD_COINS, 0).edit().clear().apply();//  Remove COINS PREFFs
                main.prefs.onRemoveFamilyAccount();//  Remove Account Data(ID, Avatar, Name, PIN)
                boolean resultService = CheckLaunchService.isMyServiceRunning(ChildService.class, main);
                if (resultService) {
                    main.stopService(new Intent(getActivity(), ChildService.class));
                }
                boolean resultIntentService = CheckLaunchService.isMyServiceRunning(ChildIntentService.class, main);
                if (resultIntentService) {
                    main.stopService(new Intent(getActivity(), ChildIntentService.class));
                }
                WorkManager.getInstance(main).cancelAllWorkByTag("jobTag");

                main.presenter.onSelectFragment("FamilyMemberListFragment");
                break;

        }
    }




















    @Override
    public void onDestroy() {
        super.onDestroy();
        if (main != null) {
            main = null;
        }
        if (view != null) {
            view = null;
        }
    }
}
