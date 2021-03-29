package kz.tech.smartgrades.child.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.parent.model.ModelChatsLastMessages;

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
        holder.tvName.setText(messagesList.get(position).getFirstName() + " " + messagesList.get(position).getLastName());
        holder.tvTime.setText(messagesList.get(position).getLastMessageDate());
        holder.tvMessage.setText(messagesList.get(position).getLastMessage());
    }

    @Override
    public int getItemCount() {
        if (messagesList != null) return messagesList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAvatar;
        TextView tvName;
        TextView tvTime;
        TextView tvMessage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvMessage = itemView.findViewById(R.id.tvMessage);

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    onItemClickListener.onClick(messagesList.get(position));
                }
            });
        }
    }
}
