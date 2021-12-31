package kz.tech.smartgrades.childs_module.adapters;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;

public class PiggyAdapter extends RecyclerView.Adapter<PiggyAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(ImageView imageView);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private Context context;
    private ArrayList<Integer> arrayList;
    public PiggyAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PiggyAdapter.ViewHolder(new PiggyItemList(context));
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById((int) 129901);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {

                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(imageView);
                    }

                    return false;
                }
            });
        }
    }
    public void insertData(int numbMinute) {
        if (numbMinute > 0) {
            arrayList.clear();
            int coins = ((int) (10 * numbMinute) / 20) / 10;
            for (int i = 0; i < coins; i++) {
                arrayList.add(1);
            }
            notifyDataSetChanged();
        }
    }
    public void updateData(int numbMinute) {
        if (numbMinute > 0) {
            int coins = ((int) (10 * numbMinute) / 20) / 10;
            for (int i = 0; i < coins; i++) {
                arrayList.add(1);
            }
            notifyDataSetChanged();
        }
    }
    public void deleteData() {
        int size = arrayList.size();
        arrayList.remove(size-1);
        notifyDataSetChanged();
    }
}
