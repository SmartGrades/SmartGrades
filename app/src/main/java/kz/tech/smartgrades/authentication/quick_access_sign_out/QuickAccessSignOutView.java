package kz.tech.smartgrades.authentication.quick_access_sign_out;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.models.ModelFamilyRoom;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;

public class QuickAccessSignOutView extends FrameLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onBackButtonClick();
        void onMenuClick(View view);
        void onMessageClick(String msg);
        void onAccessOkClick(ModelFamilyRoom modelFamilyRoom);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};

    private static final String[] PARAM_MAIN_LL = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,61,0,0",
            "orn:ver", "WeiSum:2"};

    private static final String[] PARAM_CONTAINER = {"prt:LinLay", "layW:mPrt", "layH:0",
            "orn:ver", "wei:1", "WeiSum:7"};

    private static final String[] PARAM_TOP = {"prt:LinLay", "layW:mPrt", "layH:0",
            "orn:ver", "wei:4", "WeiSum:5"};

    private static final String[] PARAM_BOTTOM = {"prt:LinLay", "layW:mPrt", "layH:0", "mrg:50,0,50,0",
            "orn:ver", "wei:3", "WeiSum:10"};


    private static final String[] PARAM_TOOLBAR = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:56",
            "backC:WHITE"};
    private static final String[] PARAM_TOOLBAR_RL = {"prt:Toolbar", "layW:mPrt", "layH:mPrt"};

    private static final String[] PARAM_BACK = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:56", "parH:56", "pad:5,5,5,5"};
    private static final String[] PARAM_LOGO = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "layH:56", "mrg:56,0,56,0", "pad:5,5,5,5",
            "Rule:CEN_PAR"};
    private static final String[] PARAM_AVATAR = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:56", "parH:56", "pad:5,5,5,5",
            "BordC:GRAY_THREE", "BordW:2", "img:avatar", "Rule:RIGHT"};

    private static final String[] PARAM_SELECT_PHOTO_USER = {"prt:LinLay", "layW:mPrt", "layH:0", "mrg:50,0,50,0",
            "wei:3", "GRAV:HOR", "img:avatar"};
    private static final String[] PARAM_SELECT_NAME_USER = {"prt:LinLay", "layW:mPrt", "layH:0", "mrg:50,0,50,0",
            "wei:2", "GRAV:HOR", "grv:CHCV", "txtC:GRAY_THREE"};


    private static final String[] PARAM_PIN_HOR = {"prt:LinLay", "layW:mPrt", "layH:0", "orn:hor", "WeiSum:4",
            "wei:6", "GRAV:HOR"};

    private static final String[] PARAM_PIN_TV = {"prt:LinLay", "layW:0", "layH:mPrt",   "mrg:10,0,10,0",
            "wei:1", "grv:BCH", "txtC:RED_ONE", "txtS:30", "TyFa:BOLD", "Lines:1"};

    private static final String[] PARAM_LINE_HOR = {"prt:LinLay", "layW:mPrt", "layH:0", "orn:hor", "WeiSum:4",
            "wei:1", "GRAV:HOR"};

    private static final String[] PARAM_RED_LINE = {"prt:LinLay", "layW:0", "layH:wCnt",  "parH:1", "mrg:10,0,10,0",
            "wei:1", "backC:RED_ONE"};

    private static final String[] PARAM_QUICK_ACCESS_CODE = {"prt:LinLay", "layW:mPrt", "layH:0", //"parH:56",
            "wei:3", "GRAV:HOR", "grv:CHCV", "txtC:GRAY_THREE"};


    private static final String[] PARAM_NUMBER_VIEW = {"prt:LinLay", "layW:mPrt", "layH:0", "mrg:50,0,50,40",
            "WeiSum:4", "orn:ver", "wei:1"};
    private static final String[] PARAM_NUMBERS = {"prt:LinLay", "layW:mPrt", "layH:0", "WeiSum:3",
            "orn:hor", "wei:1"};
    private static final String[] PARAM_NUMB = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1", "pad:5,5,5,5"};
    private Context context;
    private CircleImageView civAvatar, civSelectPhotoUser;
    private TextView tvSelectNameUser, tv1, tv2, tv3, tv4;
    private Resources res;
    private ModelFamilyRoom modelFamilyRoom;
    public QuickAccessSignOutView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        //   this.setOrientation(VERTICAL);
        this.setBackgroundColor(Color.rgb(255, 255, 255));
        //   this.setWeightSum(2);
        this.context = context;
        this.res = res;
        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM_MAIN_LL);
        this.addView(linearLayout);

        ///////////////////////        CONTAINER          /////////////////////
        LinearLayout llContainer = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER);
        linearLayout.addView(llContainer);



        Toolbar toolbar = new CustomToolbar().onCustom(context, PARAM_TOOLBAR);
        this.addView(toolbar);
        RelativeLayout rlToolbar = new CustomRelativeLayout().onCustom(context, PARAM_TOOLBAR_RL);
        toolbar.addView(rlToolbar);

        ImageView ivBack = new CustomImageView().onCustom(context, PARAM_BACK, R.mipmap.red_close);
        rlToolbar.addView(ivBack);
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onBackButtonClick();
                }
            }
        });

        ImageView ivLogo = new CustomImageView().onCustom(context, PARAM_LOGO, R.drawable.logo);
        rlToolbar.addView(ivLogo);

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

        //////////////////      TOP CONTAINER        ///////////////////
        LinearLayout llTop = new CustomLinearLayout().onCustom(context, PARAM_TOP);
        llContainer.addView(llTop);

        civSelectPhotoUser = new CustomCircleImageView().onCustom(context, PARAM_SELECT_PHOTO_USER);
        llTop.addView(civSelectPhotoUser);

        tvSelectNameUser = new CustomTextView().onCustom(context, PARAM_SELECT_NAME_USER, "Welcome");
        llTop.addView(tvSelectNameUser);

        //////////////////      BOTTOM CONTAINER        ///////////////////
        LinearLayout llBottom = new CustomLinearLayout().onCustom(context, PARAM_BOTTOM);
        llContainer.addView(llBottom);

        LinearLayout llPinHOR = new CustomLinearLayout().onCustom(context, PARAM_PIN_HOR);
        llBottom.addView(llPinHOR);

        tv1 = new CustomTextView().onCustom(context, PARAM_PIN_TV, null);
        llPinHOR.addView(tv1);

        tv2 = new CustomTextView().onCustom(context, PARAM_PIN_TV, null);
        llPinHOR.addView(tv2);

        tv3 = new CustomTextView().onCustom(context, PARAM_PIN_TV, null);
        llPinHOR.addView(tv3);

        tv4 = new CustomTextView().onCustom(context, PARAM_PIN_TV, null);
        llPinHOR.addView(tv4);

        LinearLayout llLineHOR = new CustomLinearLayout().onCustom(context, PARAM_LINE_HOR);
        llBottom.addView(llLineHOR);

        View v1 =  new CustomView().onCustom(context, PARAM_RED_LINE);
        llLineHOR.addView(v1);

        View v2 =  new CustomView().onCustom(context, PARAM_RED_LINE);
        llLineHOR.addView(v2);

        View v3 =  new CustomView().onCustom(context, PARAM_RED_LINE);
        llLineHOR.addView(v3);

        View v4 =  new CustomView().onCustom(context, PARAM_RED_LINE);
        llLineHOR.addView(v4);

        TextView tvPleaseEnterQuickAccessCode = new CustomTextView().onCustom(context, PARAM_QUICK_ACCESS_CODE, res.getString(R.string.enter_quick_access_code));
        llBottom.addView(tvPleaseEnterQuickAccessCode);

        /////////////////      NUMBER VIEW            ////////////////////
        LinearLayout llNumberView = new CustomLinearLayout().onCustom(context, PARAM_NUMBER_VIEW);
        linearLayout.addView(llNumberView);

        //////////////////     1   2   3     ////////////////////////
        LinearLayout llOneTwoThree = new CustomLinearLayout().onCustom(context, PARAM_NUMBERS);
        llNumberView.addView(llOneTwoThree);

        CircleImageView civ1 = new CustomCircleImageView().onCustom(context, PARAM_NUMB);
        llOneTwoThree.addView(civ1);
        civ1.setImageResource(R.mipmap.numb_one);
        civ1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onEnterNumber(1);
            }
        });

        CircleImageView civ2 = new CustomCircleImageView().onCustom(context, PARAM_NUMB);
        llOneTwoThree.addView(civ2);
        civ2.setImageResource(R.mipmap.numb_two);
        civ2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onEnterNumber(2);
            }
        });

        CircleImageView civ3 = new CustomCircleImageView().onCustom(context, PARAM_NUMB);
        llOneTwoThree.addView(civ3);
        civ3.setImageResource(R.mipmap.numb_three);
        civ3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onEnterNumber(3);
            }
        });

        //////////////////     4   5   6     ////////////////////////
        LinearLayout llFourFiveSix = new CustomLinearLayout().onCustom(context, PARAM_NUMBERS);
        llNumberView.addView(llFourFiveSix);

        CircleImageView civ4 = new CustomCircleImageView().onCustom(context, PARAM_NUMB);
        llFourFiveSix.addView(civ4);
        civ4.setImageResource(R.mipmap.numb_four);
        civ4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onEnterNumber(4);
            }
        });

        CircleImageView civ5 = new CustomCircleImageView().onCustom(context, PARAM_NUMB);
        llFourFiveSix.addView(civ5);
        civ5.setImageResource(R.mipmap.numb_five);
        civ5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onEnterNumber(5);
            }
        });

        CircleImageView civ6 = new CustomCircleImageView().onCustom(context, PARAM_NUMB);
        llFourFiveSix.addView(civ6);
        civ6.setImageResource(R.mipmap.numb_six);
        civ6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onEnterNumber(6);
            }
        });

        //////////////////     7   8   9     ////////////////////////
        LinearLayout llSevenEightNine = new CustomLinearLayout().onCustom(context, PARAM_NUMBERS);
        llNumberView.addView(llSevenEightNine);

        CircleImageView civ7 = new CustomCircleImageView().onCustom(context, PARAM_NUMB);
        llSevenEightNine.addView(civ7);
        civ7.setImageResource(R.mipmap.numb_seven);
        civ7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onEnterNumber(7);
            }
        });

        CircleImageView civ8 = new CustomCircleImageView().onCustom(context, PARAM_NUMB);
        llSevenEightNine.addView(civ8);
        civ8.setImageResource(R.mipmap.numb_eight);
        civ8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onEnterNumber(8);
            }
        });

        CircleImageView civ9 = new CustomCircleImageView().onCustom(context, PARAM_NUMB);
        llSevenEightNine.addView(civ9);
        civ9.setImageResource(R.mipmap.numb_nine);
        civ9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onEnterNumber(9);
            }
        });

        //////////////////        0    <-    ////////////////////////
        LinearLayout llZeroBackSpace = new CustomLinearLayout().onCustom(context, PARAM_NUMBERS);
        llNumberView.addView(llZeroBackSpace);

        CircleImageView civLOL = new CustomCircleImageView().onCustom(context, PARAM_NUMB);
        llZeroBackSpace.addView(civLOL);


        CircleImageView civ0 = new CustomCircleImageView().onCustom(context, PARAM_NUMB);
        llZeroBackSpace.addView(civ0);
        civ0.setImageResource(R.mipmap.numb_zero);
        civ0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onEnterNumber(0);
            }
        });

        ImageView civBackSpace = new CustomImageView().onCustom(context, PARAM_NUMB, R.mipmap.numb_back_space);
        llZeroBackSpace.addView(civBackSpace);
        civBackSpace.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackSpaceNumber();
            }
        });
        // civBackSpace.setImageResource(R.mipmap.numb_one);
    }

    public QuickAccessSignOutView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public QuickAccessSignOutView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public QuickAccessSignOutView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void onEnterNumber(int number) {
        if (TextUtils.isEmpty(tv1.getText().toString())) {
            tv1.setText(String.valueOf(number));
        } else if (TextUtils.isEmpty(tv2.getText().toString())) {
            tv2.setText(String.valueOf(number));
        } else if (TextUtils.isEmpty(tv3.getText().toString())) {
            tv3.setText(String.valueOf(number));
        } else if (TextUtils.isEmpty(tv4.getText().toString())) {
            tv4.setText(String.valueOf(number));
            if (modelFamilyRoom != null) {
                String pin = tv1.getText().toString() + tv2.getText().toString() + tv3.getText().toString() + tv4.getText().toString();
                /*if (pin.equals(modelFamilyRoom.getPin())) {
                    if (onItemClickListener != null && modelFamilyRoom != null) {
                        onItemClickListener.onAccessOkClick(modelFamilyRoom);
                    }
                } else {
                    if (onItemClickListener != null) {
                        onItemClickListener.onMessageClick(res.getString(R.string.invalid_access_code));
                        onTextViewClear();
                    }
                }*/
            }
        }
    }
    private void onBackSpaceNumber(){
        if (!TextUtils.isEmpty(tv4.getText().toString())) {
            tv4.setText("");
        } else if (!TextUtils.isEmpty(tv3.getText().toString())) {
            tv3.setText("");
        } else if (!TextUtils.isEmpty(tv2.getText().toString())) {
            tv2.setText("");
        } else if (!TextUtils.isEmpty(tv1.getText().toString())) {
            tv1.setText("");
        }
    }
    public void setData(ModelFamilyRoom modelFamilyRoom) {
        if (modelFamilyRoom != null) {
            this.modelFamilyRoom = modelFamilyRoom;
            //Picasso.with(context).load(modelFamilyRoom.getAvatar()).fit().centerInside().into(civSelectPhotoUser);
            //tvSelectNameUser.setText(modelFamilyRoom.getName() + ",\n" + res.getString(R.string.goodbye));
        }
    }
    private void onTextViewClear() {
        tv1.setText("");
        tv2.setText("");
        tv3.setText("");
        tv4.setText("");
    }
}
