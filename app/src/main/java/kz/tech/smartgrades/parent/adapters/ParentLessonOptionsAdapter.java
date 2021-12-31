package kz.tech.smartgrades.parent.adapters;

import android.annotation.SuppressLint;
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
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.model.ModelMentorList;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ParentLessonOptionsAdapter extends RecyclerView.Adapter<ParentLessonOptionsAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelMentorList> mentors;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(ModelMentorList mMentor);
    }

    public ParentLessonOptionsAdapter(ParentActivity activity) {
        this.activity = activity;
        this.mentors = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(ArrayList<ModelMentorList> mentors) {
        onClear(this.mentors);
        this.mentors = mentors;
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_remove_mentor, parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ParentLessonOptionsAdapter.ViewHolder holder, int position) {
        ModelMentorList m = mentors.get(position);

        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar))
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
        else holder.civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));

        String name = m.getFirstName() + " " + m.getLastName();
        if (!stringIsNullOrEmpty(name)){
            holder.tvName.setText(name);
            holder.tvLogin.setText(m.getLogin());
            holder.tvLogin.setVisibility(View.VISIBLE);
        }
        else {
            holder.tvName.setText(m.getLogin());
            holder.tvLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (mentors != null) return mentors.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civAvatar;
        TextView tvName;
        TextView tvLogin;
        ImageView ivRemove;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvLogin = itemView.findViewById(R.id.tvLogin);
            ivRemove = itemView.findViewById(R.id.ivRemove);

            ivRemove.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    onItemClickListener.onClick(mentors.get(position));
                }
            });
        }
    }
}
