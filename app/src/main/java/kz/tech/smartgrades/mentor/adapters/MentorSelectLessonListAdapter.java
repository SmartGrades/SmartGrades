package kz.tech.smartgrades.mentor.adapters;

import android.annotation.SuppressLint;
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


public class MentorSelectLessonListAdapter extends RecyclerView.Adapter<MentorSelectLessonListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelLesson> Lessons;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onLessonClick(ModelLesson m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public MentorSelectLessonListAdapter(Context context) {
        this.context = context;
        this.Lessons = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_lesson, parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelLesson m = Lessons.get(position);
        holder.tvLesson.setText(m.getLessonName());
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
        TextView tvLesson;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLesson = itemView.findViewById(R.id.tvLesson);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemClickListener != null)
                        onItemClickListener.onLessonClick(Lessons.get(getAdapterPosition()));
                }
            });
        }
    }
}
