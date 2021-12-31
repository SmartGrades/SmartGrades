package kz.tech.smartgrades.parent.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentChatAdapter;
import kz.tech.smartgrades.parent.model.ModelChatMessages;
import kz.tech.smartgrades.root.login.LoginKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_GetMessages;

public class ParentChatFragment extends Fragment implements View.OnClickListener,
        ParentChatAdapter.OnItemCLickListener,
        SwipyRefreshLayout.OnRefreshListener{

    private ParentActivity activity;
    private String PARENT_ID;
    private String lessonId;

    private ImageView ivBack;
    private TextView tvChatLabel;
    private RecyclerView rvMessages;
    private FrameLayout flMessageEnter;

    private ParentChatAdapter parentChatAdapter;

    private SwipyRefreshLayout swipeContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view){
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvChatLabel = view.findViewById(R.id.tvChatLabel);
        rvMessages = view.findViewById(R.id.rvMessages);
        flMessageEnter = view.findViewById(R.id.flMessageEnter);
        flMessageEnter.setVisibility(View.GONE);

        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
    private void onLoadChatMessages(String lessonId) {
        JsonObject json = new JsonObject();
        json.addProperty(F.ChildLessonId, lessonId);
        json.addProperty(F.UserId, PARENT_ID);

        String SOAP = SoapRequest(func_GetMessages, json.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {}

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<ModelChatMessages> modelChatMessagesList =
                                    new Gson().fromJson(result, new TypeToken<ArrayList<ModelChatMessages>>(){
                                    }.getType());
                            if (!listIsNullOrEmpty(modelChatMessagesList)) {
                                setMessages(modelChatMessagesList);
                            }
                        }
                    });
                }
            }
        });
    }

    private void setMessages(ArrayList<ModelChatMessages> modelChatMessagesList) {
        rvMessages.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        parentChatAdapter = new ParentChatAdapter(activity);
        parentChatAdapter.setOnItemClickListener(this);
        rvMessages.setAdapter(parentChatAdapter);
        parentChatAdapter.updateData(modelChatMessagesList);
        rvMessages.scrollToPosition(parentChatAdapter.getItemCount() - 1);
    }

    public void setData(String lessonName, String lessonId) {
        this.lessonId = lessonId;
        tvChatLabel.setText(lessonName);
        onLoadChatMessages(lessonId);
    }

    private void onBack(){
        activity.onBackPressed();
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
                break;
        }
    }

    @Override
    public void onItemClick(ModelChatMessages m) {

    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeContainer.setRefreshing(false);
                onLoadChatMessages(lessonId);
            }
        }, 300);
    }
}