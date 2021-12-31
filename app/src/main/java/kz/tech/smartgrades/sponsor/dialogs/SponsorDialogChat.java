package kz.tech.smartgrades.sponsor.dialogs;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import kz.tech.smartgrades.sponsor.adapters.SponsorUsersListAdapter;
import kz.tech.smartgrades.sponsor.fragments.SponsorChatFragment;
import kz.tech.smartgrades.sponsor.models.ModelSponsorData;
import kz.tech.smartgrades.sponsor.models.ModelSponsorUsersListAdapter;

public class SponsorDialogChat extends Dialog implements View.OnClickListener, SponsorUsersListAdapter.OnItemClickListener {

    private SponsorActivity activity;
    private String PARENT_ID;

    private Dialog DIALOG;

    private SponsorUsersListAdapter UserListAdapter;
    private RecyclerView rvUserListAdapter;

    private ImageView ivBack;

    private ModelSponsorData modelSponsorData;

    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onItemClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public SponsorDialogChat(SponsorActivity activity, ModelSponsorData modelSponsorData) {
        super(activity, R.style.CustomDialog2);
        this.activity = activity;
        this.modelSponsorData = modelSponsorData;

        DIALOG = this;
        DIALOG.setCanceledOnTouchOutside(true);
        View view = getLayoutInflater().inflate(R.layout.dialog_parent_chat, null, false);
        DIALOG.setContentView(view);

        initViews(view);
        onLoadChatsList();
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
        DIALOG.dismiss();
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

        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container2, SponsorChatFragment.newInstance(mChat));
        ft.commit();
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