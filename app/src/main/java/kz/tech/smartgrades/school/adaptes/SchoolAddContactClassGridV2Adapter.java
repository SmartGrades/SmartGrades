package kz.tech.smartgrades.school.adaptes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;

public class SchoolAddContactClassGridV2Adapter extends RecyclerView.Adapter<SchoolAddContactClassGridV2Adapter.ViewHolder>{

    private ArrayList<ModelSchoolLesson> LessonsList;

    public interface OnItemCLickListener{
        void onClassClick(ModelSchoolLesson m);
    }
    private OnItemCLickListener onItemCLickListener;
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public SchoolAddContactClassGridV2Adapter(){
        this.LessonsList = new ArrayList<>();
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
    public void UpdateData(ArrayList<ModelSchoolLesson> ClassList){
        if(ClassList == null) return;
        onClear(this.LessonsList);
        this.LessonsList.addAll(ClassList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lessons_grid, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolLesson m = LessonsList.get(position);
        holder.tvLessonName.setText(m.getLessonName());
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvLessonName;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
        }
    }
}