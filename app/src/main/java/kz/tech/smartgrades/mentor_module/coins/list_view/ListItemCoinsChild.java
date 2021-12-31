package kz.tech.smartgrades.mentor_module.coins.list_view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomHorizontalScrollView;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;
import kz.tech.smartgrades.root.var_resources.DimensionDP;

public class ListItemCoinsChild extends RelativeLayout {
    private static final String[] PARAM = {"layW:mPrt", "layH:wCnt"};
    private static final String[] PARAM_AVATAR = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "parW:49", "parH:49",
            "mrg:5,1,0,0", "ID:400101"};
    private static final String[] PARAM_NAME = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "parH:49",
            "mrg:55,1,0,0", "ID:400102", "grv:LCV", "backC:GREEN_ONE"};

    private static final String[] PARAM_VIEW_LINE = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "mrg:50,49,0,0",
            "parH:1", "backC:GRAY_THREE", "ID:400103"};

    private static final String[] PARAM_TODAY_COINS_FL = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "mrg:5,5,0,0",
            "parW:40", "parH:40", "ID:400104", "VIS:GONE", "RIGHT_OF:400102"};
    private static final String[] PARAM_TODAY_COINS_IV = {"prt:FrmLay", "layW:mPrt", "layH:mPrt",
            "ID:400105"};
    private static final String[] PARAM_TODAY_COINS_TV = {"prt:FrmLay", "layW:mPrt", "layH:mPrt",
            "txtC:GRAY_THREE", "grv:CHCV", "txtS:14", "ID:400106"};




    private static final String[] PARAM_CONTAINER = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "mrg:10,50,10,0",
            "orn:ver", "ID:400108", "VIS:GONE"};
    private static final String[] PARAM_CONTAINER_HSV = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "GRAV:RIGHT"};
    private static final String[] PARAM_CONTAINER_LL_1 = {"prt:HScrLay", "layW:mPrt", "layH:wCnt",
            "orn:hor", "ID:400109"};
    private static final String[] PARAM_CONTAINER_LL_2 = {"prt:HScrLay", "layW:mPrt", "layH:wCnt",
            "orn:hor", "ID:400110"};




    public ListItemCoinsChild(Context c) {
        super(c);
        this.setLayoutParams(new FrameLayoutParams().getParams(c, PARAM));

        CircleImageView civAvatar = new CustomCircleImageView().onCustom(c, PARAM_AVATAR);
        this.addView(civAvatar);

 //       TextView tvName = new CustomTextView().onCustom(c, PARAM_NAME, "");
        TextView tvName = new TextView(c);
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.height = DimensionDP.sizeDP(49, c);
        params.setMargins(DimensionDP.sizeDP(55, c), DimensionDP.sizeDP(1, c), 0, 0);
        tvName.setLayoutParams(params);
    //    tvName.setBackgroundColor(Color.CYAN);
      //  tvName.setText("suka");
        tvName.setId((int)400102);
     //   tvName.setPadding(0,0,DimensionDP.sizeDP(5, c), 0);
        tvName.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
        this.addView(tvName);

        FrameLayout flTodayCoins = new CustomFrameLayout().onCustom(c, PARAM_TODAY_COINS_FL);
        this.addView(flTodayCoins);

        ImageView ivTodayCoins = new CustomImageView().onCustom(c, PARAM_TODAY_COINS_IV, 0);
        flTodayCoins.addView(ivTodayCoins);

        TextView tvTodayCoins = new CustomTextView().onCustom(c, PARAM_TODAY_COINS_TV, null);
        flTodayCoins.addView(tvTodayCoins);

        View v1 = new CustomView().onCustom(c, PARAM_VIEW_LINE);
        this.addView(v1);

        LinearLayout llContainer = new CustomLinearLayout().onCustom(c, PARAM_CONTAINER);
        this.addView(llContainer);

        HorizontalScrollView hsvCoins = new CustomHorizontalScrollView().onCustom(c, PARAM_CONTAINER_HSV);
        llContainer.addView(hsvCoins);

        LinearLayout llCoins = new CustomLinearLayout().onCustom(c, PARAM_CONTAINER_LL_1);
        hsvCoins.addView(llCoins);

        HorizontalScrollView hsvContracts = new CustomHorizontalScrollView().onCustom(c, PARAM_CONTAINER_HSV);
        llContainer.addView(hsvContracts);

        LinearLayout llContracts = new CustomLinearLayout().onCustom(c, PARAM_CONTAINER_LL_2);
        hsvContracts.addView(llContracts);





    }

    public ListItemCoinsChild(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ListItemCoinsChild(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ListItemCoinsChild(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
