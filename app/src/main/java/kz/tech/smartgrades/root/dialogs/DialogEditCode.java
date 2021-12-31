package kz.tech.smartgrades.root.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelAnswer;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.SoapRequest;


public class DialogEditCode extends Dialog implements View.OnClickListener{

    private AppCompatActivity activity;
    private ImageView[] ivDot1 = new ImageView[4];
    private ImageView[] ivDot2 = new ImageView[4];
    private ImageView ivRemoveCode;
    private ImageView ivBack;
    private LinearLayout llDots2;
    private TextView tvTitle, tvNum1, tvNum2, tvNum3, tvNum4, tvNum5,
            tvNum6, tvNum7, tvNum8, tvNum9, tvNum0;

    private boolean CheckOldCode = true;//Отвечает за ввода кода на проверку валидности
    private boolean IsFirstEnter = true;//Отвечает за ввод нового кода впервые; false = идет повторный ввод нового кода

    private String AccessCode = "";
    private String oldAccessCode = "";

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onCheckCode(String code);
        void onSaveCode(String code);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public DialogEditCode(AppCompatActivity compatActivity){
        super(compatActivity, R.style.DefaultCustomDialog);
        this.activity = compatActivity;

        View view = getLayoutInflater().inflate(R.layout.dialog_access_edit, null, false);
        setContentView(view);
        initViews(view);
        onSetData();
        show();
    }
    private void initViews(View view){
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivRemoveCode = view.findViewById(R.id.ivRemoveCode);
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
    private void onSetData(){
        tvTitle.setText("Введите старый код доступа");
    }

    public void onSetAnswer(ModelAnswer answer){
        if(answer.isSuccess()){
            CheckOldCode = false;
            tvTitle.setText("Введите новый код");
        }
        else {
            tvTitle.setText("Неверный код, повторите");
        }
        AccessCode = "";
        for(ImageView imageView : ivDot1)
            imageView.setBackground(activity.getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
                break;
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
    private void onBack(){
        dismiss();
    }
    private void onAccessCode(String code){
        AccessCode += code;
        if(CheckOldCode || IsFirstEnter)
            ivDot1[AccessCode.length() - 1].setBackground(activity.getResources().getDrawable(R.drawable.view_oval_green));
        else ivDot2[AccessCode.length() - 1].setBackground(activity.getResources().getDrawable(R.drawable.view_oval_green));
        if(AccessCode.length() == 4){
            if(CheckOldCode) onItemClickListener.onCheckCode(AccessCode);
            else {
                if(IsFirstEnter){
                    IsFirstEnter = false;
                    oldAccessCode = AccessCode;
                    AccessCode = "";
                    tvTitle.setText("Повторите код");
                    llDots2.setVisibility(View.VISIBLE);
                }
                else {
                    if(AccessCode.equals(oldAccessCode)){
                        if(onItemClickListener != null) onItemClickListener.onSaveCode(AccessCode);
                        dismiss();
                    }
                    else {
                        IsFirstEnter = true;
                        AccessCode = "";
                        tvTitle.setText("Неверный код, повторите");
                        for(ImageView imageView : ivDot1)
                            imageView.setBackground(activity.getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
                        for(ImageView imageView : ivDot2)
                            imageView.setBackground(activity.getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
                    }
                }
            }
        }
        if(AccessCode.length() > 0) ivRemoveCode.setVisibility(View.VISIBLE);
        else ivRemoveCode.setVisibility(View.INVISIBLE);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void onRemoveCode(){
        if(stringIsNullOrEmpty(AccessCode)) return;
        AccessCode = AccessCode.substring(0, AccessCode.length() - 1);
        if(CheckOldCode || IsFirstEnter){
            ivDot1[AccessCode.length()].setBackground(activity.getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
        }
        else ivDot2[AccessCode.length()].setBackground(activity.getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
        if(AccessCode.length() <= 0) ivRemoveCode.setVisibility(View.INVISIBLE);
    }
}