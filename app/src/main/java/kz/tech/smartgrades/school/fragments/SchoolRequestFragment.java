package kz.tech.smartgrades.school.fragments;

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
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolAddStudentsAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolRequestMessagesAdapter;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolRequest;
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
import static kz.tech.smartgrades._Web.func_SchoolAddStudentV2;
import static kz.tech.smartgrades._Web.func_SchoolAddStudentV3;

public class SchoolRequestFragment extends Fragment implements View.OnClickListener, SchoolAddStudentsAdapter.OnItemCLickListener {

    private SchoolActivity activity;
    private ImageView ivBack;
    private TextView tvTitle;
    private RecyclerView rvMessages;
    private SchoolRequestMessagesAdapter messagesAdapter;
    private ModelSchoolRequest mRequest;

    private CircleImageView civAvatar, civChildAvatar;
    private TextView tvMentorFullName, tvClass;
    private Button btnAdd;

    private RecyclerView rvSimiliar;

    private FrameLayout flDataBot;
    private CircleImageView civAvatarBot;
    private Button btnUnite;
    private TextView textView8;

    private ModelSchoolStudent SelectStudent;

    private TextView tvTransactionDate;

    public static SchoolRequestFragment newInstance(ModelSchoolRequest model) {
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
        if (getArguments() != null) mRequest = getArguments().getParcelable("model");
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
        if (mRequest != null) onLoadRequestMessages();
        onSetData();
        onLoadRequestMessages();
    }
    private void initUI(View view) {
        tvTransactionDate = view.findViewById(R.id.tvTransactionDate);
        textView8 = view.findViewById(R.id.textView8);
        btnUnite = view.findViewById(R.id.btnUnite);
        btnUnite.setOnClickListener(this);
        civAvatarBot = view.findViewById(R.id.civAvatarBot);
        flDataBot = view.findViewById(R.id.flDataBot);
        rvSimiliar = view.findViewById(R.id.rvSimiliar);
        civAvatar = view.findViewById(R.id.civAvatar);
        civChildAvatar = view.findViewById(R.id.civChildAvatar);
        tvMentorFullName = view.findViewById(R.id.tvMentorFullName);
        tvClass = view.findViewById(R.id.tvClass);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvTitle = view.findViewById(R.id.tvTitle);

        rvMessages = view.findViewById(R.id.rvMessages);
        messagesAdapter = new SchoolRequestMessagesAdapter(activity);
        rvMessages.setLayoutManager(new LinearLayoutManager(activity));
        rvMessages.setAdapter(messagesAdapter);
    }
    private void onSetData() {

        if(mRequest.getSourceType() !=null && mRequest.getSourceType().equals("Mentor")){
            tvTitle.setText(mRequest.getSourceFirstName() + " " + mRequest.getSourceLastName());

            if (!stringIsNullOrEmpty(mRequest.getSourceAvatar()))
                Picasso.get().load(mRequest.getSourceAvatar()).fit().centerCrop().into(civAvatar);

            tvMentorFullName.setText(mRequest.getChildFirstName() + " " + mRequest.getChildLastName());
            tvClass.setText(mRequest.getName());
            tvTransactionDate.setText(mRequest.getDate());

            ModelSchoolData modelSchoolData = activity.getSchoolData();
            ArrayList<ModelSchoolStudent> Students = new ArrayList<>();
            if(!listIsNullOrEmpty(modelSchoolData.getStudents())){
                for (ModelSchoolStudent _student : modelSchoolData.getStudents()){
                    if(!listIsNullOrEmpty(_student.getClasses()))
                        for (ModelSchoolClass _class : _student.getClasses()){
                            if(_class.getId().equals(mRequest.getClassId()))
                                Students.add(_student);
                        }
                }
                if(!listIsNullOrEmpty(Students)){
                    SchoolAddStudentsAdapter adapter = new SchoolAddStudentsAdapter(activity);
                    rvSimiliar.setAdapter(adapter);
                    rvSimiliar.setLayoutManager(new LinearLayoutManager(activity));
                    adapter.UpdateData(Students);
                    adapter.setOnItemCLickListener(this);

                    flDataBot.setVisibility(View.VISIBLE);
                    civAvatarBot.setVisibility(View.VISIBLE);
                    btnUnite.setVisibility(View.VISIBLE);
                    textView8.setVisibility(View.VISIBLE);
                }
                else {
                    flDataBot.setVisibility(View.GONE);
                    civAvatarBot.setVisibility(View.GONE);
                    btnUnite.setVisibility(View.GONE);
                    textView8.setVisibility(View.GONE);
                }
            }
            else {
                flDataBot.setVisibility(View.GONE);
                civAvatarBot.setVisibility(View.GONE);
                btnUnite.setVisibility(View.GONE);
                textView8.setVisibility(View.GONE);
            }
        }
        else {
            tvTitle.setText(mRequest.getParentFirstName() + " " + mRequest.getParentLastName());

            if (!stringIsNullOrEmpty(mRequest.getChildAvatar()))
                Picasso.get().load(mRequest.getChildAvatar()).fit().centerCrop().into(civChildAvatar);
            if (!stringIsNullOrEmpty(mRequest.getParentAvatar()))
                Picasso.get().load(mRequest.getParentAvatar()).fit().centerCrop().into(civAvatar);

            tvMentorFullName.setText(mRequest.getChildFirstName() + " " + mRequest.getChildLastName());
            tvClass.setText(mRequest.getName());
            tvTransactionDate.setText(mRequest.getDate());

            ModelSchoolData modelSchoolData = activity.getSchoolData();
            ArrayList<ModelSchoolStudent> Students = new ArrayList<>();
            if(!listIsNullOrEmpty(modelSchoolData.getStudents())){
                for (ModelSchoolStudent _student : modelSchoolData.getStudents()){
                    if(!listIsNullOrEmpty(_student.getClasses()))
                        for (ModelSchoolClass _class : _student.getClasses()){
                            if(_class.getId().equals(mRequest.getClassId()))
                                Students.add(_student);
                        }
                }
                if(!listIsNullOrEmpty(Students)){
                    SchoolAddStudentsAdapter adapter = new SchoolAddStudentsAdapter(activity);
                    rvSimiliar.setAdapter(adapter);
                    rvSimiliar.setLayoutManager(new LinearLayoutManager(activity));
                    adapter.UpdateData(Students);
                    adapter.setOnItemCLickListener(this);

                    flDataBot.setVisibility(View.VISIBLE);
                    civAvatarBot.setVisibility(View.VISIBLE);
                    btnUnite.setVisibility(View.VISIBLE);
                    textView8.setVisibility(View.VISIBLE);
                }
                else {
                    flDataBot.setVisibility(View.GONE);
                    civAvatarBot.setVisibility(View.GONE);
                    btnUnite.setVisibility(View.GONE);
                    textView8.setVisibility(View.GONE);
                }
            }
            else {
                flDataBot.setVisibility(View.GONE);
                civAvatarBot.setVisibility(View.GONE);
                btnUnite.setVisibility(View.GONE);
                textView8.setVisibility(View.GONE);
            }
        }
    }
    private void onLoadRequestMessages() {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackClick();
                break;
            case R.id.btnAdd:
                onAddClick();
                break;
            case R.id.btnUnite:
                onUniteClick();
                break;
        }
    }
    private void onAddClick() {
        mRequest.setSchoolId(activity.login.loadUserDate(LoginKey.ID));
        String SOAP = SoapRequest(func_SchoolAddStudentV2, new Gson().toJson(mRequest));
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
                    String result = _Web.XMLReader(response.body().string());
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
    private void onUniteClick() {
        mRequest.setSchoolId(activity.login.loadUserDate(LoginKey.ID));
        mRequest.setStudentId(SelectStudent.getIndex());
        String SOAP = SoapRequest(func_SchoolAddStudentV3, new Gson().toJson(mRequest));
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
                    String result = _Web.XMLReader(response.body().string());
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
    private void onBackClick() {
        activity.onBackPressed();
    }
    @Override
    public void onSelectChildForUnite(ModelSchoolStudent mStudent) {
        SelectStudent = mStudent;
        btnUnite.setEnabled(true);
        btnUnite.setBackgroundResource(R.drawable.background_oval_green);
        btnUnite.setPadding(Convert.DpToPx(activity,20),0,Convert.DpToPx(activity,20),0);
    }
}
