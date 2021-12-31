package kz.tech.smartgrades.school.fragments;

import android.annotation.SuppressLint;
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolStudentsListAdapterV2;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_SchoolAddClassToStudent;

public class SchoolMoveStudentListFragment extends Fragment implements View.OnClickListener, SchoolStudentsListAdapterV2.OnItemClickListener{

    private SchoolActivity activity;
    private String SCHOOL_ID;
    private ImageView ivBack;
    private EditText etSearch;
    private TextView tvTitle;

    private ImageView ivSearch;
    private ImageView ivCancel;
    private CardView cvSearch;

    private RecyclerView rvStudentsList;
    private SchoolStudentsListAdapterV2 studentsListAdapter;

    private ModelSchoolData mSchoolData;
    private ModelSchoolClass mSchoolClass;


    public static SchoolMoveStudentListFragment newInstance(ModelMentorData mMentorData){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", mMentorData);
        SchoolMoveStudentListFragment fragment = new SchoolMoveStudentListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (SchoolActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_school_move_student_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initViews(View view){
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        tvTitle = view.findViewById(R.id.tvTitle);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivSearch = view.findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(this);
        ivCancel = view.findViewById(R.id.ivCancel);
        cvSearch = view.findViewById(R.id.cvSearch);
        ivCancel.setOnClickListener(this);
        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                studentsListAdapter.filter(editable.toString());
            }
        });

        rvStudentsList = view.findViewById(R.id.rvStudentsList);
        studentsListAdapter = new SchoolStudentsListAdapterV2(activity);
        studentsListAdapter.setOnItemClickListener(this);
        rvStudentsList.setLayoutManager(new LinearLayoutManager(activity));
        rvStudentsList.setAdapter(studentsListAdapter);
//        studentsListAdapter.updateData(mSchoolData.getStudents());
        tvTitle.setText(getString(R.string.Adding_a_student_to) + "\n" + mSchoolClass.getName());
        if(!listIsNullOrEmpty(mSchoolData.getStudents())){
//            ArrayList<ModelSchoolStudent> students = new ArrayList<>();
//            ArrayList<ModelSchoolClass> classes = new ArrayList<>();
//            for(ModelSchoolStudent _student : mSchoolData.getStudents()){
//                if(!listIsNullOrEmpty(_student.getClasses())){
//                    for(ModelSchoolClass _class : _student.getClasses()){
//                        if(!_class.getId().equals(mSchoolClass.getId())){
//                            classes.add(_class);
//                        }
//                    }
//                    if(!listIsNullOrEmpty(classes)){
//                        ModelSchoolStudent student = new ModelSchoolStudent();
//                        student.setId(_student.getId());
//                        student.setAvatar(_student.getAvatar());
//                        student.setFirstName(_student.getFirstName());
//                        student.setLastName(_student.getLastName());
//                        student.setClasses(classes);
//                        students.add(student);
//                    }
//                }
//            }
//            if(!listIsNullOrEmpty(students))
            studentsListAdapter.updateData(mSchoolData.getStudents());
            studentsListAdapter.setClassId(mSchoolClass.getId());
        }
    }


    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onCancelClick();
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
        tvTitle.setVisibility(View.GONE);

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
        tvTitle.setVisibility(View.VISIBLE);

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
    private void onCancelClick(){
        activity.onBackPressed();
    }

    @Override
    public void onItemClick(ModelSchoolStudent m){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(F.StudentId, m.getId());
        jsonObject.addProperty(F.SchoolId, SCHOOL_ID);
        jsonObject.addProperty(F.ClassId, mSchoolClass.getId());
        String SOAP = SoapRequest(func_SchoolAddClassToStudent, jsonObject.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.alert.onToast(answer.getMessage());
                            activity.updatePresenter();
                            onCancelClick();
                        }
                    });
                }
            }
        });
    }
    public void setClassData(ModelSchoolData mSchoolData, ModelSchoolClass mSchoolClass){
        this.mSchoolData = mSchoolData;
        this.mSchoolClass = mSchoolClass;
    }
}