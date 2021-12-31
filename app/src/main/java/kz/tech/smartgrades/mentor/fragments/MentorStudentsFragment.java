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

import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorSchoolsTabAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorStudentsListAdapterV2;
import kz.tech.smartgrades.mentor.adapters.MentorStudentsListAdapterV3;
import kz.tech.smartgrades.mentor.models.ModelMentorClass;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.mentor.models.ModelMentorStudentsList;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.SoapRequest;

public class MentorStudentsFragment extends Fragment implements View.OnClickListener, MentorStudentsListAdapterV2.OnItemClickListener, MentorSchoolsTabAdapter.OnItemClickListener{

    private MentorActivity activity;
    private String MENTOR_ID;
    private ModelMentorData mMentorData;
    private ModelMentorClass mClass;
    private ModelSchoolLesson mSelectLesson;

    private ImageView ivBack;
    private TextView tvTitle, tvLabel;

    private CardView cvSearch;
    private ImageView ivSearchShow, ivSearchHide;
    private EditText etSearch;
    private String TempText;

    private CircleImageView civAddStudent;

    private MentorSchoolsTabAdapter TabAdapter;
    private DiscreteScrollView dcvSchoolsList;
    private MentorStudentsListAdapterV3 StudentsAdapter;
    private RecyclerView rvStudentsList;


    public static MentorStudentsFragment newInstance(ModelMentorData mMentorData){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", mMentorData);
        MentorStudentsFragment fragment = new MentorStudentsFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fgmt_mentor_students, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view){
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        civAddStudent = view.findViewById(R.id.civAddStudent);
        civAddStudent.setOnClickListener(this);

        tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText("Ученики");
        tvLabel = view.findViewById(R.id.tvLabel);

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
                StudentsAdapter.onFilter(editable.toString());
                TempText = editable.toString();
            }
        });

        TabAdapter = new MentorSchoolsTabAdapter(activity);
        TabAdapter.setOnItemClickListener(this);
        dcvSchoolsList = view.findViewById(R.id.dcvSchoolsList);
        dcvSchoolsList.setAdapter(TabAdapter);
        dcvSchoolsList.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1f)
                .setMinScale(0.75f)
                .setPivotX(Pivot.X.CENTER)
                .setPivotY(Pivot.Y.CENTER)
                .build());
        dcvSchoolsList.addScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>(){
            @Override
            public void onScrollStart(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition){ }
            @Override
            public void onScrollEnd(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition){
                StudentsAdapter.updateData(mMentorData.getStudents().get(adapterPosition).getStudents());
                if(!stringIsNullOrEmpty(TempText)) StudentsAdapter.onFilter(TempText);
                if(!stringIsNullOrEmpty(mMentorData.getStudents().get(adapterPosition).getSchoolId()))
                    civAddStudent.setVisibility(View.GONE);
                else civAddStudent.setVisibility(View.VISIBLE);
            }
            @Override
            public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder, @Nullable RecyclerView.ViewHolder newCurrent){ }
        });

        if(!listIsNullOrEmpty(mMentorData.getStudents())) {
            tvLabel.setVisibility(View.GONE);
            TabAdapter.updateData(mMentorData.getStudents());
        }
        else tvLabel.setVisibility(View.VISIBLE);

        StudentsAdapter = new MentorStudentsListAdapterV3(activity);
        rvStudentsList = view.findViewById(R.id.rvStudentsList);
        rvStudentsList.setLayoutManager(new LinearLayoutManager(activity));
        rvStudentsList.setAdapter(StudentsAdapter);

        if(!listIsNullOrEmpty(mMentorData.getStudents()))
            StudentsAdapter.updateData(mMentorData.getStudents().get(0).getStudents());
    }

    public void onSetData(ModelMentorData mMentorData){
        this.mMentorData = mMentorData;

        if(!listIsNullOrEmpty(mMentorData.getStudents())) {
            if(tvLabel!=null) tvLabel.setVisibility(View.GONE);
            if(TabAdapter!=null) TabAdapter.updateData(mMentorData.getStudents());
        }
        else if(tvLabel!=null) tvLabel.setVisibility(View.VISIBLE);
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
            case R.id.civAddStudent:
                onAddStudent();
                break;
        }
    }
    private void onBack(){
        activity.onBackPressed();
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
    private void onAddStudent(){
        activity.onAddStudentClick();
    }

    @Override
    public void onItemClick(int p){
        dcvSchoolsList.setItemTransitionTimeMillis(100);
        dcvSchoolsList.smoothScrollToPosition(p);
    }
    @Override
    public void onItemLongClick(ModelMentorStudentsList m){
        if(mSelectLesson == null){
            activity.alert.onToast("Предмет не выбран. Чтобы начислить оценку, нужно выбрать предмет.");
            return;
        }
//        BSDMentorSetGrade bsdMentorSetGrade = new BSDMentorSetGrade(activity, m, mClass, m.getLessons());
//        bsdMentorSetGrade.setOnItemClickListener(new BSDMentorSetGrade.OnItemClickListener(){
//            @Override
//            public void onGradeClick(int type, ModelMentorStudentLessons selectLesson){
//                bsdMentorSetGrade.dismiss();
//                JsonObject jsonData = new JsonObject();
//                jsonData.addProperty(F.SourceId, MENTOR_ID);
//                jsonData.addProperty(F.StudentId, m.getId());
//                jsonData.addProperty(F.SchoolId, mClass.getSchoolId());
//                jsonData.addProperty(F.ClassId, mClass.getId());
//                jsonData.addProperty(F.LessonId, mSelectLesson.getLessonId());
//                //jsonData.addProperty(F.ChildLessonId, m.getChildLessonId());
//                jsonData.addProperty(F.Grade, type);
//
//                String SOAP = SoapRequest(func_MentorSetGrade, jsonData.toString());
//                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
//                Request request = new Request.Builder().url(URL).post(body).build();
//                HttpClient.newCall(request).enqueue(new Callback(){
//                    @Override
//                    public void onFailure(final Call call, IOException e){ }
//                    @Override
//                    public void onResponse(Call call, final Response response) throws IOException{
//                        if(response.isSuccessful()){
//                            String result = _Web.XMLReader(response.body().string());
//                            activity.runOnUiThread(new Runnable(){
//                                @Override
//                                public void run(){
//                                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
//                                    activity.alert.onToast(answer.getMessage());
//                                }
//                            });
//                        }
//                    }
//                });
//            }
//        });
    }
}