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
import kz.tech.smartgrades.parent.adapters.ParentOtherListAdapter;
import kz.tech.smartgrades.parent.bottom_sheet_dialogs.BSDSetRegularGrades;
import kz.tech.smartgrades.parent.model.ModelLessonsWithOutSmartGrades;
import kz.tech.smartgrades.root.login.LoginKey;

import static kz.tech.smartgrades.GET.CurrentMonth;
import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class ParentAllOtherLessonFragment extends Fragment implements View.OnClickListener,
        ParentOtherListAdapter.OnItemClickListener,
        ParentOtherListAdapter.OnItemLongClickListener{

    private ParentActivity activity;
    private String PARENT_ID;

    private TextView tvLessonName;
    private CircleImageView сivAvatar;
    private ImageView ivNav;
    private TextView tvMonth;
    private TextView tvHold;
    private RecyclerView rvIncomeSource;

    private ArrayList<ModelLessonsWithOutSmartGrades> lessonsWithOutSmartGrades;
    private ParentOtherListAdapter parentOtherListAdapter;

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
        tvLessonName.setText(activity.getResources().getString(R.string.other_lesson_label));
        сivAvatar = view.findViewById(R.id.сivAvatar);
        ivNav = view.findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        tvMonth = view.findViewById(R.id.tvMonth);
        tvMonth.setText(CurrentMonth().toLowerCase());
        tvMonth.setOnClickListener(this);
        tvHold = view.findViewById(R.id.tvHold);
        tvMonth.setOnClickListener(this);
        rvIncomeSource = view.findViewById(R.id.rvIncomeSource);
    }
    public void setLessonList(ArrayList<ModelLessonsWithOutSmartGrades> lessonsWithOutSmartGrades) {
        this.lessonsWithOutSmartGrades = lessonsWithOutSmartGrades;
        loadLessonList();
    }

    private void loadLessonList() {
        rvIncomeSource.setVisibility(View.VISIBLE);
        rvIncomeSource.setLayoutManager(new GridLayoutManager(activity, 2));

        parentOtherListAdapter = new ParentOtherListAdapter(activity);
        parentOtherListAdapter.setOnItemClickListener(this);
        parentOtherListAdapter.setOnItemLongClickListener(this);
        rvIncomeSource.setAdapter(parentOtherListAdapter);
        parentOtherListAdapter.updateData(lessonsWithOutSmartGrades);
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
    public void onOtherClick(ModelLessonsWithOutSmartGrades mLessonsWithOutSmartGrades) {
        activity.setLessonsWithOutSmartGradesData(mLessonsWithOutSmartGrades);
        activity.onNextFragment();
    }

    @Override
    public void onOtherLongClickListener(ModelLessonsWithOutSmartGrades mLessonsWithOutSmartGrades, View itemView) {
        if (listIsNullOrEmpty(mLessonsWithOutSmartGrades.getMentors())) return;
        boolean flag = false;
        for (kz.tech.smartgrades.parent.model.ModelMentorList m : mLessonsWithOutSmartGrades.getMentors()) {
            if (m.getId().equals(PARENT_ID)) flag = true;
        }
        if (flag) {
            BSDSetRegularGrades dialog = new BSDSetRegularGrades(activity, mLessonsWithOutSmartGrades);
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
