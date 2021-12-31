package kz.tech.smartgrades.school;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.school.models.ModelSchoolCreateContact;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;
import kz.tech.smartgrades.school.mvp.ICallBack;
import kz.tech.smartgrades.school.mvp.IModel;
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
import static kz.tech.smartgrades._Web.func_GetSchoolData;
import static kz.tech.smartgrades._Web.func_SchoolAddLesson;
import static kz.tech.smartgrades._Web.func_SchoolAddStudent;
import static kz.tech.smartgrades._Web.func_SchoolAddTeacher;
import static kz.tech.smartgrades._Web.func_SchoolGetLessonsList;
import static kz.tech.smartgrades._Web.func_SchoolGetStudentsList;
import static kz.tech.smartgrades._Web.func_SchoolGetTeachersList;

public class SchoolModel implements IModel{

    private SchoolActivity activity;
    private String SCHOOL_ID;

    public SchoolModel(SchoolActivity activity){
        this.activity = activity;
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    public void onLoadSchoolData(ICallBack callBack){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("Id", SCHOOL_ID);

        String SOAP = SoapRequest(func_GetSchoolData, jsonData.toString());
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
                            ModelSchoolData modelSchoolData = new Gson().fromJson(result, ModelSchoolData.class);
                            callBack.onResultLoadSchoolData(modelSchoolData);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onAddStudent(ICallBack callBack, ModelSchoolCreateContact model){
        String SOAP = SoapRequest(func_SchoolAddStudent, new Gson().toJson(model));
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
                    if(answer.isSuccess()) activity.presenter.onStartPresenter();
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            activity.alert.onToast(answer.getMessage());
                            activity.updatePresenter();
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onGetStudentsList(ICallBack callBack){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("SchoolId", activity.login.loadUserDate(LoginKey.ID));

        String SOAP = SoapRequest(func_SchoolGetStudentsList, jsonData.toString());
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
                    ArrayList<ModelSchoolStudent> students = new Gson().fromJson(result, new TypeToken<ArrayList<ModelSchoolStudent>>(){
                    }.getType());
                    callBack.onResultGetStudentsList(students);
                }
            }
        });
    }

    @Override
    public void onAddTeacher(ICallBack callBack, ModelSchoolTeacher model){
        String SOAP = SoapRequest(func_SchoolAddTeacher, new Gson().toJson(model));
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
                    if(answer.isSuccess()) activity.presenter.onStartPresenter();
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            activity.alert.onToast(answer.getMessage());
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onGetTeachersList(ICallBack callBack){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("SchoolId", activity.login.loadUserDate(LoginKey.ID));

        String SOAP = SoapRequest(func_SchoolGetTeachersList, jsonData.toString());
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
                    ArrayList<ModelSchoolTeacher> teachers = new Gson().fromJson(result, new TypeToken<ArrayList<ModelSchoolTeacher>>(){
                    }.getType());
                    callBack.onResultGetTeachersList(teachers);
                }
            }
        });
    }

    @Override
    public void onAddLesson(ICallBack callBack, ModelSchoolLesson model){
        String SOAP = SoapRequest(func_SchoolAddLesson, new Gson().toJson(model));
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
                            if(answer.isSuccess()) activity.presenter.onStartPresenter();
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onGetLessonsList(ICallBack callBack){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("SchoolId", activity.login.loadUserDate(LoginKey.ID));

        String SOAP = SoapRequest(func_SchoolGetLessonsList, jsonData.toString());
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
                    ArrayList<ModelSchoolLesson> lessons = new Gson().fromJson(result, new TypeToken<ArrayList<ModelSchoolLesson>>(){
                    }.getType());
                    callBack.onResultGetLessonsList(lessons);
                }
            }
        });
    }
}