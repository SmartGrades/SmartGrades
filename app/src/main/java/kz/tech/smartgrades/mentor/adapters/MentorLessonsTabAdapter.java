package kz.tech.smartgrades.mentor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.models.ModelLesson;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;


public class MentorLessonsTabAdapter extends RecyclerView.Adapter<MentorLessonsTabAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelLesson> LessonsList;
    private int SelectLessonPos;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int p);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MentorLessonsTabAdapter(Context context, int lastPos) {
        this.context = context;
        this.SelectLessonPos = lastPos;
        this.LessonsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mentor_lessons_tag, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelLesson m = LessonsList.get(position);
        holder.tvLessonName.setText(m.getLessonName());
        if(SelectLessonPos==position){
            holder.itemView.setBackgroundResource(R.drawable.background_mentor_set_grade_lesson_tag_active);
        }
        else holder.itemView.setBackgroundResource(R.drawable.background_mentor_set_grade_lesson_tag_not_active);
    }

    @Override
    public int getItemCount() {
        return LessonsList.size();
    }

    private void onClear() {
        if (LessonsList.size() > 0) LessonsList.clear();
    }

    public void updateData(ArrayList<ModelLesson> lessons) {
        if (listIsNullOrEmpty(lessons)) return;
        onClear();
        LessonsList.addAll(lessons);
        notifyDataSetChanged();
    }

    public void selectList() {
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLessonName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        SelectLessonPos = getAdapterPosition();
                        onItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
