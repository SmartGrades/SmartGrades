package kz.tech.smartgrades.child;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.child.mvp.ICallBack;
import kz.tech.smartgrades.child.mvp.IModel;
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
import static kz.tech.smartgrades._Web.func_GetChildData;

public class ChildModel implements IModel {

    private ChildActivity activity;
    private String CHILD_ID;
    private ModelChildData modelChildData;

    public ChildModel(ChildActivity activity) {
        this.activity = activity;
        CHILD_ID = activity.login.loadUserDate(LoginKey.ID);
        modelChildData = new ModelChildData();
    }

    public void onLoadChildWardCoins(ICallBack callBack) {
        /*ArrayList<ModelChildWardCoin> ChildWardCoinList = new ArrayList<>();
        String childId = activity.login.loadUserDate(LoginKey.ID);

        DatabaseReference dbrCoins = FirebaseDatabase.getInstance().getReference("Coins");
        dbrCoins.child(childId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = "0";
                if (dataSnapshot.exists()) value = dataSnapshot.child("coins").getValue().toString();
                ModelChildWardCoin valueList = new ModelChildWardCoin();
                valueList.setType("coins");
                valueList.setValue(value);
                ChildWardCoinList.add(valueList);

                DatabaseReference dbrPiggy = FirebaseDatabase.getInstance().getReference("Piggy");
                dbrPiggy.child(childId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value = "0";
                        if (dataSnapshot.exists()) value = dataSnapshot.child("value").getValue().toString();
                        ModelChildWardCoin valueList = new ModelChildWardCoin();
                        valueList.setType("piggy");
                        valueList.setValue(value);
                        ChildWardCoinList.add(valueList);

                        DatabaseReference dbrDepozit = FirebaseDatabase.getInstance().getReference("Depozit");
                        dbrDepozit.child(childId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    Iterator i = dataSnapshot.getChildren().iterator();
                                    while (i.hasNext()) {
                                        String key = (String) ((DataSnapshot) i.next()).getKey();
                                        ModelChildWardCoin valueList = dataSnapshot.child(key).getValue(ModelChildWardCoin.class);
                                        valueList.setType("depozit");
                                        ChildWardCoinList.add(valueList);
                                    }
                                }

                                DatabaseReference dbrBank = FirebaseDatabase.getInstance().getReference("Bank");
                                dbrBank.child(childId).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String value = "0";
                                        if (dataSnapshot.exists()) value = dataSnapshot.child("value").getValue().toString();
                                        ModelChildWardCoin valueList = new ModelChildWardCoin();
                                        valueList.setType("bank");
                                        valueList.setValue(value);
                                        ChildWardCoinList.add(valueList);

                                        DatabaseReference drChats = FirebaseDatabase.getInstance().getReference("Chats");
                                        drChats.child(childId).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                ArrayList<ModelDefaultChat> modelDefaultChat = new ArrayList<>();
                                                Iterator I = dataSnapshot.getChildren().iterator();
                                                while (I.hasNext()){
                                                    String msgId = ((DataSnapshot) I.next()).getKey();
                                                    ModelDefaultChat detail = dataSnapshot.child(msgId).getValue(ModelDefaultChat.class);
                                                    if (detail.getMessageType().equals("temporary_coins")) modelDefaultChat.add(detail);
                                                }
                                                DatabaseReference dbrUsers = FirebaseDatabase.getInstance().getReference("Users");
                                                dbrUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        //ArrayList<ModelUserList> UserList = new ArrayList<>();
                                                        Iterator i = dataSnapshot.getChildren().iterator();
                                                        while (i.hasNext()) {
                                                            String key = (String) ((DataSnapshot) i.next()).getKey();
                                                            Iterator I = dataSnapshot.child(key).getChildren().iterator();
                                                            while (I.hasNext()) {
                                                                String id = (String) ((DataSnapshot) I.next()).getKey();
                                                                for(int j = 0; j < modelDefaultChat.size(); j++){
                                                                    if (id.equals(modelDefaultChat.get(j).getSourceId())){
                                                                        kz.tech.smartgrades.mentor.models.ModelUserList valueUserList = dataSnapshot.child(key).child(id).getValue(kz.tech.smartgrades.mentor.models.ModelUserList.class);
                                                                        modelDefaultChat.get(j).setSourceAvatar(valueUserList.getAvatar());
                                                                        modelDefaultChat.get(j).setSourceFirstName(valueUserList.getFirstName());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        callBack.onLoadChildWardCoins(ChildWardCoinList, modelDefaultChat);
                                                    }
                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                                                });
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) { }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                                });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) { }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });*/
    }

    public void onLoadData(ICallBack callBack) {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, CHILD_ID);

        String SOAP = SoapRequest(func_GetChildData, jsonData.toString());
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
                                callBack.ResultLoadChildData(new Gson().fromJson(result, ModelChildData.class));
                            }
                        }
                    });
                }
            }
        });
    }

    public void onUpdateCartList(ICallBack callBack) {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, CHILD_ID);

        String SOAP = SoapRequest(func_GetChildData, jsonData.toString());
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
                                modelChildData = new Gson().fromJson(result, ModelChildData.class);
                                callBack.ResultLoadChildData(modelChildData);
                            }
                        }
                    });
                }
            }
        });
    }
}