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
import kz.tech.smartgrades.school.models.ModelSchoolClass;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class SchoolAddTeacherClassGridAdapter extends RecyclerView.Adapter<SchoolAddTeacherClassGridAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ModelSchoolClass> ClassList;

    public interface OnItemCLickListener{
        void onAddLessonToClass(int index);
    }
    private OnItemCLickListener onItemCLickListener;
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public SchoolAddTeacherClassGridAdapter(Context context){
        this.context = context;
        this.ClassList = new ArrayList<>();
    }

    @Override
    public int getItemCount(){
        return ClassList.size();
    }
    private void onClear(ArrayList<?> DataList){
        if(DataList.size() > 0) DataList.clear();
    }
    public void ClearData(){
        onClear(ClassList);
        notifyDataSetChanged();
    }
    public void UpdateData(ArrayList<ModelSchoolClass> ClassList){
        if(ClassList == null) return;
        onClear(this.ClassList);
        this.ClassList.addAll(ClassList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school_add_teacher_class_grid, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolClass m = ClassList.get(position);
        holder.tvName.setText(m.getName());
        SchoolAddContactClassGridV2Adapter adapter = new SchoolAddContactClassGridV2Adapter();
        holder.rvLessons.setAdapter(adapter);
        holder.rvLessons.setLayoutManager(new LinearLayoutManager(context));
        adapter.UpdateData(m.getLessons());
        if (!listIsNullOrEmpty(m.getLessons())) {
            holder.tvAddLesson.setVisibility(View.GONE);
            holder.ivEdit.setVisibility(View.VISIBLE);
        } else {
            holder.ivEdit.setVisibility(View.GONE);
            holder.tvAddLesson.setVisibility(View.VISIBLE);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView rvLessons;
        TextView tvName, tvAddLesson;
        ImageView ivEdit;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddLesson = itemView.findViewById(R.id.tvAddLesson);
            rvLessons = itemView.findViewById(R.id.rvLessons);
            ivEdit = itemView.findViewById(R.id.ivEdit);

            tvAddLesson.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener == null) return;
                    onItemCLickListener.onAddLessonToClass(getAdapterPosition());
                }
            });
            ivEdit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener == null) return;
                    onItemCLickListener.onAddLessonToClass(getAdapterPosition());
                }
            });
        }
    }
}