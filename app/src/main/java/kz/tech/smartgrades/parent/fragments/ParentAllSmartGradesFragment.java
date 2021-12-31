package kz.tech.smartgrades.parent.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentSmartGradesListAdapter;
import kz.tech.smartgrades.parent.bottom_sheet_dialogs.BSDSetSmartGrades;
import kz.tech.smartgrades.parent.model.ModelLessonsWithSmartGrades;
import kz.tech.smartgrades.root.login.LoginKey;

import static kz.tech.smartgrades.GET.CurrentMonth;
import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class ParentAllSmartGradesFragment extends Fragment implements View.OnClickListener,
        ParentSmartGradesListAdapter.OnItemClickListener,
        ParentSmartGradesListAdapter.OnItemLongClickListener{

    private ParentActivity activity;
    private String PARENT_ID;

    private TextView tvLessonName;
    private CircleImageView сivAvatar;
    private ImageView ivNav;
    private TextView tvMonth;
    private RecyclerView rvIncomeSource;

    private ImageView ivNotification;
    private TextView tvHold;

    private ArrayList<ModelLessonsWithSmartGrades> lessonsWithSmartGrades;
    private ParentSmartGradesListAdapter parentSmartGradesListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
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
        tvHold = view.findViewById(R.id.tvHold);
    }
    public void setLessonList(ArrayList<ModelLessonsWithSmartGrades> lessonsWithSmartGrades) {
        this.lessonsWithSmartGrades = lessonsWithSmartGrades;
        loadLessonList();
    }

    private void loadLessonList() {
        rvIncomeSource.setVisibility(View.VISIBLE);
        rvIncomeSource.setLayoutManager(new GridLayoutManager(activity, 2));

        parentSmartGradesListAdapter = new ParentSmartGradesListAdapter(activity);
        parentSmartGradesListAdapter.setOnItemClickListener(this);
        parentSmartGradesListAdapter.setOnItemLongClickListener(this);
        rvIncomeSource.setAdapter(parentSmartGradesListAdapter);
        parentSmartGradesListAdapter.updateData(lessonsWithSmartGrades);
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
        activity.setLessonsWithSmartGradesData(mLessonsWithSmartGrades);
        activity.onNextFragment();
    }

    @Override
    public void onSmartLongClickListener(ModelLessonsWithSmartGrades mLessonsWithSmartGrades, View itemView) {
        boolean flag = false;
        if (!listIsNullOrEmpty(mLessonsWithSmartGrades.getMentors())) {
            for (kz.tech.smartgrades.parent.model.ModelMentorList m : mLessonsWithSmartGrades.getMentors()) {
                if (m.getId().equals(PARENT_ID)) {
                    flag = true;
                    break;
                }
            }
        }
        if (flag) {
            BSDSetSmartGrades dialog = new BSDSetSmartGrades(activity, mLessonsWithSmartGrades);
            dialog.show();
        } else {
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            View popUpView = getLayoutInflater().inflate(R.layout.pw_parent_smart_grades_error, null);
            PopupWindow popupWindow = new PopupWindow(popUpView, width, height, true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.setElevation(20);
            }
            popupWindow.showAsDropDown(itemView, 0, 0);
        }
    }
}
