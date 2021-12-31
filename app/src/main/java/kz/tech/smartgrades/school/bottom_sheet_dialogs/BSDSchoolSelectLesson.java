package kz.tech.smartgrades.school.bottom_sheet_dialogs;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades._System;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.root.models.ModelLesson;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolAddContactLessonsListAdapterV2;
import kz.tech.smartgrades.school.models.ModelTeacherProfileClasses;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetLessons;


public class BSDSchoolSelectLesson extends BottomSheetDialog implements View.OnClickListener, SchoolAddContactLessonsListAdapterV2.OnItemCLickListener {

    private SchoolActivity activity;
    private String className;
    private ModelTeacherProfileClasses modelClass;

    private ImageView ivSearch;
    private ImageView ivCancel;
    private RecyclerView rvLessonsList;
    private CardView cvSearch;
    private TextView tvLabel;
    private EditText etSearch;

    private ArrayList<ModelLesson> Lessons;
    private ArrayList<ModelLesson> LessonsSelected;

    private SchoolAddContactLessonsListAdapterV2 LessonsListAdapter;

    private BSDSchoolSelectLesson.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onAddLessonClick(ModelLesson m, ModelTeacherProfileClasses modelClass);
    }

    public void setOnItemClickListener(BSDSchoolSelectLesson.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BSDSchoolSelectLesson(@NonNull SchoolActivity activity, ModelTeacherProfileClasses modelClass) {
        super(activity);
        this.activity = activity;
        this.modelClass = modelClass;
        this.className = modelClass.getName();
        this.LessonsSelected = modelClass.getLessons();

        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_lesson_list, null, false);
        setContentView(view);

        View bottomSheet = findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        bottomSheet.setLayoutParams(layoutParams);
        layoutParams.height = (int)(_System.getWindowHeight(activity) * 0.8);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        initViews(view);
        loadLessons();
    }

    private void loadLessons() {
        String SOAP = SoapRequest(func_GetLessons, null);
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
                            Lessons = new Gson().fromJson(result,
                                    new TypeToken<ArrayList<ModelLesson>>(){
                                    }.getType());
                            setLessons();
                        }
                    });
                }
            }
        });
    }

    private void setLessons() {
        ArrayList<ModelLesson> sortedLessons = new ArrayList<>();
        sortedLessons.addAll(Lessons);
        if(!listIsNullOrEmpty(LessonsSelected) && !listIsNullOrEmpty(Lessons))
            for(ModelLesson _lessonSelected : Lessons)
                for(ModelLesson _lessonClass : LessonsSelected)
                    if(_lessonClass.getLessonId().equals(_lessonSelected.getLessonId()))
                        sortedLessons.remove(_lessonSelected);
        LessonsListAdapter.UpdateData(sortedLessons);
    }

    private void initViews(View view) {

        ivSearch = view.findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(this);
        ivCancel = view.findViewById(R.id.ivCancel);
        cvSearch = view.findViewById(R.id.cvSearch);
        tvLabel = view.findViewById(R.id.tvLabel);
        tvLabel.setText(activity.getResources().getString(R.string.Add_lesson_to) + " " + className);
        ivCancel.setOnClickListener(this);
        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                LessonsListAdapter.filter(editable.toString());
            }
        });

        LessonsListAdapter = new SchoolAddContactLessonsListAdapterV2();
        rvLessonsList = view.findViewById(R.id.rvLessonsList);
        rvLessonsList.setLayoutManager(new LinearLayoutManager(activity));
        LessonsListAdapter.setOnItemCLickListener(this);
        rvLessonsList.setAdapter(LessonsListAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivSearch:
                onSearch();
                break;
            case R.id.ivCancel:
                onCancel();
                break;
        }
    }

    private void onCancel() {
        tvLabel.setVisibility(View.VISIBLE);

        if(ivSearch.getVisibility() == View.VISIBLE) return;
        etSearch.setText(null);
        activity.alert.hideKeyboard(activity);
        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        cvSearch.setLayoutParams(params);
        ivSearch.setVisibility(View.VISIBLE);
        etSearch.setVisibility(View.GONE);
        ivCancel.setVisibility(View.GONE);
    }

    private void onSearch() {
        tvLabel.setVisibility(View.GONE);

        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = 0;
        cvSearch.setLayoutParams(params);
        ivSearch.setVisibility(View.GONE);
        etSearch.setVisibility(View.VISIBLE);
        etSearch.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        ivCancel.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLessonClick(ModelLesson m) {
        onItemClickListener.onAddLessonClick(m, modelClass);
        activity.alert.hideKeyboard(activity);
        dismiss();
    }
}