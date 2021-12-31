package kz.tech.smartgrades.parents_module.family_group.dialogs_page;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class ListViewMentorList extends FrameLayout {
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt", "parH:50", "mrg:0,0,0,5"};
    private static final String[] PARAM_AVATAR = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "mrg:15,0,0,0",
            "parW:50", "parH:50", "GRAV:LEFT", "ID:400301", "pad:5,5,5,5"};


    private static final String[] PARAM_LOGIN = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:70,0,50,20",
            "parH:30", "GRAV:HOR", "ID:400302", "txtC:BLACK",  "grv:LCV"};
    private static final String[] PARAM_FULL_NAME = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:70,30,50,0",
            "parH:20", "GRAV:HOR",  "ID:400303", "txtC:GRAY_THREE", "grv:LCV"};


    private static final String[] PARAM_NEXT = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "parW:45",
            "parH:45", "GRAV:RIGHT_VER", "ID:400304", "pad:5,5,5,5"};
    public ListViewMentorList(Context context) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));

        CircleImageView civAvatar = new CustomCircleImageView().onCustom(context, PARAM_AVATAR);
        this.addView(civAvatar);

        TextView tvLogin = new CustomTextView().onCustom(context, PARAM_LOGIN, null);
        this.addView(tvLogin);

        TextView tvFullName = new CustomTextView().onCustom(context, PARAM_FULL_NAME, null);
        this.addView(tvFullName);

        ImageView ivNext = new CustomImageView().onCustom(context, PARAM_NEXT, R.mipmap.red_right);
        this.addView(ivNext);

    }

    public ListViewMentorList(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewMentorList(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ListViewMentorList(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
