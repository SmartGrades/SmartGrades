package kz.tech.smartgrades.parent_add_child.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kz.tech.esparta.R;


public class ParentAvatarSelectAdapter extends RecyclerView.Adapter<ParentAvatarSelectAdapter.ViewHolder> {



    public interface OnItemClickListener {
        void onItemClick(ImageView img);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private Context context;
    private int[] images;
    private int selectPosition = 0;
    private boolean isFirst = false;

    public ParentAvatarSelectAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_parent_avatar_select, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivAvatar.setImageResource(images[position]);
        if (isFirst) {
            if (selectPosition == position) {
                holder.flBackground.setBackgroundColor(context.getResources().getColor(R.color.green_background));
            } else {
                holder.flBackground.setBackgroundColor(0);
            }
        }
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout flBackground;
        ImageView ivAvatar;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            flBackground = itemView.findViewById(R.id.flBackground);
            ivAvatar = itemView.findViewById(R.id.civAvatar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(ivAvatar);
                        selectPosition = getAdapterPosition();
                        isFirst = true;
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }
}
