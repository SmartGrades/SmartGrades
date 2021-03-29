package kz.tech.smartgrades.family_room.fragments.app_lock_list;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomCheckBox;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomRecyclerView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;

public class AppLockListView extends FrameLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onBackButtonClick();
        void onCheckBoxClick(String select);


    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_TOOLBAR = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:56", "backC:WHITE"};
    private static final String[] PARAM_TOOLBAR_RL = {"prt:Toolbar", "layW:mPrt", "layH:mPrt"};

    private static final String[] PARAM_BACK = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:56", "parH:56", "pad:5,5,5,5"};
    private static final String[] PARAM_TITLE = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:56,0,56,0", "pad:5,5,5,5",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};


    private static final String[] PARAM_CONTAINER_LL = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parH:40", "mrg:0,61,0,0",
             "orn:hor", "WeiSum:2", "pad:5,5,5,5"};
    private static final String[] PARAM_CHECKBOX = {"prt:LinLay", "layW:0", "layH:mPrt",
           "wei:1", "RTL:RIGHT", "grv:RCV"};

    private static final String[] PARAM_RV = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,101,0,0",
            "hfs:true", "layMan:llm"};



    private Context context;
    private Resources res;
    private TextView tvTitle;
    private RecyclerView recyclerView;
    private CheckBox cbCustomizable, cbStandard;
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
    public AppLockListView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new RelativeLayoutParams().getParams(context, PARAM));
        this.context = context;
        this.res = res;

        Toolbar toolbar = new CustomToolbar().onCustom(context, PARAM_TOOLBAR);
        this.addView(toolbar);
        RelativeLayout rlToolbar = new CustomRelativeLayout().onCustom(context, PARAM_TOOLBAR_RL);
        toolbar.addView(rlToolbar);

        ImageView ivBack = new CustomImageView().onCustom(context, PARAM_BACK, R.mipmap.red_arrow_left);
        rlToolbar.addView(ivBack);
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onBackButtonClick();
                }
            }
        });

         tvTitle = new CustomTextView().onCustom(context, PARAM_TITLE, null);
        rlToolbar.addView(tvTitle);

        LinearLayout llHorizontal = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        this.addView(llHorizontal);

        cbStandard = new CustomCheckBox().onCustom(context, PARAM_CHECKBOX, res.getString(R.string.standard_list_menu));
        llHorizontal.addView(cbStandard);
        cbStandard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onCheckBoxClick("standard");
                }
                if (cbStandard.isChecked()) { cbStandard.setChecked(true); }
                if (cbCustomizable.isChecked()) { cbCustomizable.setChecked(false); }
                cbStandard.setBackgroundColor(ColorsRGB.WHITE);
                cbCustomizable.setBackgroundColor(0);
            }
        });


        cbCustomizable = new CustomCheckBox().onCustom(context, PARAM_CHECKBOX, res.getString(R.string.customizable_menu));
        llHorizontal.addView(cbCustomizable);
        cbCustomizable.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onCheckBoxClick("customizable");
                }
                if (cbCustomizable.isChecked()) { cbCustomizable.setChecked(true); }
                if (cbStandard.isChecked()) { cbStandard.setChecked(false); }
                cbCustomizable.setBackgroundColor(ColorsRGB.WHITE);
                cbStandard.setBackgroundColor(0);
            }
        });


        recyclerView = new CustomRecyclerView().onCustom(context, PARAM_RV);
        this.addView(recyclerView);



    }

    public AppLockListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AppLockListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AppLockListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void setData(String select) {
        String text = res.getString(R.string.blocked_app) + "\n";
        switch (select) {
            case "standardList":
                text += "(" + res.getString(R.string.standard_list_menu) + ")";
                if (!cbStandard.isChecked()) { cbStandard.setChecked(true); }
                if (cbCustomizable.isChecked()) { cbCustomizable.setChecked(false); }
                cbStandard.setBackgroundColor(ColorsRGB.WHITE);
                cbCustomizable.setBackgroundColor(0);
                break;
            case "customizableList":
                text += "(" + res.getString(R.string.customizable_menu) + ")";
                if (!cbCustomizable.isChecked()) { cbCustomizable.setChecked(true); }
                if (cbStandard.isChecked()) { cbStandard.setChecked(false); }
                cbCustomizable.setBackgroundColor(ColorsRGB.WHITE);
                cbStandard.setBackgroundColor(0);
                break;
        }


        tvTitle.setText(text);
    }
}
