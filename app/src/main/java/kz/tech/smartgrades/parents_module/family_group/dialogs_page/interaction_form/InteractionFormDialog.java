package kz.tech.smartgrades.parents_module.family_group.dialogs_page.interaction_form;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.authentication.fragments.registration_mentor.models.ModelMentor;
import kz.tech.smartgrades.MainActivity;
import kz.tech.smartgrades.root.models.ModelChild;
import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomHorizontalScrollView;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomScrollView;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomEditText;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomSwitch;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.animation.GetButtonRipple;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;

public class InteractionFormDialog extends Dialog {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onMsgClick(String msg);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_TOOLBAR = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:50", "backC:WHITE"};
    private static final String[] PARAM_TOOLBAR_RL = {"prt:Toolbar", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_LEFT_BUTTON = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "pad:5,5,5,5"};
    private static final String[] PARAM_TITLE = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:56,0,56,0", "pad:5,5,5,5",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_AVATAR = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "pad:5,5,5,5",
            "BordC:GRAY_THREE", "BordW:2", "img:avatar", "Rule:RIGHT"};

    private static final String[] PARAM_MENTOR_FL = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "parH:50", "mrg:0,55,0,5", "backC:WHITE"};
    private static final String[] PARAM_MENTOR_AVATAR = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "mrg:15,0,0,0",
            "parW:50", "parH:50", "GRAV:LEFT", "ID:400301", "pad:5,5,5,5"};
    private static final String[] PARAM_MENTOR_LOGIN = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:70,0,50,20",
            "parH:30", "GRAV:HOR", "ID:400302", "txtC:BLACK",  "grv:LCV"};
    private static final String[] PARAM_MENTOR_FULL_NAME = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:70,30,50,0",
            "parH:20", "GRAV:HOR",  "ID:400303", "txtC:GRAY_THREE", "grv:LCV"};

    private static final String[] PARAM_CONTAINER_SV = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,110,0,0",
            "FillView:TRUE", "backC:WHITE"};
    private static final String[] PARAM_CONTAINER_LL = {"prt:ScrLay", "layW:mPrt", "layH:mPrt", "orn:ver"};
    private static final String[] PARAM_CONTAINER_FL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50"};

    private static final String[] PARAM_NAME = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:40", "mrg:10,0,200,0",
            "grv:LCV", "txtC:GRAY_THREE", "GRAV:LEFT_VER"};

    private static final String[] PARAM_ARROW_RIGHT = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "parW:40", "parH:40",
            "GRAV:RIGHT_VER", "pad:5,5,5,5"};
    private static final String[] PARAM_SUB_SWITCH = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:0,0,20,0",
            "parW:50", "parH:40", "GRAV:RIGHT_VER"};


    private static final String[] PARAM_TEXT_RIGHT = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parW:160", "mrg:0,0,40,0",
            "parH:40", "grv:RCV", "txtC:GRAY_THREE", "GRAV:RIGHT_VER"};
    private static final String[] PARAM_WARD_PHOTO = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "mrg:0,0,40,0",
            "parW:40", "parH:40", "GRAV:RIGHT_VER", "pad:5,5,5,5", "BordC:GRAY_THREE", "BordW:2", "img:avatar"};
    private static final String[] PARAM_WARD_PHOTO_CONTAINER_HSV = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "mrg:0,0,40,0",
            "parW:160", "parH:50", "GRAV:RIGHT_VER"};
    private static final String[] PARAM_WARD_PHOTO_CONTAINER_LL = {"prt:HScrLay", "layW:mPrt", "layH:mPrt", "orn:hor",
            "GRAV:RIGHT_VER", "VIS:GONE"};
    private static final String[] PARAM_WARD_DYNAMIC_FL = {"prt:LinLay", "layW:wCnt", "layH:mPrt", "parW:50"};
    private static final String[] PARAM_WARD_DYNAMIC_IV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:35",
            "GRAV:TOP"};
    private static final String[] PARAM_WARD_DYNAMIC_TV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:15",
            "GRAV:BOTTOM", "grv:CHCV", "txtC:GRAY_THREE"};


    private static final String[] PARAM_SUB_FL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50", "mrg:70,0,0,0"};
    private static final String[] PARAM_SUB_TV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:40",
            "GRAV:LEFT_VER", "grv:LCV", "txtC:GRAY_THREE"};
    private static final String[] PARAM_SUB_S = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:0,0,20,0",
            "parW:50", "parH:40", "GRAV:RIGHT_VER"};

    private static final String[] PARAM_SEND_MSG_TV = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:10,0,20,0",
            "parH:40", "grv:LCV", "txtC:GRAY_THREE"};
    private static final String[] PARAM_SEND_MSG_ET = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:70,0,20,0",
            "backC:0", "txtC:BLACK"};

    private static final String[] PARAM_SEND = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "mrg:0,50,0,50",
            "parW:150", "parH:40", "SctDrw:30,2,GRAY_THREE,GRAY_THREE", "grv:CHCV", "txtC:WHITE", "GRAV:HOR"};
    private Context context;
    private Resources res;
    private Dialog dialog;
    private List<ModelChild> list;
    private boolean[] isChecked;
    private FrameLayout[] fl;
    private LinearLayout linearLayout, llHOR;
    private CircleImageView civWard;
    private TextView tvSend;
    private EditText etSendMessage;
    private boolean isReady = false;
    private boolean[] isMentors;
    private ModelMentor m;
    private MainActivity main;
    private String accrualOfCoins, accrualOfCoinsSuper, accrualOfOffset, accrualOfOffsetSuper;
    public InteractionFormDialog(@NonNull Context context) {
        super(context);
    }
    public InteractionFormDialog(Context c, int themeResId, Resources res, String avatar,
                                 ModelMentor m, List<ModelChild> list, MainActivity main) {
        super(c, themeResId);
        this.context = c;
        this.res = res;
        this.list = list;
        dialog = this;
        this.m = m;
        this.main = main;
        isChecked = new boolean[this.list.size()];
        for (int i = 0; i < this.list.size(); i++) {
            isChecked[i] = false;
        }
        fl = new FrameLayout[this.list.size()];

        isMentors = new boolean[5];
        for (int j = 0; j < 5; j++) {
            isMentors[j] = false;
        }

        FrameLayout frameLayout = new CustomFrameLayout().onCustom(context, PARAM);
        this.setContentView(frameLayout);
        frameLayout.setBackgroundColor(ColorsRGB.GRAY_TWO);

        Toolbar toolbar = new CustomToolbar().onCustom(context, PARAM_TOOLBAR);
        frameLayout.addView(toolbar);
        RelativeLayout rlToolbar = new CustomRelativeLayout().onCustom(context, PARAM_TOOLBAR_RL);
        toolbar.addView(rlToolbar);
        ImageView ivLeftButton = new CustomImageView().onCustom(context, PARAM_LEFT_BUTTON, R.mipmap.red_arrow_left);
        rlToolbar.addView(ivLeftButton);
        ivLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView tvTitle = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.form_of_interaction));
        rlToolbar.addView(tvTitle);

        CircleImageView civAvatar = new CustomCircleImageView().onCustom(context, PARAM_AVATAR);
        rlToolbar.addView(civAvatar);
        Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);

        FrameLayout flMentor = new CustomFrameLayout().onCustom(context, PARAM_MENTOR_FL);
        frameLayout.addView(flMentor);

        CircleImageView civMentorAvatar = new CustomCircleImageView().onCustom(context, PARAM_MENTOR_AVATAR);
        flMentor.addView(civMentorAvatar);
        if (m.getAvatar() != null) { Picasso.get().load(m.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civMentorAvatar); }

        TextView tvMentorLogin = new CustomTextView().onCustom(context, PARAM_MENTOR_LOGIN, null);
        flMentor.addView(tvMentorLogin);
        if (m.getLogin() != null) { tvMentorLogin.setText(m.getLogin()); }

        TextView tvMentorFullName = new CustomTextView().onCustom(context, PARAM_MENTOR_FULL_NAME, null);
        flMentor.addView(tvMentorFullName);
        if (m.getName() != null) { tvMentorFullName.setText(m.getName()); }


        ScrollView scrollView = new CustomScrollView().onCustom(context, PARAM_CONTAINER_SV);
        frameLayout.addView(scrollView);


        linearLayout = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        scrollView.addView(linearLayout);


        //////////              STATUS             /////////////////
        onMentorsContainer();


        //////////              WARD             /////////////////
        onWardContainer();


        //////////              ACCRUAL OF COINS             /////////////////
        onAccrualOfCoins();


        //////////              ACCRUAL OF OFFSET             /////////////////
        onAccrualOfOffset();


        //////////              PREVENT INTERACTION             /////////////////
      //  onPreventInteraction();


        //////////              SEND MESSAGE             /////////////////
        onSendMessage();

        tvSend = new CustomTextView().onCustom(context, PARAM_SEND, res.getString(R.string.send));
        linearLayout.addView(tvSend);
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isReady) {
                    onSendInteractionForm();
                } else {

                }
            }
        });
    }

    protected InteractionFormDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    ////////////             MENTORS SELECT             //////////////////
    private void onMentorsContainer() {
        FrameLayout flStatus = new CustomFrameLayout().onCustom(context, PARAM_CONTAINER_FL);
        linearLayout.addView(flStatus);

        TextView tvStatusName = new CustomTextView().onCustom(context, PARAM_NAME, res.getString(R.string.status));
        flStatus.addView(tvStatusName);

        isMentors[0] = true;//  DEFAULT SELECT
        TextView tvStatusMentor = new CustomTextView().onCustom(context, PARAM_TEXT_RIGHT, res.getString(R.string.upbringer));
        flStatus.addView(tvStatusMentor);

        ImageView ivStatusArrowRight = new CustomImageView().onCustom(context, PARAM_ARROW_RIGHT, R.mipmap.red_right);
        flStatus.addView(ivStatusArrowRight);
        ivStatusArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialogMentors dialogMentors = new BottomDialogMentors(context, res, isMentors);
                dialogMentors.show();
                dialogMentors.setOnItemClickListener(new BottomDialogMentors.OnItemClickListener() {
                    @Override
                    public void onItemClick(String text) {
                        tvStatusMentor.setText(text);
                    }
                });
            }
        });
    }




    ////////////             WARD             //////////////////
    private void onWardContainer() {
        FrameLayout flWard = new CustomFrameLayout().onCustom(context, PARAM_CONTAINER_FL);
        linearLayout.addView(flWard);

        TextView tvWardName = new CustomTextView().onCustom(context, PARAM_NAME, res.getString(R.string.ward));
        flWard.addView(tvWardName);

        civWard = new CustomCircleImageView().onCustom(context, PARAM_WARD_PHOTO);
        flWard.addView(civWard);
        if (list.size() == 1) {
            onTextViewOkBackground(true);
        }

        ImageView ivWardArrowRight = new CustomImageView().onCustom(context, PARAM_ARROW_RIGHT, R.mipmap.red_right);
        flWard.addView(ivWardArrowRight);
        ivWardArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialogWard dialogWard = new BottomDialogWard(context, res, list, isChecked);
                dialogWard.show();
                dialogWard.setOnItemClickListener(new BottomDialogWard.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (isChecked[position]) {
                            onAddWard(position);
                        } else {
                            onRemoveWard(position);
                        }
                        boolean lol = false;
                        for (int i = 0; i < list.size(); i++) {
                            if (isChecked[i]) { lol = true; }
                        }
                        if (lol) {
                            onSelectWard(true);
                            onTextViewOkBackground(true);
                        } else {
                            onSelectWard(false);
                            onTextViewOkBackground(false);
                        }
                    }
                });
            }
        });

        HorizontalScrollView hsv = new CustomHorizontalScrollView().onCustom(context, PARAM_WARD_PHOTO_CONTAINER_HSV);
        flWard.addView(hsv);

        llHOR = new CustomLinearLayout().onCustom(context, PARAM_WARD_PHOTO_CONTAINER_LL);
        hsv.addView(llHOR);
    }
    private void onSelectWard(boolean isShownWard) {
        if (isShownWard) {
            civWard.setVisibility(View.GONE);
            llHOR.setVisibility(View.VISIBLE);
        } else {
            civWard.setVisibility(View.VISIBLE);
            llHOR.setVisibility(View.GONE);
        }
    }
    private void onAddWard(int n) {
        fl[n] = new CustomFrameLayout().onCustom(context, PARAM_WARD_DYNAMIC_FL);
        llHOR.addView(fl[n]);

        ImageView iv = new CustomImageView().onCustom(context, PARAM_WARD_DYNAMIC_IV, 0);
        fl[n].addView(iv);
        Picasso.get().load(list.get(n).getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(iv);

        TextView tv = new CustomTextView().onCustom(context, PARAM_WARD_DYNAMIC_TV, list.get(n).getName());
        fl[n].addView(tv);
    }
    private void onRemoveWard(int n) {
        fl[n].removeAllViewsInLayout();
        llHOR.removeView(fl[n]);
    }





    ////////////             ACCRUAL OF COINS             //////////////////
    private void onAccrualOfCoins() {
        FrameLayout flAccrualOfCoins = new CustomFrameLayout().onCustom(context, PARAM_CONTAINER_FL);
        linearLayout.addView(flAccrualOfCoins);

        TextView tvAccrualOfCoinsName = new CustomTextView().onCustom(context, PARAM_NAME, res.getString(R.string.accrual_of_coins));
        flAccrualOfCoins.addView(tvAccrualOfCoinsName);


        String textNum1 = "0";
        String textNum2 = "+3";
        String textResult = res.getString(R.string.from) + " " + textNum1 +
                " " + res.getString(R.string.to) + " " + textNum2;
        accrualOfCoins = textNum1 + ":" + textNum2;
        TextView tvCountCoins = new CustomTextView().onCustom(context, PARAM_TEXT_RIGHT, textResult.toLowerCase());
        flAccrualOfCoins.addView(tvCountCoins);

        ImageView ivAccrualOfCoinsArrowRight = new CustomImageView().onCustom(context, PARAM_ARROW_RIGHT, R.mipmap.red_right);
        flAccrualOfCoins.addView(ivAccrualOfCoinsArrowRight);
        ivAccrualOfCoinsArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialogAccrualOfCoins dialog =
                        new BottomDialogAccrualOfCoins(context, res, textNum1, textNum2);
                dialog.show();
                dialog.setOnItemClickListener(new BottomDialogAccrualOfCoins.OnItemClickListener() {
                    @Override
                    public void onOkClick(String s1, String s2) {
                        accrualOfCoins = s1 + ":" + s2;
                        String result = res.getString(R.string.from) + " " + s1 +
                                " " + res.getString(R.string.to) + " " + s2;
                        tvCountCoins.setText(result.toLowerCase());
                    }
                });
            }
        });


        ////////////             ACCRUAL OF COINS             //////////////////

        ////////////            SUPER ACHIEVEMENT            ///////////////////
        FrameLayout flSuper = new CustomFrameLayout().onCustom(context, PARAM_SUB_FL);
        linearLayout.addView(flSuper);

        TextView tvSuperAchievement = new CustomTextView().onCustom(context, PARAM_SUB_TV, res.getString(R.string.super_achievement));
        flSuper.addView(tvSuperAchievement);

        Switch sSuper = new CustomSwitch().onCustom(context, PARAM_SUB_S);
        flSuper.addView(sSuper);


        String textSuper = "+7";

        TextView tvTextSuper = new CustomTextView().onCustom(context, PARAM_TEXT_RIGHT, null);
        flSuper.addView(tvTextSuper);
        tvTextSuper.setVisibility(View.GONE);

        ImageView ivRightArrowSuper = new CustomImageView().onCustom(context, PARAM_ARROW_RIGHT, R.mipmap.red_right);
        flSuper.addView(ivRightArrowSuper);
        ivRightArrowSuper.setVisibility(View.GONE);
        ivRightArrowSuper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialogAccrualOfCoinsSuper dialog =
                        new BottomDialogAccrualOfCoinsSuper(context, res, textSuper);
                dialog.show();
                dialog.setOnItemClickListener(new BottomDialogAccrualOfCoinsSuper.OnItemClickListener() {
                    @Override
                    public void onDisableClick() {
                        sSuper.setChecked(false);
                        sSuper.setVisibility(View.VISIBLE);
                        tvTextSuper.setVisibility(View.GONE);
                        ivRightArrowSuper.setVisibility(View.GONE);
                        tvTextSuper.setText("");
                        accrualOfCoinsSuper = null;
                    }
                    @Override
                    public void onOkClick(String text) {
                        accrualOfCoinsSuper = text;
                        tvTextSuper.setText(text);
                    }
                });
            }
        });

        sSuper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sSuper.isChecked()) {
                    sSuper.setVisibility(View.GONE);
                    tvTextSuper.setVisibility(View.VISIBLE);
                    ivRightArrowSuper.setVisibility(View.VISIBLE);
                    tvTextSuper.setText(textSuper);
                    accrualOfCoinsSuper = textSuper;
                }
            }
        });
    }





    //////////              ACCRUAL OF OFFSET             /////////////////
    private void onAccrualOfOffset() {
        FrameLayout flAccrualOfOffset = new CustomFrameLayout().onCustom(context, PARAM_CONTAINER_FL);
        linearLayout.addView(flAccrualOfOffset);

        TextView tvAccrualOfOffset = new CustomTextView().onCustom(context, PARAM_NAME, res.getString(R.string.accrual_of_offset));
        flAccrualOfOffset.addView(tvAccrualOfOffset);

        Switch sSuper1 = new CustomSwitch().onCustom(context, PARAM_SUB_SWITCH);
        flAccrualOfOffset.addView(sSuper1);

        String textNum1 = "+1";
        String textNum2 = "+2";
        String textResult = res.getString(R.string.from) + " " + textNum1 +
                " " + res.getString(R.string.to) + " " + textNum2;


        TextView tvTextSuper1 = new CustomTextView().onCustom(context, PARAM_TEXT_RIGHT, null);
        flAccrualOfOffset.addView(tvTextSuper1);
        tvTextSuper1.setVisibility(View.GONE);

        ImageView ivRightArrowSuper1 = new CustomImageView().onCustom(context, PARAM_ARROW_RIGHT, R.mipmap.red_right);
        flAccrualOfOffset.addView(ivRightArrowSuper1);
        ivRightArrowSuper1.setVisibility(View.GONE);



        //////////              ACCRUAL OF OFFSET             /////////////////

        ////////////            SUPER ACHIEVEMENT            ///////////////////
        FrameLayout flSuper = new CustomFrameLayout().onCustom(context, PARAM_SUB_FL);
        linearLayout.addView(flSuper);
        flSuper.setVisibility(View.GONE);

        TextView tvSuperAchievement = new CustomTextView().onCustom(context, PARAM_SUB_TV, res.getString(R.string.super_achievement));
        flSuper.addView(tvSuperAchievement);

        Switch sSuper2 = new CustomSwitch().onCustom(context, PARAM_SUB_S);
        flSuper.addView(sSuper2);

        String textSuper = "+5";

        TextView tvTextSuper2 = new CustomTextView().onCustom(context, PARAM_TEXT_RIGHT, null);
        flSuper.addView(tvTextSuper2);
        tvTextSuper2.setVisibility(View.GONE);

        ImageView ivRightArrowSuper2 = new CustomImageView().onCustom(context, PARAM_ARROW_RIGHT, R.mipmap.red_right);
        flSuper.addView(ivRightArrowSuper2);
        ivRightArrowSuper2.setVisibility(View.GONE);


        ///////////             1              //////////////
        sSuper1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sSuper1.isChecked()) {
                    sSuper1.setVisibility(View.GONE);
                    flSuper.setVisibility(View.VISIBLE);
                    tvTextSuper1.setVisibility(View.VISIBLE);
                    ivRightArrowSuper1.setVisibility(View.VISIBLE);
                    tvTextSuper1.setText(textResult.toLowerCase());
                    accrualOfOffset = textNum1 + ":" + textNum2;
                }
            }
        });

        ivRightArrowSuper1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialogAccrualOfOffset dialog =
                        new BottomDialogAccrualOfOffset(context, res, textNum1, textNum2);
                dialog.show();
                dialog.setOnItemClickListener(new BottomDialogAccrualOfOffset.OnItemClickListener() {
                    @Override
                    public void onOkClick(String s1, String s2) {
                        accrualOfOffset = s1 + ":" + s2;
                        String result = res.getString(R.string.from) + " " + s1 +
                                " " + res.getString(R.string.to) + " " + s2;
                        tvTextSuper1.setText(result.toLowerCase());
                    }
                });
            }
        });


        ///////////             2              //////////////
        sSuper2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sSuper2.isChecked()) {
                    sSuper2.setVisibility(View.GONE);
                    tvTextSuper2.setVisibility(View.VISIBLE);
                    ivRightArrowSuper2.setVisibility(View.VISIBLE);
                    tvTextSuper2.setText(textSuper);
                    accrualOfOffsetSuper = textSuper;
                }
            }
        });

        ivRightArrowSuper2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialogAccrualOfOffsetSuper dialog =
                        new BottomDialogAccrualOfOffsetSuper(context, res, textSuper);
                dialog.show();
                dialog.setOnItemClickListener(new BottomDialogAccrualOfOffsetSuper.OnItemClickListener() {
                    @Override
                    public void onDisableClick() {
                        sSuper2.setChecked(false);
                        sSuper2.setVisibility(View.VISIBLE);
                        tvTextSuper2.setVisibility(View.GONE);
                        ivRightArrowSuper2.setVisibility(View.GONE);
                        tvTextSuper2.setText("");
                        accrualOfOffsetSuper = null;
                    }
                    @Override
                    public void onOkClick(String text) {
                        accrualOfOffsetSuper = text;
                        tvTextSuper2.setText(text);
                    }
                });
            }
        });

    }






    //////////              PREVENT INTERACTION             /////////////////
    private void onPreventInteraction() {
        FrameLayout flPreventInteraction = new CustomFrameLayout().onCustom(context, PARAM_CONTAINER_FL);
        linearLayout.addView(flPreventInteraction);

        TextView tvPreventInteraction = new CustomTextView().onCustom(context, PARAM_NAME, res.getString(R.string.prevent_interaction));
        flPreventInteraction.addView(tvPreventInteraction);
    }




    //////////              SEND MESSAGE             /////////////////
    private void onSendMessage() {
        TextView tvSendMessage = new CustomTextView().onCustom(context, PARAM_SEND_MSG_TV, res.getString(R.string.mentor_note_one));
        linearLayout.addView(tvSendMessage);


        etSendMessage = new CustomEditText().onCustom(context, PARAM_SEND_MSG_ET, res.getString(R.string.mentor_note_three));
        linearLayout.addView(etSendMessage);
        etSendMessage.setText(res.getString(R.string.mentor_note_two));

    }

    private void onSendInteractionForm() {
        /*ModelInteractionForm model = new ModelInteractionForm();

        if (accrualOfCoins != null) {
            model.setAccrualOfCoins(accrualOfCoins);
        } else {
            model.setAccrualOfCoins("");
        }

        if (accrualOfCoinsSuper != null) {
            model.setAccrualOfCoinsSuper(accrualOfCoinsSuper);
        } else {
            model.setAccrualOfCoinsSuper("");
        }

        if (accrualOfOffset != null) {
            model.setAccrualOfOffset(accrualOfOffset);
        } else {
            model.setAccrualOfOffset("");
        }

        if (accrualOfOffsetSuper != null) {
            model.setAccrualOfOffsetSuper(accrualOfOffsetSuper);
        } else {
            model.setAccrualOfOffsetSuper("");
        }

        List<ModelChildArray> childList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (isChecked[i]) {
                childList.add(new ModelChildArray(
                        list.get(i).getAvatar(), list.get(i).getIdChild(), list.get(i).getName()));
            }
        }

        model.setChildList(childList);

        String dateOfCreation = main.iDate.getCurrentDate();
        model.setDateOfCreation(dateOfCreation);

        String idLogin = main.prefs.getLoginData("id");
        model.setIdLogin(idLogin);

        String avatar = main.prefs.getFamilyData("avatar");
        model.setParentAvatar(avatar);

        String sendMsg = etSendMessage.getText().toString();
        if (sendMsg != null) {
            model.setParentMessage(sendMsg);
        } else {
            model.setParentMessage("");
        }

        String name = main.prefs.getFamilyData("name");
        model.setParentName(name);



        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("InteractionForm");
        String generationID = dbr.push().getKey();
        dbr.child(m.getId()).child(generationID).setValue(model);

        dialog.dismiss();*/
    }


    private void onTextViewOkBackground(boolean backR) {
        if (backR) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tvSend.setBackgroundDrawable(new GetButtonRipple()
                        .getRipple(30f, 2, ColorsRGB.GRAY_THREE,
                                GetColorInText.getIntColor("RED_ONE"),//  First Color
                                ColorsRGB.BLUE_ONE));//  Second Color
            } else {
                tvSend.setBackgroundColor(GetColorInText.getIntColor("RED_ONE"));
            }
            isReady = backR;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tvSend.setBackgroundDrawable(new GetButtonRipple()
                        .getRipple(30f, 2, ColorsRGB.GRAY_THREE,
                                GetColorInText.getIntColor("GRAY_THREE"),//  First Color
                                ColorsRGB.BLUE_ONE));//  Second Color
            } else {
                tvSend.setBackgroundColor(GetColorInText.getIntColor("GRAY_THREE"));
            }
            isReady = backR;
        }
    }
}


