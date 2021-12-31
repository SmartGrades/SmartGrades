package kz.tech.smartgrades.school.adaptes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.App;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.childs_module.V;
import kz.tech.smartgrades.root.models.ModelLesson;

public class LessonsTagAdapter extends RecyclerView.Adapter<LessonsTagAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ModelLesson> LessonsList;
    private ArrayList<ModelLesson> ChosenLessonsList;
    private boolean delete = false;
    private boolean edit = false;

    public interface OnItemCLickListener{
        void onSelectLessonForClassClick(ModelLesson m);
        void onSelectLessonForDeleteClick(ModelLesson m);
    }
    private OnItemCLickListener onItemCLickListener;
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public LessonsTagAdapter(){
        this.context = App.app();
        this.LessonsList = new ArrayList<>();
        this.ChosenLessonsList = new ArrayList<>();
    }

    public void setDelete() {
        this.delete = true;
    }

    public void setEdit() {
        this.edit = true;
    }

    public ArrayList<ModelLesson> getLessonsList() {
        return this.LessonsList;
    }

    @Override
    public int getItemCount(){
        return LessonsList.size();
    }
    private void onClear(ArrayList<?> DataList){
        if(DataList.size() > 0) DataList.clear();
    }
    public void ClearData(){
        onClear(LessonsList);
        notifyDataSetChanged();
    }
    public void UpdateData(ArrayList<ModelLesson> ClassList){
        if(ClassList == null) return;
        onClear(this.LessonsList);
        this.LessonsList.addAll(ClassList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lessons_tag, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelLesson m = LessonsList.get(position);
        holder.tvLessonName.setText(m.getLessonName());
        if (delete) {
            holder.ivDelete.setVisibility(View.VISIBLE);
        }
        if (edit) {
            holder.tvLessonName.setTextColor(context.getResources().getColor(R.color.white));
            holder.tvLessonName.setBackground(context.getResources().getDrawable(R.drawable.background_square_blue));
            holder.tvLessonName.setPadding((int)Convert.DpToPx(5), (int)Convert.DpToPx(2), (int)Convert.DpToPx(5), (int)Convert.DpToPx(2));
        }
        if (m.isChosen()) {
            holder.tvLessonName.setTextColor(context.getResources().getColor(R.color.white));
            holder.tvLessonName.setBackground(context.getResources().getDrawable(R.drawable.background_square_green));
            holder.tvLessonName.setPadding((int)Convert.DpToPx(5), (int)Convert.DpToPx(2), (int)Convert.DpToPx(5), (int)Convert.DpToPx(2));
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvLessonName;
        ImageView ivDelete;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
            ivDelete = itemView.findViewById(R.id.ivDelete);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if (edit && LessonsList.get(getAdapterPosition()).isChosen()) {
                        ChosenLessonsList.remove(LessonsList.get(getAdapterPosition()));
                        LessonsList.get(getAdapterPosition()).setChosen(false);
                    } else if (edit && !LessonsList.get(getAdapterPosition()).isChosen()){
                        ChosenLessonsList.add(LessonsList.get(getAdapterPosition()));
                        LessonsList.get(getAdapterPosition()).setChosen(true);
                    } else {
                        if(onItemCLickListener == null) return;
                        onItemCLickListener.onSelectLessonForClassClick(LessonsList.get(getAdapterPosition()));
                    }
                    notifyDataSetChanged();
                }
            });

            ivDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener == null) return;
                    onItemCLickListener.onSelectLessonForDeleteClick(LessonsList.get(getAdapterPosition()));
                }
            });
        }
    }
}