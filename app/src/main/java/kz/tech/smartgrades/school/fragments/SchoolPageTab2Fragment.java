package kz.tech.smartgrades.school.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import kz.tech.esparta.R;

public class SchoolPageTab2Fragment extends Fragment{
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    public static SchoolPageTab2Fragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        SchoolPageTab2Fragment fragment = new SchoolPageTab2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_school_page_tab2, container, false);
        return view;
    }
}