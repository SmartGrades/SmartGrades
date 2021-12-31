package kz.tech.smartgrades.parents_module.auto_charge.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class AutoChargeTimeDialog extends BottomSheetDialog {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(String coinsAutoTime);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "orn:ver"};
    private static final String[] PARAM_TITLE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:40", "mrg:20,0,20,0",
            "backC:WHITE", "grv:CHCV", "txtC:BLACK"};
    private static final String[] PARAM_CONTAINER_2 = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:50",
            "orn:hor", "WeiSum:4", "backC:WHITE"};
    private static final String[] PARAM_OK_CANCEL = {"prt:LinLay", "layW:0", "layH:mPrt",
            "grv:CHCV", "wei:1", "pad:5,5,5,5", "txtC:GREEN_ONE"};
    private static final String[] PARAM_TURN_OFF = {"prt:LinLay", "layW:0", "layH:mPrt",
            "grv:CHCV", "wei:2", "pad:5,5,5,5", "txtC:RED_ONE"};

    private BottomSheetDialog dialog;
    private String startHour, startMinute;
    public AutoChargeTimeDialog(Context context, String selectDay, Resources res) {
        super(context);
        switch (selectDay) {
            case "close": selectDay = "08:00"; break;//  default date, if variable == close
        }
        dialog = this;

        String[] parseStartDate = selectDay.split(":");//  PARSE START DATE DOT
        startHour = parseStartDate[0];//  GET START HOUR
        startMinute = parseStartDate[1];//  GET START MINUTE

        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM);
        this.setContentView(linearLayout);

        TextView tvTitle = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.set_the_interval_for_access));
        linearLayout.addView(tvTitle);

        //           TIME   PICKER
        LayoutInflater inflater = getLayoutInflater();
        View vDatePicker = (View) inflater.inflate(R.layout.timer_picker_solo, null);
        linearLayout.addView(vDatePicker);

        TimePicker timePicker1 = (TimePicker) vDatePicker.findViewById(R.id.timePicker1);
        timePicker1.setIs24HourView(true);
        timePicker1.setCurrentHour(Integer.parseInt(startHour));
        timePicker1.setCurrentMinute(Integer.parseInt(startMinute));

        timePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String min = String.valueOf(minute);
                if (minute < 9 ) {
                    min = "0" + String.valueOf(minute);
                } else if (minute == 0) {
                    min = "00";
                }
                startHour = String.valueOf(hourOfDay);
                startMinute = min;
            }
        });

        ///        LL 2
        LinearLayout ll2 = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_2);
        linearLayout.addView(ll2);

        TextView tvTurnOff = new CustomTextView().onCustom(context, PARAM_TURN_OFF, res.getString(R.string.turn_off));
        ll2.addView(tvTurnOff);
        tvTurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick("close");
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
                    onItemClickListener.onItemClick(startHour + ":" + startMinute);
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

    public AutoChargeTimeDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected AutoChargeTimeDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
