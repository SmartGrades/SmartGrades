package kz.tech.smartgrades.parent_add_child.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static kz.tech.smartgrades.GET.isEmailOrSite;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_IsFreeEmail;

public class pacMailFragment extends Fragment implements View.OnClickListener {

    private ParentAddChildActivity activity;

    private ImageView ivNext;
    private EditText etMailEnter;
    private TextView tvType, tvMailTitle;

    public int type = 0;
    private boolean isMailNotEmpty = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddChildActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mail_enter, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        changeText();
        onImageTernar(true);
    }

    private void initViews(View view) {
        etMailEnter = view.findViewById(R.id.etMailEnter);
        tvType = view.findViewById(R.id.tvType);
        tvMailTitle = view.findViewById(R.id.tvMailTitle);
        ivNext = view.findViewById(R.id.ivNext);
        ivNext.setOnClickListener(this);

        etMailEnter.requestFocus();

        etMailEnter.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.length() == 0) isMailNotEmpty = true;
                else isMailNotEmpty = arg0.length() > 0 && isEmailOrSite(arg0.toString(), true);
                onImageTernar(isMailNotEmpty);
            }
        });
    }

    private void changeText() {
        tvMailTitle.setText(activity.onTranslateString(R.string.enter_your_mail));
        etMailEnter.setHint(activity.onTranslateString(R.string.enter_your_mail));
        tvType.setText(activity.onTranslateString(R.string.mail));
    }

    private void onImageTernar(boolean isImg) {
        ivNext.setImageResource(isImg ? R.drawable.img_right_arrow_green : R.drawable.img_right_arrow_uncheck_green);
    }

    private void onBack() {
        activity.onBackPressed();
    }

    private void onNext() {
        if (stringIsNullOrEmpty(etMailEnter.getText().toString())) {
            activity.onNextFragment();
            return;
        }
        if (isMailNotEmpty) {
            String Email = etMailEnter.getText().toString();
            JsonObject json = new JsonObject();
            json.addProperty("Email", Email);
            String SOAP = SoapRequest(func_IsFreeEmail, json.toString());
            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
            Request request = new Request.Builder().url(URL).post(body).build();
            HttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, IOException e) {
                    String xml = e.getMessage();
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
                                    activity.onNextFragment();
                                    activity.setChildMail(etMailEnter.getText().toString());
                                } else activity.alert.onToast(answer.getMessage());
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNext:
                onNext();
                break;
        }
    }
}
