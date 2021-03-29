package kz.tech.smartgrades.school;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

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
import kz.tech.smartgrades.auth.models.ModelUserData;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.custom_view.CustomViewPager;
import kz.tech.smartgrades.root.dialogs.DAceessCode;
import kz.tech.smartgrades.root.fragments.IFragments;
import kz.tech.smartgrades.root.language.ILanguage;
import kz.tech.smartgrades.root.login.ILogin;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.adaptes.SchoolClassAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolClassesFragmentAdapter;
import kz.tech.smartgrades.school.adaptes.TabPagerFragmentAdapter;
import kz.tech.smartgrades.school.fragments.SchoolAddContactFragment;
import kz.tech.smartgrades.school.fragments.SchoolChatFragment;
import kz.tech.smartgrades.school.fragments.SchoolClassFragment;
import kz.tech.smartgrades.school.fragments.SchoolClassessFragment;
import kz.tech.smartgrades.school.fragments.SchoolEditProfileFragment;
import kz.tech.smartgrades.school.fragments.SchoolLessonsFragment;
import kz.tech.smartgrades.school.fragments.SchoolRequestFragment;
import kz.tech.smartgrades.school.fragments.SchoolStudentsFragment;
import kz.tech.smartgrades.school.fragments.SchoolTeachersFragment;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelSchoolRequest;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;
import kz.tech.smartgrades.school.models.ModelUsersList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.S.SCHOOL;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetUser;
import static kz.tech.smartgrades._Web.func_UserEditPassword;

public class SchoolActivity extends AppCompatActivity implements View.OnClickListener, SchoolClassAdapter.OnItemClickListener{

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
    private CustomViewPager viewPager;
    private int CURRENT_PAGE;
    private SchoolStudentsFragment studentsFragment;
    private SchoolTeachersFragment teachersFragment;
    private SchoolLessonsFragment lessonsFragment;

    private TabPagerFragmentAdapter TabAdapter;

    private SchoolRequestFragment requestFragment;
    private SchoolAddContactFragment addContactFragment;

    private int FRAGMENT_ID = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        App.app().getComponent().inject(this);
        setContentView(R.layout.activity_school);
        initViews();
        loadProfileData();
        initPresenter();
    }
    private void initViews(){
        SCHOOL_ID = login.loadUserDate(LoginKey.ID);

        viewPager = findViewById(R.id.viewPager);

        ivNav = findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        drawer = findViewById(R.id.drawer);
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

        ivAvatar = findViewById(R.id.civAvatar);
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
    }
    public void loadProfileData(){
        String avatar = login.loadUserDate(LoginKey.AVATAR);
        if(avatar != null && !avatar.isEmpty()){
            Picasso.get().load(avatar).fit().centerInside().into(ivAvatar);
            Picasso.get().load(avatar).fit().centerInside().into(civNavAvatar);
        }
        else{
            if(login.loadUserDate(LoginKey.TYPE).equals(SCHOOL)){
                ivAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_school));
                civNavAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_school));
            }
            else{
                ivAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
                civNavAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
            }
        }

        String Name = login.loadUserDate(LoginKey.NAME);
        if(Name != null && !Name.isEmpty()) tvNavFullName.setText(Name);
        tvNavLogin.setVisibility(View.GONE);

        String phone = login.loadUserDate(LoginKey.PHONE);
        if(phone != null && !phone.isEmpty()) tvNavPhoneOrMail.setText(phone);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("SP_SETTINGS_SCHOOL_NAV", 0);
        Boolean b = sp.getBoolean("PUSH", true);
        sNavPushTurnOffOrOn.setChecked(b);
    }
    private void initPresenter(){
        presenter = new SchoolPresenter(this);
        presenter.onStartPresenter();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(presenter != null) presenter.onDestroyView();
    }

    public void onNextFragment(int frag, ModelUsersList m){
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        switch(frag){
            case 1:
                ft.replace(R.id.container2, new SchoolEditProfileFragment()).addToBackStack("tag");
                break;
            case 2:
                ft.replace(R.id.container2, SchoolChatFragment.newInstance(m));
                break;
        }
        ft.commit();
    }
    public void onBackFragment(){
        CURRENT_PAGE--;
        viewPager.setCurrentItem(CURRENT_PAGE);
    }
    public void onRemoveFragment(){
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            //if (fragment instanceof SchoolEditProfileFragment || fragment instanceof SchoolChatFragment)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
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
                    String result = _Web.XMLReader(response.body().string());
                    ArrayList<ModelUserData> userList = new Gson().fromJson(result, new TypeToken<ArrayList<ModelUserData>>(){
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

    @Override
    public void onSelectClass(ModelSchoolClass m){
        if(classesFragmentAdapter == null) return;
        classesFragmentAdapter.setSelectClassData(m);
        CURRENT_PAGE++;
        viewPager.setCurrentItem(CURRENT_PAGE);
    }
    @Override
    public void onBackPressed(){
        if(FRAGMENT_ID == 0) super.onBackPressed();
        else{
            if(FRAGMENT_ID == 1){//CLASSES
                if(CURRENT_PAGE > 0) onBackFragment();
                else {
                    for (Fragment fragment : getSupportFragmentManager().getFragments())
                        if (fragment instanceof SchoolClassessFragment || fragment instanceof SchoolClassFragment)
                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    FRAGMENT_ID = 0;
                }
            }
            else if(FRAGMENT_ID == 2){//STUDENTS
                for(Fragment fragment : getSupportFragmentManager().getFragments())
                    if(fragment instanceof SchoolStudentsFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                FRAGMENT_ID = 0;
            }
            else if(FRAGMENT_ID == 3){//TEACHERS
                for(Fragment fragment : getSupportFragmentManager().getFragments())
                    if(fragment instanceof SchoolTeachersFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                FRAGMENT_ID = 0;
            }
            else if(FRAGMENT_ID == 4){//LESSONS
                for(Fragment fragment : getSupportFragmentManager().getFragments())
                    if(fragment instanceof SchoolLessonsFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                FRAGMENT_ID = 0;
            }
            else if(FRAGMENT_ID == 7){//REQUEST
                for(Fragment fragment : getSupportFragmentManager().getFragments())
                    if(fragment instanceof SchoolRequestFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                FRAGMENT_ID = 0;
            }
        }
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.civAvatar:
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

    private void onClassessClick(){
        FRAGMENT_ID = 1;//CLASSES
        if(classesFragmentAdapter == null)
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
        if(studentsFragment == null)
            studentsFragment = SchoolStudentsFragment.newInstance(mSchoolData);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, studentsFragment);
        ft.commit();
    }
    private void onTeachersClick(){
        FRAGMENT_ID = 3;//TEACHERS
        if(teachersFragment == null)
            teachersFragment = SchoolTeachersFragment.newInstance(mSchoolData);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, teachersFragment).commit();
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
    public void onClickRequest(ModelSchoolRequest m){
        if(m.getSourceType() !=null && m.getSourceType().equals("Mentor")){
            FRAGMENT_ID = 8;//REQUEST
            if(addContactFragment == null) addContactFragment = new SchoolAddContactFragment();
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container2, addContactFragment).commit();
            addContactFragment.setRequestData(m);
        }
        else {
            FRAGMENT_ID = 7;//REQUEST
            if(requestFragment == null) requestFragment = SchoolRequestFragment.newInstance(m);
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container2, requestFragment).commit();
        }
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
                GET.onEditAccess(SchoolActivity.this, "1", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum2).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(SchoolActivity.this, "2", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum3).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(SchoolActivity.this, "3", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum4).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(SchoolActivity.this, "4", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum5).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(SchoolActivity.this, "5", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum6).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(SchoolActivity.this, "6", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum7).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(SchoolActivity.this, "7", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum8).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(SchoolActivity.this, "8", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum9).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(SchoolActivity.this, "9", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum0).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(SchoolActivity.this, "0", dialog, ivDot, tvTitle);
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
                jsonData.addProperty(F.UserId, SCHOOL_ID);
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

    public void setSchoolData(ModelSchoolData mSchoolData){
        this.mSchoolData = mSchoolData;

        int ClassCount = 0;
        if(!listIsNullOrEmpty(mSchoolData.getClassessColumns())){
            for(int i = 0; i < mSchoolData.getClassessColumns().size(); i++)
                if(!listIsNullOrEmpty(mSchoolData.getClassessColumns().get(i).getClasses()))
                    ClassCount += mSchoolData.getClassessColumns().get(i).getClasses().size();
        }
        tvClassCount.setText(String.valueOf(ClassCount));
        if(!listIsNullOrEmpty(mSchoolData.getStudents()))
            tvStudentsCount.setText(String.valueOf(mSchoolData.getStudents().size()));
        if(!listIsNullOrEmpty(mSchoolData.getTeachers()))
            tvTeacherCount.setText(String.valueOf(mSchoolData.getTeachers().size()));
        if(!listIsNullOrEmpty(mSchoolData.getLessons()))
            tvLessonsCount.setText(String.valueOf(mSchoolData.getLessons().size()));

        if(classesFragmentAdapter != null) classesFragmentAdapter.setSchoolData(mSchoolData);

        TabAdapter.updateDate(mSchoolData);
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


}