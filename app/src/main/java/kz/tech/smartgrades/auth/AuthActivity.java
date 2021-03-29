package kz.tech.smartgrades.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import kz.tech.smartgrades.App;
import kz.tech.esparta.R;
import kz.tech.smartgrades.S;
import kz.tech.smartgrades.auth.adapters.SchoolRegisterFragmentAdapter;
import kz.tech.smartgrades.auth.adapters.UserRegisterFragmentAdapter;
import kz.tech.smartgrades.auth.fragments.SignInFragment;
import kz.tech.smartgrades.auth.fragments.TypeFragment;
import kz.tech.smartgrades.auth.mvp.IView;

import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.custom_view.CustomViewPager;
import kz.tech.smartgrades.root.language.ILanguage;
import kz.tech.smartgrades.root.login.ILogin;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.sponsor.SponsorActivity;

public class AuthActivity extends AppCompatActivity implements IView, View.OnClickListener {

    private CustomViewPager viewPager;
    private UserRegisterFragmentAdapter UserRegisterAdapter;
    private SchoolRegisterFragmentAdapter SchoolRegisterAdapter;
    public AuthPresenter presenter;
    private String Type;

    @Inject
    public ILanguage language;
    @Inject
    public IAlert alert;
    @Inject
    public ILogin login;

    public int CURRENT_PAGE = 0;
    private int MODE = 0;//0 - SignUp; 1-StatusSelect; 2-Register

    private ImageView ivBack;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.app().getComponent().inject(this);
        setContentView(R.layout.activity_auth);
        onLoadStorage();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        viewPager = findViewById(R.id.viewPager);
    }

    private void initPresenter() {
        presenter = new AuthPresenter(this);
    }

    public void onShowSignInFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SignInFragment()).commit();
    }
    public void onShowStatusSelectFragment() {
        if (MODE == 1){
            toolbar.setVisibility(View.VISIBLE);
            for (Fragment fragment : getSupportFragmentManager().getFragments())
                if (fragment instanceof SignInFragment) getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new TypeFragment()).commit();
        }
        else if (MODE == 0){
            toolbar.setVisibility(View.GONE);
            for (Fragment fragment : getSupportFragmentManager().getFragments())
                if (fragment instanceof TypeFragment) getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new SignInFragment()).commit();
        }
        else if (MODE == 2){
            toolbar.setVisibility(View.GONE);
            for (Fragment fragment : getSupportFragmentManager().getFragments())
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new SignInFragment()).commit();
        }
    }

    private void onLoadStorage() {
        String type = login.loadUserDate(LoginKey.TYPE);
        if (type == null) onSignIn();
        else {
            switch (type) {
                case S.PARENT:
                    onNextActivity(1);
                    return;
                case S.MENTOR:
                    onNextActivity(2);
                    return;
                case S.CHILD:
                    onNextActivity(3);
                    return;
                case S.SCHOOL:
                    onNextActivity(4);
                    return;
                case S.SPONSOR:
                    onNextActivity(5);
                    return;
            }
        }
    }

    public String onTranslateString(int str) {
        return language.getLanguage().getString(str);
    }

    public void onNextFragment(int statusSelect) {
        TextView tvTitleBar = findViewById(R.id.tvTitleBar);
        MODE = 2;
        if (statusSelect == 4){
            tvTitleBar.setText("Создание аккаунта для школы");
            SchoolRegisterAdapter = new SchoolRegisterFragmentAdapter(getSupportFragmentManager());
            viewPager.setAdapter(SchoolRegisterAdapter);
            viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
            viewPager.setOffscreenPageLimit(SchoolRegisterAdapter.getCount());
        }
        else{
            tvTitleBar.setText("Создание аккаунта");
            UserRegisterAdapter = new UserRegisterFragmentAdapter(getSupportFragmentManager());
            viewPager.setAdapter(UserRegisterAdapter);
            viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
            viewPager.setOffscreenPageLimit(UserRegisterAdapter.getCount());
            UserRegisterAdapter.setType(Type);
        }
        viewPager.setCurrentItem(CURRENT_PAGE);
    }
    public void onNextFragment() { // ????????
        CURRENT_PAGE++;
        if (CURRENT_PAGE == 1) toolbar.setVisibility(View.VISIBLE);
        if (CURRENT_PAGE > viewPager.getOffscreenPageLimit() - 1) {
            CURRENT_PAGE = 0;
            toolbar.setVisibility(View.GONE);
            onHideFragments();
            onShowSignInFragment();
            return;
        }
        viewPager.setCurrentItem(CURRENT_PAGE);
    }

    private void onHideFragments() {
        for (Fragment fragment : getSupportFragmentManager().getFragments())
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    private void onSignIn() {
        findViewById(R.id.llRegoster).setVisibility(View.GONE);
        initViews();
        initPresenter();
        onShowSignInFragment();
    }
    public void onRegistration() {
        MODE = 1;
        onShowStatusSelectFragment();
    }

    @Override
    public void onBackPressed() {
        if (MODE == 1){
            MODE = 0;
            onShowStatusSelectFragment();
        }
        else if (MODE == 2 && CURRENT_PAGE == 0) {
            onShowStatusSelectFragment();
        }
        else if (MODE == 2 && CURRENT_PAGE > 0){
            CURRENT_PAGE--;
            viewPager.setCurrentItem(CURRENT_PAGE);
            if (CURRENT_PAGE <= 0) toolbar.setVisibility(View.GONE);
        }
        else super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }

    public void onNextActivity(int n) {
        switch (n) {
            case 1:
                startActivity(new Intent(this, ParentActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, MentorActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, ChildActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, SchoolActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, SponsorActivity.class));
                break;
        }
        finish();
    }
    ////////////       IPresenter       ////////////


    ////////////       PAGE CLICK       ////////////
    public void onSetPhone(String phone) {
        presenter.setPhone(phone);
    }
    public void onSetMail(String Mail) {
        presenter.setMail(Mail);
    }
    public void onSetType(String statusSelect) {
        Type = statusSelect;
        presenter.setType(statusSelect);
    }
    public void onFullNameString(String firstName, String LastName) {
        presenter.setFullName(firstName, LastName);
    }
    public void onSetAvatarOriginal(String avatarSelect) {
        presenter.setAvatarOriginal(avatarSelect);
    }
    public void onSetAvatar(String avatarSelect) {
        presenter.setAvatar(avatarSelect);
    }
    public void onBirthdayString(String birthday) {
        presenter.setBirthday(birthday);
    }
    public void onSetLogin(String login) {
        presenter.setLogin(login);
    }
    public void onPasswordString(String password) {
        presenter.setPassword(password);
    }
    public void onAccessCodeString(String accessCode) {
        presenter.setAccessCode(accessCode);
    }
    public void onRegister() {
        presenter.onRegister();
    }

    public void setSchoolData(ModelSchoolData modelSchoolData) {
        presenter.setSchoolData(modelSchoolData);
    }
    public ModelSchoolData getSchoolData() {
        return presenter.getSchoolData();
    }

    public String getType() {
        return Type;
    }
}
