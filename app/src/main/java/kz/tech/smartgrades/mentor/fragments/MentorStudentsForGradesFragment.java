package kz.tech.smartgrades.mentor.fragments;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorLessonsTabAdapter;
import kz.tech.smartgrades.mentor.adapters.MentorStudentsListAdapterV4;
import kz.tech.smartgrades.mentor.dialog.BSDMentorSetGrade;
import kz.tech.smartgrades.mentor.models.ModelMentorClass;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.mentor.models.ModelMentorStudentLessons;
import kz.tech.smartgrades.mentor.models.ModelMentorStudentsList;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelLesson;
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
import static kz.tech.smartgrades._Web.func_MentorSendMessage;
import static kz.tech.smartgrades._Web.func_MentorSetGrade;

public class MentorStudentsForGradesFragment extends Fragment implements View.OnClickListener,
        MentorStudentsListAdapterV4.OnItemClickListener, MentorLessonsTabAdapter.OnItemClickListener{

    private MentorActivity activity;
    private String MENTOR_ID;
    private ModelMentorClass mClass;

    private ImageView ivBack, ivGroupMessage;
    private TextView tvClassName, tvSchoolName, tvLabel;

    private CardView cvSearch;
    private ImageView ivSearchShow, ivSearchHide;
    private EditText etSearch;

    private MentorLessonsTabAdapter TabAdapter;
    private DiscreteScrollView dcvLessons;
    private ModelLesson SelectLesson;
    private int SelectLessonPos;

    private RecyclerView rvStudents;
    private MentorStudentsListAdapterV4 studentsListAdapter;


    public static MentorStudentsForGradesFragment newInstance(ModelMentorData mMentorData){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", mMentorData);
        MentorStudentsForGradesFragment fragment = new MentorStudentsForGradesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (MentorActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fgmt_mentor_students_grades, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view){
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);
        SelectLessonPos = 0;

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivGroupMessage = view.findViewById(R.id.ivGroupMessage);
        ivGroupMessage.setOnClickListener(this);

        tvClassName = view.findViewById(R.id.tvClassName);
        tvSchoolName = view.findViewById(R.id.tvSchoolName);

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
                studentsListAdapter.onFilter(editable.toString());
            }
        });

        tvLabel = view.findViewById(R.id.tvLabel);
        rvStudents = view.findViewById(R.id.rvStudents);
        studentsListAdapter = new MentorStudentsListAdapterV4(activity);
        studentsListAdapter.setOnItemClickListener(this);
        rvStudents.setLayoutManager(new LinearLayoutManager(activity));
        rvStudents.setAdapter(studentsListAdapter);

        TabAdapter = new MentorLessonsTabAdapter(activity, SelectLessonPos);
        TabAdapter.setOnItemClickListener(this);
        dcvLessons = view.findViewById(R.id.dcvLessons);
        dcvLessons.setAdapter(TabAdapter);
        dcvLessons.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1f)
                .setMinScale(0.75f)
                .setPivotX(Pivot.X.CENTER)
                .setPivotY(Pivot.Y.CENTER)
                .build());
        dcvLessons.addScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>(){
            @Override
            public void onScrollStart(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition){
                currentItemHolder.itemView.setBackgroundResource(R.drawable.background_mentor_set_grade_lesson_tag_not_active);
            }
            @Override
            public void onScrollEnd(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition){
                currentItemHolder.itemView.setBackgroundResource(R.drawable.background_mentor_set_grade_lesson_tag_active);
            }
            @Override
            public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder,
                                 @Nullable RecyclerView.ViewHolder newCurrent){ }
        });

        if(mClass != null){
            tvClassName.setText(mClass.getName());
            tvSchoolName.setText(mClass.getSchoolName());
            studentsListAdapter.updateData(mClass.getStudents());
            if(!listIsNullOrEmpty(mClass.getStudents())) tvLabel.setVisibility(View.GONE);
            else tvLabel.setVisibility(View.VISIBLE);
            if(!listIsNullOrEmpty(mClass.getLessons())){
                TabAdapter.updateData(mClass.getLessons());
                dcvLessons.scrollToPosition(SelectLessonPos);
                SelectLesson = mClass.getLessons().get(SelectLessonPos);
                dcvLessons.setVisibility(View.VISIBLE);
            }
            else dcvLessons.setVisibility(View.GONE);
        }
    }

    public void setData(ModelMentorData mMentorData){
//        this.mMentorData = mMentorData;
    }
    public void setSelectClass(ModelMentorClass m){
        if(m == null) return;
        mClass = m;
        if(tvClassName != null) tvClassName.setText(mClass.getName());
        if(tvSchoolName != null){
            if(!stringIsNullOrEmpty(mClass.getSchoolName())){
                tvSchoolName.setText(mClass.getSchoolName());
                tvSchoolName.setVisibility(View.VISIBLE);
            }
            else tvSchoolName.setVisibility(View.GONE);
        }
        if(tvLabel != null){
            if(!listIsNullOrEmpty(mClass.getStudents())) tvLabel.setVisibility(View.GONE);
            else tvLabel.setVisibility(View.VISIBLE);
        }
        if(studentsListAdapter != null) studentsListAdapter.updateData(m.getStudents());
        if(TabAdapter != null)
            if(!listIsNullOrEmpty(mClass.getLessons())){
                TabAdapter.updateData(mClass.getLessons());
                dcvLessons.scrollToPosition(SelectLessonPos);
                SelectLesson = mClass.getLessons().get(SelectLessonPos);
                dcvLessons.setVisibility(View.VISIBLE);
            }
            else dcvLessons.setVisibility(View.GONE);
    }

    public void onShowDialogSelectLesson(){
//        if(!listIsNullOrEmpty(mClass.getLessons())){
//            mSelectLesson = mClass.getLessons().get(0);
//            if(tvLesson!=null){
//                tvLesson.setText(mSelectLesson.getLessonName());
//            }
//            if(mClass.getLessons().size() > 1){
//                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//                View view = getLayoutInflater().inflate(R.layout.ad_mentor_select_lesson, null);
//                builder.setView(view);
//                AlertDialog alertDialog = builder.create();
//                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                alertDialog.show();
//
//                MentorSelectLessonListAdapter adapter = new MentorSelectLessonListAdapter(activity);
//                RecyclerView rvLessons = view.findViewById(R.id.rvLessons);
//                rvLessons.setAdapter(adapter);
//                rvLessons.setLayoutManager(new LinearLayoutManager(activity));
//                adapter.updateData(mClass.getLessons());
//                adapter.setOnItemClickListener(new MentorSelectLessonListAdapter.OnItemClickListener(){
//                    @Override
//                    public void onLessonClick(ModelLesson m){
//                        mSelectLesson = m;
//                        tvLesson.setText(mSelectLesson.getLessonName());
//                        alertDialog.dismiss();
//                    }
//                });
//            }
//        }
//        else tvLesson.setVisibility(View.GONE);
//        studentsListAdapter.updateData(mClass.getStudents());
    }

    @Override
    public void onItemLongClick(ModelMentorStudentsList mStudent){
        if(!stringIsNullOrEmpty(mClass.getSchoolId())){
            BSDMentorSetGrade bsdMentorSetGrade = new BSDMentorSetGrade(activity, mStudent, mClass, SelectLesson);
            bsdMentorSetGrade.setOnItemClickListener(new BSDMentorSetGrade.OnItemClickListener(){
                @Override
                public void onGradeClick(int type, ModelMentorStudentLessons selectLesson){
                    bsdMentorSetGrade.dismiss();
                    JsonObject jsonData = new JsonObject();
                    jsonData.addProperty(F.SourceId, MENTOR_ID);
                    jsonData.addProperty(F.StudentId, mStudent.getId());
                    jsonData.addProperty(F.SchoolId, mClass.getSchoolId());
                    jsonData.addProperty(F.ClassId, mClass.getId());
                    jsonData.addProperty(F.LessonId, SelectLesson.getLessonId());
                    jsonData.addProperty(F.Grade, type);

                    String SOAP = SoapRequest(func_MentorSetGrade, jsonData.toString());
                    RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                    Request request = new Request.Builder().url(URL).post(body).build();
                    HttpClient.newCall(request).enqueue(new Callback(){
                        @Override
                        public void onFailure(final Call call, IOException e){ }
                        @Override
                        public void onResponse(Call call, final Response response) throws IOException{
                            if(response.isSuccessful()){
                                String result = _Web.XMLToJson(response.body().string());
                                activity.runOnUiThread(new Runnable(){
                                    @Override
                                    public void run(){
                                        ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                        activity.alert.onToast(answer.getMessage());
                                    }
                                });
                            }
                        }
                    });
                }
                @Override
                public void onEnterMessageClick(ModelMentorStudentLessons selectLesson){
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    View view = getLayoutInflater().inflate(R.layout.ad_mentor_send_message_to_student, null);
                    builder.setView(view);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    alertDialog.show();

                    ImageView ivSend = view.findViewById(R.id.ivSend);
                    EditText etMessage = view.findViewById(R.id.etMessage);
                    ivSend.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            alertDialog.dismiss();

                            JsonObject jsonData = new JsonObject();
                            jsonData.addProperty(F.MentorId, MENTOR_ID);
                            jsonData.addProperty(F.Message, etMessage.getText().toString());
                            jsonData.addProperty(F.SchoolId, mClass.getSchoolId());
                            jsonData.addProperty(F.ClassId, mClass.getId());
                            jsonData.addProperty(F.LessonId, selectLesson.getLessonId());
                            jsonData.addProperty(F.StudentId, mStudent.getId());

                            String SOAP = SoapRequest(func_MentorSendMessage, jsonData.toString());
                            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                            Request request = new Request.Builder().url(URL).post(body).build();
                            HttpClient.newCall(request).enqueue(new Callback(){
                                @Override
                                public void onFailure(final Call call, IOException e){
                                }
                                @Override
                                public void onResponse(Call call, final Response response) throws IOException{
                                    if(response.isSuccessful()){
                                        String result = _Web.XMLToJson(response.body().string());
                                        activity.runOnUiThread(new Runnable(){
                                            @Override
                                            public void run(){
                                                ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                                activity.alert.onToast(answer.getMessage());
                                                if(answer.isSuccess()) activity.presenter.onStartPresenter();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                }
                @Override
                public void onCancelLastGradeClick(){

                }
            });
        }
        else{
            if(listIsNullOrEmpty(mStudent.getLessons())) return;
            BSDMentorSetGrade bsdMentorSetGrade = new BSDMentorSetGrade(activity, mStudent, mClass, null);
            bsdMentorSetGrade.setOnItemClickListener(new BSDMentorSetGrade.OnItemClickListener(){
                @Override
                public void onGradeClick(int type, ModelMentorStudentLessons selectLesson){
                    bsdMentorSetGrade.dismiss();
                    JsonObject jsonData = new JsonObject();
                    jsonData.addProperty(F.SourceId, MENTOR_ID);
                    jsonData.addProperty(F.StudentId, mStudent.getId());
                    jsonData.addProperty(F.ChildLessonId, selectLesson.getChildLessonId());
                    jsonData.addProperty(F.Grade, type);

                    String SOAP = SoapRequest(func_MentorSetGrade, jsonData.toString());
                    RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                    Request request = new Request.Builder().url(URL).post(body).build();
                    HttpClient.newCall(request).enqueue(new Callback(){
                        @Override
                        public void onFailure(final Call call, IOException e){ }
                        @Override
                        public void onResponse(Call call, final Response response) throws IOException{
                            if(response.isSuccessful()){
                                String result = _Web.XMLToJson(response.body().string());
                                activity.runOnUiThread(new Runnable(){
                                    @Override
                                    public void run(){
                                        ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                        activity.alert.onToast(answer.getMessage());
                                    }
                                });
                            }
                        }
                    });
                }
                @Override
                public void onEnterMessageClick(ModelMentorStudentLessons selectLesson){
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    View view = getLayoutInflater().inflate(R.layout.ad_mentor_send_message_to_student, null);
                    builder.setView(view);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    alertDialog.show();

                    ImageView ivSend = view.findViewById(R.id.ivSend);
                    EditText etMessage = view.findViewById(R.id.etMessage);
                    ivSend.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            alertDialog.dismiss();

                            JsonObject jsonData = new JsonObject();
                            jsonData.addProperty(F.MentorId, MENTOR_ID);
                            jsonData.addProperty(F.Message, etMessage.getText().toString());
                            jsonData.addProperty(F.StudentId, mStudent.getId());
                            jsonData.addProperty(F.ChildLessonId, selectLesson.getChildLessonId());

                            String SOAP = SoapRequest(func_MentorSendMessage, jsonData.toString());
                            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                            Request request = new Request.Builder().url(URL).post(body).build();
                            HttpClient.newCall(request).enqueue(new Callback(){
                                @Override
                                public void onFailure(final Call call, IOException e){
                                }
                                @Override
                                public void onResponse(Call call, final Response response) throws IOException{
                                    if(response.isSuccessful()){
                                        String result = _Web.XMLToJson(response.body().string());
                                        activity.runOnUiThread(new Runnable(){
                                            @Override
                                            public void run(){
                                                ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                                activity.alert.onToast(answer.getMessage());
                                                if(answer.isSuccess()) activity.presenter.onStartPresenter();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                }
                @Override
                public void onCancelLastGradeClick(){

                }
            });
        }
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
                break;
            case R.id.ivGroupMessage:
                onGroupMessageClick();
                break;
            case R.id.ivSearchShow:
                onSearchShowClick();
                break;
            case R.id.ivSearchHide:
                onSearchHideClick();
                break;
        }
    }
    private void onBack(){
        onSearchHideClick();
        activity.onBackPressed();
    }
    private void onGroupMessageClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = getLayoutInflater().inflate(R.layout.ad_mentor_send_message_to_group, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

        ImageView ivSend = view.findViewById(R.id.ivSend);
        EditText etMessage = view.findViewById(R.id.etMessage);
        ivSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();

                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.MentorId, MENTOR_ID);
                jsonData.addProperty(F.SchoolId, mClass.getSchoolId());
                jsonData.addProperty(F.ClassId, mClass.getId());
                jsonData.addProperty(F.LessonId, SelectLesson.getLessonId());
                jsonData.addProperty(F.Message, etMessage.getText().toString());

                String SOAP = SoapRequest(func_MentorSendMessage, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback(){
                    @Override
                    public void onFailure(final Call call, IOException e){
                    }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException{
                        if(response.isSuccessful()){
                            String result = _Web.XMLToJson(response.body().string());
                            activity.runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                    activity.alert.onToast(answer.getMessage());
                                    if(answer.isSuccess()) activity.presenter.onStartPresenter();
                                }
                            });
                        }
                    }
                });
            }
        });
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
        activity.alert.hideKeyboard(activity);
        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        cvSearch.setLayoutParams(params);
        ivSearchShow.setVisibility(View.VISIBLE);
        etSearch.setVisibility(View.GONE);
        ivSearchHide.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(int p){
        SelectLessonPos = p;
        SelectLesson = mClass.getLessons().get(SelectLessonPos);
        dcvLessons.setItemTransitionTimeMillis(100);
        dcvLessons.smoothScrollToPosition(SelectLessonPos);
    }
}