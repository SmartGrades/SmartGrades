package kz.tech.smartgrades.child.bottom_sheet_dialog;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.models.ModelDiscontCard;
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
import static kz.tech.smartgrades._Web.func_EditNameDiscontCard;

public class ChildBSDRenameCard extends BottomSheetDialog implements View.OnClickListener{

    private ChildActivity activity;
    private ModelDiscontCard card;

    private EditText etEnterCardLabel;
    private Button btnChangeCardLabel;

    public ChildBSDRenameCard(@NonNull ChildActivity activity, ModelDiscontCard card) {
        super(activity, R.style.BottomSheetStyle);
        this.activity = activity;
        this.card = card;
        View view = getLayoutInflater().inflate(R.layout.bsd_parent_rename_card, null, false);
        setContentView(view);
        initViews(view);
    }

    private void initViews(View view) {
        etEnterCardLabel = view.findViewById(R.id.etEnterCardLabel);
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
                renameCard();
                break;
        }
    }

    private void renameCard() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(F.UserId, activity.login.loadUserDate(LoginKey.ID));
        jsonObject.addProperty(F.Id, card.getId());
        jsonObject.addProperty(F.CardName, etEnterCardLabel.getText().toString());
        String SOAP = SoapRequest(func_EditNameDiscontCard, jsonObject.toString());
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
                            activity.alert.onToast(answer.getMessage());
                            if (answer.isSuccess()) {
                                activity.updatePresenter();
                                dismiss();
                            }
                        }
                    });
                }
            }
        });
    }
}
