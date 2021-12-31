package kz.tech.smartgrades.mentor_module.cabinet;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;

public class CabinetView extends FrameLayout {
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};

    private static final String[] PARAM_CONTAINER = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:0,5,0,0", "orn:ver"};
    public CabinetView(Context context, Resources res) {
        super(context);



        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER);
        this.addView(linearLayout);
    }

    public CabinetView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CabinetView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CabinetView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
