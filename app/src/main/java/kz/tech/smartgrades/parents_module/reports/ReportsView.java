package kz.tech.smartgrades.parents_module.reports;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parents_module.auto_charge.adapters.UserPhotoSelect;
import kz.tech.smartgrades.root.models.ModelChild;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomViewPager;

public class ReportsView extends FrameLayout {

    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};


    private static final String[] PARAM_PAGER = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parH:56", "mrg:0,5,0,0",
            "backC:WHITE"};
    private static final String[] PARAM_CONTAINER = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,122,0,0",
            "orn:ver", "WeiSum:2", "backC:WHITE"};
    private static final String[] PARAM_CONTAINER_TOP = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "wei:1",
            "orn:ver", "WeiSum:10", };
    private static final String[] PARAM_CONTAINER_BOTTOM = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "wei:1",
            "orn:ver", "WeiSum:10"};
    private static final String[] PARAM_TEXT_1 = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "wei:2",
            "txtC:GRAY_THREE", "grv:BCH"};
    private static final String[] PARAM_TEXT_2 = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "wei:1",
            "txtC:RED_ONE", "grv:CHCV"};
    private Context context;
    private Resources res;

    private UserPhotoSelect adapter;
    private TextView tvWeekText1, tvWeekText2, tvMonthText1, tvMonthText2;
    public ReportsView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        this.context = context;
        this.res = res;

        ViewPager viewPager = new CustomViewPager().onCustom(context, PARAM_PAGER);
        this.addView(viewPager);

        adapter = new UserPhotoSelect(context);
        viewPager.setAdapter(adapter);

        LinearLayout llContainer = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER);
        this.addView(llContainer);

        LinearLayout llTop = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_TOP);
        llContainer.addView(llTop);

        tvWeekText1 = new CustomTextView().onCustom(context, PARAM_TEXT_1, null);
        llTop.addView(tvWeekText1);

        tvWeekText2 = new CustomTextView().onCustom(context, PARAM_TEXT_2, null);
        llTop.addView(tvWeekText2);

        BarChart barChart = new BarChart(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        params.weight = 7;
        barChart.setLayoutParams(params);
        llTop.addView(barChart);
        barChart.setScaleEnabled(false);
        barChart.setTouchEnabled(false);
      //  barChart.setBackgroundColor(Color.BLUE);
  //      barChart.setDrawBarShadow(false);
    //    barChart.setDrawValueAboveBar(true);
      //  barChart.setMaxVisibleValueCount(50);
     //   barChart.setPinchZoom(false);
     //   barChart.setDrawGridBackground(true);
        setDataWeek(barChart);






        LinearLayout llBottom = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_BOTTOM);
        llContainer.addView(llBottom);

        tvMonthText1 = new CustomTextView().onCustom(context, PARAM_TEXT_1, null);
        llBottom.addView(tvMonthText1);

        tvMonthText2 = new CustomTextView().onCustom(context, PARAM_TEXT_2, null);
        llBottom.addView(tvMonthText2);

        BarChart bcMonth = new BarChart(context);
        bcMonth.setLayoutParams(params);
        llBottom.addView(bcMonth);
        bcMonth.setScaleEnabled(false);
        bcMonth.setTouchEnabled(false);

        setDataMonth(bcMonth);
    }

    public ReportsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ReportsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ReportsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void onSelectChild(List<ModelChild> listChild) {
        adapter.onUpdateDate(listChild);
    }

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
        tvWeekText1.setText(res.getString(R.string.reports_week_text_one) + "3.5" + res.getString(R.string.obols));
        tvWeekText2.setText(res.getString(R.string.reports_week_text_two) + "1.5" + res.getString(R.string.times));


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


        tvMonthText1.setText(res.getString(R.string.reports_month_text_one) + "5.5" + res.getString(R.string.obols));
        tvMonthText2.setText(res.getString(R.string.reports_week_text_two) + "2.2" + res.getString(R.string.times));
        barChart.getBarData().setDrawValues(false);

        LimitLine ll = new LimitLine(2, "");
        ll.setLineWidth(1f);
        ll.enableDashedLine(20f, 5f, 0f);

        barChart.getAxisLeft().addLimitLine(ll);
    }

}
