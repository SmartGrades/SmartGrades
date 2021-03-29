package kz.tech.smartgrades.root.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import kz.tech.esparta.R;

import static kz.tech.smartgrades._Web.SoapRequest;

public class AlertDialogDefaultET extends AlertDialog implements View.OnClickListener{

    private AlertDialog dialog;
    private Context context;
    private TextView tvTitle;
    private EditText etValue;
    private TextView tvCancel;
    private TextView tvAdd;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onOkClick(String etValue);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public AlertDialogDefaultET(Context context, String title, String hint){
        super(context);
        this.context = context;

        View view = getLayoutInflater().inflate(R.layout.ad_school_add_new_column, null);
        Builder builder = new Builder(context).setView(view);

        initViews(view);
        tvTitle.setText(title);
        etValue.setHint(hint);

        dialog = this;
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    public AlertDialogDefaultET(Context context, String title, String hint, String btnOkText){
        super(context);
        this.context = context;

        View view = getLayoutInflater().inflate(R.layout.ad_school_add_new_column, null);
        Builder builder = new Builder(context).setView(view);

        initViews(view);
        tvTitle.setText(title);
        etValue.setHint(hint);
        tvAdd.setText(btnOkText);

        dialog = this;
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    private void initViews(View view){
        tvTitle = view.findViewById(R.id.tvTitle);
        etValue = view.findViewById(R.id.etColumnName);
        etValue.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3){
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
            }
            @Override
            public void afterTextChanged(Editable arg0){
                if(arg0.length() == 0){
                    tvAdd.setEnabled(false);
                    tvAdd.setTextColor(context.getResources().getColor(R.color.gray_default));
                    tvAdd.setBackground(null);
                    tvAdd.setPadding(10, 10, 10, 10);
                }
                else{
                    tvAdd.setEnabled(true);
                    tvAdd.setTextColor(context.getResources().getColor(R.color.white));
                    tvAdd.setBackgroundResource(R.drawable.background_square_blue_sea);
                    tvAdd.setPadding(10, 10, 10, 10);
                }
            }
        });
        tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        tvAdd = view.findViewById(R.id.tvAdd);
        tvAdd.setOnClickListener(this);
    }


    private void onCancelClick(){
        dialog.dismiss();
    }
    private void onOkClick(){
        dialog.dismiss();
        if(onItemClickListener == null) return;
        onItemClickListener.onOkClick(etValue.getText().toString());
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.tvCancel:
                onCancelClick();
                break;
            case R.id.tvAdd:
                onOkClick();
                break;
        }
    }
}
