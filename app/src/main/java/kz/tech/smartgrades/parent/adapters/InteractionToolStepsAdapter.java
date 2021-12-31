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

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.model.ModelInteractionToolSteps;

public class InteractionToolStepsAdapter extends RecyclerView.Adapter<InteractionToolStepsAdapter.ViewHolder> {

    protected Context context;
    private ArrayList<ModelInteractionToolSteps> arrayList;

    public InteractionToolStepsAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_interaction_tool_add_step, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTarget.setText(arrayList.get(position).getTarget());
        holder.tvStep.setText(arrayList.get(position).getDone() + "/" +arrayList.get(position).getCount());
        Picasso.get().load(arrayList.get(position).getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
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

    public void update(ArrayList<ModelInteractionToolSteps> arrayList) {
        onClear();
        this.arrayList.addAll(arrayList);
        notifyDataSetChanged();
    }

    public void update(ModelInteractionToolSteps item) {
        this.arrayList.add(item);
        notifyItemInserted(arrayList.size() -1);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTarget, tvStep;
        CircleImageView civAvatar;
        ViewHolder(@NonNull  View itemView) {
            super(itemView);
            tvTarget = itemView.findViewById(R.id.tvTarget);
            tvStep = itemView.findViewById(R.id.tvStep);
            civAvatar = itemView.findViewById(R.id.civAvatar);
        }
    }
}
