package kz.tech.smartgrades.parents_module.family_group;

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
import androidx.recyclerview.widget.RecyclerView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomRecyclerView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;

public class FamilyGroupView extends ScrollView {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onAddFamilyMembersClick();
        void onAddMentorsClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_CONTAINER = {"prt:ScrLay", "layW:mPrt", "layH:mPrt", "orn:ver", "mrg:0,5,0,0"};
    private static final String[] PARAM_TITLE = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:30",
            "grv:CHCV", "txtC:BLACK"};
    private static final String[] PARAM_RV = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "hfs:true", "layMan:llm", "mrg:0,0,0,5"};
    private static final String[] PARAM_ADD_FL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50", "backC:WHITE", "mrg:0,0,0,5"};
    private static final String[] PARAM_ADD_VIEW_1 = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "GRAV:TOP", "parH:1", "backC:GRAY_THREE"};
    private static final String[] PARAM_ADD_IV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "GRAV:LEFT_VER", "mrg:10,0,0,0",
            "parW:38", "parH:38", "pad:5,5,5,5"};
    private static final String[] PARAM_ADD_TV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "GRAV:CORE", "mrg:0,0,0,0",
            "parH:48", "grv:CHCV", "txtC:GRAY_THREE"};
    private static final String[] PARAM_ADD_VIEW_2 = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "GRAV:BOTTOM", "parH:1", "backC:GRAY_THREE"};
    private static final String[] PARAM_MENTORS_EMPTY = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:0,5,0,5",
            "grv:CHCV", "txtC:GRAY_THREE", "backC:WHITE", "txtS:12"};
    private Context context;
    private Resources res;
    private TextView tvMentorsEmpty;
    private RecyclerView rvFamilyMembers, rvMentors;
    public RecyclerView getFamilyMembers() {
        return rvFamilyMembers;
    }
    public RecyclerView getMentors() {
        return rvMentors;
    }
    public FamilyGroupView(Context context, Resources res) {
        super(context);
        this.context = context;
        this.res = res;
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
     //   this.setFillViewport(true);

        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER);
        this.addView(linearLayout);

        //////////////            TITLE FAMILY MEMBERS           //////////////////
        TextView tvTitleFamilyMembers = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.family_members));
        linearLayout.addView(tvTitleFamilyMembers);

        //////////////           RECYCLER VIEW FAMILY MEMBERS             ////////////////////
        rvFamilyMembers = new CustomRecyclerView().onCustom(context, PARAM_RV);
        linearLayout.addView(rvFamilyMembers);

        ///////////            ADD FAMILY MEMBERS            //////////////
        FrameLayout flFamilyMembers = new CustomFrameLayout().onCustom(context, PARAM_ADD_FL);
        linearLayout.addView(flFamilyMembers);
        flFamilyMembers.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onAddFamilyMembersClick();
                }
            }
        });

        View v1 = new CustomView().onCustom(context, PARAM_ADD_VIEW_1);
        flFamilyMembers.addView(v1);

        ImageView ivFamilyMembers = new CustomImageView().onCustom(context, PARAM_ADD_IV, R.mipmap.plus_oval);
        flFamilyMembers.addView(ivFamilyMembers);

        TextView tvFamilyMembers = new CustomTextView().onCustom(context, PARAM_ADD_TV, res.getString(R.string.add_family_member));
        flFamilyMembers.addView(tvFamilyMembers);

        View v2 = new CustomView().onCustom(context, PARAM_ADD_VIEW_2);
        flFamilyMembers.addView(v2);

        //////////////            TITLE MENTORS           //////////////////
        TextView tvTitleMentors = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.mentors));
        linearLayout.addView(tvTitleMentors);


        //////////////           RECYCLER VIEW MENTORS             ////////////////////
        rvMentors = new CustomRecyclerView().onCustom(context, PARAM_RV);
        linearLayout.addView(rvMentors);

        //////////////           MENTORS EMPTY            ////////////////////
        tvMentorsEmpty = new CustomTextView().onCustom(context, PARAM_MENTORS_EMPTY, res.getString(R.string.mentors_empty));
        linearLayout.addView(tvMentorsEmpty);


        ///////////            ADD MENTORS            //////////////
        FrameLayout flMentors = new CustomFrameLayout().onCustom(context, PARAM_ADD_FL);
        linearLayout.addView(flMentors);
        flMentors.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onAddMentorsClick();
                }
            }
        });

        View v3 = new CustomView().onCustom(context, PARAM_ADD_VIEW_1);
        flMentors.addView(v3);

        ImageView ivMentors = new CustomImageView().onCustom(context, PARAM_ADD_IV, R.mipmap.plus_oval);
        flMentors.addView(ivMentors);

        TextView tvMentors = new CustomTextView().onCustom(context, PARAM_ADD_TV, res.getString(R.string.add_mentor));
        flMentors.addView(tvMentors);

        View v4 = new CustomView().onCustom(context, PARAM_ADD_VIEW_2);
        flMentors.addView(v4);
    }

    public FamilyGroupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FamilyGroupView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FamilyGroupView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
