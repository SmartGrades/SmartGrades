package kz.tech.smartgrades.child.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.parent.model.ModelTransactions;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ChildTransactionListAdapter extends RecyclerView.Adapter<ChildTransactionListAdapter.ViewHolder> {

    private ChildActivity activity;
    private ArrayList<ModelTransactions> modelTransactions;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onTransactionClick();
    }

    public ChildTransactionListAdapter(ChildActivity activity) {
        this.activity = activity;
        this.modelTransactions = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<ModelTransactions> modelTransactions) {
        onClear(this.modelTransactions);
        this.modelTransactions = modelTransactions;
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_child_avatar_with_income, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChildTransactionListAdapter.ViewHolder holder, int position) {
        //ModelChildData model = childList.get(position); // error
//        Picasso.get().load(model.getAvatar()).fit().centerInside().into(holder.civAvatar);
//        holder.tvFocusedChild.setText("Имя Фамилия");
        if (Integer.parseInt(modelTransactions.get(position).getSum()) > 0)
            holder.tvIncomeCount.setText("+" + modelTransactions.get(position).getSum());
        else holder.tvIncomeCount.setText(modelTransactions.get(position).getSum());
        if (!stringIsNullOrEmpty(modelTransactions.get(position).getAvatar())) Picasso.get().load(modelTransactions.get(position).getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
        else Picasso.get().load(R.drawable.img_default_avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
    }



    @Override
    public int getItemCount() {
//        return Integer.MAX_VALUE;
        if (modelTransactions != null) return modelTransactions.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civAvatar;
        TextView tvIncomeCount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvIncomeCount = itemView.findViewById(R.id.tvIncomeCount);

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    onItemClickListener.onTransactionClick();
                }
            });
        }
    }
}
