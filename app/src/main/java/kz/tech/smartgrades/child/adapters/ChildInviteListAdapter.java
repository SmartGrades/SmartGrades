package kz.tech.smartgrades.child.adapters;

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
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ChildInviteListAdapter extends RecyclerView.Adapter<ChildInviteListAdapter.ViewHolder> {

    private Context context;
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

    public ChildInviteListAdapter(Context context) {
        this.context = context;
        this.inviteList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_child_parent_invite, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String avatar = inviteList.get(position).getSourceAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).fit().centerInside()
                .into(holder.civAvatar);
        else holder.civAvatar
                .setImageDrawable(context.getResources().getDrawable(R.drawable.img_default_avatar));

        holder.tvParentName
                .setText(inviteList.get(position).getSourceFirstName() + " " + inviteList.get(position).getSourceLastName());

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
        CardView cvEnroll;
        CardView cvDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvParentName = itemView.findViewById(R.id.tvParentName);
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

    public void cancel(ModelInterFormList modelInterFormList) {
        inviteList.remove(modelInterFormList);
        notifyDataSetChanged();
    }
}
