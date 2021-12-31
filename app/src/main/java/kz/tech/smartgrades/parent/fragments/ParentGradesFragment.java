package kz.tech.smartgrades.parent.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.GradesTableMonthAdapter;
import kz.tech.smartgrades.mentor.models.ModelDefaultMessages;
import kz.tech.smartgrades.mentor.models.ModelGradesTableMonth;
import kz.tech.smartgrades.mentor.models.ModelMentorChat;
import kz.tech.smartgrades.parent.adapters.DefaultMessagesAdapter;
import kz.tech.smartgrades.parent.model.ModelChat;
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
import static kz.tech.smartgrades._Web.func_GradesTable;

public class ParentGradesFragment extends Fragment implements View.OnClickListener, DefaultMessagesAdapter.OnItemClickListener, GradesTableMonthAdapter.OnItemClickListener {

    private MentorActivity activity;
    private String MENTOR_ID;

    private ImageView ivBack, ivMenu;
    private CircleImageView civAvatar;
    private TextView tvChildName, tvLessonName, tvLessonDescription, tvLessonRewardSettings;

    private RecyclerView rvGradesTable;
    private GradesTableMonthAdapter gradesTableMonthAdapter;

    private ModelMentorChat mChat;


    public static ParentGradesFragment newInstance(ModelChat modelMentorChat) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("mChildrenList", modelMentorChat);
        ParentGradesFragment fragment = new ParentGradesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MentorActivity) getActivity();
        if (getArguments() != null) {
            mChat = getArguments().getParcelable("mChildrenList");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_grades_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onSetTargetInfo();
        onLoadGrades();
    }

    private void initViews(View view) {
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivMenu = view.findViewById(R.id.ivMenu);
        ivMenu.setOnClickListener(this);

        civAvatar = view.findViewById(R.id.civAvatar);
        tvChildName = view.findViewById(R.id.tvMentorName);
        tvLessonName = view.findViewById(R.id.tvLessonName);
        tvLessonDescription = view.findViewById(R.id.tvLessonDescription);
        tvLessonRewardSettings = view.findViewById(R.id.tvLessonRewardSettings);

        gradesTableMonthAdapter = new GradesTableMonthAdapter(activity);
        //gradesTableMonthAdapter.setOnItemClickListener(this);
        rvGradesTable = view.findViewById(R.id.rvGradesTable);
        rvGradesTable.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvGradesTable.setAdapter(gradesTableMonthAdapter);
    }

    private void onSetTargetInfo() {
//        String avatar = mChat.getAvatar();
//        if (avatar != null && !avatar.isEmpty())
//            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);
//        else civAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
//
//        tvChildName.setText(mChat.getFirstName() + " " + mChat.getLastName());
//        //tvLessonName.setText(mMentorGroup.getLessonName());
    }

    private void onLoadGrades() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.TargetId, MENTOR_ID);
//        jsonData.addProperty(F.ChildId, mChat.getUserId());
        //jsonData.addProperty(F.GroupId, mMentorGroup.getGroupId());
        //jsonData.addProperty(F.LessonId, mMentorGroup.getLessonId());

        String SOAP = SoapRequest(func_GradesTable, jsonData.toString());
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
                                ArrayList<ModelGradesTableMonth> GradesTable = new Gson().fromJson(result, new TypeToken<ArrayList<ModelGradesTableMonth>>() {
                                }.getType());
                                gradesTableMonthAdapter.updateData(GradesTable);
                            }
                        }
                    });
                }
            }
        });
    }

    private void onMenu(View view) {
        /*PopupMenu popupMenu = new PopupMenu(activity, view);
        if ((mMentorGroup.getGroupId() != null && !mMentorGroup.getGroupId().equals("InterForm") && mChat.getType() == null) || (mChat.getType() != null && !mChat.getType().equals("PrivateChat"))) {
            popupMenu.getMenu().add("Удалить ребенка из группы").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    popupMenu.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    View viewAlert = getLayoutInflater().inflate(R.layout.alert_dialog_builder_transition, null);
                    builder.setView(viewAlert);
                    AlertDialog alertDialog = builder.create();

                    TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
                    TextView tvOk = viewAlert.findViewById(R.id.tvExit);
                    tvTitle.setText("Вы действительно хотите\n" +
                            "удалить ребенка из группы " + mMentorGroup.getGroupName() + "?");
                    tvOk.setText("Удалить");
                    alertDialog.show();

                    viewAlert.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    tvOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();

                            JsonObject jsonData = new JsonObject();
                            jsonData.addProperty(F.MentorId, MENTOR_ID);
                            jsonData.addProperty(F.ChildId, mChat.getUserId());
                            jsonData.addProperty(F.GroupId, mMentorGroup.getGroupId());
                            jsonData.addProperty(ChatId, mChat.getChatId());

                            String SOAP = SoapRequest(func_RemoveChildFromMentorGroup, jsonData.toString());
                            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                            Request request = new Request.Builder().url(URL).post(body).build();
                            HttpClient.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(final Call call, IOException e) {
                                }

                                @Override
                                public void onResponse(Call call, final Response response) throws IOException {
                                    if (response.code() >= 200 && response.code() <= 300) {
                                        String xml = response.body().string();
                                        String result = _Web.XMLReader(xml);
                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (result.equals("0"))
                                                    activity.alert.onToast("Ошибка: не удалось удалить ребенка из группы");
                                                else {
                                                    activity.alert.onToast("Ребенок успешно удален из группы");
                                                    onLoadGrades();
                                                    activity.presenter.onUpdateMentorData();
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                    return true;
                }
            });
        }
        if (mChat.getType() != null && mChat.getType().equals("PrivateChat")) {
            popupMenu.getMenu().add("Удалить чат").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    popupMenu.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    View viewAlert = getLayoutInflater().inflate(R.layout.alert_dialog_builder_transition, null);
                    builder.setView(viewAlert);
                    AlertDialog alertDialog = builder.create();

                    TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
                    TextView tvOk = viewAlert.findViewById(R.id.tvExit);
                    tvTitle.setText("Вы действительно хотите\n" +
                            "удалить чат?");
                    tvOk.setText("Удалить");
                    alertDialog.show();

                    viewAlert.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    tvOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();

                            JsonObject jsonData = new JsonObject();
                            jsonData.addProperty(F.SourceId, MENTOR_ID);
                            jsonData.addProperty(ChatId, mChat.getChatId());

                            String SOAP = SoapRequest(func_RemoveChat, jsonData.toString());
                            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                            Request request = new Request.Builder().url(URL).post(body).build();
                            HttpClient.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(final Call call, IOException e) {
                                }

                                @Override
                                public void onResponse(Call call, final Response response) throws IOException {
                                    if (response.code() >= 200 && response.code() <= 300) {
                                        String xml = response.body().string();
                                        String result = _Web.XMLReader(xml);
                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (result.equals("0"))
                                                    activity.alert.onToast("Ошибка, попробуйте позже");
                                                else if (result.equals("1")) {
                                                    activity.alert.onToast("Чат успешно удален");
                                                    activity.presenter.onStartPresenter();
                                                }
                                            }
                                        });
                                    } else activity.alert.onToast("Ошибка, попробуйте позже");
                                }
                            });
                        }
                    });
                    return true;
                }
            });
        }
        if (popupMenu.getMenu().size() > 0) popupMenu.show();*/
    }

    private void onBackPressed() {
        activity.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivMenu:
                onMenu(v);
                break;
        }
    }

    @Override
    public void onItemClick() {

    }

    @Override
    public void onMessageClick(ModelDefaultMessages m){

    }
    @Override
    public void onMessageLong(ModelDefaultMessages m){

    }
}
