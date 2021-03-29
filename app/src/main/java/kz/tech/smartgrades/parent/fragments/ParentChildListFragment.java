package kz.tech.smartgrades.parent.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentChildListAdapter;
import kz.tech.smartgrades.parent.model.ModelFamilyGroup;

public class ParentChildListFragment extends Fragment implements View.OnClickListener {

    private ParentActivity activity;

    private CardView cvAddChild, cvAddMentor;
    private RecyclerView rvChildList;
    private ParentChildListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_child_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        //onLoadChildList();
    }

    private void initViews(View view) {
        cvAddChild = view.findViewById(R.id.cvAddChild);
        cvAddChild.setOnClickListener(this);
        cvAddMentor = view.findViewById(R.id.cvAddMentor);
        cvAddMentor.setOnClickListener(this);
        rvChildList = view.findViewById(R.id.rvChildList);
        rvChildList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        adapter = new ParentChildListAdapter(activity);
        rvChildList.setAdapter(adapter);
    }

    public void onUpdateChildList(ArrayList<ModelFamilyGroup> familyRoomList) {
        adapter.update(familyRoomList);
    }


    private void onAddChild() {
        //activity.onNextActivity(1, false, "", FamilyGroups);
    }

    private void onAddMentor() {
        //activity.onNextActivity(2, false, "Mentor", FamilyGroups);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cvAddChild:
                onAddChild();
                break;
            case R.id.cvAddMentor:
                onAddMentor();
                break;
        }
    }
}
