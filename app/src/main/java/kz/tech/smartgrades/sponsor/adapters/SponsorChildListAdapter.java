package kz.tech.smartgrades.sponsor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.sponsor.models.ModelChildrenListForSponsorship;
import kz.tech.smartgrades.sponsor.SponsorActivity;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class SponsorChildListAdapter extends RecyclerView.Adapter<SponsorChildListAdapter.ViewHolder> {

    private SponsorActivity activity;
    private ArrayList<ModelChildrenListForSponsorship> childrenList;

    private OnItemCLickListener onItemCLickListener;

    public void onRandom(){
        if(onItemCLickListener==null) return;
        onItemCLickListener.onClick(childrenList.get(new Random().nextInt(childrenList.size())));
    }
    public interface OnItemCLickListener {
        void onClick(ModelChildrenListForSponsorship mChild);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    public SponsorChildListAdapter(SponsorActivity activity) {
        this.activity = activity;
        this.childrenList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return childrenList.size();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    public void updateData(ArrayList<ModelChildrenListForSponsorship> childList) {
        onClear(this.childrenList);
        this.childrenList.addAll(childList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_child_add, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelChildrenListForSponsorship m = childrenList.get(position);
        if(!stringIsNullOrEmpty(m.getAvatar()))
            Picasso.get().load(m.getAvatar()).fit().centerCrop().into(holder.civChildAvatar);
        holder.tvChildFullName.setText(m.getFirstName() + " " + m.getLastName());
        holder.tvChildStatus.setText(m.getStateName());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvChildFullName;
        TextView tvChildStatus;
        CircleImageView civChildAvatar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvChildFullName = itemView.findViewById(R.id.tvChildFullName);
            tvChildStatus = itemView.findViewById(R.id.tvChildStatus);
            civChildAvatar =itemView.findViewById(R.id.civChildAvatar);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener==null) return;
                    onItemCLickListener.onClick(childrenList.get(getAdapterPosition()));
                }
            });
        }
    }
}