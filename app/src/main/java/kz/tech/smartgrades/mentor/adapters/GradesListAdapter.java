package kz.tech.smartgrades.mentor.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.models.ModelGradesTableDay;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;


public class GradesListAdapter extends RecyclerView.Adapter<GradesListAdapter.ViewHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private ArrayList<ModelGradesTableDay> Grades;

    public GradesListAdapter(Context context) {
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

    public void updateData(ArrayList<ModelGradesTableDay> list) {
        onClear(Grades);
        if (listIsNullOrEmpty(list)) return;
        Grades.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_grades_list_v2, parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelGradesTableDay m = Grades.get(position);

        holder.tvDay.setText(String.valueOf(m.getDay()));

        if (m.getGrades().size() > 2){
            GradesTableDayAdapter adapter = new GradesTableDayAdapter(context);
            holder.rvGradesList.setLayoutManager(new GridLayoutManager(context, 2,GridLayoutManager.HORIZONTAL, false));
            holder.rvGradesList.setAdapter(adapter);
            holder.rvGradesList.setVisibility(View.VISIBLE);
            adapter.updateData(m.getGrades());
        }
        else{
            holder.llGrades.setVisibility(View.VISIBLE);
            holder.tvGrade1.setVisibility(View.VISIBLE);
            holder.tvGrade1.setText(m.getGrades().get(0).getGrade());
            if (m.getGrades().get(0).getType().equals("0")) {
                switch (m.getGrades().get(0).getGrade()) {
                    case "5":
                        holder.tvGrade1.setBackground(context.getResources().getDrawable(R.drawable.background_square_purple));
                        break;
                    case "4":
                        holder.tvGrade1.setBackground(context.getResources().getDrawable(R.drawable.background_square_green));
                        break;
                    case "3":
                        holder.tvGrade1.setBackground(context.getResources().getDrawable(R.drawable.background_square_orange));
                        break;
                    case "2":
                        holder.tvGrade1.setBackground(context.getResources().getDrawable(R.drawable.background_square_red));
                        break;
                }
            }
            else holder.tvGrade1.setText("Н");

            if (m.getGrades().size() > 1){
                holder.tvGrade2.setVisibility(View.VISIBLE);
                holder.tvGrade2.setText(m.getGrades().get(1).getGrade());
                if (m.getGrades().get(1).getType().equals("0")) {
                    switch (m.getGrades().get(0).getGrade()) {
                        case "5":
                            holder.tvGrade2.setBackground(context.getResources().getDrawable(R.drawable.background_square_purple));
                            break;
                        case "4":
                            holder.tvGrade2.setBackground(context.getResources().getDrawable(R.drawable.background_square_green));
                            break;
                        case "3":
                            holder.tvGrade2.setBackground(context.getResources().getDrawable(R.drawable.background_square_orange));
                            break;
                        case "2":
                            holder.tvGrade2.setBackground(context.getResources().getDrawable(R.drawable.background_square_red));
                            break;
                    }
                }
                else holder.tvGrade2.setText("Н");
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay, tvGrade1, tvGrade2;
        LinearLayout llGrades;
        RecyclerView rvGradesList;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvGrade1 = itemView.findViewById(R.id.tvGrade1);
            tvGrade2 = itemView.findViewById(R.id.tvGrade2);
            llGrades = itemView.findViewById(R.id.llGrades);
            rvGradesList = itemView.findViewById(R.id.rvGradesList);
        }
    }
}