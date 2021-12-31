package kz.tech.smartgrades.sponsor.dialogs;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
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
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._System;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import kz.tech.smartgrades.sponsor.adapters.GetChildLessonsListAdapter;
import kz.tech.smartgrades.sponsor.models.ModelGetChildLessons;
import kz.tech.smartgrades.sponsor.models.ModelSponsorAddChild;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetChildLessons;
import static kz.tech.smartgrades._Web.func_InterFormOfSponsorship;


public class DialogSponsorAddChild extends Dialog implements View.OnClickListener {

    private SponsorActivity activity;
    private ModelSponsorAddChild modelSponsorAddChild;

    private String SPONSOR_ID;
    private String[] SELECT_LESSON_ID;
    private String[] SELECT_LESSON_NAME;

    private CircleImageView civAvatar;
    private TextView tvFullName;
    private TextView tvLogin;
    private TextView tvAboutMe;
    private Button btnSend;
    private ImageView ivBack;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick();
    }

    public DialogSponsorAddChild(SponsorActivity activity, ModelSponsorAddChild modelSponsorAddChild) {
        super(activity, R.style.CustomDialog2);
        this.activity = activity;
        this.modelSponsorAddChild = modelSponsorAddChild;
        setCanceledOnTouchOutside(true);

        SELECT_LESSON_ID = new String[4];
        SELECT_LESSON_NAME = new String[4];

        SPONSOR_ID = activity.login.loadUserDate(LoginKey.ID);

        View view = getLayoutInflater().inflate(R.layout.dialog_sponsor_add_user, null, false);
        this.setContentView(view);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        civAvatar = view.findViewById(R.id.civAvatar);
        tvFullName = view.findViewById(R.id.tvFullName);
        tvLogin = view.findViewById(R.id.tvLogin);
        tvAboutMe = view.findViewById(R.id.tvTitle1);
        btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        String avatar = modelSponsorAddChild.getAvatar();
        if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);
        else civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));

        tvFullName.setText(modelSponsorAddChild.getFirstName());
        tvLogin.setText(modelSponsorAddChild.getLogin());
        tvAboutMe.setText(modelSponsorAddChild.getAboutMe());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.btnSend:
                onSend();
                break;
        }
    }

    private void onBack() {
        dismiss();
    }

    private void onSend() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        View viewDialog = getLayoutInflater().inflate(R.layout.dialog_sponsor_add_user_2, null, false);
        bottomSheetDialog.setContentView(viewDialog);
        View bottomSheet = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        bottomSheet.setLayoutParams(layoutParams);
        layoutParams.height = (_System.getWindowHeight(activity));
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        TextView tvTitle, tvLogin, tvFullName;
        CircleImageView civAvatar, civParentAvatar;
        TextView tvAverageGrade, tvGradeCount, tvAmountOfPayments;
        FrameLayout flLesson1, flLesson2, flLesson3, flLesson4;
        TextView[] tvSelectLessonLabel = new TextView[4];
        TextView[] tvSelectLessonImg = new TextView[4];
        Button btnSend;
        ImageView ivBack;

        final double[] dAverageGrade = new double[1];
        final int[] SELECT_LESSON_COUNT = {0};

        tvTitle = viewDialog.findViewById(R.id.tvTitle);
        tvLogin = viewDialog.findViewById(R.id.tvLogin);
        tvFullName = viewDialog.findViewById(R.id.tvFullName);
        civAvatar = viewDialog.findViewById(R.id.civAvatar);
        civParentAvatar = viewDialog.findViewById(R.id.civParentAvatar);
        tvAverageGrade = viewDialog.findViewById(R.id.tvAverageGrade);
        tvGradeCount = viewDialog.findViewById(R.id.tvGradeCount);
        tvAmountOfPayments = viewDialog.findViewById(R.id.tvAmountOfPayments);
        flLesson1 = viewDialog.findViewById(R.id.flLesson1);
        flLesson2 = viewDialog.findViewById(R.id.flLesson2);
        flLesson3 = viewDialog.findViewById(R.id.flLesson3);
        flLesson4 = viewDialog.findViewById(R.id.flLesson4);
        tvSelectLessonLabel[0] = viewDialog.findViewById(R.id.tvSelectLesson1Label);
        tvSelectLessonImg[0] = viewDialog.findViewById(R.id.tvSelectLesson1Img);
        tvSelectLessonLabel[1] = viewDialog.findViewById(R.id.tvSelectLesson2Label);
        tvSelectLessonImg[1] = viewDialog.findViewById(R.id.tvSelectLesson2Img);
        tvSelectLessonLabel[2] = viewDialog.findViewById(R.id.tvSelectLesson3Label);
        tvSelectLessonImg[2] = viewDialog.findViewById(R.id.tvSelectLesson3Img);
        tvSelectLessonLabel[3] = viewDialog.findViewById(R.id.tvSelectLesson4Label);
        tvSelectLessonImg[3] = viewDialog.findViewById(R.id.tvSelectLesson4Img);
        btnSend = viewDialog.findViewById(R.id.btnSend);
        ivBack = viewDialog.findViewById(R.id.ivBack);

        tvTitle.setText(modelSponsorAddChild.getLogin());
        tvLogin.setText(modelSponsorAddChild.getLogin());
        tvFullName.setText(modelSponsorAddChild.getFirstName() + " " + modelSponsorAddChild.getLastName());

        String avatar = modelSponsorAddChild.getAvatar();
        if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);
        else civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));

        avatar = modelSponsorAddChild.getParentAvatar();
        if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civParentAvatar);
        else civParentAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));

        tvAmountOfPayments.setText("8 135");

        GetChildLessonsListAdapter adapter = new GetChildLessonsListAdapter(activity);

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, modelSponsorAddChild.getUserId());

        String SOAP = SoapRequest(func_GetChildLessons, jsonData.toString());
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
                    if (result.equals("0")) {
                        //Error
                    } else {
                        Type founderListType = new TypeToken<ArrayList<ModelGetChildLessons>>() {}.getType();
                        ArrayList<ModelGetChildLessons> lessonsList = new Gson().fromJson(result, founderListType);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                adapter.updateData(lessonsList);
                            }
                        });
                    }
                }
            }
        });

        flLesson1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomDialog = new BottomSheetDialog(activity);
                View viewDialog = getLayoutInflater().inflate(R.layout.dialog_add_child_select_lesson, null, false);
                bottomDialog.setContentView(viewDialog);
                bottomDialog.show();
                TextView tvListCleanLabel = viewDialog.findViewById(R.id.tvListCleanLabel);
                if (adapter.getItemCount() > 0){
                    RecyclerView recyclerView = viewDialog.findViewById(R.id.rvGradesList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(adapter);
//                    adapter.setOnItemClickListener(new GetChildLessonsListAdapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(ModelGetChildLessons m) {
//                            bottomDialog.dismiss();
//                            SELECT_LESSON_ID[0] = m.getLessonId();
//                            SELECT_LESSON_NAME[0] = m.getLessonName();
//                            SELECT_LESSON_COUNT[0] += 1;
//                            if (SELECT_LESSON_COUNT[0] > 1){
//                                btnSend.setEnabled(true);
//                                btnSend.setBackgroundResource(R.drawable.btn_background_purple);
//                            }
//                            tvSelectLessonLabel[0].setText(m.getLessonName());
//                            int TotalGradeCount = m.getGrades().size();
//                            if (TotalGradeCount > 0){
//                                double AverageGrade = (double) Convert.Str2Double(m.getAverageGrade());
//                                tvSelectLessonImg[0].setText(Convert.Double2Str(AverageGrade, "#.#"));
//                                if (AverageGrade < 3)
//                                    tvSelectLessonImg[0].setBackgroundResource(R.drawable.img_oval_red);
//                                else if (AverageGrade >= 3 && AverageGrade < 4)
//                                    tvSelectLessonImg[0].setBackgroundResource(R.drawable.img_oval_yellow);
//                                else if (AverageGrade >= 4 && AverageGrade < 5)
//                                    tvSelectLessonImg[0].setBackgroundResource(R.drawable.img_oval_green);
//                                else if (AverageGrade == 5)
//                                    tvSelectLessonImg[0].setBackgroundResource(R.drawable.img_oval_purple);
//
//                                dAverageGrade[0] += AverageGrade;
//                                tvAverageGrade.setText(Convert.Double2Str(dAverageGrade[0] / SELECT_LESSON_COUNT[0], "#.#"));
//                            }
//                            else {
//                                tvSelectLessonImg[0].setText("-");
//                                tvSelectLessonImg[0].setBackgroundResource(R.drawable.background_oval_gray);
//                                dAverageGrade[0] += 3.5;
//                                tvAverageGrade.setText(Convert.Double2Str(dAverageGrade[0] / SELECT_LESSON_COUNT[0], "#.#"));
//                            }
//                            tvGradeCount.setText(String.valueOf(SELECT_LESSON_COUNT[0] * 2));
//                        }
//                    });
                }
                else {
                    tvListCleanLabel.setVisibility(View.VISIBLE);
                }
            }
        });
        flLesson2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomDialog = new BottomSheetDialog(activity);
                View viewDialog = getLayoutInflater().inflate(R.layout.dialog_add_child_select_lesson, null, false);
                bottomDialog.setContentView(viewDialog);
                bottomDialog.show();
                TextView tvListCleanLabel = viewDialog.findViewById(R.id.tvListCleanLabel);
                if (adapter.getItemCount() > 0){
                    RecyclerView recyclerView = viewDialog.findViewById(R.id.rvGradesList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(adapter);
//                    adapter.setOnItemClickListener(new GetChildLessonsListAdapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(ModelGetChildLessons m) {
//                            bottomDialog.hide();
//                            SELECT_LESSON_ID[1] = m.getLessonId();
//                            SELECT_LESSON_NAME[1] = m.getLessonName();
//                            SELECT_LESSON_COUNT[0] += 1;
//                            if (SELECT_LESSON_COUNT[0] > 1){
//                                btnSend.setEnabled(true);
//                                btnSend.setBackgroundResource(R.drawable.btn_background_purple);
//                            }
//                            tvSelectLessonLabel[1].setText(m.getLessonName());
//                            int TotalGradeCount = m.getGrades().size();
//                            if (TotalGradeCount > 0){
//                                double AverageGrade = (double) Convert.Str2Double(m.getAverageGrade());
//                                tvSelectLessonImg[1].setText(Convert.Double2Str(AverageGrade, "#.#"));
//                                if (AverageGrade < 3)
//                                    tvSelectLessonImg[1].setBackgroundResource(R.drawable.img_oval_red);
//                                else if (AverageGrade >= 3 && AverageGrade < 4)
//                                    tvSelectLessonImg[1].setBackgroundResource(R.drawable.img_oval_yellow);
//                                else if (AverageGrade >= 4 && AverageGrade < 5)
//                                    tvSelectLessonImg[1].setBackgroundResource(R.drawable.img_oval_green);
//                                else if (AverageGrade == 5)
//                                    tvSelectLessonImg[1].setBackgroundResource(R.drawable.img_oval_purple);
//
//                                dAverageGrade[0] += AverageGrade;
//                                tvAverageGrade.setText(Convert.Double2Str(dAverageGrade[0] / SELECT_LESSON_COUNT[0], "#.#"));
//                            }
//                            else {
//                                tvSelectLessonImg[1].setText("-");
//                                tvSelectLessonImg[1].setBackgroundResource(R.drawable.background_oval_gray);
//                                dAverageGrade[0] += 3.5;
//                                tvAverageGrade.setText(Convert.Double2Str(dAverageGrade[0] / SELECT_LESSON_COUNT[0], "#.#"));
//                            }
//                            tvGradeCount.setText(String.valueOf(SELECT_LESSON_COUNT[0] * 2));
//                        }
//                    });
                }
                else {
                    tvListCleanLabel.setVisibility(View.VISIBLE);
                }
            }
        });
        flLesson3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomDialog = new BottomSheetDialog(activity);
                View viewDialog = getLayoutInflater().inflate(R.layout.dialog_add_child_select_lesson, null, false);
                bottomDialog.setContentView(viewDialog);
                bottomDialog.show();
                TextView tvListCleanLabel = viewDialog.findViewById(R.id.tvListCleanLabel);
                if (adapter.getItemCount() > 0){
                    RecyclerView recyclerView = viewDialog.findViewById(R.id.rvGradesList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(adapter);
//                    adapter.setOnItemClickListener(new GetChildLessonsListAdapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(ModelGetChildLessons m) {
//                            bottomDialog.hide();
//                            SELECT_LESSON_ID[2] = m.getLessonId();
//                            SELECT_LESSON_NAME[2] = m.getLessonName();
//                            SELECT_LESSON_COUNT[0] += 1;
//                            if (SELECT_LESSON_COUNT[0] > 1){
//                                btnSend.setEnabled(true);
//                                btnSend.setBackgroundResource(R.drawable.btn_background_purple);
//                            }
//                            tvSelectLessonLabel[2].setText(m.getLessonName());
//                            int TotalGradeCount = m.getGrades().size();
//                            if (TotalGradeCount > 0){
//                                double AverageGrade = (double) Convert.Str2Double(m.getAverageGrade());
//                                tvSelectLessonImg[2].setText(Convert.Double2Str(AverageGrade, "#.#"));
//                                if (AverageGrade < 3)
//                                    tvSelectLessonImg[2].setBackgroundResource(R.drawable.img_oval_red);
//                                else if (AverageGrade >= 3 && AverageGrade < 4)
//                                    tvSelectLessonImg[2].setBackgroundResource(R.drawable.img_oval_yellow);
//                                else if (AverageGrade >= 4 && AverageGrade < 5)
//                                    tvSelectLessonImg[2].setBackgroundResource(R.drawable.img_oval_green);
//                                else if (AverageGrade == 5)
//                                    tvSelectLessonImg[2].setBackgroundResource(R.drawable.img_oval_purple);
//
//                                dAverageGrade[0] += AverageGrade;
//                                tvAverageGrade.setText(Convert.Double2Str(dAverageGrade[0] / SELECT_LESSON_COUNT[0], "#.#"));
//                            }
//                            else {
//                                tvSelectLessonImg[2].setText("-");
//                                tvSelectLessonImg[2].setBackgroundResource(R.drawable.background_oval_gray);
//                                dAverageGrade[0] += 3.5;
//                                tvAverageGrade.setText(Convert.Double2Str(dAverageGrade[0] / SELECT_LESSON_COUNT[0], "#.#"));
//                            }
//                            tvGradeCount.setText(String.valueOf(SELECT_LESSON_COUNT[0] * 2));
//                        }
//                    });
                }
                else {
                    tvListCleanLabel.setVisibility(View.VISIBLE);
                }
            }
        });
        flLesson4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomDialog = new BottomSheetDialog(activity);
                View viewDialog = getLayoutInflater().inflate(R.layout.dialog_add_child_select_lesson, null, false);
                bottomDialog.setContentView(viewDialog);
                bottomDialog.show();
                TextView tvListCleanLabel = viewDialog.findViewById(R.id.tvListCleanLabel);
                if (adapter.getItemCount() > 0){
                    RecyclerView recyclerView = viewDialog.findViewById(R.id.rvGradesList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(adapter);
//                    adapter.setOnItemClickListener(new GetChildLessonsListAdapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(ModelGetChildLessons m) {
//                            bottomDialog.hide();
//                            SELECT_LESSON_ID[3] = m.getLessonId();
//                            SELECT_LESSON_NAME[3] = m.getLessonName();
//                            SELECT_LESSON_COUNT[0] += 1;
//                            if (SELECT_LESSON_COUNT[0] > 1){
//                                btnSend.setEnabled(true);
//                                btnSend.setBackgroundResource(R.drawable.btn_background_purple);
//                            }
//                            tvSelectLessonLabel[3].setText(m.getLessonName());
//                            int TotalGradeCount = m.getGrades().size();
//                            if (TotalGradeCount > 0){
//                                double AverageGrade = (double) Convert.Str2Double(m.getAverageGrade());
//                                tvSelectLessonImg[3].setText(Convert.Double2Str(AverageGrade, "#.#"));
//                                if (AverageGrade < 3)
//                                    tvSelectLessonImg[3].setBackgroundResource(R.drawable.img_oval_red);
//                                else if (AverageGrade >= 3 && AverageGrade < 4)
//                                    tvSelectLessonImg[3].setBackgroundResource(R.drawable.img_oval_yellow);
//                                else if (AverageGrade >= 4 && AverageGrade < 5)
//                                    tvSelectLessonImg[3].setBackgroundResource(R.drawable.img_oval_green);
//                                else if (AverageGrade == 5)
//                                    tvSelectLessonImg[3].setBackgroundResource(R.drawable.img_oval_purple);
//
//                                dAverageGrade[0] += AverageGrade;
//                                tvAverageGrade.setText(Convert.Double2Str(dAverageGrade[0] / SELECT_LESSON_COUNT[0], "#.#"));
//                            }
//                            else {
//                                tvSelectLessonImg[3].setText("-");
//                                tvSelectLessonImg[3].setBackgroundResource(R.drawable.background_oval_gray);
//                                dAverageGrade[0] += 3.5;
//                                tvAverageGrade.setText(Convert.Double2Str(dAverageGrade[0] / SELECT_LESSON_COUNT[0], "#.#"));
//                            }
//                            tvGradeCount.setText(String.valueOf(SELECT_LESSON_COUNT[0] * 2));
//                        }
//                    });
                }
                else {
                    tvListCleanLabel.setVisibility(View.VISIBLE);
                }
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                dismiss();

                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.SponsorId, SPONSOR_ID);
                jsonData.addProperty(F.ParentId, modelSponsorAddChild.getParentId());
                jsonData.addProperty(F.ChildId, modelSponsorAddChild.getUserId());
                jsonData.addProperty("LessonId1", SELECT_LESSON_ID[0]);
                jsonData.addProperty("LessonId2", SELECT_LESSON_ID[1]);
                jsonData.addProperty("LessonId3", SELECT_LESSON_ID[2]);
                jsonData.addProperty("LessonId4", SELECT_LESSON_ID[3]);
                jsonData.addProperty("AverageGrade", tvAverageGrade.getText().toString());
                jsonData.addProperty("CountGrade", tvGradeCount.getText().toString());
                jsonData.addProperty("Reward", tvAmountOfPayments.getText().toString().replace(" ", ""));

                String msg = "Здравствуйте, хочу спонсировать вашего ребенка - " + modelSponsorAddChild.getLogin() + ".\n\n" +
                        "Условие на неделю: \n" +
                        "1. Достичь среднего балла выше, чем " + tvAverageGrade.getText() + "\n" +
                        "2. Получить не менее " + tvGradeCount.getText() + " оценок;\n\n" +
                        "По следующим предметам:\n";
                if (!stringIsNullOrEmpty(SELECT_LESSON_NAME[0])) msg += "- " + SELECT_LESSON_NAME[0] + ";\n";
                if (!stringIsNullOrEmpty(SELECT_LESSON_NAME[1])) msg += "- " + SELECT_LESSON_NAME[1] + ";\n";
                if (!stringIsNullOrEmpty(SELECT_LESSON_NAME[2])) msg += "- " + SELECT_LESSON_NAME[2] + ";\n";
                if (!stringIsNullOrEmpty(SELECT_LESSON_NAME[3])) msg += "- " + SELECT_LESSON_NAME[3] + ";\n";
                msg += "\nСрок: в течении 6 дней, после принятия заявки.\n\n" +
                        "Вознаграждение: " + tvAmountOfPayments.getText() + " тенге;\n";

                jsonData.addProperty(F.SourceId, SPONSOR_ID);
                jsonData.addProperty(F.TargetId, modelSponsorAddChild.getParentId());
                jsonData.addProperty(F.Message, msg);

                String SOAP = SoapRequest(func_InterFormOfSponsorship, jsonData.toString());
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
                                    if (result.equals("0")) activity.alert.onToast("Ошибка, попробуйте позднее.");
                                    else if (result.equals("1")) {
                                        activity.alert.onToast("Заявка на добавление успешно отправлена.");
                                        activity.presenter.onUpdateData();
                                    }
                                    else if (result.equals("2")) activity.alert.onToast("Заявка на данного ребенка уже отправлена.");
                                    else if (result.equals("3")) activity.alert.onToast("Данный ребенок уже спонсируется.");
                                }
                            });
                        }
                        else activity.alert.onToast("Произошла ошибка, попробуйте позже.");
                    }
                });

            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }


}