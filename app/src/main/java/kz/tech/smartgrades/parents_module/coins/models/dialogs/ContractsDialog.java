package kz.tech.smartgrades.parents_module.coins.models.dialogs;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomButton;
import kz.tech.smartgrades.root.ui.CustomView.CustomEditText;

public class ContractsDialog extends AlertDialog.Builder {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(String text);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt", "orn:ver"};
    private static final String[] PARAM_BTN = {"prt:LinLay", "layW:mPrt", "layH:wCnt"};
    public AlertDialog dialog;
    private Context context;
    private String title, hint;
    private String[] paramsET;
    public ContractsDialog(Context context, String title, String[] paramsET, String hint) {
        super(context);
        this.context = context;
        this.title = title;
        this.paramsET = paramsET;
        this.hint = hint;
    }

    public ContractsDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
    public void showAlert() {
        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM);
        this.setView(linearLayout);


        EditText editText = new CustomEditText().onCustom(context, paramsET, hint);
        linearLayout.addView(editText);
        Button button = new CustomButton().onCustom(context, PARAM_BTN, "OK");
        linearLayout.addView(button);

        dialog = this.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    if(TextUtils.isEmpty(editText.getText().toString())) {
                        dialog.dismiss();
                        return;
                    } else {
                        onItemClickListener.onItemClick(editText.getText().toString());
                        dialog.dismiss();
                    }
                }
            }
        });
    }
}
