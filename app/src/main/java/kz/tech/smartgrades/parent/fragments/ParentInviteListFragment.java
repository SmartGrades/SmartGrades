package kz.tech.smartgrades.parent.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentTabInvitePagerFragmentAdapter;

public class ParentInviteListFragment extends Fragment implements View.OnClickListener{

    private ParentActivity activity;

    private ImageView ivBack;
    private RecyclerView rvInviteList;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ParentTabInvitePagerFragmentAdapter tabInvitePagerFragmentAdapter;

    public ParentInviteListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_invite_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setTabInvitesAdapter();
    }

    private void setTabInvitesAdapter() {
        tabInvitePagerFragmentAdapter = new ParentTabInvitePagerFragmentAdapter(activity.getSupportFragmentManager(), activity);
        viewPager.setAdapter(tabInvitePagerFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setInvites();
    }

    public void setInvites() {
        tabInvitePagerFragmentAdapter.setInvites();
    }

    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        rvInviteList = view.findViewById(R.id.rvInviteList);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
    }

    private void onBack() {
        activity.onRemoveFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
        }
    }
}
