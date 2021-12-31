package kz.tech.smartgrades.parent.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.parent.ParentActivity;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ParentChildesListAdapter extends RecyclerView.Adapter<ParentChildesListAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelChildData> childList;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onClick(ModelChildData modelInterFormList, int p);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ParentChildesListAdapter(ParentActivity activity) {
        this.activity = activity;
        this.childList = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(ArrayList<ModelChildData> childList) {
        onClear(this.childList);
        //this.childList.add(new ModelChildData());
        this.childList.addAll(childList);
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_child_with_avatar, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParentChildesListAdapter.ViewHolder holder, int position) {
        ModelChildData m = childList.get(position);
        if (stringIsNullOrEmpty(m.getId())) {
            holder.tvFocusedChild.setText(activity.getResources().getString(R.string.add));
            holder.cvFocusedChild.setVisibility(View.INVISIBLE);
            holder.ivAdd.setVisibility(View.VISIBLE);
        }
        else {
            holder.cvFocusedChild.setVisibility(View.VISIBLE);
            holder.ivAdd.setVisibility(View.GONE);
            if (!stringIsNullOrEmpty(m.getFirstName()))
                holder.tvFocusedChild.setText(m.getFirstName());
            else holder.tvFocusedChild.setText(m.getLogin());
            if(!stringIsNullOrEmpty(m.getAvatar()))
                Picasso.get().load(m.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerCrop().into(holder.civFocusedChild);
            if(m.getSponsorship() != null)
                holder.ivSponsor.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
//        return Integer.MAX_VALUE;
        return childList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout clItem;
        ImageView ivSponsor;
        ImageView ivAdd;
        CircleImageView civFocusedChild;
        TextView tvFocusedChild;
        CardView cvFocusedChild;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            clItem = itemView.findViewById(R.id.clItem);
            ivSponsor = itemView.findViewById(R.id.ivSponsor);
            ivAdd = itemView.findViewById(R.id.ivAdd);
            civFocusedChild = itemView.findViewById(R.id.civFocusedChild);
            tvFocusedChild = itemView.findViewById(R.id.tvFocusedChild);
            cvFocusedChild = itemView.findViewById(R.id.cvFocusedChild);

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position == 0) {
                        Dialog dialog = new Dialog(activity, R.style.CustomDialog2);
                        dialog.setCanceledOnTouchOutside(true);
                        View v = activity.getLayoutInflater().inflate(R.layout.activity_parent_add_child, null, false);
                        dialog.show();
                        ImageView ivBack = v.findViewById(R.id.ivBack);
                        Button btnAddChild = v.findViewById(R.id.btnAddChild);
                        Button btnSearchChild = v.findViewById(R.id.btnSearchChild);
                        ivBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        btnAddChild.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                activity.onCreateChild();
                            }
                        });
                        btnSearchChild.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                activity.onAddChild();
                                dialog.dismiss();
                            }
                        });
                        dialog.setContentView(v);
                    }
                    else {
                        onItemClickListener.onClick(childList.get(position), position);
                    }
                }
            });
        }
    }
}
