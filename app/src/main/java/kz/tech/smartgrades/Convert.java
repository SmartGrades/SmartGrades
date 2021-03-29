package kz.tech.smartgrades;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Convert {

    private final static SimpleDateFormat SIMPLE_DATE_FORMAT_DEFAULT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT_SQL = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT_SHORT_TIME = new SimpleDateFormat("HH:mm", Locale.US);
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT_SHORT_DATE = new SimpleDateFormat("dd MMM", Locale.US);
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT_DATE_TIME = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT_DATE = new SimpleDateFormat("dd.MM.yyyy");

    private final static DateFormat DATE_FORMAT_SHORT_TIME = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private final static DateFormat DATE_FORMAT_SHORT_DATE = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());

    public static String Double2Str(double d, String pattern){
        if (pattern.isEmpty()) pattern = "#.#";
        try{
            return new DecimalFormat(pattern).format(d).replace(',', '.');
        }
        catch (Exception e){
            return "0.0";
        }
    }
    public static double Str2Double(String s){
        try{
            return Double.parseDouble(s.replace(",", "."));
        }
        catch (Exception e){
            return 0.0;
        }
    }

    public static int S2I(String s){
        try{
            return Integer.parseInt(s);
        }
        catch (Exception e){
            return 0;
        }
    }
    public static long S2L(String s){
        try{
            Double a = Double.parseDouble(s);
            return Math.round(a);
        }
        catch (Exception e){
            return 0;
        }
    }



    @SuppressLint("DefaultLocale")
    public static String M2S(int totalMinute){
        try{
            return String.format("%02d:%02dч", totalMinute / 60 % 60, totalMinute % 60);
        }
        catch (Exception e){
            return "";
        }
    }

    public static Date StringDate2Date(String stringDateTime){
        try {
            return SIMPLE_DATE_FORMAT_DATE.parse(stringDateTime);
        } catch (Exception e) {
            return null;
        }
    }
    public static Date String2DateTime(String stringDateTime){
        try {
            return SIMPLE_DATE_FORMAT_DATE_TIME.parse(stringDateTime);
        } catch (Exception e) {
            return null;
        }
    }
    public static String String2ShortDate(String stringDateTime){
        try{
            return SIMPLE_DATE_FORMAT_SHORT_DATE.format(SIMPLE_DATE_FORMAT_SQL.parse(stringDateTime));
        }
        catch (Exception e){
            return "";
        }
    }
    public static String String2ShortTime(String stringDateTime){
        try{
            return SIMPLE_DATE_FORMAT_SHORT_TIME.format(SIMPLE_DATE_FORMAT_SQL.parse(stringDateTime));
        }
        catch (Exception e){
            return "";
        }
    }

    public static String Date2ShortTime(Date date){
        try{
            return DATE_FORMAT_SHORT_TIME.format(date);
        }
        catch (Exception e){
            return "";
        }
    }
    public static String Date2ShortDate(Date date){
        try{
            return DATE_FORMAT_SHORT_DATE.format(date);
        }
        catch (Exception e){
            return "";
        }
    }
    public static String MD5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static float PxToDp(float px){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }
    public static float DpToPx(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
    public static int DpToPx(Context context, int dp){
        return Math.round(dp*(context.getResources().getDisplayMetrics().xdpi/DisplayMetrics.DENSITY_DEFAULT));
    }
    private int PxToDp(int px){
        return Math.round(px/(Resources.getSystem().getDisplayMetrics().xdpi/DisplayMetrics.DENSITY_DEFAULT));
    }

}
