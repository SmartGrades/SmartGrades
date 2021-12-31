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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorAddStudentClassListAdapter;
import kz.tech.smartgrades.mentor.models.ModelMentorClass;
import kz.tech.smartgrades.mentor.models.ModelMentorClassesColumn;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelLesson;
import kz.tech.smartgrades.school.adaptes.SchoolAddContactLessonsGridAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolAddContactLessonsListAdapter;
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
import static kz.tech.smartgrades._Web.func_GetLessons;
import static kz.tech.smartgrades._Web.func_GetMentorData;
import static kz.tech.smartgrades._Web.func_SchoolAddClass;

public class MentorAddStudentProfileFragment extends Fragment implements View.OnClickListener, MentorAddStudentClassListAdapter.OnItemCLickListener, SchoolAddContactLessonsListAdapter.OnItemCLickListener, SchoolAddContactLessonsGridAdapter.OnItemCLickListener {

    private MentorActivity activity;
    private ModelMentorData mMentorData;
    private String MENTOR_ID;
    private ModelMentorClass mSelectClass;
    private ModelUser mSelectStudent;

    private ImageView ivBack;
    private CircleImageView civAvatar;

    //    private MentorClassListAdapterV2 classListAdapter;
//    private RecyclerView rvClassList;
    private EditText etLastName, etFirstName;

    private ConstraintLayout clClasses;
    private TextView tvClassLabel, tvAddNewClass;
    private ImageView ivAddClass;
    private RecyclerView rvClassList;
    private MentorAddStudentClassListAdapter ClassListAdapter;
    private ArrayList<ModelMentorClass> Classes;

    private ConstraintLayout clLessons;
    private TextView tvLessonLabel;
    private ImageView ivAddLesson;
    private RecyclerView rvLessonList, rvSelectLessonsList;
    private ArrayList<ModelLesson> Lessons, LessonsSelected;
    private SchoolAddContactLessonsListAdapter LessonsAdapter;
    private SchoolAddContactLessonsGridAdapter LessonsSelectedAdapter;

    private ConstraintLayout clSearch;
    private EditText etSearch;

    private boolean[] IsSuccess = new boolean[2];
    private TextView tvAdd;


    public static MentorAddStudentProfileFragment newInstance(ModelMentorData mMentorData) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", mMentorData);
        MentorAddStudentProfileFragment fragment = new MentorAddStudentProfileFragment();
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
        return inflater.inflate(R.layout.fragment_mentor_add_student, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        Lessons = new ArrayList<>();
        LessonsSelected = new ArrayList<>();
        Classes = new ArrayList<>();
    }
    private void initViews(View view) {
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvAdd = view.findViewById(R.id.tvAdd);
        tvAdd.setOnClickListener(this);

        civAvatar = view.findViewById(R.id.civAvatar);
        etLastName = view.findViewById(R.id.etLastName);
        etFirstName = view.findViewById(R.id.etFirstName);

        clClasses = view.findViewById(R.id.clClasses);
        clClasses.setOnClickListener(this);
        tvClassLabel = view.findViewById(R.id.tvClassLabel);
        tvAddNewClass = view.findViewById(R.id.tvAddNewClass);
        tvAddNewClass.setOnClickListener(this);
        ivAddClass = view.findViewById(R.id.ivAddClass);

        ClassListAdapter = new MentorAddStudentClassListAdapter();
        rvClassList = view.findViewById(R.id.rvClassList);
        rvClassList.setAdapter(ClassListAdapter);
        rvClassList.setLayoutManager(new LinearLayoutManager(activity));
        ClassListAdapter.setOnItemCLickListener(this);

        clLessons = view.findViewById(R.id.clLessons);
        clLessons.setOnClickListener(this);
        tvLessonLabel = view.findViewById(R.id.tvLessonLabel);
        ivAddLesson = view.findViewById(R.id.ivAddLesson);

        clSearch = view.findViewById(R.id.clSearch);
        clSearch.setOnClickListener(this);
        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                LessonsAdapter.filter(arg0.toString());
            }
        });

        LessonsAdapter = new SchoolAddContactLessonsListAdapter();
        rvLessonList = view.findViewById(R.id.rvLessonList);
        rvLessonList.setAdapter(LessonsAdapter);
        rvLessonList.setLayoutManager(new LinearLayoutManager(activity));
        LessonsAdapter.setOnItemCLickListener(this);

        LessonsSelectedAdapter = new SchoolAddContactLessonsGridAdapter();
        rvSelectLessonsList = view.findViewById(R.id.rvSelectLessonsList);
        rvSelectLessonsList.setAdapter(LessonsSelectedAdapter);
        rvSelectLessonsList.setLayoutManager(new LinearLayoutManager(activity));
        LessonsSelectedAdapter.setOnItemCLickListener(this);
    }

    public void onSetMentorData(ModelMentorData m) {
        mMentorData = m;
    }
    public void onSetSelectStudentData(ModelUser m) {
        mSelectStudent = m;
        if (mSelectStudent != null) {
            if (!stringIsNullOrEmpty(mSelectStudent.getAvatar()))
                Picasso.get().load(mSelectStudent.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerCrop().into(civAvatar);
            else civAvatar.setImageResource(R.drawable.img_default_avatar);
            etLastName.setText(mSelectStudent.getLastName());
            etFirstName.setText(mSelectStudent.getFirstName());
        }
//        if(!stringIsNullOrEmpty(m.getAvatar())) Picasso.get().load(m.getAvatar()).fit().centerCrop().into(civAvatar);
//        tvFullName.setText(m.getFirstName() + " " +m.getLastName());
//        tvLogin.setText(m.getLogin());
    }

    private void isNextEnable() {
        if (IsSuccess[0] && IsSuccess[1]) {
            tvAdd.setEnabled(true);
            tvAdd.setTextColor(activity.getResources().getColor(R.color.white));
            tvAdd.setBackgroundResource(R.drawable.background_square_blue_sea);
            tvAdd.setPadding(10, 10, 10, 10);
        }
        else {
            tvAdd.setEnabled(false);
            tvAdd.setTextColor(activity.getResources().getColor(R.color.gray_default));
            tvAdd.setBackground(null);
            tvAdd.setPadding(10, 10, 10, 10);
        }
    }

    @Override
    public void onClassClick(ModelMentorClass m) {
        tvAddNewClass.setVisibility(View.GONE);
        rvClassList.setVisibility(View.GONE);
        if (mSelectClass == null) ivAddClass.setImageResource(R.drawable.img_edit_gray);
        mSelectClass = m;
        tvClassLabel.setText(m.getName());
        IsSuccess[0] = true;
        isNextEnable();
    }
    @Override
    public void onLessonClick(ModelLesson m) {
        if (!etSearch.getText().toString().isEmpty()) etSearch.setText("");
        ivAddLesson.setImageResource(R.drawable.img_okay_blue);
        Lessons.remove(m);
        LessonsAdapter.UpdateData(Lessons);
        LessonsSelected.add(m);
        LessonsSelectedAdapter.UpdateData(LessonsSelected);
        rvSelectLessonsList.setVisibility(View.VISIBLE);
        tvLessonLabel.setVisibility(View.GONE);
        IsSuccess[1] = !listIsNullOrEmpty(LessonsSelected);
        isNextEnable();
    }
    @Override
    public void onLessonDeleteClick(ModelLesson m) {
        Lessons.add(m);
        LessonsAdapter.UpdateData(Lessons);
        LessonsSelected.remove(m);
        LessonsSelectedAdapter.UpdateData(LessonsSelected);
        if (listIsNullOrEmpty(LessonsSelected)) {
            tvLessonLabel.setText("Предмет*");
            tvLessonLabel.setVisibility(View.VISIBLE);
        }
        IsSuccess[1] = !listIsNullOrEmpty(LessonsSelected);
        isNextEnable();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.clClasses:
                onAddClassClick();
                break;
            case R.id.tvAddNewClass:
                onAddNewClass();
                break;
            case R.id.clLessons:
                onShowLessonListClick();
                break;
            case R.id.tvAdd:
                onSend();
                break;
        }
    }
    private void onBack() {
        activity.onBackPressed();
    }
    private void onAddClassClick() {
        activity.alert.hideKeyboard(activity);

        if (rvClassList.getVisibility() == View.GONE) {
            if (listIsNullOrEmpty(Classes)) {
                if (!listIsNullOrEmpty(mMentorData.getColumns()))
                    for (ModelMentorClassesColumn _column : mMentorData.getColumns())
                        if (!listIsNullOrEmpty(_column.getClasses()))
                            Classes.addAll(_column.getClasses());
            }
            ClassListAdapter.UpdateData(Classes);
            rvClassList.setVisibility(View.VISIBLE);
            tvAddNewClass.setVisibility(View.VISIBLE);
        }
        else {
            rvClassList.setVisibility(View.GONE);
            tvAddNewClass.setVisibility(View.GONE);
        }
    }
    private void onAddNewClass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = getLayoutInflater().inflate(R.layout.ad_school_add_teacher_add_class, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvAdd = view.findViewById(R.id.tvAdd);
        EditText etClassName = view.findViewById(R.id.etClassName);
        etClassName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    tvAdd.setEnabled(true);
                    tvAdd.setTextColor(activity.getResources().getColor(R.color.blue_sea));
                }
                else {
                    tvAdd.setEnabled(false);
                    tvAdd.setTextColor(activity.getResources().getColor(R.color.gray_default));
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.alert.hideKeyboard(activity);
                alertDialog.dismiss();
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.MentorId, MENTOR_ID);
                jsonData.addProperty(F.Name, etClassName.getText().toString());

                String SOAP = SoapRequest(func_SchoolAddClass, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        System.out.println(e.toString());
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
                                    if (answer.isSuccess()) {
                                        activity.presenter.onStartPresenter();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
    private void onShowLessonListClick() {
        activity.alert.hideKeyboard(activity);

        if (clSearch.getVisibility() == View.GONE) {
            if (listIsNullOrEmpty(Lessons)) {
                String SOAP = SoapRequest(func_GetLessons, null);
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
                                    Lessons = new Gson().fromJson(result,
                                            new TypeToken<ArrayList<ModelLesson>>() {
                                            }.getType());
                                    LessonsAdapter.UpdateData(Lessons);
                                    clSearch.setVisibility(View.VISIBLE);
                                    ivAddLesson.setImageResource(R.drawable.img_okay_blue);
                                }
                            });
                        }
                    }
                });
            }
            else {
                LessonsAdapter.UpdateData(Lessons);
                clSearch.setVisibility(View.VISIBLE);
                ivAddLesson.setImageResource(R.drawable.img_okay_blue);
            }
        }
        else {
            clSearch.setVisibility(View.GONE);
            ivAddLesson.setImageResource(R.drawable.img_edit_gray);
        }

    }
    private void onSend() {
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
                    String result = _Web.XMLToJson(response.body().string());
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
}
