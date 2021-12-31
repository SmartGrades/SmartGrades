package kz.tech.smartgrades.mentor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.school.models.ModelSchoolLessons;


public class SelectLessonsForAddedMentorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ModelSchoolLessons> dataList;
    private OnItemClickListener onItemClickListener;

    public SelectLessonsForAddedMentorAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
    }

    public interface OnItemClickListener {
        void onItemClick(ModelSchoolLessons m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_school_select_group_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ModelSchoolLessons m = dataList.get(position);
        ((ViewHolder)holder).tvTitle.setText(m.getLessonName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private void onClear() {
        if (dataList.size() > 0) dataList.clear();
    }

    public void updateData(ArrayList<ModelSchoolLessons> dataList) {
        onClear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(dataList.get(getAdapterPosition()));
                }
            });
        }
    }
}
