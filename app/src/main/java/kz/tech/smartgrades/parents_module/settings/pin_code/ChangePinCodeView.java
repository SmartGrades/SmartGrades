package kz.tech.smartgrades.parents_module.settings.pin_code;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomEditText;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;

public class ChangePinCodeView extends FrameLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onMessageClick(String msg);
        void onOkClick(String currentPIN, String newPIN, String newPINAgain);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};


    private static final String[] PARAM_CONTAINER = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "orn:ver", "mrg:0,5,0,0",
            "backC:WHITE"};

    private static final String[] PARAM_ET = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50", "mrg:20,0,20,0", "pad:5,5,5,5",
            "Hint:GRAY_TWO", "backC:0", "grv:LCV", "InTy:NUMB", "Filter:PIN"};

    private static final String[] PARAM_OK = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parW:120", "parH:50", "mrg:0,0,20,0",
            "GRAV:RIGHT", "txtC:GREEN_ONE", "txtS:18", "grv:RCV"};

    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:1", "backC:GRAY_THREE"};
    private Context context;
    public ChangePinCodeView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        this.context = context;






        LinearLayout llContainer = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER);
        this.addView(llContainer);

        View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        llContainer.addView(v1);

        EditText etCurrentPassword = new CustomEditText().onCustom(context, PARAM_ET, res.getString(R.string.current_quick_access_code));
        llContainer.addView(etCurrentPassword);

        View v2 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        llContainer.addView(v2);

        EditText etNewPassword = new CustomEditText().onCustom(context, PARAM_ET, res.getString(R.string.new_quick_access_code));
        llContainer.addView(etNewPassword);

        View v3 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        llContainer.addView(v3);

        EditText etNewPasswordAgain = new CustomEditText().onCustom(context, PARAM_ET, res.getString(R.string.new_quick_access_code_again));
        llContainer.addView(etNewPasswordAgain);

        View v4 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        llContainer.addView(v4);


        TextView tvOk = new CustomTextView().onCustom(context, PARAM_OK, "OK");
        llContainer.addView(tvOk);
        tvOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(etCurrentPassword.getText().toString())) {
                    onItemClickListener.onMessageClick(res.getString(R.string.enter_current_quick_access_code));
                    return;
                }
                if(TextUtils.isEmpty(etNewPassword.getText().toString())) {
                    onItemClickListener.onMessageClick(res.getString(R.string.enter_new_quick_access_code));
                    return;
                }
                if(TextUtils.isEmpty(etNewPasswordAgain.getText().toString())) {
                    onItemClickListener.onMessageClick(res.getString(R.string.enter_new_quick_access_code_again));
                    return;
                }
                if (onItemClickListener != null) {
                    onItemClickListener.onOkClick(etCurrentPassword.getText().toString(),
                            etNewPassword.getText().toString(),
                            etNewPasswordAgain.getText().toString());
                }
            }
        });
    }

    public ChangePinCodeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChangePinCodeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChangePinCodeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
