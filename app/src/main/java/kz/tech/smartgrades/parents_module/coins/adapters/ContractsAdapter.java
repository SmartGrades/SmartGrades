package kz.tech.smartgrades.parents_module.coins.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parents_module.coins.models.ModelChangeStep;
import kz.tech.smartgrades.root.models.ModelContract;
import kz.tech.smartgrades.root.models.ModelFamilyRoom;
import kz.tech.smartgrades.parents_module.contracts.ItemContracts;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;
import kz.tech.smartgrades.root.var_resources.VarID;

public class ContractsAdapter extends RecyclerView.Adapter<ContractsAdapter.ViewHolder>{
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(ModelContract modelContract);
        void onStepOkClick(int position, ModelChangeStep modelChangeStep);
        void onStepCancelClick(int position, ModelChangeStep modelChangeStep);
        void onMessageClick(String msg);
        void onFulfillsClick(int position, ModelContract modelContract);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private Context context;
    public List<ModelContract> list;
    private List<ModelFamilyRoom> familyRoomList;
    private Resources res;
    public ContractsAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new ItemContracts(context));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelContract modelContract = list.get(position);
        if (modelContract.getIdParent() != null) {
            for (int j = 0; j < familyRoomList.size(); j++) {
                /*if (familyRoomList.get(j).getZId().equals(modelContract.getIdParent())) {
                    if (familyRoomList.get(j).getAvatar() != null) {
                        Picasso.with(context).load(familyRoomList.get(j).getAvatar()).fit().centerInside().into(holder.civParent);
                    }
                }*/
            }

        }
        if (modelContract.getIdChild() != null) {
            for (int x = 0; x < familyRoomList.size(); x++) {
                /*if (familyRoomList.get(x).getZId().equals(modelContract.getIdChild())) {
                    if (familyRoomList.get(x).getAvatar() != null) {
                        Picasso.with(context).load(familyRoomList.get(x).getAvatar()).fit().centerInside().into(holder.civChild);
                    }
                }*/
            }
        }
        if (modelContract.getAvatar() != null) {
            Picasso.get().load(modelContract.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civPresentImage);
        }
        if (modelContract.getTitle() != null) {
            holder.tvContractTitle.setText(modelContract.getTitle());
        }

        if (modelContract.getCountSteps() != null) {
            List<ModelChangeStep> modelChangeStep = new ArrayList<>();
            boolean firstUnStep = false;
            String[] step = modelContract.getCountSteps().split("/");
            int total = Integer.parseInt(step[0]);
            int done = Integer.parseInt(step[1]);
            int totalDone = Integer.parseInt(step[1]);
            for (int i = 0; i < total; i++) {
                if (done > 0) {//  IF STEP OK
                    done--;
                    modelChangeStep.add(new ModelChangeStep(modelContract.getCountSteps(), modelContract.getIdChild(), modelContract.getIdContract(), 1));
                } else if (done == 0 && !firstUnStep) {//  IF STEP NOT OK
                    firstUnStep = true;
                    modelChangeStep.add(new ModelChangeStep(modelContract.getCountSteps(), modelContract.getIdChild(), modelContract.getIdContract(), 2));
                }
                else if (done == 0) {//  IF STEP NOT OK AND NOT FIRST
                    modelChangeStep.add(new ModelChangeStep(modelContract.getCountSteps(), modelContract.getIdChild(), modelContract.getIdContract(), 0));
                }
            }
            if (holder.recyclerView != null) {
                StepsAdapter stepsAdapter = new StepsAdapter(context, modelChangeStep);
                holder.recyclerView.setAdapter(stepsAdapter);
                stepsAdapter.setOnItemClickListener(new StepsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ModelChangeStep modelChangeStep) {
                        if (modelContract.getCheckChild().equals("yes") && modelContract.getCheckParent().equals("yes")) {
                            if (onItemClickListener != null) {
                                if (modelChangeStep.getStep() == 2 || modelChangeStep.getStep() == 777) {//  UnStep
                                onItemClickListener.onStepOkClick(position, modelChangeStep);
                                } else if (modelChangeStep.getStep() == 1) {//  Step
                                onItemClickListener.onStepCancelClick(position, modelChangeStep);
                                }
                            }
                        } else if (modelContract.getCheckChild().equals("not") && modelContract.getCheckParent().equals("not")) {
                            if (onItemClickListener != null) {
                                onItemClickListener.onMessageClick(res.getString(R.string.alert_check_child_parent));
                            }
                        } else if (modelContract.getCheckChild().equals("yes") && modelContract.getCheckParent().equals("not")) {
                            if (onItemClickListener != null) {
                                onItemClickListener.onMessageClick(res.getString(R.string.alert_check_parent));
                            }
                        } else if (modelContract.getCheckChild().equals("not") && modelContract.getCheckParent().equals("yes")) {
                            if (onItemClickListener != null) {
                                onItemClickListener.onMessageClick(res.getString(R.string.alert_check_child));
                            }
                        }
                    }
                });
            }
            ////////           MESSAGE: IF CHILD DONE ALL STEPS              ////////////////
            if (total == totalDone) {
                if (modelContract.getDateParent() != null) {
                    if (modelContract.getDateParent().equals("")) {
                        if (modelContract.getCheckParent() != null) {
                            if (!modelContract.getCheckParent().equals("")) {
                                holder.tvWarning.setText(res.getString(R.string.contract_execution));
                            }

                        }
                    }
                }
                if (modelContract.getDateChild() != null) {
                    holder.tvDateDoneChild.setText(modelContract.getDateChild());
                }
                if (modelContract.getDateParent() != null) {
                    if (!modelContract.getDateParent().equals("")) {
                        holder.tvDateDoneParent.setText(modelContract.getDateParent());
                    } else {
                        holder.tvFulfills.setVisibility(View.VISIBLE);
                        holder.tvFulfills.setText(res.getString(R.string.fulfills));
                        holder.civPresentImage.setBorderWidth(4);
                        holder.civPresentImage.setBorderColor(ColorsRGB.RED_ONE);
                    }
                }
            }
        }
    }
//Picasso.with(context).load(url).fit().centerInside().into(imageView);
    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView tvContractTitle, tvWarning, tvDateDoneChild, tvDateDoneParent, tvFulfills;
        CircleImageView civChild, civParent, civPresentImage;
        ImageView ivMoveLeft;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civChild = (CircleImageView) itemView.findViewById(VarID.ID_TV_CONTRACT_AVATAR_CHILD);
            civParent = (CircleImageView) itemView.findViewById(VarID.ID_TV_CONTRACT_AVATAR_PARENT);

            recyclerView = (RecyclerView) itemView.findViewById(VarID.ID_RV_ITEM_CONTRACTS);
            civPresentImage = (CircleImageView) itemView.findViewById(VarID.ID_CIV_CONTRACT_PRESENT_IMAGE);
            ivMoveLeft = (ImageView) itemView.findViewById(VarID.ID_TV_CONTRACT_MOVE);
            ivMoveLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        onItemClickListener.onItemClick(list.get(position));
                    }
                }
            });
            tvContractTitle = (TextView) itemView.findViewById(VarID.ID_TV_CONTRACT_TITLE);
            tvWarning = (TextView) itemView.findViewById(VarID.ID_TV_CONTRACT_WARNING);
            tvDateDoneChild = (TextView) itemView.findViewById(VarID.ID_TV_CONTRACT_DATE_CHILD);
            tvDateDoneParent = (TextView) itemView.findViewById(VarID.ID_TV_CONTRACT_DATE_PARENT);
            tvFulfills = (TextView) itemView.findViewById(VarID.ID_TV_CONTRACT_FULFILLS);
            tvFulfills.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        onItemClickListener.onFulfillsClick(position, list.get(position));
                    }
                }
            });
        }
    }//
    public void setData(List<ModelContract> list, List<ModelFamilyRoom> familyRoomList, Resources res) {
        this.res = res;
        this.list = list;
        this.familyRoomList = familyRoomList;
        notifyDataSetChanged();
    }
    public void updateFulfills(int position, String dateDoneParent) {
        list.get(position).setDateParent(dateDoneParent);
        notifyItemChanged(position);
    }
    public void updateSteps(int position, String countSteps, String dateDoneChild) {
        list.get(position).setCountSteps(countSteps);
        list.get(position).setDateChild(dateDoneChild);
        notifyItemChanged(position);
    }


}
