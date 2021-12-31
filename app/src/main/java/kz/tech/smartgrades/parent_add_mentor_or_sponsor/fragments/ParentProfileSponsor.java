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

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.model.ModelChat;
import kz.tech.smartgrades.parent.model.ModelMentorSponsorRoom;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.ParentAddSponsorOrMentorActivity;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.dialog.DialogDefaultChat;
import kz.tech.smartgrades.root.login.LoginKey;

public class ParentProfileSponsor extends Fragment implements View.OnClickListener {

    private ParentAddSponsorOrMentorActivity activity;
    private CircleImageView civAvatar;
    private TextView tvFullName, tvLogin, tvSponsoredDateValue, tvSponsoredChildValue, tvAboutMe;
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
        return inflater.inflate(R.layout.fragment_parent_profile_sponsor, container, false);
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
        tvLogin = view.findViewById(R.id.tvLogin);
        tvSponsoredDateValue = view.findViewById(R.id.tvSponsoredDateValue);
        tvSponsoredChildValue = view.findViewById(R.id.tvSponsoredChildValue);
        tvAboutMe = view.findViewById(R.id.tvTitle1);
        btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        btnChat = view.findViewById(R.id.btnChat);
        btnChat.setOnClickListener(this);
    }

    private void onSend() {
        if (m != null) {
            activity.parentAddMentorFragmentAdapter.setSponsorWardData(m);
            activity.onNextFragment(4);
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
        tvLogin.setText(m.getLogin());
        tvAboutMe.setText(m.getAboutMe());
        tvSponsoredDateValue.setText(m.getSponsoredDate());
        tvSponsoredChildValue.setText(m.getSponsoredChild());
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
