package kz.tech.smartgrades.parent_add_mentor_or_sponsor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;

public class WardCoinsAdapter extends RecyclerView.Adapter<WardCoinsAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(String select);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    private Context context;
    private ArrayList<String> arrayList;

    public WardCoinsAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
        this.arrayList.add("-4");//0
        this.arrayList.add("-3");//1
        this.arrayList.add("-2");//2
        this.arrayList.add("-1");
        this.arrayList.add("0");
        this.arrayList.add("+1");
        this.arrayList.add("+2");
        this.arrayList.add("+3");
        this.arrayList.add("+4");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ward_coins, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (arrayList.get(position).substring(0, 1).equals("-")) {
            holder.tvCoins.setTextColor(context.getResources().getColor(R.color.red_tomato));
        } else if (arrayList.get(position).substring(0, 1).equals("0")) {
            holder.tvCoins.setTextColor(context.getResources().getColor(R.color.gray_default));
        } else if (arrayList.get(position).substring(0, 1).equals("+")) {
            holder.tvCoins.setTextColor(context.getResources().getColor(R.color.blue_sea));
        }
        holder.tvCoins.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();//9
    }

    private void onClear() {
        if (arrayList.size() > 0) {
            arrayList.clear();
        }
    }

    public void onUpdate(String interval) {
        onClear();
        try {
            String[] agr = interval.split(":");
            int start = Integer.parseInt(agr[0]);
            int end = Integer.parseInt(agr[1]);
            for (int i = 0; i > start; i--) {
                int lol = i -1;
                arrayList.add(0,String.valueOf(lol));
            }
            arrayList.add("0");
            for (int j = 0; j < end; j++) {
                int lol2 = j +1;
                arrayList.add("+" + String.valueOf(lol2));
            }
        } catch (Exception e) { }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCoins;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCoins = itemView.findViewById(R.id.tvCoins);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        onItemClickListener.onItemClick(arrayList.get(position));
                    }
                }
            });
        }
    }
}
