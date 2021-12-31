package kz.tech.smartgrades.parent.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentSponsorGradesListAdapter;
import kz.tech.smartgrades.parent.adapters.ParentSponsorPeriodListAdapter;
import kz.tech.smartgrades.parent.model.ModelSponsorship;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.SoapRequest;

public class ParentSponsoringInfoFragment extends Fragment implements View.OnClickListener{

    private ParentActivity activity;
    private ModelSponsorship m;

    private ImageView ivBack;
    private TextView tvSponsorName;
    private CircleImageView civSponsorAvatar;
    private TextView tvHeaderSumOfIncome;
    private TextView tvAverageGradeCount;
    private TextView tvReceivedGradesCount;
    private RecyclerView rvWeeksList;
    private TextView tvLesson1;
    private TextView tvLesson2;
    private TextView tvLesson3;
    private TextView tvLesson4;
    private RecyclerView rvLesson1;
    private RecyclerView rvLesson2;
    private RecyclerView rvLesson3;
    private RecyclerView rvLesson4;
    private ProgressBar pbAverageGrade;
    private ProgressBar pbReceivedGrades;
    private TextView tvTotal;

    public ParentSponsoringInfoFragment(ModelSponsorship modelChildSponsorship) {
        this.m = modelChildSponsorship;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (ParentActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child_sponsoring, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setSponsoringData();
    }

    private void initView(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvSponsorName = view.findViewById(R.id.tvSponsorName);
        civSponsorAvatar = view.findViewById(R.id.civSponsorAvatar);
        tvHeaderSumOfIncome = view.findViewById(R.id.tvHeaderSumOfIncome);
        tvAverageGradeCount = view.findViewById(R.id.tvAverageGradeCount);
        tvReceivedGradesCount = view.findViewById(R.id.tvReceivedGradesCount);
        rvWeeksList = view.findViewById(R.id.rvWeeksList);
        tvLesson1 = view.findViewById(R.id.tvLesson1);
        tvLesson2 = view.findViewById(R.id.tvLesson2);
        tvLesson3 = view.findViewById(R.id.tvLesson3);
        tvLesson4 = view.findViewById(R.id.tvLesson4);
        rvLesson1 = view.findViewById(R.id.rvLesson1);
        rvLesson2 = view.findViewById(R.id.rvLesson2);
        rvLesson3 = view.findViewById(R.id.rvLesson3);
        rvLesson4 = view.findViewById(R.id.rvLesson4);
        pbAverageGrade = view.findViewById(R.id.pbAverageGrade);
        pbReceivedGrades = view.findViewById(R.id.pbReceivedGrades);
        tvTotal = view.findViewById(R.id.tvTotal);
    }

    private void setSponsoringData() {
        String avatar = m.getSponsorAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civSponsorAvatar);
        else civSponsorAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
        tvSponsorName.setText(m.getSponsorFirstName() + " " + m.getSponsorLastName());
        tvAverageGradeCount.setText(m.getCurrentAverageGrade() + "/" + m.getAverageGrade());
        pbAverageGrade.setProgress((int) ((m.getCurrentAverageGrade() / m.getAverageGrade()) * 100));
        tvReceivedGradesCount.setText(m.getCurrentCountGrade() + "/" + m.getCountGrade());
        pbReceivedGrades.setProgress((int) ((m.getCurrentCountGrade() / (m.getCountGrade() * 1.0f)) * 100));
        tvHeaderSumOfIncome.setText("₸ + " + m.getTotalReward());
        if (!listIsNullOrEmpty(m.getLessons())) {
            if (m.getLessons().size() >= 1) {
                tvLesson1.setVisibility(View.VISIBLE);
                rvLesson1.setVisibility(View.VISIBLE);
                tvLesson1.setText(m.getLessons().get(0).getLessonName());
                ParentSponsorGradesListAdapter adapter = new ParentSponsorGradesListAdapter(activity);
                rvLesson1.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
                rvLesson1.setAdapter(adapter);
                adapter.updateData(m.getLessons().get(0).getGrades());
            }
            if (m.getLessons().size() >= 2) {
                tvLesson2.setVisibility(View.VISIBLE);
                rvLesson2.setVisibility(View.VISIBLE);
                tvLesson2.setText(m.getLessons().get(1).getLessonName());
                ParentSponsorGradesListAdapter adapter = new ParentSponsorGradesListAdapter(activity);
                rvLesson2.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
                rvLesson2.setAdapter(adapter);
                adapter.updateData(m.getLessons().get(1).getGrades());
            }
            if (m.getLessons().size() >= 3) {
                tvLesson3.setVisibility(View.VISIBLE);
                rvLesson3.setVisibility(View.VISIBLE);
                tvLesson3.setText(m.getLessons().get(2).getLessonName());
                ParentSponsorGradesListAdapter adapter = new ParentSponsorGradesListAdapter(activity);
                rvLesson3.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
                rvLesson3.setAdapter(adapter);
                adapter.updateData(m.getLessons().get(2).getGrades());
            }
            if (m.getLessons().size() >= 4) {
                tvLesson4.setVisibility(View.VISIBLE);
                rvLesson4.setVisibility(View.VISIBLE);
                tvLesson4.setText(m.getLessons().get(3).getLessonName());
                ParentSponsorGradesListAdapter adapter = new ParentSponsorGradesListAdapter(activity);
                rvLesson4.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
                rvLesson4.setAdapter(adapter);
                adapter.updateData(m.getLessons().get(3).getGrades());
            }
        }
        if (!listIsNullOrEmpty(m.getPeriods())) {
            ParentSponsorPeriodListAdapter adapter = new ParentSponsorPeriodListAdapter(activity, m);
            rvWeeksList.setLayoutManager(new LinearLayoutManager(activity));
            rvWeeksList.setAdapter(adapter);
            adapter.updateData(m.getPeriods());
        }
        if (!stringIsNullOrEmpty(m.getCurrentTotalReward())) tvTotal.setText(m.getCurrentTotalReward());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBack();
                break;
        }
    }

    private void onBack() {
        activity.onRemoveFragment();
    }
}