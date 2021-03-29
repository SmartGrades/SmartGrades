package kz.tech.smartgrades.childs_module.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parents_module.coins.SquareImageView;
import kz.tech.smartgrades.root.var_resources.VarID;


public class ChildMainStepsAdapter extends RecyclerView.Adapter<ChildMainStepsAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private List<Integer> arrayList;
    private Context context;
    public ChildMainStepsAdapter(Context context, int[] steps) {
        this.context = context;
        this.arrayList = new ArrayList<>();
        for (int i = 0; i < steps.length; i++) {
            arrayList.add(steps[i]);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new SquareImageView(context));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int step = arrayList.get(position);
        if (step == 1) {
            holder.imageView.setImageResource(R.mipmap.done);
            holder.imageView.setAlpha(1.0f);
        }
        if (step == 2) {
            holder.imageView.setAlpha(1.0f);
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
            imageView = (ImageView)itemView.findViewById(VarID.ID_IV_SQUARE);
        }
    }
}
