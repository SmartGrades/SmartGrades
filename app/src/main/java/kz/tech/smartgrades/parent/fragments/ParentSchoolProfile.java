package kz.tech.smartgrades.parent.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelUserData;
import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentSchoolChildListAdapter;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.models.ModelSchoolInfo;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.SoapRequest;

public class ParentSchoolProfile extends Fragment implements View.OnClickListener, ParentSchoolChildListAdapter.OnItemClickListener{

    private ParentActivity activity;
    private ArrayList<ModelUserData> childList;
    private ModelSchoolInfo mSchool;

    private ImageView ivNav;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvContacts;
    private RecyclerView rvChildList;

    private ParentSchoolChildListAdapter parentSchoolChildListAdapter;

    public ParentSchoolProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_school_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    private void initUI(View view) {
        ivNav = view.findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        tvName = view.findViewById(R.id.tvName);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvContacts = view.findViewById(R.id.tvContacts);
        rvChildList = view.findViewById(R.id.rvChildList);
    }

    private void setChildList() {
        rvChildList.setLayoutManager(new LinearLayoutManager(activity));
        parentSchoolChildListAdapter = new ParentSchoolChildListAdapter(activity);
        parentSchoolChildListAdapter.setOnItemClickListener(this);
        rvChildList.setAdapter(parentSchoolChildListAdapter);
        parentSchoolChildListAdapter.updateData(childList);
    }

    private void setSchoolInfo() {
        if (!stringIsNullOrEmpty(mSchool.getSchoolName())) tvName.setText(mSchool.getSchoolName());
        if (!stringIsNullOrEmpty(mSchool.getAddress())) tvAddress.setText(mSchool.getAddress());
        String contact = "";
        if (!stringIsNullOrEmpty(mSchool.getPhones().get(0).getPhone())) contact += mSchool.getPhones().get(0).getPhone() + "\n";
        if (!stringIsNullOrEmpty(mSchool.getMails().get(0).getMail())) contact += mSchool.getMails().get(0).getMail() + "\n";
        if (!stringIsNullOrEmpty(contact)) tvContacts.setText(contact);
    }

    private void onBack() {
        activity.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNav:
                onBack();
                break;
        }
    }

    public void setSchoolModel(ModelSchoolInfo mSchool) {
        this.mSchool = mSchool;
        this.childList = mSchool.getChildren();
        setSchoolInfo();
        setChildList();
    }

    @Override
    public void onClick(ModelChildData modelMentor) {

    }
}