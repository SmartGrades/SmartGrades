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
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolAddTeachersAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolRequestMessagesAdapter;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;
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
import static kz.tech.smartgrades._Web.func_SchoolAddTeacher;
import static kz.tech.smartgrades._Web.func_SchoolUniteTeacher;

public class SchoolTeacherRequestFragment extends Fragment implements View.OnClickListener, SchoolAddTeachersAdapter.OnItemCLickListener{

    private SchoolActivity activity;
    private String SCHOOL_ID;
    private ImageView ivBack;
    private TextView tvTitle;
    private RecyclerView rvMessages;
    private SchoolRequestMessagesAdapter messagesAdapter;
    private ModelInterForm mInterForm;

    private CircleImageView civAvatar;
    private Button btnAdd;

    private RecyclerView rvSimiliar;

    private FrameLayout flDataBot;
    private CircleImageView civAvatarBot;
    private Button btnUnite;
    private TextView textView8;

    private ModelSchoolTeacher SelectStudent;

    private SchoolAddTeachersAdapter adapter;

    private TextView tvTransactionDate;


    public static SchoolTeacherRequestFragment newInstance(ModelInterForm model) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", model);
        SchoolTeacherRequestFragment fragment = new SchoolTeacherRequestFragment();
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
        return inflater.inflate(R.layout.fragment_school_teacher_request, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        onSetData();
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
        tvTitle.setText(mInterForm.getSourceFirstName() + " " + mInterForm.getSourceLastName());

        if (!stringIsNullOrEmpty(mInterForm.getSourceAvatar()))
            Picasso.get().load(mInterForm.getSourceAvatar()).fit().centerCrop().into(civAvatar);

        tvTransactionDate.setText(mInterForm.getTimeStamp());

        ModelSchoolData modelSchoolData = activity.getSchoolData();
        ArrayList<ModelSchoolTeacher> Teachers = new ArrayList<>();
        if(!listIsNullOrEmpty(modelSchoolData.getTeachers())){
            for (ModelSchoolTeacher _teacher : modelSchoolData.getTeachers()){
                if (stringIsNullOrEmpty(_teacher.getUserId()))
                    Teachers.add(_teacher);
            }
            if(!listIsNullOrEmpty(Teachers)){
                adapter = new SchoolAddTeachersAdapter(activity);
                rvSimiliar.setAdapter(adapter);
                rvSimiliar.setLayoutManager(new LinearLayoutManager(activity));
                adapter.UpdateData(Teachers);
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
        String SOAP = SoapRequest(func_SchoolAddTeacher, new Gson().toJson(mInterForm));
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
        mInterForm.setId(SelectStudent.getId());
        mInterForm.setUserId(mInterForm.getSourceId());
        mInterForm.setAvatar(mInterForm.getSourceAvatar());
        mInterForm.setAvatarOriginal(mInterForm.getSourceAvatarOriginal());
        mInterForm.setFirstName(mInterForm.getSourceFirstName());
        mInterForm.setLastName(mInterForm.getSourceLastName());
        String SOAP = SoapRequest(func_SchoolUniteTeacher, new Gson().toJson(mInterForm));
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
    public void onSelectForUnite(ModelSchoolTeacher index) {
        SelectStudent = index;
        adapter.notifyDataSetChanged();
        btnUnite.setEnabled(true);
        btnUnite.setBackgroundResource(R.drawable.background_oval_green);
        btnUnite.setPadding(Convert.DpToPx(activity,20),0,Convert.DpToPx(activity,20),0);
    }
}
