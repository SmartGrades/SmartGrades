package kz.tech.smartgrades.root.adapters;

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

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class LessonsListAdapter extends RecyclerView.Adapter<LessonsListAdapter.ViewHolder>{

    private ArrayList<ModelLesson> classList;
    private ArrayList<ModelLesson> filterList;

    private OnItemCLickListener onItemCLickListener;
    public void filter(String toString){
        filterList.clear();
        if(stringIsNullOrEmpty(toString)) filterList.addAll(classList);
        else for(ModelLesson _class : classList)
            if(_class.getLessonName().toLowerCase().contains(toString.toLowerCase()))
                filterList.add(_class);
        notifyDataSetChanged();
    }
    public interface OnItemCLickListener{
        void onItemClick(ModelLesson m);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public LessonsListAdapter(){
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

    public void updateData(ArrayList<ModelLesson> lessonsList){
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
        ModelLesson m = filterList.get(position);
        holder.tvName.setText(m.getLessonName());
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