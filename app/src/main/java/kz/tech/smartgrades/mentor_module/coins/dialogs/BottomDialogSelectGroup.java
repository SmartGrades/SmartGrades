package kz.tech.smartgrades.mentor_module.coins.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor_module.coins.models.ModelGroups;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class BottomDialogSelectGroup extends BottomSheetDialog {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(ModelGroups m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "orn:ver"};
    private static final String[] PARAM_TITLE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50",
            "grv:CHCV", "txtC:BLACK"};
    private static final String[] PARAM_CONTAINER_FL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:40", "mrg:50,0,50,10"};
    private static final String[] PARAM_NAME = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:20,0,50,0",
            "grv:LCV", "txtC:GRAY_THREE"};

    private BottomSheetDialog dialog;
    private Context context;
    private Resources res;
    private LinearLayout linearLayout;
    public BottomDialogSelectGroup(Context c, Resources res, List<ModelGroups> groupsList) {
        super(c);
        this.context = c;
        this.res = res;
        dialog = this;

        linearLayout = new CustomLinearLayout().onCustom(context, PARAM);
        this.setContentView(linearLayout);

        TextView tvName = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.group_selection));
        linearLayout.addView(tvName);

        for (int i = 0; i < groupsList.size(); i++) {
            onAddGroup(groupsList.get(i));
        }
    }
    private void onAddGroup(ModelGroups m) {
        FrameLayout frameLayout = new CustomFrameLayout().onCustom(context, PARAM_CONTAINER_FL);
        linearLayout.addView(frameLayout);

        TextView tvName = new CustomTextView().onCustom(context, PARAM_NAME, m.getName());
        frameLayout.addView(tvName);

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(m);
                    dialog.dismiss();
                }
            }
        });

    }






    public BottomDialogSelectGroup(@NonNull Context context, int theme) {
        super(context, theme);
    }
    protected BottomDialogSelectGroup(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
