package kz.tech.smartgrades.parent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;

public class InteractionToolStepsToysAdapter extends RecyclerView.Adapter<InteractionToolStepsToysAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int image);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    private Context context;
    private ArrayList<Integer> arrayList;

    public InteractionToolStepsToysAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
        onLoadImage();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_grid_sreps_toys, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivImg.setImageResource(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImg;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.ivImg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        onItemClickListener.onItemClick(arrayList.get(position));
                    }
                }
            });
        }
    }

    private void onLoadImage() {
        arrayList.add(R.drawable.img_toys_1);
        arrayList.add(R.drawable.img_toys_2);
        arrayList.add(R.drawable.img_toys_3);
        arrayList.add(R.drawable.img_toys_4);
        arrayList.add(R.drawable.img_toys_5);

        arrayList.add(R.drawable.img_toys_6);
        arrayList.add(R.drawable.img_toys_7);
        arrayList.add(R.drawable.img_toys_8);
        arrayList.add(R.drawable.img_toys_9);
        arrayList.add(R.drawable.img_toys_10);

        arrayList.add(R.drawable.img_toys_11);
        arrayList.add(R.drawable.img_toys_12);
        arrayList.add(R.drawable.img_toys_13);
        arrayList.add(R.drawable.img_toys_14);
        arrayList.add(R.drawable.img_toys_15);

        arrayList.add(R.drawable.img_toys_16);
        arrayList.add(R.drawable.img_toys_17);
        arrayList.add(R.drawable.img_toys_18);
        arrayList.add(R.drawable.img_toys_19);
        arrayList.add(R.drawable.img_toys_20);

        arrayList.add(R.drawable.img_toys_21);
        arrayList.add(R.drawable.img_toys_22);
        arrayList.add(R.drawable.img_toys_23);
        arrayList.add(R.drawable.img_toys_24);
        arrayList.add(R.drawable.img_toys_25);

        arrayList.add(R.drawable.img_toys_26);
        arrayList.add(R.drawable.img_toys_27);
        arrayList.add(R.drawable.img_toys_28);
        arrayList.add(R.drawable.img_toys_29);
        arrayList.add(R.drawable.img_toys_30);

        arrayList.add(R.drawable.img_toys_31);
        arrayList.add(R.drawable.img_toys_32);
      //  arrayList.add(R.drawable.img_toys_33);
        arrayList.add(R.drawable.img_toys_34);
        arrayList.add(R.drawable.img_toys_35);

        arrayList.add(R.drawable.img_toys_36);
        arrayList.add(R.drawable.img_toys_37);
        arrayList.add(R.drawable.img_toys_38);
        arrayList.add(R.drawable.img_toys_39);
        arrayList.add(R.drawable.img_toys_40);

        arrayList.add(R.drawable.img_toys_41);
        arrayList.add(R.drawable.img_toys_42);
        arrayList.add(R.drawable.img_toys_43);
        arrayList.add(R.drawable.img_toys_44);
        arrayList.add(R.drawable.img_toys_45);

        arrayList.add(R.drawable.img_toys_46);
        arrayList.add(R.drawable.img_toys_47);
        arrayList.add(R.drawable.img_toys_48);
        arrayList.add(R.drawable.img_toys_49);
        arrayList.add(R.drawable.img_toys_50);

        arrayList.add(R.drawable.img_toys_51);
        arrayList.add(R.drawable.img_toys_52);
        arrayList.add(R.drawable.img_toys_53);
        arrayList.add(R.drawable.img_toys_54);
        arrayList.add(R.drawable.img_toys_55);

        arrayList.add(R.drawable.img_toys_56);
    }
}
