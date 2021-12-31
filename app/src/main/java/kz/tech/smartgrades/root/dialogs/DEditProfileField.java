package kz.tech.smartgrades.root.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.root.adapters.LessonsListAdapter;
import kz.tech.smartgrades.root.models.ModelLesson;

import static kz.tech.smartgrades.F.isNameValid;
import static kz.tech.smartgrades.F.isUsernameValid;
import static kz.tech.smartgrades.GET.isEmailOrSite;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.SoapRequest;

public class DEditProfileField extends Dialog implements View.OnClickListener{

    private AppCompatActivity activity;
    private ModelUser mUser;

    private ImageView ivBack, ivOk;
    private TextView tvLabel, tvInputLabel1, tvInputLabel2, tvDes;
    private EditText etInput1, etInput2;
    private View view1, view2, view3;
    private String TYPE_EDIT;

    private boolean[] isOkName;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onOkClick(ModelUser mUser);
        void onCancelClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public DEditProfileField(AppCompatActivity activity, ModelUser model, String typeEdit){
        super(activity, R.style.DefaultCustomDialog);
        this.activity = activity;
        this.mUser = model;
        this.TYPE_EDIT = typeEdit;

        View view = getLayoutInflater().inflate(R.layout.dialog_edit, null, false);
        setContentView(view);
        initViews(view);
        onSetData();
        show();
    }
    private void initViews(View view){
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivOk = view.findViewById(R.id.ivOk);
        ivOk.setOnClickListener(this);
        tvLabel = view.findViewById(R.id.tvLabel);
        tvInputLabel1 = view.findViewById(R.id.tvInputLabel1);
        tvInputLabel2 = view.findViewById(R.id.tvInputLabel2);
        etInput1 = view.findViewById(R.id.etInput1);
        etInput2 = view.findViewById(R.id.etInput2);
        tvDes = view.findViewById(R.id.tvDes);
        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        view3 = view.findViewById(R.id.view3);

        tvInputLabel2.setVisibility(View.GONE);
        etInput2.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
    }
    private void onSetData(){
        if(TYPE_EDIT.equals("Name")){
            isOkName = new boolean[2];

            tvLabel.setText(R.string.Surname_and_name);
            tvDes.setText(R.string.name_des);

            tvInputLabel1.setVisibility(View.VISIBLE);
            tvInputLabel2.setVisibility(View.VISIBLE);
            etInput1.setVisibility(View.VISIBLE);
            etInput2.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
            etInput1.setMaxLines(1);
            etInput2.setMaxLines(1);
            etInput1.setText(mUser.getLastName());
            etInput2.setText(mUser.getFirstName());
            etInput1.addTextChangedListener(new TextWatcher(){
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){ }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){ }
                @Override
                public void afterTextChanged(Editable editable){
                    if(editable.length() == 0) isOkName[0] = false;
                    else if(editable.length() > 0){
                        if(Character.isLowerCase(editable.toString().charAt(0)))
                            etInput1.setText(editable.toString().toUpperCase());
                        etInput1.setSelection(etInput1.getText().length());
                        isOkName[0] = isNameValid(editable.toString());
                    }
                }
            });
            etInput2.addTextChangedListener(new TextWatcher(){
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){ }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){ }
                @Override
                public void afterTextChanged(Editable editable){
                    if(editable.length() == 0) isOkName[1] = false;
                    else if(editable.length() > 0){
                        if(Character.isLowerCase(editable.toString().charAt(0)))
                            etInput1.setText(editable.toString().toUpperCase());
                        etInput1.setSelection(etInput1.getText().length());
                        isOkName[1] = isNameValid(editable.toString());
                    }
                }
            });
            etInput1.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
        else if(TYPE_EDIT.equals("Phone")){
            isOkName = new boolean[1];

            tvLabel.setText(R.string.Phone);
            tvDes.setText(R.string.phone_des);

            etInput1.setText(mUser.getPhone());
            etInput1.setInputType(InputType.TYPE_CLASS_PHONE);
            etInput1.setMaxLines(1);
        }
        else if(TYPE_EDIT.equals("Mail")){
            isOkName = new boolean[1];

            tvLabel.setText(R.string.email);
            tvDes.setText(R.string.mail_des);

            etInput1.setText(mUser.getMail());
            etInput1.setMaxLines(1);
            etInput1.addTextChangedListener(new TextWatcher(){
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){ }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){ }
                @Override
                public void afterTextChanged(Editable editable){
                    isOkName[0] = editable.length() > 0 && isEmailOrSite(editable.toString(), true);
                }
            });
            if(etInput1.getText().length() > 0){
                if(etInput1.getText().toString().contains("+7")){
                    InputFilter filter = new InputFilter.LengthFilter(12);
                    etInput1.setFilters(new InputFilter[]{filter});
                }
                else if(etInput1.getText().toString().contains("87")){
                    InputFilter filter = new InputFilter.LengthFilter(11);
                    etInput1.setFilters(new InputFilter[]{filter});
                }
                else {
                    InputFilter filter = new InputFilter.LengthFilter(50);
                    etInput1.setFilters(new InputFilter[]{filter});
                }
            }

            etInput1.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
        else if(TYPE_EDIT.equals("Login")){
            isOkName = new boolean[1];

            tvLabel.setText(R.string.login);
            tvDes.setText(R.string.login_des);

            etInput1.setText(mUser.getLogin());
            etInput1.setMaxLines(1);
            etInput1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length() == 0) isOkName[0] = false;
                    else isOkName[0] = isUsernameValid(editable.toString());
                    String s = editable.toString();
                    if (!s.equals(s.toLowerCase())){
                        s=s.toLowerCase();
                        etInput1.setText(s);
                        etInput1.setSelection(s.length());
                    }
                }
            });
            etInput1.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
        else if(TYPE_EDIT.equals("Bio")){
            isOkName = new boolean[1];

            tvLabel.setText(R.string.About_me);
            //tvDes.setText(R.string.bio_des);

            etInput1.setText(mUser.getAboutMe());
            etInput1.addTextChangedListener(new TextWatcher(){
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){ }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){ }
                @Override
                public void afterTextChanged(Editable editable){
                    if(editable.length() < 50) isOkName[0] = true;
                    else isOkName[0] = false;
                }
            });
            etInput1.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
        else if(TYPE_EDIT.equals("SchoolName")){
            isOkName = new boolean[1];

            tvLabel.setText(R.string.school_name);

            etInput1.setText(mUser.getName());
            etInput1.addTextChangedListener(new TextWatcher(){
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){ }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){ }
                @Override
                public void afterTextChanged(Editable editable){
                    if(editable.length() < 50) isOkName[0] = true;
                    else isOkName[0] = false;
                }
            });
            etInput1.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
        else if(TYPE_EDIT.equals("SchoolLocation")){
            isOkName = new boolean[1];

            tvLabel.setText(R.string.address_);
            etInput1.setText(mUser.getAddress());
            etInput1.addTextChangedListener(new TextWatcher(){
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){ }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){ }
                @Override
                public void afterTextChanged(Editable editable){
                    if(editable.length() < 50) isOkName[0] = true;
                    else isOkName[0] = false;
                }
            });
            etInput1.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    private void onCancelClick(){
        if(onItemClickListener == null) return;
        onItemClickListener.onCancelClick();
    }
    private void onOkClick(){
        if(onItemClickListener == null) return;
        if(TYPE_EDIT.equals("Name")){
            mUser.setFirstName(etInput2.getText().toString());
            mUser.setLastName(etInput1.getText().toString());
        }
        else if(TYPE_EDIT.equals("Phone")){
            mUser.setPhone(etInput1.getText().toString());
        }
        else if(TYPE_EDIT.equals("Mail")){
            mUser.setMail(etInput1.getText().toString());
        }
        else if(TYPE_EDIT.equals("Login")){
            mUser.setLogin(etInput1.getText().toString());
        }
        else if(TYPE_EDIT.equals("Bio")){
            mUser.setAboutMe(etInput1.getText().toString());
        }
        else if(TYPE_EDIT.equals("SchoolName")){
            mUser.setName(etInput1.getText().toString());
        }
        else if(TYPE_EDIT.equals("SchoolLocation")){
            mUser.setAddress(etInput1.getText().toString());
        }
        onItemClickListener.onOkClick(mUser);
    }

    private void isOkEnable(){
        boolean isOK = true;
        for(boolean _bool : isOkName)
            if(!_bool){
                isOK = false;
                break;
            }
        if(isOK){
            ivOk.setEnabled(false);
            //ivOk.setBackground(null);
            //ivOk.setPadding(10, 10, 10, 10);
        }
        else{
            ivOk.setEnabled(true);
            //ivOk.setBackgroundResource(R.drawable.background_square_blue_sea);
            //ivOk.setPadding(10, 10, 10, 10);
        }
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.ivBack:
                onCancelClick();
                break;
            case R.id.tvCancel:
                onCancelClick();
                break;
            case R.id.ivOk:
                onOkClick();
                break;
        }
    }
}