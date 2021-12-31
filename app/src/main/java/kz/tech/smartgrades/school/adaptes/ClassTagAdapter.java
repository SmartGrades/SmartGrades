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
import kz.tech.smartgrades.school.models.ModelSchoolClass;

public class ClassTagAdapter extends RecyclerView.Adapter<ClassTagAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ModelSchoolClass> list;
    private boolean delete = false;

    public interface OnItemCLickListener{
        void onSelectLessonForClassClick(ModelLesson m);
        void onSelectLessonForDeleteClick(ModelLesson m);
    }
    private OnItemCLickListener onItemCLickListener;
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public ClassTagAdapter(){
        this.list = new ArrayList<>();
    }

    public void setDelete() {
        this.delete = true;
    }

    @Override
    public int getItemCount(){
        return list.size();
    }
    private void onClear(ArrayList<?> DataList){
        if(DataList.size() > 0) DataList.clear();
    }
    public void ClearData(){
        onClear(list);
        notifyDataSetChanged();
    }
    public void UpdateData(ArrayList<ModelSchoolClass> ClassList){
        if(ClassList == null) return;
        onClear(this.list);
        this.list.addAll(ClassList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lessons_tag, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolClass m = list.get(position);
        holder.tvLessonName.setText(m.getName());
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
        }
    }
}