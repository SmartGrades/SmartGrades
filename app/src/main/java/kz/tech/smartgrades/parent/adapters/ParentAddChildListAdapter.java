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
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.parent.ParentActivity;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ParentAddChildListAdapter extends RecyclerView.Adapter<ParentAddChildListAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelUser> childList;
    private ArrayList<ModelUser> filterList;

    private OnItemCLickListener onItemCLickListener;

    public void filter(String toString){
        filterList.clear();
        if(stringIsNullOrEmpty(toString)) filterList.addAll(childList);
        else for(ModelUser _class : childList)
            if((_class.getFirstName() + " " + _class.getLastName()).toLowerCase().contains(toString.toLowerCase()))
                filterList.add(_class);
        notifyDataSetChanged();
    }

    public interface OnItemCLickListener {
        void onClick(ModelUser mChild);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    public ParentAddChildListAdapter(ParentActivity activity) {
        this.activity = activity;
        childList = new ArrayList<>();
        filterList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    public void updateData(ArrayList<ModelUser> ChildList) {
        onClear(this.childList);
        onClear(this.filterList);
        if (!listIsNullOrEmpty(ChildList)) {
            childList.addAll(ChildList);
            filterList.addAll(ChildList);
            notifyDataSetChanged();
        }
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

        if(stringIsNullOrEmpty(m.getFirstName()) || stringIsNullOrEmpty(m.getLastName())){
            holder.tvChildFullName.setText(m.getLogin());
        }
        else holder.tvChildFullName.setText(m.getFirstName() + " " + m.getLastName());

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