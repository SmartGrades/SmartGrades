package kz.tech.smartgrades.child.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.MSG_TYPE;
import kz.tech.smartgrades.parent.model.ModelGrades;


public class ChildGradesListAdapter extends RecyclerView.Adapter<ChildGradesListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelGrades> lastGrades;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ChildGradesListAdapter(Context context) {
        this.context = context;
        this.lastGrades = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return lastGrades.size();
    }

    private void onClear(ArrayList<?> arrayList) {
        if (arrayList.size() > 0) arrayList.clear();
    }

    public void updateData(ArrayList<ModelGrades> list) {
        onClear(lastGrades);
        lastGrades.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_grades_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelGrades m = lastGrades.get(position);
        if (m.getType().equals(Integer.toString(MSG_TYPE.SKIP_WANC)) || m.getType().equals(Integer.toString(MSG_TYPE.SKIP_WAC)) || m.getType().equals(Integer.toString(MSG_TYPE.SKIP_LATE)) || m.getType().equals(Integer.toString(MSG_TYPE.SKIP_SICK))){
            switch (Integer.parseInt(m.getType())){
                case MSG_TYPE.SKIP_WANC:
                    holder.tvGrade.setText("Н");
                    holder.fl.setBackgroundResource(R.drawable.background_grade_red);
                    break;
                case MSG_TYPE.SKIP_WAC:
                    holder.tvGrade.setText("П");
                    holder.fl.setBackgroundResource(R.drawable.background_grade_gray);
                    break;
                case MSG_TYPE.SKIP_LATE:
                    holder.tvGrade.setText("О");
                    holder.fl.setBackgroundResource(R.drawable.background_grade_gray);
                    break;
                case MSG_TYPE.SKIP_SICK:
                    holder.tvGrade.setText("Б");
                    holder.fl.setBackgroundResource(R.drawable.background_grade_gray);
                    break;
            }
        }
        else {
            holder.tvGrade.setText(m.getGrade());
            switch (m.getGrade()){
                case "5":holder.fl.setBackgroundResource(R.drawable.background_grade_green); break;
                case "4":holder.fl.setBackgroundResource(R.drawable.background_grade_green); break;
                case "3":holder.fl.setBackgroundResource(R.drawable.background_grade_orange); break;
                case "2":holder.fl.setBackgroundResource(R.drawable.background_grade_red); break;
                case "...":
                    holder.fl.setBackground(null);
                    holder.tvGrade.setTextColor(Color.BLACK);
                    break;
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout fl;
        TextView tvGrade;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            fl = itemView.findViewById(R.id.fl);
            tvGrade = itemView.findViewById(R.id.tvGrade);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick();
                }
            });
        }
    }
}
