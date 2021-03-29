package kz.tech.smartgrades.childs_module.adapters;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomRecyclerView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;
import kz.tech.smartgrades.root.var_resources.VarID;

public class ItemListContracts extends LinearLayout {
    private static final String[] PARAM = {"mPrt:mPrt", "layH:wCnt"};
    private static final String[] PARAM_HOR = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "orn:hor", "WeiSum:4"};
    private static final String[] PARAM_RV = {"prt:LinLay", "layW:0", "layH:wCnt", "wei:3", "mrg:10,0,10,10",
            "hfs:true", "GridC:5", "layMan:glm"};
    private static final String[] PARAM_MSG_PLUS = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "GRAV:HOR", "mrg:0,0,0,10"};
    private static final String[] PARAM_PRESENT = {"prt:LinLay", "layW:0", "layH:wCnt", "layH:100",
            "wei:1", "GRAV:VER", "ImgType:CENTER_CROP"};
    private static final String[] PARAM_TEXT_COINS = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50", "pad:5,5,5,5",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:1", "mrg:20,15,20,15",
            "backC:GRAY_THREE"};
    public ItemListContracts(Context context) {
        super(context);
   //     this.setLayoutParams(new RecyclerViewParams().getParams(context, PARAM));
        this.setOrientation(VERTICAL);
    //    this.setBackgroundColor(Color.rgb(152,152,152));
        View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        this.addView(v1);

        TextView tvDescription = new CustomTextView().onCustom(context, PARAM_TEXT_COINS, null);
        this.addView(tvDescription);
        tvDescription.setId(VarID.ID_TV_DESCRIPTION_CHILD_MAIN);

        LinearLayout llHOR = new CustomLinearLayout().onCustom(context, PARAM_HOR);
        this.addView(llHOR);

        RecyclerView rvSquare = new CustomRecyclerView().onCustom(context, PARAM_RV);
        llHOR.addView(rvSquare);
        rvSquare.setId(VarID.ID_RV_SQUARE_CHILD_MAIN);

        ImageView ivPresent = new CustomImageView().onCustom(context, PARAM_PRESENT, R.mipmap.ball);
        llHOR.addView(ivPresent);
        ivPresent.setId(VarID.ID_IV_PRESENT_CHILD_MAIN);

        ImageView ivMsgOk = new CustomImageView().onCustom(context, PARAM_MSG_PLUS, R.mipmap.msg_ok);
        this.addView(ivMsgOk);


    }

    public ItemListContracts(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemListContracts(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ItemListContracts(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
/*
  public static final int ID_TV_DESCRIPTION_CHILD_MAIN = 595959;
    public static final int ID_RV_SQUARE_CHILD_MAIN = 585858;
    public static final int ID_IV_PRESENT_CHILD_MAIN = 575757;
 */