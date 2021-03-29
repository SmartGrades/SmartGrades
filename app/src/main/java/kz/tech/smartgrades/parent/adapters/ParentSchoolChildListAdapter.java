package kz.tech.smartgrades.parent.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelUserData;
import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.mentor.models.ModelMentorList;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.school.models.ModelSchoolData;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ParentSchoolChildListAdapter extends RecyclerView.Adapter<ParentSchoolChildListAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelUserData> childList;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(ModelChildData modelMentor);
    }

    public ParentSchoolChildListAdapter(ParentActivity activity) {
        this.activity = activity;
        this.childList = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<ModelUserData> childList) {
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
        ModelUserData m = childList.get(position);
        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar))
            Picasso.get().load(avatar).fit().centerCrop().into(holder.civAvatar);
        if (!stringIsNullOrEmpty(m.getFirstName()) && !stringIsNullOrEmpty(m.getLastName()))
            holder.tvName.setText(m.getFirstName() + " " + m.getLastName());
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

//            itemView.setOnClickListener( new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    onItemClickListener.onClick(childList.get(position));
//                }
//            });
        }
    }
}
