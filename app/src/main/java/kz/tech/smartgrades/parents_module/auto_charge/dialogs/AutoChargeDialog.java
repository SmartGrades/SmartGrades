package kz.tech.smartgrades.parents_module.auto_charge.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;

public class AutoChargeDialog extends BottomSheetDialog {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(int position, String incrementTime);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "orn:ver"};
    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:1", "backC:GRAY_THREE"};
    private static final String[] PARAM_TITLE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:40",
            "backC:WHITE", "grv:CHCV", "txtC:BLACK"};

    private static final String[] PARAM_CONTAINER_1 = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:50", "mrg:50,0,50,0",
            "orn:hor", "WeiSum:3", "backC:WHITE"};
    private static final String[] PARAM_IMAGE_INCREMENT = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1", "pad:5,5,5,5"};
    private static final String[] PARAM_FL = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1"};
    private static final String[] PARAM_IMAGE_FL = {"prt:FrmLay", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_TEXT_INCREMENT = {"prt:FrmLay", "layW:mPrt", "layH:mPrt",
            "grv:CHCV", "txtC:GRAY_THREE", "pad:5,5,5,5"};

    private static final String[] PARAM_COINS_DATE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:30",
            "grv:CHCV", "txtC:GREEN_ONE"};


    private static final String[] PARAM_CONTAINER_2 = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:50",
            "orn:hor", "WeiSum:4", "backC:WHITE"};
    private static final String[] PARAM_OK_CANCEL = {"prt:LinLay", "layW:0", "layH:mPrt",
             "grv:CHCV", "wei:1", "pad:5,5,5,5", "txtC:GREEN_ONE"};

    private static final String[] PARAM_TURN_OFF = {"prt:LinLay", "layW:0", "layH:mPrt",
            "grv:CHCV", "wei:2", "pad:5,5,5,5", "txtC:RED_ONE"};

    private BottomSheetDialog dialog;
    private TextView tvIncrementTime, tvCoinsDate;
    private int incrementTime = 0;
    private int pos = 0;
    public AutoChargeDialog(Context context, String selectDay, int position, Resources res) {
        super(context);
        if (selectDay.equals("close")) {
            selectDay = "20";
        }
        dialog = this;
        pos = position;
        incrementTime = Integer.parseInt(selectDay);
        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM);
        this.setContentView(linearLayout);

        TextView tvTitle = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.set_the_number_of_coins));
        linearLayout.addView(tvTitle);
        String day = "";
        String[] daysFull = res.getStringArray(R.array.days_full);
        switch (position) {
            case 0: day = daysFull[0]; break;//  Monday
            case 1: day = daysFull[1]; break;//  Tuesday
            case 2: day = daysFull[2]; break;//  Wednesday
            case 3: day = daysFull[3]; break;//  Thursday
            case 4: day = daysFull[4]; break;//  Friday
            case 5: day = daysFull[5]; break;//  Saturday
            case 6: day = daysFull[6]; break;//  Sunday
        }
        TextView tvSelectDay = new CustomTextView().onCustom(context, PARAM_TITLE, day);
        linearLayout.addView(tvSelectDay);
        ///         LL 1
        LinearLayout ll1 = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_1);
        linearLayout.addView(ll1);

        ImageView ivMinus = new CustomImageView().onCustom(context, PARAM_IMAGE_INCREMENT, R.mipmap.increment_minus);
        ll1.addView(ivMinus);
        ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (incrementTime > 0) {
                    incrementTime -= 20;
                    int result = ((int)(10*incrementTime)/20)/10;
                    tvIncrementTime.setText(String.valueOf(result));
                    if (incrementTime == 0) {
                        tvIncrementTime.setTextColor(ColorsRGB.GRAY_THREE);
                    }
                    int hours = incrementTime / 60; //since both are ints, you get an int
                    int minutes = incrementTime % 60;
                    String coinsDate = "";
                    if (hours > 0) { coinsDate += String.valueOf(hours) + res.getString(R.string.h); }
                    if (minutes > 0) { coinsDate += String.valueOf(minutes) + res.getString(R.string.min); }
                    if (incrementTime == 0 && hours == 0 && minutes == 0) { coinsDate = "0" + res.getString(R.string.min); }
                    tvCoinsDate.setText(coinsDate);
                }
            }
        });

        FrameLayout flIncrement = new CustomFrameLayout().onCustom(context, PARAM_FL);
        ll1.addView(flIncrement);

        ImageView ivImage = new CustomImageView().onCustom(context, PARAM_IMAGE_FL, R.mipmap.coins_gold);
        flIncrement.addView(ivImage);

        tvIncrementTime = new CustomTextView().onCustom(context, PARAM_TEXT_INCREMENT, null);
        flIncrement.addView(tvIncrementTime);
        if (incrementTime == 0) {
            int result = ((int)(10*incrementTime)/20)/10;
            tvIncrementTime.setText(String.valueOf(result));
            tvIncrementTime.setTextColor(ColorsRGB.GRAY_THREE);
        }else if (incrementTime > 0) {
            int result = ((int)(10*incrementTime)/20)/10;
            tvIncrementTime.setText(String.valueOf(result));
            tvIncrementTime.setTextColor(ColorsRGB.GREEN_ONE);
        }

        ImageView ivPlus = new CustomImageView().onCustom(context, PARAM_IMAGE_INCREMENT, R.mipmap.increment_plus);
        ll1.addView(ivPlus);
        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementTime += 20;
                int result = ((int)(10*incrementTime)/20)/10;
                tvIncrementTime.setText(String.valueOf(result));
                tvIncrementTime.setTextColor(ColorsRGB.GREEN_ONE);

                int hours = incrementTime / 60; //since both are ints, you get an int
                int minutes = incrementTime % 60;
                String coinsDate = "";
                if (hours > 0) { coinsDate += String.valueOf(hours) + res.getString(R.string.h); }
                if (minutes > 0) { coinsDate += String.valueOf(minutes) + res.getString(R.string.min); }
                if (incrementTime == 0 && hours == 0 && minutes == 0) { coinsDate = "0" + res.getString(R.string.min); }
                tvCoinsDate.setText(coinsDate);
            }
        });

        ////////          COINS DATE           ///////////
        int hours = incrementTime / 60; //since both are ints, you get an int
        int minutes = incrementTime % 60;
        String coinsDate = "";
        if (hours > 0) { coinsDate += String.valueOf(hours) + res.getString(R.string.h); }
        if (minutes > 0) { coinsDate += String.valueOf(minutes) + res.getString(R.string.min); }
        if (incrementTime == 0 && hours == 0 && minutes == 0) { coinsDate = "0" + res.getString(R.string.min); }
        tvCoinsDate = new CustomTextView().onCustom(context, PARAM_COINS_DATE, coinsDate);
        linearLayout.addView(tvCoinsDate);


        ///        LL 2
        LinearLayout ll2 = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_2);
        linearLayout.addView(ll2);

        TextView tvTurnOff = new CustomTextView().onCustom(context, PARAM_TURN_OFF, res.getString(R.string.turn_off));
        ll2.addView(tvTurnOff);
        tvTurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(pos, "close");
                }
                dialog.dismiss();
            }
        });

        TextView tvOk = new CustomTextView().onCustom(context, PARAM_OK_CANCEL, "Ok");
        ll2.addView(tvOk);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(pos, String.valueOf(incrementTime));
                }
                dialog.dismiss();
            }
        });

        TextView tvCancel = new CustomTextView().onCustom(context, PARAM_OK_CANCEL, res.getString(R.string.cancel));
        ll2.addView(tvCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public AutoChargeDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected AutoChargeDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
