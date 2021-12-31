package kz.tech.smartgrades.mentor.dialog;

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
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
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

public class MentorADAddClass extends AlertDialog implements View.OnClickListener{

    private AlertDialog dialog;
    private AppCompatActivity activity;
    private TextView tvTitle, tvCancel, tvAdd;
    private EditText etClassName;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onOkClick(String Name);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public MentorADAddClass(AppCompatActivity activity){
        super(activity);
        this.activity = activity;

        View view = getLayoutInflater().inflate(R.layout.ad_mentor_add_class, null);
        Builder builder = new Builder(activity).setView(view);

        initViews(view);

        dialog = this;
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    private void initViews(View view){
        tvTitle = view.findViewById(R.id.tvTitle);

        etClassName = view.findViewById(R.id.etClassName);
        etClassName.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3){
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
            }
            @Override
            public void afterTextChanged(Editable arg0){
                isOkEnable();
            }
        });

        tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        tvAdd = view.findViewById(R.id.tvAdd);
        tvAdd.setOnClickListener(this);
    }

    private void isOkEnable(){
        if(etClassName.getText().toString().isEmpty()){
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
    public void onClick(View view){
        switch(view.getId()){
            case R.id.tvCancel:
                onCancelClick();
                break;
            case R.id.tvAdd:
                onOkClick();
                break;
        }
    }
    private void onCancelClick(){
        dialog.dismiss();
    }
    private void onOkClick(){
        dialog.dismiss();
        if(onItemClickListener == null) return;
        onItemClickListener.onOkClick(etClassName.getText().toString());
    }

}
