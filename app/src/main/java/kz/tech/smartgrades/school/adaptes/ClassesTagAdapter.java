package kz.tech.smartgrades.school.adaptes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.models.ModelLesson;
import kz.tech.smartgrades.school.models.ModelTeacherProfileClasses;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ClassesTagAdapter extends RecyclerView.Adapter<ClassesTagAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelTeacherProfileClasses> list;
    private boolean editable = false;

    public interface OnItemCLickListener{
        void onDeleteClassClick(ModelTeacherProfileClasses m);
        void onAddLessonClassClick(ModelTeacherProfileClasses m);
    }
    private OnItemCLickListener onItemCLickListener;
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public ClassesTagAdapter(Context context){
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setEditable() {
        this.editable = true;
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
    public void UpdateData(ArrayList<ModelTeacherProfileClasses> ClassList){
        if(ClassList == null) return;
        onClear(this.list);
        this.list.addAll(ClassList);
        notifyDataSetChanged();
    }

    public ArrayList<ModelTeacherProfileClasses> getList() {
        return this.list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classes, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelTeacherProfileClasses m = list.get(position);

        if (!stringIsNullOrEmpty(m.getName())) {
            holder.tvClass.setText(m.getName());
        }

        LessonsTagAdapter adapter = new LessonsTagAdapter();
        holder.rvLessons.setAdapter(adapter);
        holder.rvLessons.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        adapter.setOnItemCLickListener(new LessonsTagAdapter.OnItemCLickListener() {
            @Override
            public void onSelectLessonForClassClick(ModelLesson m) {
//                list.get(position).getLessons().add(m);
//                notifyDataSetChanged();
                // игнор
            }
            @Override
            public void onSelectLessonForDeleteClick(ModelLesson m) {
                list.get(position).getLessons().remove(m);
                notifyDataSetChanged();
            }
        });
        adapter.UpdateData(m.getLessons());

        if (editable) {
            holder.ivDelete.setVisibility(View.VISIBLE);
            holder.ivAddLesson.setVisibility(View.VISIBLE);
            holder.tvAddLesson.setVisibility(View.VISIBLE);
            adapter.setDelete();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvClass;
        RecyclerView rvLessons;
        ImageView ivAddLesson;
        ImageView ivDelete;
        TextView tvAddLesson;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvClass = itemView.findViewById(R.id.tvClass);
            rvLessons = itemView.findViewById(R.id.rvLessons);
            ivAddLesson = itemView.findViewById(R.id.ivAddLesson);
            tvAddLesson = itemView.findViewById(R.id.tvAddLesson);
            ivDelete = itemView.findViewById(R.id.ivDelete);

            ivDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener == null) return;
                    onItemCLickListener.onDeleteClassClick(list.get(getAdapterPosition()));
                }
            });

            ivAddLesson.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener == null) return;
                    onItemCLickListener.onAddLessonClassClick(list.get(getAdapterPosition()));
                }
            });
            tvAddLesson.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener == null) return;
                    onItemCLickListener.onAddLessonClassClick(list.get(getAdapterPosition()));
                }
            });
        }
    }
}