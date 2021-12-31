package kz.tech.smartgrades.parent.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.mentor.adapters.GradesTableMonthAdapter;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentLastMessagesListAdapter;
import kz.tech.smartgrades.parent.bottom_sheet_dialogs.BSDOtherLessonToSmart;
import kz.tech.smartgrades.parent.model.ModelChatsLastMessages;
import kz.tech.smartgrades.parent.model.ModelGetLessonInfo;
import kz.tech.smartgrades.parent.model.ModelGradesSettings;
import kz.tech.smartgrades.parent.model.ModelLessonsWithOutSmartGrades;
import kz.tech.smartgrades.parent.model.ModelLessonsWithSmartGrades;
import kz.tech.smartgrades.parent.model.ModelMentorList;
import kz.tech.smartgrades.parent.model.ModelSetLessonSmartGrades;
import kz.tech.smartgrades.parent.popup.PWOption;
import kz.tech.smartgrades.parent.popup.PWRemoveLessonProve;
import kz.tech.smartgrades.parent.popup.PWRemoveProve;
import kz.tech.smartgrades.parent.popup.PWSchoolInfo;
import kz.tech.smartgrades.root.dialogs.DefaultDialog;
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
import static kz.tech.smartgrades._Web.func_DisableSmartGradesForLesson;
import static kz.tech.smartgrades._Web.func_GetLessonInfo;
import static kz.tech.smartgrades._Web.func_SetLessonSmartGrades;

public class ParentChildLessonInfoFragment extends Fragment implements View.OnClickListener, DefaultDialog.OnClickListener {

    private ParentActivity activity;
    private ModelLessonsWithOutSmartGrades mLessonsWithOutSmartGrades;
    private ModelLessonsWithSmartGrades mLessonsWithSmartGrades;
    private ModelGetLessonInfo mGetLessonInfo;
    private String CHILD_LESSON_ID;
    private boolean SMART_GRADES_ON = false;
    private ArrayList<ModelMentorList> mentorList;
    private ModelChildData mChild;

    private boolean CHECK_TABLE = true;

    private ImageView ivNav;
    private ImageView ivSchool;
    private TextView tvLessonName;

    private ConstraintLayout clGrades;
    private ConstraintLayout clMessages;

    private RecyclerView rvLastMessages;
    private ParentLastMessagesListAdapter parentLastMessagesListAdapter;
    private RecyclerView rvGradesTable;
    private GradesTableMonthAdapter gradesTableMonthAdapter;

    private CardView cvShowAllGrades;
    private TextView tvShowAllGrades;

    private CircleImageView civAvatar;

    private ConstraintLayout clSmartGradesOff;
    private CardView cvSetting;
    private CardView cvSetSmartGrades;

    private ConstraintLayout clSmartGradesOn;

    private ImageView ivOptions;

    private  TextView tvNoMessages;
    private  CardView cvShowAllMessages;

    private ModelSetLessonSmartGrades mRewardForGrades;
    private ModelGradesSettings mGradesSettings;

    private TextView tvAbsentIncomeCount;
    private TextView tvGrade2IncomeCount;
    private TextView tvGrade3IncomeCount;
    private TextView tvGrade4IncomeCount;
    private TextView tvGrade5IncomeCount;

    private ConstraintLayout container;
    private ProgressBar pgLoading;

    public ParentChildLessonInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
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
        rvLastMessages = view.findViewById(R.id.rvLastMessages);
        rvGradesTable = view.findViewById(R.id.rvGradesTable);

        ivSchool = view.findViewById(R.id.ivSchool);
        ivSchool.setOnClickListener(this);

        cvShowAllGrades = view.findViewById(R.id.cvShowAllGrades);
        cvShowAllGrades.setOnClickListener(this);
        tvShowAllGrades = view.findViewById(R.id.tvShowAllGrades);

        civAvatar = view.findViewById(R.id.civAvatar);

        clSmartGradesOff = view.findViewById(R.id.clSmartGradesOff);
        clSmartGradesOn = view.findViewById(R.id.clSmartGradesOn);
        cvSetSmartGrades = view.findViewById(R.id.cvSetSmartGrades);
        cvSetSmartGrades.setOnClickListener(this);
        cvSetting = view.findViewById(R.id.cvSetting);
        cvSetting.setOnClickListener(this);

        tvAbsentIncomeCount = view.findViewById(R.id.tvAbsentIncomeCount);
        tvGrade2IncomeCount = view.findViewById(R.id.tvGrade2IncomeCount);
        tvGrade3IncomeCount = view.findViewById(R.id.tvGrade3IncomeCount);
        tvGrade4IncomeCount = view.findViewById(R.id.tvGrade4IncomeCount);
        tvGrade5IncomeCount = view.findViewById(R.id.tvGrade5IncomeCount);

        ivOptions = view.findViewById(R.id.ivOptions);
        ivOptions.setOnClickListener(this);

        tvNoMessages = view.findViewById(R.id.tvNoMessages);
        cvShowAllMessages = view.findViewById(R.id.cvShowAllMessages);
        cvShowAllMessages.setOnClickListener(this);
    }

    private void setLessonData() {
        if (civAvatar != null && !stringIsNullOrEmpty(mChild.getAvatar())) {
            civAvatar.setVisibility(View.VISIBLE);
            Picasso.get().load(mChild.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerCrop().into(civAvatar);
        }
        if (mLessonsWithOutSmartGrades != null && tvLessonName != null) {
            if (!stringIsNullOrEmpty(mLessonsWithOutSmartGrades.getLessonName()))
                tvLessonName.setText(mLessonsWithOutSmartGrades.getLessonName());
            CHILD_LESSON_ID = mLessonsWithOutSmartGrades.getChildLessonId();
            boolean b = mLessonsWithOutSmartGrades.isSchoolLesson();
            if (!stringIsNullOrEmpty(mLessonsWithOutSmartGrades.getSchoolId())) {
                ivOptions.setVisibility(View.GONE);
                ivSchool.setVisibility(View.VISIBLE);
            }
        }
        else if (mLessonsWithSmartGrades != null && tvLessonName != null) {
            tvLessonName.setText(mLessonsWithSmartGrades.getLessonName());
            CHILD_LESSON_ID = mLessonsWithSmartGrades.getChildLessonId();
            if (!stringIsNullOrEmpty(mLessonsWithSmartGrades.getSchoolId())) {
                ivOptions.setVisibility(View.GONE);
                ivSchool.setVisibility(View.VISIBLE);
            }
        }
        if (mGradesSettings != null) {
            clSmartGradesOff.setVisibility(View.GONE);
            clSmartGradesOn.setVisibility(View.VISIBLE);

            tvAbsentIncomeCount.setText(Integer.toString(mGradesSettings.getRewardForN()));
            tvGrade2IncomeCount.setText(Integer.toString(mGradesSettings.getRewardFor2()));
            tvGrade5IncomeCount.setText("+" + mGradesSettings.getRewardFor5());

            if (mGradesSettings.getRewardFor3() > 0) tvGrade3IncomeCount.setText("+" + mGradesSettings.getRewardFor3());
            else tvGrade3IncomeCount.setText(Integer.toString(mGradesSettings.getRewardFor3()));
            if (mGradesSettings.getRewardFor4() > 0) tvGrade4IncomeCount.setText("+" + mGradesSettings.getRewardFor4());
            else tvGrade4IncomeCount.setText(Integer.toString(mGradesSettings.getRewardFor4()));
        } else {
            if (clSmartGradesOff != null && clSmartGradesOn != null) {
                clSmartGradesOff.setVisibility(View.VISIBLE);
                clSmartGradesOn.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNav:
                onBack();
                break;
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
            case R.id.ivOptions:
                onOptionsClick();
                break;
            case R.id.ivSchool:
                onSchoolClick();
                break;
            case R.id.cvSetting:
            case R.id.cvSetSmartGrades:
                if (listIsNullOrEmpty(activity.getMParentData().getPrivateAccount().getCards()) && !SMART_GRADES_ON) {
                    DefaultDialog dialog = new DefaultDialog(activity);
                    dialog.setCancel(activity.getResources().getColor(R.color.gray_average_dark), getString(R.string.cancel));
                    dialog.setExit(activity.getResources().getColor(R.color.blue_sea), getString(R.string.bind));
                    dialog.setMessage(getString(R.string.To_create_an_item_with_smart_ratings_link_card));
                    dialog.setOnClickListener(this);
                    dialog.show();
                    break;
                }
                openSmartGradeSettings();
                break;
            case R.id.cvShowAllMessages:
                openChat();
                break;
        }
    }


    private void loadLessonInfo() {
        container.setVisibility(View.GONE);
        pgLoading.setVisibility(View.VISIBLE);
        JsonObject json = new JsonObject();
        json.addProperty(F.ChildLessonId, CHILD_LESSON_ID);

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
                    activity.runOnUiThread(() -> {
                        mGetLessonInfo = new Gson().fromJson(result, ModelGetLessonInfo.class);
                        if (mGetLessonInfo != null) {
                            container.setVisibility(View.VISIBLE);
                            pgLoading.setVisibility(View.GONE);
                            if (!listIsNullOrEmpty(mGetLessonInfo.getGradesTable())) {
                                clGrades.setVisibility(View.VISIBLE);
                                setGradesTable();
                            }
                            if (!listIsNullOrEmpty(mGetLessonInfo.getChatsLastMessages())) {
                                setMessages();
                            } else {
                                tvNoMessages.setVisibility(View.VISIBLE);
                                cvShowAllMessages.setVisibility(View.GONE);
                                rvLastMessages.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
    }



    private void openChat() {
        if (mLessonsWithOutSmartGrades != null) {
            activity.onOpenChat(mLessonsWithOutSmartGrades.getLessonName(), mLessonsWithOutSmartGrades.getChildLessonId());
        } else if (mLessonsWithSmartGrades != null) {
            activity.onOpenChat(mLessonsWithSmartGrades.getLessonName(), mLessonsWithSmartGrades.getChildLessonId());
        }
    }
    public void openProveWindow(ModelMentorList mMentor) {
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        PWRemoveProve popupWindow = new PWRemoveProve(width, height, activity, mMentor, CHILD_LESSON_ID);
        popupWindow.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(20);
        }
        popupWindow.showAtLocation(ivOptions, Gravity.CENTER, 0, 0);
    }
    private void openSmartGradeSettings() {
        mRewardForGrades = new ModelSetLessonSmartGrades();
        BSDOtherLessonToSmart dialog = new BSDOtherLessonToSmart(activity);
        try {
            dialog.setGrades(mLessonsWithSmartGrades.getGradesSettings());
        } catch (Exception ignored) {}
        dialog.show();
        if (SMART_GRADES_ON) dialog.setVisibleDisable();
        dialog.setOnItemClickListener(new BSDOtherLessonToSmart.OnItemClickListener() {

            @Override
            public void onClick(ModelSetLessonSmartGrades m) {
                dialog.dismiss();
                mRewardForGrades = m;
                onSetSmartGrades(mRewardForGrades);
                ModelGradesSettings mSetting = new ModelGradesSettings();
                if (!stringIsNullOrEmpty(m.getRewardForN())) mSetting.setRewardForN(Integer.parseInt(m.getRewardForN()));
                else mSetting.setRewardForN(0);
                if (!stringIsNullOrEmpty(m.getRewardFor2())) mSetting.setRewardFor2(Integer.parseInt(m.getRewardFor2()));
                else mSetting.setRewardFor2(0);
                if (!stringIsNullOrEmpty(m.getRewardFor3())) mSetting.setRewardFor3(Integer.parseInt(m.getRewardFor3()));
                else mSetting.setRewardFor3(0);
                if (!stringIsNullOrEmpty(m.getRewardFor4())) mSetting.setRewardFor4(Integer.parseInt(m.getRewardFor4()));
                else mSetting.setRewardFor4(0);
                if (!stringIsNullOrEmpty(m.getRewardFor5())) mSetting.setRewardFor5(Integer.parseInt(m.getRewardFor5()));
                else mSetting.setRewardFor5(0);
                try {
                    mLessonsWithSmartGrades.setGradesSettings(mSetting);
                } catch (Exception ignored) {}
            }

            @Override
            public void onCancel() {
                dialog.dismiss();
            }
        });
        dialog.setOnDisableClickListener(new BSDOtherLessonToSmart.OnDisableClickListener() {
            @Override
            public void onDisable() {
                dialog.dismiss();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(F.ChildLessonId, CHILD_LESSON_ID);
                String SOAP = SoapRequest(func_DisableSmartGradesForLesson, jsonObject.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = XMLToJson(response.body().string());
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    activity.alert.onToast(answer.getMessage());
                                    if (answer.isSuccess()) {
                                        SMART_GRADES_ON = false;
                                        activity.updatePresenter();
                                        clSmartGradesOn.setVisibility(View.GONE);
                                        clSmartGradesOff.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void onSetSmartGrades(ModelSetLessonSmartGrades mRewardForGrades) {
        mRewardForGrades.setChildLessonId(CHILD_LESSON_ID);
        String SOAP = SoapRequest(func_SetLessonSmartGrades, new Gson().toJson(mRewardForGrades));
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.alert.onToast(answer.getMessage());
                            if (answer.isSuccess()) {
                                SMART_GRADES_ON = true;
                                activity.updatePresenter();
                                clSmartGradesOff.setVisibility(View.GONE);
                                clSmartGradesOn.setVisibility(View.VISIBLE);

                                if (!stringIsNullOrEmpty(mRewardForGrades.getRewardForN())) {
                                    tvAbsentIncomeCount.setText(mRewardForGrades.getRewardForN());
                                } else tvAbsentIncomeCount.setText("0");
                                if (!stringIsNullOrEmpty(mRewardForGrades.getRewardFor2())) {
                                    tvGrade2IncomeCount.setText(mRewardForGrades.getRewardFor2());
                                } else tvGrade2IncomeCount.setText("0");
                                if (!stringIsNullOrEmpty(mRewardForGrades.getRewardFor3())) {
                                    tvGrade3IncomeCount.setText(mRewardForGrades.getRewardFor3());
                                } else tvGrade3IncomeCount.setText("0");
                                if (!stringIsNullOrEmpty(mRewardForGrades.getRewardFor5())) {
                                    tvGrade5IncomeCount.setText("+" + mRewardForGrades.getRewardFor5());
                                } else tvGrade5IncomeCount.setText("0");

                                if (!stringIsNullOrEmpty(mRewardForGrades.getRewardFor4()) && Integer.parseInt(mRewardForGrades.getRewardFor4()) > 0) tvGrade4IncomeCount.setText("+" + mRewardForGrades.getRewardFor4());
                                else if (stringIsNullOrEmpty(mRewardForGrades.getRewardFor4())) tvGrade4IncomeCount.setText("0");
                                else tvGrade4IncomeCount.setText(mRewardForGrades.getRewardFor4());
                            }
                        }
                    });
                }
            }
        });
    }

    private void onOptionsClick() {
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        PWOption popupWindow;
        if (mLessonsWithOutSmartGrades != null) {
            popupWindow = new PWOption(width, height, activity, mentorList, mLessonsWithOutSmartGrades.getLessonName(), mLessonsWithOutSmartGrades.getChildLessonId(), mLessonsWithOutSmartGrades.getLessonId());
        }
        else {
            popupWindow = new PWOption(width, height, activity, mentorList, mLessonsWithSmartGrades.getLessonName(), mLessonsWithSmartGrades.getChildLessonId(), mLessonsWithSmartGrades.getLessonId());
        }

        popupWindow.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(5);
        }
        popupWindow.showAsDropDown(ivOptions);
    }

    private void onSchoolClick() {
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        PWSchoolInfo popupWindow;
        if (mLessonsWithOutSmartGrades != null) {
            popupWindow = new PWSchoolInfo(width, height, activity, mLessonsWithOutSmartGrades.getSchoolId());
        }
        else {
            popupWindow = new PWSchoolInfo(width, height, activity, mLessonsWithSmartGrades.getSchoolId());
        }

        popupWindow.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(5);
        }
        popupWindow.showAsDropDown(ivSchool);
    }

    private void onBack() {
        mLessonsWithOutSmartGrades = null;
        mLessonsWithSmartGrades = null;
        mentorList = null;
        mGetLessonInfo = null;
        CHILD_LESSON_ID = null;
        activity.onBackPressed();
    }

    public void setLessonsWithOutSmartGradesData(ModelLessonsWithOutSmartGrades mLessonsWithOutSmartGrades, ModelChildData mChild) {
        this.mLessonsWithOutSmartGrades = mLessonsWithOutSmartGrades;
        this.mChild = mChild;
        mentorList = mLessonsWithOutSmartGrades.getMentors();
        SMART_GRADES_ON = false;
        setLessonData();
        loadLessonInfo();
    }

    public void setLessonsWithSmartGradesData(ModelLessonsWithSmartGrades mLessonsWithSmartGrades, ModelChildData mChild) {
        this.mLessonsWithSmartGrades = mLessonsWithSmartGrades;
        this.mChild = mChild;
        mentorList = mLessonsWithSmartGrades.getMentors();
        mGradesSettings = mLessonsWithSmartGrades.getGradesSettings();
        SMART_GRADES_ON = true;
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
        parentLastMessagesListAdapter = new ParentLastMessagesListAdapter(activity);
        //parentLastMessagesListAdapter.setOnItemClickListener(this);
        rvLastMessages.setAdapter(parentLastMessagesListAdapter);
        rvLastMessages.suppressLayout(true);
        parentLastMessagesListAdapter.updateData(chats);
    }

    private void setGradesTable() {
        rvGradesTable.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true));
        gradesTableMonthAdapter = new GradesTableMonthAdapter(activity);
        //gradesTableMonthAdapter.setOnItemClickListener(this);
        rvGradesTable.setAdapter(gradesTableMonthAdapter);
        if (!listIsNullOrEmpty(mGetLessonInfo.getGradesTable())) {
            gradesTableMonthAdapter.updateData(new ArrayList(mGetLessonInfo.getGradesTable().subList(mGetLessonInfo.getGradesTable().size() - 1, mGetLessonInfo.getGradesTable().size())));
        }
        if (!listIsNullOrEmpty(mGetLessonInfo.getGradesTable())) {
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



    public void onRemoveMentor(ModelMentorList mMentor) {
        mentorList.remove(mMentor);
    }

    public void onRemoveLessonProve() {
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        PWRemoveLessonProve popupWindow = new PWRemoveLessonProve(width, height, activity, CHILD_LESSON_ID);
        popupWindow.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(20);
        }
        popupWindow.showAtLocation(ivOptions, Gravity.CENTER, 0, 0);
    }

    public void setNewMentor(kz.tech.smartgrades.mentor.models.ModelMentorList mMentor) {
        ModelMentorList m = new ModelMentorList();
        m.setAboutMe(mMentor.getAboutMe());
        m.setAvatar(mMentor.getAvatar());
        m.setFirstName(mMentor.getFirstName());
        m.setLastName(mMentor.getLastName());
        m.setId(mMentor.getId());
        m.setLogin(mMentor.getLogin());
        if (mentorList == null)
            mentorList = new ArrayList<>();
        mentorList.add(m);
    }

    @Override
    public void onCancelDialog() {

    }

    @Override
    public void onExitDialog() {

    }
}