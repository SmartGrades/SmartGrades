package kz.tech.smartgrades.childs_module.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PowerManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import kz.tech.esparta.R;
import kz.tech.smartgrades.MainActivity;
import kz.tech.smartgrades.root.firebase.FireBaseAutoCharge;
import kz.tech.smartgrades.root.firebase.FireBaseChildrenTime;

public class ChildIntentService extends IntentService {
    public static final String CHANNEL_ID = "ForegroundServiceChannel2";
    private static final String PREFS_STORAGE_LOGIN = "PREFS_STORAGE_LOGIN";
    private static final String PREFS_STORAGE_ACCOUNT = "PREFS_STORAGE_ACCOUNT";
    private static final String PREFS_STORAGE_AUTO_CHARGE = "PREFS_STORAGE_AUTO_CHARGE";
    private static final String PREFS_STORAGE_CHILDREN_TIME = "PREFS_STORAGE_CHILDREN_TIME";
    private PowerManager.WakeLock wakeLock;
    private String idLogin;
    private String idAccount;
    public ChildIntentService() {
        super("MyChildIntentService");
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
    @Override
    public ComponentName startForegroundService(Intent service) {
        return super.startForegroundService(service);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        android.util.Log.e("TAG ", "IntentService: START");
        idLogin = onLoadLoginID();
        String[] strings = onLoadAccountID();
        idAccount = strings[4];

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "eSpartaApp:Wakelock");
        wakeLock.acquire();


    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String inputService = intent.getStringExtra("inputService");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(inputService)
                .setContentText(inputService)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        if (inputService != null) {
            switch (inputService) {
                case "autoCharge": onAutoCharge(); break;
                case "timeChildren": onChildrenTime(); break;
            }
        }
    }
    private void onAutoCharge() {
        DatabaseReference dbrAutoCharge = new FireBaseAutoCharge().getAutoCharge();//  AutoCharge
        dbrAutoCharge.child(idLogin).child(idAccount).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Iterator i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {
                        String monday = (String) ((DataSnapshot) i.next()).getValue();
                        String tuesday = (String) ((DataSnapshot) i.next()).getValue();
                        String wednesday = (String) ((DataSnapshot) i.next()).getValue();
                        String thursday = (String) ((DataSnapshot) i.next()).getValue();
                        String friday = (String) ((DataSnapshot) i.next()).getValue();
                        String saturday = (String) ((DataSnapshot) i.next()).getValue();
                        String sunday = (String) ((DataSnapshot) i.next()).getValue();
                        String timeAutoCharge = (String) ((DataSnapshot) i.next()).getValue();
                        String isSwitch = (String) ((DataSnapshot) i.next()).getValue();
                        onSavePrefsAutoCharge(monday, tuesday, wednesday, thursday, friday, saturday,
                                sunday, timeAutoCharge, isSwitch);
                        android.util.Log.e("AutoCharge Tag", " Good load and save prefs");
                    }
                    android.util.Log.e("AutoCharge Tag", " Exist");
                } else {
                    android.util.Log.e("AutoCharge Tag", " Not exist");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                android.util.Log.e("AutoCharge Tag", " Error dbr");
            }
        });
    }
    private void onChildrenTime() {
        DatabaseReference dbrChildrenTime = new FireBaseChildrenTime().getChildrenTime();//  ChildrenTime
        dbrChildrenTime.child(idLogin).child(idAccount).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Iterator i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {
                        String monday = (String) ((DataSnapshot) i.next()).getValue();
                        String tuesday = (String) ((DataSnapshot) i.next()).getValue();
                        String wednesday = (String) ((DataSnapshot) i.next()).getValue();
                        String thursday = (String) ((DataSnapshot) i.next()).getValue();
                        String friday = (String) ((DataSnapshot) i.next()).getValue();
                        String saturday = (String) ((DataSnapshot) i.next()).getValue();
                        String sunday = (String) ((DataSnapshot) i.next()).getValue();
                        String isSwitch = (String) ((DataSnapshot) i.next()).getValue();
                        onSavePrefsChildrenTime(monday, tuesday, wednesday, thursday, friday, saturday, sunday,  isSwitch);
                        android.util.Log.e("ChildrenTime Tag", " Good load and save prefs");
                    }
                    android.util.Log.e("ChildrenTime Tag", " Exist");
                } else {
                    android.util.Log.e("ChildrenTime Tag", " Not exist");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                android.util.Log.e("ChildrenTime Tag", " Error dbr");
            }
        });
    }
    private String onLoadLoginID() {
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_STORAGE_LOGIN, 0);
        return settings.getString("id", "");
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
    private void onSavePrefsAutoCharge(String monday, String tuesday, String wednesday, String thursday, String friday,
                                 String saturday, String sunday, String timeAutoCharge, String isSwitch) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(PREFS_STORAGE_AUTO_CHARGE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("monday", monday);
        editor.putString("tuesday", tuesday);
        editor.putString("wednesday", wednesday);
        editor.putString("thursday", thursday);
        editor.putString("friday", friday);
        editor.putString("saturday", saturday);
        editor.putString("sunday", sunday);
        editor.putString("timeAutoCharge", timeAutoCharge);
        editor.putString("isSwitch", isSwitch);
        editor.apply();
    }
    private void onSavePrefsChildrenTime(String monday, String tuesday, String wednesday, String thursday,
                                         String friday, String saturday, String sunday, String isSwitch) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(PREFS_STORAGE_CHILDREN_TIME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("monday", monday);
        editor.putString("tuesday", tuesday);
        editor.putString("wednesday", wednesday);
        editor.putString("thursday", thursday);
        editor.putString("friday", friday);
        editor.putString("saturday", saturday);
        editor.putString("sunday", sunday);
        editor.putString("isSwitch", isSwitch);
        editor.apply();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        android.util.Log.e("TAG ", "IntentService: DESTROY");
        wakeLock.release();
        android.util.Log.e("TAG ", "WakeLock: Release");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true);
        }
    }
}
