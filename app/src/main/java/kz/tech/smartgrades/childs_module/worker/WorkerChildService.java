package kz.tech.smartgrades.childs_module.worker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.work.RxWorker;
import androidx.work.WorkerParameters;

import io.reactivex.Single;
import kz.tech.smartgrades.childs_module.service.ChildIntentService;
import kz.tech.smartgrades.childs_module.service.ChildService;
import kz.tech.smartgrades.root.service.CheckLaunchService;

public class WorkerChildService extends RxWorker {
    private static final String PREFS_STORAGE_ACCOUNT = "PREFS_STORAGE_ACCOUNT";
    private String statusAccount;
    public WorkerChildService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        String[] strings = onLoadAccountID();
        statusAccount = strings[3];
        if (statusAccount != null) {
            if (statusAccount.equals("Son") || statusAccount.equals("Daughter")) {
                /////////////////            SERVICE             //////////////////////
                android.util.Log.e("TAG", "Worker: START");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildService.class, getApplicationContext());
                    if (!isServiceLife) {
                        Intent intent = new Intent(getApplicationContext(), ChildService.class);
                        intent.putExtra("eSparta", "inputExtra");
                        ContextCompat.startForegroundService(getApplicationContext(), intent);
                        return Single.just(Result.success());
                    } else {
                        return Single.just(Result.retry());
                    }
                }
                /////////////////        INTENT SERVICE          ////////////////////////
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Intent intent = new Intent(getApplicationContext(), ChildIntentService.class);
                    intent.putExtra("inputService", "autoCharge");
                    ContextCompat.startForegroundService(getApplicationContext(), intent);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Intent intent = new Intent(getApplicationContext(), ChildIntentService.class);
                    intent.putExtra("inputService", "timeChildren");
                    ContextCompat.startForegroundService(getApplicationContext(), intent);
                }
            }
        }
     /*   boolean result = CheckLaunchService.isMyServiceRunning(ChildService.class, getApplicationContext());
        if (!result) {
            Intent intent = new Intent(getApplicationContext(), ChildService.class);
            intent.putExtra("eSparta", "inputExtra");
            PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, intent, 0);
            AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pendingIntent);
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 15000, pendingIntent);
        }*/
        return Single.just(Result.failure());
    }
    private String[] onLoadAccountID() {
        String[] strings = new String[5];
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_STORAGE_ACCOUNT, 0);
        strings[0] = settings.getString("avatar", "");
        strings[1] = settings.getString("name", "");
        strings[2] = settings.getString("pin", "");
        strings[3] = settings.getString("status", "");
        strings[4] = settings.getString("id", "");
        return strings;
    }
}