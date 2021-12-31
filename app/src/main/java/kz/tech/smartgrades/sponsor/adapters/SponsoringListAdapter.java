package kz.tech.smartgrades.sponsor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.parent.model.ModelLessonsWithSmartGrades;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import kz.tech.smartgrades.sponsor.models.ModelSponsorChildrenList;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class SponsoringListAdapter extends RecyclerView.Adapter<SponsoringListAdapter.ViewHolder> {

    private SponsorActivity activity;
    private ArrayList<ModelSponsorChildrenList> childrenLists;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(ModelSponsorChildrenList child);
    }

    public SponsoringListAdapter(SponsorActivity activity) {
        this.activity = activity;
        this.childrenLists = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<ModelSponsorChildrenList> childrenLists) {
        onClear(this.childrenLists);
        this.childrenLists = childrenLists;
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_sponsoring_lesson, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SponsoringListAdapter.ViewHolder holder, int position) {
        ModelSponsorChildrenList m = childrenLists.get(position);
        if(!stringIsNullOrEmpty(m.getAvatar()))
            Picasso.get().load(m.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerCrop().into(holder.civAvatar);
        holder.tvLessonName.setText(m.getFirstName() + " " + m.getLastName());
        if (m.getSponsorship() != null) {
            if (!stringIsNullOrEmpty(m.getSponsorship().getCurrentTotalReward()))
                holder.tvMoney.setText(m.getSponsorship().getCurrentTotalReward());
            holder.tvProgress.setText(m.getSponsorship().getProgress() + "%");
            holder.pbChildMoney.setProgress(m.getSponsorship().getProgress());
            holder.tvDays.setText(m.getSponsorship().getCurrentDay() + "/" + m.getSponsorship().getTotalDays() + " " + activity.getResources().getString(R.string.days));
            holder.tvWeek.setText(activity.getResources().getString(R.string.week) + " " + m.getSponsorship().getCurrentWeek());
        }
    }

    @Override
    public int getItemCount() {
        return childrenLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLessonName;
        ImageView civAvatar;
        TextView tvMoney;
        TextView tvProgress;
        ProgressBar pbChildMoney;

        TextView tvWeek;
        TextView tvDays;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvMoney = itemView.findViewById(R.id.tvMoney);
            tvWeek = itemView.findViewById(R.id.tvWeek);
            tvProgress = itemView.findViewById(R.id.tvProgress);
            pbChildMoney = itemView.findViewById(R.id.pbChildMoney);
            tvDays = itemView.findViewById(R.id.tvDays);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    onItemClickListener.onClick(childrenLists.get(position));
                }
            });
        }
    }
}
