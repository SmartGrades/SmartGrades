package kz.tech.smartgrades.child.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.child.models.ModelComplaintDataStudent;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ChildComplaintSelectedStudentAdapter extends RecyclerView.Adapter<ChildComplaintSelectedStudentAdapter.ViewHolder> {

    private ChildActivity activity;
    private ArrayList<ModelComplaintDataStudent> list;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(String type, View itemView);
    }

    public ChildComplaintSelectedStudentAdapter(ChildActivity activity) {
        this.activity = activity;
        this.list = new ArrayList<>();
    }

    public void setOnItemLongClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ModelComplaintDataStudent item) {
        list.add(item);
        notifyDataSetChanged();
    }

    public void clearList() {
        list = new ArrayList<>();
    }

    public ArrayList<ModelComplaintDataStudent> getList() {
        return list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_complaint_to, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChildComplaintSelectedStudentAdapter.ViewHolder holder, int position) {
        ModelComplaintDataStudent m = list.get(position);
        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
        if (!stringIsNullOrEmpty(m.getFirstName()) && !stringIsNullOrEmpty(m.getLastName())) {
            holder.tvName.setText(m.getFirstName() + " " + m.getLastName());
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        CircleImageView civAvatar;
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
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
