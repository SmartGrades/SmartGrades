package kz.tech.smartgrades.root.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomCardView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class CustomAlertDialog extends AlertDialog.Builder {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onOkClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:wCnt"};
    private static final String[] PARAM_LL = {"prt:CardView", "layW:mPrt", "layH:wCnt", "orn:ver",
            "backC:WHITE"};
    private static final String[] PARAM_TITLE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50", "mrg:20,20,20,10",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_DEVICE_DELETED_PERMANENTLY = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:40",
            "txtC:GREEN_ONE", "grv:CHCV", "txtS:14"};
    private static final String[] PARAM_LL_HOR = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50", "mrg:20,0,20,0",
            "orn:hor", "WeiSum:2" };
    private static final String[] PARAM_YES = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1", "mrg:20,0,20,0",
            "SctDrw:50,0,GRAY_THREE,GREEN_ONE", "txtC:RED_ONE", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_NO = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1", "mrg:20,0,20,0",
            "SctDrw:50,0,GRAY_THREE,GRAY_FIVE", "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_DEVICES_CANNOT_BE_RESTORED = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:20,20,20,20",
            "txtC:GRAY_THREE", "grv:CHCV"};
    public AlertDialog dialog;
    private Context context;
    private Resources res;
    private String[] array;// 1 - Text(Black), 2 - Text(Green), 3 - Text(Gray)
    public CustomAlertDialog(Context context, Resources res, String[] array) {
        super(context);
        this.context = context;
        this.res = res;
        this.array = array;
    }
    public CustomAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
    public void showAlert() {
        CardView cardView = new CustomCardView().onCustom(context, PARAM);
        this.setView(cardView);

        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM_LL);
        cardView.addView(linearLayout);

        TextView tvText1 = new CustomTextView().onCustom(context, PARAM_TITLE, array[0]);
        linearLayout.addView(tvText1);

        TextView tvText2 = new CustomTextView().onCustom(context, PARAM_DEVICE_DELETED_PERMANENTLY, array[1]);
        linearLayout.addView(tvText2);

        LinearLayout llHOR = new CustomLinearLayout().onCustom(context, PARAM_LL_HOR);
        linearLayout.addView(llHOR);

        TextView tvYes = new CustomTextView().onCustom(context, PARAM_YES, res.getString(R.string.yes));
        llHOR.addView(tvYes);
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onOkClick();
                }
                dialog.dismiss();
            }
        });

        TextView tvNo = new CustomTextView().onCustom(context, PARAM_NO, res.getString(R.string.no));
        llHOR.addView(tvNo);
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView tvText3 = new CustomTextView().onCustom(context, PARAM_DEVICES_CANNOT_BE_RESTORED, array[2]);
        linearLayout.addView(tvText3);

        dialog = this.show();
    }
}
