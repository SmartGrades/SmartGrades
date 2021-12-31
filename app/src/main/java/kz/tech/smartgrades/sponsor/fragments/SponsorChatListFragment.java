package kz.tech.smartgrades.sponsor.fragments;

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
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import kz.tech.smartgrades.sponsor.adapters.SponsorUsersListAdapter;
import kz.tech.smartgrades.sponsor.dialogs.SponsorDialogChat;
import kz.tech.smartgrades.sponsor.models.ModelSponsorData;
import kz.tech.smartgrades.sponsor.models.ModelSponsorUsersListAdapter;

public class SponsorChatListFragment extends Fragment implements View.OnClickListener, SponsorUsersListAdapter.OnItemClickListener{

    private SponsorActivity activity;
    private String PARENT_ID;

    private Dialog DIALOG;

    private SponsorUsersListAdapter UserListAdapter;
    private RecyclerView rvUserListAdapter;

    private ImageView ivBack;

    private ModelSponsorData modelSponsorData;

    private ModelSponsorUsersListAdapter modelSponsorUsersListAdapter;

    private SponsorDialogChat.OnItemClickListener onItemClickListener;

    public void setData(ModelSponsorData mSponsorData) {
        this.modelSponsorData = mSponsorData;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SponsorActivity) getActivity();
        assert activity != null;
        PARENT_ID = activity.login.loadUserDate(LoginKey.LOGIN);
        if(getArguments() != null) modelSponsorData = getArguments().getParcelable("model");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sponsor_chat_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onLoadChatsList();
    }

    public interface OnItemClickListener {
        void onItemClick();
    }
    public void setOnItemClickListener(SponsorDialogChat.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void initViews(View view){
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        UserListAdapter = new SponsorUsersListAdapter(activity);
        UserListAdapter.setOnItemClickListener(this);
        rvUserListAdapter = view.findViewById(R.id.rvContactsList);
        rvUserListAdapter.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvUserListAdapter.setAdapter(UserListAdapter);
    }

    private void onLoadChatsList() {
        UserListAdapter.updateData(modelSponsorData, true);
    }
    public void updateChatsList(ModelSponsorData mSponsorData) {
        modelSponsorData = mSponsorData;
        UserListAdapter.updateData(modelSponsorData, true);
    }

    private void onBack() {
//        DIALOG.dismiss();
        activity.onBackPressed();
    }

    @Override
    public void onItemClick(ModelSponsorUsersListAdapter m) {
//        activity.ChatVisible(false);
        ModelSponsorUsersListAdapter mChat = new ModelSponsorUsersListAdapter();
        mChat.setInterFormId(m.getInterFormId());
        mChat.setAnswer(m.getAnswer());
        mChat.setChatId(m.getChatId());
        mChat.setUserId(m.getUserId());
        mChat.setAvatar(m.getAvatar());
        mChat.setFirstName(m.getFirstName());
        mChat.setLastName(m.getLastName());
        mChat.setType(m.getType());

//        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.container2, SponsorChatFragment.newInstance(mChat));
//        ft.commit();

//        activity.setChatModel(mChat);
//        activity.onNextFragment();
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