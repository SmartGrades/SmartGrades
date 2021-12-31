package kz.tech.smartgrades.auth.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.AuthActivity;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

import static kz.tech.smartgrades.S.CHILD;
import static kz.tech.smartgrades.S.MENTOR;
import static kz.tech.smartgrades.S.PARENT;
import static kz.tech.smartgrades.S.SCHOOL;
import static kz.tech.smartgrades.S.INVESTOR;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_GetRoleIsEnable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

public class TypeFragment extends Fragment implements View.OnClickListener {

    private AuthActivity activity;

    private TextView tvStatusSelectTitle, tvParent, tvChild, tvMentor;
    private CardView cvParent, cvChild, cvMentor, cvSchool, cvInvestor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AuthActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_type_select, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        changeText();
    }
    private void initViews(View view) {
        tvStatusSelectTitle = view.findViewById(R.id.tvStatusSelectTitle);
        tvParent = view.findViewById(R.id.tvParent);
        tvChild = view.findViewById(R.id.tvChild);
        tvMentor = view.findViewById(R.id.tvMentor);
        cvParent = view.findViewById(R.id.cvParent);
        cvParent.setOnClickListener(this);
        cvChild = view.findViewById(R.id.cvChild);
        cvChild.setOnClickListener(this);
        cvMentor = view.findViewById(R.id.cvMentor);
        cvMentor.setOnClickListener(this);
        cvSchool = view.findViewById(R.id.cvSchool);
        cvSchool.setOnClickListener(this);
        cvInvestor = view.findViewById(R.id.cvInvestor);
        cvInvestor.setOnClickListener(this);
    }

    private void changeText() {
        tvStatusSelectTitle.setText(activity.onTranslateString(R.string.who_is_the_account_created_for));
        tvParent.setText(activity.onTranslateString(R.string.parent));
        tvChild.setText(activity.onTranslateString(R.string.child));
        tvMentor.setText(activity.onTranslateString(R.string.teacher));
    }

    private void onTypeSelect(int typeSelect) {
        switch (typeSelect) {
            case 1:
                getCheckRole(PARENT);
                break;
            case 2:
                getCheckRole(CHILD);
                break;
            case 3:
                getCheckRole(MENTOR);
                break;
            case 4:
                getCheckRole(SCHOOL);
                break;
            case 5:
                getCheckRole(INVESTOR);
                break;
        }
    }
    private void getCheckRole(String type) {
        JsonObject json = new JsonObject();
        json.addProperty("Type", type);
        String SOAP = SoapRequest(func_GetRoleIsEnable, json.toString());
        RequestBody body = RequestBody.create(SOAP, ContentType_XML);
        Request request = new Request.Builder().url(URL).post(body).build();
        new OkHttpClient.Builder().protocols(Util.immutableListOf(Protocol.HTTP_1_1)).build()
                .newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                activity.alert.onToast(e.toString());
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(() -> {
                        if (answer.isSuccess()) {
                            activity.setType(type);
                            activity.onNextFragment();
                        }
                        else activity.alert.onToast("В данный момент эта роль дорабатывается");
                    });
                }
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cvParent:
                onTypeSelect(1);
                break;
            case R.id.cvChild:
                onTypeSelect(2);
                break;
            case R.id.cvMentor:
                onTypeSelect(3);
                break;
            case R.id.cvSchool:
                onTypeSelect(4);
                break;
            case R.id.cvInvestor:
                onTypeSelect(5);
                break;
        }
    }
}//
