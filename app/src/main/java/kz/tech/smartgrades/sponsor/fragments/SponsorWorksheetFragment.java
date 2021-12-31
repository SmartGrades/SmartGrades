package kz.tech.smartgrades.sponsor.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.SponsorActivity;

public class SponsorWorksheetFragment extends Fragment implements View.OnClickListener {

    public SponsorWorksheetFragment() {}

    private SponsorActivity activity;

    private CircleImageView civAvatar;
    private TextView tvFullName;
    private EditText etAboutMe;
    private Button btnSend;
    private ImageView ivIcon;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SponsorActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sponsor_worksheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onLoadMentorData();
    }


    private void initViews(View view) {
        civAvatar = view.findViewById(R.id.civAvatar);
        tvFullName = view.findViewById(R.id.tvFullName);

        ivIcon = view.findViewById(R.id.ivIcon);

        etAboutMe = view.findViewById(R.id.etAboutMe);

        btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        etAboutMe.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                //      adapter.getFilter().filter(cs);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //  Toast.makeText(getApplicationContext(),"before text change",Toast.LENGTH_LONG).show();
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                //   Toast.makeText(getApplicationContext(),"after text change",Toast.LENGTH_LONG).show();
                if (arg0.length() == 0) {
                    onButtonSendIsReady(false);
                } else if (arg0.length() > 0) {
                    onButtonSendIsReady(true);
                }
            }
        });
    }

    private void onLoadMentorData() {
        ivIcon.setBackgroundResource(R.drawable.img_icon_sponsor);

        String avatar = activity.login.loadUserDate(LoginKey.AVATAR);
        if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);
        else civAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));

        String fullName = activity.login.loadUserDate(LoginKey.FIRST_NAME) + " " + activity.login.loadUserDate(LoginKey.LAST_NAME);
        if (fullName != null) {
            tvFullName.setText(fullName);
        }
    }

    private void onButtonSendIsReady(boolean isReady) {
        btnSend.setBackgroundResource(isReady ? R.drawable.btn_background_purple :
                R.drawable.btn_background_purple_alpha);
        btnSend.setEnabled(isReady);
    }

    private void onSendClick() {
        activity.presenter.getAddSponsorWorksheet(etAboutMe.getText().toString(), "");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend:
                onSendClick();
                break;
        }
    }
}
