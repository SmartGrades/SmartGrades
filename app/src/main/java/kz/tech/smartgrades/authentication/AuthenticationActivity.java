package kz.tech.smartgrades.authentication;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import javax.inject.Inject;
import kz.tech.smartgrades.App;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.L;
import kz.tech.smartgrades.S;
import kz.tech.smartgrades.authentication.mvp.IView;
import kz.tech.smartgrades.childs_module.ChildActivity;
import kz.tech.smartgrades.mentor_module.MentorActivity;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.language.ILanguage;
import kz.tech.smartgrades.root.login.ILogin;
import kz.tech.smartgrades.root.fragments.IFragments;

public class AuthenticationActivity extends AppCompatActivity implements IView {

    private AuthenticationView view;

    @Inject
    public ILogin login;
    @Inject
    public ILanguage language;
    @Inject
    public IFragments fragments;
    @Inject
    public IAlert alert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view = new AuthenticationView(this));
        //Init DI Dagger 2
        //Init objects
        init();
    }

    private void init() {
        String typeAccount = "";
        if (typeAccount != null) {
            switch (typeAccount) {
                case S.PARENT: onNextActivity(1); break;
                case S.MENTOR: onNextActivity(2); break;
                case S.CHILD: onNextActivity(3); break;
            }
        } else {
            fragments.initAuthenticationActivity(this);
            fragments.onReplaceFragment(F.SIGN_IN, L.layout_authentication);
        }
    }

    private void onNextActivity(int n) {
        switch (n) {
            //case 1: startActivity(new Intent(this, ParentActivity.class)); break;
            case 2: startActivity(new Intent(this, MentorActivity.class)); break;
            case 3: startActivity(new Intent(this, ChildActivity.class)); break;
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (view != null) { view = null; }
    }
}
