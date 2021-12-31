package kz.tech.smartgrades.parent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.models.ModelSchoolList;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.model.ModelChildProfileSchools;

public class ParentEditChildSchoolListAdapter extends RecyclerView.Adapter<ParentEditChildSchoolListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelChildProfileSchools> schoolList;

    public ParentEditChildSchoolListAdapter(Context context) {
        this.context = context;
        this.schoolList = new ArrayList<>();
    }

    public void updateData(ArrayList<ModelChildProfileSchools> schoolList) {
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
    public void onBindViewHolder(@NonNull ParentEditChildSchoolListAdapter.ViewHolder holder, int position) {
        ModelChildProfileSchools model = schoolList.get(position);
        holder.tvSchool.setText(model.getName() + "\n" + model.getClasses());
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
