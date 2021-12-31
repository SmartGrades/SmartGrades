package kz.tech.smartgrades.sponsor.dialogs;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.mentor.models.ModelDefaultMessages;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import kz.tech.smartgrades.sponsor.adapters.GetChildLessonsListAdapter;
import kz.tech.smartgrades.sponsor.models.ModelGetChildLessons;
import kz.tech.smartgrades.sponsor.models.ModelInterFormParentToSponsorEdit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_AcceptSponsorEditParentInterForm;
import static kz.tech.smartgrades._Web.func_GetChildLessons;
import static kz.tech.smartgrades._Web.func_GetSponsorParentInterFormData;


public class DialogSponsorEditParentInterForm extends Dialog implements View.OnClickListener {

    private SponsorActivity activity;

    private String InterFormId;
    private String MessageId;
    private ModelInterFormParentToSponsorEdit mInterFormData;

    private TextView tvChildLogin, tvAverageGrade, tvGradeCount, tvAmountOfPayments;
    private CircleImageView civChildAvatar, civParentAvatar;
    private CardView flLesson1, flLesson2, flLesson3, flLesson4;
    private ImageView ivBack;
    private Button btnSaveEdit;

    private String[] SELECT_LESSON_ID;
    private String[] SELECT_LESSON_NAME;

    final int[] SELECT_LESSON_COUNT = {0};
    final double[] iAverageGrade = new double[4];

    private TextView[] tvSelectLessonLabel = new TextView[4];
    private TextView[] tvSelectLessonImg = new TextView[4];

    private GetChildLessonsListAdapter LessonsListAdapter;

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(ArrayList<ModelDefaultMessages> modelChats);
    }


    public DialogSponsorEditParentInterForm(@NonNull SponsorActivity activity, String InterFormId, String MessageId) {
        super(activity, R.style.CustomDialog2);
        this.activity = activity;
        this.InterFormId = InterFormId;
        this.MessageId = MessageId;

        this.setCanceledOnTouchOutside(true);
        View view = getLayoutInflater().inflate(R.layout.fragment_sponsor_edit_parent_inter_form, null, false);
        this.setContentView(view);

        initViews(view);
        onLoadData();
    }

    private void initViews(View view){
        tvChildLogin = view.findViewById(R.id.tvChildLogin);
        tvAverageGrade = view.findViewById(R.id.tvAverageGrade);
        tvGradeCount = view.findViewById(R.id.tvGradeCount);
        tvAmountOfPayments = view.findViewById(R.id.tvAmountOfPayments);
        civChildAvatar = view.findViewById(R.id.civMentorAvatar);
        civParentAvatar = view.findViewById(R.id.civParentAvatar);
        flLesson1 = view.findViewById(R.id.flLesson1);
        flLesson1.setOnClickListener(this);
        flLesson2 = view.findViewById(R.id.flLesson2);
        flLesson2.setOnClickListener(this);
        flLesson3 = view.findViewById(R.id.flLesson3);
        flLesson3.setOnClickListener(this);
        flLesson4 = view.findViewById(R.id.flLesson4);
        flLesson4.setOnClickListener(this);
        tvSelectLessonLabel[0] = view.findViewById(R.id.tvSelectLesson1Label);
        tvSelectLessonImg[0] = view.findViewById(R.id.tvSelectLesson1Img);
        tvSelectLessonLabel[1] = view.findViewById(R.id.tvSelectLesson2Label);
        tvSelectLessonImg[1] = view.findViewById(R.id.tvSelectLesson2Img);
        tvSelectLessonLabel[2] = view.findViewById(R.id.tvSelectLesson3Label);
        tvSelectLessonImg[2] = view.findViewById(R.id.tvSelectLesson3Img);
        tvSelectLessonLabel[3] = view.findViewById(R.id.tvSelectLesson4Label);
        tvSelectLessonImg[3] = view.findViewById(R.id.tvSelectLesson4Img);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        btnSaveEdit = view.findViewById(R.id.btnSaveEdit);
        btnSaveEdit.setOnClickListener(this);

        LessonsListAdapter = new GetChildLessonsListAdapter(activity);

        SELECT_LESSON_ID = new String[4];
        SELECT_LESSON_NAME = new String[4];
    }

    private void onLoadData(){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.Index, InterFormId);

        String SOAP = SoapRequest(func_GetSponsorParentInterFormData, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.code() >= 200 && response.code() <= 300) {
                    String xml = response.body().string();
                    String result = _Web.XMLToJson(xml);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("0")) { }
                            else {
                                mInterFormData = new Gson().fromJson(result, ModelInterFormParentToSponsorEdit.class);

                                String avatar = mInterFormData.getChildAvatar();
                                if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civChildAvatar);
                                else civChildAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));

                                tvChildLogin.setText(mInterFormData.getChildLogin());
                                tvAverageGrade.setText(mInterFormData.getAverageGrade());
                                tvGradeCount.setText(mInterFormData.getCountGrade());
                                tvAmountOfPayments.setText(mInterFormData.getReward());

                                avatar = mInterFormData.getParentAvatar();
                                if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civParentAvatar);
                                else civParentAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));

                                if (mInterFormData.getLessonAverage1() != null && !mInterFormData.getLessonAverage1().isEmpty()) {
                                    SELECT_LESSON_ID[0] = mInterFormData.getLessonId1();
                                    SELECT_LESSON_NAME[0] = mInterFormData.getLessonName1();
                                    SELECT_LESSON_COUNT[0] += 1;

                                    tvSelectLessonLabel[0].setText(mInterFormData.getLessonName1());
                                    tvSelectLessonImg[0].setText(mInterFormData.getLessonAverage1());
                                    if (mInterFormData.getLessonAverage1() != null && !mInterFormData.getLessonAverage1().isEmpty()){
                                        double AverageGrade = Convert.Str2Double(mInterFormData.getLessonAverage1());
                                        if (AverageGrade < 3)
                                            tvSelectLessonImg[0].setBackgroundResource(R.drawable.background_oval_red);
                                        else if (AverageGrade >= 3 && AverageGrade < 4)
                                            tvSelectLessonImg[0].setBackgroundResource(R.drawable.background_oval_yellow);
                                        else if (AverageGrade >= 4 && AverageGrade < 5)
                                            tvSelectLessonImg[0].setBackgroundResource(R.drawable.background_oval_green);
                                        else if (AverageGrade == 5)
                                            tvSelectLessonImg[0].setBackgroundResource(R.drawable.background_oval_purple);
                                    }
                                    else{
                                        tvSelectLessonImg[0].setBackgroundResource(R.drawable.background_oval_gray);
                                        tvSelectLessonImg[0].setText("-");
                                    }
                                }
                                if (mInterFormData.getLessonAverage2() != null && !mInterFormData.getLessonAverage2().isEmpty()) {
                                    SELECT_LESSON_ID[1] = mInterFormData.getLessonId2();
                                    SELECT_LESSON_NAME[1] = mInterFormData.getLessonName2();
                                    SELECT_LESSON_COUNT[0] += 1;

                                    tvSelectLessonLabel[1].setText(mInterFormData.getLessonName2());
                                    tvSelectLessonImg[1].setText(mInterFormData.getLessonAverage2());
                                    if (mInterFormData.getLessonAverage2() != null && !mInterFormData.getLessonAverage2().isEmpty()){
                                        double AverageGrade = Convert.Str2Double(mInterFormData.getLessonAverage2());
                                        if (AverageGrade < 3)
                                            tvSelectLessonImg[1].setBackgroundResource(R.drawable.background_oval_red);
                                        else if (AverageGrade >= 3 && AverageGrade < 4)
                                            tvSelectLessonImg[1].setBackgroundResource(R.drawable.background_oval_yellow);
                                        else if (AverageGrade >= 4 && AverageGrade < 5)
                                            tvSelectLessonImg[1].setBackgroundResource(R.drawable.background_oval_green);
                                        else if (AverageGrade == 5)
                                            tvSelectLessonImg[1].setBackgroundResource(R.drawable.background_oval_purple);
                                    }
                                    else {
                                        tvSelectLessonImg[1].setBackgroundResource(R.drawable.background_oval_gray);
                                        tvSelectLessonImg[1].setText("-");
                                    }
                                }
                                if (mInterFormData.getLessonAverage3() != null && !mInterFormData.getLessonAverage3().isEmpty()) {
                                    SELECT_LESSON_ID[2] = mInterFormData.getLessonId3();
                                    SELECT_LESSON_NAME[2] = mInterFormData.getLessonName3();
                                    SELECT_LESSON_COUNT[0] += 1;

                                    tvSelectLessonLabel[2].setText(mInterFormData.getLessonName3());
                                    tvSelectLessonImg[2].setText(mInterFormData.getLessonAverage3());
                                    if (mInterFormData.getLessonAverage3() != null && !mInterFormData.getLessonAverage3().isEmpty()){
                                        double AverageGrade = Convert.Str2Double(mInterFormData.getLessonAverage3());
                                        if (AverageGrade < 3)
                                            tvSelectLessonImg[2].setBackgroundResource(R.drawable.background_oval_red);
                                        else if (AverageGrade >= 3 && AverageGrade < 4)
                                            tvSelectLessonImg[2].setBackgroundResource(R.drawable.background_oval_yellow);
                                        else if (AverageGrade >= 4 && AverageGrade < 5)
                                            tvSelectLessonImg[2].setBackgroundResource(R.drawable.background_oval_green);
                                        else if (AverageGrade == 5)
                                            tvSelectLessonImg[2].setBackgroundResource(R.drawable.background_oval_purple);
                                    }
                                    else {
                                        tvSelectLessonImg[2].setBackgroundResource(R.drawable.background_oval_gray);
                                        tvSelectLessonImg[2].setText("-");
                                    }
                                }
                                if (mInterFormData.getLessonAverage4() !=null && !mInterFormData.getLessonAverage4().isEmpty()) {
                                    SELECT_LESSON_ID[3] = mInterFormData.getLessonId4();
                                    SELECT_LESSON_NAME[3] = mInterFormData.getLessonName4();
                                    SELECT_LESSON_COUNT[0] += 1;

                                    tvSelectLessonLabel[3].setText(mInterFormData.getLessonName4());
                                    tvSelectLessonImg[3].setText(mInterFormData.getLessonAverage4());
                                    if (mInterFormData.getLessonAverage4() != null && !mInterFormData.getLessonAverage4().isEmpty()){
                                        double AverageGrade = Convert.Str2Double(mInterFormData.getLessonAverage4());
                                        if (AverageGrade < 3)
                                            tvSelectLessonImg[3].setBackgroundResource(R.drawable.background_oval_red);
                                        else if (AverageGrade >= 3 && AverageGrade < 4)
                                            tvSelectLessonImg[3].setBackgroundResource(R.drawable.background_oval_yellow);
                                        else if (AverageGrade >= 4 && AverageGrade < 5)
                                            tvSelectLessonImg[3].setBackgroundResource(R.drawable.background_oval_green);
                                        else if (AverageGrade == 5)
                                            tvSelectLessonImg[3].setBackgroundResource(R.drawable.background_oval_purple);
                                    }
                                    else {
                                        tvSelectLessonImg[3].setBackgroundResource(R.drawable.background_oval_gray);
                                        tvSelectLessonImg[3].setText("-");
                                    }
                                }

                                JsonObject jsonData = new JsonObject();
                                jsonData.addProperty(F.UserId, mInterFormData.getChildId());

                                String SOAP = SoapRequest(func_GetChildLessons, jsonData.toString());
                                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                                Request request = new Request.Builder().url(URL).post(body).build();
                                HttpClient.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(final Call call, IOException e) {
                                    }
                                    @Override
                                    public void onResponse(Call call, final Response response) throws IOException {
                                        if (response.code() >= 200 && response.code() <= 300) {
                                            String xml = response.body().string();
                                            String result = _Web.XMLToJson(xml);
                                            activity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (result.equals("0")) {}
                                                    else {
                                                        Type founderListType = new TypeToken<ArrayList<ModelGetChildLessons>>() {}.getType();
                                                        ArrayList<ModelGetChildLessons> lessonsList = new Gson().fromJson(result, founderListType);
//                                                        LessonsListAdapter.updateData(lessonsList);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }

    private void IsBTNSendEnable() {
        if (SELECT_LESSON_COUNT[0] > 1){
            btnSaveEdit.setEnabled(true);
            btnSaveEdit.setBackgroundResource(R.drawable.btn_background_purple);
        }
        else {
            btnSaveEdit.setEnabled(false);
            btnSaveEdit.setBackgroundResource(R.drawable.btn_background_purple_alpha);
        }
    }

    private void LessonSelectClick(int number) {
        if (mInterFormData == null) {
            return;
        }
        BottomSheetDialog bottomDialog = new BottomSheetDialog(activity);
        View viewDialog = getLayoutInflater().inflate(R.layout.dialog_add_child_select_lesson, null, false);
        bottomDialog.setContentView(viewDialog);
        bottomDialog.show();
        TextView tvListCleanLabel = viewDialog.findViewById(R.id.tvListCleanLabel);
        if (LessonsListAdapter.getItemCount() > 0){
            RecyclerView recyclerView = viewDialog.findViewById(R.id.rvGradesList);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(LessonsListAdapter);
//            LessonsListAdapter.setOnItemClickListener(new GetChildLessonsListAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(ModelGetChildLessons m) {
//                    bottomDialog.dismiss();
//                    if (m.getLessonId().equals(SELECT_LESSON_ID[number])) return;
//                    SELECT_LESSON_COUNT[0] = 0;
//                    SELECT_LESSON_ID[number] = m.getLessonId();
//                    SELECT_LESSON_NAME[number] = m.getLessonName();
//
//                    for(int i = 0; i < SELECT_LESSON_ID.length; i++){
//                        if (i != number){
//                            if (SELECT_LESSON_ID[i] != null && SELECT_LESSON_ID[i].equals(m.getLessonId())){
//                                SELECT_LESSON_ID[i] = "";
//                                SELECT_LESSON_NAME[i] = "";
//
//                                tvSelectLessonLabel[i].setText("");
//                                tvSelectLessonImg[i].setText("");
//                                tvSelectLessonImg[i].setBackgroundResource(R.drawable.img_arrow_down);
//                            }
//                        }
//                    }
//                    for(int i = 0; i < SELECT_LESSON_ID.length; i++){
//                        if (SELECT_LESSON_ID[i] != null && !SELECT_LESSON_ID[i].isEmpty()) SELECT_LESSON_COUNT[0] += 1;
//                    }
//
//                    IsBTNSendEnable();
//                    tvSelectLessonLabel[number].setText(m.getLessonName());
//                    int TotalGradeCount = m.getGrades().size();
//                    if (TotalGradeCount > 0){
//                        int TotalGradeSum = 0;
//                        for (int i = 0; i < TotalGradeCount; i++){
//                            TotalGradeSum += Convert.S2I(m.getGrades().get(i).getGrade());
//                        }
//                        double AverageGrade = (double) TotalGradeSum / TotalGradeCount;
//                        if (AverageGrade > 5) AverageGrade = 5;
//
//                        tvSelectLessonImg[number].setText(Convert.Double2Str(AverageGrade, "#.#"));
//                        if (AverageGrade < 3)
//                            tvSelectLessonImg[number].setBackgroundResource(R.drawable.img_oval_red);
//                        else if (AverageGrade >= 3 && AverageGrade < 4)
//                            tvSelectLessonImg[number].setBackgroundResource(R.drawable.img_oval_yellow);
//                        else if (AverageGrade >= 4 && AverageGrade < 5)
//                            tvSelectLessonImg[number].setBackgroundResource(R.drawable.img_oval_green);
//                        else if (AverageGrade == 5)
//                            tvSelectLessonImg[number].setBackgroundResource(R.drawable.img_oval_purple);
//
//                        iAverageGrade[0] += AverageGrade;
//                        tvAverageGrade.setText(Convert.Double2Str(iAverageGrade[0] / SELECT_LESSON_COUNT[0], "#.#"));
//                        tvGradeCount.setText(String.valueOf(SELECT_LESSON_COUNT[0] * 2));
//                    }
//                    else {
//                        tvSelectLessonImg[number].setText("-");
//                        tvSelectLessonImg[number].setBackgroundResource(R.drawable.background_oval_gray);
//                    }
//                }
//            });
        }
        else {
            tvListCleanLabel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.flLesson1:
                LessonSelectClick(0);
                break;
            case R.id.flLesson2:
                LessonSelectClick(1);
                break;
            case R.id.flLesson3:
                LessonSelectClick(2);
                break;
            case R.id.flLesson4:
                LessonSelectClick(3);
                break;
            case R.id.btnSaveEdit:
                onSaveEdit();
                break;
        }
    }

    private void onBack() {
        this.dismiss();
    }

    private void onSaveEdit() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.MessageId, MessageId);
        jsonData.addProperty(F.Index, mInterFormData.getIndex());
        jsonData.addProperty(F.SponsorId, mInterFormData.getSponsorId());
        jsonData.addProperty(F.ParentId, mInterFormData.getParentId());
        jsonData.addProperty(F.ChildId, mInterFormData.getChildId());
        jsonData.addProperty("LessonId1", SELECT_LESSON_ID[0]);
        jsonData.addProperty("LessonId2", SELECT_LESSON_ID[1]);
        jsonData.addProperty("LessonId3", SELECT_LESSON_ID[2]);
        jsonData.addProperty("LessonId4", SELECT_LESSON_ID[3]);
        jsonData.addProperty("ThresholdGrade", tvAverageGrade.getText().toString());
        jsonData.addProperty("GradesCount", tvGradeCount.getText().toString());
        jsonData.addProperty("Reward", tvAmountOfPayments.getText().toString().replace(" ", ""));

        jsonData.addProperty(F.SourceId, mInterFormData.getSponsorId());
        jsonData.addProperty(F.ChatId, mInterFormData.getChatId());

        String SOAP = SoapRequest(func_AcceptSponsorEditParentInterForm, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String xml = response.body().string();
                if (response.code() >= 200 && response.code() <= 300) {
                    String result = _Web.XMLToJson(xml);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("0")) {
                                activity.alert.onToast("Произошла ошибка, попробуйте позже.");
                            }
                            else {
                                Type founderListType = new TypeToken<ArrayList<ModelDefaultMessages>>(){}.getType();
                                ArrayList<ModelDefaultMessages> modelChats = new Gson().fromJson(result, founderListType);
                                if (onItemClickListener != null) {
                                    onItemClickListener.onItemClick(modelChats);
                                }
                                //onBack();
                            }
                        }
                    });
                }
                else activity.alert.onToast("Произошла ошибка, попробуйте позже.");
            }
        });

    }
}