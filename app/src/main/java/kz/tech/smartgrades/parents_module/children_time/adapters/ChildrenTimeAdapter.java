package kz.tech.smartgrades.parents_module.children_time.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parents_module.auto_charge.ItemListAutoCharge;
import kz.tech.smartgrades.parents_module.children_time.models.ModelChildrenTime;
import kz.tech.smartgrades.root.var_resources.VarID;

public class ChildrenTimeAdapter extends RecyclerView.Adapter<ChildrenTimeAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(int position, String time);
        void onDayTimeOff(int position, String time);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private Context context;
    private ArrayList<String> arrayList;
    public ChildrenTimeAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new ItemListAutoCharge(context));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] days = context.getResources().getStringArray(R.array.days_of_the_week);
        String[] from_to = context.getResources().getStringArray(R.array.from_to);//  0 = from, 1 = to
        if (arrayList.get(position) != null) {
            holder.tvDay.setText(days[position]);

            if (arrayList.get(position).equals("close")) {
                holder.tvTime.setText(context.getResources().getString(R.string.turn_off));
            } else {

                String selectDay = arrayList.get(position);//  GET POSITION TEXT IN ARRAY LIST
                String[] parseDate = selectDay.split("-");//  PARSE DATE DASH
                String startDate = parseDate[0];//  GET START DATE
                String endDate = parseDate[1];//  GET END DATE

                String[] parseStartDate = startDate.split(":");//  PARSE START DATE DOT
                String startHour = parseStartDate[0];//  GET START HOUR
                String startMinute = parseStartDate[1];//  GET START MINUTE

                String[] parseEndDate = endDate.split(":");//  PARSE END DATE DOT
                String endHour = parseEndDate[0];//  GET END HOUR
                String endMinute = parseEndDate[1];//  GET END MINUTE

                String result = from_to[0] + " " + startHour + ":" + startMinute + " " + from_to[1] + "  " + endHour + ":" + endMinute;

                holder.tvTime.setText(result);
            }
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay, tvTime;
        ImageView ivMove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = (TextView) itemView.findViewById(VarID.ID_TV_AUTO_CHARGE_DAY);
            tvTime = (TextView) itemView.findViewById(VarID.ID_TV_AUTO_CHARGE_TIME);
            ivMove = (ImageView) itemView.findViewById(VarID.ID_IV_AUTO_CHARGE_MOVE);
            ivMove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position, arrayList.get(position));
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null) {
                        onItemClickListener.onDayTimeOff(position, arrayList.get(position));
                    }
                    return false;
                }
            });
        }
    }
    public void setData(ModelChildrenTime data) {
        arrayList.clear();
        if (data != null) {
            arrayList.add(data.getaMonday());
            arrayList.add(data.getbTuesday());
            arrayList.add(data.getcWednesday());
            arrayList.add(data.getdThursday());
            arrayList.add(data.geteFriday());
            arrayList.add(data.getfSaturday());
            arrayList.add(data.getgSunday());
        }
        notifyDataSetChanged();
    }
    public void changeDataByDayPosition(int position, String incrementTime) {
        if (arrayList.get(position) != null) {
            arrayList.set(position, incrementTime);
        }
        notifyItemChanged(position);
    }
}
