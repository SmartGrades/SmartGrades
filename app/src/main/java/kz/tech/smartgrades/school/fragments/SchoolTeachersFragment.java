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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolTeachersListAdapterV2;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class SchoolTeachersFragment extends Fragment implements View.OnClickListener, SchoolTeachersListAdapterV2.OnItemClickListener{

    private SchoolActivity activity;
    private String SCHOOL_ID;
    private ImageView ivBack;
    private TextView tvTitleBar;
    private ImageView ivSearch;

    private ImageView ivCancel;
    private EditText etSearch;
    private CardView cvSearch;

    private RecyclerView rvTeachers;
    private SchoolTeachersListAdapterV2 teachersListAdapter;
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

        ivSearch = view.findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(this);
        ivCancel = view.findViewById(R.id.ivCancel);
        ivCancel.setOnClickListener(this);
        cvSearch = view.findViewById(R.id.cvSearch);
        cvSearch.setOnClickListener(this);

        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                teachersListAdapter.filter(editable.toString());
            }
        });

        tvTitleBar = view.findViewById(R.id.tvTitleBar);
        ivSearch = view.findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(this);

        rvTeachers = view.findViewById(R.id.rvTeachers);
        teachersListAdapter = new SchoolTeachersListAdapterV2(activity);
        teachersListAdapter.setOnItemClickListener(this);
        rvTeachers.setLayoutManager(new LinearLayoutManager(activity));
        rvTeachers.setAdapter(teachersListAdapter);
        if (mSchoolData != null && !listIsNullOrEmpty(mSchoolData.getTeachers())) {
            teachersListAdapter.updateData(mSchoolData.getTeachers());
        }
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
            case R.id.ivSearch:
                onSearch();
                break;
            case R.id.ivCancel:
                onCancel();
                break;
        }
    }
    private void onSearch() {
        tvTitleBar.setVisibility(View.GONE);

        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = 0;
        cvSearch.setLayoutParams(params);
        ivSearch.setVisibility(View.GONE);
        etSearch.setVisibility(View.VISIBLE);
        etSearch.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        ivCancel.setVisibility(View.VISIBLE);
    }

    private void onCancel() {
        tvTitleBar.setVisibility(View.VISIBLE);

        if(ivSearch.getVisibility() == View.VISIBLE) return;
        etSearch.setText(null);
        activity.alert.hideKeyboard(activity);
        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        cvSearch.setLayoutParams(params);
        ivSearch.setVisibility(View.VISIBLE);
        etSearch.setVisibility(View.GONE);
        ivCancel.setVisibility(View.GONE);
    }
    private void onBack(){
        activity.onBackPressed();
    }
    private void onAddTeacherClick(){
        activity.onAddTeachersClick();
    }
    @Override
    public void onItemClick(ModelSchoolTeacher m){
        activity.setTeacherData(m);
    }
    public void onUpdateData(ModelSchoolData mSchoolData){
        this.mSchoolData = mSchoolData;
        if(teachersListAdapter!=null) teachersListAdapter.updateData(mSchoolData.getTeachers());
    }
}
