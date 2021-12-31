package kz.tech.smartgrades.parent_add_mentor_or_sponsor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.S;
import kz.tech.smartgrades.parent.model.ModelMentorSponsorRoom;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.ParentAddSponsorOrMentorActivity;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.adapters.SearchUsersListAdapter;

public class ParentSearchMentorOrSponsorFragment extends Fragment implements View.OnClickListener, SearchUsersListAdapter.OnItemClickListener {

    private ParentAddSponsorOrMentorActivity activity;
    private RecyclerView recyclerView;
    private SearchUsersListAdapter usersListAdapter;

    private ImageView ivBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddSponsorOrMentorActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_search_mentor, container, false);
    }

    public SearchUsersListAdapter getUsersListAdapter(){
        return usersListAdapter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.rvGradesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        usersListAdapter = new SearchUsersListAdapter(activity);
        recyclerView.setAdapter(usersListAdapter);
        usersListAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(ModelMentorSponsorRoom m) {
        activity.alert.hideKeyboard(activity);
        if (m.getType().equals(S.MENTOR)){
            activity.parentAddMentorFragmentAdapter.setMentorProfileData(m);
            activity.onNextFragment(1);
        }
        else {
            activity.parentAddMentorFragmentAdapter.setSponsorProfileData(m);
            activity.onNextFragment(2);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivBack: onBackClick(); break;
        }
    }

    private void onBackClick() {
        activity.onBack();
    }
}