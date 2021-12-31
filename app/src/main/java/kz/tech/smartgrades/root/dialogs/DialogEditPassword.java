package kz.tech.smartgrades.root.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.childs_module.V;
import kz.tech.smartgrades.root.models.ModelPasswordEdit;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class DialogEditPassword extends Dialog implements View.OnClickListener{

    private AppCompatActivity activity;

    private ImageView ivBack;
    private EditText etOldPass, etNewPass, etNewPass2;
    private TextView tvLabel;
    private Button btnEdit;

    private ModelPasswordEdit modelPasswordEdit;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onOk(ModelPasswordEdit mPassword);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public DialogEditPassword(AppCompatActivity compatActivity){
        super(compatActivity, R.style.DefaultCustomDialog);
        this.activity = compatActivity;
        modelPasswordEdit = new ModelPasswordEdit();

        View view = getLayoutInflater().inflate(R.layout.dialog_nav_edit_access, null, false);
        setContentView(view);
        initViews(view);
        show();
    }
    private void initViews(View view){
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        etOldPass = view.findViewById(R.id.etOldPass);
        etNewPass = view.findViewById(R.id.etNewPass);
        etNewPass.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){

            }
            @Override
            public void afterTextChanged(Editable editable){

            }
        });
        etNewPass2 = view.findViewById(R.id.etNewPass2);
        etNewPass2.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){

            }
            @Override
            public void afterTextChanged(Editable editable){
                if(tvLabel.getVisibility() == View.VISIBLE)
                    tvLabel.setVisibility(View.GONE);
            }
        });
        tvLabel = view.findViewById(R.id.tvLabel);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
                break;
            case R.id.btnEdit:
                onOkClick();
                break;
        }
    }
    private void onBack(){
        dismiss();
    }
    private void onOkClick(){
        if(!etNewPass.getText().toString().equals(etNewPass2.getText().toString()))
            tvLabel.setVisibility(View.VISIBLE);
        if(onItemClickListener == null) return;
        modelPasswordEdit.setOldPassword(etOldPass.getText().toString());
        modelPasswordEdit.setNewPassword(etNewPass.getText().toString());
        onItemClickListener.onOk(modelPasswordEdit);
    }
}