package kz.tech.smartgrades.authentication.fragments.forgot_password;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomScrollView;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomEditText;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;

public class ForgotPasswordView extends FrameLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onBackButtonClick();
        void onMessageClick(String msg);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_TOOLBAR = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:56", "backC:WHITE"};
    private static final String[] PARAM_TOOLBAR_RL = {"prt:Toolbar", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_LEFT_BUTTON = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "pad:5,5,5,5"};
    private static final String[] PARAM_TITLE = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:56,0,56,0",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_CONTAINER_SV = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,55,0,50",
            "FillView:TRUE", "backC:WHITE"};
    private static final String[] PARAM_CONTAINER_LL = {"prt:ScrLay", "layW:mPrt", "layH:mPrt", "orn:ver", "mrg:50,40,50,0"};
    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:1", "backC:RED_ONE"};
    private static final String[] PARAM_INPUT_FL = {"prt:LinLay", "layW:mPrt", "layH:wCnt"};
    private static final String[] PARAM_INPUT_IV = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "parW:24", "parH:24", "GRAV:LEFT_VER"};
    private static final String[] PARAM_ET_MAIL = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "backC:0", "mrg:30,0,30,0", "InTy:MAIL"};
    private static final String[] PARAM_BOTTOM_FL = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:50", "GRAV:BOTTOM", "backC:WHITE"};
    private static final String[] PARAM_NEXT = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:0,0,10,10", "parW:150",
            "txtC:GREEN_ONE", "grv:CHCV", "txtS:18", "GRAV:RIGHT"};
    public ForgotPasswordView(Context context, Resources res) {
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
        TextView tvTitle = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.password_recovery));
        rlToolbar.addView(tvTitle);

        ScrollView scrollView = new CustomScrollView().onCustom(context, PARAM_CONTAINER_SV);
        this.addView(scrollView);

        LinearLayout llContainer = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        scrollView.addView(llContainer);

        View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        llContainer.addView(v1);

        FrameLayout flMail = new CustomFrameLayout().onCustom(context, PARAM_INPUT_FL);
        llContainer.addView(flMail);

        ImageView ivMail = new CustomImageView().onCustom(context, PARAM_INPUT_IV, R.mipmap.red_mail);
        flMail.addView(ivMail);

        EditText etMail = new CustomEditText().onCustom(context, PARAM_ET_MAIL, res.getString(R.string.mail));
        flMail.addView(etMail);

        View v2 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        llContainer.addView(v2);

        FrameLayout flBottom = new CustomFrameLayout().onCustom(context, PARAM_BOTTOM_FL);
        this.addView(flBottom);

        TextView tvOk = new CustomTextView().onCustom(context, PARAM_NEXT, res.getString(R.string.restore));
        flBottom.addView(tvOk);
        tvOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    if (TextUtils.isEmpty(etMail.getText().toString())) {
                        onItemClickListener.onMessageClick(res.getString(R.string.enter_mail));
                        return;
                    }
                }
            }
        });
    }
    public ForgotPasswordView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public ForgotPasswordView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ForgotPasswordView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
