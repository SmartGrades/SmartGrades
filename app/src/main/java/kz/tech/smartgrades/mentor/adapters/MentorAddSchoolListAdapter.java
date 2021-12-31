package kz.tech.smartgrades.mentor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolData;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class MentorAddSchoolListAdapter extends RecyclerView.Adapter<MentorAddSchoolListAdapter.ViewHolder>{

    private MentorActivity activity;
    private String MENTOR_ID;
    private ArrayList<ModelSchoolData> schoolsList;
    private ArrayList<ModelSchoolData> filterList;

    private OnItemCLickListener onItemCLickListener;
    public interface OnItemCLickListener{
        void onItemClick(ModelSchoolData m);
    }
    public void setOnItemClickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public void onFilter(String filterString){
        filterString = filterString.toLowerCase();
        if(!listIsNullOrEmpty(schoolsList)){
            filterList.clear();
            for(ModelSchoolData _school : schoolsList)
                if(_school.getName().toLowerCase().contains(filterString))
                    filterList.add(_school);
            notifyDataSetChanged();
        }
    }
    public void filter(String toString){
        filterList.clear();
        if(stringIsNullOrEmpty(toString)) filterList.addAll(schoolsList);
        else{
            for(ModelSchoolData _class : schoolsList)
                if(_class.getName().contains(toString))
                    filterList.add(_class);
        }
        notifyDataSetChanged();
    }

    public void ClearData(){
        onClear(schoolsList);
        onClear(filterList);
        notifyDataSetChanged();
    }

    public MentorAddSchoolListAdapter(MentorActivity activity){
        this.activity = activity;
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);
        this.schoolsList = new ArrayList<>();
        this.filterList = new ArrayList<>();
    }

    @Override
    public int getItemCount(){
        return filterList.size();
    }

    private void onClear(ArrayList<?> DataList){
        if(DataList.size() > 0) DataList.clear();
    }

    public void updateData(ArrayList<ModelSchoolData> schoolList){
        if(schoolList == null) return;
        this.schoolsList.addAll(schoolList);
        this.filterList.addAll(schoolList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mentor_school_list, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolData m = filterList.get(position);
        holder.tvName.setText(m.getName());
        holder.tvAddress.setText(m.getAddress());
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivAvatar;
        TextView tvName, tvAddress;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);

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