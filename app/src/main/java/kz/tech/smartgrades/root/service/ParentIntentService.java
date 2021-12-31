package kz.tech.smartgrades.root.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import kz.tech.esparta.R;
import kz.tech.smartgrades.MainActivity;

public class ParentIntentService extends IntentService {
    public static final String CHANNEL_ID = "Start: IntentService";
    public ParentIntentService(String name) {
        super(name);
    }
    @Override
    public ComponentName startForegroundService(Intent service) {
        return super.startForegroundService(service);
    }
    @Override
    public void onCreate() {
        super.onCreate();

    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String parentInputService = intent.getStringExtra("parentInputService");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(parentInputService)
                .setContentText(parentInputService)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        Context context = getApplicationContext();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);


        if (parentInputService != null) {
            switch (parentInputService) {
                case "FamilyRoom": onFamilyRoomCall(); break;
              //  case "": break;
             //   case "": break;
            }
        }

    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
    private void onFamilyRoomCall() {

    }
}
