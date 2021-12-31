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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.root.dialogs.DefaultDialog;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.ClassesTagAdapterV2;
import kz.tech.smartgrades.school.bottom_sheet_dialogs.BSDSelectStudentClass;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;
import kz.tech.smartgrades.school.models.ModelStudentProfile;
import kz.tech.smartgrades.school.models.ModelStudentProfileClasses;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_SchoolAddClass;
import static kz.tech.smartgrades._Web.func_SchoolEditStudent;

public class SchoolEditStudentClassesFragment extends Fragment
        implements View.OnClickListener,
        DefaultDialog.OnClickListener,
        ClassesTagAdapterV2.OnItemCLickListener,
        BSDSelectStudentClass.OnItemClickListener{

    private SchoolActivity activity;
    private String SCHOOL_ID;
    private ModelStudentProfile mStudent;

    private CardView cvBack, cvOk;
    private ImageView ivAddClass, civAvatar;
    private TextView tvAddClass, tvName;
    private RecyclerView rvClasses;

    private ArrayList<ModelStudentProfileClasses> ClassesList;
    private ClassesTagAdapterV2 adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SchoolActivity) getActivity();
        this.SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_school_edit_student_classes, container, false);
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
        ivAddClass = view.findViewById(R.id.ivAddClass);
        ivAddClass.setOnClickListener(this);
        civAvatar = view.findViewById(R.id.civAvatar);
        tvAddClass = view.findViewById(R.id.tvAddClass);
        tvAddClass.setOnClickListener(this);
        tvName = view.findViewById(R.id.tvName);
        rvClasses = view.findViewById(R.id.rvClasses);
    }

    public void setClasses(ArrayList<ModelStudentProfileClasses> classesList, ModelStudentProfile mStudent) {
        this.mStudent = mStudent;
        tvName.setText(getString(R.string._class) + "\n" + mStudent.getFirstName() + " " + mStudent.getLastName());
        ClassesList = classesList;
        setAdapter(classesList);

        String avatar = mStudent.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) {
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);
        }
    }

    private void setAdapter(ArrayList<ModelStudentProfileClasses> classesList) {
        adapter = new ClassesTagAdapterV2(activity);
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
            case R.id.tvAddClass:
                onAddClass();
                break;
        }
    }

    private void onAddClass() {
        BSDSelectStudentClass dialog = new BSDSelectStudentClass(activity, ClassesList);
        dialog.setOnItemClickListener(this);
        dialog.show();
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
    public void onCancelDialog() {

    }

    @Override
    public void onExitDialog() {
        ModelSchoolStudent model = new ModelSchoolStudent();
        model.setId(mStudent.getId());
        model.setSchoolId(SCHOOL_ID);

        ArrayList<ModelSchoolClass> Classes = new ArrayList<>();
        for (ModelStudentProfileClasses _class : ClassesList) {
            ModelSchoolClass m = new ModelSchoolClass();
            m.setId(_class.getId());
            m.setName(_class.getName());
            Classes.add(m);
        }
        model.setClasses(Classes);

        String SOAP = SoapRequest(func_SchoolEditStudent, new Gson().toJson(model));
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){ }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            activity.alert.onToast(answer.getMessage());
                            if(answer.isSuccess()){
                                activity.updatePresenter();
                                activity.loadStudentModel();
                                onBack();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDeleteClassClick(ModelStudentProfileClasses m) {
        ArrayList<ModelStudentProfileClasses> list = adapter.getList();
        list.remove(m);
        ClassesList = list;
        setAdapter(list);
    }

    @Override
    public void onCreateClassClick() {
        onCreateNewClass();
    }

    @Override
    public void onAddClassClick(ModelSchoolClass m) {
        ModelStudentProfileClasses model = new ModelStudentProfileClasses();
        model.setName(m.getName());
        model.setId(m.getId());
        ArrayList<ModelStudentProfileClasses> list = adapter.getList();
        list.add(model);
        ClassesList = list;
        setAdapter(list);
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
                                        ModelStudentProfileClasses m = new ModelStudentProfileClasses();
                                        m.setName(etClassName.getText().toString());
                                        ArrayList<ModelStudentProfileClasses> list = adapter.getList();
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
}
