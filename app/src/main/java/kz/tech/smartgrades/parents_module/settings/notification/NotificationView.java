package kz.tech.smartgrades.parents_module.settings.notification;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomSwitch;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;

public class NotificationView extends FrameLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onSwitchClick(int position, boolean select);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};

    private static final String[] PARAM_CONTAINER = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,5,0,0",
            "backC:WHITE"};
    private static final String[] PARAM_RL_1 = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:50"};
    private static final String[] PARAM_RL_2 = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:50", "mrg:0,50,0,0"};
    private static final String[] PARAM_RL_3 = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:50", "mrg:0,100,0,0"};

    private static final String[] PARAM_TEXT = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:10,0,80,0", "Rule:LEFT",
            "txtC:GRAY_THREE", "grv:LCV", "pad:5,5,5,5"};
    private static final String[] PARAM_SWITCH = {"prt:RelLay", "layW:wCnt", "layH:wCnt", "parW:60", "parH:40",
            "Rule:RIGHT", "Rule:CEN_VER"};

    private static final String[] PARAM_VIEW_LINE_TOP = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parH:1",
            "backC:GRAY_THREE", "Rule:TOP"};

    private static final String[] PARAM_VIEW_LINE_BOTTOM = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parH:1",
            "backC:GRAY_THREE", "Rule:BOTTOM"};
    private Context context;
    private CircleImageView civAvatar;

    public NotificationView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        this.context = context;

        FrameLayout flContainer = new CustomFrameLayout().onCustom(context, PARAM_CONTAINER);
        this.addView(flContainer);

        //////    RL 1
        RelativeLayout rl1 = new CustomRelativeLayout().onCustom(context, PARAM_RL_1);
        flContainer.addView(rl1);

        View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE_TOP);
        rl1.addView(v1);

        TextView tv1 = new CustomTextView().onCustom(context, PARAM_TEXT, res.getString(R.string.sound_notifications));
        rl1.addView(tv1);

        Switch sw1 = new CustomSwitch().onCustom(context, PARAM_SWITCH);
        rl1.addView(sw1);
        sw1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onSwitchClick(1, sw1.isChecked());
                }
            }
        });

        //////    RL 2
        RelativeLayout rl2 = new CustomRelativeLayout().onCustom(context, PARAM_RL_2);
        flContainer.addView(rl2);

        View v2 = new CustomView().onCustom(context, PARAM_VIEW_LINE_TOP);
        rl2.addView(v2);

        TextView tv2 = new CustomTextView().onCustom(context, PARAM_TEXT, res.getString(R.string.vibration));
        rl2.addView(tv2);

        Switch sw2 = new CustomSwitch().onCustom(context, PARAM_SWITCH);
        rl2.addView(sw2);
        sw2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onSwitchClick(2, sw2.isChecked());
                }
            }
        });

        //////    RL 3
        RelativeLayout rl3 = new CustomRelativeLayout().onCustom(context, PARAM_RL_3);
        flContainer.addView(rl3);

        View v3 = new CustomView().onCustom(context, PARAM_VIEW_LINE_TOP);
        rl3.addView(v3);

        View v4 = new CustomView().onCustom(context, PARAM_VIEW_LINE_BOTTOM);
        rl3.addView(v4);

        TextView tv3 = new CustomTextView().onCustom(context, PARAM_TEXT, res.getString(R.string.popup_notifications));
        rl3.addView(tv3);

        Switch sw3 = new CustomSwitch().onCustom(context, PARAM_SWITCH);
        rl3.addView(sw3);
        sw3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onSwitchClick(3, sw3.isChecked());
                }
            }
        });
    }

    public NotificationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NotificationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NotificationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
