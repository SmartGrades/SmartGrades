package kz.tech.smartgrades.mentor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.models.ModelMentorSchool;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.adaptes.LessonsTagAdapter;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class MentorSchoolsAdapter extends RecyclerView.Adapter<MentorSchoolsAdapter.ViewHolder>{

    private MentorActivity activity;
    private String MENTOR_ID;
    private ArrayList<ModelMentorSchool> schoolsList;
    private ArrayList<ModelMentorSchool> filterList;

    private OnItemCLickListener onItemCLickListener;
    public interface OnItemCLickListener{
        void onItemClick(ModelMentorSchool m);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public void onFilter(String toString){
        filterList.clear();
        toString = toString.toLowerCase();
        if(stringIsNullOrEmpty(toString)) filterList.addAll(schoolsList);
        else{
            for(ModelMentorSchool _school : schoolsList)
                if(_school.getName().toLowerCase().contains(toString))
                    filterList.add(_school);
        }
        notifyDataSetChanged();
    }
    public void ClearData(){
        onClear(schoolsList);
        onClear(filterList);
        notifyDataSetChanged();
    }

    public MentorSchoolsAdapter(MentorActivity activity){
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

    public void updateData(ArrayList<ModelMentorSchool> schoolList){
        onClear(schoolsList);
        onClear(filterList);
        if(!listIsNullOrEmpty(schoolList)){
            this.schoolsList.addAll(schoolList);
            this.filterList.addAll(schoolList);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mentor_schools_list, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelMentorSchool m = filterList.get(position);
        holder.tvSchoolName.setText(m.getName());
        holder.tvClassesCount.setText(String.valueOf(m.getClassesCount()));
        holder.tvStudentsCount.setText(String.valueOf(m.getStudentsCount()));

        GridLayoutManager layoutManager = new GridLayoutManager(activity, LinearLayoutManager.VERTICAL);
        if(m.getLessons().size() > 5) layoutManager.setSpanCount(5);
        else layoutManager.setSpanCount(m.getLessons().size());

        LessonsTagAdapter adapter = new LessonsTagAdapter();
        holder.rvLessons.setLayoutManager(layoutManager);
        holder.rvLessons.setAdapter(adapter);
        adapter.UpdateData(m.getLessons());
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvSchoolName, tvClassesCount, tvStudentsCount;
        RecyclerView rvLessons;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvSchoolName = itemView.findViewById(R.id.tvSchoolName);
            tvClassesCount = itemView.findViewById(R.id.tvClassesCount);
            tvStudentsCount = itemView.findViewById(R.id.tvStudentsCount);
            rvLessons = itemView.findViewById(R.id.rvLessons);

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