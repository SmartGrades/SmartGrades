package kz.tech.smartgrades.auth.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.AuthActivity;

import static kz.tech.smartgrades.S.CHILD;
import static kz.tech.smartgrades.S.MENTOR;
import static kz.tech.smartgrades.S.PARENT;
import static kz.tech.smartgrades.S.SCHOOL;
import static kz.tech.smartgrades.S.SPONSOR;

public class TypeFragment extends Fragment implements View.OnClickListener {

    private AuthActivity activity;

    private TextView tvStatusSelectTitle, tvParent, tvChild, tvMentor;
    private FrameLayout flParent, flChild, flMentor, flSchool, flSponsor;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AuthActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_status_select, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        changeText();
    }

    private void initViews(View view) {
        tvStatusSelectTitle = view.findViewById(R.id.tvStatusSelectTitle);
        tvParent = view.findViewById(R.id.tvParent);
        tvChild = view.findViewById(R.id.tvChild);
        tvMentor = view.findViewById(R.id.tvMentor);
        flParent = view.findViewById(R.id.flParent);
        flParent.setOnClickListener(this);
        flChild = view.findViewById(R.id.flChild);
        flChild.setOnClickListener(this);
        flMentor = view.findViewById(R.id.flMentor);
        flMentor.setOnClickListener(this);
        flSchool = view.findViewById(R.id.flSchool);
        flSchool.setOnClickListener(this);
        flSponsor = view.findViewById(R.id.flSponsor);
        flSponsor.setOnClickListener(this);
    }

    private void changeText() {
        tvStatusSelectTitle.setText(activity.onTranslateString(R.string.who_is_the_account_created_for));
        tvParent.setText(activity.onTranslateString(R.string.parent));
        tvChild.setText(activity.onTranslateString(R.string.child));
        tvMentor.setText(activity.onTranslateString(R.string.mentor));
    }

    private void onBack() {
        activity.onBackPressed();
    }

    private void onStatusSelect(int statusSelect) {
        switch (statusSelect) {
            case 1: activity.onSetType(PARENT); break;
            case 2: activity.onSetType(CHILD); break;
            case 3: activity.onSetType(MENTOR); break;
            case 4: activity.onSetType(SCHOOL); break;
            case 5: activity.onSetType(SPONSOR); break;
        }
        activity.onNextFragment(statusSelect);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flParent:
                onStatusSelect(1);
                break;
            case R.id.flChild:
                onStatusSelect(2);
                break;
            case R.id.flMentor:
                onStatusSelect(3);
                break;
            case R.id.flSchool:
                onStatusSelect(4);
                break;
            case R.id.flSponsor:
                onStatusSelect(5);
                break;
        }
    }
}//
