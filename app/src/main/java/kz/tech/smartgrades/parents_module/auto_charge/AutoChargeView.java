package kz.tech.smartgrades.parents_module.auto_charge;

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

public class AutoChargeView extends RelativeLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onCurrentChildClick(int position, boolean isSelect);
        void onDisableAutoChargeClick(boolean isOpen);
        void onAutoChargeTimeClick(String text);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};


    private static final String[] PARAM_INSTALL_COUNT = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parH:30", "mrg:0,85,0,0",
            "txtC:GRAY_THREE", "grv:CHCV", "txtS:12"};

    private static final String[] PARAM_RV = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "mrg:0,115,0,130", "hfs:true", "layMan:llm"};

    private static final String[] PARAM_ACCRUAL_RECOMMENDATION = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:30", "mrg:0,0,0,100",
            "Rule:BOTTOM", "txtC:BLUE_ONE", "grv:CHCV"};

    private static final String[] PARAM_BOTTOM_AUTO_CHARGE_TIME = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:50", "mrg:0,0,0,50",
            "Rule:BOTTOM", "orn:hor", "WeiSum:8", "backC:WHITE"};

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



    private static final String[] PARAM_VIEW_LINE_4 = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parH:1", "mrg:0,0,0,100",
            "backC:GRAY_THREE", "Rule:BOTTOM"};
    private static final String[] PARAM_VIEW_LINE_5 = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parH:1", "mrg:0,0,0,50",
            "backC:GRAY_THREE", "Rule:BOTTOM"};


    private static final String[] PARAM_TIME_AUTO_CHARGE_TEXT = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:4",
            "txtC:GRAY_THREE", "grv:CHCV"};
    private static final String[] PARAM_TIME_HH_MM = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:3",
            "txtC:BLACK", "grv:CHCV"};
    private static final String[] PARAM_TIME_MOVE = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1", "pad:5,5,5,5"
    };


    private Context context;

    public RecyclerView recyclerView;


    private TextView tvTime;
    private boolean isChildSelect = false;
    private Switch sDisableAutoCharge;
    private String text;
    public AutoChargeView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new RelativeLayoutParams().getParams(context, PARAM));
        this.context = context;

        View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE_1);
        this.addView(v1);



        View v2 = new CustomView().onCustom(context, PARAM_VIEW_LINE_2);
        this.addView(v2);

        TextView tvInstallCount = new CustomTextView().onCustom(context, PARAM_INSTALL_COUNT, res.getString(R.string.set_the_number_of_coins));
        this.addView(tvInstallCount);



        recyclerView = new CustomRecyclerView().onCustom(context, PARAM_RV);
        this.addView(recyclerView);






        TextView tvAccrualRecommendation = new CustomTextView().onCustom(context, PARAM_ACCRUAL_RECOMMENDATION, res.getString(R.string.accrual_recommendations));
        this.addView(tvAccrualRecommendation);
        ////////////////////////////////////

        View v4 = new CustomView().onCustom(context, PARAM_VIEW_LINE_4);
        this.addView(v4);

        LinearLayout llBottomAutoCharge = new CustomLinearLayout().onCustom(context, PARAM_BOTTOM_AUTO_CHARGE_TIME);
        this.addView(llBottomAutoCharge);

        TextView tvTimeAutoCharge = new CustomTextView().onCustom(context, PARAM_TIME_AUTO_CHARGE_TEXT, res.getString(R.string.auto_charge_time));
        llBottomAutoCharge.addView(tvTimeAutoCharge);

        tvTime = new CustomTextView().onCustom(context, PARAM_TIME_HH_MM, null);
        llBottomAutoCharge.addView(tvTime);

        ImageView ivMove = new CustomImageView().onCustom(context, PARAM_TIME_MOVE, R.mipmap.move_hor_red);
        llBottomAutoCharge.addView(ivMove);
        ivMove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    if (text.equals("close")) {
                        onItemClickListener.onAutoChargeTimeClick("close");
                    } else {
                        onItemClickListener.onAutoChargeTimeClick(tvTime.getText().toString());
                    }
                }
            }
        });

        //////////////////////
        View v5 = new CustomView().onCustom(context, PARAM_VIEW_LINE_5);
        this.addView(v5);

        LinearLayout llBottomDisableAutoCharge = new CustomLinearLayout().onCustom(context, PARAM_BOTTOM_DISABLE_AUTO_CHARGE);
        this.addView(llBottomDisableAutoCharge);

        ImageView ivDisableAutoCharge = new CustomImageView().onCustom(context, PARAM_DISABLE_AUTO_CHARGE_IMAGE, R.mipmap.auto_coins);
        llBottomDisableAutoCharge.addView(ivDisableAutoCharge);

        TextView tvDisableAutoCharge = new CustomTextView().onCustom(context, PARAM_DISABLE_AUTO_CHARGE_TEXT, res.getString(R.string.disable_all_auto_charges_by_day));
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

    public AutoChargeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoChargeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AutoChargeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }



    public void setAutoTime(String text) {
        if (text != null) {
            if (text.equals("close")) {
                this.text = text;
                tvTime.setText(context.getResources().getString(R.string.turn_off));
            } else {
                tvTime.setText(text);
            }

        }
    }
    public void setTimeData(String text, String selectAutoChargeDisable) {
        tvTime.setText(text);
        this.text = text;
        switch (selectAutoChargeDisable) {
            case "open": sDisableAutoCharge.setChecked(false); break;
            case "close": sDisableAutoCharge.setChecked(true); break;
        }
    }
}
