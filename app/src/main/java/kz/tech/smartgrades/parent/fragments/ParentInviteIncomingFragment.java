package kz.tech.smartgrades.parent.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentInviteListAdapter;
import kz.tech.smartgrades.root.login.LoginKey;
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
import static kz.tech.smartgrades._Web.func_AcceptInterFormInFamilyGroup;
import static kz.tech.smartgrades._Web.func_RejectChildRequests;
import static kz.tech.smartgrades._Web.func_RejectInterFormInFamilyGroup;
import static kz.tech.smartgrades._Web.func_RejectInterFormOfSponsorship;

public class ParentInviteIncomingFragment extends Fragment implements ParentInviteListAdapter.OnItemClickListener{
    public static final String ARG_PAGE = "ARG_PAGE";
    private String PARENT_ID;
    private int mPage;
    private ParentInviteListAdapter adapter;
    private ParentActivity activity;
    private ArrayList<ModelInterForm> invites;

    private RecyclerView rvInviteList;

    public ParentInviteIncomingFragment(ParentActivity activity, int i){
        this.activity = activity;
        this.PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_parent_nested_invite_list, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view){
        rvInviteList = view.findViewById(R.id.rvInviteList);
        rvInviteList.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new ParentInviteListAdapter(activity);
        adapter.setOnItemClickListener(this);
        rvInviteList.setAdapter(adapter);
        ArrayList<ModelInterForm> invitesSort = new ArrayList<>();
        for (ModelInterForm m : activity.getModelInterFormList()) {
            if (!m.getSourceId().equals(PARENT_ID)) {
                invitesSort.add(m);
            }
        }
        adapter.updateData(invitesSort);
    }

    public void setInvites(ArrayList<ModelInterForm> invites) {
        this.invites = invites;
        if (adapter != null) {
            ArrayList<ModelInterForm> invitesSort = new ArrayList<>();
            for (ModelInterForm m : invites) {
                if (!m.getSourceId().equals(PARENT_ID)) {
                    invitesSort.add(m);
                }
            }
            adapter.updateData(invitesSort);
        }
    }

    @Override
    public void onEnrollClick(ModelInterForm modelInterForm) {
        JsonObject jsonData = new JsonObject();
        //jsonData.addProperty(F.SourceId, PARENT_ID);

        if (!stringIsNullOrEmpty(modelInterForm.getSourceType()) && !(modelInterForm.getType().equals("CreateLesson") || modelInterForm.getType().equals("SmartGrades"))) {
            jsonData.addProperty(F.Id, modelInterForm.getId());
            if (modelInterForm.getSourceType().equals("Child") || modelInterForm.getSourceType().equals("Parent")) {
                String SOAP = SoapRequest(func_AcceptInterFormInFamilyGroup, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = _Web.XMLToJson(response.body().string());
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!result.equals("null")) {
                                        ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                        activity.alert.onToast(answer.getMessage());
                                        if (answer.isSuccess()) {
                                            activity.updatePresenter();
                                            adapter.cancel(modelInterForm);
                                            //InviteListAdapter.cancel(modelInterFormList);
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            }
            else if (modelInterForm.getSourceType().equals("Sponsor")) {
                activity.aboutSponsoring(modelInterForm);
            }
            else if (modelInterForm.getSourceType().equals("Mentor")) {

            }
            else if (modelInterForm.getSourceType().equals("School")) {

            }
        }
        if (modelInterForm.getType().equals("CreateLesson")) {
            jsonData.addProperty(F.InterFormId, modelInterForm.getId());
            for (int i = 0; i < activity.getMParentData().getFamilyGroup().getChildrens().size(); i++) {
                if (activity.getMParentData().getFamilyGroup().getChildrens().get(i).getId().equals(modelInterForm.getChildId())) {
                    activity.onRemoveFragment();
                    activity.onAddNewLesson(i, modelInterForm.getId());
                    break;
                }
            }
        } else if (modelInterForm.getType().equals("SmartGrades")) {
            jsonData.addProperty(F.InterFormId, modelInterForm.getId());
        }
    }

    @Override
    public void onRejectClick(ModelInterForm modelInterForm) {
        JsonObject jsonData = new JsonObject();
        if (!stringIsNullOrEmpty(modelInterForm.getSourceType()) && !(modelInterForm.getType().equals("CreateLesson") || modelInterForm.getType().equals("SmartGrades"))) {
            jsonData.addProperty(F.Id, modelInterForm.getId());
            if (modelInterForm.getSourceType().equals("Child") || modelInterForm.getSourceType().equals("Parent")) {
                String SOAP = SoapRequest(func_RejectInterFormInFamilyGroup, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = _Web.XMLToJson(response.body().string());
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!result.equals("null")) {
                                        ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                        activity.alert.onToast(answer.getMessage());
                                        activity.updatePresenter();
                                        adapter.cancel(modelInterForm);
                                        //InviteListAdapter.cancel(modelInterFormList);
                                    }
                                }
                            });
                        }
                    }
                });
            }
            else if (modelInterForm.getSourceType().equals("Sponsor")) {
                String SOAP = SoapRequest(func_RejectInterFormOfSponsorship, jsonData.toString());
                jsonData.addProperty(F.Type, modelInterForm.getType());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = _Web.XMLToJson(response.body().string());
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!result.equals("null")) {
                                        ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                        activity.alert.onToast(answer.getMessage());
                                        activity.updatePresenter();
                                        adapter.cancel(modelInterForm);
                                    }
                                }
                            });
                        }
                    }
                });
            }
            else if (modelInterForm.getSourceType().equals("Mentor")) {

            }
            else if (modelInterForm.getSourceType().equals("School")) {

            }
        }
        if (!stringIsNullOrEmpty(modelInterForm.getType())) {
            jsonData.addProperty(F.InterFormId, modelInterForm.getId());
            jsonData.addProperty(F.Type, modelInterForm.getType());
            jsonData.addProperty(F.SourceId, PARENT_ID);
            if (modelInterForm.getType().equals("CreateLesson") || modelInterForm.getType().equals("SmartGrades")) {
                String SOAP = SoapRequest(func_RejectChildRequests, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = _Web.XMLToJson(response.body().string());
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!result.equals("null")) {
                                        ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                        activity.alert.onToast(answer.getMessage());
                                        if (answer.isSuccess()) {
                                            activity.updatePresenter();
                                            adapter.cancel(modelInterForm);
                                            //InviteListAdapter.cancel(modelInterFormList);
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onCancelClick(ModelInterForm modelInterForm) {

    }
}