package kz.tech.smartgrades.root.view_pager_child;

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

public class ListViewPhotoChild extends LinearLayout {
    public ImageView imageView;
    public TextView textView;
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_IMAGE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:55"};
    private static final String[] PARAM_TEXT = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:20",
            "grv:TCH", "txtC:GRAY_THREE"};

    public ListViewPhotoChild(@NonNull Context context) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        this.setOrientation(VERTICAL);
        imageView = new CustomImageView().onCustom(context, PARAM_IMAGE, 0);
        this.addView(imageView);
        textView = new CustomTextView().onCustom(context, PARAM_TEXT, null);
        this.addView(textView);
    }

    public ListViewPhotoChild(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewPhotoChild(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ListViewPhotoChild(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}