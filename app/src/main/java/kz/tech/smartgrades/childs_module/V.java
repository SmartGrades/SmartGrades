package kz.tech.smartgrades.childs_module;

public class V {
}
/*
   String s = main.getCurrentDate();
        AlarmManager mAlarmManger = (AlarmManager)main.getSystemService(Context.ALARM_SERVICE);

        //Create pending intent & register it to your alarm notifier class
        Intent intent = new Intent(main, IntentService.class);
        intent.putExtra("uur", "1e"); // if you want
        PendingIntent pendingIntent = PendingIntent.getBroadcast(main, 0, intent, 0);

        //set timer you want alarm to work (here I have set it to 9.00)
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 11);
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 44);
        calendar.set(Calendar.SECOND, 0);
        String z = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));

        //set that timer as a RTC Wakeup to alarm manager object
        mAlarmManger.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
 */
/*
   final PackageManager pm = getActivity().getPackageManager();
//get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            Log.fdfd("TAG", "Installed package :" + packageInfo.packageName);
            Log.fdfd("TAG", "Source dir : " + packageInfo.sourceDir);
            Log.fdfd("TAG", "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
        }




        android.util.Log.e("Shag", "FUCK YOU ");
 */

/*
public String getUniqueID(){
    String myAndroidDeviceId = "";
    TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    if (mTelephony.getDeviceId() != null){
        myAndroidDeviceId = mTelephony.getDeviceId();
    }else{
         myAndroidDeviceId = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
    }
    return myAndroidDeviceId;
}
 */



// onLoadData();

      /*  BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.present, options);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.mipmap.present)
                        .setContentTitle("Title")
                        .setContentText("Notification text")
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));

        Notification notification = builder.build();

        NotificationManager notificationManager =
                (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);*/

    /*    disposable1 = Flowable.interval(2L, TimeUnit.SECONDS)//  Время для интервала, выбор интервала, секунды, минуты, часы
                // スレッド(thread)
                //.delay(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                // 購読(subscribe)
                .subscribe(aLong -> {
                    android.util.Log.e("Shag", count +" Msg"); count++;
                });




        PowerManager pm = (PowerManager)getActivity().getSystemService(Context.POWER_SERVICE);




        disposable2 = Flowable.interval(1L, TimeUnit.SECONDS)//  Время для интервала, выбор интервала, секунды, минуты, часы
                // スレッド(thread)
                //.delay(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                // 購読(subscribe)
                .subscribe(aLong -> {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH) {
                        isScreenOn = pm.isScreenOn();
                    } else {
                        isScreenOn = pm.isInteractive();
                    }
                    android.util.Log.e("Shag", "is " + isScreenOn);
                    if(!isScreenOn) {
                        startTH();
                    }
                });


*/



/*
  Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int hour_of_day = calendar.get(Calendar.HOUR_OF_DAY);
        int hour = calendar.get(Calendar.HOUR);
        //int month = calendar.get(Calendar.MONTH);
 */

/*
    private void onBlockApplications() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(main)
                        .setSmallIcon(R.drawable.ic_android)
                        .setContentTitle("Title")
                        .setContentText("Notification text");

        Notification notification = builder.build();

        NotificationManager notificationManager =
                (NotificationManager) main.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(numb, notification);

        PackageManager packageManager = mainActivity.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> appList = packageManager.queryIntentActivities(mainIntent, 0);
        Collections.sort(appList, new ResolveInfo.DisplayNameComparator(packageManager));
        List<PackageInfo> packs = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            ApplicationInfo a = p.applicationInfo;
            // skip system apps if they shall not be included
            if ((a.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                continue;
            }
           // appList.add(p.packageName);
        }
        android.util.Log.e("Tag", " Msg");
        numb++;
    }
 */

/*
private void startTH() {
        disposable3 = Flowable.interval(1L, TimeUnit.SECONDS)//  Время для интервала, выбор интервала, секунды, минуты, часы
                // スレッド(thread)
                //.delay(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                // 購読(subscribe)
                .subscribe(aLong -> {

                    android.util.Log.e("Shag", "FUCK YOU ");

                });
    }
    private void endTH() {
        if (disposable3 != null && !disposable3.isDisposed()) {
            disposable3.dispose();
        }
    }
 */




/*
 if (childSize ==3) {
                        ArrayList<ModelChildContracts> arrayList = new ArrayList<>();
                        ModelChildDataParse parse = dataSnapshot.getValue(ModelChildDataParse.class);
                        String totalCoins = parse.getCoins();
                        if (totalCoins != null) {
                            numbMinute = Integer.parseInt(totalCoins);
                            int coins = ((int) (10 * numbMinute) / 20) / 10;

                            if (numbMinute > 0) {
                                int[] data = new int[coins];

                                for (int i = 0; i < coins; i++) {
                                    data[i] = 1;
                                }


                                coinsAdapter.setData(data);
                                view.setDataCoins(totalCoins, main.getResLanguage());
                            }

                            if (parse.getContracts() instanceof HashMap) {
                                HashMap hashMap1 = (HashMap) parse.getContracts();
                                ArrayList<ModelContract> contractsList = new ArrayList<>(hashMap1.values());

                                for (int i = 0; i < contractsList.size(); i++) {

                                    HashMap hashMap2 = new HashMap((Map) contractsList.get(i));
                                    String title = String.valueOf(hashMap2.get("title"));
                                    if (title != null) {
                                        if (!title.equals("0")) {
                                            String avatar = String.valueOf(hashMap2.get("avatar"));
                                            String description = String.valueOf(hashMap2.get("description"));
                                            String countSteps = String.valueOf(hashMap2.get("countSteps"));

                                            arrayList.add(new ModelChildContracts(avatar, countSteps, description));
                                        }
                                    }
                                }
                                view.onClearContainerForContracts();
                                view.onSelectContractStatus(true);
                            } else if (parse.getContracts() instanceof ArrayList) {
                                view.onClearContainerForContracts();
                                view.onContractEmpty();//  No Contracts
                                view.onSelectContractStatus(false);
                            }
                        }
                        contractsAdapter.setData(arrayList);
                    } else if (childSize == 4) {
                        Iterator i = dataSnapshot.getChildren().iterator();
                        String s1 = null, s2 = null, s3 = null;
                        Object o1 = null;
                        ArrayList<ModelChildContracts> arrayList2 = new ArrayList<>();
                        while (i.hasNext()) {
                            s1 = (String) ((DataSnapshot) i.next()).getValue();
                            s2 = (String) ((DataSnapshot) i.next()).getValue();
                            o1 = (Object) ((DataSnapshot) i.next()).getValue();
                            s3 = (String) ((DataSnapshot) i.next()).getValue();
                        }
                        if (s1 != null) {
                            numbMinute = Integer.parseInt(s1);
                            int coins = ((int) (10 * numbMinute) / 20) / 10;

                            if (numbMinute > 0) {
                                int[] data = new int[coins];

                                for (int j = 0; j < coins; j++) {
                                    data[j] = 1;
                                }
                                coinsAdapter.setData(data);
                                view.setDataCoins(s1, main.getResLanguage());
                                view.getCoins().invalidate();
                            }
                        }
                        if (s2 != null) {
                            int totalPiggyCoins = Integer.parseInt(s2);
                            piggyAdapter.insertData(totalPiggyCoins);
                        }
                        if (o1 instanceof HashMap) {
                            HashMap hashMap1 = (HashMap) o1;
                            ArrayList<ModelContract> contractsList = new ArrayList<>(hashMap1.values());

                            for (int w = 0; w < contractsList.size(); w++) {

                                HashMap hashMap2 = new HashMap((Map) contractsList.get(w));
                                String title = String.valueOf(hashMap2.get("title"));
                                if (title != null) {
                                    if (!title.equals("0")) {
                                        String avatar = String.valueOf(hashMap2.get("avatar"));
                                        String description = String.valueOf(hashMap2.get("description"));
                                        String countSteps = String.valueOf(hashMap2.get("countSteps"));

                                        arrayList2.add(new ModelChildContracts(avatar, countSteps, description));
                                    }
                                }
                            }
                            view.onClearContainerForContracts();
                            view.onSelectContractStatus(true);
                        } else if (o1 instanceof ArrayList) {
                            view.onClearContainerForContracts();
                            view.onContractEmpty();//  No Contracts
                            view.onSelectContractStatus(false);
                        }
                    }
 */