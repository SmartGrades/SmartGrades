package kz.tech.smartgrades.mentor.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.SelectLessonsForAddedMentorAdapter;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolLessons;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_AddLesson;
import static kz.tech.smartgrades._Web.func_GetLessons;


public class BSDSelectLesson extends BottomSheetDialog implements View.OnClickListener, SelectLessonsForAddedMentorAdapter.OnItemClickListener {

    private MentorActivity activity;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(ModelSchoolLessons m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private SelectLessonsForAddedMentorAdapter adapter;
    private ImageView ivBack;
    private Button btnAddNewLesson;
    private Button btnSelectLesson;
    private TextView tvCleanList;

    private String UserId;


    public BSDSelectLesson(MentorActivity activity) {
        super(activity);
        this.activity = activity;
        this.UserId = activity.login.loadUserDate(LoginKey.ID);

        View view = getLayoutInflater().inflate(R.layout.bsd_select_lesson, null, false);
        setContentView(view);

        initViews(view);
        onLoadLessonsList();

        show();
    }

    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        tvCleanList = view.findViewById(R.id.tvCleanList);

        btnAddNewLesson = view.findViewById(R.id.btnAdd);
        btnAddNewLesson.setOnClickListener(this);

        RecyclerView rvLessonsList = view.findViewById(R.id.rvLessonsList);
        rvLessonsList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        adapter = new SelectLessonsForAddedMentorAdapter(activity);
        rvLessonsList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    private void onLoadLessonsList(){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, UserId);

        String SOAP = SoapRequest(func_GetLessons, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<ModelSchoolLessons> SchoolLessons = new Gson().fromJson(result, new TypeToken<ArrayList<ModelSchoolLessons>>(){}.getType());
                            if (SchoolLessons.size()>0) {
                                tvCleanList.setVisibility(View.GONE);
                                adapter.updateData(SchoolLessons);
                            }
                        }
                    });
                }
            }
        });
    }

    private void onAddNewLessonClick(){
        final boolean[] isOkEnable = {false};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View vLayout = getLayoutInflater().inflate(R.layout.ad_add_new_lesson, null);
        builder.setView(vLayout);
        AlertDialog adAddNewLesson = builder.create();
        adAddNewLesson.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        EditText etLessonsName = vLayout.findViewById(R.id.etLessonsName);
        TextView tvCancel = vLayout.findViewById(R.id.tvCancel);
        TextView tvOk = vLayout.findViewById(R.id.tvOk);

        etLessonsName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable editable) {
                isOkEnable[0] = !editable.toString().isEmpty();
                if (isOkEnable[0]) tvOk.setTextColor(activity.getResources().getColor(R.color.blue_sea));
                else tvOk.setTextColor(activity.getResources().getColor(R.color.gray_default));
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adAddNewLesson.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOkEnable[0]) return;
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.LessonName, etLessonsName.getText().toString());

                String SOAP = SoapRequest(func_AddLesson, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = _Web.XMLToJson(response.body().string());
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (answer.isSuccess()) {
                                        onLoadLessonsList();
                                        adAddNewLesson.dismiss();
                                    }
                                    activity.alert.onToast(answer.getMessage());
                                }
                            });
                        }
                    }
                });
            }
        });

        adAddNewLesson.show();
    }

    private void onBack() {
        dismiss();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;

            case R.id.btnAdd:
                onAddNewLessonClick();
                break;
        }
    }

    @Override
    public void onItemClick(ModelSchoolLessons m) {
        dismiss();
        if (onItemClickListener == null) return;
        onItemClickListener.onClick(m);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();
        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {
                final InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                View focus = getCurrentFocus();
                if (focus != null) {
                    im.hideSoftInputFromWindow(focus.getWindowToken(), 0);
                }
            }

        }
        return super.dispatchTouchEvent(ev);
    }
}