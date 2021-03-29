package kz.tech.smartgrades.mentor.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
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
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.SelectMentorGroupForAddedAdapter;
import kz.tech.smartgrades.mentor.dialog.BSDSelectLesson;
import kz.tech.smartgrades.mentor.models.ModelMentorChat;
import kz.tech.smartgrades.mentor.models.ModelDefaultChat;
import kz.tech.smartgrades.mentor.models.ModelMentorGroups;
import kz.tech.smartgrades.parent.adapters.DefaultChatAdapter;
import kz.tech.smartgrades.parent.model.ModelChat;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolLessons;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.F.ChatId;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_AcceptInterFormParentToMentor;
import static kz.tech.smartgrades._Web.func_CancelInterForm;
import static kz.tech.smartgrades._Web.func_Chats;
import static kz.tech.smartgrades._Web.func_RejectInterFormParentToMentor;
import static kz.tech.smartgrades._Web.func_RemoveChat;
import static kz.tech.smartgrades._Web.func_RemoveChildFromMentorGroup;
import static kz.tech.smartgrades._Web.func_RemoveGrade;
import static kz.tech.smartgrades._Web.func_SendMessage;

public class MentorChatFragment extends Fragment implements View.OnClickListener, DefaultChatAdapter.OnItemClickListener{

    private MentorActivity activity;
    private String MENTOR_ID;

    private CircleImageView civAvatar;
    private TextView tvName;
    private ImageView ivBack, ivMenu;

    private ModelMentorGroups mMentorGroup;
    private ArrayList<ModelDefaultChat> mChatMessages;
    private ModelMentorChat mChat = new ModelMentorChat();
    private DefaultChatAdapter ChatAdapter;
    private RecyclerView rvChatAdapter;

    private FrameLayout flBottomViews, flButtons, flMessageEnter;
    private TextView tvNo, tvEdit, tvYes;
    private EditText etMessage;
    private ImageView ivSend;


    public static MentorChatFragment newInstance(ModelMentorChat modelMentorChat, ModelMentorGroups modelMentorGroup){
        Bundle bundle = new Bundle();
        bundle.putParcelable("mChildrenList", modelMentorChat);
        bundle.putParcelable("mMentorGroup", modelMentorGroup);
        MentorChatFragment fragment = new MentorChatFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (MentorActivity) getActivity();
        if(getArguments() != null){
            mChat = getArguments().getParcelable("mChildrenList");
            mMentorGroup = getArguments().getParcelable("mMentorGroup");
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_parent_chat_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onSetTargetInfo();
        onLoadChatMessages();
    }
    private void initViews(View view){
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);

        civAvatar = view.findViewById(R.id.civAvatar);
        tvName = view.findViewById(R.id.tvName);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivMenu = view.findViewById(R.id.ivMenu);
        ivMenu.setOnClickListener(this);

        ChatAdapter = new DefaultChatAdapter(activity, activity.login.loadUserDate(LoginKey.ID));
        ChatAdapter.setOnItemClickListener(this);
        rvChatAdapter = view.findViewById(R.id.rvChatAdapter);
        rvChatAdapter.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvChatAdapter.setAdapter(ChatAdapter);

        flBottomViews = view.findViewById(R.id.flBottomViews);
        flButtons = view.findViewById(R.id.flButtons);
        flMessageEnter = view.findViewById(R.id.flMessageEnter);
        tvNo = view.findViewById(R.id.tvNo);
        tvNo.setOnClickListener(this);
        tvEdit = view.findViewById(R.id.tvEdit);
        tvEdit.setOnClickListener(this);
        tvYes = view.findViewById(R.id.tvYes);
        tvYes.setOnClickListener(this);
        etMessage = view.findViewById(R.id.etMessage);
        ivSend = view.findViewById(R.id.ivSend);
        ivSend.setOnClickListener(this);
    }
    private void onSetTargetInfo(){
        String avatar = mChat.getAvatar();
        if(avatar != null && !avatar.isEmpty())
            Picasso.get().load(avatar).fit().centerInside().into(civAvatar);
        else civAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));

        tvName.setText(mChat.getFirstName() + " " + mChat.getLastName());
    }

    private void onLoadChatMessages(){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.TargetId, MENTOR_ID);
        jsonData.addProperty(F.ChatId, mChat.getChatId());

        String SOAP = SoapRequest(func_Chats, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.code() >= 200 && response.code() <= 300){
                    String xml = response.body().string();
                    String result = _Web.XMLReader(xml);
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            if(result.equals("0")){
                            }
                            else{
                                Type founderListType = new TypeToken<ArrayList<ModelDefaultChat>>(){
                                }.getType();
                                mChatMessages = new Gson().fromJson(result, founderListType);
                                ChatAdapter.updateData(mChatMessages);
                                rvChatAdapter.scrollToPosition(rvChatAdapter.getAdapter().getItemCount() - 1);
                                if(mChat.getModel() != null && mChat.getModel().equals(F.PrivateChat)){
                                    flMessageEnter.setVisibility(View.VISIBLE);
                                    flBottomViews.setVisibility(View.VISIBLE);
                                }
                                activity.presenter.onUpdateMentorData();
                            }
                        }
                    });
                }
            }
        });
    }
    private void onSendMessage(){
        String msg = etMessage.getText().toString();
        etMessage.setText("");

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.SourceId, MENTOR_ID);
        jsonData.addProperty(F.TargetId, mChat.getUserId());
        jsonData.addProperty(F.Message, msg);

        String SOAP = SoapRequest(func_SendMessage, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.code() >= 200 && response.code() <= 300){
                    String xml = response.body().string();
                    String result = _Web.XMLReader(xml);
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            if(result.equals("0")){
                            }
                            else{
                                Type founderListType = new TypeToken<ArrayList<ModelDefaultChat>>(){
                                }.getType();
                                ArrayList<ModelDefaultChat> modelChats = new Gson().fromJson(result, founderListType);
                                ChatAdapter.updateData(modelChats);
                                rvChatAdapter.scrollToPosition(rvChatAdapter.getAdapter().getItemCount() - 1);
                            }
                        }
                    });
                }
            }
        });
    }

    private void onMenu(View view){
        PopupMenu popupMenu = new PopupMenu(activity, view);
        if((mMentorGroup.getGroupId() != null && !mMentorGroup.getGroupId().equals("InterForm") && mChat.getType() == null) || (mChat.getType() != null && !mChat.getType().equals("PrivateChat"))){
            popupMenu.getMenu().add("Удалить ребенка из группы").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
                @Override
                public boolean onMenuItemClick(MenuItem menuItem){
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

                    viewAlert.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            alertDialog.dismiss();
                        }
                    });
                    tvOk.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            alertDialog.dismiss();

                            JsonObject jsonData = new JsonObject();
                            jsonData.addProperty(F.MentorId, MENTOR_ID);
                            jsonData.addProperty(F.ChildId, mChat.getUserId());
                            jsonData.addProperty(F.GroupId, mMentorGroup.getGroupId());
                            jsonData.addProperty(ChatId, mChat.getChatId());

                            String SOAP = SoapRequest(func_RemoveChildFromMentorGroup, jsonData.toString());
                            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                            Request request = new Request.Builder().url(URL).post(body).build();
                            HttpClient.newCall(request).enqueue(new Callback(){
                                @Override
                                public void onFailure(final Call call, IOException e){
                                }
                                @Override
                                public void onResponse(Call call, final Response response) throws IOException{
                                    if(response.code() >= 200 && response.code() <= 300){
                                        String xml = response.body().string();
                                        String result = _Web.XMLReader(xml);
                                        activity.runOnUiThread(new Runnable(){
                                            @Override
                                            public void run(){
                                                if(result.equals("0"))
                                                    activity.alert.onToast("Ошибка: не удалось удалить ребенка из группы");
                                                else{
                                                    activity.alert.onToast("Ребенок успешно удален из группы");
                                                    onLoadChatMessages();
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
        if(mChat.getType() != null && mChat.getType().equals("PrivateChat")){
            popupMenu.getMenu().add("Удалить чат").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
                @Override
                public boolean onMenuItemClick(MenuItem menuItem){
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

                    viewAlert.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            alertDialog.dismiss();
                        }
                    });
                    tvOk.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            alertDialog.dismiss();

                            JsonObject jsonData = new JsonObject();
                            jsonData.addProperty(F.SourceId, MENTOR_ID);
                            jsonData.addProperty(ChatId, mChat.getChatId());

                            String SOAP = SoapRequest(func_RemoveChat, jsonData.toString());
                            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                            Request request = new Request.Builder().url(URL).post(body).build();
                            HttpClient.newCall(request).enqueue(new Callback(){
                                @Override
                                public void onFailure(final Call call, IOException e){
                                }
                                @Override
                                public void onResponse(Call call, final Response response) throws IOException{
                                    if(response.code() >= 200 && response.code() <= 300){
                                        String xml = response.body().string();
                                        String result = _Web.XMLReader(xml);
                                        activity.runOnUiThread(new Runnable(){
                                            @Override
                                            public void run(){
                                                if(result.equals("0"))
                                                    activity.alert.onToast("Ошибка, попробуйте позже");
                                                else if(result.equals("1")){
                                                    activity.alert.onToast("Чат успешно удален");
                                                    activity.presenter.onStartPresenter();
                                                }
                                            }
                                        });
                                    }
                                    else activity.alert.onToast("Ошибка, попробуйте позже");
                                }
                            });
                        }
                    });
                    return true;
                }
            });
        }
        if(popupMenu.getMenu().size() > 0) popupMenu.show();
    }

    private void onBack(){
        activity.onBackPressed();
        //if (mChat.getType() != null && mChat.getType().equals("PrivateChat")) activity.ChatVisible(true);
    }

    private void onYes(ModelDefaultChat mMessage){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_select_group_for_added, null, false);
        bottomSheetDialog.setContentView(view);

        SelectMentorGroupForAddedAdapter adapter = new SelectMentorGroupForAddedAdapter(activity);
        RecyclerView recyclerView = view.findViewById(R.id.rvGradesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        TextView tvCreateGroup = view.findViewById(R.id.tvCreateGroup);
        tvCreateGroup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final boolean[] isEnter = {false, false};
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                View vLayout = getLayoutInflater().inflate(R.layout.alert_dialog_builder_mentor_create_group, null);
                builder.setView(vLayout);
                EditText etEnterCreateGroup = vLayout.findViewById(R.id.etEnterCreateGroup);
                TextView tvSelectLessonValue = vLayout.findViewById(R.id.tvSelectLessonValue);
                TextView tvDefault = vLayout.findViewById(R.id.tvDefault);
                TextView tvOk = vLayout.findViewById(R.id.tvOk);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                final ModelSchoolLessons[] modelSchoolLessons = new ModelSchoolLessons[1];

                etEnterCreateGroup.addTextChangedListener(new TextWatcher(){
                    @Override
                    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3){ }
                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){ }
                    @Override
                    public void afterTextChanged(Editable arg0){
                        if(arg0.length() == 0) isEnter[0] = false;
                        else isEnter[0] = true;
                        if(isEnter[0] && isEnter[1])
                            tvOk.setTextColor(getResources().getColor(R.color.blue_sea));
                        else tvOk.setTextColor(getResources().getColor(R.color.gray_default));
                    }
                });
                tvSelectLessonValue.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        BSDSelectLesson bsdSelectLesson = new BSDSelectLesson(activity);
                        bsdSelectLesson.setOnItemClickListener(new BSDSelectLesson.OnItemClickListener(){
                            @Override
                            public void onClick(ModelSchoolLessons m){
                                tvSelectLessonValue.setText(m.getLessonName());
                                modelSchoolLessons[0] = m;
                                isEnter[1] = true;
                                if(isEnter[0] && isEnter[1])
                                    tvOk.setTextColor(getResources().getColor(R.color.blue_sea));
                                else
                                    tvOk.setTextColor(getResources().getColor(R.color.gray_default));
                            }
                        });
                    }
                });

                tvDefault.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        etEnterCreateGroup.setText("Новая группа");
                    }
                });
                tvOk.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if(isEnter[0] && isEnter[1]){
                            activity.presenter.onAddMentorGroup(etEnterCreateGroup.getText().toString(), modelSchoolLessons[0]);
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });

        adapter.updateData(activity.getMentorGroups());
        adapter.selectList();
        recyclerView.setAdapter(adapter);
        bottomSheetDialog.show();

        adapter.setOnItemClickListener(new SelectMentorGroupForAddedAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(ModelMentorGroups mGroup){
                bottomSheetDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                View viewAlert = getLayoutInflater().inflate(R.layout.alert_dialog_builder_transition, null);
                builder.setView(viewAlert);
                AlertDialog alertDialog = builder.create();

                TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
                tvTitle.setText("Добавить подопечного в " + mGroup.getGroupName() + "?");
                TextView tvOk = viewAlert.findViewById(R.id.tvExit);
                tvOk.setText("Добавить");
                alertDialog.show();

                viewAlert.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        alertDialog.dismiss();
                    }
                });
                tvOk.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        alertDialog.dismiss();
                        JsonObject jsonData = new JsonObject();
                        jsonData.addProperty(F.SourceId, MENTOR_ID);
                        jsonData.addProperty(F.ParentId, mChat.getUserId());
                        jsonData.addProperty(F.ChildId, mChat.getChildId());
                        jsonData.addProperty(F.GroupId, mGroup.getGroupId());
                        jsonData.addProperty("Index", mMessage.getInterFormId());
                        jsonData.addProperty(F.ChatId, mChat.getChatId());
                        jsonData.addProperty(F.MessageId, mMessage.getMessageId());
                        jsonData.addProperty(F.Message, "Ваша заявка принята. Ребенок добавлен в группу - " + mGroup.getGroupName() + ", по предмету - " + mGroup.getLessonName());

                        String SOAP = SoapRequest(func_AcceptInterFormParentToMentor, jsonData.toString());
                        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                        Request request = new Request.Builder().url(URL).post(body).build();
                        HttpClient.newCall(request).enqueue(new Callback(){
                            @Override
                            public void onFailure(final Call call, IOException e){
                            }
                            @Override
                            public void onResponse(Call call, final Response response) throws IOException{
                                if(response.isSuccessful()){
                                    String result = _Web.XMLReader(response.body().string());
                                    activity.runOnUiThread(new Runnable(){
                                        @Override
                                        public void run(){
                                            if(result.equals("0"))
                                                activity.alert.onToast("Ошибка, попробуйте позже");
                                            else if(result.equals("-1"))
                                                activity.alert.onToast("Ошибка, этот ребенок уже добавлен в данную группу");
                                            else if(result.equals("2")){
                                                activity.alert.onToast("Заявка была отменена Родителем.");
                                                activity.presenter.onUpdateMentorData();
                                            }
                                            else{
                                                Type founderListType = new TypeToken<ArrayList<ModelDefaultChat>>(){
                                                }.getType();
                                                ArrayList<ModelDefaultChat> modelChats = new Gson().fromJson(result, founderListType);
                                                ChatAdapter.updateData(modelChats);
                                                rvChatAdapter.scrollToPosition(rvChatAdapter.getAdapter().getItemCount() - 1);
                                                activity.presenter.onUpdateMentorData();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            }
        });
    }
    private void onNo(ModelDefaultChat mMessage){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View viewAlert = getLayoutInflater().inflate(R.layout.alert_dialog_builder_transition, null);
        builder.setView(viewAlert);
        AlertDialog alertDialog = builder.create();

        TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
        tvTitle.setText("Вы действительно хотите Отклонить заявку?");
        TextView tvOk = viewAlert.findViewById(R.id.tvExit);
        tvOk.setText("Отклонить");
        alertDialog.show();

        viewAlert.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty("Index", mMessage.getInterFormId());
                jsonData.addProperty(F.SourceId, MENTOR_ID);
                jsonData.addProperty(F.ChatId, mChat.getChatId());
                jsonData.addProperty(F.MessageId, mMessage.getMessageId());
                jsonData.addProperty(F.Message, "Ваша заявка отклонена.");

                String SOAP = SoapRequest(func_RejectInterFormParentToMentor, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback(){
                    @Override
                    public void onFailure(final Call call, IOException e){
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException{
                        if(response.code() >= 200 && response.code() <= 300){
                            String xml = response.body().string();
                            String result = _Web.XMLReader(xml);
                            activity.runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    if(result.equals("0")){
                                    }
                                    else if(result.equals("2")){
                                        flBottomViews.setVisibility(View.GONE);
                                        activity.alert.onToast("Заявка была отменена Родителем.");
                                        activity.presenter.onUpdateMentorData();
                                    }
                                    else{
                                        Type founderListType = new TypeToken<ArrayList<ModelDefaultChat>>(){
                                        }.getType();
                                        ArrayList<ModelDefaultChat> modelChats = new Gson().fromJson(result, founderListType);
                                        ChatAdapter.updateData(modelChats);
                                        rvChatAdapter.scrollToPosition(rvChatAdapter.getAdapter().getItemCount() - 1);
                                        activity.presenter.onUpdateMentorData();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
    private void onCancel(ModelDefaultChat mMessage){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View viewAlert = getLayoutInflater().inflate(R.layout.alert_dialog_builder_transition, null);
        builder.setView(viewAlert);
        AlertDialog alertDialog = builder.create();

        TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
        TextView tvOk = viewAlert.findViewById(R.id.tvExit);
        tvTitle.setText("Вы действительно хотите\n" +
                "отменить заявку?");
        tvOk.setText("Отменить");
        alertDialog.show();

        viewAlert.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();

                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, mMessage.getSourceId());
                jsonData.addProperty(F.Type, "Mentor");
                jsonData.addProperty(F.Index, mMessage.getInterFormId());
                jsonData.addProperty(F.MessageId, mMessage.getMessageId());
                jsonData.addProperty(ChatId, mChat.getChatId());

                String SOAP = SoapRequest(func_CancelInterForm, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback(){
                    @Override
                    public void onFailure(final Call call, IOException e){
                    }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException{
                        if(response.code() >= 200 && response.code() <= 300){
                            String xml = response.body().string();
                            String result = _Web.XMLReader(xml);
                            activity.runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    if(result.equals("0"))
                                        activity.alert.onToast("Ошибка, попробуйте позже");
                                    else{
                                        activity.alert.onToast("Заявка успешно отменена");
                                        Type founderListType = new TypeToken<ArrayList<ModelDefaultChat>>(){
                                        }.getType();
                                        mChatMessages = new Gson().fromJson(result, founderListType);
                                        ChatAdapter.updateData(mChatMessages);
                                        rvChatAdapter.scrollToPosition(rvChatAdapter.getAdapter().getItemCount() - 1);
                                        //activity.presenter.onStartPresenter();
                                    }
                                }
                            });
                        }
                        else activity.alert.onToast("Ошибка, попробуйте позже");
                    }
                });
            }
        });
    }

    private void RemoveGrade(ModelDefaultChat m, int gradePos){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View viewAlert = getLayoutInflater().inflate(R.layout.alert_dialog_builder_transition, null);
        builder.setView(viewAlert);
        AlertDialog alertDialog = builder.create();

        TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
        TextView tvCancel = viewAlert.findViewById(R.id.tvCancel);
        TextView tvExit = viewAlert.findViewById(R.id.tvExit);
        tvTitle.setText("Удалить выбранную оценку?");
        tvExit.setText("Удалить");
        alertDialog.show();

        tvCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();
            }
        });
        tvExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.Index, m.getGradeId());

                String SOAP = SoapRequest(func_RemoveGrade, jsonData.toString());
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
                            String result = _Web.XMLReader(xml);
                            activity.runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    if(result.equals("0"))
                                        activity.alert.onToast("Ошибка: не удалось отменить оценку");
                                    else if(result.equals("1")){
                                        activity.restartPresenter();
                                        mChatMessages.get(gradePos).setType(6);
                                        mChatMessages.get(gradePos).setMessage("Оценка отменена");
                                        ChatAdapter.updateData(mChatMessages);
                                    }
                                    else if(result.equals("2"))
                                        activity.alert.onToast("Нельзя отменить. (Время истекло)");
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onItemClick(ModelDefaultChat m, int answerType, int gradePos){
        if(answerType == 0) RemoveGrade(m, gradePos);
        else if(answerType == 1) onYes(m);
        else if(answerType == 2) onNo(m);
        else if(answerType == 3) onCancel(m);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
                break;
            case R.id.ivMenu:
                onMenu(v);
                break;
            case R.id.ivSend:
                onSendMessage();
                break;
        }
    }

    public void setData(ModelMentorChat m) {
        this.mChat = m;
        onSetTargetInfo();
        onLoadChatMessages();
    }
}
