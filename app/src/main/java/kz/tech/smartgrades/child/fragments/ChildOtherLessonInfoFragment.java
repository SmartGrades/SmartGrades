package kz.tech.smartgrades.child.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.child.adapters.ChildLastMessagesListAdapter;
import kz.tech.smartgrades.mentor.adapters.GradesTableMonthAdapter;
import kz.tech.smartgrades.parent.model.ModelChatsLastMessages;
import kz.tech.smartgrades.parent.model.ModelGetLessonInfo;
import kz.tech.smartgrades.parent.model.ModelLessonsWithOutSmartGrades;
import kz.tech.smartgrades.root.login.LoginKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_ChildRequests;
import static kz.tech.smartgrades._Web.func_GetLessonInfo;

public class ChildOtherLessonInfoFragment extends Fragment implements View.OnClickListener {

    private ChildActivity activity;
    private ModelLessonsWithOutSmartGrades mLessonsWithOutSmartGrades;
    private ModelGetLessonInfo mGetLessonInfo;
    private String CHILD_ID;

    private boolean CHECK_TABLE = true;

    private ImageView ivNav;
    private TextView tvLessonName;

    private ConstraintLayout clGrades;
    private ConstraintLayout clMessages;
    private ConstraintLayout clAskSmartGradesOn;

    private RecyclerView rvLastMessages;
    private ChildLastMessagesListAdapter childLastMessagesListAdapter;
    private RecyclerView rvGradesTable;
    private GradesTableMonthAdapter gradesTableMonthAdapter;

    private CardView cvShowAllGrades;
    private TextView tvShowAllGrades;

    private ConstraintLayout clSmartGradesOff;

    private ImageView ivOptions;

    private CardView cvShowAllMessages;
    private CardView cvAskOnSmartGrades;

    private ConstraintLayout container;
    private ProgressBar pgLoading;

    private TextView tvNoMessages;
    public ChildOtherLessonInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ChildActivity) getActivity();
        CHILD_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lesson_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    private void initUI(View view) {
        container = view.findViewById(R.id.container);
        pgLoading = view.findViewById(R.id.pgLoading);
        ivNav = view.findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        tvLessonName = view.findViewById(R.id.tvLessonName);
        clGrades = view.findViewById(R.id.clGrades);
        clMessages = view.findViewById(R.id.clMessages);
        clAskSmartGradesOn = view.findViewById(R.id.clAskSmartGradesOn);
        clAskSmartGradesOn.setVisibility(View.VISIBLE);
        rvLastMessages = view.findViewById(R.id.rvLastMessages);
        rvGradesTable = view.findViewById(R.id.rvGradesTable);

        tvNoMessages = view.findViewById(R.id.tvNoMessages);

        cvShowAllGrades = view.findViewById(R.id.cvShowAllGrades);
        cvShowAllGrades.setOnClickListener(this);
        tvShowAllGrades = view.findViewById(R.id.tvShowAllGrades);

        clSmartGradesOff = view.findViewById(R.id.clSmartGradesOff);
        ivOptions = view.findViewById(R.id.ivOptions);
        ivOptions.setOnClickListener(this);
        ivOptions.setVisibility(View.GONE);

        cvShowAllMessages = view.findViewById(R.id.cvShowAllMessages);
        cvShowAllMessages.setOnClickListener(this);
        cvAskOnSmartGrades = view.findViewById(R.id.cvAskOnSmartGrades);
        cvAskOnSmartGrades.setOnClickListener(this);
    }

    private void setLessonData() {
        tvLessonName.setText(mLessonsWithOutSmartGrades.getLessonName());
    }

    private void openChat() {
        activity.onOpenChat(mLessonsWithOutSmartGrades.getLessonName(), mLessonsWithOutSmartGrades.getChildLessonId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNav:
                onBack();
            case R.id.cvShowAllGrades:
                if (CHECK_TABLE) {
                    showAllGradeTables();
                    tvShowAllGrades.setText(activity.getResources().getString(R.string.hide));
                    CHECK_TABLE = false;
                } else {
                    setGradesTable();
                    tvShowAllGrades.setText(activity.getResources().getString(R.string.show_all));
                    CHECK_TABLE = true;
                }
                break;
            case R.id.cvShowAllMessages:
                openChat();
                break;
            case R.id.cvAskOnSmartGrades:
                onAsk();
                break;
        }
    }

    private void onAsk() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.ChildId, CHILD_ID);
        jsonData.addProperty(F.FamilyGroupId, activity.getMChildData().getFamilyGroupId());
        jsonData.addProperty(F.Type, "SmartGrades");
        jsonData.addProperty(F.ChildLessonId, mLessonsWithOutSmartGrades.getChildLessonId());

        String SOAP = SoapRequest(func_ChildRequests, jsonData.toString());
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
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            if(!result.equals("null")){
                                ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                activity.alert.onToast(answer.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }

    private void onBack() {
        activity.onBackPressed();
    }

    public void setLessonsWithOutSmartGradesData(ModelLessonsWithOutSmartGrades mLessonsWithOutSmartGrades) {
        this.mLessonsWithOutSmartGrades = mLessonsWithOutSmartGrades;
        setLessonData();
        loadLessonInfo();
        clSmartGradesOff.setVisibility(View.GONE);
    }

    private void setMessages() {
        ArrayList<ModelChatsLastMessages> chats = new ArrayList<>();
        for (ModelChatsLastMessages m : mGetLessonInfo.getChatsLastMessages()) {
            if (!stringIsNullOrEmpty(m.getLastMessage())) {
                chats.add(m);
            }
        }

        if (listIsNullOrEmpty(chats)) {
            tvNoMessages.setVisibility(View.VISIBLE);
            cvShowAllMessages.setVisibility(View.GONE);
            rvLastMessages.setVisibility(View.GONE);
            return;
        }

        rvLastMessages.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        childLastMessagesListAdapter = new ChildLastMessagesListAdapter(activity);
        //parentLastMessagesListAdapter.setOnItemClickListener(this);
        rvLastMessages.setAdapter(childLastMessagesListAdapter);
        childLastMessagesListAdapter.updateData(chats);
    }

    private void setGradesTable() {
        rvGradesTable.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true));
        gradesTableMonthAdapter = new GradesTableMonthAdapter(activity);
        //gradesTableMonthAdapter.setOnItemClickListener(this);
        rvGradesTable.setAdapter(gradesTableMonthAdapter);
        if (!listIsNullOrEmpty(mGetLessonInfo.getGradesTable())) {
            gradesTableMonthAdapter.updateData(new ArrayList(mGetLessonInfo.getGradesTable().subList(mGetLessonInfo.getGradesTable().size() - 1, mGetLessonInfo.getGradesTable().size())));
            if (mGetLessonInfo.getGradesTable().size() < 2) {
                cvShowAllGrades.setVisibility(View.GONE);
            }
        }
    }

    private void showAllGradeTables() {
        rvGradesTable.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true));
        gradesTableMonthAdapter = new GradesTableMonthAdapter(activity);
        //gradesTableMonthAdapter.setOnItemClickListener(this);
        rvGradesTable.setAdapter(gradesTableMonthAdapter);
        gradesTableMonthAdapter.updateData(mGetLessonInfo.getGradesTable());
    }

    private void loadLessonInfo() {
        container.setVisibility(View.GONE);
        pgLoading.setVisibility(View.VISIBLE);
        JsonObject json = new JsonObject();
        json.addProperty(F.ChildLessonId, mLessonsWithOutSmartGrades.getChildLessonId());

        String SOAP = SoapRequest(func_GetLessonInfo, json.toString());
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
                            ModelGetLessonInfo modelGetLessonInfo =
                                    new Gson().fromJson(result, ModelGetLessonInfo.class);
                            if (modelGetLessonInfo != null) {
                                mGetLessonInfo = modelGetLessonInfo;
                                container.setVisibility(View.VISIBLE);
                                pgLoading.setVisibility(View.GONE);
                                if (!listIsNullOrEmpty(modelGetLessonInfo.getGradesTable())) {
                                    clGrades.setVisibility(View.VISIBLE);
                                    setGradesTable();
                                }
                                if (!listIsNullOrEmpty(mGetLessonInfo.getChatsLastMessages())) {
                                    clMessages.setVisibility(View.VISIBLE);
                                    setMessages();
                                } else {
                                    tvNoMessages.setVisibility(View.VISIBLE);
                                    cvShowAllMessages.setVisibility(View.GONE);
                                    rvLastMessages.setVisibility(View.GONE);
                                }
                            }
                        }
                    });
                }
            }
        });
    }
}