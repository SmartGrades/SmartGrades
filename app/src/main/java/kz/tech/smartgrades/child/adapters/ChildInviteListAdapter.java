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
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.root.login.LoginKey;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ChildInviteListAdapter extends RecyclerView.Adapter<ChildInviteListAdapter.ViewHolder> {

    private ChildActivity activity;
    private String CHILD_ID;
    private ArrayList<ModelInterForm> inviteList;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onEnrollClick(ModelInterForm modelInterForm);
        void onRejectClick(ModelInterForm modelInterForm);
        void onCancelClick(ModelInterForm modelInterForm);
    }

    public void updateData(ArrayList<ModelInterForm> inviteList) {
        onClear(this.inviteList);
        this.inviteList = inviteList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ChildInviteListAdapter(ChildActivity activity) {
        this.activity = activity;
        CHILD_ID = activity.login.loadUserDate(LoginKey.ID);
        this.inviteList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_income_invite, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelInterForm m = inviteList.get(position);
        if (!m.getSourceId().equals(CHILD_ID)) {
            String avatar = m.getSourceAvatar();
            if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar)
                    .fit().centerInside().into(holder.civAvatar);
            else holder.civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));

            String name = m.getSourceFirstName();
            if (!stringIsNullOrEmpty(name))
                holder.tvParentName.setText(name);
            else holder.tvParentName.setText(m.getSourceLogin());
            holder.tvFrom.setText(activity.getResources().getString(R.string.ask_you_to_add_as_parent));
        }
        else {
            holder.cvEnroll.setVisibility(View.GONE);

            String avatar = m.getTargetAvatar();
            if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar)
                    .fit().centerInside().into(holder.civAvatar);
            else holder.civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));

            String name = m.getTargetFirstName();
            if (!stringIsNullOrEmpty(name))
                holder.tvParentName.setText(name);
            else holder.tvParentName.setText(m.getTargetLogin());

            if (m.getType().equals("SmartGrades") && !stringIsNullOrEmpty(m.getLessonName())) {
                holder.tvFrom.setText(activity.getResources().getString(R.string.You_asked_to_enable_Smart_Grades_in_a_subject) + " " + m.getLessonName());
            } else if (m.getType().equals("CreateLesson")) {
                holder.tvFrom.setText(R.string.You_are_asking_to_create_an_item_with_Smart_Grades);
            } else if (m.getType().equals("ChildToParent")) {
                holder.tvFrom.setText(R.string.You_child);
            }
        }
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
            cvDelete = itemView.findViewById(R.id.cvDelete);

            cvEnroll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    onItemClickListener.onEnrollClick(inviteList.get(position));
                }
            });

            cvDelete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (!inviteList.get(position).getSourceId().equals(CHILD_ID)) onItemClickListener.onRejectClick(inviteList.get(position));
                    else onItemClickListener.onCancelClick(inviteList.get(position));
                }
            });
        }
    }

    public void cancel(ModelInterForm modelInterFormList) {
        if (!listIsNullOrEmpty(inviteList) && inviteList.contains(modelInterFormList)) {
            inviteList.remove(modelInterFormList);
            notifyDataSetChanged();
        }
    }
}
