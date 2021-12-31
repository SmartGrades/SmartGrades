package kz.tech.smartgrades.parents_module;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.smartgrades.MainView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.DrawerLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomProgressBar;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomViewPager;
import kz.tech.smartgrades.root.var_resources.DimensionDP;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;
import kz.tech.smartgrades.root.var_resources.VarID;
import kz.tech.smartgrades.root.view_pager_child.PagerAdapterPhotoChild;

public class ParentView extends DrawerLayout {
    private MainView.ParentClickListener parentClickListener;
    public interface ParentClickListener {
        void onBackButtonClick(int position);
        void onMenuClick(View view);
        void onViewPagerClick(int num1, int numb2);
        void onChildViewPagerClick(int position, boolean isSelect);
        void onLeftArrowClick();
        void onRightArrowClick();
    }
    public void setParentClickListener(MainView.ParentClickListener parentClickListener) {
        this.parentClickListener = parentClickListener;
    }

    private static final String[] PARAMS = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAMS_CONTAINER = {"prt:FrmLay", "layW:mPrt", "layH:mPrt",
            "backC:GRAY_TWO"};
    private static final String[] PARAM_TOOLBAR = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:50", "backC:WHITE"};
    private static final String[] PARAM_TOOLBAR_RL = {"prt:Toolbar", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_LEFT_BUTTON = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "pad:5,5,5,5"};
    private static final String[] PARAM_LOGO = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:50,0,50,0",
            "parH:50", "HIDE:GONE"};
    private static final String[] PARAM_TITLE = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:50,0,50,0", "pad:5,5,5,5",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_SEARCH = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:50", "mrg:0,0,50,0",
            "parH:50", "pad:5,5,5,5", "BordC:GRAY_THREE", "BordW:2", "img:avatar", "Rule:RIGHT"};
    private static final String[] PARAM_AVATAR = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "pad:5,5,5,5",
            "BordC:GRAY_THREE", "BordW:2", "img:avatar", "Rule:RIGHT"};
    private static final String[] PARAM_LOAD_ANIMATION = {"prt:FrmLay", "layW:wCnt", "layH:wCnt",
            "parW:50", "parH:50", "GRAV:CORE"};
    private static final String[] PARAM_VIEW_PAGER = {"prt:FrmLay", "layW:mPrt", "layH:mPrt"};

    private static final String[] PARAM_RL_VIEW_PAGER = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:75", "mrg:0,5,0,0",
            "backC:WHITE"};
    private static final String[] PARAM_VIEW_PAGER_LEFT = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:10,0,0,0",
            "parW:40", "parH:40", "pad:5,5,5,5", "Rule:LEFT", "Rule:CEN_VER", "HIDE:GONE"};
    private static final String[] PARAM_VIEW_PAGER_RIGHT = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:0,0,10,0",
            "parW:40", "parH:40", "pad:5,5,5,5", "Rule:RIGHT", "Rule:CEN_VER", "HIDE:GONE"};
    private static final String[] PARAM_CHILD_VIEW_PAGER = {"prt:RelLay", "layW:mPrt", "layH:wCnt", "parH:75"};




    private FrameLayout flContainer;
    public DrawerLayout dlMain;
    private ProgressBar progressBar;
    public FrameLayout flMain;
    private FrameLayout.LayoutParams params;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private ImageView ivLeftButton, ivLogo;
    private TextView tvTitle;

    private CircleImageView civAvatar;
    private int leftButtonSelect = 0;
    private ViewPager viewPager, childViewPager;
    public ViewPager getViewPager() {
        return viewPager;
    }
    public ViewPager getChildViewPager() {
        return childViewPager;
    }
    private PagerAdapterPhotoChild adapter;
    public PagerAdapterPhotoChild getAdapterPhotoChild() {
        return adapter;
    }
    private boolean isSelect = false;
    private boolean isMenu1 = false;
    private boolean isMenu2 = false;
    private RelativeLayout rlViewPager;
    public RelativeLayout getRlViewPager() {
        return rlViewPager;
    }
    private ImageView ivLeftArrow, ivRightArrow;

    ParentActivity activity;
    public ParentView(ParentActivity activity) {
        super(activity);
       this.activity = activity;
        dlMain = this;
        this.setLayoutParams(new DrawerLayoutParams().getParams(activity, PARAMS));
        this.setFitsSystemWindows(true);

        flContainer = new CustomFrameLayout().onCustom(activity, PARAMS_CONTAINER);
        this.addView(flContainer);

        //          MAIN CONTAINER          //
        flMain = new FrameLayout(activity);
        params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL;
        params.setMargins(0, DimensionDP.sizeDP(50, activity), 0, DimensionDP.sizeDP(50, activity));
        flMain.setLayoutParams(params);
        flContainer.addView(flMain);
        flMain.setId(VarID.ID_FL_MAIN);

        progressBar = new CustomProgressBar().onCustom(activity, PARAM_LOAD_ANIMATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.setElevation(DimensionDP.sizeDP(10,activity));
        }
        flMain.addView(progressBar);
        progressBar.setVisibility(GONE);

        onParentOn();


    }

    public ParentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ParentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onNavInit() {
        ParentsNavigationView navigationView = new ParentsNavigationView(activity, activity.language.getLanguage());
        this.addView(navigationView);
    }


    public void onLoadAnimation(boolean isLoad) {
        if (isLoad) {
            // progressBar.setVisibility(VISIBLE);
            progressBar.setVisibility(GONE);
        } else {
            progressBar.setVisibility(GONE);
        }
    }

    /////////////////////        PARENT        ///////////////////
    public void onParentOn() {
        ///////////              TOOLBAR               //////////////
        initToolbar();

        ///////////         BOTTOM NAVIGATION VIEW          //////////////
        initBottomNavigationView();

        //////////         FRAGMENT VIEW PAGER        /////////////
        initFragmentViewPager();

        //////////        CHILD VIEW PAGER          /////////////
        initChildViewPager();

    }


    /////////////////////        TOOLBAR        ///////////////////
    private void initToolbar() {
        toolbar = new CustomToolbar().onCustom(activity, PARAM_TOOLBAR);
        flContainer.addView(toolbar);
        RelativeLayout rlToolbar = new CustomRelativeLayout().onCustom(activity, PARAM_TOOLBAR_RL);
        toolbar.addView(rlToolbar);
        ivLeftButton = new CustomImageView().onCustom(activity, PARAM_LEFT_BUTTON, 0);
        rlToolbar.addView(ivLeftButton);
        ivLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (parentClickListener != null) {
                    parentClickListener.onBackButtonClick(leftButtonSelect);
                }
            }
        });

        ivLogo = new CustomImageView().onCustom(activity, PARAM_LOGO, R.drawable.logo);
        rlToolbar.addView(ivLogo);

        tvTitle = new CustomTextView().onCustom(activity, PARAM_TITLE, null);
        rlToolbar.addView(tvTitle);

        civAvatar = new CustomCircleImageView().onCustom(activity, PARAM_AVATAR);
        rlToolbar.addView(civAvatar);
        civAvatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (parentClickListener != null) {
                    parentClickListener.onMenuClick(view);
                }
            }
        });
    }
    public void onToolbarSelect(int select, String title) {
        ivLeftButton.setImageResource(0);
        ivLogo.setVisibility(GONE);
        tvTitle.setText("");
        leftButtonSelect = 0;
        if (select == 1) {
            ivLeftButton.setImageResource(R.mipmap.nav_menu);
            ivLogo.setVisibility(VISIBLE);
            leftButtonSelect = 1;
        } else if (select > 1) {
            ivLeftButton.setImageResource(R.mipmap.red_arrow_left);
            tvTitle.setText(title);
            leftButtonSelect = select;
        }
    }
    public void setAvatar(String image) {
        Picasso.get().load(image).placeholder(R.drawable.img_default_avatar).fit().centerInside().error(R.mipmap.avatar).placeholder(R.mipmap.avatar).into(civAvatar);
    }
    public void changeLocaleText(Resources res) {
        tvTitle.setText(res.getString(R.string.change_language));
    }


    ////////////////         BOTTOM NAVIGATION VIEW          //////////////////////
    private void initBottomNavigationView() {
        bottomNavigationView = new BottomNavigationView(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        params.height = DimensionDP.sizeDP(50, activity);
        bottomNavigationView.setLayoutParams(params);
        bottomNavigationView.setBackgroundColor(ColorsRGB.WHITE);
        bottomNavigationView.setItemIconTintList(null);

        int[][] states = new int[][] { new int[] { android.R.attr.state_activated }, new int[] { -android.R.attr.state_activated } };
        int[] colors = {ColorsRGB.GRAY_THREE, ColorsRGB.BLACK};
        ColorStateList colorStateList = new ColorStateList(states, colors);

        bottomNavigationView.setItemTextColor(colorStateList);
        onMenuAdd(1);

        onBottomNavigationViewSelect(1);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case 1://  Cabinet
                        onBottomNavigationViewSelect(1);
                        viewPager.setCurrentItem(0);
                        isShownViewPagerChild(false);
                        break;
                    case 2://  Coins
                        onBottomNavigationViewSelect(2);
                        viewPager.setCurrentItem(1);
                        break;
                    case 3://  Contracts
                        onBottomNavigationViewSelect(3);
                        viewPager.setCurrentItem(2);
                        break;
                    case 4://  Devices
                        onBottomNavigationViewSelect(4);
                        viewPager.setCurrentItem(3);
                        isShownViewPagerChild(false);
                        break;
                    case 5://  RIGHT ARROW
                        viewPager.setCurrentItem(4);
                        onMenuRemove(1);//  REMOVE MENU 1
                        onMenuAdd(2);//  ADD MENU 2
                        onBottomNavigationViewSelect(6);
                        break;
                    case 6://
                        viewPager.setCurrentItem(3);
                        onMenuRemove(2);//  REMOVE MENU 2
                        onMenuAdd(1);//  ADD MENU 1
                        onBottomNavigationViewSelect(4);
                        break;
                    case 7:
                        viewPager.setCurrentItem(4);
                        onBottomNavigationViewSelect(6);
                        break;
                    case 8:
                        viewPager.setCurrentItem(5);
                        onBottomNavigationViewSelect(7);
                        break;
                }
                return false;
            }
        });
        flContainer.addView(bottomNavigationView);
    }
    public void onBottomNavigationViewSelect(int select) {
        if (isMenu1) {
            bottomNavigationView.getMenu().getItem(0).setIcon(R.mipmap.cabinet_off);//  OFF
            bottomNavigationView.getMenu().getItem(1).setIcon(R.mipmap.coins_black);//  OFF
            bottomNavigationView.getMenu().getItem(2).setIcon(R.mipmap.contracts_off);//  OFF
            bottomNavigationView.getMenu().getItem(3).setIcon(R.mipmap.devices_black);//  OFF
            switch (select) {
                case 1:
                    bottomNavigationView.getMenu().getItem(0).setIcon(R.mipmap.cabinet).setChecked(true);
                    break;
                case 2:
                    bottomNavigationView.getMenu().getItem(1).setIcon(R.mipmap.coins_gold).setChecked(true);
                    break;
                case 3:
                    bottomNavigationView.getMenu().getItem(2).setIcon(R.mipmap.done).setChecked(true);
                    break;
                case 4:
                    bottomNavigationView.getMenu().getItem(3).setIcon(R.mipmap.devices).setChecked(true);
                    break;
            }
            return;
        }
        if (isMenu2) {
            bottomNavigationView.getMenu().getItem(1).setIcon(R.mipmap.auto_charge_black);//  OFF
            bottomNavigationView.getMenu().getItem(2).setIcon(R.mipmap.children_time_black);//  OFF
            switch (select) {
                case 6:
                    bottomNavigationView.getMenu().getItem(1).setIcon(R.mipmap.auto_charge).setChecked(true);
                    break;
                case 7:
                    bottomNavigationView.getMenu().getItem(2).setIcon(R.mipmap.timer).setChecked(true);
                    break;
            }
        }
    }
    private void onMenuRemove(int numb) {
        switch (numb) {
            case 1:
                bottomNavigationView.getMenu().clear();
                isMenu1 = false;
                break;
            case 2:
                bottomNavigationView.getMenu().clear();
                isMenu2 = false;
                break;
        }
    }
    private void onMenuAdd(int numb) {
        //  isMenu1 = false;
        //   isMenu2 = false;
        switch (numb) {
            case 1:
                Menu menu1 = bottomNavigationView.getMenu();
                menu1.add(Menu.NONE, 1, Menu.NONE, getResources().getString(R.string.cabinet)).setIcon(R.mipmap.cabinet_off);
                menu1.add(Menu.NONE, 2, Menu.NONE, getResources().getString(R.string.obols)).setIcon(R.mipmap.coins_black);
                menu1.add(Menu.NONE, 3, Menu.NONE, getResources().getString(R.string.contracts)).setIcon(R.mipmap.contracts_off);
                menu1.add(Menu.NONE, 4, Menu.NONE, getResources().getString(R.string.devices)).setIcon(R.mipmap.devices_black);
                menu1.add(Menu.NONE, 5, Menu.NONE, null).setIcon(R.mipmap.red_arrow_right);
                isMenu1 = true;
                break;
            case 2:
                Menu menu2 = bottomNavigationView.getMenu();
                menu2.add(Menu.NONE, 6, Menu.NONE, null).setIcon(R.mipmap.red_arrow_left);
                menu2.add(Menu.NONE, 7, Menu.NONE, getResources().getString(R.string.auto_charge_text)).setIcon(R.mipmap.auto_charge_black);
                menu2.add(Menu.NONE, 8, Menu.NONE, getResources().getString(R.string.children_time_text)).setIcon(R.mipmap.children_time_black);
                isMenu2 = true;
                break;
        }
    }


    ///////////           FRAGMENT VIEW PAGER             //////////////////
    private void initFragmentViewPager() {

        viewPager = new CustomViewPager().onCustom(activity, PARAM_VIEW_PAGER);
        flMain.addView(viewPager);
        viewPager.setId((int)179356);

        // viewPager.setSaveFromParentEnabled(false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                isShownViewPagerChild(true);
                switch (position) {
                    case 0://  Cabinet
                        if (isMenu1) { onBottomNavigationViewSelect(1); }
                        isShownViewPagerChild(false);
                        break;
                    case 1://  Coins
                        if (isMenu1) { onBottomNavigationViewSelect(2); }
                        break;
                    case 2://  Contracts
                        if (isMenu1) { onBottomNavigationViewSelect(3); }
                        break;
                    case 3://  Devices
                        if (isMenu1) { onBottomNavigationViewSelect(4); }
                        if (isMenu2) {
                            onMenuRemove(2);//  REMOVE MENU 1
                            onMenuAdd(1);//  ADD MENU 2
                            if (isMenu1) { onBottomNavigationViewSelect(4); }
                        }
                        isShownViewPagerChild(false);
                        break;
                    case 4:
                        //     if (isMenu1) { onBottomNavigationViewSelect(4); }
                        onMenuRemove(1);//  REMOVE MENU 1
                        onMenuAdd(2);//  ADD MENU 2
                        if (isMenu2) { onBottomNavigationViewSelect(6); }
                        break;
                    case 5:
                        //   if (isMenu1) { onBottomNavigationViewSelect(4); }
                        if (isMenu2) { onBottomNavigationViewSelect(7); }
                        break;
                }
                if (parentClickListener != null) {
                    parentClickListener.onViewPagerClick(position, childViewPager.getCurrentItem());
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void onDefaultPageSelect(int position) {
        viewPager.setCurrentItem(position);
    }
    public void onLeftArrow(String select) {
        switch (select) {
            case "VISIBLE":
                ivLeftArrow.setVisibility(VISIBLE);
                break;
            case "GONE":
                ivLeftArrow.setVisibility(GONE);
                break;
        }
    }
    public void onRightArrow(String select) {
        switch (select) {
            case "VISIBLE":
                ivRightArrow.setVisibility(VISIBLE);
                break;
            case "GONE":
                ivRightArrow.setVisibility(GONE);
                break;
        }
    }


    //////////           CHILD VIEW PAGER             //////////////////
    private void initChildViewPager() {
        rlViewPager = new CustomRelativeLayout().onCustom(activity, PARAM_RL_VIEW_PAGER);
        flMain.addView(rlViewPager);

        childViewPager = new CustomViewPager().onCustom(activity, PARAM_CHILD_VIEW_PAGER);
        rlViewPager.addView(childViewPager);
        adapter = new PagerAdapterPhotoChild(activity);

        childViewPager.setAdapter(adapter);
        childViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (!isSelect) {
                    isSelect = true;
                    if (parentClickListener != null) {
                        parentClickListener.onChildViewPagerClick(position, isSelect);
                    }
                }
            }
            @Override
            public void onPageSelected(int position) {
                if (isSelect) {
                    if (parentClickListener != null) {
                        parentClickListener.onChildViewPagerClick(position, isSelect);

                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        ivLeftArrow = new CustomImageView().onCustom(activity, PARAM_VIEW_PAGER_LEFT, R.mipmap.red_left);
        rlViewPager.addView(ivLeftArrow);
        ivLeftArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (parentClickListener != null) {
                    parentClickListener.onLeftArrowClick();
                }
            }
        });
        ivLeftArrow.setVisibility(VISIBLE);

        ivRightArrow = new CustomImageView().onCustom(activity, PARAM_VIEW_PAGER_RIGHT, R.mipmap.red_right);
        rlViewPager.addView(ivRightArrow);
        ivRightArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (parentClickListener != null) {
                    parentClickListener.onRightArrowClick();
                }
            }
        });




    }
    private void isShownViewPagerChild(boolean isShowViewPager) {
        if (rlViewPager != null) {
            if (isShowViewPager) {
                rlViewPager.setVisibility(VISIBLE);
            } else {
                rlViewPager.setVisibility(GONE);
            }

        }

    }


}
