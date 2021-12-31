package kz.tech.smartgrades.parents_module.family_group.dialogs_page.interaction_form;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.models.ModelChild;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class BottomDialogWard extends BottomSheetDialog {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "orn:ver"};
    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:1", "backC:GRAY_THREE"};

    private static final String[] PARAM_TITLE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:30",
            "grv:CHCV", "txtC:BLACK"};

    private static final String[] PARAM_CONTAINER_FL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50"};
    private static final String[] PARAM_AVATAR = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "mrg:20,0,0,0",
            "parW:40", "parH:40", "GRAV:LEFT_VER"};
    private static final String[] PARAM_NAME = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:80,0,50,0",
            "grv:LCV", "txtC:GRAY_THREE"};
    private static final String[] PARAM_CHECKBOX = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "mrg:0,0,10,0",
            "parW:30", "parH:30", "GRAV:RIGHT_VER", "RTL:RIGHT", "pad:5,5,5,5" };


    private static final String[] PARAM_CONTAINER_LL = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:50",
            "orn:hor", "WeiSum:2", "backC:WHITE"};
    private static final String[] PARAM_OK_CANCEL = {"prt:LinLay", "layW:0", "layH:mPrt",
            "grv:CHCV", "wei:1", "pad:5,5,5,5", "txtC:GREEN_ONE"};
    private BottomSheetDialog dialog;
    private Context context;
    private Resources res;
    private LinearLayout linearLayout;
    private boolean[] isChecked;

    public BottomDialogWard(Context context, Resources res, List<ModelChild> list, boolean[] isChecked) {
        super(context);
        this.context = context;
        this.res = res;
        this.isChecked = isChecked;
        dialog = this;



        linearLayout = new CustomLinearLayout().onCustom(context, PARAM);
        this.setContentView(linearLayout);

    //    View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
    //    linearLayout.addView(v1);
        TextView tvName = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.selection_of_the_ward));
        linearLayout.addView(tvName);

        if (list != null) {
            if (list.size() > 0) {

                for (int i = 0; i < list.size(); i++) {
                    setUserChild(list.get(i), i);
               //     View v2 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
                //    linearLayout.addView(v2);
                }
            }
        }








     /*   LinearLayout ll2 = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        linearLayout.addView(ll2);

        TextView tvOk = new CustomTextView().onCustom(context, PARAM_OK_CANCEL, "Ok");
        ll2.addView(tvOk);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        TextView tvCancel = new CustomTextView().onCustom(context, PARAM_OK_CANCEL, res.getString(R.string.cancel));
        ll2.addView(tvCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });*/
    }
    private void setUserChild(ModelChild m, int n) {
        FrameLayout frameLayout = new CustomFrameLayout().onCustom(context, PARAM_CONTAINER_FL);
        linearLayout.addView(frameLayout);

        CircleImageView civAvatar = new CustomCircleImageView().onCustom(context, PARAM_AVATAR);
        frameLayout.addView(civAvatar);
        if (m.getAvatar() != null) { Picasso.get().load(m.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar); }

        TextView tvName = new CustomTextView().onCustom(context, PARAM_NAME, m.getName());
        frameLayout.addView(tvName);

        int image = 0;
        if (isChecked[n]) {
            image = R.mipmap.green_checked;
        } else {
            image = R.mipmap.black_checked;
        }
        ImageView ivChecked = new CustomImageView().onCustom(context, PARAM_CHECKBOX, image);
        frameLayout.addView(ivChecked);

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    if (isChecked[n]) {
                        isChecked[n] = false;
                        ivChecked.setImageResource(R.mipmap.black_checked);
                    } else {
                        isChecked[n] = true;
                        ivChecked.setImageResource(R.mipmap.green_checked);
                    }
                    onItemClickListener.onItemClick(n);
                }
            }
        });

      /*  CheckBox cbChecked = new CustomCheckBox().onCustom(context, PARAM_CHECKBOX, null);
        frameLayout.addView(cbChecked);
        cbChecked.setButtonDrawable(null);
        cbChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbChecked.isChecked()) {
                    cbChecked.setButtonDrawable(context.getResources().getDrawable(
                        R.mipmap.black_checked));
                } else {
                    cbChecked.setButtonDrawable(context.getResources().getDrawable(
                        R.mipmap.green_checked));
                }
            }
        });*/
    }

    public BottomDialogWard(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected BottomDialogWard(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
