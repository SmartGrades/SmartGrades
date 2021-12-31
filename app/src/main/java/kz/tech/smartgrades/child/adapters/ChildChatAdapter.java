package kz.tech.smartgrades.child.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.parent.model.ModelChatMessages;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ChildChatAdapter extends RecyclerView.Adapter<ChildChatAdapter.ViewHolder> {

    private ChildActivity activity;

    private ArrayList<ModelChatMessages> messages;

    private OnItemCLickListener onItemCLickListener;
    public interface OnItemCLickListener {
        void onItemClick(ModelChatMessages m);
    }
    public void setOnItemClickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    public ChildChatAdapter(ChildActivity activity) {
        this.activity = activity;
        messages = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    public void updateData(ArrayList<ModelChatMessages> messages) {
        onClear(this.messages);
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChildChatAdapter.ViewHolder holder, int position) {
        ModelChatMessages m = messages.get(position);
        if (!stringIsNullOrEmpty(m.getMessageDate())) {
            holder.tvDate.setText(m.getMessageDate());
        } else {
            holder.tvDate.setText("");
        }
        if (!stringIsNullOrEmpty(m.getMessage())) {
            holder.tvMessage.setText(m.getMessage());
        } else {
            holder.tvMessage.setText("");
        }
        if (!stringIsNullOrEmpty(m.getFirstName())) {
            holder.tvName.setText(m.getFirstName() + " " + m.getLastName());
        } else {
            holder.tvName.setText("");
        }
        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) {
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerCrop().into(holder.civAvatar);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvMessage;
        TextView tvDate;
        CircleImageView civAvatar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDate = itemView.findViewById(R.id.tvDate);
            civAvatar = itemView.findViewById(R.id.civAvatar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemCLickListener == null) return;
                    onItemCLickListener.onItemClick(messages.get(getAdapterPosition()));
                }
            });
        }
    }
}