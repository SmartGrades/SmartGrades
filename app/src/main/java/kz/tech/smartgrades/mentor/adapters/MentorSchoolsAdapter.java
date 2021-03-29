package kz.tech.smartgrades.mentor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolData;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class MentorSchoolsAdapter extends RecyclerView.Adapter<MentorSchoolsAdapter.ViewHolder>{

    private MentorActivity activity;
    private String MENTOR_ID;
    private ArrayList<ModelSchoolData> schoolsList;
    private ArrayList<ModelSchoolData> filterList;

    private OnItemCLickListener onItemCLickListener;
    public interface OnItemCLickListener{
        void onItemClick(ModelSchoolData m);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
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

    public void updateData(ArrayList<ModelSchoolData> schoolList){
        if(schoolList == null) return;
        this.schoolsList.addAll(schoolList);
        this.filterList.addAll(schoolList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_class_list, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolData m = filterList.get(position);
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