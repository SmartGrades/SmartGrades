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


public class MentorGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ModelMentorGroups> mentorGroupsList;
    private OnItemClickListener onItemClickListener;

    public MentorGroupAdapter(Context context) {
        this.context = context;
        this.mentorGroupsList = new ArrayList<>();
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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mentor_group_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ModelMentorGroups m = mentorGroupsList.get(position);
        ((ViewHolder)holder).tvTitle.setText(m.getGroupName());
    }

    @Override
    public int getItemCount() {
        return mentorGroupsList.size();
    }

    private void onClear() {
        if (mentorGroupsList.size() > 0) mentorGroupsList.clear();
    }

    public void updateData(ArrayList<ModelMentorGroups> arrayList2) {
        if (listIsNullOrEmpty(arrayList2)) return;
        onClear();
        mentorGroupsList.addAll(arrayList2);
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
                        onItemClickListener.onItemClick(mentorGroupsList.get(getAdapterPosition()));
                }
            });
        }
    }
}
