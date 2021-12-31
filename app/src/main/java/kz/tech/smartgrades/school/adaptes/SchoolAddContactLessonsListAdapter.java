package kz.tech.smartgrades.school.adaptes;

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

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class SchoolAddContactLessonsListAdapter extends RecyclerView.Adapter<SchoolAddContactLessonsListAdapter.ViewHolder>{

    private ArrayList<ModelLesson> LessonList;
    private ArrayList<ModelLesson> FilterList;
    private ArrayList<ModelLesson> ChosenList;

    public interface OnItemCLickListener{
        void onLessonClick(ModelLesson m);
    }
    private OnItemCLickListener onItemCLickListener;
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public SchoolAddContactLessonsListAdapter(){
        this.LessonList = new ArrayList<>();
        this.FilterList = new ArrayList<>();
        this.ChosenList = new ArrayList<>();
    }

    @Override
    public int getItemCount(){
        return FilterList.size();
    }
    private void onClear(ArrayList<?> DataList){
        if(DataList.size() > 0) DataList.clear();
    }
    public void ClearData(){
        onClear(LessonList);
        onClear(FilterList);
        notifyDataSetChanged();
    }
    public void setChosenData(ArrayList<ModelLesson> lessonsList) {
        if(lessonsList == null) return;
        onClear(ChosenList);
        this.ChosenList.addAll(lessonsList);
    }
    public void UpdateData(ArrayList<ModelLesson> lessonsList){
        if(lessonsList == null) return;
        onClear(LessonList);
        onClear(FilterList);
        if(!listIsNullOrEmpty(ChosenList)){
            this.LessonList.addAll(ChosenList);
            this.FilterList.addAll(ChosenList);
            for(ModelLesson _lesson : lessonsList)
                if(!ChosenList.contains(_lesson)){
                    this.LessonList.add(_lesson);
                    this.FilterList.add(_lesson);
                }
        }
        else {
            this.LessonList.addAll(lessonsList);
            this.FilterList.addAll(lessonsList);
        }
        notifyDataSetChanged();
    }
    public void filter(String toString){
        FilterList.clear();
        if(stringIsNullOrEmpty(toString)) FilterList.addAll(LessonList);
        else for(ModelLesson _class : LessonList)
            if(_class.getLessonName().toLowerCase().contains(toString.toLowerCase()))
                FilterList.add(_class);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lessons_list, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelLesson m = FilterList.get(position);
        holder.tvName.setText(m.getLessonName());
        if(ChosenList.contains(m)) holder.ivChose.setVisibility(View.VISIBLE);
        else holder.ivChose.setVisibility(View.GONE);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivChose;
        TextView tvName;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivChose = itemView.findViewById(R.id.ivChose);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener == null) return;
                    try {
                        onItemCLickListener.onLessonClick(FilterList.get(getAdapterPosition()));
                    } catch (Exception ignored){}
                }
            });
        }
    }
}