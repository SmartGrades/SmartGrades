package kz.tech.smartgrades.parent.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.models.ModelMentorList;
import kz.tech.smartgrades.parent.ParentActivity;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ParentMentorListAdapter extends RecyclerView.Adapter<ParentMentorListAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelMentorList> mentorList;

    private OnItemClickListener onItemClickListener;

    private ArrayList<ModelMentorList> SELECTED = new ArrayList<>();

    public interface OnItemClickListener {
        void onClick(ModelMentorList modelMentor);
    }

    public ParentMentorListAdapter(ParentActivity activity) {
        this.activity = activity;
        this.mentorList = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<ModelMentorList> mentorList) {
        onClear(this.mentorList);
        this.mentorList = mentorList;
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_parent_mentor_add, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParentMentorListAdapter.ViewHolder holder, int position) {
        ModelMentorList m = mentorList.get(position);
        if(SELECTED.contains(m)){
            holder.clMentorItem.setBackgroundColor(activity.getResources().getColor(R.color.green_background));
            holder.clMentorItem.setClickable(false);
        }
        else {
            holder.clMentorItem.setBackgroundColor(activity.getResources().getColor(R.color.white));
            holder.clMentorItem.setClickable(true);
        }
        String avatar = m.getAvatar();
        if(!stringIsNullOrEmpty(avatar))
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civMentorAvatar);

        if(stringIsNullOrEmpty(m.getFirstName()) || stringIsNullOrEmpty(m.getLastName())) holder.tvMentorFullName.setText(m.getLogin());
        else holder.tvMentorFullName.setText(m.getFirstName() + " " + m.getLastName());

        if(!stringIsNullOrEmpty(m.getLogin()))
            holder.tvMentorLogin.setText(m.getLogin());
        else holder.tvMentorLogin.setText("");
    }

    @Override
    public int getItemCount() {
        return mentorList.size();
    }

    public void checkToSelected(ArrayList<ModelMentorList> selectedMentorList) {
        onClear(SELECTED);
        for (ModelMentorList m : selectedMentorList) {
            if (mentorList.contains(m)) {
                SELECTED.add(m);
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout clMentorItem;
        CircleImageView civMentorAvatar;
        TextView tvMentorFullName;
        TextView tvMentorLogin;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            clMentorItem = itemView.findViewById(R.id.clMentorItem);
            civMentorAvatar = itemView.findViewById(R.id.civMentorAvatar);
            tvMentorFullName = itemView.findViewById(R.id.tvMentorFullName);
            tvMentorLogin = itemView.findViewById(R.id.tvMentorLogin);

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    onItemClickListener.onClick(mentorList.get(position));
                }
            });
        }
    }
}
