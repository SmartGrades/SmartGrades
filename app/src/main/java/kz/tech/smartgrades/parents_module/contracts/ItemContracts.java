package kz.tech.smartgrades.parents_module.contracts;

import android.content.Context;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomCardView;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomRecyclerView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;
import kz.tech.smartgrades.root.var_resources.DimensionDP;
import kz.tech.smartgrades.root.var_resources.VarID;

public class ItemContracts extends FrameLayout {
    private static final String[] PARAM = {"layW:mPrt", "layH:wCnt",  "mrg:0,5,0,5"};
    private static final String[] PARAM_CONTAINER = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "orn:ver"};

    private static final String[] PARAM_TITLE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:0,5,0,5",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};

    private static final String[] PARAM_WARNING  = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:0,20,0,20",
            "txtC:RED_ONE", "grv:CHCV", "txtS:14"};

    private static final String[] PARAM_CONTAINER_RL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "backC:WHITE"};

    private static final String[] PARAM_CONTAINER_LL_FOR_CV = {"prt:CardView", "layW:mPrt", "layH:wCnt",
            "orn:ver", "mrg:0,0,0,20"};

    //  CHILD
    private static final String[] PARAM_CONTAINER_CHILD_CV = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "mrg:5,5,50,0",
               "Rule:TOP", "pad:5,5,5,5", "Radius:8"};
    private static final String[] PARAM_RV = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:58,8,58,0",
           "hfs:true", "GridC:5", "layMan:glm"};
    private static final String[] PARAM_DATE_DONE_CHILD = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:0,5,0,5",
            "txtC:BLACK", "grv:CHCV"};
    private static final String[] PARAM_MOVE_LEFT = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "parW:50", "parH:50",
            "Rule:TOP", "Rule:RIGHT", "pad:5,5,5,5"};
    private static final String[] PARAM_AVATAR_CHILD = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "parW:50", "parH:50",
            "mrg:0,50,0,0", "Rule:TOP", "Rule:RIGHT", "BordC:GRAY_THREE", "BordW:2", "img:avatar", "pad:5,5,5,5"};
    //////////////             GOOD          //////////////
    private static final String[] PARAM_CHILD_GOOD = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "parW:120","parH:30",
            "mrg:0,0,40,0", "Rule:RIGHT", "txtC:WHITE", "grv:CHCV", "backC:RED_ONE", "ABOVE:141402"};
    private static final String[] PARAM_CHILD_VIEW_FUCK = {"prt:RelLay", "layW:mPrt", "layH:wCnt",  "parH:30", "BELOW:141401"};

    //  PARENT
    private static final String[] PARAM_CONTAINER_PARENT_CV = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "mrg:50,5,5,10",
            "Rule:TOP", "pad:5,5,5,5", "Radius:8"};
    private static final String[] PARAM_AVATAR_PARENT = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "parW:50", "parH:50", "mrg:0,0,0,0",
            "ABOVE:141405", "Rule:LEFT", "BordC:GRAY_THREE", "BordW:2", "img:avatar", "pad:5,5,5,5"};
    // IMAGE PRESENT
    private static final String[] PARAM_PRESENT_IMAGE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parW:100",
            "parH:100", "GRAV:HOR", "mrg:0,10,0,0"};//, "pad:5,5,5,5"
    private static final String[] PARAM_FULFILLS = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parW:200", "parH:30",
            "GRAV:HOR", "txtC:WHITE", "SctDrw:10,0,RED_ONE,RED_ONE", "grv:CHCV", "VIS:GONE"};
    private static final String[] PARAM_PARENT_GOOD = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "parW:120","parH:30",
            "mrg:40,0,0,20", "Rule:LEFT", "txtC:WHITE", "grv:CHCV", "backC:RED_ONE",  "ABOVE:141405"};
    private static final String[] PARAM_CHILD_VIEW_SUCK = {"prt:RelLay", "layW:mPrt", "layH:wCnt",  "parH:30", "BELOW:141404"};


    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:1",
            "backC:GRAY_THREE"};

    public ItemContracts(@NonNull Context context) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));

        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER);
        this.addView(linearLayout);

      //  View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
      //  linearLayout.addView(v1);
        TextView tvContractTitle = new CustomTextView().onCustom(context, PARAM_TITLE, null);
        linearLayout.addView(tvContractTitle);
        tvContractTitle.setId(VarID.ID_TV_CONTRACT_TITLE);//

        //  CHILD
        RelativeLayout rlContainerChild = new CustomRelativeLayout().onCustom(context, PARAM_CONTAINER_RL);
        linearLayout.addView(rlContainerChild);

        CardView cvChild = new CustomCardView().onCustom(context, PARAM_CONTAINER_CHILD_CV);
        rlContainerChild.addView(cvChild);
        cvChild.setId((int)141401);

        LinearLayout llChild = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL_FOR_CV);
        cvChild.addView(llChild);

        RecyclerView recyclerView = new CustomRecyclerView().onCustom(context, PARAM_RV);
        llChild.addView(recyclerView);
        recyclerView.setId(VarID.ID_RV_ITEM_CONTRACTS);

        TextView tvDateDoneChild = new CustomTextView().onCustom(context, PARAM_DATE_DONE_CHILD, null);
        llChild.addView(tvDateDoneChild);
        tvDateDoneChild.setId(VarID.ID_TV_CONTRACT_DATE_CHILD);

        View vFUCK = new CustomView().onCustom(context, PARAM_CHILD_VIEW_FUCK);
        rlContainerChild.addView(vFUCK);
        vFUCK.setId((int)141402);

        ///////////           GOOD           /////////////
        TextView tvChildGOOD = new CustomTextView().onCustom(context, PARAM_CHILD_GOOD, null);
        rlContainerChild.addView(tvChildGOOD);
        tvChildGOOD.bringToFront();
       // rlContainerChild.bringChildToFront(tvChildGOOD);
        tvChildGOOD.setFitsSystemWindows(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tvChildGOOD.setElevation(DimensionDP.sizeDP(10,context));
        }
        tvChildGOOD.setRotation(330f);
        tvChildGOOD.setId((int)141403);
        tvChildGOOD.setVisibility(GONE);

        ImageView ivMoveLeft = new CustomImageView().onCustom(context, PARAM_MOVE_LEFT, R.mipmap.move_red);
        rlContainerChild.addView(ivMoveLeft);
        ivMoveLeft.setId(VarID.ID_TV_CONTRACT_MOVE);

        CircleImageView civChild = new CustomCircleImageView().onCustom(context, PARAM_AVATAR_CHILD);
        rlContainerChild.addView(civChild);
        civChild.setId(VarID.ID_TV_CONTRACT_AVATAR_CHILD);//


        //  PARENT
        RelativeLayout rlContainerParent = new CustomRelativeLayout().onCustom(context, PARAM_CONTAINER_RL);
        linearLayout.addView(rlContainerParent);

        CardView cvParent = new CustomCardView().onCustom(context, PARAM_CONTAINER_PARENT_CV);
        rlContainerParent.addView(cvParent);
        cvParent.setId((int)141404);

        LinearLayout llParent = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL_FOR_CV);
        cvParent.addView(llParent);

        //////////           IMAGE DONE             /////////////
        CircleImageView civPresentImage = new CustomCircleImageView().onCustom(context, PARAM_PRESENT_IMAGE);
        llParent.addView(civPresentImage);
        civPresentImage.setId(VarID.ID_CIV_CONTRACT_PRESENT_IMAGE);

        TextView tvFulfills = new CustomTextView().onCustom(context, PARAM_FULFILLS, null);
        llParent.addView(tvFulfills);
        tvFulfills.setId(VarID.ID_TV_CONTRACT_FULFILLS);


        TextView tvDateDoneParent = new CustomTextView().onCustom(context, PARAM_DATE_DONE_CHILD, null);
        llParent.addView(tvDateDoneParent);
        tvDateDoneParent.setId(VarID.ID_TV_CONTRACT_DATE_PARENT);

        CircleImageView civParent = new CustomCircleImageView().onCustom(context, PARAM_AVATAR_PARENT);
        rlContainerParent.addView(civParent);
        civParent.setId(VarID.ID_TV_CONTRACT_AVATAR_PARENT);//

        View vSUCK = new CustomView().onCustom(context, PARAM_CHILD_VIEW_SUCK);
        rlContainerParent.addView(vSUCK);
        vSUCK.setId((int)141405);

        ///////////             GOOD           //////////////
        TextView tvParentGOOD = new CustomTextView().onCustom(context, PARAM_PARENT_GOOD, "FUCK YOU");
        rlContainerParent.addView(tvParentGOOD);
        tvParentGOOD.bringToFront();
        // rlContainerChild.bringChildToFront(tvChildGOOD);
        tvParentGOOD.setFitsSystemWindows(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tvParentGOOD.setElevation(DimensionDP.sizeDP(10,context));
        }
        tvParentGOOD.setRotation(30f);
        tvParentGOOD.setId((int)141406);
        tvParentGOOD.setVisibility(GONE);

//
        View v5 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v5);

        TextView tvWarning = new CustomTextView().onCustom(context, PARAM_WARNING, null);
        linearLayout.addView(tvWarning);
        tvWarning.setId(VarID.ID_TV_CONTRACT_WARNING);
    }

    public ItemContracts(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemContracts(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ItemContracts(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
