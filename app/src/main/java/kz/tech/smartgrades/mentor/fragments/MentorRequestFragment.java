package kz.tech.smartgrades.mentor.fragments;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorClassListAdapter;
import kz.tech.smartgrades.mentor.models.ModelMentorClass;
import kz.tech.smartgrades.mentor.models.ModelMentorClassesColumn;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.mentor.models.ModelMentorStudents;
import kz.tech.smartgrades.mentor.models.ModelMentorStudentsList;
import kz.tech.smartgrades.root.login.LoginKey;
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
import static kz.tech.smartgrades._Web.func_AcceptInterFormOfMentoring;

public class MentorRequestFragment extends Fragment implements View.OnClickListener{

    private MentorActivity activity;
    private String MENTOR_ID;
    private ModelInterForm mInterForm;
    private ModelMentorData mMentorData;

    private ImageView ivBack;
    private TextView tvParentName, tvParentMessageDate, tvMessage, tvChildName, tvLesson, tvMentorMessage;
    private CircleImageView civParentAvatar, civChildAvatar;
    private Button btnAdd;
    private FrameLayout flMentorMessage;


    public static MentorRequestFragment newInstance(ModelInterForm mInterForm, ModelMentorData mMentorData){
        Bundle bundle = new Bundle();
        bundle.putParcelable("mInterForm", mInterForm);
        bundle.putParcelable("mMentorData", mMentorData);
        MentorRequestFragment fragment = new MentorRequestFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (MentorActivity) getActivity();
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);
        if(getArguments() != null){
            mInterForm = getArguments().getParcelable("mInterForm");
            mMentorData = getArguments().getParcelable("mMentorData");
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_mentor_request, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        if(mInterForm != null) onLoadRequestMessages();
        onSetData();
        onLoadRequestMessages();
    }
    private void initUI(View view){
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvParentName = view.findViewById(R.id.tvParentName);
        tvParentName = view.findViewById(R.id.tvParentName);
        tvParentMessageDate = view.findViewById(R.id.tvParentMessageDate);
        tvMessage = view.findViewById(R.id.tvMessage);
        tvChildName = view.findViewById(R.id.tvChildName);
        tvLesson = view.findViewById(R.id.tvLesson);
        civParentAvatar = view.findViewById(R.id.civParentAvatar);
        civChildAvatar = view.findViewById(R.id.civChildAvatar);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        flMentorMessage = view.findViewById(R.id.flMentorMessage);
        tvMentorMessage = view.findViewById(R.id.tvMentorMessage);
    }
    private void onSetData(){
        if(!stringIsNullOrEmpty(mInterForm.getSourceFirstName()) || !stringIsNullOrEmpty(mInterForm.getSourceLastName()))
            tvParentName.setText(mInterForm.getSourceFirstName() + " " + mInterForm.getSourceLastName());
        else tvParentName.setText(mInterForm.getSourceLogin());

        if(!stringIsNullOrEmpty(mInterForm.getSourceAvatar()))
            Picasso.get().load(mInterForm.getSourceAvatar()).fit().centerCrop().into(civParentAvatar);
        if(!stringIsNullOrEmpty(mInterForm.getChildAvatar()))
            Picasso.get().load(mInterForm.getChildAvatar()).fit().centerCrop().into(civChildAvatar);

        if(!stringIsNullOrEmpty(mInterForm.getChildFirstName()) || !stringIsNullOrEmpty(mInterForm.getChildLastName()))
            tvChildName.setText(mInterForm.getChildFirstName() + " " + mInterForm.getChildLastName());
        else tvChildName.setText(mInterForm.getChildLogin());

        tvLesson.setText(mInterForm.getLessonName());
        tvParentMessageDate.setText(mInterForm.getTimeStamp());
    }
    private void onLoadRequestMessages(){
//        JsonObject jsonData = new JsonObject();
//        jsonData.addProperty("Id", mRequest.getId());
//        jsonData.addProperty(F.ChatId, mRequest.getChatId());
//
//        String SOAP = SoapRequest(func_GetRequestMessages, jsonData.toString());
//        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
//        Request request = new Request.Builder().url(URL).post(body).build();
//        HttpClient.newCall(request).enqueue(new Callback(){
//            @Override
//            public void onFailure(final Call call, IOException e){
//            }
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException{
//                if(response.isSuccessful()){
//                    String result = _Web.XMLReader(response.body().string());
//                    activity.runOnUiThread(new Runnable(){
//                        @Override
//                        public void run(){
//                            if(!result.equals("null")){
//                                ArrayList<ModelRequestMessages> mMessages = new Gson().fromJson
//                                (result, new TypeToken<ArrayList<ModelRequestMessages>>(){
//                                }.getType());
//                                messagesAdapter.updateData(mMessages);
//                            }
//                        }
//                    });
//                }
//            }
//        });
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBackClick();
                break;
            case R.id.btnAdd:
                onAddClick();
                break;
        }
    }
    private void onBackClick(){
        activity.onBackPressed();
    }
    private void onAddClick(){
        boolean isExist = false;
        if(!listIsNullOrEmpty(mMentorData.getStudents()))
            for(ModelMentorStudents _students : mMentorData.getStudents()){
                if(isExist) break;
                if(!listIsNullOrEmpty(_students.getStudents()))
                    for(ModelMentorStudentsList _student : _students.getStudents())
                        if(_student.getUserId().equals(mInterForm.getChildId())){
                            isExist = true;
                            break;
                        }
            }
        if(!isExist){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            View view = getLayoutInflater().inflate(R.layout.ad_mentor_select_class_for_inter_form, null);
            builder.setView(view);
            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alertDialog.show();

            MentorClassListAdapter adapter = new MentorClassListAdapter();
            RecyclerView rvClassList = view.findViewById(R.id.rvClassList);
            rvClassList.setLayoutManager(new LinearLayoutManager(activity));
            rvClassList.setAdapter(adapter);

            ArrayList<ModelMentorClass> Classes = new ArrayList<>();
            if(!listIsNullOrEmpty(mMentorData.getColumns()))
                for(ModelMentorClassesColumn _column : mMentorData.getColumns())
                    if(!listIsNullOrEmpty(_column.getClasses()))
                        for(ModelMentorClass _class : _column.getClasses())
                            if(stringIsNullOrEmpty(_class.getSchoolId()))
                                Classes.add(_class);
            if(!listIsNullOrEmpty(Classes)) adapter.updateData(Classes);

            adapter.setOnItemClickListener(new MentorClassListAdapter.OnItemClickListener(){
                @Override
                public void onSelectClass(ModelMentorClass m){
                    mInterForm.setClassId(m.getId());
                    String SOAP = SoapRequest(func_AcceptInterFormOfMentoring, new Gson().toJson(mInterForm));
                    RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                    Request request = new Request.Builder().url(URL).post(body).build();
                    HttpClient.newCall(request).enqueue(new Callback(){
                        @Override
                        public void onFailure(final Call call, IOException e){ }
                        @Override
                        public void onResponse(Call call, final Response response) throws IOException{
                            if(response.isSuccessful()){
                                String result = _Web.XMLToJson(response.body().string());
                                ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                activity.runOnUiThread(new Runnable(){
                                    @Override
                                    public void run(){
                                        if(answer.isSuccess()){
                                            alertDialog.dismiss();
                                            btnAdd.setVisibility(View.GONE);
                                            activity.presenter.onStartPresenter();
                                            flMentorMessage.setVisibility(View.VISIBLE);
                                            tvMentorMessage.setText("Ваш ребенок успешно добавлен по предмету");
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
        else {
            String SOAP = SoapRequest(func_AcceptInterFormOfMentoring, new Gson().toJson(mInterForm));
            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
            Request request = new Request.Builder().url(URL).post(body).build();
            HttpClient.newCall(request).enqueue(new Callback(){
                @Override
                public void onFailure(final Call call, IOException e){ }
                @Override
                public void onResponse(Call call, final Response response) throws IOException{
                    if(response.isSuccessful()){
                        String result = _Web.XMLToJson(response.body().string());
                        ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                        activity.runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                if(answer.isSuccess()){
                                    btnAdd.setVisibility(View.GONE);
                                    activity.presenter.onStartPresenter();
                                    flMentorMessage.setVisibility(View.VISIBLE);
                                    tvMentorMessage.setText("Ваш ребенок успешно добавлен по предмету");
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}
