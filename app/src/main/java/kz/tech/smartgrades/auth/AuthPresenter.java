package kz.tech.smartgrades.auth;

import com.google.gson.Gson;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.auth.models.ModelRegisterData;
import kz.tech.smartgrades.auth.mvp.ICallBack;
import kz.tech.smartgrades.auth.mvp.IPresenter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_RegisterUser;


public class AuthPresenter implements IPresenter, ICallBack {

    private AuthActivity activity;
    private ModelRegisterData mRegisterData;
    private ICallBack callBack;

    public AuthPresenter(AuthActivity activity) {
        this.activity = activity;
        mRegisterData = new ModelRegisterData();
        callBack = this;
    }

    ////////////       IPresenter       ////////////
    @Override
    public void onStartPresenter() {
    }
    @Override
    public void onDestroyView() {
        if (callBack != null) callBack = null;
        if (mRegisterData != null) mRegisterData = null;
        if (activity != null) activity = null;
    }

    // SETTER / GETTER //
    public void setType(String statusSelect) {
        mRegisterData.setType(statusSelect);
    }
    public String getType() {
        return mRegisterData.getType();
    }
    public void setPhoneOrMail(String mail) {
        mRegisterData.setPhoneOrMail(mail);
    }
    public void setBirthday(String mail) {
        mRegisterData.setBirthday(mail);
    }
    public void setLogin(String login) {
        mRegisterData.setLogin(login);
    }
    public void setPassword(String password) {
        mRegisterData.setPassword(password);
    }
    public void setSchoolName(String schoolName) {
        mRegisterData.setSchoolName(schoolName);
    }
    public void setCountry(String country) {
        mRegisterData.setCityId(country);
    }
    //

    public void onRegister() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            byte[] bytes = CryptoKey.createKeyPair().getEncoded();
//            mRegisterData.setPublicKey(Base64.getEncoder().encodeToString(bytes));
//        }

        String SOAP = SoapRequest(func_RegisterUser, new Gson().toJson(mRegisterData));
        RequestBody body = RequestBody.create(SOAP, ContentType_XML);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(() -> {
                        if (answer.isSuccess()) {
                            activity.onSaveRegisterData(mRegisterData);
                            activity.onSetToolbarVisible(false);
                            activity.onNextFragment();
                        }
                        else activity.alert.onToast(answer.getMessage());
                    });
                }
                else {
                    activity.runOnUiThread(() -> activity.alert.onToast(activity.getResources().getString(R.string.try_again)));
                }
            }
        });
    }
}
