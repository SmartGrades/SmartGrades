package kz.tech.smartgrades.parents_module.coins;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.smartgrades.root.var_resources.VarID;


public class SquareAdapter extends RecyclerView.Adapter<SquareAdapter.ViewHolder> {
    private ArrayList<Integer> arrayList;
    private Context context;
    public SquareAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new SquareImageView(context));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(VarID.ID_IV_SQUARE);
        }
    }
    public void setData(int squares) {
        for (int z = 0; z < squares; z++) {
            arrayList.add(z);
        }
        notifyDataSetChanged();
    }
}
