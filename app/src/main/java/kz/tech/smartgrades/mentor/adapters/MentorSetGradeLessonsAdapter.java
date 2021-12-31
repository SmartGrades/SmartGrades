package kz.tech.smartgrades.mentor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.models.ModelMentorStudentLessons;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades._Web.SoapRequest;

public class MentorSetGradeLessonsAdapter extends RecyclerView.Adapter<MentorSetGradeLessonsAdapter.ViewHolder>{

    private ArrayList<ModelMentorStudentLessons> lessons;
    private int SelectLessonPos;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onSelectLesson(ModelMentorStudentLessons m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public MentorSetGradeLessonsAdapter(){
        lessons = new ArrayList<>();
        SelectLessonPos = 0;
    }

    public void updateData(ArrayList<ModelMentorStudentLessons> lessons){
        onClear();
        if(!listIsNullOrEmpty(lessons)) this.lessons.addAll(lessons);
        notifyDataSetChanged();
    }
    private void onClear(){
        if(!listIsNullOrEmpty(lessons)) lessons.clear();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelMentorStudentLessons m = lessons.get(position);
        holder.tvLessonName.setText(m.getLessonName());

        if(SelectLessonPos==position){
            holder.clLessonName.setBackgroundResource(R.drawable.background_mentor_set_grade_lesson_tag_active);
            if(onItemClickListener != null) onItemClickListener.onSelectLesson(lessons.get(position));
        }
        else holder.clLessonName.setBackgroundResource(R.drawable.background_mentor_set_grade_lesson_tag_not_active);
    }

    @Override
    public int getItemCount(){
        return lessons.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mentor_set_grade_lessons_tag, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvLessonName;
        ConstraintLayout clLessonName;

        ViewHolder(@NonNull View view){
            super(view);
            tvLessonName = view.findViewById(R.id.tvLessonName);
            clLessonName = view.findViewById(R.id.clLessonName);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener == null) return;
                    SelectLessonPos = getAdapterPosition();
                    onItemClickListener.onSelectLesson(lessons.get(getAdapterPosition()));
                }
            });
        }
    }
}
