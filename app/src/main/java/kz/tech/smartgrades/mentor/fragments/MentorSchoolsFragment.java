package kz.tech.smartgrades.mentor.fragments;

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

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorSchoolsAdapter;
import kz.tech.smartgrades.mentor.models.ModelMentorData;

public class MentorSchoolsFragment extends Fragment implements View.OnClickListener{

    private MentorActivity activity;
    private ImageView ivBack;
    private RecyclerView rvSchools;
    private MentorSchoolsAdapter schoolsAdapter;
    private ModelMentorData mMentorData;

    private CircleImageView civAddSchool;

    public static MentorSchoolsFragment newInstance(ModelMentorData modelMentorData){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", modelMentorData);
        MentorSchoolsFragment fragment = new MentorSchoolsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (MentorActivity) getActivity();
        if(getArguments() != null) mMentorData = getArguments().getParcelable("model");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fgmt_mentor_schools, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        onLoadRequestMessages();
    }
    private void initUI(View view){
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        civAddSchool = view.findViewById(R.id.civAddSchool);
        civAddSchool.setOnClickListener(this);

        schoolsAdapter = new MentorSchoolsAdapter(activity);
        rvSchools = view.findViewById(R.id.rvSchools);
        rvSchools.setLayoutManager(new LinearLayoutManager(activity));
        rvSchools.setAdapter(schoolsAdapter);
    }
    private void onLoadRequestMessages(){
        schoolsAdapter.updateData(mMentorData.getSchools());
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBackClick();
                break;
            case R.id.civAddSchool:
                activity.onAddSchoolClick();
                break;
        }
    }
    private void onBackClick(){
        activity.onBackPressed();
    }
}
