package kz.tech.smartgrades.childs_module.adapters;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;

import static kz.tech.smartgrades.root.var_resources.VarID.ID_IV_COINS_ITEMS;

public class ItemCoins extends FrameLayout {
    private static final String[] PARAM = {"layW:wCnt", "layH:wCnt"};
    private static final String[] PARAM_IMAGE = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "parW:40", "parH:40", "pad:5,5,5,5"};
    public ItemCoins(@NonNull Context context) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        ImageView imageView = new CustomImageView().onCustom(context, PARAM_IMAGE, 0);
        this.addView(imageView);
        imageView.setId(ID_IV_COINS_ITEMS);
    }

    public ItemCoins(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemCoins(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ItemCoins(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}



