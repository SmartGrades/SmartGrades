package kz.tech.smartgrades.parent.bottom_sheet_dialogs;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades._System;
import kz.tech.smartgrades.parent.model.ModelSetLessonSmartGrades;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class BSDOtherLessonToSmart extends BottomSheetDialog implements View.OnClickListener, View.OnFocusChangeListener {

    private Context context;

    private ImageView ivBack;
    private TextView tvTitle;

    private FrameLayout[] FL = new FrameLayout[5];
    private FrameLayout[] FL_SELECT = new FrameLayout[5];
    private ImageView[] ivPlusOrMinus = new ImageView[5];
    private EditText[] etReward = new EditText[5];

    private Button btnSave;
    private boolean[] isButtonEnable = new boolean[5];

    private int Index = -1;
    private boolean[] bPlus = new boolean[5];

    private ModelSetLessonSmartGrades mRewardForGeades = new ModelSetLessonSmartGrades();

    private OnItemClickListener onItemClickListener;

    private TextView tvAccept;
    private TextView tvCancel;
    private TextView tvDisable;

    private ImageView ivNav;
    private RelativeLayout relativeLayout;

    public interface OnItemClickListener {
        void onClick(ModelSetLessonSmartGrades mRewardForGeades);
        void onCancel();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BSDOtherLessonToSmart(@NonNull Context context) {
        super(context);
        this.context = context;

        View view = getLayoutInflater().inflate(R.layout.fragment_parent_smart_grates_setting, null, false);
        setContentView(view);

        View bottomSheet = findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        bottomSheet.setLayoutParams(layoutParams);
        layoutParams.height = (int)(_System.getWindowHeight(context) * 0.8);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        initViews(view);
    }
    private void initViews(View view) {
        ivNav = findViewById(R.id.ivNav);
        relativeLayout = findViewById(R.id.relativeLayout);
        ivNav.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
//        ivBack = view.findViewById(R.id.ivBack);
//        ivBack.setOnClickListener(this);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvAccept = view.findViewById(R.id.tvAccept);
        tvAccept.setOnClickListener(this);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        tvDisable = view.findViewById(R.id.tvDisable);
        tvDisable.setOnClickListener(this);

        FL_SELECT[0] = view.findViewById(R.id.flSelectAbsent);
        FL_SELECT[1] = view.findViewById(R.id.flSelect2);
        FL_SELECT[2] = view.findViewById(R.id.flSelect3);
        FL_SELECT[3] = view.findViewById(R.id.flSelect4);
        FL_SELECT[4] = view.findViewById(R.id.flSelect5);

        FL[0] = view.findViewById(R.id.flAbsent);
        FL[0].setOnClickListener(this);
        FL[1] = view.findViewById(R.id.fl2);
        FL[1].setOnClickListener(this);
        FL[2] = view.findViewById(R.id.fl3);
        FL[2].setOnClickListener(this);
        FL[3] = view.findViewById(R.id.fl4);
        FL[3].setOnClickListener(this);
        FL[4] = view.findViewById(R.id.fl5);
        FL[4].setOnClickListener(this);

        etReward[0] = view.findViewById(R.id.etAbsentReward);
        etReward[1] = view.findViewById(R.id.et2Reward);
        etReward[2] = view.findViewById(R.id.et3Reward);
        etReward[3] = view.findViewById(R.id.et4Reward);
        etReward[4] = view.findViewById(R.id.et5Reward);

        etReward[0].setOnFocusChangeListener(this);
        etReward[1].setOnFocusChangeListener(this);
        etReward[2].setOnFocusChangeListener(this);
        etReward[3].setOnFocusChangeListener(this);
        etReward[4].setOnFocusChangeListener(this);

        etReward[0].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                int sum = Convert.S2I(etReward[0].getText().toString());
                isButtonEnable[0] = sum != 0;
                mRewardForGeades.setRewardForN(Integer.toString(sum));
            }
        });
        etReward[1].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                int sum = Convert.S2I(etReward[1].getText().toString());
                isButtonEnable[1] = sum != 0;
//                isButtonEnabled();
                mRewardForGeades.setRewardFor2(Integer.toString(sum));
            }
        });
        etReward[2].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                int sum = Convert.S2I(etReward[2].getText().toString());
                isButtonEnable[2] = sum != 0;
//                isButtonEnabled();
                mRewardForGeades.setRewardFor3(Integer.toString(sum));
//                if(sum < 0) ivPlusOrMinus[Index].setImageResource(R.drawable.img_togle_minus_red);
//                else ivPlusOrMinus[Index].setImageResource(R.drawable.img_togle_plus_purple);
            }
        });
        etReward[3].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                int sum = Convert.S2I(etReward[3].getText().toString());
                isButtonEnable[3] = sum != 0;
//                isButtonEnabled();
                mRewardForGeades.setRewardFor4(Integer.toString(sum));
                if(sum < 0) ivPlusOrMinus[Index].setImageResource(R.drawable.img_togle_minus_red);
                else ivPlusOrMinus[Index].setImageResource(R.drawable.img_togle_plus_purple);
            }
        });
        etReward[4].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                int sum = Convert.S2I(etReward[4].getText().toString());
                isButtonEnable[4] = sum != 0;
//                isButtonEnabled();
                mRewardForGeades.setRewardFor5(Integer.toString(sum));
            }
        });

        ivPlusOrMinus[0] = view.findViewById(R.id.ivPlusOrMinusAbsent);
        ivPlusOrMinus[1] = view.findViewById(R.id.ivPlusOrMinus2);
        ivPlusOrMinus[2] = view.findViewById(R.id.ivPlusOrMinus3);
        ivPlusOrMinus[3] = view.findViewById(R.id.ivPlusOrMinus4);
        ivPlusOrMinus[4] = view.findViewById(R.id.ivPlusOrMinus5);

        ivPlusOrMinus[3].setOnClickListener(this);

//        btnSave = view.findViewById(R.id.btnSave);
//        btnSave.setOnClickListener(this);
    }

    private void onDisable() {
        if (onDisableClickListener == null) return;
        onDisableClickListener.onDisable();
    }

    private OnDisableClickListener onDisableClickListener;

    public interface OnDisableClickListener {
        void onDisable();
    }

    public void setOnDisableClickListener(OnDisableClickListener onDisableClickListener) {
        this.onDisableClickListener = onDisableClickListener;
    }

    public void setVisibleDisable() {
        tvDisable.setVisibility(View.VISIBLE);
    }

//    private void isButtonEnabled() {
//        if (isButtonEnable[0] || isButtonEnable[1] || isButtonEnable[2] || isButtonEnable[3]) {
//            btnSave.setEnabled(true);
//            btnSave.setBackgroundResource(R.drawable.background_rectangle_purple);
//        } else {
//            btnSave.setEnabled(false);
//            btnSave.setBackgroundResource(R.drawable.background_rectangle_gray);
//        }
//    }
    private void onBack() {
        dismiss();
    }
    private void onFLClick(int index) {
        etReward[index].requestFocus();
    }
    private void onPlusOrMinus(int index) {
        if(Index < 0) Index=index;
        if (bPlus[Index]) {
            bPlus[Index] = false;
            ivPlusOrMinus[Index].setImageResource(R.drawable.img_togle_minus_red);
            String text = etReward[Index].getText().toString();
            if (text.length() > 0 && !text.substring(0, 1).equals("-"))
                etReward[Index].setText("-" + text);
            else etReward[Index].setText("-");
            etReward[Index].setSelection(etReward[Index].getText().length());
        } else {
            bPlus[Index] = true;
            ivPlusOrMinus[Index].setImageResource(R.drawable.img_togle_plus_purple);
            String text = etReward[Index].getText().toString();
            if (text.length() > 0 && text.substring(0, 1).equals("-"))
                etReward[Index].setText(text.subSequence(1, text.length()));
        }
        if (Index != index) {
            etReward[index].requestFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etReward[index], InputMethodManager.SHOW_IMPLICIT);
        }
    }
    private void onOkey() {
        if (onItemClickListener == null) return;
        onItemClickListener.onClick(mRewardForGeades);
    }

    private void onCancel() {
        if (onItemClickListener == null) return;
        onItemClickListener.onCancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.flAbsent:
                onFLClick(0);
                break;
            case R.id.fl2:
                onFLClick(1);
                break;
            case R.id.fl3:
                onFLClick(2);
                break;
            case R.id.fl4:
                onFLClick(3);
                break;
            case R.id.fl5:
                onFLClick(4);
                break;

//            case R.id.ivPlusOrMinus4:
//                bPlus[3] = true;
//                onPlusOrMinus(3);
//                break;

            case R.id.tvAccept:
                if (!stringIsNullOrEmpty(mRewardForGeades.getRewardForN()) &&
                        !stringIsNullOrEmpty(mRewardForGeades.getRewardFor2()) &&
                        !stringIsNullOrEmpty(mRewardForGeades.getRewardFor3()) &&
                        !stringIsNullOrEmpty(mRewardForGeades.getRewardFor4()) &&
                        !stringIsNullOrEmpty(mRewardForGeades.getRewardFor5())) {
                    onOkey();
                }
                break;
            case R.id.tvCancel:
                onCancel();
                break;
            case R.id.tvDisable:
                onDisable();
                break;
        }
    }
    private void onFocusChange(int index) {
        if (Index >= 0) {
            FL_SELECT[Index].setBackground(null);
            etReward[Index].setTextColor(context.getResources().getColor(R.color.blue_sea));
            String text = etReward[Index].getText().toString();
            if (text.isEmpty() || text.equals("-") || text.equals("-0")) etReward[Index].setText("0");
            int sum = Convert.S2I(text);
            if (sum < 0)
                ivPlusOrMinus[Index].setImageResource(R.drawable.img_togle_minus_red);
            else if (sum > 0)
                ivPlusOrMinus[Index].setImageResource(R.drawable.img_togle_plus_purple);
            else
                ivPlusOrMinus[Index].setImageResource(R.drawable.img_togle_neutral_gray);
        }
        Index = index;
        FL_SELECT[Index].setBackgroundResource(R.drawable.background_square_blue_sea);
        FL_SELECT[Index].setPadding(10,5,10,5);
        etReward[Index].setTextColor(Color.WHITE);
        String text = etReward[Index].getText().toString();
        if (Index < 3) {
            ivPlusOrMinus[Index].setImageResource(R.drawable.img_togle_minus_red);
            if (text.isEmpty() || text.equals("0") || text.equals("-")){
                etReward[Index].setText("-");
                etReward[Index].setSelection(etReward[Index].getText().length());
            }
        }
        else {
            ivPlusOrMinus[Index].setImageResource(R.drawable.img_togle_plus_purple);
            if (text.isEmpty() || text.equals("0") || text.equals("-"))
                etReward[Index].setText("");
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etReward[Index], InputMethodManager.SHOW_IMPLICIT);
    }
    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) return;
        switch (view.getId()) {
            case R.id.etAbsentReward:
                onFocusChange(0);
                break;
            case R.id.et2Reward:
                onFocusChange(1);
                break;
            case R.id.et3Reward:
                onFocusChange(2);
                break;
            case R.id.et4Reward:
                onFocusChange(3);
                break;
            case R.id.et5Reward:
                onFocusChange(4);
                break;

        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();
        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {
                final InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                View focus = getCurrentFocus();
                if (focus != null) {
                    im.hideSoftInputFromWindow(focus.getWindowToken(), 0);
                }
            }

        }
        return super.dispatchTouchEvent(ev);
    }

//    public void setEtReward(ModelGradesSettings mRewardForGeades) {
//        etReward[0].setText(Integer.toString(mRewardForGeades.getRewardForN()));
//        etReward[1].setText(Integer.toString(mRewardForGeades.getRewardFor2()));
//        etReward[2].setText(Integer.toString(mRewardForGeades.getRewardFor3()));
//        etReward[3].setText(Integer.toString(mRewardForGeades.getRewardFor4()));
//        etReward[4].setText(Integer.toString(mRewardForGeades.getRewardFor5()));
//    }
}