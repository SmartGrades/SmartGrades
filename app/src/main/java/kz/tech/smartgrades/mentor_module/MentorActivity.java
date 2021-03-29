package kz.tech.smartgrades.mentor_module;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import kz.tech.smartgrades.App;
import kz.tech.esparta.R;
import kz.tech.smartgrades.authentication.AuthenticationActivity;
import kz.tech.smartgrades.mentor_module.mvp.IView;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.date.IDateCustomPicker;
import kz.tech.smartgrades.root.language.ILanguage;
import kz.tech.smartgrades.root.login.ILogin;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.prefs.IPreferences;

public class MentorActivity extends AppCompatActivity implements IView {
    private MentorView view;
    public MentorFragmentAdapter adapter;
    private MentorView.MentorClickListener listener;

    @Inject
    public ILogin login;
    @Inject
    public ILanguage language;
    @Inject
    public IPreferences prefs;
    @Inject
    public IDateCustomPicker iDate;
    @Inject
    public IAlert alert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view = new MentorView(this));
        //  Init DI Dagger 2
        //Init views
        initViews();
    }
    private void initViews() {
        adapter = new MentorFragmentAdapter(getSupportFragmentManager());
        view.getViewPager().setAdapter(adapter);
        view.getViewPager().setCurrentItem(1);

        listener = new MentorView.MentorClickListener() {
            @Override
            public void onBackButtonClick(int position) {
                adapter.onFragmentListenerID(21, null);
            }
            @Override
            public void onMenuClick(View view) {
                onMenu(view);
            }
        };
        view.setMentorClickListener(listener);

        String avatar = login.loadUserDate(LoginKey.AVATAR);
        if (avatar != null) {
            view.setAvatar(avatar);
        }

    }


    @Override
    public void onMenu(View v) {
        PopupMenu menu = new PopupMenu(this, v);
        menu.getMenu().add(language.getLanguage().getString(R.string.exit)).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                login.deleteUserDate();
                startActivity(new Intent(MentorActivity.this, AuthenticationActivity.class));
                return false;
            }
        });
        menu.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onShowToast(String msg) {
        alert.onToast(msg);
    }

    @Override
    public void onNextFragment(String fragment) {

    }
}
