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
import kz.tech.smartgrades.parent.model.ModelGrades;
import kz.tech.smartgrades.parent.model.ModelTransactions;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ParentSponsorGradesListAdapter extends RecyclerView.Adapter<ParentSponsorGradesListAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelGrades> modelGrades;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onTransactionClick();
    }

    public ParentSponsorGradesListAdapter(ParentActivity activity) {
        this.activity = activity;
        this.modelGrades = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<ModelGrades> modelGrades) {
        onClear(this.modelGrades);
        this.modelGrades = modelGrades;
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_grades_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParentSponsorGradesListAdapter.ViewHolder holder, int position) {
        ModelGrades m = modelGrades.get(position);
        holder.tvGrade.setText(m.getGrade());
        if (m.getGrade().equals("0")) holder.tvGrade.setBackground(activity.getResources().getDrawable(R.drawable.background_square_red));
        else if (m.getGrade().equals("2")) holder.tvGrade.setBackground(activity.getResources().getDrawable(R.drawable.background_square_red));
        else if (m.getGrade().equals("3")) holder.tvGrade.setBackground(activity.getResources().getDrawable(R.drawable.background_square_orange));
        else if (m.getGrade().equals("4")) holder.tvGrade.setBackground(activity.getResources().getDrawable(R.drawable.background_square_green));
        else if (m.getGrade().equals("5")) holder.tvGrade.setBackground(activity.getResources().getDrawable(R.drawable.background_square_purple));
    }

    @Override
    public int getItemCount() {
        if (modelGrades != null) return modelGrades.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvGrade;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGrade = itemView.findViewById(R.id.tvGrade);
        }
    }
}
