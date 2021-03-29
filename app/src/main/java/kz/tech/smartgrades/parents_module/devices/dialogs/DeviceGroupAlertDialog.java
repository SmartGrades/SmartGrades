package kz.tech.smartgrades.parents_module.devices.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomButton;
import kz.tech.smartgrades.root.ui.CustomView.CustomEditText;

public class DeviceGroupAlertDialog extends AlertDialog.Builder {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onMessageAlert(String msg);
        void onEnterTextClick(String text);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt", "orn:ver"};
    private static final String[] PARAM_ET = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "grv:CHCV", "mrg:10,20,10,10"};
    private static final String[] PARAM_BTN = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parW:100", "mrg:0,0,0,10",
            "backR:RED_ONE", "GRAV:HOR", "txtC:WHITE"};
    public AlertDialog dialog;
    private Context context;
    private Resources res;
    public DeviceGroupAlertDialog(Context context, Resources res) {
        super(context);
        this.context = context;
        this.res = res;
    }

    public DeviceGroupAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
    public void showAlertDialog() {
        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM);
        this.setView(linearLayout);

        EditText editText = new CustomEditText().onCustom(context, PARAM_ET, res.getString(R.string.enter_new_device_name));
        linearLayout.addView(editText);
        Button button = new CustomButton().onCustom(context, PARAM_BTN, "OK");
        linearLayout.addView(button);

        dialog = this.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    if(TextUtils.isEmpty(editText.getText().toString())) {
                        onItemClickListener.onMessageAlert(res.getString(R.string.enter_new_device_name));
                        return;
                    } else {
                        onItemClickListener.onEnterTextClick(editText.getText().toString());
                        dialog.dismiss();
                    }

                }
            }
        });
    }
}
