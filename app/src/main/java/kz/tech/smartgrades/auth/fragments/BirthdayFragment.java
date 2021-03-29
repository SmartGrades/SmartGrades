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

public class BirthdayFragment extends Fragment implements View.OnClickListener {

    private AuthActivity activity;

    private ImageView ivNext;
    private TextView tvBirthdayTitle;
    private EditText etDay, etMonth, etYear;
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
        ivNext = view.findViewById(R.id.ivNext);
        ivNext.setOnClickListener(this);

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
                else if (Convert.S2I(arg0.toString()) > 0) isDayEmpty = true;
                onImageTernar(isDayEmpty && isMonthEmpty && isYearEmpty);
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
                else if (Convert.S2I(arg0.toString()) > 0) isMonthEmpty = true;
                onImageTernar(isDayEmpty && isMonthEmpty && isYearEmpty);
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
                else if (Convert.S2I(arg0.toString()) > 1900) isYearEmpty = true;
                onImageTernar(isDayEmpty && isMonthEmpty && isYearEmpty);
            }
        });
    }


    private void changeText() {
        tvBirthdayTitle.setText(activity.onTranslateString(R.string.enter_date_of_birth));
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
        if (isDayEmpty && isMonthEmpty && isYearEmpty) {
            activity.alert.hideSoftKeyboard(activity);
            activity.onNextFragment();
            activity.onBirthdayString(etDay.getText().toString() +
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
        }
    }
}// checker
