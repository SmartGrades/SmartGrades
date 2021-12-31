package kz.tech.smartgrades.authentication.fragments.sign_in;

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

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomEditText;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;

public class SignInView extends ScrollView {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onMessageClick(String msg);
        void onSignInClick(String login, String password);
        void onRegistrationClick();
        void onForgotPasswordClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_CONTAINER = {"prt:ScrLay", "layW:mPrt", "layH:wCnt", "pad:5,5,5,5"};
    private static final String[] PARAM_CONTAINER_TOP = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "ABOVE:400002",
            "Rule:TOP", "ID:400001"};
    private static final String[] PARAM_CONTAINER_CENTER = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "mrg:70,0,70,0",
            "orn:ver", "Rule:CEN_VER", "ID:400002"};
    private static final String[] PARAM_CONTAINER_BOTTOM = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "BELOW:400002",
            "Rule:BOTTOM", "ID:400003"};
    private static final String[] PARAM_LOGO = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "Rule:CEN_PAR", "parW:150", "parH:150"};

    private static final String[] PARAM_INPUT_FL = {"prt:LinLay", "layW:mPrt", "layH:wCnt"};
    private static final String[] PARAM_INPUT_IV = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "parW:24", "parH:24", "GRAV:LEFT_VER"};
    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:1", "backC:RED_ONE"};
    private static final String[] PARAM_INPUT_ET_LOGIN = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "backC:0", "mrg:30,0,30,0",
            "MinHeight:40", "Filter:OnlyLatin"};
    private static final String[] PARAM_INPUT_ET = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "backC:0", "mrg:30,0,30,0", "MinHeight:40"};
    private static final String[] PARAM_SIGN_IN = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:0,30,0,0", "pad:0,10,0,10",//"parH:40",
            "backRR:RED_ONE", "grv:CHCV", "txtC:WHITE", "txtS:18"};

    private static final String[] PARAM_FOR_PASS_AND_REG = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:0,20,0,0",
            "orn:hor", "WeiSum:2"};
    private static final String[] PARAM_FORGOT_PASSWORD = {"prt:LinLay", "layW:0", "layH:wCnt", "wei:1",
            "grv:CHCV", "txtC:GRAY_THREE", "txtS:14"};
    private static final String[] PARAM_REGISTRATION = {"prt:LinLay", "layW:0", "layH:wCnt", "wei:1",
            "grv:CHCV", "txtC:BLUE_ONE", "txtS:14"};//"SctDrw:30,2,GRAY_THREE,WHITE"
    public SignInView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        this.setBackgroundColor(ColorsRGB.WHITE);
        this.setFillViewport(true);

        RelativeLayout relativeLayout = new CustomRelativeLayout().onCustom(context, PARAM_CONTAINER);
        this.addView(relativeLayout);

        RelativeLayout rlTop = new CustomRelativeLayout().onCustom(context, PARAM_CONTAINER_TOP);
        relativeLayout.addView(rlTop);

        ImageView ivLogo = new CustomImageView().onCustom(context, PARAM_LOGO, R.mipmap.logo);
        rlTop.addView(ivLogo);

        //////////////////////
        LinearLayout llCenter = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_CENTER);
        relativeLayout.addView(llCenter);

        View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        llCenter.addView(v1);

        ////         LOGIN
        FrameLayout flLogin = new CustomFrameLayout().onCustom(context, PARAM_INPUT_FL);
        llCenter.addView(flLogin);

        ImageView ivLogin = new CustomImageView().onCustom(context, PARAM_INPUT_IV, R.mipmap.red_man);
        flLogin.addView(ivLogin);

        EditText etLogin = new CustomEditText().onCustom(context, PARAM_INPUT_ET_LOGIN, res.getString(R.string.login));
        flLogin.addView(etLogin);

        View v2 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        llCenter.addView(v2);

        ////        PASSWORD
        FrameLayout flPassword = new CustomFrameLayout().onCustom(context, PARAM_INPUT_FL);
        llCenter.addView(flPassword);

        ImageView ivPassword = new CustomImageView().onCustom(context, PARAM_INPUT_IV, R.mipmap.lock);
        flPassword.addView(ivPassword);

        EditText etPassword = new CustomEditText().onCustom(context, PARAM_INPUT_ET, res.getString(R.string.password));
        flPassword.addView(etPassword);

        View v3 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        llCenter.addView(v3);



        TextView tvSignIn = new CustomTextView().onCustom(context, PARAM_SIGN_IN, res.getString(R.string.sign_in));
        llCenter.addView(tvSignIn);
        tvSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(etLogin.getText().toString())) {
                    onItemClickListener.onMessageClick(res.getString(R.string.enter_login));
                    return;
                }
                if(TextUtils.isEmpty(etPassword.getText().toString())) {
                    onItemClickListener.onMessageClick(res.getString(R.string.enter_password));
                    return;
                } else if (etPassword.length() < 6){
                    onItemClickListener.onMessageClick(res.getString(R.string.password_must_be_with_six_symbols));
                    return;
                }
                if (onItemClickListener != null) {
                    onItemClickListener.onSignInClick(etLogin.getText().toString(), etPassword.getText().toString());
                }
            }
        });

        LinearLayout llForPassAndReg = new CustomLinearLayout().onCustom(context, PARAM_FOR_PASS_AND_REG);
        llCenter.addView(llForPassAndReg);

        TextView tvForgotPassword = new CustomTextView().onCustom(context, PARAM_FORGOT_PASSWORD, res.getString(R.string.forgot_password));
        llForPassAndReg.addView(tvForgotPassword);
        tvForgotPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onForgotPasswordClick();
                }
            }
        });

        TextView tvRegistration = new CustomTextView().onCustom(context, PARAM_REGISTRATION, res.getString(R.string.registration));
        llForPassAndReg.addView(tvRegistration);
        tvRegistration.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onRegistrationClick();
                }
            }
        });

        RelativeLayout rlBottom = new CustomRelativeLayout().onCustom(context, PARAM_CONTAINER_BOTTOM);
        relativeLayout.addView(rlBottom);


    }
    public SignInView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public SignInView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SignInView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
