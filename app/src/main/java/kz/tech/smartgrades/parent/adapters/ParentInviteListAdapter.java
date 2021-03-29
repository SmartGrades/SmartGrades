package kz.tech.smartgrades.parent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.models.ModelInterFormList;
import kz.tech.smartgrades.parent.ParentActivity;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ParentInviteListAdapter extends RecyclerView.Adapter<ParentInviteListAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelInterFormList> inviteList;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onEnrollClick(ModelInterFormList modelInterFormList);
        void onCancelClick(ModelInterFormList modelInterFormList);
    }

    public void updateData(ArrayList<ModelInterFormList> inviteList) {
        onClear(this.inviteList);
        this.inviteList = inviteList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ParentInviteListAdapter(ParentActivity activity) {
        this.activity = activity;
        this.inviteList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_parent_parent_invite, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelInterFormList m = inviteList.get(position);

        String avatar = m.getSourceAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).fit().centerInside()
                .into(holder.civAvatar);
        else holder.civAvatar
                .setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));

        holder.tvParentName
                .setText(m.getSourceFirstName() + " " + m.getSourceLastName());

        if (m.getSourceType().equals("Parent")) holder.tvFrom.setText(activity.getResources().getString(R.string.ask_you_to_add_as_parent));
        else if (m.getSourceType().equals("Child")) holder.tvFrom.setText(R.string.ask_you_to_add_as_child);
        else if (m.getSourceType().equals("Sponsor")) {
            holder.tvFrom.setText(R.string.sponsorship_application);
            holder.tvAccept.setText(R.string.familiarize);
        }
        else if (m.getSourceType().equals("Mentor")) holder.tvFrom.setText("Mentor");
        else if (m.getSourceType().equals("School")) holder.tvFrom.setText("School");

    }

    @Override
    public int getItemCount() {
        return inviteList.size();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvParentName;
        TextView tvFrom;
        CardView cvEnroll;
        CardView cvDelete;
        TextView tvAccept;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvParentName = itemView.findViewById(R.id.tvParentName);
            tvFrom = itemView.findViewById(R.id.tvFrom);
            tvAccept = itemView.findViewById(R.id.tvAccept);
            cvEnroll = itemView.findViewById(R.id.cvEnroll);
            cvEnroll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    onItemClickListener.onEnrollClick(inviteList.get(position));
                }
            });

            cvDelete = itemView.findViewById(R.id.cvDelete);
            cvDelete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    onItemClickListener.onCancelClick(inviteList.get(position));
                }
            });
        }
    }

//    public void cancel(ModelInterFormList modelInterFormList) {
//        inviteList.remove(modelInterFormList);
//        if (listIsNullOrEmpty(inviteList)) activity.hideInvite();
//        notifyDataSetChanged();
//    }
}
