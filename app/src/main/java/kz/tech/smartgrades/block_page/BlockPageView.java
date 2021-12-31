package kz.tech.smartgrades.block_page;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.var_resources.VarID;

public class BlockPageView extends RelativeLayout {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_TOOLBAR = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:56", "backC:WHITE"};
    private static final String[] PARAM_TOOLBAR_RL = {"prt:Toolbar", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_TITLE = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:56,0,56,0",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_AVATAR = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:56", "parH:56", "pad:5,5,5,5",
            "BordC:GRAY_THREE", "BordW:2", "img:avatar", "Rule:RIGHT"};

    private static final String[] PARAM_CONTAINER = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,61,0,0", "backC:WHITE"};

    private static final String[] PARAM_CONTAINER_CENTER_LL = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "orn:ver", "Rule:CEN_PAR"};
    private static final String[] PARAM_TEXT_CENTER = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:56",
            "txtC:BLACK", "grv:CHCV", "txtS:18", "Rule:BLOCK"};
    private static final String[] PARAM_IMAGE_CENTER = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parW:100", "parH:100",
            "Rule:CEN_PAR"};


    private static final String[] PARAM_CONTAINER_BOTTOM_LL = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "mrg:0,0,0,30",
            "orn:ver", "Rule:BOTTOM"};
    private static final String[] PARAM_TEXT_BOTTOM = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:56",
            "txtC:BLUE_ONE", "grv:CHCV"};
    private static final String[] PARAM_IMAGE_BOTTOM = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50"};

    private CircleImageView civAvatar;
    private Context context;

    public BlockPageView(Context context) {
        super(context);
        this.context = context;
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        Toolbar toolbar = new CustomToolbar().onCustom(context, PARAM_TOOLBAR);
        this.addView(toolbar);
        RelativeLayout rlToolbar = new CustomRelativeLayout().onCustom(context, PARAM_TOOLBAR_RL);
        toolbar.addView(rlToolbar);

        TextView tvTitle = new CustomTextView().onCustom(context, PARAM_TITLE, getResources().getString(R.string.device_blocked));
        rlToolbar.addView(tvTitle);

        civAvatar = new CustomCircleImageView().onCustom(context, PARAM_AVATAR);
        rlToolbar.addView(civAvatar);
        civAvatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick();
                }

            }
        });


        RelativeLayout rlContainer = new CustomRelativeLayout().onCustom(context, PARAM_CONTAINER);
        this.addView(rlContainer);

        LinearLayout llCenter = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_CENTER_LL);
        rlContainer.addView(llCenter);

        TextView tvCenter = new CustomTextView().onCustom(context, PARAM_TEXT_CENTER, getResources().getString(R.string.coins_ends));
        this.addView(tvCenter);

        ImageView ivCenter = new CustomImageView().onCustom(context, PARAM_IMAGE_CENTER, R.mipmap.coins_off);
        this.addView(ivCenter);
        ivCenter.setId(VarID.ID_IV_BLOCK);

        LinearLayout llBottom = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_BOTTOM_LL);
        rlContainer.addView(llBottom);
        llBottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ImageView ivBottom = new CustomImageView().onCustom(context, PARAM_IMAGE_BOTTOM, R.mipmap.msg_plus);
        llBottom.addView(ivBottom);
        TextView tvBottom = new CustomTextView().onCustom(context, PARAM_TEXT_BOTTOM, getResources().getString(R.string.parent_to_replenish));
        llBottom.addView(tvBottom);


    }

    public BlockPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlockPageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BlockPageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setAvatar(String image) {
        //Picasso.with(context).load(image).fit().centerInside().into(civAvatar);
    }
}
