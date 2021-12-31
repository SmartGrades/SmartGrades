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


public class MentorLessonsHorizontalAdapter extends RecyclerView.Adapter<MentorLessonsHorizontalAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ModelLesson> lessonsList;

    public MentorLessonsHorizontalAdapter(Context context){
        this.context = context;
        this.lessonsList = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_lessons_grid, parent, false));
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelLesson m = lessonsList.get(position);
        holder.tvLessonName.setText(m.getLessonName());
    }

    @Override
    public int getItemCount(){
        return lessonsList.size();
    }

    private void onClear(){
        if(lessonsList.size() > 0) lessonsList.clear();
    }

    public void updateData(ArrayList<ModelLesson> list){
        if(listIsNullOrEmpty(list)) return;
        onClear();
        lessonsList.addAll(list);
    }

    public void selectList(){
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvLessonName;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
        }
    }
}
