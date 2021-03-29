package kz.tech.smartgrades.parent_add_mentor_or_sponsor;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import kz.tech.smartgrades.App;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.model.ModelParentChildList;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.mvp.IView;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.custom_view.CustomViewPager;
import kz.tech.smartgrades.root.language.ILanguage;
import kz.tech.smartgrades.root.login.ILogin;

public class ParentAddSponsorOrMentorActivity extends AppCompatActivity implements IView, View.OnClickListener {

    @Inject
    public ILanguage language;
    @Inject
    public IAlert alert;
    @Inject
    public ILogin login;

    public ParentAddMentorFragmentAdapter parentAddMentorFragmentAdapter;
    private CustomViewPager viewPager;
    public ParentAddMentorPresenter presenter;

    private EditText etSearch;
    private androidx.appcompat.widget.Toolbar toolbar;
    private static final int CURRENT_PAGE = 0;

    private ArrayList<ModelParentChildList> ParentChildLists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.app().getComponent().inject(this);
        setContentView(R.layout.activity_parent_add_mentor);
        initViews();
        String type = getIntent().getStringExtra("Type");
        ParentChildLists = getIntent().getParcelableArrayListExtra("ParentChildLists");
        initPresenter(type);
    }

    private void initViews() {
        etSearch = findViewById(R.id.etSearch);
        viewPager = findViewById(R.id.viewPager);
        parentAddMentorFragmentAdapter = new ParentAddMentorFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(parentAddMentorFragmentAdapter);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
        viewPager.setOffscreenPageLimit(6);
        viewPager.setCurrentItem(CURRENT_PAGE);
        toolbar = findViewById(R.id.toolbar);
    }

    private void initPresenter(String type) {
        presenter = new ParentAddMentorPresenter(this);
        presenter.onStartPresenter(type);
    }

    public ArrayList<ModelParentChildList> getParentChildLists(){
        return ParentChildLists;
    }

    public void hideToolbar() {
        toolbar.setVisibility(View.GONE);
    }

    public void onBack() {
        switch (viewPager.getCurrentItem()) {
            case 0: onBackPressed(); break;
            case 1: viewPager.setCurrentItem(0); break;
            case 2: viewPager.setCurrentItem(0); break;
            case 3: viewPager.setCurrentItem(1); break;
            case 4: viewPager.setCurrentItem(2); break;
            case 5: viewPager.setCurrentItem(0); break;
        }
    }

    public void onNextFragment(int page) {
        viewPager.setCurrentItem(page);
    }

    ////////////       Activity LifeCycle       ////////////
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroyView();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    ////////////       Implements Interfaces       ////////////
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
