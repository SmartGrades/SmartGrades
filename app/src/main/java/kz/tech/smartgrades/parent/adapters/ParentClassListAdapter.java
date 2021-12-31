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
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.models.ModelSchoolClass;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ParentClassListAdapter extends RecyclerView.Adapter<ParentClassListAdapter.ViewHolder> {

    private ParentActivity activity;
    private String PARENT_ID;
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
    public interface OnItemCLickListener {
        void onSchoolClick(ModelSchoolClass m);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    public ParentClassListAdapter(ParentActivity activity) {
        this.activity = activity;
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_class_list, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolClass m = filterList.get(position);
        holder.tvName.setText(m.getName());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            civAvatar = itemView.findViewById(R.id.civAvatar);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener==null) return;
                    onItemCLickListener.onSchoolClick(filterList.get(getAdapterPosition()));
                }
            });
        }
    }
}