package kz.tech.smartgrades.parents_module.coins.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parents_module.coins.SquareImageView;
import kz.tech.smartgrades.parents_module.coins.models.ModelChangeStep;
import kz.tech.smartgrades.root.var_resources.VarID;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder>{
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(ModelChangeStep modelChangeStep);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private List<ModelChangeStep> arrayList;
    private Context context;
    public StepsAdapter(Context context, List<ModelChangeStep> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StepsAdapter.ViewHolder(new SquareImageView(context));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelChangeStep modelChangeStep = arrayList.get(position);

        int step = modelChangeStep.getStep();
        if (step == 1) {
            holder.imageView.setImageResource(R.mipmap.done);
            holder.imageView.setAlpha(1.0f);
        }
        if (step == 2) {
            holder.imageView.setAlpha(1.0f);
        }
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(VarID.ID_IV_SQUARE);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    String[] step = arrayList.get(position).getCountSteps().split("/");
                    int total = Integer.parseInt(step[0]);
                    int done = Integer.parseInt(step[1]);
                    //////////////
                    if (onItemClickListener != null) {
                        if (arrayList.get(position).getStep() == 2) {//  UnStep
                            if (total > done) {
                            done++;
                            int okDateChild = 2;
                            if (total == done) { okDateChild = 777; }
                            arrayList.get(position).setCountSteps(String.valueOf(total) + "/" + String.valueOf(done));
                            onItemClickListener.onItemClick(new ModelChangeStep(arrayList.get(position).getCountSteps(),
                                    arrayList.get(position).getIdChild(), arrayList.get(position).getIdContract(), okDateChild));
                        }
                        } else if (arrayList.get(position).getStep() == 1) {//  Step
                            if (done > 0) {
                                done--;
                            arrayList.get(position).setCountSteps(String.valueOf(total) + "/" + String.valueOf(done));
                            onItemClickListener.onItemClick(new ModelChangeStep(arrayList.get(position).getCountSteps(),
                                    arrayList.get(position).getIdChild(), arrayList.get(position).getIdContract(), arrayList.get(position).getStep()));
                        }
                        }
                    }

                }
            });

        }
    }
}
