package kz.tech.smartgrades.parents_module.contracts;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomScrollView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomRecyclerView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;


public class ContractsView extends FrameLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onNewContractClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    ///////////////              CREATE CONTRACT             ///////////////////////////
    private static final String[] PARAM_CREATE_CONTRACT_CONTAINER_RL = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "backC:WHITE", "mrg:0,85,0,0",};
    private static final String[] PARAM_CONTAINER_SV = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "mrg:0,0,0,100"};
    private static final String[] PARAM_CONTAINER_LL = {"prt:ScrLay", "layW:mPrt", "layH:wCnt", "orn:ver"};
    private static final String[] PARAM_CONTAINER_LL_LL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50", "orn:hor",
            "WeiSum:6", "mrg:8,15,8,15"};
    private static final String[] PARAM_CONTAINER_LL_LL_IV = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1"};
    private static final String[] PARAM_CONTAINER_LL_LL_TV = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:5",
            "grv:CHCV", "txtC:BLACK"};
    private static final String[] PARAM_BOTTOM_CONTAINER_CREATE = {"prt:RelLay", "layW:mPrt", "layH:wCnt",
            "parH:100", "orn:ver", "Rule:BOTTOM"};
    private static final String[] PARAM_CREATE_DETAIL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:30",
            "grv:CHCV", "txtC:GREEN_ONE"};
    private static final String[] PARAM_CREATE_CONTRACT = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:56,10,56,0",
            "parH:50", "grv:CHCV", "txtC:WHITE", "backR:RED_ONE"};

    ///////////////              LOAD CONTRACTS LIST RV             ///////////////////////////
    private static final String[] PARAM_LOAD_CONTRACTS_CONTAINER_RL = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,85,0,0"};
    private static final String[] PARAM_RV = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "hfs:true", "layMan:llm", "mrg:0,0,0,50"};
    private static final String[] PARAM_NEW_CONTRACT = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:60", "pad:5,5,5,5",
            "Rule:BOTTOM", "grv:BCH", "txtC:GRAY_THREE", "SctDrw:0,2,GRAY_THREE,WHITE"};
    private static final String[] PARAM_NEW_CONTRACT_IMAGE = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parW:40", "parH:40", "mrg:0,0,0,20",
            "pad:5,5,5,5", "Rule:BOTTOM", "Rule:CEN_HOR"};
    private Context context;
    public RecyclerView recyclerView;
    private RelativeLayout rlCreateContract, rlLoadContracts;
    private Resources res;
    public ContractsView(Context context, Resources res) {
        super(context);
        this.context = context;
        this.res = res;
        onCreateContractView();
        rlCreateContract.setVisibility(GONE);

        onLoadContractsView();
        rlLoadContracts.setVisibility(GONE);

    }

    public ContractsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ContractsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ContractsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void onCreateContractView() {
        rlCreateContract = new CustomRelativeLayout().onCustom(context, PARAM_CREATE_CONTRACT_CONTAINER_RL);
        this.addView(rlCreateContract);

        ScrollView scrollView = new CustomScrollView().onCustom(context, PARAM_CONTAINER_SV);
        rlCreateContract.addView(scrollView);

        LinearLayout liContainer = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        scrollView.addView(liContainer);


        int[] images = {
                R.mipmap.purpose,
                R.mipmap.baby_conditions,
                R.mipmap.control_and_monitoring,
                R.mipmap.present,
                R.mipmap.medal};
        String[] texts = getResources().getStringArray(R.array.about_contract);
        for (int i = 0; i < 5; i++) {
            LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL_LL);
            liContainer.addView(linearLayout);

            ImageView imageView = new CustomImageView().onCustom(context, PARAM_CONTAINER_LL_LL_IV, images[i]);
            linearLayout.addView(imageView);

            TextView textView = new CustomTextView().onCustom(context, PARAM_CONTAINER_LL_LL_TV, texts[i]);
            linearLayout.addView(textView);
        }

        LinearLayout llBottom = new CustomLinearLayout().onCustom(context, PARAM_BOTTOM_CONTAINER_CREATE);
        rlCreateContract.addView(llBottom);

        TextView tvDetail = new CustomTextView().onCustom(context, PARAM_CREATE_DETAIL, res.getString(R.string.more_details));
        llBottom.addView(tvDetail);

        TextView tvCreateContract = new CustomTextView().onCustom(context, PARAM_CREATE_CONTRACT, res.getString(R.string.go_to_signing));
        llBottom.addView(tvCreateContract);
        tvCreateContract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onNewContractClick();
                }
            }
        });
    }
    private void onLoadContractsView() {
        rlLoadContracts = new CustomRelativeLayout().onCustom(context, PARAM_LOAD_CONTRACTS_CONTAINER_RL);
        this.addView(rlLoadContracts);
        recyclerView = new CustomRecyclerView().onCustom(context, PARAM_RV);
        rlLoadContracts.addView(recyclerView);
        TextView tvNewContract = new CustomTextView().onCustom(context, PARAM_NEW_CONTRACT, res.getString(R.string.new_contract));
        rlLoadContracts.addView(tvNewContract);
        tvNewContract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onNewContractClick();
                }
            }
        });
        ImageView ivNewContract = new CustomImageView().onCustom(context, PARAM_NEW_CONTRACT_IMAGE, R.mipmap.plus_oval);
        rlLoadContracts.addView(ivNewContract);
    }
    public void setSelectRL(String text) {
        rlCreateContract.setVisibility(GONE);
        rlLoadContracts.setVisibility(GONE);
        switch (text) {
            case "CreateContract":
                rlCreateContract.setVisibility(VISIBLE);
                break;
            case "LoadContracts":
                rlLoadContracts.setVisibility(VISIBLE);
                break;
        }
    }
}
