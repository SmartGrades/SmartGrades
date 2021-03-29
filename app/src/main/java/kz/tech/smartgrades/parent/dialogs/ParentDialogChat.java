package kz.tech.smartgrades.parent.dialogs;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentUserListAdapter;
import kz.tech.smartgrades.parent.model.ModelChat;
import kz.tech.smartgrades.parent.model.ModelParentData;
import kz.tech.smartgrades.root.login.LoginKey;

public class ParentDialogChat extends Dialog implements View.OnClickListener, ParentUserListAdapter.OnItemCLickListener {

    private ParentActivity activity;
    private String PARENT_ID;

    private Dialog DIALOG;

    private ParentUserListAdapter UserListAdapter;
    private RecyclerView rvUserListAdapter;

    private ImageView ivBack;

    private ModelParentData modelParentData;

    private OnItemClickListener onItemClickListener;

    @Override
    public void onChatClick(ModelParentData m) {
        onBack();
        ModelChat mChat = new ModelChat();
        //mChat.setModel(m.getModelType());
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
        else if (m.getModelType().equals(F.PrivateChat)){
            mChat.setChatId(m.getPrivateChatData().getChatId());
            mChat.setUserId(m.getPrivateChatData().getTargetId());
            mChat.setAvatar(m.getPrivateChatData().getTargetAvatar());
            mChat.setFirstName(m.getPrivateChatData().getTargetFirstName());
            mChat.setLastName(m.getPrivateChatData().getTargetLastName());
            mChat.setType(m.getPrivateChatData().getTargetType());
        }*/
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        //ft.replace(R.id.container2, ParentChatFragment.newInstance(mChat));
        ft.commit();
    }

    @Override
    public void onMentorClick(int type, ModelParentData m) {

    }

    @Override
    public void onLessonrClick(int i, ModelParentData modelParentData) {

    }

    public interface OnItemClickListener {
        void onItemClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public ParentDialogChat(ParentActivity activity, ModelParentData modelParentData) {
        super(activity, R.style.CustomDialog2);
        this.activity = activity;
        this.modelParentData = modelParentData;

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
        UserListAdapter = new ParentUserListAdapter(activity);
        UserListAdapter.setOnItemCLickListener(this);
        rvUserListAdapter = view.findViewById(R.id.rvContactsList);
        rvUserListAdapter.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvUserListAdapter.setAdapter(UserListAdapter);
    }

    private void onLoadChatsList() {
        UserListAdapter.updateData(modelParentData, true);
    }
    public void updateChatsList(ModelParentData mParentData) {
        modelParentData = mParentData;
        UserListAdapter.updateData(modelParentData, true);
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
        }
    }
}