package kz.tech.smartgrades.parents_module.contracts.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.models.ModelContract;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomScrollView;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;

public class GoodDealDialog extends Dialog {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(int position, ModelContract modelContract);
        void onRemoveContractIdClick(ModelContract modelContract);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt", "backC:WHITE"};
    private static final String[] PARAM_TOOLBAR = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:50", "backC:WHITE"};
    private static final String[] PARAM_TOOLBAR_RL = {"prt:Toolbar", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_LEFT_BUTTON = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "pad:5,5,5,5"};
    private static final String[] PARAM_TITLE = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:50,0,50,0", "pad:5,5,5,5",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_CONTAINER_SV = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:0,55,0,100"};
    private static final String[] PARAM_CONTAINER_LL = {"prt:ScrLay", "layW:mPrt", "layH:mPrt", "orn:ver"};
    private static final String[] PARAM_GOOD_DEAL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:100", "mrg:10,0,10,0",
            "txtC:RED_ONE", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_IMAGES = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "orn:hor", "WeiSum:5", "mrg:10,0,10,0"};
    private static final String[] PARAM_IMAGE_FAMILY = {"prt:LinLay", "layW:0", "layH:mPrt",  "wei:1", "parH:100",
            "BordC:GRAY_THREE", "BordW:2", "img:avatar", "pad:5,5,5,5"};
    private static final String[] PARAM_IMAGE_GOOD_DEAL = {"prt:LinLay", "layW:0", "layH:mPrt",  "wei:3", "parH:150"};
    private static final String[] PARAM_REMEMBER = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:30", "mrg:10,0,10,0",
            "txtC:GRAY_THREE", "grv:CHCV"};
    private static final String[] PARAM_TRY = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50", "mrg:10,0,10,0",
            "txtC:BLACK", "grv:CHCV"};
    private static final String[] PARAM_BACK_MAIN = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parW:150", "parH:40",  "mrg:5,40,5,20",
            "GRAV:HOR", "txtC:WHITE", "grv:CHCV", "backR:RED_ONE"};
    private static final String[] PARAM_VIEW_LINE = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:1", "backC:GRAY_THREE"};
    private static final String[] PARAM_CONTAINER_BOTTOM_LL = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:50", "mrg:0,0,0,50",
            "WeiSum:2", "orn:hor", "Rule:BOTTOM"};
    private static final String[] PARAM_PRINT = {"prt:LinLay", "layW:0", "layH:wCnt", "parH:50", "pad:10,5,5,5", "wei:1",
            "txtC:BLACK", "grv:LCV", "img:print"};
    private static final String[] PARAM_CANCEL_CONTRACT = {"prt:LinLay", "layW:0", "layH:wCnt", "parH:50", "pad:10,5,5,5",
            "txtC:BLACK", "grv:LCV", "img:close", "wei:1"};
    private static final String[] PARAM_CLOSE = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parW:120", "parH:40", "mrg:0,0,0,5",
            "Rule:BOTTOM", "Rule:CEN_HOR", "txtC:WHITE", "grv:CHCV", "backR:RED_ONE"};
    private Dialog dialog;
    public GoodDealDialog(@NonNull Context context) {
        super(context);
    }
    public GoodDealDialog(@NonNull Context con, int themeResId, Resources res, ModelContract m, String p, String c) {
        super(con, themeResId);
        dialog = this;
        RelativeLayout rlContainer = new CustomRelativeLayout().onCustom(con, PARAM);
        this.setContentView(rlContainer);

        Toolbar toolbar = new CustomToolbar().onCustom(con, PARAM_TOOLBAR);
        rlContainer.addView(toolbar);
        RelativeLayout rlToolbar = new CustomRelativeLayout().onCustom(con, PARAM_TOOLBAR_RL);
        toolbar.addView(rlToolbar);
        ImageView ivLeftButton = new CustomImageView().onCustom(con, PARAM_LEFT_BUTTON, R.mipmap.red_arrow_left);
        rlToolbar.addView(ivLeftButton);
        ivLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView tvTitle = new CustomTextView().onCustom(con, PARAM_TITLE, res.getString(R.string.contract_signing));
        rlToolbar.addView(tvTitle);

        //  TOP
        View v1 = new CustomView().onCustom(con, PARAM_VIEW_LINE);
        rlContainer.addView(v1);

        ScrollView svGoodDeal = new CustomScrollView().onCustom(con, PARAM_CONTAINER_SV);
        rlContainer.addView(svGoodDeal);

        LinearLayout linearLayout = new CustomLinearLayout().onCustom(con, PARAM_CONTAINER_LL);
        svGoodDeal.addView(linearLayout);

        TextView tvGoodDeal = new CustomTextView().onCustom(con, PARAM_GOOD_DEAL, res.getString(R.string.congratulations_the_contract_is_concluded));
        linearLayout.addView(tvGoodDeal);

        LinearLayout llImages = new CustomLinearLayout().onCustom(con, PARAM_IMAGES);
        linearLayout.addView(llImages);

        CircleImageView civParent = new CustomCircleImageView().onCustom(con, PARAM_IMAGE_FAMILY);
        llImages.addView(civParent);
        if (p != null) {
            Picasso.get().load(p).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civParent);
        }

        ImageView ivGoodDeal = new CustomImageView().onCustom(con, PARAM_IMAGE_GOOD_DEAL, R.mipmap.good_deal);
        llImages.addView(ivGoodDeal);

        CircleImageView civChild = new CustomCircleImageView().onCustom(con, PARAM_IMAGE_FAMILY);
        llImages.addView(civChild);
        if (c != null) {
            Picasso.get().load(c).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civChild);
        }

        TextView tvRemember = new CustomTextView().onCustom(con, PARAM_REMEMBER, res.getString(R.string.remember_to_help_your_child));
        linearLayout.addView(tvRemember);

        TextView tvTry = new CustomTextView().onCustom(con, PARAM_TRY, res.getString(R.string.try_credit_to_the_child));
        linearLayout.addView(tvTry);

      /*  TextView tvBackToTheMain = new CustomTextView().onCustom(con, PARAM_BACK_MAIN, res.getString(R.string.to_the_main));
        linearLayout.addView(tvBackToTheMain);
        tvBackToTheMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.dismiss();
            }
        });*/
        //  BOTTOM
        LinearLayout llBottom = new CustomLinearLayout().onCustom(con, PARAM_CONTAINER_BOTTOM_LL);
        rlContainer.addView(llBottom);

        TextView tvPrint = new CustomTextView().onCustom(con, PARAM_PRINT, res.getString(R.string.print_contract));
        llBottom.addView(tvPrint);
        tvPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(con, "Beta", Toast.LENGTH_SHORT).show();

            }
        });

        TextView tvCancelContract = new CustomTextView().onCustom(con, PARAM_CANCEL_CONTRACT, res.getString(R.string.cancel_contract));
        llBottom.addView(tvCancelContract);
        tvCancelContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    if (m != null) {
                        onItemClickListener.onRemoveContractIdClick(m);
                    }
                }
            }
        });

        TextView tvClose = new CustomTextView().onCustom(con, PARAM_CLOSE, res.getString(R.string.to_the_main));
        rlContainer.addView(tvClose);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    protected GoodDealDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
