package kz.tech.smartgrades.sponsor.fragments;

import android.os.Bundle;
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
import kz.tech.smartgrades.mentor.models.ModelDefaultMessages;
import kz.tech.smartgrades.parent.adapters.DefaultMessagesAdapter;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import kz.tech.smartgrades.sponsor.dialogs.DialogSponsorEditParentInterForm;
import kz.tech.smartgrades.sponsor.models.ModelSponsorUsersListAdapter;
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
import static kz.tech.smartgrades._Web.func_AcceptInterFormParentToSponsor;
import static kz.tech.smartgrades._Web.func_CancelInterForm;
import static kz.tech.smartgrades._Web.func_Chats;
import static kz.tech.smartgrades._Web.func_RejectInterFormParentToSponsor;
import static kz.tech.smartgrades._Web.func_RemoveChat;
import static kz.tech.smartgrades._Web.func_SendMessage;
import static kz.tech.smartgrades._Web.func_SponsorToChildChats;

public class SponsorChatFragment extends Fragment implements View.OnClickListener, DefaultMessagesAdapter.OnItemClickListener {

    private SponsorActivity activity;
    private String SPONSOR_ID;

    private CircleImageView civAvatar;
    private TextView tvName;
    private ImageView ivBack, ivMenu;

    private ModelSponsorUsersListAdapter mChat;
    private DefaultMessagesAdapter ChatAdapter;
    private RecyclerView rvChatAdapter;

    private FrameLayout flBottomViews, flButtons, flMessageEnter;
    private TextView tvNo, tvEdit, tvYes;
    private EditText etMessage;
    private ImageView ivSend;


    public static SponsorChatFragment newInstance(ModelSponsorUsersListAdapter m) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", m);
        SponsorChatFragment fragment = new SponsorChatFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SponsorActivity) getActivity();
        if (getArguments() != null) mChat = getArguments().getParcelable("model");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_chat_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        SPONSOR_ID = activity.login.loadUserDate(LoginKey.ID);

        civAvatar = view.findViewById(R.id.civAvatar);
        tvName = view.findViewById(R.id.tvName);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivMenu = view.findViewById(R.id.ivMenu);
        ivMenu.setOnClickListener(this);

        ChatAdapter = new DefaultMessagesAdapter(activity, activity.login.loadUserDate(LoginKey.ID));
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

    private void onSetTargetInfo() {
        String avatar = mChat.getAvatar();
        if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);
        else civAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));

        tvName.setText(mChat.getFirstName() + " " + mChat.getLastName());
    }

    private void onLoadChatMessages() {
        if (mChat.getType() == null){
            JsonObject jsonData = new JsonObject();
            jsonData.addProperty(F.ChatId, mChat.getChatId());
            jsonData.addProperty(F.SponsorId, SPONSOR_ID);
            jsonData.addProperty(F.ChildId, mChat.getUserId());

            //String SOAP = SoapRequest(func_ChildGetGrades, jsonData.toString());
            String SOAP = SoapRequest(func_SponsorToChildChats, jsonData.toString());
            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
            Request request = new Request.Builder().url(URL).post(body).build();
            HttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, IOException e) { }
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    String xml = response.body().string();
                    if (response.code() >= 200 && response.code() <= 300) {
                        String result = _Web.XMLToJson(xml);
                        if (result.equals("0")) {}
                        else {
                            Type founderListType = new TypeToken<ArrayList<ModelDefaultMessages>>(){}.getType();
                            ArrayList<ModelDefaultMessages> modelChats = new Gson().fromJson(result, founderListType);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ChatAdapter.updateData(modelChats);
                                    rvChatAdapter.scrollToPosition(rvChatAdapter.getAdapter().getItemCount() - 1);
                                }
                            });

                        }
                    }
                }
            });
        }
        else if (mChat.getType().equals("ParentInterForm") || mChat.getType().equals("SponsorInterForm") || mChat.getType().equals("PrivateChat")){
            JsonObject jsonData = new JsonObject();
            jsonData.addProperty(F.ChatId, mChat.getChatId());
            jsonData.addProperty(F.TargetId, SPONSOR_ID);

            String SOAP = SoapRequest(func_Chats, jsonData.toString());
            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
            Request request = new Request.Builder().url(URL).post(body).build();
            HttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, IOException e) { }
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    String xml = response.body().string();
                    if (response.code() >= 200 && response.code() <= 300) {
                        String result = _Web.XMLToJson(xml);
                        if (result.equals("0")) {}
                        else {
                            Type founderListType = new TypeToken<ArrayList<ModelDefaultMessages>>(){}.getType();
                            ArrayList<ModelDefaultMessages> modelChats = new Gson().fromJson(result, founderListType);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ChatAdapter.updateData(modelChats);
                                    rvChatAdapter.scrollToPosition(rvChatAdapter.getAdapter().getItemCount() - 1);
                                    if (mChat.getType().equals("PrivateChat")) {
                                        flMessageEnter.setVisibility(View.VISIBLE);
                                        flBottomViews.setVisibility(View.VISIBLE);
                                    }
                                    activity.presenter.onUpdateData();
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    private void onBack() {
//        activity.onRemoveFragment();
//        if (mChat.getType() != null && mChat.getType().equals("PrivateChat")) activity.ChatVisible(true);
        activity.onBackPressed();
    }

    private void onYesClick(ModelDefaultMessages mMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View viewAlert = getLayoutInflater().inflate(R.layout.alert_dialog_builder_transition, null);
        builder.setView(viewAlert);
        AlertDialog alertDialog = builder.create();

        TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
        tvTitle.setText("Добавить спонсируемого?");
        TextView tvOk = viewAlert.findViewById(R.id.tvExit);
        tvOk.setText("Добавить");
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
                jsonData.addProperty("Index", mMessage.getInterFormId());

                jsonData.addProperty(F.ChatId, mChat.getChatId());
                jsonData.addProperty(F.MessageId, mMessage.getMessageId());
                jsonData.addProperty(F.Message, "Ваша заявка принята. Учет результатов начнется со следующего учебного дня.");

                String SOAP = SoapRequest(func_AcceptInterFormParentToSponsor, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.code() >= 200 && response.code() <= 300) {
                            String xml = response.body().string();
                            String result = _Web.XMLToJson(xml);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result.equals("0")) activity.alert.onToast("Ошибка, попробуйте позже.");
                                    else if (result.equals("2")) {
                                        activity.alert.onToast("Заявка была отменена Родителем.");
                                        activity.presenter.onUpdateData();
                                    }
                                    else {
                                        Type founderListType = new TypeToken<ArrayList<ModelDefaultMessages>>(){}.getType();
                                        ArrayList<ModelDefaultMessages> modelChats = new Gson().fromJson(result, founderListType);
                                        ChatAdapter.updateData(modelChats);
                                        rvChatAdapter.scrollToPosition(rvChatAdapter.getAdapter().getItemCount() - 1);
                                        activity.presenter.onUpdateData();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void onEditClick(ModelDefaultMessages m) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View viewAlert = getLayoutInflater().inflate(R.layout.alert_dialog_builder_transition, null);
        builder.setView(viewAlert);
        AlertDialog alertDialog = builder.create();

        TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
        tvTitle.setText("Вы уверены что хотите изменить заявку?");
        TextView tvOk = viewAlert.findViewById(R.id.tvExit);
        tvOk.setText("Изменить");
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

                DialogSponsorEditParentInterForm dialog = new DialogSponsorEditParentInterForm(activity, m.getInterFormId(), m.getMessageId());
                dialog.show();
                dialog.setOnItemClickListener(new DialogSponsorEditParentInterForm.OnItemClickListener(){
                    @Override
                    public void onItemClick(ArrayList<ModelDefaultMessages> modelChats) {
                        dialog.dismiss();
                        ChatAdapter.updateData(modelChats);
                        rvChatAdapter.scrollToPosition(rvChatAdapter.getAdapter().getItemCount() - 1);
                        activity.presenter.onUpdateData();
                    }
                });

            }
        });
    }

    private void onNoLick(ModelDefaultMessages mMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View viewAlert = getLayoutInflater().inflate(R.layout.alert_dialog_builder_transition, null);
        builder.setView(viewAlert);
        AlertDialog alertDialog = builder.create();

        TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
        tvTitle.setText("Вы действительно хотите Отклонить заявку?");
        TextView tvOk = viewAlert.findViewById(R.id.tvExit);
        tvOk.setText("Отклонить");
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
                jsonData.addProperty("Index", mMessage.getInterFormId());
                jsonData.addProperty(F.SourceId, SPONSOR_ID);
                jsonData.addProperty(F.TargetId, mChat.getUserId());
                jsonData.addProperty(F.ChatId, mChat.getChatId());
                jsonData.addProperty(F.MessageId, mMessage.getMessageId());
                jsonData.addProperty(F.Message, "Ваша заявка отклонена.");

                String SOAP = SoapRequest(func_RejectInterFormParentToSponsor, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.code() >= 200 && response.code() <= 300) {
                            String xml = response.body().string();
                            String result = _Web.XMLToJson(xml);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result.equals("0")) activity.alert.onToast("Ошибка, попробуйте позже.");
                                    else if (result.equals("2")) {
                                        activity.alert.onToast("Заявка была отменена Родителем.");
                                        activity.presenter.onUpdateData();
                                    }
                                    else {
                                        Type founderListType = new TypeToken<ArrayList<ModelDefaultMessages>>(){}.getType();
                                        ArrayList<ModelDefaultMessages> modelChats = new Gson().fromJson(result, founderListType);
                                        ChatAdapter.updateData(modelChats);
                                        rvChatAdapter.scrollToPosition(rvChatAdapter.getAdapter().getItemCount() - 1);
                                        activity.presenter.onUpdateData();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void onSendMessage() {
        String msg = etMessage.getText().toString();
        etMessage.setText("");

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.SourceId, SPONSOR_ID);
        jsonData.addProperty(F.TargetId, mChat.getUserId());
        jsonData.addProperty(F.Message, msg);

        String SOAP = SoapRequest(func_SendMessage, jsonData.toString());
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
                    String result = _Web.XMLToJson(xml);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("0")) {}
                            else {
                                Type founderListType = new TypeToken<ArrayList<ModelDefaultMessages>>(){}.getType();
                                ArrayList<ModelDefaultMessages> modelChats = new Gson().fromJson(result, founderListType);
                                ChatAdapter.updateData(modelChats);
                                rvChatAdapter.scrollToPosition(rvChatAdapter.getAdapter().getItemCount() - 1);
                            }
                        }
                    });
                }
            }
        });
    }

    private void onMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(activity, view);
        if (mChat.getType().equals("PrivateChat")){
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
                            jsonData.addProperty(F.SourceId, SPONSOR_ID);
                            jsonData.addProperty(ChatId, mChat.getChatId());

                            String SOAP = SoapRequest(func_RemoveChat, jsonData.toString());
                            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                            Request request = new Request.Builder().url(URL).post(body).build();
                            HttpClient.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(final Call call, IOException e) { }
                                @Override
                                public void onResponse(Call call, final Response response) throws IOException {
                                    if (response.code() >= 200 && response.code() <= 300) {
                                        String xml = response.body().string();
                                        String result = _Web.XMLToJson(xml);
                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (result.equals("0")) activity.alert.onToast("Ошибка, попробуйте позже");
                                                else if (result.equals("1")){
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
        if (mChat.getType().equals("SponsorInterForm")){
            popupMenu.getMenu().add("Отменить заявку").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
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
                            "отменить заявку?");
                    tvOk.setText("Отменить");
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
                            jsonData.addProperty(F.UserId, SPONSOR_ID);
                            jsonData.addProperty(F.Type, "SPONSOR");
                            jsonData.addProperty(F.Index, mChat.getInterFormId());

                            String SOAP = SoapRequest(func_CancelInterForm, jsonData.toString());
                            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                            Request request = new Request.Builder().url(URL).post(body).build();
                            HttpClient.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(final Call call, IOException e) { }
                                @Override
                                public void onResponse(Call call, final Response response) throws IOException {
                                    if (response.code() >= 200 && response.code() <= 300) {
                                        String xml = response.body().string();
                                        String result = _Web.XMLToJson(xml);
                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (result.equals("0")) activity.alert.onToast("Ошибка, попробуйте позже");
                                                else if (result.equals("1")){
                                                    activity.alert.onToast("Заявка успешно отменена");
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
        if (popupMenu.getMenu().size() > 0) popupMenu.show();
    }

    private void onCancel(ModelDefaultMessages mMessage){
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
                jsonData.addProperty(F.UserId, mMessage.getSourceId());
                jsonData.addProperty(F.Type, "Sponsor");
                jsonData.addProperty(F.Index, mMessage.getInterFormId());
                jsonData.addProperty(F.MessageId, mMessage.getMessageId());
                jsonData.addProperty(ChatId, mChat.getChatId());

                String SOAP = SoapRequest(func_CancelInterForm, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.code() >= 200 && response.code() <= 300) {
                            String xml = response.body().string();
                            String result = _Web.XMLToJson(xml);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result.equals("0")) activity.alert.onToast("Ошибка, попробуйте позже");
                                    else{
                                        activity.alert.onToast("Заявка успешно отменена");
                                        Type founderListType = new TypeToken<ArrayList<ModelDefaultMessages>>() {}.getType();
                                        ArrayList<ModelDefaultMessages> mChatMessages = new Gson().fromJson(result, founderListType);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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


//    @Override
//    public void onItemClick(ModelDefaultMessages m, int answerType, int gradePos) {
//        if (answerType == 1) onYesClick(m);
//        else if (answerType == 2) onNoLick(m);
//        else if (answerType == 3) onCancel(m);
//        else if (answerType == 4) onEditClick(m);
//    }

    public void setData(ModelSponsorUsersListAdapter m) {
        this.mChat = m;
        onSetTargetInfo();
        onLoadChatMessages();
    }
    @Override
    public void onMessageClick(ModelDefaultMessages m){

    }
    @Override
    public void onMessageLong(ModelDefaultMessages m){

    }
}
