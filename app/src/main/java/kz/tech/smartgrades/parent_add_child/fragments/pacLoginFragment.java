package kz.tech.smartgrades.parent_add_child.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.parent_add_child.ParentAddChildActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.F.isUsernameValid;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_IsFreeLogin;

public class pacLoginFragment extends Fragment implements View.OnClickListener {

    private ParentAddChildActivity activity;

    private ImageView ivNext;
    private TextView tvLoginTitle;
    private EditText etLogin;
    private boolean isLoginEmpty = false; // not empty

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddChildActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_enter, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        changeText();
    }

    private void initViews(View view) {
        tvLoginTitle = view.findViewById(R.id.tvLoginTitle);
        etLogin = view.findViewById(R.id.etLogin);
        ivNext = view.findViewById(R.id.ivNext);
        ivNext.setOnClickListener(this);

        etLogin.requestFocus();

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        etLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.length() == 0) isLoginEmpty = false;
                else isLoginEmpty = isUsernameValid(arg0.toString());
                onImageTernar(isLoginEmpty);
                String s=arg0.toString();
                if (!s.equals(s.toLowerCase())){
                    s=s.toLowerCase();
                    etLogin.setText(s);
                    etLogin.setSelection(s.length()); // what to do
                }
            }
        });

    }

    private void changeText() {
        tvLoginTitle.setText(activity.onTranslateString(R.string.create_login));
        etLogin.setHint(activity.onTranslateString(R.string.enter_login));
    }

    private void onImageTernar(boolean isImg) {
        ivNext.setImageResource(isImg? R.drawable.img_right_arrow_green : R.drawable.img_right_arrow_uncheck_green);
    }


    private void onBack() {
        activity.onBackPressed();
    }

    private void onNext() {
        if (isLoginEmpty) {
            String Login = etLogin.getText().toString();
            JsonObject json = new JsonObject();
            json.addProperty("Login", Login);
            String SOAP = SoapRequest(func_IsFreeLogin, json.toString());
            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
            Request request = new Request.Builder().url(URL).post(body).build();
            HttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, IOException e) {
                }
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = XMLToJson(response.body().string());
                        ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (answer.isSuccess()){
                                    activity.alert.hideKeyboard(activity);
                                    activity.onNextFragment();
                                    activity.setChildLogin(etLogin.getText().toString());
                                }
                                else activity.alert.onToast(answer.getMessage());
                            }
                        });
                        ivNext.setClickable(true);
                    }
                }
            });
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNext:
                ivNext.setClickable(false);
                onNext();
                break;
        }
    }
}
