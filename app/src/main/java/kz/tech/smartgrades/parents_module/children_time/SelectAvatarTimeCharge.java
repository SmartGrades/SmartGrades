package kz.tech.smartgrades.parents_module.children_time;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class SelectAvatarTimeCharge extends LinearLayout {
    public ImageView imageView;
    public TextView textView;
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_IMAGE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:40"};
    private static final String[] PARAM_TEXT = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:16",
            "grv:TCH", "txtC:GRAY_THREE"};

    public SelectAvatarTimeCharge(@NonNull Context context) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        this.setOrientation(VERTICAL);
        imageView = new CustomImageView().onCustom(context, PARAM_IMAGE, 0);
        this.addView(imageView);
        textView = new CustomTextView().onCustom(context, PARAM_TEXT, null);
        this.addView(textView);
    }

    public SelectAvatarTimeCharge(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectAvatarTimeCharge(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SelectAvatarTimeCharge(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}