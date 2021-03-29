package kz.tech.smartgrades.parent.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.root.models.ModelLesson;

public class ParentMentorLessonListAdapter extends RecyclerView.Adapter<ParentMentorLessonListAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelLesson> lessonList;

    public ParentMentorLessonListAdapter(ParentActivity activity) {
        this.activity = activity;
        this.lessonList = new ArrayList<>();
    }

    public void updateData(ArrayList<ModelLesson> lessonList) {
        onClear(this.lessonList);
        this.lessonList = lessonList;
        notifyDataSetChanged();
    }

    public void clear() {
        onClear(this.lessonList);
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_lesson, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParentMentorLessonListAdapter.ViewHolder holder, int position) {
        ModelLesson model = lessonList.get(position);
        holder.tvLesson.setText(model.getLessonName());
    }



    @Override
    public int getItemCount() {
        if (lessonList != null) return lessonList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLesson;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLesson = itemView.findViewById(R.id.tvLesson);
        }
    }
}
