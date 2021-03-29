package kz.tech.smartgrades.parents_module.coins;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.var_resources.VarID;

public class SquareImageView extends RelativeLayout {
    private static final String[] PARAM = {"layW:wCnt", "layH:wCnt", "mrg:5,5,5,0"};
    private static final String[] PARAM_AVATAR = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "Alpha:4"};
    public SquareImageView(@NonNull Context context) {
        super(context);
        this.setLayoutParams(new RelativeLayoutParams().getParams(context, PARAM));
        ImageView imageView = new CustomImageView().onCustom(context, PARAM_AVATAR, R.mipmap.blue_square);
        this.addView(imageView);
        imageView.setId(VarID.ID_IV_SQUARE);
    }

    public SquareImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
