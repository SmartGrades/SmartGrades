package kz.tech.smartgrades.parent_add_child;

import android.content.Intent;

import com.google.gson.Gson;

import java.io.IOException;

import kz.tech.smartgrades.S;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent_add_child.mvp.ICallBack;
import kz.tech.smartgrades.parent_add_child.mvp.IPresenter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_ParentRegisterChild;

public class ParentAddChildPresenter implements IPresenter, ICallBack {

    private ParentAddChildActivity activity;
    private ModelUser userData;
    private ICallBack callBack;

    public ParentAddChildPresenter(ParentAddChildActivity activity) {
        this.activity = activity;
        this.userData = new ModelUser();
        this.callBack = this;
    }

    public void setChildFullName(String firstName, String lastName) {
        userData.setFirstName(firstName);
        userData.setLastName(lastName);
    }
    public void setChildDateBirthday(String daeBirthday) {
        userData.setBirthday(daeBirthday);
    }
    public void setAddress(String address) {
        userData.setAddress(address);
    }
    public void setChildLogin(String login) {
        userData.setLogin(login);
    }
    public void setChildAvatar(String avatar) {
        userData.setAvatar(avatar);
    }
    public void setChildAvatarOriginal(String avatarSelectChild){
        userData.setAvatarOriginal(avatarSelectChild);
    }
    public void setChildAccessCode(String accessCode) {
        userData.setCode(accessCode);
    }
    public void setChildPassword(String password) {
        userData.setPassword(password);
    }

    public void onRegisterChild(String parentId) {
        userData.setParentId(parentId);
        userData.setType(S.CHILD);

        String SOAP = SoapRequest(func_ParentRegisterChild, new Gson().toJson(userData));
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        _Web.HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            activity.alert.onToast(answer.getMessage());
                            activity.startActivity(new Intent(activity, ParentActivity.class));
                            activity.finish();
                        }
                    });
                } else {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            activity.alert.onToast("Ошибка, попробуйте еще раз.");
                        }
                    });
                }
            }
        });
    }

    public void setChildPhone(String phone) {
        userData.setPhone(phone);
    }

    public void setChildMail(String mail) {
        userData.setMail(mail);
    }
}