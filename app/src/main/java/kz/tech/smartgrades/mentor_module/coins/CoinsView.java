package kz.tech.smartgrades.mentor_module.coins;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomHorizontalScrollView;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomScrollView;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomRecyclerView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;

public class CoinsView extends FrameLayout {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onSelectViewClick(int n);
        void onAddGroupsClick(String name);
        void onRemoveGroupClick(String idGroup);
        void onPushClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_FL = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,5,0,5"};
    private static final String[] PARAM_CONTAINER_HSV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "GRAV:TOP",
            "parH:50", "backC:WHITE"};
    private static final String[] PARAM_CONTAINER_LL_HOR = {"prt:HScrLay", "layW:mPrt", "layH:mPrt", "orn:hor"};
    private static final String[] PARAM_MOVE_ADD = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "parW:40", "parH:40", "GRAV:VER"};
    private static final String[] PARAM_MAIN_GROUP_FL = {"prt:LinLay", "layW:wCnt", "layH:wCnt",
            "parW:120", "parH:40", "GRAV:VER", "backRR:WHITE"};
    private static final String[] PARAM_MAIN_GROUP_TV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt",
            "GRAV:TOP", "parH:35", "grv:CHCV", "txtC:GRAY_THREE", "txtS:14"};
    private static final String[] PARAM_MAIN_GROUP_V = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:5,0,5,0",
            "GRAV:BOTTOM", "parH:5", "backC:RED_ONE", "VIS:GONE"};

    private static final String[] PARAM_VIEW_SPACE = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "parW:1",
            "parH:40", "GRAV:VER", "backC:GRAY_THREE"};

    private static final String[] PARAM_CENTER_FL = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,55,0,55", "backC:WHITE"};
    private static final String[] PARAM_RV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "hfs:true", "layMan:llm"};

    private static final String[] PARAM_BOTTOM_PUSH_FL = {"prt:FrmLay", "layW:mPrt", "layH:wCnt",
            "GRAV:BOTTOM", "parH:50"};//  , "backC:RED_ONE", "Alpha:0.5"
    private static final String[] PARAM_BOTTOM_PUSH_IV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "GRAV:LEFT_VER", "mrg:10,0,0,0",
            "parW:38", "parH:38", "pad:5,5,5,5"};
    private static final String[] PARAM_BOTTOM_PUSH_TV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "GRAV:CORE", "mrg:38,0,0,0",
            "parH:48", "grv:LCV", "txtC:BLACK"};

    private static final String[] PARAM_BOTTOM_FL = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:0,0,0,50",
            "GRAV:BOTTOM", "parH:50", "backC:WHITE"};
    private static final String[] PARAM_BOTTOM_VIEW_1 = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "GRAV:TOP", "parH:1", "backC:GRAY_THREE"};
    private static final String[] PARAM_BOTTOM_IV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "GRAV:LEFT_VER", "mrg:10,0,0,0",
            "parW:38", "parH:38", "pad:5,5,5,5"};
    private static final String[] PARAM_BOTTOM_TV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "GRAV:CORE", "mrg:38,0,0,0",
            "parH:48", "grv:LCV", "txtC:BLACK"};
    private static final String[] PARAM_BOTTOM_VIEW_2 = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "GRAV:BOTTOM", "parH:1", "backC:GRAY_THREE"};

    private static final String[] PARAM_ADD_GROUPS_V = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:0,0,10,0",
            "GRAV:TOP", "parH:1", "backC:GRAY_THREE"};
    private static final String[] PARAM_ADD_GROUPS_FL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:30,0,0,50", "parH:50"};
    private static final String[] PARAM_ADD_GROUPS_IV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "GRAV:LEFT_VER",
            "parW:38", "parH:38", "pad:5,5,5,5"};
    private static final String[] PARAM_ADD_GROUPS_TV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "GRAV:CORE", "mrg:38,0,0,0",
            "parH:48", "grv:LCV", "txtC:BLACK"};

    private static final String[] PARAM_CONTAINER_SV = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "GRAV:TOP",
           "backC:WHITE", "FillView:TRUE"};
    private static final String[] PARAM_CONTAINER_LL_VER = {"prt:ScrLay", "layW:mPrt", "layH:mPrt", "orn:ver"};

    private static final String[] PARAM_ADD_GROUP_FL = {"prt:FrmLay", "layW:mPrt", "layH:wCnt",
            "GRAV:BOTTOM", "parH:50"};
    private static final String[] PARAM_ADD_GROUP_TV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "GRAV:LEFT_VER", "mrg:30,0,48,0",
            "parH:48", "grv:LCV", "txtC:BLACK"};
    private static final String[] PARAM_ADD_GROUP_IV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "GRAV:RIGHT_VER", "mrg:0,0,10,0",
            "parW:38", "parH:38", "pad:5,5,5,5"};

    private Context context;
    private Resources res;
    private FrameLayout flMain, flAddGroups;
    private ImageView ivPushBackground;
    private LinearLayout llHor, llVer;
    private List<View> vRedLine;
    private RecyclerView rvCoinsChild;
    public RecyclerView getCoinsChild() {
        return rvCoinsChild;
    }
    public CoinsView(Context context, Resources res) {
        super(context);
        this.context = context;
        this.res = res;
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));

        flMain = new CustomFrameLayout().onCustom(context, PARAM_FL);
        this.addView(flMain);

        flAddGroups = new CustomFrameLayout().onCustom(context, PARAM_FL);
        this.addView(flAddGroups);
        flAddGroups.setVisibility(GONE);


        onMainFL();

        onAddGroupsFL();
    }
    public CoinsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public CoinsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CoinsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void onMainFL() {
        //////////          TOP         ///////////////
        HorizontalScrollView hsv = new CustomHorizontalScrollView().onCustom(context, PARAM_CONTAINER_HSV);
        flMain.addView(hsv);

        llHor = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL_HOR);
        hsv.addView(llHor);

        ImageView ivMoveAdd = new CustomImageView().onCustom(context, PARAM_MOVE_ADD, R.mipmap.move_red);
        llHor.addView(ivMoveAdd);
        ivMoveAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onSelectViewClick(2);
                }

            }
        });

        View v1 = new CustomView().onCustom(context, PARAM_VIEW_SPACE);
        llHor.addView(v1);

        //////////               VIEW ARRAY LIST              ///////////////
        vRedLine = new ArrayList<>();


        //////////          CENTER         ///////////////
        FrameLayout flCenter = new CustomFrameLayout().onCustom(context, PARAM_CENTER_FL);
        flMain.addView(flCenter);

        rvCoinsChild = new CustomRecyclerView().onCustom(context, PARAM_RV);
        flCenter.addView(rvCoinsChild);


        //////////          BOTTOM         ///////////////
        FrameLayout flBottom = new CustomFrameLayout().onCustom(context, PARAM_BOTTOM_FL);
        flMain.addView(flBottom);

        View vBottom1 = new CustomView().onCustom(context, PARAM_BOTTOM_VIEW_1);
        flBottom.addView(vBottom1);

        ImageView ivBottom = new CustomImageView().onCustom(context, PARAM_BOTTOM_IV, R.mipmap.plus_oval);
        flBottom.addView(ivBottom);

        TextView tvBottom = new CustomTextView().onCustom(context, PARAM_BOTTOM_TV, res.getString(R.string.add_schoolboy));
        flBottom.addView(tvBottom);

        View vBottom2 = new CustomView().onCustom(context, PARAM_BOTTOM_VIEW_2);
        flBottom.addView(vBottom2);


        FrameLayout flPush = new CustomFrameLayout().onCustom(context, PARAM_BOTTOM_PUSH_FL);
        flMain.addView(flPush);
        flPush.setBackgroundColor(Color.rgb(229, 122, 137));


        ivPushBackground = new CustomImageView().onCustom(context, PARAM_BOTTOM_PUSH_IV, R.mipmap.push_red);
        flPush.addView(ivPushBackground);

        TextView tvPush = new CustomTextView().onCustom(context, PARAM_BOTTOM_PUSH_TV, res.getString(R.string.push_text));
        flPush.addView(tvPush);

        flPush.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onPushClick();
                }
            }
        });

      //  ivPushBackground = new CustomImageView().onCustom(context, PARAM_PUSH_BACKGROUND, 0);
      //  flPush.addView(ivPushBackground);
    }


    private void onAddGroupsFL() {
        ScrollView sv = new CustomScrollView().onCustom(context, PARAM_CONTAINER_SV);
        flAddGroups.addView(sv);

        llVer = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL_VER);
        sv.addView(llVer);


        FrameLayout flAddGroups = new CustomFrameLayout().onCustom(context, PARAM_ADD_GROUPS_FL);
        llVer.addView(flAddGroups);

        View vAddGroups1 = new CustomView().onCustom(context, PARAM_ADD_GROUPS_V);
        flAddGroups.addView(vAddGroups1);

        ImageView ivAddGroups = new CustomImageView().onCustom(context, PARAM_ADD_GROUPS_IV, R.mipmap.plus_oval);
        flAddGroups.addView(ivAddGroups);

        TextView tvAddGroups = new CustomTextView().onCustom(context, PARAM_ADD_GROUPS_TV, res.getString(R.string.add_group));
        flAddGroups.addView(tvAddGroups);



        /*flAddGroups.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogAddGroups dialog = new AlertDialogAddGroups(context, res);
                dialog.showAlert();
                dialog.setOnItemClickListener(new AlertDialogAddGroups.OnItemClickListener() {
                    @Override
                    public void onItemClick(String name) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onAddGroupsClick(name);
                        }
                    }
                });
            }
        });*/
    }
    public void onChildrenGroups(String name, String idGroup, int position) {

        ///////////       HORIZONTAL       //////////////
        FrameLayout flTab = new CustomFrameLayout().onCustom(context, PARAM_MAIN_GROUP_FL);
        llHor.addView(flTab);

        TextView tv = new CustomTextView().onCustom(context, PARAM_MAIN_GROUP_TV, name);
        flTab.addView(tv);
        flTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

        View vR = new CustomView().onCustom(context, PARAM_MAIN_GROUP_V);
        flTab.addView(vR);
        vRedLine.add(vR);


        View v1 = new CustomView().onCustom(context, PARAM_VIEW_SPACE);
        llHor.addView(v1);


        ///////////       VERTICAL       //////////////
        FrameLayout flAddGroup = new CustomFrameLayout().onCustom(context, PARAM_ADD_GROUP_FL);
        llVer.addView(flAddGroup, 0);

        TextView tvAddGroup = new CustomTextView().onCustom(context, PARAM_ADD_GROUP_TV, name);
        flAddGroup.addView(tvAddGroup);

        ImageView ivAddGroup = new CustomImageView().onCustom(context, PARAM_ADD_GROUP_IV, R.mipmap.black_close);
        flAddGroup.addView(ivAddGroup);
        ivAddGroup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                flTab.removeAllViewsInLayout();
                llHor.removeView(flTab);
                flAddGroup.removeAllViewsInLayout();
                llVer.removeView(flAddGroup);
                if (onItemClickListener != null) {
                    onItemClickListener.onRemoveGroupClick(idGroup);
                }
            }
        });

    }


    public void onTabSelect(int n) {
        for (int i = 0; i < vRedLine.size(); i++) {
            vRedLine.get(i).setVisibility(GONE);
        }
        vRedLine.get(n).setVisibility(VISIBLE);
    }

    public void onSelectFL(int n) {
        flMain.setVisibility(GONE);
        flAddGroups.setVisibility(GONE);
        switch (n) {
            case 1: flMain.setVisibility(VISIBLE); break;
            case 2: flAddGroups.setVisibility(VISIBLE); break;
        }
    }
    public void onEnablePushAnimation() {
        animateImageView(ivPushBackground);
    }
    private void animateImageView(final ImageView v) {
        final int orange = getResources().getColor(android.R.color.holo_orange_dark);
        // final int orange = kz.tech.esparta.root.var_resources.ColorsRGB.RED_ONE;

        final ValueAnimator colorAnim = ObjectAnimator.ofFloat(0f, 1f);
        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float mul = (Float) animation.getAnimatedValue();
                int alphaOrange = adjustAlpha(orange, mul);
                v.setColorFilter(alphaOrange, PorterDuff.Mode.SRC_ATOP);
                if (mul == 0.0) {
                    v.setColorFilter(null);
                }
            }
        });

        colorAnim.setDuration(1500);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.setRepeatCount(-1);
        colorAnim.start();

    }
    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}
