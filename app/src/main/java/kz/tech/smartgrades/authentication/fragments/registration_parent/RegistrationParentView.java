package kz.tech.smartgrades.authentication.fragments.registration_parent;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
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

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;

import kz.tech.smartgrades.root.tools.GetAvatarByte;
import kz.tech.smartgrades.root.dialogs.BottomDialogSelectAvatar;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomScrollView;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;

import kz.tech.smartgrades.root.ui.CustomView.CustomCheckBox;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomEditText;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;

public class RegistrationParentView extends FrameLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onBackButtonClick();
        void onMessageClick(String msg);
        void onLoginCreateClick(String login, String mail, String password, byte[] dataAvatar,
                                String name, String quickAccessCode, String status);
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
    private static final String[] PARAM_CONTAINER_SV = {"prt:FrmLay", "layW:mPrt", "layH:mPrt",
            "mrg:0,55,0,50", "FillView:TRUE", "VIS:GONE", "backC:WHITE"};
    private static final String[] PARAM_CONTAINER_LL_1 = {"prt:ScrLay", "layW:mPrt", "layH:mPrt", "orn:ver", "mrg:50,40,50,0"};
    private static final String[] PARAM_CONTAINER_LL_2 = {"prt:ScrLay", "layW:mPrt", "layH:mPrt", "orn:ver", "mrg:50,10,50,0"};
    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:1", "backC:RED_ONE"};
    private static final String[] PARAM_INPUT_FL = {"prt:LinLay", "layW:mPrt", "layH:wCnt"};
    private static final String[] PARAM_INPUT_IV = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "parW:24", "parH:24", "GRAV:LEFT_VER"};
    private static final String[] PARAM_INPUT_ET = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "backC:0", "mrg:30,0,30,0"};
    private static final String[] PARAM_ET_MAIL = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "backC:0", "mrg:30,0,30,0",
            "InTy:MAIL"};
    private static final String[] PARAM_ET_QUICK_ACCESS_CODE = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "backC:0", "mrg:30,0,30,0",
            "InTy:NUMB", "Filter:PIN"};
    private static final String[] PARAM_ADD_PHOTO = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "mrg:0,0,0,20",
            "parW:80", "parH:80", "BordC:RED_ONE", "BordW:2", "img:red_add_photo", "GRAV:HOR"};
    private static final String[] PARAM_NEXT = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:0,0,10,10", "parW:150",
            "txtC:GREEN_ONE", "grv:CHCV", "txtS:18", "GRAV:RIGHT"};
    private static final String[] PARAM_BACK = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:0,0,10,10", "parW:150",
            "txtC:GREEN_ONE", "grv:CHCV", "txtS:18", "GRAV:LEFT"};
    private static final String[] PARAM_STATUS_LL = {"prt:LinLay", "layW:mPrt", "layH:wCnt",
            "parH:40", "orn:hor", "WeiSum:2"};
    private static final String[] PARAM_STATUS_CB = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1"};
    private static final String[] PARAM_BOTTOM_FL = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:50",
            "GRAV:BOTTOM", "backC:WHITE"};
    private Context context;
    private Resources res;
    private ScrollView svLoginInput, svAccountInput;
    private FrameLayout flBottom;
    private TextView tvNext, tvBack, tvOk;
    private CircleImageView civAvatar;
    private EditText etLogin, etMail, etPassword, etName, etQuickAccessCode;
    private CheckBox cbMother, cbFather;
    public RegistrationParentView(Context context, Resources res) {
        super(context);
        this.context = context;
        this.res = res;
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
        TextView tvTitle = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.registration));
        rlToolbar.addView(tvTitle);

        flBottom = new CustomFrameLayout().onCustom(context, PARAM_BOTTOM_FL);
        this.addView(flBottom);

        onLoginInput();

        onAccountInput();

    }
    public RegistrationParentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public RegistrationParentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RegistrationParentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void onSelectScrollView(int numb) {
        svLoginInput.setVisibility(GONE);
        svAccountInput.setVisibility(GONE);
        tvNext.setVisibility(GONE);
        tvBack.setVisibility(GONE);
        tvOk.setVisibility(GONE);
        switch (numb) {
            case 1:
                svLoginInput.setVisibility(VISIBLE);
                tvNext.setVisibility(VISIBLE);
                break;
            case 2:
                svAccountInput.setVisibility(VISIBLE);
                tvBack.setVisibility(VISIBLE);
                tvOk.setVisibility(VISIBLE);
                break;
        }

    }
    private void onLoginInput() {
        svLoginInput = new CustomScrollView().onCustom(context, PARAM_CONTAINER_SV);
        this.addView(svLoginInput);

        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL_1);
        svLoginInput.addView(linearLayout);
        //   linearLayout.setBackgroundColor(Color.GREEN);

        View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v1);

        FrameLayout flLogin = new CustomFrameLayout().onCustom(context, PARAM_INPUT_FL);
        linearLayout.addView(flLogin);

        ImageView ivLogin = new CustomImageView().onCustom(context, PARAM_INPUT_IV, R.mipmap.red_man);
        flLogin.addView(ivLogin);

        etLogin = new CustomEditText().onCustom(context, PARAM_INPUT_ET, res.getString(R.string.login));
        flLogin.addView(etLogin);

        View v2 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v2);

        FrameLayout flMail = new CustomFrameLayout().onCustom(context, PARAM_INPUT_FL);
        linearLayout.addView(flMail);

        ImageView ivMail = new CustomImageView().onCustom(context, PARAM_INPUT_IV, R.mipmap.red_mail);
        flMail.addView(ivMail);

        etMail = new CustomEditText().onCustom(context, PARAM_ET_MAIL, res.getString(R.string.mail));
        flMail.addView(etMail);

        View v3 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v3);

        FrameLayout flPassword = new CustomFrameLayout().onCustom(context, PARAM_INPUT_FL);
        linearLayout.addView(flPassword);

        ImageView ivPassword = new CustomImageView().onCustom(context, PARAM_INPUT_IV, R.mipmap.lock);
        flPassword.addView(ivPassword);

        etPassword = new CustomEditText().onCustom(context, PARAM_INPUT_ET, res.getString(R.string.password));
        flPassword.addView(etPassword);

        View v4 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v4);

        ////////         BOTTOM          ////////////
        tvNext = new CustomTextView().onCustom(context, PARAM_NEXT, res.getString(R.string.next));
        flBottom.addView(tvNext);
        tvNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    if (TextUtils.isEmpty(etLogin.getText().toString())) {
                        onItemClickListener.onMessageClick(res.getString(R.string.enter_login));
                        return;
                    }
                    if (TextUtils.isEmpty(etMail.getText().toString())) {
                        onItemClickListener.onMessageClick(res.getString(R.string.enter_mail));
                        return;
                    }
                    if (TextUtils.isEmpty(etPassword.getText().toString())) {
                        onItemClickListener.onMessageClick(res.getString(R.string.enter_password));
                        return;
                    } else if (etPassword.length() < 6) {
                        onItemClickListener.onMessageClick(res.getString(R.string.password_must_be_with_six_symbols));
                        return;
                    }
                    onSelectScrollView(2);
                }
            }
        });
    }
    private void onAccountInput() {
        svAccountInput = new CustomScrollView().onCustom(context, PARAM_CONTAINER_SV);
        this.addView(svAccountInput);

        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL_2);
        svAccountInput.addView(linearLayout);

        civAvatar = new CustomCircleImageView().onCustom(context, PARAM_ADD_PHOTO);
        linearLayout.addView(civAvatar);
        civAvatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cbFather.isChecked() && !cbMother.isChecked()) {
                    onItemClickListener.onMessageClick(res.getString(R.string.select_parent));
                    return;
                } else {
                    String isParents = "";
                    if (cbFather.isChecked()) {
                        isParents = "Father";
                    } else if (cbMother.isChecked()) {
                        isParents = "Mother";
                    }
                    BottomDialogSelectAvatar bottomDialogSelectAvatar =
                            new BottomDialogSelectAvatar(context, civAvatar, isParents);
                    bottomDialogSelectAvatar.show();
                }
            }
        });

        View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v1);

        FrameLayout flName = new CustomFrameLayout().onCustom(context, PARAM_INPUT_FL);
        linearLayout.addView(flName);

        ImageView ivName = new CustomImageView().onCustom(context, PARAM_INPUT_IV, R.mipmap.red_man);
        flName.addView(ivName);

        etName = new CustomEditText().onCustom(context, PARAM_INPUT_ET, res.getString(R.string.name));
        flName.addView(etName);

        View v2 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v2);

        FrameLayout flQuickAccessCode = new CustomFrameLayout().onCustom(context, PARAM_INPUT_FL);
        linearLayout.addView(flQuickAccessCode);

        ImageView ivQuickAccessCode = new CustomImageView().onCustom(context, PARAM_INPUT_IV, R.mipmap.lock);
        flQuickAccessCode.addView(ivQuickAccessCode);

        etQuickAccessCode = new CustomEditText().onCustom(context, PARAM_ET_QUICK_ACCESS_CODE, res.getString(R.string.quiack_access_code));
        flQuickAccessCode.addView(etQuickAccessCode);

        View v3 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v3);

        LinearLayout cbParents = new CustomLinearLayout().onCustom(context, PARAM_STATUS_LL);
        linearLayout.addView(cbParents);
        cbFather = new CustomCheckBox().onCustom(context, PARAM_STATUS_CB, res.getString(R.string.father));
        cbParents.addView(cbFather);
        cbFather.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbFather.isChecked()) {
                    cbFather.setChecked(true);
                }
                if (cbMother.isChecked()) {
                    cbMother.setChecked(false);
                }
            }
        });
        cbMother = new CustomCheckBox().onCustom(context, PARAM_STATUS_CB, res.getString(R.string.mother));
        cbParents.addView(cbMother);
        cbMother.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbMother.isChecked()) {
                    cbMother.setChecked(true);
                }
                if (cbFather.isChecked()) {
                    cbFather.setChecked(false);
                }
            }
        });
        ////////         BOTTOM          ////////////
        tvBack = new CustomTextView().onCustom(context, PARAM_BACK, res.getString(R.string.back));
        flBottom.addView(tvBack);
        tvBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectScrollView(1);
            }
        });
        tvOk = new CustomTextView().onCustom(context, PARAM_NEXT, res.getString(R.string.create));
        flBottom.addView(tvOk);
        tvOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    if (TextUtils.isEmpty(etName.getText().toString())) {
                        onItemClickListener.onMessageClick(res.getString(R.string.enter_name));
                        return;
                    }
                    if (TextUtils.isEmpty(etQuickAccessCode.getText().toString())) {
                        onItemClickListener.onMessageClick(res.getString(R.string.enter_quick_access_code));
                        return;
                    } else if (etQuickAccessCode.length() < 4) {
                        onItemClickListener.onMessageClick(res.getString(R.string.quick_access_code_must_be_with_six_numbers));
                        return;
                    }
                    String isParents = "";
                    if (!cbFather.isChecked() && !cbMother.isChecked()) {
                        onItemClickListener.onMessageClick(res.getString(R.string.select_parent));
                        return;
                    } else {
                        if (cbFather.isChecked()) {
                            isParents = res.getString(R.string.father);
                        } else if (cbMother.isChecked()) {
                            isParents = res.getString(R.string.mother);
                        }
                    }
                    onItemClickListener.onLoginCreateClick(
                            etLogin.getText().toString(),
                            etMail.getText().toString(),
                            etPassword.getText().toString(),
                            GetAvatarByte.getAvatar(civAvatar),
                            etName.getText().toString(),
                            etQuickAccessCode.getText().toString(),
                            isParents);
                }

            }
        });
    }
}
