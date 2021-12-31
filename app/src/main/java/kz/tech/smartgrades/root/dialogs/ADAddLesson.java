package kz.tech.smartgrades.root.dialogs;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.root.adapters.LessonsListAdapter;
import kz.tech.smartgrades.root.models.ModelLesson;
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

public class ADAddLesson extends AlertDialog implements View.OnClickListener, kz.tech.smartgrades.root.adapters.LessonsListAdapter.OnItemCLickListener{

    private AlertDialog dialog;
    private AppCompatActivity activity;
    private TextView tvCancel, tvAdd, tvSelectLesson;

    private ModelLesson mSelectLesson;
    private ArrayList<ModelLesson> LessonsList;
    private ArrayList<ModelLesson> AddedLessons;
    private kz.tech.smartgrades.root.adapters.LessonsListAdapter LessonsListAdapter;
    private RecyclerView rvLessonsList;

    private ConstraintLayout clLabel;
    private ConstraintLayout clSearch;
    private EditText etSearch;
    private ImageView ivSearchArrow;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onOkClick(ModelLesson model);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public ADAddLesson(AppCompatActivity activity){
        super(activity);
        this.activity = activity;

        View view = getLayoutInflater().inflate(R.layout.ad_add_lesson, null);
        Builder builder = new Builder(activity).setView(view);

        initViews(view);

        dialog = this;
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    public ADAddLesson(AppCompatActivity activity, ArrayList<ModelLesson> AddedLessons){
        super(activity);
        this.activity = activity;
        this.AddedLessons = AddedLessons;

        View view = getLayoutInflater().inflate(R.layout.ad_add_lesson, null);
        Builder builder = new Builder(activity).setView(view);

        initViews(view);

        dialog = this;
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    private void initViews(View view){
        tvSelectLesson = view.findViewById(R.id.tvSelectLesson);
        clLabel = view.findViewById(R.id.clLabel);
        clLabel.setOnClickListener(this);
        clSearch = view.findViewById(R.id.clSearch);
        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3){ }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){ }
            @Override
            public void afterTextChanged(Editable arg0){
                LessonsListAdapter.filter(arg0.toString());
            }
        });
        ivSearchArrow = view.findViewById(R.id.ivSearchArrow);
        ivSearchArrow.setOnClickListener(this);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        tvAdd = view.findViewById(R.id.tvAdd);
        tvAdd.setOnClickListener(this);
        LessonsListAdapter = new LessonsListAdapter();
        LessonsListAdapter.setOnItemCLickListener(this);
        rvLessonsList = view.findViewById(R.id.rvLessonsList);
        rvLessonsList.setLayoutManager(new LinearLayoutManager(activity));
        rvLessonsList.setAdapter(LessonsListAdapter);
    }

    private void onCancelClick(){
        dialog.dismiss();
    }
    private void onOkClick(){
        dialog.dismiss();
        if(onItemClickListener == null) return;
        onItemClickListener.onOkClick(mSelectLesson);
    }

    private void onShowLessonListClick(){
        clLabel.setVisibility(View.GONE);
        clSearch.setVisibility(View.VISIBLE);

        if(listIsNullOrEmpty(LessonsList)){
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
                                if(!result.equals("null")){
                                    LessonsList = new Gson().fromJson(result, new TypeToken<ArrayList<ModelLesson>>(){
                                    }.getType());
                                    LessonsListAdapter.updateData(LessonsList);
                                    clLabel.setVisibility(View.GONE);
                                    clSearch.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                }
            });
        }
        else{
            LessonsListAdapter.updateData(LessonsList);
            clLabel.setVisibility(View.GONE);
            clSearch.setVisibility(View.VISIBLE);
        }
    }
    private void onHideLessonsListClick(){
        etSearch.setText("");
        LessonsListAdapter.ClearData();
        clLabel.setVisibility(View.VISIBLE);
        clSearch.setVisibility(View.GONE);
    }

    private void isOkEnable(){
        if(mSelectLesson == null){
            tvAdd.setEnabled(false);
            tvAdd.setTextColor(activity.getResources().getColor(R.color.gray_default));
            tvAdd.setBackground(null);
            tvAdd.setPadding(10, 10, 10, 10);
        }
        else{
            tvAdd.setEnabled(true);
            tvAdd.setTextColor(activity.getResources().getColor(R.color.white));
            tvAdd.setBackgroundResource(R.drawable.background_square_blue_sea);
            tvAdd.setPadding(10, 10, 10, 10);
        }
    }

    @Override
    public void onItemClick(ModelLesson m){
        mSelectLesson = m;
        isOkEnable();
        LessonsListAdapter.ClearData();
        tvSelectLesson.setText(m.getLessonName());
        onHideLessonsListClick();
    }
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.clLabel:
                onShowLessonListClick();
                break;
            case R.id.ivSearchArrow:
                onHideLessonsListClick();
                break;
            case R.id.tvCancel:
                onCancelClick();
                break;
            case R.id.tvAdd:
                onOkClick();
                break;
        }
    }

}
