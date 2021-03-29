package kz.tech.smartgrades.parents_module.family_group.dialogs_page.interaction_form;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomNumberPicker;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class BottomDialogAccrualOfOffsetSuper extends BottomSheetDialog {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onDisableClick();
        void onOkClick(String text);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "orn:ver"};
    private static final String[] PARAM_TITLE = {"prt:LinLay", "layW:mPrt", "layH:wCnt",
            "grv:CHCV", "txtC:BLACK"};
    private static final String[] PARAM_FL = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:200"};
    private static final String[] PARAM_NUMB_PICKER = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "GRAV:HOR",
            "DisplayValue:Second", "parW:50", "parH:200", "Wrap:false", "MinValue:0", "MaxValue:31", "Value:7", "Focus:block"};

    private static final String[] PARAM_DISABLE = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "mrg:20,0,0,10",
            "grv:LCV", "txtC:BLUE_ONE", "txtS:18", "GRAV:BOTTOM_LEFT"};

    private static final String[] PARAM_OK = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "mrg:0,0,20,10",
            "grv:RCV", "txtC:GREEN_ONE", "txtS:18", "GRAV:BOTTOM_RIGHT", "TyFa:BOLD"};

    private String[] values2 = new String[]{"0", "+1", "+2", "+3", "+4", "+5", "+6", "+7", "+8", "+9", "+10",
            "+11", "+12", "+13", "+14", "+15", "+16", "+17", "+18", "+19", "+20",
            "+21", "+22", "+23", "+24", "+25", "+26", "+27", "+28", "+29", "+30"};
    private BottomSheetDialog dialog;
    private Context context;
    private Resources res;
    private LinearLayout linearLayout;
    private String lol = "+5";
    public BottomDialogAccrualOfOffsetSuper(Context context, Resources res, String text) {
        super(context);
        this.context = context;
        this.res = res;
        dialog = this;
        lol = text;


        linearLayout = new CustomLinearLayout().onCustom(context, PARAM);
        this.setContentView(linearLayout);

        TextView tvName = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.accrual_of_coins_title));
        linearLayout.addView(tvName);

        FrameLayout frameLayout = new CustomFrameLayout().onCustom(context, PARAM_FL);
        linearLayout.addView(frameLayout);


        NumberPicker np = new CustomNumberPicker().onCustom(context, PARAM_NUMB_PICKER);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                lol = values2[newVal];
            }
        });
        frameLayout.addView(np);



        TextView tvDisable = new CustomTextView().onCustom(context, PARAM_DISABLE, res.getString(R.string.turn_off));
        frameLayout.addView(tvDisable);
        tvDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onDisableClick();
                    dialog.dismiss();
                }
            }
        });


        TextView tvOK = new CustomTextView().onCustom(context, PARAM_OK, "OK");
        frameLayout.addView(tvOK);
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onOkClick(lol);
                    dialog.dismiss();
                }
            }
        });
    }

    public BottomDialogAccrualOfOffsetSuper(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected BottomDialogAccrualOfOffsetSuper(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
