package kz.tech.smartgrades.school;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
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
import kz.tech.smartgrades.child.models.ModelInterForm;
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
import kz.tech.smartgrades.school.adaptes.SchoolClassAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolClassesFragmentAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolStudentsFragmentAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolTeachersFragmentAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolTeachersFragmentAdapterV2;
import kz.tech.smartgrades.school.adaptes.TabPagerFragmentAdapter;
import kz.tech.smartgrades.school.fragments.SchoolAddStudentFragment;
import kz.tech.smartgrades.school.fragments.SchoolAddTeacherFragment;
import kz.tech.smartgrades.school.fragments.SchoolChatFragment;
import kz.tech.smartgrades.school.fragments.SchoolClassFragment;
import kz.tech.smartgrades.school.fragments.SchoolClassessFragment;
import kz.tech.smartgrades.school.fragments.SchoolEditClassesFragment;
import kz.tech.smartgrades.school.fragments.SchoolEditProfileFragment;
import kz.tech.smartgrades.school.fragments.SchoolEditStudentClassesFragment;
import kz.tech.smartgrades.school.fragments.SchoolEditStudentProfileFragment;
import kz.tech.smartgrades.school.fragments.SchoolEditTeacherProfileFragment;
import kz.tech.smartgrades.school.fragments.SchoolLessonsFragment;
import kz.tech.smartgrades.school.fragments.SchoolMoveStudentListFragment;
import kz.tech.smartgrades.school.fragments.SchoolMoveTeachersListFragment;
import kz.tech.smartgrades.school.fragments.SchoolOtherSchoolProfileFragment;
import kz.tech.smartgrades.school.fragments.SchoolRequestFragment;
import kz.tech.smartgrades.school.fragments.SchoolStudentsFragment;
import kz.tech.smartgrades.school.fragments.SchoolTeacherRequestFragment;
import kz.tech.smartgrades.school.fragments.SchoolTeachersFragment;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolClassesColumn;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;
import kz.tech.smartgrades.school.models.ModelStudentProfile;
import kz.tech.smartgrades.school.models.ModelStudentProfileClasses;
import kz.tech.smartgrades.school.models.ModelTeacherProfile;
import kz.tech.smartgrades.school.models.ModelTeacherProfileClasses;
import kz.tech.smartgrades.school.models.ModelUsersList;
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

public class SchoolActivity extends AppCompatActivity implements
        View.OnClickListener,
        SchoolClassAdapter.OnItemClickListener,
        SwipyRefreshLayout.OnRefreshListener{

    @Inject
    public ILanguage language;
    @Inject
    public IAlert alert;
    @Inject
    public ILogin login;
    @Inject
    public IFragments fragments;

    private CardView cvSearch;
    private ImageView ivAvatar;

    private LinearLayout llClassess, llTeachers, llStudents, llItems;
    private TextView tvClassCount, tvStudentsCount, tvTeacherCount, tvLessonsCount;

    private TextView tvOffers;
    private LinearLayout llOffers;
    private ImageView ivOffers;
    private Button btnEnableOffers;
    private RecyclerView rvComplaintsAndSuggestions;

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
    private TextView tvNavAppAbout;
    private TextView tvNavInviteFriends;
    private TextView tvNavSupport;

    public SchoolPresenter presenter;
    private String SCHOOL_ID;
    private ModelSchoolData mSchoolData;

    private SchoolClassesFragmentAdapter classesFragmentAdapter;
    private SchoolTeachersFragmentAdapter teachersFragmentAdapter;
    private SchoolTeachersFragmentAdapterV2 teachersFragmentAdapterV2;
    private SchoolStudentsFragmentAdapter studentsFragmentAdapter;
    private CustomViewPager viewPager;
    private CustomViewPager viewPager2;
    private int CURRENT_PAGE;
    private int CURRENT_PAGE_2;
    private SchoolStudentsFragment studentsFragment;
    private SchoolTeachersFragment teachersFragment;
    private SchoolLessonsFragment lessonsFragment;

    private TabPagerFragmentAdapter TabAdapter;

    private SchoolRequestFragment requestFragment;
    private SchoolTeacherRequestFragment requestTeacherFragment;
    private SchoolAddTeacherFragment addContactFragment;

    private SchoolMoveStudentListFragment moveStudentListFragment;
    private SchoolMoveTeachersListFragment moveTeacherListFragment;
    private SchoolAddTeacherFragment addTeacherFragment;
    private SchoolAddStudentFragment addStudentFragment;
    private SchoolOtherSchoolProfileFragment otherSchoolProfileFragment;
    private SchoolEditProfileFragment editProfileFragment;

    private int FRAGMENT_ID = 0;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_LOAD_PHOTO = 2;
    private static final int PERMISSIONS_REQUEST = 1;
    public int selectedType = 0;

    private ProgressBar progressbar;

    private SwipyRefreshLayout swipeContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        App.app().getComponent().inject(this);
        setContentView(R.layout.activity_school);
        initViews();
        setProfileData();
        initPresenter();
    }
    public BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String Tag = intent.getStringExtra("Tag");
            String Body = intent.getStringExtra("Body");
//            if (!stringIsNullOrEmpty(Tag) && Tag.equals("ParentRejectChildRequest")) {
//                alert.onToast(Body);
//            }
            updatePresenter();
        }
    };
    private void initViews(){
        SCHOOL_ID = login.loadUserDate(LoginKey.ID);

        viewPager = findViewById(R.id.viewPager);
        viewPager2 = findViewById(R.id.viewPager2);

        ivNav = findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        drawer = findViewById(R.id.drawer);
        progressbar = findViewById(R.id.progressbar);
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

        ivAvatar = findViewById(R.id.ivAvatar);
        ivAvatar.setOnClickListener(this);

        llClassess = findViewById(R.id.llClassess);
        llClassess.setOnClickListener(this);
        llTeachers = findViewById(R.id.llTeachers);
        llTeachers.setOnClickListener(this);
        llStudents = findViewById(R.id.llStudents);
        llStudents.setOnClickListener(this);
        llItems = findViewById(R.id.llItems);
        llItems.setOnClickListener(this);

        tvClassCount = findViewById(R.id.tvClassCount);
        tvStudentsCount = findViewById(R.id.tvStudentsCount);
        tvTeacherCount = findViewById(R.id.tvTeacherCount);
        tvLessonsCount = findViewById(R.id.tvLessonsCount);

        TabAdapter = new TabPagerFragmentAdapter(getSupportFragmentManager(), SchoolActivity.this, false);
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(TabAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
    public void setProfileData(){
        ivAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_school));
        civNavAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_school));

        String Name = login.loadUserDate(LoginKey.FIRST_NAME);
        if(!stringIsNullOrEmpty(Name)) tvNavFullName.setText(Name);

        String _login = login.loadUserDate(LoginKey.LOGIN);
        if(!stringIsNullOrEmpty(_login)) tvNavLogin.setText(_login);

        String phone = login.loadUserDate(LoginKey.PHONE);
        if(phone != null && !phone.isEmpty()) tvNavPhoneOrMail.setText(phone);
    }
    private void initPresenter(){
        presenter = new SchoolPresenter(this);
        presenter.onStartPresenter();
    }
    public void updatePresenter() {
        presenter.onStartPresenter();
        progressbar.setVisibility(View.VISIBLE);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(presenter != null) presenter.onDestroyView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressbar.setVisibility(View.VISIBLE);
        registerReceiver(myReceiver, new IntentFilter("update"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    public void setSchoolData(ModelSchoolData mSchoolData){
        this.mSchoolData = mSchoolData;
        progressbar.setVisibility(View.GONE);

        int ClassCount = 0;
        if(!listIsNullOrEmpty(mSchoolData.getClassessColumns())){
            for(ModelSchoolClassesColumn _column : mSchoolData.getClassessColumns())
                if(!listIsNullOrEmpty(_column.getClasses()))
                    ClassCount += _column.getClasses().size();
        }
        tvClassCount.setText(String.valueOf(ClassCount));

        if(!listIsNullOrEmpty(mSchoolData.getStudents()))
            tvStudentsCount.setText(String.valueOf(mSchoolData.getStudents().size()));
        if(studentsFragment != null) studentsFragment.onUpdateData(mSchoolData);
        if(!listIsNullOrEmpty(mSchoolData.getTeachers()))
            tvTeacherCount.setText(String.valueOf(mSchoolData.getTeachers().size()));
        if(teachersFragment != null) teachersFragment.onUpdateData(mSchoolData);
        if(!listIsNullOrEmpty(mSchoolData.getLessons()))
            tvLessonsCount.setText(String.valueOf(mSchoolData.getLessons().size()));
        if(lessonsFragment != null) lessonsFragment.onUpdateData(mSchoolData);
        if(addTeacherFragment != null) addTeacherFragment.onUpdateData(mSchoolData);
        if(addStudentFragment != null) addStudentFragment.onUpdateData(mSchoolData);
        if (addContactFragment != null) addContactFragment.onUpdateData(mSchoolData);
        TabAdapter.updateDate(mSchoolData);
        if (teachersFragmentAdapter != null) teachersFragmentAdapter.updateData(mSchoolData);
        if (teachersFragmentAdapterV2 != null) {
            teachersFragmentAdapterV2.updateClassData(mSchoolData);
        }
        if (studentsFragmentAdapter != null) studentsFragmentAdapter.updateData(mSchoolData);
        if (classesFragmentAdapter != null) {
            classesFragmentAdapter.updateData(mSchoolData);
            classesFragmentAdapter.onUpdateSchoolData(mSchoolData);
        }
    }
    public ModelSchoolData getSchoolData(){
        return mSchoolData;
    }

    public void onUpdateStudentsList(ArrayList<ModelSchoolStudent> students){
        mSchoolData.setStudents(students);
        if(classesFragmentAdapter != null) classesFragmentAdapter.setSchoolData(mSchoolData);
        if(studentsFragment != null) studentsFragment.onUpdateData(mSchoolData);
    }
    public void onUpdateTeachersList(ArrayList<ModelSchoolTeacher> teachers){
        mSchoolData.setTeachers(teachers);
        if(classesFragmentAdapter != null) classesFragmentAdapter.setSchoolData(mSchoolData);
        if(teachersFragment != null) teachersFragment.onUpdateData(mSchoolData);
    }
    public void onUpdateLessonsList(ArrayList<ModelSchoolLesson> lessons){
        mSchoolData.setLessons(lessons);
        if(classesFragmentAdapter != null) classesFragmentAdapter.setSchoolData(mSchoolData);
        if(lessonsFragment != null) lessonsFragment.onUpdateData(mSchoolData);
    }

    public void onNextFragment(int frag, ModelUsersList m){
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        switch(frag){
            case 1:
                editProfileFragment = new SchoolEditProfileFragment();
                ft.replace(R.id.container2, editProfileFragment).addToBackStack("tag");
                break;
            case 2:
                ft.replace(R.id.container2, SchoolChatFragment.newInstance(m));
                break;
        }
        ft.commit();
    }
    public void onNextFragment(){
        if (FRAGMENT_ID == 91) {
            CURRENT_PAGE_2++;
            viewPager2.setCurrentItem(CURRENT_PAGE_2);
        } else {
            CURRENT_PAGE++;
            viewPager.setCurrentItem(CURRENT_PAGE);
        }
    }
    public void onBackFragment(){
        if (FRAGMENT_ID == 91) {
            CURRENT_PAGE_2--;
            viewPager2.setCurrentItem(CURRENT_PAGE_2);
        } else {
            CURRENT_PAGE--;
            viewPager.setCurrentItem(CURRENT_PAGE);
        }
    }
    public void onRemoveFragment(){
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            //if (fragment instanceof SchoolEditProfileFragment || fragment instanceof SchoolChatFragment)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    @Override
    public void onSelectClass(ModelSchoolClass m){
        if(classesFragmentAdapter == null) return;
        classesFragmentAdapter.setSelectClassData(m);
        CURRENT_PAGE++;
        viewPager.setCurrentItem(CURRENT_PAGE);
    }
    @Override
    public void onBackPressed(){
        alert.hideKeyboard(this);
        for(Fragment fragment : getSupportFragmentManager().getFragments())
            if(fragment instanceof SchoolAddTeacherFragment
                    || fragment instanceof SchoolOtherSchoolProfileFragment
                    || fragment instanceof SchoolAddStudentFragment
                    || fragment instanceof SchoolEditProfileFragment){
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                if (addTeacherFragment != null) {
                    addTeacherFragment = null;
                    return;
                }
                if (addStudentFragment != null) {
                    addStudentFragment = null;
                    return;
                }
                if (otherSchoolProfileFragment != null) {
                    otherSchoolProfileFragment = null;
                    return;
                }
                if (editProfileFragment != null) {
                    editProfileFragment = null;
                    return;
                }
                break;
            }
        if(FRAGMENT_ID == 0) super.onBackPressed();
        else{
            if(FRAGMENT_ID == 1){//CLASSES
                if(CURRENT_PAGE > 0) onBackFragment();
                else {
                    for (Fragment fragment : getSupportFragmentManager().getFragments())
                        if (fragment instanceof SchoolClassessFragment || fragment instanceof SchoolClassFragment)
                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    FRAGMENT_ID = 0;
                    classesFragmentAdapter = null;
                }
            }
            else if(FRAGMENT_ID == 2){//STUDENTS
//                for(Fragment fragment : getSupportFragmentManager().getFragments())
//                    if(fragment instanceof SchoolStudentsFragment)
//                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//                FRAGMENT_ID = 0;
                if(CURRENT_PAGE > 0) {
                    onBackFragment();
                } else {
                    for (Fragment fragment : getSupportFragmentManager().getFragments())
                        if (fragment instanceof SchoolStudentsFragment
                                || fragment instanceof SchoolEditStudentProfileFragment
                                || fragment instanceof SchoolEditStudentClassesFragment
                                || fragment instanceof SchoolAddStudentFragment) {
                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }
                    studentsFragmentAdapter = null;
                    FRAGMENT_ID = 0;
                }
            }
            else if(FRAGMENT_ID == 3){//TEACHERS
                if(CURRENT_PAGE > 0) {
                    onBackFragment();
                } else {
                    for (Fragment fragment : getSupportFragmentManager().getFragments())
                        if (fragment instanceof SchoolTeachersFragment
                                || fragment instanceof SchoolEditTeacherProfileFragment
                                || fragment instanceof SchoolEditClassesFragment
                                || fragment instanceof SchoolAddTeacherFragment) {
                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }
                    teachersFragmentAdapter = null;
                    FRAGMENT_ID = 0;
                }
            }
            else if(FRAGMENT_ID == 4){//LESSONS
                for(Fragment fragment : getSupportFragmentManager().getFragments())
                    if(fragment instanceof SchoolLessonsFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                FRAGMENT_ID = 0;
                lessonsFragment = null;
            }
            else if(FRAGMENT_ID == 7){//REQUEST
                for(Fragment fragment : getSupportFragmentManager().getFragments())
                    if(fragment instanceof SchoolRequestFragment) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        requestFragment = null;
                    }
                FRAGMENT_ID = 0;
            }
            else if(FRAGMENT_ID == 8){//REQUEST
                for(Fragment fragment : getSupportFragmentManager().getFragments())
                    if(fragment instanceof SchoolTeacherRequestFragment) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        requestTeacherFragment = null;
                    }
                FRAGMENT_ID = 0;
            }
            else if(FRAGMENT_ID == 9){//MOVE STUDENT
                for(Fragment fragment : getSupportFragmentManager().getFragments())
                    if(fragment instanceof SchoolMoveStudentListFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                FRAGMENT_ID = 1;
            }
            else if(FRAGMENT_ID == 91){//MOVE TEACHER
                if(CURRENT_PAGE_2 > 0) {
                    onBackFragment();
                } else {
                    for (Fragment fragment : getSupportFragmentManager().getFragments())
                        if (fragment instanceof SchoolMoveTeachersListFragment
                                || fragment instanceof SchoolEditTeacherProfileFragment
                                || fragment instanceof SchoolEditClassesFragment) {
                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }
                    teachersFragmentAdapterV2 = null;
                    FRAGMENT_ID = 1;
                }
            }
        }
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivAvatar:
                onMenu(v);
                break;
            case R.id.llClassess:
                onClassessClick();
                break;
            case R.id.llStudents:
                onStudentsClick();
                break;
            case R.id.llTeachers:
                onTeachersClick();
                break;
            case R.id.llItems:
                onLessonClick();
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
        }
    }

    private void onMenu(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("UserId", SCHOOL_ID);

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
                                AlertDialog.Builder builder = new AlertDialog.Builder(SchoolActivity.this);
                                View viewAlert = getLayoutInflater().inflate(R.layout.ad_access_code, null);
                                builder.setView(viewAlert);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                ((TextView) viewAlert.findViewById(R.id.tvText)).setText("Вы действительно хотите перейти в выбранный акканут?");
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
                                                new DAceessCode(SchoolActivity.this, login.loadUserDate(LoginKey.TYPE), userList.get(i));
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
                            popupMenu.getMenu().add("Выйти").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem){
                                    login.deleteUserDate();
                                    startActivity(new Intent(SchoolActivity.this, AuthActivity.class));
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

    private void onClassessClick(){
        FRAGMENT_ID = 1;//CLASSES
        classesFragmentAdapter = new SchoolClassesFragmentAdapter(getSupportFragmentManager(), mSchoolData);
        classesFragmentAdapter.setSchoolData(mSchoolData);

        CURRENT_PAGE = 0;
        viewPager.setAdapter(classesFragmentAdapter);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        viewPager.setOffscreenPageLimit(classesFragmentAdapter.getCount());
        viewPager.setCurrentItem(CURRENT_PAGE);
    }
    private void onStudentsClick(){
        FRAGMENT_ID = 2;//STUDENTS
        studentsFragmentAdapter = new SchoolStudentsFragmentAdapter(getSupportFragmentManager(), mSchoolData);

        CURRENT_PAGE = 0;
        viewPager.setAdapter(studentsFragmentAdapter);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        viewPager.setOffscreenPageLimit(studentsFragmentAdapter.getCount());
        viewPager.setCurrentItem(CURRENT_PAGE);
    }
    public void onAddStudentsClick(){
        addStudentFragment = new SchoolAddStudentFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, addStudentFragment).commit();
    }
    public void onAddTeachersClick(){//ADD TEACHERS
        addTeacherFragment = new SchoolAddTeacherFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, addTeacherFragment).commit();
    }
    private void onTeachersClick(){
        FRAGMENT_ID = 3;//TEACHERS
        teachersFragmentAdapter = new SchoolTeachersFragmentAdapter(getSupportFragmentManager(), mSchoolData);

        CURRENT_PAGE = 0;
        viewPager.setAdapter(teachersFragmentAdapter);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        viewPager.setOffscreenPageLimit(teachersFragmentAdapter.getCount());
        viewPager.setCurrentItem(CURRENT_PAGE);
    }
    public void onSchoolProfileClick(String id){//SHOW SCHOOL PROFILE
        otherSchoolProfileFragment = new SchoolOtherSchoolProfileFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, otherSchoolProfileFragment).commit();
        otherSchoolProfileFragment.loadModel(id);
    }
    private void onLessonClick(){
        FRAGMENT_ID = 4;//LESSONS
        /*if(!listIsNullOrEmpty(mSchoolData.getLessons())){
            for(ModelSchoolLesson _lesson : mSchoolData.getLessons()){
                //if(listIsNullOrEmpty(_lesson.getStudents())) _lesson.setStudents(new ArrayList<>());
                /*if(!listIsNullOrEmpty(mSchoolData.getLessons()))
                    for(ModelSchoolStudent _student : mSchoolData.getStudents())
                        if(_student.getClassId().equals(_lesson.getClassId()))
                            _lesson.getStudents().add(_student);

                /*if(listIsNullOrEmpty(_lesson.getTeachers())) _lesson.setTeachers(new ArrayList<>());
                for(ModelSchoolTeacher _teacher : mSchoolData.getTeachers())
                    if(_teacher.getClassId().equals(_lesson.getClassId()))
                        _lesson.getTeachers().add(_teacher);
            }
        }*/
        if(lessonsFragment == null)
            lessonsFragment = SchoolLessonsFragment.newInstance(mSchoolData);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, lessonsFragment).commit();
    }

    public void onClickInterForm(ModelInterForm m){
        if(m.getType().equals("MentorToSchool")){
            FRAGMENT_ID = 8;//REQUEST
            if(requestTeacherFragment == null) requestTeacherFragment = SchoolTeacherRequestFragment.newInstance(m);
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container2, requestTeacherFragment).commit();
        }
        else if(m.getType().equals("ParentToSchool")){
            FRAGMENT_ID = 7;//REQUEST
            if(requestFragment == null) requestFragment = SchoolRequestFragment.newInstance(m);
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container2, requestFragment).commit();
        }
    }

    public void onMoveStudentClick(ModelSchoolClass mSchoolClass){
        FRAGMENT_ID = 9;//MOVE STUDENT
        if(moveStudentListFragment == null) moveStudentListFragment = new SchoolMoveStudentListFragment();
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, moveStudentListFragment).commit();
        moveStudentListFragment.setClassData(mSchoolData, mSchoolClass);
    }
    public void onMoveTeacherClick(ModelSchoolClass mSchoolClass){
        FRAGMENT_ID = 91;//MOVE TEACHER
        teachersFragmentAdapterV2 = new SchoolTeachersFragmentAdapterV2(getSupportFragmentManager(), mSchoolData, mSchoolClass);

        CURRENT_PAGE_2 = 0;
        viewPager2.setAdapter(teachersFragmentAdapterV2);
        viewPager2.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        viewPager2.setOffscreenPageLimit(teachersFragmentAdapterV2.getCount());
        viewPager2.setCurrentItem(CURRENT_PAGE_2);
    }

    private void onNavigationClick(){
        if(drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else drawer.openDrawer(GravityCompat.START);
    }
    private void onNavEditClick(){
        onNextFragment(1, null);
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
                mPassword.setUserId(SCHOOL_ID);
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

    public void setTeacherData(ModelSchoolTeacher m) {
        if (teachersFragmentAdapter != null) {
            teachersFragmentAdapter.setTeacherData(m);
            onNextFragment();
        }else if (teachersFragmentAdapterV2 != null) {
            teachersFragmentAdapterV2.setTeacherData(m);
            onNextFragment();
        }
    }
    public void setStudentData(ModelSchoolStudent m) {
        if (studentsFragmentAdapter != null) {
            studentsFragmentAdapter.setStudentData(m);
            onNextFragment();
        }
    }
    public void setTeacherClasses(ArrayList<ModelTeacherProfileClasses> classesList, ModelTeacherProfile mTeacher) {
        if (teachersFragmentAdapter != null) {
            teachersFragmentAdapter.setClasses(classesList, mTeacher);
        }else if (teachersFragmentAdapterV2 != null) {
            teachersFragmentAdapterV2.setClasses(classesList, mTeacher);
        }
    }
    public void setStudentClasses(ArrayList<ModelStudentProfileClasses> classesList, ModelStudentProfile mStudent) {
        if (studentsFragmentAdapter != null) {
            studentsFragmentAdapter.setClasses(classesList, mStudent);
        }
    }

    public void loadTeacherModel() {
        if (teachersFragmentAdapter != null) {
            teachersFragmentAdapter.loadTeacherModel();
        } else if (teachersFragmentAdapterV2 != null) {
            teachersFragmentAdapterV2.loadTeacherModel();
        }
    }
    public void loadStudentModel() {
        if (studentsFragmentAdapter != null) {
            studentsFragmentAdapter.loadStudentModel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            try{
                if (editProfileFragment != null) editProfileFragment.prepareAvatar(resultUri);
                else if (addTeacherFragment != null) addTeacherFragment.prepareAvatar(resultUri);
                else if (addStudentFragment != null) addStudentFragment.prepareAvatar(resultUri);
                else if (studentsFragmentAdapter != null) studentsFragmentAdapter.prepareAvatar(resultUri);
                else if (teachersFragmentAdapter != null) teachersFragmentAdapter.prepareAvatar(resultUri);
                else if (teachersFragmentAdapterV2 != null) teachersFragmentAdapterV2.prepareAvatar(resultUri);
                else if (classesFragmentAdapter != null) classesFragmentAdapter.prepareAvatar(resultUri);
            } catch(Exception ignored){}
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        } else if(resultCode == RESULT_OK){
            Uri imageUri = null;
            if (classesFragmentAdapter != null) {
                if(requestCode == REQUEST_TAKE_PHOTO) imageUri = Uri.fromFile(classesFragmentAdapter.getMPhotoFile());
                else if(requestCode == REQUEST_LOAD_PHOTO && null != data) imageUri = data.getData();
                try {
                    classesFragmentAdapter.startCrop(imageUri);
                } catch (Exception ignored) {}
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && selectedType == 1) {
                if (classesFragmentAdapter != null) {
                    classesFragmentAdapter.ToMakeAPhoto(REQUEST_TAKE_PHOTO);
                }
            } else if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED && selectedType == 0) {
                if (classesFragmentAdapter != null) {
                    classesFragmentAdapter.ToMakeAPhoto(REQUEST_LOAD_PHOTO);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}