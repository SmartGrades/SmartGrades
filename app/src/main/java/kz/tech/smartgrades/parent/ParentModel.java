package kz.tech.smartgrades.parent;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import kz.tech.smartgrades.F;
import kz.tech.smartgrades.parent.model.ModelParentData;
import kz.tech.smartgrades.parent.mvp.ICallBack;
import kz.tech.smartgrades.parent.mvp.IModel;
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
import static kz.tech.smartgrades._Web.func_GetParentData;

public class ParentModel implements IModel {

    private ParentActivity activity;
    private String PARENT_ID;

    public ParentModel(ParentActivity activity) {
        this.activity = activity;
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
    }
    @Override
    public void onDestroyModel() {
        if (activity != null) activity = null;
    }

    public void onLoadData(ICallBack callBack){
        if (stringIsNullOrEmpty(PARENT_ID)) return;
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, PARENT_ID);

        String SOAP = SoapRequest(func_GetParentData, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
//                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onResultLoadData(new Gson().fromJson(result, ModelParentData.class));
//                            else activity.alert.onToast(answer.getMessage());
                        }
                    });
                }
            }
        });
    }
}