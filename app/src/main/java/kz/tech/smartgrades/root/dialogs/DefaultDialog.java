package kz.tech.smartgrades.root.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import kz.tech.esparta.R;

public class DefaultDialog extends Dialog {

    private TextView tvCancel;
    private TextView tvExit;
    private TextView tvErrorMessage;
    private TextView tvErrorBody;
    private OnClickListener onCLickListener;

    public DefaultDialog(Context context) {
        super(context);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pw_default_dialog, null, false);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.setContentView(view);
        initView();
    }

    public interface OnClickListener {
        void onCancelDialog();
        void onExitDialog();
    }

    public void setOnClickListener(DefaultDialog.OnClickListener onCLickListener) {
        this.onCLickListener = onCLickListener;
    }

    private void initView() {
        tvCancel = findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCLickListener.onCancelDialog();
                dismiss();
            }
        });
        tvExit = findViewById(R.id.tvExit);
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCLickListener.onExitDialog();
                dismiss();
            }
        });
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
        tvErrorBody = findViewById(R.id.tvErrorBody);
    }

    public void setCancel(int color, String text) {
        tvCancel.setTextColor(color);
        tvCancel.setText(text);
    }

    public void setExit(int color, String text) {
        tvExit.setTextColor(color);
        tvExit.setText(text);
    }

    public void setMessage(String text) {
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.setText(text);
    }

    public void setBody(String text) {
        tvErrorBody.setVisibility(View.VISIBLE);
        tvErrorBody.setText(text);
    }
}
