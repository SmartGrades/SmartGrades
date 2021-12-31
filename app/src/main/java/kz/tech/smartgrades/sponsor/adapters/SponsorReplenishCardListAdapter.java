package kz.tech.smartgrades.sponsor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import kz.tech.smartgrades.sponsor.models.ModelDiscontCard;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class SponsorReplenishCardListAdapter extends RecyclerView.Adapter<SponsorReplenishCardListAdapter.ViewHolder> {

    private SponsorActivity activity;
    private ArrayList<ModelDiscontCard> cardList;

    private OnItemCLickListener onItemCLickListener;

    public interface OnItemCLickListener {
        void onReplenishClick(ModelDiscontCard mCard);
        void onOptionClick(ModelDiscontCard mCard, View itemView);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    public SponsorReplenishCardListAdapter(SponsorActivity activity) {
        this.activity = activity;
        this.cardList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        if (cardList != null)
            return cardList.size();
        return 0;
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    public void updateData(ArrayList<ModelDiscontCard> childList) {
        if (!listIsNullOrEmpty(this.cardList)) onClear(this.cardList);
        this.cardList = childList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank_card, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelDiscontCard m = cardList.get(position);
        String start = m.getCardNumber().substring(0, 4);
        String end = m.getCardNumber().substring(11, 15);
        holder.tvCardNumber.setText(start + " **** **** " + end);
        if (!stringIsNullOrEmpty(m.getCardName())) {
            holder.tvMyBankName.setText(m.getCardName());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCardNumber;
        TextView tvExtract;
        ImageView ivExtract;
        ImageView ivBankCardOptions;
        TextView tvMyBankName;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCardNumber = itemView.findViewById(R.id.tvCardNumber);
            ivExtract = itemView.findViewById(R.id.ivExtract);
            tvExtract = itemView.findViewById(R.id.tvExtract);
            tvExtract.setText(activity.getResources().getString(R.string.top_up));
            ivBankCardOptions = itemView.findViewById(R.id.ivBankCardOptions);
            ivBankCardOptions.setVisibility(View.VISIBLE);
            tvMyBankName = itemView.findViewById(R.id.tvMyBankName);

            ivExtract.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener==null) return;
                    onItemCLickListener.onReplenishClick(cardList.get(getAdapterPosition()));
                }
            });
            ivBankCardOptions.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener==null) return;
                    onItemCLickListener.onOptionClick(cardList.get(getAdapterPosition()), ivBankCardOptions);
                }
            });
        }
    }
}