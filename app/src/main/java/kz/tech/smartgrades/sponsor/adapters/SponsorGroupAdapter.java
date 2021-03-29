package kz.tech.smartgrades.sponsor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.models.ModelMentorGroups;


public class SponsorGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    private ArrayList<ModelMentorGroups> arrayList;
    private OnItemClickListener onItemClickListener;

    public SponsorGroupAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
    }

    public interface OnItemClickListener {
        void onItemClick(ModelMentorGroups m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SponsorGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mentor_group_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ModelMentorGroups m = arrayList.get(position);
        ((ViewHolder)holder).tvTitle.setText(m.getGroupName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void onClear() {
        if (arrayList.size() > 0) arrayList.clear();
    }

    public void updateList(ArrayList<ModelMentorGroups> arrayList2) {
        onClear();
        arrayList.addAll(arrayList2);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(arrayList.get(getAdapterPosition()));
                }
            });
        }
    }
}
