package kz.tech.smartgrades.parents_module.contracts.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
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
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parents_module.coins.SquareAdapter;
import kz.tech.smartgrades.root.models.ModelContract;
import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomScrollView;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomRecyclerView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;
import kz.tech.smartgrades.root.ui.animation.GetButtonRipple;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;

public class SignInContractDialog extends Dialog {
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
    private static final String[] PARAM_CONTAINER_LL_LL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:10,0,10,0",
            "orn:hor", "WeiSum:8"};
    private static final String[] PARAM_IMAGE = {"prt:LinLay", "layW:0", "layH:mPrt", "GRAV:VER", "wei:1", "parH:50"};
    private static final String[] PARAM_RV = {"prt:LinLay", "layW:0", "layH:wCnt", "wei:4", "mrg:8,8,8,0",
            "hfs:true", "GridC:5", "layMan:glm"};
    private static final String[] PARAM_OK = {"prt:LinLay", "layW:0", "layH:mPrt", "GRAV:VER", "wei:2", "mrg:5,0,5,0",
            "parH:40", "txtC:WHITE", "grv:CHCV", "backR:RED_ONE"};
    private static final String[] PARAM_PRESENT = {"prt:LinLay", "layW:0", "layH:wCnt", "parH:100", "wei:4"};
    private static final String[] PARAM_CONTAINER_BOTTOM_LL = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:50", "mrg:0,0,0,50",
            "WeiSum:2", "orn:hor", "Rule:BOTTOM"};
    private static final String[] PARAM_PRINT = {"prt:LinLay", "layW:0", "layH:wCnt", "parH:50", "pad:10,5,5,5", "wei:1",
            "txtC:BLACK", "grv:LCV", "img:print"};
    private static final String[] PARAM_CANCEL_CONTRACT = {"prt:LinLay", "layW:0", "layH:wCnt", "parH:50", "pad:10,5,5,5",
            "txtC:BLACK", "grv:LCV", "img:close", "wei:1"};
    private static final String[] PARAM_DESCRIPTION = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:56",
            "txtC:GRAY_THREE", "grv:CHCV"};
    private static final String[] PARAM_VIEW_LINE = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:1", "backC:GRAY_THREE"};
    private static final String[] PARAM_CLOSE = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parW:120", "parH:40", "mrg:0,0,0,5",
            "Rule:BOTTOM", "Rule:CEN_HOR", "txtC:WHITE", "grv:CHCV", "backR:RED_ONE"};


    /*       GoodDealDialog dialog = new GoodDealDialog(
                            getActivity(), R.style.CustomDialog2, main.getResLanguage(),  modelContract, avatarParent, avatarChild);
                    dialog.show();


              */

    private CircleImageView civAvatarChild, civAvatarParent;
    private SquareAdapter squareAdapter;
    private TextView tvOkChild, tvOkParent;
    private ImageView ivPresent;
    private Dialog dialog;
    private boolean isParent = false, isChild = false;
    public SignInContractDialog(@NonNull Context context) {
        super(context);
    }
    public SignInContractDialog(Context con, int themeResId, Resources res, ModelContract m, String p, String c) {
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

        //  CENTER
        ScrollView svSigningContracts = new CustomScrollView().onCustom(con, PARAM_CONTAINER_SV);
        rlContainer.addView(svSigningContracts);

        LinearLayout linearLayout = new CustomLinearLayout().onCustom(con, PARAM_CONTAINER_LL);
        svSigningContracts.addView(linearLayout);

        TextView tvDescription = new CustomTextView().onCustom(con, PARAM_DESCRIPTION, res.getString(R.string.repeat_all_conditions));
        linearLayout.addView(tvDescription);

        //  CHILD
        LinearLayout llChild = new CustomLinearLayout().onCustom(con, PARAM_CONTAINER_LL_LL);
        linearLayout.addView(llChild);

        civAvatarChild = new CustomCircleImageView().onCustom(con, PARAM_IMAGE);
        llChild.addView(civAvatarChild);

        if (c != null) {
            Picasso.get().load(c).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatarChild);
        }


        ImageView ivArrowChild = new CustomImageView().onCustom(con, PARAM_IMAGE, R.mipmap.red_left_arrow);
        llChild.addView(ivArrowChild);

        RecyclerView recyclerView = new CustomRecyclerView().onCustom(con, PARAM_RV);
        llChild.addView(recyclerView);

        squareAdapter = new SquareAdapter(con);
        recyclerView.setAdapter(squareAdapter);
        String countSteps = m.getCountSteps();
        if (countSteps != null) {
            String[] step = countSteps.split("/");
            int count = Integer.parseInt(step[0]);
            squareAdapter.setData(count);
        }

        tvOkChild = new CustomTextView().onCustom(con, PARAM_OK, res.getString(R.string.i_agree));
        llChild.addView(tvOkChild);
        tvOkChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChild = true;
                onTextViewOkBackground(tvOkChild, false);
                if (onItemClickListener != null && m != null) {
                    onItemClickListener.onItemClick(1, m);//  CHILD
                }
                if (isParent) {
                    dialog.dismiss();
                    GoodDealDialog dialog = new GoodDealDialog(con, R.style.CustomDialog2, res,  m, p, c);
                    dialog.show();
                }
            }
        });

        //  PARENT
        LinearLayout llParent = new CustomLinearLayout().onCustom(con, PARAM_CONTAINER_LL_LL);
        linearLayout.addView(llParent);

        civAvatarParent = new CustomCircleImageView().onCustom(con, PARAM_IMAGE);
        llParent.addView(civAvatarParent);
        if (p != null) {
            Picasso.get().load(p).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatarParent);
        }

        ImageView ivArrowParent = new CustomImageView().onCustom(con, PARAM_IMAGE, R.mipmap.red_left_arrow);
        llParent.addView(ivArrowParent);

        ivPresent = new CustomImageView().onCustom(con, PARAM_PRESENT, 0);
        llParent.addView(ivPresent);
        String urlImage = m.getAvatar();
        if (urlImage != null) {
            Picasso.get().load(urlImage).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(ivPresent);
        }

        tvOkParent = new CustomTextView().onCustom(con, PARAM_OK, res.getString(R.string.i_agree));
        llParent.addView(tvOkParent);
        tvOkParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isParent = true;
                onTextViewOkBackground(tvOkParent, false);
                if (onItemClickListener != null && m != null) {
                    onItemClickListener.onItemClick(2, m);//  PARENT
                }
                if (isChild) {
                    dialog.dismiss();
                    GoodDealDialog dialog = new GoodDealDialog(con, R.style.CustomDialog2, res,  m, p, c);
                    dialog.show();
                }
            }
        });

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


        if (m.getCheckChild().equals("not")) {
            onTextViewOkBackground(tvOkChild, true);
        } else if (m.getCheckChild().equals("yes")) {
            onTextViewOkBackground(tvOkChild, false);
            isChild = true;
        }
        if (m.getCheckParent().equals("not")) {
            onTextViewOkBackground(tvOkParent, true);
        } else if (m.getCheckParent().equals("yes")) {
            onTextViewOkBackground(tvOkParent, false);
            isParent = true;
        }

    }
    protected SignInContractDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    private void onTextViewOkBackground(TextView textView, boolean backR) {
        if (backR) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textView.setBackgroundDrawable(new GetButtonRipple()
                        .getRipple(30f, 2, ColorsRGB.GRAY_THREE,
                                GetColorInText.getIntColor("RED_ONE"),//  First Color
                                ColorsRGB.BLUE_ONE));//  Second Color
            } else {
                textView.setBackgroundColor(GetColorInText.getIntColor("RED_ONE"));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textView.setBackgroundDrawable(new GetButtonRipple()
                        .getRipple(30f, 2, ColorsRGB.GRAY_THREE,
                                GetColorInText.getIntColor("GRAY_THREE"),//  First Color
                                ColorsRGB.BLUE_ONE));//  Second Color
            } else {
                textView.setBackgroundColor(GetColorInText.getIntColor("GRAY_THREE"));
            }
            textView.setEnabled(false);
        }
    }
}
