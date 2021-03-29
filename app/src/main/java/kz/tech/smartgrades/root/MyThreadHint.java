package kz.tech.smartgrades.root;

public class MyThreadHint {

}

/*  disposable = Flowable.interval(1L, TimeUnit.SECONDS)//  Время для интервала, выбор интервала, секунды, минуты, часы
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                   // android.util.Log.e("TAG", "COUNT ");
                    if (totalCoins == 0) {
                      //  getForegroundLaunchApp();
                       // Toast.makeText(getApplicationContext(), getRecentApps(getApplicationContext()),Toast.LENGTH_SHORT).show();
                       // android.util.Log.e("APP ", getRecentApps(getApplicationContext()));
                       lockApps();
                    }
                });*/


 /*  Observable.interval(20, 4000, TimeUnit.MILLISECONDS)
                .map(aLong -> aLong + offset)

                .observeOn(AndroidSchedulers.mainThread())
                // 購読(subscribe)
                .subscribe(aLong -> {
                    android.util.Log.e("Shag", "FUCK YOU " + loh);
                    loh++;
                });*/
 /*
 //
      //  PeriodicWorkRequest ddddd = new PeriodicWorkRequest.Builder(WorkerChildService.class, 60, TimeUnit.SECONDS)
            //    60, TimeUnit.SECONDS)
     //           .build();
     //   WorkManager.getInstance().enqueue(ddddd);


//
     //   PeriodicWorkRequest PeriodicWorkRequest = new PeriodicWorkRequest.Builder(WorkerChildService.class, 1, TimeUnit.MINUTES)
      //          .build();
     //   WorkManager.getInstance().enqueue(PeriodicWorkRequest);

      //  OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(WorkerChildService.class)

            //    .build();
       // WorkManager.getInstance().enqueue(myWorkRequest);

  /*      FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getActivity()));

        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class) // the JobService that will be called
                .setTag("my-unique-tag")        // uniquely identifies the job
                .setRecurring(false)
                .setLifetime(Lifetime.FOREVER)
                .build();

        dispatcher.mustSchedule(myJob);*/
/*
 private void test1() {
        packageManager = main.getPackageManager();
        List<PackageInfo> packageList1 = new ArrayList<PackageInfo>();

        List<PackageInfo> packageList = packageManager
                .getInstalledPackages(PackageManager.GET_PERMISSIONS);

        List<PackageInfo> packageList2 = packageManager
                .getInstalledPackages(PackageManager.GET_PERMISSIONS);


        for(PackageInfo pi : packageList) {

            boolean b = isSystemPackage(pi);
            boolean c = isSystemPackage1(pi);
            boolean d = isSystemPackage2(pi);


            if ((!b || !c ) && d ){
                packageList1.add(pi);
            }

        }
        android.util.Log.e("Shag", "FUCK YOU ");

        for(PackageInfo pi : packageList) {

            boolean b = isExceptionPackage(pi);
           // boolean b = isSystemPackage3(pi);
           // boolean c = isSystemPackage4(pi);
          //  boolean x = isSystemPackage5(pi);

            if (b) {
                packageList1.add(pi);
            }

        }

        android.util.Log.e("Shag", "FUCK YOU ");

        final PackageItemInfo.DisplayNameComparator comparator = new PackageItemInfo.DisplayNameComparator(packageManager);

        Collections.sort(packageList1, new Comparator<PackageInfo>() {
            @Override
            public int compare(PackageInfo lhs, PackageInfo rhs) {
                return comparator.compare(lhs.applicationInfo, rhs.applicationInfo);
            }
        });

        SharedPreferences.Editor editor = main.getSharedPreferences(main.getApplicationContext().getPackageName(), Context.MODE_PRIVATE).edit();
        SharedPreferences.Editor editorapp = main.getSharedPreferences("appdb", Context.MODE_PRIVATE).edit();


        for (int i = 0; i < packageList1.size(); i++) {
            final PackageInfo packageInfo = (PackageInfo) packageList1.get(i);
            android.util.Log.e("Shag", packageInfo.packageName);
            editor.putBoolean(packageInfo.packageName, true);
            editorapp.putString(packageInfo.packageName, packageInfo.packageName);
            editor.apply();
            editorapp.apply();
        }


    }


    public void amKillProcess(String process) {
        ActivityManager am = (ActivityManager) main.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();

        for(ActivityManager.RunningAppProcessInfo runningProcess : runningProcesses) {
            if(runningProcess.processName.equals(process)) {
                android.os.Process.sendSignal(runningProcess.pid, android.os.Process.SIGNAL_KILL);
            }
        }
    }
    private String getTopPackageTry() {


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                return mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
            }
        } else {
            ActivityManager manager = (ActivityManager) main.getApplicationContext().getSystemService(ACTIVITY_SERVICE);

            List<ActivityManager.RunningAppProcessInfo> tasks = manager.getRunningAppProcesses();

            return tasks.get(0).processName;
        }

           return "";
    }
    private String getTopPackageName() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                return mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
            }
        } else {
            // Hack, see
            // http://stackoverflow.com/questions/24625936/getrunningtasks-doesnt-work-in-android-l/27140347#27140347
            pis = mActivityManager.getRunningAppProcesses();
            if(pis!=null){
            }
            else {


                for (ActivityManager.RunningAppProcessInfo pi : pis) {
                    if (pi.pkgList.length == 1) return pi.pkgList[0];
                }
            }
        }
        return "";
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
                : false;
    }

    private boolean isSystemPackage1(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) ? false
                : true;
    }

    private boolean isSystemPackage2(PackageInfo pkgInfo) {
        return ((pkgInfo.packageName.contains("com.android.systemui"))) ? false
                : true;
    }


    private boolean isExceptionPackage(PackageInfo packageInfo) {
        if (packageInfo.packageName.contains("com.whatsapp")) {
            return true;
        } else if (packageInfo.packageName.contains("org.telegram.messenger")) {
            return true;
        } else if (packageInfo.packageName.contains("kz.tech.esparta")) {
            return true;
        }
        return false;
    }
 */