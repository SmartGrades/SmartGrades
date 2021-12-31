package kz.tech.smartgrades.parents_module.contracts.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class ContractsGoodDialog extends AlertDialog.Builder {
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_TITLE = {"prt:FrmLay","layW:mPrt", "layH:wCnt", "mrg:0,0,0,0",
            "txtS:26", "grv:CHCV", "txtC:WHITE", "GRAV:TOP"};
    private static final String[] PARAM_IMAGE = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,100,0,100",
            "pad:10,10,10,10", "GRAV:CORE"};
    private static final String[] PARAM_TEXT = {"prt:FrmLay","layW:mPrt", "layH:wCnt", "parH:40", "mrg:0,0,0,60",
            "txtS:18", "grv:CHCV", "txtC:WHITE", "GRAV:BOTTOM"};
    private static final String[] PARAM_CONTINUE = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:40", "parW:150", "mrg:0,0,0,20",
            "txtS:18", "grv:CHCV", "txtC:WHITE", "backR:RED_ONE", "GRAV:BOTTOM_HOR"};
    public AlertDialog dialog;
    private Context context;
    private Resources res;
    public ContractsGoodDialog(Context context, Resources res) {
        super(context);
        this.context = context;
        this.res = res;



    }
    public void showAlertDialog() {
  //      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        FrameLayout frameLayout = new CustomFrameLayout().onCustom(context, PARAM);
        this.setView(frameLayout);

        TextView tvTitle = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.good_deal_text_one));
        frameLayout.addView(tvTitle);

        ImageView imageView = new CustomImageView().onCustom(context, PARAM_IMAGE, R.drawable.contracts_good);
        frameLayout.addView(imageView);

        TextView tvText = new CustomTextView().onCustom(context, PARAM_TEXT, res.getString(R.string.good_deal_text_two));
        frameLayout.addView(tvText);

        TextView tvContinue = new CustomTextView().onCustom(context, PARAM_CONTINUE, res.getString(R.string.btn_continue));
        frameLayout.addView(tvContinue);
        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog = this.show();

    }
    public ContractsGoodDialog(Context context, int themeResId, Resources res) {
        super(context, themeResId);
        this.context = context;
        this.res = res;
    }
}
