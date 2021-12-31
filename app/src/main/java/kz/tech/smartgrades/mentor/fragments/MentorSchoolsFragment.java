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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorSchoolsAdapter;
import kz.tech.smartgrades.mentor.models.ModelMentorData;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class MentorSchoolsFragment extends Fragment implements View.OnClickListener {

    private MentorActivity activity;
    private ModelMentorData mMentorData;

    private ImageView ivBack;
    private TextView tvLabel;

    private CardView cvSearch;
    private ImageView ivSearchShow, ivSearchHide;
    private EditText etSearch;

    private RecyclerView rvSchools;
    private MentorSchoolsAdapter schoolsAdapter;

    private CircleImageView civAddSchool;

    public static MentorSchoolsFragment newInstance(ModelMentorData modelMentorData) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", modelMentorData);
        MentorSchoolsFragment fragment = new MentorSchoolsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MentorActivity) getActivity();
        if (getArguments() != null) mMentorData = getArguments().getParcelable("model");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fgmt_mentor_schools, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }
    private void initUI(View view) {
        tvLabel = view.findViewById(R.id.tvLabel);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        civAddSchool = view.findViewById(R.id.civAddSchool);
        civAddSchool.setOnClickListener(this);

        cvSearch = view.findViewById(R.id.cvSearch);
        ivSearchShow = view.findViewById(R.id.ivSearchShow);
        ivSearchShow.setOnClickListener(this);
        ivSearchHide = view.findViewById(R.id.ivSearchHide);
        ivSearchHide.setOnClickListener(this);
        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (schoolsAdapter != null)
                    schoolsAdapter.onFilter(editable.toString());
            }
        });

        schoolsAdapter = new MentorSchoolsAdapter(activity);
        rvSchools = view.findViewById(R.id.rvSchools);
        rvSchools.setLayoutManager(new LinearLayoutManager(activity));
        rvSchools.setAdapter(schoolsAdapter);
        if (mMentorData != null) {
            if (!listIsNullOrEmpty(mMentorData.getSchools())) {
                tvLabel.setVisibility(View.GONE);
                schoolsAdapter.updateData(mMentorData.getSchools());
            }
            else tvLabel.setVisibility(View.VISIBLE);
        }
    }

    public void setData(ModelMentorData mMentorData) {
        this.mMentorData = mMentorData;
        if (!listIsNullOrEmpty(mMentorData.getSchools())) {
            if (tvLabel != null) tvLabel.setVisibility(View.GONE);
            if (schoolsAdapter != null) schoolsAdapter.updateData(mMentorData.getSchools());
        }
        else if (tvLabel != null) tvLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackClick();
                break;
            case R.id.civAddSchool:
                activity.onAddSchoolClick();
                break;
            case R.id.ivSearchShow:
                onSearchShowClick();
                break;
            case R.id.ivSearchHide:
                onSearchHideClick();
                break;
        }
    }
    private void onBackClick() {
        activity.onBackPressed();
    }
    private void onSearchShowClick() {
        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = 0;
        cvSearch.setLayoutParams(params);
        ivSearchShow.setVisibility(View.GONE);
        etSearch.setVisibility(View.VISIBLE);
        ivSearchHide.setVisibility(View.VISIBLE);
    }
    private void onSearchHideClick() {
        if (ivSearchShow.getVisibility() == View.VISIBLE) return;
        activity.alert.hideKeyboard(activity);
        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        cvSearch.setLayoutParams(params);
        ivSearchShow.setVisibility(View.VISIBLE);
        etSearch.setVisibility(View.GONE);
        ivSearchHide.setVisibility(View.GONE);
    }


}
