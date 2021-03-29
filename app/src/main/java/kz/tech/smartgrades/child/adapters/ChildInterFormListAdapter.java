package kz.tech.smartgrades.child.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.child.models.ModelChildMentorList;


public class ChildInterFormListAdapter extends RecyclerView.Adapter<ChildInterFormListAdapter.ViewHolder> {

    private Context context;
    private ModelChildData modelChildData;
    private ArrayList<ModelChildMentorList> ChildMentorList;
    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(ModelChildMentorList m);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public ChildInterFormListAdapter(Context context) {
        this.context = context;
        this.ChildMentorList = new ArrayList<>();
    }


    @Override
    public int getItemCount() {
        return ChildMentorList.size();
    }

    private void onClear() {
        if (ChildMentorList.size() > 0) ChildMentorList.clear();
    }

    public void updateData(ModelChildData modelChildData) {
        onClear();
//        this.ChildMentorList.addAll(modelChildData.getMentorList());
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*ModelChildData2 m = multiChatItemsList.get(position);
        Picasso.get().load(m.getAvatar()).fit().centerInside().into(holder.civAvatar);
        (holder).tvFullName.setText(m.getFullName());
        (holder).tvDate.setText(Convert.String2ShortTime(m.getDate()));
        (holder).tvTitle.setText(m.getLastMessage());*/
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_parent_chat_child, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvFullName, tvDate, tvBlueTrigger, tvTitle;
        RecyclerView recyclerView;
        ImageView ivLogoSponsor;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvBlueTrigger = itemView.findViewById(R.id.tvNewMessageCount);
            recyclerView = itemView.findViewById(R.id.rvGradesList);
            //ivLogoSponsor = itemView.findViewById(R.id.ivLogoSponsor);
            tvTitle = itemView.findViewById(R.id.tvTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onItemClickListener.onItemClick(ChildMentorList.get(position));
                }
            });
        }
    }
}
