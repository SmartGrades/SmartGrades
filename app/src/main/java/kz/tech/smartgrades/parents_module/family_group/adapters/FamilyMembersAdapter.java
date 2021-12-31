package kz.tech.smartgrades.parents_module.family_group.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parents_module.family_group.list_view.FamilyMembersListView;
import kz.tech.smartgrades.root.models.ModelFamilyRoom;

public class FamilyMembersAdapter extends RecyclerView.Adapter<FamilyMembersAdapter.ViewHolder> {
    private Context context;
    private List<ModelFamilyRoom> list;
    private Resources res;
    public FamilyMembersAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new FamilyMembersListView(context));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelFamilyRoom model = list.get(position);
        /*if (model.getAvatar() != null) {
            Picasso.with(context).load(list.get(position).getAvatar()).fit().centerInside().into(holder.civAvatar);
        }
        if (model.getName() != null) {
            holder.tvName.setText(list.get(position).getName());
        }*/
        if (model.getLogin() != null) {
            switch (model.getLogin()) {
                case "Father": holder.tvStatus.setText(res.getString(R.string.father)); break;
                case "Mother": holder.tvStatus.setText(res.getString(R.string.mother)); break;
                case "Son": holder.tvStatus.setText(res.getString(R.string.son)); break;
                case "Daughter": holder.tvStatus.setText(res.getString(R.string.daughter)); break;
                case "FamilyMember": holder.tvStatus.setText(res.getString(R.string.family_member)); break;
            }

        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvName, tvStatus;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = (CircleImageView) itemView.findViewById((int)400201);
            tvName = (TextView) itemView.findViewById((int)400202);
            tvStatus = (TextView) itemView.findViewById((int)400203);
        }
    }
    public void setData(List<ModelFamilyRoom> familyRoomList, Resources res) {
        this.res = res;
        if (list.size() > 0) { list.clear(); }
        list = familyRoomList;
    }
}
