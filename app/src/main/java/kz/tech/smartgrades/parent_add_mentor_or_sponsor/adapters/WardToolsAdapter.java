package kz.tech.smartgrades.parent_add_mentor_or_sponsor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;

public class WardToolsAdapter extends RecyclerView.Adapter<WardToolsAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(String position, String name);
        void onItemClickMessage(String msg);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private Context context;
    private ArrayList<String> arrayList;
    private String[] toolType = new String[8];

    public WardToolsAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
        toolType[0] = "TemporaryCoins";
        toolType[1] = "GadgetTime";
        toolType[2] = "Steps";
        toolType[3] = "Money";
        toolType[4] = "SmartTemporaryCoins";
        toolType[5] = "SmartGadgetTime";
        toolType[6] = "SmartSteps";
        toolType[7] = "SmartMoney";
        toolType[8] = "Grades";
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ward_tools, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivSelect.setImageResource(R.drawable.img_circle_purple_unchecked);
        holder.tvName.setText(arrayList.get(position));
        /*if (position == pos) {
            holder.ivSelect.setImageResource(R.drawable.img_circle_purple_checked);
        }*/
        /*switch (selectType) {
            case 1:
                if (position == 3) {
                    holder.tvName.setTextColor(context.getResources().getColor(R.color.gray_default));
                }
                break;
            case 2:
                if (position == 2) {
                    holder.tvName.setTextColor(context.getResources().getColor(R.color.gray_default));
                }
                break;
        }*/
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public void update(String select) {
        switch (select) {
            case "All":
                arrayList.add("Временные монеты");
                arrayList.add("Время за гаджетом");
                arrayList.add("Шаги к цели");
                arrayList.add("Деньги");
                arrayList.add("Умные оценки = Временные монеты");
                arrayList.add("Умные оценки = Время за геджетом ");
                arrayList.add("Умные оценки = Шаги к цели");
                arrayList.add("Умные оценки = Деньги");
                arrayList.add("Традиционные школьные оценки");
               break;
            case "notTime":
                arrayList.add("Временные монеты");
                arrayList.add("Шаги к цели");
                arrayList.add("Деньги");
                arrayList.add("Умные оценки = Временные монеты");
                arrayList.add("Умные оценки = Шаги к цели");
                arrayList.add("Умные оценки = Деньги");
                arrayList.add("Традиционные школьные оценки");
                break;
            case "Grades":
                arrayList.add("Традиционные школьные оценки");
                break;
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivSelect;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivSelect = itemView.findViewById(R.id.ivPlusOrMinus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    boolean isAccess = true;
                    if (onItemClickListener != null) {
                        /*switch (selectType) {
                            case 1:
                                if (position == 3) {
                                    onItemClickListener.onItemClickMessage("для Менторов этот инструмент не доступен");
                                    isAccess = false;
                                }
                                break;
                            case 2:
                                if (position == 2) {
                                    onItemClickListener.onItemClickMessage("для Менторов этот инструмент не доступен");
                                    isAccess = false;
                                }
                                break;
                        }*/
                        if (isAccess) onItemClickListener.onItemClick(toolType[position], arrayList.get(position));
                    }
                }
            });
        }
    }
}
