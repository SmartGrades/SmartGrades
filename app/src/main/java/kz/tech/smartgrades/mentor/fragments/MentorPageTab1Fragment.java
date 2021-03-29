package kz.tech.smartgrades.mentor.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorRequestAdapter;
import kz.tech.smartgrades.mentor.dialog.DialogMentorAddChildToClass;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.parent.model.ModelInterFormOfMentoring;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_AcceptInterFormOfMentoring;
import static kz.tech.smartgrades._Web.func_RejectInterFormOfMentoring;

public class MentorPageTab1Fragment extends Fragment implements MentorRequestAdapter.OnItemClickListener{

    private MentorActivity activity;
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private TextView tvLabelRequest;
    private RecyclerView rvRequestList;
    private MentorRequestAdapter adapter;
    private ModelMentorData mMentorData;

    public static MentorPageTab1Fragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MentorPageTab1Fragment fragment = new MentorPageTab1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null) mPage = getArguments().getInt(ARG_PAGE);
        activity = (MentorActivity) getActivity();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_mentor_page_tab1, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        tvLabelRequest = view.findViewById(R.id.tvLabelRequest);
        rvRequestList = view.findViewById(R.id.rvRequestList);

        adapter = new MentorRequestAdapter(activity);
        rvRequestList.setLayoutManager(new LinearLayoutManager(activity));
        rvRequestList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    public void onSetData(ArrayList<ModelInterFormOfMentoring> requests){
        if (tvLabelRequest!= null) tvLabelRequest.setVisibility(View.GONE);
        adapter.updateData(requests);
    }

    @Override
    public void onRequestAcceptClick(ModelInterFormOfMentoring m){
        DialogMentorAddChildToClass dialogMentorAddChildToClass = new DialogMentorAddChildToClass(activity, mMentorData, m);
        dialogMentorAddChildToClass.setOnItemClickListener(new DialogMentorAddChildToClass.OnItemClickListener(){
            @Override
            public void onItemClick(ModelSchoolClass mSelectClass){
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.Id, m.getId());
                jsonData.addProperty(F.SourceId, activity.login.loadUserDate(LoginKey.ID));
                jsonData.addProperty(F.ClassId, mSelectClass.getId());
                //jsonData.addProperty(F.LessonId, mSelectClass.getLessonId());

                String SOAP = SoapRequest(func_AcceptInterFormOfMentoring, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = _Web.XMLReader(response.body().string());
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                    activity.alert.onToast(answer.getMessage());
                                    if(answer.isSuccess()) adapter.removeItem(m);
                                }
                            });
                        }
                    }
                });
            }
        });
    }
    @Override
    public void onCancelAcceptClick(ModelInterFormOfMentoring m){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.Id, m.getId());
        jsonData.addProperty(F.SourceId, activity.login.loadUserDate(LoginKey.ID));

        String SOAP = SoapRequest(func_RejectInterFormOfMentoring, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLReader(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.alert.onToast(answer.getMessage());
                            if(answer.isSuccess()) adapter.removeItem(m);
                        }
                    });
                }
            }
        });
    }

    public void onSetModelMentorData(ModelMentorData mMentorData){
        this.mMentorData = mMentorData;
    }
}