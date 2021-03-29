package kz.tech.smartgrades.childs_module;

import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import kz.tech.esparta.R;
import kz.tech.smartgrades.childs_module.adapters.ChildMainCoinsAdapter;
import kz.tech.smartgrades.childs_module.adapters.ChildMainContractsAdapter;


import kz.tech.smartgrades.childs_module.adapters.PiggyAdapter;

import kz.tech.smartgrades.childs_module.service.ChildIntentService;
import kz.tech.smartgrades.childs_module.worker.WorkerChildService;
import kz.tech.smartgrades.MainActivity;
import kz.tech.smartgrades.root.models.ModelContract;
import kz.tech.smartgrades.childs_module.service.ChildService;
import kz.tech.smartgrades.root.service.CheckLaunchService;


public class ChildMainFragment extends Fragment {
    private static final String PREFS_CHILD_COINS = "PREFS_CHILD_COINS";
    private static final String PREFS_CHILD_LOGIN = "PREFS_CHILD_LOGIN";
    private Disposable disChildDataUpdate;

    private MainActivity main;
    private ChildMainView view;
    private ChildMainCoinsAdapter coinsAdapter;
    private ChildMainContractsAdapter contractsAdapter;
    private PiggyAdapter piggyAdapter;

    private int numbMinute = 0;

    private boolean isHuawei = false;

    View.OnLongClickListener longClick;
    MyDragListener dragListener;


    private PiggyLongClickListener piggyListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        saveChildLogin();//  Save ID Login and ID Account for child by Service
        onRequestChildData();
        onTimerUpdateChildData();

    //   MyUtils.StartPowerSaverIntent(getActivity());
    //    Intent nIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
    //    nIntent.addCategory("android.intent.category.DEFAULT");
    //    nIntent.putExtra("message", "test");
     //   nIntent.setClass(main, AlarmReceiver.class);

    //    PendingIntent broadcast = PendingIntent.getBroadcast(main, 100, nIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.P && Build.MANUFACTURER.equals("HUAWEI")) {//HUAWEI
          //  MyUtils.StartPowerSaverIntent(getActivity());
            isHuawei = true;
        }

       ////////////////////               WORKER                 ///////////////////////////
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PeriodicWorkRequest.Builder myWorkBuilder =
                    new PeriodicWorkRequest.Builder(WorkerChildService.class, 15, TimeUnit.MINUTES);

         //   myWorkBuilder.setInitialDelay(10, TimeUnit.SECONDS);

            PeriodicWorkRequest myWork = myWorkBuilder.build();
            WorkManager.getInstance(main).enqueueUniquePeriodicWork("jobTag", ExistingPeriodicWorkPolicy.KEEP, myWork);
       }
       /////////////////////                     SERVICE                  /////////////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//  new version < 26
            boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildService.class, main);
            if (!isServiceLife) {
                Intent intent = new Intent(main, ChildService.class);
                intent.putExtra("eSparta", "inputExtra");
                ContextCompat.startForegroundService(main, intent);
            }
        }
        if (Build.VERSION.SDK_INT < 26) {//  old version device == 24
            boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildService.class, main);
            if (!isServiceLife) {
                Intent intent = new Intent(main, ChildService.class);
                intent.putExtra("eSparta", "inputExtra");
                PendingIntent pendingIntent = PendingIntent.getService(main, 0, intent, 0);
                AlarmManager alarm = (AlarmManager) main.getSystemService(Context.ALARM_SERVICE);
                alarm.cancel(pendingIntent);
                alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 15000, pendingIntent);
            }
        }

        /////////////////        INTENT SERVICE          ////////////////////////
        //                 AUTO CHARGE                 //
 /*       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//  NEW VERSION
            boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildIntentService.class, main);
            if (!isServiceLife) {
                Intent intent = new Intent(main, ChildIntentService.class);
                intent.putExtra("inputService", "autoCharge");
                ContextCompat.startForegroundService(main, intent);
            }
        }
        if (Build.VERSION.SDK_INT < 26) {//  OLD VERSION
            boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildIntentService.class, main);
            if (!isServiceLife) {
                Intent intent = new Intent(main, ChildIntentService.class);
                intent.putExtra("inputService", "autoCharge");
                main.startService(intent);
            }
        }*/
        //                 TIME CHILDREN               //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//  NEW VERSION
            boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildIntentService.class, main);
            if (!isServiceLife) {
                Intent intent = new Intent(main, ChildIntentService.class);
                intent.putExtra("inputService", "timeChildren");
                ContextCompat.startForegroundService(main, intent);
            }
        }
        if (Build.VERSION.SDK_INT < 26) {//  OLD VERSION
            boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildIntentService.class, main);
            if (!isServiceLife) {
                Intent intent = new Intent(main, ChildIntentService.class);
                intent.putExtra("inputService", "timeChildren");
                main.startService(intent);
            }
        }
        return view;
    }

    private void initViews() {
        main = (MainActivity) getActivity();
        view = new ChildMainView(getActivity(), main.language.getLanguage());

        coinsAdapter = new ChildMainCoinsAdapter(getActivity());
        view.getCoins().setAdapter(coinsAdapter);
        coinsAdapter.setOnItemClickListener(new ChildMainCoinsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v) {
                v.setOnLongClickListener(longClick);
            }
        });

        contractsAdapter = new ChildMainContractsAdapter(getActivity());
        view.getRvContracts().setAdapter(contractsAdapter);

        piggyAdapter = new PiggyAdapter(getActivity());
        view.getPiggy().setAdapter(piggyAdapter);
        piggyAdapter.setOnItemClickListener(new PiggyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ImageView imageView) {
             //   imageView.setOnTouchListener(new CoinsChoiceTouchListener());
            }
        });

        view.setOnItemClickListener(new ChildMainView.OnItemClickListener() {
            @Override
            public void onBackButtonClick() {

            }

            @Override
            public void onMenuClick(View view) {
                PopupMenu menu = new PopupMenu(main, view);
                menu.getMenu().add(main.language.getLanguage().getString(R.string.exit)).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        main.presenter.onSelectFragment("QuickAccessSignOutFragment");
                        return false;
                    }
                });

                menu.show();
            }
        });

        if (main.prefs.getFamilyData("avatar") != null) {
            String image = main.prefs.getFamilyData("avatar");
            view.setAvatar(image);
        }

     //   view.getCoins().setOnTouchListener(new PiggyChoiceTouchListener());

        longClick = new MyOnLongClickListener();

        view.getCoins().setOnLongClickListener(longClick);
        view.getCardViewCoins().setOnLongClickListener(longClick);

        dragListener = new MyDragListener();
        view.getCardViewPiggy().setOnDragListener(dragListener);
        ///////         TOUCH AND   DRUG

        //  TOUCH
     //   view.getCoins().setOnTouchListener(new ChoiceTouchListener());

   //     view.getBankIcon().setOnTouchListener(new ChoiceTouchListener());
        //  DRUG

    //   view.getPiggyIcon().setOnDragListener(new PiggyChoiceDragListener());

        //////////////            PIGGY              //////////////////
        piggyListener = new PiggyLongClickListener();
        view.getCardViewPiggy().setOnLongClickListener(piggyListener); //  TO TAKE

        view.getCardViewCoins().setOnDragListener(new PiggyDragListener());  //  DROP

    }
    ///////////             PIGGY           ////////////////
    class PiggyLongClickListener implements View.OnLongClickListener {
        public boolean onLongClick(View v) {
            view.onPiggyToCoinsOn();
            ClipData clipData = ClipData.newPlainText("Image", "Picture");
            View.DragShadowBuilder shadowBuilder = new DrawableDragShadowBuilder(v,  getResources().getDrawable(R.mipmap.coins_gold));
            v.startDrag(clipData, shadowBuilder, v, 0);
            return true;
        }
    }
    class PiggyDragListener implements View.OnDragListener {
        private boolean iGot;
        @SuppressWarnings("deprecation")
        public boolean onDrag(View v, DragEvent event) {
            int dragAction = event.getAction();
            switch (dragAction) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //  v.setBackgroundColor(Color.GREEN);
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //     v.setBackgroundColor(Color.BLUE);
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.i("TAG", "Location: "+event.getX()+":"+event.getY());
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    //  v.setBackgroundColor(Color.YELLOW);
                    return true;
                case DragEvent.ACTION_DROP:
                    //      ImageView image = (ImageView)event.getLocalState();
                    //      v.setBackgroundDrawable(image.getDrawable());
                    //      iGot=true;

                    //   ClipData clipData= event.getClipData();
                    //    ClipData.Item item = clipData.getItemAt(0);
                    //    String str= (String) item.getText();
                    //  Log.i(TAG,"Item Text :"+str);
                    view.onPiggyToCoinsOff();
                    piggyAdapter.deleteData();
                    coinsAdapter.updateData();
                    view.updateDataCoinsPlus(20, numbMinute, main.language.getLanguage());
                    insertCoins(20);


                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    if(!iGot)
                        v.setBackgroundColor(Color.WHITE);
                    if(event.getResult())
                        Log.i("","Dropped Successfully");
                    else
                        Log.i("","Drop not Successful");


                    view.onPiggyToCoinsOff();
                    return true;
            }
            return false;
        }


    }


    private void onTimerUpdateChildData() {
        disChildDataUpdate = Flowable.interval(30, TimeUnit.SECONDS)
                // スレッド(thread)
              //  .delay(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                // 購読(subscribe)
                .subscribe(aLong -> {
                    onRequestChildData();
                });
    }
    private void onRequestChildData() {
        main.presenter.model.getChildDataDBR().child(main.prefs.getLoginData("id"))
                .child(main.prefs.getFamilyData("id"))
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                   if (dataSnapshot.getChildrenCount() == 6) {
                        Iterator i = dataSnapshot.getChildren().iterator();
                        String sCoins = null, sCoinsBank = null, sCoinsPiggy = null, sCoinsSpentToday = null, sChild = null;
                        Object oContracts = null;
                        ArrayList<ModelChildContracts> arrayList2 = new ArrayList<>();
                        while (i.hasNext()) {
                            sCoins = (String) ((DataSnapshot) i.next()).getValue();
                            sCoinsBank = (String) ((DataSnapshot) i.next()).getValue();
                            sCoinsPiggy = (String) ((DataSnapshot) i.next()).getValue();
                            sCoinsSpentToday = (String) ((DataSnapshot) i.next()).getValue();
                            oContracts = (Object) ((DataSnapshot) i.next()).getValue();
                            sChild = (String) ((DataSnapshot) i.next()).getValue();
                        }
                        if (sCoins != null) {
                            numbMinute = Integer.parseInt(sCoins);
                            int coins = ((int) (10 * numbMinute) / 20) / 10;
                            if (numbMinute > 0) {
                                int[] data = new int[coins];

                                for (int j = 0; j < coins; j++) {
                                    data[j] = 1;
                                }
                                coinsAdapter.setData(data);
                                view.setDataCoins(sCoins, main.language.getLanguage());
                                view.getCoins().invalidate();
                            }
                        }
                       if (sCoinsBank != null) {
                           view.setBankCount(sCoinsBank);
                       }
                        if (sCoinsPiggy != null) {
                            int totalPiggyCoins = Integer.parseInt(sCoinsPiggy);
                            piggyAdapter.insertData(totalPiggyCoins);
                            view.setPiggyCount(sCoinsPiggy);
                        }
                        if (oContracts instanceof HashMap) {
                            HashMap hashMap1 = (HashMap) oContracts;
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
                        } else if (oContracts instanceof ArrayList) {
                            view.onClearContainerForContracts();
                            view.onContractEmpty();//  No Contracts
                            view.onSelectContractStatus(false);
                        }
                    }
                } else {
                    //  DataSnapshot: NOT EXIST
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void saveChildLogin() {
        SharedPreferences sharedPreferences = main.getSharedPreferences(PREFS_CHILD_LOGIN, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idLogin", main.prefs.getLoginData("id"));
        editor.putString("idChild", main.prefs.getFamilyData("id"));
        editor.apply();
    }

    private static boolean isCallable(Context context, Intent intent) {
        List<ResolveInfo>list=context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
    @Override
    public void onResume() {
        super.onResume();

        //saveUserSessionManager is just a Preference you can set your preference class instead of SessionManager

      ///  if (!saveUserSessionManager.getDataByKey("skipProtectedAppCheck", false)) {

      //      Utils.startPowerSaverIntent(mContext, saveUserSessionManager);
       // }
    }
    public static void LogDeviceBrandActivities(Context context) {
        String brand = Build.BRAND.toLowerCase();
        String manufacturer = Build.MANUFACTURER.toLowerCase();

        List<PackageInfo> apps = context.getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES);

        Collections.sort(apps, (a, b) -> a.packageName.compareTo(b.packageName));
        for (PackageInfo pi : apps) {
            if (pi.packageName.toLowerCase().contains(brand) || pi.packageName.toLowerCase().contains(manufacturer)) {
                boolean print = false;
                StringBuilder activityInfo = new StringBuilder();

                if (pi.activities != null && pi.activities.length > 0) {
                    List<ActivityInfo> activities = Arrays.asList(pi.activities);

                    Collections.sort(activities, (a, b) -> a.name.compareTo(b.name));
                    for (ActivityInfo ai : activities) {
                        if (ai.name.toLowerCase().contains(brand) || ai.name.toLowerCase().contains(manufacturer)) {
                            activityInfo.append("  Activity: ").append(ai.name)
                                    .append(ai.permission == null || ai.permission.length() == 0 ? "" : " - Permission: " + ai.permission)
                                    .append("\n");
                            print = true;
                        }
                    }
                }

                if (print) {
                    Log.e("brand.activities", "PackageName: " + pi.packageName);
                    Log.w("brand.activities", activityInfo.toString());
                }
            }
        }
    }
    private void insertPiggyCoins(int coinsPiggyNumb1) {
        String idLogin = main.prefs.getLoginData("id");
        String idAccount = main.prefs.getFamilyData("id");
        String coins = "coins";
        String coinsPiggy = "coinsPiggy";
        final int coinsPiggyNumb2 = coinsPiggyNumb1;
        DatabaseReference dbr = main.presenter.model.getChildDataDBR();

        Query query = dbr.child(idLogin).child(idAccount);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    /////////      COINS      /////////////
                    String totalCoins = (String) dataSnapshot.child(coins).getValue();
                    int numbCoins = Integer.parseInt(totalCoins);
                    numbCoins -= coinsPiggyNumb1;
                    dataSnapshot.getRef().child(coins).setValue(String.valueOf(numbCoins));

                    /////////         PIGGY COINS        //////////
                    int numbPiggyCoins = 0;
                    String totalPiggyCoins = (String) dataSnapshot.child(coinsPiggy).getValue();
                    if (totalPiggyCoins != null) {
                        numbPiggyCoins = Integer.parseInt(totalPiggyCoins);
                    }
                    numbPiggyCoins += coinsPiggyNumb2;
                    if (numbPiggyCoins > 0) {
                        dataSnapshot.getRef().child(coinsPiggy).setValue(String.valueOf(numbPiggyCoins));
                    } else if (numbPiggyCoins == 0) {
                        dataSnapshot.getRef().child(coinsPiggy).setValue(String.valueOf(coinsPiggyNumb1));
                    }
                  //  android.util.Log.e("TAG", "DeviceCheck: EXIST");
                } else {
                    dataSnapshot.getRef().child(coinsPiggy).setValue(String.valueOf(coinsPiggyNumb1));
                 //   android.util.Log.e("TAG", "DeviceCheck: EXIST");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
             //   android.util.Log.e("TAG", "DeviceCheck: ERROR");
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }
    private void insertCoins(int coinsNumb1) {
        String idLogin = main.prefs.getLoginData("id");
        String idAccount = main.prefs.getFamilyData("id");
        String coins = "coins";
        String coinsPiggy = "coinsPiggy";
        final int coinsNumb2 = coinsNumb1;
        DatabaseReference dbr = main.presenter.model.getChildDataDBR();

        Query query = dbr.child(idLogin).child(idAccount);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    /////////      PIGGY COINS      /////////////
                    String totalCoins = (String) dataSnapshot.child(coinsPiggy).getValue();
                    int numbCoins = Integer.parseInt(totalCoins);
                    numbCoins -= coinsNumb1;
                    dataSnapshot.getRef().child(coinsPiggy).setValue(String.valueOf(numbCoins));

                    /////////          COINS        //////////
                    String totalPiggyCoins = (String) dataSnapshot.child(coins).getValue();
                    int numbPiggyCoins = Integer.parseInt(totalPiggyCoins);
                    numbPiggyCoins += coinsNumb2;
                    if (numbPiggyCoins > 0) {
                        dataSnapshot.getRef().child(coins).setValue(String.valueOf(numbPiggyCoins));
                    } else if (numbPiggyCoins == 0) {
                        dataSnapshot.getRef().child(coins).setValue(String.valueOf(coinsNumb1));
                    }
                    //  android.util.Log.e("TAG", "DeviceCheck: EXIST");
                } else {
                    dataSnapshot.getRef().child(coins).setValue(String.valueOf(coinsNumb1));
                    //   android.util.Log.e("TAG", "DeviceCheck: EXIST");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //   android.util.Log.e("TAG", "DeviceCheck: ERROR");
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }
    private final class PiggyChoiceTouchListener implements View.OnTouchListener{
        public boolean onTouch(View view, MotionEvent motionEvent){
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                //setup drag
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);

                return true;
            }
            else {
                return false;
            }
        }
    }
    private class PiggyChoiceDragListener implements View.OnDragListener{
        @Override
        public boolean onDrag(View v, DragEvent event) {
            //handle drag events
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:
                    //handle the dragged view being dropped over a drop view - asta de deasupra
                    //handle the dragged view being dropped over a target view -asta de jos
                    View vImage = (View) event.getLocalState();
                    //stop displaying the view where it was before it was dragged
                //    vImage.setVisibility(View.INVISIBLE);
                    //view dragged item is being dropped on
                    ImageView dropTarget = (ImageView) v;
                    ViewGroup owner = (ViewGroup) v.getParent();
                    owner.removeView(vImage);


                    //view being dragged and dropped
                    ImageView dropped = (ImageView) vImage;
                    //update the image in the target view to reflect the data being dropped
                    //    dropTarget.setImageDrawable(dropped.getDrawable());
                    piggyAdapter.updateData(20);
                    view.updateDataCoinsMinus(20, numbMinute, main.language.getLanguage());
                    insertPiggyCoins(20);
                    coinsAdapter.deleteData();

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }

            return true;
        }
    }
    private final class CoinsChoiceTouchListener implements View.OnTouchListener{
        public boolean onTouch(View view, MotionEvent motionEvent){
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                //setup drag
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            }
            else {
                return false;
            }
        }
    }
    private class CoinsChoiceDragListener implements View.OnDragListener{
        @Override
        public boolean onDrag(View v, DragEvent event) {
            //handle drag events
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:
                    //handle the dragged view being dropped over a drop view - asta de deasupra
                    //handle the dragged view being dropped over a target view -asta de jos
                    View vImage = (View) event.getLocalState();
                    //stop displaying the view where it was before it was dragged
                //    vImage.setVisibility(View.INVISIBLE);
                    //view dragged item is being dropped on
                    CardView dropTarget = (CardView) v;
                    //view being dragged and dropped

                    ViewGroup owner = (ViewGroup) v.getParent();
                    owner.removeView(vImage);

                    ImageView dropped = (ImageView) vImage;

                    dropped.setVisibility(View.VISIBLE);
                    //update the image in the target view to reflect the data being dropped
                    //    dropTarget.setImageDrawable(dropped.getDrawable());
               //     piggyAdapter.updateData(20);
                 //   view.updateDataCoins(20, numbMinute, main.getResLanguage());
                  //  insetPiggyCoins(20);
                    piggyAdapter.deleteData();
                    coinsAdapter.updateData();
                    view.updateDataCoinsPlus(20, numbMinute, main.language.getLanguage());
                    insertCoins(20);
                //    view.getCoins().invalidate();

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }

            return true;
        }
    }



    class MyOnLongClickListener implements View.OnLongClickListener {


        public boolean onLongClick(View v) {
          //  listener1.setVisibility(View.VISIBLE);
           // listener2.setVisibility(View.VISIBLE);
            view.onCoinsToBankOrPiggyOn();


            ClipData clipData = ClipData.newPlainText("Image", "Picture");
// Default shadow
//DragShadowBuilder shadowBuilder = new DragShadowBuilder(v);
// Custom Shadow
            View.DragShadowBuilder shadowBuilder = new DrawableDragShadowBuilder(v,  getResources().getDrawable(R.mipmap.coins_gold));

        //    getResources().getDrawable(R.mipmap.coins_gold);
          //  shadowBuilder.getView().setBackgroundColor(Color.RED);



        //    View.DragShadowBuilder shadowBuilder = new MyDragShadow(v);

          //  ImageView image = (ImageView)v.getParent();
       //     image.setImageResource(R.mipmap.coins_gold);
            //      v.setBackgroundDrawable(image.getDrawable());


            v.startDrag(clipData, shadowBuilder, v, 0);

            Log.i("","Drag the shadow");

            return true;
        }


    }
    class DrawableDragShadowBuilder extends View.DragShadowBuilder {
        private Drawable mDrawable;

        public DrawableDragShadowBuilder(View view, Drawable drawable) {
            super(view);
            // Set the drawable and apply a green filter to it
            mDrawable = drawable;
          //  mDrawable.setColorFilter(new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY));
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point touchPoint) {
            // Fill in the size
            shadowSize.x = mDrawable.getIntrinsicWidth();
            shadowSize.y = mDrawable.getIntrinsicHeight();
            // Fill in the location of the shadow relative to the touch.
            // Here we center the shadow under the finger.
            touchPoint.x = mDrawable.getIntrinsicWidth() / 2;
            touchPoint.y = mDrawable.getIntrinsicHeight() / 2;

            mDrawable.setBounds(new Rect(0, 0, shadowSize.x, shadowSize.y));
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            //Draw the shadow view onto the provided canvas
            mDrawable.draw(canvas);
        }
    }
    class MyDragListener implements View.OnDragListener {
        private boolean iGot;
        @SuppressWarnings("deprecation")
        public boolean onDrag(View v, DragEvent event) {
            int dragAction = event.getAction();
            switch (dragAction) {
                case DragEvent.ACTION_DRAG_STARTED:
                  //  v.setBackgroundColor(Color.GREEN);
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
               //     v.setBackgroundColor(Color.BLUE);
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.i("TAG", "Location: "+event.getX()+":"+event.getY());
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                  //  v.setBackgroundColor(Color.YELLOW);
                    return true;
                case DragEvent.ACTION_DROP:
              //      ImageView image = (ImageView)event.getLocalState();
              //      v.setBackgroundDrawable(image.getDrawable());
              //      iGot=true;

                 //   ClipData clipData= event.getClipData();
                //    ClipData.Item item = clipData.getItemAt(0);
                //    String str= (String) item.getText();
                  //  Log.i(TAG,"Item Text :"+str);
                    view.onCoinsToBankOrPiggyOff();
                    piggyAdapter.updateData(20);
                    view.updateDataCoinsMinus(20, numbMinute, main.language.getLanguage());
                    insertPiggyCoins(20);
                    coinsAdapter.deleteData();


                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    if(!iGot)
                        v.setBackgroundColor(Color.WHITE);
                    if(event.getResult())
                        Log.i("","Dropped Successfully");
                    else
                        Log.i("","Drop not Successful");


                    view.onCoinsToBankOrPiggyOff();
                    return true;
            }
            return false;
        }


    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disChildDataUpdate != null && !disChildDataUpdate.isDisposed()) {
            disChildDataUpdate.dispose();
        }
        if (main != null) {
            main = null;
        }
        if (view != null) {
            view = null;
        }
    }
}



