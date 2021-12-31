package kz.tech.smartgrades.school.fragments;

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

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.root.dialogs.DefaultDialog;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelLesson;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.ClassesTagAdapter;
import kz.tech.smartgrades.school.bottom_sheet_dialogs.BSDSelectClass;
import kz.tech.smartgrades.school.bottom_sheet_dialogs.BSDSchoolSelectLesson;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelTeacherProfile;
import kz.tech.smartgrades.school.models.ModelTeacherProfileClasses;
import kz.tech.smartgrades.school.models.ModelTeacherProfileLessons;
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
import static kz.tech.smartgrades._Web.func_SchoolAddClass;
import static kz.tech.smartgrades._Web.func_SchoolEditTeacher;

public class SchoolEditClassesFragment extends Fragment
        implements View.OnClickListener,
        ClassesTagAdapter.OnItemCLickListener,
        BSDSelectClass.OnItemClickListener,
        BSDSchoolSelectLesson.OnItemClickListener,
        DefaultDialog.OnClickListener{

    private SchoolActivity activity;
    private String SCHOOL_ID;
    private ModelTeacherProfile mTeacher;

    private ArrayList<ModelTeacherProfileClasses> ClassesList;

    private CardView cvBack;
    private CardView cvOk;
    private TextView tvName;
    private RecyclerView rvClasses;
    private ImageView ivAddClass;
    private CardView cvAddClass;

    private ClassesTagAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SchoolActivity) getActivity();
        this.SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_school_edit_classes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        cvBack = view.findViewById(R.id.cvBack);
        cvBack.setOnClickListener(this);
        cvOk = view.findViewById(R.id.cvOk);
        cvOk.setOnClickListener(this);
        tvName = view.findViewById(R.id.tvName);
        rvClasses = view.findViewById(R.id.rvClasses);
        ivAddClass = view.findViewById(R.id.ivAddClass);
        ivAddClass.setOnClickListener(this);
        cvAddClass = view.findViewById(R.id.cvAddClass);
        cvAddClass.setOnClickListener(this);
    }

    public void setClasses(ArrayList<ModelTeacherProfileClasses> classesList, ModelTeacherProfile mTeacher) {
        this.mTeacher = mTeacher;
        tvName.setText(mTeacher.getFirstName() + " " + mTeacher.getLastName());
        ClassesList = classesList;
        setAdapter(classesList);
    }

    private void setAdapter(ArrayList<ModelTeacherProfileClasses> classesList) {
        adapter = new ClassesTagAdapter(activity);
        adapter.setOnItemCLickListener(this);
        rvClasses.setAdapter(adapter);
        rvClasses.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        adapter.setEditable();
        adapter.UpdateData(classesList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cvBack:
                onBack();
                break;
            case R.id.cvOk:
                onOk();
                break;
            case R.id.ivAddClass:
            case R.id.cvAddClass:
                onAddClass();
                break;
        }
    }

    private void onAddClass() {
        BSDSelectClass dialog = new BSDSelectClass(activity, ClassesList);
        dialog.setOnItemClickListener(this);
        dialog.show();
    }

    private void onCreateNewClass(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = getLayoutInflater().inflate(R.layout.ad_school_add_teacher_add_class, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvAdd = view.findViewById(R.id.tvAdd);
        EditText etClassName = view.findViewById(R.id.etClassName);
        etClassName.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){ }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){ }
            @Override
            public void afterTextChanged(Editable editable){
                if(!editable.toString().isEmpty()){
                    tvAdd.setEnabled(true);
                    tvAdd.setTextColor(activity.getResources().getColor(R.color.blue_sea));
                }
                else{
                    tvAdd.setEnabled(false);
                    tvAdd.setTextColor(activity.getResources().getColor(R.color.gray_default));
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();
            }
        });
        tvAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                activity.alert.hideKeyboard(activity);
                alertDialog.dismiss();
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.SchoolId, SCHOOL_ID);
                jsonData.addProperty(F.Name, etClassName.getText().toString());

                String SOAP = SoapRequest(func_SchoolAddClass, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback(){
                    @Override
                    public void onFailure(final Call call, IOException e){
                        System.out.println(e.toString());
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
                                    if(answer.isSuccess()) {
                                        activity.presenter.onStartPresenter();
                                        ModelTeacherProfileClasses m = new ModelTeacherProfileClasses();
                                        m.setName(etClassName.getText().toString());
                                        ArrayList<ModelTeacherProfileClasses> list = adapter.getList();
                                        list.add(m);
                                        ClassesList = list;
                                        setAdapter(list);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void onOk() {
        DefaultDialog dialog = new DefaultDialog(activity);
        dialog.setCancel(activity.getResources().getColor(R.color.gray_average_dark), getString(R.string.cancel));
        dialog.setExit(activity.getResources().getColor(R.color.blue_sea), getString(R.string.ok));
        dialog.setBody(getString(R.string.Are_you_sure_you_want_to_change_the_data));
        dialog.setOnClickListener(this);
        dialog.show();
    }

    private void onBack() {
        activity.onBackPressed();
    }

    @Override
    public void onDeleteClassClick(ModelTeacherProfileClasses m) {
        ArrayList<ModelTeacherProfileClasses> list = adapter.getList();
        list.remove(m);
        ClassesList = list;
        setAdapter(list);
    }

    @Override
    public void onAddLessonClassClick(ModelTeacherProfileClasses m) {
        // выходит список уроков
        BSDSchoolSelectLesson dialog = new BSDSchoolSelectLesson(activity, m);
        dialog.setOnItemClickListener(this);
        dialog.show();
    }

    @Override
    public void onCreateClassClick() {
        onCreateNewClass();
    }

    @Override
    public void onAddClassClick(ModelSchoolClass m) {
        ModelTeacherProfileClasses model = new ModelTeacherProfileClasses();
        model.setName(m.getName());
        model.setId(m.getId());
        ArrayList<ModelTeacherProfileClasses> list = adapter.getList();
        list.add(model);
        ClassesList = list;
        setAdapter(list);
    }

    @Override
    public void onAddLessonClick(ModelLesson m, ModelTeacherProfileClasses modelClass) {
        ModelTeacherProfileClasses model = new ModelTeacherProfileClasses();
        model.setName(modelClass.getName());
        model.setId(modelClass.getId());
        ArrayList<ModelLesson> mLessons = new ArrayList<>();
        if (!listIsNullOrEmpty(modelClass.getLessons())) mLessons.addAll(modelClass.getLessons());
        mLessons.add(m);
        model.setLessons(mLessons);
        ArrayList<ModelTeacherProfileClasses> list = adapter.getList();
        list.set(list.indexOf(modelClass), model);
        ClassesList = list;
        setAdapter(list);
    }

    @Override
    public void onCancelDialog() {

    }

    @Override
    public void onExitDialog() {
        mTeacher.setClasses(adapter.getList());
        ArrayList<ModelLesson> lessonsList = new ArrayList<>();
        ArrayList<ModelTeacherProfileLessons> sortedLessonsList = new ArrayList<>();
        if (!listIsNullOrEmpty(adapter.getList())) {
            for (ModelTeacherProfileClasses _class : adapter.getList()) {
                lessonsList.addAll(_class.getLessons());
            }
        }

        if (!listIsNullOrEmpty(lessonsList)) {
            for (ModelLesson _lesson : lessonsList) {
                if (!sortedLessonsList.contains(_lesson)) {
                    ModelTeacherProfileLessons m = new ModelTeacherProfileLessons();
                    m.setLessonName(_lesson.getLessonName());
                    m.setLessonId(_lesson.getLessonId());
                    sortedLessonsList.add(m);
                }
            }
        }

        mTeacher.setLessons(sortedLessonsList);

        String SOAP = SoapRequest(func_SchoolEditTeacher, new Gson().toJson(mTeacher));
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
                            if (answer.isSuccess()) {
                                onBack();
                                activity.updatePresenter();
                                activity.loadTeacherModel();
                            }
                        }
                    });
                }
            }
        });
    }
}
