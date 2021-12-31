package kz.tech.smartgrades.parent.popup;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.annotations.NotNull;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.model.ModelMentorList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_ChildDeleteMentor;

public class PWRemoveProve extends PopupWindow implements View.OnClickListener{

    private ParentActivity activity;
    private ModelMentorList mMentor;
    private String CHILD_LESSON_ID;

    private TextView tvCancel;
    private TextView tvRemove;

    public PWRemoveProve(int width, int height, ParentActivity activity, ModelMentorList mMentor, String CHILD_LESSON_ID) {
        super(width, height);
        this.activity = activity;
        this.mMentor = mMentor;
        this.CHILD_LESSON_ID = CHILD_LESSON_ID;
        LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pw_want_remove_mentor, null);
        TextView tvErrorMessage = view.findViewById(R.id.tvErrorMessage);
        tvErrorMessage.setText(mMentor.getFirstName() + " " + mMentor.getLastName());
        setContentView(view);
        initViews(view);
    }

    private void initViews(View view) {
        tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        tvRemove = view.findViewById(R.id.tvRemove);
        tvRemove.setOnClickListener(this);
    }

    private void removeMentor() {
        JsonObject json = new JsonObject();
        json.addProperty(F.ChildLessonId, CHILD_LESSON_ID);
        json.addProperty(F.MentorId, mMentor.getId());

        String SOAP = SoapRequest(func_ChildDeleteMentor, json.toString());
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
                            ModelAnswer answer =
                                    new Gson().fromJson(result, ModelAnswer.class);
                            if (answer.isSuccess()) {
                                activity.alert.onToast(answer.getMessage());
                                activity.onRemoveMentor(mMentor);
                                activity.updatePresenter();
                            } else {
                                activity.alert.onToast(answer.getMessage());
                            }
                        }
                    });
                }
            }
        });
        dismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                dismiss();
                break;
            case  R.id.tvRemove:
                removeMentor();
                break;
        }
    }

}
