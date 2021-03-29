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

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class BottomDialogMentors extends BottomSheetDialog {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(String text);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "orn:ver"};
    private static final String[] PARAM_TITLE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50",
            "grv:CHCV", "txtC:BLACK"};
    private static final String[] PARAM_CONTAINER_FL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:40", "mrg:50,0,50,0"};
    private static final String[] PARAM_NAME = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:20,0,50,0",
            "grv:LCV", "txtC:GRAY_THREE"};
    private static final String[] PARAM_CHECKBOX = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "mrg:0,0,10,0",
            "parW:30", "parH:30", "GRAV:RIGHT_VER", "RTL:RIGHT", "pad:5,5,5,5" };


    private BottomSheetDialog dialog;
    private Context context;
    private Resources res;
    private LinearLayout linearLayout;
    private boolean[] isChecked;
    public BottomDialogMentors(Context context, Resources res, boolean[] isChecked) {
        super(context);
        this.context = context;
        this.res = res;
        this.isChecked = isChecked;
        dialog = this;
        String[] strings = {res.getString(R.string.upbringer), res.getString(R.string.teacher),
                res.getString(R.string.trainer), res.getString(R.string.nanny), res.getString(R.string.relative)};

        linearLayout = new CustomLinearLayout().onCustom(context, PARAM);
        this.setContentView(linearLayout);

        TextView tvName = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.mentor_status));
        linearLayout.addView(tvName);

        if (strings != null) {
            if (strings.length > 0) {
                for (int i = 0; i < strings.length; i++) {
                    setUserChild(strings[i], i);
                }
            }
        }
    }
    private void setUserChild(String s, int n) {
        FrameLayout frameLayout = new CustomFrameLayout().onCustom(context, PARAM_CONTAINER_FL);
        linearLayout.addView(frameLayout);

        TextView tvName = new CustomTextView().onCustom(context, PARAM_NAME, s);
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
                    onItemClickListener.onItemClick(s);
                }
                for (int i = 0; i < isChecked.length; i++) {
                    isChecked[i] = false;
                }
                isChecked[n] = true;
                dialog.dismiss();
            }
        });
    }

    public BottomDialogMentors(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected BottomDialogMentors(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
