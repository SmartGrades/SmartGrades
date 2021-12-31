package kz.tech.smartgrades.school.adaptes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.school.models.ModelSchoolClass;

public class SchoolAddStudentClassGridAdapter extends RecyclerView.Adapter<SchoolAddStudentClassGridAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ModelSchoolClass> ClassList;

    public SchoolAddStudentClassGridAdapter(Context context){
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school_add_student_class_grid, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolClass m = ClassList.get(position);
        holder.tvName.setText(m.getName());
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}