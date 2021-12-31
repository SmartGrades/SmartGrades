package kz.tech.smartgrades.parent.adapters;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

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
import kz.tech.smartgrades.mentor.models.ModelMentorList;
import kz.tech.smartgrades.parent.ParentActivity;

public class ParentAddMentorListAdapter extends RecyclerView.Adapter<ParentAddMentorListAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelMentorList> mentorList;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onRemoveSelectedClick(int p);
    }

    public ParentAddMentorListAdapter(ParentActivity activity) {
        this.activity = activity;
        this.mentorList = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<ModelMentorList> mentorList) {
        onClear(this.mentorList);
        this.mentorList.addAll(mentorList);
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_added_mentor, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParentAddMentorListAdapter.ViewHolder holder, int position) {
        ModelMentorList model = mentorList.get(position);

        String avatar = model.getAvatar();
        if(avatar != null && !avatar.isEmpty()){
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
        }
        else {
            holder.civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
        }

        if(!stringIsNullOrEmpty(model.getFirstName()) || !stringIsNullOrEmpty(model.getLastName()))
            holder.tvMentorFullName.setText(model.getFirstName() + " " + model.getLastName());
        else holder.tvMentorFullName.setText(model.getLogin());

    }



    @Override
    public int getItemCount() {
        if (mentorList != null) return mentorList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civAvatar;
        TextView tvMentorFullName;
        ImageView ivRemove;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvMentorFullName = itemView.findViewById(R.id.tvMentorFullName);
            ivRemove = itemView.findViewById(R.id.ivRemove);

            ivRemove.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    onItemClickListener.onRemoveSelectedClick(position);
                    mentorList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
