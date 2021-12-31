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
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.models.ModelChildTools;

public class WardItemAdapter extends RecyclerView.Adapter<WardItemAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelChildTools> childToolsList;

    public WardItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1://coins
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ward_child_chat, parent, false));
            case 2://time
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ward_child_chat, parent, false));
            case 3://money
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ward_child_chat_money, parent, false));
            case 4://steps
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ward_child_chat_step, parent, false));
        }
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (childToolsList.get(position).getType()){
            case "coins":
                holder.tvCoins.setText(childToolsList.get(position).getCoinsCount());
                holder.ivCoins.setBackground(context.getResources().getDrawable(R.drawable.img_oval_item_ward)); break;
            case "time":
                holder.tvCoins.setText(childToolsList.get(position).getGadgetTime());
                holder.ivCoins.setBackground(context.getResources().getDrawable(R.drawable.img_rectangle_item_ward)); break;
            case "money":
                holder.tvCoins.setText(childToolsList.get(position).getMoneyCount());
                holder.ivCoins.setBackground(context.getResources().getDrawable(R.drawable.img_money_item_ward)); break;
            case "steps":
                holder.tvCoins.setText(childToolsList.get(position).getStepsDone() + "/" + childToolsList.get(position).getStepsCount());
                holder.ivCoins.setBackground(context.getResources().getDrawable(R.drawable.img_step_item_ward));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (childToolsList.get(position).getType()){
            case "coins": return 1;
            case "time": return 2;
            case "money": return 3;
            case "steps": return 4;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return childToolsList.size();//9
    }


    public void updateData(ArrayList<ModelChildTools> childToolsList) {
        this.childToolsList = childToolsList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCoins;
        TextView tvCoins;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCoins = itemView.findViewById(R.id.tvCoins);
            ivCoins = itemView.findViewById(R.id.ivCoins);
        }
    }
}