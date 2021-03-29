package kz.tech.smartgrades.parents_module.devices;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;
import kz.tech.smartgrades.root.var_resources.VarID;


public class DeviceGroupItemList extends LinearLayout {
    private static final String[] PARAM = {"layW:mPrt", "layH:wCnt"};
    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:1", "backC:GRAY_THREE"};
    private static final String[] PARAM_LL_HOR = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:60", "mrg:0,20,0,20",
            "orn:hor" , "WeiSum:20"};

    private static final String[] PARAM_DEVICE_ICON = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:4"};
    private static final String[] PARAM_DEVICE_NAME = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:6",
            "txtC:BLUE_ONE", "grv:LCV"};
    private static final String[] PARAM_LOCK_STATUS = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:3",
            "GRAV:VER"};
    private static final String[] PARAM_ONLINE_STATUS = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1", "parH:30",
             "GRAV:VER"};
    private static final String[] PARAM_USER_NAME = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:4",
            "txtC:GRAY_THREE", "grv:CHCV"};
    private static final String[] PARAM_CHANGE_USER = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:2",
            "pad:5,5,5,5"};
    public DeviceGroupItemList(Context context) {
        super(context);
        this.setLayoutParams(new LinearLayoutParams().getParams(context, PARAM));
        this.setOrientation(VERTICAL);
        this.setBackgroundColor(Color.rgb(255,255,255));

        View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        this.addView(v1);
        v1.setId(VarID.ID_V_TOP_DEVICE);
        v1.setVisibility(GONE);

        LinearLayout llHOR = new CustomLinearLayout().onCustom(context, PARAM_LL_HOR);
        this.addView(llHOR);

        ImageView ivDeviceIcon = new CustomImageView().onCustom(context, PARAM_DEVICE_ICON, 0);
        llHOR.addView(ivDeviceIcon);
        ivDeviceIcon.setId(VarID.ID_IV_DEVICE_ICON_DEVICE);

        TextView tvDeviceName = new CustomTextView().onCustom(context, PARAM_DEVICE_NAME, null);
        llHOR.addView(tvDeviceName);
        tvDeviceName.setId(VarID.ID_TV_DEVICE_NAME_DEVICE);

        ImageView ivLockStatus = new CustomImageView().onCustom(context, PARAM_LOCK_STATUS, 0);
        llHOR.addView(ivLockStatus);
        ivLockStatus.setId(VarID.ID_IV_LOCK_STATUS_DEVICE);

        ImageView ivOnlineStatus = new CustomImageView().onCustom(context, PARAM_ONLINE_STATUS, 0);
        llHOR.addView(ivOnlineStatus);
        ivOnlineStatus.setId(VarID.ID_IV_ONLINE_STATUS_DEVICE);

        TextView tvUserName = new CustomTextView().onCustom(context, PARAM_USER_NAME, null);
        llHOR.addView(tvUserName);
        tvUserName.setId(VarID.ID_TV_USER_NAME_DEVICE);

        ImageView ivChangeUser = new CustomImageView().onCustom(context, PARAM_CHANGE_USER, 0);
        llHOR.addView(ivChangeUser);
        ivChangeUser.setId(VarID.ID_IV_CHANGE_USER_DEVICE);

        View v2 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        this.addView(v2);
    }

    public DeviceGroupItemList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DeviceGroupItemList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DeviceGroupItemList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
