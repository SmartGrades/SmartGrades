package kz.tech.smartgrades.root.adapters;

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
import kz.tech.smartgrades.root.models.ModelCountry;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class DefaultAdapter extends RecyclerView.Adapter<DefaultAdapter.ViewHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;
    private ArrayList<ModelCountry> Countries;
    private ArrayList<ModelCountry> showCountries;
    private ArrayList<ModelCountry> oldCountries;

    public interface OnItemClickListener {
        void onItemClick(ModelCountry Country);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public DefaultAdapter(Context context) {
        this.context = context;
        Countries = new ArrayList<>();
        showCountries = new ArrayList<>();
        oldCountries = new ArrayList<>();
    }

    public void setData(ArrayList<ModelCountry> countries) {
        if (listIsNullOrEmpty(countries)) return;
        if (showCountries.size() > 0) {
            onClear(oldCountries);
            oldCountries.addAll(showCountries);
        }
        onClear(Countries);
        onClear(showCountries);
        Countries.addAll(countries);
        showCountries.addAll(countries);
        notifyDataSetChanged();
    }
    public void selectOldData() {
        onClear(showCountries);
        showCountries.addAll(oldCountries);
        notifyDataSetChanged();
    }
    private void onClear(ArrayList<?> list){
        list.clear();
    }

    @Override
    public int getItemCount() {
        return showCountries.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_adapter, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(showCountries.get(position).getName());
        if (showCountries.get(position).getType() == 2)
            holder.ivArrow.setVisibility(View.GONE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivArrow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivArrow = itemView.findViewById(R.id.ivArrow);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener == null) return;
                    onItemClickListener.onItemClick(showCountries.get(getAdapterPosition()));
                }
            });
        }
    }

    public void getFilter(CharSequence charSequence) {
        showCountries.clear();
        for (int i = 0; i < Countries.size(); i++){
            if (Countries.get(i).getName().contains(charSequence)) showCountries.add(Countries.get(i));
        }
        notifyDataSetChanged();
    }
}
