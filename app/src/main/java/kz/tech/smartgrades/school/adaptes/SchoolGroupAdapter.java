package kz.tech.smartgrades.school.adaptes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.school.models.ModelSchoolGroups;


public class SchoolGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ModelSchoolGroups> dataList;
    private ArrayList<ModelSchoolGroups> showList;
    private OnItemClickListener onItemClickListener;

    public SchoolGroupAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        this.showList = new ArrayList<>();
    }

    public interface OnItemClickListener {
        void onItemClick(ModelSchoolGroups m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SchoolGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mentor_group_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ModelSchoolGroups m = showList.get(position);
        ((ViewHolder)holder).tvTitle.setText(m.getGroupName());
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    private void onClear() {
        if (dataList.size() > 0) dataList.clear();
    }

    public void updateData(ArrayList<ModelSchoolGroups> dataList) {
        onClear();
        this.dataList.addAll(dataList);
    }

    public void selectList(String type) {
        if (showList.size() > 0) showList.clear();
        for (int i = 0; i < dataList.size(); i++){
            if (dataList.get(i).getType().equals(type)) showList.add(dataList.get(i));
        }
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
                        onItemClickListener.onItemClick(showList.get(getAdapterPosition()));
                }
            });
        }
    }
}
