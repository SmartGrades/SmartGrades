package kz.tech.smartgrades.childs_module;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomScrollView;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomCardView;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomRecyclerView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.var_resources.DimensionDP;

public class ChildMainView extends FrameLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onBackButtonClick();
        void onMenuClick(View view);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_TOOLBAR = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:56", "backC:WHITE"};
    private static final String[] PARAM_TOOLBAR_RL = {"prt:Toolbar", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_AVATAR = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "parW:56", "parH:56", "pad:5,5,5,5",
             "BordC:GRAY_THREE", "BordW:2", "img:avatar", "Rule:RIGHT"};
    private static final String[] PARAM_LOGO = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "layH:56", "mrg:56,0,56,0", "pad:5,5,5,5",
            "Rule:CEN_PAR"};

    private static final String[] PARAM_CONTAINER_SV = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,61,0,50"};
    private static final String[] PARAM_CONTAINER_LL = {"prt:ScrLay", "layW:mPrt", "layH:mPrt", "orn:ver"};

    private static final String[] PARAM_CONTAINER_CV_COINS = {"prt:LinLay", "layW:mPrt", "layH:wCnt","mrg:5,5,5,5",
            "Radius:10", "pad:5,5,5,5"};
    private static final String[] PARAM_COINS_LL = {"prt:CardView", "layW:mPrt", "layH:wCnt", "orn:ver"};
    private static final String[] PARAM_TEXT_COINS = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50", "pad:5,5,5,5",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_RV_COINS = {"prt:LinLay", "layW:wCnt", "layH:wCnt",
            "hfs:true", "GridC:9", "layMan:glm", "GRAV:CORE"};
    private static final String[] PARAM_COINS_TIME = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "GRAV:HOR",
            "txtC:BLACK", "grv:CHCV", "txtS:14"};
    private static final String[] PARAM_MSG_PLUS = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "GRAV:HOR", "mrg:0,0,0,10"};


    private static final String[] PARAM_RV_CONTRACTS = {"prt:LinLay", "layW:mPrt", "layH:wCnt",
            "hfs:true", "layMan:llm"};

    private static final String[] PARAM_BOTTOM_LL = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:50", "orn:ver",
            "backC:WHITE", "GRAV:BOTTOM"};
    private static final String[] PARAM_BOTTOM_IMG = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:30", "GRAV:HOR"};
    private static final String[] PARAM_BOTTOM_TXT = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:20",
            "txtC:BLUE_ONE", "grv:CHCV"};

    ////////////          BANK AND PIGGY            //////////////
    private static final String[] PARAM_CONTAINER_BANK_PIGGY = {"prt:LinLay", "layW:mPrt", "layH:wCnt",
            "orn:hor", "WeiSum:2"};
    ///////          BANK         //////////
    private static final String[] PARAM_CONTAINER_BANK_PIGGY_CV = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1", "mrg:5,5,10,5"};
    private static final String[] PARAM_CONTAINER_BANK_PIGGY_FL = {"prt:CardView", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_BANK_PIGGY_TITLE = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:30",
            "txtC:BLACK", "grv:CHCV", "txtS:14"};
    private static final String[] PARAM_BANK_PIGGY_COUNT = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "parW:40", "parH:40",
            "txtC:GRAY_THREE", "grv:CHCV", "txtS:18", "GRAV:RIGHT"};
    private static final String[] PARAM_BANK_PIGGY_ICON = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parW:50", "parH:50", "mrg:0,30,0,20",
            "GRAV:HOR"};
    private static final String[] PARAM_RV_BANK_PIGGY = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:10,100,10,0",
            "hfs:true", "GridC:8", "layMan:glm"};



    ////////////         CONTAINER CONTRACTS          //////////////////
    private static final String[] PARAM_CONTAINER_FOR_CONTRACTS = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "orn:ver"};

    ////////////          NO CONTRACTS         //////////////////
    private static final String[] PARAM_CONTAINER_NO_CONTRACTS_CV = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:5,5,5,5"};
    private static final String[] PARAM_CONTAINER_NO_CONTRACTS_LL = {"prt:CardView", "layW:mPrt", "layH:wCnt", "orn:ver"};
    private static final String[] PARAM_CONTAINER_NO_CONTRACTS_TITLE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:50,10,50,10",
            "txtC:BLACK", "grv:CHCV", "txtS:14"};
    private static final String[] PARAM_CONTAINER_NO_CONTRACTS_TEXT = {"prt:LinLay", "layW:mPrt", "layH:wCnt",  "mrg:30,0,30,10",
            "txtC:GRAY_THREE", "grv:CHCV", "txtS:12"};


    private Toolbar toolbar;
    private Context context;
    private Resources res;
    private CircleImageView civAvatar;


    private RecyclerView rvCoins, rvContracts;
    public RecyclerView getCoins() {
        return rvCoins;
    }
    public RecyclerView getRvContracts() {
        return rvContracts;
    }
    private TextView tvTimeCoins;

    private LinearLayout linearLayout, llContainerForContracts;
    private CardView cvContracts, cvCoins;
    public CardView getCardViewCoins() {
        return cvCoins;
    }

    private ImageView ivPiggyIcon, ivBankIcon;
    public ImageView getPiggyIcon() {
        return ivPiggyIcon;
    }
    public ImageView getBankIcon() {
        return ivBankIcon;
    }
    private RecyclerView rvBank, rvPiggy;
    public RecyclerView getBank() {
        return rvBank;
    }
    public RecyclerView getPiggy() {
        return rvPiggy;
    }
    private TextView tvBankCount, tvPiggyCount;
    private CardView cvPiggy, cvBank;
    public CardView getCardViewBank() {
        return cvBank;
    }
    public CardView getCardViewPiggy() {
        return cvPiggy;
    }

    private LinearLayout llBottom;

    public ChildMainView(Context context, Resources res) {
        super(context);
        this.res = res;

        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        this.context = context;
        toolbar = new CustomToolbar().onCustom(context, PARAM_TOOLBAR);
        this.addView(toolbar);
        RelativeLayout rlToolbar = new CustomRelativeLayout().onCustom(context, PARAM_TOOLBAR_RL);
        toolbar.addView(rlToolbar);

        civAvatar = new CustomCircleImageView().onCustom(context, PARAM_AVATAR);
        rlToolbar.addView(civAvatar);
        civAvatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onMenuClick(view);
                }
            }
        });
     //   ImageView ivLogo = new CustomImageView().onCustom(context, PARAM_LOGO, R.drawable.logo);
        //rlToolbar.addView(ivLogo);

        ScrollView scrollView = new CustomScrollView().onCustom(context, PARAM_CONTAINER_SV);
        this.addView(scrollView);

        linearLayout = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        scrollView.addView(linearLayout);

        /////////////////////           COINS           //////////////////////
        cvCoins = new CustomCardView().onCustom(context, PARAM_CONTAINER_CV_COINS);
        linearLayout.addView(cvCoins);
        cvCoins.setRadius(DimensionDP.sizeDP(10, context));

        LinearLayout llCoins = new CustomLinearLayout().onCustom(context, PARAM_COINS_LL);
        cvCoins.addView(llCoins);

        TextView tvCoins = new CustomTextView().onCustom(context, PARAM_TEXT_COINS, res.getString(R.string.coins));
        llCoins.addView(tvCoins);

        rvCoins = new CustomRecyclerView().onCustom(context, PARAM_RV_COINS);
        llCoins.addView(rvCoins);


        tvTimeCoins = new CustomTextView().onCustom(context, PARAM_COINS_TIME, null);
        llCoins.addView(tvTimeCoins);

     //   ImageView ivMsgPlus = new CustomImageView().onCustom(context, PARAM_MSG_PLUS, R.mipmap.msg_plus);
     //   llCoins.addView(ivMsgPlus);

        ////////////      BANK AND PIGGY       /////////////
        initBankAndPiggy();

        llContainerForContracts = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_FOR_CONTRACTS);
        linearLayout.addView(llContainerForContracts);

        ////////////////////////           CONTRACTS            //////////////
        onContractLoad();


        ////////////              BOTTOM              ///////////////////
        llBottom = new CustomLinearLayout().onCustom(context, PARAM_BOTTOM_LL);
        this.addView(llBottom);

        ImageView ivMsgPlus = new CustomImageView().onCustom(context, PARAM_BOTTOM_IMG, R.mipmap.msg_plus);
        llBottom.addView(ivMsgPlus);

        String call = "Обратиться к родителю";
        TextView tvCallParent = new CustomTextView().onCustom(context, PARAM_BOTTOM_TXT, call);
        llBottom.addView(tvCallParent);


    }

    public ChildMainView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public ChildMainView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChildMainView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void initBankAndPiggy() {
        LinearLayout llBankAndPiggy = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_BANK_PIGGY);
        linearLayout.addView(llBankAndPiggy);

        ////////      BANK      ///////////
        cvBank = new CustomCardView().onCustom(context, PARAM_CONTAINER_BANK_PIGGY_CV);
        llBankAndPiggy.addView(cvBank);

        FrameLayout flBank = new CustomFrameLayout().onCustom(context, PARAM_CONTAINER_BANK_PIGGY_FL);
        cvBank.addView(flBank);

        TextView tvBankTitle =  new CustomTextView().onCustom(context, PARAM_BANK_PIGGY_TITLE, res.getString(R.string.bank));
        flBank.addView(tvBankTitle);

        tvBankCount = new CustomTextView().onCustom(context, PARAM_BANK_PIGGY_COUNT, null);
        flBank.addView(tvBankCount);

        ivBankIcon = new CustomImageView().onCustom(context, PARAM_BANK_PIGGY_ICON, R.mipmap.bank);
        flBank.addView(ivBankIcon);

        ////////      PIGGY       ///////////
         cvPiggy = new CustomCardView().onCustom(context, PARAM_CONTAINER_BANK_PIGGY_CV);
        llBankAndPiggy.addView(cvPiggy);

        FrameLayout flPiggy = new CustomFrameLayout().onCustom(context, PARAM_CONTAINER_BANK_PIGGY_FL);
        cvPiggy.addView(flPiggy);

        TextView tvPiggyTitle =  new CustomTextView().onCustom(context, PARAM_BANK_PIGGY_TITLE, res.getString(R.string.piggy));
        flPiggy.addView(tvPiggyTitle);

        tvPiggyCount = new CustomTextView().onCustom(context, PARAM_BANK_PIGGY_COUNT, null);
        flPiggy.addView(tvPiggyCount);

        ivPiggyIcon = new CustomImageView().onCustom(context, PARAM_BANK_PIGGY_ICON, R.mipmap.piggy);
        flPiggy.addView(ivPiggyIcon);

        rvPiggy = new CustomRecyclerView().onCustom(context, PARAM_RV_BANK_PIGGY);
        flPiggy.addView(rvPiggy);
    }

    public void onContractLoad() {
        cvContracts = new CustomCardView().onCustom(context, PARAM_CONTAINER_CV_COINS);
        linearLayout.addView(cvContracts);
        cvContracts.setRadius(DimensionDP.sizeDP(10, context));
        cvContracts.setVisibility(GONE);

        LinearLayout llContracts = new CustomLinearLayout().onCustom(context, PARAM_COINS_LL);
        cvContracts.addView(llContracts);

        TextView tvContracts = new CustomTextView().onCustom(context, PARAM_TEXT_COINS, res.getString(R.string.contracts));
        llContracts.addView(tvContracts);

        //   View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        //   llContracts.addView(v1);

        rvContracts = new CustomRecyclerView().onCustom(context, PARAM_RV_CONTRACTS);
        llContracts.addView(rvContracts);
    }
    public void onContractEmpty() {
        CardView cvNoContracts = new CustomCardView().onCustom(context, PARAM_CONTAINER_NO_CONTRACTS_CV);
        llContainerForContracts.addView(cvNoContracts);

        LinearLayout llNoContracts = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_NO_CONTRACTS_LL);
        cvNoContracts.addView(llNoContracts);

        TextView tvNoContractsTitle = new CustomTextView().onCustom(context, PARAM_CONTAINER_NO_CONTRACTS_TITLE, res.getString(R.string.no_contracts_title));
        llNoContracts.addView(tvNoContractsTitle);

        TextView tvNoContractsText = new CustomTextView().onCustom(context, PARAM_CONTAINER_NO_CONTRACTS_TEXT, res.getString(R.string.no_contracts_text));
        llNoContracts.addView(tvNoContractsText);
    }
    public void onClearContainerForContracts() {
        llContainerForContracts.removeAllViewsInLayout();

    }
    public void onSelectContractStatus(boolean status) {
        if (status) {
            cvContracts.setVisibility(VISIBLE);
        } else {
            cvContracts.setVisibility(GONE);
        }
    }



    public void setAvatar(String image) {
        Picasso.get().load(image).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);
    }
    public void setDataCoins(String totalCoins, Resources res) {
        int numbMinute = Integer.parseInt(totalCoins);

        float result = ((int)(10*numbMinute)/20)/10;
        int hours = numbMinute / 60; //since both are ints, you get an int
        int minutes = numbMinute % 60;

        String dateCoins = "";
        if (hours > 0) { dateCoins += String.valueOf(hours) + res.getString(R.string.h); }
        if (minutes > 0) { dateCoins += String.valueOf(minutes) + res.getString(R.string.min); }
        tvTimeCoins.setText(dateCoins);

    }
    public void updateDataCoinsMinus(int coins, int numbMinute, Resources res) {
        if (numbMinute > 0) {
            numbMinute = numbMinute - coins;


            int hours = numbMinute / 60; //since both are ints, you get an int
            int minutes = numbMinute % 60;

            String dateCoins = "";
            if (hours > 0) { dateCoins += String.valueOf(hours) + res.getString(R.string.h); }
            if (minutes > 0) { dateCoins += String.valueOf(minutes) + res.getString(R.string.min); }
            tvTimeCoins.setText(dateCoins);
        }
    }
    public void updateDataCoinsPlus(int coins, int numbMinute, Resources res) {
        if (numbMinute > 0) {
            numbMinute = numbMinute + coins;


            int hours = numbMinute / 60; //since both are ints, you get an int
            int minutes = numbMinute % 60;

            String dateCoins = "";
            if (hours > 0) { dateCoins += String.valueOf(hours) + res.getString(R.string.h); }
            if (minutes > 0) { dateCoins += String.valueOf(minutes) + res.getString(R.string.min); }
            tvTimeCoins.setText(dateCoins);
        }
    }

    public void setPiggyCount(String count) {
        int totalCount = Integer.parseInt(count);
        if (totalCount > 20) {
            int coins = ((int) (10 * totalCount) / 20) / 10;
            tvPiggyCount.setText(String.valueOf(coins));
        } else if (totalCount == 0) {
            tvPiggyCount.setText("0");
        }
    }
    public void setBankCount(String count) {
        int totalCount = Integer.parseInt(count);
        if (totalCount > 20) {
            int coins = ((int) (10 * totalCount) / 20) / 10;
            tvBankCount.setText(String.valueOf(coins));
        } else if (totalCount == 0) {
            tvBankCount.setText("0");
        }
    }

    private ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1.1f, 1f, 1.1f);
    public void onCoinsToBankOrPiggyOn() {
        scaleAnimation.setDuration(1000);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        ivPiggyIcon.startAnimation(scaleAnimation);
        toolbar.setAlpha(0.5f);
        cvCoins.setAlpha(0.5f);
        llContainerForContracts.setAlpha(0.5f);
        llBottom.setAlpha(0.5f);
        cvBank.setAlpha(0.5f);
    }

    public void onCoinsToBankOrPiggyOff() {
        ivPiggyIcon.clearAnimation();
        toolbar.setAlpha(1f);
        cvCoins.setAlpha(1f);
        llContainerForContracts.setAlpha(1f);
        llBottom.setAlpha(1f);
        cvBank.setAlpha(1f);
    }


    public void onPiggyToCoinsOn() {
        toolbar.setAlpha(0.5f);

        cvPiggy.setAlpha(0.5f);
        cvBank.setAlpha(0.5f);
        llContainerForContracts.setAlpha(0.5f);
        llBottom.setAlpha(0.5f);
    }
    public void onPiggyToCoinsOff() {
        toolbar.setAlpha(1f);
        cvPiggy.setAlpha(1f);
        cvBank.setAlpha(1f);
        llContainerForContracts.setAlpha(1f);
        llBottom.setAlpha(1f);
    }
}
