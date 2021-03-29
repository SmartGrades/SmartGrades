package kz.tech.smartgrades.child;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

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

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import kz.tech.smartgrades.child.adapters.ChildAddParentFragmentAdapter;
import kz.tech.smartgrades.child.adapters.ChildAllTransactionAdapter;
import kz.tech.smartgrades.child.adapters.ChildInviteListAdapter;
import kz.tech.smartgrades.child.adapters.ChildOtherListAdapter;
import kz.tech.smartgrades.child.adapters.ChildShowIncomeLessonsFromActivityAdapter;
import kz.tech.smartgrades.child.adapters.ChildShowMoreIncomeLessonsAdapter;
import kz.tech.smartgrades.child.adapters.ChildShowMoreOtherLessonsAdapter;
import kz.tech.smartgrades.child.adapters.ChildShowOtherLessonsFromActivityAdapter;
import kz.tech.smartgrades.child.adapters.ChildSmartGradesListAdapter;
import kz.tech.smartgrades.child.adapters.ChildTransactionListAdapter;
import kz.tech.smartgrades.child.adapters.ChildUserListAdapter;
import kz.tech.smartgrades.child.fragments.ChildAddParentListFragment;
import kz.tech.smartgrades.child.fragments.ChildAllOtherLessonFragment;
import kz.tech.smartgrades.child.fragments.ChildAllSmartGradesFragment;
import kz.tech.smartgrades.child.fragments.ChildAllTransactionFragment;
import kz.tech.smartgrades.child.fragments.ChildCashFragment;
import kz.tech.smartgrades.child.fragments.ChildChatFragment;
import kz.tech.smartgrades.child.fragments.ChildEditProfileFragment;
import kz.tech.smartgrades.child.fragments.ChildOtherLessonInfoFragment;
import kz.tech.smartgrades.child.fragments.ChildParentProfileFragment;
import kz.tech.smartgrades.child.fragments.ChildSmartLessonInfoFragment;
import kz.tech.smartgrades.child.fragments.ChildSponsoringInfoFragment;
import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.child.models.ModelChildSponsorData;
import kz.tech.smartgrades.child.models.ModelInterFormList;
import kz.tech.smartgrades.parent.fragments.ParentSponsoringInfoFragment;
import kz.tech.smartgrades.parent.model.ModelChildSponsorship;
import kz.tech.smartgrades.parent.model.ModelLessonsWithOutSmartGrades;
import kz.tech.smartgrades.parent.model.ModelLessonsWithSmartGrades;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.custom_view.CustomViewPager;
import kz.tech.smartgrades.root.dialogs.DAceessCode;
import kz.tech.smartgrades.root.hardware_access.IHardwareAccess;
import kz.tech.smartgrades.root.language.ILanguage;
import kz.tech.smartgrades.root.login.ILogin;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.models.ModelPrivateAccount;
import kz.tech.smartgrades.sponsor.models.ModelUserCards;
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
import static kz.tech.smartgrades._Web.func_GetTimeInterFormInFamilyGroup;
import static kz.tech.smartgrades._Web.func_GetUser;
import static kz.tech.smartgrades._Web.func_RejectInterFormInFamilyGroup;
import static kz.tech.smartgrades._Web.func_UserEditPassword;


public class ChildActivity extends AppCompatActivity implements
        View.OnClickListener,
        ChildUserListAdapter.OnItemClickListener,
        ChildInviteListAdapter.OnItemClickListener,
        ChildSmartGradesListAdapter.OnItemClickListener,
        ChildSmartGradesListAdapter.OnItemLongClickListener,
        ChildOtherListAdapter.OnItemClickListener,
        ChildTransactionListAdapter.OnItemClickListener{
    @Inject
    public ILanguage language;
    @Inject
    public ILogin login;
    @Inject
    public IHardwareAccess hardwareAccess;
    @Inject
    public IAlert alert;
    public ChildPresenter presenter;

    private CustomViewPager FullViewPager;

    private String CHILD_ID;
    private int FRAGMENT_ID = 0; // 1 - ChildCashFragment, 2 - ChildAddParentFragment
    private int CURRENT_PAGE = 0;

    private DrawerLayout drawer;
    private ScrollView svChildWithParentContainer;
    private ScrollView svChildWithoutParentContainer;
    private ConstraintLayout clYouHaveNoFamilyGroup;
    private TextView tvYouHaveNoFamilyGroup;

    private ConstraintLayout clInviteFamilyGroupContainer;
    private TextView tvInviteFamilyGroup;

    private RecyclerView rvParentInvite;

    private CircleImageView civAvatar;
    private TextView tvCurrentCash;
    private ImageView ivNav;

    private CardView cardViewTodayIncome;
    private TextView tvTodayIncomeLabel;
    private TextView tvTodayIncomeValue;

    private RecyclerView rvAvatarWithIncome;

    private ConstraintLayout clSponsorInfo;
    private TextView tvSponsoring;
    private TextView tvSumOfMoney;
    private CircleImageView civSponsorAvatar;
    private TextView tvSponsorName;
    private TextView tvWeek;
    private TextView tvWeekProgress;
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

    private View vclTransactions;
    private View vclSponsorInfo;
    private View vclIncomeSourceContainer;
    private View vclOtherLessonsContainer;

    private ConstraintLayout clIncomeSourceContainer;
    private TextView tvIncomeSource;
    private TextView tvMonth;
    private ConstraintLayout emptySourceOfIncomeContainer;
    private TextView tvNoIncomeSource;
    private Button btnCreateIncomeSource;

    private RecyclerView rvIncomeSource;

    private ConstraintLayout clOtherLessonsContainer;
    private TextView tvOtherLessons;
    private ConstraintLayout emptyLessonsContainer;
    private TextView tvNoOtherLessons;
    private Button btnCreateOtherLessons;

    private RecyclerView rvOtherLessons;

    private NavigationView navigation;
    private CircleImageView civNavAvatar;

    private CardView cvShare;
    private CardView cvEnroll;

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
    //
//    public ParentFragmentAdapter adapter;
//
//    private RecyclerView rvUserListAdapter;
//    public ChildUserListAdapter UserListAdapter;
//
//    private ConstraintLayout clSponsorData, clSponsorInfo;
//    private CircleImageView civSponsorAvatar;
//    private TextView tvDaysLeft, tvGradesLeft, tvThresholdGrade, tvAverageGrade;
//    private ChildListGradesListAdapter adapterGrades;
//    private RecyclerView rvGradesList, rvSponsorUserList;
//
//
    private ModelPrivateAccount PrivateAccount;
    private ModelChildSponsorData SponsorData;
    private ModelChildData mChildData;

    public ArrayList<ModelUserCards> modelUserCards;

    private ChildCashFragment childCashFragment;

    private ConstraintLayout clTransactions;

    private ChildInviteListAdapter InviteListAdapter;
    private ChildAddParentFragmentAdapter childAddParentFragmentAdapter;

    private ChildSmartGradesListAdapter childSmartGradesListAdapter;
    private ChildOtherListAdapter childOtherListAdapter;

    private CardView cvShowMoreIncomeLessons;
    private CardView cvShowMoreOtherLessons;

    private ChildShowMoreIncomeLessonsAdapter childShowMoreIncomeLessonsAdapter;
    private ChildShowIncomeLessonsFromActivityAdapter childShowIncomeLessonsFromActivityAdapter;
    private ChildShowMoreOtherLessonsAdapter childShowMoreOtherLessonsAdapter;
    private ChildShowOtherLessonsFromActivityAdapter childShowOtherLessonsFromActivityAdapter;
    private ChildTransactionListAdapter childTransactionListAdapter;
    private ChildAllTransactionAdapter childAllTransactionAdapter;

    private View vemptySourceOfIncomeContainer;
    private TextView tvTransactionsEmptyLabel;

    private ChildSponsoringInfoFragment childSponsoringInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        App.app().getComponent().inject(this);
        setContentView(R.layout.activity_child_with_cards);
        initViews();
        initPresenter();
        onSetChildData(null);

        /*SharedPreferences spSettings = getApplicationContext().getSharedPreferences("PREFS_STORAGE_LOGIN", 0);
        SharedPreferences.Editor ed = spSettings.edit();
        ed.putString("id", login.loadUserDate(LoginKey.ID));
        ed.apply();*/
    }

    private void initViews(){
        CHILD_ID = login.loadUserDate(LoginKey.ID);

        svChildWithoutParentContainer = findViewById(R.id.svChildWithoutParentContainer);

        drawer = findViewById(R.id.drawer);
        svChildWithParentContainer = findViewById(R.id.svChildWithParentContainer);

        clInviteFamilyGroupContainer = findViewById(R.id.clInviteFamilyGroupContainer);
        clYouHaveNoFamilyGroup = findViewById(R.id.clYouHaveNoFamilyGroup);
        tvYouHaveNoFamilyGroup = findViewById(R.id.tvYouHaveNoFamilyGroup);

        clInviteFamilyGroupContainer = findViewById(R.id.clInviteFamilyGroupContainer);
        tvInviteFamilyGroup = findViewById(R.id.tvInviteFamilyGroup);

        rvParentInvite = findViewById(R.id.rvParentInvite);

        tvCurrentCash = findViewById(R.id.tvCurrentCash);
        ivNav = findViewById(R.id.ivNav);
        civAvatar = findViewById(R.id.civAvatar);

        cvShare = findViewById(R.id.cvShare);
        cvShare.setOnClickListener(this);

        cvEnroll = findViewById(R.id.cvEnroll);
        cvEnroll.setOnClickListener(this);

        FullViewPager = findViewById(R.id.FullViewPager);

        cardViewTodayIncome = findViewById(R.id.cardViewTodayIncome);
        tvTodayIncomeLabel = findViewById(R.id.tvTodayIncomeLabel);
        tvTodayIncomeValue = findViewById(R.id.tvTodayIncomeValue);

        rvAvatarWithIncome = findViewById(R.id.rvAvatarWithIncome);

        clSponsorInfo = findViewById(R.id.clSponsorInfo);
        vclSponsorInfo = findViewById(R.id.vclSponsorInfo);
        tvSponsoring = findViewById(R.id.tvSponsoring);
        tvSumOfMoney = findViewById(R.id.tvSumOfMoney);
        civSponsorAvatar = findViewById(R.id.civSponsorAvatar);
        tvSponsorName = findViewById(R.id.tvSponsorName);
        tvWeek = findViewById(R.id.tvWeek);
        tvWeekProgress = findViewById(R.id.tvWeekProgress);
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

        clIncomeSourceContainer = findViewById(R.id.clIncomeSourceContainer);
        vclIncomeSourceContainer = findViewById(R.id.vclIncomeSourceContainer);
        tvIncomeSource = findViewById(R.id.tvIncomeSource);
        tvMonth = findViewById(R.id.tvMonth);
        emptySourceOfIncomeContainer = findViewById(R.id.emptySourceOfIncomeContainer);
        vemptySourceOfIncomeContainer = findViewById(R.id.vemptySourceOfIncomeContainer);
        tvNoIncomeSource = findViewById(R.id.tvNoIncomeSource);
        btnCreateIncomeSource = findViewById(R.id.btnCreateIncomeSource);
        btnCreateIncomeSource.setOnClickListener(this);

        rvIncomeSource = findViewById(R.id.rvIncomeSource);

        clOtherLessonsContainer = findViewById(R.id.clOtherLessonsContainer);
        vclOtherLessonsContainer = findViewById(R.id.vclOtherLessonsContainer);
        tvOtherLessons = findViewById(R.id.tvOtherLessons);
        emptyLessonsContainer = findViewById(R.id.emptyLessonsContainer);
        tvNoOtherLessons = findViewById(R.id.tvNoOtherLessons);
        btnCreateOtherLessons = findViewById(R.id.btnCreateOtherLessons);

        rvOtherLessons = findViewById(R.id.rvOtherLessons);

        tvTransactionsEmptyLabel = findViewById(R.id.tvTransactionsEmptyLabel);


//        clSponsorData = findViewById(R.id.clSponsorData);
        clSponsorInfo.setOnClickListener(this);
//        tvDaysLeft = findViewById(R.id.tvDaysLeft);
//        tvGradesLeft = findViewById(R.id.tvGradesLeft);
//        tvThresholdGrade = findViewById(R.id.tvThresholdGrade);
//        tvAverageGrade = findViewById(R.id.tvAverageGrade);
//        rvGradesList = findViewById(R.id.rvGradesList);
//
//        rvSponsorUserList = findViewById(R.id.rvSponsorUserList);
//        rvSponsorUserList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        rvUserListAdapter = findViewById(R.id.rvUserList);
//        rvUserListAdapter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//
//        adapterGrades = new ChildListGradesListAdapter(this);
//        rvGradesList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
//        rvGradesList.setAdapter(adapterGrades);

        navigation = findViewById(R.id.navigation);
        View navLayout = navigation.getHeaderView(0);
        civNavAvatar = navLayout.findViewById(R.id.civNavAvatar);
        tvNavFullName = navLayout.findViewById(R.id.tvNavFullName);
        tvNavLogin = navLayout.findViewById(R.id.tvNavLogin);
        tvNavPhoneOrMail = navLayout.findViewById(R.id.tvNavPhoneOrMail);

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

        clTransactions = findViewById(R.id.clTransactions);
        vclTransactions = findViewById(R.id.vclTransactions);

        civAvatar.setOnClickListener(this);
        ivNav.setOnClickListener(this);

        cvShowMoreIncomeLessons = findViewById(R.id.cvShowMoreIncomeLessons);
        cvShowMoreIncomeLessons.setOnClickListener(this);
        cvShowMoreOtherLessons = findViewById(R.id.cvShowMoreOtherLessons);
        cvShowMoreOtherLessons.setOnClickListener(this);
    }

    private void initPresenter(){
        presenter = new ChildPresenter(this);
        presenter.onStartPresenter();
    }

    public void onSetChildData(ModelChildData mChildData){
        if(mChildData == null){
            String avatar = login.loadUserDate(LoginKey.AVATAR);
            if(avatar != null && !avatar.isEmpty()){
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
        }
        else{
            this.mChildData = mChildData;
            onSetPrivateAccountData(mChildData.getPrivateAccount());
            onSetInvitesData(mChildData.getInterFormList());
            onSetFamilyGroupData();
            onSetChildLessonsData();
            setSponsorData();
        }
        if (childCashFragment != null) childCashFragment.updateCardList();
    }

    private void onSetPrivateAccountData(ModelPrivateAccount privateAccount){
        if(privateAccount == null) return;
        PrivateAccount = privateAccount;
        if(!stringIsNullOrEmpty(PrivateAccount.getCurrentSum()))
            tvCurrentCash.setText("₸ " + PrivateAccount.getCurrentSum());
        else tvCurrentCash.setText("₸ 0");
        onSetTransaction();
    }

    private void onSetTransaction(){
        if(mChildData.getPrivateAccount() == null) return;
        if(listIsNullOrEmpty(mChildData.getPrivateAccount().getTransactions())){
            clTransactions.setVisibility(View.GONE);
            vclTransactions.setVisibility(View.GONE);
        }
        else{
            clTransactions.setVisibility(View.VISIBLE);
            vclTransactions.setVisibility(View.VISIBLE);
            if(!stringIsNullOrEmpty(mChildData.getPrivateAccount().getCurrentSum()))
                tvTodayIncomeValue.setText(mChildData.getPrivateAccount().getCurrentSum());
            else tvTodayIncomeValue.setText("0");
            rvAvatarWithIncome.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            childTransactionListAdapter = new ChildTransactionListAdapter(this);
            childTransactionListAdapter.setOnItemClickListener(this);
            rvAvatarWithIncome.setAdapter(childTransactionListAdapter);
            childTransactionListAdapter.updateData(mChildData.getPrivateAccount().getTransactions());
        }
        if(!listIsNullOrEmpty(mChildData.getPrivateAccount().getTransactions())) {
            tvTransactionsEmptyLabel.setVisibility(View.GONE);
        } else {
            tvTransactionsEmptyLabel.setVisibility(View.VISIBLE);
        }
    }

    public void onSetInvitesData(ArrayList<ModelInterFormList> interFormList){
        if(!listIsNullOrEmpty(interFormList)){
            clInviteFamilyGroupContainer.setVisibility(View.VISIBLE);
            clYouHaveNoFamilyGroup.setVisibility(View.GONE);
            rvParentInvite.setVisibility(View.VISIBLE);
            rvParentInvite.setLayoutManager(new LinearLayoutManager(this));

            InviteListAdapter = new ChildInviteListAdapter(this);
            InviteListAdapter.setOnItemClickListener(this);
            rvParentInvite.setAdapter(InviteListAdapter);
            InviteListAdapter.updateData(getModelInterFormList());
        }
    }

    private void onSetFamilyGroupData(){
        if(stringIsNullOrEmpty(mChildData.getFamilyGroupId())){
            svChildWithParentContainer.setVisibility(View.GONE);
            svChildWithoutParentContainer.setVisibility(View.VISIBLE);
        }
        else{
            svChildWithoutParentContainer.setVisibility(View.GONE);
            svChildWithParentContainer.setVisibility(View.VISIBLE);
        }
    }

    private void onSetChildLessonsData(){
        if(!listIsNullOrEmpty(mChildData.getLessonsWithSmartGrades())){
            clIncomeSourceContainer.setVisibility(View.VISIBLE);
            vclIncomeSourceContainer.setVisibility(View.VISIBLE);
            onSetSmartGradesListAdapter();
        }
        if(!listIsNullOrEmpty(mChildData.getLessonsWithoutSmartGrades())){
            clOtherLessonsContainer.setVisibility(View.VISIBLE);
            vclOtherLessonsContainer.setVisibility(View.VISIBLE);
            tvNoOtherLessons.setVisibility(View.GONE);
            setOtherListAdapter();
        }
        if(listIsNullOrEmpty(mChildData.getLessonsWithSmartGrades())
                && listIsNullOrEmpty(mChildData.getLessonsWithoutSmartGrades())){
            vemptySourceOfIncomeContainer.setVisibility(View.VISIBLE);
            emptySourceOfIncomeContainer.setVisibility(View.VISIBLE);
        }
    }

    private void onSetSmartGradesListAdapter(){
        ArrayList<ModelLessonsWithSmartGrades> lessonList = mChildData.getLessonsWithSmartGrades();

        childSmartGradesListAdapter = new ChildSmartGradesListAdapter(this);
        childSmartGradesListAdapter.setOnItemClickListener(this);
        childSmartGradesListAdapter.setOnItemLongClickListener(this);
        rvIncomeSource.setVisibility(View.VISIBLE);
        rvIncomeSource.setLayoutManager(new GridLayoutManager(this, 2));
        rvIncomeSource.setAdapter(childSmartGradesListAdapter);
        rvIncomeSource.suppressLayout(true);

        if(lessonList.size() > 4){
            cvShowMoreIncomeLessons.setVisibility(View.VISIBLE);
            childSmartGradesListAdapter.updateData(new ArrayList(lessonList.subList(0, 4)));
        }
        else{
            cvShowMoreIncomeLessons.setVisibility(View.GONE);
            childSmartGradesListAdapter.updateData(lessonList);
        }
    }

    private void setOtherListAdapter(){
        ArrayList<ModelLessonsWithOutSmartGrades> lessonList = mChildData.getLessonsWithoutSmartGrades();

        childOtherListAdapter = new ChildOtherListAdapter(this);
        childOtherListAdapter.setOnItemClickListener(this);
        rvOtherLessons.setVisibility(View.VISIBLE);
        rvOtherLessons.setLayoutManager(new GridLayoutManager(this, 2));
        rvOtherLessons.setAdapter(childOtherListAdapter);
        rvOtherLessons.suppressLayout(true);

        if(lessonList.size() > 4){
            cvShowMoreOtherLessons.setVisibility(View.VISIBLE);
            childOtherListAdapter.updateData(new ArrayList(lessonList.subList(0, 4)));
        }
        else{
            cvShowMoreOtherLessons.setVisibility(View.GONE);
            childOtherListAdapter.updateData(lessonList);
        }
    }

    private void setSponsorData() {
        ModelChildSponsorship m = mChildData.getSponsorship();
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
            childSponsoringInfoFragment = new ChildSponsoringInfoFragment(mChildData.getSponsorship());
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container2, childSponsoringInfoFragment).commit();
        }
    }

    public void setModelUserCards(ArrayList<ModelUserCards> modelUserCards){
        this.modelUserCards = modelUserCards;
    }

    public ArrayList<ModelInterFormList> getModelInterFormList(){
        return mChildData.getInterFormList();
    }

    @Override
    public void onBackPressed(){
        CURRENT_PAGE--;
        if(FRAGMENT_ID == 1){
            if(!childCashFragment.canClose()) childCashFragment.onBack();
            else{
                onRemoveFragment();
                FRAGMENT_ID = 0;
            }
        }
        else if(FRAGMENT_ID == 2){
            if(CURRENT_PAGE < 0) onRemoveFragment();
            else FullViewPager.setCurrentItem(CURRENT_PAGE);
        }
        else if(FRAGMENT_ID == 7){
            if(CURRENT_PAGE < 0) onRemoveFragment();
            else FullViewPager.setCurrentItem(CURRENT_PAGE);
        }
        else if(FRAGMENT_ID == 8){
            if(CURRENT_PAGE < 0) onRemoveFragment();
            else FullViewPager.setCurrentItem(CURRENT_PAGE);
        }
        else if(FRAGMENT_ID == 9){
            if(CURRENT_PAGE < 0) onRemoveFragment();
            else FullViewPager.setCurrentItem(CURRENT_PAGE);
        }
        else if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    public ModelPrivateAccount getPrivateAccount(){
        return PrivateAccount;
    }

    //
//        if (!listIsNullOrEmpty(mChildData.getSponsoredNotifications())){
//            for (int i = 0; i < mChildData.getSponsoredNotifications().size(); i++){
//                ModelSponsoredNotification m = mChildData.getSponsoredNotifications().get(i);
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
//                civAvatar.setVisibility(View.GONE);
//
//                if(!m.isCondAreMet()){
//                    tvTitle.setText("К сожалению,\nУсловия по спонсорству за " + m.getWeek() + " неделю НЕ исполнены.");
//                    tvTitle.setBackgroundResource(R.drawable.background_grade_red);
//                    tvTitle.setPadding(10,10,10,10);
//                    imageView2.setImageDrawable(getResources().getDrawable(R.drawable.img_sponsorship_not_done));
//                    btnOkey.setText("Ничего страшного, Продолжить.");
//                    btnOkey.setPadding(10,10,10,10);
//                }
//                else{
//                    tvTitle.setText("Поздравляем!\nУсловия за " + m.getWeek() + " неделю по спонсорству исполнены.");
//                }
//                alertDialog.show();
//
//                btnOkey.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        JsonObject jsonData = new JsonObject();
//                        jsonData.addProperty("Index", m.getIndex());
//                        jsonData.addProperty("UserType", login.loadUserDate(LoginKey.TYPE));
//
//                        String SOAP = SoapRequest(func_UpdateCheckedSponsored, jsonData.toString());
//                        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
//                        Request request = new Request.Builder().url(URL).post(body).build();
//                        HttpClient.newCall(request).enqueue(new Callback() {
//                            @Override
//                            public void onFailure(final Call call, IOException e) {
//                            }
//                            @Override
//                            public void onResponse(Call call, final Response response) throws IOException {
//                                if (response.code() >= 200 && response.code() <= 300) {
//                                    String xml = response.body().string();
//                                    String result = _Web.XMLReader(xml);
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            if (result.equals("1")) alertDialog.dismiss();
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
//        UserListAdapter = new ChildUserListAdapter(this, false);
//        UserListAdapter.setOnItemClickListener(this);
//        rvUserListAdapter.setAdapter(UserListAdapter);
//        UserListAdapter.updateData(mChildData);
    public void onRemoveFragment(){
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            if(fragment instanceof ChildEditProfileFragment
                    || fragment instanceof ChildChatFragment
                    || fragment instanceof ChildCashFragment
                    || fragment instanceof ChildAddParentListFragment
                    || fragment instanceof ChildParentProfileFragment
                    || fragment instanceof ChildAllSmartGradesFragment
                    || fragment instanceof ChildSmartLessonInfoFragment
                    || fragment instanceof ChildAllOtherLessonFragment
                    || fragment instanceof ChildOtherLessonInfoFragment
                    || fragment instanceof ChildAllTransactionFragment
                    || fragment instanceof ChildChatFragment
                    || fragment instanceof ChildSponsoringInfoFragment)
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        FRAGMENT_ID = 0;
    }

    public void onProfileEditFragment(){
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, new ChildEditProfileFragment()).addToBackStack("tag");
        ft.commit();
    }

    private void onMenu(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("UserId", CHILD_ID);

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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChildActivity.this);
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
                                                    new DAceessCode(ChildActivity.this, login.loadUserDate(LoginKey.TYPE), userList.get(i));
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
                            popupMenu.getMenu().add("Выйти").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem){
                                    login.deleteUserDate();
                                    startActivity(new Intent(ChildActivity.this, AuthActivity.class));
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

    private void onNavigationClick(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            drawer.openDrawer(GravityCompat.START);
        }
    }
    private void onNavEditClick(){
        onProfileEditFragment();
        drawer.closeDrawer(GravityCompat.START);

    }
    private void onNavPushClick(){
        boolean b = sNavPushTurnOffOrOn.isChecked();
        if(b){
            tvNavPushTurnOffOrOn.setText("Вкл.");
        }
        else{
            tvNavPushTurnOffOrOn.setText("Выкл.");
        }
        SharedPreferences sp = getApplicationContext().getSharedPreferences("SP_SETTINGS_MENTOR_NAV", 0);
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean("PUSH", b);
        spe.apply();
    }
    private void onNavQuickAccessCodeClick(){
        View view = getLayoutInflater().inflate(R.layout.fragment_access_enter, null, false);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
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
        tvAccessTitle.setText("Введите старый код доступа");

        view.findViewById(R.id.tvNum1).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ChildActivity.this, "1", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum2).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ChildActivity.this, "2", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum3).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ChildActivity.this, "3", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum4).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ChildActivity.this, "4", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum5).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ChildActivity.this, "5", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum6).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ChildActivity.this, "6", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum7).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ChildActivity.this, "7", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum8).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ChildActivity.this, "8", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum9).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ChildActivity.this, "9", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum0).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(ChildActivity.this, "0", dialog, ivDot, tvTitle);
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
                    alert.onToast("Ошибка: Пароли не совпадают");
                    return;
                }
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, CHILD_ID);
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
                                        alert.onToast("Ошибка: неверный пароль!");
                                    else if(result.equals("1")){
                                        dialog.dismiss();
                                        alert.onToast("Пароль успешно изменен.");
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
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Советую скачать приложение SmartGrades для родительского контроля.");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Пригласить друзей"));
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
                email.putExtra(Intent.EXTRA_SUBJECT, "Нужна помощь");
                email.putExtra(Intent.EXTRA_TEXT, "");
//need this to prompts email client only
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Выберите клиент Email"));
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
    private void onCashExtract(){
//        ChildDialogPrivateAccount Dialog = new ChildDialogPrivateAccount(this, PrivateAccount);
//        Dialog.show();
//        Dialog.setOnItemClickListener(new ChildDialogPrivateAccount.OnItemClickListener() {
//            @Override
//            public void onItemClick() {
//                Dialog.dismiss();
//            }
//        });
        boolean flag = true;
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            if(fragment instanceof ChildCashFragment){
                flag = false;
                break;
            }

        }
        if(flag){
            FRAGMENT_ID = 1;
            childCashFragment = new ChildCashFragment();
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container2, childCashFragment).commit();
        }
        onNavigationClick();
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
                onCashExtract();
                break;
            case R.id.cvEnroll:
                addParent();
                break;
            case R.id.cvShare:
                onShare();
                break;
            case R.id.cvShowMoreIncomeLessons:
                onShowMoreIncomeLessons();
                break;
            case R.id.cvShowMoreOtherLessons:
                onShowMoreOtherLessons();
                break;
            case R.id.btnCreateIncomeSource:
                askParent();
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

    private void onShare(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Прошу Вас скачать приложение SmartGrades для родительского контроля.");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Пригласить родителя"));
    }

    private void askParent(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Прошу создать предметы в приложении SmartGrades.\n" +
                "А если включите умные оценки, то у меня будет больше мотивации и я буду лучше учиться.");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Пригласить родителя"));
    }

    private void onShowMoreIncomeLessons(){
        FRAGMENT_ID = 8;
        childShowMoreIncomeLessonsAdapter = new ChildShowMoreIncomeLessonsAdapter(getSupportFragmentManager());

        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(childShowMoreIncomeLessonsAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(childShowMoreIncomeLessonsAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);

        childShowMoreIncomeLessonsAdapter.setLessonList(mChildData.getLessonsWithSmartGrades());
    }

    public void updatePresenter(){
        presenter.onStartPresenter();
    }

    private void onShowMoreOtherLessons(){
        FRAGMENT_ID = 8;
        childShowMoreOtherLessonsAdapter = new ChildShowMoreOtherLessonsAdapter(getSupportFragmentManager());

        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(childShowMoreOtherLessonsAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(childShowMoreOtherLessonsAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);

        childShowMoreOtherLessonsAdapter.setLessonList(mChildData.getLessonsWithoutSmartGrades());
    }

    private void addParent(){
        FRAGMENT_ID = 2;
        childAddParentFragmentAdapter = new ChildAddParentFragmentAdapter(getSupportFragmentManager());
        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(childAddParentFragmentAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(childAddParentFragmentAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);
//        ChildParentProfileFragment childParentProfileFragment = new ChildParentProfileFragment();
//        childAddParentFragmentAdapter.addFragment(childParentProfileFragment);
    }

    public void onNextFragment(){
        CURRENT_PAGE++;
        FullViewPager.setCurrentItem(CURRENT_PAGE);
    }

    public void onSetParentInfo(ModelUserData m){
        if(childAddParentFragmentAdapter != null){
            childAddParentFragmentAdapter.setModel(m);
        }
    }


    @Override
    public void onItemClick(ModelChildData m){
//        ModelChat mChat = new ModelChat();
//        mChat.setModel(m.getModel());
//        if (m.getModel().equals(F.PrivateChat)){
//            mChat.setChatId(m.getPrivateChat().getChatId());
//            mChat.setUserId(m.getPrivateChat().getTargetId());
//            mChat.setAvatar(m.getPrivateChat().getTargetAvatar());
//            mChat.setFirstName(m.getPrivateChat().getTargetFirstName());
//            mChat.setLastName(m.getPrivateChat().getTargetLastName());
//            mChat.setType(m.getPrivateChat().getTargetType());
//        }
//        else{
//            mChat.setChatId(m.getMentorData().getChatId());
//            mChat.setUserId(m.getMentorData().getMentorId());
//            mChat.setAvatar(m.getMentorData().getMentorAvatar());
//            mChat.setFirstName(m.getMentorData().getMentorFirstName());
//            mChat.setLastName(m.getMentorData().getMentorLastName());
//            mChat.setLessonName(m.getMentorData().getLessonName());
//        }
//        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.container2, ChildChatFragment.newInstance(mChat));
//        ft.commit();
    }

    @Override
    public void onCancelClick(ModelInterFormList modelInterFormList){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.Id, modelInterFormList.getId());
        String SOAP = SoapRequest(func_RejectInterFormInFamilyGroup, jsonData.toString());
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
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            if(!result.equals("null")){
                                ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                alert.onToast(answer.getMessage());
                                InviteListAdapter.cancel(modelInterFormList);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onEnrollClick(ModelInterFormList modelInterFormList){
        View view = getLayoutInflater().inflate(R.layout.pw_family_group_code, null);
        TextView tvTimer = view.findViewById(R.id.tvTimer);
        EditText etEnterCode = view.findViewById(R.id.etEnterCode);
        AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.Id, modelInterFormList.getId());
        String SOAP = SoapRequest(func_GetTimeInterFormInFamilyGroup, jsonData.toString());
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
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            if(!result.equals("null")){
                                String timer = new Gson().fromJson(result, String.class);
                                int timerInt = Integer.parseInt(timer) * 1000;

                                if(timerInt < 0){
                                    alert.onToast("Код недействителен");
                                }
                                else{
                                    alertDialog.show();
                                    new CountDownTimer(timerInt, 1000){
                                        public void onTick(long millisUntilFinished){
                                            SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                                            String time = df.format(millisUntilFinished);
                                            tvTimer.setText(time);
                                        }

                                        public void onFinish(){
                                            alertDialog.dismiss();
                                        }
                                    }.start();

                                    etEnterCode.addTextChangedListener(new TextWatcher(){
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable){
                                            if(editable.toString().length() == 4){
                                                acceptInvite(modelInterFormList, editable.toString());
                                                alertDialog.dismiss();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void acceptInvite(ModelInterFormList modelInterFormList, String code){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.Id, modelInterFormList.getId());
        jsonData.addProperty(F.Code, code);
        jsonData.addProperty(F.SourceId, CHILD_ID);
        jsonData.addProperty(F.ChildId, CHILD_ID);
        String SOAP = SoapRequest(func_AcceptInterFormInFamilyGroup, jsonData.toString());
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
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            if(!result.equals("null")){
                                ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                alert.onToast(answer.getMessage());
                                InviteListAdapter.cancel(modelInterFormList);
                                updatePresenter();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onSmartClick(ModelLessonsWithSmartGrades mLessonsWithSmartGrades){
        FRAGMENT_ID = 7;
        childShowIncomeLessonsFromActivityAdapter = new ChildShowIncomeLessonsFromActivityAdapter(getSupportFragmentManager());

        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(childShowIncomeLessonsFromActivityAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(childShowIncomeLessonsFromActivityAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);
        childShowIncomeLessonsFromActivityAdapter.setLessonsWithSmartGradesData(mLessonsWithSmartGrades);
    }

    @Override
    public void onSmartLongClickListener(ModelLessonsWithSmartGrades mLessonsWithSmartGrades){
    }

    @Override
    public void onOtherClick(ModelLessonsWithOutSmartGrades mLessonWithOutSmartGrades){
        FRAGMENT_ID = 8;
        childShowOtherLessonsFromActivityAdapter = new ChildShowOtherLessonsFromActivityAdapter(getSupportFragmentManager());

        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(childShowOtherLessonsFromActivityAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(childShowOtherLessonsFromActivityAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);
        childShowOtherLessonsFromActivityAdapter.setLessonsWithOutSmartGradesData(mLessonWithOutSmartGrades);
    }

    public void setLessonsWithSmartGradesData(ModelLessonsWithSmartGrades mLessonsWithSmartGrades){
        childShowMoreIncomeLessonsAdapter.setLessonsWithSmartGradesData(mLessonsWithSmartGrades);
    }

    public void setLessonsWithOutSmartGradesData(ModelLessonsWithOutSmartGrades mLessonsWithOutSmartGrades){
        childShowMoreOtherLessonsAdapter.setLessonsWithOutSmartGradesData(mLessonsWithOutSmartGrades);
    }

    @Override
    public void onTransactionClick(){
        FRAGMENT_ID = 9;
        childAllTransactionAdapter = new ChildAllTransactionAdapter(getSupportFragmentManager());
        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(childAllTransactionAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(childAllTransactionAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);

        childAllTransactionAdapter.setChildModel(mChildData, CHILD_ID);
    }

    public void onOpenChat(String lessonName, String lessonId){
        onNextFragment();
        if(FRAGMENT_ID == 7){
            if(childShowIncomeLessonsFromActivityAdapter != null)
                childShowIncomeLessonsFromActivityAdapter.setLessonData(lessonName, lessonId);
            else if(childShowMoreIncomeLessonsAdapter != null)
                childShowMoreIncomeLessonsAdapter.setLessonData(lessonName, lessonId);
        }
        else if(FRAGMENT_ID == 8){
            if(childShowMoreOtherLessonsAdapter != null)
                childShowMoreOtherLessonsAdapter.setLessonData(lessonName, lessonId);
            else if(childShowOtherLessonsFromActivityAdapter != null)
                childShowOtherLessonsFromActivityAdapter.setLessonData(lessonName, lessonId);
        }
    }

    //    public void AddFragment(int i) {
//        FragmentParentAddNewLesson fragmentParentAddNewLesson = new FragmentParentAddNewLesson();
//        childAddParentFragmentAdapter.addFragment(fragmentParentAddNewLesson);
//        CURRENT_PAGE = 1;
//        FullViewPager.setCurrentItem(CURRENT_PAGE);
//    }
}