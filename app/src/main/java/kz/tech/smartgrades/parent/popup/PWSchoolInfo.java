package kz.tech.smartgrades.parent.popup;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.annotations.NotNull;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_GetSchoolInfo;

public class PWSchoolInfo extends PopupWindow implements View.OnClickListener{

    public ParentActivity activity;
    private ModelSchoolInfo mSchool;
    private String schoolId;

    private TextView tvSchoolName;

    public PWSchoolInfo(int width, int height, ParentActivity activity, String schoolId) {
        super(width, height);
        this.activity = activity;
        this.schoolId = schoolId;
        LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pw_school_info, null);
        setContentView(view);
        initViews(view);
        loadSchoolData();
    }

    private void initViews(View view) {
        tvSchoolName = view.findViewById(R.id.tvSchoolName);
        tvSchoolName.setOnClickListener(this);
    }

    private void loadSchoolData() {
        JsonObject json = new JsonObject();
        json.addProperty(F.ParentId, activity.login.loadUserDate(LoginKey.ID));
        json.addProperty(F.SchoolId, schoolId);

        String SOAP = SoapRequest(func_GetSchoolInfo, json.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {}

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ModelSchoolInfo modelSchoolInfo =
                                    new Gson().fromJson(result, ModelSchoolInfo.class);
                            if (modelSchoolInfo != null) {
                                mSchool = modelSchoolInfo;
                                tvSchoolName.setText(mSchool.getSchoolName() + "\n" + mSchool.getAddress());
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSchoolName:
                dismiss();
                if (!stringIsNullOrEmpty(tvSchoolName.getText().toString())) onShowSchoolInfo();
                break;
        }
    }

    private void onShowSchoolInfo() {
        activity.setSchoolModel(mSchool);
    }
}
