package kz.tech.smartgrades.mentor.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.model.ModelGrades;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;


public class GradesTableDayAdapter extends RecyclerView.Adapter<GradesTableDayAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelGrades> Grades;

    public GradesTableDayAdapter(Context context) {
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

    public void updateData(ArrayList<ModelGrades> list) {
        onClear(Grades);
        if (listIsNullOrEmpty(list)) return;
        Grades.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_grades_list, parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelGrades m = Grades.get(position);
        holder.tvGrade.setText(m.getGrade());
        if (m.getType().equals("0")) {
            switch (m.getGrade()) {
                case "5":
                    holder.tvGrade.setBackground(context.getResources().getDrawable(R.drawable.background_square_purple));
                    break;
                case "4":
                    holder.tvGrade.setBackground(context.getResources().getDrawable(R.drawable.background_square_green));
                    break;
                case "3":
                    holder.tvGrade.setBackground(context.getResources().getDrawable(R.drawable.background_square_orange));
                    break;
                case "2":
                    holder.tvGrade.setBackground(context.getResources().getDrawable(R.drawable.background_square_red));
                    break;
            }
        }
        else holder.tvGrade.setText("Н");
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGrade;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGrade = itemView.findViewById(R.id.tvGrade);
        }
    }
}