package kz.tech.smartgrades.mentor.dialog;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.SelectLessonsForAddedMentorAdapter;
import kz.tech.smartgrades.mentor.adapters.SelectMentorGroupForAddedAdapter;
import kz.tech.smartgrades.mentor.models.ModelMentorGroups;
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
import static kz.tech.smartgrades._Web.func_GetLessons;


public class DialogMentorAddChild extends Dialog implements View.OnClickListener{

    private MentorActivity activity;
    private ModelUser mChild;
    private ModelMentorGroups modelMentorGroup;
    private ModelSchoolLessons modelSchoolLessons;

    private CircleImageView civAvatar;
    private TextView tvFullName;
    private TextView tvLogin;
    private ImageView ivIcon;
    private TextView tvSelectGroup;
    private TextView tvSelectGroupValue;
    private TextView tvSelectLessons;
    private TextView tvSelectLessonValue;
    private Button btnSend;

    private boolean isSelectLessons = false;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(ModelMentorGroups modelSchoolGroups);
    }

    public DialogMentorAddChild(@NonNull MentorActivity activity, ModelUser mChild){
        super(activity, R.style.CustomDialog2);
        this.activity = activity;
        this.mChild = mChild;
        modelSchoolLessons = new ModelSchoolLessons();

        this.setCanceledOnTouchOutside(true);
        View view = getLayoutInflater().inflate(R.layout.dialog_school_add_user, null, false);
        this.setContentView(view);

        ImageView ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        civAvatar = view.findViewById(R.id.civAvatar);
        tvFullName = view.findViewById(R.id.tvFullName);
        tvLogin = view.findViewById(R.id.tvLogin);
        ivIcon = view.findViewById(R.id.ivIcon);
        tvSelectGroup = view.findViewById(R.id.tvTitle1);
        tvSelectGroupValue = view.findViewById(R.id.tvSelectGroupValue);
        tvSelectGroupValue.setOnClickListener(this);
        tvSelectLessons = view.findViewById(R.id.tvSelectLessons);
        tvSelectLessonValue = view.findViewById(R.id.tvSelectLessonValue);
        tvSelectLessonValue.setOnClickListener(this);
        btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        String avatar = mChild.getAvatar();
        if(avatar != null && !avatar.isEmpty())
            Picasso.get().load(avatar).fit().centerInside().into(civAvatar);
        else
            (civAvatar).setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));

        tvFullName.setText(mChild.getFirstName() + " " + mChild.getLastName());
        tvLogin.setText(mChild.getLogin());
        ivIcon.setVisibility(View.GONE);
        tvSelectLessons.setText("К какому предмету относиться данный ребенок?");
        tvSelectLessonValue.setHint("Выберите предмет");
        onTernarButton();
    }

    private void onTernarButton(){
        boolean isActive = false;
        /*if (mChild.getType().equals(S.MENTOR)) {
            if (modelMentorGroup != null) isActive = true;
        } else {
            if (modelMentorGroup != null) isActive = true;
        }*/
        if(modelMentorGroup != null) isActive = true;
        btnSend.setBackgroundResource(isActive ?
                R.drawable.btn_background_purple :
                R.drawable.btn_background_purple_alpha);
        btnSend.setEnabled(isActive);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
                break;
            case R.id.tvSelectGroupValue:
                onSelectGroup();
                break;
            case R.id.tvSelectLessonValue:
                onSelectLesson();
                break;
            case R.id.btnSend:
                onSend();
                break;
        }
    }

    private void onBack(){
        this.dismiss();
    }
    private void onSelectLesson(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        View view = getLayoutInflater().inflate(R.layout.bsd_select_lesson, null, false);
        bottomSheetDialog.setContentView(view);

        SelectLessonsForAddedMentorAdapter adapter = new SelectLessonsForAddedMentorAdapter(activity);

        TextView tvCleanList = view.findViewById(R.id.tvCleanList);
        Button btnAdd = view.findViewById(R.id.btnAdd);

        btnAdd.setVisibility(View.GONE);

        RecyclerView recyclerView = view.findViewById(R.id.rvGradesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, activity.login.loadUserDate(LoginKey.ID));

        String SOAP = SoapRequest(func_GetLessons, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                String xml = response.body().string();
                if(response.code() >= 200 && response.code() <= 300){
                    String result = _Web.XMLToJson(xml);
                    if(result.equals("0")){
                    }
                    else{
                        activity.runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                Type founderListType = new TypeToken<ArrayList<ModelSchoolLessons>>(){
                                }.getType();
                                ArrayList<ModelSchoolLessons> SchoolLessons = new Gson().fromJson(result, founderListType);
                                tvCleanList.setVisibility(View.GONE);
                                adapter.updateData(SchoolLessons);
                                recyclerView.setAdapter(adapter);
                                bottomSheetDialog.show();
                            }
                        });
                    }
                }
            }
        });

        adapter.setOnItemClickListener(new SelectLessonsForAddedMentorAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(ModelSchoolLessons m){
                bottomSheetDialog.dismiss();
                modelMentorGroup.setLessonId(m.getLessonId());
                modelMentorGroup.setLessonName(m.getLessonName());
                tvSelectLessonValue.setText(m.getLessonName());
                isSelectLessons = true;
                onTernarButton();
            }
        });
        bottomSheetDialog.show();
    }
    private void onSelectGroup(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_select_group_for_added, null, false);
        bottomSheetDialog.setContentView(view);

        SelectMentorGroupForAddedAdapter adapter = new SelectMentorGroupForAddedAdapter(activity);
        RecyclerView recyclerView = view.findViewById(R.id.rvGradesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        adapter.updateData(activity.getMentorGroups());
        adapter.selectList();
        recyclerView.setAdapter(adapter);
        bottomSheetDialog.show();

        adapter.setOnItemClickListener(new SelectMentorGroupForAddedAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(ModelMentorGroups m){
                bottomSheetDialog.dismiss();
                modelMentorGroup = m;
                tvSelectGroupValue.setText(m.getGroupName());
                onTernarButton();
            }
        });
    }

    private void onSend(){
        if(onItemClickListener != null){
            onItemClickListener.onItemClick(modelMentorGroup);
        }
        onBack();
    }
}