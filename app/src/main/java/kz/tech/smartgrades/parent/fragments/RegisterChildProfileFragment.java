package kz.tech.smartgrades.parent.fragments;

import static kz.tech.smartgrades.Convert.DATE_FORMAT_FULL;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades.S.CHILD;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_ParentRegisterChild;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;

import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.auth.dialogs.D_SelectBirthday;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.model.ModelRegisterChild;
import kz.tech.smartgrades.root.login.LoginKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterChildProfileFragment extends Fragment implements View.OnClickListener {

    private ParentActivity activity;

    private ImageView ivBack;
    private TextView tvRegistrationState, tvAgeLabel, tvAge, tvTermsOfUse, tvPrivacyPolicy, tvPasswordDoesNotMatch;
    private CardView cvLogin, cvPhoneOrMail, cvAge, cvPassword, cvPasswordRepeat;
    private EditText etLogin, etPhoneOrMail, etPassword, etPasswordRepeat;
    private CheckBox cbConditions;
    private Button btnNext;

    private String Type = CHILD;
    private String Age;

    private boolean[] EditTextIsNotEmpty = new boolean[3];


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_parent_register_child_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onChangeText();
    }
    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvRegistrationState = view.findViewById(R.id.tvRegistrationState);
        cvLogin = view.findViewById(R.id.cvLogin);
        cvLogin.setOnClickListener(this);
        etLogin = view.findViewById(R.id.etLogin);
        etLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                EditTextIsNotEmpty[0] = !stringIsNullOrEmpty(editable.toString());
                onIsEnableButton();
            }
        });
        cvPhoneOrMail = view.findViewById(R.id.cvPhoneOrMail);
        cvPhoneOrMail.setOnClickListener(this);
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
                EditTextIsNotEmpty[1] = !stringIsNullOrEmpty(editable.toString());
                onIsEnableButton();
            }
        });
        tvAgeLabel = view.findViewById(R.id.tvAgeLabel);
        cvAge = view.findViewById(R.id.cvAge);
        cvAge.setOnClickListener(this);
        tvAge = view.findViewById(R.id.tvAge);
        cvPassword = view.findViewById(R.id.cvPassword);
        cvPassword.setOnClickListener(this);
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
                EditTextIsNotEmpty[2] = etPassword.getText().toString().equals(etPasswordRepeat.getText().toString());
                onIsEnableButton();
            }
        });
        cvPasswordRepeat = view.findViewById(R.id.cvPasswordRepeat);
        cvPasswordRepeat.setOnClickListener(this);
        etPasswordRepeat = view.findViewById(R.id.etPasswordRepeat);
        etPasswordRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                EditTextIsNotEmpty[2] = etPassword.getText().toString().equals(etPasswordRepeat.getText().toString());
                onIsEnableButton();
                if (!EditTextIsNotEmpty[2]) tvPasswordDoesNotMatch.setVisibility(View.VISIBLE);
                else tvPasswordDoesNotMatch.setVisibility(View.GONE);
            }
        });
        tvPasswordDoesNotMatch = view.findViewById(R.id.tvPasswordDoesNotMatch);
        cbConditions = view.findViewById(R.id.cbConditions);
        cbConditions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onIsEnableButton();
            }
        });
        tvTermsOfUse = view.findViewById(R.id.tvTermsOfUse);
        tvPrivacyPolicy = view.findViewById(R.id.tvPrivacyPolicy);
        btnNext = view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
    }
    private void onChangeText() {
        tvRegistrationState.setText("Регистрация аккаунта ребенка");
    }

    private void onIsEnableButton() {
        ConstraintLayout.LayoutParams buttonLayoutParams = (ConstraintLayout.LayoutParams) btnNext.getLayoutParams();

        if (EditTextIsNotEmpty[0] && EditTextIsNotEmpty[1] && EditTextIsNotEmpty[2] && cbConditions.isChecked()) {
            btnNext.setBackgroundResource(R.drawable.btn_background_blue_dark_rectangle);
            btnNext.setEnabled(true);
        }
        else {
            btnNext.setBackgroundResource(R.drawable.btn_background_blue_rectangle);
            btnNext.setEnabled(false);
        }

        btnNext.setLayoutParams(buttonLayoutParams);
    }
    private void onRegistrationClick() {
        activity.alert.hideKeyboard(activity);
        // функция отправки данных на сервер, регистрация и обновлениях страницы родителя уже с ребенком
        ModelRegisterChild mChild = new ModelRegisterChild();
        mChild.setParentId(activity.login.loadUserDate(LoginKey.ID));
        mChild.setLogin(etLogin.getText().toString());
        mChild.setPhoneOrMail(etPhoneOrMail.getText().toString());
        mChild.setBirthday(Age);
        mChild.setPassword(etPassword.getText().toString());

        String SOAP = SoapRequest(func_ParentRegisterChild, new Gson().toJson(mChild));
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                activity.runOnUiThread(() -> {
                    activity.alert.onToast("Сервер не доступен");
//                    String answer = e.toString();
//                    alert.onToast(answer);
                });
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(() -> {
                        if (answer.isSuccess()) {
                            activity.alert.onToast("Регистрация прошла успешно");
                            activity.presenter.onStartPresenter();
                            activity.onBackPressed();
                        }
                        else {
                            activity.alert.onToast("Ошибка регистрации");
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackClick();
                break;
            case R.id.cvLogin:
                onLoginClick();
                break;
            case R.id.cvPhoneOrMail:
                onPhoneOrMailClick();
                break;
            case R.id.cvAge:
                onAgeClick();
                break;
            case R.id.cvPassword:
                onPasswordClick();
                break;
            case R.id.cvPasswordRepeat:
                onPasswordRepeatClick();
                break;
            case R.id.btnNext:
                onRegistrationClick();
                break;
        }
    }
    private void onBackClick() {
        activity.onBackPressed();
    }
    private void onLoginClick() {
        etLogin.requestFocus();
        etLogin.setSelection(etLogin.getText().length());
    }
    private void onPhoneOrMailClick() {
        etPhoneOrMail.requestFocus();
        etPhoneOrMail.setSelection(etPhoneOrMail.getText().length());
    }
    private void onAgeClick() {
        D_SelectBirthday dialog = new D_SelectBirthday(activity);
        dialog.setOnResultListener(new D_SelectBirthday.OnResultListener() {
            @Override
            public void onResult(Calendar calendar) {
                Age = Convert.DATE_TIME_FORMAT_SQL.format(calendar.getTime());
                tvAge.setText(DATE_FORMAT_FULL.format(calendar.getTime()));
            }
        });
    }
    private void onPasswordClick() {
        etPassword.requestFocus();
        etPassword.setSelection(etPassword.getText().length());
    }
    private void onPasswordRepeatClick() {
        etPasswordRepeat.requestFocus();
        etPasswordRepeat.setSelection(etPasswordRepeat.getText().length());
    }

}



