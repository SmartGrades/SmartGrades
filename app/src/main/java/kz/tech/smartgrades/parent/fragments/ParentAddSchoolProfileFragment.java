package kz.tech.smartgrades.parent.fragments;

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
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentSchoolClassListAdapter;
import kz.tech.smartgrades.parent.model.ModelParentData;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolData;
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
import static kz.tech.smartgrades._Web.func_GetSchoolClassList;
import static kz.tech.smartgrades._Web.func_InterFormParentToSchool;

public class ParentAddSchoolProfileFragment extends Fragment implements View.OnClickListener, ParentSchoolClassListAdapter.OnItemCLickListener{

    private ParentActivity activity;
    private String PARENT_ID;

    private ImageView ivNav;

    private ModelSchoolData mSchoolData;
    private ModelParentData mParentData;
    private ModelSchoolClass mSchoolClass;
    private int CHILD_SELECT_INDEX;

    private TextView tvName;
    private TextView tvContacts;
    private Button btnAddSchool;
    private CardView cvChooseClass;
    private EditText etSearch;
    private ImageView ivSearchArrow;
    private TextView tvSelectLesson;
    private RecyclerView rvLessonsList;
    private TextView tvContactsLabel;
    private ConstraintLayout clLabel;
    private ConstraintLayout clSearch;

    private ParentSchoolClassListAdapter parentSchoolClassListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_parent_add_school_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view){
        clLabel = view.findViewById(R.id.clLabel);
        clLabel.setOnClickListener(this);
        clSearch = view.findViewById(R.id.clSearch);
        clSearch.setOnClickListener(this);
        ivNav = view.findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        tvName = view.findViewById(R.id.tvName);
        tvContactsLabel = view.findViewById(R.id.tvContactsLabel);
        tvContacts = view.findViewById(R.id.tvContacts);
        btnAddSchool = view.findViewById(R.id.btnAddSchool);
        btnAddSchool.setOnClickListener(this);
        btnAddSchool.setClickable(false);
        cvChooseClass = view.findViewById(R.id.cvChooseClass);
        cvChooseClass.setOnClickListener(this);
        parentSchoolClassListAdapter = new ParentSchoolClassListAdapter();
        parentSchoolClassListAdapter.setOnItemCLickListener(this);
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
                parentSchoolClassListAdapter.filter(arg0.toString());
            }
        });
        ivSearchArrow = view.findViewById(R.id.ivSearchArrow);
        ivSearchArrow.setOnClickListener(this);
        tvSelectLesson = view.findViewById(R.id.tvSelectLesson);

        parentSchoolClassListAdapter = new ParentSchoolClassListAdapter();
        parentSchoolClassListAdapter.setOnItemCLickListener(this);
        rvLessonsList = view.findViewById(R.id.rvLessonsList);
        rvLessonsList.setLayoutManager(new LinearLayoutManager(activity));
        rvLessonsList.setAdapter(parentSchoolClassListAdapter);
    }

    private void setTargetInfo() {
        String schoolData = "";
        if (!stringIsNullOrEmpty(mSchoolData.getName())) schoolData += mSchoolData.getName() + "\n";
        if (!stringIsNullOrEmpty(mSchoolData.getAddress())) schoolData += mSchoolData.getAddress();
        tvName.setText(schoolData);

        String contacts = "";
        if (!stringIsNullOrEmpty(mSchoolData.getMail())) contacts += mSchoolData.getMail() + "\n";
        if (!stringIsNullOrEmpty(mSchoolData.getPhone())) contacts += mSchoolData.getPhone();
        tvContacts.setText(contacts);
        if (stringIsNullOrEmpty(contacts)) tvContactsLabel.setVisibility(View.INVISIBLE);
    }

    private void onShowLessonListClick() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.Id, mSchoolData.getId());
        String SOAP = SoapRequest(func_GetSchoolClassList, jsonData.toString());
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
                            if(!result.equals("null")){
                                ArrayList<ModelSchoolClass> classList = new Gson().fromJson(result, new TypeToken<ArrayList<ModelSchoolClass>>(){
                                }.getType());
                                parentSchoolClassListAdapter.updateData(classList);
                                clLabel.setVisibility(View.GONE);
                                clSearch.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }
        });
    }

    private void onHideLessonsListClick() {
        etSearch.setText("");
        parentSchoolClassListAdapter.ClearData();
        clLabel.setVisibility(View.VISIBLE);
        clSearch.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivNav:
                onBack();
                break;
            case R.id.btnAddSchool:
                onAdd();
                break;
            case R.id.clLabel:
                onShowLessonListClick();
                break;
            case R.id.ivSearchArrow:
                onHideLessonsListClick();
                break;
        }
    }

    private void onAdd() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.SourceId, PARENT_ID);
        jsonData.addProperty(F.TargetId, mSchoolData.getId());
        jsonData.addProperty(F.ChildId, mParentData.getFamilyGroup().getChildrens().get(CHILD_SELECT_INDEX).getId());
        jsonData.addProperty(F.ClassId, mSchoolClass.getId());
        String SOAP = SoapRequest(func_InterFormParentToSchool, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){ }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.alert.onToast(answer.getMessage());
                            if (answer.isSuccess()) {
                                onBack();
                                onBack();
                                activity.updatePresenter();
                            }
                        }
                    });
                }
            }
        });
    }

    private void onBack(){
        activity.onBackPressed();
    }

    public void setData(ModelParentData mParentData, int select_child_index){
        this.mParentData = mParentData;
        CHILD_SELECT_INDEX = select_child_index;
    }

    public void setData(ModelSchoolData m){
        this.mSchoolData = m;
        setTargetInfo();
    }

    @Override
    public void onItemClick(ModelSchoolClass m) {
        mSchoolClass = m;
        parentSchoolClassListAdapter.ClearData();
        tvSelectLesson.setText(m.getName());
        onHideLessonsListClick();
        btnAddSchool.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_blue_border));
        btnAddSchool.setTextColor(activity.getResources().getColor(R.color.blue_sea));
        btnAddSchool.setClickable(true);
    }
}