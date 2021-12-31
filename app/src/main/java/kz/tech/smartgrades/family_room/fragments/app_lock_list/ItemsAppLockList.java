package kz.tech.smartgrades.family_room.fragments.app_lock_list;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;
import kz.tech.smartgrades.root.var_resources.VarID;

public class ItemsAppLockList extends RelativeLayout {
    private static final String[] PARAM = {"layW:mPrt", "layH:wCnt"};
    private static final String[] PARAM_ICON = {"prt:RelLay", "layW:wCnt", "layH:wCnt",
            "parW:40", "parH:40", "Rule:LEFT", "pad:5,5,5,5"};
    private static final String[] PARAM_TEXT = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "mrg:40,0,40,0",
            "parH:40", "Rule:CEN_PAR", "pad:5,5,5,5", "grv:LCV"};
    private static final String[] PARAM_CHECK_BOX = {"prt:RelLay", "layW:wCnt", "layH:wCnt",
            "parW:40", "parH:40", "Rule:RIGHT", "pad:5,5,5,5"};
    private static final String[] PARAM_VIEW_LINE_TOP = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parH:1",
            "backC:GRAY_THREE", "VIS:GONE", "Rule:TOP"};
    private static final String[] PARAM_VIEW_LINE_BOTTOM = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parH:1",
            "backC:GRAY_THREE", "Rule:BOTTOM"};
    public ItemsAppLockList(@NonNull Context context) {
        super(context);
        this.setLayoutParams(new RelativeLayoutParams().getParams(context, PARAM));
        this.setBackgroundColor(Color.rgb(255,255,255));

        View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE_TOP);
        this.addView(v1);
        v1.setId(VarID.ID_IV_APP_LOCK_LIST_TOP);

        ImageView ivIcon = new CustomImageView().onCustom(context, PARAM_ICON, 0);
        this.addView(ivIcon);
        ivIcon.setId(VarID.ID_IV_APP_LOCK_LIST_ICON);

        TextView tvText = new CustomTextView().onCustom(context, PARAM_TEXT, null);
        this.addView(tvText);
        tvText.setId(VarID.ID_TV_APP_LOCK_LIST_TEXT);

        ImageView ivCheckBox = new CustomImageView().onCustom(context, PARAM_CHECK_BOX, 0);
        this.addView(ivCheckBox);
        ivCheckBox.setId(VarID.ID_IV_APP_LOCK_LIST_CHECK_BOX);

        View v2 = new CustomView().onCustom(context, PARAM_VIEW_LINE_BOTTOM);
        this.addView(v2);
    }


    public ItemsAppLockList(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemsAppLockList(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ItemsAppLockList(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
