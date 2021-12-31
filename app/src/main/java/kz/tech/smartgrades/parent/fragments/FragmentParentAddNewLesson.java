package kz.tech.smartgrades.parent.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.bottom_sheet_dialogs.BSDSetupRewardForGrades;
import kz.tech.smartgrades.parent.model.ModelGradesSettings;
import kz.tech.smartgrades.root.login.LoginKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_ParentAddLessonForChild;

public class FragmentParentAddNewLesson extends Fragment implements View.OnClickListener{

    private ParentActivity activity;
    private String PARENT_ID;

    private ImageView ivBack;
    private CircleImageView civChildAvatar;

    private EditText etLessonName, etLessonDescription;
    private CardView cvSettingSmartGrades;
    private TextView tvSelectSettingSmartGrades;

    private boolean[] isButtonEnable = new boolean[3];
    private Button btnSend;

    private ModelGradesSettings modelRewardForGeades;

    private String ChildId;


    public static FragmentParentAddNewLesson newInstance(String childId) {
        FragmentParentAddNewLesson fragment = new FragmentParentAddNewLesson();
        Bundle bundle = new Bundle();
        bundle.putString("ChildId", childId);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
        if (getArguments() != null) ChildId = getArguments().getString("ChildId");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_add_new_lesson, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        civChildAvatar = view.findViewById(R.id.civMentorAvatar);
        etLessonName = view.findViewById(R.id.etLessonName);
        etLessonDescription = view.findViewById(R.id.etLessonDescription);
        cvSettingSmartGrades = view.findViewById(R.id.cvSettingSmartGrades);
        cvSettingSmartGrades.setOnClickListener(this);
        tvSelectSettingSmartGrades = view.findViewById(R.id.tvSelectSettingSmartGrades);

        etLessonName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0){
                    isButtonEnable[0] = true;
                    isButtonEnabled();
                }
            }
        });

    }

    private void isButtonEnabled() {
        if (isButtonEnable[0] && isButtonEnable[1]) {
            btnSend.setEnabled(true);
            btnSend.setBackgroundResource(R.drawable.background_rectangle_purple);
        } else {
            btnSend.setEnabled(false);
            btnSend.setBackgroundResource(R.drawable.background_rectangle_gray);
        }
    }

    private void onSelectMentorOfGroup() {
        /*FrameLayout viewGroup = (FrameLayout) activity.findViewById(R.id.llParentsAddNewLesson);
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pw_parent_add_new_lesson_parents_list, viewGroup);
        PopupWindow popupWindow = new PopupWindow(activity);
        popupWindow.setContentView(view);
        popupWindow.setWidth(cvMentorOfGroup.getWidth());
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        Point p = getLocationOnScreen(cvMentorOfGroup);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, p.x, p.y + cvMentorOfGroup.getHeight());

        ParentsListAdapter parentsListAdapter = new ParentsListAdapter(activity);
        RecyclerView rvParentsList = view.findViewById(R.id.rvParentsList);
        rvParentsList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvParentsList.setAdapter(parentsListAdapter);

        ModelUserData m = new ModelUserData();
        m.setAvatar(activity.login.loadUserDate(LoginKey.AVATAR));
        m.setFirstName(activity.login.loadUserDate(LoginKey.FIRST_NAME));
        m.setLastName(activity.login.loadUserDate(LoginKey.LAST_NAME));
        ArrayList<ModelUserData> list = new ArrayList<>();
        list.add(m);
        parentsListAdapter.updateData(list);

        parentsListAdapter.setOnItemCLickListener(new ParentsListAdapter.OnItemCLickListener() {
            @Override
            public void onItemClick(ModelUserData m) {
                popupWindow.dismiss();
                String avatar = m.getAvatar();
                if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).fit().centerInside().into(civAvatar);
                else civAvatar.setBackgroundResource(R.drawable.img_default_avatar);
                civAvatar.setVisibility(View.VISIBLE);
                tvSelectMentorOfGroup.setText(m.getFirstName() + " " + m.getLastName());
                isButtonEnable[1] = true;
                isButtonEnabled();
            }
        });*/
    }

    private void onBack() {
        activity.onRemoveFragment(3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.cvSettingSmartGrades:
                onSettingSmartGrades();
                break;
            case R.id.btnSend:
                onSend();
                break;
        }
    }

    private void onSend() {
        JsonObject json = new JsonObject();
        json.addProperty(F.ParentId, PARENT_ID);
        json.addProperty(F.ChildId, ChildId);
        json.addProperty("LessonName", etLessonName.getText().toString());
        json.addProperty("LessonDescription", etLessonDescription.getText().toString());
        json.addProperty("RewardFor2", modelRewardForGeades.getRewardFor2());
        json.addProperty("RewardFor3", modelRewardForGeades.getRewardFor3());
        json.addProperty("RewardFor4", modelRewardForGeades.getRewardFor4());
        json.addProperty("RewardFor5", modelRewardForGeades.getRewardFor5());
        json.addProperty("RewardForAbsent", modelRewardForGeades.getRewardForN());

        String SOAP = SoapRequest(func_ParentAddLessonForChild, json.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {}
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.alert.onToast(answer.getMessage());
                            if (answer.isSuccess()) activity.presenter.onStartPresenter();
                            activity.onHideFragmentParentAddNewLesson();
                        }
                    });
                }
            }
        });
    }

    private void onSettingSmartGrades() {
        BSDSetupRewardForGrades dialog = new BSDSetupRewardForGrades(activity, "Настройка умных оценок", new ModelGradesSettings());
        dialog.show();
        dialog.setOnItemClickListener(new BSDSetupRewardForGrades.OnItemClickListener() {
            @Override
            public void onClick(ModelGradesSettings m) {
                dialog.dismiss();
                modelRewardForGeades = m;
                tvSelectSettingSmartGrades.setText("2 = " + m.getRewardFor2() + "; 3 = " + m.getRewardFor3() +
                        "; 4 = " + m.getRewardFor4() + "; 5 = " + m.getRewardFor5());
                isButtonEnable[1] = true;
                isButtonEnabled();
            }

            @Override
            public void onCancel() {

            }
        });
    }
}