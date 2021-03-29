package kz.tech.smartgrades.parents_module.children_time.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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

public class ChildrenTimeDialog extends BottomSheetDialog {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(int position, String incrementTime);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private static final String[] PARAM = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "orn:ver"};
    private static final String[] PARAM_TITLE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:40", "mrg:20,0,20,0",
            "backC:WHITE", "grv:CHCV", "txtC:BLACK"};

    private static final String[] PARAM_TITLE_DAY = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:40", "mrg:20,0,20,0",
            "backC:WHITE", "grv:CHCV", "txtC:RED_ONE"};

    private static final String[] PARAM_CONTAINER_2 = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:50",
            "orn:hor", "WeiSum:4", "backC:WHITE"};
    private static final String[] PARAM_OK_CANCEL = {"prt:LinLay", "layW:0", "layH:mPrt",
            "grv:CHCV", "wei:1", "pad:5,5,5,5", "txtC:GREEN_ONE"};
    private static final String[] PARAM_TURN_OFF = {"prt:LinLay", "layW:0", "layH:mPrt",
            "grv:CHCV", "wei:2", "pad:5,5,5,5", "txtC:RED_ONE"};

    private static final String[] PARAM_START_END = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:30",
            "orn:hor", "WeiSum:2"};
    private static final String[] PARAM_START_END_TV = {"prt:LinLay", "layW:mPrt", "layH:mPrt",
            "wei:1", "txtC:BLUE_ONE", "txtS:12", "grv:CHCV"};


    private BottomSheetDialog dialog;
    private String incrementTime = "";
    private int pos = 0;
    private String startHour, startMinute, endHour, endMinute;
    public ChildrenTimeDialog(Context context, String selectDay, int position, Resources res) {
        super(context);
        switch (selectDay) {
            case "close": selectDay = "22:00-12:00"; break;//  default date, if variable == close
        }
        dialog = this;
        pos = position;

        String[] parseDate = selectDay.split("-");//  PARSE DATE DASH
        String startDate = parseDate[0];//  GET START DATE
        String endDate = parseDate[1];//  GET END DATE

        String[] parseStartDate = startDate.split(":");//  PARSE START DATE DOT
        startHour = parseStartDate[0];//  GET START HOUR
        startMinute = parseStartDate[1];//  GET START MINUTE

        String[] parseEndDate = endDate.split(":");//  PARSE END DATE DOT
        endHour = parseEndDate[0];//  GET END HOUR
        endMinute = parseEndDate[1];//  GET END MINUTE

    //    String result = startHour + ":" + startMinute + "-" + endHour + ":" + endMinute;

        //android.util.Log.e("Tag", " Msg");
        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM);
        this.setContentView(linearLayout);

        TextView tvTitle = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.set_time_limits));
        linearLayout.addView(tvTitle);

        String day = "";
        switch (position) {
            case 0: day = "Monday"; break;
            case 1: day = "Tuesday"; break;
            case 2: day = "Wednesday"; break;
            case 3: day = "Thursday"; break;
            case 4: day = "Friday"; break;
            case 5: day = "Saturday"; break;
            case 6: day = "Sunday"; break;
        }

        String[] from_to = res.getStringArray(R.array.from_to);//  0 = from, 1 = to

        String firstText = day;
        String textFirst =  from_to[0] + " " + startHour + ":" + startMinute + " " + from_to[1] + "  " + endHour + ":" + endMinute;
        int startIndex = 0;
        int endIndex = firstText.length();

        SpannableString spannableString = new SpannableString(firstText+ "\n"  + textFirst);
        spannableString.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView tvSelectDay = new CustomTextView().onCustom(context, PARAM_TITLE_DAY, null);//day
        linearLayout.addView(tvSelectDay);
        tvSelectDay.setText(spannableString);

        //   START END
        LinearLayout llStartEnd = new CustomLinearLayout().onCustom(context, PARAM_START_END);
        linearLayout.addView(llStartEnd);

        TextView tvStart = new CustomTextView().onCustom(context, PARAM_START_END_TV, res.getString(R.string.start));
        llStartEnd.addView(tvStart);

        TextView tvEnd = new CustomTextView().onCustom(context, PARAM_START_END_TV, res.getString(R.string.end));
        llStartEnd.addView(tvEnd);


        //           TIME   PICKER
        LayoutInflater inflater = getLayoutInflater();
        View vDatePicker = (View) inflater.inflate(R.layout.timer_picker_layout, null);
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

        TimePicker timePicker2 = (TimePicker) vDatePicker.findViewById(R.id.timePicker2);
        timePicker2.setIs24HourView(true);
        timePicker2.setCurrentHour(Integer.parseInt(endHour));
        timePicker2.setCurrentMinute(Integer.parseInt(endMinute));

        timePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String min = String.valueOf(minute);
                if (minute < 9 ) {
                    min = "0" + String.valueOf(minute);
                } else if (minute == 0) {
                    min = "00";
                }
                endHour = String.valueOf(hourOfDay);
                endMinute = min;
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
                    incrementTime = startHour + ":" + startMinute + "-" + endHour + ":" + endMinute;
                    onItemClickListener.onItemClick(pos, incrementTime);
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

    public ChildrenTimeDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }
    protected ChildrenTimeDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

}
