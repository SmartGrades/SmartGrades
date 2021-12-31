package kz.tech.smartgrades.parent_add_child.fragments;

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
import kz.tech.smartgrades.parent_add_child.ParentAddChildActivity;

import static kz.tech.smartgrades.F.isPasswordValid;

public class pacPasswordFragment extends Fragment implements View.OnClickListener {

    private ParentAddChildActivity activity;

    private ImageView ivNext;
    private TextView tvPasswordTitle;
    private EditText etPassword, etPasswordSecond;
    private boolean isNextEnable = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddChildActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_password, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        changeText();
    }

    private void initViews(View view) {
        tvPasswordTitle = view.findViewById(R.id.tvPasswordTitle);
        etPassword = view.findViewById(R.id.etPassword);
        etPasswordSecond = view.findViewById(R.id.etPasswordSecond);
        ivNext = view.findViewById(R.id.ivNext);
        ivNext.setOnClickListener(this);

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) { }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.length() == 0) isNextEnable = false;
                else if (arg0.length() > 0) isNextEnable = true;
                onImageTernar();
            }
        });
        etPasswordSecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) { }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.length() == 0) isNextEnable = false;
                else if (arg0.length() > 0) isNextEnable = true;
                onImageTernar();
            }
        });
    }

    private void changeText() {
        tvPasswordTitle.setText(activity.onTranslateString(R.string.create_password));
        etPassword.setHint(activity.onTranslateString(R.string.enter_password));
    }

    private void onImageTernar() {
        isNextEnable = etPassword.getText().toString().equals(etPasswordSecond.getText().toString())
                && isPasswordValid(etPassword.getText().toString());
        ivNext.setImageResource(isNextEnable? R.drawable.img_right_arrow_green : R.drawable.img_right_arrow_uncheck_green);
    }

    private void onNext() {
        if (!isNextEnable) return;
        activity.alert.hideKeyboard(activity);
        activity.setChildPassword(etPassword.getText().toString());
        activity.onNextFragment();
        ivNext.setClickable(true);
        //activity.onPasswordString(MD5(etPassword.getText().toString()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNext:
                ivNext.setClickable(false);
                onNext();
                break;
        }
    }
}
