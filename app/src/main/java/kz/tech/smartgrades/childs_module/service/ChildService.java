package kz.tech.smartgrades.childs_module.service;

import android.app.ActivityManager;

import android.app.AlarmManager;

import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.PowerManager;
import android.text.TextUtils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;


import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import kz.tech.smartgrades.Convert;
import kz.tech.esparta.R;
import kz.tech.smartgrades.MainActivity;
import kz.tech.smartgrades._Firebase;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.root.service.CheckLaunchService;


public class ChildService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel1";
    private static final String PREFS_STORAGE_LOGIN = "PREFS_STORAGE_LOGIN";
    private static final String PREFS_STORAGE_FAMILY_ACCOUNT = "PREFS_STORAGE_FAMILY_ACCOUNT";

    private static final String PREFS_STORAGE_AUTO_CHARGE = "PREFS_STORAGE_AUTO_CHARGE";
    private static final String PREFS_STORAGE_CHILDREN_TIME = "PREFS_STORAGE_CHILDREN_TIME";
    private static final String PREFS_CHILD_COINS = "PREFS_CHILD_COINS";
    private static final String PREFS_APP_LOCK_LIST_SELECT = "PREFS_APP_LOCK_LIST_SELECT";
    private PowerManager.WakeLock wakeLock;
    private String childId;

    private ActivityManager activityManager;
    List<ActivityManager.RunningAppProcessInfo> pis = null;
    List<ActivityManager.RunningTaskInfo> taskInfo = null;

    private Disposable disposable;
    private CountDownTimer countDownTimer = null;
    private boolean isTimerStart = false;

    ArrayList<String> servicePackageList = null;
    ArrayList<String> packageIgnoreList = null;

    private boolean timeIsUp = true;
    private byte timeInterval = 0;
    private int timeMinuteIsUp = 0;
    private int totalTimeMinute = 0;
    private int totalCoins = 0;
    private String activeTool;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public ComponentName startForegroundService(Intent service) {
        return super.startForegroundService(service);
    }

    //@Override
    public void onCreate() {
        super.onCreate();

        android.util.Log.e("TAG ", "Service: START");
        childId = onLoadLoginID();
        //String[] strings = onLoadAccountID();
        //statusAccount = strings[3];
        //idAccount = strings[4];

        PowerManager powerManager = (PowerManager) getApplicationContext().getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "eSpartaApp:Wakelock");
        wakeLock.acquire();

        activityManager = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);

        String selectAppLockList = onLoadAppLockList();
        switch (selectAppLockList) {
            case "standardList":
                servicePackageList = onStandardList();
                break;
            case "customizableList":
                servicePackageList = onCustomizableList();
                break;
        }
        getTimeIsUp();
        onLockAppsTimerStart();
    }

    //////////////////////             TIMER              //////////////////////////
    private void startCountDownTimer() {
        /*long countDownTimerCoins = totalCoins * 60 * 1000;
        countDownTimer = new CountDownTimer(countDownTimerCoins, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                minute++;
                if (minute == 60) {
                    if (totalCoins > 0) {
                        totalCoins--;
                    }

                    saveCoins(totalCoins);
                    onRequestCoinsChange();
                    minute = 0;
                } else if (minute > 50 && totalCoins == 1) {
                    totalCoins = 0;

                    saveCoins(totalCoins);
                    onRequestCoinsChange();
                    minute = 0;

                    onLockAppsTimerStart();
                    if (countDownTimer != null) {
                        stopCountDownTimer();
                    }
                }
                int currentSecond = (int) millisUntilFinished / 1000;
                if (60 == currentSecond) {//  1 minute(60 second) left
                    onPushTime("Obolas end", "1 minute left");
                }
                if (totalCoins == 20) {//  20 minutes left
                    onPushTime("Obolas end", "20 minute left");
                }
            }

            @Override
            public void onFinish() {
                if (isTimerStart)
                    stopCountDownTimer();
            }
        };
        countDownTimer.start();
        isTimerStart = true;

        if (totalCoins == 0) {
            onLockAppsTimerStart();
        }*/
    }

    private void stopCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        isTimerStart = false;
    }

    /////////////////////           NOTIFICATION            ///////////////////////////
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
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(input)
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        //do heavy work on a background thread
        //stopSelf();
        return START_STICKY;
    }

    private void onRequestTotalCoins() {
        DatabaseReference dbrChildData = new _Firebase().getRefChildData();//  ChildData
        dbrChildData.child(childId).child("coins").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String value = (String) dataSnapshot.getValue();
                    totalCoins = Integer.parseInt(value);
                    saveCoins(totalCoins);
                    if (countDownTimer == null) {
                        startCountDownTimer();
                    }
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onRequestCoinsChange() {
        DatabaseReference dbrChildData = new _Firebase().getRefChildData();//  ChildData
        Query query = dbrChildData.child(childId).child("coins");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String totalCoinsDB = (String) dataSnapshot.getValue();
                int numb = Integer.parseInt(totalCoinsDB);
                numb -= 1;
                String result = String.valueOf(numb);
                dataSnapshot.getRef().setValue(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    private void onPushTime(String title, String text) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_android)
                        .setContentTitle(title)
                        .setContentText(text);
        Notification notification = builder.build();
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }


    //////////////////////         LOCK APPS         ////////////////////////////////
    private void onLockAppsTimerStart() {
        disposable = Observable.interval(3000L, TimeUnit.MILLISECONDS)
                .timeInterval()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Timed<Long>>() {
                    @Override
                    public void accept(@NonNull Timed<Long> longTimed) throws Exception {
                        String selectPackage = getRecentApps(getApplicationContext());
                        if (packageInIgnoreList(selectPackage)) return;
                        if (!timeIsUp){
                            timeInterval += 3;
                            if (timeInterval >= 60) setTimeIsUp(1);
                        }
                        else{
                            activityManager.killBackgroundProcesses(selectPackage);

                            Intent startMain = new Intent(Intent.ACTION_MAIN);
                            startMain.addCategory(Intent.CATEGORY_HOME);
                            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(startMain);

                            Intent i = new Intent(getApplicationContext(), ChildActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            i.putExtra("isBlock", true);
                            getApplicationContext().startActivity(i);
                        }

                    }
                });
    }

    private void getTimeIsUp() {
        final int[] totalCoins = {0};
        final Query[] qTime = new Query[1];
        final ValueEventListener[] valueEventListener = {null};
        DatabaseReference dbrSettings = FirebaseDatabase.getInstance().getReference("Settings").child(childId);
        dbrSettings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if ((dataSnapshot.child("TemporaryCoins").child("Enable").getValue()).toString().equals("true"))
                        activeTool = "Coins";
                    else if ((dataSnapshot.child("GadgetTime").child("Enable").getValue()).toString().equals("true"))
                        activeTool = "GadgetTime";
                    else return;
                    DatabaseReference dbrTime = FirebaseDatabase.getInstance().getReference(activeTool).child(childId);
                    if (qTime[0] != null) qTime[0].removeEventListener(valueEventListener[0]);
                    qTime[0] = dbrTime;
                    valueEventListener[0] = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String count = (dataSnapshot.child("time").getValue()).toString();
                                totalTimeMinute = Convert.Str2Int(count);
                                if (totalTimeMinute <= 0) {
                                    if (activeTool.equals("Coins")) {
                                        count = (dataSnapshot.child("coins").getValue()).toString();
                                        totalCoins[0] = Convert.Str2Int(count);
                                        if (totalCoins[0] > 1) {
                                            totalCoins[0] -= 1;
                                            totalTimeMinute = 20;
                                            timeIsUp = false;
                                            Map<String, String> hmData = new HashMap<>();
                                            hmData.put("coins", String.valueOf(totalCoins[0]));
                                            hmData.put("time", String.valueOf(totalTimeMinute));
                                            dbrTime.getRef().setValue(hmData);
                                            return;
                                        } else timeIsUp = true;
                                    }
                                    timeIsUp = true;
                                } else timeIsUp = false;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    };
                    qTime[0].addValueEventListener(valueEventListener[0]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setTimeIsUp(int miuteNext) {
        DatabaseReference dbrTime = FirebaseDatabase.getInstance().getReference(activeTool).child(childId);
        timeMinuteIsUp += miuteNext;
        totalTimeMinute -= timeMinuteIsUp;
        dbrTime.getRef().child("time").setValue(String.valueOf(totalTimeMinute));
        timeMinuteIsUp = 0;
    }

    public String getRecentApps(Context context) {
        String topPackageName = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
            //UsageEvents usageEvents = mUsageStatsManager.queryEvents(time - 1000 * 30, System.currentTimeMillis() + (10 * 1000));
            long time = System.currentTimeMillis();
            UsageEvents usageEvents = mUsageStatsManager.queryEvents(0, System.currentTimeMillis());
            UsageEvents.Event event = new UsageEvents.Event();
            while (usageEvents.hasNextEvent()) {
                usageEvents.getNextEvent(event);
            }
            if (event != null && !TextUtils.isEmpty(event.getPackageName()) && event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                if (AndroidUtils.isRecentActivity(event.getClassName())) return event.getClassName();
                return event.getPackageName();
            }
            else topPackageName = "";
        }
        else {
            taskInfo = activityManager.getRunningTasks(1);
            ComponentName componentInfo = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) componentInfo = taskInfo.get(0).topActivity;
            if (AndroidUtils.isRecentActivity(componentInfo.getClassName())) return componentInfo.getClassName();
            topPackageName = componentInfo.getPackageName();
        }
        return topPackageName;
    }

    private boolean packageInIgnoreList(String packageName) {
        if (packageName.isEmpty() || packageName.equals("com.sec.android.app.launcher") || packageName.equals("com.google.android.apps.nexuslauncher") ||
                packageName.equals("kz.tech.esparta")) return true;
        if (packageName.contains("com.whatsapp")) return true;
        else if (packageName.contains("org.telegram.messenger")) return true;
        else if (packageName.contains("kz.tech.esparta")) return true;
        else if (packageName.contains("com.facebook.orca")) return true;
        if (servicePackageList != null) {
            for (int m = 0; m < servicePackageList.size(); m++) {
                if (servicePackageList.get(m).equals(packageName)) return false;
            }
        }
        return false;
    }

    /*private boolean timeIsUp() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hour_of_day = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String currentTodayTime = "";
        String currentHour = "";
        String currentMinute = "";
        if (hour_of_day < 9) {
            currentHour = "0" + String.valueOf(hour_of_day);
        } else {
            currentHour = String.valueOf(hour_of_day);
        }
        if (hour_of_day < 9) {
            currentMinute = "0" + String.valueOf(minute);
        } else {
            currentMinute = String.valueOf(minute);
        }
        currentTodayTime = currentHour + ":" + currentMinute;

        String currentDay = "";

        if (Calendar.MONDAY == dayOfWeek) currentDay = "monday";
        else if (Calendar.TUESDAY == dayOfWeek) currentDay = "tuesday";
        else if (Calendar.WEDNESDAY == dayOfWeek) currentDay = "wednesday";
        else if (Calendar.THURSDAY == dayOfWeek) currentDay = "thursday";
        else if (Calendar.FRIDAY == dayOfWeek) currentDay = "friday";
        else if (Calendar.SATURDAY == dayOfWeek) currentDay = "saturday";
        else if (Calendar.SUNDAY == dayOfWeek) currentDay = "sunday";

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(PREFS_STORAGE_CHILDREN_TIME, 0);

        String weekDay = "";

        switch (currentDay) {
            case "monday":
                weekDay = preferences.getString("monday", "22:00-12:00");
                break;
            case "tuesday":
                weekDay = preferences.getString("tuesday", "22:00-12:00");
                break;
            case "wednesday":
                weekDay = preferences.getString("wednesday", "22:00-12:00");
                break;
            case "thursday":
                weekDay = preferences.getString("thursday", "22:00-12:00");
                break;
            case "friday":
                weekDay = preferences.getString("friday", "22:00-12:00");
                break;
            case "saturday":
                weekDay = preferences.getString("saturday", "22:00-12:00");
                break;
            case "sunday":
                weekDay = preferences.getString("sunday", "22:00-12:00");
                break;
        }
        if (weekDay.equals("close")) {
            return true;
        }
        String[] parseDate = weekDay.split("-");//  PARSE DATE DASH
        String startDate = parseDate[0];//  GET START DATE
        String endDate = parseDate[1];//  GET END DATE

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date currentTime = null;
        Date startTime = null;
        Date endTime = null;
        try {
            currentTime = simpleDateFormat.parse(currentTodayTime);
            startTime = simpleDateFormat.parse(startDate);
            endTime = simpleDateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (startTime.getTime() >= currentTime.getTime() && currentTime.getTime() >= endTime.getTime()) {
            // return true;

        }
        return false;
    }*/

    private void amKillProcess(String process) {
        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo runningProcess : runningProcesses) {
            if (runningProcess.processName.equals(process)) {
                android.os.Process.sendSignal(runningProcess.pid, android.os.Process.SIGNAL_KILL);
            }
        }
    }


    ////////////       STANDARD         ///////////////
    private ArrayList<String> onStandardList() {
        PackageManager packageManager = getApplicationContext().getPackageManager();
        List<PackageInfo> packageList = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
        ArrayList<String> servicePackageList = new ArrayList<String>();
        for (PackageInfo packageInfo : packageList) {
            boolean b = isExceptionPackage(packageInfo);
            if (!b) servicePackageList.add(packageInfo.packageName);
        }
        return servicePackageList;
    }

    ////////////       CUSTOMIZABLE         ///////////////
    private ArrayList<String> onCustomizableList() {
        ArrayList<String> servicePackageList = new ArrayList<String>();

        Map<String, ?> allEntries;
        SharedPreferences sharedPrefs = getApplicationContext().getSharedPreferences("PREFS_APP_LOCK_LIST", Context.MODE_PRIVATE);
        //allEntries = null;
        allEntries = sharedPrefs.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet())
            servicePackageList.add(entry.getKey());

        return servicePackageList;
    }

    private boolean isExceptionPackage(PackageInfo packageInfo) {

        return false;
    }

    ////////////////////////            PREFS              //////////////////////
    private String onLoadLoginID() {
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_STORAGE_LOGIN, 0);
        return settings.getString("id", "");
    }

    private String[] onLoadAccountID() {
        String[] strings = new String[5];
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_STORAGE_FAMILY_ACCOUNT, 0);
        strings[0] = settings.getString("avatar", "");
        strings[1] = settings.getString("name", "");
        strings[2] = settings.getString("pin", "");
        strings[3] = settings.getString("status", "");
        strings[4] = settings.getString("id", "");
        return strings;
    }

    private void saveCoins(long coins) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(PREFS_CHILD_COINS, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("coins", coins);
        editor.apply();
    }

    //private long loadCoins() {
        /*SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_CHILD_COINS, 0);
        return totalCoins = settings.getLong("coins", 0);*/
    // }

    public String onLoadAppLockList() {
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_APP_LOCK_LIST_SELECT, 0);
        return settings.getString("name", "standardList");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            if (countDownTimer != null) {
                countDownTimer = null;
            }
        }
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        Intent in = new Intent();
        in.setAction("YouWillNeverKillMe");
        sendBroadcast(in);

        /////////////////////                     SERVICE                  /////////////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//  new version < 26
            boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildService.class, getApplicationContext());
            if (!isServiceLife) {
                Intent intent = new Intent(getApplicationContext(), ChildService.class);
                intent.putExtra("eSparta", "inputExtra");
                ContextCompat.startForegroundService(getApplicationContext(), intent);
            }
        }
        if (Build.VERSION.SDK_INT < 26) {//  old version device == 24
            boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildService.class, getApplicationContext());
            if (!isServiceLife) {
                Intent intent = new Intent(getApplicationContext(), ChildService.class);
                intent.putExtra("eSparta", "inputExtra");
                PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, intent, 0);
                AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                alarm.cancel(pendingIntent);
                alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 15000, pendingIntent);
            }
        }

        android.util.Log.e("TAG ", "Service: DESTROY");
        wakeLock.release();
        android.util.Log.e("TAG ", "WakeLock: Release");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true);
        }

    }
}