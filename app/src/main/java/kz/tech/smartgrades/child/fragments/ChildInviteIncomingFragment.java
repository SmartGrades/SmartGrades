package kz.tech.smartgrades.child.fragments;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.child.adapters.ChildInviteListAdapter;
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.root.login.LoginKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetTimeInterFormInFamilyGroup;
import static kz.tech.smartgrades._Web.func_RejectInterFormInFamilyGroup;

public class ChildInviteIncomingFragment extends Fragment implements ChildInviteListAdapter.OnItemClickListener{
    public static final String ARG_PAGE = "ARG_PAGE";
    private String CHILD_ID;
    private int mPage;
    private ChildInviteListAdapter adapter;
    private ChildActivity activity;
    private ArrayList<ModelInterForm> invites;

    private RecyclerView rvInviteList;

    public ChildInviteIncomingFragment(ChildActivity activity, int i){
        this.activity = activity;
        this.CHILD_ID = activity.login.loadUserDate(LoginKey.ID);
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
        adapter = new ChildInviteListAdapter(activity);
        adapter.setOnItemClickListener(this);
        rvInviteList.setAdapter(adapter);
        ArrayList<ModelInterForm> invitesSort = new ArrayList<>();
        for (ModelInterForm m : activity.getModelInterFormList()) {
            if (!m.getSourceId().equals(CHILD_ID)) {
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
                if (!m.getSourceId().equals(CHILD_ID)) {
                    invitesSort.add(m);
                }
            }
            adapter.updateData(invitesSort);
        }
    }

    @Override
    public void onEnrollClick(ModelInterForm modelInterForm) {
        View view = getLayoutInflater().inflate(R.layout.pw_family_group_code, null);
        TextView tvTimer = view.findViewById(R.id.tvTimer);
        EditText etEnterCode = view.findViewById(R.id.etEnterCode);
        etEnterCode.requestFocus();
        AlertDialog alertDialog = new AlertDialog.Builder(activity).setView(view).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.Id, modelInterForm.getId());
        String SOAP = SoapRequest(func_GetTimeInterFormInFamilyGroup, jsonData.toString());
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
                            if(!result.equals("null")){
                                String timer = new Gson().fromJson(result, String.class);
                                int timerInt = Integer.parseInt(timer) * 1000;

                                if(timerInt < 0){
                                    activity.alert.onToast(getString(R.string.The_code_is_invalid));
                                }
                                else{
                                    alertDialog.show();
                                    new CountDownTimer(timerInt, 1000){
                                        public void onTick(long millisUntilFinished){
                                            SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                                            String time = df.format(millisUntilFinished);
                                            tvTimer.setText(time);
                                        }

                                        public void onFinish(){
                                            alertDialog.dismiss();
                                        }
                                    }.start();

                                    etEnterCode.addTextChangedListener(new TextWatcher(){
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable){
                                            if(editable.toString().length() == 4){
                                                activity.acceptInvite(modelInterForm, editable.toString());
                                                adapter.cancel(modelInterForm);
                                                alertDialog.dismiss();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRejectClick(ModelInterForm modelInterForm) {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.Id, modelInterForm.getId());
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
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onCancelClick(ModelInterForm modelInterForm) {

    }
}