package kz.tech.smartgrades.mentor_module;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomViewPager;
import kz.tech.smartgrades.root.var_resources.DimensionDP;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;
import kz.tech.smartgrades.root.view_pager_child.PagerAdapterPhotoChild;

public class MentorView extends FrameLayout {
    private MentorClickListener mentorClickListener;
    public interface MentorClickListener {
        void onBackButtonClick(int position);
        void onMenuClick(View view);
    }
    public void setMentorClickListener(MentorClickListener mentorClickListener) {
        this.mentorClickListener = mentorClickListener;
    }

    private static final String[] PARAMS = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAMS_CONTAINER = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,50,0,50",
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
    private static final String[] PARAM_VIEW_PAGER = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,50,0,50"};


    private Context c;
    public FrameLayout flContainer;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private ImageView ivLeftButton, ivLogo;
    private ViewPager viewPager, childViewPager;
    private TextView tvTitle;
    private CircleImageView civAvatar;
    public ViewPager getViewPager() {
        return viewPager;
    }
    private RelativeLayout rlViewPager;
    public RelativeLayout getRlViewPager() {
        return rlViewPager;
    }
    private int leftButtonSelect = 0;
    private boolean isMenu1 = false;
    private PagerAdapterPhotoChild adapter;
    public PagerAdapterPhotoChild getAdapterPhotoChild() {
        return adapter;
    }

    public MentorView(@NonNull Context c) {
        super(c);
        this.c = c;
        this.setLayoutParams(new FrameLayoutParams().getParams(c, PARAMS));


     //   flContainer = new CustomFrameLayout().onCustom(c, PARAMS_CONTAINER);
     //   this.addView(flContainer);
     //   flContainer.setId(L.layout_mentor);

        onMentorOn();
    }

    public MentorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MentorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MentorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }





    /////////////////////        MENTOR        ///////////////////
    public void onMentorOn() {
        ///////////              TOOLBAR               //////////////
        initToolbarMentor();

        ///////////         BOTTOM NAVIGATION VIEW          //////////////
        initBottomNavigationViewMentor();

        //////////         FRAGMENT VIEW PAGER        /////////////
        initFragmentViewPagerMentor();
    }



    /////////////////////        TOOLBAR MENTOR       ///////////////////
    private void initToolbarMentor() {
        toolbar = new CustomToolbar().onCustom(c, PARAM_TOOLBAR);
        this.addView(toolbar);

        RelativeLayout rlToolbar = new CustomRelativeLayout().onCustom(c, PARAM_TOOLBAR_RL);
        toolbar.addView(rlToolbar);

        ivLeftButton = new CustomImageView().onCustom(c, PARAM_LEFT_BUTTON, R.mipmap.nav_menu);
        rlToolbar.addView(ivLeftButton);
        ivLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mentorClickListener != null) {
                    mentorClickListener.onBackButtonClick(leftButtonSelect);
                }
            }
        });

        ivLogo = new CustomImageView().onCustom(c, PARAM_LOGO, R.drawable.logo);
        rlToolbar.addView(ivLogo);

        tvTitle = new CustomTextView().onCustom(c, PARAM_TITLE, null);
        rlToolbar.addView(tvTitle);

        ImageView ivSearch = new CustomImageView().onCustom(c, PARAM_SEARCH, R.mipmap.gray_search);
        rlToolbar.addView(ivSearch);

        civAvatar = new CustomCircleImageView().onCustom(c, PARAM_AVATAR);
        rlToolbar.addView(civAvatar);
        civAvatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mentorClickListener != null) {
                    mentorClickListener.onMenuClick(view);
                }
            }
        });
    }

    public void setAvatar(String image) {
        Picasso.get().load(image).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);
    }
    public void onToolbarMentorSelect(int select, String title) {
        ivLeftButton.setImageResource(0);
        ivLogo.setVisibility(GONE);
        // tvTitle.setText("");
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

    ////////////////         BOTTOM NAVIGATION VIEW          //////////////////////
    private void initBottomNavigationViewMentor() {
        bottomNavigationView = new BottomNavigationView(c);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        params.height = DimensionDP.sizeDP(50, c);
        bottomNavigationView.setLayoutParams(params);
        bottomNavigationView.setBackgroundColor(ColorsRGB.WHITE);
        bottomNavigationView.setItemIconTintList(null);

        int[][] states = new int[][] { new int[] { android.R.attr.state_activated }, new int[] { -android.R.attr.state_activated } };
        int[] colors = {ColorsRGB.GRAY_THREE, ColorsRGB.BLACK};
        ColorStateList colorStateList = new ColorStateList(states, colors);

        bottomNavigationView.setItemTextColor(colorStateList);

        onMenuAddMentor(1);

        onBottomNavigationViewSelectMentor(2);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case 1://  Cabinet
                        onBottomNavigationViewSelectMentor(1);
                        viewPager.setCurrentItem(0);
                        break;
                    case 2://  Coins
                        onBottomNavigationViewSelectMentor(2);
                        viewPager.setCurrentItem(1);
                        break;
                    case 3://  Contracts
                        onBottomNavigationViewSelectMentor(3);
                        viewPager.setCurrentItem(2);
                        break;
                    case 4://  Devices
                        onBottomNavigationViewSelectMentor(4);
                        viewPager.setCurrentItem(3);
                        break;
                }
                return false;
            }
        });

        //  END
        this.addView(bottomNavigationView);

        //   MBMBMBMB

    }
    public void onBottomNavigationViewSelectMentor(int select) {
        bottomNavigationView.getMenu().getItem(0).setIcon(R.mipmap.cabinet_off);//  OFF
        bottomNavigationView.getMenu().getItem(1).setIcon(R.mipmap.coins_black);//  OFF
        bottomNavigationView.getMenu().getItem(2).setIcon(R.mipmap.black_content);//  OFF
        bottomNavigationView.getMenu().getItem(3).setIcon(R.mipmap.black_calendar);//  OFF
        switch (select) {
            case 1:
                bottomNavigationView.getMenu().getItem(0).setIcon(R.mipmap.cabinet).setChecked(true);
                break;
            case 2:
                bottomNavigationView.getMenu().getItem(1).setIcon(R.mipmap.coins_gold).setChecked(true);
                break;
            case 3:
                bottomNavigationView.getMenu().getItem(2).setIcon(R.mipmap.red_content).setChecked(true);
                break;
            case 4:
                bottomNavigationView.getMenu().getItem(3).setIcon(R.mipmap.red_calendar).setChecked(true);
                break;
        }
        return;

    }
    private void onMenuAddMentor(int numb) {
        //  isMenu1 = false;
        //   isMenu2 = false;
        switch (numb) {
            case 1:
                Menu menu1 = bottomNavigationView.getMenu();
                menu1.add(Menu.NONE, 1, Menu.NONE, getResources().getString(R.string.cabinet)).setIcon(R.mipmap.cabinet_off);
                menu1.add(Menu.NONE, 2, Menu.NONE, getResources().getString(R.string.obols)).setIcon(R.mipmap.coins_black);
                menu1.add(Menu.NONE, 3, Menu.NONE, getResources().getString(R.string.content)).setIcon(R.mipmap.black_content);
                menu1.add(Menu.NONE, 4, Menu.NONE, getResources().getString(R.string.calendar)).setIcon(R.mipmap.black_calendar);
                menu1.add(Menu.NONE, 5, Menu.NONE, null).setIcon(R.mipmap.red_arrow_right);
                isMenu1 = true;
                break;
            case 2:
                //   Menu menu2 = bottomNavigationView.getMenu();
                //   menu2.add(Menu.NONE, 6, Menu.NONE, null).setIcon(R.mipmap.red_arrow_left);
                //  menu2.add(Menu.NONE, 7, Menu.NONE, getResources().getString(R.string.auto_charge_text)).setIcon(R.mipmap.auto_charge_black);
                ///  menu2.add(Menu.NONE, 8, Menu.NONE, getResources().getString(R.string.children_time_text)).setIcon(R.mipmap.children_time_black);
                // isMenu2 = true;
                break;
        }
    }

    ///////////           FRAGMENT VIEW PAGER             //////////////////
    private void initFragmentViewPagerMentor() {
        viewPager = new CustomViewPager().onCustom(c, PARAM_VIEW_PAGER);
        this.addView(viewPager);
        viewPager.setId((int)179356);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                isShownViewPagerChild(true);
                switch (position) {
                    case 0://  Cabinet
                        onBottomNavigationViewSelectMentor(1);
                        break;
                    case 1://  Coins
                        onBottomNavigationViewSelectMentor(2);
                        break;
                    case 2://
                        onBottomNavigationViewSelectMentor(3);
                        break;
                    case 3://  Devices
                        onBottomNavigationViewSelectMentor(4);
                        break;
                }
                //   if (parentClickListener != null) {
                //      parentClickListener.onViewPagerClick(position, childViewPager.getCurrentItem());
                //   }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

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
