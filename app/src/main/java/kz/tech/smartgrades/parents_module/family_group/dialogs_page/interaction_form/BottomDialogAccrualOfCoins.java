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


public class BottomDialogAccrualOfCoins extends BottomSheetDialog {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onOkClick(String s1, String s2);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "orn:ver"};
    private static final String[] PARAM_TITLE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "grv:CHCV", "txtC:BLACK"};
    private static final String[] PARAM_LL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "orn:hor", "WeiSum:2", "parH:200"};
    private static final String[] PARAM_FL = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1"};
    private static final String[] PARAM_NUMB_PICKER_1 = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "mrg:0,0,50,0", "GRAV:RIGHT",
            "DisplayValue:First", "parW:50", "parH:200", "Wrap:false", "MinValue:0", "MaxValue:26", "Value:10", "Focus:block"};
    private static final String[] PARAM_NUMB_PICKER_2 = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "mrg:50,0,0,0", "GRAV:LEFT",
            "DisplayValue:First", "parW:50", "parH:200", "Wrap:false", "MinValue:0", "MaxValue:26", "Value:10", "Focus:block"};
    private static final String[] PARAM_FROM = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "mrg:0,0,100,0",
            "grv:CHCV", "txtC:BLACK", "txtS:18", "GRAV:VER", "TyFa:BOLD"};
    private static final String[] PARAM_TO = {"prt:FrmLay", "layW:wCnt", "layH:wCnt",
            "grv:LCV", "txtC:BLACK", "txtS:18", "GRAV:LEFT_VER", "TyFa:BOLD"};
    private static final String[] PARAM_OK = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "mrg:0,0,20,10",
            "grv:RCV", "txtC:GREEN_ONE", "txtS:18", "GRAV:BOTTOM_RIGHT", "TyFa:BOLD"};
    private String[] values = new String[]{"-10", "-9", "-8", "-7", "-6", "-5", "-4","-3","-2","-1", "0",
            "+1", "+2", "+3", "+4", "+5", "+6", "+7", "+8", "+9", "+10", "+11", "+12", "+13", "+14", "+15"};
    private BottomSheetDialog dialog;
    private Context context;
    private Resources res;
    private LinearLayout linearLayout;
    private String lol1 = "", lol2 = "";
    public BottomDialogAccrualOfCoins(Context context, Resources res, String s1, String s2) {
        super(context);
        this.context = context;
        this.res = res;
        dialog = this;
        lol1 = s1;
        lol2 = s2;

        linearLayout = new CustomLinearLayout().onCustom(context, PARAM);
        this.setContentView(linearLayout);

        TextView tvName = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.accrual_of_coins_title));
        linearLayout.addView(tvName);

        LinearLayout llHor = new CustomLinearLayout().onCustom(context, PARAM_LL);
        linearLayout.addView(llHor);

        ///////////            1           /////////////////
        FrameLayout fl1 = new CustomFrameLayout().onCustom(context, PARAM_FL);
        llHor.addView(fl1);

        TextView tvFrom = new CustomTextView().onCustom(context, PARAM_FROM, res.getString(R.string.from));
        fl1.addView(tvFrom);

        NumberPicker np1 = new CustomNumberPicker().onCustom(context, PARAM_NUMB_PICKER_1);
        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                lol1 = values[newVal];
            }
        });
        fl1.addView(np1);

        ///////////            2           /////////////////
        FrameLayout fl2 = new CustomFrameLayout().onCustom(context, PARAM_FL);
        llHor.addView(fl2);

        TextView tvTo = new CustomTextView().onCustom(context, PARAM_TO, res.getString(R.string.to));
        fl2.addView(tvTo);

        NumberPicker np2 = new CustomNumberPicker().onCustom(context, PARAM_NUMB_PICKER_2);
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                lol2 = values[newVal];
            }
        });
        fl2.addView(np2);

        TextView tvOK = new CustomTextView().onCustom(context, PARAM_OK, "OK");
        fl2.addView(tvOK);
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onOkClick(lol1, lol2);
                    dialog.dismiss();
                }

            }
        });
    }

    public BottomDialogAccrualOfCoins(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected BottomDialogAccrualOfCoins(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
