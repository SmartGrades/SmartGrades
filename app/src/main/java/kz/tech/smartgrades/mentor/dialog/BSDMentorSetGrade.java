package kz.tech.smartgrades.mentor.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.model.ModelLessonsWithSmartGrades;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;

public class BSDMentorSetGrade extends BottomSheetDialog implements View.OnClickListener{

    private Context context;
    private ModelLessonsWithSmartGrades mLesson;

    private TextView tvLessonName;
    private TextView tvRewardForAbsent;
    private TextView tvRewardFor2;
    private TextView tvRewardFor3;
    private TextView tvRewardFor4;
    private TextView tvRewardFor5;
    private CardView cvAbsent;
    private CardView cvGrade2;
    private CardView cvGrade3;
    private CardView cvGrade4;
    private CardView cvGrade5;

    private ModelSchoolStudent mStudent;
    private ModelSchoolClass mClass;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onGradeClick(int type);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public BSDMentorSetGrade(@NonNull Context context, ModelSchoolStudent mStudent, ModelSchoolClass mClass) {
        super(context);
        this.context = context;
        this.mStudent = mStudent;
        this.mClass = mClass;
        View view = getLayoutInflater().inflate(R.layout.bsd_mentor_send_grade, null, false);
        setContentView(view);
        initViews(view);
        show();
    }

    private void initViews(View view) {
        tvLessonName = view.findViewById(R.id.tvLessonName);
        tvRewardForAbsent = view.findViewById(R.id.tvRewardForAbsent);
        tvRewardFor2 = view.findViewById(R.id.tvRewardFor2);
        tvRewardFor3 = view.findViewById(R.id.tvRewardFor3);
        tvRewardFor4 = view.findViewById(R.id.tvRewardFor4);
        tvRewardFor5 = view.findViewById(R.id.tvRewardFor5);
        cvAbsent = view.findViewById(R.id.cvAbsent);
        cvAbsent.setOnClickListener(this);
        cvGrade2 = view.findViewById(R.id.cvGrade2);
        cvGrade2.setOnClickListener(this);
        cvGrade3 = view.findViewById(R.id.cvGrade3);
        cvGrade3.setOnClickListener(this);
        cvGrade4 = view.findViewById(R.id.cvGrade4);
        cvGrade4.setOnClickListener(this);
        cvGrade5 = view.findViewById(R.id.cvGrade5);
        cvGrade5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cvAbsent:
                onClick(0);
                break;
            case R.id.cvGrade2:
                onClick(2);
                break;
            case R.id.cvGrade3:
                onClick(3);
                break;
            case R.id.cvGrade4:
                onClick(4);
            case R.id.cvGrade5:
                onClick(5);
                break;
        }
    }
    private void onClick(int i){
        if(onItemClickListener!=null)
            onItemClickListener.onGradeClick(i);
        dismiss();
    }
}
