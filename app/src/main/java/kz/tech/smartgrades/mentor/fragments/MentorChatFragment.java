package kz.tech.smartgrades.mentor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorLessonsTabAdapter;
import kz.tech.smartgrades.mentor.models.ModelDefaultMessages;
import kz.tech.smartgrades.mentor.models.ModelMentorChat;
import kz.tech.smartgrades.parent.adapters.DefaultMessagesAdapter;
import kz.tech.smartgrades.parent.model.ModelPrivateChat;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelLesson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.F.ChatId;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetChatMessages;
import static kz.tech.smartgrades._Web.func_SendMessage;

public class MentorChatFragment extends Fragment implements View.OnClickListener, DefaultMessagesAdapter.OnItemClickListener, MentorLessonsTabAdapter.OnItemClickListener{

    private MentorActivity activity;
    private String MENTOR_ID;

    private CircleImageView civAvatar;
    private TextView tvTitle;
    private ImageView ivBack, ivMenu;

    private ModelMentorChat mChat;
    private ModelMentorChat mMentorChat;
    private DefaultMessagesAdapter MessagesAdapter;
    private RecyclerView rvChatAdapter;

    private EditText etMessage;
    private ImageView ivSend;

    private MentorLessonsTabAdapter TabAdapter;
    private DiscreteScrollView dcvLessons;
    private ModelLesson SelectLesson;
    private int SelectLessonPos;

    private FrameLayout flBottomViews, flButtons, flMessageEnter;
    private TextView tvNo, tvEdit, tvYes;


    public static MentorChatFragment newInstance(ModelPrivateChat mChat){
        Bundle bundle = new Bundle();
        bundle.putParcelable("mChat", mChat);
        MentorChatFragment fragment = new MentorChatFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (MentorActivity) getActivity();
        if(getArguments() != null) mChat = getArguments().getParcelable("mChat");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_mentor_chat, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onLoadMessages();
    }
    private void initViews(View view){
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);

        civAvatar = view.findViewById(R.id.civAvatar);
        tvTitle = view.findViewById(R.id.tvTitle);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivMenu = view.findViewById(R.id.ivMenu);
        ivMenu.setOnClickListener(this);
        ivMenu.setVisibility(View.GONE);

        MessagesAdapter = new DefaultMessagesAdapter(activity, activity.login.loadUserDate(LoginKey.ID));
        MessagesAdapter.setOnItemClickListener(this);
        rvChatAdapter = view.findViewById(R.id.rvChatAdapter);
        rvChatAdapter.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvChatAdapter.setAdapter(MessagesAdapter);

        KeyboardVisibilityEvent.setEventListener(activity, new KeyboardVisibilityEventListener(){
            @Override
            public void onVisibilityChanged(boolean isOpen){
                if(isOpen)
                    rvChatAdapter.scrollToPosition(rvChatAdapter.getAdapter().getItemCount() - 1);
            }
        });

        TabAdapter = new MentorLessonsTabAdapter(activity, SelectLessonPos);
        TabAdapter.setOnItemClickListener(this);
        dcvLessons = view.findViewById(R.id.dcvLessons);
        dcvLessons.setAdapter(TabAdapter);
        dcvLessons.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1f)
                .setMinScale(0.75f)
                .setPivotX(Pivot.X.CENTER)
                .setPivotY(Pivot.Y.CENTER)
                .build());
        dcvLessons.addScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>(){
            @Override
            public void onScrollStart(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition){
                currentItemHolder.itemView.setBackgroundResource(R.drawable.background_mentor_set_grade_lesson_tag_not_active);
            }
            @Override
            public void onScrollEnd(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition){
                currentItemHolder.itemView.setBackgroundResource(R.drawable.background_mentor_set_grade_lesson_tag_active);
            }
            @Override
            public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder,
                                 @Nullable RecyclerView.ViewHolder newCurrent){ }
        });

//        flBottomViews = view.findViewById(R.id.flBottomViews);
//        flButtons = view.findViewById(R.id.flButtons);
//        flMessageEnter = view.findViewById(R.id.flMessageEnter);
//        tvNo = view.findViewById(R.id.tvNo);
//        tvNo.setOnClickListener(this);
//        tvEdit = view.findViewById(R.id.tvEdit);
//        tvEdit.setOnClickListener(this);
//        tvYes = view.findViewById(R.id.tvYes);
//        tvYes.setOnClickListener(this);
        etMessage = view.findViewById(R.id.etMessage);
        ivSend = view.findViewById(R.id.ivSend);
        ivSend.setOnClickListener(this);

        if(mChat != null){
            onSetProfileData();
            onLoadMessages();
        }
    }
    private void onLoadMessages(){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, MENTOR_ID);
        jsonData.addProperty(F.ChatId, mChat.getChatId());

        String SOAP = SoapRequest(func_GetChatMessages, jsonData.toString());
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
                                ArrayList<ModelDefaultMessages> mMessages = new Gson().fromJson(result, new TypeToken<ArrayList<ModelDefaultMessages>>(){
                                }.getType());
                                MessagesAdapter.updateData(mMessages);
                                rvChatAdapter.scrollToPosition(rvChatAdapter.getAdapter().getItemCount() - 1);
                                activity.presenter.onUpdateMentorData();
                            }
                        }
                    });
                }
            }
        });
    }

    public void onSetChatData(ModelMentorChat m){
        this.mChat = m;
        //onSetProfileData();
        //onLoadMessages();
    }

    private void onSetProfileData(){
        if(mChat.getChatType().equals("Group")){
            tvTitle.setText(mChat.getChatName());
            civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_chat_group));
        }
        else{
//            tvTitle.setText(mChat.getTargetFirstName() + " " + mChat.getTargetLastName());
//            tvTitle.setVisibility(View.VISIBLE);
//            if(!stringIsNullOrEmpty(mChat.getTargetAvatar()))
//                Picasso.get().load(mChat.getTargetAvatar()).fit().centerInside().into(civAvatar);
//            else
//                civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
        }
    }


//    private void onYes(ModelDefaultMessages mMessage){
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
//        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_select_group_for_added, null, false);
//        bottomSheetDialog.setContentView(view);
//
//        SelectMentorGroupForAddedAdapter adapter = new SelectMentorGroupForAddedAdapter(activity);
//        RecyclerView recyclerView = view.findViewById(R.id.rvGradesList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
//
//        TextView tvCreateGroup = view.findViewById(R.id.tvCreateGroup);
//        tvCreateGroup.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                final boolean[] isEnter = {false, false};
//                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//                View vLayout = getLayoutInflater().inflate(R.layout.alert_dialog_builder_mentor_create_group, null);
//                builder.setView(vLayout);
//                EditText etEnterCreateGroup = vLayout.findViewById(R.id.etEnterCreateGroup);
//                TextView tvSelectLessonValue = vLayout.findViewById(R.id.tvSelectLessonValue);
//                TextView tvDefault = vLayout.findViewById(R.id.tvDefault);
//                TextView tvOk = vLayout.findViewById(R.id.tvOk);
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//
//                final ModelSchoolLessons[] modelSchoolLessons = new ModelSchoolLessons[1];
//
//                etEnterCreateGroup.addTextChangedListener(new TextWatcher(){
//                    @Override
//                    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3){ }
//                    @Override
//                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){ }
//                    @Override
//                    public void afterTextChanged(Editable arg0){
//                        if(arg0.length() == 0) isEnter[0] = false;
//                        else isEnter[0] = true;
//                        if(isEnter[0] && isEnter[1])
//                            tvOk.setTextColor(getResources().getColor(R.color.blue_sea));
//                        else tvOk.setTextColor(getResources().getColor(R.color.gray_default));
//                    }
//                });
//                tvSelectLessonValue.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v){
//                        BSDSelectLesson bsdSelectLesson = new BSDSelectLesson(activity);
//                        bsdSelectLesson.setOnItemClickListener(new BSDSelectLesson.OnItemClickListener(){
//                            @Override
//                            public void onClick(ModelSchoolLessons m){
//                                tvSelectLessonValue.setText(m.getLessonName());
//                                modelSchoolLessons[0] = m;
//                                isEnter[1] = true;
//                                if(isEnter[0] && isEnter[1])
//                                    tvOk.setTextColor(getResources().getColor(R.color.blue_sea));
//                                else
//                                    tvOk.setTextColor(getResources().getColor(R.color.gray_default));
//                            }
//                        });
//                    }
//                });
//
//                tvDefault.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v){
//                        etEnterCreateGroup.setText("Новая группа");
//                    }
//                });
//                tvOk.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v){
//                        if(isEnter[0] && isEnter[1]){
//                            activity.presenter.onAddMentorGroup(etEnterCreateGroup.getText().toString(), modelSchoolLessons[0]);
//                            alertDialog.dismiss();
//                        }
//                    }
//                });
//            }
//        });
//
//        adapter.updateData(activity.getMentorGroups());
//        adapter.selectList();
//        recyclerView.setAdapter(adapter);
//        bottomSheetDialog.show();
//
//        adapter.setOnItemClickListener(new SelectMentorGroupForAddedAdapter.OnItemClickListener(){
//            @Override
//            public void onItemClick(ModelMentorGroups mGroup){
//                bottomSheetDialog.dismiss();
//                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//                View viewAlert = getLayoutInflater().inflate(R.layout.alert_dialog_builder_transition, null);
//                builder.setView(viewAlert);
//                AlertDialog alertDialog = builder.create();
//
//                TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
//                tvTitle.setText("Добавить подопечного в " + mGroup.getGroupName() + "?");
//                TextView tvOk = viewAlert.findViewById(R.id.tvExit);
//                tvOk.setText("Добавить");
//                alertDialog.show();
//
//                viewAlert.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v){
//                        alertDialog.dismiss();
//                    }
//                });
//                tvOk.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v){
//                        alertDialog.dismiss();
//                        JsonObject jsonData = new JsonObject();
//                        jsonData.addProperty(F.SourceId, MENTOR_ID);
//                        jsonData.addProperty(F.ParentId, mChat.getUserId());
//                        jsonData.addProperty(F.ChildId, mChat.getChildId());
//                        jsonData.addProperty(F.GroupId, mGroup.getGroupId());
//                        jsonData.addProperty("Index", mMessage.getInterFormId());
//                        jsonData.addProperty(F.ChatId, mChat.getChatId());
//                        jsonData.addProperty(F.MessageId, mMessage.getMessageId());
//                        jsonData.addProperty(F.Message, "Ваша заявка принята. Ребенок добавлен в группу - " + mGroup.getGroupName() + ", по предмету - " + mGroup.getLessonName());
//
//                        String SOAP = SoapRequest(func_AcceptInterFormParentToMentor, jsonData.toString());
//                        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
//                        Request request = new Request.Builder().url(URL).post(body).build();
//                        HttpClient.newCall(request).enqueue(new Callback(){
//                            @Override
//                            public void onFailure(final Call call, IOException e){
//                            }
//                            @Override
//                            public void onResponse(Call call, final Response response) throws IOException{
//                                if(response.isSuccessful()){
//                                    String result = _Web.XMLReader(response.body().string());
//                                    activity.runOnUiThread(new Runnable(){
//                                        @Override
//                                        public void run(){
//                                            if(result.equals("0"))
//                                                activity.alert.onToast("Ошибка, попробуйте позже");
//                                            else if(result.equals("-1"))
//                                                activity.alert.onToast("Ошибка, этот ребенок уже добавлен в данную группу");
//                                            else if(result.equals("2")){
//                                                activity.alert.onToast("Заявка была отменена Родителем.");
//                                                activity.presenter.onUpdateMentorData();
//                                            }
//                                            else{
//                                                Type founderListType = new TypeToken<ArrayList<ModelDefaultMessages>>(){
//                                                }.getType();
//                                                ArrayList<ModelDefaultMessages> modelChats = new Gson().fromJson(result, founderListType);
//                                                MessagesAdapter.updateData(modelChats);
//                                                rvChatAdapter.scrollToPosition(rvChatAdapter.getAdapter().getItemCount() - 1);
//                                                activity.presenter.onUpdateMentorData();
//                                            }
//                                        }
//                                    });
//                                }
//                            }
//                        });
//                    }
//                });
//            }
//        });
//    }
//    private void onNo(ModelDefaultMessages mMessage){
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        View viewAlert = getLayoutInflater().inflate(R.layout.alert_dialog_builder_transition, null);
//        builder.setView(viewAlert);
//        AlertDialog alertDialog = builder.create();
//
//        TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
//        tvTitle.setText("Вы действительно хотите Отклонить заявку?");
//        TextView tvOk = viewAlert.findViewById(R.id.tvExit);
//        tvOk.setText("Отклонить");
//        alertDialog.show();
//
//        viewAlert.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                alertDialog.dismiss();
//            }
//        });
//        tvOk.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                alertDialog.dismiss();
//                JsonObject jsonData = new JsonObject();
//                jsonData.addProperty("Index", mMessage.getInterFormId());
//                jsonData.addProperty(F.SourceId, MENTOR_ID);
//                jsonData.addProperty(F.ChatId, mChat.getChatId());
//                jsonData.addProperty(F.MessageId, mMessage.getMessageId());
//                jsonData.addProperty(F.Message, "Ваша заявка отклонена.");
//
//                String SOAP = SoapRequest(func_RejectInterFormParentToMentor, jsonData.toString());
//                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
//                Request request = new Request.Builder().url(URL).post(body).build();
//                HttpClient.newCall(request).enqueue(new Callback(){
//                    @Override
//                    public void onFailure(final Call call, IOException e){
//                    }
//
//                    @Override
//                    public void onResponse(Call call, final Response response) throws IOException{
//                        if(response.code() >= 200 && response.code() <= 300){
//                            String xml = response.body().string();
//                            String result = _Web.XMLReader(xml);
//                            activity.runOnUiThread(new Runnable(){
//                                @Override
//                                public void run(){
//                                    if(result.equals("0")){
//                                    }
//                                    else if(result.equals("2")){
//                                        flBottomViews.setVisibility(View.GONE);
//                                        activity.alert.onToast("Заявка была отменена Родителем.");
//                                        activity.presenter.onUpdateMentorData();
//                                    }
//                                    else{
//                                        Type founderListType = new TypeToken<ArrayList<ModelDefaultMessages>>(){
//                                        }.getType();
//                                        ArrayList<ModelDefaultMessages> modelChats = new Gson().fromJson(result, founderListType);
//                                        MessagesAdapter.updateData(modelChats);
//                                        rvChatAdapter.scrollToPosition(rvChatAdapter.getAdapter().getItemCount() - 1);
//                                        activity.presenter.onUpdateMentorData();
//                                    }
//                                }
//                            });
//                        }
//                    }
//                });
//            }
//        });
//    }
//    private void onCancel(ModelDefaultMessages mMessage){
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        View viewAlert = getLayoutInflater().inflate(R.layout.alert_dialog_builder_transition, null);
//        builder.setView(viewAlert);
//        AlertDialog alertDialog = builder.create();
//
//        TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
//        TextView tvOk = viewAlert.findViewById(R.id.tvExit);
//        tvTitle.setText("Вы действительно хотите\n" +
//                "отменить заявку?");
//        tvOk.setText("Отменить");
//        alertDialog.show();
//
//        viewAlert.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                alertDialog.dismiss();
//            }
//        });
//        tvOk.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                alertDialog.dismiss();
//
//                JsonObject jsonData = new JsonObject();
//                jsonData.addProperty(F.UserId, mMessage.getSourceId());
//                jsonData.addProperty(F.Type, "Mentor");
//                jsonData.addProperty(F.Index, mMessage.getInterFormId());
//                jsonData.addProperty(F.MessageId, mMessage.getMessageId());
//                jsonData.addProperty(ChatId, mChat.getChatId());
//
//                String SOAP = SoapRequest(func_CancelInterForm, jsonData.toString());
//                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
//                Request request = new Request.Builder().url(URL).post(body).build();
//                HttpClient.newCall(request).enqueue(new Callback(){
//                    @Override
//                    public void onFailure(final Call call, IOException e){
//                    }
//                    @Override
//                    public void onResponse(Call call, final Response response) throws IOException{
//                        if(response.code() >= 200 && response.code() <= 300){
//                            String xml = response.body().string();
//                            String result = _Web.XMLReader(xml);
//                            activity.runOnUiThread(new Runnable(){
//                                @Override
//                                public void run(){
//                                    if(result.equals("0"))
//                                        activity.alert.onToast("Ошибка, попробуйте позже");
//                                    else{
//                                        activity.alert.onToast("Заявка успешно отменена");
//                                        Type founderListType = new TypeToken<ArrayList<ModelDefaultMessages>>(){
//                                        }.getType();
//                                        mChatMessages = new Gson().fromJson(result, founderListType);
//                                        MessagesAdapter.updateData(mChatMessages);
//                                        rvChatAdapter.scrollToPosition(rvChatAdapter.getAdapter().getItemCount() - 1);
//                                        //activity.presenter.onStartPresenter();
//                                    }
//                                }
//                            });
//                        }
//                        else activity.alert.onToast("Ошибка, попробуйте позже");
//                    }
//                });
//            }
//        });
//    }

//    private void RemoveGrade(ModelDefaultMessages m, int gradePos){
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        View viewAlert = getLayoutInflater().inflate(R.layout.alert_dialog_builder_transition, null);
//        builder.setView(viewAlert);
//        AlertDialog alertDialog = builder.create();
//
//        TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
//        TextView tvCancel = viewAlert.findViewById(R.id.tvCancel);
//        TextView tvExit = viewAlert.findViewById(R.id.tvExit);
//        tvTitle.setText("Удалить выбранную оценку?");
//        tvExit.setText("Удалить");
//        alertDialog.show();
//
//        tvCancel.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                alertDialog.dismiss();
//            }
//        });
//        tvExit.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                alertDialog.dismiss();
//                JsonObject jsonData = new JsonObject();
//                jsonData.addProperty(F.Index, m.getGradeId());
//
//                String SOAP = SoapRequest(func_RemoveGrade, jsonData.toString());
//                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
//                Request request = new Request.Builder().url(URL).post(body).build();
//                HttpClient.newCall(request).enqueue(new Callback(){
//                    @Override
//                    public void onFailure(final Call call, IOException e){
//                    }
//                    @Override
//                    public void onResponse(Call call, final Response response) throws IOException{
//                        String xml = response.body().string();
//                        if(response.code() >= 200 && response.code() <= 300){
//                            String result = _Web.XMLReader(xml);
//                            activity.runOnUiThread(new Runnable(){
//                                @Override
//                                public void run(){
//                                    if(result.equals("0"))
//                                        activity.alert.onToast("Ошибка: не удалось отменить оценку");
//                                    else if(result.equals("1")){
//                                        activity.restartPresenter();
//                                        mChatMessages.get(gradePos).setType(6);
//                                        mChatMessages.get(gradePos).setMessage("Оценка отменена");
//                                        MessagesAdapter.updateData(mChatMessages);
//                                    }
//                                    else if(result.equals("2"))
//                                        activity.alert.onToast("Нельзя отменить. (Время истекло)");
//                                }
//                            });
//                        }
//                    }
//                });
//            }
//        });
//    }
    //    @Override
//    public void onItemClick(ModelDefaultMessages m, int answerType, int gradePos){
//        if(answerType == 0) RemoveGrade(m, gradePos);
//        else if(answerType == 1) onYes(m);
//        else if(answerType == 2) onNo(m);
//        else if(answerType == 3) onCancel(m);
//    }

    @Override
    public void onItemClick(int p){
        SelectLessonPos = p;
        //SelectLesson = mClass.getLessons().get(SelectLessonPos);
        dcvLessons.setItemTransitionTimeMillis(100);
        dcvLessons.smoothScrollToPosition(SelectLessonPos);
    }

    @Override
    public void onMessageClick(ModelDefaultMessages m){

    }
    @Override
    public void onMessageLong(ModelDefaultMessages m){

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
    private void onBack(){
        activity.onBackPressed();
    }
    private void onMenu(View view){
//        PopupMenu popupMenu = new PopupMenu(activity, view);
//        if((mMentorGroup.getGroupId() != null && !mMentorGroup.getGroupId().equals("InterForm") && mChat.getType() == null) || (mChat.getType() != null && !mChat.getType().equals("PrivateChat"))){
//            popupMenu.getMenu().add("Удалить ребенка из группы").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
//                @Override
//                public boolean onMenuItemClick(MenuItem menuItem){
//                    popupMenu.dismiss();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//                    View viewAlert = getLayoutInflater().inflate(R.layout.alert_dialog_builder_transition, null);
//                    builder.setView(viewAlert);
//                    AlertDialog alertDialog = builder.create();
//
//                    TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
//                    TextView tvOk = viewAlert.findViewById(R.id.tvExit);
//                    tvTitle.setText("Вы действительно хотите\n" +
//                            "удалить ребенка из группы " + mMentorGroup.getGroupName() + "?");
//                    tvOk.setText("Удалить");
//                    alertDialog.show();
//
//                    viewAlert.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener(){
//                        @Override
//                        public void onClick(View v){
//                            alertDialog.dismiss();
//                        }
//                    });
//                    tvOk.setOnClickListener(new View.OnClickListener(){
//                        @Override
//                        public void onClick(View v){
//                            alertDialog.dismiss();
//
//                            JsonObject jsonData = new JsonObject();
//                            jsonData.addProperty(F.MentorId, MENTOR_ID);
//                            jsonData.addProperty(F.ChildId, mChat.getUserId());
//                            jsonData.addProperty(F.GroupId, mMentorGroup.getGroupId());
//                            jsonData.addProperty(ChatId, mChat.getChatId());
//
//                            String SOAP = SoapRequest(func_RemoveChildFromMentorGroup, jsonData.toString());
//                            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
//                            Request request = new Request.Builder().url(URL).post(body).build();
//                            HttpClient.newCall(request).enqueue(new Callback(){
//                                @Override
//                                public void onFailure(final Call call, IOException e){
//                                }
//                                @Override
//                                public void onResponse(Call call, final Response response) throws IOException{
//                                    if(response.code() >= 200 && response.code() <= 300){
//                                        String xml = response.body().string();
//                                        String result = _Web.XMLReader(xml);
//                                        activity.runOnUiThread(new Runnable(){
//                                            @Override
//                                            public void run(){
//                                                if(result.equals("0"))
//                                                    activity.alert.onToast("Ошибка: не удалось удалить ребенка из группы");
//                                                else{
//                                                    activity.alert.onToast("Ребенок успешно удален из группы");
//                                                    onLoadMessages();
//                                                    activity.presenter.onUpdateMentorData();
//                                                }
//                                            }
//                                        });
//                                    }
//                                }
//                            });
//                        }
//                    });
//                    return true;
//                }
//            });
//        }
//        if(mChat.getType() != null && mChat.getType().equals("PrivateChat")){
//            popupMenu.getMenu().add("Удалить чат").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
//                @Override
//                public boolean onMenuItemClick(MenuItem menuItem){
//                    popupMenu.dismiss();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//                    View viewAlert = getLayoutInflater().inflate(R.layout.alert_dialog_builder_transition, null);
//                    builder.setView(viewAlert);
//                    AlertDialog alertDialog = builder.create();
//
//                    TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
//                    TextView tvOk = viewAlert.findViewById(R.id.tvExit);
//                    tvTitle.setText("Вы действительно хотите\n" +
//                            "удалить чат?");
//                    tvOk.setText("Удалить");
//                    alertDialog.show();
//
//                    viewAlert.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener(){
//                        @Override
//                        public void onClick(View v){
//                            alertDialog.dismiss();
//                        }
//                    });
//                    tvOk.setOnClickListener(new View.OnClickListener(){
//                        @Override
//                        public void onClick(View v){
//                            alertDialog.dismiss();
//
//                            JsonObject jsonData = new JsonObject();
//                            jsonData.addProperty(F.SourceId, MENTOR_ID);
//                            jsonData.addProperty(ChatId, mChat.getChatId());
//
//                            String SOAP = SoapRequest(func_RemoveChat, jsonData.toString());
//                            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
//                            Request request = new Request.Builder().url(URL).post(body).build();
//                            HttpClient.newCall(request).enqueue(new Callback(){
//                                @Override
//                                public void onFailure(final Call call, IOException e){
//                                }
//                                @Override
//                                public void onResponse(Call call, final Response response) throws IOException{
//                                    if(response.code() >= 200 && response.code() <= 300){
//                                        String xml = response.body().string();
//                                        String result = _Web.XMLReader(xml);
//                                        activity.runOnUiThread(new Runnable(){
//                                            @Override
//                                            public void run(){
//                                                if(result.equals("0"))
//                                                    activity.alert.onToast("Ошибка, попробуйте позже");
//                                                else if(result.equals("1")){
//                                                    activity.alert.onToast("Чат успешно удален");
//                                                    activity.presenter.onStartPresenter();
//                                                }
//                                            }
//                                        });
//                                    }
//                                    else activity.alert.onToast("Ошибка, попробуйте позже");
//                                }
//                            });
//                        }
//                    });
//                    return true;
//                }
//            });
//        }
//        if(popupMenu.getMenu().size() > 0) popupMenu.show();
    }
    private void onSendMessage(){
        String msg = etMessage.getText().toString().trim();
        if(stringIsNullOrEmpty(msg)) return;
        etMessage.setText("");

        JsonObject json = new JsonObject();
        json.addProperty(ChatId, mChat.getChatId());
        json.addProperty(F.SourceId, MENTOR_ID);
        //json.addProperty(F.TargetId, mChat.getTargetId());
        json.addProperty(F.Message, msg);

        String SOAP = SoapRequest(func_SendMessage, json.toString());
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
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            if(answer.isSuccess()) onLoadMessages();
                            else activity.alert.onToast(answer.getMessage());
                        }
                    });
                }
            }
        });
    }
}