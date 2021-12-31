package kz.tech.smartgrades.parent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.model.ModelInteractionToolGrade;


public class InteractionToolGradeAdapter extends RecyclerView.Adapter<InteractionToolGradeAdapter.ViewHolder> {

    protected Context context;
    private ArrayList<ModelInteractionToolGrade> arrayList;

    public InteractionToolGradeAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_interaction_tool_smart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String Title = "";
        String Type = arrayList.get(position).getType();
        if (Type.equals(context.getString(R.string.TemporaryСoinsType))) Title = context.getString(R.string.TemporaryСoins);
        else if (Type.equals(context.getString(R.string.GadgetTimeType))) Title = context.getString(R.string.GadgetTime);
        else if (Type.equals(context.getString(R.string.StepsType))) Title = context.getString(R.string.Steps);
        else if (Type.equals(context.getString(R.string.MoneyType))) Title = context.getString(R.string.Money);
        holder.tvTitle.setText(Title);
        holder.tvDescription.setText(arrayList.get(position).getDiscription());
        holder.tvParentName.setText(arrayList.get(position).getFullName());
        if (!arrayList.get(position).getStepTarget().isEmpty()){
            holder.tvStepTarget.setText(arrayList.get(position).getStepTarget());
            holder.tvStepTarget.setVisibility(View.VISIBLE);
        }
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

    public void update(ArrayList<ModelInteractionToolGrade> arrayList) {
        onClear();
        this.arrayList.addAll(arrayList);
        notifyDataSetChanged();
    }

    public void update(ModelInteractionToolGrade item) {
        this.arrayList.add(item);
        notifyItemInserted(arrayList.size() -1);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView civAvatar;
        TextView tvTitle, tvDescription, tvParentName, tvStepTarget;
        ViewHolder(@NonNull  View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvParentName = itemView.findViewById(R.id.tvParentName);
            tvStepTarget = itemView.findViewById(R.id.tvStepTarget);
        }
    }
}
