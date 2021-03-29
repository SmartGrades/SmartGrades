package kz.tech.smartgrades.root.var_resources;

import android.content.Context;
import android.util.TypedValue;

public class DimensionDP {
    public static int sizeDP(float count, Context context) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, count, context.getResources().getDisplayMetrics());
        int numb = Math.round(px);
        return numb;
    }
}
