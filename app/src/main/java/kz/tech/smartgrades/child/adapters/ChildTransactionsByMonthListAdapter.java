package kz.tech.smartgrades.child.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.parent.model.ModelTransactionsMonth;

public class ChildTransactionsByMonthListAdapter extends RecyclerView.Adapter<ChildTransactionsByMonthListAdapter.ViewHolder> {

    private ChildActivity activity;
    private ArrayList<ModelTransactionsMonth> transactionsMonthsList;

    private ChildTransactionsDayListAdapter childTransactionsDayListAdapter;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(ModelTransactionsMonth transactionsMonthsList);
    }

    public ChildTransactionsByMonthListAdapter(ChildActivity activity) {
        this.activity = activity;
        this.transactionsMonthsList = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<ModelTransactionsMonth> transactionsMonthsList) {
        onClear(this.transactionsMonthsList);
        this.transactionsMonthsList = transactionsMonthsList;
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_child_income_history_by_date, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChildTransactionsByMonthListAdapter.ViewHolder holder, int position) {
        holder.tvTransactionDate.setText(transactionsMonthsList.get(position).getDate());
        holder.rvIncomesList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true));
        childTransactionsDayListAdapter = new ChildTransactionsDayListAdapter(activity);
        holder.rvIncomesList.setAdapter(childTransactionsDayListAdapter);
        childTransactionsDayListAdapter.updateData(transactionsMonthsList.get(position).getTransactions());
    }

    @Override
    public int getItemCount() {
        if (transactionsMonthsList != null) return transactionsMonthsList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTransactionDate;
        RecyclerView rvIncomesList;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTransactionDate = itemView.findViewById(R.id.tvTransactionDate);
            rvIncomesList = itemView.findViewById(R.id.rvIncomesList);

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                }
            });
        }
    }
}
