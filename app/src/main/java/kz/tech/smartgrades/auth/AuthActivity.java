package kz.tech.smartgrades.auth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yalantis.ucrop.UCrop;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.inject.Inject;

import kz.tech.smartgrades.App;
import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.auth.adapters.UserRegisterFragmentAdapterV2;
import kz.tech.smartgrades.auth.fragments.SignInFragment;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.auth.models.ModelRegisterData;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.auth.mvp.IView;

import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.custom_view.CustomViewPager;
import kz.tech.smartgrades.root.language.ILanguage;
import kz.tech.smartgrades.root.login.ILogin;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.Animation;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades.S.CHILD;
import static kz.tech.smartgrades.S.INVESTOR;
import static kz.tech.smartgrades.S.MENTOR;
import static kz.tech.smartgrades.S.PARENT;
import static kz.tech.smartgrades.S.SCHOOL;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_AuthUser;

public class AuthActivity extends AppCompatActivity implements IView, View.OnClickListener {

    private AuthPresenter presenter;

    private UserRegisterFragmentAdapterV2 RegisterAdapter;
    private CustomViewPager viewPager;
    public int CURRENT_ITEM = 0;

    @Inject
    public ILanguage language;
    @Inject
    public IAlert alert;
    @Inject
    public ILogin login;

    private boolean isAutoAuth = true;
    private int MODE = 0;//0-SignUp 1-Register
    private boolean FREEZE = false;

    private ConstraintLayout clToolbar;
    private ImageView ivBack;
    private TextView tvToolbar;

    private LinearLayout llProgressBar;


    public BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String SmsBody = intent.getStringExtra("SmsBody");
//            if (UserRegisterAdapter != null) {
//                UserRegisterAdapter.setSms(SmsBody);
//            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.app().getComponent().inject(this);
        setContentView(R.layout.activity_auth);
        onStartAuth();
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, new IntentFilter("sms"));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    public void onStartAuth() {
        String Login = login.loadUserDate(LoginKey.PHONE);
        if (stringIsNullOrEmpty(Login)) {
            Login = login.loadUserDate(LoginKey.MAIL);
            if (stringIsNullOrEmpty(Login)) {
                Login = login.loadUserDate(LoginKey.PHONE_OR_MAIL);
                if (stringIsNullOrEmpty(Login)) {
                    onSignIn();
                    return;
                }
            }
        }
        String Password = login.loadUserDate(LoginKey.PASSWORD);
        String Token = FirebaseInstanceId.getInstance().getToken();
        onAuth(Login, Password, Token);
    }
    public void onAuth(String Login, String Password, String Token) {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("Login", Login);
        jsonData.addProperty("Password", Password);
        jsonData.addProperty("Token", Token);

        String SOAP = SoapRequest(func_AuthUser, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                runOnUiThread(() -> {
                    alert.onToast("Сервер не доступен");
//                    String answer = e.toString();
//                    alert.onToast(answer);
                });
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    runOnUiThread(() -> {
                        if (answer.isSuccess()) {
                            login.saveUserData(new Gson().fromJson(result, ModelUser.class));
                            onNextActivity(login.loadUserDate(LoginKey.TYPE));
                        }
                        else {
                            if (isAutoAuth) {
                                login.deleteUserDate();
                                onStartAuth();
                                isAutoAuth = false;
                            }
                            else {
                                if (answer.getCode() == 100) {
                                    alert.onToast("Не верно указан номер телефона или почта");
                                }
                                else if (answer.getCode() == 110)
                                    alert.onToast("Не указан номер телефона или почта");
                                else if (answer.getCode() == 200)
                                    alert.onToast("Не верно указан пароль");
                                else if (answer.getCode() == 210) alert.onToast("Не указан пароль");
                                else if (answer.getCode() == 10)
                                    alert.onToast("Пользователь не найден");
                            }
                        }
                    });
                }
            }
        });
    }
    private void onSignIn() {
        initViews();
        onSetProgressBarVisible(false);
        initPresenter();
        onShowSignInFragment();
    }
    private void initViews() {
        clToolbar = findViewById(R.id.clToolbar);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        viewPager = findViewById(R.id.viewPager);
        llProgressBar = findViewById(R.id.llRegister);
        tvToolbar = findViewById(R.id.tvToolbar);
    }
    private void initPresenter() {
        presenter = new AuthPresenter(this);
    }
    public void onShowSignInFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SignInFragment()).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int MY_PERMISSIONS_REQUEST_SMS_RECEIVE = 10;
        if (requestCode == MY_PERMISSIONS_REQUEST_SMS_RECEIVE) {
            // YES!!
            Log.i("TAG", "MY_PERMISSIONS_REQUEST_SMS_RECEIVE --> YES");
        }
    }

    public String onTranslateString(int str) {
        return language.getLanguage().getString(str);
    }

    public void onNextFragment() {
        CURRENT_ITEM++;
        viewPager.setCurrentItem(CURRENT_ITEM);
    }
    private void onHideFragments() {
        for (Fragment fragment : getSupportFragmentManager().getFragments())
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }
    private void onHideFragments(ArrayList<Fragment> fragments) {
        for (Fragment fragment : fragments) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    public void onRegistrationClick() {
        MODE = 1;
        RegisterAdapter = new UserRegisterFragmentAdapterV2(viewPager, getSupportFragmentManager());
        viewPager.setAdapter(RegisterAdapter);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        viewPager.setOffscreenPageLimit(RegisterAdapter.getCount());

        onSetToolbarText("Выберите роль");

        Animation animation = new Animation();
        animation.Play(viewPager, Animation.Type.BOT, 300);
        animation.setOnFinishListener(new Animation.OnFinishListener() {
            @Override
            public void onFinish() {
                clToolbar.setAlpha(0f);
                onSetToolbarVisible(true);
                clToolbar.animate().alpha(1f).setDuration(300);
            }
        });
    }
    public void onRegister() {
        presenter.onRegister();
        FREEZE = true;
    }

    @Override
    public void onBackPressed() {
        alert.hideKeyboard(this);
        if (FREEZE) return;
        if (MODE == 1) {
            if (CURRENT_ITEM > 0) {
                CURRENT_ITEM--;
                if (CURRENT_ITEM == 0) onSetToolbarText("Выберите роль");
                viewPager.setCurrentItem(CURRENT_ITEM);
            }
            else {
                MODE = 0;

                AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
                alphaAnimation.setDuration(300);
                clToolbar.startAnimation(alphaAnimation);
                alphaAnimation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(android.view.animation.Animation animation) {
                    }
                    @Override
                    public void onAnimationEnd(android.view.animation.Animation animation) {
                        clToolbar.setVisibility(View.GONE);
                    }
                    @Override
                    public void onAnimationRepeat(android.view.animation.Animation animation) {
                    }
                });

                Animation animation = new Animation();
                animation.Play(viewPager, Animation.Type.TOP, 300);
                animation.setOnFinishListener(() -> {
                    viewPager.removeAllViews();
                    onHideFragments(RegisterAdapter.getItems());
                    RegisterAdapter = null;
                });
            }
        }
        else if (MODE == 0) super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }

    public void onNextActivity(String type) {
        switch (type) {
            case PARENT:
                startActivity(new Intent(this, ParentActivity.class));
                break;
            case MENTOR:
                startActivity(new Intent(this, MentorActivity.class));
                break;
            case CHILD:
                startActivity(new Intent(this, ChildActivity.class));
                break;
            case SCHOOL:
                startActivity(new Intent(this, SchoolActivity.class));
                break;
            case INVESTOR:
                startActivity(new Intent(this, SponsorActivity.class));
                break;
        }
        finish();
    }
    ////////////       IPresenter       ////////////

    ////////////       SETTERS / GETTERS       ////////////
    public void setType(String type) {
        presenter.setType(type);
        RegisterAdapter.onSetType(type);
        switch (type) {
            case PARENT:
                tvToolbar.setText("Регистрация аккаунта родителя");
                break;
            case CHILD:
                tvToolbar.setText("Регистрация аккаунта ребенка");
                break;
            case MENTOR:
                tvToolbar.setText("Регистрация аккаунта репетитора / тренера / учителя");
                break;
            case INVESTOR:
                tvToolbar.setText("Регистрация аккаунта спонсора");
                break;
            case SCHOOL:
                tvToolbar.setText("Регистрация аккаунта школы");
                break;
        }

    }
    public String getType() {
        return presenter.getType();
    }
    public void setLogin(String login) {
        presenter.setLogin(login);
    }
    public void setPhoneOrMail(String login) {
        presenter.setPhoneOrMail(login);
    }
    public void setBirthday(String birthday) {
        presenter.setBirthday(birthday);
    }
    public void setSchoolName(String SchoolName) {
        presenter.setSchoolName(SchoolName);
    }
    public void setCountryId(String Country) {
        presenter.setCountry(Country);
    }
    public void setPassword(String password) {
        presenter.setPassword(password);
    }

//    public void setSchoolData(ModelSchoolData modelSchoolData) {
//        presenter.setSchoolData(modelSchoolData);
//    }
//    public ModelSchoolData getSchoolData() {
//        return presenter.getSchoolData();
//    }
//    public ModelUser getUserData() {
//        return presenter.getUserData();
//    }
    //

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            try {
                InputStream imageStream = getContentResolver().openInputStream(resultUri);
//                UserRegisterAdapter.setPhoto(BitmapFactory.decodeStream(imageStream));
            }
            catch (Exception ignored) {}
        }
        else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setViewPagerFullScreen() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(Convert.DpToPx(this, 0), Convert.DpToPx(this, 0), Convert.DpToPx(this, 0), Convert.DpToPx(this, 0));
        viewPager.setLayoutParams(params);
    }
    public void setViewPagerNormal() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(Convert.DpToPx(this, 0), Convert.DpToPx(this, 50), Convert.DpToPx(this, 0), Convert.DpToPx(this, 0));
        viewPager.setLayoutParams(params);
    }

    public void onSetToolbarVisible(boolean isVisible) {
        clToolbar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
    public void onSetToolbarText(String text) {
        tvToolbar.setText(text);
    }
    public void onSetProgressBarVisible(boolean isVisible) {
        if (llProgressBar != null)
            llProgressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void onSaveRegisterData(ModelRegisterData mRegisterData) {
        login.saveUserData(mRegisterData);
    }
}
