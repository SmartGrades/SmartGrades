package kz.tech.smartgrades.parent_add_child.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent_add_child.ParentAddChildActivity;

import static kz.tech.smartgrades.Convert.MD5;

public class pacCodeFragment extends Fragment implements View.OnClickListener {

    private ParentAddChildActivity activity;

    private ImageView[] ivDot = new ImageView[4];
    private TextView tvTitle, tvNum1, tvNum2, tvNum3, tvNum4, tvNum5,
            tvNum6, tvNum7, tvNum8, tvNum9, tvNum0;

    private boolean SecondAccessCode = false;
    private String AccessCode = "";
    private String oldAccessCode = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddChildActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_access_enter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        changeText();
    }

    private void initViews(View view) {
        tvTitle = view.findViewById(R.id.tvAccessTitle);

        ivDot[0] = view.findViewById(R.id.ivDot1_1);
        ivDot[1] = view.findViewById(R.id.ivDot1_2);
        ivDot[2] = view.findViewById(R.id.ivDot1_3);
        ivDot[3] = view.findViewById(R.id.ivDot1_4);

        tvNum0 = view.findViewById(R.id.tvNum0);
        tvNum0.setOnClickListener(this);
        tvNum1 = view.findViewById(R.id.tvNum1);
        tvNum1.setOnClickListener(this);
        tvNum2 = view.findViewById(R.id.tvNum2);
        tvNum2.setOnClickListener(this);
        tvNum3 = view.findViewById(R.id.tvNum3);
        tvNum3.setOnClickListener(this);
        tvNum4 = view.findViewById(R.id.tvNum4);
        tvNum4.setOnClickListener(this);
        tvNum5 = view.findViewById(R.id.tvNum5);
        tvNum5.setOnClickListener(this);
        tvNum6 = view.findViewById(R.id.tvNum6);
        tvNum6.setOnClickListener(this);
        tvNum7 = view.findViewById(R.id.tvNum7);
        tvNum7.setOnClickListener(this);
        tvNum8 = view.findViewById(R.id.tvNum8);
        tvNum8.setOnClickListener(this);
        tvNum9 = view.findViewById(R.id.tvNum9);
        tvNum9.setOnClickListener(this);
    }

    private void changeText() {
        tvTitle.setText(activity.onTranslateString(R.string.create_access));
    }

    private void onBack() {
        onClear();
        activity.onBackPressed();
    }

    private void onClear() {
        AccessCode = "";
        tvTitle.setText(activity.onTranslateString(R.string.create_access));
        ivDot[0].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
        ivDot[1].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
        ivDot[2].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
        ivDot[3].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
        SecondAccessCode = false;
    }

    private void onGood() {
        activity.setChildAccessCode(MD5(AccessCode));
        activity.onRegisterChild();
        startActivity(new Intent(activity, ParentActivity.class));
        activity.finish();
    }

    private void onAccessCode(String code) {
        AccessCode += code;
        ivDot[AccessCode.length() - 1].setBackground(getResources().getDrawable(R.drawable.view_oval_green));
        if (!SecondAccessCode){
            if (AccessCode.length() == 4) {
                tvTitle.setText("Повторите код доступа.");
                ivDot[0].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
                ivDot[1].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
                ivDot[2].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
                ivDot[3].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
                oldAccessCode = AccessCode;
                AccessCode = "";
                SecondAccessCode = true;
            }
        }
        else {
            if (AccessCode.length() == oldAccessCode.length()) {
                if (AccessCode.equals(oldAccessCode)) onGood();
                else {
                    tvTitle.setText("Неверный код доступа! Введите новый код доступа.");
                    ivDot[0].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
                    ivDot[1].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
                    ivDot[2].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
                    ivDot[3].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
                    AccessCode = "";
                    SecondAccessCode = false;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNum0:
                onAccessCode("0");
                break;
            case R.id.tvNum1:
                onAccessCode("1");
                break;
            case R.id.tvNum2:
                onAccessCode("2");
                break;
            case R.id.tvNum3:
                onAccessCode("3");
                break;
            case R.id.tvNum4:
                onAccessCode("4");
                break;
            case R.id.tvNum5:
                onAccessCode("5");
                break;
            case R.id.tvNum6:
                onAccessCode("6");
                break;
            case R.id.tvNum7:
                onAccessCode("7");
                break;
            case R.id.tvNum8:
                onAccessCode("8");
                break;
            case R.id.tvNum9:
                onAccessCode("9");
                break;
        }
    }
}