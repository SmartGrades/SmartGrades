package kz.tech.smartgrades.child.adapters;

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
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelLesson;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ChildParentsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ChildActivity activity;
    private String PARENT_ID;

    private ArrayList<ModelUser> ParentsList;
    private ArrayList<ModelUser> filterList;

    private OnItemCLickListener onItemCLickListener;

    public void filter(String toString){
        filterList.clear();
        if(stringIsNullOrEmpty(toString)) filterList.addAll(ParentsList);
        else for(ModelUser _class : ParentsList)
            if((_class.getFirstName() + " " + _class.getLastName()).toLowerCase().contains(toString.toLowerCase()))
                filterList.add(_class);
        notifyDataSetChanged();
    }

    public interface OnItemCLickListener {
        void onItemClick(ModelUser m);
    }
    public void setOnItemClickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    public ChildParentsListAdapter(ChildActivity activity) {
        this.activity = activity;
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
        ParentsList = new ArrayList<>();
        filterList = new ArrayList<>();
    }


    @Override
    public int getItemCount() {
        return filterList.size();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    public void updateData(ArrayList<ModelUser> parentsList) {
        onClear(ParentsList);
        onClear(filterList);
        if (!listIsNullOrEmpty(parentsList)){
            ParentsList.addAll(parentsList);
            filterList.addAll(parentsList);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_child_add, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ModelUser m = filterList.get(position);
        ((ViewHolder) holder).tvChildFullName.setText(m.getFirstName() + " " + m.getLastName());
        ((ViewHolder) holder).tvChildStatus.setText(m.getLogin());
        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(((ViewHolder) holder).civChildAvatar);
        else ((ViewHolder) holder).civChildAvatar.setBackgroundResource(R.drawable.img_default_avatar);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civChildAvatar;
        TextView tvChildFullName, tvChildStatus;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civChildAvatar = itemView.findViewById(R.id.civChildAvatar);
            tvChildFullName = itemView.findViewById(R.id.tvChildFullName);
            tvChildStatus = itemView.findViewById(R.id.tvChildStatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemCLickListener == null) return;
                    onItemCLickListener.onItemClick(filterList.get(getAdapterPosition()));
                }
            });
        }
    }
}