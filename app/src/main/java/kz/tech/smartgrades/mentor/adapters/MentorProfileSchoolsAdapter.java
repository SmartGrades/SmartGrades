package kz.tech.smartgrades.mentor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.models.ModelSchoolList;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class MentorProfileSchoolsAdapter extends RecyclerView.Adapter<MentorProfileSchoolsAdapter.ViewHolder>{

    private ArrayList<ModelSchoolList> schoolsList;

    private OnItemCLickListener onItemCLickListener;
    public interface OnItemCLickListener{
        void onItemClick(ModelSchoolList m);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public MentorProfileSchoolsAdapter(){
        this.schoolsList = new ArrayList<>();
    }

    private void onClear(ArrayList<?> DataList){
        if(DataList.size() > 0) DataList.clear();
    }

    public void updateData(ArrayList<ModelSchoolList> schoolList){
        onClear(schoolsList);
        if(!listIsNullOrEmpty(schoolList)){
            this.schoolsList.addAll(schoolList);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolList m = schoolsList.get(position);
        holder.tvName.setText(m.getName());
        holder.tvAddress.setVisibility(View.GONE);
    }
    @Override
    public int getItemCount(){
        return schoolsList.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mentor_school_list, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvAddress;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener == null) return;
                    onItemCLickListener.onItemClick(schoolsList.get(getAdapterPosition()));
                }
            });
        }
    }
}