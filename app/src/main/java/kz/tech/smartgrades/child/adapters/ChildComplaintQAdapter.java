package kz.tech.smartgrades.child.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.parent.model.ModelChatsLastMessages;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class ChildComplaintQAdapter extends RecyclerView.Adapter<ChildComplaintQAdapter.ViewHolder> {

    private ChildActivity activity;
    private ArrayList<String> list;

    private OnItemLongClickListener onItemLongClickListener;

    public interface OnItemLongClickListener {
        void onLongClick(String type, View itemView);
    }

    public ChildComplaintQAdapter(ChildActivity activity) {
        this.activity = activity;
        this.list = new ArrayList<>();
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void updateData(String item) {
        list.add(item);
        notifyDataSetChanged();
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void clearList() {
        list = new ArrayList<>();
    }

    public void remove(String item) {
        if (!listIsNullOrEmpty(list) && list.contains(item)) {
            list.remove(item);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_complaint, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChildComplaintQAdapter.ViewHolder holder, int position) {
        String selected = list.get(position);
        if (selected.equals("Student")) {
            holder.ivAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_disciple_intruder));
            holder.tvName.setText(activity.getResources().getString(R.string.Intruder_Apprentice));
        }
        else if (selected.equals("Parent")) {
            holder.ivAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_parent_student_intruder));
            holder.tvName.setText(activity.getResources().getString(R.string.Parent_of_the_offending_student));
        }
        else if (selected.equals("Teacher")) {
            holder.ivAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_classy_the_leader));
            holder.tvName.setText(activity.getResources().getString(R.string.Classroom_teacher));
        }
        else if (selected.equals("Psychologist")) {
            holder.ivAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_psychologist_schools));
            holder.tvName.setText(activity.getResources().getString(R.string.School_psychologist));
        }
        else if (selected.equals("Director")) {
            holder.ivAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_director_schools));
            holder.tvName.setText(activity.getResources().getString(R.string.Head_teacher));
        }
        else if (selected.equals("Inspector")) {
            holder.ivAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_inspector_schools));
            holder.tvName.setText(activity.getResources().getString(R.string.School_inspector));
        }
        else if (selected.equals("Protection")) {
            holder.ivAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_protection));
            holder.tvName.setText(activity.getResources().getString(R.string.Organization_for_the_Protection_of_the_Rights_of_the_Child));
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAvatar;
        TextView tvName;
        CardView cvStudent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            cvStudent = itemView.findViewById(R.id.cvStudent);

            itemView.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    onItemLongClickListener.onLongClick(list.get(position), itemView);
                    return false;
                }
            });
        }
    }
}
