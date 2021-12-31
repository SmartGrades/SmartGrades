package kz.tech.smartgrades.parent.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.models.ModelMentorList;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentMentorListAdapter;
import kz.tech.smartgrades.parent.adapters.ParentSelectedMentorListAdapter;

public class ParentMentorListFragment extends Fragment implements View.OnClickListener,
        ParentMentorListAdapter.OnItemClickListener{

    private ParentActivity activity;

    private ImageView ivBack;
    private RecyclerView rvSelectedMentors;
    private RecyclerView rvMentorsList;

    private CardView cvOk;

    private ArrayList<ModelMentorList> mentorList;
    private ParentMentorListAdapter parentMentorListAdapter;

    private ArrayList<ModelMentorList> selectedMentorList;
    private ParentSelectedMentorListAdapter parentSelectedMentorListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
        selectedMentorList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_mentor_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    public void setMentorListAdapter() {
        rvMentorsList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        parentMentorListAdapter = new ParentMentorListAdapter(activity);
        parentMentorListAdapter.setOnItemClickListener(this);
        rvMentorsList.setAdapter(parentMentorListAdapter);
        parentMentorListAdapter.updateData(mentorList);
        parentMentorListAdapter.checkToSelected(selectedMentorList);
    }

    public void setSelectedMentorListAdapter() {
        rvSelectedMentors.setLayoutManager(new GridLayoutManager(activity, 2));
        parentSelectedMentorListAdapter = new ParentSelectedMentorListAdapter(activity);
        parentSelectedMentorListAdapter.setOnItemClickListener(activity);
        rvSelectedMentors.setAdapter(parentSelectedMentorListAdapter);
        parentSelectedMentorListAdapter.updateData(selectedMentorList);
    }

    public void setMentorList(ArrayList<ModelMentorList> mentorList) {
        this.mentorList = mentorList;
        setMentorListAdapter();
        setSelectedMentorListAdapter();
    }

    public void setSelectedMentor(ModelMentorList mMentor) {
        selectedMentorList.add(mMentor);
        setSelectedMentorListAdapter();
    }

    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        rvSelectedMentors = view.findViewById(R.id.rvSelectedMentors);
        rvMentorsList = view.findViewById(R.id.rvMentorsList);
        cvOk = view.findViewById(R.id.cvOk);
        cvOk.setOnClickListener(this);
    }

    private void onBack() {
        activity.onBackPressed();
    }

    private void addMentors() {
        activity.addMentors(selectedMentorList);
        onBack();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.cvOk:
                addMentors();
                break;
        }
    }

    @Override
    public void onClick(ModelMentorList mMentor) {
        activity.setMentorModel(mMentor);
        activity.onNextFragment();
    }

    public void onDeleteMentorFromSelectedList(int p) {
        selectedMentorList.remove(p);
        parentSelectedMentorListAdapter.updateData(selectedMentorList);
        parentMentorListAdapter.checkToSelected(selectedMentorList);
        parentMentorListAdapter.notifyDataSetChanged();
    }
}
