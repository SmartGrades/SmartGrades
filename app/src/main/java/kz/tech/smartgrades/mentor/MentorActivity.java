package kz.tech.smartgrades.mentor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import kz.tech.smartgrades.mentor.adapters.MentorAddSchoolFragmentAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorAddSchoolListAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorAddStudentFragmentAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorClassAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorClassesFragmentAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorGroupAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorTabPagerFragmentAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorUserListAdapter;
import kz.tech.smartgrades.mentor.dialog.BSDSelectLesson;
import kz.tech.smartgrades.mentor.fragments.MentorAddSchoolListFragment;
import kz.tech.smartgrades.mentor.fragments.MentorAddSchoolProfileFragment;
import kz.tech.smartgrades.mentor.fragments.MentorAddStudentListFragment;
import kz.tech.smartgrades.mentor.fragments.MentorAddStudentProfileFragment;
import kz.tech.smartgrades.mentor.fragments.MentorChatFragment;
import kz.tech.smartgrades.mentor.fragments.MentorClassessFragment;
import kz.tech.smartgrades.mentor.fragments.MentorEditProfileFragment;
import kz.tech.smartgrades.mentor.fragments.MentorLessonsFragment;
import kz.tech.smartgrades.mentor.fragments.MentorSchoolsFragment;
import kz.tech.smartgrades.mentor.fragments.MentorStudentsFragment;
import kz.tech.smartgrades.mentor.models.ModelMentorChat;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.mentor.models.ModelMentorGroups;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.custom_view.CustomViewPager;
import kz.tech.smartgrades.root.dialogs.DAceessCode;
import kz.tech.smartgrades.root.fragments.IFragments;
import kz.tech.smartgrades.root.language.ILanguage;
import kz.tech.smartgrades.root.login.ILogin;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.fragments.SchoolRequestFragment;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolClassesColumn;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolLessons;
import kz.tech.smartgrades.school.models.ModelSchoolRequest;
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
import static kz.tech.smartgrades._Web.func_UserEditPassword;

public class MentorActivity extends AppCompatActivity implements View.OnClickListener, MentorUserListAdapter.OnItemClickListener, MentorGroupAdapter.OnItemClickListener, MentorClassAdapter.OnItemClickListener, MentorAddSchoolListAdapter.OnItemCLickListener{

    @Inject
    public ILanguage language;
    @Inject
    public IAlert alert;
    @Inject
    public ILogin login;
    @Inject
    public IFragments fragments;

    private CardView cvSearch;
    private CircleImageView civAvatar;

    private LinearLayout llClassess, llSchools, llStudents, llItems;
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

    public MentorPresenter presenter;
    private String MENTOR_ID;
    private ModelMentorData mMentorData;

    private MentorClassesFragmentAdapter classesFragmentAdapter;
    private CustomViewPager viewPager;
    private int CURRENT_PAGE;
    private MentorStudentsFragment studentsFragment;
    private MentorSchoolsFragment schoolsFragment;
    private MentorLessonsFragment lessonsFragment;

    private MentorTabPagerFragmentAdapter TabAdapter;

    private SchoolRequestFragment requestFragment;

    private int FRAGMENT_ID = 0;

    private MentorAddStudentFragmentAdapter adapter;
    private MentorAddSchoolFragmentAdapter addSchoolFragmentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        App.app().getComponent().inject(this);
        setContentView(R.layout.activity_mentor_v2);
        initViews();
        onSetMentorData(null);
        initPresenter();
    }
    private void initViews(){
        MENTOR_ID = login.loadUserDate(LoginKey.ID);

        cvSearch = findViewById(R.id.cvSearch);
        cvSearch.setOnClickListener(this);
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
        tvOffers = findViewById(R.id.tvOffers);
        //llOffers = findViewById(R.id.llOffers);
        ivOffers = findViewById(R.id.ivOffers);
        btnEnableOffers = findViewById(R.id.btnEnableOffers);
        rvComplaintsAndSuggestions = findViewById(R.id.rvComplaintsAndSuggestions);

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
    public void onSetMentorData(ModelMentorData model){
        if(model != null){
            mMentorData = model;

            int ClassCount = 0;
            if(!listIsNullOrEmpty(mMentorData.getClassessColumns())){
                for(ModelSchoolClassesColumn _column : mMentorData.getClassessColumns())
                    if(!listIsNullOrEmpty(_column.getClasses()))
                        ClassCount += _column.getClasses().size();
            }
            tvClassCount.setText(String.valueOf(ClassCount));
            if(!listIsNullOrEmpty(mMentorData.getStudents()))
                tvStudentsCount.setText(String.valueOf(mMentorData.getStudents().size()));
            if(!listIsNullOrEmpty(mMentorData.getSchools()))
                tvTeacherCount.setText(String.valueOf(mMentorData.getSchools().size()));
            if(!listIsNullOrEmpty(mMentorData.getLessons()))
                tvLessonsCount.setText(String.valueOf(mMentorData.getLessons().size()));

            if(classesFragmentAdapter!=null)
                classesFragmentAdapter.setMentorData(mMentorData);

            TabAdapter.setData(model.getInterForms());
            TabAdapter.onSetModelMentorData(mMentorData);
        }
        else{
            String avatar = login.loadUserDate(LoginKey.AVATAR);
            if(!stringIsNullOrEmpty(avatar)){
                Picasso.get().load(avatar).fit().centerInside().into(civAvatar);
                Picasso.get().load(avatar).fit().centerInside().into(civNavAvatar);
            }

            String fullName = login.loadUserDate(LoginKey.FIRST_NAME) + " " + login.loadUserDate(LoginKey.LAST_NAME);
            if(!stringIsNullOrEmpty(fullName)) tvNavFullName.setText(fullName);

            String Login = login.loadUserDate(LoginKey.LOGIN);
            if(!stringIsNullOrEmpty(Login)) tvNavLogin.setText(Login);

            SharedPreferences sp = getApplicationContext().getSharedPreferences("SP_SETTINGS_MENTOR_NAV", 0);
            boolean b = sp.getBoolean("PUSH", true);
            sNavPushTurnOffOrOn.setChecked(b);
        }
    }
    private void initPresenter(){
        presenter = new MentorPresenter(this);
        presenter.onStartPresenter();
    }
    public void restartPresenter(){
        presenter.onStartPresenter();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(presenter != null) presenter.onDestroyView();
    }

    public void onSendMessageToGroup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View viewAlert = getLayoutInflater().inflate(R.layout.ad_mentor_send_message_to_group, null);
        builder.setView(viewAlert);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        EditText etMessage = viewAlert.findViewById(R.id.etMessage);
        ImageView ivSend = viewAlert.findViewById(R.id.ivSend);

        etMessage.post(new Runnable(){
            @Override
            public void run(){
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(
                        etMessage.getApplicationWindowToken(), InputMethodManager.SHOW_IMPLICIT, 0);
                etMessage.requestFocus();
            }
        });
        ivSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
            }
        });

        alertDialog.show();
    }
    public ArrayList<ModelMentorGroups> getMentorGroups(){
        return null;
    }
    public void onNextFragment(){
        CURRENT_PAGE++;
        viewPager.setCurrentItem(CURRENT_PAGE);
    }
    public void onNextFragment(int frag, ModelMentorChat modelMentorChat){
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        switch(frag){
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
    public boolean onRemoveFragment(){
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            if(fragment instanceof MentorEditProfileFragment || fragment instanceof MentorChatFragment){
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                return true;
            }
        }
        return false;
    }
    public void onRemoveFragment(int frag){
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            switch(frag){
                case 1:
                    if(fragment instanceof MentorStudentsFragment || fragment instanceof MentorClassessFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    FRAGMENT_ID = 0;
                    break;
                case 2:
                    if(fragment instanceof MentorStudentsFragment){
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        FRAGMENT_ID = 0;
                    }
                    break;
                case 3:
                    if(fragment instanceof MentorSchoolsFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    break;
                case 4:
                    if(fragment instanceof MentorLessonsFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    break;
                case 5:
                    if(fragment instanceof MentorAddStudentProfileFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    else if(fragment instanceof MentorAddStudentListFragment){
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        FRAGMENT_ID = 2;
                    }
                    break;
                case 6:
                    if(fragment instanceof MentorAddSchoolProfileFragment)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    else if(fragment instanceof MentorAddSchoolListFragment){
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        FRAGMENT_ID = 3;
                    }
                    break;
            }
        }
    }

    //МЕНЮ ДЛЯ ПЕРЕХОДА НА ДРУГОЙ АККАУНТ
    private void onMenu(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("UserId", MENTOR_ID);

        String SOAP = SoapRequest(func_GetUser, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
                alert.onToast("Сервис временно не доступен.");
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(MentorActivity.this);
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
                                                new DAceessCode(MentorActivity.this, login.loadUserDate(LoginKey.TYPE), userList.get(i));
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
    //ДОБАВЛЕНИЕ НОВОЙ ГРУППЫ
    private void onCreateGroup(){
        final boolean[] isEnter = {false, false};
        AlertDialog.Builder builder = new AlertDialog.Builder(MentorActivity.this);
        View vLayout = getLayoutInflater().inflate(R.layout.alert_dialog_builder_mentor_create_group, null);
        builder.setView(vLayout);
        EditText etEnterCreateGroup = vLayout.findViewById(R.id.etEnterCreateGroup);
        TextView tvSelectLessonValue = vLayout.findViewById(R.id.tvSelectLessonValue);
        TextView tvDefault = vLayout.findViewById(R.id.tvDefault);
        TextView tvOk = vLayout.findViewById(R.id.tvOk);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        final ModelSchoolLessons[] modelSchoolLessons = new ModelSchoolLessons[1];

        etEnterCreateGroup.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3){
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
            }

            @Override
            public void afterTextChanged(Editable arg0){
                if(arg0.length() == 0) isEnter[0] = false;
                else isEnter[0] = true;
                if(isEnter[0] && isEnter[1])
                    tvOk.setTextColor(getResources().getColor(R.color.blue_sea));
                else tvOk.setTextColor(getResources().getColor(R.color.gray_default));
            }
        });
        tvSelectLessonValue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                BSDSelectLesson bsdSelectLesson = new BSDSelectLesson(MentorActivity.this);
                bsdSelectLesson.setOnItemClickListener(new BSDSelectLesson.OnItemClickListener(){
                    @Override
                    public void onClick(ModelSchoolLessons m){
                        tvSelectLessonValue.setText(m.getLessonName());
                        modelSchoolLessons[0] = m;
                        isEnter[1] = true;
                        if(isEnter[0] && isEnter[1])
                            tvOk.setTextColor(getResources().getColor(R.color.blue_sea));
                        else tvOk.setTextColor(getResources().getColor(R.color.gray_default));
                    }
                });
            }
        });

        tvDefault.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                etEnterCreateGroup.setText("Новая группа");
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isEnter[0] && isEnter[1]){
                    presenter.onAddMentorGroup(etEnterCreateGroup.getText().toString(), modelSchoolLessons[0]);
                    alertDialog.dismiss();
                }
            }
        });
    }
    //ОТОБРАЖЕНИЕ СПИСКА ГРУПП
    private void onGetGroupSpinner(){
        /*if(svGroup.getVisibility() == View.GONE){
            svGroup.setVisibility(View.VISIBLE);
            ivGroup.setImageResource(R.drawable.img_white_arrow_up);
        }
        else{
            svGroup.setVisibility(View.GONE);
            ivGroup.setImageResource(R.drawable.img_white_arrow_dawn);
        }*/
    }
    private boolean onHideGroupList(){
        /*if(svGroup.getVisibility() == View.VISIBLE){
            svGroup.setVisibility(View.GONE);
            ivGroup.setImageResource(R.drawable.img_white_arrow_dawn);
            return true;
        }*/
        return false;
    }
    //ОТПРАВКА ЗАЯВКИ РОДИТЕЛЮ НА ДОБАВЛЕНИЕ
    private void onAddChildInGroup(){
        /*JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, MENTOR_ID);

        String SOAP = SoapRequest(func_GetChildrenList, jsonData.toString());
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
                    if(!result.equals("null")){
                        ArrayList<ModelUserData> userList = new Gson().fromJson(result, new TypeToken<ArrayList<ModelUserData>>(){
                        }.getType());
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MentorActivity.this);
                                View view = getLayoutInflater().inflate(R.layout.fragment_school_add_users, null, false);
                                bottomSheetDialog.setContentView(view);
                                View bottomSheet = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
                                BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                                ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
                                bottomSheet.setLayoutParams(layoutParams);
                                layoutParams.height = _System.getWindowHeight(MentorActivity.this);
                                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                                ImageView ivBack = view.findViewById(R.id.ivBack);
                                EditText etSearch = view.findViewById(R.id.etSearch);
                                RecyclerView recyclerView = view.findViewById(R.id.rvGradesList);

                                ivBack.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View v){
                                        bottomSheetDialog.dismiss();
                                    }
                                });

                                recyclerView.setLayoutManager(new LinearLayoutManager(MentorActivity.this, LinearLayoutManager.VERTICAL, false));
                                recyclerView.setAdapter(childListForAddedAdapter);
                                childListForAddedAdapter.updateData(userList);
                                childListForAddedAdapter.selectList();
                                childListForAddedAdapter.setOnItemClickListener(new ChildListForAddedAdapter.OnItemClickListener(){
                                    @Override
                                    public void onItemClick(ModelUserData m){
                                        DialogMentorAddChild dialog = new DialogMentorAddChild(MentorActivity.this, m);
                                        dialog.show();
                                        dialog.setOnItemClickListener(new DialogMentorAddChild.OnItemClickListener(){
                                            @Override
                                            public void onItemClick(ModelMentorGroups modelMentorGroup){
                                                JsonObject jsonData = new JsonObject();
                                                jsonData.addProperty(F.MentorId, MENTOR_ID);
                                                jsonData.addProperty(F.ChildId, m.getUserId());
                                                jsonData.addProperty(F.GroupId, modelMentorGroup.getGroupId());

                                                String msg = "Здравствуйте, прошу добавить меня в качестве ментора для вашего ребенка - " + m.getLogin() + ". По предмету - " + modelMentorGroup.getLessonName();

                                                jsonData.addProperty(F.SourceId, MENTOR_ID);
                                                jsonData.addProperty(F.TargetId, m.getParentId());
                                                jsonData.addProperty(F.Message, msg);

                                                String SOAP = SoapRequest(func_InterFormMentorToParent, jsonData.toString());
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
                                                                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                                                    alert.onToast(answer.getMessage());
                                                                    if(answer.isSuccess())
                                                                        presenter.onUpdateMentorData();
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                                bottomSheetDialog.show();
                            }
                        });
                    }
                }
            }
        });*/
    }
    //ПОКАЗАТЬ СПИСОК С ЗАЯВКАМИ
    private void onAdditionRequests(){
        /*lastSelectGroup.setGroupId("InterForm");
        lastSelectGroup.setGroupName("Заявки на добавление");
        tvGroupName.setText(lastSelectGroup.getGroupName());
        mentorUserListAdapter.selectList(lastSelectGroup.getGroupId());
        onHideGroupList();*/
    }
    //ВЫБОР ГРУППЫ ПО УМОЛЧАНИЮ ПРИ ЗАПУСКЕ ПРИЛОЖЕНИЯ
    public void SelectMentorDefaultGroup(ModelMentorGroups group){
        /*if(group == null){
            tvGroupName.setText(lastSelectGroup.getGroupName());
            mentorUserListAdapter.selectList(lastSelectGroup.getGroupId());
        }
        else if(lastSelectGroup == null){
            lastSelectGroup = group;
            tvGroupName.setText(lastSelectGroup.getGroupName());
            mentorUserListAdapter.selectList(lastSelectGroup.getGroupId());
        }
        else{
            tvGroupName.setText(lastSelectGroup.getGroupName());
            mentorUserListAdapter.selectList(lastSelectGroup.getGroupId());
        }
        if(lastSelectGroup.getChildrenList() != null && lastSelectGroup.getChildrenList().size() > 0)
            ivSendMessageToGroup.setVisibility(View.VISIBLE);
        else ivSendMessageToGroup.setVisibility(View.GONE);*/
    }
    //ВЫБОР ГРУППЫ ИЗ СПИСКА

    public void setChatModel(ModelMentorChat m){
        /*if(chatFragmentAdapter != null){
            chatFragmentAdapter.setChatModel(m);
        }*/
    }
    private void ivChat(){
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
    public void ChatVisible(boolean show){
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
    public void onItemClick(ModelSchoolData m){
        addSchoolFragmentAdapter.setSelectSchool(m);
        onNextFragment();
//        onSetSelectStudentData(m);

    }
    @Override
    public void onItemClick(ModelMentorGroups m){
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
    public void onItemClick(int i, ModelMentorChat m){

    }
    @Override
    public void onSelectClass(ModelSchoolClass m){
        classesFragmentAdapter.setSelectClass(m);
        onNextFragment();
        if(m.getLessons().size() > 1){
            classesFragmentAdapter.onShowDialogSelectLesson();
        }
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
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

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else if(FRAGMENT_ID > 0){
            CURRENT_PAGE--;
            if(CURRENT_PAGE < 0) onRemoveFragment(FRAGMENT_ID);
            else viewPager.setCurrentItem(CURRENT_PAGE);
        }
        else super.onBackPressed();
    }

    private void onClassessClick(){
        FRAGMENT_ID = 1;//CLASSES
        classesFragmentAdapter = new MentorClassesFragmentAdapter(getSupportFragmentManager());
        classesFragmentAdapter.setMentorData(mMentorData);

        CURRENT_PAGE = 0;
        viewPager.setAdapter(classesFragmentAdapter);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        viewPager.setOffscreenPageLimit(classesFragmentAdapter.getCount());
        viewPager.setCurrentItem(CURRENT_PAGE);
    }
    private void onStudentsClick(){
        FRAGMENT_ID = 2;//STUDENTS
        studentsFragment = MentorStudentsFragment.newInstance(mMentorData);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, studentsFragment).commit();
        studentsFragment.setData(mMentorData);
    }
    private void onSchoolsClick(){
        FRAGMENT_ID = 3;//SCHOOLS
        /*if(!listIsNullOrEmpty(mMentorData.getSchools()))
            for(ModelSchoolData _lesson : mMentorData.getSchools()){
                if(listIsNullOrEmpty(_lesson.getStudents())) _lesson.setStudents(new ArrayList<>());
                for(ModelSchoolStudent _student : mMentorData.getStudents())
                    if(_student.getClassId().equals(_lesson.getClassId()))
                        _lesson.getStudents().add(_student);
            }*/

        schoolsFragment = MentorSchoolsFragment.newInstance(mMentorData);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, schoolsFragment).commit();
    }
    private void onLessonClick(){
        FRAGMENT_ID = 4;//LESSONS
        lessonsFragment = MentorLessonsFragment.newInstance(mMentorData);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, lessonsFragment).commit();
    }

    public void onAddStudentClick(){
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
    public void onAddSchoolClick(){
        FRAGMENT_ID = 6;//ADD SCHOOL
        addSchoolFragmentAdapter = new MentorAddSchoolFragmentAdapter(getSupportFragmentManager());

        CURRENT_PAGE = 0;
        viewPager.removeAllViews();
        viewPager.setAdapter(addSchoolFragmentAdapter);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        viewPager.setOffscreenPageLimit(addSchoolFragmentAdapter.getCount());
        viewPager.setCurrentItem(CURRENT_PAGE);
    }
    public void onSetSelectStudentData(ModelUserData m){
        adapter.onSetSelectStudentData(m);
    }

    public void onClickRequest(ModelSchoolRequest m){
        FRAGMENT_ID = 7;//REQUEST
        if(requestFragment == null) requestFragment = SchoolRequestFragment.newInstance(m);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, requestFragment).commit();
    }

    //NAVIGATION
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
                GET.onEditAccess(MentorActivity.this, "1", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum2).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(MentorActivity.this, "2", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum3).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(MentorActivity.this, "3", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum4).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(MentorActivity.this, "4", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum5).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(MentorActivity.this, "5", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum6).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(MentorActivity.this, "6", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum7).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(MentorActivity.this, "7", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum8).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(MentorActivity.this, "8", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum9).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(MentorActivity.this, "9", dialog, ivDot, tvTitle);
            }
        });
        view.findViewById(R.id.tvNum0).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GET.onEditAccess(MentorActivity.this, "0", dialog, ivDot, tvTitle);
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
                jsonData.addProperty(F.UserId, MENTOR_ID);
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
}