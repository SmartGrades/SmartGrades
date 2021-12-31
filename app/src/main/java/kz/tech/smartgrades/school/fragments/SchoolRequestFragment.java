package kz.tech.smartgrades.school.fragments;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolClassListAdapterV2;
import kz.tech.smartgrades.school.adaptes.SchoolFormalStudentsListAdapter;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolClassesColumn;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;
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
import static kz.tech.smartgrades._Web.func_AcceptInterFormParentToSchool;
import static kz.tech.smartgrades._Web.func_UniteInterFormParentToSchool;

public class SchoolRequestFragment extends Fragment implements View.OnClickListener, SchoolClassListAdapterV2.OnItemClickListener, SchoolFormalStudentsListAdapter.OnItemClickListener{

    private SchoolActivity activity;
    private String SCHOOL_ID;
    private ModelInterForm mInterForm;
    private boolean isChange = false;
    private boolean isListOpen = false;
    private boolean isUnit = false;

    private ImageView ivBack, ivArrow;
    private CircleImageView civAvatar, civChildAvatar;
    private TextView tvFullName, tvClass, tvClass2, tvTitle, tvTransactionDate, tvChange, tvReject;
    private Button btnAdd;
    private CardView cvFullName, cvClass;
    private EditText etSearch;
    private RecyclerView rvStudentList;
    private ArrayList<ModelSchoolStudent> Students;
    private SchoolFormalStudentsListAdapter studentslistAdapter;

    private PopupWindow popupWindow;


    public static SchoolRequestFragment newInstance(ModelInterForm model) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", model);
        SchoolRequestFragment fragment = new SchoolRequestFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SchoolActivity) getActivity();
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);
        if (getArguments() != null) mInterForm = getArguments().getParcelable("model");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_school_request, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        onSetData();
    }
    private void initUI(View view) {
        tvTransactionDate = view.findViewById(R.id.tvTransactionDate);
        tvChange = view.findViewById(R.id.tvChange);
        tvChange.setOnClickListener(this);
        tvReject = view.findViewById(R.id.tvReject);
        tvReject.setOnClickListener(this);
        civAvatar = view.findViewById(R.id.civAvatar);
        civChildAvatar = view.findViewById(R.id.civChildAvatar);
        tvFullName = view.findViewById(R.id.tvFullName);
        cvFullName = view.findViewById(R.id.cvFullName);
        cvFullName.setOnClickListener(this);
        tvClass = view.findViewById(R.id.tvClass);
        rvStudentList = view.findViewById(R.id.rvStudentList);
        tvClass2 = view.findViewById(R.id.tvClass2);
        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0)
                    if (studentslistAdapter != null)
                        studentslistAdapter.filter(editable.toString());
            }
        });
        cvClass = view.findViewById(R.id.cvClass);
        cvClass.setOnClickListener(this);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivArrow = view.findViewById(R.id.ivArrow);
        tvTitle = view.findViewById(R.id.tvTitle);
    }
    private void onSetData() {
        tvTitle.setText(mInterForm.getSourceFirstName() + " " + mInterForm.getSourceLastName());

        if (!stringIsNullOrEmpty(mInterForm.getChildAvatar()))
            Picasso.get().load(mInterForm.getChildAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerCrop().into(civChildAvatar);
        if (!stringIsNullOrEmpty(mInterForm.getSourceAvatar()))
            Picasso.get().load(mInterForm.getSourceAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerCrop().into(civAvatar);

        String childName = mInterForm.getChildFirstName() + " " + mInterForm.getChildLastName();
        if (!stringIsNullOrEmpty(childName))
            tvFullName.setText(childName);
        else tvFullName.setText(mInterForm.getChildLogin());

        tvClass.setText(mInterForm.getClassName());
        tvTransactionDate.setText(mInterForm.getTimeStamp());

        ModelSchoolData modelSchoolData = activity.getSchoolData();
        Students = new ArrayList<>();
        if(!listIsNullOrEmpty(modelSchoolData.getStudents())){
            for (ModelSchoolStudent _student : modelSchoolData.getStudents()){
                if(stringIsNullOrEmpty(_student.getUserId()))
                    Students.add(_student);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackClick();
                break;
            case R.id.btnAdd:
                if (isUnit) onUniteClick();
                else onAddClick();
                break;
            case R.id.cvClass:
                onClassClick();
                break;
            case R.id.tvChange:
                onChangeClick();
                break;
            case R.id.tvReject:
                onReject();
                break;
        }
    }

    private void onChangeClick() {
        if (!isChange) {
            cvFullName.setVisibility(View.VISIBLE);
            cvClass.setVisibility(View.VISIBLE);
            tvChange.setText(R.string.done);
            etSearch.setText(tvFullName.getText());
            tvClass2.setText(tvClass.getText());
            studentslistAdapter = new SchoolFormalStudentsListAdapter(activity);
            rvStudentList.setVisibility(View.VISIBLE);
            rvStudentList.setAdapter(studentslistAdapter);
            rvStudentList.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
            studentslistAdapter.updateData(Students);
            studentslistAdapter.setOnItemClickListener(this);
            if (!stringIsNullOrEmpty(etSearch.getText().toString())) studentslistAdapter.filter(etSearch.getText().toString());
        } else {
            rvStudentList.setVisibility(View.GONE);
            cvFullName.setVisibility(View.INVISIBLE);
            tvChange.setText(getString(R.string.change));
            cvClass.setVisibility(View.GONE);
        }
        isChange = !isChange;
    }
    private void onClassClick() {
        ivArrow.setRotation(180);
        int width = cvClass.getWidth();
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        View popUpView = getLayoutInflater().inflate(R.layout.pw_list, null);
        popupWindow = new PopupWindow(popUpView, width, height, true);
        RecyclerView rvList = popUpView.findViewById(R.id.rvList);
        ArrayList<ModelSchoolClass> Classes = new ArrayList<>();
        if(!listIsNullOrEmpty(activity.getSchoolData().getClassessColumns())){
            Classes.clear();
            for(ModelSchoolClassesColumn _column : activity.getSchoolData().getClassessColumns()) {
                if(!listIsNullOrEmpty(_column.getClasses()))
                    Classes.addAll(_column.getClasses());
            }
        }
        SchoolClassListAdapterV2 listAdapter = new SchoolClassListAdapterV2(activity);
        rvList.setAdapter(listAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        listAdapter.updateData(Classes);
        listAdapter.setOnItemClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) popupWindow.setElevation(20);
        popupWindow.showAsDropDown(cvClass, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivArrow.setRotation(0);
            }
        });
    }

    private void onReject() {

    }
    private void onAddClick() {
        String SOAP = SoapRequest(func_AcceptInterFormParentToSchool, new Gson().toJson(mInterForm));
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
                            if(answer.isSuccess()){
                                activity.onBackPressed();
                                activity.presenter.onStartPresenter();
                            }
                        }
                    });
                }
            }
        });
    }
    private void onUniteClick() {
        String SOAP = SoapRequest(func_UniteInterFormParentToSchool, new Gson().toJson(mInterForm));
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
                            if(answer.isSuccess()){
                                activity.onBackPressed();
                                activity.presenter.onStartPresenter();
                            }
                        }
                    });
                }
            }
        });
    }

    private void onBackClick() {
        activity.onBackPressed();
    }

    @Override
    public void onItemClick(ModelSchoolClass m) {
        popupWindow.dismiss();
        tvClass.setText(m.getName());
        tvClass2.setText(m.getName());
        mInterForm.setClassId(m.getId());
        mInterForm.setClassName(m.getName());
    }

    @Override
    public void onItemClick(ModelSchoolStudent m) {
        isUnit = true;
        tvFullName.setText(m.getFirstName() + " " + m.getLastName());
        etSearch.setText(m.getFirstName() + " " + m.getLastName());
        etSearch.setSelection(etSearch.getText().length());
        mInterForm.setStudentId(m.getId());
        mInterForm.setChildFirstName(m.getFirstName());
        mInterForm.setChildLastName(m.getLastName());
    }
}
