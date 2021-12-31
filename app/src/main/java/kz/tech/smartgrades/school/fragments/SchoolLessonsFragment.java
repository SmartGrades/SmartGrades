package kz.tech.smartgrades.school.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
    private TextView tvTitle;

    private CardView cvSearch;
    private ImageView ivSearchShow, ivSearchHide;
    private EditText etSearch;

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

        tvTitle = view.findViewById(R.id.tvTitle);

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
                lessonsListAdapter.filter(editable.toString());
            }
        });

        rvLessons = view.findViewById(R.id.rvLessons);
        lessonsListAdapter = new SchoolLessonsListAdapterV2(activity);
        lessonsListAdapter.setOnItemClickListener(this);
        rvLessons.setLayoutManager(new GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false));
        rvLessons.setAdapter(lessonsListAdapter);
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
        tvTitle.setVisibility(View.GONE);

        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = 0;
        cvSearch.setLayoutParams(params);
        ivSearchShow.setVisibility(View.GONE);
        etSearch.setVisibility(View.VISIBLE);
        etSearch.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        ivSearchHide.setVisibility(View.VISIBLE);
    }
    private void onSearchHideClick(){
        tvTitle.setVisibility(View.VISIBLE);

        if(ivSearchShow.getVisibility() == View.VISIBLE) return;
        etSearch.setText(null);
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
