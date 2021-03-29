package kz.tech.smartgrades.root.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.smartgrades.mentor_module.coins.list_view.ImageAvatarView;
import kz.tech.smartgrades.root.var_resources.GetAvatars;
import kz.tech.smartgrades.root.var_resources.GetToys;
import kz.tech.smartgrades.root.var_resources.VarID;


public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.ViewHolder>{
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(int image);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private Context context;
    private ArrayList<Integer> arrayList;
    public AvatarAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new ImageAvatarView(context));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder.imageView != null) {
            holder.imageView.setImageResource(arrayList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(VarID.ID_IV_AVATAR);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(arrayList.get(position));
                    }

                }
            });
        }
    }
    public void setData(int avatars) {
        switch (avatars) {
            case 1:
                int[] mans = GetAvatars.getManAvatars();
                for (int i = 0; mans.length > i; i++) {
                    arrayList.add(mans[i]);
                }
                break;
            case 2:
                int[] woman = GetAvatars.getWomanAvatars();
                for (int j = 0; woman.length > j; j++) {
                    arrayList.add(woman[j]);
                }
                break;
            case 3:
                int[] son = GetAvatars.getSonAvatars();
                for (int j = 0; son.length > j; j++) {
                    arrayList.add(son[j]);
                }
                break;
            case 4:
                int[] daughter = GetAvatars.getDaughterAvatars();
                for (int j = 0; daughter.length > j; j++) {
                    arrayList.add(daughter[j]);
                }
                break;
            case 5:
                int[] toys = GetToys.get();
                for (int j = 0; toys.length > j; j++) {
                    arrayList.add(toys[j]);
                }
                break;
        }

    }
}
