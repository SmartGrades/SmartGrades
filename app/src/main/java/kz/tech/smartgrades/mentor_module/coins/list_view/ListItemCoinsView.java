package kz.tech.smartgrades.mentor_module.coins.list_view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class ListItemCoinsView extends FrameLayout {
    private static final String[] PARAM = {"layW:wCnt", "layH:wCnt", "parW:50", "parH:50"};
    private static final String[] PARAM_IV = {"prt:FrmLay", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_TV = {"prt:FrmLay", "layW:mPrt", "layH:mPrt",
            "txtC:GRAY_THREE", "grv:CHCV", "txtS:14"};
    public ListItemCoinsView(Context c, int i, String t) {
        super(c);
        int img = 0;
        switch (i) {
            case 1: img = R.mipmap.red_monet; break;
            case 2: img = R.mipmap.green_monet; break;
            case 3: img = R.mipmap.blue_square; break;
            case 4: img = R.mipmap.done; break;
        }
        this.setLayoutParams(new FrameLayoutParams().getParams(c, PARAM));

        ImageView iv = new CustomImageView().onCustom(c, PARAM_IV, img);
        this.addView(iv);

        TextView tv = new CustomTextView().onCustom(c, PARAM_TV, t);
        this.addView(tv);
    }

    public ListItemCoinsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ListItemCoinsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ListItemCoinsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
