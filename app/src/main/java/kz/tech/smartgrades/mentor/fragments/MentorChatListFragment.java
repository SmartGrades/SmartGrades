package kz.tech.smartgrades.mentor.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorUserListAdapter;
import kz.tech.smartgrades.mentor.dialog.MentorDialogChat;
import kz.tech.smartgrades.mentor.models.ModelMentorChat;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.root.login.LoginKey;

public class MentorChatListFragment extends Fragment implements View.OnClickListener, MentorUserListAdapter.OnItemClickListener {

    private MentorActivity activity;
    private String MENTOR_ID;

    private Dialog DIALOG;

    private MentorUserListAdapter UserListAdapter;
    private RecyclerView rvUserListAdapter;

    private ImageView ivBack;

    private ModelMentorData modelMentorData;

    private MentorDialogChat.OnItemClickListener onItemClickListener;

    public void setData(ModelMentorData mMentorData) {
        this.modelMentorData = mMentorData;
    }

    public interface OnItemClickListener {
        void onItemClick();
    }
    public void setOnItemClickListener(MentorDialogChat.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MentorActivity) getActivity();
        assert activity != null;
        MENTOR_ID = activity.login.loadUserDate(LoginKey.LOGIN);
        if(getArguments() != null) modelMentorData = getArguments().getParcelable("model");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mentor_chat_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onLoadChatsList();
    }

    private void initViews(View view){
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        UserListAdapter = new MentorUserListAdapter(activity, MENTOR_ID);
        UserListAdapter.setOnItemClickListener(this);
        rvUserListAdapter = view.findViewById(R.id.rvContactsList);
        rvUserListAdapter.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvUserListAdapter.setAdapter(UserListAdapter);
    }

    private void onLoadChatsList() {
        UserListAdapter.updateData(modelMentorData);
        UserListAdapter.selectList(F.InterForm);
    }

    public void updateChatsList(ModelMentorData modelMentorData){
        this.modelMentorData = modelMentorData;
        UserListAdapter.updateData(modelMentorData);
        UserListAdapter.selectList(F.InterForm);
    }


    private void onBack() {
        activity.onBackPressed();
    }

    @Override
    public void onItemClick(int i, ModelMentorChat m) {
//        activity.ChatVisible(false);

        /*ModelChat mChat = new ModelChat();
        mChat.setModel(m.getModel());
        if (m.getModel().equals(F.InterForm)){
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
        else if (m.getModel().equals(F.PrivateChat)){
            mChat.setChatId(m.getPrivateChat().getChatId());
            mChat.setUserId(m.getPrivateChat().getTargetId());
            mChat.setAvatar(m.getPrivateChat().getTargetAvatar());
            mChat.setFirstName(m.getPrivateChat().getTargetFirstName());
            mChat.setLastName(m.getPrivateChat().getTargetLastName());
            mChat.setType(m.getPrivateChat().getTargetType());
        }*/
        ModelMentorChat modelMentorChat = new ModelMentorChat();
//        modelMentorChat.setInterFormId(m.getInterFormId());
//        modelMentorChat.setAnswer(m.getAnswer());
//        modelMentorChat.setChatId(m.getChatId());
//
//        modelMentorChat.setUserId(m.getSourceId());
//        modelMentorChat.setAvatar(m.getAvatar());
//        modelMentorChat.setFirstName(m.getFirstName());
//        modelMentorChat.setLastName(m.getLastName());
//
//        modelMentorChat.setType("PrivateChat");

//        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.container2, MentorChatFragment.newInstance(modelMentorChat, null));
//        ft.commit();
        //как в Parent

        activity.setChatModel(modelMentorChat);
        activity.onNextFragment();
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