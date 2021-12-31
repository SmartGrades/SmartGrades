package kz.tech.smartgrades.root.alert;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

public interface IAlert {
    void onToast(String msg);
    void onAlertDialogSolo(Context context, String title, String message, String btn);
    void onAlertDialogCustomOne(AppCompatActivity activity, String title, String message, String action);
    void hideKeyboard(AppCompatActivity activity);
}
