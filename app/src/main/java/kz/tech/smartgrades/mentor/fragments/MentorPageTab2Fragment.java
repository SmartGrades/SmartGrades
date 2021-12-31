package kz.tech.smartgrades.mentor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorChatAdapter;
import kz.tech.smartgrades.mentor.models.ModelMentorData;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class MentorPageTab2Fragment extends Fragment{

    private MentorActivity activity;
    private ModelMentorData mMentorData;
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    private TextView tvLabel;
    private RecyclerView rvChatsList;
    private MentorChatAdapter adapter;

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
        activity = (MentorActivity) getActivity();
        if(getArguments() != null) mPage = getArguments().getInt(ARG_PAGE);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_mentor_page_tab2, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        tvLabel = view.findViewById(R.id.tvLabel);

        rvChatsList = view.findViewById(R.id.rvChatsList);
        adapter = new MentorChatAdapter(activity);
        rvChatsList.setLayoutManager(new LinearLayoutManager(activity));
        rvChatsList.setAdapter(adapter);
        adapter.setOnItemClickListener(activity);
    }

    public void onSetMentorData(ModelMentorData mMentorData){
        this.mMentorData = mMentorData;
        if(!listIsNullOrEmpty(mMentorData.getChats())){
            if(adapter!=null) adapter.updateData(mMentorData.getChats());
            if(tvLabel!=null)tvLabel.setVisibility(View.GONE);
        }
        else if(tvLabel!=null) tvLabel.setVisibility(View.VISIBLE);
    }
}