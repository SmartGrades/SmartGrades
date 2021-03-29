package kz.tech.smartgrades.parents_module.about_app;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomScrollView;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;

public class AboutAppView extends RelativeLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onFacebookClick();
        void onInstagramClick();
        void onTelegramClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_CONTAINER_SV = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:0,5,0,120"};
    private static final String[] PARAM_CONTAINER_LL = {"prt:ScrLay", "layW:mPrt", "layH:mPrt", "orn:ver", "backC:WHITE"};
    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:1", "backC:GRAY_THREE"};
    private static final String[] PARAM_DESCRIPTION = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50", "mrg:5,0,5,0", "pad:5,5,5,5",
            "txtC:GREEN_ONE", "grv:CHCV", "txtS:14"};
    private static final String[] PARAM_CONTENT = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "pad:5,5,5,5", "mrg:5,0,5,0",
            "txtC:BLACK", "grv:CHCV"};
    private static final String[] PARAM_SUB_DESCRIPTION = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:5,0,5,0", "pad:5,5,5,5",
            "txtC:GREEN_ONE", "grv:CHCV"};
    private static final String[] PARAM_WE_SOCIAL = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:40", "mrg:0,0,0,80",
            "txtC:BLUE_ONE", "grv:CHCV", "Rule:BOTTOM", "backC:WHITE"};
    private static final String[] PARAM_BOTTOM_CONTAINER = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:80", "pad:60,0,60,0",
            "orn:hor", "WeiSum:3", "backC:WHITE", "Rule:BOTTOM"};
    private static final String[] PARAM_SOCIAL_IMAGE = {"prt:LinLay", "layW:0", "layH:wCnt", "parH:50", "wei:1"};
    public AboutAppView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new RelativeLayoutParams().getParams(context, PARAM));

        ScrollView scrollView = new CustomScrollView().onCustom(context, PARAM_CONTAINER_SV);
        this.addView(scrollView);

        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        scrollView.addView(linearLayout);

        View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v1);

        TextView tvDescription = new CustomTextView().onCustom(context, PARAM_DESCRIPTION, res.getString(R.string.description));
        linearLayout.addView(tvDescription);

        TextView tvContent = new CustomTextView().onCustom(context, PARAM_CONTENT, getResources().getString(R.string.about_app_description));
        linearLayout.addView(tvContent);

        TextView tvSubDescription = new CustomTextView().onCustom(context, PARAM_SUB_DESCRIPTION, getResources().getString(R.string.about_app_sub_description));
        linearLayout.addView(tvSubDescription);

        //  BOTTOM
        TextView tvWeSocial = new CustomTextView().onCustom(context, PARAM_WE_SOCIAL, res.getString(R.string.we_are_in_social_networks));
        this.addView(tvWeSocial);

        LinearLayout llBottom = new CustomLinearLayout().onCustom(context, PARAM_BOTTOM_CONTAINER);
        this.addView(llBottom);

        ImageView ivFacebook = new CustomImageView().onCustom(context, PARAM_SOCIAL_IMAGE, R.mipmap.facebook);
        llBottom.addView(ivFacebook);
        ivFacebook.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onFacebookClick();
                }
            }
        });
        ImageView ivInstagram = new CustomImageView().onCustom(context, PARAM_SOCIAL_IMAGE, R.mipmap.instagram);
        llBottom.addView(ivInstagram);
        ivInstagram.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onInstagramClick();
                }
            }
        });
        ImageView ivTelegram = new CustomImageView().onCustom(context, PARAM_SOCIAL_IMAGE, R.mipmap.telegram);
        llBottom.addView(ivTelegram);
        ivTelegram.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onTelegramClick();
                }
            }
        });
    }
    public AboutAppView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public AboutAppView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AboutAppView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
