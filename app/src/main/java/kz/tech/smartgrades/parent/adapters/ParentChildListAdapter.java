package kz.tech.smartgrades.parent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
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
import kz.tech.smartgrades.parent.model.ModelFamilyGroup;

public class ParentChildListAdapter extends RecyclerView.Adapter<ParentChildListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelFamilyGroup> arrayList;

    public ParentChildListAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_messenger_child_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelFamilyGroup model = arrayList.get(position);
//        Picasso.get().load(model.getAvatar()).fit().centerInside().into(holder.civAvatar);
//        holder.tvFullName.setText(model.getFirstName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void onClear() {
        if (arrayList.size() > 0) {
            arrayList.clear();
        }
    }

    public void update(List<ModelFamilyGroup> list) {
        onClear();
        this.arrayList.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvFullName, tvInteraction;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvInteraction = itemView.findViewById(R.id.tvInteraction);
        }
    }


}
