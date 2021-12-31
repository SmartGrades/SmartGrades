package kz.tech.smartgrades.parents_module.settings.locality;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RecyclerViewParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;
import kz.tech.smartgrades.root.ui.animation.GetButtonRipple;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;
import kz.tech.smartgrades.root.var_resources.VarID;


public class LocalityItemList extends RelativeLayout {
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt", "parH:50"};
    private static final String[] PARAM_IMAGE = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "parW:50", "parH:50",
            "pad:5,5,5,5", "Rule:LEFT"};
    private static final String[] PARAM_TEXT = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:50,0,0,0",
            "grv:LCV", "pad:5,5,5,5", "txtC:BLACK", "Rule:LEFT"};
    private static final String[] PARAM_VIEW_TOP = {"prt:RelLay", "layW:wCnt", "layH:mPrt", "parH:1",
            "backC:GRAY_THREE", "Rule:TOP", "VIS:GONE"};
    private static final String[] PARAM_VIEW_BOTTOM = {"prt:RelLay", "layW:wCnt", "layH:mPrt", "parH:1",
            "backC:GRAY_THREE", "Rule:BOTTOM"};
    public LocalityItemList(Context context) {
        super(context);
        this.setLayoutParams(new RecyclerViewParams().getParams(context, PARAM));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setBackgroundDrawable(new GetButtonRipple()
                    .getRipple(0f, 0, ColorsRGB.GRAY_THREE,
                            GetColorInText.getIntColor("WHITE"),//  First Color
                            ColorsRGB.BLUE_ONE));//  Second Color
        } else {
            this.setBackgroundColor(GetColorInText.getIntColor("WHITE"));
        }

        View vTop = new CustomView().onCustom(context, PARAM_VIEW_TOP);
        this.addView(vTop);
        vTop.setId(VarID.ID_V_TOP_LOCALITY);

        ImageView ivImage = new CustomImageView().onCustom(context, PARAM_IMAGE, 0);
        this.addView(ivImage);
        ivImage.setId(VarID.ID_IV_IMAGE_LOCALITY);

        TextView tvText = new CustomTextView().onCustom(context, PARAM_TEXT, null);
        this.addView(tvText);
        tvText.setId(VarID.ID_TV_TEXT_LOCALITY);

        View vBottom = new CustomView().onCustom(context, PARAM_VIEW_BOTTOM);
        this.addView(vBottom);
        vBottom.setId(VarID.ID_V_BOTTOM_LOCALITY);



    }

    public LocalityItemList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LocalityItemList(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LocalityItemList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
