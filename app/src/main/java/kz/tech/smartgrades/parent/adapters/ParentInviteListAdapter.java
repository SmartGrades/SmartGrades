package kz.tech.smartgrades.parent.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.root.login.LoginKey;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ParentInviteListAdapter extends RecyclerView.Adapter<ParentInviteListAdapter.ViewHolder> {

    private ParentActivity activity;
    private String PARENT_ID;
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

    public ParentInviteListAdapter(ParentActivity activity) {
        this.activity = activity;
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
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

        if (!m.getSourceId().equals(PARENT_ID)) {
            String avatar = m.getSourceAvatar();
            if (!stringIsNullOrEmpty(avatar))
                Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
            else holder.civAvatar
                    .setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));

            if (!stringIsNullOrEmpty(m.getSourceFirstName())) {
                holder.tvParentName
                        .setText(m.getSourceFirstName() + " " + m.getSourceLastName());
            }
            else holder.tvParentName
                    .setText(m.getSourceLogin());

            if (!stringIsNullOrEmpty(m.getSourceType()) && !(m.getType().equals("CreateLesson") || m.getType().equals("SmartGrades"))) {
                if (m.getSourceType().equals("Parent"))
                    holder.tvFrom.setText(activity.getResources().getString(R.string.ask_you_to_add_as_parent));
                else if (m.getSourceType().equals("Child"))
                    holder.tvFrom.setText(R.string.ask_you_to_add_as_child);
                else if (m.getSourceType().equals("Sponsor")) {
                    holder.tvFrom.setText(R.string.sponsorship_application);
                    holder.tvAccept.setText(R.string.familiarize);
                }
                else if (m.getSourceType().equals("Mentor")) {
                    holder.llLesson.setVisibility(View.VISIBLE);
                    holder.tvLesson.setText(m.getLessonName());
                    holder.tvFrom.setText(activity.getResources().getString(R.string.asks_to_add_to_your_child_pupils) + " " + m.getChildFirstName());
                    holder.tvParentName
                            .setText(m.getSourceFirstName() + " " + m.getSourceLastName());
                }
                else if (m.getSourceType().equals("School"))
                    holder.tvFrom.setText(activity.getResources().getString(R.string.You_are_asking_to_add_to_school) + " " + m.getChildFirstName());
            }
            if (m.getType().equals("SmartGrades") && !stringIsNullOrEmpty(m.getLessonName())) {
                holder.tvFrom.setText(activity.getResources().getString(R.string.ask_to_on_sg_in) + " " + m.getLessonName());
                holder.tvAccept.setText(R.string.turn_on);
            }
            else if (m.getType().equals("CreateLesson")) {
                holder.tvFrom.setText(R.string.asks_him_to_include_Smart_estimates_Now_he_has_no_source_of_income);
                holder.tvAccept.setText(R.string.create);
            }
            if (!stringIsNullOrEmpty(m.getChildAvatar())) {
                Picasso.get().load(m.getChildAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
            }
            String name = m.getSourceFirstName() + " " + m.getSourceLastName();
            if (!stringIsNullOrEmpty(name))
                holder.tvParentName.setText(name);
            else holder.tvParentName.setText(m.getSourceLogin());
        }
        else {
            holder.cvEnroll.setVisibility(View.GONE);

            String avatar = m.getTargetAvatar();
            if (!stringIsNullOrEmpty(avatar))
                Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside()
                        .into(holder.civAvatar);
            else holder.civAvatar
                    .setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));

            if (!stringIsNullOrEmpty(m.getTargetFirstName())) {
                holder.tvParentName
                        .setText(m.getTargetFirstName() + " " + m.getTargetLastName());
            }
            else holder.tvParentName.setText(m.getTargetLogin());

            if (!stringIsNullOrEmpty(m.getTargetType())) {
                if (m.getTargetType().equals("Parent") || m.getTargetType().equals("Child")) {
                    holder.tvFrom.setText(R.string.You_have_sent_an_application_to_be_added_to_your);
                }
                else if (m.getTargetType().equals("Sponsor")) {
                    holder.tvFrom.setText(R.string.sponsorship_application);
                }
                else if (m.getTargetType().equals("Mentor")) {
                    holder.llLesson.setVisibility(View.VISIBLE);
                    holder.tvLesson.setText(m.getLessonName());

                    if (!stringIsNullOrEmpty(m.getChildFirstName()))
                        holder.tvFrom.setText(activity.getResources().getString(R.string.You_have_submitted_an_application_to_be_added_as_a_teacher_for_your_child) + " "
                                + m.getChildFirstName());
                    else holder.tvFrom.setText(activity.getResources().getString(R.string.You_have_submitted_an_application_to_be_added_as_a_teacher_for_your_child) + " "
                            + m.getChildLogin());

                    String name = m.getTargetFirstName() + " " + m.getTargetLastName();
                    if (!stringIsNullOrEmpty(name))
                        holder.tvParentName.setText(name);
                    else holder.tvParentName.setText(m.getTargetLogin());
                }
                else if (m.getTargetType().equals("School"))
                    holder.tvFrom.setText(activity.getResources().getString(R.string.You_are_asking_to_add_to_school) + " " + m.getChildFirstName());
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
        TextView tvLesson;
        LinearLayout llLesson;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            llLesson = itemView.findViewById(R.id.llLesson);
            tvParentName = itemView.findViewById(R.id.tvParentName);
            tvFrom = itemView.findViewById(R.id.tvFrom);
            tvLesson = itemView.findViewById(R.id.tvLesson);
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
            cvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (!inviteList.get(position).getSourceId().equals(PARENT_ID))
                        onItemClickListener.onRejectClick(inviteList.get(position));
                    else onItemClickListener.onCancelClick(inviteList.get(position));
                }
            });
        }
    }

    public void cancel(ModelInterForm modelInterForm) {
        if (!listIsNullOrEmpty(inviteList) && inviteList.contains(modelInterForm)) {
            inviteList.remove(modelInterForm);
            notifyDataSetChanged();
        }
    }
}
