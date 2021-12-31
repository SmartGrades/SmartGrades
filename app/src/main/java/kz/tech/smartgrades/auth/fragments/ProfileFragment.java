package kz.tech.smartgrades.auth.fragments;

import static kz.tech.smartgrades.Convert.DATE_FORMAT_FULL;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades.S.CHILD;
import static kz.tech.smartgrades.S.SCHOOL;
import static kz.tech.smartgrades._Web.SoapRequest;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.auth.AuthActivity;
import kz.tech.smartgrades.auth.dialogs.D_SelectBirthday;
import kz.tech.smartgrades.root.dialogs.DialogSelectCountry;
import kz.tech.smartgrades.root.models.ModelCountry;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private AuthActivity activity;

    private TextView tvBirthday, tvSchoolNameLabel, tvCountry, tvPasswordDoesNotMatch;
    private CardView cvLogin, cvPhoneOrMail, cvBirthday, cvSchoolName, cvCountry, cvPassword, cvPasswordRepeat;
    private EditText etLogin, etPhoneOrMail, etSchoolName, etPassword, etPasswordRepeat;
    private CheckBox cbConditions;
    private Button btnNext;

    private String Type;
    private String Birthday;
    private ModelCountry mCountry;

    private boolean[] EditTextIsNotEmpty = new boolean[5];
    private boolean isAutoEnter = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AuthActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view) {
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
//        etLogin.setFilters(new InputFilter[]{new InputFilter.AllCaps() {
//            @Override
//            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                return String.valueOf(source).toLowerCase();
//            }
//        }});

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

        cvBirthday = view.findViewById(R.id.cvBirthday);
        cvBirthday.setOnClickListener(this);
        tvBirthday = view.findViewById(R.id.tvBirthday);

        tvSchoolNameLabel = view.findViewById(R.id.tvSchoolNameLabel);
        cvSchoolName = view.findViewById(R.id.cvSchoolName);
        cvSchoolName.setOnClickListener(this);
        etSchoolName = view.findViewById(R.id.etSchoolName);
        etSchoolName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                EditTextIsNotEmpty[2] = !stringIsNullOrEmpty(editable.toString());
                onIsEnableButton();
            }
        });

        cvCountry = view.findViewById(R.id.cvCountry);
        cvCountry.setOnClickListener(this);
        tvCountry = view.findViewById(R.id.tvCountry);

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
                EditTextIsNotEmpty[3] = !stringIsNullOrEmpty(editable.toString());
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
                EditTextIsNotEmpty[4] = etPassword.getText().toString().equals(etPasswordRepeat.getText().toString());
                onIsEnableButton();
                if (!EditTextIsNotEmpty[4]) tvPasswordDoesNotMatch.setVisibility(View.VISIBLE);
                else tvPasswordDoesNotMatch.setVisibility(View.GONE);
            }
        });
        tvPasswordDoesNotMatch = view.findViewById(R.id.tvPasswordDoesNotMatch);

        cbConditions = view.findViewById(R.id.cbConditions);
        cbConditions.setOnCheckedChangeListener((compoundButton, b) -> onIsEnableButton());

        btnNext = view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
    }

    public void onSetType(String typeSelect) {
        Type = typeSelect;
        onSetData();
    }
    private void onSetData() {
//        switch (Type) {
//            case PARENT:
//                tvRegistrationState.setText("Регистрация аккаунта родителя");
//                break;
//            case CHILD:
//                tvRegistrationState.setText("Регистрация аккаунта ребенка");
//                break;
//            case MENTOR:
//                tvRegistrationState.setText("Регистрация аккаунта репетитора / тренера / учителя");
//                break;
//            case INVESTOR:
//                tvRegistrationState.setText("Регистрация аккаунта спонсора");
//                break;
//            case SCHOOL:
//                tvRegistrationState.setText("Регистрация аккаунта школы");
//                break;
//        }
        if (Type.equals(SCHOOL)) {
            tvBirthday.setVisibility(View.GONE);
            cvBirthday.setVisibility(View.GONE);
            tvSchoolNameLabel.setVisibility(View.VISIBLE);
            cvSchoolName.setVisibility(View.VISIBLE);
            tvCountry.setVisibility(View.VISIBLE);
            cvCountry.setVisibility(View.VISIBLE);
        }
        else if (Type.equals(CHILD)) {
            tvBirthday.setVisibility(View.VISIBLE);
            cvBirthday.setVisibility(View.VISIBLE);
            tvSchoolNameLabel.setVisibility(View.GONE);
            cvSchoolName.setVisibility(View.GONE);
            tvCountry.setVisibility(View.GONE);
            cvCountry.setVisibility(View.GONE);
        }
        else {
            tvBirthday.setVisibility(View.GONE);
            cvBirthday.setVisibility(View.GONE);
            tvSchoolNameLabel.setVisibility(View.GONE);
            cvSchoolName.setVisibility(View.GONE);
            tvCountry.setVisibility(View.GONE);
            cvCountry.setVisibility(View.GONE);
        }
    }

    private void onIsEnableButton() {
        boolean isEnable = EditTextIsNotEmpty[0] //Login
                && EditTextIsNotEmpty[1]; //PhoneOrMail
        if (Type.equals(CHILD))
            isEnable = isEnable && !stringIsNullOrEmpty(Birthday);
        else if (Type.equals(SCHOOL)) {
            isEnable = isEnable && EditTextIsNotEmpty[2] //SchoolName
                    && mCountry != null;
        }
        isEnable = isEnable && EditTextIsNotEmpty[3] //Password
                && EditTextIsNotEmpty[4] //PasswordRepeat
                && cbConditions.isChecked();

        ConstraintLayout.LayoutParams buttonLayoutParams = (ConstraintLayout.LayoutParams) btnNext.getLayoutParams();
        if (isEnable) {
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
        activity.setLogin(etLogin.getText().toString());
        activity.setPhoneOrMail(etPhoneOrMail.getText().toString());
        if (Type.equals(CHILD))
            activity.setBirthday(Birthday);
        else if (Type.equals(SCHOOL)){
            activity.setSchoolName(etSchoolName.getText().toString());
            activity.setCountryId(mCountry.getId());
        }
        activity.setPassword(etPassword.getText().toString());
        activity.onRegister();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cvLogin:
                onLoginClick();
                break;
            case R.id.cvPhoneOrMail:
                onPhoneOrMailClick();
                break;
            case R.id.cvBirthday:
                onBirthdayClick();
                break;
            case R.id.cvCountry:
                onCountryClick();
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
    private void onLoginClick() {
        etLogin.requestFocus();
        etLogin.setSelection(etLogin.getText().length());
    }
    private void onPhoneOrMailClick() {
        etPhoneOrMail.requestFocus();
        etPhoneOrMail.setSelection(etPhoneOrMail.getText().length());
    }
    private void onBirthdayClick() {
        D_SelectBirthday dialog = new D_SelectBirthday(activity);
        dialog.setOnResultListener(new D_SelectBirthday.OnResultListener() {
            @Override
            public void onResult(Calendar calendar) {
                Birthday = Convert.DATE_TIME_FORMAT_SQL.format(calendar.getTime());
                tvBirthday.setText(DATE_FORMAT_FULL.format(calendar.getTime()));
            }
        });
    }
    private void onCountryClick() {
        DialogSelectCountry dialogSelectCountry = new DialogSelectCountry(activity);
        dialogSelectCountry.setOnItemClickListener(Country -> {
            mCountry = Country;
            tvCountry.setText(mCountry.getName());
            activity.alert.hideKeyboard(activity);
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
