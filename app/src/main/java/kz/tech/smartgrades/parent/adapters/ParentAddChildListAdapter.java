package kz.tech.smartgrades.parent.adapters;

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
import kz.tech.smartgrades.auth.models.ModelUserData;
import kz.tech.smartgrades.parent.ParentActivity;

public class ParentAddChildListAdapter extends RecyclerView.Adapter<ParentAddChildListAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelUserData> childList;

    private OnItemCLickListener onItemCLickListener;

    public interface OnItemCLickListener {
        void onClick(ModelUserData mChild);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    public ParentAddChildListAdapter(ParentActivity activity) {
        this.activity = activity;
        this.childList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return childList.size();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    public void updateData(ArrayList<ModelUserData> childList) {
        onClear(this.childList);
        this.childList = childList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_child_add, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelUserData m = childList.get(position);

        String avatar = m.getAvatar();
        if(avatar != null && !avatar.isEmpty()){
            Picasso.get().load(avatar).fit().centerInside().into(holder.civChildAvatar);
        }

        holder.tvChildFullName.setText(m.getFirstName() + " " + m.getLastName());
        holder.tvChildStatus.setText(m.getLogin());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvChildFullName;
        TextView tvChildStatus;
        CircleImageView civChildAvatar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvChildFullName = itemView.findViewById(R.id.tvChildFullName);
            tvChildStatus = itemView.findViewById(R.id.tvChildStatus);
            civChildAvatar =itemView.findViewById(R.id.civChildAvatar);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener==null) return;
                    onItemCLickListener.onClick(childList.get(getAdapterPosition()));
                }
            });
        }
    }
}