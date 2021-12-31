package kz.tech.smartgrades.childs_module.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.content.ContextCompat;

import kz.tech.smartgrades.root.service.CheckLaunchService;

public class ChildBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            if (Build.VERSION.SDK_INT < 26) {//  OLD VERSION
                boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildService.class, context);
                if (!isServiceLife) {
                    Intent i = new Intent(context, ChildService.class);
                    i.putExtra("eSparta", "inputExtra");
                    PendingIntent pendingIntent = PendingIntent.getService(context, 0, i, 0);
                    AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarm.cancel(pendingIntent);
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60000, pendingIntent);
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//  NEW VERSION
                boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildService.class, context);
                if (!isServiceLife) {
                    Intent j = new Intent(context, ChildService.class);
                    j.putExtra("eSparta", "inputExtra");
                    ContextCompat.startForegroundService(context, j);
                }
            }
        }
    }
}