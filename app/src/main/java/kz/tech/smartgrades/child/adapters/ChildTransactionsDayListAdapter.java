package kz.tech.smartgrades.child.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.parent.model.ModelTransactions;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ChildTransactionsDayListAdapter extends RecyclerView.Adapter<ChildTransactionsDayListAdapter.ViewHolder> {

    private ChildActivity activity;
    private ArrayList<ModelTransactions> transactionsList;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(ModelTransactions transactionsList);
    }

    public ChildTransactionsDayListAdapter(ChildActivity activity) {
        this.activity = activity;
        this.transactionsList = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<ModelTransactions> transactionsList) {
        onClear(this.transactionsList);
        this.transactionsList = transactionsList;
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_income_info, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChildTransactionsDayListAdapter.ViewHolder holder, int position) {
        ModelTransactions m = transactionsList.get(position);
        holder.tvLessonName.setText(m.getLessonName());
        holder.tvTeacherName.setText(m.getFirstName() + " " + m.getLastName());
        if (!stringIsNullOrEmpty(m.getSum())) {
            if (Integer.parseInt(m.getSum()) > 0) {
                holder.tvIncomeSum.setText("+" + m.getSum());
            } else holder.tvIncomeSum.setText(m.getSum());
        }
        if (!stringIsNullOrEmpty(m.getGrade())) {
            int grade = Integer.parseInt(m.getGrade());
            if (grade == 0)
                holder.cvIncomeSum.setCardBackgroundColor(activity.getResources().getColor(R.color.red));
            else if (grade == 2)
                holder.cvIncomeSum.setCardBackgroundColor(activity.getResources().getColor(R.color.red));
            else if (grade == 3)
                holder.cvIncomeSum.setCardBackgroundColor(activity.getResources().getColor(R.color.orange_background));
            else if (grade == 4)
                holder.cvIncomeSum.setCardBackgroundColor(activity.getResources().getColor(R.color.green_background));
            else if (grade == 5)
                holder.cvIncomeSum.setCardBackgroundColor(activity.getResources().getColor(R.color.purple_background));
        }

        String avatar = m.getAvatar();
        if(!stringIsNullOrEmpty(avatar)){
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatarMentor);
        }
        else{
            holder.civAvatarMentor.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
        }
    }

    @Override
    public int getItemCount() {
        if (transactionsList != null) return transactionsList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civAvatarMentor;
        TextView tvLessonName;
        TextView tvTeacherName;
        TextView tvIncomeSum;
        CardView cvIncomeSum;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            civAvatarMentor = itemView.findViewById(R.id.civAvatarMentor);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
            tvTeacherName = itemView.findViewById(R.id.tvTeacherName);
            tvIncomeSum = itemView.findViewById(R.id.tvIncomeSum);
            cvIncomeSum = itemView.findViewById(R.id.cvIncomeSum);

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                }
            });
        }
    }
}
