package kz.tech.smartgrades.sponsor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.sponsor.models.ModelSponsorData;
import kz.tech.smartgrades.sponsor.mvp.ICallBack;
import kz.tech.smartgrades.sponsor.mvp.IModel;
import kz.tech.smartgrades.root.login.LoginKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_AddAboutMe;
import static kz.tech.smartgrades._Web.func_AddSponsorRoom;
import static kz.tech.smartgrades._Web.func_GetSponsorData;
import static kz.tech.smartgrades._Web.func_GetSponsorRoom;

public class SponsorModel implements IModel {

    private SponsorActivity activity;
    private String SPONSOR_ID;
    private ModelSponsorData mSponsorData;

    public SponsorModel(SponsorActivity activity) {
        this.activity = activity;
        SPONSOR_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    @Override
    public void onCheckSponsorWorksheet(ICallBack callBack) {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, SPONSOR_ID);

        String SOAP = SoapRequest(func_GetSponsorRoom, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String xml = response.body().string();
                if (response.code() >= 200 && response.code() <= 300) {
                    String result = _Web.XMLToJson(xml);
                    if (result.equals("0")) {
                        callBack.onCheckSponsorWorksheetResult(true);
                    }
                    else if (result.equals("1")) {
                        callBack.onCheckSponsorWorksheetResult(false);
                    }
                }
            }
        });
    }
    public void onAddSponsorWorksheet(ICallBack callBack, String aboutMe, String keyWordsYourMentors) {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, SPONSOR_ID);
        jsonData.addProperty(F.AboutMe, aboutMe);
        jsonData.addProperty(F.Description, keyWordsYourMentors);

        String SOAP = SoapRequest(func_AddSponsorRoom, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String xml = response.body().string();
                if (response.code() >= 200 && response.code() <= 300) {
                    String result = _Web.XMLToJson(xml);
                    if (result.equals("0")) {
                        callBack.onAddSponsorWorksheetResult(false);
                    }
                    else if (result.equals("1")) {
                        callBack.onAddSponsorWorksheetResult(true);
                    }
                }
            }
        });
    }

    public void onLoadData(ICallBack callBack) {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, SPONSOR_ID);

        String SOAP = SoapRequest(func_GetSponsorData, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String xml = response.body().string();
                if (response.code() >= 200 && response.code() <= 300) {
                    String result = _Web.XMLToJson(xml);
                    if (result.equals("0")) { }
                    else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSponsorData = new Gson().fromJson(result, ModelSponsorData.class);
                                callBack.onLoadDataResult(mSponsorData);
                            }
                        });
                    }
                }
            }
        });
    }

    public void onAddAboutMe(ICallBack callBack, String aboutMe) {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, SPONSOR_ID);
        jsonData.addProperty(F.AboutMe, aboutMe);

        String SOAP = SoapRequest(func_AddAboutMe, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.code() >= 200 && response.code() <= 300) {
                    String xml = response.body().string();
                    String result = _Web.XMLToJson(xml);
                    if (result.equals("0")) {
                        callBack.ResultAddAboutMe(false);
                    }
                    else if (result.equals("1")) {
                        callBack.ResultAddAboutMe(true);
                        activity.login.updateUserData(LoginKey.ABOUT_ME, aboutMe);
                    }
                }
            }
        });
    }
}