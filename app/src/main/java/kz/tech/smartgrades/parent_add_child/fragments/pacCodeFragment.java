package kz.tech.smartgrades.parent_add_child.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.AuthActivity;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent_add_child.ParentAddChildActivity;

import static kz.tech.smartgrades.Convert.MD5;

public class pacCodeFragment extends Fragment implements View.OnClickListener {

    private ParentAddChildActivity activity;

    private ConstraintLayout clCode;
    private ConstraintLayout clLoading;
    private ImageView[] ivDot1 = new ImageView[4];
    private ImageView[] ivDot2 = new ImageView[4];
    private ImageView ivRemoveCode;
    private LinearLayout llDots2;
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
        ivRemoveCode = view.findViewById(R.id.ivRemoveCode);
        clCode = view.findViewById(R.id.clCode);
        clLoading = view.findViewById(R.id.clLoading);
        ivRemoveCode.setOnClickListener(this);
        tvTitle = view.findViewById(R.id.tvAccessTitle);
        llDots2 = view.findViewById(R.id.llDots2);

        ivDot1[0] = view.findViewById(R.id.ivDot1_1);
        ivDot1[1] = view.findViewById(R.id.ivDot1_2);
        ivDot1[2] = view.findViewById(R.id.ivDot1_3);
        ivDot1[3] = view.findViewById(R.id.ivDot1_4);

        ivDot2[0] = view.findViewById(R.id.ivDot2_1);
        ivDot2[1] = view.findViewById(R.id.ivDot2_2);
        ivDot2[2] = view.findViewById(R.id.ivDot2_3);
        ivDot2[3] = view.findViewById(R.id.ivDot2_4);

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
        tvNum0 = view.findViewById(R.id.tvNum0);
        tvNum0.setOnClickListener(this);
    }

    private void changeText() {
        tvTitle.setText(activity.onTranslateString(R.string.create_access));
    }

    private void onClear() {
        AccessCode = "";
        SecondAccessCode = false;
        tvTitle.setText(activity.onTranslateString(R.string.create_access));
        llDots2.setVisibility(View.GONE);
        ivDot1[0].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
        ivDot1[1].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
        ivDot1[2].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
        ivDot1[3].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
        ivDot2[0].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
        ivDot2[1].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
        ivDot2[2].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
        ivDot2[3].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
    }

    private void onGood() {
        activity.setChildAccessCode(AccessCode);
        activity.onRegisterChild();
        startActivity(new Intent(activity, ParentActivity.class));
        activity.finish();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void onRemoveCode() {
        if (ivDot2[2].getBackground().getConstantState() == activity.getResources().getDrawable(R.drawable.view_oval_green).getConstantState()) {
            ivDot2[2].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
            AccessCode = AccessCode.substring(0, 2);
        } else if (ivDot2[1].getBackground().getConstantState() == activity.getResources().getDrawable(R.drawable.view_oval_green).getConstantState()) {
            ivDot2[1].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
            AccessCode = AccessCode.substring(0, 1);
        } else if (ivDot2[0].getBackground().getConstantState() == activity.getResources().getDrawable(R.drawable.view_oval_green).getConstantState()) {
            ivDot2[0].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
            AccessCode = AccessCode.substring(0, 0);
            //почистить все
            llDots2.setVisibility(View.GONE);
            AccessCode = "";
            oldAccessCode = "";
            SecondAccessCode = false;
            ivDot1[0].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
            ivDot1[1].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
            ivDot1[2].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
            ivDot1[3].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
        } else if (ivDot1[2].getBackground().getConstantState() == activity.getResources().getDrawable(R.drawable.view_oval_green).getConstantState()) {
            ivDot1[2].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
            AccessCode = AccessCode.substring(0, 2);
        } else if (ivDot1[1].getBackground().getConstantState() == activity.getResources().getDrawable(R.drawable.view_oval_green).getConstantState()) {
            ivDot1[1].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
            AccessCode = AccessCode.substring(0, 1);
        } else if (ivDot1[0].getBackground().getConstantState() == activity.getResources().getDrawable(R.drawable.view_oval_green).getConstantState()) {
            ivDot1[0].setBackground(getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
            AccessCode = AccessCode.substring(0, 0);
            ivRemoveCode.setVisibility(View.INVISIBLE);
        }
    }

    private void onAccessCode(String code) {
        AccessCode += code;
        if (!SecondAccessCode){
            ivDot1[AccessCode.length() - 1].setBackground(getResources().getDrawable(R.drawable.view_oval_green));
            if (AccessCode.length() == 4) {
                tvTitle.setText(getString(R.string.Repeat_the_access_code));
                llDots2.setVisibility(View.VISIBLE);
                oldAccessCode = AccessCode;
                AccessCode = "";
                SecondAccessCode = true;
            }
        }
        else {
            ivDot2[AccessCode.length() - 1].setBackground(getResources().getDrawable(R.drawable.view_oval_green));
            if (AccessCode.length() == oldAccessCode.length()) {
                if (AccessCode.equals(oldAccessCode)) onGood();
                else onClear();
            }
        }
        if (AccessCode.length() > 0) {
            ivRemoveCode.setVisibility(View.VISIBLE);
        } else {
            ivRemoveCode.setVisibility(View.INVISIBLE);
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
            case R.id.ivRemoveCode:
                onRemoveCode();
                break;
        }
    }
}