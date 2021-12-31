package kz.tech.smartgrades.block_page;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


import io.reactivex.disposables.Disposable;

import kz.tech.smartgrades._Firebase;
import kz.tech.smartgrades.childs_module.service.ChildService;
import kz.tech.smartgrades.MainActivity;
import kz.tech.smartgrades.root.service.CheckLaunchService;

public class LockScreenActivity extends AppCompatActivity {

    private static final String PREFS_CHILD_COINS = "PREFS_CHILD_COINS";
    private BlockPageView view;
    long totalCoins = 0;
    String idLogin = "";
    String idChild = "";
    private Disposable disposable;
    String selectPackage = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        view = new BlockPageView(this);
        setContentView(view);
        view.setOnItemClickListener(new BlockPageView.OnItemClickListener() {
            @Override
            public void onItemClick() {
                startActivity(new Intent(LockScreenActivity.this, MainActivity.class));
            }
        });

        String[] strings = onLoadAccountID();

        if (strings[0] != null) {
            String image = strings[0];
            view.setAvatar(image);
        }

        /*Intent intent = getIntent();
        selectPackage = intent.getStringExtra("package_name");
        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        //  List<ActivityManager.RunningAppProcessInfo> pids = am.getRunningAppProcesses();
        if (selectPackage != null && !selectPackage.isEmpty()) {
            if (am != null) am.killBackgroundProcesses(selectPackage);
        }*/
        //   finish();

        //   android.os.Process.killProcess(android.os.Process.myPid());

    /*    loadCoins();

        disposable = Flowable.interval(1L, TimeUnit.SECONDS)//  Время для интервала, выбор интервала, секунды, минуты, часы
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (totalCoins == 0) {
                        onRequestTotalCoins();
                    }
                });*/
    }

    /*
     * So here's what's going on: When the user presses the back button,
     * the Instagram app (or any other app that's being blocked) is relaunched
     * from the stack and that triggers the LockScreenActivity to be launched
     * again. Kinda goes into an infinite loop in a sense. What I'm going to do
     * to get around that, is completely kill the Instagram application.
     *
     * Every running application has a PID (process id). This is how the system
     * keeps track of which apps are running. First, we're gonna need to find the
     * PID of the blocked app (Instagram in this case).
     */
    private void saveCoins() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(PREFS_CHILD_COINS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idLogin", idLogin);
        editor.putString("idChild", idChild);
        editor.putLong("coins", totalCoins);
        editor.apply();
    }

    private void loadCoins() {
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_CHILD_COINS, 0);
        idLogin = settings.getString("idLogin", "");
        idChild = settings.getString("idChild", "");
        totalCoins = settings.getLong("coins", 0);
    }

    private void onRequestTotalCoins() {
        DatabaseReference dbrChildData = new _Firebase().getRefChildData();//  ChildData
        dbrChildData.child(idLogin).child(idChild).child("coins").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String value = (String) dataSnapshot.getValue();
                    totalCoins = Integer.parseInt(value);
                    if (totalCoins > 0) {
                        saveCoins();
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(startMain);
                    }
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Grab a list of all running processes and their PIDs.
        //ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        //List<ActivityManager.RunningAppProcessInfo> pids = am.getRunningAppProcesses();
        // Now loop through the list of PIDs and find Instagram's PID.
        // Killing any process for blocked applications when the back button is pressed while the lock screen is displayed
        //   am.killBackgroundProcesses("kz.tech.esparta");
        //am.killBackgroundProcesses(selectPackage);

        // Display confirmation here, finish() activity.
        /*Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);*/

        //  android.os.Process.killProcess(android.os.Process.myPid());

        /////////////////////                     SERVICE                  /////////////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//  new version < 26
            boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildService.class, this);
            if (!isServiceLife) {
                Intent intent = new Intent(this, ChildService.class);
                intent.putExtra("eSparta", "inputExtra");
                ContextCompat.startForegroundService(this, intent);
            }
        }
        if (Build.VERSION.SDK_INT < 26) {//  old version device == 24
            boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildService.class, this);
            if (!isServiceLife) {
                Intent intent = new Intent(this, ChildService.class);
                intent.putExtra("eSparta", "inputExtra");
                PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
                AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
                alarm.cancel(pendingIntent);
                alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 15000, pendingIntent);
            }
        }
        finish();
        // android.os.Process.killProcess(android.os.Process.myPid());
        super.onBackPressed();
    }
    /*
     * So here's what's going on: When the user presses the back button,
     * the Instagram app (or any other app that's being blocked) is relaunched
     * from the stack and that triggers the LockScreenActivity to be launched
     * again. Kinda goes into an infinite loop in a sense. What I'm going to do
     * to get around that, is completely kill the Instagram application.
     */
    /*
     * Every running application has a PID (process id). This is how the system
     * keeps track of which apps are running. First, we're gonna need to find the
     * PID of the blocked app (Instagram in this case).
     */

    @Override
    public void onStop() {
        // Grab a list of all running processes and their PIDs.
        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> pids = am.getRunningAppProcesses();
        // Now loop through the list of PIDs and find Instagram's PID.
        // Killing any process for blocked applications when the back button is pressed while the lock screen is displayed
        //   am.killBackgroundProcesses("kz.tech.esparta");
        am.killBackgroundProcesses(selectPackage);

        // Display confirmation here, finish() activity.
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

        //    android.os.Process.killProcess(android.os.Process.myPid());

        /////////////////////                     SERVICE                  /////////////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//  new version < 26
            boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildService.class, this);
            if (!isServiceLife) {
                Intent intent = new Intent(this, ChildService.class);
                intent.putExtra("eSparta", "inputExtra");
                ContextCompat.startForegroundService(this, intent);
            }
        }
        if (Build.VERSION.SDK_INT < 26) {//  old version device == 24
            boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildService.class, this);
            if (!isServiceLife) {
                Intent intent = new Intent(this, ChildService.class);
                intent.putExtra("eSparta", "inputExtra");
                PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
                AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
                alarm.cancel(pendingIntent);
                alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 15000, pendingIntent);
            }
        }

        finish();
        // android.os.Process.killProcess(android.os.Process.myPid());
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

    }


    public String[] onLoadAccountID() {
        String[] strings = new String[5];
        SharedPreferences settings = getSharedPreferences("PREFS_STORAGE_FAMILY_ACCOUNT", 0);
        strings[0] = settings.getString("avatar", "");
        strings[1] = settings.getString("name", "");
        strings[2] = settings.getString("pin", "");
        strings[3] = settings.getString("status", "");
        strings[4] = settings.getString("id", "");
        return strings;
    }

    public String onLoadLoginID() {
        SharedPreferences settings = getSharedPreferences("PREFS_STORAGE_LOGIN", 0);
        return settings.getString("id", "");
    }
}
