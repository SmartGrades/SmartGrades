package kz.tech.smartgrades.mentor.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelUser;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class ChildListForAddedAdapter extends RecyclerView.Adapter<ChildListForAddedAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelUser> childrenList;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ModelUser m);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ChildListForAddedAdapter(Context context) {
        this.context = context;
        this.childrenList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mentor_child_for_added_list, parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelUser m = childrenList.get(position);

        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).fit().centerInside().into(holder.civAvatar);
        else(holder.civAvatar).setImageDrawable(context.getResources().getDrawable(R.drawable.img_default_avatar));

        holder.tvFullName.setText(m.getFirstName() + " " + m.getLastName());
        holder.tvTitle.setText(m.getLogin());
    }

    @Override
    public int getItemCount() {
        return childrenList.size();
    }

    private void onClear() {
        if (childrenList.size() > 0) childrenList.clear();
    }

    public void updateData(ArrayList<ModelUser> list) {
        if (listIsNullOrEmpty(list)) return;
        onClear();
        childrenList.addAll(list);
    }

    public void selectList() {
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvFullName, tvTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(childrenList.get(getAdapterPosition()));
                }
            });
        }
    }
}
