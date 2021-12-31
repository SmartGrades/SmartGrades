package kz.tech.smartgrades.parents_module.family_group.list_view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;


public class FamilyMembersListView extends FrameLayout {
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt", "parH:50"};
    private static final String[] PARAM_AVATAR = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "parW:50",
            "parH:30", "GRAV:TOP_LEFT", "ID:400201", "pad:0,5,0,0"};
    private static final String[] PARAM_NAME = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "parW:50", "parH:20",
            "GRAV:BOTTOM_LEFT", "ID:400202", "txtC:GRAY_THREE", "txtS:12", "grv:CHCV"};
    private static final String[] PARAM_STATUS = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "parH:45",
            "GRAV:TOP", "mrg:50,0,50,0", "ID:400203", "txtC:BLACK", "grv:CHCV"};
    private static final String[] PARAM_VIEW_LINE = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "GRAV:BOTTOM",
            "parH:1", "backC:GRAY_THREE"};

    public FamilyMembersListView(Context context) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        this.setBackgroundColor(ColorsRGB.WHITE);

        CircleImageView civAvatar = new CustomCircleImageView().onCustom(context, PARAM_AVATAR);
        this.addView(civAvatar);

        TextView tvName = new CustomTextView().onCustom(context, PARAM_NAME, null);
        this.addView(tvName);

        TextView tvStatus = new CustomTextView().onCustom(context, PARAM_STATUS, null);
        this.addView(tvStatus);

        View v = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        this.addView(v);

    }

    public FamilyMembersListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FamilyMembersListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FamilyMembersListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
