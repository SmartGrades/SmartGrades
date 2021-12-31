package kz.tech.smartgrades.mentor.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.adapters.MentorSetGradeLessonsAdapter;
import kz.tech.smartgrades.mentor.models.ModelMentorClass;
import kz.tech.smartgrades.mentor.models.ModelMentorStudentLessons;
import kz.tech.smartgrades.mentor.models.ModelMentorStudentsList;
import kz.tech.smartgrades.root.models.ModelLesson;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class BSDMentorSetGrade extends BottomSheetDialog implements View.OnClickListener, MentorSetGradeLessonsAdapter.OnItemClickListener{

    private Context context;

    private CircleImageView civAvatar;
    private TextView tvName;
    private LinearLayout llEnterMessage, llCancelLastGrade, llLate, llWasAbsentCause, llWasAbsentNonCause, llSick;
    private RecyclerView rvLessons;
    private TextView tvGrade2, tvGrade3, tvGrade4, tvGrade5;

    private ModelMentorStudentsList mStudent;
    private ModelMentorClass mClass;
    private ArrayList<ModelMentorStudentLessons> lessons;
    private ModelMentorStudentLessons SelectLesson;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onGradeClick(int GradeType, ModelMentorStudentLessons selectLesson);
        void onEnterMessageClick(ModelMentorStudentLessons selectLesson);
        void onCancelLastGradeClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public BSDMentorSetGrade(@NonNull Context context, ModelMentorStudentsList mStudent, ModelMentorClass mClass, ModelLesson selectLesson) {
        super(context);
        this.context = context;
        this.mStudent = mStudent;
        this.mClass = mClass;
        if(!listIsNullOrEmpty(mStudent.getLessons())){
            lessons = new ArrayList<>();
            lessons.addAll(mStudent.getLessons());
        }
        if(selectLesson!=null){
            SelectLesson = new ModelMentorStudentLessons();
            SelectLesson.setLessonId(selectLesson.getLessonId());
        }

        View view = getLayoutInflater().inflate(R.layout.bsd_mentor_send_grade_v2, null, false);
        setContentView(view);
        initViews(view);
        show();
    }
    private void initViews(View view) {
        civAvatar = view.findViewById(R.id.civAvatar);
        tvName = view.findViewById(R.id.tvName);
        llEnterMessage = view.findViewById(R.id.llEnterMessage);
        llEnterMessage.setOnClickListener(this);
        llCancelLastGrade = view.findViewById(R.id.llCancelLastGrade);
        llCancelLastGrade.setOnClickListener(this);
        llLate = view.findViewById(R.id.llLate);
        llLate.setOnClickListener(this);
        llWasAbsentCause = view.findViewById(R.id.llWasAbsentCause);
        llWasAbsentCause.setOnClickListener(this);
        llWasAbsentNonCause = view.findViewById(R.id.llWasAbsentNonCause);
        llWasAbsentNonCause.setOnClickListener(this);
        llSick = view.findViewById(R.id.llSick);
        llSick.setOnClickListener(this);
        rvLessons = view.findViewById(R.id.rvLessons);
        tvGrade2 = view.findViewById(R.id.tvGrade2);
        tvGrade2.setOnClickListener(this);
        tvGrade3 = view.findViewById(R.id.tvGrade3);
        tvGrade3.setOnClickListener(this);
        tvGrade4 = view.findViewById(R.id.tvGrade4);
        tvGrade4.setOnClickListener(this);
        tvGrade5 = view.findViewById(R.id.tvGrade5);
        tvGrade5.setOnClickListener(this);

        if(!stringIsNullOrEmpty(mStudent.getAvatar()))
            Picasso.get().load(mStudent.getAvatar()).centerCrop().fit().into(civAvatar);
        tvName.setText(mStudent.getFirstName() + " " + mStudent.getLastName());

        MentorSetGradeLessonsAdapter adapter = new MentorSetGradeLessonsAdapter();
        rvLessons.setAdapter(adapter);
        rvLessons.setLayoutManager(new GridLayoutManager(context, 1,LinearLayoutManager.VERTICAL, false));
        adapter.updateData(lessons);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onSelectLesson(ModelMentorStudentLessons m){
        SelectLesson = m;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llEnterMessage:
                onEnterMessageClick();
                break;
            case R.id.llCancelLastGrade:
                onCancelLastGradeClick();
                break;

            case R.id.llLate:
                onClick(6);
                break;
            case R.id.llWasAbsentCause:
                onClick(7);
            case R.id.llWasAbsentNonCause:
                onClick(8);
                break;
            case R.id.llSick:
                onClick(9);
                break;

            case R.id.tvGrade2:
                onClick(2);
                break;
            case R.id.tvGrade3:
                onClick(3);
                break;
            case R.id.tvGrade4:
                onClick(4);
            case R.id.tvGrade5:
                onClick(5);
                break;
        }
    }
    private void onEnterMessageClick(){
        if(onItemClickListener!=null) onItemClickListener.onEnterMessageClick(SelectLesson);
    }
    private void onCancelLastGradeClick(){
        if(onItemClickListener!=null) onItemClickListener.onCancelLastGradeClick();
    }
    private void onClick(int GradeType){
        if(onItemClickListener!=null) onItemClickListener.onGradeClick(GradeType, SelectLesson);
        dismiss();
    }
}