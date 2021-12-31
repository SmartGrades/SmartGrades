package kz.tech.smartgrades.parent.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.models.ModelLesson;
import kz.tech.smartgrades.school.models.ModelSchoolClass;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ParentSchoolClassListAdapter extends RecyclerView.Adapter<ParentSchoolClassListAdapter.ViewHolder>{

    private ArrayList<ModelSchoolClass> classList;
    private ArrayList<ModelSchoolClass> filterList;

    private OnItemCLickListener onItemCLickListener;
    public void filter(String toString){
        filterList.clear();
        if(stringIsNullOrEmpty(toString)) filterList.addAll(classList);
        else for(ModelSchoolClass _class : classList)
            if(_class.getName().toLowerCase().contains(toString.toLowerCase()))
                filterList.add(_class);
        notifyDataSetChanged();
    }
    public interface OnItemCLickListener{
        void onItemClick(ModelSchoolClass m);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public ParentSchoolClassListAdapter(){
        this.classList = new ArrayList<>();
        this.filterList = new ArrayList<>();
    }

    @Override
    public int getItemCount(){
        return filterList.size();
    }

    private void onClear(ArrayList<?> DataList){
        if(DataList.size() > 0) DataList.clear();
    }

    public void updateData(ArrayList<ModelSchoolClass> lessonsList){
        if(lessonsList == null) return;
        onClear(classList);
        onClear(filterList);
        this.classList.addAll(lessonsList);
        this.filterList.addAll(lessonsList);
        notifyDataSetChanged();
    }
    public void ClearData(){
        onClear(classList);
        onClear(filterList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_class_list, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolClass m = filterList.get(position);
        holder.tvName.setText(m.getName());
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civAvatar;
        TextView tvName;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            civAvatar = itemView.findViewById(R.id.civAvatar);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener == null) return;
                    onItemCLickListener.onItemClick(filterList.get(getAdapterPosition()));
                }
            });
        }
    }
}