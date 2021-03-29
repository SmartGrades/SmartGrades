package kz.tech.smartgrades.parent.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentUserListAdapter;
import kz.tech.smartgrades.parent.model.ModelChat;
import kz.tech.smartgrades.parent.model.ModelParentData;
import kz.tech.smartgrades.root.login.LoginKey;

public class ParentChatListFragment extends Fragment implements View.OnClickListener, ParentUserListAdapter.OnItemCLickListener {

    private ParentActivity activity;
    private String PARENT_ID;

    private ParentUserListAdapter UserListAdapter;
    private RecyclerView rvUserListAdapter;

    private ImageView ivBack;

    private ModelParentData modelParentData;

    public static ParentChatListFragment newInstance(ModelParentData m) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", m);
        ParentChatListFragment fragment = new ParentChatListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
        if(getArguments() != null) modelParentData = getArguments().getParcelable("model");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_chat_list2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onLoadChatsList();
    }

    private void initViews(View view){
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        UserListAdapter = new ParentUserListAdapter(activity);
        UserListAdapter.setOnItemCLickListener(this);
        rvUserListAdapter = view.findViewById(R.id.rvContactsList);
        rvUserListAdapter.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvUserListAdapter.setAdapter(UserListAdapter);
    }

    @Override
    public void onChatClick(ModelParentData m) {
        /*ModelChat mChat = new ModelChat();
        mChat.setModel(m.getModelType());

        /*if (m.getModelType().equals(F.InterForm)){
            mChat.setIndex(m.getInterForm().getInterFormId());
            mChat.setAnswer(m.getInterForm().getAnswer());
            mChat.setChatId(m.getInterForm().getChatId());
            mChat.setSourceId(m.getInterForm().getSourceId());

            if (m.getInterForm().getType().equals("Sponsor")){
                mChat.setUserId(m.getInterForm().getSponsorId());
                mChat.setAvatar(m.getInterForm().getSponsorAvatar());
                mChat.setFirstName(m.getInterForm().getSponsorFirstName());
                mChat.setLastName(m.getInterForm().getSponsorLastName());
            }
            else if (m.getInterForm().getType().equals("Mentor")){
                mChat.setUserId(m.getInterForm().getMentorId());
                mChat.setAvatar(m.getInterForm().getMentorAvatar());
                mChat.setFirstName(m.getInterForm().getMentorFirstName());
                mChat.setLastName(m.getInterForm().getMentorLastName());
            }
        }
        else
        if (m.getModelType().equals(F.PrivateChat)){
            mChat.setChatId(m.getPrivateChatData().getChatId());
            mChat.setUserId(m.getPrivateChatData().getTargetId());
            mChat.setAvatar(m.getPrivateChatData().getTargetAvatar());
            mChat.setFirstName(m.getPrivateChatData().getTargetFirstName());
            mChat.setLastName(m.getPrivateChatData().getTargetLastName());
            mChat.setType(m.getPrivateChatData().getTargetType());
        }*/


        //activity.setChatModel(mChat);
        //activity.onNextFragment();

    }

    public void setData(ModelParentData m) {
        this.modelParentData = m;
    }

    @Override
    public void onMentorClick(int type, ModelParentData m) {

    }

    @Override
    public void onLessonrClick(int i, ModelParentData modelParentData) {

    }

    private void onLoadChatsList() {
        UserListAdapter.updateData(modelParentData, true);
    }

    private void onBack() {
        activity.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
        }
    }
}