package kz.tech.smartgrades.parent.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentChildSchoolListAdapter;
import kz.tech.smartgrades.parent.model.ModelInviteCode;
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
import static kz.tech.smartgrades._Web.func_CheckInterFormInFamilyGroup;
import static kz.tech.smartgrades._Web.func_InterFormInFamilyGroup;

public class ParentChildProfileFragment extends Fragment implements View.OnClickListener {

    private ParentActivity activity;
    private ModelUser mChild;
    private String PARENT_ID;
    private int TIMER_SECOND;

    private ImageView ivNav;
    private TextView tvName;
    private TextView tvStatus;
    private Button btnAddChild;
    private TextView tvBio;
    private TextView tvLogin;
    private RecyclerView rvSchoolList;
    private ImageView ivAvatar;
    private TextView tvSchool;
    private TextView tvIsYourChild;

    private CountDownTimer countDownTimer;

    private ParentChildSchoolListAdapter parentChildSchoolListAdapter;

    public ParentChildProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (ParentActivity) getActivity();
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_child_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void setTargetInfo() {
        ivAvatar.setImageResource(0);
        if (!stringIsNullOrEmpty(mChild.getAboutMe())) {
            tvBio.setText(mChild.getAboutMe());
        } else tvBio.setText(activity.getResources().getString(R.string.no_bio));
        tvIsYourChild.setText(getString(R.string.IsYourChild));
        tvName.setTextColor(activity.getResources().getColor(R.color.white));
        tvStatus.setTextColor(activity.getResources().getColor(R.color.white));
        tvSchool.setVisibility(View.VISIBLE);

        tvName.setText(mChild.getFirstName() + " " + mChild.getLastName());
        tvLogin.setText(mChild.getLogin());
        String avatar = mChild.getAvatarOriginal();
        if(!stringIsNullOrEmpty(avatar)){
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(ivAvatar);
            ivAvatar.getLayoutParams().height = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    321,
                    getResources().getDisplayMetrics()
            );
        }
        else{
            ivAvatar.getLayoutParams().height = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    150,
                    getResources().getDisplayMetrics()
            );
            ivAvatar.requestLayout();
            tvName.setTextColor(activity.getResources().getColor(R.color.black));
            tvStatus.setTextColor(activity.getResources().getColor(R.color.black));
        }
        if (!stringIsNullOrEmpty(mChild.getAboutMe())) {
            tvBio.setText(mChild.getAboutMe());
        }
        if (listIsNullOrEmpty(mChild.getSchoolList())) {
            tvSchool.setVisibility(View.INVISIBLE);
        }
        setChildSchools();
    }

    private void setChildSchools() {
        rvSchoolList.setVisibility(View.VISIBLE);
        rvSchoolList.setLayoutManager(new GridLayoutManager(activity, 2));
        parentChildSchoolListAdapter = new ParentChildSchoolListAdapter(activity);
        rvSchoolList.setAdapter(parentChildSchoolListAdapter);
        if (listIsNullOrEmpty(mChild.getSchoolList()))
            parentChildSchoolListAdapter.clear();
        else parentChildSchoolListAdapter.updateData(mChild.getSchoolList());
    }

    private void initViews(View view) {
        ivNav = view.findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        tvName = view.findViewById(R.id.tvName);
        tvStatus = view.findViewById(R.id.tvStatus);
        btnAddChild = view.findViewById(R.id.btnAddChild);
        btnAddChild.setOnClickListener(this);
        tvBio = view.findViewById(R.id.tvBio);
        tvLogin = view.findViewById(R.id.tvLogin);
        rvSchoolList = view.findViewById(R.id.rvSchoolList);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        tvSchool = view.findViewById(R.id.tvSchool);
        tvIsYourChild = view.findViewById(R.id.tvIsYourChild);
    }

    public void setChildModel(ModelUser mChild) {
        this.mChild = mChild;
        setTargetInfo();
        checkInvite();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivNav:
                onBack();
                break;
            case R.id.btnAddChild:
                onAddChild();
                break;
        }
    }

    private void onAddChild() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.SourceId, PARENT_ID);
        jsonData.addProperty(F.TargetId, mChild.getUserId());

        String SOAP = SoapRequest(func_InterFormInFamilyGroup, jsonData.toString());
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
                                ModelInviteCode inviteCode = new Gson().fromJson(result, ModelInviteCode.class);
                                if (inviteCode.getTime() > 0) {
                                    openCode(inviteCode.getCode(), inviteCode.getTime());
                                    setButtonYellow(inviteCode.getCode(), inviteCode.getTime());
                                    activity.updatePresenter();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public void openCode(String code, int timer) {
        View view = getLayoutInflater().inflate(R.layout.pw_family_group_code_generet, null);
        TextView tvTimer = view.findViewById(R.id.tvTimer);
        TextView tvCode = view.findViewById(R.id.tvCode);
        TextView tvNewCode = view.findViewById(R.id.tvNewCode);
        AlertDialog alertDialog = new AlertDialog.Builder(activity).setView(view).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
        tvCode.setText(code.charAt(0) + " " + code.charAt(1) + " " + code.charAt(2) + " " + code.charAt(3));
        tvNewCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                onAddChild();
            }
        });

        countDownTimer = new CountDownTimer(timer * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                String time = df.format(millisUntilFinished);
                tvTimer.setText(time);
            }
            public void onFinish() {
                tvNewCode.setVisibility(View.VISIBLE);
                tvTimer.setVisibility(View.GONE);
            }
        }.start();
    }

    private void checkInvite() {
        if (countDownTimer != null) {
            countDownTimer.onFinish();
            countDownTimer.cancel();
            countDownTimer = null;
        }

        // проверяет на наличее инвайта, если есть ставит данные на кнопку. После истечение времени возвращяет к изначальному
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.SourceId, PARENT_ID);
        jsonData.addProperty(F.TargetId, mChild.getUserId());

        String SOAP = SoapRequest(func_CheckInterFormInFamilyGroup, jsonData.toString());
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
                                ModelInviteCode inviteCode = new Gson().fromJson(result, ModelInviteCode.class);
                                //ModelInviteCode inviteCode = new Gson().fromJson(result, new TypeToken<ModelInviteCode>(){
                                //}.getType());
                                if (!stringIsNullOrEmpty(inviteCode.getInterFormId())) {
                                    tvIsYourChild.setText(R.string.You_received_an_application_from_him_to_add_to_the_family_group);
                                } else {
                                    setButtonYellow(inviteCode.getCode(), inviteCode.getTime());
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void setButtonYellow(String code, int timer) {
        btnAddChild.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_yellow_border));
        btnAddChild.setTextColor(activity.getResources().getColor(R.color.background_oval_yellow));
        btnAddChild.setClickable(false);

        countDownTimer = new CountDownTimer(timer*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                String time = df.format(millisUntilFinished);
                btnAddChild.setText(code + "\n" + time);
            }
            public void onFinish() {
                btnAddChild.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_blue_border));
                btnAddChild.setTextColor(activity.getResources().getColor(R.color.blue_sea));
                btnAddChild.setText(activity.getResources().getString(R.string.Invite_to_family_group));
                btnAddChild.setClickable(true);
            }
        }.start();
    }

    private void clearData() {
        btnAddChild.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_blue_border));
        btnAddChild.setTextColor(activity.getResources().getColor(R.color.blue_sea));
        btnAddChild.setClickable(true);
        btnAddChild.setText(getString(R.string.Invite_to_family_group));
    }

    private void onBack() {
        activity.onBackPressed();
        clearData();
    }
}