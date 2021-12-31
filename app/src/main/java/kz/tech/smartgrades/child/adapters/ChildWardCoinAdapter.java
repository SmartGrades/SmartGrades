package kz.tech.smartgrades.child.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

import kz.tech.smartgrades.Convert;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.models.ModelChildWardCoin;
import kz.tech.smartgrades.mentor.models.ModelDefaultMessages;

public class ChildWardCoinAdapter extends RecyclerView.Adapter<ChildWardCoinAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelChildWardCoin> arrayList;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(String type, ArrayList<ModelChildWardCoin> arrayList);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ChildWardCoinAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int coinsCount = Convert.Str2Int(arrayList.get(position).getValue());
        holder.flButtons.setVisibility(View.GONE);
        holder.tvDayInfo.setVisibility(View.GONE);
        holder.pbAvatar.setVisibility(View.GONE);
        switch (arrayList.get(position).getType()) {
            case "coins":
                holder.ivAvatar.setImageResource(R.drawable.img_child_tool_coin_plus);
                holder.tvName.setText("Текущий счет");
                break;
            case "piggy":
                holder.ivAvatar.setImageResource(R.drawable.img_child_tool_pig);
                holder.tvName.setText("Копилка");
                break;
            case "depozit":
                holder.ivAvatar.setImageResource(R.drawable.img_progress_bar);
                holder.tvName.setText("Депозит");
                Date nowDate = new Date();
                /*Date startDate = Convert.S2D(arrayList.get(position).getDateStart());
                Date endDate = Convert.S2D(arrayList.get(position).getDateEnd());
                int days = (int) ((nowDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000));
                int totalDays = (int) ((endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000));
                holder.tvDayInfo.setText(days + "/" + totalDays);
                holder.tvDayInfo.setVisibility(View.VISIBLE);
                if (days > 0){
                    holder.pbAvatar.setMax(totalDays);
                    holder.pbAvatar.setProgress(days);
                    holder.pbAvatar.setVisibility(View.VISIBLE);
                }
                coinsCount = Convert.S2I(arrayList.get(position).getCoinsStart());*/
                break;
            case "bank":
                holder.ivAvatar.setImageResource(R.drawable.img_child_tool_bank);
                holder.tvName.setText("Банк");
                if (coinsCount > 0) holder.flButtons.setVisibility(View.VISIBLE);
                break;
        }
        holder.tvCoinsCount.setText(String.valueOf(coinsCount));
        if (coinsCount <= 0){
            holder.ivCoinsGroup0.setVisibility(View.GONE);
            holder.ivCoinsGroup1.setVisibility(View.GONE);
            holder.ivCoinsGroup2.setVisibility(View.GONE);
            holder.ivCoinsGroup3.setVisibility(View.GONE);
            holder.ivCoinsGroup4.setVisibility(View.GONE);
        }
        else if (coinsCount <= 18){
            setRowCoinsCount(holder.ivCoinsGroup0, coinsCount);
            holder.ivCoinsGroup0.setVisibility(View.VISIBLE);
            holder.ivCoinsGroup1.setVisibility(View.GONE);
            holder.ivCoinsGroup2.setVisibility(View.GONE);
            holder.ivCoinsGroup3.setVisibility(View.GONE);
            holder.ivCoinsGroup4.setVisibility(View.GONE);
        }
        else{
            setRowCoinsCount(holder.ivCoinsGroup1, 18);
            holder.ivCoinsGroup1.setVisibility(View.VISIBLE);
            holder.ivCoinsGroup0.setVisibility(View.GONE);
            coinsCount -= 18;
            if (coinsCount <= 18){
                setRowCoinsCount(holder.ivCoinsGroup2, coinsCount);
                holder.ivCoinsGroup2.setVisibility(View.VISIBLE);
            }
            else {
                setRowCoinsCount(holder.ivCoinsGroup2, 18);
                holder.ivCoinsGroup2.setVisibility(View.VISIBLE);
                coinsCount -= 18;
                if (coinsCount <= 18){
                    setRowCoinsCount(holder.ivCoinsGroup3, coinsCount);
                    holder.ivCoinsGroup3.setVisibility(View.VISIBLE);
                }
                else{
                    setRowCoinsCount(holder.ivCoinsGroup3, 18);
                    holder.ivCoinsGroup3.setVisibility(View.VISIBLE);
                    coinsCount -= 18;
                    setRowCoinsCount(holder.ivCoinsGroup4, coinsCount);
                    holder.ivCoinsGroup4.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void setRowCoinsCount(ImageView iv, int count){
        switch (count){
            case 1: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_1)); break;
            case 2: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_2)); break;
            case 3: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_3)); break;
            case 4: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_4)); break;
            case 5: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_5)); break;
            case 6: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_6)); break;
            case 7: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_7)); break;
            case 8: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_8)); break;
            case 9: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_9)); break;
            case 10: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_10)); break;
            case 11: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_11)); break;
            case 12: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_12)); break;
            case 13: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_13)); break;
            case 14: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_14)); break;
            case 15: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_15)); break;
            case 16: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_16)); break;
            case 17: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_17)); break;
            case 18: iv.setBackground(context.getResources().getDrawable(R.drawable.img_row_coins_18)); break;
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void onClear() {
        if (arrayList.size() > 0) arrayList.clear();
    }

    public void updateGroup(ArrayList<ModelChildWardCoin> arrayListe, ArrayList<ModelDefaultMessages> modelDefaultChat) {
        //onClear();
        this.arrayList = arrayListe;
        notifyDataSetChanged();
    }

    public void updateData() {
        notifyItemRangeChanged(0, arrayList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_child_coins, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCoinsCount;
        TextView tvDayInfo;
        TextView tvName;

        ImageView ivAvatar;
        ProgressBar pbAvatar;

        ImageView ivCoinsGroup0;
        ImageView ivCoinsGroup1;
        ImageView ivCoinsGroup2;
        ImageView ivCoinsGroup3;
        ImageView ivCoinsGroup4;

        FrameLayout flButtons;

        Button[] buttons = new Button[2];

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCoinsCount = itemView.findViewById(R.id.tvCoinsCount);
            tvDayInfo = itemView.findViewById(R.id.tvDayInfo);
            tvName = itemView.findViewById(R.id.tvName);

            ivAvatar = itemView.findViewById(R.id.civAvatar);
            pbAvatar = itemView.findViewById(R.id.pbAvatar);

            ivCoinsGroup0 = itemView.findViewById(R.id.ivCoinsGroup0);
            ivCoinsGroup1 = itemView.findViewById(R.id.ivCoinsGroup1);
            ivCoinsGroup2 = itemView.findViewById(R.id.ivCoinsGroup2);
            ivCoinsGroup3 = itemView.findViewById(R.id.ivCoinsGroup3);
            ivCoinsGroup4 = itemView.findViewById(R.id.ivCoinsGroup4);

            flButtons = itemView.findViewById(R.id.flButtons);

            buttons[0] = itemView.findViewById(R.id.btnCancel);;
            buttons[1] = itemView.findViewById(R.id.btnOk);;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(arrayList.get(getAdapterPosition()).getType(), arrayList);
                }
            });

            buttons[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick("cancel", arrayList);
                }
            });
            buttons[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick("ok", arrayList);
                }
            });
        }
    }
}
