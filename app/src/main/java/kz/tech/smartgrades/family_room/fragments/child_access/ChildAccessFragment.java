package kz.tech.smartgrades.family_room.fragments.child_access;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import kz.tech.esparta.BuildConfig;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.family_room.FamilyRoomActivity;
import kz.tech.smartgrades.family_room.fragments.child_access.dialog.ChildAccessDialog;

public class ChildAccessFragment extends Fragment {

    private FamilyRoomActivity activity;
    private ChildAccessView view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }

    private void initViews() {
        activity = (FamilyRoomActivity) getActivity();
        view = new ChildAccessView(getActivity(), activity.language.getLanguage());
        view.setOnItemClickListener(new ChildAccessView.OnItemClickListener() {
            @Override
            public void onBackButtonClick() {
               // main.prefs.onRemoveFamilyAccount();//  Remove Account Data(ID, Avatar, Name, PIN)
                activity.onNextFragment(F.FAMILY_MEMBER_LIST);
            }

            @Override
            public void onServiceKillerClick() {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                }
            }

            @Override
            public void onNextPageClick() {
                activity.onNextFragment(F.QUICK_ACCESS_SIGN_IN);
            }

            @Override
            public void onMsgClick(String msg) {
                activity.onShowToast(msg);
            }

            @Override
            public void onTextBodyAccessClick(String textBody) {
                ChildAccessDialog dialog = new ChildAccessDialog(getActivity(), activity.language.getLanguage());
                dialog.showAlertDialog(textBody);
            }

            @Override
            public void onPhoneClick() {
                if (activity.hardwareAccess.isPhoneAccess()) {
                    startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                } else {
                    activity.hardwareAccess.onPhoneAccess();
                }
            }

            @Override
            public void onModelPhoneClick(String modelName) {
                switch (modelName) {
                    case "Xiaomi": onXiaomiAutoStart(); break;
              //      case "": break;
               //     case "": break;
              //      case "": break;
                }
            }
        });
        view.updateCheckButtonServiceKiller(activity.hardwareAccess.isToReadNetworkHistory());
    }

    private void onXiaomiAutoStart() {
    //    Intent intent1 = new Intent();
    //    intent1.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
    //    main.startActivity(intent1);


        Intent iLOL = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        iLOL.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        iLOL.setData(uri);
        startActivity(iLOL);
    }

    @Override
    public void onResume() {
        super.onResume();
        //  Service killer access
        view.updateCheckButtonServiceKiller(activity.hardwareAccess.isToReadNetworkHistory());
        //  Phone access
        view.updateCheckButtonPhoneAccess(activity.hardwareAccess.isPhoneAccess());
    }

    @Override
    public void onPause() {
        super.onPause();
        //  Service killer access
        view.updateCheckButtonServiceKiller(activity.hardwareAccess.isToReadNetworkHistory());
        //  Phone access
        view.updateCheckButtonPhoneAccess(activity.hardwareAccess.isPhoneAccess());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (activity != null) { activity = null; }
        if (view != null) { view = null; }
    }






    private void addAutoStartup() {

        try {
            Intent intent = new Intent();
            String manufacturer = android.os.Build.MANUFACTURER;
            if ("xiaomi".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            } else if ("oppo".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
            } else if ("vivo".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
            } else if ("Letv".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity"));
            } else if ("Honor".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
            }

            List<ResolveInfo> list = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if  (list.size() > 0) {
                activity.startActivity(intent);
            }
        } catch (Exception e) {
            Log.e("exc" , String.valueOf(e));
        }
    }



}
