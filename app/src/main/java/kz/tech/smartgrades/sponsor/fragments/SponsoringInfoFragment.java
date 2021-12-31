package kz.tech.smartgrades.sponsor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.model.ModelSponsorship;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import kz.tech.smartgrades.sponsor.adapters.SponsorGradesListAdapter;
import kz.tech.smartgrades.sponsor.adapters.SponsorPeriodListAdapter;
import kz.tech.smartgrades.sponsor.models.ModelSponsorChildrenList;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class SponsoringInfoFragment extends Fragment implements View.OnClickListener{

    private SponsorActivity activity;
    private ModelSponsorship m;
    private ModelSponsorChildrenList mChild;

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
    private TextView tvMyReward;

    public SponsoringInfoFragment(ModelSponsorship modelChildSponsorship, ModelSponsorChildrenList mChild) {
        this.m = modelChildSponsorship;
        this.mChild = mChild;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (SponsorActivity) getActivity();
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
        tvMyReward = view.findViewById(R.id.tvMyReward);
        tvMyReward.setVisibility(View.GONE);
    }

    private void setSponsoringData() {
        String avatar = mChild.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civSponsorAvatar);
        else civSponsorAvatar.setImageDrawable(getResources().getDrawable(R.drawable.img_default_avatar));
        tvSponsorName.setText(mChild.getFirstName() + " " + mChild.getLastName());
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
                SponsorGradesListAdapter adapter = new SponsorGradesListAdapter(activity);
                rvLesson1.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
                rvLesson1.setAdapter(adapter);
                adapter.updateData(m.getLessons().get(0).getGrades());
            }
            if (m.getLessons().size() >= 2) {
                tvLesson2.setVisibility(View.VISIBLE);
                rvLesson2.setVisibility(View.VISIBLE);
                tvLesson2.setText(m.getLessons().get(1).getLessonName());
                SponsorGradesListAdapter adapter = new SponsorGradesListAdapter(activity);
                rvLesson2.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
                rvLesson2.setAdapter(adapter);
                adapter.updateData(m.getLessons().get(1).getGrades());
            }
            if (m.getLessons().size() >= 3) {
                tvLesson3.setVisibility(View.VISIBLE);
                rvLesson3.setVisibility(View.VISIBLE);
                tvLesson3.setText(m.getLessons().get(2).getLessonName());
                SponsorGradesListAdapter adapter = new SponsorGradesListAdapter(activity);
                rvLesson3.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
                rvLesson3.setAdapter(adapter);
                adapter.updateData(m.getLessons().get(2).getGrades());
            }
            if (m.getLessons().size() >= 4) {
                tvLesson4.setVisibility(View.VISIBLE);
                rvLesson4.setVisibility(View.VISIBLE);
                tvLesson4.setText(m.getLessons().get(3).getLessonName());
                SponsorGradesListAdapter adapter = new SponsorGradesListAdapter(activity);
                rvLesson4.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
                rvLesson4.setAdapter(adapter);
                adapter.updateData(m.getLessons().get(3).getGrades());
            }
        }
        if (!listIsNullOrEmpty(m.getPeriods())) {
            SponsorPeriodListAdapter adapter = new SponsorPeriodListAdapter(activity, m);
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