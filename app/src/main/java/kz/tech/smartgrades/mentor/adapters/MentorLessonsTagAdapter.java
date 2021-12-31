package kz.tech.smartgrades.mentor.adapters;

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


public class MentorLessonsTagAdapter extends RecyclerView.Adapter<MentorLessonsTagAdapter.ViewHolder> {

    private ArrayList<ModelLesson> Lessons;

    public MentorLessonsTagAdapter() {
        this.Lessons = new ArrayList<>();
    }

    private OnItemCLickListener onItemCLickListener;
    public interface OnItemCLickListener{
        void onItemClick(ModelLesson m);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lessons_tag, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelLesson m = Lessons.get(position);
        holder.tvLessonName.setText(m.getLessonName());
    }

    @Override
    public int getItemCount() {
        return Lessons.size();
    }

    private void onClear() {
        if (Lessons.size() > 0) Lessons.clear();
    }

    public void updateData(ArrayList<ModelLesson> Lessons) {
        onClear();
        if (listIsNullOrEmpty(Lessons)) return;
        this.Lessons.addAll(Lessons);
    }

    public void selectList() {
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLessonName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener != null) onItemCLickListener.onItemClick(Lessons.get(getAdapterPosition()));
                }
            });
        }
    }
}
