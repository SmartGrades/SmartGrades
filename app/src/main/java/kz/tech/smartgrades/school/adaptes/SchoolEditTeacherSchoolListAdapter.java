package kz.tech.smartgrades.school.adaptes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.model.ModelChildProfileSchools;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelTeacherProfile;
import kz.tech.smartgrades.school.models.ModelTeacherProfileSchools;

public class SchoolEditTeacherSchoolListAdapter extends RecyclerView.Adapter<SchoolEditTeacherSchoolListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelTeacherProfileSchools> schoolList;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onSchoolClick(ModelTeacherProfileSchools m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public SchoolEditTeacherSchoolListAdapter(Context context) {
        this.context = context;
        this.schoolList = new ArrayList<>();
    }

    public void updateData(ArrayList<ModelTeacherProfileSchools> schoolList) {
        onClear(this.schoolList);
        this.schoolList = schoolList;
        notifyDataSetChanged();
    }

    public void clear() {
        onClear(this.schoolList);
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_parent_school, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolEditTeacherSchoolListAdapter.ViewHolder holder, int position) {
        ModelTeacherProfileSchools model = schoolList.get(position);
        holder.tvSchool.setText(model.getSchoolName());
    }

    @Override
    public int getItemCount() {
        if (schoolList != null) return schoolList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSchool;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSchool = itemView.findViewById(R.id.tvSchool);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onSchoolClick(schoolList.get(getAdapterPosition()));
                }
            });
        }
    }
}
