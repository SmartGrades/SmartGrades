package kz.tech.smartgrades.family_room;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import kz.tech.smartgrades.App;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.L;
import kz.tech.smartgrades.authentication.AuthenticationActivity;
import kz.tech.smartgrades.family_room.mvp.IView;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.family_room.IFamily;
import kz.tech.smartgrades.root.fragments.IFragments;
import kz.tech.smartgrades.root.hardware_access.IHardwareAccess;
import kz.tech.smartgrades.root.language.ILanguage;
import kz.tech.smartgrades.root.login.ILogin;

public class FamilyRoomActivity extends AppCompatActivity implements IView {

    private FamilyRoomView view;
    public FamilyRoomPresenter presenter;

    @Inject
    public ILogin login;
    @Inject
    public IFragments fragments;
    @Inject
    public ILanguage language;
    @Inject
    public IHardwareAccess hardwareAccess;
    @Inject
    public IAlert alert;
    @Inject
    public IFamily family;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view = new FamilyRoomView(this));
        //Init Dagger 2
        //Init Views
        initViews();

        presenter = new FamilyRoomPresenter(this);

        fragments.initFamilyRoomActivity(this);
        onNextFragment(F.FAMILY_MEMBER_LIST);
    }

    private void initViews() {

    }

    @Override
    public void onExitLogin() {
        login.deleteUserDate();
        startActivity(new Intent(this, AuthenticationActivity.class));
    }

    @Override
    public void onReturnPreviousActivity() {
        onBackPressed();
        finish();
    }

    @Override
    public void onShowToast(String msg) {
        alert.onToast(msg);
    }

    @Override
    public void onNextFragment(String fragment) {
        fragments.onReplaceFragment(fragment, L.layout_family_room);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) { presenter.onDestroyView(); }
        if (view != null) { view = null; }
    }
}
