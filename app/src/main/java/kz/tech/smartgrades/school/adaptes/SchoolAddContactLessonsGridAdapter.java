package kz.tech.smartgrades.school.adaptes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.models.ModelLesson;

public class SchoolAddContactLessonsGridAdapter extends RecyclerView.Adapter<SchoolAddContactLessonsGridAdapter.ViewHolder> {

    private ArrayList<ModelLesson> LessonList;
    private boolean IsEnableEdit = true;

    public interface OnItemCLickListener{
        void onLessonDeleteClick(ModelLesson m);
    }
    private OnItemCLickListener onItemCLickListener;
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public SchoolAddContactLessonsGridAdapter() {
        this.LessonList = new ArrayList<>();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }
    public void ClearData() {
        onClear(LessonList);
        notifyDataSetChanged();
    }
    public void UpdateData(ArrayList<ModelLesson> lessonsList) {
        if (lessonsList == null) return;
        onClear(LessonList);
        this.LessonList.addAll(lessonsList);
        notifyDataSetChanged();
    }
    public void setEditEnable(boolean b) {
        IsEnableEdit = b;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return LessonList.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school_add_contact_lessons_grid, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelLesson m = LessonList.get(position);
        holder.tvLessonName.setText(m.getLessonName());
        holder.ivDelete.setVisibility(IsEnableEdit ? View.VISIBLE : View.GONE);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLessonName;
        ImageView ivDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemCLickListener!=null) onItemCLickListener.onLessonDeleteClick(LessonList.get(getAdapterPosition()));
                }
            });
        }
    }
}