package kz.tech.smartgrades.mentor.adapters;

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

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;


public class SelectMentorGroupForAddedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ModelMentorGroups> mentorGroupList;
    private OnItemClickListener onItemClickListener;

    public SelectMentorGroupForAddedAdapter(Context context) {
        this.context = context;
        this.mentorGroupList = new ArrayList<>();
    }

    public interface OnItemClickListener {
        void onItemClick(ModelMentorGroups m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_school_select_group_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ModelMentorGroups m = mentorGroupList.get(position);
        ((ViewHolder)holder).tvTitle.setText(m.getGroupName());
    }

    @Override
    public int getItemCount() {
        return mentorGroupList.size();
    }

    private void onClear() {
        if (mentorGroupList.size() > 0) mentorGroupList.clear();
    }

    public void updateData(ArrayList<ModelMentorGroups> groupList) {
        if (listIsNullOrEmpty(groupList)) return;
        onClear();
        this.mentorGroupList.addAll(groupList);
    }

    public void selectList() {
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
                        onItemClickListener.onItemClick(mentorGroupList.get(getAdapterPosition()));
                }
            });
        }
    }
}
