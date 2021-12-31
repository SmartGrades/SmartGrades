package kz.tech.smartgrades.mentor.dialog;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorEditProfileLessonsListAdapter;
import kz.tech.smartgrades.root.dialogs.ADAddLesson;
import kz.tech.smartgrades.root.models.ModelLesson;

public class MentorDialogEditProfileLessons extends Dialog implements View.OnClickListener,
        MentorEditProfileLessonsListAdapter.OnItemClickListener{

    private MentorActivity activity;
    private String MENTOR_ID;
    private Dialog DIALOG;

    private ImageView ivBack, ivSave;
    private RecyclerView rvLessons;

    private MentorEditProfileLessonsListAdapter adapter;
    private ArrayList<ModelLesson> lessonsList;

    private LinearLayout llAddLesson;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onSaveClick(ArrayList<ModelLesson> lessonsList);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public MentorDialogEditProfileLessons(MentorActivity activity, ArrayList<ModelLesson> lessonsList){
        super(activity, R.style.DefaultCustomDialog);
        this.activity = activity;
        this.lessonsList = lessonsList;

        DIALOG = this;
        DIALOG.setCanceledOnTouchOutside(true);
        View view = getLayoutInflater().inflate(R.layout.fragment_mentor_edit_profile_lessons, null, false);
        DIALOG.setContentView(view);
        DIALOG.show();

        initViews(view);
        onLoadData();
    }
    private void initViews(View view){
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivSave = view.findViewById(R.id.ivSave);
        ivSave.setOnClickListener(this);
        llAddLesson = view.findViewById(R.id.llAddLesson);
        llAddLesson.setOnClickListener(this);

        adapter = new MentorEditProfileLessonsListAdapter();
        adapter.setOnItemClickListener(this);
        rvLessons = view.findViewById(R.id.rvLessons);
        rvLessons.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvLessons.setAdapter(adapter);
    }
    private void onLoadData(){
        adapter.updateData(lessonsList);
    }

    @Override
    public void onRemoveLesson(ModelLesson m){
        lessonsList.remove(m);
        adapter.updateData(lessonsList);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBackClick();
                break;
            case R.id.ivSave:
                onSaveClick();
                break;
            case R.id.llAddLesson:
                onAddLessonClick();
                break;
        }
    }
    private void onBackClick(){
        DIALOG.dismiss();
    }
    private void onSaveClick(){
        if(onItemClickListener!=null) onItemClickListener.onSaveClick(lessonsList);
    }
    private void onAddLessonClick(){
        ADAddLesson adAddLesson = new ADAddLesson(activity);
        adAddLesson.setOnItemClickListener(new ADAddLesson.OnItemClickListener(){
            @Override
            public void onOkClick(ModelLesson mLesson){
                lessonsList.add(mLesson);
                adapter.updateData(lessonsList);
            }
        });
    }
}