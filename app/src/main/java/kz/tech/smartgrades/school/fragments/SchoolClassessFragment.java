package kz.tech.smartgrades.school.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolClassesColumn;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.SchoolClassMoveDragListener;
import kz.tech.smartgrades.root.dialogs.AlertDialogDefaultET;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolColumnsAdapter;
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
import static kz.tech.smartgrades._Web.func_SchoolAddColumnToClassess;
import static kz.tech.smartgrades._Web.func_SchoolEditColumn;

public class SchoolClassessFragment extends Fragment implements View.OnClickListener, SchoolColumnsAdapter.OnItemClickListener{

    private SchoolActivity activity;
    private ImageView ivBack;

    private ModelSchoolData mSchoolData;
    private String SCHOOL_ID;

    private RecyclerView rvColumns;
    private SchoolColumnsAdapter ColumnsAdapter;

    private TextView tvTitle;
    private ImageView ivSearch;
    private ImageView ivCancel;
    private EditText etSearch;
    private CardView cvSearch;

    private FrameLayout flDelete;
    private TextView tvDelete;
    private View vMoveListLeft, vMoveListRight;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (SchoolActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fgmt_school_classess, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view){
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivSearch = view.findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(this);
        ivCancel = view.findViewById(R.id.ivCancel);
        ivCancel.setOnClickListener(this);

        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                ColumnsAdapter.filter(editable.toString());
            }
        });

        flDelete = view.findViewById(R.id.flDelete);
        tvTitle = view.findViewById(R.id.tvTitle);
        cvSearch = view.findViewById(R.id.cvSearch);
        tvDelete = view.findViewById(R.id.tvDelete);
        tvDelete.setOnDragListener(new SchoolClassMoveDragListener(activity, flDelete));

        rvColumns = view.findViewById(R.id.rvColumns);
        ColumnsAdapter = new SchoolColumnsAdapter(activity, flDelete);
        ColumnsAdapter.setOnItemClickListener(this);
        rvColumns.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        rvColumns.setAdapter(ColumnsAdapter);
        rvColumns.setOnDragListener(new SchoolClassMoveDragListener());
        if (mSchoolData != null)
            ColumnsAdapter.updateData(mSchoolData);
        else ColumnsAdapter.updateData(null);

        vMoveListLeft = view.findViewById(R.id.vMoveListLeft);
        vMoveListLeft.setOnDragListener(new SchoolClassMoveDragListener(rvColumns));
        vMoveListRight = view.findViewById(R.id.vMoveListRight);
        vMoveListRight.setOnDragListener(new SchoolClassMoveDragListener(rvColumns));
    }

    public void updateData(ModelSchoolData mSchoolData){
        this.mSchoolData = mSchoolData;
        if(ColumnsAdapter == null) return;
        ColumnsAdapter.updateData(this.mSchoolData);
    }

    private void onBack(){
        activity.onBackPressed();
    }
    @Override
    public void onAddColumnClick(){
        AlertDialogDefaultET dialog = new AlertDialogDefaultET(activity, "Добавить столбец", "Название столбца");
        dialog.setOnItemClickListener(new AlertDialogDefaultET.OnItemClickListener(){
            @Override
            public void onOkClick(String etValue){
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty("SchoolId", SCHOOL_ID);
                jsonData.addProperty("Name", etValue);

                String SOAP = SoapRequest(func_SchoolAddColumnToClassess, jsonData.toString());
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
    public void onAddClassClick(ModelSchoolClassesColumn m){
        AlertDialogDefaultET dialog = new AlertDialogDefaultET(activity, "Добавить класс", "Название класса");
        dialog.setOnItemClickListener(etValue -> {
            if(!listIsNullOrEmpty(mSchoolData.getClassessColumns())){
                for (ModelSchoolClassesColumn _column : mSchoolData.getClassessColumns()) {
                    if (!listIsNullOrEmpty(_column.getClasses())){
                        for (ModelSchoolClass _class : _column.getClasses()) {
                            if (_class.getName().equals(etValue)) {
                                activity.alert.onToast(getString(R.string.class_alredy_exist));
                                return;
                            }
                        }
                    }
                }
            }

            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("SchoolId", SCHOOL_ID);
            jsonData.addProperty("ColumnId", m.getId());
            jsonData.addProperty("Name", etValue);

            String SOAP = SoapRequest(func_SchoolAddClass, jsonData.toString());
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
                                if(answer.isSuccess()) activity.presenter.onStartPresenter();
                            }
                        });
                    }
                }
            });
        });
    }
    @Override
    public void onEditColumnClick(ModelSchoolClassesColumn m){
        AlertDialogDefaultET dialog = new AlertDialogDefaultET(activity, "Изменить название столбца", "Название столбца", "Изменить");
        dialog.setOnItemClickListener(new AlertDialogDefaultET.OnItemClickListener(){
            @Override
            public void onOkClick(String etValue){
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty("SchoolId", SCHOOL_ID);
                jsonData.addProperty("Id", m.getId());
                jsonData.addProperty("Name", etValue);

                String SOAP = SoapRequest(func_SchoolEditColumn, jsonData.toString());
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
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
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

    public void setSchoolData(ModelSchoolData mSchoolData){
        this.mSchoolData = mSchoolData;
        if(ColumnsAdapter!=null) ColumnsAdapter.updateData(mSchoolData);
    }
}
