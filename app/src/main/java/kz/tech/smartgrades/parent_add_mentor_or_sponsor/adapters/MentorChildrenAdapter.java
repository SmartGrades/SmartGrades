package kz.tech.smartgrades.parent_add_mentor_or_sponsor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.model.ModelMentorChildren;

public class MentorChildrenAdapter extends RecyclerView.Adapter<MentorChildrenAdapter.ViewHolder> {

    private Context context;
    private List<ModelMentorChildren> MentorChildren;

    public MentorChildrenAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ward_mentor_child_list, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(MentorChildren.get(position).getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
    }

    @Override
    public int getItemCount() {
        return MentorChildren.size();//9
    }


    public void updateData(List<ModelMentorChildren> mentorChildren) {
        this.MentorChildren = mentorChildren;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
        }
    }
}