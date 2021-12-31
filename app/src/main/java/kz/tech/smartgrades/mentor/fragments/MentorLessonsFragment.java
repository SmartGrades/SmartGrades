package kz.tech.smartgrades.mentor.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorLessonsListAdapter;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.mentor.models.ModelMentorLesson;
import kz.tech.smartgrades.root.dialogs.ADAddLesson;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelLesson;
import kz.tech.smartgrades.school.adaptes.SchoolLessonsListAdapter;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class MentorLessonsFragment extends Fragment implements View.OnClickListener, SchoolLessonsListAdapter.OnItemClickListener, MentorLessonsListAdapter.OnItemClickListener{

    private MentorActivity activity;
    private ModelMentorData mMentorData;
    private String MENTOR_ID;

    private ImageView ivBack;
    private TextView tvLabel;

    private RecyclerView rvLessons;
    private MentorLessonsListAdapter lessonsListAdapter;

    private CircleImageView civAddLesson;

    private CardView cvSearch;
    private ImageView ivSearchShow, ivSearchHide;
    private EditText etSearch;
    private String TempText;

    public static MentorLessonsFragment newInstance(ModelMentorData mMentorData){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", mMentorData);
        MentorLessonsFragment fragment = new MentorLessonsFragment();
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
        return inflater.inflate(R.layout.fgmt_school_lessons, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        if(mMentorData != null) onLoadWardOnAddition();
    }
    private void initViews(View view){
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);

        tvLabel = view.findViewById(R.id.tvLabel);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        civAddLesson = view.findViewById(R.id.civAddLesson);
        civAddLesson.setOnClickListener(this);

        cvSearch = view.findViewById(R.id.cvSearch);
        ivSearchShow = view.findViewById(R.id.ivSearchShow);
        ivSearchShow.setOnClickListener(this);
        ivSearchHide = view.findViewById(R.id.ivSearchHide);
        ivSearchHide.setOnClickListener(this);
        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                lessonsListAdapter.onFilter(editable.toString());
                TempText = editable.toString();
            }
        });

        rvLessons = view.findViewById(R.id.rvLessons);
        lessonsListAdapter = new MentorLessonsListAdapter(activity);
        lessonsListAdapter.setOnItemClickListener(this);
        rvLessons.setLayoutManager(new GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false));
        rvLessons.setAdapter(lessonsListAdapter);
    }

    public void updateData(ModelMentorData mMentorData){
        this.mMentorData = mMentorData;
        if(!listIsNullOrEmpty(mMentorData.getLessons())){
            if(tvLabel != null) tvLabel.setVisibility(View.GONE);
            if(lessonsListAdapter != null) lessonsListAdapter.updateData(this.mMentorData.getLessons());
        }
        else if(tvLabel != null) tvLabel.setVisibility(View.VISIBLE);
    }
    private void onLoadWardOnAddition(){
        if(!listIsNullOrEmpty(mMentorData.getLessons())){
            if(tvLabel != null) tvLabel.setVisibility(View.GONE);
            if(lessonsListAdapter != null) lessonsListAdapter.updateData(this.mMentorData.getLessons());
        }
        else if(tvLabel != null) tvLabel.setVisibility(View.VISIBLE);
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
            case R.id.ivSearchShow:
                onSearchShowClick();
                break;
            case R.id.ivSearchHide:
                onSearchHideClick();
                break;
            case R.id.civAddLesson:
                onAddLessonClick();
                break;
        }
    }
    private void onSearchShowClick(){
        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = 0;
        cvSearch.setLayoutParams(params);
        ivSearchShow.setVisibility(View.GONE);
        etSearch.setVisibility(View.VISIBLE);
        ivSearchHide.setVisibility(View.VISIBLE);
    }
    private void onSearchHideClick(){
        if(ivSearchShow.getVisibility() == View.VISIBLE) return;
        TempText = "";
        etSearch.setText(TempText);
        activity.alert.hideKeyboard(activity);
        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        cvSearch.setLayoutParams(params);
        ivSearchShow.setVisibility(View.VISIBLE);
        etSearch.setVisibility(View.GONE);
        ivSearchHide.setVisibility(View.GONE);
    }
    private void onAddLessonClick(){
        ADAddLesson adAddLesson = new ADAddLesson(activity);
        adAddLesson.setOnItemClickListener(new ADAddLesson.OnItemClickListener(){
            @Override
            public void onOkClick(ModelLesson mLesson){
                ModelMentorLesson model = new ModelMentorLesson();
                model.setMentorId(MENTOR_ID);
                model.setLessonId(mLesson.getLessonId());
                activity.presenter.onAddLesson(model);
            }
        });
    }
    @Override
    public void onItemClick(ModelSchoolLesson m){

    }
}
