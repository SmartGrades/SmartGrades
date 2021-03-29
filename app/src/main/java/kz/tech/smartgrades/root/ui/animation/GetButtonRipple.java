package kz.tech.smartgrades.root.ui.animation;

import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import androidx.annotation.RequiresApi;

public class GetButtonRipple {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RippleDrawable getRipple(float radius, int stroke, int colorStroke, int colorFirst, int colorSecond) {
        return new RippleDrawable(
                new GetColorStateList()
                .getStateList(colorSecond),
                new GetSelectDrawable()
                .onChangeBackground(radius, stroke, colorStroke, colorFirst), null);
    }
}


