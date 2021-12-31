package kz.tech.smartgrades.root.date;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

public class DateCustomPicker implements IDateCustomPicker{
    public DateCustomPicker(Context context) {
    }
    @Override
    public String getCurrentDate() {
        String sDay = "";
        String sMonth = "";
        String sYear = "";
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (month < 9) {
            sMonth = "0" + String.valueOf(month);
        } else {
            sMonth = String.valueOf(month);
        }
        if (day < 9) {
            sDay = "0" + String.valueOf(day);
        } else {
            sDay = String.valueOf(day);
        }
        sYear = String.valueOf(year);
        return sDay + "." + sMonth + "." + sYear;
    }
    @Override
    public String getDateForReports(int offset) {
        Calendar calendar = Calendar.getInstance();;
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, offset);
        String sDay = "";
        String sMonth = "";
        String sYear = "";
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (month < 9) {
            sMonth = "0" + String.valueOf(month);
        } else {
            sMonth = String.valueOf(month);
        }
        if (day < 9) {
            sDay = "0" + String.valueOf(day);
        } else {
            sDay = String.valueOf(day);
        }
        sYear = String.valueOf(year);
        return sDay + "." + sMonth + "." + sYear;
    }
}
