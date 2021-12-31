package kz.tech.smartgrades.parents_module.devices;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomRecyclerView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class DevicesView extends FrameLayout {
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_DEVICE_USER_LL = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:40", "mrg:0,5,0,0",
            "orn:hor", "WeiSum:2"};
    private static final String[] PARAM_DEVICE_USER_TV = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1",
            "grv:CHCV", "txtC:GRAY_ONE", "txtS:14"};
    private static final String[] PARAM_RV = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:0,50,0,0",
            "hfs:true", "layMan:llm"};
    private RecyclerView recyclerView;
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
    public DevicesView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new RelativeLayoutParams().getParams(context, PARAM));

        LinearLayout llDeviceUser = new CustomLinearLayout().onCustom(context, PARAM_DEVICE_USER_LL);
        this.addView(llDeviceUser);

        TextView tvDevice = new CustomTextView().onCustom(context, PARAM_DEVICE_USER_TV, res.getString(R.string.devices));
        llDeviceUser.addView(tvDevice);

        TextView tvUser = new CustomTextView().onCustom(context, PARAM_DEVICE_USER_TV, res.getString(R.string.user));
        llDeviceUser.addView(tvUser);

        recyclerView = new CustomRecyclerView().onCustom(context, PARAM_RV);
        this.addView(recyclerView);
    }

    public DevicesView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DevicesView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DevicesView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
