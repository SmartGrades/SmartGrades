package kz.tech.smartgrades.parents_module.family_group.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomEditText;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class AlertDialogAddGroups extends AlertDialog.Builder {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(String name);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:wCnt", "orn:ver", "backC:WHITE"};
    private static final String[] PARAM_TITLE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50", "mrg:20,20,20,20",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_GROUP_NAME = {"prt:LinLay", "layW:mPrt", "layH:wCnt",  "mrg:20,20,20,20",
            "txtC:GRAY_THREE", "grv:CHCV", "txtS:18", "Filter:Login"};
    private static final String[] PARAM_WARNING = {"prt:LinLay", "layW:mPrt", "layH:wCnt",  "mrg:20,20,20,20",
            "txtC:GRAY_THREE", "grv:CHCV", "txtS:14"};

    private static final String[] PARAM_CONTAINER_LL = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:50",
            "orn:hor", "WeiSum:2", "backC:WHITE"};
    private static final String[] PARAM_OK_CANCEL = {"prt:LinLay", "layW:0", "layH:mPrt",
            "grv:CHCV", "wei:1", "pad:5,5,5,5", "txtC:GREEN_ONE"};
    public AlertDialog dialog;
    private Context context;
    private Resources res;
    public AlertDialogAddGroups(Context context, Resources res) {
        super(context);
        this.context = context;
        this.res = res;
    }
    public void showAlert() {
        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM);
        this.setView(linearLayout);

        TextView tvTitle = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.add_group_dialog_title));
        linearLayout.addView(tvTitle);

        EditText etGroupName = new CustomEditText().onCustom(context, PARAM_GROUP_NAME, null);
        linearLayout.addView(etGroupName);

        TextView tvWarning = new CustomTextView().onCustom(context, PARAM_WARNING, res.getString(R.string.add_group_dialog_warning));
        linearLayout.addView(tvWarning);

        LinearLayout ll2 = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        linearLayout.addView(ll2);

        TextView tvOk = new CustomTextView().onCustom(context, PARAM_OK_CANCEL, "Ok");
        ll2.addView(tvOk);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(etGroupName.getText().toString());
                }
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
        });

        dialog = this.show();
    }
    public AlertDialogAddGroups(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
}
