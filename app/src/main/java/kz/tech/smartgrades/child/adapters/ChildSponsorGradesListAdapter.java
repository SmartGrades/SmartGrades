package kz.tech.smartgrades.child.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.model.ModelGrades;

public class ChildSponsorGradesListAdapter extends RecyclerView.Adapter<ChildSponsorGradesListAdapter.ViewHolder> {

    private ChildActivity activity;
    private ArrayList<ModelGrades> modelGrades;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onTransactionClick();
    }

    public ChildSponsorGradesListAdapter(ChildActivity activity) {
        this.activity = activity;
        this.modelGrades = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<ModelGrades> modelGrades) {
        onClear(this.modelGrades);
        this.modelGrades = modelGrades;
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_grades_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChildSponsorGradesListAdapter.ViewHolder holder, int position) {
        ModelGrades m = modelGrades.get(position);
        holder.tvGrade.setText(m.getGrade());
        if (m.getGrade().equals("0")) holder.tvGrade.setBackground(activity.getResources().getDrawable(R.drawable.background_square_red));
        else if (m.getGrade().equals("2")) holder.tvGrade.setBackground(activity.getResources().getDrawable(R.drawable.background_square_red));
        else if (m.getGrade().equals("3")) holder.tvGrade.setBackground(activity.getResources().getDrawable(R.drawable.background_square_orange));
        else if (m.getGrade().equals("4")) holder.tvGrade.setBackground(activity.getResources().getDrawable(R.drawable.background_square_green));
        else if (m.getGrade().equals("5")) holder.tvGrade.setBackground(activity.getResources().getDrawable(R.drawable.background_square_purple));
    }

    @Override
    public int getItemCount() {
        if (modelGrades != null) return modelGrades.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvGrade;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGrade = itemView.findViewById(R.id.tvGrade);
        }
    }
}
