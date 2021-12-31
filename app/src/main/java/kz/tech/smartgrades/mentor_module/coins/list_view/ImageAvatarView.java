package kz.tech.smartgrades.mentor_module.coins.list_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RecyclerViewParams;

import static kz.tech.smartgrades.root.var_resources.VarID.ID_IV_AVATAR;


public class ImageAvatarView extends CardView {
    private static final String[] PARAM = {"layW:wCnt", "layH:wCnt", "mrg:5,5,5,0"};
    private static final String[] PARAM_RV = {"prt:CardView", "layW:wCnt", "layH:mPrt", "parH:50"};
    private static final String[] PARAM_AVATAR = {"prt:RelLay", "layW:wCnt", "layH:mPrt"};
    public ImageAvatarView(@NonNull Context context) {
        super(context);
        this.setLayoutParams(new RecyclerViewParams().getParams(context, PARAM));
        RelativeLayout relativeLayout = new CustomRelativeLayout().onCustom(context, PARAM_RV);
        this.addView(relativeLayout);
        ImageView imageView = new CustomImageView().onCustom(context, PARAM_AVATAR, 0);
        relativeLayout.addView(imageView);
        imageView.setId(ID_IV_AVATAR);
    }

    public ImageAvatarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageAvatarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
