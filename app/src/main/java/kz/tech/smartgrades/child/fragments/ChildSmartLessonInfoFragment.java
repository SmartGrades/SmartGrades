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
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.child.adapters.ChildLastMessagesListAdapter;
import kz.tech.smartgrades.mentor.adapters.GradesTableMonthAdapter;
import kz.tech.smartgrades.parent.model.ModelChatsLastMessages;
import kz.tech.smartgrades.parent.model.ModelGetLessonInfo;
import kz.tech.smartgrades.parent.model.ModelGradesSettings;
import kz.tech.smartgrades.parent.model.ModelLessonsWithSmartGrades;
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
import static kz.tech.smartgrades._Web.func_GetLessonInfo;

public class ChildSmartLessonInfoFragment extends Fragment implements View.OnClickListener,
        ChildLastMessagesListAdapter.OnItemClickListener{

    private ChildActivity activity;
    private ModelLessonsWithSmartGrades mLessonsWithSmartGrades;
    private ModelGradesSettings mRewardForGeades;
    private ModelGetLessonInfo mGetLessonInfo;

    private ImageView ivSchool;
    private ImageView ivOptions;
    private ImageView ivNav;

    private ConstraintLayout clSmartGradesOff;
    private ConstraintLayout clSmartGradesOn;
    private ConstraintLayout clMessages;
    private ConstraintLayout clGrades;

    private TextView tvAbsentIncomeCount;
    private TextView tvGrade2IncomeCount;
    private TextView tvGrade3IncomeCount;
    private TextView tvGrade4IncomeCount;
    private TextView tvGrade5IncomeCount;

    private CardView cvSetting;
    private CardView cvSetSmartGrades;

    private TextView tvLessonName;

    private RecyclerView rvLastMessages;
    private RecyclerView rvGradesTable;

    private CardView cvShowAllGrades;
    private TextView tvShowAllGrades;

    private GradesTableMonthAdapter gradesTableMonthAdapter;

    private ChildLastMessagesListAdapter childLastMessagesListAdapter;

    private ConstraintLayout clSetting;

    private CardView cvShowAllMessages;

    private boolean CHECK_TABLE = true;

    private TextView tvNoMessages;

    private ConstraintLayout container;
    private ProgressBar pgLoading;

    public ChildSmartLessonInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ChildActivity) getActivity();
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
        ivSchool = view.findViewById(R.id.ivSchool);
        ivSchool.setOnClickListener(this);
        ivOptions = view.findViewById(R.id.ivOptions);
        ivOptions.setOnClickListener(this);
        ivOptions.setVisibility(View.GONE);
        ivNav = view.findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);

        tvNoMessages = view.findViewById(R.id.tvNoMessages);

        clSmartGradesOff = view.findViewById(R.id.clSmartGradesOff);
        clSmartGradesOn = view.findViewById(R.id.clSmartGradesOn);
        clMessages = view.findViewById(R.id.clMessages);
        clGrades = view.findViewById(R.id.clGrades);

        tvAbsentIncomeCount = view.findViewById(R.id.tvAbsentIncomeCount);
        tvGrade2IncomeCount = view.findViewById(R.id.tvGrade2IncomeCount);
        tvGrade3IncomeCount = view.findViewById(R.id.tvGrade3IncomeCount);
        tvGrade4IncomeCount = view.findViewById(R.id.tvGrade4IncomeCount);
        tvGrade5IncomeCount = view.findViewById(R.id.tvGrade5IncomeCount);

        cvSetting = view.findViewById(R.id.cvSetting);
        cvSetting.setOnClickListener(this);
        cvSetSmartGrades = view.findViewById(R.id.cvSetSmartGrades);
        cvSetSmartGrades.setOnClickListener(this);

        tvLessonName = view.findViewById(R.id.tvLessonName);

        rvLastMessages = view.findViewById(R.id.rvLastMessages);
        rvGradesTable = view.findViewById(R.id.rvGradesTable);

        cvShowAllGrades = view.findViewById(R.id.cvShowAllGrades);
        cvShowAllGrades.setOnClickListener(this);
        tvShowAllGrades = view.findViewById(R.id.tvShowAllGrades);

        clSetting = view.findViewById(R.id.clSetting);
        clSetting.setVisibility(View.GONE);

        cvShowAllMessages = view.findViewById(R.id.cvShowAllMessages);
        cvShowAllMessages.setOnClickListener(this);
    }

    private void setLessonData() {
        tvLessonName.setText(mLessonsWithSmartGrades.getLessonName());
        if (mRewardForGeades != null) {
            clSmartGradesOff.setVisibility(View.GONE);
            clSmartGradesOn.setVisibility(View.VISIBLE);

            tvAbsentIncomeCount.setText(Integer.toString(mRewardForGeades.getRewardForN()));
            tvGrade2IncomeCount.setText(Integer.toString(mRewardForGeades.getRewardFor2()));
            tvGrade4IncomeCount.setText("+" + mRewardForGeades.getRewardFor4());
            tvGrade5IncomeCount.setText("+" + mRewardForGeades.getRewardFor5());

            if (mRewardForGeades.getRewardFor3() > 0) tvGrade3IncomeCount.setText("+" + mRewardForGeades.getRewardFor3());
            else tvGrade3IncomeCount.setText(Integer.toString(mRewardForGeades.getRewardFor3()));
            if (mRewardForGeades.getRewardFor4() > 0) tvGrade4IncomeCount.setText("+" + mRewardForGeades.getRewardFor4());
            else tvGrade4IncomeCount.setText(Integer.toString(mRewardForGeades.getRewardFor4()));
        } else {
            clSmartGradesOff.setVisibility(View.VISIBLE);
            clSmartGradesOn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNav:
                onBack();
                break;
            case R.id.ivSchool:
                onSchoolClick();
                break;
            case R.id.cvSetting:
            case R.id.cvSetSmartGrades:
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
            case R.id.cvShowAllGrades:
                showAllGradeTables();
                break;
            case R.id.cvShowAllMessages:
                openChat();
                break;
        }
    }

    private void openChat() {
        activity.onOpenChat(mLessonsWithSmartGrades.getLessonName(), mLessonsWithSmartGrades.getChildLessonId());
    }

    private void onBack() {
        activity.onBackPressed();
    }

    private void onSchoolClick() {

    }

    public void setLessonsWithSmartGradesData(ModelLessonsWithSmartGrades mLessonsWithSmartGrades) {
        this.mLessonsWithSmartGrades = mLessonsWithSmartGrades;
        mRewardForGeades = mLessonsWithSmartGrades.getGradesSettings();
        setLessonData();
        loadLessonInfo();
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
        childLastMessagesListAdapter.setOnItemClickListener(this);
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
        json.addProperty(F.ChildLessonId, mLessonsWithSmartGrades.getChildLessonId());

        String SOAP = SoapRequest(func_GetLessonInfo, json.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ModelGetLessonInfo modelGetLessonInfo =
                                    new Gson().fromJson(result, new TypeToken<ModelGetLessonInfo>(){
                                    }.getType());
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

    @Override
    public void onClick(ModelChatsLastMessages messagesList) {
        //activity.alert.onToast("111");
        //activity.onOpenChat(mLessonsWithSmartGrades.getLessonName(), mLessonsWithSmartGrades.getLessonId());
    }
}