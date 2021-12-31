package kz.tech.smartgrades.school.adaptes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.S;
import kz.tech.smartgrades.school.models.ModelUsersList;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ModelUsersList m);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private ArrayList<ModelUsersList> childList;
    private ArrayList<ModelUsersList> mentorList;
    private ArrayList<ModelUsersList> userList;
    private Context context;

    public UserListAdapter(Context context) {
        this.context = context;
        this.childList = new ArrayList<>();
        this.mentorList = new ArrayList<>();
        this.userList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mentor_child_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelUsersList m = userList.get(position);
        Picasso.get().load(m.getAvatar()).fit().centerInside().into(holder.civAvatar);
        holder.tvFullName.setText(m.getFullName());
        holder.tvDate.setText(Convert.String2ShortTime(m.getDate()));
        holder.tvTitle.setText(m.getLogin());
        holder.tvGrade.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private void onClear() {
        if (childList.size() > 0) childList.clear();
        if (mentorList.size() > 0) mentorList.clear();
    }

    public void updateData(List<ModelUsersList> list) {
        onClear();
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getType().equals(S.CHILD)) childList.add(list.get(i));
            else mentorList.add(list.get(i));
        }
    }

    public void selectList(String type, String groupId) {
        if (userList.size() > 0) userList.clear();
        switch (type) {
            case S.CHILD:
                for (int i = 0; i < childList.size(); i++){
                    if (childList.get(i).getGroupId().equals(groupId)) userList.add(childList.get(i));
                }
                break;
            case S.MENTOR:
                for (int i = 0; i < mentorList.size(); i++){
                    if (mentorList.get(i).getGroupId().equals(groupId)) userList.add(mentorList.get(i));
                }
                break;
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvFullName, tvTitle, tvDate, tvGrade;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvGrade = itemView.findViewById(R.id.tvGrade);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(userList.get(getAdapterPosition()));
                }
            });
        }
    }
}
