package kz.tech.smartgrades.family_room.fragments.family_member_list.adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RecyclerViewParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.var_resources.DimensionDP;
import kz.tech.smartgrades.root.var_resources.VarID;


public class FamilyMemberView extends CardView {
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt", "parH:80", "mrg:8,8,8,0"};
    private static final String[] PARAM_RV = {"prt:CardView", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_AVATAR = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:80", "pad:5,5,5,5"};
    private static final String[] PARAM_STATUS = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parH:40",
            "mrg:80,0,0,0", "pad:5,5,5,5", "grv:LCV", "txtC:BLACK", "TyFa:BOLD"};
    private static final String[] PARAM_NAME = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parH:40", "mrg:80,40,0,0", "pad:5,5,5,5", "grv:LCV"};
    public FamilyMemberView(@NonNull Context context) {
        super(context);
        this.setLayoutParams(new RecyclerViewParams().getParams(context, PARAM));
        this.setRadius(DimensionDP.sizeDP(8, context));

        RelativeLayout relativeLayout = new CustomRelativeLayout().onCustom(context, PARAM_RV);
        this.addView(relativeLayout);

        CircleImageView civAvatar = new CustomCircleImageView().onCustom(context, PARAM_AVATAR);
        relativeLayout.addView(civAvatar);
        civAvatar.setId(VarID.ID_CIV_FAMILY_AVATAR);

        TextView tvStatus = new CustomTextView().onCustom(context, PARAM_STATUS, null);
        relativeLayout.addView(tvStatus);
        tvStatus.setId(VarID.ID_TV_FAMILY_STATUS);

        TextView tvName = new CustomTextView().onCustom(context, PARAM_NAME, null);
        relativeLayout.addView(tvName);
        tvName.setId(VarID.ID_TV_FAMILY_NAME);
    }

    public FamilyMemberView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FamilyMemberView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
