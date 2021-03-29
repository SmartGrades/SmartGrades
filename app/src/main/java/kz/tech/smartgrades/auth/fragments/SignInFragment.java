package kz.tech.smartgrades.auth.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.S;
import kz.tech.smartgrades.auth.AuthActivity;

import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.auth.models.ModelUserData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLReader;
import static kz.tech.smartgrades._Web.func_AuthUser;

public class SignInFragment extends Fragment implements View.OnClickListener {

    private AuthActivity activity;

    private EditText etMailOrPhoneEnter, etPasswordEnter;
    private Button btnSignIn;
    private TextView tvForgotPassword, tvRegistration;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AuthActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        changeText();
    }

    private void initViews(View view) {
        etMailOrPhoneEnter = view.findViewById(R.id.etMailEnter);
        etPasswordEnter = view.findViewById(R.id.etPasswordEnter);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);
        tvForgotPassword = view.findViewById(R.id.tvForgotPassword);
        tvForgotPassword.setOnClickListener(this);
        tvRegistration = view.findViewById(R.id.tvRegistration);
        tvRegistration.setOnClickListener(this);
    }

    private void changeText() {
        etMailOrPhoneEnter.setHint(activity.onTranslateString(R.string.mail_or_phone));
        etPasswordEnter.setHint(activity.onTranslateString(R.string.password));
        btnSignIn.setText(activity.onTranslateString(R.string.sign_in));
        tvForgotPassword.setText(activity.onTranslateString(R.string.forgot_password));
        tvRegistration.setText(activity.onTranslateString(R.string.register));
    }

    private void onSignInClick() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("Login", etMailOrPhoneEnter.getText().toString());
        //jsonData.addProperty("Password", MD5(etPasswordEnter.getText().toString()));
        jsonData.addProperty("Password", etPasswordEnter.getText().toString());

        String SOAP = SoapRequest(func_AuthUser, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLReader(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.alert.onToast(answer.getMessage());
                            if (answer.isSuccess()) {
                                ModelUserData modelLogin = new Gson().fromJson(result, ModelUserData.class);
                                activity.login.saveUserData(modelLogin); // глубже
                                switch (modelLogin.getType()) {
                                    case S.PARENT:
                                        activity.onNextActivity(1);
                                        return;
                                    case S.MENTOR:
                                        activity.onNextActivity(2);
                                        return;
                                    case S.CHILD:
                                        activity.onNextActivity(3);
                                        return;
                                    case S.SCHOOL:
                                        activity.onNextActivity(4);
                                        return;
                                    case S.SPONSOR:
                                        activity.onNextActivity(5);
                                        return;
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void onForgotPassword() {
    }

    private void onRegistrationClick() {
        activity.onRegistration();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                onSignInClick();
                break;
            case R.id.tvForgotPassword:
                onForgotPassword();
                break;
            case R.id.tvRegistration:
                onRegistrationClick();
                break;
        }
    }
}