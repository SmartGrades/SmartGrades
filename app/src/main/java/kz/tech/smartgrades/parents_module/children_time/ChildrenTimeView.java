package kz.tech.smartgrades.parents_module.children_time;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomRecyclerView;
import kz.tech.smartgrades.root.ui.CustomView.CustomSwitch;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;

public class ChildrenTimeView extends RelativeLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onCurrentChildClick(int position, boolean isSelect);
        void onDisableAutoChargeClick(boolean isOpen);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};


    private static final String[] PARAM_INSTALL_COUNT = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parH:30", "mrg:0,85,0,0",
            "txtC:GRAY_THREE", "grv:CHCV", "txtS:12"};

    private static final String[] PARAM_RV = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "mrg:0,115,0,80", "hfs:true", "layMan:llm"};

    private static final String[] PARAM_ACCRUAL_RECOMMENDATION = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:30", "mrg:0,0,0,50",
            "Rule:BOTTOM", "txtC:BLUE_ONE", "grv:CHCV", "txtS:12"};


    private static final String[] PARAM_BOTTOM_DISABLE_AUTO_CHARGE = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:50",
            "Rule:BOTTOM", "orn:hor", "WeiSum:7", "backC:WHITE"};

    private static final String[] PARAM_DISABLE_AUTO_CHARGE_IMAGE = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1",
            "pad:5,5,5,5"};
    private static final String[] PARAM_DISABLE_AUTO_CHARGE_TEXT = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:5",
            "txtC:BLACK", "grv:CHCV"};
    private static final String[] PARAM_DISABLE_AUTO_CHARGE_SWITCH = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1"};


    private static final String[] PARAM_VIEW_LINE_1 = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parH:1", "mrg:0,85,0,0",
            "backC:GRAY_THREE", "Rule:TOP"};
    private static final String[] PARAM_VIEW_LINE_2 = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parH:1", "mrg:0,115,0,0",
            "backC:GRAY_THREE", "Rule:TOP"};

    private static final String[] PARAM_VIEW_LINE_5 = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parH:1", "mrg:0,0,0,50",
            "backC:GRAY_THREE", "Rule:BOTTOM"};

    private Context context;

    public RecyclerView recyclerView;

    private Switch sDisableAutoCharge;
    private boolean isChildSelect = false;
    public ChildrenTimeView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new RelativeLayoutParams().getParams(context, PARAM));
        this.context = context;

        View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE_1);
        this.addView(v1);


        View v2 = new CustomView().onCustom(context, PARAM_VIEW_LINE_2);
        this.addView(v2);

        TextView tvInstallCount = new CustomTextView().onCustom(context, PARAM_INSTALL_COUNT, res.getString(R.string.set_time_limits));
        this.addView(tvInstallCount);

        recyclerView = new CustomRecyclerView().onCustom(context, PARAM_RV);
        this.addView(recyclerView);

        TextView tvAccrualRecommendation = new CustomTextView().onCustom(context, PARAM_ACCRUAL_RECOMMENDATION, res.getString(R.string.why_can_i_limit));
        this.addView(tvAccrualRecommendation);



        //////////////////////
        View v5 = new CustomView().onCustom(context, PARAM_VIEW_LINE_5);
        this.addView(v5);

        LinearLayout llBottomDisableAutoCharge = new CustomLinearLayout().onCustom(context, PARAM_BOTTOM_DISABLE_AUTO_CHARGE);
        this.addView(llBottomDisableAutoCharge);

        ImageView ivDisableAutoCharge = new CustomImageView().onCustom(context, PARAM_DISABLE_AUTO_CHARGE_IMAGE, R.mipmap.auto_coins);
        llBottomDisableAutoCharge.addView(ivDisableAutoCharge);

        TextView tvDisableAutoCharge = new CustomTextView().onCustom(context, PARAM_DISABLE_AUTO_CHARGE_TEXT, res.getString(R.string.disable_all_day_restrictions));
        llBottomDisableAutoCharge.addView(tvDisableAutoCharge);

        sDisableAutoCharge = new CustomSwitch().onCustom(context, PARAM_DISABLE_AUTO_CHARGE_SWITCH);
        llBottomDisableAutoCharge.addView(sDisableAutoCharge);
        sDisableAutoCharge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onItemClickListener != null) {
                    onItemClickListener.onDisableAutoChargeClick(isChecked);
                }
                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });


    }

    public ChildrenTimeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildrenTimeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChildrenTimeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }



    public void setTimeData(String selectAutoChargeDisable) {
        switch (selectAutoChargeDisable) {
            case "open": sDisableAutoCharge.setChecked(false); break;
            case "close": sDisableAutoCharge.setChecked(true); break;
        }

    }
}
