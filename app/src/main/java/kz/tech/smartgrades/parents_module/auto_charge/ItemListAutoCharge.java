package kz.tech.smartgrades.parents_module.auto_charge;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RecyclerViewParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;
import kz.tech.smartgrades.root.var_resources.VarID;

public class ItemListAutoCharge extends RelativeLayout {
    private static final String[] PARAM = {"layW:mPrt", "layH:wCnt", "parH:50"};
    private static final String[] PARAM_DAY = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:100", "parH:50",
            "Rule:LEFT", "grv:CHCV", "txtC:BLACK"};

    private static final String[] PARAM_MOVE = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "parW:40", "parH:40", "mrg:0,0,5,0",
            "Rule:RIGHT", "Rule:CEN_VER", "pad:5,5,5,5"};

    private static final String[] PARAM_TIME = {"prt:RelLay", "layW:wCnt", "layH:wCnt",  "parH:50",
            "Rule:CEN_PAR", "pad:5,5,5,5", "grv:CHCV", "txtC:BLACK"};

    private static final String[] PARAM_VIEW_LINE_5 = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:1", "mrg:0,0,0,1",
            "backC:GRAY_THREE", "Rule:BOTTOM"};
    public ItemListAutoCharge(Context context) {
        super(context);
        this.setLayoutParams(new RecyclerViewParams().getParams(context, PARAM));
        this.setBackgroundColor(Color.rgb(255, 255, 255));

        TextView tvDay = new CustomTextView().onCustom(context, PARAM_DAY, null);
        this.addView(tvDay);
        tvDay.setId(VarID.ID_TV_AUTO_CHARGE_DAY);

        ImageView ivMove = new CustomImageView().onCustom(context, PARAM_MOVE, R.mipmap.move_hor_red);
        this.addView(ivMove);
        ivMove.setId(VarID.ID_IV_AUTO_CHARGE_MOVE);

        TextView tvTime = new CustomTextView().onCustom(context, PARAM_TIME, null);
        this.addView(tvTime);
        tvTime.setId(VarID.ID_TV_AUTO_CHARGE_TIME);

        View v5 = new CustomView().onCustom(context, PARAM_VIEW_LINE_5);
        this.addView(v5);


    }

    public ItemListAutoCharge(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemListAutoCharge(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ItemListAutoCharge(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
