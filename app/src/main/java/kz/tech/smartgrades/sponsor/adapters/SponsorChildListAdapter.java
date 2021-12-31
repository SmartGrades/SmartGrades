package kz.tech.smartgrades.sponsor.adapters;

import android.annotation.SuppressLint;
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
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.sponsor.models.ModelChildrenListForSponsorship;
import kz.tech.smartgrades.sponsor.SponsorActivity;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class SponsorChildListAdapter extends RecyclerView.Adapter<SponsorChildListAdapter.ViewHolder> {

    private SponsorActivity activity;
    private ArrayList<ModelChildrenListForSponsorship> childrenList;
    private ArrayList<ModelChildrenListForSponsorship> filterList;

    private OnItemCLickListener onItemCLickListener;

    public void filter(String toString){
        filterList.clear();
        if(stringIsNullOrEmpty(toString)) filterList.addAll(childrenList);
        else for(ModelChildrenListForSponsorship _class : childrenList)
            if((_class.getFirstName() + " " + _class.getLastName()).toLowerCase().contains(toString.toLowerCase()))
                filterList.add(_class);
        notifyDataSetChanged();
    }

    public void onRandom(){
        if(onItemCLickListener==null) return;
        if (!listIsNullOrEmpty(childrenList)) {
            onItemCLickListener.onClick(childrenList.get(new Random().nextInt(childrenList.size())));
        }
    }
    public interface OnItemCLickListener {
        void onClick(ModelChildrenListForSponsorship mChild);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    public SponsorChildListAdapter(SponsorActivity activity) {
        this.activity = activity;
        childrenList = new ArrayList<>();
        filterList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(ArrayList<ModelChildrenListForSponsorship> ChildList) {
        onClear(this.childrenList);
        onClear(this.filterList);
        if (!listIsNullOrEmpty(ChildList)) {
            childrenList.addAll(ChildList);
            filterList.addAll(ChildList);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_child_add, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelChildrenListForSponsorship m = filterList.get(position);
        if(!stringIsNullOrEmpty(m.getAvatar()))
            Picasso.get().load(m.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerCrop().into(holder.civChildAvatar);
        String name = m.getFirstName() + " " + m.getLastName();
        if (!stringIsNullOrEmpty(name))
            holder.tvChildFullName.setText(name);
        else holder.tvChildFullName.setText(m.getLogin());
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
                    onItemCLickListener.onClick(filterList.get(getAdapterPosition()));
                }
            });
        }
    }
}