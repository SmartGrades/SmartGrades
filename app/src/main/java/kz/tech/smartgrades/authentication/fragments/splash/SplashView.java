package kz.tech.smartgrades.authentication.fragments.splash;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;

import static kz.tech.smartgrades.root.var_resources.ColorsRGB.WHITE;

public class SplashView extends FrameLayout {
    private static final String[] param = {"prt:FrmLay", "layW:mPrt", "layH:mPrt"};
    private static final String[] paramSplash = {"prt:LinLay", "layW:mPrt", "layH:mPrt"};
    public SplashView(Context context) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, param));
        this.setBackgroundColor(WHITE);
        ImageView imageView = new CustomImageView().onCustom(context, paramSplash, R.drawable.splash);
        this.addView(imageView);
    }
    public SplashView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public SplashView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SplashView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
