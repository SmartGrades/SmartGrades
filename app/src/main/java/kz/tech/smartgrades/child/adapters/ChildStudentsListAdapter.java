package kz.tech.smartgrades.child.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.child.models.ModelComplaintDataStudent;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ChildStudentsListAdapter extends RecyclerView.Adapter<ChildStudentsListAdapter.ViewHolder>{

    private ArrayList<ModelComplaintDataStudent> list;
    private ArrayList<ModelComplaintDataStudent> filterList;

    private OnItemCLickListener onItemCLickListener;
    public void filter(String toString){
        filterList.clear();
        if(stringIsNullOrEmpty(toString)) filterList.addAll(list);
        else for(ModelComplaintDataStudent _list : list)
            if(_list.getFirstName().toLowerCase().contains(toString.toLowerCase()))
                filterList.add(_list);
        notifyDataSetChanged();
    }
    public interface OnItemCLickListener{
        void onItemClick(ModelComplaintDataStudent m);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public ChildStudentsListAdapter(){
        this.list = new ArrayList<>();
        this.filterList = new ArrayList<>();
    }

    @Override
    public int getItemCount(){
        return filterList.size();
    }

    private void onClear(ArrayList<?> DataList){
        if(DataList.size() > 0) DataList.clear();
    }

    public void updateData(ArrayList<ModelComplaintDataStudent> list){
        if(list == null) return;
        onClear(this.list);
        onClear(filterList);
        this.list.addAll(list);
        this.filterList.addAll(list);
        notifyDataSetChanged();
    }
    public void ClearData(){
        onClear(list);
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
        ModelComplaintDataStudent m = filterList.get(position);
        holder.tvName.setText(m.getFirstName() + " " + m.getLastName());
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;

        ViewHolder(@NonNull View itemView){
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);

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