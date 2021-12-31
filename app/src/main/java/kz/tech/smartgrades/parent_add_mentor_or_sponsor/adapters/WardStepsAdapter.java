package kz.tech.smartgrades.parent_add_mentor_or_sponsor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;

public class WardStepsAdapter extends RecyclerView.Adapter<WardStepsAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(String select);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private Context context;
    private ArrayList<String> arrayList;

    public WardStepsAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
        this.arrayList.add("-5");
        this.arrayList.add("-4");
        this.arrayList.add("-3");
        this.arrayList.add("-2");
        this.arrayList.add("-1");
        this.arrayList.add("0");
        this.arrayList.add("+1");
        this.arrayList.add("+2");
        this.arrayList.add("+3");
        this.arrayList.add("+4");
        this.arrayList.add("+5");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ward_steps, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvSteps.setText(arrayList.get(position));
        if (arrayList.get(position).equals("-5")) holder.ivSteps.setImageResource(R.drawable.img_step_minus_five);
        else if (arrayList.get(position).equals("-4")) holder.ivSteps.setImageResource(R.drawable.img_step_minus_four);
        else if (arrayList.get(position).equals("-3")) holder.ivSteps.setImageResource(R.drawable.img_step_minus_three);
        else if (arrayList.get(position).equals("-2")) holder.ivSteps.setImageResource(R.drawable.img_step_minus_two);
        else if (arrayList.get(position).equals("-1")) holder.ivSteps.setImageResource(R.drawable.img_step_minus_one);
        else if (arrayList.get(position).equals("0")) holder.ivSteps.setImageResource(R.drawable.img_step_zero);
        else if (arrayList.get(position).equals("+1")) holder.ivSteps.setImageResource(R.drawable.img_step_plus_one);
        else if (arrayList.get(position).equals("+2")) holder.ivSteps.setImageResource(R.drawable.img_step_plus_two);
        else if (arrayList.get(position).equals("+3")) holder.ivSteps.setImageResource(R.drawable.img_step_plus_three);
        else if (arrayList.get(position).equals("+4")) holder.ivSteps.setImageResource(R.drawable.img_step_plus_four);
        else if (arrayList.get(position).equals("+5")) holder.ivSteps.setImageResource(R.drawable.img_step_plus_five);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivSteps;
        TextView tvSteps;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSteps = itemView.findViewById(R.id.ivSteps);
            tvSteps = itemView.findViewById(R.id.tvSteps);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        onItemClickListener.onItemClick(arrayList.get(position));
                    }
                }
            });
        }
    }

    private void onClear() {
        if (arrayList.size() > 0) {
            arrayList.clear();
        }
    }

    public void onUpdate(String interval) {
        onClear();
        try {
            String[] agr = interval.split(":");
            int start = Integer.parseInt(agr[0]);
            int end = Integer.parseInt(agr[1]);
            for (int i = 0; i > start; i--) {
                int lol = i -1;
                arrayList.add(0,String.valueOf(lol));
            }
            arrayList.add("0");
            for (int j = 0; j < end; j++) {
                int lol2 = j +1;
                arrayList.add("+" + String.valueOf(lol2));
            }
        } catch (Exception e) { }
    }
}
