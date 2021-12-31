package kz.tech.smartgrades.root.adapters;

import android.annotation.SuppressLint;
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

    private ArrayList<ModelCountry> Countries;
    private ArrayList<ModelCountry> filterCountries;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ModelCountry Country);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public DefaultAdapter(Context context) {
        this.context = context;
        Countries = new ArrayList<>();
        filterCountries = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<ModelCountry> countries) {
        if (listIsNullOrEmpty(countries)) return;
        onClear(Countries);
        onClear(filterCountries);
        Countries.addAll(countries);
        filterCountries.addAll(countries);
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setFilter(String string) {
        filterCountries.clear();
        for (ModelCountry mCountry : Countries) {
            if (mCountry.getName().toLowerCase().contains(string.toLowerCase()))
                filterCountries.add(mCountry);
        }
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> list) {
        list.clear();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void onClear() {
        Countries.clear();
        filterCountries.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filterCountries.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_adapter, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(filterCountries.get(position).getName());
        if (filterCountries.get(position).getType() == 2)
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
                    onItemClickListener.onItemClick(filterCountries.get(getAdapterPosition()));
                }
            });
        }
    }


}
