package kz.tech.smartgrades.parent.dialogs;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.parent.ParentActivity;
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
import static kz.tech.smartgrades._Web.func_AcceptInterFormOfSponsorship;
import static kz.tech.smartgrades._Web.func_RejectInterFormOfSponsorship;

public class ParentSponsorInterFormDialog extends Dialog implements View.OnClickListener {

    private ParentActivity activity;
    private ModelInterForm modelInterForm;

    private TextView tvChildLogin, tvAverageGrade, tvGradeCount, tvAmountOfPayments;
    private CircleImageView civChildAvatar, civParentAvatar;
    private Button btnCancel, btnAccept;

    private ImageView ivBack;

    private CardView[] cvLesson = new CardView[4];
    private TextView[] tvSelectLessonLabel = new TextView[4];

    public ParentSponsorInterFormDialog(@NonNull ParentActivity activity, ModelInterForm modelInterForm) {
        super(activity, R.style.CustomDialog2);
        this.activity = activity;
        this.modelInterForm = modelInterForm;

        this.setCanceledOnTouchOutside(true);
        View view = getLayoutInflater().inflate(R.layout.fragment_parent_sponsor_about_inter_form, null, false);
        this.setContentView(view);

        initViews(view);
        setData();
    }
    private void initViews(View view){
        tvChildLogin = view.findViewById(R.id.tvChildLogin);
        tvAverageGrade = view.findViewById(R.id.tvAverageGrade);
        tvGradeCount = view.findViewById(R.id.tvGradeCount);
        tvAmountOfPayments = view.findViewById(R.id.tvAmountOfPayments);
        civChildAvatar = view.findViewById(R.id.civMentorAvatar);
        civParentAvatar = view.findViewById(R.id.civParentAvatar);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnAccept = view.findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(this);

        cvLesson[0] = view.findViewById(R.id.cvLesson1);
        cvLesson[0].setOnClickListener(this);
        cvLesson[1] = view.findViewById(R.id.cvLesson2);
        cvLesson[1].setOnClickListener(this);
        cvLesson[2] = view.findViewById(R.id.cvLesson3);
        cvLesson[2].setOnClickListener(this);
        cvLesson[3] = view.findViewById(R.id.cvLesson4);
        cvLesson[3].setOnClickListener(this);

        tvSelectLessonLabel[0] = view.findViewById(R.id.tvSelectLesson1Label);
        tvSelectLessonLabel[1] = view.findViewById(R.id.tvSelectLesson2Label);
        tvSelectLessonLabel[2] = view.findViewById(R.id.tvSelectLesson3Label);
        tvSelectLessonLabel[3] = view.findViewById(R.id.tvSelectLesson4Label);
    }

    private void setData() {
        String avatar = modelInterForm.getChildAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civChildAvatar);
        avatar = activity.login.loadUserDate(LoginKey.AVATAR);
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civParentAvatar);
        tvChildLogin.setText(modelInterForm.getChildFirstName() + " " + modelInterForm.getChildLastName());
        if (!stringIsNullOrEmpty(modelInterForm.getAverageGrade())) tvAverageGrade.setText(modelInterForm.getAverageGrade());
        if (!stringIsNullOrEmpty(modelInterForm.getCountGrade())) tvGradeCount.setText(modelInterForm.getCountGrade());
        if (!stringIsNullOrEmpty(modelInterForm.getReward())) tvAmountOfPayments.setText(modelInterForm.getReward());

        if (!listIsNullOrEmpty(modelInterForm.getLessons())) {
            for (int i = 0; i < modelInterForm.getLessons().size(); i++) {
                if (!stringIsNullOrEmpty(modelInterForm.getLessons().get(i).getLessonName())) {
                    cvLesson[i].setVisibility(View.VISIBLE);
                    tvSelectLessonLabel[i].setText(modelInterForm.getLessons().get(i).getLessonName());
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.btnCancel:
                onCancel();
                break;
            case R.id.btnAccept:
                onAccept();
                break;
        }
    }

    private void onAccept() {
        String SOAP = SoapRequest(func_AcceptInterFormOfSponsorship, new Gson().toJson(modelInterForm));
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
                            if (!result.equals("null")) {
                                ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                activity.alert.onToast(answer.getMessage());
                                if (answer.isSuccess()) {
                                    Dialog dialog = new Dialog(activity, R.style.CustomDialog2);
                                    View view = getLayoutInflater().inflate(R.layout.dialog_parent_success_sponsoring, null, false);
                                    dialog.setContentView(view);
                                    ImageView ivAvatar = view.findViewById(R.id.ivAvatar);
                                    TextView tvName = view.findViewById(R.id.tvName);
                                    String avatar = modelInterForm.getSourceAvatar();
                                    if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(ivAvatar);
                                    if (!(stringIsNullOrEmpty(modelInterForm.getSourceFirstName())) && !(stringIsNullOrEmpty(modelInterForm.getSourceLastName()))) {
                                        tvName.setText(modelInterForm.getSourceFirstName() + " " + modelInterForm.getSourceLastName());
                                    }
                                    CardView cvOk = view.findViewById(R.id.cvOk);
                                    cvOk.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();
                                    activity.updatePresenter();
                                    dismiss();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void onCancel() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.Id, modelInterForm.getId());

        String SOAP = SoapRequest(func_RejectInterFormOfSponsorship, jsonData.toString());
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
                            if (!result.equals("null")) {
                                ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                activity.alert.onToast(answer.getMessage());
                                if (answer.isSuccess()) {
                                    activity.updatePresenter();
                                    dismiss();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void onBack() {
        this.dismiss();
    }
}