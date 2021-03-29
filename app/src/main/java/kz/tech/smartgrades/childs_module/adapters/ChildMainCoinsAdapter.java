package kz.tech.smartgrades.childs_module.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.var_resources.VarID;

public class ChildMainCoinsAdapter extends RecyclerView.Adapter<ChildMainCoinsAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(View view);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private Context context;
    private ArrayList<Integer> arrayList;
    public ChildMainCoinsAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new ItemCoins(context));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (arrayList.get(position) != null) {
            if (arrayList.get(position) == 1) {
                holder.imageView.setImageResource(R.mipmap.coins_gold);
            }
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(VarID.ID_IV_COINS_ITEMS);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemView);
                    }
                    return false;
                }
            });

        }
    }

    public void setData(int[] data) {
        arrayList.clear();
        for (int i = 0; i < data.length; i++) {
            arrayList.add(data[i]);
        }
        notifyDataSetChanged();
    }
    public void updateData() {
        arrayList.add(1);
        notifyDataSetChanged();
    }
    public void deleteData() {

        arrayList.remove(0);
        notifyDataSetChanged();
    }
}
