package kz.tech.smartgrades.mentor.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelUserData;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorClassListAdapterV2;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolClassesColumn;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetMentorData;

public class MentorAddStudentProfileFragment extends Fragment implements View.OnClickListener, MentorClassListAdapterV2.OnItemCLickListener{

    private MentorActivity activity;
    private String MENTOR_ID;
    private ModelMentorData mMentorData;
    private ModelSchoolClass mSelectClass;
    private ModelUserData mSelectStudent;

    private ImageView ivBack;
    private CircleImageView civAvatar;
    private TextView tvTitleBar, tvFullName, tvLogin;
    private Button btnSend;

    private ConstraintLayout clLabel;
    private ConstraintLayout clSearch;
    private EditText etSearch;
    private ImageView ivSearchArrow;
    private TextView tvSelectClass;
    private MentorClassListAdapterV2 classListAdapter;
    private RecyclerView rvClassList;


    public static MentorAddStudentProfileFragment newInstance(ModelMentorData mMentorData){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", mMentorData);
        MentorAddStudentProfileFragment fragment = new MentorAddStudentProfileFragment();
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
        return inflater.inflate(R.layout.fgmt_mentor_add_student_profile, container, false);
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

        tvSelectClass = view.findViewById(R.id.tvSelectClass);
        civAvatar = view.findViewById(R.id.civAvatar);
        tvFullName = view.findViewById(R.id.tvFullName);
        tvLogin = view.findViewById(R.id.tvLogin);
        btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        tvTitleBar = view.findViewById(R.id.tvTitleBar);

        classListAdapter = new MentorClassListAdapterV2(activity);
        classListAdapter.setOnItemCLickListener(this);
        rvClassList = view.findViewById(R.id.rvClassList);
        rvClassList.setLayoutManager(new LinearLayoutManager(activity));
        rvClassList.setAdapter(classListAdapter);

        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3){
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
            }
            @Override
            public void afterTextChanged(Editable arg0){
                classListAdapter.filter(arg0.toString());
            }
        });
        clLabel = view.findViewById(R.id.clLabel);
        clLabel.setOnClickListener(this);
        clSearch = view.findViewById(R.id.clSearch);
        ivSearchArrow = view.findViewById(R.id.ivSearchArrow);
        ivSearchArrow.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
                break;
            case R.id.btnSend:
                onSend();
                break;
            case R.id.clLabel:
                onShowLessonListClick();
                break;
            case R.id.ivSearchArrow:
                onHideLessonsListClick();
                break;
        }
    }

    private void isOkEnable(){
        if(mSelectClass != null){
            btnSend.setEnabled(true);
            btnSend.setBackgroundResource(R.drawable.btn_background_purple);
        }
        else{
            btnSend.setEnabled(false);
            btnSend.setBackgroundResource(R.drawable.btn_background_purple_alpha);
        }
    }

    private void onShowLessonListClick(){
        if(!listIsNullOrEmpty(mMentorData.getClassessColumns())){
            ArrayList<ModelSchoolClass> classes = new ArrayList<>();
            for(ModelSchoolClassesColumn _column : mMentorData.getClassessColumns())
                classes.addAll(_column.getClasses());
            classListAdapter.updateData(classes);
            clLabel.setVisibility(View.GONE);
            clSearch.setVisibility(View.VISIBLE);
        }
    }
    private void onHideLessonsListClick(){
        etSearch.setText("");
        classListAdapter.ClearData();
        clLabel.setVisibility(View.VISIBLE);
        clSearch.setVisibility(View.GONE);
    }
    private void onBack(){
        activity.onBackPressed();
    }
    private void onSend(){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.SourceId, MENTOR_ID);
        jsonData.addProperty(F.MentorId, MENTOR_ID);
        jsonData.addProperty(F.ChildId, mSelectStudent.getUserId());
        jsonData.addProperty(F.ClassId, mSelectClass.getId());
        //jsonData.addProperty(F.LessonId, mSelectClass);

        String SOAP = SoapRequest(func_GetMentorData, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLReader(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("0")) { }
                            else {
                                /*modelMentorData = new Gson().fromJson(result, ModelMentorData.class);
                                callBack.onResultLoadMentorData(modelMentorData);*/
                            }
                        }
                    });

                }
            }
        });
    }

    @Override
    public void onItemClick(ModelSchoolClass m){
        mSelectClass = m;
        isOkEnable();
        tvSelectClass.setText(m.getName());
        onHideLessonsListClick();
    }

    public void onSetSelectStudentData(ModelUserData m){
        mSelectStudent = m;
        if(!stringIsNullOrEmpty(m.getAvatar())) Picasso.get().load(m.getAvatar()).fit().centerCrop().into(civAvatar);
        tvFullName.setText(m.getFirstName() + " " +m.getLastName());
        tvLogin.setText(m.getLogin());
    }
    public void onSetMentorData(ModelMentorData m){
        mMentorData = m;
    }
}
