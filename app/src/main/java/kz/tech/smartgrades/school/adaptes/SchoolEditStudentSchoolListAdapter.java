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
import kz.tech.smartgrades.school.models.ModelStudentProfileSchools;
import kz.tech.smartgrades.school.models.ModelTeacherProfileSchools;

public class SchoolEditStudentSchoolListAdapter extends RecyclerView.Adapter<SchoolEditStudentSchoolListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelStudentProfileSchools> schoolList;

    public SchoolEditStudentSchoolListAdapter(Context context) {
        this.context = context;
        this.schoolList = new ArrayList<>();
    }

    public void updateData(ArrayList<ModelStudentProfileSchools> schoolList) {
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
    public void onBindViewHolder(@NonNull SchoolEditStudentSchoolListAdapter.ViewHolder holder, int position) {
        ModelStudentProfileSchools model = schoolList.get(position);
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
        }
    }
}
