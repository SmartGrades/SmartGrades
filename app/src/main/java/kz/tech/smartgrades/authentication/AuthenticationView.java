package kz.tech.smartgrades.authentication;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import kz.tech.smartgrades.L;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;

public class AuthenticationView extends FrameLayout {
    private static final String[] PARAMS = {"layW:mPrt", "layH:mPrt"};
    public AuthenticationView(@NonNull Context c) {
        super(c);
        this.setLayoutParams(new FrameLayoutParams().getParams(c, PARAMS));
        this.setId(L.layout_authentication);
    }

    public AuthenticationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AuthenticationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AuthenticationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
