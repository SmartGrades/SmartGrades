package kz.tech.smartgrades.auth.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;
import java.util.Date;

import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades._System;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.dialogs.DialogInteractionToolStepsToys;
import kz.tech.smartgrades.root.var_resources.DimensionDP;


public class D_SelectBirthday extends BottomSheetDialog implements View.OnClickListener {

    private ImageView ivCancel;
    private DatePicker datePicker1;
    private Button button;

    public interface OnResultListener {
        void onResult(Calendar calendar);
    }
    private OnResultListener onResultListener;
    public void setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    public D_SelectBirthday(Context context) {
        super(context);

        View view = getLayoutInflater().inflate(R.layout.birthday_select, null, false);
        setContentView(view);

        View bottomSheet = findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        bottomSheet.setLayoutParams(layoutParams);
        layoutParams.height = (int) (_System.getWindowHeight(context) * 0.8);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        show();

        initViews(view);
    }
    private void initViews(View view) {
        ivCancel = view.findViewById(R.id.ivCancel);
        ivCancel.setOnClickListener(this);
        datePicker1 = view.findViewById(R.id.datePicker1);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.button:
                onSave();
                break;

        }
    }
    private void onBack() {
        dismiss();
    }
    private void onSave() {
        if (onResultListener != null) {
            Calendar myDate = Calendar.getInstance();
            myDate.set(Calendar.YEAR, datePicker1.getYear());
            myDate.set(Calendar.MONTH, datePicker1.getMonth());
            myDate.set(Calendar.DAY_OF_MONTH, datePicker1.getDayOfMonth());
            myDate.set(Calendar.HOUR, 0);
            myDate.set(Calendar.MINUTE, 0);
            myDate.set(Calendar.SECOND, 0);
            myDate.set(Calendar.MILLISECOND, 0);
            onResultListener.onResult(myDate);
        }
        dismiss();
    }


}