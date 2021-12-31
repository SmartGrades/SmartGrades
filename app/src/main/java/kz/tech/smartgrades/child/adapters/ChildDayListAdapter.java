package kz.tech.smartgrades.child.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.parent.model.ModelTransactions;

public class ChildDayListAdapter extends RecyclerView.Adapter<ChildDayListAdapter.ViewHolder> {

    private ChildActivity activity;
    private ArrayList<Integer> list;
    private int tag;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onDayClick(String day, int tag);
    }

    public ChildDayListAdapter(ChildActivity activity, int tag) {
        this.activity = activity;
        this.tag = tag;
        this.list = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<Integer> list) {
        onClear(this.list);
        this.list = list;
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_day, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChildDayListAdapter.ViewHolder holder, int position) {
        holder.tvDay.setText(Integer.toString(list.get(position)));
        if (list.get(position) < 2) {
            holder.tvDayLabel.setText(activity.getResources().getString(R.string.day1));
        } else if (list.get(position) < 5) {
            holder.tvDayLabel.setText(activity.getResources().getString(R.string.day2));
        } else {
            holder.tvDayLabel.setText(activity.getResources().getString(R.string.day3));
        }
    }



    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDay;
        TextView tvDayLabel;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDay = itemView.findViewById(R.id.tvDay);
            tvDayLabel = itemView.findViewById(R.id.tvDayLabel);

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    String result;
                    if (list.get(position) < 2) {
                        result = list.get(position) + " " + activity.getResources().getString(R.string.day1);
                    } else if (list.get(position) < 5) {
                        result = list.get(position) + " " + activity.getResources().getString(R.string.day2);
                    } else {
                        result = list.get(position) + " " + activity.getResources().getString(R.string.day3);
                    }
                    onItemClickListener.onDayClick(result, tag);
                }
            });
        }
    }
}
