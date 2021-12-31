package kz.tech.smartgrades.auth.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.esparta.R;
import kz.tech.smartgrades.S;
import kz.tech.smartgrades.auth.AuthActivity;

import static kz.tech.smartgrades._Web.SoapRequest;

public class SuccessFragment extends Fragment implements View.OnClickListener {

    private AuthActivity activity;
    private String TYPE;

    private TextView tvDesSchool;
    private TextView tvDesParent;
    private TextView tvDesChild;
    private TextView tvDesSponsor;
    private TextView tvDesMentor;

    private Button btnNext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AuthActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth_success, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        tvDesSchool = view.findViewById(R.id.tvDesSchool);
        tvDesSponsor = view.findViewById(R.id.tvDesSponsor);
        tvDesParent = view.findViewById(R.id.tvDesParent);
        tvDesChild = view.findViewById(R.id.tvDesChild);
        tvDesMentor = view.findViewById(R.id.tvDesMentor);
        btnNext = view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
    }

    private void onNext() {
        activity.onStartAuth();
        btnNext.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                onNext();
                break;
        }
    }

    public void setType(String type) {
        TYPE = type;
        if (type.equals(S.CHILD)) {
            tvDesChild.setVisibility(View.VISIBLE);
        }
        else if (type.equals(S.PARENT)) {
            tvDesParent.setVisibility(View.VISIBLE);
        }
        else if (type.equals(S.INVESTOR)) {
            tvDesSponsor.setVisibility(View.VISIBLE);
        }
        else if (type.equals(S.SCHOOL)) {
            tvDesSchool.setVisibility(View.VISIBLE);
        }
        else if (type.equals(S.MENTOR)) {
            tvDesMentor.setVisibility(View.VISIBLE);
        }
    }
}
