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

public class ParentSelectedMentorListAdapter extends RecyclerView.Adapter<ParentSelectedMentorListAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelMentorList> mentorList;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onSelectClick(int p);
    }

    public ParentSelectedMentorListAdapter(ParentActivity activity) {
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
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_mentor_mini, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParentSelectedMentorListAdapter.ViewHolder holder, int position) {
        ModelMentorList model = mentorList.get(position); // error
//        Picasso.get().load(model.getAvatar()).fit().centerInside().into(holder.civAvatar);
//        holder.tvFocusedChild.setText("Имя Фамилия");
        String avatar = model.getAvatar();
        if(!stringIsNullOrEmpty(avatar)){
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
        }
        else {
            holder.civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
        }
        if(!stringIsNullOrEmpty(model.getFirstName()) || !stringIsNullOrEmpty(model.getLastName()))
            holder.tvName.setText(model.getFirstName() + " " + model.getLastName());
        else holder.tvName.setText(model.getLogin());
    }



    @Override
    public int getItemCount() {
        if (mentorList != null) return mentorList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civAvatar;
        TextView tvName;
        ImageView ivDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            ivDelete = itemView.findViewById(R.id.ivDelete);

            ivDelete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    onItemClickListener.onSelectClick(position);
                }
            });
        }
    }
}
