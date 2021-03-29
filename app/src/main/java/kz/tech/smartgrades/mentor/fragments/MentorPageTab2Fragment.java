package kz.tech.smartgrades.mentor.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import kz.tech.esparta.R;

public class MentorPageTab2Fragment extends Fragment{
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    public static MentorPageTab2Fragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MentorPageTab2Fragment fragment = new MentorPageTab2Fragment();
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
        View view = inflater.inflate(R.layout.fragment_mentor_page_tab2, container, false);
        return view;
    }
}