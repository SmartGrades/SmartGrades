package kz.tech.smartgrades.parent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.App;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.GET;
import kz.tech.smartgrades._System;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.AuthActivity;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.auth.models.ModelUserData;
import kz.tech.smartgrades.child.fragments.ChildCashFragment;
import kz.tech.smartgrades.child.fragments.ChildEditProfileFragment;
import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.child.models.ModelInterFormList;
import kz.tech.smartgrades.mentor.models.ModelMentorList;
import kz.tech.smartgrades.parent.adapters.ParentAddChildFragmentAdapter;
import kz.tech.smartgrades.parent.adapters.ParentAddLessonFragmentAdapter;
import kz.tech.smartgrades.parent.adapters.ParentAddParentFragmentAdapter;
import kz.tech.smartgrades.parent.adapters.ParentAddSchoolFragmentAdapter;
import kz.tech.smartgrades.parent.adapters.ParentAllTransactionAdapter;
import kz.tech.smartgrades.parent.adapters.ParentChatFragmentAdapter;
import kz.tech.smartgrades.parent.adapters.ParentChildesListAdapter;
import kz.tech.smartgrades.parent.adapters.ParentInviteListAdapter;
import kz.tech.smartgrades.parent.adapters.ParentOtherListAdapter;
import kz.tech.smartgrades.parent.adapters.ParentSelectedMentorListAdapter;
import kz.tech.smartgrades.parent.adapters.ParentShowIncomeLessonsFromActivityAdapter;
import kz.tech.smartgrades.parent.adapters.ParentShowMoreIncomeLessonsAdapter;
import kz.tech.smartgrades.parent.adapters.ParentShowMoreOtherLessonsAdapter;
import kz.tech.smartgrades.parent.adapters.ParentShowOtherLessonsFromActivityAdapter;
import kz.tech.smartgrades.parent.adapters.ParentSmartGradesListAdapter;
import kz.tech.smartgrades.parent.adapters.ParentTransactionListAdapter;
import kz.tech.smartgrades.parent.adapters.ParentUserListAdapter;
import kz.tech.smartgrades.parent.bottom_sheet_dialogs.BSDSetRegularGrades;
import kz.tech.smartgrades.parent.bottom_sheet_dialogs.BSDSetSmartGrades;
import kz.tech.smartgrades.parent.dialogs.ParentDialogMyFamily;
import kz.tech.smartgrades.parent.dialogs.ParentSponsorInterFormDialog;
import kz.tech.smartgrades.parent.fragments.FragmentParentAddNewLesson;
import kz.tech.smartgrades.parent.fragments.InteractionToolFragment;
import kz.tech.smartgrades.parent.fragments.ParentAddChildListFragment;
import kz.tech.smartgrades.parent.fragments.ParentAddLessonFragment;
import kz.tech.smartgrades.parent.fragments.ParentAddLessonMentorProfileFragment;
import kz.tech.smartgrades.parent.fragments.ParentAddMentorListFragment;
import kz.tech.smartgrades.parent.fragments.ParentAddMentorProfileFragment;
import kz.tech.smartgrades.parent.fragments.ParentAddParentFragment;
import kz.tech.smartgrades.parent.fragments.ParentAddParentListFragment;
import kz.tech.smartgrades.parent.fragments.ParentAddSchoolFragment;
import kz.tech.smartgrades.parent.fragments.ParentAddSchoolProfileFragment;
import kz.tech.smartgrades.parent.fragments.ParentAllOtherLessonFragment;
import kz.tech.smartgrades.parent.fragments.ParentAllSmartGradesFragment;
import kz.tech.smartgrades.parent.fragments.ParentAllTransactionFragment;
import kz.tech.smartgrades.parent.fragments.ParentCashExtractFragment;
import kz.tech.smartgrades.parent.fragments.ParentCashFragment;
import kz.tech.smartgrades.parent.fragments.ParentCashReplenishFragment;
import kz.tech.smartgrades.parent.fragments.ParentChatFragment;
import kz.tech.smartgrades.parent.fragments.ParentChatListFragment;
import kz.tech.smartgrades.parent.fragments.ParentChildLessonInfoFragment;
import kz.tech.smartgrades.parent.fragments.ParentChildProfileFragment;
import kz.tech.smartgrades.parent.fragments.ParentEditProfileFragment;
import kz.tech.smartgrades.parent.fragments.ParentGradesFragment;
import kz.tech.smartgrades.parent.fragments.ParentMentorListFragment;
import kz.tech.smartgrades.parent.fragments.ParentParentProfileFragment;
import kz.tech.smartgrades.parent.fragments.ParentSchoolProfile;
import kz.tech.smartgrades.parent.fragments.ParentSponsoringInfoFragment;
import kz.tech.smartgrades.parent.model.ModelChat;
import kz.tech.smartgrades.parent.model.ModelChildSponsorship;
import kz.tech.smartgrades.parent.model.ModelLessonsWithOutSmartGrades;
import kz.tech.smartgrades.parent.model.ModelLessonsWithSmartGrades;
import kz.tech.smartgrades.parent.model.ModelParentChildList;
import kz.tech.smartgrades.parent.model.ModelParentData;
import kz.tech.smartgrades.parent.model.ModelParentInFamilyGroup;
import kz.tech.smartgrades.parent.popup.PWFamilyGroupInfo;
import kz.tech.smartgrades.parent_add_child.ParentAddChildActivity;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.ParentAddSponsorOrMentorActivity;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.custom_view.CustomViewPager;
import kz.tech.smartgrades.root.dialogs.DAceessCode;
import kz.tech.smartgrades.root.language.ILanguage;
import kz.tech.smartgrades.root.login.ILogin;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolInfo;
import kz.tech.smartgrades.sponsor.models.ModelPrivateAccount;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_AcceptInterFormInFamilyGroup;
import static kz.tech.smartgrades._Web.func_GetUser;
import static kz.tech.smartgrades._Web.func_RejectInterFormInFamilyGroup;
import static kz.tech.smartgrades._Web.func_RejectInterFormOfSponsorship;
import static kz.tech.smartgrades._Web.func_UserEditPassword;

public class ParentActivity extends AppCompatActivity implements
        View.OnClickListener,
        ParentUserListAdapter.OnItemCLickListener,
        ParentChildesListAdapter.OnItemClickListener,
        ParentSelectedMentorListAdapter.OnItemClickListener,
        ParentSmartGradesListAdapter.OnItemClickListener,
        ParentOtherListAdapter.OnItemClickListener,
        ParentOtherListAdapter.OnItemLongClickListener,
        ParentSmartGradesListAdapter.OnItemLongClickListener,
        ParentTransactionListAdapter.OnItemClickListener,
        ParentInviteListAdapter.OnItemClickListener{

    @Inject
    public ILanguage language;
    @Inject
    public IAlert alert;
    @Inject
    public ILogin login;

    private String PARENT_ID;

    private CustomViewPager FullViewPager;
    private DrawerLayout drawer;
    private ConstraintLayout container1;
    private CircleImageView civAvatar;

    //    private ConstraintLayout clDefaultContainer;
    private FrameLayout flContainer2;
    private int Container2Id;
    //    private CustomViewPager viewPager;
    public ParentFragmentAdapter FragmentAdapter;
    //
//    private ImageView ivSearch;
//    private CircleImageView civAvatar;
//
    public ParentPresenter presenter;
    //
    private ImageView ivNav;
    private NavigationView navigation;
    private CircleImageView civNavAvatar;
    private TextView tvNavFullName, tvNavLogin, tvNavPhoneOrMail;
    private ImageView ivNavEdit;
    private TextView tvNavPushTurnOffOrOn;
    private Switch sNavPushTurnOffOrOn;
    private TextView tvNavQuickAccessCode;
    private TextView tvNavPassword;
    private TextView tvNavAppAbout;
    private TextView tvNavInviteFriends;
    private TextView tvNavSupport;
    private FrameLayout flCashExtract;

    private static int CURRENT_PAGE = -1;
    private static int FRAGMENT_ID = 0;//0=NULL, 1=ADD_SCHOOL

    private ModelPrivateAccount PrivateAccount;
    private ModelParentData mParentData;

    private TextView tvCurrentCash;

    private FrameLayout flMyFamily;
    //
//    private ParentUserListAdapter UserListAdapter;
//    private RecyclerView rvUserListAdapter;
//
    private ArrayList<ModelParentChildList> mParentChildLists;
    private int SELECT_CHILD_INDEX = 0;
    //
//    private CircleImageView civAddContact;
//    private LinearLayout llAddChild, llAddMentor, llSearchSponsor, llAddNewLesson;
//    private boolean SHOW_ADDS_BUTTONS = false;
//
//    private ConstraintLayout clChildInfo, clSponsorData;
//
//    private CircleImageView civSponsorshipChildAvatar, civSponsorshipChildAvatar0, civSponsorshipChildAvatar1, civSponsorshipChildAvatar2, civSponsorshipChildAvatar3;
//    private ImageView ivNotification0, ivNotification1, ivNotification2, ivNotification3;
//    private TextView tvSponsorshipArrowLeft, tvSponsorshipArrowRight;
//    private TextView tvSponsorshipAverageGrade, tvSponsorshipSum;
//
//    private CircleImageView civSponsorAvatar;
//    private TextView tvDaysLeft, tvGradesLeft, tvThresholdGrade, tvAverageGrade;
//
//    private CircleImageView civSendMoney;
//    private FrameLayout flChat;
//    private TextView tvNoCheckCount;
//
//    private ParentDialogChat dialogChat;
//    private ParentChatListFragment chatListFragment;
//    private ParentChatFragment chatFragment;
//
//    private ImageView[] ivStateWeek = new ImageView[4];
//
//    private ImageView ivExtraMenu;
//
//    private boolean AddChildEnable, AddSponsorEnable, AddMentorEnable, AddLessonEnable, AddSchoolEnable;
//    private TextView tvAddNewLesson, tvSearchSponsor, tvAddMentor, tvAddSchool;
//    private CircleImageView civAddNewLesson, civSearchSponsor, civAddMentor;
//    private LinearLayout llAddSchool;
//
//    private ParentAddSchoolFragmentAdapter addSchoolFragmentAdapter;
//
    private ParentChatFragmentAdapter chatFragmentAdapter;

    private ParentAddLessonFragmentAdapter parentAddLessonFragmentAdapter;


    private View vrvChildList;
    private View vclTransactions;
    private View vclSponsorInfo;
    private View vemptySourceOfIncomeContainer;
    private View vclIncomeSourceContainer;
    private View vclOtherLessonsContainer;


    //NO FAMILY FIRST START PAGE
    private ConstraintLayout cvNoFamilyGroup;
    private LinearLayout llNoFamilyGroup;
    private CardView cvCreateChildAccount;
    private CardView cvSearchChildAccount;
    private CardView cvEnrollFamilyGroup;
    //MAIN PAGE
    //---Child list
    private ScrollView svChildWithParentContainer;
    private DiscreteScrollView dcvChildList;
    private RecyclerView rvAvatarWithIncome;
    //---Sponsor info
    private ConstraintLayout clSponsorInfo;
    private TextView tvSumOfMoney;
    private CircleImageView civSponsorAvatar;
    private TextView tvSponsorName;
    private TextView tvWeek;
    private ProgressBar pbChildMoney;
    private TextView tvChildMoneyPercentage;
    private TextView tvDayCount;
    private CardView btnLesson1;
    private CardView btnLesson2;
    private CardView btnLesson3;
    private CardView btnLesson4;
    private TextView tvLesson1;
    private TextView tvLesson2;
    private TextView tvLesson3;
    private TextView tvLesson4;
    private TextView tvMonth;
    private ConstraintLayout clTransactions;
    private TextView tvTodayIncomeValue;
    //____
    private ImageView ivNotification;
    private TextView tvHold;
    private TextView tvHold2;
    private RecyclerView rvIncomeSource;
    private TextView tvAddLesson;
    private CardView cvShowMoreIncomeLessons;
    //____
    private ConstraintLayout emptySourceOfIncomeContainer;
    private Button btnCreateIncomeSource;
    //____
    private RecyclerView rvOtherLessons;
    private CardView cvShowMoreOtherLessons;
    //___
    private ConstraintLayout emptyLessonsContainer;
    private Button btnCreateOtherLessons;
    //---notification
    private ImageView ivFamilyInvite;
    private RecyclerView rvParentInvite;
    private ConstraintLayout clInviteFamilyGroupContainer;
    private View vclInviteFamilyGroupContainer;

    private TextView tvFamilyGroupInfo;

    private ParentChildesListAdapter parentChildesListAdapter;

    private ParentCashFragment parentCashFragment;

    private ConstraintLayout clIncomeSourceContainer;
    private ConstraintLayout clOtherLessonsContainer;

    private ParentAddLessonFragment parentAddLessonFragment;

    private ParentSmartGradesListAdapter parentSmartGradesListAdapter;

    private CardView cvDatePeriod;
    private CardView cvAddSchool;
    private CardView cvAddLessonFAB;

    private ParentShowMoreIncomeLessonsAdapter parentShowMoreIncomeLessonsAdapter;
    private ParentShowIncomeLessonsFromActivityAdapter parentShowIncomeLessonsFromActivityAdapter;
    private ParentOtherListAdapter parentOtherListAdapter;

    private ParentShowMoreOtherLessonsAdapter parentShowMoreOtherLessonsAdapter;
    private ParentShowOtherLessonsFromActivityAdapter parentShowOtherLessonsFromActivityAdapter;

    private ParentTransactionListAdapter parentTransactionListAdapter;
    private ParentAllTransactionAdapter parentAllTransactionAdapter;

    private ParentAddChildFragmentAdapter parentAddChildFragmentAdapter;
    private ParentAddParentFragmentAdapter parentAddParentFragmentAdapter;

    private ParentAddSchoolFragmentAdapter addSchoolFragmentAdapter;

    private ParentInviteListAdapter InviteListAdapter;

    private TextView tvTransactionsEmptyLabel;

    private ParentSponsoringInfoFragment parentSponsoringInfoFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_with_cards);
        App.app().getComponent().inject(this);
        PARENT_ID = login.loadUserDate(LoginKey.ID);
        initUI();
        setProfileData();
        initPresenter();
    }

    public void updateActivity(){
        PARENT_ID = login.loadUserDate(LoginKey.ID);
        initUI();
        setProfileData();
        initPresenter();
    }

    private void initUI(){
        Container2Id = R.id.container2;
        flContainer2 = findViewById(Container2Id);
        FullViewPager = findViewById(R.id.FullViewPager);

        civAvatar = findViewById(R.id.civAvatar);
        civAvatar.setOnClickListener(this);
        tvCurrentCash = findViewById(R.id.tvCurrentCash);

        ivNav = findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        navigation = findViewById(R.id.navigation);
        drawer = findViewById(R.id.drawer);
        View navLayout = navigation.getHeaderView(0);
        civNavAvatar = navLayout.findViewById(R.id.civNavAvatar);
        tvNavFullName = navLayout.findViewById(R.id.tvNavFullName);
        tvNavLogin = navLayout.findViewById(R.id.tvNavLogin);
        tvNavPhoneOrMail = navLayout.findViewById(R.id.tvNavPhoneOrMail);

        flMyFamily = navLayout.findViewById(R.id.flMyFamily);
        flMyFamily.setOnClickListener(this);
        flMyFamily.setVisibility(View.VISIBLE);

        ivNavEdit = navLayout.findViewById(R.id.ivNavEdit);
        ivNavEdit.setOnClickListener(this);
        tvNavPushTurnOffOrOn = navLayout.findViewById(R.id.tvNavPushTurnOffOrOn);
        sNavPushTurnOffOrOn = navLayout.findViewById(R.id.sNavPushTurnOffOrOn);
        sNavPushTurnOffOrOn.setOnClickListener(this);
        tvNavQuickAccessCode = navLayout.findViewById(R.id.tvNavQuickAccessCode);
        tvNavQuickAccessCode.setOnClickListener(this);
        tvNavPassword = navLayout.findViewById(R.id.tvNavPassword);
        tvNavPassword.setOnClickListener(this);
        tvNavAppAbout = navLayout.findViewById(R.id.tvNavAppAbout);
        tvNavAppAbout.setOnClickListener(this);
        tvNavInviteFriends = navLayout.findViewById(R.id.tvNavInviteFriends);
        tvNavInviteFriends.setOnClickListener(this);
        tvNavSupport = navLayout.findViewById(R.id.tvNavSupport);
        tvNavSupport.setOnClickListener(this);
        flCashExtract = navLayout.findViewById(R.id.flCashExtract);
        flCashExtract.setOnClickListener(this);
        flCashExtract.setVisibility(View.VISIBLE);

        tvTransactionsEmptyLabel = findViewById(R.id.tvTransactionsEmptyLabel);

        container1 = findViewById(R.id.container1);
        cvNoFamilyGroup = findViewById(R.id.cvNoFamilyGroup);
        llNoFamilyGroup = findViewById(R.id.llNoFamilyGroup);
        cvCreateChildAccount = findViewById(R.id.cvCreateChildAccount);
        cvCreateChildAccount.setOnClickListener(this);
        cvSearchChildAccount = findViewById(R.id.cvSearchChildAccount);
        cvSearchChildAccount.setOnClickListener(this);
        cvEnrollFamilyGroup = findViewById(R.id.cvEnrollFamilyGroup);
        cvEnrollFamilyGroup.setOnClickListener(this);
        svChildWithParentContainer = findViewById(R.id.svChildWithParentContainer);
        dcvChildList = findViewById(R.id.dcvChildList);
        vrvChildList = findViewById(R.id.vrvChildList);
        rvAvatarWithIncome = findViewById(R.id.rvAvatarWithIncome);
        clSponsorInfo = findViewById(R.id.clSponsorInfo);
        clSponsorInfo.setOnClickListener(this);
        vclSponsorInfo = findViewById(R.id.vclSponsorInfo);
        tvSumOfMoney = findViewById(R.id.tvSumOfMoney);
        civSponsorAvatar = findViewById(R.id.civSponsorAvatar);
        tvSponsorName = findViewById(R.id.tvSponsorName);
        tvWeek = findViewById(R.id.tvWeek);
        pbChildMoney = findViewById(R.id.pbChildMoney);
        tvChildMoneyPercentage = findViewById(R.id.tvChildMoneyPercentage);
        tvDayCount = findViewById(R.id.tvDayCount);
        btnLesson1 = findViewById(R.id.btnLesson1);
        btnLesson1.setOnClickListener(this);
        btnLesson2 = findViewById(R.id.btnLesson2);
        btnLesson2.setOnClickListener(this);
        btnLesson3 = findViewById(R.id.btnLesson3);
        btnLesson3.setOnClickListener(this);
        btnLesson4 = findViewById(R.id.btnLesson4);
        btnLesson4.setOnClickListener(this);
        tvLesson1 = findViewById(R.id.tvLesson1);
        tvLesson2 = findViewById(R.id.tvLesson2);
        tvLesson3 = findViewById(R.id.tvLesson3);
        tvLesson4 = findViewById(R.id.tvLesson4);
        tvMonth = findViewById(R.id.tvMonth);
        tvMonth.setOnClickListener(this);
        ivNotification = findViewById(R.id.ivNotification);
        tvHold = findViewById(R.id.tvHold);
        tvHold2 = findViewById(R.id.tvHold2);
        rvIncomeSource = findViewById(R.id.rvIncomeSource);
        tvAddLesson = findViewById(R.id.tvAddLesson);
        cvShowMoreIncomeLessons = findViewById(R.id.cvShowMoreIncomeLessons);
        cvShowMoreIncomeLessons.setOnClickListener(this);
        emptySourceOfIncomeContainer = findViewById(R.id.emptySourceOfIncomeContainer);
        vemptySourceOfIncomeContainer = findViewById(R.id.vemptySourceOfIncomeContainer);
        btnCreateIncomeSource = findViewById(R.id.btnCreateIncomeSource);
        btnCreateIncomeSource.setOnClickListener(this);
        rvOtherLessons = findViewById(R.id.rvOtherLessons);
        cvShowMoreOtherLessons = findViewById(R.id.cvShowMoreOtherLessons);
        cvShowMoreOtherLessons.setOnClickListener(this);
        emptyLessonsContainer = findViewById(R.id.emptyLessonsContainer);
        btnCreateOtherLessons = findViewById(R.id.btnCreateOtherLessons);
        btnCreateOtherLessons.setOnClickListener(this);
        ivFamilyInvite = findViewById(R.id.ivFamilyInvite);
        rvParentInvite = findViewById(R.id.rvParentInvite);
        vclInviteFamilyGroupContainer = findViewById(R.id.vclInviteFamilyGroupContainer);
        clInviteFamilyGroupContainer = findViewById(R.id.clInviteFamilyGroupContainer);

        clTransactions = findViewById(R.id.clTransactions);
        vclTransactions = findViewById(R.id.vclTransactions);
        tvTodayIncomeValue = findViewById(R.id.tvTodayIncomeValue);
        clIncomeSourceContainer = findViewById(R.id.clIncomeSourceContainer);
        vclIncomeSourceContainer = findViewById(R.id.vclIncomeSourceContainer);
        clOtherLessonsContainer = findViewById(R.id.clOtherLessonsContainer);
        vclOtherLessonsContainer = findViewById(R.id.vclOtherLessonsContainer);


        cvDatePeriod = findViewById(R.id.cvDatePeriod);
        cvDatePeriod.setOnClickListener(this);
        cvAddSchool = findViewById(R.id.cvAddSchool);
        cvAddSchool.setOnClickListener(this);
        cvAddLessonFAB = findViewById(R.id.cvAddLessonFAB);
        cvAddLessonFAB.setOnClickListener(this);

        tvFamilyGroupInfo = findViewById(R.id.tvFamilyGroupInfo);
        tvFamilyGroupInfo.setOnClickListener(this);
//        tvCurentCash = findViewById(R.id.tvCurentCash);
//        civAvatar = findViewById(R.id.civAvatar);
//        civAvatar.setOnClickListener(this);
//        ivNav = findViewById(R.id.ivNav);
//        ivNav.setOnClickListener(this);
//        ivSearch = findViewById(R.id.ivSearch);
//        ivSearch.setOnClickListener(this);
//
//        FragmentAdapter = new ParentFragmentAdapter(getSupportFragmentManager());
//        /*viewPager.setAdapter(FragmentAdapter);
//        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
//        viewPager.setOffscreenPageLimit(4);
//        viewPager.setCurrentItem(CURRENT_PAGE);*/
//
//        llAddSchool = findViewById(R.id.llAddSchool);
//        llAddSchool.setOnClickListener(this);
//        tvAddSchool = findViewById(R.id.tvAddSchool);
//
//        tvAddNewLesson = findViewById(R.id.tvAddNewLesson);
//        tvSearchSponsor = findViewById(R.id.tvSearchSponsor);
//        tvAddMentor = findViewById(R.id.tvAddMentor);
//        civAddNewLesson = findViewById(R.id.civAddNewLesson);
//        civSearchSponsor = findViewById(R.id.civSearchSponsor);
//        civAddMentor = findViewById(R.id.civAddMentor);
//
//        ivExtraMenu = findViewById(R.id.ivExtraMenu);
//        ivExtraMenu.setOnClickListener(this);
//
//        ivStateWeek[0] = findViewById(R.id.ivStateWeek1);
//        ivStateWeek[1] = findViewById(R.id.ivStateWeek2);
//        ivStateWeek[2] = findViewById(R.id.ivStateWeek3);
//        ivStateWeek[3] = findViewById(R.id.ivStateWeek4);
//
//        ivNotification0 = findViewById(R.id.ivNotification0);
//        ivNotification1 = findViewById(R.id.ivNotification1);
//        ivNotification2 = findViewById(R.id.ivNotification2);
//        ivNotification3 = findViewById(R.id.ivNotification3);
//
//        civSponsorshipChildAvatar0 = findViewById(R.id.civSponsorshipChildAvatar0);
//        civSponsorshipChildAvatar1 = findViewById(R.id.civSponsorshipChildAvatar1);
//        civSponsorshipChildAvatar2 = findViewById(R.id.civSponsorshipChildAvatar2);
//        civSponsorshipChildAvatar3 = findViewById(R.id.civSponsorshipChildAvatar3);
//        tvSponsorshipArrowLeft = findViewById(R.id.tvSponsorshipArrowLeft);
//        tvSponsorshipArrowLeft.setOnClickListener(this);
//        tvSponsorshipArrowRight = findViewById(R.id.tvSponsorshipArrowRight);
//        tvSponsorshipArrowRight.setOnClickListener(this);
//        civSponsorshipChildAvatar = findViewById(R.id.civSponsorshipChildAvatar);
//        civSponsorshipChildAvatar.setOnClickListener(this);
//        tvSponsorshipAverageGrade = findViewById(R.id.tvSponsorshipAverageGrade);
//        tvSponsorshipSum = findViewById(R.id.tvSponsorshipSum);
//
//        flChat = findViewById(R.id.flChat);
//        flChat.setOnClickListener(this);
//        tvNoCheckCount = findViewById(R.id.tvNoCheckCount);
//
//        clChildInfo = findViewById(R.id.clChildInfo);
//        clSponsorData = findViewById(R.id.clSponsorData);
//        clSponsorData.setOnClickListener(this);
//
//        civSponsorAvatar = findViewById(R.id.civSponsorAvatar);
//        tvDaysLeft = findViewById(R.id.tvDaysLeft);
//        tvGradesLeft = findViewById(R.id.tvGradesLeft);
//        tvThresholdGrade = findViewById(R.id.tvThresholdGrade);
//        tvAverageGrade = findViewById(R.id.tvAverageGrade);
//
//        civAddContact = findViewById(R.id.civAddContact);
//        civAddContact.setOnClickListener(this);
//        civSendMoney = findViewById(R.id.civSendMoney);
//        civSendMoney.setOnClickListener(this);
//
//        llAddChild = findViewById(R.id.llAddChild);
//        llAddChild.setOnClickListener(this);
//        llAddMentor = findViewById(R.id.llAddMentor);
//        llAddMentor.setOnClickListener(this);
//        llSearchSponsor = findViewById(R.id.llSearchSponsor);
//        llSearchSponsor.setOnClickListener(this);
//        llAddNewLesson = findViewById(R.id.llAddNewLesson);
//        llAddNewLesson.setOnClickListener(this);
//
//        UserListAdapter = new ParentUserListAdapter(this);
//        UserListAdapter.setOnItemCLickListener(this);
//        rvUserListAdapter = findViewById(R.id.rvContactsList);
//        rvUserListAdapter.setLayoutManager(new LinearLayoutManager(this));
//        rvUserListAdapter.setAdapter(UserListAdapter);
    }

    public void setProfileData(){
        String avatar = login.loadUserDate(LoginKey.AVATAR);
        if(!stringIsNullOrEmpty(avatar)){
            //   Picasso.with(this).load(avatar).fit().centerInside().error(R.mipmap.avatar).placeholder(R.mipmap.avatar).into(civAvatar);
            Picasso.get().load(avatar).fit().centerInside().into(civAvatar);
            Picasso.get().load(avatar).fit().centerInside().into(civNavAvatar);
        }
        else{
            civAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
            civNavAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
        }

        String fullName = login.loadUserDate(LoginKey.FIRST_NAME) + " " + login.loadUserDate(LoginKey.LAST_NAME);
        if(fullName != null) tvNavFullName.setText(fullName);

        String loginn = login.loadUserDate(LoginKey.LOGIN);
        if(loginn != null) tvNavLogin.setText(loginn);

        String phoneOrMail = login.loadUserDate(LoginKey.PHONE);
        if(phoneOrMail != null) tvNavPhoneOrMail.setText(phoneOrMail);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("SP_SETTINGS_PARENT_NAV", 0);
        Boolean b = sp.getBoolean("PUSH", true);
        sNavPushTurnOffOrOn.setChecked(b);
    }

    private void initPresenter(){
        presenter = new ParentPresenter(this);
        presenter.onStartPresenter();
    }

    private void setChildesList(){
        if(!listIsNullOrEmpty(mParentData.getChildrenList())){
            dcvChildList.setVisibility(View.VISIBLE);
            vrvChildList.setVisibility(View.VISIBLE);;
            parentChildesListAdapter = new ParentChildesListAdapter(this);
            parentChildesListAdapter.setOnItemClickListener(this);
            dcvChildList.setAdapter(parentChildesListAdapter);

            ArrayList<ModelChildData> m = new ArrayList<>();
            m.add(new ModelChildData());
            m.addAll(mParentData.getChildrenList());
            parentChildesListAdapter.updateData(m);

            dcvChildList.scrollToPosition(1);
            dcvChildList.setItemTransformer(new ScaleTransformer.Builder()
                    .setMaxScale(1.1f)
                    .setMinScale(0.7f)
                    .setPivotX(Pivot.X.CENTER)
                    .setPivotY(Pivot.Y.CENTER)
                    .build());
            dcvChildList.addScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>() {
                @Override
                public void onScrollStart(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) { }
                @Override
                public void onScrollEnd(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
                    if (adapterPosition != 0) {
                        SELECT_CHILD_INDEX = adapterPosition - 1;
                        setTransaction(SELECT_CHILD_INDEX);
                        setChildrenLessons(SELECT_CHILD_INDEX);
                        setSponsorData(SELECT_CHILD_INDEX);
                    }
                }
                @Override
                public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder, @Nullable RecyclerView.ViewHolder newCurrent) {
                }
            });

            setTransaction(SELECT_CHILD_INDEX);
            setChildrenLessons(SELECT_CHILD_INDEX);
            setSponsorData(SELECT_CHILD_INDEX);
        }
    }

    private void setSponsorData(int childrenPosition) {
        ModelChildSponsorship m = mParentData.getChildrenList().get(childrenPosition).getSponsorship();
        if (m != null) {
            vclSponsorInfo.setVisibility(View.VISIBLE);
            clSponsorInfo.setVisibility(View.VISIBLE);
            String avatar = m.getSponsorAvatar();
            if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).fit().centerInside().into(civSponsorAvatar);
            else civSponsorAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
            tvSponsorName.setText(m.getSponsorFirstName() + " " + m.getSponsorLastName());
            if (m.getTotalReward() > 0) tvSumOfMoney.setText("+ " + m.getTotalReward());
            else tvSumOfMoney.setText("0");
            tvWeek.setText(getResources().getString(R.string.week) + " " + m.getCurrentWeek() + "/" + m.getTotalWeek());
            int progress = m.getProgress();
            tvChildMoneyPercentage.setText(progress + "%");
            pbChildMoney.setProgress(progress);
            if (m.getLessons() != null) {
                if (m.getLessons().size() >= 1) {
                btnLesson1.setVisibility(View.VISIBLE);
                tvLesson1.setText(m.getLessons().get(0).getLessonName());
                }
                if (m.getLessons().size() >= 2) {
                    btnLesson2.setVisibility(View.VISIBLE);
                    tvLesson2.setText(m.getLessons().get(1).getLessonName());
                }
                if (m.getLessons().size() >= 3) {
                    btnLesson3.setVisibility(View.VISIBLE);
                    tvLesson3.setText(m.getLessons().get(2).getLessonName());
                }
                if (m.getLessons().size() >= 4) {
                    btnLesson4.setVisibility(View.VISIBLE);
                    tvLesson4.setText(m.getLessons().get(3).getLessonName());
                }
            }
            tvDayCount.setText(m.getCurrentDay() + "/" + m.getTotalDays() + " " + getResources().getString(R.string.days));
        }
        else {
            vclSponsorInfo.setVisibility(View.GONE);
            clSponsorInfo.setVisibility(View.GONE);
        }
    }

    private void onSponsorInfo() {
        boolean flag = true;
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            if(fragment instanceof ChildCashFragment){
                flag = false;
                break;
            }
        }
        if(flag){
            parentSponsoringInfoFragment = new ParentSponsoringInfoFragment(mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getSponsorship());
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container2, parentSponsoringInfoFragment).commit();
        }
    }

    private void setChildrenLessons(int childrenPosition){
        int flag = 0;
        boolean LessonIsExist = false;
        if(!listIsNullOrEmpty(mParentData.ChildrenList.get(childrenPosition).getLessonsWithSmartGrades())){
            LessonIsExist = true;
            emptySourceOfIncomeContainer.setVisibility(View.GONE);
            vemptySourceOfIncomeContainer.setVisibility(View.GONE);
            clIncomeSourceContainer.setVisibility(View.VISIBLE);
            vclIncomeSourceContainer.setVisibility(View.VISIBLE);
            rvIncomeSource.setVisibility(View.VISIBLE);
            setSmartGradesListAdapter(childrenPosition);
            if(mParentData.ChildrenList.get(childrenPosition).getLessonsWithSmartGrades().size() > 4){
                cvShowMoreIncomeLessons.setVisibility(View.VISIBLE);
            }
            else cvShowMoreIncomeLessons.setVisibility(View.GONE);
        }
        else {
            flag++;
//            emptySourceOfIncomeContainer.setVisibility(View.VISIBLE);
//            vemptySourceOfIncomeContainer.setVisibility(View.VISIBLE);
            clIncomeSourceContainer.setVisibility(View.GONE);
            vclIncomeSourceContainer.setVisibility(View.GONE);
            rvIncomeSource.setVisibility(View.GONE);
        }
        if(!listIsNullOrEmpty(mParentData.ChildrenList.get(childrenPosition).getLessonsWithoutSmartGrades())){
            LessonIsExist = true;
            clOtherLessonsContainer.setVisibility(View.VISIBLE);
            vclOtherLessonsContainer.setVisibility(View.VISIBLE);
            rvOtherLessons.setVisibility(View.VISIBLE);
            setOtherListAdapter(childrenPosition);
            if(mParentData.ChildrenList.get(childrenPosition).getLessonsWithoutSmartGrades().size() > 4){
                cvShowMoreOtherLessons.setVisibility(View.VISIBLE);
            }
            else cvShowMoreOtherLessons.setVisibility(View.GONE);
        }
        else {
            flag++;
            clOtherLessonsContainer.setVisibility(View.GONE);
            vclOtherLessonsContainer.setVisibility(View.GONE);
            rvOtherLessons.setVisibility(View.GONE);
        }

        if(LessonIsExist){
            emptySourceOfIncomeContainer.setVisibility(View.GONE);
            vemptySourceOfIncomeContainer.setVisibility(View.GONE);
        }

        if(flag == 2){
            emptySourceOfIncomeContainer.setVisibility(View.VISIBLE);
            vemptySourceOfIncomeContainer.setVisibility(View.VISIBLE);
            setFabGone();
            return;
        }
        else{
            if (CURRENT_PAGE < 0) setFabVisible();
        }
    }

    private void setTransaction(int childrenPosition){
        if(mParentData.getChildrenList().get(childrenPosition).getPrivateAccount() == null) return;

        clTransactions.setVisibility(View.VISIBLE);
        vclTransactions.setVisibility(View.VISIBLE);
        if(!stringIsNullOrEmpty(mParentData.getChildrenList().get(childrenPosition).getPrivateAccount().getCurrentSum())){
            tvTodayIncomeValue.setText(mParentData.getChildrenList().get(childrenPosition).getPrivateAccount().getCurrentSum());
        }
        else tvTodayIncomeValue.setText("0");

        rvAvatarWithIncome.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        parentTransactionListAdapter = new ParentTransactionListAdapter(this);
        parentTransactionListAdapter.setOnItemClickListener(this);
        rvAvatarWithIncome.setAdapter(parentTransactionListAdapter);
        parentTransactionListAdapter.updateData(mParentData.getChildrenList().get(childrenPosition).getPrivateAccount().getTransactions());

        if(!listIsNullOrEmpty(mParentData.getChildrenList().get(childrenPosition).getPrivateAccount().getTransactions())) {
            tvTransactionsEmptyLabel.setVisibility(View.GONE);
        } else {
            tvTransactionsEmptyLabel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTransactionClick(){
        setFabGone();
        FRAGMENT_ID = 9;
        parentAllTransactionAdapter = new ParentAllTransactionAdapter(getSupportFragmentManager());
        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(parentAllTransactionAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(parentAllTransactionAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);

        parentAllTransactionAdapter.setChildModel(mParentData.ChildrenList.get(SELECT_CHILD_INDEX));
    }

    private void setSmartGradesListAdapter(int childrenPosition){
        ArrayList<ModelLessonsWithSmartGrades> lessonList = mParentData.ChildrenList.get(childrenPosition).getLessonsWithSmartGrades();

        rvIncomeSource.setVisibility(View.VISIBLE);
        rvIncomeSource.setLayoutManager(new GridLayoutManager(this, 2));

        parentSmartGradesListAdapter = new ParentSmartGradesListAdapter(this);
        parentSmartGradesListAdapter.setOnItemClickListener(this);
        parentSmartGradesListAdapter.setOnItemLongClickListener(this);
        rvIncomeSource.setAdapter(parentSmartGradesListAdapter);
        rvIncomeSource.suppressLayout(true);

        if(lessonList.size() > 4)
            parentSmartGradesListAdapter.updateData(new ArrayList(lessonList.subList(0, 4)));
        else parentSmartGradesListAdapter.updateData(lessonList);
    }

    private void setOtherListAdapter(int childrenPosition){
        ArrayList<ModelLessonsWithOutSmartGrades> lessonList = mParentData.ChildrenList.get(childrenPosition).getLessonsWithoutSmartGrades();

        rvOtherLessons.setVisibility(View.VISIBLE);
        rvOtherLessons.setLayoutManager(new GridLayoutManager(this, 2));

        parentOtherListAdapter = new ParentOtherListAdapter(this);
        parentOtherListAdapter.setOnItemClickListener(this);
        parentOtherListAdapter.setOnItemLongClickListener(this);
        rvOtherLessons.setAdapter(parentOtherListAdapter);
        rvOtherLessons.suppressLayout(true);

        if(lessonList.size() > 4)
            parentOtherListAdapter.updateData(new ArrayList(lessonList.subList(0, 4)));
        else parentOtherListAdapter.updateData(lessonList);
    }

    @Override
    public void onClick(ModelChildData modelChildData, int p){
        SELECT_CHILD_INDEX = p - 1;
        dcvChildList.setItemTransitionTimeMillis(100);
        dcvChildList.smoothScrollToPosition(p);
    }

    public void onContainerIsVisible(boolean isVisible){
//        clDefaultContainer.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void setParentData(ModelParentData mParentData){
        if(mParentData == null) return;
        this.mParentData = mParentData;
        this.PrivateAccount = mParentData.getPrivateAccount();
        if(!stringIsNullOrEmpty(PrivateAccount.getCurrentSum()))
            tvCurrentCash.setText(PrivateAccount.getCurrentSum() + " " + PrivateAccount.getAccountType());
        else tvCurrentCash.setText("??? 0");
        checkFamilyGroup();
        setChildesList();

        clInviteFamilyGroupContainer.setVisibility(View.GONE);
        vclInviteFamilyGroupContainer.setVisibility(View.GONE);

        //?????? ???????????????????? ?????????? ?????????????????? ?????????? ????????????
        if(parentShowMoreIncomeLessonsAdapter != null){
            parentShowMoreIncomeLessonsAdapter.setLessonList(mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getLessonsWithSmartGrades());
        }

        if(parentShowMoreOtherLessonsAdapter != null){
            parentShowMoreOtherLessonsAdapter.setLessonList(mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getLessonsWithoutSmartGrades());
        }


        if (mParentData.getFamilyGroupId() == null) {
            flMyFamily.setBackgroundColor(getResources().getColor(R.color.gray_disabled));
            flMyFamily.setClickable(false);
        }

        if (parentCashFragment != null) {
            parentCashFragment.updateCards();
        }
//        mParentChildLists = mParentData.getChildrenList();
//        if(!listIsNullOrEmpty(mParentChildLists)){
//            UserListAdapter.updateData(mParentData, false);
//            if(mParentChildLists.size() > 1){
//                tvSponsorshipArrowLeft.setVisibility(View.VISIBLE);
//                tvSponsorshipArrowRight.setVisibility(View.VISIBLE);
//            }
//            clChildInfo.setVisibility(View.VISIBLE);
//            civSendMoney.setVisibility(View.VISIBLE);
//            SELECT_CHILD(null);
//
//            AddLessonEnable = AddMentorEnable = AddSponsorEnable = AddSchoolEnable = true;
//            tvAddNewLesson.setTextColor(Color.BLACK);
//            tvSearchSponsor.setTextColor(Color.BLACK);
//            tvAddMentor.setTextColor(Color.BLACK);
//            tvAddSchool.setTextColor(Color.BLACK);
//            civAddNewLesson.setImageDrawable(getResources().getDrawable(R.drawable.img_add_new_lesson_active));
//            civSearchSponsor.setImageDrawable(getResources().getDrawable(R.drawable.img_search_sponsor_active));
//            civAddMentor.setImageDrawable(getResources().getDrawable(R.drawable.img_add_mentor_active));
//        }
//
//        if(!listIsNullOrEmpty(mParentData.getPrivateChats())){
//            if(dialogChat != null) dialogChat.updateChatsList(mParentData);
//
//            int TotalCountNewMessages = 0;
//            for(int i = 0; i < mParentData.getPrivateChats().size(); i++)
//                TotalCountNewMessages += mParentData.getPrivateChats().get(i).getNoCheckCount();
//
//            if(TotalCountNewMessages > 0){
//                tvNoCheckCount.setText(String.valueOf(TotalCountNewMessages));
//                tvNoCheckCount.setVisibility(View.VISIBLE);
//            }
//            else tvNoCheckCount.setVisibility(View.GONE);
//        }
//        else tvNoCheckCount.setVisibility(View.GONE);
    }

//    public ModelPrivateAccount getPrivateAccount(){
//        return PrivateAccount;
//    }


    private void checkFamilyGroup(){
        String s = mParentData.getFamilyGroupId();
        if(!stringIsNullOrEmpty(mParentData.getFamilyGroupId())){
            cvNoFamilyGroup.setVisibility(View.GONE);
            svChildWithParentContainer.setVisibility(View.VISIBLE);
        }
        else{
            //svChildWithParentContainer.setVisibility(View.GONE);
            cvNoFamilyGroup.setVisibility(View.VISIBLE);
        }
    }

    public void onOpenChat(String lessonName, String lessonId){
        onNextFragment();
        if(FRAGMENT_ID == 7){
            if(parentShowIncomeLessonsFromActivityAdapter != null)
                parentShowIncomeLessonsFromActivityAdapter.setLessonData(lessonName, lessonId);
            else if(parentShowMoreIncomeLessonsAdapter != null)
                parentShowMoreIncomeLessonsAdapter.setLessonData(lessonName, lessonId);
        }
        else if(FRAGMENT_ID == 8){
            if(parentShowMoreOtherLessonsAdapter != null)
                parentShowMoreOtherLessonsAdapter.setLessonData(lessonName, lessonId);
            else if(parentShowOtherLessonsFromActivityAdapter != null)
                parentShowOtherLessonsFromActivityAdapter.setLessonData(lessonName, lessonId);
        }
    }

    public void setInvites(){
        clInviteFamilyGroupContainer.setVisibility(View.VISIBLE);
        vclInviteFamilyGroupContainer.setVisibility(View.VISIBLE);

        rvParentInvite.setLayoutManager(new LinearLayoutManager(this));

        InviteListAdapter = new ParentInviteListAdapter(this);
        InviteListAdapter.setOnItemClickListener(this);
        rvParentInvite.setAdapter(InviteListAdapter);
        InviteListAdapter.updateData(getModelInterFormList());
    }

    public ArrayList<ModelInterFormList> getModelInterFormList(){
        return mParentData.getInterFormList();
    }

    /*private void checkLessons() {
        if (!listIsNullOrEmpty(mParentData.getChildrenList().get(0).getModelLessonWithSettings())) {
            clIncomeSourceContainer.setVisibility(View.VISIBLE);
            emptySourceOfIncomeContainer.setVisibility(View.VISIBLE);
        }
    }*/

    public void onNextActivity(int n, boolean isFinish, String Type, ArrayList<ModelParentChildList> modelParentChildLists){
        switch(n){
            case 1:
                startActivity(new Intent(this, ParentAddChildActivity.class));
                break;
            case 2:
                Intent intent = new Intent(this, ParentAddSponsorOrMentorActivity.class);
                intent.putExtra("Type", Type);
                intent.putExtra("ParentChildLists", modelParentChildLists);
                startActivity(intent);
                break;
        }
        if(isFinish) finish();
    }

    public void onNextFragment(int position){
        CURRENT_PAGE = position;
        FullViewPager.setCurrentItem(position, false);
    }

    public void onNextFragment(){
        CURRENT_PAGE++;
        FullViewPager.setCurrentItem(CURRENT_PAGE);
    }

    public void onShowChatFragment(ModelParentData m){
//        ModelChat mChat = new ModelChat();
//        mChat.setType(m.getModelType());
//        mChat.setChatId(m.getPrivateChatData().getChatId());
//        mChat.setUserId(m.getPrivateChatData().getTargetId());
//        mChat.setFirstName(m.getPrivateChatData().getTargetFirstName());
//        mChat.setLastName(m.getPrivateChatData().getTargetLastName());
//        mChat.setAvatar(m.getPrivateChatData().getTargetAvatar());
//        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
//        ft.replace(Container2Id, ParentChatFragment.newInstance(mChat));
//        ft.commit();
//        onContainerIsVisible(false);
    }

    public void onShowUsersListFragment(int type, ModelParentData m){
//        ModelChat mChat = new ModelChat();
//        mChat.setType(m.getModelType());
//        if(m.getModelType().equals("MentorList")){
//            if(type == 0){
//                mChat.setChatId(m.getMentorData().getChatId());
//                mChat.setChildId(m.getChildId());
//                mChat.setUserId(m.getMentorData().getMentorId());
//                mChat.setFirstName(m.getMentorData().getMentorFirstName());
//                mChat.setLastName(m.getMentorData().getMentorLastName());
//                mChat.setAvatar(m.getMentorData().getMentorAvatar());
//                mChat.setGroupId(m.getMentorData().getGroupId());
//                mChat.setGroupName(m.getMentorData().getGroupName());
//            }
//            else{
//                BSDGrading bsdGrading = new BSDGrading(this, "", "Mentor");
//                bsdGrading.show();
//                bsdGrading.setOnItemClickListener(new BSDGrading.OnItemClickListener(){
//                    @Override
//                    public void onGradeClick(int type, String grade){
//                        ParentSendGradeInsteadOfMentor(ParentActivity.this, PARENT_ID, m.getChildId(), m.getMentorData().getGroupId(),
//                                m.getMentorData().getLessonId(), type, grade, m.getMentorData().getChatId());
//                        bsdGrading.dismiss();
//                    }
//                    @Override
//                    public void onChatClick(){
//
//                    }
//                });
//                return;
//            }
//        }
//        else if(m.getModelType().equals("LessonsList")){
//            if(type == 0){
//                mChat.setChatId(m.getParentLessonData().getChatId());
//                mChat.setUserId(m.getParentLessonData().getChildId());
//                mChat.setAvatar(m.getParentLessonData().getChildAvatar());
//                mChat.setFirstName(m.getParentLessonData().getChildFirstName());
//                mChat.setLastName(m.getParentLessonData().getChildLastName());
//            }
//            else{
//                BSDGrading bsdGrading = new BSDGrading(this, m.getParentLessonData().getChildFirstName() + " " + m.getParentLessonData().getChildLastName(), "Parent");
//                bsdGrading.show();
//                bsdGrading.setOnItemClickListener(new BSDGrading.OnItemClickListener(){
//                    @Override
//                    public void onGradeClick(int type, String grade){
//                        _Web.ParentSendGrade(ParentActivity.this, PARENT_ID, m.getChildId(), m.getParentLessonData().getLessonId(),
//                                grade, m.getParentLessonData().getChatId());
//                        bsdGrading.dismiss();
//                    }
//                    @Override
//                    public void onChatClick(){
//
//                    }
//                });
//                return;
//            }
//        }
//        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
//        ft.replace(Container2Id, ParentChatFragment.newInstance(mChat));
//        ft.commit();
//        onContainerIsVisible(false);
    }

    public void onShowFragment(int frag, ModelChat mChat){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch(frag){
            case 1:
                //ft.replace(Container2Id, ParentChatFragment.newInstance(mChat));
                ft.replace(Container2Id, ParentGradesFragment.newInstance(mChat));
                break;
            case 2:
                ft.replace(Container2Id, new ParentEditProfileFragment()).addToBackStack("tag");
                break;
        }
        ft.commit();
        onContainerIsVisible(false);
    }

    public void onShowFragmentParentAddNewLesson(String childId){
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(Container2Id, FragmentParentAddNewLesson.newInstance(childId));
//        ft.commit();
//        onContainerIsVisible(false);
    }

    public void onShowParentAddParenFragment(){
//        onNavigationClick();
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(Container2Id, ParentAddParentFragment.newInstance());
//        ft.commit();
//        onContainerIsVisible(false);
    }

    public void onHideParentAddParenFragment(){
        for(Fragment fragment : getSupportFragmentManager().getFragments())
            if(fragment instanceof ParentAddParentFragment)
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        onContainerIsVisible(true);
    }

    public void onHideFragmentParentAddNewLesson(){
        for(Fragment fragment : getSupportFragmentManager().getFragments())
            if(fragment instanceof FragmentParentAddNewLesson)
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        onContainerIsVisible(true);
    }

    public void onRemoveFragment(int flag){
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            switch(flag){
                case 1:
                    break;
                case 2:
                    if(fragment instanceof InteractionToolFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    break;
                case 3:
                    if(fragment instanceof FragmentParentAddNewLesson)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    break;
                case 4:
                    if(fragment instanceof ParentChatListFragment || fragment instanceof ParentChatFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    break;
                case 5:
                    if(fragment instanceof ParentAddSchoolFragment || fragment instanceof ParentAddSchoolProfileFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    break;
                case 6:
                    if(fragment instanceof ParentAddLessonFragment || fragment instanceof ParentMentorListFragment
                            || fragment instanceof ParentAddLessonMentorProfileFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    break;
                case 7:
                    if(fragment instanceof ParentAllSmartGradesFragment || fragment instanceof ParentChildLessonInfoFragment
                            || fragment instanceof ParentChatFragment || fragment instanceof ParentAddMentorProfileFragment
                            || fragment instanceof ParentAddMentorListFragment || fragment instanceof ParentSchoolProfile)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    break;
                case 8:
                    if(fragment instanceof ParentAllOtherLessonFragment || fragment instanceof ParentChildLessonInfoFragment
                            || fragment instanceof ParentChatFragment || fragment instanceof ParentAddMentorProfileFragment
                            || fragment instanceof ParentAddMentorListFragment || fragment instanceof ParentSchoolProfile)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    break;
                case 9:
                    if(fragment instanceof ParentAllTransactionFragment){
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    }
                    break;
                case 10:
                    if(fragment instanceof ParentAddChildListFragment || fragment instanceof ParentChildProfileFragment){
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    }
                    break;
                case 11:
                    if(fragment instanceof ParentAddParentListFragment || fragment instanceof ParentParentProfileFragment){
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    }
                    break;
            }
        }
        parentShowMoreIncomeLessonsAdapter = null;
        parentShowIncomeLessonsFromActivityAdapter = null;
        parentShowMoreOtherLessonsAdapter = null;
        parentShowOtherLessonsFromActivityAdapter = null;
        onContainerIsVisible(true);
    }

    public void onRemoveFragment(){
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            if(fragment instanceof ChildEditProfileFragment
                    || fragment instanceof ParentCashFragment
                    || fragment instanceof ParentCashExtractFragment
                    || fragment instanceof ParentCashReplenishFragment
                    || fragment instanceof ParentSponsoringInfoFragment)
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.civAvatar:
                onMenu(v);
                break;
            case R.id.ivNav:
                onNavigationClick();
                break;
            case R.id.ivSearch:
                //onNextActivity(2, false);
                break;
            case R.id.flMyFamily:
                onMyFamilyClick();
                break;

            case R.id.civAddContact:
                showButton();
                break;
            case R.id.civSendMoney:
                onSendMoney();
                break;
            case R.id.flChat:
                onChatClick();
                break;
            case R.id.llAddChild:
                showButton();
                onAddChild();
                break;
//            case R.id.llAddMentor:
//                if(AddMentorEnable){
//                    showButton();
//                    onNextActivity(2, false, S.MENTOR, mParentChildLists);
//                }
//                else alert.onToast("?????????????? ?????????? ???????????????? ??????????????.");
//                break;
//            case R.id.llSearchSponsor:
//                if(AddSponsorEnable){
//                    showButton();
//                    onNextActivity(2, false, S.SPONSOR, mParentChildLists);
//                }
//                else alert.onToast("?????????????? ?????????? ???????????????? ??????????????.");
//                break;
//            case R.id.llAddNewLesson:
//                if(AddLessonEnable){
//                    showButton();
//                    onAddNewLesson();
//                }
//                else alert.onToast("?????????????? ?????????? ???????????????? ??????????????.");
//                break;
//            case R.id.llAddSchool:
//                if(AddSchoolEnable){
//                    showButton();
//                    onAddSchool();
//                }
//                else alert.onToast("?????????????? ?????????? ???????????????? ??????????????.");
//                break;

            case R.id.tvSponsorshipArrowLeft:
                SELECT_CHILD("left");
                break;
            case R.id.tvSponsorshipArrowRight:
                SELECT_CHILD("right");
                break;
            case R.id.civSponsorshipChildAvatar:
                showChat();
                break;
            case R.id.clSponsorData:
                onSponsorData();
                break;
            case R.id.ivExtraMenu:
                onExtraMenu();
                break;

            case R.id.sNavPushTurnOffOrOn:
                onNavPushClick();
                break;
            case R.id.tvNavQuickAccessCode:
                onNavQuickAccessCodeClick();
                break;
            case R.id.tvNavPassword:
                onNavPasswordClick();
                break;
            case R.id.tvNavAppAbout:
                onNavAppAboutClick();
                break;
            case R.id.tvNavInviteFriends:
                onNavInviteFriendsClick();
                break;
            case R.id.tvNavSupport:
                onNavSupportClick();
                break;
            case R.id.ivNavEdit:
                onNavEditClick();
                break;
            case R.id.flCashExtract:
                onNavCashExtract();
                break;
            case R.id.cvSearchChildAccount:
                onAddChild();
                break;
            case R.id.cvCreateChildAccount:
                onCreateChild();
                break;
            case R.id.btnCreateIncomeSource:
                onAddNewLesson(SELECT_CHILD_INDEX);
                break;
            case R.id.cvDatePeriod:
                ///
                break;
            case R.id.cvAddLessonFAB:
                onAddNewLesson(SELECT_CHILD_INDEX);
                break;
            case R.id.cvShowMoreIncomeLessons:
                onShowMoreIncomeLessons(SELECT_CHILD_INDEX);
                break;
            case R.id.cvShowMoreOtherLessons:
                onShowMoreOtherLessons(SELECT_CHILD_INDEX);
                break;
            case R.id.tvFamilyGroupInfo:
                onShowFamilyGroupInfo();
                break;
            case R.id.cvEnrollFamilyGroup:
                onEnrollFamilyGroup();
                break;
            case R.id.cvAddSchool:
                onAddSchool();
                break;
            case R.id.clSponsorInfo:
            case R.id.btnLesson1:
            case R.id.btnLesson2:
            case R.id.btnLesson3:
            case R.id.btnLesson4:
                onSponsorInfo();
                break;
        }
    }

    private void onEnrollFamilyGroup(){
        setFabGone();
        FRAGMENT_ID = 11;
        parentAddParentFragmentAdapter = new ParentAddParentFragmentAdapter(getSupportFragmentManager());

        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(parentAddParentFragmentAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(parentAddParentFragmentAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);
    }

    private void onShowFamilyGroupInfo(){
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        PWFamilyGroupInfo popupWindow = new PWFamilyGroupInfo(width, height, this);
        popupWindow.setFocusable(true);
        //popupWindow.showAsDropDown(tvFamilyGroupInfo);
        popupWindow.showAtLocation(tvFamilyGroupInfo, Gravity.CENTER, 0, 0);
    }

    private void onMenu(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("UserId", PARENT_ID);

        String SOAP = SoapRequest(func_GetUser, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = _Web.XMLReader(response.body().string());
                    ArrayList<ModelUserData> userList = new Gson().fromJson(result, new TypeToken<ArrayList<ModelUserData>>(){
                    }.getType());
                    if(!listIsNullOrEmpty(userList)){
                        for(int i = 0; i < userList.size(); i++){
                            popupMenu.getMenu().add(userList.get(i).getLogin()).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
                                @Override
                                public boolean onMenuItemClick(MenuItem item){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ParentActivity.this);
                                    View viewAlert = getLayoutInflater().inflate(R.layout.ad_access_code, null);
                                    builder.setView(viewAlert);
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                    ((TextView) viewAlert.findViewById(R.id.tvText)).setText(getResources().getString(R.string.accept_change_acc));
                                    viewAlert.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View v){
                                            alertDialog.dismiss();
                                        }
                                    });
                                    viewAlert.findViewById(R.id.tvOkay).setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View v){
                                            alertDialog.dismiss();
                                            for(int i = 0; i < userList.size(); i++)
                                                if(userList.get(i).getLogin().equals(item.toString())){
                                                    new DAceessCode(ParentActivity.this, login.loadUserDate(LoginKey.TYPE), userList.get(i));
                                                    break;
                                                }

                                        }
                                    });
                                    alertDialog.show();
                                    return false;
                                }
                            });
                        }
                    }
                    runOnUiThread(new Runnable(){
                        public void run(){
                            popupMenu.getMenu().add("??????????").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem){
                                    login.deleteUserDate();
                                    startActivity(new Intent(ParentActivity.this, AuthActivity.class));
                                    finish();
                                    return true;
                                }
                            });
                            popupMenu.show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        if(parentShowMoreIncomeLessonsAdapter != null && CURRENT_PAGE == 3) CURRENT_PAGE--;
        else if (parentShowIncomeLessonsFromActivityAdapter != null && CURRENT_PAGE == 2) CURRENT_PAGE--;
        else if (parentShowMoreOtherLessonsAdapter != null && CURRENT_PAGE == 3) CURRENT_PAGE--;
        else if (parentShowOtherLessonsFromActivityAdapter != null && CURRENT_PAGE == 2) CURRENT_PAGE--;

        if(parentShowMoreIncomeLessonsAdapter != null && CURRENT_PAGE == 5) CURRENT_PAGE -= 3;
        else if (parentShowIncomeLessonsFromActivityAdapter != null && CURRENT_PAGE == 4) CURRENT_PAGE -= 3;
        else if (parentShowMoreOtherLessonsAdapter != null && CURRENT_PAGE == 5) CURRENT_PAGE -= 3;
        else if (parentShowOtherLessonsFromActivityAdapter != null && CURRENT_PAGE == 4) CURRENT_PAGE -= 3;

        CURRENT_PAGE--;
        if(CURRENT_PAGE == -1){
            onRemoveFragment(FRAGMENT_ID);
            if(mParentData.getFamilyGroupId() != null &&
                    (mParentData.ChildrenList.get(SELECT_CHILD_INDEX).getLessonsWithSmartGrades() != null
                            || mParentData.ChildrenList.get(SELECT_CHILD_INDEX).getLessonsWithoutSmartGrades() != null)){
                setFabVisible();
            }
        }
        else if(CURRENT_PAGE >= 0) FullViewPager.setCurrentItem(CURRENT_PAGE, false);
        else if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
            onContainerIsVisible(true);
        }
        else super.onBackPressed();
    }

    private void onNavigationClick(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            drawer.openDrawer(GravityCompat.START);
        }
    }

    public void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    private void onNavEditClick(){
        onShowFragment(2, null);
        drawer.closeDrawer(GravityCompat.START);
    }
    private void onNavPushClick(){
        boolean b = sNavPushTurnOffOrOn.isChecked();
        if(b){
            tvNavPushTurnOffOrOn.setText("??????.");
        }
        else{
            tvNavPushTurnOffOrOn.setText("????????.");
        }
        SharedPreferences sp = getApplicationContext().getSharedPreferences("SP_SETTINGS_MENTOR_NAV", 0);
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean("PUSH", b);
        spe.apply();
    }
    private void onNavQuickAccessCodeClick(){
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.fragment_access_enter, null, false);
        dialog.setContentView(view);
        View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        bottomSheet.setLayoutParams(layoutParams);
        layoutParams.height = (_System.getWindowHeight(this) * 90 / 100);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        dialog.show();

        ImageView[] ivDot = new ImageView[4];
        ivDot[0] = view.findViewById(R.id.ivDot1);
        ivDot[1] = view.findViewById(R.id.ivDot2);
        ivDot[2] = view.findViewById(R.id.ivDot3);
        ivDot[3] = view.findViewById(R.id.ivDot4);
        TextView tvTitle = view.findViewById(R.id.tvAccessTitle);

        TextView tvAccessTitle = view.findViewById(R.id.tvAccessTitle);
        tvAccessTitle.setText("?????????????? ???????????? ?????? ??????????????");

        view.findViewById(R.id.tvNum1).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ParentActivity.this, "1", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum2).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ParentActivity.this, "2", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum3).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ParentActivity.this, "3", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum4).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ParentActivity.this, "4", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum5).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ParentActivity.this, "5", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum6).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ParentActivity.this, "6", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum7).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ParentActivity.this, "7", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum8).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ParentActivity.this, "8", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum9).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ParentActivity.this, "9", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum0).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ParentActivity.this, "0", dialog, ivDot, tvTitle);
            }
        });
    }
    private void onNavPasswordClick(){
        View view = getLayoutInflater().inflate(R.layout.dialog_nav_edit_access, null, false);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        bottomSheet.setLayoutParams(layoutParams);
        layoutParams.height = (_System.getWindowHeight(this) * 90 / 100);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        dialog.show();

        EditText etOldPass = view.findViewById(R.id.etOldPass);
        EditText etNewPass = view.findViewById(R.id.etNewPass);
        EditText etNewPass2 = view.findViewById(R.id.etNewPass2);
        ImageView ivBack = view.findViewById(R.id.ivBack);
        Button btnEdit = view.findViewById(R.id.btnEdit);

        ivBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dialog.dismiss();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!etNewPass.getText().toString().equals(etNewPass2.getText().toString())){
                    alert.onToast("????????????: ???????????? ???? ??????????????????");
                    return;
                }
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, login.loadUserDate(LoginKey.ID));
                jsonData.addProperty("OldP", etOldPass.getText().toString());
                jsonData.addProperty("NewP", etNewPass.getText().toString());

                String SOAP = SoapRequest(func_UserEditPassword, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback(){
                    @Override
                    public void onFailure(final Call call, IOException e){
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException{
                        if(response.code() >= 200 && response.code() <= 300){
                            String xml = response.body().string();
                            String result = _Web.XMLReader(xml);
                            runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    if(result.equals("0"))
                                        alert.onToast("????????????: ???????????????? ????????????!");
                                    else if(result.equals("1")){
                                        dialog.dismiss();
                                        alert.onToast("???????????? ?????????????? ??????????????.");
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
    private void onNavAppAboutClick(){
        View view = getLayoutInflater().inflate(R.layout.alert_dialog_app_about, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).create();
        alertDialog.show();
    }
    private void onNavInviteFriendsClick(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "?????????????? ?????????????? ???????????????????? Smart Grades ?????? ?????????????????????????? ???????????????? ?? PlayMarket");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getString(R.string.call_friends)));
    }
    private void onNavSupportClick(){
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_support, null, false);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
        /*BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        bottomSheet.setLayoutParams(layoutParams);
        layoutParams.height = (_System.getWindowHeight(ChildActivity.this) * 30 / 100);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);*/
        dialog.show();

        ImageView ivPhone = view.findViewById(R.id.ivPhone);
        ImageView ivEmail = view.findViewById(R.id.ivEmail);
        ImageView ivWhatsApp = view.findViewById(R.id.ivWhatsApp);
        ImageView ivTelegram = view.findViewById(R.id.ivTelegram);

        ivPhone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.dismiss();
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+77004040007")));
            }
        });
        ivEmail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.dismiss();
                //onShowDialogSupport("Email");
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"esparta.tech@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "?????????? ????????????");
                email.putExtra(Intent.EXTRA_TEXT, "");
//need this to prompts email client only
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "???????????????? ???????????? Email"));
            }
        });
        ivWhatsApp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.dismiss();
                //onShowDialogSupport("WhatsApp");
                String url = "https://wa.me/+77004040007";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        });
        ivTelegram.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.dismiss();
                String url = "https://telegram.im/@zhaslan27";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        });
    }
    public void onNavCashExtract(){
        FRAGMENT_ID = 1;
        parentCashFragment = ParentCashFragment.newInstance(PrivateAccount);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, parentCashFragment).commit();
        onNavigationClick();
    }

    private void onMyFamilyClick(){
        ParentDialogMyFamily dialog = new ParentDialogMyFamily(ParentActivity.this, mParentData);
        dialog.show();
    }

    private void SELECT_CHILD(String Click){
//        if(mParentData.getChildrenList().size() == 0) return;
//
//        if(Click == null && SELECT_CHILD_INDEX == -1) SELECT_CHILD_INDEX = 0;
//        else if(Click != null && Click.equals("right")) SELECT_CHILD_INDEX += 1;
//        else if(Click != null && Click.equals("left")) SELECT_CHILD_INDEX -= 1;
//        else{
//            ModelParentChildList mChild = mParentData.getChildrenList().get(SELECT_CHILD_INDEX);
//            UserListAdapter.selectData(mChild.getChildId());
//            return;
//        }
//
//        if(SELECT_CHILD_INDEX > mParentData.getChildrenList().size() - 1) SELECT_CHILD_INDEX = 0;
//        else if(SELECT_CHILD_INDEX < 0)
//            SELECT_CHILD_INDEX = mParentData.getChildrenList().size() - 1;
//
//        ModelParentChildList mChild = mParentData.getChildrenList().get(SELECT_CHILD_INDEX);
//        clChildInfo.setVisibility(View.VISIBLE);
//
//        if(mParentData.getChildrenList().size() == 2){
//            int child_id = SELECT_CHILD_INDEX;
//            String avatar = mParentData.getChildrenList().get(child_id).getChildAvatar();
//            if(!stringIsNullOrEmpty(avatar))
//                Picasso.get().load(avatar).fit().centerInside().into(civSponsorshipChildAvatar1);
//            else
//                civSponsorshipChildAvatar1.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
//            if(mParentData.getChildrenList().get(child_id).getMentorList() != null){
//                int NoCheckCount = 0;
//                for(int i = 0; i < mParentData.getChildrenList().get(child_id).getMentorList().size(); i++){
//                    ModelChildMentorList MentorData = mParentData.getChildrenList().get(child_id).getMentorList().get(i);
//                    NoCheckCount += MentorData.getNoCheckCount();
//                }
//                if(NoCheckCount > 0) ivNotification1.setVisibility(View.VISIBLE);
//                else ivNotification1.setVisibility(View.GONE);
//            }
//            else ivNotification1.setVisibility(View.GONE);
//        }
//        else if(mParentData.getChildrenList().size() == 3){
//            int child_id = SELECT_CHILD_INDEX - 1;
//            if(child_id < 0) child_id = mParentData.getChildrenList().size() + child_id;
//            String avatar = mParentData.getChildrenList().get(child_id).getChildAvatar();
//            if(!stringIsNullOrEmpty(avatar))
//                Picasso.get().load(avatar).fit().centerInside().into(civSponsorshipChildAvatar1);
//            else
//                civSponsorshipChildAvatar1.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
//            if(mParentData.getChildrenList().get(child_id).getMentorList() != null){
//                int NoCheckCount = 0;
//                for(int i = 0; i < mParentData.getChildrenList().get(child_id).getMentorList().size(); i++){
//                    ModelChildMentorList MentorData = mParentData.getChildrenList().get(child_id).getMentorList().get(i);
//                    NoCheckCount += MentorData.getNoCheckCount();
//                }
//                if(NoCheckCount > 0) ivNotification1.setVisibility(View.VISIBLE);
//                else ivNotification1.setVisibility(View.GONE);
//            }
//            else ivNotification1.setVisibility(View.GONE);
//
//            child_id = SELECT_CHILD_INDEX + 1;
//            if(child_id > mParentData.getChildrenList().size() - 1) child_id = 0;
//            avatar = mParentData.getChildrenList().get(child_id).getChildAvatar();
//            if(avatar != null && !avatar.isEmpty())
//                Picasso.get().load(avatar).fit().centerInside().into(civSponsorshipChildAvatar2);
//            else
//                civSponsorshipChildAvatar2.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
//            if(mParentData.getChildrenList().get(child_id).getMentorList() != null){
//                int NoCheckCount = 0;
//                for(int i = 0; i < mParentData.getChildrenList().get(child_id).getMentorList().size(); i++){
//                    ModelChildMentorList MentorData = mParentData.getChildrenList().get(child_id).getMentorList().get(i);
//                    NoCheckCount += MentorData.getNoCheckCount();
//                }
//                if(NoCheckCount > 0) ivNotification2.setVisibility(View.VISIBLE);
//                else ivNotification2.setVisibility(View.GONE);
//            }
//            else ivNotification2.setVisibility(View.GONE);
//        }
//        else if(mParentData.getChildrenList().size() == 4){
//            int child_id = SELECT_CHILD_INDEX - 2;
//            if(child_id < 0) child_id = mParentData.getChildrenList().size() + child_id;
//            String avatar = mParentData.getChildrenList().get(child_id).getChildAvatar();
//            if(!stringIsNullOrEmpty(avatar))
//                Picasso.get().load(avatar).fit().centerInside().into(civSponsorshipChildAvatar0);
//            else
//                civSponsorshipChildAvatar0.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
//            if(mParentData.getChildrenList().get(child_id).getMentorList() != null){
//                int NoCheckCount = 0;
//                for(int i = 0; i < mParentData.getChildrenList().get(child_id).getMentorList().size(); i++){
//                    ModelChildMentorList MentorData = mParentData.getChildrenList().get(child_id).getMentorList().get(i);
//                    NoCheckCount += MentorData.getNoCheckCount();
//                }
//                if(NoCheckCount > 0) ivNotification0.setVisibility(View.VISIBLE);
//                else ivNotification0.setVisibility(View.GONE);
//            }
//            else ivNotification0.setVisibility(View.GONE);
//
//            child_id = SELECT_CHILD_INDEX - 1;
//            if(child_id < 0) child_id = mParentData.getChildrenList().size() + child_id;
//            avatar = mParentData.getChildrenList().get(child_id).getChildAvatar();
//            if(!stringIsNullOrEmpty(avatar))
//                Picasso.get().load(avatar).fit().centerInside().into(civSponsorshipChildAvatar1);
//            else
//                civSponsorshipChildAvatar1.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
//            if(mParentData.getChildrenList().get(child_id).getMentorList() != null){
//                int NoCheckCount = 0;
//                for(int i = 0; i < mParentData.getChildrenList().get(child_id).getMentorList().size(); i++){
//                    ModelChildMentorList MentorData = mParentData.getChildrenList().get(child_id).getMentorList().get(i);
//                    NoCheckCount += MentorData.getNoCheckCount();
//                }
//                if(NoCheckCount > 0) ivNotification1.setVisibility(View.VISIBLE);
//                else ivNotification1.setVisibility(View.GONE);
//            }
//            else ivNotification1.setVisibility(View.GONE);
//
//            child_id = SELECT_CHILD_INDEX + 1;
//            if(child_id > mParentData.getChildrenList().size() - 1) child_id = 0;
//            avatar = mParentData.getChildrenList().get(child_id).getChildAvatar();
//            if(!stringIsNullOrEmpty(avatar))
//                Picasso.get().load(avatar).fit().centerInside().into(civSponsorshipChildAvatar2);
//            else
//                civSponsorshipChildAvatar2.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
//            if(mParentData.getChildrenList().get(child_id).getMentorList() != null){
//                int NoCheckCount = 0;
//                for(int i = 0; i < mParentData.getChildrenList().get(child_id).getMentorList().size(); i++){
//                    ModelChildMentorList MentorData = mParentData.getChildrenList().get(child_id).getMentorList().get(i);
//                    NoCheckCount += MentorData.getNoCheckCount();
//                }
//                if(NoCheckCount > 0) ivNotification2.setVisibility(View.VISIBLE);
//                else ivNotification2.setVisibility(View.GONE);
//            }
//            else ivNotification2.setVisibility(View.GONE);
//        }
//        else if(mParentData.getChildrenList().size() == 5){
//            int child_id = SELECT_CHILD_INDEX - 2;
//            if(child_id < 0) child_id = mParentData.getChildrenList().size() + child_id;
//            String avatar = mParentData.getChildrenList().get(child_id).getChildAvatar();
//            if(!stringIsNullOrEmpty(avatar))
//                Picasso.get().load(avatar).fit().centerInside().into(civSponsorshipChildAvatar0);
//            else
//                civSponsorshipChildAvatar0.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
//            if(mParentData.getChildrenList().get(child_id).getMentorList() != null){
//                int NoCheckCount = 0;
//                for(int i = 0; i < mParentData.getChildrenList().get(child_id).getMentorList().size(); i++){
//                    ModelChildMentorList MentorData = mParentData.getChildrenList().get(child_id).getMentorList().get(i);
//                    NoCheckCount += MentorData.getNoCheckCount();
//                }
//                if(NoCheckCount > 0) ivNotification0.setVisibility(View.VISIBLE);
//                else ivNotification0.setVisibility(View.GONE);
//            }
//            else ivNotification0.setVisibility(View.GONE);
//
//            child_id = SELECT_CHILD_INDEX - 1;
//            if(child_id < 0) child_id = mParentData.getChildrenList().size() + child_id;
//            avatar = mParentData.getChildrenList().get(child_id).getChildAvatar();
//            if(!stringIsNullOrEmpty(avatar))
//                Picasso.get().load(avatar).fit().centerInside().into(civSponsorshipChildAvatar1);
//            else
//                civSponsorshipChildAvatar1.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
//            if(mParentData.getChildrenList().get(child_id).getMentorList() != null){
//                int NoCheckCount = 0;
//                for(int i = 0; i < mParentData.getChildrenList().get(child_id).getMentorList().size(); i++){
//                    ModelChildMentorList MentorData = mParentData.getChildrenList().get(child_id).getMentorList().get(i);
//                    NoCheckCount += MentorData.getNoCheckCount();
//                }
//                if(NoCheckCount > 0) ivNotification1.setVisibility(View.VISIBLE);
//                else ivNotification1.setVisibility(View.GONE);
//            }
//            else ivNotification1.setVisibility(View.GONE);
//
//            child_id = SELECT_CHILD_INDEX + 1;
//            if(child_id > mParentData.getChildrenList().size() - 1)
//                child_id = child_id - mParentData.getChildrenList().size();
//            avatar = mParentData.getChildrenList().get(child_id).getChildAvatar();
//            if(avatar != null && !avatar.isEmpty())
//                Picasso.get().load(avatar).fit().centerInside().into(civSponsorshipChildAvatar2);
//            else
//                civSponsorshipChildAvatar2.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
//            if(mParentData.getChildrenList().get(child_id).getMentorList() != null){
//                int NoCheckCount = 0;
//                for(int i = 0; i < mParentData.getChildrenList().get(child_id).getMentorList().size(); i++){
//                    ModelChildMentorList MentorData = mParentData.getChildrenList().get(child_id).getMentorList().get(i);
//                    NoCheckCount += MentorData.getNoCheckCount();
//                }
//                if(NoCheckCount > 0) ivNotification2.setVisibility(View.VISIBLE);
//                else ivNotification2.setVisibility(View.GONE);
//            }
//            else ivNotification2.setVisibility(View.GONE);
//
//            child_id = SELECT_CHILD_INDEX + 2;
//            if(child_id > mParentData.getChildrenList().size() - 1)
//                child_id = child_id - mParentData.getChildrenList().size();
//            avatar = mParentData.getChildrenList().get(child_id).getChildAvatar();
//            if(avatar != null && !avatar.isEmpty())
//                Picasso.get().load(avatar).fit().centerInside().into(civSponsorshipChildAvatar3);
//            else
//                civSponsorshipChildAvatar3.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
//            if(mParentData.getChildrenList().get(child_id).getMentorList() != null){
//                int NoCheckCount = 0;
//                for(int i = 0; i < mParentData.getChildrenList().get(child_id).getMentorList().size(); i++){
//                    ModelChildMentorList MentorData = mParentData.getChildrenList().get(child_id).getMentorList().get(i);
//                    NoCheckCount += MentorData.getNoCheckCount();
//                }
//                if(NoCheckCount > 0) ivNotification3.setVisibility(View.VISIBLE);
//                else ivNotification3.setVisibility(View.GONE);
//            }
//            else ivNotification3.setVisibility(View.GONE);
//        }
//
//        String avatar = mChild.getChildAvatar();
//        if(!stringIsNullOrEmpty(avatar))
//            Picasso.get().load(avatar).fit().centerInside().into(civSponsorshipChildAvatar);
//        else
//            civSponsorshipChildAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
//
//        //?????????????? ?????? ?? ??????????
//        if(!stringIsNullOrEmpty(mChild.getAverageGrade())){
//            tvSponsorshipAverageGrade.setText(mChild.getAverageGrade());
//            double averageGrade = Convert.Str2Double(mChild.getAverageGrade());
//            if(averageGrade >= 2 && averageGrade < 3)
//                tvSponsorshipAverageGrade.setBackground(getResources().getDrawable(R.drawable.img_oval_red));
//            else if(averageGrade >= 3 && averageGrade < 4)
//                tvSponsorshipAverageGrade.setBackground(getResources().getDrawable(R.drawable.img_oval_yellow));
//            else if(averageGrade >= 4 && averageGrade < 5)
//                tvSponsorshipAverageGrade.setBackground(getResources().getDrawable(R.drawable.img_oval_green));
//            else if(averageGrade == 5)
//                tvSponsorshipAverageGrade.setBackground(getResources().getDrawable(R.drawable.img_oval_purple));
//        }
//        else{
//            tvSponsorshipAverageGrade.setText("-");
//            tvSponsorshipAverageGrade.setBackground(getResources().getDrawable(R.drawable.background_oval_gray));
//        }
//
//        //??????-???? ?????????? ?? ??????????
//        if(!stringIsNullOrEmpty(mChild.getCurentCash()))
//            tvSponsorshipSum.setText(mChild.getCurentCash() + "\nKZT");
//        else tvSponsorshipSum.setText("0\nKZT");
//
//        //???????????????????? ???? ??????????????????????
//        ModelChildSponsorData mSponsorData = mChild.getSponsorData();
//        if(mSponsorData != null){
//            avatar = mSponsorData.getSponsorAvatar();
//            if(!stringIsNullOrEmpty(avatar))
//                Picasso.get().load(avatar).fit().centerInside().into(civSponsorAvatar);
//            else
//                civSponsorAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
//
//            if(mSponsorData.getDaysLeft() <= 2) tvDaysLeft.setTextColor(Color.RED);
//            tvDaysLeft.setText(String.valueOf(mSponsorData.getDaysLeft()));
//            if(mSponsorData.getGradesLeft() <= 2) tvGradesLeft.setTextColor(Color.RED);
//            tvGradesLeft.setText(String.valueOf(mSponsorData.getGradesLeft()));
//            tvThresholdGrade.setText(mSponsorData.getThresholdGrade());
//            tvAverageGrade.setText(mSponsorData.getAverageGrade());
//
//            if(!stringIsNullOrEmpty(mSponsorData.getAverageGrade())){
//                double averageGrade = Convert.Str2Double(mSponsorData.getAverageGrade());
//                if(averageGrade < Convert.Str2Double(mSponsorData.getThresholdGrade()))
//                    tvAverageGrade.setBackgroundResource(R.drawable.background_oval_red);
//                else if(averageGrade == Convert.Str2Double(mSponsorData.getThresholdGrade()))
//                    tvAverageGrade.setBackgroundResource(R.drawable.background_oval_yellow);
//                else if(averageGrade > Convert.Str2Double(mSponsorData.getThresholdGrade()))
//                    tvAverageGrade.setBackgroundResource(R.drawable.background_oval_green);
//            }
//            else{
//                tvAverageGrade.setText("-");
//                tvAverageGrade.setBackgroundResource(R.drawable.background_oval_gray);
//            }
//            clSponsorData.setVisibility(View.VISIBLE);
//
//            if(!listIsNullOrEmpty(mSponsorData.getSponsored())){
//                for(int i = 0; i < mSponsorData.getSponsored().size(); i++){
//                    ModelSponsored m = mSponsorData.getSponsored().get(i);
//                    if(m.isDone()){
//                        if(m.isCondAreMet())
//                            ivStateWeek[i].setImageDrawable(getResources().getDrawable(R.drawable.background_oval_green));
//                        else
//                            ivStateWeek[i].setImageDrawable(getResources().getDrawable(R.drawable.background_oval_red));
//                    }
//                    else{
//                        ivStateWeek[i].setImageDrawable(getResources().getDrawable(R.drawable.animation_current_week));
//                        //ivStateWeek[i].setBackgroundResource(R.drawable.animation_current_week);
//                        AnimationDrawable animationDrawable = (AnimationDrawable) ivStateWeek[i].getDrawable();
//                        animationDrawable.start();
//                    }
//                }
//            }
//        }
//        else clSponsorData.setVisibility(View.GONE);
//
//        if(!listIsNullOrEmpty(mChild.getSponsoredNotifications())){
//            for(int i = 0; i < mChild.getSponsoredNotifications().size(); i++){
//                ModelSponsoredNotification m = mChild.getSponsoredNotifications().get(i);
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                View viewAlert = getLayoutInflater().inflate(R.layout.ad_sponsor_period_the_end, null);
//                builder.setView(viewAlert);
//                AlertDialog alertDialog = builder.create();
//                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                CircleImageView civAvatar = viewAlert.findViewById(R.id.civAvatar);
//                TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
//                ImageView imageView2 = viewAlert.findViewById(R.id.imageView2);
//                Button btnOkey = viewAlert.findViewById(R.id.btnOkey);
//
//                avatar = m.getAvatar();
//                if(!stringIsNullOrEmpty(avatar))
//                    Picasso.get().load(avatar).fit().centerInside().into(civAvatar);
//                else
//                    civAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
//
//                if(!m.isCondAreMet()){
//                    tvTitle.setText("?? ??????????????????,\n?????????????? ???? ?????????????????????? ???? " + m.getWeek() + " ???????????? ???? ??????????????????.");
//                    tvTitle.setBackgroundResource(R.drawable.background_grade_red);
//                    tvTitle.setPadding(10, 10, 10, 10);
//                    imageView2.setImageDrawable(getResources().getDrawable(R.drawable.img_sponsorship_not_done));
//                    btnOkey.setText("???????????? ??????????????????, ????????????????????.");
//                    btnOkey.setPadding(10, 10, 10, 10);
//                }
//                else{
//                    tvTitle.setText("??????????????????????!\n?????????????? ???? " + m.getWeek() + " ???????????? ???? ?????????????????????? ??????????????????.");
//                }
//                alertDialog.show();
//
//                btnOkey.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View view){
//                        JsonObject jsonData = new JsonObject();
//                        jsonData.addProperty("Index", m.getIndex());
//                        jsonData.addProperty("UserType", login.loadUserDate(LoginKey.TYPE));
//
//                        String SOAP = SoapRequest(func_UpdateCheckedSponsored, jsonData.toString());
//                        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
//                        Request request = new Request.Builder().url(URL).post(body).build();
//                        HttpClient.newCall(request).enqueue(new Callback(){
//                            @Override
//                            public void onFailure(final Call call, IOException e){
//                            }
//
//                            @Override
//                            public void onResponse(Call call, final Response response) throws IOException{
//                                if(response.code() >= 200 && response.code() <= 300){
//                                    String xml = response.body().string();
//                                    String result = _Web.XMLReader(xml);
//                                    runOnUiThread(new Runnable(){
//                                        @Override
//                                        public void run(){
//                                            if(result.equals("1")) alertDialog.dismiss();
//                                        }
//                                    });
//                                }
//                            }
//                        });
//                    }
//                });
//            }
//        }
//
//        UserListAdapter.selectData(mChild.getChildId());
    }

    private void showButton(){
//        SHOW_ADDS_BUTTONS = !SHOW_ADDS_BUTTONS;
//        llAddChild.setVisibility(SHOW_ADDS_BUTTONS ? View.VISIBLE : View.GONE);
//        llAddMentor.setVisibility(SHOW_ADDS_BUTTONS ? View.VISIBLE : View.GONE);
//        llSearchSponsor.setVisibility(SHOW_ADDS_BUTTONS ? View.VISIBLE : View.GONE);
//        llAddNewLesson.setVisibility(SHOW_ADDS_BUTTONS ? View.VISIBLE : View.GONE);
//        llAddSchool.setVisibility(SHOW_ADDS_BUTTONS ? View.VISIBLE : View.GONE);
//        flChat.setVisibility(!SHOW_ADDS_BUTTONS ? View.VISIBLE : View.GONE);
    }

    private void onSendMoney(){
//        PrivateAccount = getPrivateAccount();
//
//        BottomSheetDialog bsdAddNewCard = new BottomSheetDialog(this);
//        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_money, null, false);
//        bsdAddNewCard.setContentView(view);
//        View bottomSheet = bsdAddNewCard.findViewById(R.id.design_bottom_sheet);
//        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
//        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
//        bottomSheet.setLayoutParams(layoutParams);
//        layoutParams.height = _System.getWindowHeight(this) / 100 * 90;
//        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        bsdAddNewCard.show();
//
//        Boolean[] isPlus = {true};
//        String[] Cause = {""};
//        TextView tvGetMoneyValue = view.findViewById(R.id.tvGetMoneyValue);
//        ImageView ivPlusOrMinus = view.findViewById(R.id.ivPlusOrMinus);
//        EditText etGetMoneyValue = view.findViewById(R.id.etGetMoneyValue);
//        FrameLayout flButton = view.findViewById(R.id.flButton);
//        Button btnExemtion = view.findViewById(R.id.btnExemtion);
//        Button btnFine = view.findViewById(R.id.btnFine);
//        Button btnCharge = view.findViewById(R.id.btnCharge);
//
//        if(PrivateAccount.getTotalSum() != null)
//            tvGetMoneyValue.setText(PrivateAccount.getTotalSum() + " " + PrivateAccount.getAccountType());
//        etGetMoneyValue.addTextChangedListener(new TextWatcher(){
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
//                int sum = Convert.S2I(charSequence.toString());
//                if(sum != 0){
//                    btnCharge.setEnabled(true);
//                    if(isPlus[0])
//                        btnCharge.setBackgroundResource(R.drawable.btn_background_purple);
//                    else btnCharge.setBackgroundResource(R.drawable.background_square_red);
//                }
//                else{
//                    btnCharge.setEnabled(false);
//                    btnCharge.setBackgroundResource(R.drawable.btn_background_purple_alpha);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable){
//            }
//        });
//        ivPlusOrMinus.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                isPlus[0] = !isPlus[0];
//                if(isPlus[0]){
//                    ivPlusOrMinus.setBackgroundResource(R.drawable.img_togle_plus_purple);
//                    String text = etGetMoneyValue.getText().toString();
//                    text = text.replace("-", "");
//                    etGetMoneyValue.setText(text);
//                    flButton.setVisibility(View.GONE);
//
//                    btnCharge.setText("??????????????????");
//                    if(btnCharge.isEnabled())
//                        btnCharge.setBackgroundResource(R.drawable.btn_background_purple);
//
//                    Cause[0] = "";
//                }
//                else{
//                    ivPlusOrMinus.setBackgroundResource(R.drawable.img_togle_minus_red);
//                    String text = etGetMoneyValue.getText().toString();
//                    text = "-" + text;
//                    etGetMoneyValue.setText(text);
//                    etGetMoneyValue.setSelection(1);
//                    flButton.setVisibility(View.VISIBLE);
//
//                    btnCharge.setText("??????????????");
//                    if(btnCharge.isEnabled())
//                        btnCharge.setBackgroundResource(R.drawable.background_square_red);
//
//                    btnExemtion.setOnClickListener(new View.OnClickListener(){
//                        @Override
//                        public void onClick(View view){
//                            Cause[0] = "Exemtion";
//                            btnExemtion.setBackgroundResource(R.drawable.background_square_green);
//                            btnFine.setBackgroundResource(R.drawable.btn_background_white_rectangle);
//                        }
//                    });
//                    btnFine.setOnClickListener(new View.OnClickListener(){
//                        @Override
//                        public void onClick(View view){
//                            Cause[0] = "Fine";
//                            btnFine.setBackgroundResource(R.drawable.background_square_red);
//                            btnExemtion.setBackgroundResource(R.drawable.btn_background_white_rectangle);
//                        }
//                    });
//                }
//            }
//        });
//        btnCharge.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                bsdAddNewCard.dismiss();
//                JsonObject jsonData = new JsonObject();
//                jsonData.addProperty(F.SourceId, PARENT_ID);
//                jsonData.addProperty(F.TargetId, mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getChildId());
//                jsonData.addProperty(F.Sum, etGetMoneyValue.getText().toString());
//                jsonData.addProperty("Cause", Cause[0]);
//                jsonData.addProperty("isPlus", isPlus[0]);
//
//                String SOAP = SoapRequest(func_ParentSendMoneyToChild, jsonData.toString());
//                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
//                Request request = new Request.Builder().url(URL).post(body).build();
//                HttpClient.newCall(request).enqueue(new Callback(){
//                    @Override
//                    public void onFailure(final Call call, IOException e){
//                    }
//
//                    @Override
//                    public void onResponse(Call call, final Response response) throws IOException{
//                        String xml = response.body().string();
//                        if(response.code() >= 200 && response.code() <= 300){
//                            String result = _Web.XMLReader(xml);
//                            runOnUiThread(new Runnable(){
//                                @Override
//                                public void run(){
//                                    if(result.equals("0"))
//                                        alert.onToast("????????????, ???????????????????? ?????? ??????.");
//                                    else if(result.equals("1")){
//                                        alert.onToast("???????????????? ?????????????? ??????????????????.");
//                                        presenter.onStartPresenter();
//                                    }
//                                }
//                            });
//                        }
//                    }
//                });
//            }
//        });

    }

    private void showChat(){
//        ModelChat mChat = new ModelChat();
//        mChat.setType("ParentChildPrivateChat");
//        mChat.setChatId(mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getChatId());
//        mChat.setUserId(mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getChildId());
//        mChat.setAvatar(mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getChildAvatar());
//        mChat.setFirstName(mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getChildFirstName());
//        mChat.setLastName(mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getChildLastName());
//        onShowFragment(1, mChat);
    }

    private void onChatClick(){
        FRAGMENT_ID = 4;
        chatFragmentAdapter = new ParentChatFragmentAdapter(getSupportFragmentManager());
        chatFragmentAdapter.setData(mParentData);
        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(chatFragmentAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(chatFragmentAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);
    }

    public void onAddChild(){
        setFabGone();
        FRAGMENT_ID = 10;
        parentAddChildFragmentAdapter = new ParentAddChildFragmentAdapter(getSupportFragmentManager());

        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(parentAddChildFragmentAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(parentAddChildFragmentAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);
    }

    private void onCreateChild(){
        onNextActivity(1, false, "", mParentChildLists);
    }

    private void onAddNewLesson(int childPosition){
        //onShowFragmentParentAddNewLesson(mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getChildId());

        //parentAddLessonFragment = ParentAddLessonFragment.newInstance(mParentData.getChildrenList().get(0));
//        ModelChildData mmm = new ModelChildData();
//        parentAddLessonFragment = ParentAddLessonFragment.newInstance(mmm);

//        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.container2, parentAddLessonFragment).commit();

        setFabGone();

        FRAGMENT_ID = 6;
        parentAddLessonFragmentAdapter = new ParentAddLessonFragmentAdapter(getSupportFragmentManager());
        parentAddLessonFragmentAdapter.setChildData(mParentData.getChildrenList().get(childPosition));
        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(parentAddLessonFragmentAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(parentAddLessonFragmentAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);
        parentAddLessonFragmentAdapter.setParentModel(mParentData);
    }

    public void onAddMentor(String lessonId) {
        if(parentShowMoreIncomeLessonsAdapter != null) {
            parentShowMoreIncomeLessonsAdapter.loadMentorList(lessonId);
            onNextFragment(3);
        }
        else if (parentShowIncomeLessonsFromActivityAdapter != null) {
            parentShowIncomeLessonsFromActivityAdapter.loadMentorList(lessonId);
            onNextFragment(2);
        }
        else if (parentShowMoreOtherLessonsAdapter != null) {
            parentShowMoreOtherLessonsAdapter.loadMentorList(lessonId);
            onNextFragment(3);
        }
        else if (parentShowOtherLessonsFromActivityAdapter != null) {
            parentShowOtherLessonsFromActivityAdapter.loadMentorList(lessonId);
            onNextFragment(2);
        }
    }

    public void setSchoolModel(ModelSchoolInfo mSchool) {
        if(parentShowMoreIncomeLessonsAdapter != null) {
            parentShowMoreIncomeLessonsAdapter.setSchoolModel(mSchool);
            onNextFragment(5);
        }
        else if (parentShowIncomeLessonsFromActivityAdapter != null) {
            parentShowIncomeLessonsFromActivityAdapter.setSchoolModel(mSchool);
            onNextFragment(4);
        }
        else if (parentShowMoreOtherLessonsAdapter != null) {
            parentShowMoreOtherLessonsAdapter.setSchoolModel(mSchool);
            onNextFragment(5);
        }
        else if (parentShowOtherLessonsFromActivityAdapter != null) {
            parentShowOtherLessonsFromActivityAdapter.setSchoolModel(mSchool);
            onNextFragment(4);
        }
    }

    private void onShowMoreIncomeLessons(int childPosition){
        setFabGone();
        FRAGMENT_ID = 7;
        parentShowMoreIncomeLessonsAdapter = new ParentShowMoreIncomeLessonsAdapter(getSupportFragmentManager());

        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(parentShowMoreIncomeLessonsAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(parentShowMoreIncomeLessonsAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);

        parentShowMoreIncomeLessonsAdapter.setLessonList(mParentData.getChildrenList().get(childPosition).getLessonsWithSmartGrades());
    }

    public void updatePresenter(){
        presenter.onStartPresenter();
    }

    private void onShowMoreOtherLessons(int childPosition){
        setFabGone();
        FRAGMENT_ID = 8;
        parentShowMoreOtherLessonsAdapter = new ParentShowMoreOtherLessonsAdapter(getSupportFragmentManager());

        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(parentShowMoreOtherLessonsAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(parentShowMoreOtherLessonsAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);

        parentShowMoreOtherLessonsAdapter.setLessonList(mParentData.getChildrenList().get(childPosition).getLessonsWithoutSmartGrades());
    }

    public void setFabVisible(){
        cvDatePeriod.setVisibility(View.VISIBLE);
        cvAddSchool.setVisibility(View.VISIBLE);
        cvAddLessonFAB.setVisibility(View.VISIBLE);
    }

    public void setFabGone(){
        cvDatePeriod.setVisibility(View.GONE);
        cvAddSchool.setVisibility(View.GONE);
        cvAddLessonFAB.setVisibility(View.GONE);
    }

    private void onAddSchool(){
        setFabGone();
        FRAGMENT_ID = 5;
        addSchoolFragmentAdapter = new ParentAddSchoolFragmentAdapter(getSupportFragmentManager());
        addSchoolFragmentAdapter.setData(mParentData, SELECT_CHILD_INDEX);
        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(addSchoolFragmentAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(addSchoolFragmentAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);
    }

    public void ChatVisible(boolean show){
//        if(show) dialogChat.show();
//        else dialogChat.hide();
    }

    private void onSponsorData(){
//        ModelChildSponsorData mSponsorData = mParentChildLists.get(SELECT_CHILD_INDEX).getSponsorData();
//        ModelChat mChat = new ModelChat();
//        mChat.setChatId(mSponsorData.getChatId());
//        mChat.setAvatar(mSponsorData.getSponsorAvatar());
//        mChat.setFirstName(mSponsorData.getSponsorFirstName());
//        mChat.setLastName(mSponsorData.getSponsorLastName());
//        mChat.setType("Sponsor");
//        onShowFragment(1, mChat);
    }

    private void onExtraMenu(){
//        FrameLayout viewGroup = (FrameLayout) findViewById(R.id.llSortChangePopup);
//        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        View layout = layoutInflater.inflate(R.layout.pw_parent_extra_menu, viewGroup);
//        PopupWindow popupWindow = new PopupWindow(this);
//        popupWindow.setContentView(layout);
//        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
//        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//        popupWindow.setFocusable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        int OFFSET_X = 0;
//        int OFFSET_Y = -30;
//        Point p = getLocationOnScreen(ivExtraMenu);
//        popupWindow.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);
//
//        TextView tvTitle1Value = layout.findViewById(R.id.tvTitle1Value);
//        TextView tvTitle2Value = layout.findViewById(R.id.tvTitle2Value);
//        TextView tvTitle3Value = layout.findViewById(R.id.tvTitle3Value);
//        TextView tvTitle4Value = layout.findViewById(R.id.tvTitle4Value);
//
//        tvTitle1Value.setText(mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getSponsorData().getDateEndCurrent());
//        tvTitle2Value.setText(mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getSponsorData().getDateEndTotal());
//        tvTitle3Value.setText(mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getSponsorData().getRewardCurrent() + " KZT");
//        tvTitle4Value.setText(mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getSponsorData().getRewardTotal() + " KZT");
//
//        TextView tvTitle5Value = layout.findViewById(R.id.tvTitle5Value);
//        tvTitle5Value.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                popupWindow.dismiss();
//
//                Date dateStart = Convert.StringDate2Date(mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getSponsorData().getDateStartCurrent());
//                Date dateEnd = Convert.StringDate2Date(mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getSponsorData().getDateEndCurrent());
//                if(dateStart != null && dateEnd != null){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(ParentActivity.this);
//                    View viewAlert = getLayoutInflater().inflate(R.layout.calendar_view, null);
//                    builder.setView(viewAlert);
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                    CalendarView calendarView = viewAlert.findViewById(R.id.calendarView);
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.set(dateStart.getYear(), dateStart.getMonth(), dateStart.getDay());
//                    calendarView.setMinDate(calendar.getTimeInMillis());
//                    calendar = Calendar.getInstance();
//                    calendar.set(dateEnd.getYear(), dateEnd.getMonth(), dateEnd.getDay());
//                    calendarView.setMaxDate(calendar.getTimeInMillis());
//
//                    alertDialog.show();
//                }
//            }
//        });
//
//        TextView tvTitle6Value = layout.findViewById(R.id.tvTitle6Value);
//        tvTitle6Value.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                popupWindow.dismiss();
//                alert.onToast("???????????? ???????????????? ?????? ???? ????????????!");
//            }
//        });

    }

    @Override
    public void onChatClick(ModelParentData m){
        onShowChatFragment(m);
    }

    @Override
    public void onMentorClick(int type, ModelParentData m){
        onShowUsersListFragment(type, m);
    }

    @Override
    public void onLessonrClick(int type, ModelParentData m){
        onShowUsersListFragment(type, m);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture){

    }

    public void setSchoolProfile(ModelSchoolData m){
        if(addSchoolFragmentAdapter!=null){
            addSchoolFragmentAdapter.setSchoolProfile(m);
        }
    }

    public void setChatModel(ModelChat m){
        if(chatFragmentAdapter != null){
            chatFragmentAdapter.setChatModel(m);
        }
    }

    public void setMentorList(ArrayList<ModelMentorList> mentorList){
        parentAddLessonFragmentAdapter.setMentorList(mentorList);
    }

    public void setMentorModel(ModelMentorList mMentor){
        parentAddLessonFragmentAdapter.setMentorModel(mMentor);
    }

    public void setMentorModel2(ModelMentorList mMentor, String lessonId) {
        if(parentShowMoreIncomeLessonsAdapter != null) {
            parentShowMoreIncomeLessonsAdapter.setMentorModel2(mMentor, lessonId, PARENT_ID, mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getId());
        }
        else if (parentShowIncomeLessonsFromActivityAdapter != null) {
            parentShowIncomeLessonsFromActivityAdapter.setMentorModel2(mMentor, lessonId, PARENT_ID, mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getId());
        }
        else if (parentShowMoreOtherLessonsAdapter != null) {
            parentShowMoreOtherLessonsAdapter.setMentorModel2(mMentor, lessonId, PARENT_ID, mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getId());
        }
        else if (parentShowOtherLessonsFromActivityAdapter != null) {
            parentShowOtherLessonsFromActivityAdapter.setMentorModel2(mMentor, lessonId, PARENT_ID, mParentData.getChildrenList().get(SELECT_CHILD_INDEX).getId());
        }
    }

    public void setSelectedMentorList(ModelMentorList mMentor){
        parentAddLessonFragmentAdapter.setSelectedMentor(mMentor);
    }

    public void checkToSelected(){
        parentAddLessonFragmentAdapter.checkToSelected();
    }

    @Override
    public void onSelectClick(int p){
        parentAddLessonFragmentAdapter.onDeleteMentorFromSelectedList(p);//remove(p);
        //setSelectedMentorListAdapter();
    }

    public void addMentors(ArrayList<ModelMentorList> selectedMentorList){
        parentAddLessonFragmentAdapter.addMentors(selectedMentorList);
    }

    @Override
    public void onSmartClick(ModelLessonsWithSmartGrades mLessonsWithSmartGrades){
        //?????????????? ????????????????
        setFabGone();
        FRAGMENT_ID = 7;
        parentShowIncomeLessonsFromActivityAdapter = new ParentShowIncomeLessonsFromActivityAdapter(getSupportFragmentManager());

        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(parentShowIncomeLessonsFromActivityAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(parentShowIncomeLessonsFromActivityAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);
        parentShowIncomeLessonsFromActivityAdapter.setLessonsWithSmartGradesData(mLessonsWithSmartGrades);
    }

    @Override
    public void onOtherClick(ModelLessonsWithOutSmartGrades mLessonsWithOutSmartGrades){
        setFabGone();
        FRAGMENT_ID = 8;
        parentShowOtherLessonsFromActivityAdapter = new ParentShowOtherLessonsFromActivityAdapter(getSupportFragmentManager());

        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(parentShowOtherLessonsFromActivityAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(parentShowOtherLessonsFromActivityAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);
        parentShowOtherLessonsFromActivityAdapter.setLessonsWithOutSmartGradesData(mLessonsWithOutSmartGrades);
    }

    public void setLessonsWithSmartGradesData(ModelLessonsWithSmartGrades mLessonsWithSmartGrades){
        parentShowMoreIncomeLessonsAdapter.setLessonsWithSmartGradesData(mLessonsWithSmartGrades);
    }

    public void setLessonsWithOutSmartGradesData(ModelLessonsWithOutSmartGrades mLessonsWithOutSmartGrades){
        parentShowMoreOtherLessonsAdapter.setLessonsWithOutSmartGradesData(mLessonsWithOutSmartGrades);
    }

    @Override
    public void onSmartLongClickListener(ModelLessonsWithSmartGrades mLessonsWithSmartGrades, View itemView){
        if (listIsNullOrEmpty(mLessonsWithSmartGrades.getMentors())) return;
        boolean flag = false;
        for (kz.tech.smartgrades.parent.model.ModelMentorList m : mLessonsWithSmartGrades.getMentors()) {
            if (m.getId().equals(PARENT_ID)) flag = true;
        }
        if (flag) {
            BSDSetSmartGrades dialog = new BSDSetSmartGrades(this, mLessonsWithSmartGrades);
            dialog.show();
        } else {
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//            PWFamilyGroupInfo popupWindow = new PWFamilyGroupInfo(width, height, this);
//            popupWindow.setFocusable(true);
//            //popupWindow.showAsDropDown(tvFamilyGroupInfo);
//            popupWindow.showAtLocation(tvFamilyGroupInfo, Gravity.CENTER, 0, 0);
            View popUpView = getLayoutInflater().inflate(R.layout.pw_parent_smart_grades_error, null);
            PopupWindow popupWindow = new PopupWindow(popUpView, width, height, true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.setElevation(20);
            }
            popupWindow.showAsDropDown(itemView, 0, 0);
        }
    }

    public void setChildModel(ModelUserData mChild){
        parentAddChildFragmentAdapter.setChildModel(mChild);
    }

    public int getCHILD_INDEX(){
        return SELECT_CHILD_INDEX;
    }

    public ModelParentData getMParentData(){
        return mParentData;
    }

    public void setParentModel(ModelParentInFamilyGroup mParent){
        parentAddParentFragmentAdapter.setParentModel(mParent);
    }

    @Override
    public void onEnrollClick(ModelInterFormList modelInterFormList){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.Id, modelInterFormList.getId());
        jsonData.addProperty(F.SourceId, PARENT_ID);

        if (modelInterFormList.getSourceType().equals("Child")) {
            String SOAP = SoapRequest(func_AcceptInterFormInFamilyGroup, jsonData.toString());
            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
            Request request = new Request.Builder().url(URL).post(body).build();
            HttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, IOException e) { }
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = _Web.XMLReader(response.body().string());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!result.equals("null")) {
                                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                    alert.onToast(answer.getMessage());
                                    if (answer.isSuccess()) {
                                        updatePresenter();
                                        //InviteListAdapter.cancel(modelInterFormList);
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }
        else if (modelInterFormList.getSourceType().equals("Parent")) {

        }
        else if (modelInterFormList.getSourceType().equals("Sponsor")) {
            aboutSponsoring(modelInterFormList);
        }
        else if (modelInterFormList.getSourceType().equals("Mentor")) {

        }
        else if (modelInterFormList.getSourceType().equals("School")) {

        }
    }

    @Override
    public void onCancelClick(ModelInterFormList modelInterFormList){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.Id, modelInterFormList.getId());

        if (modelInterFormList.getSourceType().equals("Child")) {
            String SOAP = SoapRequest(func_RejectInterFormInFamilyGroup, jsonData.toString());
            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
            Request request = new Request.Builder().url(URL).post(body).build();
            HttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, IOException e) { }
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = _Web.XMLReader(response.body().string());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!result.equals("null")) {
                                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                    alert.onToast(answer.getMessage());
                                    updatePresenter();
                                    //InviteListAdapter.cancel(modelInterFormList);
                                }
                            }
                        });
                    }
                }
            });
        }
        else if (modelInterFormList.getSourceType().equals("Parent")) {

        }
        else if (modelInterFormList.getSourceType().equals("Sponsor")) {
            String SOAP = SoapRequest(func_RejectInterFormOfSponsorship, jsonData.toString());
            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
            Request request = new Request.Builder().url(URL).post(body).build();
            HttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, IOException e) { }
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = _Web.XMLReader(response.body().string());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!result.equals("null")) {
                                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                    alert.onToast(answer.getMessage());
                                    updatePresenter();
                                }
                            }
                        });
                    }
                }
            });
        }
        else if (modelInterFormList.getSourceType().equals("Mentor")) {

        }
        else if (modelInterFormList.getSourceType().equals("School")) {

        }
    }

    public void aboutSponsoring(ModelInterFormList modelInterFormList) {
        ParentSponsorInterFormDialog dialog = new ParentSponsorInterFormDialog(this, modelInterFormList);
        dialog.show();
    }

    @Override
    public void onOtherLongClickListener(ModelLessonsWithOutSmartGrades mLessonsWithOutSmartGrades, View itemView) {
        if (listIsNullOrEmpty(mLessonsWithOutSmartGrades.getMentors())) return;
        boolean flag = false;
        for (kz.tech.smartgrades.parent.model.ModelMentorList m : mLessonsWithOutSmartGrades.getMentors()) {
            if (m.getId().equals(PARENT_ID)) flag = true;
        }
        if (flag) {
            BSDSetRegularGrades dialog = new BSDSetRegularGrades(this, mLessonsWithOutSmartGrades);
            dialog.show();
        } else {
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            View popUpView = getLayoutInflater().inflate(R.layout.pw_parent_smart_grades_error, null);
            PopupWindow popupWindow = new PopupWindow(popUpView, width, height, true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.setElevation(20);
            }
            popupWindow.showAsDropDown(itemView, 0, 0);
        }
    }

    public void setNewSelectedMentorList(ArrayList<ModelMentorList> addedMentorList) {
        parentAddLessonFragmentAdapter.setMentorList(addedMentorList);
    }

    public void openProveWindow(kz.tech.smartgrades.parent.model.ModelMentorList mMentor){
        if(parentShowMoreIncomeLessonsAdapter != null)
            parentShowMoreIncomeLessonsAdapter.openProveWindow(mMentor);
        else if (parentShowIncomeLessonsFromActivityAdapter != null)
            parentShowIncomeLessonsFromActivityAdapter.openProveWindow(mMentor);
        else if (parentShowMoreOtherLessonsAdapter != null)
            parentShowMoreOtherLessonsAdapter.openProveWindow(mMentor);
        else if (parentShowOtherLessonsFromActivityAdapter != null)
            parentShowOtherLessonsFromActivityAdapter.openProveWindow(mMentor);
    }

    public void onRemoveMentor(kz.tech.smartgrades.parent.model.ModelMentorList mMentor) {
        if(parentShowMoreIncomeLessonsAdapter != null)
            parentShowMoreIncomeLessonsAdapter.onRemoveMentor(mMentor);
        else if (parentShowIncomeLessonsFromActivityAdapter != null)
            parentShowIncomeLessonsFromActivityAdapter.onRemoveMentor(mMentor);
        else if(parentShowMoreOtherLessonsAdapter != null)
            parentShowMoreOtherLessonsAdapter.onRemoveMentor(mMentor);
        else if (parentShowOtherLessonsFromActivityAdapter != null)
            parentShowOtherLessonsFromActivityAdapter.onRemoveMentor(mMentor);
    }

    public void onRemoveLessonProve() {
        if(parentShowMoreIncomeLessonsAdapter != null)
            parentShowMoreIncomeLessonsAdapter.onRemoveLessonProve();
        else if (parentShowIncomeLessonsFromActivityAdapter != null)
            parentShowIncomeLessonsFromActivityAdapter.onRemoveLessonProve();
        else if (parentShowMoreOtherLessonsAdapter != null)
            parentShowMoreOtherLessonsAdapter.onRemoveLessonProve();
        else if (parentShowOtherLessonsFromActivityAdapter != null)
            parentShowOtherLessonsFromActivityAdapter.onRemoveLessonProve();
    }

    public void setNewMentor(ModelMentorList mMentor) {
        if(parentShowMoreIncomeLessonsAdapter != null) {
            parentShowMoreIncomeLessonsAdapter.setNewMentor(mMentor);
        }
        else if (parentShowIncomeLessonsFromActivityAdapter != null) {
            parentShowIncomeLessonsFromActivityAdapter.setNewMentor(mMentor);
        }
        else if (parentShowMoreOtherLessonsAdapter != null) {
            parentShowMoreOtherLessonsAdapter.setNewMentor(mMentor);
        }
        else if (parentShowOtherLessonsFromActivityAdapter != null) {
            parentShowOtherLessonsFromActivityAdapter.setNewMentor(mMentor);
        }
    }

    public void addCard() {
        parentCashFragment.openBindWindow();
    }
}
