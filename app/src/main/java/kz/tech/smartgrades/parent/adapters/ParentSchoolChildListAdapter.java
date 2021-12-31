package kz.tech.smartgrades.parent.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.parent.ParentActivity;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ParentSchoolChildListAdapter extends RecyclerView.Adapter<ParentSchoolChildListAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelUser> childList;

    private OnItemClickListener onItemClickListener;

    public void removeChild(ModelUser m) {
        if (!listIsNullOrEmpty(childList)) {
            childList.remove(m);
            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {
        void onClick(ModelUser m);
    }

    public ParentSchoolChildListAdapter(ParentActivity activity) {
        this.activity = activity;
        this.childList = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<ModelUser> childList) {
        onClear(this.childList);
        this.childList = childList;
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_parent_child_in_school, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParentSchoolChildListAdapter.ViewHolder holder, int position) {
        ModelUser m = childList.get(position);
        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar))
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerCrop().into(holder.civAvatar);
        if (!stringIsNullOrEmpty(m.getFirstName()))
            holder.tvName.setText(m.getFirstName());
    }

    @Override
    public int getItemCount() {
        if (childList != null) return childList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civAvatar;
        TextView tvName;
        Button btnRemove;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            btnRemove.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    onItemClickListener.onClick(childList.get(position));
                }
            });
        }
    }
}
