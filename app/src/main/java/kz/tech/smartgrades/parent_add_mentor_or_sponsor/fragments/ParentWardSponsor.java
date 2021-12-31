package kz.tech.smartgrades.parent_add_mentor_or_sponsor.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
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
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.model.ModelMentorSponsorRoom;
import kz.tech.smartgrades.parent.model.ModelParentChildList;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.ParentAddSponsorOrMentorActivity;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.adapters.WardListAdapter;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.adapters.GetChildLessonsListAdapter;
import kz.tech.smartgrades.sponsor.models.ModelGetChildLessons;
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

public class ParentWardSponsor extends Fragment implements View.OnClickListener {

    private ParentAddSponsorOrMentorActivity activity;

    private TextView tvChildLogin, tvAverageGrade, tvGradeCount, tvAmountOfPayments;
    private CircleImageView civChildAvatar, civParentAvatar;
    private CardView cvSelectChild, flLesson1, flLesson2, flLesson3, flLesson4;
    private CheckBox cbCondition1, cbCondition2;
    private Button btnSend;
    private ImageView ivBack;

    //CHILD MODEL
    private ModelParentChildList mChild;
    private ArrayList<ModelParentChildList> modelParentChildLists;
    //MENTOR MODEL
    private ModelMentorSponsorRoom mSponsor;

    private GetChildLessonsListAdapter LessonsListAdapter;

    private String[] SELECT_LESSON_ID;
    private String[] SELECT_LESSON_NAME;
    final int[] SELECT_LESSON_COUNT = {0};
    final double[] dAverageGrade = { 0 };
    private int iReward = 0;

    private TextView[] tvSelectLessonLabel = new TextView[4];
    private TextView[] tvSelectLessonImg = new TextView[4];

    private boolean IsFirstChildSelect = false;
    private int ChildSelectId = 0;

    private String PARENT_ID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddSponsorOrMentorActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_ward_sponsor, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvChildLogin = view.findViewById(R.id.tvChildLogin);
        tvAverageGrade = view.findViewById(R.id.tvAverageGrade);
        tvGradeCount = view.findViewById(R.id.tvGradeCount);
        tvAmountOfPayments = view.findViewById(R.id.tvAmountOfPayments);
        civChildAvatar = view.findViewById(R.id.civMentorAvatar);
        civParentAvatar = view.findViewById(R.id.civParentAvatar);
        cvSelectChild = view.findViewById(R.id.cvSelectChild);
        cvSelectChild.setOnClickListener(this);
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
        cbCondition1 = view.findViewById(R.id.cbCondition1);
        cbCondition1.setOnClickListener(this);
        cbCondition2 = view.findViewById(R.id.cbCondition2);
        cbCondition2.setOnClickListener(this);

        btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        modelParentChildLists = activity.getParentChildLists();

        LessonsListAdapter = new GetChildLessonsListAdapter(activity);

        SELECT_LESSON_ID = new String[4];
        SELECT_LESSON_NAME = new String[4];

        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
        String avatar = activity.login.loadUserDate(LoginKey.AVATAR);
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civParentAvatar);
        else civParentAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
    }

    private void LoadLessonsList(){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, mChild.getChildId());

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
                    if (result.equals("0")) { }
                    else {
                        Type founderListType = new TypeToken<ArrayList<ModelGetChildLessons>>() {}.getType();
                        ArrayList<ModelGetChildLessons> lessonsList = new Gson().fromJson(result, founderListType);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //LessonsListAdapter.updateList(lessonsList);
                            }
                        });
                    }
                }
            }
        });
    }


    ///////    SEND    ///////
    private void onSend() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.ParentId, PARENT_ID);
        jsonData.addProperty(F.SponsorId, mSponsor.getUserId());
        jsonData.addProperty(F.ChildId, mChild.getChildId());
        jsonData.addProperty("LessonId1", SELECT_LESSON_ID[0]);
        jsonData.addProperty("LessonId2", SELECT_LESSON_ID[1]);
        jsonData.addProperty("LessonId3", SELECT_LESSON_ID[2]);
        jsonData.addProperty("LessonId4", SELECT_LESSON_ID[3]);
        jsonData.addProperty("AverageGrade", tvAverageGrade.getText().toString());
        jsonData.addProperty("CountGrade", tvGradeCount.getText().toString());
        jsonData.addProperty("Reward", tvAmountOfPayments.getText().toString().replace(" ", ""));

        String msg = "Здравствуйте, хочу предложить спонсировать моего ребенка - " + mChild.getChildLogin() + ".\n\n" +
                "Условие на первую неделю: \n" +
                "1. Достичь среднего балла выше, чем " + tvAverageGrade.getText() + "\n" +
                "2. Получить не менее " + tvGradeCount.getText() + " оценок;\n\n" +
                "По следующим предметам:\n";
        if (!stringIsNullOrEmpty(SELECT_LESSON_NAME[0])) msg += "- " + SELECT_LESSON_NAME[0] + ";\n";
        if (!stringIsNullOrEmpty(SELECT_LESSON_NAME[1])) msg += "- " + SELECT_LESSON_NAME[1] + ";\n";
        if (!stringIsNullOrEmpty(SELECT_LESSON_NAME[2])) msg += "- " + SELECT_LESSON_NAME[2] + ";\n";
        if (!stringIsNullOrEmpty(SELECT_LESSON_NAME[3])) msg += "- " + SELECT_LESSON_NAME[3] + ";\n";
        msg += "\nСрок: в течении 6 дней, после принятия заявки.\n\n" +
                "Вознаграждение: " + tvAmountOfPayments.getText() + " тенге;\n";

        jsonData.addProperty(F.SourceId, PARENT_ID);
        jsonData.addProperty(F.TargetId, mSponsor.getUserId());
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
                                Intent intent = new Intent(activity, ParentActivity.class);
                                startActivity(intent);
                                activity.finish();
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

    public void setWardData(ModelMentorSponsorRoom m) {
        this.mSponsor = m;
    }

    private void onSelectChildClick() {
        if (!IsFirstChildSelect) {
            ChildSelectId = 0;
            IsFirstChildSelect = true;
        }
        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_child_select_child, null, false);
        dialog.setContentView(view);
        WardListAdapter adapter = new WardListAdapter(activity, modelParentChildLists, ChildSelectId, true);
        RecyclerView rvWard = view.findViewById(R.id.rvWard);
        rvWard.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvWard.setAdapter(adapter);
        dialog.show();
        adapter.setOnItemClickListener(new WardListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ModelParentChildList m) {
                dialog.dismiss();
                if (m != mChild){
                    dAverageGrade[0] = 0;
                    tvAverageGrade.setText("-");
                    tvGradeCount.setText("-");
                    for (int i = 0; i < 4; i++){
                        tvSelectLessonLabel[i].setText("");
                        tvSelectLessonImg[i].setBackgroundResource(R.drawable.img_arrow_down);
                    }
                    SELECT_LESSON_COUNT[0] = 0;
                    cbCondition1.setChecked(false);
                    cbCondition2.setChecked(false);
                    IsBTNSendEnable();
                }
                mChild = m;
                LoadLessonsList();
                ChildSelectId = position;

                String avatar = m.getChildAvatar();
                if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civChildAvatar);
                else civChildAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));

                civChildAvatar.setVisibility(View.VISIBLE);
                tvChildLogin.setText(m.getChildLogin());
            }
        });
    }

    private void IsBTNSendEnable() {
        if (SELECT_LESSON_COUNT[0] > 1 && cbCondition1.isChecked() && cbCondition2.isChecked()){
            btnSend.setEnabled(true);
            btnSend.setBackgroundResource(R.drawable.btn_background_purple);
        }
        else {
            btnSend.setEnabled(false);
            btnSend.setBackgroundResource(R.drawable.btn_background_purple_alpha);
        }
    }

    private void LessonSelectClick(int number) {
        if (mChild == null) {
            activity.alert.onToast("Выберите ребенка!");
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
//                    SELECT_LESSON_ID[number] = m.getLessonId();
//                    SELECT_LESSON_NAME[number] = m.getLessonName();
//                    SELECT_LESSON_COUNT[0] = 0;
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
//                        double AverageGrade = (double) Convert.Str2Double(m.getAverageGrade());
//                        tvSelectLessonImg[number].setText(m.getAverageGrade());
//                        if (AverageGrade < 3)
//                            tvSelectLessonImg[number].setBackgroundResource(R.drawable.img_oval_red);
//                        else if (AverageGrade >= 3 && AverageGrade < 4)
//                            tvSelectLessonImg[number].setBackgroundResource(R.drawable.img_oval_yellow);
//                        else if (AverageGrade >= 4 && AverageGrade < 5)
//                            tvSelectLessonImg[number].setBackgroundResource(R.drawable.img_oval_green);
//                        else if (AverageGrade == 5)
//                            tvSelectLessonImg[number].setBackgroundResource(R.drawable.img_oval_purple);
//
//                        dAverageGrade[0] += AverageGrade;
//                        tvAverageGrade.setText(Convert.Double2Str(dAverageGrade[0] / SELECT_LESSON_COUNT[0], "#.#"));
//                        tvGradeCount.setText(String.valueOf(SELECT_LESSON_COUNT[0] * 2));
//                    }
//                    else {
//                        tvSelectLessonImg[number].setText("-");
//                        tvSelectLessonImg[number].setBackgroundResource(R.drawable.background_oval_gray);
//                        dAverageGrade[0] += 3.5;
//                        tvAverageGrade.setText(Convert.Double2Str(dAverageGrade[0] / SELECT_LESSON_COUNT[0], "#.#"));
//                        tvGradeCount.setText(String.valueOf(SELECT_LESSON_COUNT[0] * 2));
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
            case R.id.cvSelectChild:
                onSelectChildClick();
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
            case R.id.cbCondition1:
                IsBTNSendEnable();
                break;
            case R.id.cbCondition2:
                IsBTNSendEnable();
                break;
            case R.id.btnSend:
                onSend();
                break;
            case R.id.ivBack:
                onBack();
                break;
        }
    }

    private void onBack() {
        activity.onBack();
    }
}
