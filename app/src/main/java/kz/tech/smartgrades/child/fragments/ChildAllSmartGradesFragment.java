package kz.tech.smartgrades.child.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.child.adapters.ChildSmartGradesListAdapter;
import kz.tech.smartgrades.parent.model.ModelLessonsWithSmartGrades;
import kz.tech.smartgrades.root.login.LoginKey;

import static kz.tech.smartgrades.GET.CurrentMonth;

public class ChildAllSmartGradesFragment extends Fragment implements View.OnClickListener,
        ChildSmartGradesListAdapter.OnItemClickListener,
        ChildSmartGradesListAdapter.OnItemLongClickListener{

    private ChildActivity activity;
    private String PARENT_ID;

    private TextView tvLessonName;
    private CircleImageView сivAvatar;
    private ImageView ivNav;
    private TextView tvMonth;
    private RecyclerView rvIncomeSource;

    private ImageView ivNotification;
    private TextView tvHold;

    private ArrayList<ModelLessonsWithSmartGrades> lessonsWithSmartGrades;
    private ChildSmartGradesListAdapter childSmartGradesListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (ChildActivity) getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_all_lessons, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view){
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
        tvLessonName = view.findViewById(R.id.tvLessonName);
        tvLessonName.setText(activity.getResources().getString(R.string.smart_grades_label));
        сivAvatar = view.findViewById(R.id.сivAvatar);
        ivNav = view.findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        tvMonth = view.findViewById(R.id.tvMonth);
        tvMonth.setText(CurrentMonth().toLowerCase());
        tvMonth.setOnClickListener(this);
        rvIncomeSource = view.findViewById(R.id.rvIncomeSource);

        ivNotification = view.findViewById(R.id.ivNotification);
        ivNotification.setVisibility(View.GONE);
        tvHold = view.findViewById(R.id.tvHold);
        tvHold.setVisibility(View.GONE);
    }
    public void setLessonList(ArrayList<ModelLessonsWithSmartGrades> lessonsWithSmartGrades) {
        this.lessonsWithSmartGrades = lessonsWithSmartGrades;
        loadLessonList();
    }

    private void loadLessonList() {
        rvIncomeSource.setVisibility(View.VISIBLE);
        rvIncomeSource.setLayoutManager(new GridLayoutManager(activity, 2));

        childSmartGradesListAdapter = new ChildSmartGradesListAdapter(activity);
        childSmartGradesListAdapter.setOnItemClickListener(this);
        childSmartGradesListAdapter.setOnItemLongClickListener(this);
        rvIncomeSource.setAdapter(childSmartGradesListAdapter);
        childSmartGradesListAdapter.updateData(lessonsWithSmartGrades);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.ivNav:
                onBack();
                break;
            //
            //
        }
    }

    private void onBack() {
        activity.onBackPressed();
    }

    @Override
    public void onSmartClick(ModelLessonsWithSmartGrades mLessonsWithSmartGrades) {
        activity.onNextFragment();
        activity.setLessonsWithSmartGradesData(mLessonsWithSmartGrades);
    }

    @Override
    public void onSmartLongClickListener(ModelLessonsWithSmartGrades mLessonsWithSmartGrades) {}
}
