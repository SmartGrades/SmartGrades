package kz.tech.smartgrades.root.device;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import kz.tech.smartgrades.MainActivity;
import kz.tech.smartgrades.root.models.ModelDevice;

public class DeviceConfiguration implements IDevice {

    public DeviceConfiguration() { }

    @SuppressLint("MissingPermission")
    @Override
    public String getDeviceGenerationNumber(MainActivity main) {
        String result = "";
        if (main.hardwareAccess.isPhoneAccess()) {
            TelephonyManager telephonyManager = (TelephonyManager) main.getSystemService(Context.TELEPHONY_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= 26) {
                result = telephonyManager.getImei();
            } else {
                result = telephonyManager.getDeviceId();
            }
        }
        if (result.equals("")) {
            result = Settings.Secure.getString(main.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        if (result.equals("")) {//  IF PHONE PERMISSION FALSE OR THIS DEVICE A THE TABLET
            result = android.os.Build.SERIAL;
        }
        return result;
    }
    public String getDeviceModel() {
        return android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;
    }
    public  boolean isTabletDevice(Context activityContext) {
        // Verifies if the Generalized Size of the device is XLARGE to be
        // considered a Tablet
        boolean xlarge = ((activityContext.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_XLARGE);

        // If XLarge, checks if the Generalized Density is at least MDPI
        // (160dpi)
        if (xlarge) {
            DisplayMetrics metrics = new DisplayMetrics();
            Activity activity = (Activity) activityContext;
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            // MDPI=160, DEFAULT=160, DENSITY_HIGH=240, DENSITY_MEDIUM=160,
            // DENSITY_TV=213, DENSITY_XHIGH=320
            if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT
                    || metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
                    || metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM
                    || metrics.densityDpi == DisplayMetrics.DENSITY_TV
                    || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {

                // Yes, this is a tablet!
                return true;
            }
        }

        // No, this is not a tablet!
        return false;
    }
    @Override
    public ModelDevice onDeviceRegistration(MainActivity main, String idAccount) {
        String typeDevice = "";
        if (isTabletDevice(main)) {
            typeDevice = "Tablet";
        } else {
            typeDevice = "SmartPhone";
        }
        ModelDevice modelDevice = new ModelDevice();
        modelDevice.setIdDevice(getDeviceGenerationNumber(main));
        modelDevice.setIdUser(idAccount);
        modelDevice.setLockStatus("user");  //  lock, unlock and user
        modelDevice.setNameDevice(getDeviceModel());//  GET DEVICE BRAND AND MODEL
        modelDevice.setOnlineStatus("true");//  STATUS ONLINE DEVICE
        modelDevice.setTypeDevice(typeDevice);//  TYPE DEVICE, SMART PHONE OR THE TABLET


        //  2 - Parameter = UNIQUE IDENTIFICATION, IMEI IF SIM CARD EXIST AND PERMISSION TRUE OR DEVICE SERIAL NUMBER
        //   model.onDeviceRegistration(modelDevice, getDeviceGenerationNumber());
        return modelDevice;
    }
}
