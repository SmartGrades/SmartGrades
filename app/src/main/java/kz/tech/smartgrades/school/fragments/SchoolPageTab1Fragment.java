package kz.tech.smartgrades.school.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kz.tech.esparta.R;
import kz.tech.smartgrades.school.SchoolActivity;

public class SchoolPageTab1Fragment extends Fragment implements View.OnClickListener {

    private SchoolActivity activity;
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private Button btnEnableOffers;

    public static SchoolPageTab1Fragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        SchoolPageTab1Fragment fragment = new SchoolPageTab1Fragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mPage = getArguments().getInt(ARG_PAGE);
        }
        activity = (SchoolActivity) getActivity();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_school_page_tab1, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view) {
        btnEnableOffers = view.findViewById(R.id.btnEnableOffers);
        btnEnableOffers.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnEnableOffers:
                onEnableOffersClick();
                break;
        }
    }
    private void onEnableOffersClick() {
        activity.alert.onToast("Данная функция временно не доступна.");
    }
}