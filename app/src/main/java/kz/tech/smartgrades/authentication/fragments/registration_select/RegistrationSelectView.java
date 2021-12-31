package kz.tech.smartgrades.authentication.fragments.registration_select;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomCardView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class RegistrationSelectView extends FrameLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onBackButtonClick();
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_TOOLBAR = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:50", "backC:WHITE"};
    private static final String[] PARAM_TOOLBAR_RL = {"prt:Toolbar", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_LEFT_BUTTON = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "pad:5,5,5,5"};
    private static final String[] PARAM_TITLE = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:56,0,56,0",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_CONTAINER = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,55,0,0", "orn:ver", "backC:WHITE"};
    private static final String[] PARAM_CONTAINER_CV = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:8,8,8,5"};
    private static final String[] PARAM_CONTAINER_RL = {"prt:CardView", "layW:mPrt", "layH:wCnt"};
    private static final String[] PARAM_CV_ICON = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "parW:100", "parH:100",
            "pad:5,5,5,5"};
    private static final String[] PARAM_CV_TITLE = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "mrg:100,0,0,0", "pad:5,5,5,5",
            "grv:CHCV" ,"txtS:18", "txtC:BLACK"};
    private static final String[] PARAM_CV_TEXT = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "mrg:100,40,0,0",
            "grv:CHCV" ,"txtS:14", "txtC:GRAY_THREE"};
    public RegistrationSelectView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));

        Toolbar toolbar = new CustomToolbar().onCustom(context, PARAM_TOOLBAR);
        this.addView(toolbar);
        RelativeLayout rlToolbar = new CustomRelativeLayout().onCustom(context, PARAM_TOOLBAR_RL);
        toolbar.addView(rlToolbar);
        ImageView ivLeftButton = new CustomImageView().onCustom(context, PARAM_LEFT_BUTTON, R.mipmap.red_arrow_left);
        rlToolbar.addView(ivLeftButton);
        ivLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onBackButtonClick();
                }
            }
        });
        TextView tvTitle = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.choose_account_type));
        rlToolbar.addView(tvTitle);

        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER);
        this.addView(linearLayout);

        onAddAccountType(context, linearLayout, R.mipmap.parents, res.getString(R.string.parent), "Description", 1);//  Parent
        onAddAccountType(context, linearLayout, R.mipmap.mentor, res.getString(R.string.mentor), "Description", 2);//  Mentor
    }
    public RegistrationSelectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public RegistrationSelectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RegistrationSelectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void onAddAccountType(Context c, LinearLayout l, int img, String title, String text, int numb) {
        CardView cvContainer = new CustomCardView().onCustom(c, PARAM_CONTAINER_CV);
        l.addView(cvContainer);
        cvContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (numb) {
                    case 1:
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(numb);
                        }
                        break;
                    case 2:
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(numb);
                        }
                        break;
                }
            }
        });

        RelativeLayout rlContainer = new CustomRelativeLayout().onCustom(c, PARAM_CONTAINER_RL);
        cvContainer.addView(rlContainer);

        ImageView ivIcon = new CustomImageView().onCustom(c, PARAM_CV_ICON, img);
        rlContainer.addView(ivIcon);

        TextView tvTitle = new CustomTextView().onCustom(c, PARAM_CV_TITLE, title);
        rlContainer.addView(tvTitle);

        TextView tvText = new CustomTextView().onCustom(c, PARAM_CV_TEXT, text);
        rlContainer.addView(tvText);
    }
}
