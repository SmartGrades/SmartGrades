package kz.tech.smartgrades.root.tools;

import android.os.Build;

public class GetDeviceNameAndModel {
    public String getDeviceInfo() {
        String manufacturer = Build.MANUFACTURER;
        String brand = Build.BRAND;
        String product = Build.PRODUCT;
        String model = Build.MODEL;
        return manufacturer + " " + brand + " " + product + " " + model;
    }
}
