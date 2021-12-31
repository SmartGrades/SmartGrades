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
import kz.tech.smartgrades.mentor.models.ModelMentorStudents;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class MentorSchoolsTabAdapter extends RecyclerView.Adapter<MentorSchoolsTabAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelMentorStudents> DataList;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int m);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MentorSchoolsTabAdapter(Context context) {
        this.context = context;
        this.DataList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position){
        if(stringIsNullOrEmpty(DataList.get(position).getSchoolId()))
            return 0;
        else return 1;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0){
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mentor_my_students_tab, parent, false));
        }
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mentor_schools_tab, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelMentorStudents m = DataList.get(position);
        if(!stringIsNullOrEmpty(m.getSchoolName())) holder.tvTitle.setText(m.getSchoolName());
        else holder.tvTitle.setText("Добавленные");
    }

    @Override
    public int getItemCount() {
        return DataList.size();
    }

    private void onClear() {
        if (DataList.size() > 0) DataList.clear();
    }

    public void updateData(ArrayList<ModelMentorStudents> list) {
        if (listIsNullOrEmpty(list)) return;
        onClear();
        DataList.addAll(list);
    }

    public void selectList() {
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
                        onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
