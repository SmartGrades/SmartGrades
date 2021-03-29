package kz.tech.smartgrades.childs_module.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.content.ContextCompat;

import kz.tech.smartgrades.root.service.CheckLaunchService;

public class MyJobIntentService extends JobIntentService {

    /* Give the Job a Unique Id */
    private static final int JOB_ID = 1000;
    public static void enqueueWork(Context ctx, Intent intent) {
        enqueueWork(ctx, MyJobIntentService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        /* your code here */
        /* reset the alarm */
        /////////////////////                     SERVICE                  /////////////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//  new version < 26
            boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildService.class, getApplicationContext());
            if (!isServiceLife) {
                Intent intent1 = new Intent(getApplicationContext(), ChildService.class);
                intent1.putExtra("eSparta", "inputExtra");
                ContextCompat.startForegroundService(getApplicationContext(), intent1);
            }
        }
        if (Build.VERSION.SDK_INT < 26) {//  old version device == 24
            boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildService.class, getApplicationContext());
            if (!isServiceLife) {
                Intent intent2 = new Intent(getApplicationContext(), ChildService.class);
                intent2.putExtra("eSparta", "inputExtra");
                PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, intent2, 0);
                AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                alarm.cancel(pendingIntent);
                alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 15000, pendingIntent);
            }
        }
        AlarmReceiver.setAlarm(getApplicationContext(),false);
        stopSelf();
    }

}
