package kz.tech.smartgrades.parent.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.parent.ParentActivity;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ParentChildesListAdapter extends RecyclerView.Adapter<ParentChildesListAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelChildData> childList;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(ModelChildData modelInterFormList, int p);
    }

    public ParentChildesListAdapter(ParentActivity activity) {
        this.activity = activity;
        this.childList = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<ModelChildData> childList) {
        onClear(this.childList);
        //this.childList.add(new ModelChildData());
        this.childList.addAll(childList);
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_child_with_avatar, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParentChildesListAdapter.ViewHolder holder, int position) {
        ModelChildData m = childList.get(position);
        if (stringIsNullOrEmpty(m.getId())) {
            holder.tvFocusedChild.setText(activity.getResources().getString(R.string.add));
            holder.civFocusedChild.setImageDrawable(activity.getResources().getDrawable(R.drawable.add_user_green));
        }
        else {
            holder.tvFocusedChild.setText(m.getFirstName());
            if(!stringIsNullOrEmpty(m.getAvatar()))
                Picasso.get().load(m.getAvatar()).fit().centerCrop().into(holder.civFocusedChild);
            if(m.Sponsorship != null)
                holder.ivSponsor.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
//        return Integer.MAX_VALUE;
        return childList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout clItem;
        ImageView ivSponsor;
        CircleImageView civFocusedChild;
        TextView tvFocusedChild;
        CardView cvFocusedChild;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            clItem = itemView.findViewById(R.id.clItem);
            ivSponsor = itemView.findViewById(R.id.ivSponsor);
            civFocusedChild = itemView.findViewById(R.id.civFocusedChild);
            tvFocusedChild = itemView.findViewById(R.id.tvFocusedChild);
            cvFocusedChild = itemView.findViewById(R.id.cvFocusedChild);

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position == 0) {
                        activity.onAddChild();
                    }
                    else {
                        onItemClickListener.onClick(childList.get(position), position);
                    }
                }
            });
        }
    }
}
