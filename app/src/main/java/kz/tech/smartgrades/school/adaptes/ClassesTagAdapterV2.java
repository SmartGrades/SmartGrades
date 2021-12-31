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
import kz.tech.smartgrades.school.models.ModelStudentProfileClasses;
import kz.tech.smartgrades.school.models.ModelTeacherProfileClasses;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ClassesTagAdapterV2 extends RecyclerView.Adapter<ClassesTagAdapterV2.ViewHolder> {

    private Context context;
    private ArrayList<ModelStudentProfileClasses> list;
    private boolean editable = false;

    public interface OnItemCLickListener{
        void onDeleteClassClick(ModelStudentProfileClasses m);
    }
    private OnItemCLickListener onItemCLickListener;
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public ClassesTagAdapterV2(Context context){
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
    public void UpdateData(ArrayList<ModelStudentProfileClasses> ClassList){
        if(ClassList == null) return;
        onClear(this.list);
        this.list.addAll(ClassList);
        notifyDataSetChanged();
    }

    public ArrayList<ModelStudentProfileClasses> getList() {
        return this.list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelStudentProfileClasses m = list.get(position);

        if (!stringIsNullOrEmpty(m.getName())) {
            holder.tvClass.setText(m.getName());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvClass;
        ImageView ivDelete;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvClass = itemView.findViewById(R.id.tvClass);
            ivDelete = itemView.findViewById(R.id.ivDelete);

            ivDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener == null) return;
                    onItemCLickListener.onDeleteClassClick(list.get(getAdapterPosition()));
                }
            });
        }
    }
}