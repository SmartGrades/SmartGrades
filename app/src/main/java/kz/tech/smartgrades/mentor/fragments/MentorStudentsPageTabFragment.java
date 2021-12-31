package kz.tech.smartgrades.mentor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorStudentsListAdapterV2;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.mentor.models.ModelMentorStudentsList;
import kz.tech.smartgrades.school.models.ModelSchoolData;

public class MentorStudentsPageTabFragment extends Fragment implements MentorStudentsListAdapterV2.OnItemClickListener, View.OnClickListener{

    private MentorActivity activity;
    public static final String PAGE_ID = "PAGE_ID";
    private int PageId;

    private TextView tvLabelListIsEmpty;
    private RecyclerView rvStudents;
    private MentorStudentsListAdapterV2 studentsListAdapterV2;
    private ModelMentorData mMentorData;
    private ModelSchoolData mSchoolData;

    private CircleImageView civAddStudent;

    public static MentorStudentsPageTabFragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(PAGE_ID, page);
        MentorStudentsPageTabFragment fragment = new MentorStudentsPageTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null) PageId = getArguments().getInt(PAGE_ID);
        activity = (MentorActivity) getActivity();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_mentor_students_page_tab, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        tvLabelListIsEmpty = view.findViewById(R.id.tvLabelListIsEmpty);
        civAddStudent = view.findViewById(R.id.civAddStudent);
        civAddStudent.setOnClickListener(this);

//        studentsListAdapterV2 = new MentorStudentsListAdapterV2(activity);
//        rvStudents = view.findViewById(R.id.rvRequestList);
//        rvStudents.setLayoutManager(new LinearLayoutManager(activity));
//        rvStudents.setAdapter(studentsListAdapterV2);
//        studentsListAdapterV2.setOnItemClickListener(this);

        if(mMentorData != null){
            civAddStudent.setVisibility(View.VISIBLE);
        }
    }

    public void onSetData(ArrayList<ModelInterForm> requests){
//        if (tvLabelListIsEmpty != null) tvLabelListIsEmpty.setVisibility(View.GONE);
//        studentsListAdapterV2.updateData(requests);
    }
    public void onSetMentorData(ModelMentorData mMentorData){
        this.mMentorData = mMentorData;

    }
    public void onSetSchoolData(ModelSchoolData mSchoolData){
        this.mSchoolData = mSchoolData;

    }

    @Override
    public void onClick(View view){

    }
    @Override
    public void onItemLongClick(ModelMentorStudentsList m){

    }
}