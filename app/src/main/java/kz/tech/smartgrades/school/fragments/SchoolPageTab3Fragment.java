package kz.tech.smartgrades.school.fragments;
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

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolRequestAdapter;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class SchoolPageTab3Fragment extends Fragment implements SchoolRequestAdapter.OnItemClickListener{

    private SchoolActivity activity;
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private TextView tvLabel;
    private RecyclerView rvRequestList;
    private SchoolRequestAdapter requestAdapter;
    private ArrayList<ModelInterForm> InterForms;

    public static SchoolPageTab3Fragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        SchoolPageTab3Fragment fragment = new SchoolPageTab3Fragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (SchoolActivity) getActivity();
        if(getArguments() != null) mPage = getArguments().getInt(ARG_PAGE);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_school_page_tab3, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }
    private void initUI(View view){
        tvLabel = view.findViewById(R.id.tvLabel);
        rvRequestList = view.findViewById(R.id.rvRequestList);
        requestAdapter = new SchoolRequestAdapter(activity);
        requestAdapter.setOnItemClickListener(this);
        rvRequestList.setLayoutManager(new LinearLayoutManager(activity));
        rvRequestList.setAdapter(requestAdapter);

        if(!listIsNullOrEmpty(InterForms)) tvLabel.setVisibility(View.GONE);
        else tvLabel.setVisibility(View.VISIBLE);
        if(requestAdapter!=null) requestAdapter.updateData(InterForms);
    }

    public void updateData(ArrayList<ModelInterForm> InterForms){
        this.InterForms = InterForms;
        if(requestAdapter!=null) requestAdapter.updateData(InterForms);
        if(tvLabel != null){
            if(!listIsNullOrEmpty(InterForms)) tvLabel.setVisibility(View.GONE);
            else tvLabel.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onItemClick(ModelInterForm m){
        activity.onClickInterForm(m);
    }
}