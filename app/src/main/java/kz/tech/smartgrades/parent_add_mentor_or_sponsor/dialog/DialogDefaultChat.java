package kz.tech.smartgrades.parent_add_mentor_or_sponsor.dialog;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import kz.tech.smartgrades.mentor.models.ModelMentorGroups;
import kz.tech.smartgrades.parent.adapters.DefaultMessagesAdapter;
import kz.tech.smartgrades.parent.model.ModelChat;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.ParentAddSponsorOrMentorActivity;
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
import static kz.tech.smartgrades._Web.func_Chats;
import static kz.tech.smartgrades._Web.func_SendMessage;


public class DialogDefaultChat extends Dialog implements View.OnClickListener {

    private ParentAddSponsorOrMentorActivity activity;
    private String PARENT_ID;
    private Dialog DIALOG;

    private CircleImageView civAvatar;
    private TextView tvName;
    private ImageView ivBack, ivMenu;

    private ModelChat mChat;
    private DefaultMessagesAdapter ChatAdapter;
    private RecyclerView rvChatAdapter;

    private FrameLayout flBottomViews, flButtons, flMessageEnter;
    private TextView tvNo, tvEdit, tvYes;
    private EditText etMessage;
    private ImageView ivSend;


    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(ModelMentorGroups modelSchoolGroups);
    }

    public DialogDefaultChat(@NonNull ParentAddSponsorOrMentorActivity activity, ModelChat mChat) {
        super(activity, R.style.CustomDialog2);
        View view = getLayoutInflater().inflate(R.layout.fragment_parent_chat_list, null, false);
        this.setContentView(view);

        this.activity = activity;
        this.mChat = mChat;
        DIALOG = this;
        DIALOG.setCanceledOnTouchOutside(true);

        initViews(view);
        onSetTargetInfo();
        onLoadChatMessages();
    }
    private void initViews(View view) {
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);

        civAvatar = view.findViewById(R.id.civAvatar);
        tvName = view.findViewById(R.id.tvName);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivMenu = view.findViewById(R.id.ivMenu);
        ivMenu.setOnClickListener(this);
        ivMenu.setVisibility(View.GONE);

        ChatAdapter = new DefaultMessagesAdapter(activity, activity.login.loadUserDate(LoginKey.ID));
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
        else civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));

        tvName.setText(mChat.getFirstName() + " " + mChat.getLastName());
    }
    //CHATS
    private void onLoadChatMessages() {
        flBottomViews.setVisibility(View.VISIBLE);
        flMessageEnter.setVisibility(View.VISIBLE);
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.SourceId, PARENT_ID);
        jsonData.addProperty(F.TargetId, mChat.getUserId());

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
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("0")) {
                            } else {
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
    private void onSendMessage() {
        String msg = etMessage.getText().toString();
        etMessage.setText("");

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.SourceId, PARENT_ID);
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
        if (mChat.getType() == null){

        }
        if (popupMenu.getMenu().size() > 0) popupMenu.show();
    }

    private void onBack() {
        DIALOG.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.ivSend:
                onSendMessage();
                break;
        }
    }
}
