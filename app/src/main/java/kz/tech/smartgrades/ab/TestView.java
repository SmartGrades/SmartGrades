package kz.tech.smartgrades.ab;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import kz.tech.smartgrades.childs_module.custom_recycler_grid_layout.MyGrid;

public class TestView extends LinearLayout {
    private Context context;
    public TestView(Context context) {
        super(context);
        this.context = context;
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TestView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setData(String coins) {
        this.addView(new MyGrid(context, coins));
    }
}
