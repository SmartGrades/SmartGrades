package kz.tech.smartgrades.parent.bottom_sheet_dialogs;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.model.ModelLessonsWithOutSmartGrades;
import kz.tech.smartgrades.parent.model.ModelParentSetGrade;
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
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_ParentDeleteLastGrade;
import static kz.tech.smartgrades._Web.func_ParentSetGrade;

public class BSDSetRegularGrades extends BottomSheetDialog implements View.OnClickListener {

    private ParentActivity activity;
    private ModelLessonsWithOutSmartGrades mLesson;
    private String PARENT_ID;

    private TextView tvLessonName;
    private TextView tvRewardForAbsent;
    private TextView tvRewardFor2;
    private TextView tvRewardFor3;
    private TextView tvRewardFor4;
    private TextView tvRewardFor5;
    private CardView cvAbsent;
    private CardView cvGrade2;
    private CardView cvGrade3;
    private CardView cvGrade4;
    private CardView cvGrade5;
    private TextView tvSmartGrades;
    private TextView tvCancelLastGrade;

    public BSDSetRegularGrades(@NonNull ParentActivity activity, ModelLessonsWithOutSmartGrades mLesson) {
        super(activity);
        this.mLesson = mLesson;
        this.activity = activity;
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);

        View view = getLayoutInflater().inflate(R.layout.bsd_parent_set_smart_grades, null, false);
        setContentView(view);

        initViews(view);
        setData();
    }

    private void initViews(View view) {
        tvLessonName = view.findViewById(R.id.tvLessonName);
        tvRewardForAbsent = view.findViewById(R.id.tvRewardForAbsent);
        tvRewardForAbsent.setVisibility(View.GONE);
        tvRewardFor2 = view.findViewById(R.id.tvRewardFor2);
        tvRewardFor2.setVisibility(View.GONE);
        tvRewardFor3 = view.findViewById(R.id.tvRewardFor3);
        tvRewardFor3.setVisibility(View.GONE);
        tvRewardFor4 = view.findViewById(R.id.tvRewardFor4);
        tvRewardFor4.setVisibility(View.GONE);
        tvRewardFor5 = view.findViewById(R.id.tvRewardFor5);
        tvRewardFor5.setVisibility(View.GONE);
        cvAbsent = view.findViewById(R.id.cvAbsent);
        cvAbsent.setOnClickListener(this);
        cvGrade2 = view.findViewById(R.id.cvGrade2);
        cvGrade2.setOnClickListener(this);
        cvGrade3 = view.findViewById(R.id.cvGrade3);
        cvGrade3.setOnClickListener(this);
        cvGrade4 = view.findViewById(R.id.cvGrade4);
        cvGrade4.setOnClickListener(this);
        cvGrade5 = view.findViewById(R.id.cvGrade5);
        cvGrade5.setOnClickListener(this);
        tvSmartGrades = view.findViewById(R.id.tvSmartGrades);
        tvSmartGrades.setText("Оценка по предмету");
        tvCancelLastGrade = view.findViewById(R.id.tvCancelLastGrade);
        tvCancelLastGrade.setOnClickListener(this);
    }

    private void setData() {
        tvLessonName.setText(mLesson.getLessonName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cvAbsent:
                setGrade(0, 1);
                break;
            case R.id.cvGrade2:
                setGrade(2, 0);
                break;
            case R.id.cvGrade3:
                setGrade(3, 0);
                break;
            case R.id.cvGrade4:
                setGrade(4, 0);
                break;
            case R.id.cvGrade5:
                setGrade(5, 0);
                break;
            case R.id.tvCancelLastGrade:
                CancelLastGrade();
                break;
        }
    }

    private void setGrade(int grade, int type) {
        ModelParentSetGrade mGrade = new ModelParentSetGrade();
        mGrade.setSourceId(PARENT_ID);
        mGrade.setChildId(activity.getMParentData().getFamilyGroup().getChildrens().get(activity.getCHILD_INDEX()).getId());
        mGrade.setChildLessonId(mLesson.getChildLessonId());
        mGrade.setGrade(grade);
        mGrade.setType(type);

        String SOAP = SoapRequest(func_ParentSetGrade, new Gson().toJson(mGrade));
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
                                activity.updatePresenter();
                                dismiss();
                            }
                        }
                    });
                }
            }
        });
    }
    private void CancelLastGrade(){
        ModelParentSetGrade mGrade = new ModelParentSetGrade();
        mGrade.setChildId(activity.getMParentData().getFamilyGroup().getChildrens().get(activity.getCHILD_INDEX()).getId());
        mGrade.setSourceId(PARENT_ID);
        mGrade.setChildLessonId(mLesson.getChildLessonId());

        String SOAP = SoapRequest(func_ParentDeleteLastGrade, new Gson().toJson(mGrade));
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
                                activity.updatePresenter();
                                dismiss();
                            }
                        }
                    });
                }
            }
        });
    }
}
