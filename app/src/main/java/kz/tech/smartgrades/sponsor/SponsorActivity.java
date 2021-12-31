package kz.tech.smartgrades.sponsor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.App;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.AuthActivity;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.child.fragments.ChildCashFragment;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.custom_view.CustomViewPager;
import kz.tech.smartgrades.root.dialogs.DAceessCode;
import kz.tech.smartgrades.root.dialogs.DialogEditCode;
import kz.tech.smartgrades.root.dialogs.DialogEditPassword;
import kz.tech.smartgrades.root.fragments.IFragments;
import kz.tech.smartgrades.root.language.ILanguage;
import kz.tech.smartgrades.root.login.ILogin;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelPasswordEdit;
import kz.tech.smartgrades.sponsor.adapters.SponsorAddChildAdapter;
import kz.tech.smartgrades.sponsor.adapters.SponsorChatFragmentAdapter;
import kz.tech.smartgrades.sponsor.adapters.SponsorChildListForAddedAdapter;
import kz.tech.smartgrades.sponsor.adapters.SponsorUsersListAdapter;
import kz.tech.smartgrades.sponsor.adapters.SponsoringListAdapter;
import kz.tech.smartgrades.sponsor.dialogs.SponsorDialogChat;
import kz.tech.smartgrades.sponsor.fragments.SponsorCashExtractFragment;
import kz.tech.smartgrades.sponsor.fragments.SponsorCashFragment;
import kz.tech.smartgrades.sponsor.fragments.SponsorCashReplenishFragment;
import kz.tech.smartgrades.sponsor.fragments.SponsorChatFragment;
import kz.tech.smartgrades.sponsor.fragments.SponsorChatListFragment;
import kz.tech.smartgrades.sponsor.fragments.SponsorChildListFragment;
import kz.tech.smartgrades.sponsor.fragments.SponsorChildProfileFragment;
import kz.tech.smartgrades.sponsor.fragments.SponsorEditProfileFragment;
import kz.tech.smartgrades.sponsor.fragments.SponsorTransactionFragment;
import kz.tech.smartgrades.sponsor.fragments.SponsorWorksheetFragment;
import kz.tech.smartgrades.sponsor.fragments.SponsoringInfoFragment;
import kz.tech.smartgrades.sponsor.models.ModelChildrenListForSponsorship;
import kz.tech.smartgrades.sponsor.models.ModelPaymentResponse;
import kz.tech.smartgrades.sponsor.models.ModelPrivateAccount;
import kz.tech.smartgrades.sponsor.models.ModelSponsorChildrenList;
import kz.tech.smartgrades.sponsor.models.ModelSponsorData;
import kz.tech.smartgrades.sponsor.models.ModelSponsorUsersListAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.cloudpayments.sdk.three_ds.ThreeDSDialogListener;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetUser;
import static kz.tech.smartgrades._Web.func_GetUserAccess;
import static kz.tech.smartgrades._Web.func_Post3Ds;
import static kz.tech.smartgrades._Web.func_SetUserAccess;
import static kz.tech.smartgrades._Web.func_UserEditPassword;

public class SponsorActivity extends AppCompatActivity implements View.OnClickListener,
        SponsorUsersListAdapter.OnItemClickListener,
        ThreeDSDialogListener,
        SponsoringListAdapter.OnItemClickListener,
        SwipyRefreshLayout.OnRefreshListener{

    @Inject
    public ILanguage language;
    @Inject
    public IAlert alert;
    @Inject
    public ILogin login;
    @Inject
    public IFragments fragments;

    public SponsorPresenter presenter;
    private String SPONSOR_ID;
    private ModelSponsorData mSponsorData;
    private ModelPrivateAccount PrivateAccount;
    private DrawerLayout drawer;
    private NavigationView navigation;
    private CircleImageView civNavAvatar;
    private TextView tvNavFullName, tvNavLogin, tvNavPhoneOrMail;
    private ImageView ivNavEdit;

    private CustomViewPager FullViewPager;
    private static int CURRENT_PAGE = 0;
    private static int FRAGMENT_ID = 0;

    private TextView tvCurrentCash;
    private ImageView ivNav;
    private CircleImageView civAvatar;

    private ConstraintLayout clNoSponsoring;
    private Button btnSponsoring;

    private NestedScrollView svSponsoring;
    private RecyclerView rvSponsoringLessons;
    private TextView tvAddSponsoring;
    private RecyclerView rvSponsoringHistory;

    private SponsorAddChildAdapter sponsorAddChildAdapter;

    private TextView tvNavPushTurnOffOrOn;
    private Switch sNavPushTurnOffOrOn;
    private TextView tvNavQuickAccessCode;
    private TextView tvNavPassword;
    private TextView tvNavAppAbout;
    private TextView tvNavInviteFriends;
    private TextView tvNavSupport;
    private FrameLayout flCashAdd;

    public SponsorChildListForAddedAdapter childListForAddedAdapter;

    private FrameLayout flChat;
    private TextView tvNoCheckCount;

    private CircleImageView civSendMoney;

    private SponsorDialogChat dialogChat;
    private SponsorChatFragmentAdapter chatFragmentAdapter;
    private SponsorCashFragment sponsorCashFragment;

    private SponsoringListAdapter sponsoringListAdapter;

    private SponsoringInfoFragment sponsoringInfoFragment;

    private SwipyRefreshLayout swipeContainer;

    private ProgressBar progressbar;
    private ConstraintLayout clNoInternet;
    private CardView cvRefresh;
    private CountDownTimer noInternetTimer;

    private SponsorTransactionFragment sponsorTransactionFragment;
    private SponsorEditProfileFragment sponsorEditProfileFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        App.app().getComponent().inject(this);
        setContentView(R.layout.activity_sponsor_with_cards);
        initViews();
        setProfileData();
        initPresenter();
    }
    public BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String Tag = intent.getStringExtra("Tag");
            String Body = intent.getStringExtra("Body");
            if (!stringIsNullOrEmpty(Tag) && Tag.equals("ParentRejectChildRequest")) {
                alert.onToast(Body);
            }
            updatePresenter();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        updatePresenter();
        registerReceiver(myReceiver, new IntentFilter("update"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
        alert.hideKeyboard(this);
    }
    public void updatePresenter(){
        presenter.onStartPresenter();
        clNoInternet.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
        noInternetTimer = new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                clNoInternet.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @Override
    public void onBackPressed(){
        CURRENT_PAGE--;
        if (getSupportFragmentManager().getFragments().contains(sponsorTransactionFragment)
            || getSupportFragmentManager().getFragments().contains(sponsorCashFragment) ) {
            onRemoveFragment();
            return;
        }
        if(FRAGMENT_ID == 0) super.onBackPressed();
        else if(CURRENT_PAGE < 0) onRemoveFragment(FRAGMENT_ID);
        else if(CURRENT_PAGE >= 0) FullViewPager.setCurrentItem(CURRENT_PAGE);
        else if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else super.onBackPressed();
    }
    private void initViews(){
        progressbar = findViewById(R.id.progressbar);
        clNoInternet = findViewById(R.id.clNoInternet);
        cvRefresh = findViewById(R.id.cvRefresh);
        cvRefresh.setOnClickListener(this);

        navigation = findViewById(R.id.navigation);
        drawer = findViewById(R.id.drawer);
        SPONSOR_ID = login.loadUserDate(LoginKey.ID);
        FullViewPager = findViewById(R.id.FullViewPager);

        View navLayout = navigation.getHeaderView(0);
        civNavAvatar = navLayout.findViewById(R.id.civNavAvatar);
        tvNavFullName = navLayout.findViewById(R.id.tvNavFullName);
        tvNavLogin = navLayout.findViewById(R.id.tvNavLogin);
        tvNavPhoneOrMail = navLayout.findViewById(R.id.tvNavPhoneOrMail);
        ivNavEdit = navLayout.findViewById(R.id.ivNavEdit);
        ivNavEdit.setOnClickListener(this);

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
        flCashAdd = navLayout.findViewById(R.id.flCashAdd);
        flCashAdd.setOnClickListener(this);
        flCashAdd.setVisibility(View.VISIBLE);

        ivNav = findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        civAvatar = findViewById(R.id.civAvatar);
        civAvatar.setOnClickListener(this);
        tvCurrentCash = findViewById(R.id.tvCurrentCash);
        tvCurrentCash.setOnClickListener(this);
        ivNav = findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        civAvatar = findViewById(R.id.civAvatar);
        civAvatar.setOnClickListener(this);
        clNoSponsoring = findViewById(R.id.clNoSponsoring);
        btnSponsoring = findViewById(R.id.btnSponsoring);
        btnSponsoring.setOnClickListener(this);
        svSponsoring = findViewById(R.id.svSponsoring);
        rvSponsoringLessons = findViewById(R.id.rvSponsoringLessons);
        tvAddSponsoring = findViewById(R.id.tvAddSponsoring);
        tvAddSponsoring.setOnClickListener(this);
        rvSponsoringHistory = findViewById(R.id.rvSponsoringHistory);

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
    public void setProfileData(){
        String avatar = login.loadUserDate(LoginKey.AVATAR);
        if(avatar != null && !avatar.isEmpty()){
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civNavAvatar);
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

        SharedPreferences sp = getApplicationContext().getSharedPreferences("SP_SETTINGS_SPONSOR_NAV", 0);
        Boolean b = sp.getBoolean("PUSH", true);
        sNavPushTurnOffOrOn.setChecked(b);
    }
    private void initPresenter(){
        presenter = new SponsorPresenter(this);
        presenter.onStartPresenter();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(presenter != null) presenter.onDestroyView();
    }
    public ModelPrivateAccount getPrivateAccount(){
        return PrivateAccount;
    }

    public void setPrivateAccount(ModelPrivateAccount PrivateAccount){
        if(PrivateAccount == null) return;
        this.PrivateAccount = PrivateAccount;
    }
    public void onSetSponsorData(ModelSponsorData mSponsorData){
        this.mSponsorData = mSponsorData;
        if(listIsNullOrEmpty(mSponsorData.getChildrenList())){
            clNoSponsoring.setVisibility(View.VISIBLE);
            svSponsoring.setVisibility(View.GONE);
            swipeContainer.setVisibility(View.GONE);
        }
        else{
            clNoSponsoring.setVisibility(View.GONE);
            svSponsoring.setVisibility(View.VISIBLE);
            swipeContainer.setVisibility(View.VISIBLE);
            setSponsoredAdapter(mSponsorData.getChildrenList());
            setSponsoredHistoryAdapter();
        }
        if (PrivateAccount != null){
            if (!stringIsNullOrEmpty(PrivateAccount.getCurrentSum()))
                tvCurrentCash.setText(PrivateAccount.getCurrentSum() + " " + PrivateAccount.getAccountType());
            else tvCurrentCash.setText("₸ 0");
            if (sponsorCashFragment != null) {
                sponsorCashFragment.updateCards();
            }
        }

        progressbar.setVisibility(View.GONE);
        if (noInternetTimer != null) noInternetTimer.cancel();
    }

    private void setSponsoredHistoryAdapter(){
        //проверка на наличие
    }

    private void setSponsoredAdapter(ArrayList<ModelSponsorChildrenList> childrenList){
        sponsoringListAdapter = new SponsoringListAdapter(this);
        sponsoringListAdapter.setOnItemClickListener(this);
        rvSponsoringLessons.setVisibility(View.VISIBLE);
        rvSponsoringLessons.setLayoutManager(new GridLayoutManager(this, 2));
        rvSponsoringLessons.setAdapter(sponsoringListAdapter);
        rvSponsoringLessons.suppressLayout(true);
        sponsoringListAdapter.updateData(childrenList);
    }

    @Override
    public void onAuthorizationCompleted(String md, String paRes){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("TransactionId", md);
        jsonData.addProperty("PaRes", paRes);

        String SOAP = SoapRequest(func_Post3Ds, jsonData.toString());
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
                    String result = _Web.XMLToJson(xml);
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            if(result.equals("0")){
                            }
                            else{
                                ModelPaymentResponse m = new Gson().fromJson(result, ModelPaymentResponse.class);
                                alert.onToast(m.getModel().getCardHolderMessage());
                                if (m.isSuccess()) {
                                    updatePresenter();
                                }
                            }
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onAuthorizationFailed(String html){
        alert.onToast(getString(R.string.Error_Failed_to_authenticate));
    }

    public void onNextFragment(){
        CURRENT_PAGE++;
        FullViewPager.setCurrentItem(CURRENT_PAGE);
    }
    public void onNextFragment(int frag, ModelSponsorUsersListAdapter m){
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        switch(frag){
            case 1:
                ft.replace(R.id.container2, new SponsorWorksheetFragment());
                break;
            case 2:
                ft.replace(R.id.container2, SponsorChatFragment.newInstance(m));
                break;
        }
        ft.commit();
    }
    public void onRemoveFragment(){
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            if(fragment instanceof SponsorWorksheetFragment
            || fragment instanceof SponsorChatFragment
            || fragment instanceof SponsorCashFragment
            || fragment instanceof SponsorCashExtractFragment
            || fragment instanceof SponsorCashReplenishFragment
            || fragment instanceof SponsoringInfoFragment
            || fragment instanceof SponsorTransactionFragment
            || fragment instanceof SponsorEditProfileFragment)
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }
    public void onRemoveFragment(int frag){
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            switch(frag){
                case 1:
                    if(fragment instanceof SponsorChatListFragment || fragment instanceof SponsorChatFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    break;
                case 2:
                    if(fragment instanceof SponsorChildListFragment || fragment instanceof SponsorChildProfileFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    break;
            }
        }
    }

    public void onProfileEditFragment(){
        sponsorEditProfileFragment = new SponsorEditProfileFragment();
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, sponsorEditProfileFragment).addToBackStack("tag");
        ft.commit();
    }

    public void setChildModel(ModelChildrenListForSponsorship mChild){
        sponsorAddChildAdapter.setChildModel(mChild);
    }

    private void onAddChildForSponsored(){
        FRAGMENT_ID = 2;
        sponsorAddChildAdapter = new SponsorAddChildAdapter(getSupportFragmentManager());
        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(sponsorAddChildAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(sponsorAddChildAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);
    }

    public ModelSponsorData getMSponsorData(){
        return mSponsorData;
    }

    @Override
    public void onItemClick(ModelSponsorUsersListAdapter m){
        onNextFragment(2, m);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.civAvatar:
                onMenu(v);
                civAvatar.setClickable(false);
                break;
            case R.id.civAddContact:
            case R.id.btnSponsoring:
            case R.id.tvAddSponsoring:
                if (false) {
                //if (Integer.parseInt(getPrivateAccount().getCurrentSum()) < 42500) {
                    View alertView = getLayoutInflater().inflate(R.layout.pw_top_up, null);
                    AlertDialog alertDialog = new AlertDialog.Builder(this).setView(alertView).create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    TextView tvCancel = alertView.findViewById(R.id.tvCancel);
                    TextView tvTopUp = alertView.findViewById(R.id.tvTopUp);
                    tvCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    tvTopUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onNavCashAdd();
                            closeDrawer();
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                    break;
                }
                onAddChildForSponsored();
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
            case R.id.flCashAdd:
                onNavCashAdd();
                break;
            case R.id.cvRefresh:
                updatePresenter();
                break;
            case R.id.tvCurrentCash:
                onSponsorTransactions();
                break;
        }
    }

    private void onSponsorTransactions() {
        sponsorTransactionFragment = new SponsorTransactionFragment();
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, sponsorTransactionFragment).commit();
    }

    public void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    private void onNavigationClick(){
        if(drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else drawer.openDrawer(GravityCompat.START);
    }
    private void onNavEditClick(){
        onProfileEditFragment();
        drawer.closeDrawer(GravityCompat.START);
    }
    private void onNavPushClick(){
//        boolean b = sNavPushTurnOffOrOn.isChecked();
//        if(b){
//            tvNavPushTurnOffOrOn.setText("Вкл.");
//        }
//        else{
//            tvNavPushTurnOffOrOn.setText("Выкл.");
//        }
//        SharedPreferences sp = getApplicationContext().getSharedPreferences("SP_SETTINGS_MENTOR_NAV", 0);
//        SharedPreferences.Editor spe = sp.edit();
//        spe.putBoolean("PUSH", b);
//        spe.apply();
    }
    private void onNavQuickAccessCodeClick(){
        DialogEditCode editCode = new DialogEditCode(this);
        editCode.setOnItemClickListener(new DialogEditCode.OnItemClickListener(){
            @Override
            public void onCheckCode(String code){
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, login.loadUserDate(LoginKey.ID));
                jsonData.addProperty(F.Code, code);

                String SOAP = SoapRequest(func_GetUserAccess, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback(){
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e){ }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException{
                        if(response.isSuccessful()){
                            String result = _Web.XMLToJson(response.body().string());
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    editCode.onSetAnswer(answer);
                                }
                            });
                        }
                    }
                });
            }
            @Override
            public void onSaveCode(String code){
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, login.loadUserDate(LoginKey.ID));
                jsonData.addProperty(F.Code, code);

                String SOAP = SoapRequest(func_SetUserAccess, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback(){
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e){ }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException{
                        if(response.isSuccessful()){
                            String result = _Web.XMLToJson(response.body().string());
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    alert.onToast(answer.getMessage());
                                }
                            });
                        }
                    }
                });
            }
        });
    }
    private void onNavPasswordClick(){
        DialogEditPassword editPassword = new DialogEditPassword(this);
        editPassword.setOnItemClickListener(new DialogEditPassword.OnItemClickListener(){
            @Override
            public void onOk(ModelPasswordEdit mPassword){
                mPassword.setUserId(SPONSOR_ID);
                String SOAP = SoapRequest(func_UserEditPassword, new Gson().toJson(mPassword));
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback(){
                    @Override
                    public void onFailure(final Call call, IOException e){ }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException{
                        if(response.isSuccessful()){
                            String result = _Web.XMLToJson(response.body().string());
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    if(answer.isSuccess()) editPassword.dismiss();
                                    alert.onToast(answer.getMessage());
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
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Советую скачать приложение Smart Grades для родительского контроля с PlayMarket");
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
    private void onNavCashAdd(){
//        SponsorDialogReplenish Dialog = new SponsorDialogReplenish(this, PrivateAccount);
//        Dialog.show();
//        Dialog.setOnItemClickListener(new SponsorDialogReplenish.OnItemClickListener(){
//            @Override
//            public void onItemClick(){
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
            sponsorCashFragment = SponsorCashFragment.newInstance(PrivateAccount);
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container2, sponsorCashFragment).commit();
        }
        onNavigationClick();
    }

    private void onMenu(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
                civAvatar.setClickable(true);
            }
        });

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("UserId", SPONSOR_ID);

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
                    String result = _Web.XMLToJson(response.body().string());
                    ArrayList<ModelUser> userList = new Gson().fromJson(result, new TypeToken<ArrayList<ModelUser>>(){
                    }.getType());
                    for(int i = 0; i < userList.size(); i++){
                        popupMenu.getMenu().add(userList.get(i).getLogin()).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
                            @Override
                            public boolean onMenuItemClick(MenuItem item){
                                AlertDialog.Builder builder = new AlertDialog.Builder(SponsorActivity.this);
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
                                                new DAceessCode(SponsorActivity.this, login.loadUserDate(LoginKey.TYPE), userList.get(i));
                                                break;
                                            }

                                    }
                                });
                                alertDialog.show();
                                return false;
                            }
                        });
                    }
                    runOnUiThread(new Runnable(){
                        public void run(){
                            popupMenu.getMenu().add(getResources().getString(R.string.Log_off)).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem){
                                    login.deleteUserDate();
                                    startActivity(new Intent(SponsorActivity.this, AuthActivity.class));
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
    public void onClick(ModelSponsorChildrenList child) {
        boolean flag = true;
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            if(fragment instanceof ChildCashFragment){
                flag = false;
                break;
            }
        }
        if(flag){
            sponsoringInfoFragment = new SponsoringInfoFragment(child.getSponsorship(), child);
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container2, sponsoringInfoFragment).commit();
        }
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeContainer.setRefreshing(false);
                updatePresenter();
            }
        }, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            try{
                sponsorEditProfileFragment.prepareAvatar(resultUri);
            } catch(Exception ignored){}
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addCard() {
        sponsorCashFragment.openBindWindow();
    }

    public void onSuccessExtract() {
        if (sponsorCashFragment != null) {
            sponsorCashFragment.onSuccessExtract();
        }
    }
}