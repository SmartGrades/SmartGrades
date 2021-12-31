package kz.tech.smartgrades.parent_add_child.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.esparta.R;
import kz.tech.smartgrades.S;
import kz.tech.smartgrades.parent_add_child.ParentAddChildActivity;

public class ParentAddChildFamilyStatus extends Fragment implements View.OnClickListener {

    private ParentAddChildActivity activity;

    private ImageView ivBack, ivNext;
    private TextView tvFamilyStatusTitle, tvYourStatusForChild;
    private CheckBox cbFather, cbMother, cbAnotherFamilyMember;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddChildActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_family_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        changeText();
    }

    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvFamilyStatusTitle = view.findViewById(R.id.tvFamilyStatusTitle);
        tvYourStatusForChild = view.findViewById(R.id.tvYourStatusForChild);
        cbFather = view.findViewById(R.id.cbFather);
        cbFather.setOnClickListener(this);
        cbMother = view.findViewById(R.id.cbMother);
        cbMother.setOnClickListener(this);
        cbAnotherFamilyMember = view.findViewById(R.id.cbAnotherFamilyMember);
        cbAnotherFamilyMember.setOnClickListener(this);
        ivNext = view.findViewById(R.id.ivNext);
        ivNext.setOnClickListener(this);
    }




    private void changeText() {
        tvFamilyStatusTitle.setText(activity.onTranslateString(R.string.enter_family_status));
        tvYourStatusForChild.setText(activity.onTranslateString(R.string.your_status_for_child));
        cbFather.setText(activity.onTranslateString(R.string.father));
        cbMother.setText(activity.onTranslateString(R.string.mother));
        cbAnotherFamilyMember.setText(activity.onTranslateString(R.string.another_family_member));
    }

    private void onImageTernar(boolean isImg) {
        ivNext.setImageResource(isImg? R.drawable.img_right_arrow_green : R.drawable.img_right_arrow_uncheck_green);
    }




    private void onBack() {
        activity.onBackPressed();
    }

    private void onFather() {
        onImageTernar(true);
        if (!cbFather.isChecked()) { cbFather.setChecked(true); }
        if (cbMother.isChecked()) { cbFather.setChecked(false); }
        if (cbAnotherFamilyMember.isChecked()) { cbAnotherFamilyMember.setChecked(false); }
    }

    private void onMother() {
        onImageTernar(true);
        if (!cbMother.isChecked()) { cbMother.setChecked(true); }
        if (cbFather.isChecked()) { cbFather.setChecked(false); }
        if (cbAnotherFamilyMember.isChecked()) { cbAnotherFamilyMember.setChecked(false); }
    }

    private void onAnotherFamilyMember() {
        onImageTernar(true);
        if (!cbAnotherFamilyMember.isChecked()) { cbAnotherFamilyMember.setChecked(true); }
        if (cbFather.isChecked()) { cbFather.setChecked(false); }
        if (cbMother.isChecked()) { cbMother.setChecked(false); }
    }


    private void onNext() {
        if (cbFather.isChecked() || cbMother.isChecked() || cbAnotherFamilyMember.isChecked()) {
            activity.alert.hideKeyboard(activity);
            activity.onNextFragment();

            String familyStatusChild = "";
            if (cbFather.isChecked()) {
                familyStatusChild = S.FATHER;
            } else if (cbMother.isChecked()) {
                familyStatusChild = S.MOTHER;
            } else if (cbAnotherFamilyMember.isChecked()) {
                familyStatusChild = S.FAMILY_MEMBER;
            }
            //activity.setChildType(familyStatusChild);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.cbFather:
                onFather();
                break;
            case R.id.cbMother:
                onMother();
                break;
            case R.id.cbAnotherFamilyMember:
                onAnotherFamilyMember();
                break;
            case R.id.ivNext:
                onNext();
                break;
        }
    }
}
