package kz.tech.smartgrades.auth;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.auth.mvp.ICallBack;
import kz.tech.smartgrades.auth.mvp.IPresenter;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.auth.models.ModelUserData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.S.SCHOOL;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLReader;
import static kz.tech.smartgrades._Web.func_RegisterSchool;
import static kz.tech.smartgrades._Web.func_RegisterUser;


public class AuthPresenter implements IPresenter, ICallBack {

    private AuthActivity activity;
    private ModelUserData mUserData;
    private ModelSchoolData mSchoolData;
    private ICallBack callBack;

    public AuthPresenter(AuthActivity activity) {
        this.activity = activity;
        this.mUserData = new ModelUserData();
        this.callBack = this;
    }

    ////////////       IPresenter       ////////////
    @Override
    public void onStartPresenter() {
    }
    @Override
    public void onDestroyView() {
        if (callBack != null) {
            callBack = null;
        }
        if (mUserData != null) {
            mUserData = null;
        }
        if (activity != null) {
            activity = null;
        }
    }

    public void setType(String statusSelect) {
        mUserData.setType(statusSelect);
    }
    public void setPhone(String phone) {
        mUserData.setPhone(phone);
    }
    public void setMail(String mail) {
        mUserData.setMail(mail);
    }
    public void setFullName(String FirstName, String LastName) {
        mUserData.setFirstName(FirstName);
        mUserData.setLastName(LastName);
    }
    public void setAvatar(String avatarSelect) {
        mUserData.setAvatar(avatarSelect);
    }
    public void setAvatarOriginal(String avatarSelect) {
        mUserData.setAvatarOriginal(avatarSelect);
    }
    public void setBirthday(String birthday) {
        mUserData.setBirthday(birthday);
    }
    public void setLogin(String login) {
        mUserData.setLogin(login);
    }
    public void setPassword(String password) {
        if (activity.getType().equals(SCHOOL)) mSchoolData.setPassword(password);
        else mUserData.setPassword(password);
    }
    public void setAccessCode(String accessCode) {
        if (activity.getType().equals(SCHOOL)) mSchoolData.setCode(accessCode);
        else mUserData.setCode(accessCode);
    }
    public void setSchoolData(ModelSchoolData mSchoolData) {
        this.mSchoolData = mSchoolData;
    }
    public ModelSchoolData getSchoolData() {
        return mSchoolData;
    }

    public void onRegister() {
        if (activity.getType().equals(SCHOOL)) onRegisterSchool();
        else onRegisterUser();
    }
    public void onRegisterUser() {
        JsonObject jsonData = new JsonObject();
        mUserData.setToken(FirebaseInstanceId.getInstance().getToken());

        String SOAP = SoapRequest(func_RegisterUser, new Gson().toJson(mUserData));
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        _Web.HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLReader(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.alert.onToast(answer.getMessage());
                        }
                    });
                }
                else {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            activity.alert.onToast("Ошибка, попробуйте еще раз.");
                        }
                    });
                }
            }
        });

    }
    public void onRegisterSchool() {
        String SOAP = SoapRequest(func_RegisterSchool, new Gson().toJson(mSchoolData));
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        _Web.HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLReader(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.alert.onToast(answer.getMessage());
                        }
                    });
                }
                else {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            activity.alert.onToast("Ошибка, попробуйте еще раз.");
                        }
                    });
                }
            }
        });
    }

    ////////////       ICallBack       /////////////
}
