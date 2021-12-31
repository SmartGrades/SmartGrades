package kz.tech.smartgrades.parent.adapters;

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
import kz.tech.smartgrades.mentor.models.ModelDefaultMessages;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.root.login.LoginKey;

public class MultiChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ParentActivity activity;
    private ArrayList<ModelDefaultMessages> multiTypeArrayList;

    public MultiChatAdapter(ParentActivity activity) {
        this.activity = activity;
        this.multiTypeArrayList = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        String id = activity.login.loadUserDate(LoginKey.ID);
        ModelDefaultMessages m = multiTypeArrayList.get(position);

        int iSource = 0;
        if (m.getSourceId().equals(id)) iSource = 10;

        /*switch (multiTypeArrayList.get(position).getMessageType()) {
            case "date":
                return 0;
            case "message":
                return 1 + iSource;
            case "gadget_time":
                return 2 + iSource;
            case "steps":
                return 3 + iSource;
            case "temporary_coins":
                return 4 + iSource;
            case "money":
                return 5 + iSource;
        }*/
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0://date
                return new ViewHolderDate(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_chat_date, parent, false));

            case 1://message
                return new ViewHolderMsg(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_chat_msg_target, parent, false));
            case 2://gadget_time
                return new ViewHolderGadgetTime(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_chat_gadget_time_mentor, parent, false));
            case 3://steps
                return new ViewHolderSteps(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_chat_steps_mentor, parent, false));
            case 4://temporary_coins
                return new ViewHolderTemporaryCoins(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_chat_temporary_coins_mentor, parent, false));
            case 5://money
                return new ViewHolderMoney(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_chat_money_target, parent, false));

            case 11://message
                return new ViewHolderMsg(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_chat_msg_source, parent, false));
            case 12://gadget_time
                return new ViewHolderGadgetTime(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_chat_gadget_time, parent, false));
            case 13://steps
                return new ViewHolderSteps(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_chat_steps, parent, false));
            case 14://temporary_coins
                return new ViewHolderTemporaryCoins(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_chat_temporary_coins, parent, false));
            case 15://money
                return new ViewHolderMoney(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_chat_money_source, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ModelDefaultMessages m = multiTypeArrayList.get(position);
        /*switch (m.getMessageType()) {
            case "date":
                ((ViewHolderDate) holder).tvMessage.setText(Convert.String2ShortTime(m.getDate()));
                break;
            case "message":
                ((ViewHolderMsg) holder).tvMessage.setText(m.getMessage());
                ((ViewHolderMsg) holder).tvDate.setText(Convert.String2ShortTime(m.getDate()));
                break;
            case "gadget_time":
                ((ViewHolderGadgetTime) holder).tvData.setText(m.getData());
                ((ViewHolderGadgetTime) holder).tvDate.setText(Convert.String2ShortTime(m.getDate()));
                break;
            case "steps":
                ((ViewHolderSteps) holder).tvStep.setText(m.getData());
                ((ViewHolderSteps) holder).tvDate.setText(Convert.String2ShortTime(m.getDate()));
                break;
            case "temporary_coins":
                ((ViewHolderTemporaryCoins) holder).tvData.setText(m.getData());
                ((ViewHolderTemporaryCoins) holder).tvMessage.setText(m.getMessage());
                ((ViewHolderTemporaryCoins) holder).tvDate.setText(Convert.String2ShortTime(m.getDate()));
                break;
            case "money":
                ((ViewHolderMoney) holder).tvData.setText(m.getData());
                ((ViewHolderMoney) holder).tvMessage.setText(m.getMessage());
                ((ViewHolderMoney) holder).tvDate.setText(Convert.String2ShortTime(m.getDate()));
                break;
        }*/
    }

    @Override
    public int getItemCount() {
        return multiTypeArrayList.size();
    }

    private void onClear() {
        if (multiTypeArrayList.size() > 0) multiTypeArrayList.clear();
    }

    public void updateList(ArrayList<ModelDefaultMessages> arrayList) {
        onClear();
        this.multiTypeArrayList.addAll(arrayList);
        notifyDataSetChanged();
    }


    class ViewHolderDate extends RecyclerView.ViewHolder {
        TextView tvMessage;

        ViewHolderDate(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvTransactionDate);
        }
    }

    class ViewHolderMsg extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvFullName, tvMessage, tvDate;

        ViewHolderMsg(@NonNull View itemView) {
            super(itemView);
            //civAvatar = itemView.findViewById(R.id.civAvatar);
            //tvFullName = itemView.findViewById(R.id.tvFullName);
            tvMessage = itemView.findViewById(R.id.tvTransactionDate);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
        }
    }

    class ViewHolderGadgetTime extends RecyclerView.ViewHolder {
        TextView tvData, tvDate;

        ViewHolderGadgetTime(@NonNull View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.tvData);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
        }
    }

    class ViewHolderSteps extends RecyclerView.ViewHolder {
        ImageView ivData;
        TextView tvDate, tvStep;

        ViewHolderSteps(@NonNull View itemView) {
            super(itemView);
            ivData = itemView.findViewById(R.id.ivData);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvStep = itemView.findViewById(R.id.tvStep);
        }

        void bind(String data) {
            switch (data) {
                case "-1":
                    ivData.setImageResource(R.drawable.img_step_minus_one);
                    break;
                case "-2":
                    ivData.setImageResource(R.drawable.img_step_minus_two);
                    break;
                case "-3":
                    ivData.setImageResource(R.drawable.img_step_minus_three);
                    break;
                case "-4":
                    ivData.setImageResource(R.drawable.img_step_minus_four);
                    break;
                case "-5":
                    ivData.setImageResource(R.drawable.img_step_minus_five);
                    break;
                case "+1":
                    ivData.setImageResource(R.drawable.img_step_plus_one);
                    break;
                case "+2":
                    ivData.setImageResource(R.drawable.img_step_plus_two);
                    break;
                case "+3":
                    ivData.setImageResource(R.drawable.img_step_plus_three);
                    break;
                case "+4":
                    ivData.setImageResource(R.drawable.img_step_plus_four);
                    break;
                case "+5":
                    ivData.setImageResource(R.drawable.img_step_plus_five);
                    break;
            }
        }
    }

    class ViewHolderTemporaryCoins extends RecyclerView.ViewHolder {
        TextView tvData, tvDate, tvMessage;

        ViewHolderTemporaryCoins(@NonNull View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.tvData);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvMessage = itemView.findViewById(R.id.tvTransactionDate);
        }
    }

    class ViewHolderMoney extends RecyclerView.ViewHolder {
        TextView tvData, tvDate, tvMessage;

        ViewHolderMoney(@NonNull View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.tvData);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvMessage = itemView.findViewById(R.id.tvTransactionDate);
        }
    }
}
