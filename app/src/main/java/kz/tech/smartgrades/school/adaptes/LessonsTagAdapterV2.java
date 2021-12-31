package kz.tech.smartgrades.school.adaptes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.models.ModelLesson;
import kz.tech.smartgrades.school.models.ModelTeacherProfileLessons;

public class LessonsTagAdapterV2 extends RecyclerView.Adapter<LessonsTagAdapterV2.ViewHolder>{

    private Context context;
    private ArrayList<ModelTeacherProfileLessons> LessonsList;
    private boolean delete = false;

    public interface OnItemCLickListener{
        void onSelectLessonForClassClick(ModelTeacherProfileLessons m);
        void onSelectLessonForDeleteClick(ModelTeacherProfileLessons m);
    }
    private OnItemCLickListener onItemCLickListener;
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public LessonsTagAdapterV2(){
        this.context = context;
        this.LessonsList = new ArrayList<>();
    }

    public void setDelete() {
        this.delete = true;
    }

    @Override
    public int getItemCount(){
        return LessonsList.size();
    }
    private void onClear(ArrayList<?> DataList){
        if(DataList.size() > 0) DataList.clear();
    }
    public void ClearData(){
        onClear(LessonsList);
        notifyDataSetChanged();
    }
    public void UpdateData(ArrayList<ModelTeacherProfileLessons> ClassList){
        if(ClassList == null) return;
        onClear(this.LessonsList);
        this.LessonsList.addAll(ClassList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lessons_tag, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelTeacherProfileLessons m = LessonsList.get(position);
        holder.tvLessonName.setText(m.getLessonName());
        if (delete) {
            holder.ivDelete.setVisibility(View.VISIBLE);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvLessonName;
        ImageView ivDelete;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
            ivDelete = itemView.findViewById(R.id.ivDelete);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener == null) return;
                    onItemCLickListener.onSelectLessonForClassClick(LessonsList.get(getAdapterPosition()));
                }
            });

            ivDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener == null) return;
                    onItemCLickListener.onSelectLessonForDeleteClick(LessonsList.get(getAdapterPosition()));
                }
            });
        }
    }
}