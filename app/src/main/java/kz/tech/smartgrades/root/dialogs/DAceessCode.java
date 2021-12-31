package kz.tech.smartgrades.root.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.AuthActivity;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.S.CHILD;
import static kz.tech.smartgrades.S.MENTOR;
import static kz.tech.smartgrades.S.PARENT;
import static kz.tech.smartgrades.S.SCHOOL;
import static kz.tech.smartgrades.S.INVESTOR;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetUserAccess;

public class DAceessCode extends Dialog implements View.OnClickListener{

    private SchoolActivity schoolActivity;
    private MentorActivity mentorActivity;
    private ParentActivity parentActivity;
    private ChildActivity childActivity;
    private SponsorActivity sponsorActivity;
    private AppCompatActivity activity;

    private ImageView[] ivDot1 = new ImageView[4];
    private LinearLayout llDots2;
    private TextView tvTitle, tvNum1, tvNum2, tvNum3, tvNum4, tvNum5,
            tvNum6, tvNum7, tvNum8, tvNum9, tvNum0;

    private String Code = "";

    ModelUser userData;

    public DAceessCode(AppCompatActivity activity, String type, ModelUser userData){
        super(activity, R.style.DefaultCustomDialog);
        this.activity = activity;
        this.userData = userData;
        if(type.equals(SCHOOL)) schoolActivity = (SchoolActivity) activity;
        if(type.equals(MENTOR)) mentorActivity = (MentorActivity) activity;
        if(type.equals(PARENT)) parentActivity = (ParentActivity) activity;
        if(type.equals(CHILD)) childActivity = (ChildActivity) activity;
        if(type.equals(INVESTOR)) sponsorActivity = (SponsorActivity) activity;
        View view = getLayoutInflater().inflate(R.layout.fragment_access_enter, null);
        setContentView(view);
        initViews(view);
        show();
    }
    private void initViews(View view){
        tvTitle = view.findViewById(R.id.tvAccessTitle);

        ivDot1[0] = view.findViewById(R.id.ivDot1_1);
        ivDot1[1] = view.findViewById(R.id.ivDot1_2);
        ivDot1[2] = view.findViewById(R.id.ivDot1_3);
        ivDot1[3] = view.findViewById(R.id.ivDot1_4);

        tvNum1 = view.findViewById(R.id.tvNum1);
        tvNum1.setOnClickListener(this);
        tvNum2 = view.findViewById(R.id.tvNum2);
        tvNum2.setOnClickListener(this);
        tvNum3 = view.findViewById(R.id.tvNum3);
        tvNum3.setOnClickListener(this);
        tvNum4 = view.findViewById(R.id.tvNum4);
        tvNum4.setOnClickListener(this);
        tvNum5 = view.findViewById(R.id.tvNum5);
        tvNum5.setOnClickListener(this);
        tvNum6 = view.findViewById(R.id.tvNum6);
        tvNum6.setOnClickListener(this);
        tvNum7 = view.findViewById(R.id.tvNum7);
        tvNum7.setOnClickListener(this);
        tvNum8 = view.findViewById(R.id.tvNum8);
        tvNum8.setOnClickListener(this);
        tvNum9 = view.findViewById(R.id.tvNum9);
        tvNum9.setOnClickListener(this);
        tvNum0 = view.findViewById(R.id.tvNum0);
        tvNum0.setOnClickListener(this);
    }

    private void onAccessCode(String code){
        Code += code;
        ivDot1[Code.length() - 1].setBackground(activity.getResources().getDrawable(R.drawable.view_oval_green));
        if(Code.length() == 4){
            JsonObject jsonData = new JsonObject();
            jsonData.addProperty(F.UserId, userData.getUserId());
            jsonData.addProperty(F.Code, Code);

            String SOAP = SoapRequest(func_GetUserAccess, jsonData.toString());
            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
            Request request = new Request.Builder().url(URL).post(body).build();
            HttpClient.newCall(request).enqueue(new Callback(){
                @Override
                public void onFailure(final Call call, IOException e){}
                @Override
                public void onResponse(Call call, final Response response) throws IOException{
                    if(response.isSuccessful()){
                        String result = _Web.XMLToJson(response.body().string());
                        ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                        activity.runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                if(!answer.isSuccess()){
                                    Code = "";
                                    tvTitle.setText(R.string.Invalid_code_please_try_again);
                                    for(ImageView imageView : ivDot1)
                                        imageView.setBackground(activity.getResources().getDrawable(R.drawable.view_oval_rectangle_dots_active));
                                }
                                else if(answer.isSuccess()){
                                    dismiss();
                                    if(schoolActivity != null){
                                        schoolActivity.login.deleteUserDate();
                                        schoolActivity.login.saveUserData(userData);
                                        schoolActivity.startActivity(new Intent(schoolActivity, AuthActivity.class));
                                        schoolActivity.finish();
                                    }
                                    else if(mentorActivity != null){
                                        mentorActivity.login.deleteUserDate();
                                        mentorActivity.login.saveUserData(userData);
                                        mentorActivity.startActivity(new Intent(mentorActivity, AuthActivity.class));
                                        mentorActivity.finish();
                                    }
                                    else if(parentActivity != null){
                                        parentActivity.login.deleteUserDate();
                                        parentActivity.login.saveUserData(userData);
                                        parentActivity.startActivity(new Intent(parentActivity, AuthActivity.class));
                                        parentActivity.finish();
                                    }
                                    else if(childActivity != null){
                                        childActivity.login.deleteUserDate();
                                        childActivity.login.saveUserData(userData);
                                        childActivity.startActivity(new Intent(childActivity, AuthActivity.class));
                                        childActivity.finish();
                                    }
                                    else if(sponsorActivity != null){
                                        sponsorActivity.login.deleteUserDate();
                                        sponsorActivity.login.saveUserData(userData);
                                        sponsorActivity.startActivity(new Intent(sponsorActivity, AuthActivity.class));
                                        sponsorActivity.finish();
                                    }
                                }
                            }
                        });

                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.tvNum0:
                onAccessCode("0");
                break;
            case R.id.tvNum1:
                onAccessCode("1");
                break;
            case R.id.tvNum2:
                onAccessCode("2");
                break;
            case R.id.tvNum3:
                onAccessCode("3");
                break;
            case R.id.tvNum4:
                onAccessCode("4");
                break;
            case R.id.tvNum5:
                onAccessCode("5");
                break;
            case R.id.tvNum6:
                onAccessCode("6");
                break;
            case R.id.tvNum7:
                onAccessCode("7");
                break;
            case R.id.tvNum8:
                onAccessCode("8");
                break;
            case R.id.tvNum9:
                onAccessCode("9");
                break;
        }
    }
}
