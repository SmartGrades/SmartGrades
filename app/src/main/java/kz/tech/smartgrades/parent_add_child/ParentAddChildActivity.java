package kz.tech.smartgrades.parent_add_child;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yalantis.ucrop.UCrop;

import java.io.InputStream;

import javax.inject.Inject;

import kz.tech.smartgrades.App;
import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.parent_add_child.mvp.IView;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.custom_view.CustomViewPager;
import kz.tech.smartgrades.root.language.ILanguage;
import kz.tech.smartgrades.root.login.ILogin;
import kz.tech.smartgrades.root.login.LoginKey;

public class ParentAddChildActivity extends AppCompatActivity implements IView, View.OnClickListener {

    @Inject
    public ILanguage language;
    @Inject
    public IAlert alert;
    @Inject
    public ILogin login;

    private CustomViewPager viewPager;
    private ParentAddChildFragmentAdapter AddChildAdapter;
    private ParentSearchChildFragmentAdapter SearchChildAdapter;
    private ParentAddChildPresenter presenter;

    private ImageView ivBack;
    private LinearLayout llAddChild;
    private Button btnAddChild, btnSearchChild;

     private int CURRENT_PAGE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.app().getComponent().inject(this);
        setContentView(R.layout.activity_parent_add_child);
        initViews();
        initPresenter();
        onAddChildClick();
    }

    private void initViews() {
        llAddChild = findViewById(R.id.llAddChild);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        btnAddChild = findViewById(R.id.btnAddChild);
        btnAddChild.setOnClickListener(this);
        btnSearchChild = findViewById(R.id.btnSearchChild);
        btnSearchChild.setOnClickListener(this);

        viewPager = findViewById(R.id.viewPager);
        AddChildAdapter = new ParentAddChildFragmentAdapter(getSupportFragmentManager());
        SearchChildAdapter = new ParentSearchChildFragmentAdapter(getSupportFragmentManager());
    }

    private void initPresenter() {
        presenter = new ParentAddChildPresenter(this);
    }

    public String onTranslateString(int str) {
        return language.getLanguage().getString(str);
    }

    public void onNextFragment() {
        CURRENT_PAGE++;
        viewPager.setCurrentItem(CURRENT_PAGE);
    }
    public void onNextFragment(int PageCount) {
        CURRENT_PAGE += PageCount;
        viewPager.setCurrentItem(CURRENT_PAGE);
    }
    public void onNextFragment(ModelUser m) {
        SearchChildAdapter.setProfileData(m);
        CURRENT_PAGE ++;
        viewPager.setCurrentItem(CURRENT_PAGE);
    }

    ////////////       SET DATA       ////////////
    public void setChildFullName(String firstName, String lastName) {
        presenter.setChildFullName(firstName, lastName);
    }
    public void setChildDateBirthday(String birthdayChild) {
        presenter.setChildDateBirthday(birthdayChild);
    }
    public void onAddress(String address) {
        presenter.setAddress(address);
    }
    public void setChildLogin(String loginChild) {
        presenter.setChildLogin(loginChild);
    }
    public void setAvatar(String avatarSelectChild) {
        presenter.setChildAvatar(avatarSelectChild);
    }
    public void setAvatarOriginal(String avatarOriginalSelectChild) {
        presenter.setChildAvatarOriginal(avatarOriginalSelectChild);
    }
    public void setChildAccessCode(String accessCodeChild) {
        presenter.setChildAccessCode(accessCodeChild);
    }
    public void setChildPassword(String password) {
        presenter.setChildPassword(password);
    }
    public void setChildMail(String Mail) {
        presenter.setChildMail(Mail);
    }
    public void setChildPhone(String Phone) {
        presenter.setChildPhone(Phone);
    }
    public void onRegisterChild() {
        presenter.onRegisterChild(login.loadUserDate(LoginKey.ID));
    }
    ////////////       SET DATA       ////////////

    @Override
    public void onBackPressed() {
        if (CURRENT_PAGE <= 0){
            /*if (viewPager.getVisibility() == View.VISIBLE){
                viewPager.setVisibility(View.GONE);
                llAddChild.setVisibility(View.VISIBLE);
            }*/
            super.onBackPressed();

        }
        else {
            CURRENT_PAGE--;
            viewPager.setCurrentItem(CURRENT_PAGE);
        }
    }

    private void onAddChildClick() {
        llAddChild.setVisibility(View.GONE);
        viewPager.setAdapter(AddChildAdapter);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        viewPager.setOffscreenPageLimit(7);
        viewPager.setCurrentItem(CURRENT_PAGE);
        viewPager.setVisibility(View.VISIBLE);
    }
    private void onSearchChildClick() {
        llAddChild.setVisibility(View.GONE);
        viewPager.setAdapter(SearchChildAdapter);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(CURRENT_PAGE);
        viewPager.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnAddChild:
                onAddChildClick();
                break;
            case R.id.btnSearchChild:
                onSearchChildClick();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            try{
                InputStream imageStream = getContentResolver().openInputStream(resultUri);
                AddChildAdapter.setPhoto(BitmapFactory.decodeStream(imageStream));
            } catch(Exception ignored){}
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
