package kz.tech.smartgrades.parents_module.coins.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomCheckBox;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;

public class IncrementDialog extends BottomSheetDialog {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private BottomSheetDialog dialog;
    private static final String[] PARAM = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "orn:ver"};
    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:1", "backC:GRAY_THREE"};

    private static final String[] PARAM_TITLE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:40",
            "backC:WHITE", "grv:CHCV", "txtC:GRAY_THREE"};
    private static final String[] PARAM_CHECKED = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:40",
            "backC:WHITE", "grv:LCV", "txtC:BLACK", "TyFa:BOLD", "txtS:18"};

    private static final String[] PARAM_OK = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:50", "mrg:56,5,56,5",
            "backR:RED_ONE", "grv:CHCV", "txtC:WHITE"};

    private int coins = 0;
    public IncrementDialog(@NonNull Context context, Resources res, int coins) {
        super(context);
        dialog = this;
       // this.coins = coins;
        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM);
        this.setContentView(linearLayout);

        View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v1);

        TextView tvTitle = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.obols_accounting_interval));
        linearLayout.addView(tvTitle);

        View v2 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v2);

        CheckBox cb1 = new CustomCheckBox().onCustom(context, PARAM_CHECKED, res.getString(R.string.coin_zero_five_min));
        linearLayout.addView(cb1);

        View v3 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v3);

        CheckBox cb2 = new CustomCheckBox().onCustom(context, PARAM_CHECKED, res.getString(R.string.coin_one_min));
        linearLayout.addView(cb2);

        View v4 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v4);

        TextView tvOk = new CustomTextView().onCustom(context, PARAM_OK, "OK");
        linearLayout.addView(tvOk);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.cancel();
                    dialog = null;
                }
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(IncrementDialog.this.coins);
                }
            }
        });

        View v5 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v5);


        switch (coins) {
            case 1:
                if (cb2.isChecked()) { cb2.setChecked(false); }
                cb1.setChecked(true);
                break;
            case 2:
                if (cb1.isChecked()) { cb1.setChecked(false); }
                cb2.setChecked(true);
                break;
        }
        cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb2.isChecked()) { cb2.setChecked(false); }
                cb1.setChecked(true);
                IncrementDialog.this.coins = 1;
            }
        });
        cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb1.isChecked()) { cb1.setChecked(false); }
                cb2.setChecked(true);
                IncrementDialog.this.coins = 2;
            }
        });

    }

    public IncrementDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected IncrementDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
