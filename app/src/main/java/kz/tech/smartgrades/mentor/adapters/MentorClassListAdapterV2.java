package kz.tech.smartgrades.mentor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolClass;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class MentorClassListAdapterV2 extends RecyclerView.Adapter<MentorClassListAdapterV2.ViewHolder> {

    private MentorActivity activity;
    private String MENTOR_ID;
    private ArrayList<ModelSchoolClass> classList;
    private ArrayList<ModelSchoolClass> filterList;

    private OnItemCLickListener onItemCLickListener;
    public void filter(String toString){
        filterList.clear();
        if(stringIsNullOrEmpty(toString)) filterList.addAll(classList);
        else {
            for(ModelSchoolClass _class : classList)
                if(_class.getName().contains(toString))
                    filterList.add(_class);
        }
        notifyDataSetChanged();
    }
    public void ClearData(){
        onClear(classList);
        onClear(filterList);
        notifyDataSetChanged();
    }
    public interface OnItemCLickListener {
        void onItemClick(ModelSchoolClass m);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    public MentorClassListAdapterV2(MentorActivity activity) {
        this.activity = activity;
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);
        this.classList = new ArrayList<>();
        this.filterList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    public void updateData(ArrayList<ModelSchoolClass> schoolList) {
        if (schoolList==null) return;
        this.classList.addAll(schoolList);
        this.filterList.addAll(schoolList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mentor_class_list, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolClass m = filterList.get(position);
        holder.tvName.setText(m.getName());

        MentorLessonsHorizontalAdapter adapter = new MentorLessonsHorizontalAdapter(activity);
        holder.rvLessonsList.setLayoutManager(new LinearLayoutManager(activity));
        holder.rvLessonsList.setAdapter(adapter);
        adapter.updateData(m.getLessons());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvLessonsList;
        TextView tvName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            rvLessonsList = itemView.findViewById(R.id.rvLessonsList);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener==null) return;
                    onItemCLickListener.onItemClick(filterList.get(getAdapterPosition()));
                }
            });
        }
    }
}