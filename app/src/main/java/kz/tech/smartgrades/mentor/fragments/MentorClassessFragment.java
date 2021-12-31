package kz.tech.smartgrades.mentor.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.MentorClassMoveDragListener;
import kz.tech.smartgrades.mentor.adapters.MentorColumnsAdapter;
import kz.tech.smartgrades.mentor.dialog.MentorADAddClass;
import kz.tech.smartgrades.mentor.models.ModelMentorClass;
import kz.tech.smartgrades.mentor.models.ModelMentorClassesColumn;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.root.dialogs.AlertDialogDefaultET;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolClassMoveDragListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_MentorAddClass;
import static kz.tech.smartgrades._Web.func_MentorAddColumnToClassess;
import static kz.tech.smartgrades._Web.func_MentorEditColumn;

public class MentorClassessFragment extends Fragment implements View.OnClickListener, MentorColumnsAdapter.OnItemClickListener{

    private MentorActivity activity;
    private ModelMentorData mMentorData;
    private String MENTOR_ID;

    private ImageView ivBack;

    private RecyclerView rvColumns;
    private MentorColumnsAdapter ColumnsAdapter;

    private FrameLayout flDelete;
    private TextView tvDelete;
    private View vMoveListLeft, vMoveListRight;

    private CardView cvSearch;
    private ImageView ivSearchShow, ivSearchHide;
    private EditText etSearch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (MentorActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fgmt_mentor_classess, container, false);
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

        flDelete = view.findViewById(R.id.flDelete);
        tvDelete = view.findViewById(R.id.tvDelete);
        tvDelete.setOnDragListener(new MentorClassMoveDragListener(activity, flDelete));

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
//                StudentsAdapter.onFilter(editable.toString());
//                TempText = editable.toString();
            }
        });

        rvColumns = view.findViewById(R.id.rvColumns);
        ColumnsAdapter = new MentorColumnsAdapter(activity, flDelete);
        ColumnsAdapter.setOnItemClickListener(this);
        rvColumns.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        rvColumns.setAdapter(ColumnsAdapter);
        rvColumns.setOnDragListener(new SchoolClassMoveDragListener());
        if (mMentorData != null)
            ColumnsAdapter.updateData(mMentorData);
        else ColumnsAdapter.updateData(null);

        vMoveListLeft = view.findViewById(R.id.vMoveListLeft);
        vMoveListLeft.setOnDragListener(new SchoolClassMoveDragListener(rvColumns));
        vMoveListRight = view.findViewById(R.id.vMoveListRight);
        vMoveListRight.setOnDragListener(new SchoolClassMoveDragListener(rvColumns));
    }

    public void updateData(ModelMentorData mMentorData){
        this.mMentorData = mMentorData;
        if(ColumnsAdapter == null) return;
        ColumnsAdapter.updateData(this.mMentorData);
    }

    @Override
    public void onAddClassClick(ModelMentorClassesColumn m){
        MentorADAddClass dialog = new MentorADAddClass(activity);
        dialog.setOnItemClickListener(new MentorADAddClass.OnItemClickListener(){
            @Override
            public void onOkClick(String Name){
                ModelMentorClass mClass = new ModelMentorClass();
                mClass.setMentorId(MENTOR_ID);
                mClass.setColumnId(m.getId());
                mClass.setName(Name);

                String SOAP = SoapRequest(func_MentorAddClass, new Gson().toJson(mClass));
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
    public void onEditColumnClick(ModelMentorClassesColumn m){
        AlertDialogDefaultET dialog = new AlertDialogDefaultET(activity, "Изменить название столбца", "Название столбца", "Изменить");
        dialog.setOnItemClickListener(new AlertDialogDefaultET.OnItemClickListener(){
            @Override
            public void onOkClick(String etValue){
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.MentorId, MENTOR_ID);
                jsonData.addProperty("Id", m.getId());
                jsonData.addProperty("Name", etValue);

                String SOAP = SoapRequest(func_MentorEditColumn, jsonData.toString());
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
            }
        });
    }
    @Override
    public void onAddColumnClick(){
        AlertDialogDefaultET dialog = new AlertDialogDefaultET(activity, "Добавить столбец", "Название столбца");
        dialog.setOnItemClickListener(new AlertDialogDefaultET.OnItemClickListener(){
            @Override
            public void onOkClick(String etValue){
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.MentorId, MENTOR_ID);
                jsonData.addProperty("Name", etValue);

                String SOAP = SoapRequest(func_MentorAddColumnToClassess, jsonData.toString());
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
            }
        });
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

        activity.alert.hideKeyboard(activity);
        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        cvSearch.setLayoutParams(params);
        ivSearchShow.setVisibility(View.VISIBLE);
        etSearch.setVisibility(View.GONE);
        etSearch.setText("");
        ivSearchHide.setVisibility(View.GONE);
    }

    public void setMentorData(ModelMentorData mMentorData){
        this.mMentorData = mMentorData;
        if(ColumnsAdapter!=null) ColumnsAdapter.updateData(mMentorData);
    }
}