package kz.tech.smartgrades.parent.adapters;

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
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.model.ModelUser;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ParentAddParentListAdapter extends RecyclerView.Adapter<ParentAddParentListAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelUser> parentList;
    private ArrayList<ModelUser> filterList;

    private OnItemCLickListener onItemCLickListener;

    public void filter(String toString){
        filterList.clear();
        if(stringIsNullOrEmpty(toString)) filterList.addAll(parentList);
        else for(ModelUser _class : parentList)
            if((_class.getFirstName() + " " + _class.getLastName()).toLowerCase().contains(toString.toLowerCase()))
                filterList.add(_class);
        notifyDataSetChanged();
    }

    public interface OnItemCLickListener {
        void onClick(ModelUser mParent);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    public ParentAddParentListAdapter(ParentActivity activity) {
        this.activity = activity;
        this.parentList = new ArrayList<>();
        this.filterList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    public void updateData(ArrayList<ModelUser> ParentList) {
        onClear(this.parentList);
        onClear(this.filterList);
        if (!listIsNullOrEmpty(ParentList)) {
            this.parentList = ParentList;
            filterList.addAll(ParentList);
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
        ModelUser m = filterList.get(position);

        String avatar = m.getAvatar();
        if(avatar != null && !avatar.isEmpty()){
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civChildAvatar);
        }

        holder.tvChildFullName.setText(m.getFirstName() + " " + m.getLastName());
        holder.tvChildStatus.setText(m.getLogin());
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