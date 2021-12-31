package kz.tech.smartgrades.parent_add_child.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.parent.model.ModelInterFormParentToChild;
import kz.tech.smartgrades.parent_add_child.ParentAddChildActivity;
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
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_InterFormParentToChild;

public class pacProfileChildFragment extends Fragment implements View.OnClickListener {

    private ParentAddChildActivity activity;
    private String PARENT_ID;
    private ModelUser model;

    private CircleImageView civAvatar;
    private TextView tvFullName, tvLogin, tvAboutMe, tvDescription;
    private Button btnSend;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddChildActivity) getActivity();
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_profile_child, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        civAvatar = view.findViewById(R.id.civAvatar);
        tvFullName = view.findViewById(R.id.tvFullName);
        tvLogin = view.findViewById(R.id.tvLogin);
        tvAboutMe = view.findViewById(R.id.tvTitle1);
        tvDescription = view.findViewById(R.id.tvDescription);
        btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
    }

    public void setProfileData(ModelUser m) {
        model = m;
        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);
        else civAvatar.setBackgroundResource(R.drawable.img_default_avatar);

        tvFullName.setText(m.getFirstName() + " " + m.getLastName());
        tvLogin.setText(m.getLogin());
        tvAboutMe.setText(m.getAboutMe());
        tvDescription.setText(m.getDescription());
    }

    private void onSend() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.ParentId, PARENT_ID);
        jsonData.addProperty(F.ChildId, model.getUserId());
        jsonData.addProperty(F.Message, getString(R.string.Please_add_me_as_a_Parent));

        String SOAP = SoapRequest(func_InterFormParentToChild, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!answer.isSuccess()) activity.alert.onToast(answer.getMessage());
                            else {
                                activity.alert.onToast(answer.getMessage());

                                ModelInterFormParentToChild m = new Gson().fromJson(result, ModelInterFormParentToChild.class);

                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                View viewAlert = getLayoutInflater().inflate(R.layout.ad_parent_show_code, null);
                                builder.setView(viewAlert);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                                TextView tvCode = viewAlert.findViewById(R.id.tvCode);
                                tvCode.setText(m.getCode());
                                Button btnOkey = viewAlert.findViewById(R.id.btnOkey);
                                btnOkey.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        alertDialog.dismiss();
                                    }
                                });
                            }
                        }
                    });

                }
                else activity.alert.onToast(getString(R.string.An_error_occured_please_try_again_later));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend:
                onSend();
                break;
        }
    }

}
