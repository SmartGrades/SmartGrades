package kz.tech.smartgrades.childs_module.custom_recycler_grid_layout;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;

public class MyGrid extends LinearLayout {
    private static final String[] PARAM = {"layW:mPrt", "layH:wCnt", "mrg:5,5,5,5"};

    private Context context;
    private int coins = 0;
    private int countCycle = 0;
    private int countVerticalCycle = 0;
    public MyGrid(Context context, String count) {
        super(context);
        this.context = context;
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        this.setOrientation(VERTICAL);
        int numbMinute = Integer.parseInt(count);
        if (numbMinute > 0) {
            coins = ((int) (10 * numbMinute) / 20) / 10;

            if (numbMinute < 61) {
                onFirst(numbMinute);
            } else if (numbMinute > 61 && numbMinute < 121) {
                onSecond(numbMinute);
            } else if (numbMinute > 121 && numbMinute < 181) {
                onThird(numbMinute);
            } else if (numbMinute > 181) {
                onFour(numbMinute);
            }
        }

    }
    public MyGrid(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public MyGrid(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyGrid(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void onFirst(int numbMinute) {
        String[] PARAM_LL_1 = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "orn:hor", "GRAV:HOR"};
        String[] PARAM_IV_1 = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "parW:40", "parH:40", "pad:1,1,1,1"};
        LinearLayout ll = new CustomLinearLayout().onCustom(context, PARAM_LL_1);
        this.addView(ll);
        for (int i = 0; i < coins; i++) {
            ImageView iv = new CustomImageView().onCustom(context, PARAM_IV_1, R.mipmap.coins_gold);
            ll.addView(iv);
        }
    }
    private void onSecond(int numbMinute) {
        String[] PARAM_LL_1 = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "orn:hor", "mrg:0,0,5,0"};
        String[] PARAM_LL_2 = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "orn:hor", "mrg:5,0,0,0"};
        String[] PARAM_IV_1 = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "parW:40", "parH:40", "pad:1,1,1,1"};
        String[] PARAM_IV_2 = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "parW:40", "parH:40", "pad:1,1,1,1"};

        this.setOrientation(HORIZONTAL);
        int lol = coins - 3;
        this.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout ll1 = new CustomLinearLayout().onCustom(context, PARAM_LL_1);
        this.addView(ll1);
        for (int i = 0; i < 3; i++) {
            ImageView iv = new CustomImageView().onCustom(context, PARAM_IV_1, R.mipmap.coins_gold);
            ll1.addView(iv);
        }

        LinearLayout ll2 = new CustomLinearLayout().onCustom(context, PARAM_LL_2);
        this.addView(ll2);
        for (int j = 0; j < lol; j++) {
            ImageView iv = new CustomImageView().onCustom(context, PARAM_IV_2, R.mipmap.coins_gold);
            ll2.addView(iv);
        }
    }
    private void onThird(int numbMinute) {
        String[] PARAM_LL_1 = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "orn:hor", "mrg:0,0,5,0"};
        String[] PARAM_LL_2 = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "orn:hor", "mrg:5,0,5,0"};
        String[] PARAM_LL_3 = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "orn:hor", "mrg:5,0,0,0"};
        String[] PARAM_IV = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "parW:35", "parH:35", "pad:1,1,1,1"};

        this.setOrientation(HORIZONTAL);
        int lol = coins - 6;
        this.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout ll1 = new CustomLinearLayout().onCustom(context, PARAM_LL_1);
        this.addView(ll1);
        for (int i = 0; i < 3; i++) {
            ImageView iv = new CustomImageView().onCustom(context, PARAM_IV, R.mipmap.coins_gold);
            ll1.addView(iv);
        }

        LinearLayout ll2 = new CustomLinearLayout().onCustom(context, PARAM_LL_2);
        this.addView(ll2);
        for (int j = 0; j < 3; j++) {
            ImageView iv = new CustomImageView().onCustom(context, PARAM_IV, R.mipmap.coins_gold);
            ll2.addView(iv);
        }

        LinearLayout ll3 = new CustomLinearLayout().onCustom(context, PARAM_LL_3);
        this.addView(ll3);
        for (int l = 0; l < lol; l++) {
            ImageView iv = new CustomImageView().onCustom(context, PARAM_IV, R.mipmap.coins_gold);
            ll3.addView(iv);
        }

    }
    private void onFour(int numbMinute) {
        String[] PARAM_COLUMNS_VERTICAL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "orn:hor", "GRAV:HOR"};
        String[] PARAM_LL_1 = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "orn:hor", "mrg:0,0,5,0"};
        String[] PARAM_LL_2 = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "orn:hor", "mrg:5,0,5,0"};
        String[] PARAM_LL_3 = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "orn:hor", "mrg:5,0,0,0"};
        String[] PARAM_IV = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "parW:35", "parH:35", "pad:1,1,1,1"};


        int columnsVertical = coins / 3 / 3;//  3
        int columnsHorizontal = coins / 3;//  9
        countCycle = coins;//



        for (int i = 0; i < columnsVertical; i++) {
            LinearLayout llColumnsVertical = new CustomLinearLayout().onCustom(context, PARAM_COLUMNS_VERTICAL);
            this.addView(llColumnsVertical);

            LinearLayout ll1 = new CustomLinearLayout().onCustom(context, PARAM_LL_1);
            llColumnsVertical.addView(ll1);
            int firstCycle = getNumbCycle();
            if (firstCycle == 0) return;
            for (int j = 0; j < firstCycle; j++) {
                ImageView iv = new CustomImageView().onCustom(context, PARAM_IV, R.mipmap.coins_gold);
                ll1.addView(iv);
            }

            LinearLayout ll2 = new CustomLinearLayout().onCustom(context, PARAM_LL_2);
            llColumnsVertical.addView(ll2);
            int secondCycle = getNumbCycle();
            if (secondCycle == 0) return;
            for (int l = 0; l < secondCycle; l++) {
                ImageView iv = new CustomImageView().onCustom(context, PARAM_IV, R.mipmap.coins_gold);
                ll2.addView(iv);
            }

            LinearLayout ll3 = new CustomLinearLayout().onCustom(context, PARAM_LL_3);
            llColumnsVertical.addView(ll3);
            int thirdCycle = getNumbCycle();
            if (thirdCycle == 0) return;
            for (int k = 0; k < thirdCycle; k++) {
                ImageView iv = new CustomImageView().onCustom(context, PARAM_IV, R.mipmap.coins_gold);
                ll3.addView(iv);
            }
        }

        android.util.Log.e("Tag", " Msg");

    }
    private int getNumbCycle() {
        if (countCycle >= 3) {
            countCycle = countCycle - 3;
            return 3;
        } else if (countCycle >= 2) {
            countCycle = countCycle - 2;
            return 2;
        } else if (countCycle >= 1) {
            countCycle = countCycle - 1;
            return 1;
        }
        return 0;
    }
}
