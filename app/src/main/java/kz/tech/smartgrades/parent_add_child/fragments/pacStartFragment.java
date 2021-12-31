package kz.tech.smartgrades.parent_add_child.fragments;

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

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import kz.tech.esparta.R;
import kz.tech.smartgrades._System;
import kz.tech.smartgrades.parent_add_child.ParentAddChildActivity;

public class pacStartFragment extends Fragment implements View.OnClickListener {

    private ParentAddChildActivity activity;

    private ImageView ivBack;
    private Button btnAddChild, btnSearchChild;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddChildActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_add_child, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        btnAddChild = view.findViewById(R.id.btnAddChild);
        btnAddChild.setOnClickListener(this);
        btnSearchChild = view.findViewById(R.id.btnSearchChild);
        btnSearchChild.setOnClickListener(this);
    }

    private void onBack() {
        activity.onBackPressed();
    }

    private void onAddChild() {
        activity.onNextFragment();
    }

    private void onSearchChild() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.btnAddChild:
                onAddChild();
                break;
            case R.id.btnSearchChild:
                onSearchChild();
                break;
        }
    }
}
