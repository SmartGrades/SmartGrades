package kz.tech.smartgrades.school.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolTeachersListAdapter;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;

public class SchoolTeachersFragment extends Fragment implements View.OnClickListener, SchoolTeachersListAdapter.OnItemClickListener{

    private SchoolActivity activity;
    private String SCHOOL_ID;
    private ImageView ivBack;
    private TextView tvTitleBar;
    private ImageView ivSearch;
    private RecyclerView rvTeachers;
    private SchoolTeachersListAdapter teachersListAdapter;
    private CircleImageView civAddTeacher;
    private ModelSchoolData mSchoolData;

    public static SchoolTeachersFragment newInstance(ModelSchoolData modelSchoolData){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", modelSchoolData);
        SchoolTeachersFragment fragment = new SchoolTeachersFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (SchoolActivity) getActivity();
        if(getArguments() != null) mSchoolData = getArguments().getParcelable("model");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fgmt_school_teachers, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        if(mSchoolData != null) onLoadWardOnAddition();
    }
    private void initViews(View view){
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        civAddTeacher = view.findViewById(R.id.civAddTeacher);
        civAddTeacher.setOnClickListener(this);

        tvTitleBar = view.findViewById(R.id.tvTitleBar);
        ivSearch = view.findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(this);

        rvTeachers = view.findViewById(R.id.rvTeachers);
        teachersListAdapter = new SchoolTeachersListAdapter(activity);
        teachersListAdapter.setOnItemClickListener(this);
        rvTeachers.setLayoutManager(new LinearLayoutManager(activity));
        rvTeachers.setAdapter(teachersListAdapter);
        teachersListAdapter.updateData(mSchoolData.getTeachers());
    }
    public void updateData(ModelSchoolData modelSchoolData){
        mSchoolData = modelSchoolData;
        if(teachersListAdapter == null) return;
        teachersListAdapter.updateData(mSchoolData.getTeachers());
    }
    private void onLoadWardOnAddition(){
        /*Picasso.get().load(modelSchoolData.getAvatar()).fit().centerInside().into(civAvatar);
        tvTitle.setText(modelSchoolData.getFullName());
        String chatId = modelSchoolData.getId();*/
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
                break;
            case R.id.civAddTeacher:
                onAddTeacherClick();
                break;
        }
    }
    private void onBack(){
        activity.onBackPressed();
    }
    private void onAddTeacherClick(){
        SchoolAddContactFragment fragment = new SchoolAddContactFragment();
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, fragment).commit();
    }
    @Override
    public void onItemClick(ModelSchoolTeacher m){

    }
    public void onUpdateData(ModelSchoolData mSchoolData){
        this.mSchoolData = mSchoolData;
        if(teachersListAdapter!=null) teachersListAdapter.updateData(mSchoolData.getTeachers());
    }
}
