package kz.tech.smartgrades.childs_module.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.childs_module.ModelChildContracts;
import kz.tech.smartgrades.root.var_resources.VarID;

public class ChildMainContractsAdapter extends RecyclerView.Adapter<ChildMainContractsAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ModelChildContracts> arrayList;
    public ChildMainContractsAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new ItemListContracts(context));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelChildContracts model = arrayList.get(position);
        if (model.getAvatar() != null) {
          //  Picasso.with(context).load(model.getAvatar()).fit().centerInside().into(holder.ivPresent);
          //  Glide.with(context).load(model.getAvatar()).dontAnimate().into(holder.ivPresent);


           Picasso.get().load(model.getAvatar()).placeholder(R.drawable.img_default_avatar).into(holder.ivPresent);

          //  Picasso.with(context).load(model.getAvatar()).fit().centerInside().into(holder.ivPresent);


        }
        if (model.getDescription() != null) {
            holder.tvDescription.setText(model.getDescription());
        }
        if (model.getCountSteps() != null) {
           // List<ModelChangeStep> modelChangeStep = new ArrayList<>();
            boolean firstUnStep = false;
            String[] step = model.getCountSteps().split("/");
            int total = Integer.parseInt(step[0]);
            int[] someSteps = new int[total];
            int done = Integer.parseInt(step[1]);
            for (int i = 0; i < total; i++) {
                if (done > 0) {//  IF STEP OK
                    someSteps[i] = 1;
                    done--;
                 //   modelChangeStep.add(new ModelChangeStep("", "","", 1));

                } else if (done == 0 && !firstUnStep) {//  IF STEP NOT OK
                    someSteps[i] = 2;
                    firstUnStep = true;
                   // modelChangeStep.add(new ModelChangeStep("", "","", 2));
                }
                else if (done == 0) {//  IF STEP NOT OK AND NOT FIRST
                    someSteps[i] = 0;
                 //   modelChangeStep.add(new ModelChangeStep("", "","", 0));
                }
            }
            if (holder.rvSquare != null) {
                ChildMainStepsAdapter adapter = new ChildMainStepsAdapter(context, someSteps);
                holder.rvSquare.setAdapter(adapter);
            }
        }
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription;
        RecyclerView rvSquare;
        ImageView ivPresent;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = (TextView) itemView.findViewById(VarID.ID_TV_DESCRIPTION_CHILD_MAIN);
            rvSquare = (RecyclerView) itemView.findViewById(VarID.ID_RV_SQUARE_CHILD_MAIN);
            ivPresent = (ImageView) itemView.findViewById(VarID.ID_IV_PRESENT_CHILD_MAIN);
        }
    }
    public void setData(ArrayList<ModelChildContracts> arrayList) {
        this.arrayList.clear();
        this.arrayList = arrayList;
        notifyDataSetChanged();

    }
}
