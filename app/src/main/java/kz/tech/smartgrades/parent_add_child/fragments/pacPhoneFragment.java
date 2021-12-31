package kz.tech.smartgrades.parent_add_child.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import kz.tech.smartgrades.auth.models.ModelSMSCode;
import kz.tech.smartgrades.parent_add_child.ParentAddChildActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_SendSMSCode;

public class pacPhoneFragment extends Fragment implements View.OnClickListener {

    private ParentAddChildActivity activity;

    private ImageView ivNext;
    private EditText etPhoneEnter;
    private TextView tvLabelEnterPhoneNumber, tvPhoneTitle;

    public int type = 0;
    private boolean isPhoneNotEmpty = false;
    private String Phone;

    private Button btnSendCode;
    private EditText etSMSCode;

    private ImageView ivCheck;
    private TextView tvAccept;

    private String SMSCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddChildActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_phone_enter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        changeText();
    }

    private void EnableButtonSendSMSCode(boolean bool) {
        btnSendCode.setEnabled(bool);
        if (bool) btnSendCode.setBackgroundResource(R.drawable.background_square_white);
        else btnSendCode.setBackgroundResource(R.drawable.background_square_gray);
        btnSendCode.setPadding(10, 10, 10, 10);
        etSMSCode.requestFocus();
    }

    private void initViews(View view) {
        etPhoneEnter = view.findViewById(R.id.etPhoneEnter);
        tvLabelEnterPhoneNumber = view.findViewById(R.id.tvLabelEnterPhoneNumber);
        tvPhoneTitle = view.findViewById(R.id.tvPhoneTitle);
        ivNext = view.findViewById(R.id.ivNext);
        ivNext.setOnClickListener(this);
        btnSendCode = view.findViewById(R.id.btnSendCode);
        btnSendCode.setOnClickListener(this);
        etSMSCode = view.findViewById(R.id.etSMSCode);
        ivCheck = view.findViewById(R.id.ivCheck);
        tvAccept = view.findViewById(R.id.tvAccept);
        ivCheck.setOnClickListener(this);

        etPhoneEnter.requestFocus();

        etPhoneEnter.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.length() == 10) {
                    isPhoneNotEmpty = true;
                    Phone = "+7" + etPhoneEnter.getText().toString();
                } else isPhoneNotEmpty = false;
                EnableButtonSendSMSCode(isPhoneNotEmpty && ivCheck.getDrawable().getConstantState().equals(activity.getResources().getDrawable(R.drawable.img_green_check).getConstantState()));
            }
        });
        etSMSCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (SMSCode.contentEquals(arg0)) {
                    activity.onNextFragment();
                    activity.setChildPhone(Phone);
                    etSMSCode.setText("");
                }
            }
        });
    }

    private void changeText() {
        tvPhoneTitle.setText(activity.onTranslateString(R.string.enter_your_phone_number));
        etPhoneEnter.setHint(activity.onTranslateString(R.string.enter_your_phone_number));
        tvLabelEnterPhoneNumber.setText(activity.onTranslateString(R.string.phone_number));
    }

    private void onSendCode() {
        JsonObject json = new JsonObject();
        json.addProperty("Phone", Phone);
        String SOAP = SoapRequest(func_SendSMSCode, json.toString());
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
                            if (answer.isSuccess()) {
                                ModelSMSCode code = new Gson().fromJson(result, ModelSMSCode.class);
                                SMSCode = code.getCode();
                                etSMSCode.setVisibility(View.VISIBLE);
                                EnableButtonSendSMSCode(false);
                            } else activity.alert.onToast(answer.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void onCheck() {
        if (ivCheck.getDrawable().getConstantState().equals(activity.getResources().getDrawable(R.drawable.img_green_check).getConstantState())) {
            ivCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_green_uncheck));
        } else {
            ivCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_green_check));
        }
        EnableButtonSendSMSCode(isPhoneNotEmpty && ivCheck.getDrawable().getConstantState().equals(activity.getResources().getDrawable(R.drawable.img_green_check).getConstantState()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSendCode:
                onSendCode();
                break;
            case R.id.ivCheck:
                onCheck();
                break;
        }
    }
}