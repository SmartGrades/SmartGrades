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
import kz.tech.smartgrades.school.models.ModelSchoolClass;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class SchoolAddTeacherClassListAdapter extends RecyclerView.Adapter<SchoolAddTeacherClassListAdapter.ViewHolder>{

    private ArrayList<ModelSchoolClass> ClassList;

    public interface OnItemCLickListener{
        void onClassClick(ModelSchoolClass m);
    }
    private OnItemCLickListener onItemCLickListener;
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public SchoolAddTeacherClassListAdapter(){
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
        if (!listIsNullOrEmpty(ClassList)) this.ClassList.addAll(ClassList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class_list, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolClass m = ClassList.get(position);
        holder.tvName.setText(m.getName());
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
                    onItemCLickListener.onClassClick(ClassList.get(getAdapterPosition()));
                }
            });
        }
    }
}