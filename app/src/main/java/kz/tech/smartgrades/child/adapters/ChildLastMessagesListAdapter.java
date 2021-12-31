package kz.tech.smartgrades.child.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.parent.model.ModelChatsLastMessages;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ChildLastMessagesListAdapter extends RecyclerView.Adapter<ChildLastMessagesListAdapter.ViewHolder> {

    private ChildActivity activity;
    private ArrayList<ModelChatsLastMessages> messagesList;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(ModelChatsLastMessages messagesList);
    }

    public ChildLastMessagesListAdapter(ChildActivity activity) {
        this.activity = activity;
        this.messagesList = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<ModelChatsLastMessages> messagesList) {
        onClear(this.messagesList);
        this.messagesList = messagesList;
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_last_message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChildLastMessagesListAdapter.ViewHolder holder, int position) {
        ModelChatsLastMessages m = messagesList.get(position);

        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
        holder.tvName.setText(m.getFirstName() + " " + m.getLastName());
        holder.tvTime.setText(m.getLastMessageDate());
        holder.tvMessage.setText(m.getLastMessage());

        if (!stringIsNullOrEmpty(m.getAnswer())) {
            if (m.getAnswer().equals("1")) {
                if (m.getNoCheckCount() == 0) {
                    holder.tvName.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
                    holder.tvTime.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
                    holder.tvMessage.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
                } else {
                    holder.tvName.setTextColor(activity.getResources().getColor(R.color.black));
                    holder.tvTime.setTextColor(activity.getResources().getColor(R.color.black));
                    holder.tvMessage.setTextColor(activity.getResources().getColor(R.color.black));
                }
            } else if (m.getAnswer().equals("0")) {
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.gray_disabled));
                holder.tvTime.setTextColor(activity.getResources().getColor(R.color.gray_disabled));
                holder.tvMessage.setTextColor(activity.getResources().getColor(R.color.gray_disabled));
                //holder.civAvatar.setAlpha(0.5f);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (messagesList != null) return messagesList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civAvatar;
        TextView tvName;
        TextView tvTime;
        TextView tvMessage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvMessage = itemView.findViewById(R.id.tvMessage);

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (!listIsNullOrEmpty(messagesList)) {
                        onItemClickListener.onClick(messagesList.get(position));
                    }
                }
            });
        }
    }
}
