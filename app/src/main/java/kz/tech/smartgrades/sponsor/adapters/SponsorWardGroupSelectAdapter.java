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

public class SponsorWardGroupSelectAdapter extends RecyclerView.Adapter<SponsorWardGroupSelectAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int groupName);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private Context context;
    private ArrayList<ModelMentorGroups> arrayList;

    public SponsorWardGroupSelectAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mentor_ward_group_select, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvText.setText(arrayList.get(position).getGroupName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void onClear() {
        if (arrayList.size() > 0) {
            arrayList.clear();
        }
    }

    public void updateGroup(ArrayList<ModelMentorGroups> arrayList) {
        onClear();
        this.arrayList.addAll(arrayList);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tvText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
