package kz.tech.smartgrades.mentor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

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
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.mentor.adapters.MentorAddSchoolFragmentAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorAddSchoolListAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorAddStudentFragmentAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorChatAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorClassAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorClassesFragmentAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorGroupAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorRequestAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorTabPagerFragmentAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorUserListAdapter;
import kz.tech.smartgrades.mentor.fragments.MentorAddSchoolListFragment;
import kz.tech.smartgrades.mentor.fragments.MentorAddSchoolProfileFragment;
import kz.tech.smartgrades.mentor.fragments.MentorAddStudentListFragment;
import kz.tech.smartgrades.mentor.fragments.MentorAddStudentProfileFragment;
import kz.tech.smartgrades.mentor.fragments.MentorChatFragment;
import kz.tech.smartgrades.mentor.fragments.MentorClassessFragment;
import kz.tech.smartgrades.mentor.fragments.MentorEditProfileFragment;
import kz.tech.smartgrades.mentor.fragments.MentorLessonsFragment;
import kz.tech.smartgrades.mentor.fragments.MentorRequestFragment;
import kz.tech.smartgrades.mentor.fragments.MentorSchoolsFragment;
import kz.tech.smartgrades.mentor.fragments.MentorStudentsForGradesFragment;
import kz.tech.smartgrades.mentor.fragments.MentorStudentsFragment;
import kz.tech.smartgrades.mentor.models.ModelMentorChat;
import kz.tech.smartgrades.mentor.models.ModelMentorClass;
import kz.tech.smartgrades.mentor.models.ModelMentorClassesColumn;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.mentor.models.ModelMentorGroups;
import kz.tech.smartgrades.mentor.models.ModelMentorStudents;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.custom_view.CustomViewPager;
import kz.tech.smartgrades.root.dialogs.DialogEditCode;
import kz.tech.smartgrades.root.dialogs.DialogEditPassword;
import kz.tech.smartgrades.root.language.ILanguage;
import kz.tech.smartgrades.root.login.ILogin;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelPasswordEdit;
import kz.tech.smartgrades.school.models.ModelSchoolData;
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
import static kz.tech.smartgrades._Web.func_GetUser;
import static kz.tech.smartgrades._Web.func_GetUserAccess;
import static kz.tech.smartgrades._Web.func_SetUserAccess;
import static kz.tech.smartgrades._Web.func_UserEditPassword;

public class MentorActivity extends AppCompatActivity implements View.OnClickListener,
        MentorUserListAdapter.OnItemClickListener,
        MentorGroupAdapter.OnItemClickListener,
        MentorClassAdapter.OnItemClickListener,
        MentorAddSchoolListAdapter.OnItemCLickListener, MentorChatAdapter.OnItemClickListener, MentorRequestAdapter.OnItemClickListener {

    @Inject
    public ILanguage language;
    @Inject
    public IAlert alert;
    @Inject
    public ILogin login;

    private CircleImageView civAvatar;

    private LinearLayout llClassess, llSchools, llStudents, llItems;
    private TextView tvClassCount, tvStudentsCount, tvTeacherCount, tvLessonsCount;

    private CardView cvSearch;
    private ImageView ivSearchShow, ivSearchHide;
    private EditText etSearch;

    private ImageView ivNav;
    private DrawerLayout drawer;
    private NavigationView navigation;
    private CircleImageView civNavAvatar;
    private TextView tvNavFullName, tvNavLogin, tvNavPhoneOrMail;
    private ImageView ivNavEdit;
    private TextView tvNavPushTurnOffOrOn;
    private Switch sNavPushTurnOffOrOn;
    private TextView tvNavQuickAccessCode;
    private TextView tvNavPassword;
    private TextView tvNavInviteFriends;
    private TextView tvNavSupport;

    public MentorPresenter presenter;
    private String MENTOR_ID;
    private ModelMentorData mMentorData;

    private MentorClassesFragmentAdapter classesFragmentAdapter;
    private CustomViewPager viewPager;
    private int CURRENT_PAGE;
    private MentorStudentsFragment studentsFragment;
    private MentorSchoolsFragment schoolsFragment;
    private MentorLessonsFragment lessonsFragment;
    private MentorRequestFragment requestFragment;

    private int FRAGMENT_ID = 0;
    private MentorAddStudentFragmentAdapter adapter;
    private MentorAddSchoolFragmentAdapter addSchoolFragmentAdapter;
    private MentorTabPagerFragmentAdapter TabAdapter;
    private MentorChatFragment chatFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.app().getComponent().inject(this);
        setContentView(R.layout.activity_mentor_v2);
        initViews();
        onSetMentorData(null);
        initPresenter();
    }
    private void initViews() {
        MENTOR_ID = login.loadUserDate(LoginKey.ID);

        cvSearch = findViewById(R.id.cvSearch);
        ivSearchShow = findViewById(R.id.ivSearchShow);
        ivSearchShow.setOnClickListener(this);
        ivSearchHide = findViewById(R.id.ivSearchHide);
        ivSearchHide.setOnClickListener(this);
        etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
//                StudentsAdapter.onFilter(editable.toString());
//                TempText = editable.toString();
            }
        });

        civAvatar = findViewById(R.id.civAvatar);
        civAvatar.setOnClickListener(this);
        llClassess = findViewById(R.id.llClassess);
        llClassess.setOnClickListener(this);
        llSchools = findViewById(R.id.llSchools);
        llSchools.setOnClickListener(this);
        llStudents = findViewById(R.id.llStudents);
        llStudents.setOnClickListener(this);
        llClassess = findViewById(R.id.llClassess);
        llClassess.setOnClickListener(this);
        llItems = findViewById(R.id.llItems);
        llItems.setOnClickListener(this);
        tvClassCount = findViewById(R.id.tvClassCount);
        tvStudentsCount = findViewById(R.id.tvStudentsCount);
        tvTeacherCount = findViewById(R.id.tvTeacherCount);
        tvLessonsCount = findViewById(R.id.tvLessonsCount);

        navigation = findViewById(R.id.navigation);
        drawer = findViewById(R.id.drawer);
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
//        tvNavAppAbout = navLayout.findViewById(R.id.tvNavAppAbout);
//        tvNavAppAbout.setOnClickListener(this);
        tvNavInviteFriends = navLayout.findViewById(R.id.tvNavInviteFriends);
        tvNavInviteFriends.setOnClickListener(this);
        tvNavSupport = navLayout.findViewById(R.id.tvNavSupport);
        tvNavSupport.setOnClickListener(this);
        ivNav = findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);

        viewPager = findViewById(R.id.viewPager);

        TabAdapter = new MentorTabPagerFragmentAdapter(getSupportFragmentManager(), MentorActivity.this, false);
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(TabAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void initPresenter() {
        presenter = new MentorPresenter(this);
        presenter.onStartPresenter();
    }
    public void restartPresenter() {
        presenter.onStartPresenter();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.onDestroyView();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void onSetMentorData(ModelMentorData model) {
        if (model != null) {
            mMentorData = model;

            int ClassCount = 0;
            if (!listIsNullOrEmpty(mMentorData.getColumns())) {
                for (ModelMentorClassesColumn _column : mMentorData.getColumns())
                    if (!listIsNullOrEmpty(_column.getClasses()))
                        ClassCount += _column.getClasses().size();
            }
            tvClassCount.setText(String.valueOf(ClassCount));

            int StudentsCount = 0;
            if (!listIsNullOrEmpty(mMentorData.getStudents()))
                for (ModelMentorStudents _students : mMentorData.getStudents()) {
                    if (!listIsNullOrEmpty(_students.getStudents()))
                        StudentsCount += _students.getStudents().size();
                }
            tvStudentsCount.setText(String.valueOf(StudentsCount));

            if (!listIsNullOrEmpty(mMentorData.getSchools()))
                tvTeacherCount.setText(String.valueOf(mMentorData.getSchools().size()));

            if (!listIsNullOrEmpty(mMentorData.getLessons())) {
                tvLessonsCount.setText(String.valueOf(mMentorData.getLessons().size()));
                if (lessonsFragment != null) lessonsFragment.updateData(mMentorData);
            }

            if (classesFragmentAdapter != null)
                classesFragmentAdapter.setMentorData(mMentorData);

            TabAdapter.setData(model.getInterForms());
            TabAdapter.onSetModelMentorData(mMentorData);
        }
        else {
            String avatar = login.loadUserDate(LoginKey.AVATAR);
            if (!stringIsNullOrEmpty(avatar)) {
                Picasso.get().load(avatar).fit().centerInside().into(civAvatar);
                Picasso.get().load(avatar).fit().centerInside().into(civNavAvatar);
            }
            else {
                civAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
                civNavAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
            }

            String fullName = login.loadUserDate(LoginKey.FIRST_NAME) + " " + login.loadUserDate(LoginKey.LAST_NAME);
            if (!stringIsNullOrEmpty(fullName)) tvNavFullName.setText(fullName);

            String Login = login.loadUserDate(LoginKey.LOGIN);
            if (!stringIsNullOrEmpty(Login)) tvNavLogin.setText(Login);

            String Phone = login.loadUserDate(LoginKey.PHONE);
            if (!stringIsNullOrEmpty(Phone)) tvNavPhoneOrMail.setText(Phone);
        }
    }
    public ArrayList<ModelMentorGroups> getMentorGroups() {
        return null;
    }

    public void onNextFragment() {
        CURRENT_PAGE++;
        viewPager.setCurrentItem(CURRENT_PAGE);
    }
    public void onNextFragment(int frag, ModelMentorChat modelMentorChat) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        switch (frag) {
            case 1:
                ft.replace(R.id.container2, new MentorEditProfileFragment()).addToBackStack("tag");
                break;
            /*case 2:
                ft.replace(R.id.container2, MentorChatFragment.newInstance(modelMentorChat, lastSelectGroup));
                break;*/
            case 2:
                //ft.replace(R.id.container2, MentorGradesFragment.newInstance(modelMentorChat, lastSelectGroup));
                break;
        }
        ft.commit();
    }
    public boolean onRemoveFragment() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof MentorEditProfileFragment || fragment instanceof MentorChatFragment) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                return true;
            }
        }
        return false;
    }
    public void onRemoveFragment(int fragmentId) {
        for (Fragment fragment : getSupportFragmentManager().getFragments())
            switch (fragmentId) {
                case 1:
                    if (fragment instanceof MentorStudentsForGradesFragment || fragment instanceof MentorClassessFragment) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        FRAGMENT_ID = 0;
                    }
                    break;
                case 2:
                    if (fragment instanceof MentorStudentsFragment) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        FRAGMENT_ID = 0;
                    }
                    break;
                case 3:
                    if (fragment instanceof MentorSchoolsFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    break;
                case 4:
                    if (fragment instanceof MentorLessonsFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    break;
                case 5:
                    if (fragment instanceof MentorAddStudentProfileFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    else if (fragment instanceof MentorAddStudentListFragment) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        FRAGMENT_ID = 2;
                    }
                    break;
                case 6:
                    if (fragment instanceof MentorAddSchoolProfileFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    else if (fragment instanceof MentorAddSchoolListFragment) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        FRAGMENT_ID = 3;
                    }
                    break;
                case 7:
                    if (fragment instanceof MentorChatFragment) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        FRAGMENT_ID = 3;
                    }
                    break;
                case 8:
                    if (fragment instanceof MentorRequestFragment) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        FRAGMENT_ID = 0;
                    }
                    break;
            }
    }

    //МЕНЮ ДЛЯ ПЕРЕХОДА НА ДРУГОЙ АККАУНТ
    private void onMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
                civAvatar.setClickable(true);
            }
        });

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("UserId", MENTOR_ID);

        String SOAP = SoapRequest(func_GetUser, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLToJson(response.body().string());
                    ArrayList<kz.tech.smartgrades.auth.models.ModelUser> userList = new Gson().fromJson(result, new TypeToken<ArrayList<kz.tech.smartgrades.auth.models.ModelUser>>() {
                    }.getType());
                    /*if (!listIsNullOrEmpty(userList)) {
                        for (int i = 0; i < userList.size(); i++) {
                            popupMenu.getMenu().add(userList.get(i).getLogin()).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ParentActivity.this);
                                    View viewAlert = getLayoutInflater().inflate(R.layout.ad_access_code, null);
                                    builder.setView(viewAlert);
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                    ((TextView) viewAlert.findViewById(R.id.tvText)).setText(getResources().getString(R.string.accept_change_acc));
                                    viewAlert.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            alertDialog.dismiss();
                                        }
                                    });
                                    viewAlert.findViewById(R.id.tvOkay).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            alertDialog.dismiss();
                                            for (int i = 0; i < userList.size(); i++)
                                                if (userList.get(i).getLogin().equals(item.toString())) {
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
                    }*/
                    runOnUiThread(new Runnable() {
                        public void run() {
                            popupMenu.getMenu().add(getResources().getString(R.string.Log_off)).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {
                                    login.deleteUserDate();
                                    startActivity(new Intent(MentorActivity.this, AuthActivity.class));
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

    public void setChatModel(ModelMentorChat m) {
        /*if(chatFragmentAdapter != null){
            chatFragmentAdapter.setChatModel(m);
        }*/
    }
    private void ivChat() {
//        dialogChat = new MentorDialogChat(this, modelMentorData);
//        dialogChat.show();
        /*FRAGMENT_ID = 1;
        chatFragmentAdapter = new MentorChatFragmentAdapter(getSupportFragmentManager());
        chatFragmentAdapter.setData(modelMentorData);
        CURRENT_PAGE = 0;
        FullViewPager.removeAllViews();
        FullViewPager.setAdapter(chatFragmentAdapter);
        FullViewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        FullViewPager.setOffscreenPageLimit(chatFragmentAdapter.getCount());
        FullViewPager.setCurrentItem(CURRENT_PAGE);*/

    }
    public void ChatVisible(boolean show) {
        /*if(isReopenDialog){
            isReopenDialog = false;
            onItemClick(0, mChat);
        }
        else{
            if(show) dialogChat.show();
            else dialogChat.hide();
        }*/
    }

    @Override
    public void onItemClick(ModelSchoolData m) {
        addSchoolFragmentAdapter.setSelectSchool(m);
        onNextFragment();
//        onSetSelectStudentData(m);

    }
    @Override
    public void onItemClick(ModelMentorGroups m) {
        /*lastSelectGroup = m;
        onGetGroupSpinner();
        tvGroupName.setText(lastSelectGroup.getGroupName());
        mentorUserListAdapter.selectList(lastSelectGroup.getGroupId());
        if(lastSelectGroup.getChildrenList() != null && lastSelectGroup.getChildrenList().size() > 0)
            ivSendMessageToGroup.setVisibility(View.VISIBLE);
        else ivSendMessageToGroup.setVisibility(View.GONE);/*
    }
    //НАЧИСЛЕНИЕ ОЦЕНКИ ИЛИ ОТКРЫТИЕ ЧАТА
    @Override
    public void onItemClick(int i, ModelMentorChat mChat){
        /*if(onHideGroupList()) return;
        if(i == 0){//НАЧИСЛЕНИЕ ОЦЕНКИ
            this.mChat = mChat;
            BSDGrading bsdGrading = new BSDGrading(this, mChat.getFirstName() + " " + mChat.getLastName(), "Mentor");
            bsdGrading.show();
            bsdGrading.setOnItemClickListener(new BSDGrading.OnItemClickListener(){
                @Override
                public void onGradeClick(int type, String grade){
                    bsdGrading.dismiss();
                    SendGrade(MentorActivity.this, MENTOR_ID, mChat.getUserId(), lastSelectGroup.getGroupId(), lastSelectGroup.getLessonId(), type, grade, mChat.getChatId());
                }
                @Override
                public void onChatClick(){
                    ModelMentorChat modelMentorChat = new ModelMentorChat();
                    modelMentorChat.setChatId(mChat.getChatId());
                    modelMentorChat.setUserId(mChat.getSourceId());
                    modelMentorChat.setAvatar(mChat.getAvatar());
                    modelMentorChat.setFirstName(mChat.getFirstName());
                    modelMentorChat.setLastName(mChat.getLastName());
                    modelMentorChat.setType("PrivateChat");
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container2, MentorChatFragment.newInstance(modelMentorChat, lastSelectGroup)).commit();
                    isReopenDialog = true;
                    bsdGrading.dismiss();
                }
            });
        }
        else onNextFragment(2, mChat);*/
    }
    @Override
    public void onItemClick(int i, ModelMentorChat m) {

    }

    @Override
    public void onChatClick(ModelMentorChat m) {
        FRAGMENT_ID = 7;
        chatFragment = new MentorChatFragment();
        chatFragment.onSetChatData(m);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, chatFragment).commit();
    }

    @Override
    public void onSelectClass(ModelMentorClass m) {
        classesFragmentAdapter.setSelectClass(m);
        onNextFragment();
        classesFragmentAdapter.onShowDialogSelectLesson();
    }

    @Override
    public void onRequestAcceptClick(ModelInterForm m) {
        FRAGMENT_ID = 8;//INTER FORM
        requestFragment = MentorRequestFragment.newInstance(m, mMentorData);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, requestFragment).commit();
    }
    @Override
    public void onCancelAcceptClick(ModelInterForm m) {
//        JsonObject jsonData = new JsonObject();
//        jsonData.addProperty(F.Id, m.getId());
//        jsonData.addProperty(F.SourceId, MENTOR_ID);
//
//        String SOAP = SoapRequest(func_RejectInterFormOfMentoring, jsonData.toString());
//        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
//        Request request = new Request.Builder().url(URL).post(body).build();
//        HttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(final Call call, IOException e) { }
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String result = _Web.XMLReader(response.body().string());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
//                            alert.onToast(answer.getMessage());
//                            if(answer.isSuccess()) adapter.removeItem(m);
//                        }
//                    });
//                }
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llClassess:
                onClassessClick();
                break;
            case R.id.llStudents:
                onStudentsClick();
                break;
            case R.id.llSchools:
                onSchoolsClick();
                break;
            case R.id.llItems:
                onLessonClick();
                break;

            case R.id.civAvatar:
                onMenu(v);
                civAvatar.setClickable(false);
                break;
            case R.id.ivSearchShow:
                onSearchShowClick();
                break;
            case R.id.ivSearchHide:
                onSearchHideClick();
                break;

            case R.id.ivNav:
                onNavigationClick();
                break;
            case R.id.sNavPushTurnOffOrOn:
                onNavPushClick();
                break;
            case R.id.tvNavQuickAccessCode:
                onNavAccessCodeClick();
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
        }
    }
    private void onSearchShowClick() {
        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = 0;
        cvSearch.setLayoutParams(params);
        ivSearchShow.setVisibility(View.GONE);
        etSearch.setVisibility(View.VISIBLE);
        ivSearchHide.setVisibility(View.VISIBLE);
    }
    private void onSearchHideClick() {
        if (ivSearchShow.getVisibility() == View.VISIBLE) return;
        etSearch.setText("");
        alert.hideKeyboard(this);
        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        cvSearch.setLayoutParams(params);
        ivSearchShow.setVisibility(View.VISIBLE);
        etSearch.setVisibility(View.GONE);
        ivSearchHide.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        alert.hideKeyboard(this);
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else if (FRAGMENT_ID > 0) {
            CURRENT_PAGE--;
            if (CURRENT_PAGE < 0) onRemoveFragment(FRAGMENT_ID);
            else viewPager.setCurrentItem(CURRENT_PAGE);
        }
        else super.onBackPressed();
    }

    private void onClassessClick() {
        FRAGMENT_ID = 1;//CLASSES
        classesFragmentAdapter = new MentorClassesFragmentAdapter(getSupportFragmentManager());
        classesFragmentAdapter.setMentorData(mMentorData);

        CURRENT_PAGE = 0;
        viewPager.setAdapter(classesFragmentAdapter);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        viewPager.setOffscreenPageLimit(classesFragmentAdapter.getCount());
        viewPager.setCurrentItem(CURRENT_PAGE);
    }
    private void onStudentsClick() {
        FRAGMENT_ID = 2;//STUDENTS
        studentsFragment = MentorStudentsFragment.newInstance(mMentorData);
        studentsFragment.onSetData(mMentorData);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, studentsFragment).commit();
    }
    private void onSchoolsClick() {
        FRAGMENT_ID = 3;//SCHOOLS
        /*if(!listIsNullOrEmpty(mMentorData.getSchools()))
            for(ModelSchoolData _lesson : mMentorData.getSchools()){
                if(listIsNullOrEmpty(_lesson.getStudents())) _lesson.setStudents(new ArrayList<>());
                for(ModelSchoolStudent _student : mMentorData.getStudents())
                    if(_student.getClassId().equals(_lesson.getClassId()))
                        _lesson.getStudents().add(_student);
            }*/

        schoolsFragment = new MentorSchoolsFragment();
        schoolsFragment.setData(mMentorData);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, schoolsFragment).commit();
    }
    private void onLessonClick() {
        FRAGMENT_ID = 4;//LESSONS
        lessonsFragment = MentorLessonsFragment.newInstance(mMentorData);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, lessonsFragment).commit();
    }

    public void onAddStudentClick() {
        FRAGMENT_ID = 5;//ADD STUDENT
        adapter = new MentorAddStudentFragmentAdapter(getSupportFragmentManager());
        adapter.setMentorData(mMentorData);

        CURRENT_PAGE = 0;
        viewPager.removeAllViews();
        viewPager.setAdapter(adapter);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setCurrentItem(CURRENT_PAGE);
    }
    public void onAddSchoolClick() {
        FRAGMENT_ID = 6;//ADD SCHOOL
        addSchoolFragmentAdapter = new MentorAddSchoolFragmentAdapter(getSupportFragmentManager());

        CURRENT_PAGE = 0;
        viewPager.removeAllViews();
        viewPager.setAdapter(addSchoolFragmentAdapter);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        viewPager.setOffscreenPageLimit(addSchoolFragmentAdapter.getCount());
        viewPager.setCurrentItem(CURRENT_PAGE);
    }
    public void onSetSelectStudentData(ModelUser m) {
        adapter.onSetSelectStudentData(m);
    }

    //NAVIGATION
    private void onNavigationClick() {
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else drawer.openDrawer(GravityCompat.START);
    }
    private void onNavEditClick() {
        onNextFragment(1, null);
        drawer.closeDrawer(GravityCompat.START);
    }
    private void onNavPushClick() {
        boolean b = sNavPushTurnOffOrOn.isChecked();
        if (b) {
            tvNavPushTurnOffOrOn.setText("Вкл.");
        }
        else {
            tvNavPushTurnOffOrOn.setText("Выкл.");
        }
        SharedPreferences sp = getApplicationContext().getSharedPreferences("SP_SETTINGS_MENTOR_NAV", 0);
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean("PUSH", b);
        spe.apply();
    }
    private void onNavAccessCodeClick() {
        DialogEditCode editCode = new DialogEditCode(this);
        editCode.setOnItemClickListener(new DialogEditCode.OnItemClickListener() {
            @Override
            public void onCheckCode(String code) {
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, login.loadUserDate(LoginKey.ID));
                jsonData.addProperty(F.Code, code);

                String SOAP = SoapRequest(func_GetUserAccess, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) { }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = _Web.XMLToJson(response.body().string());
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    editCode.onSetAnswer(answer);
                                }
                            });
                        }
                    }
                });
            }
            @Override
            public void onSaveCode(String code) {
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, login.loadUserDate(LoginKey.ID));
                jsonData.addProperty(F.Code, code);

                String SOAP = SoapRequest(func_SetUserAccess, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) { }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = _Web.XMLToJson(response.body().string());
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alert.onToast(answer.getMessage());
                                }
                            });
                        }
                    }
                });
            }
        });
    }
    private void onNavPasswordClick() {
        DialogEditPassword editPassword = new DialogEditPassword(this);
        editPassword.setOnItemClickListener(new DialogEditPassword.OnItemClickListener() {
            @Override
            public void onOk(ModelPasswordEdit mPassword) {
                mPassword.setUserId(MENTOR_ID);
                String SOAP = SoapRequest(func_UserEditPassword, new Gson().toJson(mPassword));
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = _Web.XMLToJson(response.body().string());
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (answer.isSuccess()) editPassword.dismiss();
                                    alert.onToast(answer.getMessage());
                                }
                            });
                        }
                    }
                });
            }
        });
    }
    private void onNavAppAboutClick() {
        View view = getLayoutInflater().inflate(R.layout.alert_dialog_app_about, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).create();
        alertDialog.show();
    }
    private void onNavInviteFriendsClick() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Советую скачать приложение Smart Grades для родительского контроля с PlayMarket");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Пригласить друзей"));
    }
    private void onNavSupportClick() {
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

        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+77004040007")));
            }
        });
        ivEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        ivWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //onShowDialogSupport("WhatsApp");
                String url = "https://wa.me/+77004040007";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        });
        ivTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String url = "https://telegram.im/@zhaslan27";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        });
    }
}