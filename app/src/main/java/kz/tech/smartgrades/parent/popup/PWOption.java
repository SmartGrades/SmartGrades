package kz.tech.smartgrades.parent.popup;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentLessonOptionsAdapter;
import kz.tech.smartgrades.parent.model.ModelMentorList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class PWOption extends PopupWindow implements View.OnClickListener, ParentLessonOptionsAdapter.OnItemClickListener{

    public ParentActivity activity;

    private ImageView ivRemove;
    private ImageView ivAdd;
    private RecyclerView rvMentorList;
    private ArrayList<ModelMentorList> mentors;
    private ParentLessonOptionsAdapter parentLessonOptionsAdapter;
    private TextView tvLessonName;

    private String lessonName;
    private String lessonId;
    private String id;

    public PWOption(int width, int height, ParentActivity activity, ArrayList<ModelMentorList> mentors, String lessonName, String lessonId, String id) {
        super(width, height);
        this.activity = activity;
        this.mentors = mentors;
        this.lessonName = lessonName;
        this.lessonId = lessonId;
        this.id = id;
        LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pw_remove_mentor, null);
        setContentView(view);
        initViews(view);
        setMentorList();
    }

    private void setMentorList() {
        rvMentorList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        parentLessonOptionsAdapter = new ParentLessonOptionsAdapter(activity);
        parentLessonOptionsAdapter.setOnItemClickListener(this);
        rvMentorList.setAdapter(parentLessonOptionsAdapter);
        rvMentorList.suppressLayout(true);
        parentLessonOptionsAdapter.updateData(mentors);
    }

    private void initViews(View view) {
        ivRemove = view.findViewById(R.id.ivRemove);
        ivRemove.setOnClickListener(this);
        ivAdd = view.findViewById(R.id.ivAdd);
        ivAdd.setOnClickListener(this);
        rvMentorList = view.findViewById(R.id.rvMentorList);
        tvLessonName = view.findViewById(R.id.tvLessonName);
        tvLessonName.setText(lessonName);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivRemove:
                onRemoveLessonProve();
                dismiss();
                break;
            case R.id.ivAdd:
                activity.onAddMentor(lessonId, id);
                dismiss();
                break;
        }
    }

    private void onRemoveLessonProve() {
        activity.onRemoveLessonProve();
    }

    @Override
    public void onClick(ModelMentorList mMentor) {
        activity.openProveWindow(mMentor);
        dismiss();
    }
}
