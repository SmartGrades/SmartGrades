package kz.tech.smartgrades.parents_module.cabinet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parents_module.cabinet.models.ModelChatListChild;

public class ChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_1 = 1;
    private static final int TYPE_2 = 2;
    private static final int TYPE_3 = 3;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {

    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private Context context;

    private ArrayList<ModelChatListChild> arrayList;

    public ChildAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_1: return new ViewHolder1(LayoutInflater.from(context).inflate(R.layout.item_chat_child_1, parent, false));
            case TYPE_2: return new ViewHolder1(LayoutInflater.from(context).inflate(R.layout.item_chat_child_2, parent, false));
            default: return new ViewHolder1(LayoutInflater.from(context).inflate(R.layout.item_empty, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (arrayList.get(position).getType()) {
            case TYPE_1: ((ViewHolder1)holder).bind(arrayList.get(position)); break;
            case TYPE_2: ((ViewHolder2)holder).bind(arrayList.get(position)); break;
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (arrayList.get(position).getType()) {
            case TYPE_1: return TYPE_1;
            case TYPE_2: return TYPE_2;
            default: return TYPE_3;
        }
    }

    public void setListData(ArrayList<ModelChatListChild> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();

    }

    class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView tvName;
        CircleImageView civAvatar;

        ViewHolder1(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            civAvatar = itemView.findViewById(R.id.civAvatar);
        }
        void bind(ModelChatListChild model) {
            tvName.setText(model.getName());
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {

        ViewHolder2(@NonNull View itemView) {
            super(itemView);
        }
        void bind(ModelChatListChild model) {

        }
    }

    class ViewHolder3 extends RecyclerView.ViewHolder {

        ViewHolder3(@NonNull View itemView) {
            super(itemView);
        }
    }
}
