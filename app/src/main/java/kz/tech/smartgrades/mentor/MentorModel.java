package kz.tech.smartgrades.mentor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.mentor.models.ModelMentorGroups;
import kz.tech.smartgrades.mentor.models.ModelMentorLesson;
import kz.tech.smartgrades.mentor.mvp.ICallBack;
import kz.tech.smartgrades.mentor.mvp.IModel;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelMessageToGroup;
import kz.tech.smartgrades.school.models.ModelSchoolLessons;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_AddMentorGroup;
import static kz.tech.smartgrades._Web.func_AddMentorRoom;
import static kz.tech.smartgrades._Web.func_GetMentorData;
import static kz.tech.smartgrades._Web.func_GetMentorGroup;
import static kz.tech.smartgrades._Web.func_GetMentorRoom;
import static kz.tech.smartgrades._Web.func_MentorAddLesson;
import static kz.tech.smartgrades._Web.func_SendMessageToGroups;

public class MentorModel implements IModel {

    private MentorActivity activity;
    private String MENTOR_ID;
    private ModelMentorData modelMentorData;

    public MentorModel(MentorActivity activity) {
        this.activity = activity;
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    public void onLoadMentorData(ICallBack callBack) {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, MENTOR_ID);

        String SOAP = SoapRequest(func_GetMentorData, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("0")) { }
                            else {
                                modelMentorData = new Gson().fromJson(result, ModelMentorData.class);
                                callBack.onResultLoadMentorData(modelMentorData);
                            }
                        }
                    });
                }
            }
        });
    }

    public void onAddWorksheet(ICallBack callBack, String aboutMe, String description) {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, MENTOR_ID);
        jsonData.addProperty(F.AboutMe, aboutMe);
        jsonData.addProperty(F.Description, description);

        String SOAP = SoapRequest(func_AddMentorRoom, jsonData.toString());
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
                        callBack.ResultAddMentorWorksheet(false);
                    }
                    else if (result.equals("1")) {
                        callBack.ResultAddMentorWorksheet(true);
                        activity.login.updateUserData(LoginKey.ABOUT_ME, aboutMe);
                        activity.login.updateUserData(LoginKey.DESCRIPTION, description);
                    }
                }
            }
        });
    }

    @Override
    public void onCheckMentorWorksheet(ICallBack callBack) {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, MENTOR_ID);

        String SOAP = SoapRequest(func_GetMentorRoom, jsonData.toString());
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
                        callBack.onResultMentorWorksheet(true);
                    }
                    else if (result.equals("1")) {
                        callBack.onResultMentorWorksheet(false);
                    }
                }
            }
        });
    }
    @Override
    public void onSendMessageToGroup(ICallBack callBack, ModelMessageToGroup model){
        String SOAP = SoapRequest(func_SendMessageToGroups, new Gson().toJson(model));
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = _Web.XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            activity.alert.onToast(answer.getMessage());
                            callBack.ResultSendMessageToGroup(answer.isSuccess());
                        }
                    });
                }
            }
        });
    }

    public void onAddMentorGroup(ICallBack callBack, String GroupName, ModelSchoolLessons modelSchoolLessons){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.MentorId, MENTOR_ID);
        jsonData.addProperty(F.GroupName, GroupName);
        jsonData.addProperty(F.LessonId, modelSchoolLessons.getLessonId());

        String SOAP = SoapRequest(func_AddMentorGroup, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (answer.isSuccess()) {
                                onLoadMentorGroups(callBack);
                            }
                        }
                    });
                }
            }
        });
    }
    public void onLoadMentorGroups(ICallBack callBack) {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.MentorId, MENTOR_ID);

        String SOAP = SoapRequest(func_GetMentorGroup, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLToJson(response.body().string());
                    if (!result.equals("null")) {
                        Type founderListType = new TypeToken<ArrayList<ModelMentorGroups>>(){}.getType();
                        ArrayList<ModelMentorGroups> MentorGroupList = new Gson().fromJson(result, founderListType);
                        callBack.onLoadMentorGroups(MentorGroupList);
                    }
                }
            }
        });
    }
    public void onAddLesson(ICallBack callBack, ModelMentorLesson model){
        String SOAP = SoapRequest(func_MentorAddLesson, new Gson().toJson(model));
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            if(answer.isSuccess())
                                activity.alert.onToast("Предмет успешно добавлен");
                            else activity.alert.onToast("Ошибка, попробуйте еще раз");
                            callBack.onResultMentorAddLesson(answer);
                        }
                    });
                }
            }
        });
    }
}