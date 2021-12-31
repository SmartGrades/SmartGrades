package kz.tech.smartgrades.parents_module.settings;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomScrollView;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;

public class SettingsView extends FrameLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onNextPageClick(String fragment);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};


    private static final String[] PARAM_CONTAINER_SV = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,5,0,0"};
    private static final String[] PARAM_CONTAINER_LL = {"prt:ScrLay", "layW:mPrt", "layH:mPrt", "orn:ver"};

    private static final String[] PARAM_CONTAINER_TOP_LL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:56",
            "orn:hor", "WeiSum:6", "backC:WHITE"};

    private static final String[] PARAM_ICON = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1", "mrg:20,0,0,0", "pad:5,5,5,5"};
    private static final String[] PARAM_TEXT = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:4",
            "txtC:BLACK", "grv:LCV", "pad:5,5,5,5"};
    private static final String[] PARAM_MOVE = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1", "pad:5,5,5,5"};


    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:1", "backC:GRAY_THREE"};

    private Context context;
    private CircleImageView civAvatar;
    private LinearLayout linearLayout;

    public SettingsView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        this.context = context;


        ScrollView scrollView = new CustomScrollView().onCustom(context, PARAM_CONTAINER_SV);
        this.addView(scrollView);

        linearLayout = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        scrollView.addView(linearLayout);

        int[] icons = {R.mipmap.personal_data,
                R.mipmap.timer,
                R.mipmap.auto_charge,
                R.mipmap.notifications,
                R.mipmap.language,
                R.mipmap.quick_access_code,
                R.mipmap.change_password};
        String[] texts = {res.getString(R.string.personal_data), res.getString(R.string.time_children), res.getString(R.string.auto_charge),
                res.getString(R.string.notification), res.getString(R.string.change_language),
                res.getString(R.string.change_quick_access_code), res.getString(R.string.change_password)};
        String[] fragments = {"PersonalDataFragment", "", "", "NotificationFragment",
                "LocalityFragment", "ChangePinCodeFragment", "ChangePasswordFragment"};

        for (int i = 0; i < 7; i++) {
            setSelectButton(i, icons[i], texts[i], fragments[i]);
        }
    }
    private void setSelectButton(int pos, int image, String text, String fragment) {
        if (pos == 0) {
            View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
            linearLayout.addView(v1);
        }
        LinearLayout llContainer = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_TOP_LL);
        linearLayout.addView(llContainer);

        ImageView ivIcon = new CustomImageView().onCustom(context, PARAM_ICON, image);
        llContainer.addView(ivIcon);

        TextView tvText = new CustomTextView().onCustom(context, PARAM_TEXT, text);
        llContainer.addView(tvText);

        ImageView ivMove = new CustomImageView().onCustom(context, PARAM_MOVE, R.mipmap.left_gray_small_arrow);
        llContainer.addView(ivMove);
        llContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onNextPageClick(fragment);
                }
            }
        });
        View v2 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        linearLayout.addView(v2);
    }
    public SettingsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public SettingsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SettingsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
