package kz.tech.smartgrades.parents_module;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomScrollView;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.DrawerLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;

import static kz.tech.smartgrades.root.var_resources.ColorsRGB.WHITE;

public class ParentsNavigationView extends NavigationView  {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onFamilyMemberClick(String zID);
        void onNextPageClick(String fragment);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] param = {"prt:DrwLay", "layW:wCnt", "layH:mPrt"};
    private static final String[] paramContainerSV = {"prt:NavLay", "layW:mPrt", "layH:mPrt"};
    private static final String[] paramContainerLL = {"prt:ScrLay", "layW:mPrt", "layH:mPrt", "orn:ver"};
    private static final String[] paramLogo = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:50"};
    private static final String[] paramViewLine = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:1", "backC:GRAY_THREE"};
    private static final String[] paramContainer = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:50", "orn:hor", "WeiSum:6"};
    private static final String[] paramIcon = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "wei:1", "mrg:15,0,0,0"};
    private static final String[] paramText = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:50", "wei:4",
            "grv:LCV", "txtC:GRAY_THREE", "pad:10,0,0,0", "TyFa:BOLD"};
    private static final String[] paramArrow = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "wei:1", "mrg:0,0,15,0"};


    private static final String[] PARAM_FAMILY_CONTAINER = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "orn:ver", "mrg:50,0,0,0", "VIS:GONE"};
    private static final String[] PARAM_ADD_USER = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:40",
            "txtC:GRAY_THREE", "img:add_user", "grv:LCV"};


    private static final String[] PARAM_USER_PHOTO = {"prt:LinLay", "layW:mPrt", "layH:mPrt",
            "parW:50", "parH:50", "wei:1", "mrg:15,0,0,0", "img:avatar"};

    private LinearLayout llFamilyContainer, llAddUser;
    private ImageView ivGroupsArrow;
    private Context context;
    private TextView tvGroups, tvAddUser, tvDevices, tvReports, tvSettings, tvAbout, tvRecall;
    public ParentsNavigationView(Context context, Resources res) {
        super(context);
        this.context = context;
        this.setLayoutParams(new DrawerLayoutParams().getParams(context, param));
        this.setFitsSystemWindows(true);
        this.setBackgroundColor(WHITE);
        this.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                return false;
            }
        });
        //headerLayout
        ScrollView svContainer = new CustomScrollView().onCustom(context, paramContainerSV);//Vertical
        LinearLayout llContainer = new CustomLinearLayout().onCustom(context, paramContainerLL);//Vertical

        ImageView imageView = new CustomImageView().onCustom(context, paramLogo, R.drawable.logo);
        llContainer.addView(imageView);
        View vLine1 = new CustomView().onCustom(context, paramViewLine);
        llContainer.addView(vLine1);

        LinearLayout llGroups = new CustomLinearLayout().onCustom(context, paramContainer);
        ImageView ivGroups = new CustomImageView().onCustom(context, paramIcon, R.mipmap.groups);
        tvGroups = new CustomTextView().onCustom(context, paramText, res.getString(R.string.family_group));
        ivGroupsArrow = new CustomImageView().onCustom(context, paramArrow, R.mipmap.arrow);
        llGroups.addView(ivGroups);
        llGroups.addView(tvGroups);
        llGroups.addView(ivGroupsArrow);
        llContainer.addView(llGroups);
        llGroups.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               // onFamilyContainer();
                if (onItemClickListener != null) {
                    onItemClickListener.onNextPageClick("FamilyGroupFragment");
                }
            }
        });
        llFamilyContainer = new CustomLinearLayout().onCustom(context, PARAM_FAMILY_CONTAINER);
        llContainer.addView(llFamilyContainer);

        llAddUser = new CustomLinearLayout().onCustom(context, PARAM_FAMILY_CONTAINER);
        llContainer.addView(llAddUser);

        tvAddUser = new CustomTextView().onCustom(context, PARAM_ADD_USER, res.getString(R.string.add_user));
        llAddUser.addView(tvAddUser);
        tvAddUser.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onNextPageClick("AddUserFragment");
                }
            }
        });

        View vLine2 = new CustomView().onCustom(context, paramViewLine);
        llContainer.addView(vLine2);

        LinearLayout llDevices = new CustomLinearLayout().onCustom(context, paramContainer);
        ImageView ivDevices = new CustomImageView().onCustom(context, paramIcon, R.mipmap.devices);
        tvDevices = new CustomTextView().onCustom(context, paramText, res.getString(R.string.device_group));
        ImageView ivDevicesArrow = new CustomImageView().onCustom(context, paramArrow, R.mipmap.arrow);
        llDevices.addView(ivDevices);
        llDevices.addView(tvDevices);
        llDevices.addView(ivDevicesArrow);
        llContainer.addView(llDevices);
        llDevices.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                  //  onItemClickListener.onNextPageClick("DeviceGroupFragment");
                }
            }
        });

        View vLine3 = new CustomView().onCustom(context, paramViewLine);
        llContainer.addView(vLine3);

        LinearLayout llReports = new CustomLinearLayout().onCustom(context, paramContainer);
        ImageView ivReports = new CustomImageView().onCustom(context, paramIcon, R.mipmap.reports);
        tvReports = new CustomTextView().onCustom(context, paramText, res.getString(R.string.reports));
        ImageView ivReportsArrow = new CustomImageView().onCustom(context, paramArrow, R.mipmap.arrow);
        llReports.addView(ivReports);
        llReports.addView(tvReports);
        llReports.addView(ivReportsArrow);
        llContainer.addView(llReports);
        llReports.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                //    onItemClickListener.onNextPageClick("ReportsFragment");
                }
            }
        });

        View vLine4 = new CustomView().onCustom(context, paramViewLine);
        llContainer.addView(vLine4);

        LinearLayout llSettings = new CustomLinearLayout().onCustom(context, paramContainer);
        ImageView ivSettings = new CustomImageView().onCustom(context, paramIcon, R.mipmap.settings);
        tvSettings = new CustomTextView().onCustom(context, paramText, res.getString(R.string.settings));
        ImageView ivSettingsArrow = new CustomImageView().onCustom(context, paramArrow, R.mipmap.arrow);
        llSettings.addView(ivSettings);
        llSettings.addView(tvSettings);
        llSettings.addView(ivSettingsArrow);
        llContainer.addView(llSettings);
        llSettings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onNextPageClick("SettingsParentFragment");
                }
            }
        });

        View vLine5 = new CustomView().onCustom(context, paramViewLine);
        llContainer.addView(vLine5);

        LinearLayout llAbout = new CustomLinearLayout().onCustom(context, paramContainer);
        ImageView ivAbout = new CustomImageView().onCustom(context, paramIcon, R.mipmap.about);
        tvAbout = new CustomTextView().onCustom(context, paramText, res.getString(R.string.about_application));
        ImageView ivAboutArrow = new CustomImageView().onCustom(context, paramArrow, R.mipmap.arrow);
        llAbout.addView(ivAbout);
        llAbout.addView(tvAbout);
        llAbout.addView(ivAboutArrow);
        llContainer.addView(llAbout);
        llAbout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onNextPageClick("AboutAppFragment");
                }
            }
        });

        View vLine6 = new CustomView().onCustom(context, paramViewLine);
        llContainer.addView(vLine6);

        LinearLayout llRecall = new CustomLinearLayout().onCustom(context, paramContainer);
        ImageView ivRecall = new CustomImageView().onCustom(context, paramIcon, R.mipmap.recall);
        tvRecall = new CustomTextView().onCustom(context, paramText, res.getString(R.string.recall_comment));
        ImageView ivRecallArrow = new CustomImageView().onCustom(context, paramArrow, R.mipmap.arrow);
        llRecall.addView(ivRecall);
        llRecall.addView(tvRecall);
        llRecall.addView(ivRecallArrow);
        llContainer.addView(llRecall);
        llRecall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    //onItemClickListener.onNextPageClick("RecallAndCommentsFragment");
                }
            }
        });

        View vLine7 = new CustomView().onCustom(context, paramViewLine);
        llContainer.addView(vLine7);

        svContainer.addView(llContainer);
        this.addHeaderView(svContainer);
    }
    public ParentsNavigationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public ParentsNavigationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    private void onFamilyContainer() {
        if (llFamilyContainer.isShown()) {
            llFamilyContainer.setVisibility(GONE);
            ivGroupsArrow.setImageResource(R.mipmap.arrow);
            llAddUser.setVisibility(GONE);
        } else {
            llAddUser.setVisibility(VISIBLE);
            llFamilyContainer.setVisibility(VISIBLE);
            ivGroupsArrow.setImageResource(R.mipmap.arrow_dawn);
        }
    }
    public void onLoadFamilyMember(String urlAvatar, String name, String zID) {
        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, paramContainer);
        linearLayout.setBackgroundColor(Color.rgb(246, 246, 246));

        CircleImageView civPhotoUser = new CustomCircleImageView().onCustom(context, PARAM_USER_PHOTO);
        linearLayout.addView(civPhotoUser);
        Picasso.get().load(urlAvatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civPhotoUser);

        TextView tvName = new CustomTextView().onCustom(context, paramText, name);
        linearLayout.addView(tvName);

        ImageView ivUserMove = new CustomImageView().onCustom(context, paramArrow, R.mipmap.left_gray_small_arrow);
        linearLayout.addView(ivUserMove);
        ivUserMove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onFamilyMemberClick(zID);
                }
            }
        });

        llFamilyContainer.addView(linearLayout, 0);
    }
    public void setClearContainerLL() {
        llFamilyContainer.removeAllViewsInLayout();
    }
    public void changeLanguage(Resources res) {
        tvGroups.setText(res.getString(R.string.family_group));
        tvAddUser.setText(res.getString(R.string.add_user));
        tvDevices.setText(res.getString(R.string.device_group));
        tvReports.setText(res.getString(R.string.reports));
        tvSettings.setText(res.getString(R.string.settings));
        tvAbout.setText(res.getString(R.string.about_application));
        tvRecall.setText(res.getString(R.string.recall_comment));
    }

//View headerLayout = navigationView.getHeaderView(0);

}
