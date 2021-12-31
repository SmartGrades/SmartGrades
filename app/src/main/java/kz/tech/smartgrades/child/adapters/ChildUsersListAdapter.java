package kz.tech.smartgrades.child.adapters;

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
import kz.tech.smartgrades.mentor.models.ModelDefaultMessages;

public class ChildUsersListAdapter extends RecyclerView.Adapter<ChildUsersListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelDefaultMessages> arrayList;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(arrayList.get(position).getSourceFirstName());
        holder.tvValue.setText(arrayList.get(position).getNoCheckCount());
        holder.tvDate.setText(arrayList.get(position).getDate());
        Picasso.get().load(arrayList.get(position).getSourceAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void updateData(ArrayList<ModelDefaultMessages> arrayListe) {
        //notifyItemRangeChanged(0, arrayList.size());
        this.arrayList = arrayListe;
        notifyDataSetChanged();
    }

    public ChildUsersListAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ward_steps_detail_list, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvValue;
        TextView tvDate;
        CircleImageView civAvatar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvValue = itemView.findViewById(R.id.tvValue);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            civAvatar = itemView.findViewById(R.id.civAvatar);
        }
    }
}