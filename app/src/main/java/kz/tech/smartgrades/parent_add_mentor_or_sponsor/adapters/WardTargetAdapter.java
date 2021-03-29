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

public class WardTargetAdapter extends RecyclerView.Adapter<WardTargetAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position, String name);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private Context context;
    private ArrayList<String> arrayList;
    private int pos;

    public WardTargetAdapter(Context context, int pos) {
        this.context = context;
        this.pos = pos;
        this.arrayList = new ArrayList<>();
        this.arrayList.add("Play Station 4");
        this.arrayList.add("Велосипед");
        this.arrayList.add("Поход в кино на \"Звездные воины\"");

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ward_tools, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position, arrayList.get(position));
                    }
                }
            });
        }
    }
 }
