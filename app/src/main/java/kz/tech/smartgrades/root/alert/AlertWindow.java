package kz.tech.smartgrades.root.alert;

import androidx.appcompat.app.AlertDialog;

import android.content.Context;

import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import kz.tech.esparta.R;

public class AlertWindow implements IAlert {
    private Context c;

    public AlertWindow(Context c) {
        this.c = c;
    }

    @Override
    public void onToast(String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onAlertDialogSolo(Context context, String title, String message, String btn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onAlertDialogCustomOne(AppCompatActivity activity, String title, String message, String action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View v = activity.getLayoutInflater().inflate(R.layout.alert_dialog_builder_custom_one, null);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        ((TextView) v.findViewById(R.id.tvTitle)).setText(title);
        ((TextView) v.findViewById(R.id.tvTransactionDate)).setText(message);
        TextView tvAction = v.findViewById(R.id.tvAction);
        tvAction.setText(action);
        tvAction.setOnClickListener(v1 -> alertDialog.dismiss());


    }

    @Override
    public void hideKeyboard(AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View f = activity.getCurrentFocus();
        if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
            imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
        else
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
