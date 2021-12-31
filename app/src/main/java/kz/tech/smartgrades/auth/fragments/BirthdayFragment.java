package kz.tech.smartgrades.auth.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.auth.AuthActivity;
import kz.tech.smartgrades.root.dialogs.DialogSelectCountry;
import kz.tech.smartgrades.root.models.ModelCountry;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class BirthdayFragment extends Fragment implements View.OnClickListener {

    private AuthActivity activity;

    private ImageView ivNext;
    private TextView tvBirthdayTitle;
    private EditText etDay, etMonth, etYear, etCity;
    private View vCity;
    private boolean isDayEmpty = false;
    private boolean isMonthEmpty = false;
    private boolean isYearEmpty = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AuthActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_birthday_enter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        changeText();
    }

    private void initViews(View view) {
        tvBirthdayTitle = view.findViewById(R.id.tvBirthdayTitle);
        etDay = view.findViewById(R.id.etDay);
        etMonth = view.findViewById(R.id.etMonth);
        etYear = view.findViewById(R.id.etYear);
        etCity = view.findViewById(R.id.etCity);
        vCity = view.findViewById(R.id.vCity);
        vCity.setOnClickListener(this);
        ivNext = view.findViewById(R.id.ivNext);
        ivNext.setOnClickListener(this);

        etDay.requestFocus();

        etDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.length() < 1) isDayEmpty = false;
                else if (Convert.Str2Int(arg0.toString()) > 0) isDayEmpty = true;
                if (arg0.length() == 2) etMonth.requestFocus();
                onImageTernar(isDayEmpty && isMonthEmpty && isYearEmpty && !stringIsNullOrEmpty(etCity.getText().toString()));
            }
        });
        etMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.length() < 1) isMonthEmpty = false;
                else if (Convert.Str2Int(arg0.toString()) > 0) isMonthEmpty = true;
                if (arg0.length() == 2) etYear.requestFocus();
                onImageTernar(isDayEmpty && isMonthEmpty && isYearEmpty && !stringIsNullOrEmpty(etCity.getText().toString()));
            }
        });
        etYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.length() < 4) isYearEmpty = false;
                else if (Convert.Str2Int(arg0.toString()) > 1900) isYearEmpty = true;
                onImageTernar(isDayEmpty && isMonthEmpty && isYearEmpty && !stringIsNullOrEmpty(etCity.getText().toString()));
            }
        });
    }


    private void changeText() {
        tvBirthdayTitle.setText(activity.onTranslateString(R.string.Enter_your_date_of_birth_and_place_of_residence));
        etDay.setHint(activity.onTranslateString(R.string.day));
        etMonth.setHint(activity.onTranslateString(R.string.month));
        etYear.setHint(activity.onTranslateString(R.string.year));
    }

    private void onImageTernar(boolean isImg) {
        ivNext.setImageResource(isImg? R.drawable.img_right_arrow_green : R.drawable.img_right_arrow_uncheck_green);
    }

    private void onBack() {
        activity.onBackPressed();
    }

    private void onNext() {
        if (isDayEmpty && isMonthEmpty && isYearEmpty && !stringIsNullOrEmpty(etCity.getText().toString())) {
            if (Integer.parseInt(etDay.getText().toString()) > 31
                    || Integer.parseInt(etMonth.getText().toString()) > 12
                    || Integer.parseInt(etYear.getText().toString()) > 2021) {
                activity.alert.onToast(getString(R.string.Date_entered_incorrectly));
                return;
            }
            activity.alert.hideKeyboard(activity);
            activity.onNextFragment();
//            activity.onAddress(etCity.getText().toString());
            activity.setBirthday(etDay.getText().toString() +
                    "-" + etMonth.getText().toString() +
                    "-" + etYear.getText().toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNext:
                onNext();
                break;
            case R.id.vCity:
                onCityClick();
                break;
        }
    }

    private void onCityClick() {
        DialogSelectCountry dialogSelectCountry = new DialogSelectCountry(activity);
        dialogSelectCountry.setOnItemClickListener(new DialogSelectCountry.OnItemClickListener(){
            @Override
            public void onClick(ModelCountry Country){
                etCity.setText(Country.getName());
                onImageTernar(isDayEmpty && isMonthEmpty && isYearEmpty && !stringIsNullOrEmpty(etCity.getText().toString()));
            }
        });
    }
}
