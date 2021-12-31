package kz.tech.smartgrades.parent_add_mentor_or_sponsor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.dialog.DialogMentorAddChild;
import kz.tech.smartgrades.parent.fragments.ParentChatFragment;
import kz.tech.smartgrades.parent.model.ModelChat;
import kz.tech.smartgrades.parent.model.ModelMentorSponsorRoom;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.ParentAddSponsorOrMentorActivity;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.dialog.DialogDefaultChat;

public class ParentProfileMentor extends Fragment implements View.OnClickListener {

    private ParentAddSponsorOrMentorActivity activity;
    private CircleImageView civAvatar;
    private TextView tvFullName, tvDescription, tvAboutMe;
    private Button btnSend, btnChat;
    private ImageView ivBack;

    private ModelMentorSponsorRoom m;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddSponsorOrMentorActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_profile_mentor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        civAvatar = view.findViewById(R.id.civAvatar);
        tvFullName = view.findViewById(R.id.tvFullName);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvAboutMe = view.findViewById(R.id.tvTitle1);
        btnChat = view.findViewById(R.id.btnChat);
        btnChat.setOnClickListener(this);
        btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
    }

    private void onSend() {
        if (m != null) {
            activity.parentAddMentorFragmentAdapter.setMentorWardData(m);
            activity.onNextFragment(3);
        }
    }
    private void onChat() {
        ModelChat mChat = new ModelChat();
        mChat.setAvatar(m.getAvatar());
        mChat.setFirstName(m.getFirstName());
        mChat.setLastName(m.getLastName());
        mChat.setUserId(m.getUserId());
        DialogDefaultChat dialog = new DialogDefaultChat(activity, mChat);
        dialog.show();
    }

    public void setProfileData(ModelMentorSponsorRoom m) {
        this.m = m;
        String avatar = m.getAvatar();
        if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);
        else civAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));

        tvFullName.setText(m.getFirstName() + " " + m.getLastName());
        tvAboutMe.setText(m.getAboutMe());
        tvDescription.setText(m.getDescription());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChat:
                onChat();
                break;
            case R.id.btnSend:
                onSend();
                break;
            case R.id.ivBack:
                onBack();
                break;
        }
    }

    private void onBack() {
        activity.onBack();
    }
}
