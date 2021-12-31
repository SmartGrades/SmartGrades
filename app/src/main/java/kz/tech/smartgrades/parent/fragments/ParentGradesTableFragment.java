package kz.tech.smartgrades.parent.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
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
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._System;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.mentor.models.ModelDefaultMessages;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.DefaultMessagesAdapter;
import kz.tech.smartgrades.parent.model.ModelChat;
import kz.tech.smartgrades.root.login.LoginKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.F.ChatId;
import static kz.tech.smartgrades.F.Message;
import static kz.tech.smartgrades.F.SourceId;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_AcceptInterFormMentorToParent;
import static kz.tech.smartgrades._Web.func_AcceptInterFormSponsorToParent;
import static kz.tech.smartgrades._Web.func_CancelInterForm;
import static kz.tech.smartgrades._Web.func_Chats;
import static kz.tech.smartgrades._Web.func_ParentRemoveChildFromMentorGroup;
import static kz.tech.smartgrades._Web.func_ParentSetChildReward;
import static kz.tech.smartgrades._Web.func_RejectInterFormMentorToParent;
import static kz.tech.smartgrades._Web.func_RejectInterFormSponsorToParent;
import static kz.tech.smartgrades._Web.func_RemoveChat;
import static kz.tech.smartgrades._Web.func_SendMessage;

public class ParentGradesTableFragment extends Fragment implements View.OnClickListener, DefaultMessagesAdapter.OnItemClickListener {

    private ParentActivity activity;
    private String PARENT_ID;

    private CircleImageView civAvatar;
    private TextView tvName;
    private ImageView ivBack, ivMenu;

    private ModelChat mChat;
    private DefaultMessagesAdapter ChatAdapter;
    private RecyclerView rvChatAdapter;

    private FrameLayout flBottomViews, flMessageEnter;
    private TextView tvNo, tvEdit, tvYes;
    private EditText etMessage;
    private ImageView ivSend;


    public static ParentGradesTableFragment newInstance(ModelChat m) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", m);
        ParentGradesTableFragment fragment = new ParentGradesTableFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
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

        ChatAdapter = new DefaultMessagesAdapter(activity, activity.login.loadUserDate(LoginKey.ID));
        ChatAdapter.setOnItemClickListener(this);
        rvChatAdapter = view.findViewById(R.id.rvChatAdapter);
        rvChatAdapter.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvChatAdapter.setAdapter(ChatAdapter);

        flBottomViews = view.findViewById(R.id.flBottomViews);
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
        else civAvatar.setBackgroundResource(R.drawable.img_default_avatar);

        tvName.setText(mChat.getFirstName() + " " + mChat.getLastName());
    }
    private void onLoadChatMessages() {
        if (mChat.getType()!=null && mChat.getType().equals("PrivateChat")) flMessageEnter.setVisibility(View.VISIBLE);

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.ChatId, mChat.getChatId());
        jsonData.addProperty(F.TargetId, PARENT_ID);

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
                                if (!stringIsNullOrEmpty(mChat.getType()) && !mChat.getType().equals("Child")){
                                    if (mChat.getModel() != null && mChat.getModel().equals(F.PrivateChat)){
                                    flMessageEnter.setVisibility(View.VISIBLE);
                                    flBottomViews.setVisibility(View.VISIBLE);
                                    }
                                    if (!stringIsNullOrEmpty(mChat.getType()) && mChat.getType().equals("Sponsor")){
                                        flMessageEnter.setVisibility(View.VISIBLE);
                                        flBottomViews.setVisibility(View.VISIBLE);
                                    }
                                }
                                else {
                                    flMessageEnter.setVisibility(View.GONE);
                                    flBottomViews.setVisibility(View.GONE);
                                }
                                activity.presenter.onStartPresenter();
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
                            "удалить ребенка из группы " + mChat.getGroupName() + "?");
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
                            jsonData.addProperty(F.ParentId, PARENT_ID);
                            jsonData.addProperty(F.MentorId, mChat.getUserId());
                            jsonData.addProperty(F.GroupId, mChat.getGroupId());
                            jsonData.addProperty(F.ChildId, mChat.getChildId());

                            String SOAP = SoapRequest(func_ParentRemoveChildFromMentorGroup, jsonData.toString());
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
                                                if (result.equals("0")) activity.alert.onToast("Ошибка: не удалось удалить ребенка из группы");
                                                else {
                                                    activity.alert.onToast("Ребенок успешно удален из группы");
                                                    onLoadChatMessages();
                                                    activity.presenter.onStartPresenter();
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
            popupMenu.getMenu().add("Настроить умные оценки").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    popupMenu.dismiss();
                    //Объявляем переменные
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
                    final boolean[] bOk = {false};
                    FrameLayout[] FL = new FrameLayout[5];
                    LinearLayout[] LL = new LinearLayout[5];
                    EditText[] etMoneyNumb = new EditText[5];
                    TextView tvCancelOrOk;
                    ImageView[] ivPlusOrMinus = new ImageView[5];
                    final int[] Index = {-1};
                    final boolean[] bPlus = new boolean[5];
                    //Переносим нашу вертску XML во View, обозначаем переменные
                    View view = getLayoutInflater().inflate(R.layout.dialog_setup_reward_for_grades, null, false);
                    bottomSheetDialog.setContentView(view);
                    View bottomSheet = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
                    BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                    ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
                    bottomSheet.setLayoutParams(layoutParams);
                    layoutParams.height = (_System.getWindowHeight(activity));
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    //Обозначаем переменные
                    FL[0] = view.findViewById(R.id.fl2);
                    FL[1] = view.findViewById(R.id.fl3);
                    FL[2] = view.findViewById(R.id.fl4);
                    FL[3] = view.findViewById(R.id.fl5);
                    LL[0] = view.findViewById(R.id.ll2);
                    LL[1] = view.findViewById(R.id.ll3);
                    LL[2] = view.findViewById(R.id.ll4);
                    LL[3] = view.findViewById(R.id.ll5);
                    etMoneyNumb[0] = view.findViewById(R.id.et2MoneyNumb);
                    etMoneyNumb[1] = view.findViewById(R.id.et3MoneyNumb);
                    etMoneyNumb[2] = view.findViewById(R.id.et4MoneyNumb);
                    etMoneyNumb[3] = view.findViewById(R.id.et5MoneyNumb);
                    ivPlusOrMinus[0] = view.findViewById(R.id.ivPlusOrMinus2);
                    ivPlusOrMinus[1] = view.findViewById(R.id.ivPlusOrMinus3);
                    ivPlusOrMinus[2] = view.findViewById(R.id.ivPlusOrMinus4);
                    ivPlusOrMinus[3] = view.findViewById(R.id.ivPlusOrMinus5);
                    tvCancelOrOk = view.findViewById(R.id.tvCancelOrOk);
                    //Регистрация событий
                    FL[0].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            etMoneyNumb[0].requestFocus();
                            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(etMoneyNumb[0], InputMethodManager.SHOW_IMPLICIT);
                        }
                    });
                    FL[1].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            etMoneyNumb[1].requestFocus();
                            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(etMoneyNumb[1], InputMethodManager.SHOW_IMPLICIT);
                        }
                    });
                    FL[2].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            etMoneyNumb[2].requestFocus();
                            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(etMoneyNumb[2], InputMethodManager.SHOW_IMPLICIT);
                        }
                    });
                    FL[3].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            etMoneyNumb[3].requestFocus();
                            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(etMoneyNumb[3], InputMethodManager.SHOW_IMPLICIT);
                        }
                    });
                    ivPlusOrMinus[2].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            byte b = 2;
                            if (bPlus[b]) {
                                bPlus[b] = false;
                                ivPlusOrMinus[b].setImageResource(R.drawable.img_togle_minus_red);
                                String text = etMoneyNumb[b].getText().toString();
                                if (text.length() > 0 && !text.substring(0, 1).equals("-"))
                                    etMoneyNumb[b].setText("-" + text);
                                else etMoneyNumb[b].setText("-");
                                etMoneyNumb[b].setSelection(etMoneyNumb[b].getText().length());
                            } else {
                                bPlus[b] = true;
                                ivPlusOrMinus[b].setImageResource(R.drawable.img_togle_plus_purple);
                                String text = etMoneyNumb[b].getText().toString();
                                if (text.length() > 0 && text.substring(0, 1).equals("-"))
                                    etMoneyNumb[b].setText(text.subSequence(1, text.length()));
                            }
                            if (Index[0] != b) {
                                etMoneyNumb[b].requestFocus();
                                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(etMoneyNumb[b], InputMethodManager.SHOW_IMPLICIT);
                            }
                        }
                    });
                    etMoneyNumb[0].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean hasFocus) {
                            if (hasFocus) {
                                if (Index[0] != -1) {
                                    LL[Index[0]].setBackgroundColor(Color.TRANSPARENT);
                                    etMoneyNumb[Index[0]].setTextColor(getResources().getColor(R.color.blue_sea));
                                    String text = etMoneyNumb[Index[0]].getText().toString();
                                    if (text.isEmpty() || text.equals("-") || text.equals("-0")) etMoneyNumb[Index[0]].setText("0");
                                    int i = Convert.Str2Int(etMoneyNumb[Index[0]].getText().toString());
                                    if (i < 0)
                                        ivPlusOrMinus[Index[0]].setImageResource(R.drawable.img_togle_minus_red);
                                    else if (i > 0)
                                        ivPlusOrMinus[Index[0]].setImageResource(R.drawable.img_togle_plus_purple);
                                    else
                                        ivPlusOrMinus[Index[0]].setImageResource(R.drawable.img_togle_neutral_gray);
                                }
                                Index[0] = 0;
                                String text = etMoneyNumb[Index[0]].getText().toString();
                                if (text.length() > 0 && !text.substring(0,1).equals("-")) {
                                    etMoneyNumb[Index[0]].setText("-");
                                    etMoneyNumb[Index[0]].setSelection(etMoneyNumb[Index[0]].getText().length());
                                }
                                etMoneyNumb[Index[0]].setTextColor(Color.WHITE);
                                LL[Index[0]].setBackgroundColor(getResources().getColor(R.color.blue_sea));
                                ivPlusOrMinus[Index[0]].setImageResource(R.drawable.img_togle_minus_red);
                                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(etMoneyNumb[0], InputMethodManager.SHOW_IMPLICIT);
                            }
                        }
                    });
                    etMoneyNumb[1].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean hasFocus) {
                            if (hasFocus) {
                                if (Index[0] != -1) {
                                    LL[Index[0]].setBackgroundColor(Color.TRANSPARENT);
                                    etMoneyNumb[Index[0]].setTextColor(getResources().getColor(R.color.blue_sea));
                                    String text = etMoneyNumb[Index[0]].getText().toString();
                                    if (text.isEmpty() || text.equals("-") || text.equals("-0")) etMoneyNumb[Index[0]].setText("0");
                                    int i = Convert.Str2Int(etMoneyNumb[Index[0]].getText().toString());
                                    if (i < 0)
                                        ivPlusOrMinus[Index[0]].setImageResource(R.drawable.img_togle_minus_red);
                                    else if (i > 0)
                                        ivPlusOrMinus[Index[0]].setImageResource(R.drawable.img_togle_plus_purple);
                                    else
                                        ivPlusOrMinus[Index[0]].setImageResource(R.drawable.img_togle_neutral_gray);
                                }
                                Index[0] = 1;
                                String text = etMoneyNumb[Index[0]].getText().toString();
                                if (text.length() > 0 && !text.substring(0,1).equals("-")) {
                                    etMoneyNumb[Index[0]].setText("-");
                                    etMoneyNumb[Index[0]].setSelection(etMoneyNumb[Index[0]].getText().length());
                                }
                                etMoneyNumb[Index[0]].setTextColor(Color.WHITE);
                                LL[Index[0]].setBackgroundColor(getResources().getColor(R.color.blue_sea));
                                ivPlusOrMinus[Index[0]].setImageResource(R.drawable.img_togle_minus_red);
                                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(etMoneyNumb[0], InputMethodManager.SHOW_IMPLICIT);
                            }
                        }
                    });
                    etMoneyNumb[2].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean hasFocus) {
                            if (hasFocus) {
                                if (Index[0] != -1) {
                                    LL[Index[0]].setBackgroundColor(Color.TRANSPARENT);
                                    etMoneyNumb[Index[0]].setTextColor(getResources().getColor(R.color.blue_sea));
                                    String text = etMoneyNumb[Index[0]].getText().toString();
                                    if (text.isEmpty() || text.equals("-") || text.equals("-0")) etMoneyNumb[Index[0]].setText("0");
                                    int i = Convert.Str2Int(etMoneyNumb[Index[0]].getText().toString());
                                    if (i < 0)
                                        ivPlusOrMinus[Index[0]].setImageResource(R.drawable.img_togle_minus_red);
                                    else if (i > 0)
                                        ivPlusOrMinus[Index[0]].setImageResource(R.drawable.img_togle_plus_purple);
                                    else
                                        ivPlusOrMinus[Index[0]].setImageResource(R.drawable.img_togle_neutral_gray);
                                }
                                Index[0] = 2;
                                String text = etMoneyNumb[Index[0]].getText().toString();
                                if (text.length() > 0 && text.substring(0,1).equals("0")) etMoneyNumb[Index[0]].setText("");
                                etMoneyNumb[Index[0]].setTextColor(Color.WHITE);
                                LL[Index[0]].setBackgroundColor(getResources().getColor(R.color.blue_sea));
                                ivPlusOrMinus[Index[0]].setImageResource(R.drawable.img_togle_plus_purple);
                                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(etMoneyNumb[0], InputMethodManager.SHOW_IMPLICIT);
                            }
                        }
                    });
                    etMoneyNumb[3].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean hasFocus) {
                            if (hasFocus) {
                                if (Index[0] != -1) {
                                    LL[Index[0]].setBackgroundColor(Color.TRANSPARENT);
                                    etMoneyNumb[Index[0]].setTextColor(getResources().getColor(R.color.blue_sea));
                                    String text = etMoneyNumb[Index[0]].getText().toString();
                                    if (text.isEmpty() || text.equals("-") || text.equals("-0")) etMoneyNumb[Index[0]].setText("0");
                                    int i = Convert.Str2Int(etMoneyNumb[Index[0]].getText().toString());
                                    if (i < 0)
                                        ivPlusOrMinus[Index[0]].setImageResource(R.drawable.img_togle_minus_red);
                                    else if (i > 0)
                                        ivPlusOrMinus[Index[0]].setImageResource(R.drawable.img_togle_plus_purple);
                                    else
                                        ivPlusOrMinus[Index[0]].setImageResource(R.drawable.img_togle_neutral_gray);
                                }
                                Index[0] = 3;
                                String text = etMoneyNumb[Index[0]].getText().toString();
                                if (text.length() > 0 && text.substring(0,1).equals("0")) etMoneyNumb[Index[0]].setText("");
                                etMoneyNumb[Index[0]].setTextColor(Color.WHITE);
                                LL[Index[0]].setBackgroundColor(getResources().getColor(R.color.blue_sea));
                                ivPlusOrMinus[Index[0]].setImageResource(R.drawable.img_togle_plus_purple);
                                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(etMoneyNumb[0], InputMethodManager.SHOW_IMPLICIT);
                            }
                        }
                    });
                    etMoneyNumb[0].setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                                if (etMoneyNumb[0].getText().equals("0") || etMoneyNumb[0].getText().equals("-0")) etMoneyNumb[0].setText("");
                            }
                            if (event.getAction() == KeyEvent.ACTION_UP) {
                                int totalSum = Convert.Str2Int(etMoneyNumb[0].getText().toString()) + Convert.Str2Int(etMoneyNumb[1].getText().toString()) + Convert.Str2Int(etMoneyNumb[2].getText().toString()) + Convert.Str2Int(etMoneyNumb[3].getText().toString());
                                if (totalSum < 0 || totalSum > 0) {
                                    bOk[0] = true;
                                    tvCancelOrOk.setText("Ок");
                                    return true;
                                } else {
                                    bOk[0] = false;
                                    tvCancelOrOk.setText("Отмена");
                                }

                            }
                            return false;
                        }
                    });
                    etMoneyNumb[1].setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                                if (etMoneyNumb[1].getText().equals("0") || etMoneyNumb[1].getText().equals("-0")) etMoneyNumb[1].setText("");
                            }
                            if (event.getAction() == KeyEvent.ACTION_UP) {
                                int totalSum = Convert.Str2Int(etMoneyNumb[0].getText().toString()) + Convert.Str2Int(etMoneyNumb[1].getText().toString()) + Convert.Str2Int(etMoneyNumb[2].getText().toString()) + Convert.Str2Int(etMoneyNumb[3].getText().toString());
                                if (totalSum < 0 || totalSum > 0) {
                                    bOk[0] = true;
                                    tvCancelOrOk.setText("Ок");
                                    return true;
                                } else {
                                    bOk[0] = false;
                                    tvCancelOrOk.setText("Отмена");
                                }
                            }
                            return false;
                        }
                    });
                    etMoneyNumb[2].setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if (event.getAction() == KeyEvent.ACTION_UP) {
                                int totalSum = Convert.Str2Int(etMoneyNumb[0].getText().toString()) + Convert.Str2Int(etMoneyNumb[1].getText().toString()) + Convert.Str2Int(etMoneyNumb[2].getText().toString()) + Convert.Str2Int(etMoneyNumb[3].getText().toString());
                                if (totalSum < 0 || totalSum > 0) {
                                    bOk[0] = true;
                                    tvCancelOrOk.setText("Ок");
                                    return true;
                                } else {
                                    bOk[0] = false;
                                    tvCancelOrOk.setText("Отмена");
                                }
                            }
                            return false;
                        }
                    });
                    etMoneyNumb[3].setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if (event.getAction() == KeyEvent.ACTION_UP) {
                                int totalSum = Convert.Str2Int(etMoneyNumb[0].getText().toString()) + Convert.Str2Int(etMoneyNumb[1].getText().toString()) + Convert.Str2Int(etMoneyNumb[2].getText().toString()) + Convert.Str2Int(etMoneyNumb[3].getText().toString());
                                if (totalSum < 0 || totalSum > 0) {
                                    bOk[0] = true;
                                    tvCancelOrOk.setText("Ок");
                                    return true;
                                } else {
                                    bOk[0] = false;
                                    tvCancelOrOk.setText("Отмена");
                                }
                            }
                            return false;
                        }
                    });
                    tvCancelOrOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomSheetDialog.dismiss();
                            if (bOk[0]) {
                                JsonObject jsonData = new JsonObject();
                                jsonData.addProperty(F.ParentId, PARENT_ID);
                                jsonData.addProperty(F.ChildId, mChat.getChildId());
                                jsonData.addProperty(F.MentorId, mChat.getUserId());
                                jsonData.addProperty(F.GroupId, mChat.getGroupId());
                                jsonData.addProperty("RewardFor2", etMoneyNumb[0].getText().toString());
                                jsonData.addProperty("RewardFor3", etMoneyNumb[1].getText().toString());
                                jsonData.addProperty("RewardFor4", etMoneyNumb[2].getText().toString());
                                jsonData.addProperty("RewardFor5", etMoneyNumb[3].getText().toString());

                                jsonData.addProperty(SourceId, PARENT_ID);
                                jsonData.addProperty(ChatId, mChat.getChatId());
                                jsonData.addProperty(Message, "Новые настройки Умных оценок:\nОценка 2 = " + etMoneyNumb[0].getText().toString() + " KZT\n" +
                                        "Оценка 3 = " + etMoneyNumb[1].getText().toString() + " KZT\n" +
                                        "Оценка 4 = " + etMoneyNumb[2].getText().toString() + " KZT\n" +
                                        "Оценка 5 = " + etMoneyNumb[3].getText().toString() + " KZT");
                                //jsonData.addProperty(F.Type, "10");// 10 - тип сообщения Умных Оценок

                                String SOAP = SoapRequest(func_ParentSetChildReward, jsonData.toString());
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
                        }
                    });
                    bottomSheetDialog.show();
                    return true;
                }
            });
        }
        else {
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
                                jsonData.addProperty(F.SourceId, PARENT_ID);
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
        }
        if (popupMenu.getMenu().size() > 0) popupMenu.show();
    }

    private void onBack() {
        activity.onRemoveFragment(1);
        if (mChat.getModel() != null && mChat.getModel().equals("PrivateChat")) activity.FragmentAdapter.setChatVisible(true);
    }

    private void onSendMessage() {
        String msg = etMessage.getText().toString();
        etMessage.setText("");

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.SourceId, PARENT_ID);
        jsonData.addProperty(F.TargetId, mChat.getUserId());
        jsonData.addProperty(ChatId, mChat.getChatId());
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

    private void onOk() {
        String func_name = null;
        if (mChat.getType().equals("Sponsor")){
            func_name = func_AcceptInterFormSponsorToParent;
        }
        else func_name = func_AcceptInterFormMentorToParent;

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.Index, mChat.getIndex());
        jsonData.addProperty(F.SourceId, PARENT_ID);
        jsonData.addProperty(F.TargetId, mChat.getUserId());



        String SOAP = SoapRequest(func_name, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String xml = response.body().string();
                if (response.code() >= 200 && response.code() <= 300) {
                    String result = _Web.XMLToJson(xml);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("0")) {
                            } else if (result.equals("1")) {
                                flBottomViews.setVisibility(View.GONE);
                                activity.presenter.onStartPresenter();
                            }
                        }
                    });
                }
            }
        });
    }
    private void onYes(ModelDefaultMessages mMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View viewAlert = getLayoutInflater().inflate(R.layout.alert_dialog_builder_transition, null);
        builder.setView(viewAlert);
        AlertDialog alertDialog = builder.create();

        TextView tvTitle = viewAlert.findViewById(R.id.tvTitle);
        tvTitle.setText("Принять заявку?");
        TextView tvOk = viewAlert.findViewById(R.id.tvExit);
        tvOk.setText("Принять");
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
                jsonData.addProperty(F.SourceId, PARENT_ID);
                jsonData.addProperty(F.TargetId, mChat.getUserId());
                jsonData.addProperty(F.ChatId, mChat.getChatId());
                jsonData.addProperty(F.MessageId, mMessage.getMessageId());

                String func_name = null;
                if (mChat.getType().equals("Mentor")){
                    func_name = func_AcceptInterFormMentorToParent;
                    jsonData.addProperty(F.Message, "Ваша заявка принята.");
                }
                else if (mChat.getType().equals("Sponsor")){
                    func_name = func_AcceptInterFormSponsorToParent;
                    jsonData.addProperty(F.Message, "Ваша заявка принята. Учет результатов начнется со следующего учебного дня.");
                }

                String SOAP = SoapRequest(func_name, jsonData.toString());
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
                                    if (result.equals("0")) activity.alert.onToast("Ошибка, попробуйте позже.");
                                    else if (result.equals("1")) {
                                        activity.presenter.onStartPresenter();
                                    }
                                    else if (result.equals("2")) {
                                        if (mChat.getType().equals("Sponsor")) activity.alert.onToast("Заявка была отменена Спонсором.");
                                        else activity.alert.onToast("Заявка была отменена Ментором.");
                                        activity.presenter.onStartPresenter();
                                    }
                                    else {
                                        Type founderListType = new TypeToken<ArrayList<ModelDefaultMessages>>() {
                                        }.getType();
                                        ArrayList<ModelDefaultMessages> modelChats = new Gson().fromJson(result, founderListType);
                                        ChatAdapter.updateData(modelChats);
                                        activity.presenter.onStartPresenter();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
    private void onNo(ModelDefaultMessages mMessage) {
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

                String func_name = null;
                if (mChat.getType().equals("Sponsor"))
                    func_name = func_RejectInterFormSponsorToParent;
                else func_name = func_RejectInterFormMentorToParent;

                JsonObject jsonData = new JsonObject();
                jsonData.addProperty("Index", mMessage.getInterFormId());
                jsonData.addProperty(F.SourceId, PARENT_ID);
                jsonData.addProperty(F.TargetId, mChat.getUserId());
                jsonData.addProperty(F.ChatId, mChat.getChatId());
                jsonData.addProperty(F.MessageId, mMessage.getMessageId());
                jsonData.addProperty(F.Message, "Ваша заявка отклонена.");

                String SOAP = SoapRequest(func_name, jsonData.toString());
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
                                        if (mChat.getType().equals("Sponsor")) activity.alert.onToast("Заявка была отменена Спонсором.");
                                        else activity.alert.onToast("Заявка была отменена Ментором.");
                                        activity.presenter.onStartPresenter();
                                    }
                                    else {
                                        Type founderListType = new TypeToken<ArrayList<ModelDefaultMessages>>() {
                                        }.getType();
                                        ArrayList<ModelDefaultMessages> modelChats = new Gson().fromJson(result, founderListType);
                                        ChatAdapter.updateData(modelChats);
                                        activity.presenter.onStartPresenter();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
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
                jsonData.addProperty(F.Type, mChat.getType());
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
            case R.id.tvOk:
                onOk();
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
//        if (answerType == 1) onYes(m);
//        else if (answerType == 2) onNo(m);
//        else if (answerType == 3) onCancel(m);
//    }
    @Override
    public void onMessageClick(ModelDefaultMessages m){

    }
    @Override
    public void onMessageLong(ModelDefaultMessages m){

    }
}