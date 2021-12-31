package kz.tech.smartgrades.parents_module.coins.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class ReportsDialog extends Dialog {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_TOOLBAR = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:50", "backC:WHITE"};
    private static final String[] PARAM_TOOLBAR_RL = {"prt:Toolbar", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_LEFT_BUTTON = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "pad:5,5,5,5"};
    private static final String[] PARAM_TITLE = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:50,0,50,0", "pad:5,5,5,5",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_AVATAR = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "pad:5,5,5,5",
            "BordC:GRAY_THREE", "BordW:2", "img:avatar", "Rule:RIGHT"};
    private static final String[] PARAM_REPORTS_CONTAINER = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,55,0,30",
            "orn:ver", "WeiSum:2", "backC:WHITE"};
    private static final String[] PARAM_REPORTS_CONTAINER_TOP = {"prt:LinLay", "layW:mPrt", "layH:0", "wei:1",
            "orn:ver", "WeiSum:10", };
    private static final String[] PARAM_REPORTS_CONTAINER_BOTTOM = {"prt:LinLay", "layW:mPrt", "layH:0", "wei:1",
            "orn:ver", "WeiSum:10"};
    private static final String[] PARAM_REPORTS_TEXT_1 = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "wei:2",
            "txtC:GRAY_THREE", "grv:BCH"};
    private static final String[] PARAM_REPORTS_TEXT_2 = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "wei:1",
            "txtC:RED_ONE", "grv:CHCV"};
    private static final String[] PARAM_CHANGE_DAILY_RATE = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:30",
            "txtC:BLUE_ONE", "grv:CHCV", "GRAV:BOTTOM", "backC:WHITE"};
    private TextView tvWeekText1, tvWeekText2, tvMonthText1, tvMonthText2;
    private Resources res;
    private Dialog dialog;
    private int defaultDay = 2;
    private BarChart bcWeek;
    public ReportsDialog(@NonNull Context context) {
        super(context);
    }
    public ReportsDialog(Context con, int themeResId, Resources res, String name, String avatar) {
        super(con, themeResId);
        this.res = res;
        dialog = this;

        FrameLayout frameLayout = new CustomFrameLayout().onCustom(con, PARAM);
        this.setContentView(frameLayout);

        Toolbar toolbar = new CustomToolbar().onCustom(con, PARAM_TOOLBAR);
        frameLayout.addView(toolbar);
        RelativeLayout rlToolbar = new CustomRelativeLayout().onCustom(con, PARAM_TOOLBAR_RL);
        toolbar.addView(rlToolbar);
        ImageView ivLeftButton = new CustomImageView().onCustom(con, PARAM_LEFT_BUTTON, R.mipmap.red_arrow_left);
        rlToolbar.addView(ivLeftButton);
        ivLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView tvTitle = new CustomTextView().onCustom(con, PARAM_TITLE, name);
        rlToolbar.addView(tvTitle);

        CircleImageView civAvatar = new CustomCircleImageView().onCustom(con, PARAM_AVATAR);
        rlToolbar.addView(civAvatar);
        Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);

        LinearLayout llReports = new CustomLinearLayout().onCustom(con, PARAM_REPORTS_CONTAINER);
        frameLayout.addView(llReports);

        LinearLayout llTop = new CustomLinearLayout().onCustom(con, PARAM_REPORTS_CONTAINER_TOP);
        llReports.addView(llTop);

        tvWeekText1 = new CustomTextView().onCustom(con, PARAM_REPORTS_TEXT_1, null);
        llTop.addView(tvWeekText1);

        tvWeekText2 = new CustomTextView().onCustom(con, PARAM_REPORTS_TEXT_2, null);
        llTop.addView(tvWeekText2);

        bcWeek = new BarChart(con);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        params.weight = 7;
        bcWeek.setLayoutParams(params);
        llTop.addView(bcWeek);
        bcWeek.setScaleEnabled(false);
        bcWeek.setTouchEnabled(false);
        //  barChart.setBackgroundColor(Color.BLUE);
        //      barChart.setDrawBarShadow(false);
        //    barChart.setDrawValueAboveBar(true);
        //  barChart.setMaxVisibleValueCount(50);
        //   barChart.setPinchZoom(false);
        //   barChart.setDrawGridBackground(true);
        //setDataWeek();

        setDataWeek(bcWeek);

        LinearLayout llBottom = new CustomLinearLayout().onCustom(con, PARAM_REPORTS_CONTAINER_BOTTOM);
        llReports.addView(llBottom);

        tvMonthText1 = new CustomTextView().onCustom(con, PARAM_REPORTS_TEXT_1, null);
        llBottom.addView(tvMonthText1);

        tvMonthText2 = new CustomTextView().onCustom(con, PARAM_REPORTS_TEXT_2, null);
        llBottom.addView(tvMonthText2);

        BarChart bcMonth = new BarChart(con);
        bcMonth.setLayoutParams(params);
        llBottom.addView(bcMonth);
        bcMonth.setScaleEnabled(false);
        bcMonth.setTouchEnabled(false);

        setDataMonth(bcMonth);

        TextView tvChangeDailyRate = new CustomTextView().onCustom(con, PARAM_CHANGE_DAILY_RATE, res.getString(R.string.change_daily_rate));
        frameLayout.addView(tvChangeDailyRate);

        //  change_daily_rate
    }
    protected ReportsDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


   // public void setDataWeek() {
        //  bcWeek
  //  }








    public void setDataWeek(BarChart barChart) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0,3.8f));
        barEntries.add(new BarEntry(1,4));
        barEntries.add(new BarEntry(2,4));
        barEntries.add(new BarEntry(3,4));
        barEntries.add(new BarEntry(4,4));
        barEntries.add(new BarEntry(5,4));
        barEntries.add(new BarEntry(6,3.8f));

        int blue1 = Color.rgb(100,149,237);
        int blue2 = Color.rgb(0,191,255);
        int blue3 = Color.rgb(0,191,255);
        int blue4 = Color.rgb(135,206,250);

        int[] colors = {blue1, blue1, blue2, blue3, blue3, blue4, blue4};

        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setColors(colors);

        BarData barData = new BarData(barDataSet);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        String[] weeks = res.getStringArray(R.array.days_of_the_week);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(weeks));

        barChart.setData(barData);
        barChart.setDescription(null);
        barChart.getLegend().setEnabled(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);

        barChart.getAxisRight().setEnabled(false);//  Remove right table column
        barChart.getAxisLeft().setAxisMinimum(1f);//  Start numb table left numbers

        barChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) Math.floor(value));
            }
        });

        int max = (int) barChart.getData().getYMax();
        barChart.getAxisLeft().setLabelCount(max);

        //  barChart.setViewPortOffsets(0f, 0f, 0f, 0f);

        // tvWeekText1, tvWeekText2, tvMonthText1, tvMonthText2;
        tvWeekText1.setText(res.getString(R.string.reports_week_text_one) + "3.5 " + res.getString(R.string.obols));
        tvWeekText2.setText(res.getString(R.string.reports_week_text_two) + "1.5 " + res.getString(R.string.times));


        barChart.getBarData().setDrawValues(false);

        LimitLine ll = new LimitLine(2.0f, "");
        ll.setLineWidth(1f);//  Line size height
        ll.enableDashedLine(20f, 5f, 0f);//  Line length and space
        //     ll.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);





        barChart.getAxisLeft().addLimitLine(ll);//  Add LimitLine to BarChart
    }
    public void setDataMonth(BarChart barChart) {
        String[] weeks = new String[24];
        int[] months = {3,4,1,1,1,5,6,7,5,9,9,9,9,9,9,7,8,8,6,7,6,5,8,9};
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            weeks[i] = "";
            barEntries.add(new BarEntry(i,months[i]));
        }


        BarDataSet barDataSet = new BarDataSet(barEntries, " ");
        BarData barData = new BarData(barDataSet);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(weeks));


        barChart.setData(barData);
        barChart.setDescription(null);
        barChart.getLegend().setEnabled(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);

        barChart.getAxisRight().setEnabled(false);//  Remove right table column


        tvMonthText1.setText(res.getString(R.string.reports_month_text_one) + "5.5 " + res.getString(R.string.obols));
        tvMonthText2.setText(res.getString(R.string.reports_week_text_two) + "2.2 " + res.getString(R.string.times));
        barChart.getBarData().setDrawValues(false);

        LimitLine ll = new LimitLine(2, "");
        ll.setLineWidth(1f);
        ll.enableDashedLine(20f, 5f, 0f);

        barChart.getAxisLeft().addLimitLine(ll);
    }

}
