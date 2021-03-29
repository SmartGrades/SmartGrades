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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.dialogs.ADAddLesson;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelLesson;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolLessonsListAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolLessonsListAdapterV2;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;

public class SchoolLessonsFragment extends Fragment implements View.OnClickListener, SchoolLessonsListAdapter.OnItemClickListener, SchoolLessonsListAdapterV2.OnItemClickListener{

    private SchoolActivity activity;
    private String SCHOOL_ID;

    private ImageView ivBack;
    private TextView tvTitleBar;
    private ImageView ivSearch;

    private RecyclerView rvLessons;
    private SchoolLessonsListAdapterV2 lessonsListAdapter;

    private CircleImageView civAddLesson;

    private ModelSchoolData mSchoolData;

    public static SchoolLessonsFragment newInstance(ModelSchoolData modelSchoolData){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", modelSchoolData);
        SchoolLessonsFragment fragment = new SchoolLessonsFragment();
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
        return inflater.inflate(R.layout.fgmt_school_lessons, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        if(mSchoolData != null) onSetDataFromModelData();
    }
    private void initViews(View view){
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        civAddLesson = view.findViewById(R.id.civAddLesson);
        civAddLesson.setOnClickListener(this);

        tvTitleBar = view.findViewById(R.id.tvTitleBar);
        ivSearch = view.findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(this);

        rvLessons = view.findViewById(R.id.rvLessons);
        lessonsListAdapter = new SchoolLessonsListAdapterV2(activity);
        lessonsListAdapter.setOnItemClickListener(this);
        rvLessons.setLayoutManager(new GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false));
        rvLessons.setAdapter(lessonsListAdapter);
    }

    public void updateData(ModelSchoolData modelSchoolData){
        mSchoolData = modelSchoolData;
        if(lessonsListAdapter == null) return;
        lessonsListAdapter.updateData(mSchoolData.getLessons());
    }
    private void onSetDataFromModelData(){
        lessonsListAdapter.updateData(mSchoolData.getLessons());
    }

    private void onBack(){
        activity.onBackPressed();
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
                break;
            case R.id.civAddLesson:
                onAddLessonClick();
                break;
        }
    }
    private void onAddLessonClick(){
        ADAddLesson adAddLesson = new ADAddLesson(activity);
        adAddLesson.setOnItemClickListener(new ADAddLesson.OnItemClickListener(){
            @Override
            public void onOkClick(ModelLesson mLesson){
                ModelSchoolLesson model = new ModelSchoolLesson();
                model.setLessonName(mLesson.getLessonName());
                model.setLessonId(mLesson.getLessonId());
                model.setSchoolId(SCHOOL_ID);
                activity.presenter.onAddLesson(model);
            }
        });
    }
    @Override
    public void onItemClick(ModelSchoolLesson m){

    }

    public void onUpdateData(ModelSchoolData mSchoolData){
        this.mSchoolData = mSchoolData;
        if(lessonsListAdapter != null) lessonsListAdapter.updateData(mSchoolData.getLessons());
    }
}
