package kz.tech.smartgrades.parent_add_mentor_or_sponsor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.parent.model.ModelFamilyGroup;
import kz.tech.smartgrades.parent.model.ModelMentorSponsorRoom;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.mvp.ICallBack;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.mvp.IModel;
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
import static kz.tech.smartgrades._Web.func_GetMentorOrSponsorList;

public class ParentAddMentorModel implements IModel {

    private List<ModelFamilyGroup> list;
    private ParentAddSponsorOrMentorActivity activity;
    private String PARENT_ID;

    public List<ModelFamilyGroup> getList() {
        return list;
    }

    public ParentAddMentorModel(ParentAddSponsorOrMentorActivity activity) {
        this.activity = activity;
        this.list = new ArrayList<>();
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    @Override
    public void LoadMentorOrSponsorList(ICallBack callBack, String type) {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, PARENT_ID);
        jsonData.addProperty(F.Type, type);

        String SOAP = SoapRequest(func_GetMentorOrSponsorList, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLToJson(response.body().string());
                    if (result.equals("0")) { }
                    else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Type founderListType = new TypeToken<ArrayList<ModelMentorSponsorRoom>>(){}.getType();
                                ArrayList<ModelMentorSponsorRoom> modelUserLists = new Gson().fromJson(result, founderListType);
                                callBack.onResultMentorSponsorList(modelUserLists);
                            }
                        });
                    }
                }
            }
        });
    }
}