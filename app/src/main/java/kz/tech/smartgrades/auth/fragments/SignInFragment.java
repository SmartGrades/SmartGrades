package kz.tech.smartgrades.auth.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.AuthActivity;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.SoapRequest;

import com.google.firebase.iid.FirebaseInstanceId;

public class SignInFragment extends Fragment implements View.OnClickListener {

    private AuthActivity activity;

    private ConstraintLayout clPhoneOrMail, clPassword;
    private EditText etPhoneOrMail, etPassword;
    private TextView tvForgotPasswordClick, tvRegistration;
    private Button btnSignIn;
    private boolean[] EditTextIsEmpty = new boolean[2];
    private ImageView ivPasswordShow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AuthActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_sign_in, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        changeText();
    }
    private void initViews(View view) {
        clPhoneOrMail = view.findViewById(R.id.clPhoneOrMail);
        clPhoneOrMail.setOnClickListener(this);
        etPhoneOrMail = view.findViewById(R.id.etPhoneOrMail);
        etPhoneOrMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                EditTextIsEmpty[0] = stringIsNullOrEmpty(editable.toString());
                onIsEnableButton();
            }
        });
        clPassword = view.findViewById(R.id.clPassword);
        clPassword.setOnClickListener(this);
        etPassword = view.findViewById(R.id.etPassword);
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                EditTextIsEmpty[1] = stringIsNullOrEmpty(editable.toString());
                onIsEnableButton();
            }
        });
        ivPasswordShow = view.findViewById(R.id.ivPasswordShow);
        ivPasswordShow.setOnClickListener(this);

        btnSignIn = view.findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);

        tvForgotPasswordClick = view.findViewById(R.id.tvForgotPasswordClick);
        tvForgotPasswordClick.setOnClickListener(this);
        tvRegistration = view.findViewById(R.id.tvRegistration);
        tvRegistration.setOnClickListener(this);

        EditTextIsEmpty[0] = EditTextIsEmpty[1] = true;
        onIsEnableButton();
    }

    private void changeText() {
        btnSignIn.setText(activity.onTranslateString(R.string.sign_in));
        tvForgotPasswordClick.setText(activity.onTranslateString(R.string.forgot_password));
        tvRegistration.setText(activity.onTranslateString(R.string.register));
    }
    private void onIsEnableButton() {
        ConstraintLayout.LayoutParams buttonLayoutParams = (ConstraintLayout.LayoutParams) btnSignIn.getLayoutParams();

        if (!EditTextIsEmpty[0] && !EditTextIsEmpty[1]) {
            btnSignIn.setBackgroundResource(R.drawable.btn_background_blue_dark_rectangle);
            btnSignIn.setEnabled(true);
        }
        else {
            btnSignIn.setBackgroundResource(R.drawable.btn_background_blue_rectangle);
            btnSignIn.setEnabled(false);
        }

        btnSignIn.setLayoutParams(buttonLayoutParams);
    }
    public void onShowKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                onSignInClick();
                break;
            case R.id.tvForgotPassword:
                onForgotPassword();
                break;
            case R.id.tvRegistration:
                onRegistrationClick();
                break;
            case R.id.clPhoneOrMail:
                onPhoneOrMailClick();
                break;
            case R.id.clPassword:
                onPasswordClick();
                break;
            case R.id.ivPasswordShow:
                onPasswordShowClick();
                break;
        }
    }
    private void onSignInClick() {
        String Login = etPhoneOrMail.getText().toString();
        String Password = etPassword.getText().toString();
        String Token = FirebaseInstanceId.getInstance().getToken();
        activity.onAuth(Login, Password, Token);
    }
    private void onForgotPassword() {
    }
    private void onRegistrationClick() {
        activity.onRegistrationClick();
    }

    private void onPhoneOrMailClick() {
        etPhoneOrMail.requestFocus();
        etPhoneOrMail.setSelection(etPhoneOrMail.getText().length());
        onShowKeyboard(etPhoneOrMail);

    }
    private void onPasswordClick() {
        etPassword.requestFocus();
        etPassword.setSelection(etPassword.getText().length());
        onShowKeyboard(etPhoneOrMail);
    }
    private void onPasswordShowClick() {
        if (etPassword.getInputType() == 129) {
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ivPasswordShow.setImageResource(R.drawable.img_password_show);
        }
        else {
            etPassword.setInputType(129);
            ivPasswordShow.setImageResource(R.drawable.img_password_hide);
        }
    }
}