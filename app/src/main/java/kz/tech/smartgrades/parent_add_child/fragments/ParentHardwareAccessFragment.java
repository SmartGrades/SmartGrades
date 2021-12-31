package kz.tech.smartgrades.parent_add_child.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parent_add_child.ParentAddChildActivity;

public class ParentHardwareAccessFragment extends Fragment implements View.OnClickListener {

    private ParentAddChildActivity activity;

    private ImageView ivBack, ivNext;
    private TextView tvHardwareAccessTitle;
    private boolean isHardwareAccess = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddChildActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_hardware_access, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        changeText();
        onImageTernar(isHardwareAccess);
    }

    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvHardwareAccessTitle = view.findViewById(R.id.tvHardwareAccessTitle);
        ivNext = view.findViewById(R.id.ivNext);
        ivNext.setOnClickListener(this);
    }

    private void changeText() {
        tvHardwareAccessTitle.setText(activity.onTranslateString(R.string.hardware_access_title));

    }

    private void onImageTernar(boolean isImg) {
        ivNext.setImageResource(isImg? R.drawable.img_right_arrow_green : R.drawable.img_right_arrow_uncheck_green);
    }




    private void onBack() {
        activity.onBackPressed();
    }

    private void onNext() {
        if (isHardwareAccess) {
            activity.alert.hideKeyboard(activity);
            activity.onNextFragment();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;

            case R.id.ivNext:
                onNext();
                break;
        }
    }
}
