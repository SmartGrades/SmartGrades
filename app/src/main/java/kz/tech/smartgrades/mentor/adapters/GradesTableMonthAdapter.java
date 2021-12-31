package kz.tech.smartgrades.mentor.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.models.ModelGradesTableMonth;

import static kz.tech.smartgrades.GET.MonthName;
import static kz.tech.smartgrades.GET.listIsNullOrEmpty;


public class GradesTableMonthAdapter extends RecyclerView.Adapter<GradesTableMonthAdapter.ViewHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private ArrayList<ModelGradesTableMonth> Grades;

    public GradesTableMonthAdapter(Context context) {
        this.context = context;
        this.Grades = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return Grades.size();
    }

    private void onClear(ArrayList<?> arrayList) {
        if (arrayList.size() > 0) arrayList.clear();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(ArrayList<ModelGradesTableMonth> list) {
        onClear(Grades);
        if (listIsNullOrEmpty(list)) return;
        Grades.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_grades_table, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelGradesTableMonth m = Grades.get(position);

        holder.tvMonth.setText(MonthName(m.getMonth()));
        holder.tvAverageGrade.setText(String.valueOf(m.getAverageGrade()));
        if (m.getReward() != 0) holder.tvReward.setText("₸ + " + m.getReward());
        else {
            holder.tvReward.setText("₸ 0.0");
        }
        if (m.getAverageGrade() != 0)
            holder.tvAverageGrade.setText(Double.toString(m.getAverageGrade()));
        else holder.tvAverageGrade.setText("0,0");

        GradesListAdapter adapter = new GradesListAdapter(context);
        holder.rvGradesList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        holder.rvGradesList.setAdapter(adapter);
        adapter.updateData(m.getGradesTableDay());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMonth, tvAverageGrade;
        RecyclerView rvGradesList;
        TextView tvReward;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvAverageGrade = itemView.findViewById(R.id.tvAverageGrade);
            tvReward = itemView.findViewById(R.id.tvReward);
            rvGradesList = itemView.findViewById(R.id.rvGradesList);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick();
                }
            });*/
        }
    }
}