package kz.tech.smartgrades.child.fragments;

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
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.child.adapters.ChildTabInvitePagerFragmentAdapter;
import kz.tech.smartgrades.parent.adapters.ParentTabInvitePagerFragmentAdapter;

public class ChildInviteListFragment extends Fragment implements View.OnClickListener{

    private ChildActivity activity;

    private ImageView ivBack;
    private RecyclerView rvInviteList;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ChildTabInvitePagerFragmentAdapter tabInvitePagerFragmentAdapter;

    public ChildInviteListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ChildActivity) getActivity();
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
        tabInvitePagerFragmentAdapter = new ChildTabInvitePagerFragmentAdapter(activity.getSupportFragmentManager(), activity);
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
