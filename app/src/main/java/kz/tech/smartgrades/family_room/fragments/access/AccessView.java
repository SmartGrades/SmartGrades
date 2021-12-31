package kz.tech.smartgrades.family_room.fragments.access;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import kz.tech.esparta.R;
import kz.tech.smartgrades.family_room.fragments.child_access.ChildAccessView;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class AccessView extends RelativeLayout {
    private ChildAccessView.OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onBackButtonClick();
        void onServiceKillerClick();
        void onNextPageClick();
        void onMsgClick(String msg);
        void onTextBodyAccessClick(String textBody);
        void onPhoneClick();
        void onModelPhoneClick(String modelName);
    }
    public void setOnItemClickListener(ChildAccessView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_TOOLBAR = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:56", "backC:WHITE"};
    private static final String[] PARAM_TOOLBAR_RL = {"prt:Toolbar", "layW:mPrt", "layH:mPrt"};

    private static final String[] PARAM_BACK = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:56", "parH:56", "pad:5,5,5,5"};
    private static final String[] PARAM_TITLE = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:56,0,56,0", "pad:5,5,5,5",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_CONTAINER_LL = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "mrg:0,56,0,56",
            "orn:ver"};
    private static final String[] PARAM_CONTAINER_RL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:0,5,0,0",
            "backC:WHITE"};
    private static final String[] PARAM_ICON = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "mrg:5,5,5,5",
            "parW:40", "parH:40", "Rule:LEFT", "Rule:CEN_VER"};
    private static final String[] PARAM_TEXT = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "mrg:50,0,50,0",
            "Rule:CEN_PAR", "txtC:BLUE_ONE", "grv:CHCV"};
    private static final String[] PARAM_NEXT_PAGE = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "mrg:5,5,5,5",
            "parW:40", "parH:40", "Rule:RIGHT", "Rule:CEN_VER", "pad:5,5,5,5"};

    private static final String[] PARAM_CONTAINER_MODEL_PHONE_LL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:0,20,0,0",
            "orn:ver", "backC:WHITE"};
    private static final String[] PARAM_MODEL_PHONE_TITLE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:0,5,0,0",
            "txtC:GRAY_THREE", "grv:CHCV", "txtS:18", "pad:5,5,5,5"};
    private static final String[] PARAM_MODEL_PHONE_DESCRIPTION = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:0,5,0,0",
            "txtC:GRAY_THREE", "grv:CHCV", "txtS:14", "pad:5,5,5,5"};

    private static final String[] PARAM_MODEL_PHONE_ACCESS_TEXT = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:0,5,0,0",
            "backC:WHITE", "txtC:GRAY_THREE", "grv:CHCV", "txtS:14", "pad:5,5,5,5"};
    private Context context;
    private boolean isAccess = false;
    private ImageView ivServiceKiller, ivPhoneAccess;
    private Resources res;
    public AccessView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new RelativeLayoutParams().getParams(context, PARAM));
        this.context = context;
        this.res = res;

        Toolbar toolbar = new CustomToolbar().onCustom(context, PARAM_TOOLBAR);
        this.addView(toolbar);
        RelativeLayout rlToolbar = new CustomRelativeLayout().onCustom(context, PARAM_TOOLBAR_RL);
        toolbar.addView(rlToolbar);

        ImageView ivBack = new CustomImageView().onCustom(context, PARAM_BACK, R.mipmap.red_arrow_left);
        rlToolbar.addView(ivBack);
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onBackButtonClick();
                }
            }
        });

        TextView tvTitle = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.to_get_a_permission));
        rlToolbar.addView(tvTitle);

          /*   civAvatar = new CustomCircleImageView().onCustom(context, PARAM_AVATAR);
        rlToolbar.addView(civAvatar);
        civAvatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onMenuClick(view);
                }
            }
        });*/

        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        this.addView(linearLayout);

        //////////////        SERVICE KILLER          ///////////////////
        RelativeLayout rlServiceKiller = new CustomRelativeLayout().onCustom(context, PARAM_CONTAINER_RL);
        linearLayout.addView(rlServiceKiller);


        ivServiceKiller = new CustomImageView().onCustom(context, PARAM_ICON, 0);
        rlServiceKiller.addView(ivServiceKiller);

        ////////////////////////////
        TextView tvServiceKiller = new CustomTextView().onCustom(context, PARAM_TEXT, null);
        rlServiceKiller.addView(tvServiceKiller);
        tvServiceKiller.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onTextBodyAccessClick(res.getString(R.string.service_killer_text));
                }
            }
        });
        String firstText = res.getString(R.string.data_access);
        String secondFirst = res.getString(R.string.more_details);
        int startIndex = 0;
        int endIndex = firstText.length();
        SpannableString spannableString = new SpannableString(firstText + "\n" + secondFirst);
        spannableString.setSpan(new ForegroundColorSpan(Color.rgb(152, 152, 152)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvServiceKiller.setText(spannableString);

        ImageView ivNextPage1 = new CustomImageView().onCustom(context, PARAM_NEXT_PAGE, R.mipmap.red_right);
        rlServiceKiller.addView(ivNextPage1);
        ivNextPage1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onServiceKillerClick();
                }
            }
        });

        ///////////////         PHONE ACCESS          /////////////////
        onPhoneAccess(linearLayout);

        ///////////////         LOCK APP ACCESS        /////////////////
        //      onLockAppAccess(linearLayout);

        //////////////         MODEL PHONE          ////////////////
        String manufacturer = android.os.Build.MANUFACTURER;
        if (manufacturer.equalsIgnoreCase("xiaomi") || manufacturer.equals("Xiaomi")) {
            onXiaomiView(linearLayout);
        }
    }

    public AccessView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public AccessView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AccessView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void onPhoneAccess(LinearLayout linearLayout) {
        RelativeLayout rlPhoneAccess = new CustomRelativeLayout().onCustom(context, PARAM_CONTAINER_RL);
        linearLayout.addView(rlPhoneAccess);

        ivPhoneAccess = new CustomImageView().onCustom(context, PARAM_ICON, 0);
        rlPhoneAccess.addView(ivPhoneAccess);

        TextView tvPhoneAccess = new CustomTextView().onCustom(context, PARAM_TEXT, null);
        rlPhoneAccess.addView(tvPhoneAccess);
        tvPhoneAccess.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onTextBodyAccessClick(res.getString(R.string.phone_text));
                }
            }
        });
        String firstText = res.getString(R.string.phone_access);
        String secondFirst = res.getString(R.string.more_details);
        int startIndex = 0;
        int endIndex = firstText.length();
        SpannableString spannableString = new SpannableString(firstText + "\n" + secondFirst);
        spannableString.setSpan(new ForegroundColorSpan(Color.rgb(152, 152, 152)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPhoneAccess.setText(spannableString);

        ImageView ivNextPage2 = new CustomImageView().onCustom(context, PARAM_NEXT_PAGE, R.mipmap.red_right);
        rlPhoneAccess.addView(ivNextPage2);
        ivNextPage2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onPhoneClick();
                }
            }
        });
    }

    private void onXiaomiView(LinearLayout linearLayout) {
        LinearLayout llContainer = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_MODEL_PHONE_LL);
        linearLayout.addView(llContainer);

        TextView tvTitle = new CustomTextView().onCustom(context, PARAM_MODEL_PHONE_TITLE, res.getString(R.string.warning));
        llContainer.addView(tvTitle);

        TextView tvDescription = new CustomTextView().onCustom(context, PARAM_MODEL_PHONE_DESCRIPTION, res.getString(R.string.device_description));
        llContainer.addView(tvDescription);

        /////////////            AUTO START            ///////////
        RelativeLayout relativeLayout = new CustomRelativeLayout().onCustom(context, PARAM_CONTAINER_RL);
        linearLayout.addView(relativeLayout);

        ImageView imageView = new CustomImageView().onCustom(context, PARAM_ICON, 0);
        relativeLayout.addView(imageView);

        TextView tvPhoneAccess = new CustomTextView().onCustom(context, PARAM_TEXT, null);
        relativeLayout.addView(tvPhoneAccess);
        tvPhoneAccess.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    //  onItemClickListener.onTextBodyAccessClick(res.getString(R.string.phone_text));
                }
            }
        });
        String firstText = res.getString(R.string.autostart);
        String secondFirst = res.getString(R.string.more_details);
        int startIndex = 0;
        int endIndex = firstText.length();
        SpannableString spannableString = new SpannableString(firstText + "\n" + secondFirst);
        spannableString.setSpan(new ForegroundColorSpan(Color.rgb(152, 152, 152)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPhoneAccess.setText(spannableString);

        ImageView ivNextPage2 = new CustomImageView().onCustom(context, PARAM_NEXT_PAGE, R.mipmap.red_right);
        relativeLayout.addView(ivNextPage2);
        ivNextPage2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onModelPhoneClick("Xiaomi");
                }
                android.util.Log.e("Tag", " Msg");
            }
        });

        TextView tvXiaomi = new CustomTextView().onCustom(context, PARAM_MODEL_PHONE_ACCESS_TEXT, res.getString(R.string.xiaomi_text));
        linearLayout.addView(tvXiaomi);
        tvXiaomi.setMovementMethod(new ScrollingMovementMethod());
    }
    public void updateCheckButtonServiceKiller(boolean isAccess) {
        this.isAccess = isAccess;
        if (isAccess) {
            ivServiceKiller.setImageResource(R.mipmap.checked_green);
        } else {
            ivServiceKiller.setImageResource(R.mipmap.checked_gray);
        }
    }
    public void updateCheckButtonPhoneAccess(boolean isAccess) {
        if (isAccess) {
            ivPhoneAccess.setImageResource(R.mipmap.checked_green);
        } else {
            ivPhoneAccess.setImageResource(R.mipmap.checked_gray);
        }
    }

}
