package kz.tech.smartgrades.parents_module.coins;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parents_module.coins.dialogs.IncrementDialog;
import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;
import kz.tech.smartgrades.root.ui.animation.GetButtonRipple;
import kz.tech.smartgrades.root.var_resources.DimensionDP;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;

public class CoinsView extends FrameLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onCoinsClick(int time);
        void onMessageClick(String msg);
        void onObolsClick(int numb);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    //////////////                COINS MAIN WINDOW                    ////////////////////
    private static final String[] PARAM_CONTAINER = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,85,0,0",
            "orn:ver", "WeiSum:100"};
    private static final String[] PARAM_CONTAINER_1 = {"prt:LinLay", "layW:mPrt", "layH:0", "orn:ver", "WeiSum:100",
            "wei:55", "mrg:0,0,0,5", "backC:WHITE"};
    private static final String[] PARAM_OBOLS_TITLE = {"prt:LinLay", "layW:mPrt", "layH:0", "wei:20",
            "grv:CHCV", "txtC:BLACK", "txtS:18"};
    private static final String[] PARAM_OBOLS_CONTAINER = {"prt:LinLay", "layW:mPrt", "layH:0","mrg:15,0,15,0",
            "orn:hor", "wei:20", "WeiSum:100"};
    private static final String[] PARAM_OBOLS_FL = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:10"};
    private static final String[] PARAM_OBOLS_ICON_1 = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:10"};
    private static final String[] PARAM_OBOLS_ICON_2 = {"prt:FrmLay", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_OBOLS_ICON_3 = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:10",
            "pad:5,5,5,5"};
    private static final String[] PARAM_OBOLS_TEXT_1 = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:40",
            "grv:LCV", "txtC:BLACK", "pad:5,5,5,5"};
    private static final String[] PARAM_OBOLS_TEXT_2 = {"prt:FrmLay", "layW:mPrt", "layH:mPrt",
            "grv:CHCV", "txtC:BLACK"};
    private static final String[] PARAM_OBOLS_TEXT_3 = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:30",
            "grv:CHCV", "txtC:BLACK"};
    //  BLOCK - 2
    private static final String[] PARAM_CONTAINER_2 = {"prt:LinLay", "layW:mPrt", "layH:0", "orn:ver", "WeiSum:100",
            "wei:45"};
    private static final String[] PARAM_ACCRUAL = {"prt:LinLay", "layW:mPrt", "layH:0", "wei:25",
            "backC:WHITE", "grv:CHCV", "txtC:BLACK"};
    private static final String[] PARAM_CONTAINER_2_LL = {"prt:LinLay", "layW:mPrt", "layH:0", "wei:40", "orn:hor",
            "WeiSum:3", "backC:WHITE"};
    private static final String[] PARAM_CONTAINER_2_FL = {"prt:LinLay", "layW:mPrt", "layH:0", "wei:25", "orn:hor",
            "backC:WHITE"};
    private static final String[] PARAM_INTERVAL_COINS = {"prt:LinLay", "layW:mPrt", "layH:0", "wei:10",
            "backC:WHITE", "grv:CHCV", "txtC:BLUE_ONE"};
    private static final String[] PARAM_CONTAINER_2_LL_RL = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1"};
    private static final String[] PARAM_INCREMENT_IV_MINUS = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "parW:50", "parH:50",
            "Rule:RIGHT", "Rule:CEN_VER", "pad:5,5,5,5"};
    private static final String[] PARAM_INCREMENT_IV_PLUS = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "parW:50", "parH:50",
            "Rule:LEFT", "Rule:CEN_VER","pad:5,5,5,5"};
    private static final String[] PARAM_INCREMENT_TV = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "parH:50",
            "Rule:CEN_PAR", "grv:CHCV", "txtC:GRAY_THREE", "txtS:18"};
    private static final String[] PARAM_INCREMENT_IV = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "Rule:CEN_PAR"};
    private static final String[] PARAM_OK_TV = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "parW:70", "parH:35", "mrg:5,0,5,5",
            "pad:5,5,5,5", "GRAV:RIGHT",  "grv:CHCV", "txtC:WHITE", "txtS:14", "backR:GRAY_THREE"};
    private static final String[] PARAM_INCREMENT_DATE = {"prt:FrmLay", "layW:mPrt", "layH:wCnt",  "mrg:75,0,75,0",
            "pad:5,5,5,5", "grv:TCH", "txtC:GREEN_ONE", "txtS:14"};
    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:1", "backC:GRAY_THREE"};


    //////////////                    BANK                    ////////////////////
    private static final String[] PARAM_BANK_CONTAINER = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,85,0,0",
            "orn:ver", "WeiSum:10", "backC:WHITE"};
    private static final String[] PARAM_BANK_CONTAINER_TOP = {"prt:LinLay", "layW:mPrt", "layH:0", "wei:7", "mrg:5,0,5,0",
            "orn:ver", "WeiSum:5", };
    private static final String[] PARAM_BANK_CONTAINER_BOTTOM = {"prt:LinLay", "layW:mPrt", "layH:0", "wei:3",
            "orn:ver", "WeiSum:10"};
    private static final String[] PARAM_BANK_CONTAINER_TOP_N = {"prt:LinLay", "layW:mPrt", "layH:0", "wei:1",
            "orn:hor", "WeiSum:5", };
    private static final String[] PARAM_BANK_TEXT_1 = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1",
            "txtC:BLACK", "grv:CHCV", "txtS:12"};
    private static final String[] PARAM_BANK_TEXT_N = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1",
            "txtC:GRAY_THREE", "grv:CHCV", "txtS:12"};
    private static final String[] PARAM_BANK_FL = {"prt:LinLay", "layW:0", "layH:wCnt", "wei:1"};
    private static final String[] PARAM_BANK_FL_IV = {"prt:FrmLay", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_BANK_FL_TV = {"prt:FrmLay", "layW:mPrt", "layH:mPrt",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_BANK_BOTTOM_TEXT = {"prt:LinLay", "layW:mPrt", "layH:0", "wei:2",
            "txtC:BLACK", "grv:CHCV"};

    private LinearLayout llCoinsMain, llBank;
    ///////  VARIABLE COINS MAIN  /////////
    private Resources res;
    private Context context;
    private LinearLayout llContainer1;
    private TextView tvCurrentBalanceCount, tvCurrentBalanceTime, tvSpentTodayCount, tvSpentTodayTime,
            tvPiggyCount, tvPiggyTime, tvBankCount, tvBankTime;
    private int incrementChecked = 1;
    private float coins = 0;
    private TextView tvIncrement, tvOk, tvIncrementDate;


    public CoinsView(Context context, Resources res) {
        super(context);
        this.context = context;
        this.res = res;


        //  COINS MAIN WINDOW
        onCoinsMain();

        //  BANK
        onBank();
        llBank.setVisibility(GONE);

    }
    public CoinsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public CoinsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CoinsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void onCoinsMain() {
        llCoinsMain = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER);
        this.addView(llCoinsMain);
        //  // BLOCK - 1 (Total Balance)
        llContainer1 = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_1);
        llCoinsMain.addView(llContainer1);

        /////////           OBOLS TITLE         /////////
        TextView tvObolsTitle = new CustomTextView().onCustom(context, PARAM_OBOLS_TITLE, res.getString(R.string.obols));
        llContainer1.addView(tvObolsTitle);
        /////////        TOTAL BALANCE        //////////////
        onContainer1(1);
        /////////        SPENT TODAY         /////////////
        onContainer1(2);
        /////////           PIGGY          ////////////
        onContainer1(3);
        /////////           BANK           /////////////
        onContainer1(4);
        //  BLOCK - 2 (Increment)
        LinearLayout llContainer2 = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_2);
        llCoinsMain.addView(llContainer2);

        View v2 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        llContainer2.addView(v2);

        TextView tvAccrual = new CustomTextView().onCustom(context, PARAM_ACCRUAL, res.getString(R.string.accrual));
        llContainer2.addView(tvAccrual);

        LinearLayout llIncrement = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_2_LL);
        llContainer2.addView(llIncrement);

        RelativeLayout rl1 = new CustomRelativeLayout().onCustom(context, PARAM_CONTAINER_2_LL_RL);
        llIncrement.addView(rl1);

        ImageView ivMinus = new CustomImageView().onCustom(context, PARAM_INCREMENT_IV_MINUS, R.mipmap.increment_minus);
        rl1.addView(ivMinus);
        ivMinus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (incrementChecked) {
                    case 1://  -0.5
                        coins -= 0.5;
                        break;
                    case 2://  -1
                        coins -= 1.0;
                        break;
                }
                onIncrementCoins();
            }
        });

        RelativeLayout rl2 = new CustomRelativeLayout().onCustom(context, PARAM_CONTAINER_2_LL_RL);
        llIncrement.addView(rl2);
        ImageView ivIncrement = new CustomImageView().onCustom(context, PARAM_INCREMENT_IV, R.mipmap.coins_gray);
        rl2.addView(ivIncrement);
        String increment = "0";
        if (coins > 0) { increment = String.valueOf(coins); }
        tvIncrement = new CustomTextView().onCustom(context, PARAM_INCREMENT_TV, increment);
        rl2.addView(tvIncrement);

        RelativeLayout rl3 = new CustomRelativeLayout().onCustom(context, PARAM_CONTAINER_2_LL_RL);
        llIncrement.addView(rl3);

        ImageView ivPlus = new CustomImageView().onCustom(context, PARAM_INCREMENT_IV_PLUS, R.mipmap.increment_plus);
        rl3.addView(ivPlus);
        ivPlus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (incrementChecked) {
                    case 1://  +0.5
                        coins += 0.5;
                        break;
                    case 2://  +1
                        coins += 1.0;
                        break;
                }
                onIncrementCoins();
            }
        });


        FrameLayout flIncrement = new CustomFrameLayout().onCustom(context, PARAM_CONTAINER_2_FL);
        llContainer2.addView(flIncrement);

        tvOk = new CustomTextView().onCustom(context, PARAM_OK_TV, "OK");
        flIncrement.addView(tvOk);
        tvOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coins == 0) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onMessageClick(res.getString(R.string.you_not_incremented_coins));
                    }
                    return;
                }
                if (onItemClickListener != null) {
                    //   int i = (int)(((10*coins)*20)/10);
                    int result = ((int)(10*coins)*20)/10;
                    onItemClickListener.onCoinsClick(result);
                    coins = 0;

                    tvIncrement.setTextColor(ColorsRGB.GRAY_THREE);
                    tvIncrement.setText("0");
                    tvIncrementDate.setText("");
                    onTextViewOkBackground(false);
                }
            }
        });
        tvIncrementDate = new CustomTextView().onCustom(context, PARAM_INCREMENT_DATE, null);
        flIncrement.addView(tvIncrementDate);

        TextView tvIntervalCoins = new CustomTextView().onCustom(context, PARAM_INTERVAL_COINS, res.getString(R.string.change_range_coins));
        llContainer2.addView(tvIntervalCoins);
        tvIntervalCoins.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                IncrementDialog dialog = new IncrementDialog(context, res, incrementChecked);
                dialog.show();
                dialog.setOnItemClickListener(new IncrementDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        incrementChecked = position;
                    }
                });
            }
        });

        View v3 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        llContainer2.addView(v3);

        //  Spannable
        String firstText = res.getString(R.string.coin_interval);
        String textFirst =  res.getString(R.string.coin_zero_five_min);
        int startIndex = 0;
        int endIndex = firstText.length();

        SpannableString spannableString = new SpannableString(firstText + "\n" + textFirst);
        //spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, 0);
        spannableString.setSpan(new ForegroundColorSpan(Color.rgb(152, 152, 152)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


    }

    private void onBank() {
        llBank = new CustomLinearLayout().onCustom(context, PARAM_BANK_CONTAINER);
        this.addView(llBank);

        ///////////            TOP            ////////////
        LinearLayout llTop = new CustomLinearLayout().onCustom(context, PARAM_BANK_CONTAINER_TOP);
        llBank.addView(llTop);

        LinearLayout llTop1 = new CustomLinearLayout().onCustom(context, PARAM_BANK_CONTAINER_TOP_N);
        llTop.addView(llTop1);

        String[] text = {"счет", "вид\nсчета", "вложено", "вознаграж\nдение", "осталось\nдней"};

        for (int i = 0; i < text.length; i++) {
            TextView tvText1 = new CustomTextView().onCustom(context, PARAM_BANK_TEXT_1, text[i]);
            llTop1.addView(tvText1);
        }

        String[] text1 = {"№3\n 20.10.2019", "x2 за 20 дней", "3", "+3", "10/20"};
        addBankList(llTop, text1, R.mipmap.coins_gold);

        String[] text2 = {"№2\n 30.10.2019", "x4 за 45 дней", "2", "+6", "44/45"};
        addBankList(llTop, text2, R.mipmap.coins_gold);

        String[] text3 = {"№1\n 30.10.2019", "x2 за 20 дней", "2", "+2", "20/20"};
        addBankList(llTop, text3, R.mipmap.coins_gold);

        String[] text4 = {"№4\n 05.10.2019", "x2 за 20 дней", "2", "+2", "срок истек"};
        addBankList(llTop, text4, R.mipmap.coins_gray);

        ///////////            BOTTOM            ////////////
        LinearLayout llBottom = new CustomLinearLayout().onCustom(context, PARAM_BANK_CONTAINER_BOTTOM);
        llBank.addView(llBottom);

        String textLOL = "Прибыль от вложений в банк по месяцам";
        TextView tvTextBank = new CustomTextView().onCustom(context, PARAM_BANK_BOTTOM_TEXT, textLOL);
        llBottom.addView(tvTextBank);


        BarChart barChart = new BarChart(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        params.setMargins(DimensionDP.sizeDP(30,context) ,0,DimensionDP.sizeDP(30, context),0);
        params.weight = 8;
        barChart.setLayoutParams(params);
        llBottom.addView(barChart);
        barChart.setScaleEnabled(false);
        barChart.setTouchEnabled(false);

        setDataBank(barChart);


    }
    public void onSelectLL(int numb) {
        llCoinsMain.setVisibility(GONE);
        llBank.setVisibility(GONE);
        switch (numb) {
            case 1:
                llCoinsMain.setVisibility(VISIBLE);
              //  if (onItemClickListener != null) { onItemClickListener.onSelectLLClick(numb); }
                break;
            case 3:
                llBank.setVisibility(VISIBLE);
             //   if (onItemClickListener != null) { onItemClickListener.onSelectLLClick(numb); }
                break;
        }
    }








    ///////  COINS MAIN  /////////
    private void onContainer1(int numb) {
        // tvCurrentBalanceCount, tvCurrentBalanceTime//, tvSpentTodayCount, tvSpentTodayTime,
        //    tvPiggyCount, tvPiggyTime, tvBankCount, tvBankTime;
        //  TextView tvCount, tvTime;
        int icon1 = 0;
        String text1 = null;
        switch (numb) {
            case 1:
                icon1 = R.mipmap.coins_gold;
                text1 = res.getString(R.string.current_balance);
                break;
            case 2:
                icon1 = R.mipmap.reports;
                text1 = res.getString(R.string.spent_today);
                break;
            case 3:
                icon1 = R.mipmap.piggy;
                text1 = res.getString(R.string.piggy_text);
                break;
            case 4:
                icon1 = R.mipmap.bank;
                text1 = res.getString(R.string.bank_text);
                break;
        }
        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM_OBOLS_CONTAINER);
        llContainer1.addView(linearLayout);

        ImageView ivIcon1 = new CustomImageView().onCustom(context, PARAM_OBOLS_ICON_1, icon1);
        linearLayout.addView(ivIcon1);

        TextView tvText1 = new CustomTextView().onCustom(context, PARAM_OBOLS_TEXT_1, text1);
        linearLayout.addView(tvText1);

        FrameLayout frameLayout = new CustomFrameLayout().onCustom(context, PARAM_OBOLS_FL);
        linearLayout.addView(frameLayout);

        ImageView ivIcon2 = new CustomImageView().onCustom(context, PARAM_OBOLS_ICON_2, R.mipmap.coins_gray);
        frameLayout.addView(ivIcon2);

        TextView tvCount = new CustomTextView().onCustom(context, PARAM_OBOLS_TEXT_2, null);//  Count
        frameLayout.addView(tvCount);

        TextView tvTime = new CustomTextView().onCustom(context, PARAM_OBOLS_TEXT_3, null);//  Time
        linearLayout.addView(tvTime);

        ImageView ivIcon3 = new CustomImageView().onCustom(context, PARAM_OBOLS_ICON_3, R.mipmap.red_right);
        linearLayout.addView(ivIcon3);
        ivIcon3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onObolsClick(numb);
                }
            }
        });
        switch (numb) {
            case 1:
                tvCurrentBalanceCount = tvCount;
                tvCurrentBalanceTime = tvTime;
                ivIcon3.setImageResource(0);
                ivIcon3.setOnClickListener(null);
                break;
            case 2:
                tvSpentTodayCount = tvCount;
                tvSpentTodayTime = tvTime;
                break;
            case 3:
                tvPiggyCount = tvCount;
                tvPiggyTime = tvTime;
                ivIcon3.setImageResource(0);
                ivIcon3.setOnClickListener(null);
                break;
            case 4:
                tvBankCount = tvCount;
                tvBankTime = tvTime;
                break;
        }



    }
    private void onIncrementCoins() {
        if (coins > 0) {
            int i = ((int)(10*coins)*20)/10;
            int hours =  i  / 60; //since both are ints, you get an int
            int minutes =  i % 60;
            String textFirst =  "";
            if (hours > 0) { textFirst += String.valueOf(hours) + res.getString(R.string.h); }
            if (minutes > 0) { textFirst += String.valueOf(minutes) + res.getString(R.string.min); }
            tvIncrement.setTextColor(ColorsRGB.GREEN_ONE);
            tvIncrement.setText("+" + String.valueOf(coins));
            tvIncrementDate.setText(textFirst);
            onTextViewOkBackground(true);
        } else if (coins == 0) {
            tvIncrement.setTextColor(ColorsRGB.GRAY_THREE);
            tvIncrement.setText("0");
            tvIncrementDate.setText("");
            onTextViewOkBackground(false);
        } else if (coins < 0) {

            int i = ((int)(10*coins)*20)/10;
            int hours =  i  / 60; //since both are ints, you get an int
            int minutes =  i % 60;

            String textFirst =  "";
            if (hours < 0) { textFirst += String.valueOf(hours) + res.getString(R.string.h); }
            if (minutes < 0) { textFirst += String.valueOf(minutes) + res.getString(R.string.min); }

            tvIncrement.setTextColor(ColorsRGB.GREEN_ONE);
            tvIncrement.setText(String.valueOf(coins));
            tvIncrementDate.setText(textFirst);
            onTextViewOkBackground(true);
        }
    }
    public void setTotalCoins(String totalCoins) {
        String coinsResult = "";
        int numbMinute = Integer.parseInt(totalCoins);//  RESULT COINS

        //  IF COINS EQUALS ==  0
        String parseResult = "";
        int first = numbMinute / 20;
        int second = (numbMinute % 20) / 2;
        if (numbMinute > 0 && numbMinute < 10) {
            first = numbMinute / 10;
            second = (numbMinute % 20) / 2;
            //android.util.Log.e("Tag", " Msg");
        }
        if (first == 0 && second > 0) {
            parseResult = "0." + String.valueOf(second);
        }
        if (first > 0 && second > 0) {
            parseResult = String.valueOf(first) + "." + String.valueOf(second);
        }
        if (first > 0 && second == 0) {
            parseResult = String.valueOf(first);
            second = numbMinute % 10;
            if (second > 0) { parseResult += "." + String.valueOf(second); }
        }
        if (first == 0 && second == 0) {
            parseResult = "0";
        }
        coinsResult = parseResult;

        int resultCoins = numbMinute;

        float result = ((int)(10*resultCoins)/20)/10;
        int hours = numbMinute / 60; //since both are ints, you get an int
        int minutes = numbMinute % 60;

        tvCurrentBalanceCount.setText(coinsResult);

        String dateCoins = "";
        if (hours > 0) { dateCoins += String.valueOf(hours) + res.getString(R.string.h); }
        if (minutes > 0) { dateCoins += String.valueOf(minutes) + res.getString(R.string.min); }
        if (numbMinute == 0 && hours == 0 && minutes == 0) { dateCoins = "0" + res.getString(R.string.min); }
        tvCurrentBalanceTime.setText(dateCoins);
    }
    public void setTotalCoinsBank(String totalCoins) {
        String coinsResult = "";
        int numbMinute = Integer.parseInt(totalCoins);//  RESULT COINS

        //  IF COINS EQUALS ==  0
        String parseResult = "";
        int first = numbMinute / 20;
        int second = (numbMinute % 20) / 2;
        if (numbMinute > 0 && numbMinute < 10) {
            first = numbMinute / 10;
            second = (numbMinute % 20) / 2;
            //android.util.Log.e("Tag", " Msg");
        }
        if (first == 0 && second > 0) {
            parseResult = "0." + String.valueOf(second);
        }
        if (first > 0 && second > 0) {
            parseResult = String.valueOf(first) + "." + String.valueOf(second);
        }
        if (first > 0 && second == 0) {
            parseResult = String.valueOf(first);
            second = numbMinute % 10;
            if (second > 0) { parseResult += "." + String.valueOf(second); }
        }
        if (first == 0 && second == 0) {
            parseResult = "0";
        }
        coinsResult = parseResult;

        int resultCoins = numbMinute;

        float result = ((int)(10*resultCoins)/20)/10;
        int hours = numbMinute / 60; //since both are ints, you get an int
        int minutes = numbMinute % 60;

        tvBankCount.setText(coinsResult);

        String dateCoins = "";
        if (hours > 0) { dateCoins += String.valueOf(hours) + res.getString(R.string.h); }
        if (minutes > 0) { dateCoins += String.valueOf(minutes) + res.getString(R.string.min); }
        if (numbMinute == 0 && hours == 0 && minutes == 0) { dateCoins = "0" + res.getString(R.string.min); }
        tvBankTime.setText(dateCoins);
    }
    public void setTotalCoinsPiggy(String totalCoins) {
        String coinsResult = "";
        int numbMinute = Integer.parseInt(totalCoins);//  RESULT COINS

        //  IF COINS EQUALS ==  0
        String parseResult = "";
        int first = numbMinute / 20;
        int second = (numbMinute % 20) / 2;
        if (numbMinute > 0 && numbMinute < 10) {
            first = numbMinute / 10;
            second = (numbMinute % 20) / 2;
            //android.util.Log.e("Tag", " Msg");
        }
        if (first == 0 && second > 0) {
            parseResult = "0." + String.valueOf(second);
        }
        if (first > 0 && second > 0) {
            parseResult = String.valueOf(first) + "." + String.valueOf(second);
        }
        if (first > 0 && second == 0) {
            parseResult = String.valueOf(first);
            second = numbMinute % 10;
            if (second > 0) { parseResult += "." + String.valueOf(second); }
        }
        if (first == 0 && second == 0) {
            parseResult = "0";
        }
        coinsResult = parseResult;

        int resultCoins = numbMinute;

        float result = ((int)(10*resultCoins)/20)/10;
        int hours = numbMinute / 60; //since both are ints, you get an int
        int minutes = numbMinute % 60;

        tvPiggyCount.setText(coinsResult);

        String dateCoins = "";
        if (hours > 0) { dateCoins += String.valueOf(hours) + res.getString(R.string.h); }
        if (minutes > 0) { dateCoins += String.valueOf(minutes) + res.getString(R.string.min); }
        if (numbMinute == 0 && hours == 0 && minutes == 0) { dateCoins = "0" + res.getString(R.string.min); }
        tvPiggyTime.setText(dateCoins);
    }
    public void setTotalCoinsSpentToday(String totalCoins) {
        String coinsResult = "";
        int numbMinute = Integer.parseInt(totalCoins);//  RESULT COINS

        //  IF COINS EQUALS ==  0
        String parseResult = "";
        int first = numbMinute / 20;
        int second = (numbMinute % 20) / 2;
        if (numbMinute > 0 && numbMinute < 10) {
            first = numbMinute / 10;
            second = (numbMinute % 20) / 2;
            //android.util.Log.e("Tag", " Msg");
        }
        if (first == 0 && second > 0) {
            parseResult = "0." + String.valueOf(second);
        }
        if (first > 0 && second > 0) {
            parseResult = String.valueOf(first) + "." + String.valueOf(second);
        }
        if (first > 0 && second == 0) {
            parseResult = String.valueOf(first);
            second = numbMinute % 10;
            if (second > 0) { parseResult += "." + String.valueOf(second); }
        }
        if (first == 0 && second == 0) {
            parseResult = "0";
        }
        coinsResult = parseResult;

        int resultCoins = numbMinute;

        float result = ((int)(10*resultCoins)/20)/10;
        int hours = numbMinute / 60; //since both are ints, you get an int
        int minutes = numbMinute % 60;

        tvSpentTodayCount.setText(coinsResult);

        String dateCoins = "";
        if (hours > 0) { dateCoins += String.valueOf(hours) + res.getString(R.string.h); }
        if (minutes > 0) { dateCoins += String.valueOf(minutes) + res.getString(R.string.min); }
        if (numbMinute == 0 && hours == 0 && minutes == 0) { dateCoins = "0" + res.getString(R.string.min); }
        tvSpentTodayTime.setText(dateCoins);
    }
    private void onTextViewOkBackground(boolean backR) {
        if (backR) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tvOk.setBackgroundDrawable(new GetButtonRipple()
                        .getRipple(30f, 2, ColorsRGB.GRAY_THREE,
                                GetColorInText.getIntColor("RED_ONE"),//  First Color
                                ColorsRGB.BLUE_ONE));//  Second Color
            } else {
                tvOk.setBackgroundColor(GetColorInText.getIntColor("RED_ONE"));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tvOk.setBackgroundDrawable(new GetButtonRipple()
                        .getRipple(30f, 2, ColorsRGB.GRAY_THREE,
                                GetColorInText.getIntColor("GRAY_THREE"),//  First Color
                                ColorsRGB.BLUE_ONE));//  Second Color
            } else {
                tvOk.setBackgroundColor(GetColorInText.getIntColor("GRAY_THREE"));
            }
        }
    }


    ////////    BANK    //////////
    private void addBankList(LinearLayout llTop, String[] texts, int image) {
        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM_BANK_CONTAINER_TOP_N);
        llTop.addView(linearLayout);

        TextView tv1 = new CustomTextView().onCustom(context, PARAM_BANK_TEXT_N, texts[0]);
        linearLayout.addView(tv1);

        TextView tv2 = new CustomTextView().onCustom(context, PARAM_BANK_TEXT_N, texts[1]);
        linearLayout.addView(tv2);

        ///////////
        FrameLayout frameLayout = new CustomFrameLayout().onCustom(context, PARAM_BANK_FL);
        linearLayout.addView(frameLayout);

        ImageView iv = new CustomImageView().onCustom(context, PARAM_BANK_FL_IV, image);
        frameLayout.addView(iv);

        TextView tv3 = new CustomTextView().onCustom(context, PARAM_BANK_FL_TV, texts[2]);
        frameLayout.addView(tv3);
        ///////////

        TextView tv4 = new CustomTextView().onCustom(context, PARAM_BANK_TEXT_N, texts[3]);
        linearLayout.addView(tv4);

        TextView tv5 = new CustomTextView().onCustom(context, PARAM_BANK_TEXT_N, texts[4]);
        linearLayout.addView(tv5);


    }
    public void setDataBank(BarChart barChart) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0,7));
        barEntries.add(new BarEntry(1,7));
        barEntries.add(new BarEntry(2,7));
        barEntries.add(new BarEntry(3,7));
        barEntries.add(new BarEntry(4,7));
        barEntries.add(new BarEntry(5,6.5f));


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
        String[] month = {"май", "июн", "июл", "авг", "сен", "окт"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(month));

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
      //  tvWeekText1.setText(res.getString(R.string.reports_week_text_one) + "3.5" + res.getString(R.string.obols));
      //  tvWeekText2.setText(res.getString(R.string.reports_week_text_two) + "1.5" + res.getString(R.string.times));


        barChart.getBarData().setDrawValues(false);

        LimitLine ll = new LimitLine(5.0f, "");
        ll.setLineWidth(1f);//  Line size height
        ll.enableDashedLine(20f, 5f, 0f);//  Line length and space
        //     ll.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);

        barChart.getAxisLeft().addLimitLine(ll);//  Add LimitLine to BarChart
    }

}
