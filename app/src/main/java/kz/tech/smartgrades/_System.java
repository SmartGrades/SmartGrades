package kz.tech.smartgrades;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import kz.tech.esparta.R;

public class _System{

    public static void setSettingTimePicker(TimePicker timePicker, int MaxHour, int MaxMinute){
        timePicker.setIs24HourView(true);
        NumberPicker minute = timePicker.findViewById(Resources.getSystem().getIdentifier("minute", "id", "android"));
        NumberPicker hour = timePicker.findViewById(Resources.getSystem().getIdentifier("hour", "id", "android"));
        hour.setMinValue(0);
        hour.setMaxValue(MaxHour - 1);
        List<String> ValuesList = new ArrayList<>();
        for(int i = 0; i < 8; i += 1){
            ValuesList.add(String.format("%02d", i));
        }
        hour.setDisplayedValues(ValuesList.toArray(new String[ValuesList.size()]));
        timePicker.setCurrentHour(new Integer(0));
        minute.setMinValue(0);
        minute.setMaxValue((MaxMinute / 10) - 1);
        ValuesList = new ArrayList<>();
        for(int i = 0; i < MaxMinute; i += 10){
            ValuesList.add(String.format("%02d", i));
        }
        minute.setDisplayedValues(ValuesList.toArray(new String[ValuesList.size()]));
        timePicker.setCurrentMinute(new Integer(0));
    }

    public static int getWindowHeight(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
    public static int getWindowWidth(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static Point getLocationOnScreen(View view){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new Point(location[0], location[1]);
    }

    public static boolean AvatarHasImage(Context context, @NonNull ImageView view){
        Drawable first = view.getDrawable();
        if(first == null) return false;
        Drawable second = context.getResources().getDrawable(R.drawable.img_default_avatar);
        Drawable.ConstantState stateFirst = first.getConstantState();
        Drawable.ConstantState stateSecond = second.getConstantState();
        if(stateFirst.equals(stateSecond)){
            return false;
        }
        return true;
    }

    public static void HideKeyboard(AppCompatActivity activity){
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean getPermissionsToUseCamera(AppCompatActivity activity){
        boolean result = false;
        String[] permissions = {android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean check = true;
        for(String p : permissions){
            if(ContextCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED)
                check = false;
        }
        result = check;
        if(!result) ActivityCompat.requestPermissions(activity, permissions, 1);
        return result;
    }

    public static void Vibrator(Context context){
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        else v.vibrate(100);//deprecated in API 26
    }

    public static class CustomLayoutManager extends LinearLayoutManager{
        private boolean isScrollEnabled = true;
        public CustomLayoutManager(Context context, @RecyclerView.Orientation int orientation) {
            super(context);
            setOrientation(orientation);
        }
        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }
        @Override
        public boolean canScrollVertically() {
            //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
            return isScrollEnabled;// && super.canScrollHorizontally();
        }
    }

    public static int getMaxLastGrades(Context context){
        return (int) (getWindowWidth(context) / 98);
    }

    public boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
