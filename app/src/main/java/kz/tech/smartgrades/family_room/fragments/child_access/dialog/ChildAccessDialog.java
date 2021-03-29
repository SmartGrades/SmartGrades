package kz.tech.smartgrades.family_room.fragments.child_access.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomButton;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class ChildAccessDialog extends AlertDialog.Builder {
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt", "orn:ver"};
    private static final String[] PARAM_TV = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "grv:CHCV", "mrg:10,20,10,10",
            "txtC:GRAY_THREE"};
    private static final String[] PARAM_BTN = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parW:100", "mrg:0,0,0,10",
            "backR:RED_ONE", "GRAV:HOR", "txtC:WHITE"};
    public AlertDialog dialog;
    private Context context;
    private Resources res;
    public ChildAccessDialog(Context context, Resources res) {
        super(context);
        this.context = context;
        this.res = res;
    }
    public void showAlertDialog(String body) {
        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM);
        this.setView(linearLayout);

        TextView tvText = new CustomTextView().onCustom(context, PARAM_TV, body);
        linearLayout.addView(tvText);
        Button button = new CustomButton().onCustom(context, PARAM_BTN, "OK");
        linearLayout.addView(button);

        dialog = this.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
