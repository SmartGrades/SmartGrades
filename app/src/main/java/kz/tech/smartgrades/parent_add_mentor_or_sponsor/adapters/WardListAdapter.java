package kz.tech.smartgrades.parent_add_mentor_or_sponsor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
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
import kz.tech.smartgrades.mentor.models.ModelUserList;
import kz.tech.smartgrades.parent.model.ModelFamilyGroup;
import kz.tech.smartgrades.parent.model.ModelParentChildList;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class WardListAdapter extends RecyclerView.Adapter<WardListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position, ModelParentChildList item);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private Context context;
    private ArrayList<ModelParentChildList> userLists;
    private int pos;

    public WardListAdapter(Context context, ArrayList<ModelParentChildList> list, int pos, boolean isSponsored) {
        this.context = context;
        this.userLists = new ArrayList<>();
        this.pos = pos;
        if (isSponsored){
            for (int i = 0; i < list.size(); i++){
                if (list.get(i).getSponsorData() == null) userLists.add(list.get(i));
            }
        }
        else userLists.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ward_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivSelect.setImageResource(R.drawable.img_circle_purple_unchecked);

        ModelParentChildList model = userLists.get(position);

        String avatar = model.getChildAvatar();
        if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
        else holder.civAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.img_default_avatar));

        holder.tvName.setText(model.getChildFirstName() + " " + model.getChildLastName());

        if (position == pos) {
            holder.ivSelect.setImageResource(R.drawable.img_circle_purple_checked);
        }
    }

    @Override
    public int getItemCount() {
        return userLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civAvatar;
        TextView tvName;
        ImageView ivSelect;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            ivSelect = itemView.findViewById(R.id.ivPlusOrMinus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position, userLists.get(position));

                    }
                }
            });
        }
    }
}
