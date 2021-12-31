package kz.tech.smartgrades.family_room.fragments.family_member_list.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.models.ModelFamilyRoom;
import kz.tech.smartgrades.root.var_resources.VarID;

public class FamilyRoomAdapter extends RecyclerView.Adapter<FamilyRoomAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(ModelFamilyRoom model);
    }
    private Context context;
    private ArrayList<ModelFamilyRoom> arrayList;
    public FamilyRoomAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();

    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new FamilyMemberView(context));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelFamilyRoom modelFamilyRoom = arrayList.get(position);
        /*if (modelFamilyRoom.getAvatar() != null && !modelFamilyRoom.getAvatar().equals("")) {
            Picasso.with(context).load(modelFamilyRoom.getAvatar()).fit().centerInside().into(holder.civAvatar);
        } else {
            holder.civAvatar.setImageResource(R.mipmap.avatar);
        }
        if (modelFamilyRoom.getLogin() != null) {
            holder.tvStatus.setText(modelFamilyRoom.getLogin());
        }
        if (modelFamilyRoom.getName() != null) {
            holder.tvName.setText(modelFamilyRoom.getName());
        }*/
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setData(java.util.List<ModelFamilyRoom> list) {
        if (arrayList.size() > 0) {
            arrayList.clear();
        }
        arrayList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvStatus, tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = (CircleImageView) itemView.findViewById(VarID.ID_CIV_FAMILY_AVATAR);
            tvStatus = (TextView) itemView.findViewById(VarID.ID_TV_FAMILY_STATUS);
            tvName = (TextView) itemView.findViewById(VarID.ID_TV_FAMILY_NAME);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    /*if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(new ModelFamilyGroup(
                                arrayList.get(position).getAvatar(),
                                arrayList.get(position).getName(),
                                arrayList.get(position).getPin(),
                                arrayList.get(position).getLogin(),
                                arrayList.get(position).getZId()));
                    }*/
                }
            });
        }
    }
}
