package kz.tech.smartgrades.parents_module.family_group.dialogs_page;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.authentication.fragments.registration_mentor.models.ModelMentor;

public class MentorListAdapter extends RecyclerView.Adapter<MentorListAdapter.ViewHolder>  {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(ModelMentor m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private Context context;
    private List<ModelMentor> list;
    public MentorListAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new ListViewMentorList(context));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelMentor m = list.get(position);
        if (m.getAvatar() != null) {
            Picasso.get().load(m.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
        }
        if (m.getLogin() != null) {
            holder.tvLogin.setText(m.getLogin());
        }
        if (m.getName() != null) {
            holder.tvFullName.setText(m.getName());
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvLogin, tvFullName;
        ImageView ivNext;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = (CircleImageView) itemView.findViewById((int) 400301);
            tvLogin = (TextView) itemView.findViewById((int) 400302);
            tvFullName = (TextView) itemView.findViewById((int) 400303);
            ivNext = (ImageView) itemView.findViewById((int) 400304);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(list.get(position));
                    }
                }
            });
        }
    }



    public void setData(List<ModelMentor> list) {
        if (this.list.size() > 0) { this.list.clear(); }
        this.list = list;
        notifyDataSetChanged();
    }
    public void removeData() {
        list.clear();
        notifyDataSetChanged();
    }
}
