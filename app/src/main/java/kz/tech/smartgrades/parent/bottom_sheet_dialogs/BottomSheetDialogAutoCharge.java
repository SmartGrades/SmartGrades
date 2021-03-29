package kz.tech.smartgrades.parent.bottom_sheet_dialogs;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetDialogAutoCharge extends BottomSheetDialog {




    public BottomSheetDialogAutoCharge(@NonNull Context context) {
        super(context);
    }

    public BottomSheetDialogAutoCharge(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected BottomSheetDialogAutoCharge(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
