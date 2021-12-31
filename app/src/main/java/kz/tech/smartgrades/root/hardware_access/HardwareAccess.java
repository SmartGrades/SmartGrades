package kz.tech.smartgrades.root.hardware_access;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import kz.tech.smartgrades.root.var_resources.PermissionCode;

public class HardwareAccess implements IHardwareAccess {
    private Context context;
    public HardwareAccess(Context context) {
        this.context = context;
    }

    private boolean hasPermissionToReadNetworkHistory() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        final AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), context.getPackageName());
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            appOps.startWatchingMode(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    context.getApplicationContext().getPackageName(),
                    new AppOpsManager.OnOpChangedListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.KITKAT)
                        public void onOpChanged(String op, String packageName) {
                            int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                                    android.os.Process.myUid(), context.getPackageName());
                            if (mode != AppOpsManager.MODE_ALLOWED) {
                                return;
                            }
                            appOps.stopWatchingMode(this);
                            // Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            //  getApplicationContext().startActivity(intent);
                        }
                    });
        }
        //  requestReadNetworkHistoryAccess();
        return false;
    }

    @Override
    public boolean isToReadNetworkHistory() {
        return hasPermissionToReadNetworkHistory();
    }

    /////////////////          PHONE ACCESS           ///////////////////////////////
    @Override
    public boolean isPhoneAccess() {
        return isCheckPhonePermission();
    }
    @Override
    public void onPhoneAccess() {
        String[] permissions = { Manifest.permission.READ_PHONE_STATE };
        ActivityCompat.requestPermissions((AppCompatActivity) context, permissions, PermissionCode.LOCATION_PERMISSION_REQUEST_CODE);
    }
    private boolean isCheckPhonePermission() {
        if (ContextCompat.checkSelfPermission(context.getApplicationContext(), PermissionCode.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
}

















/*


if (mode == AppOpsManager.MODE_DEFAULT) {
    granted = (context.checkCallingOrSelfPermission(Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
} else {
    granted = (mode == AppOpsManager.MODE_ALLOWED);
}


if (mode == AppOpsManager.MODE_DEFAULT) {
    granted = false;
} else {
    granted = (mode == AppOpsManager.MODE_ALLOWED);
}

granted = (mode == AppOpsManager.MODE_ALLOWED);

AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), context.getPackageName());
boolean granted = (mode == AppOpsManager.MODE_ALLOWED);
 */

  /*
     public boolean check_UsgAccs(){
        long tme = System.currentTimeMillis();
        UsageStatsManager usm = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            usm = (UsageStatsManager)getActivity().getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);
            List<UsageStats> al= usm.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY, tme - (1000 * 1000), tme);
            return  al.size()>0;
        }

       return false;

    }
     */