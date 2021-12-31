package kz.tech.smartgrades.root.ui.animation;

import android.graphics.drawable.GradientDrawable;

public class GetSelectDrawable {
    public GradientDrawable onChangeBackground(float radius, int stroke, int colorStroke, int colorBackground) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(radius);
        gradientDrawable.setStroke(
                stroke,
                colorStroke
                );
        gradientDrawable.setColor(colorBackground);
        return gradientDrawable;
    }
}
