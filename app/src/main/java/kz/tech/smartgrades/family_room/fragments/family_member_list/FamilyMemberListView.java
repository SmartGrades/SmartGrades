package kz.tech.smartgrades.family_room.fragments.family_member_list;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomRecyclerView;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class FamilyMemberListView extends LinearLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onBackButtonClick();
        void onMoveButtonCLick(View view);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};

    private static final String[] PARAM_TOOLBAR = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:56", "backC:WHITE"};
    private static final String[] PARAM_TOOLBAR_RL = {"prt:Toolbar", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_BACK = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:56", "parH:56", "pad:5,5,5,5"};

    private static final String[] PARAM_TITLE = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:56,0,56,0", "pad:5,5,5,5",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};

    private static final String[] PARAM_MOVE = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:56", "parH:56",
            "pad:5,5,5,5", "Rule:RIGHT"};

    private static final String[] PARAM_RV = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "hfs:true", "layMan:llm"};
    public RecyclerView recyclerView;
    public FamilyMemberListView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        this.setOrientation(VERTICAL);


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
        TextView tvTitle = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.family_room));
        rlToolbar.addView(tvTitle);

        ImageView ivMove = new CustomImageView().onCustom(context, PARAM_MOVE, R.mipmap.move_red);
        rlToolbar.addView(ivMove);
        ivMove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onMoveButtonCLick(view);
                }
            }
        });



        recyclerView = new CustomRecyclerView().onCustom(context, PARAM_RV);
        this.addView(recyclerView);



    }

    public FamilyMemberListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FamilyMemberListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
