package kz.tech.smartgrades.sponsor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.parent.model.ModelChildPeriods;
import kz.tech.smartgrades.parent.model.ModelSponsorship;
import kz.tech.smartgrades.sponsor.SponsorActivity;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class SponsorPeriodListAdapter extends RecyclerView.Adapter<SponsorPeriodListAdapter.ViewHolder> {

    private SponsorActivity activity;
    private ArrayList<ModelChildPeriods> modelPeriod;
    private ModelSponsorship modelChildSponsorship;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onTransactionClick();
    }

    public SponsorPeriodListAdapter(SponsorActivity activity, ModelSponsorship modelChildSponsorship) {
        this.activity = activity;
        this.modelChildSponsorship = modelChildSponsorship;
        this.modelPeriod = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<ModelChildPeriods> modelPeriod) {
        onClear(this.modelPeriod);
        this.modelPeriod = modelPeriod;
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_child_week, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SponsorPeriodListAdapter.ViewHolder holder, int position) {
        ModelChildPeriods m = modelPeriod.get(position);
        holder.tvCount.setText(m.getWeek());
        if (!stringIsNullOrEmpty(m.getReward())) holder.tvIncomeCount.setText("-");
        else if (Integer.parseInt(m.getReward()) == 0) holder.tvIncomeCount.setTextColor(activity.getResources().getColor(R.color.red));
        else holder.tvIncomeCount.setText(m.getReward());
        String weekStart = Convert.String2ShortDate(m.getDateStart());
        String weekEnd = Convert.String2ShortDate(m.getDateEnd());
        holder.tvWeekPeriod.setText(weekStart + "  -  " + weekEnd);
        if (Integer.toString(modelChildSponsorship.getCurrentWeek()).equals(m.getWeek())) holder.cvWeek.setCardBackgroundColor(activity.getResources().getColor(R.color.lime_background));
        else holder.cvWeek.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
    }

    @Override
    public int getItemCount() {
        if (modelPeriod != null) return modelPeriod.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCount;
        TextView tvWeekPeriod;
        TextView tvIncomeCount;
        CardView cvWeek;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCount = itemView.findViewById(R.id.tvCount);
            tvWeekPeriod = itemView.findViewById(R.id.tvWeekPeriod);
            tvIncomeCount = itemView.findViewById(R.id.tvIncomeCount);
            cvWeek = itemView.findViewById(R.id.cvWeek);
        }
    }
}
