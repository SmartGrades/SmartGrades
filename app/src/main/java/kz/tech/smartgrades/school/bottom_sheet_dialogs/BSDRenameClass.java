package kz.tech.smartgrades.school.bottom_sheet_dialogs;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
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
import static kz.tech.smartgrades._Web.func_SchoolRenameClass;

public class BSDRenameClass extends BottomSheetDialog implements View.OnClickListener{

    private SchoolActivity activity;
    private ModelSchoolClass modelSchoolClass;
    private String columnId;

    private EditText etEnterCardLabel;
    private Button btnChangeCardLabel;
    private TextView tvRenameCardLabel;

    public BSDRenameClass(@NonNull SchoolActivity activity, ModelSchoolClass modelSchoolClass, String columnId) {
        super(activity, R.style.BottomSheetStyle);
        this.activity = activity;
        this.columnId = columnId;
        this.modelSchoolClass = modelSchoolClass;
        View view = getLayoutInflater().inflate(R.layout.bsd_parent_rename_card, null, false);
        setContentView(view);
        initViews(view);
    }

    private void initViews(View view) {
        tvRenameCardLabel = view.findViewById(R.id.tvRenameCardLabel);
        tvRenameCardLabel.setText(R.string.rename_class);
        etEnterCardLabel = view.findViewById(R.id.etEnterCardLabel);
        etEnterCardLabel.requestFocus();
        etEnterCardLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (stringIsNullOrEmpty(editable.toString())) btnChangeCardLabel.setVisibility(View.INVISIBLE);
                else btnChangeCardLabel.setVisibility(View.VISIBLE);
            }
        });
        btnChangeCardLabel = view.findViewById(R.id.btnChangeCardLabel);
        btnChangeCardLabel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnChangeCardLabel:
               rename();
               break;
        }
    }

    private void rename() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(F.SchoolId, activity.login.loadUserDate(LoginKey.ID));
        jsonObject.addProperty(F.ColumnId, columnId);
        jsonObject.addProperty(F.Id, modelSchoolClass.getId());
        jsonObject.addProperty(F.Name, etEnterCardLabel.getText().toString());
        String SOAP = SoapRequest(func_SchoolRenameClass, jsonObject.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
                System.out.println(e.toString());
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = _Web.XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.alert.onToast(answer.getMessage());
                            if(answer.isSuccess()){
                                activity.presenter.onStartPresenter();
                                dismiss();
                            }
                        }
                    });
                }
            }
        });
    }
}
