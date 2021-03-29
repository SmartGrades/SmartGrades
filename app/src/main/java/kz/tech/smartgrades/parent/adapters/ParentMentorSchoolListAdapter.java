package kz.tech.smartgrades.parent.adapters;

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

public class ParentMentorSchoolListAdapter extends RecyclerView.Adapter<ParentMentorSchoolListAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelSchoolList> schoolList;

    public ParentMentorSchoolListAdapter(ParentActivity activity) {
        this.activity = activity;
        this.schoolList = new ArrayList<>();
    }

    public void updateData(ArrayList<ModelSchoolList> schoolList) {
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
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_school, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParentMentorSchoolListAdapter.ViewHolder holder, int position) {
        ModelSchoolList model = schoolList.get(position);
        holder.tvSchool.setText(model.getName() + "\n" + model.getAddress());
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
