package kz.tech.smartgrades.parent_add_mentor_or_sponsor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.adapters.DefaultMessagesAdapter;
import kz.tech.smartgrades.parent.model.ModelMentorSponsorRoom;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.ParentAddSponsorOrMentorActivity;
import kz.tech.smartgrades.root.login.LoginKey;


public class ParentChatAddMentorFragment extends Fragment implements View.OnClickListener {

    private ParentAddSponsorOrMentorActivity activity;
    private CircleImageView civAvatar;
    private TextView tvTitle;
    private ImageView ivBack;
    private RecyclerView recyclerView;
    private DefaultMessagesAdapter defaultChatAdapter;
    private ModelMentorSponsorRoom mentorModel;

    private String ChatId = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddSponsorOrMentorActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_default_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        civAvatar = view.findViewById(R.id.civAvatar);
        tvTitle = view.findViewById(R.id.tvTitle);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        defaultChatAdapter = new DefaultMessagesAdapter(activity, activity.login.loadUserDate(LoginKey.ID));
        recyclerView = view.findViewById(R.id.rvChatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(defaultChatAdapter);
    }

    private void onChatMentor() {
        /*DatabaseReference dbrChats = new _Firebase().getRefChats();
        dbrChats.child(ChatId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) return;
                ArrayList<ModelDefaultChat> modelMultiTypes = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    ModelDefaultChat valueList = dataSnapshot.child(key).getValue(ModelDefaultChat.class);
                    modelMultiTypes.add(valueList);
                }
                DatabaseReference dbrUsers = new _Firebase().getRefUsers();
                dbrUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (int j = 0; j < modelMultiTypes.size(); j++) {
                                ModelUserList sourceUser = dataSnapshot.child(modelMultiTypes.get(j).getSourceId()).getValue(ModelUserList.class);
                                ModelUserList targetUser = dataSnapshot.child(modelMultiTypes.get(j).getTargetId()).getValue(ModelUserList.class);

                                modelMultiTypes.get(j).setSourceId(sourceUser.getId());
                                modelMultiTypes.get(j).setSourceAvatar(sourceUser.getAvatar());
                                modelMultiTypes.get(j).setSourceFullName(sourceUser.getFirstName());

                                modelMultiTypes.get(j).setTargetId(targetUser.getId());
                                modelMultiTypes.get(j).setTargetAvatar(targetUser.getAvatar());
                                modelMultiTypes.get(j).setTargetFullName(targetUser.getFirstName());
                            }
                            defaultChatAdapter.updateData(modelMultiTypes);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });*/


    }

    public void setWardData(ModelMentorSponsorRoom value) {
        mentorModel = value;
        Picasso.get().load(mentorModel.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);
        tvTitle.setText(mentorModel.getFirstName());
        onChatMentor();
        activity.hideToolbar();
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